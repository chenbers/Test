package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.ReportSchedule;
import com.inthinc.pro.reports.ReportGroup;

public class ReportScheduleBean
{
    
    
    private ReportGroup reportGroup;
    private Duration duration;
    private Date startDate;
    private Date endDate;
    private ReportSchedule reportSchedule;
    
    
    public List<ReportGroup> getReportGroups(){
        return (List<ReportGroup>)Arrays.asList(ReportGroup.values());
    }
    
    public List<SelectItem> getReportGroupsAsSelectItems()
    {
        List<SelectItem> selectList = new ArrayList<SelectItem>();
        for(ReportGroup rt:getReportGroups())
        {
            selectList.add(new SelectItem(rt,rt.getLabel()));
        }
        
        return selectList;
    }
    
    public List<SelectItem> getDurationAsSelectItems()
    {
        List<SelectItem> selectList = new ArrayList<SelectItem>();
        for(Duration d : EnumSet.allOf(Duration.class))
        {
            selectList.add(new SelectItem(d,d.toString()));
        }
        
 
        return selectList;
    }

}
