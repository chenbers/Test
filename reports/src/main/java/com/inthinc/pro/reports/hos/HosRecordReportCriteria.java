package com.inthinc.pro.reports.hos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.hos.model.HOSRec;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;

public abstract class HosRecordReportCriteria extends ReportCriteria {

    private static final Logger logger = Logger.getLogger(HosRecordReportCriteria.class);
    
    protected DateTimeFormatter dateTimeFormatter;
    
    public HosRecordReportCriteria(ReportType reportType, Locale locale) 
    {
        super(reportType, "", locale);
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
    }
    
    protected List<HOSRec> getRecListFromLogList(List<HOSRecord> hosRecList, Date endDate, boolean isDriverDOT)
    {
        List<HOSRec> recList = new ArrayList<HOSRec>();
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

        }
        return recList;
    }



}
