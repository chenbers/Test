package com.inthinc.pro.backing.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.model.GroupLevel;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.reports.ReportType;

public class JsfFunctions
{
    private static final Logger logger = Logger.getLogger(JsfFunctions.class);
    
    //Not used... Just added this so that it is here.
    public static ReportType getReportType(String name)
    {
        logger.debug("Report Type: " + name);
        return ReportType.toReport(name);
    }
    
    public static List<ReportType> getReportTypes()
    {
        List<ReportType> reportTypeList = new ArrayList<ReportType>();
        reportTypeList.add(ReportType.OVERALL_SCORE);
        reportTypeList.add(ReportType.TREND);
        reportTypeList.add(ReportType.MPG_GROUP);
        return reportTypeList;
    }
    
    public static String convertDateTime(Date d,String p,TimeZone timeZone)
    {
        final String defaultFormat = "MMM dd, yyyy";
        Date date = new Date();
        String pattern = defaultFormat;
        if(p != null)
        {
            pattern = p;
        }
        if(d != null)
        {
            date = d;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        if(timeZone != null)
        {
            sdf.setTimeZone(timeZone);
        }
        return sdf.format(date);
    }
    
    public static Boolean messagesAvailable()
    {
        if(FacesContext.getCurrentInstance().getMessages().hasNext())
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }
    
    public static Integer getMessageCount()
    {
        int count = 0;
        Iterator<FacesMessage> iterator = FacesContext.getCurrentInstance().getMessages();
        while(iterator.hasNext())
        {
            iterator.next();
            count++;
        }
        
        return Integer.valueOf(count);
    }
    
    /**
     * Utility method to get back a URL for navigating to any group. The reason for this is if bookmarking is necessary, an h:outputLink 
     * may be used to embed the request in the URL.
     * 
     * @param group
     * @return URL of the group.
     */
    public static String getGroupURL(Group group)
    {
        String url = null;
        switch(group.getType())
        {
        case DIVISION: 
            url = GroupLevel.DIVISION.getUrl() + "?groupID=" + group.getGroupID().toString();
            break;
        case FLEET:
            url = GroupLevel.FLEET.getUrl() + "?groupID=" + group.getGroupID().toString();
            break;
        case TEAM:
            url = GroupLevel.TEAM.getUrl() + "?groupID=" + group.getGroupID().toString();
            break;
        }
        
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/" +  url;
    }
    
}
