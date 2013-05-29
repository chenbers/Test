package com.inthinc.pro.dao.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.inthinc.hos.adjusted.HOSAdjustedList;
import com.inthinc.hos.model.HOSOrigin;
import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.HOSRecAdjusted;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.rules.HOSRules;
import com.inthinc.hos.rules.RuleSetFactory;
import com.inthinc.hos.util.DebugUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.hos.HOSRecord;

public class HOSUtil {

    private static final long MS_IN_DAY = 86400000l;
    private static final Logger logger = Logger.getLogger(HOSUtil.class);

    
    public static HOSAdjustedList getAdjustedListFromLogList(List<HOSRecord> hosRecordList, Date endDate)
    {
        List<HOSRec> recList = new ArrayList<HOSRec>(); 
        for (HOSRecord hosRec : hosRecordList) {
            HOSStatus status = hosRec.getStatus(); 
            if (status  == null || !status.isGraphable() || hosRec.getDeleted())
                continue;
            HOSRec rec = HOSUtil.mapHOSRecord(hosRec, 0, endDate, true);
            rec.setEdited(hosRec.getEdited() || hosRec.getOrigin().equals(HOSOrigin.PORTAL));
            recList.add(rec); 

        }
        if (recList.size() > 0) {
            RuleSetType ruleSetType = recList.get(0).getRuleType();
        
            DebugUtil.dumpLogs(recList);
        
            HOSRules rules = RuleSetFactory.getRulesForRuleSetType(ruleSetType);
            recList = rules.adjustStatuses(recList, endDate);
        }

        return new HOSAdjustedList(recList, endDate);
    }

    public static HOSAdjustedList getOriginalAdjustedListFromLogList(List<HOSRecord> hosRecordList, Date endDate)
    {
        List<HOSRec> recList = new ArrayList<HOSRec>(); //HOSUtil.getRecListFromLogList(hosRecordList, endDate, driver.getDot() != null && driver.getDot() != RuleSetType.NON_DOT);
        for (HOSRecord hosRec : hosRecordList) {
            if (hosRec.getOrigin().equals(HOSOrigin.PORTAL))
                continue;
            HOSStatus status = (hosRec.getEdited() && hosRec.getOriginalStatus() != null) ? hosRec.getOriginalStatus() : hosRec.getStatus(); 
            if (status  == null || !status.isGraphable() || hosRec.getDeleted())
                continue;
            HOSRec rec = HOSUtil.mapHOSRecord(hosRec, 0, endDate, true);
            rec.setStatus(status);
            rec.setEdited(false);
            rec.setLogTimeDate(hosRec.getOriginalLogTime() == null || hosRec.getOriginalLogTime().getTime() == 0l ? hosRec.getLogTime() : hosRec.getOriginalLogTime());
            recList.add(rec); 

        }
        
        if (recList.size() > 0) {
            Collections.sort(recList);
            RuleSetType ruleSetType = recList.get(0).getRuleType();
        
            DebugUtil.dumpLogs(recList);
        
            HOSRules rules = RuleSetFactory.getRulesForRuleSetType(ruleSetType);
            recList = rules.adjustStatuses(recList, endDate);
        }

        return new HOSAdjustedList(recList, endDate);
    }
    
    public static HOSRec mapHOSRecord(HOSRecord hosRecord, long totalRealMinutes, Date endDate, Boolean isDriverDOT) {
        HOSRec hosRec = new HOSRec((hosRecord.getHosLogID() == null) ? "" : hosRecord.getHosLogID().toString(), 
                hosRecord.getStatus(), 
                totalRealMinutes,
                hosRecord.getLogTime(), 
                hosRecord.getTimeZone(),
                hosRecord.getDriverDotType(),
                hosRecord.getVehicleName(),
                (hosRecord.getSingleDriver() == null) ? false : hosRecord.getSingleDriver(),
                (hosRecord.getVehicleIsDOT() == null) ? false : hosRecord.getVehicleIsDOT() && !isDriverDOT);
        hosRec.setEndTimeDate(endDate);
        hosRec.setVehicleID(hosRecord.getVehicleID());
        hosRec.setEdited(hosRecord.getEdited() == null ? false : hosRecord.getEdited());
        hosRec.setServiceID(hosRecord.getServiceID());
        hosRec.setTrailerID(hosRecord.getTrailerID());
        hosRec.setLat(hosRecord.getLat() == null || hosRecord.getLat() == 0f ? null : new Double(hosRecord.getLat()));
        hosRec.setLng(hosRecord.getLng() == null || hosRecord.getLng() == 0f ? null : new Double(hosRecord.getLng()));
        return hosRec;
        
    }
    
    
    public static List<HOSRec> getRecListFromLogList(List<HOSRecord> hosRecList, Date endDate, boolean isDriverDOT)
    {
        List<HOSRec> recList = new ArrayList<HOSRec>();
        for (HOSRecord hosRecord : hosRecList)
        {
            if (hosRecord.getStatus() == null || (hosRecord.getDeleted() != null && hosRecord.getDeleted()) || hosRecord.getLogTime().after(endDate))
                continue;
            long totalRealMinutes = DateUtil.deltaMinutes(hosRecord.getLogTime(), endDate);
            HOSRec hosRec = HOSUtil.mapHOSRecord(hosRecord, totalRealMinutes, endDate, isDriverDOT); 
            endDate = hosRecord.getLogTime();
            
            recList.add(hosRec);
        }
        return recList;
    }

