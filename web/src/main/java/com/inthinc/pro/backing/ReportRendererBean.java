package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.ReportType;

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
    private List<ReportType> reportTypes;


    public void exportSingleReportToPDF()
    {
        ReportCriteria reportCriteria = null;
        switch(reportType){
        case OVERALL_SCORE:reportCriteria = breakdownBean.buildReportCriteria();break;
        case TREND: reportCriteria = trendBean.buildReportCriteria();break;
        case MPG_GROUP: reportCriteria = mpgBean.buildReportCriteria();break;
        }
        
        if(reportCriteria != null){
            reportRenderer.exportSingleReportToPDF(reportCriteria, getFacesContext());
        }
    }
    
    public void exportMultipleReportsToPDF()
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
        
        
        if(reportCriteriaList.size() > 0){
            reportRenderer.exportMultipleReportsToPDF(reportCriteriaList, getFacesContext());
        }
    }

    public ReportType getReportType()
    {
        return reportType;
    }

    public void setReportType(ReportType reportType)
    {
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

}
