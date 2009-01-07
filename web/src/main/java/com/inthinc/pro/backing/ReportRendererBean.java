package com.inthinc.pro.backing;

import org.apache.log4j.Logger;

import com.inthinc.pro.reports.ReportType;


public class ReportRendererBean extends BaseBean 
{
    private static final Logger logger = Logger.getLogger(ReportRendererBean.class);

    //Report Beans
    private MpgBean mpgBean;
    private BreakdownBean breakdownBean;
    private TrendBean trendBean;

    private ReportType reportType;
    
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
    
}