    public static List<ByteArrayOutputStream> packageLogsToShip(List<HOSRecord> recordList, Driver driver) throws IOException 
    {
        
        if (recordList.size() == 0)
        {
            //Send a OFF_DUTY record down to device if no records exist.
            HOSRecord dummyRec = new HOSRecord();
            dummyRec.setDriverID(driver.getDriverID());
            dummyRec.setStatus(HOSStatus.OFF_DUTY); 
            dummyRec.setLogTime(new GregorianCalendar(1970,1,1).getTime());
            dummyRec.setDriverDotType(RuleSetType.NON_DOT);
            recordList.add(dummyRec);
        }

        int recordSize = addHOSRuleSetChangeRecordsToList(recordList);
        return packageHosSummaryHistory(recordList, recordSize, driver.getPerson().getEmpid());
    }
      
    private static int addHOSRuleSetChangeRecordsToList(List<HOSRecord> recordList)
    {
        int recordSize = 0;
        int currentHOSRulesetId = -1;
        List<HOSRecord> adjustedRecordList = new ArrayList<HOSRecord>();
        for (HOSRecord record : recordList)
        {
            if (currentHOSRulesetId != record.getDriverDotType().getCode())
            {   
                currentHOSRulesetId = record.getDriverDotType().getCode();
                adjustedRecordList.add(createRulesetChangeRecord(currentHOSRulesetId));
            }
            adjustedRecordList.add(record);
            
            if (recordSize < getRecordSize(record))
                recordSize = getRecordSize(record);                 

        }
        return recordSize;
    }
    
    private static HOSRecord createRulesetChangeRecord(int ruleset)
    {
        HOSRecord record = new HOSRecord();
        record.setLng(0F);
        record.setLat(0F);
        record.setStatus(null);
        record.setVehicleOdometer((long)ruleset);
        record.setDriverDotType(RuleSetType.valueOf(ruleset));
        record.setLogTime(new java.util.Date());
        return record;
    }


    private static int getRecordSize(HOSRecord record)
    {
        final int BASE_REC_SIZE = 15;

        int size =  BASE_REC_SIZE  +
                record.getVehicleName().getBytes().length + 1 +
                (record.getTrailerID() == null ? 0 : record.getTrailerID().getBytes().length) + 1 +
                (record.getServiceID() == null ? 0 : record.getServiceID().getBytes().length) + 1 +
                (record.getLocation() == null ? 0 : record.getLocation().getBytes().length) + 1 +
                (record.getVehicleLicense() == null ? 0 : record.getVehicleLicense().getBytes().length) + 1;

            if (record.getStatus() == HOSStatus.FUEL_STOP)
                size += 4;
            else
                size += 2; //HOS ruleset

        return size;
    }

    
    
    final static int m_messageSizeLimit = 135;
    public static final int HOS_RULESET_CHANGE = 27;


    private static List<ByteArrayOutputStream> packageHosSummaryHistory(List<HOSRecord> recordList, int recordSize, String employeeId) throws IOException
    {
        
        
        int recordCount = recordList.size();
        java.util.Date date;

        int time;
        byte driverState;
        int hosRuleId;

        DataOutputStream attachmentStream = null;
        ByteArrayOutputStream outputStream = null;
        
            
        int count = 0;
        int recordsRead = 0;
        int recordsInEmailCount = 0;
        int totalPackets = 0;
        int currentPacketNumber = 0;
        int timeStamp = (int)new java.util.Date().getTime();
        List<ByteArrayOutputStream> outputList = new ArrayList<ByteArrayOutputStream>(); 
        
        for (HOSRecord record : recordList) {
            recordsRead++;
            count++;

            driverState = (record.getStatus()==null) ? HOS_RULESET_CHANGE : record.getStatus().getCode().byteValue();  
            date = record.getLogTime();
            hosRuleId =  record.getDriverDotType().getCode();
            
            logger.debug("StateHistorySummary Driver: " + employeeId + " Date: " + date + " Driver State: "+driverState);

            if (count == 1) {
                //Grab how many records we can fit into the email
                //15 accounts for info. at top of file, 5 for each record
                recordsInEmailCount = (int) Math.floor((m_messageSizeLimit - 25)/5);
                //If it's greater than the total # of records left then use # of records left.
                recordsInEmailCount = Math.min(recordsInEmailCount, recordCount - (recordsRead - count));

                if (recordsRead == 1) {
                    totalPackets = (int) Math.ceil(recordCount/(float)recordsInEmailCount);
                }

                logger.debug("recordsInEmailCount: " + recordsInEmailCount);
                logger.debug("totalPackets: " + totalPackets);

                outputStream = new ByteArrayOutputStream();
                attachmentStream = new DataOutputStream(outputStream);

                attachmentStream.writeInt(timeStamp);
                if (employeeId.length() > 10 )
                    attachmentStream.write(employeeId.substring(0, 10).trim().getBytes());
                else
                    attachmentStream.write(employeeId.getBytes());

                attachmentStream.writeByte((byte)0);
                attachmentStream.writeByte((byte)currentPacketNumber);//0
                attachmentStream.writeByte((byte)totalPackets);//1
                attachmentStream.writeByte((byte)recordsInEmailCount);//0
                currentPacketNumber++;
            }

            if (driverState == HOS_RULESET_CHANGE) //Tells MCM ruleset change
            {
                attachmentStream.writeByte(HOS_RULESET_CHANGE);
                attachmentStream.writeInt(hosRuleId);
            }
            else
            {
                attachmentStream.writeByte(driverState);
                time = (int) (date.getTime()/1000);
                attachmentStream.writeInt(time);
            }
            


            //If any more messages will push us over the limit, add that much to list
            if(((count + 1) > recordsInEmailCount) && recordsInEmailCount > 0 && count > 0)
            {
                attachmentStream.close();
                logger.debug("Email Sent count = : " + count + " recordsInEmailCount= " + recordsInEmailCount);
                count = 0;

                outputList.add(outputStream);
            }
        }

        return outputList;
    }   // end add
    
    

}
