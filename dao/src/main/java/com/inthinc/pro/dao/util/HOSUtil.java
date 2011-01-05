package com.inthinc.pro.dao.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.inthinc.hos.adjusted.HOSAdjustedList;
import com.inthinc.hos.model.HOSOrigin;
import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.HOSRecAdjusted;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.model.hos.HOSRecord;

public class HOSUtil {

    private static final long MS_IN_DAY = 86400000l;
    
    public static HOSAdjustedList getAdjustedListFromLogList(List<HOSRecord> hosRecList)
    {
        List<HOSRecAdjusted> adjustedList = new ArrayList<HOSRecAdjusted>();
        for (HOSRecord hosRec : hosRecList)
        {
            if (hosRec.getStatus() == null || !hosRec.getStatus().isGraphable() || hosRec.getDeleted())
                continue;
            HOSRecAdjusted hosDDLRec = new HOSRecAdjusted(hosRec.getHosLogID().toString(), 
                    hosRec.getStatus(), 
                    hosRec.getLogTime(), 
                    hosRec.getTimeZone());
            
            hosDDLRec.setEdited(hosRec.getEdited() || hosRec.getOrigin().equals(HOSOrigin.PORTAL));
            hosDDLRec.setServiceID(hosRec.getServiceID());
            hosDDLRec.setTrailerID(hosRec.getTrailerID());
            hosDDLRec.setVehicleID(hosRec.getVehicleID());
            hosDDLRec.setRuleType(hosRec.getDriverDotType());

            adjustedList.add(hosDDLRec);

        }
        Collections.reverse(adjustedList);
        return new HOSAdjustedList(adjustedList);

    }
    
    public static List<HOSRec> getRecListFromLogList(List<HOSRecord> hosRecList, Date endDate, boolean isDriverDOT)
    {
        List<HOSRec> recList = new ArrayList<HOSRec>();
        HOSRec leastRecent = null;
        for (HOSRecord hosRecord : hosRecList)
        {
            if (hosRecord.getStatus() == null || (hosRecord.getDeleted() != null && hosRecord.getDeleted()))
                continue;
            long totalRealMinutes = DateUtil.deltaMinutes(hosRecord.getLogTime(), endDate);
            endDate = hosRecord.getLogTime();
            HOSRec hosRec = new HOSRec(hosRecord.getHosLogID().toString(), 
                    hosRecord.getStatus(), 
                    totalRealMinutes,
                    hosRecord.getLogTime(), 
                    hosRecord.getTimeZone(),
                    hosRecord.getDriverDotType(),
                    hosRecord.getVehicleName(),
                    (hosRecord.getSingleDriver() == null) ? false : hosRecord.getSingleDriver(),
                    (hosRecord.getVehicleIsDOT() == null) ? false : hosRecord.getVehicleIsDOT() && !isDriverDOT);
            
            recList.add(hosRec);
//System.out.println(hosRec.getStatus().getName() + " " + hosRec.getTotalRealMinutes() + " " + hosRec.getLogTimeDate());            
            leastRecent = hosRec;
        }
        // pad with a 24 hour off duty record
// TODO: see if this should actually be in the hos jar        
        if (leastRecent == null) {
            recList.add(new HOSRec("", HOSStatus.OFF_DUTY, 1440, new Date(endDate.getTime()-MS_IN_DAY), TimeZone.getDefault(),
                    RuleSetType.NON_DOT, "", true, false));
        }
        else {
            HOSRec hosRec = new HOSRec("", HOSStatus.OFF_DUTY, 1440, new Date(leastRecent.getLogTimeDate().getTime()-MS_IN_DAY), leastRecent.getLogTimeZone(),
                    leastRecent.getRuleType(), "", leastRecent.isSingleDriver(), leastRecent.isNonDOTDriverDrivingDOTVehicle());
            recList.add(hosRec);
//            System.out.println(hosRec.getStatus().getName() + " " + hosRec.getTotalRealMinutes() + " " + hosRec.getLogTimeDate());
            }
        return recList;
    }


}
