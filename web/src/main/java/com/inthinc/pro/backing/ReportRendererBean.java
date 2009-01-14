package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.Report;
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
    private Report report;

    // For complex reports
    private List<Report> reports = new ArrayList<Report>(0);
    private String emailAddress;
    private Duration duration;
    
    public ReportRendererBean()
    {
        
    }
    
    public void exportReportToPdf()
    {
        exportReportToPDF();
    }
    
    public void exportReportToPDF()
    {
        List<ReportCriteria> reportCriteriaList = new ArrayList<ReportCriteria>();
        for(Report rt:reports)
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
            reportRenderer.exportReportToPDF(reportCriteriaList, getFacesContext());
        }
    }
    
    public void emailReport()
    {
        List<ReportCriteria> reportCriteriaList = new ArrayList<ReportCriteria>();
        for(Report rt:reports)
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

    public Report getReport()
    {
        return report;
    }

    public void setReport(Report report)
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

    public void setReports(List<Report> reports)
    {
        this.reports = reports;
    }

    public List<Report> getReports()
    {
        return reports;
    }
    
    public List<Report> getReportList(){
        List<Report> reportList = new ArrayList<Report>();
        reportList.add(Report.OVERALL_SCORE);
        reportList.add(Report.TREND);
        reportList.add(Report.MPG_GROUP);
        return reportList;
    }
    
    
    public List<SelectItem> getReportsAsSelectItems()
    {
        List<SelectItem> selectList = new ArrayList<SelectItem>();
        for(Report rt:getReportList())
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
            emailAddress = getProUser().getUser().getPerson().getEmail();
        }
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
