package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.util.MessageUtil;

public class DashboardReportBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(DashboardReportBean.class);
    
    // Report Beans
    private MpgBean mpgBean;
    private OverallScoreBean overallScoreBean;
    private TrendBean trendBean;
    private ReportRenderer reportRenderer;
    

    // For single reports
    private ReportType report;

    // For complex reports
    private List<ReportType> reports = new ArrayList<ReportType>(0);
    private String emailAddress;
    private Integer groupID;
    private Duration duration;
    
    public DashboardReportBean()
    {
        
    }
    
    public void exportReportToPdf()
    {
        exportReportToPDF();
    }
    
    public void exportReportToPDF()
    {
        List<ReportCriteria> reportCriteriaList = new ArrayList<ReportCriteria>();
        for(ReportType rt:reports)
        {
            switch(rt){
            case OVERALL_SCORE:reportCriteriaList.add(overallScoreBean.buildReportCriteria());break;
            case TREND: reportCriteriaList.add(trendBean.buildReportCriteria());break;
            case MPG_GROUP: reportCriteriaList.add(mpgBean.buildReportCriteria());break;
            }
        }
        
        if(duration != null)
        {
            for(ReportCriteria rt:reportCriteriaList){
                rt.setDuration(this.duration);
                rt.setLocale(getLocale());
                rt.setUseMetric(getMeasurementType() == MeasurementType.METRIC);
            }
        }
        
        
        if(reportCriteriaList.size() > 0){
            reportRenderer.exportReportToPDF(reportCriteriaList, getFacesContext());
        }
    }
    
    public void emailReport()
    {
        List<ReportCriteria> reportCriteriaList = new ArrayList<ReportCriteria>();
        for(ReportType rt:reports)
        {
            switch(rt){
            case OVERALL_SCORE:reportCriteriaList.add(overallScoreBean.buildReportCriteria());break;
            case TREND: reportCriteriaList.add(trendBean.buildReportCriteria());break;
            case MPG_GROUP: reportCriteriaList.add(mpgBean.buildReportCriteria());break;
            }
        }
        
        if(duration != null)
        {
            for(ReportCriteria rt:reportCriteriaList){
                rt.setDuration(this.duration);
                rt.setLocale(getLocale());
                rt.setUseMetric(getMeasurementType() == MeasurementType.METRIC);
            }
        }
        
        
        if(reportCriteriaList.size() > 0){
            reportRenderer.exportReportToEmail(reportCriteriaList, emailAddress);
        }
        
        
        addInfoMessage(MessageUtil.getMessageString("reports_email_sent"));
        
        
    }

    public ReportType getReport()
    {
        return report;
    }

    public void setReport(ReportType report)
    {
        reports.add(report);
        logger.debug("Report added: " + report.toString());
        this.report = report;
    }

    public MpgBean getMpgBean()
    {
        return mpgBean;
    }

    public void setMpgBean(MpgBean mpgBean)
    {
        this.mpgBean = mpgBean;
    }

    public OverallScoreBean getOverallScoreBean()
    {
        return overallScoreBean;
    }

    public void setOverallScoreBean(OverallScoreBean overallScoreBean)
    {
        this.overallScoreBean = overallScoreBean;
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

    public void setReports(List<ReportType> reports)
    {
        this.reports = reports;
    }

    public List<ReportType> getReports()
    {
        return reports;
    }
    
    public List<ReportType> getReportList(){
        List<ReportType> reportList = new ArrayList<ReportType>();
        reportList.add(ReportType.OVERALL_SCORE);
        reportList.add(ReportType.TREND);
        reportList.add(ReportType.MPG_GROUP);
        return reportList;
    }
    
    
    public List<SelectItem> getReportsAsSelectItems()
    {
        List<SelectItem> selectList = new ArrayList<SelectItem>();
        for(ReportType rt:getReportList())
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
        if(emailAddress == null){
            emailAddress = getProUser().getUser().getPerson().getPriEmail();
        }
        return emailAddress;
    }

    public Integer getGroupID()
    {
        return groupID;
    }

    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
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
