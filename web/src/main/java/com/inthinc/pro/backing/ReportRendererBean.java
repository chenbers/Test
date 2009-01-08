package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.util.MessageUtil;

public class ReportRendererBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(ReportRendererBean.class);

    // Report Beans
    private MpgBean mpgBean;
    private BreakdownBean breakdownBean;
    private TrendBean trendBean;
    private ReportRenderer reportRenderer;
    

    // For single reports
    private ReportType reportType;

    // For complex reports
    private List<ReportType> reportTypes = new ArrayList<ReportType>(0);
    private String emailAddress;
    private Duration duration;
    
    public ReportRendererBean()
    {
        
    }


//    public void exportSingleReportToPDF()
//    {
//        ReportCriteria reportCriteria = null;
//        switch(reportType){
//        case OVERALL_SCORE:reportCriteria = breakdownBean.buildReportCriteria();break;
//        case TREND: reportCriteria = trendBean.buildReportCriteria();break;
//        case MPG_GROUP: reportCriteria = mpgBean.buildReportCriteria();break;
//        }
//        
//        if(reportCriteria != null){
//            reportRenderer.exportSingleReportToPDF(reportCriteria, getFacesContext());
//        }
//    }
    
    public void exportReportToPDF()
    {
        List<ReportCriteria> reportCriteriaList = new ArrayList<ReportCriteria>();
        for(ReportType rt:reportTypes)
        {
            switch(rt){
            case OVERALL_SCORE:reportCriteriaList.add(breakdownBean.buildReportCriteria());break;
            case TREND: reportCriteriaList.add(trendBean.buildReportCriteria());break;
            case MPG_GROUP: reportCriteriaList.add(mpgBean.buildReportCriteria());break;
            }
        }
        
        if(duration != null)
        {
            for(ReportCriteria rt:reportCriteriaList){
                rt.setDuration(this.duration);
            }
        }
        
        
        if(reportCriteriaList.size() > 0){
            reportRenderer.exportMultipleReportsToPDF(reportCriteriaList, getFacesContext());
        }
    }
    
    public void sendReportViaEmail()
    {
        List<ReportCriteria> reportCriteriaList = new ArrayList<ReportCriteria>();
        for(ReportType rt:reportTypes)
        {
            switch(rt){
            case OVERALL_SCORE:reportCriteriaList.add(breakdownBean.buildReportCriteria());break;
            case TREND: reportCriteriaList.add(trendBean.buildReportCriteria());break;
            case MPG_GROUP: reportCriteriaList.add(mpgBean.buildReportCriteria());break;
            }
        }
        
        if(duration != null)
        {
            for(ReportCriteria rt:reportCriteriaList){
                rt.setDuration(this.duration);
            }
        }
        
        
        if(reportCriteriaList.size() > 0){
            reportRenderer.exportReportToEmail(reportCriteriaList, emailAddress);
        }
        
        
        addInfoMessage(MessageUtil.getMessageString("reports_email_sent"));
        
        
    }

    public ReportType getReportType()
    {
        return reportType;
    }

    public void setReportType(ReportType reportType)
    {
        reportTypes.add(reportType);
        this.reportType = reportType;
    }

    public MpgBean getMpgBean()
    {
        return mpgBean;
    }

    public void setMpgBean(MpgBean mpgBean)
    {
        this.mpgBean = mpgBean;
    }

    public BreakdownBean getBreakdownBean()
    {
        return breakdownBean;
    }

    public void setBreakdownBean(BreakdownBean breakdownBean)
    {
        this.breakdownBean = breakdownBean;
    }

    public TrendBean getTrendBean()
    {
        return trendBean;
    }

    public void setTrendBean(TrendBean trendBean)
    {
        this.trendBean = trendBean;
    }

    public void setReportRenderer(ReportRenderer reportRenderer)
    {
        this.reportRenderer = reportRenderer;
    }

    public ReportRenderer getReportRenderer()
    {
        return reportRenderer;
    }

    public void setReportTypes(List<ReportType> reportTypes)
    {
        this.reportTypes = reportTypes;
    }

    public List<ReportType> getReportTypes()
    {
        return reportTypes;
    }
    
    public List<ReportType> getReportTypeList(){
        List<ReportType> reportTypeList = new ArrayList<ReportType>();
        reportTypeList.add(ReportType.OVERALL_SCORE);
        reportTypeList.add(ReportType.TREND);
        reportTypeList.add(ReportType.MPG_GROUP);
        return reportTypeList;
    }
    
    
    public List<SelectItem> getReportTypeAsSelectItems()
    {
        List<SelectItem> selectList = new ArrayList<SelectItem>();
        for(ReportType rt:getReportTypeList())
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

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setDuration(Duration duration)
    {
        this.duration = duration;
    }

    public Duration getDuration()
    {
        return duration;
    }

}
