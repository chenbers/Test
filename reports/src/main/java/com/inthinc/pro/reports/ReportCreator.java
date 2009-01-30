package com.inthinc.pro.reports;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import com.inthinc.pro.reports.jasper.JasperReport;
import com.inthinc.pro.reports.jasper.JasperReportBuilder;
import com.inthinc.pro.reports.mail.ReportMailer;

public class ReportCreator<T extends Report>
{
    
    private Class<T> reportType;
    private T report;
    private ReportMailer reportMailer;
    
    @SuppressWarnings("unchecked")
    public ReportCreator(ReportMailer reportMailer)
    {
        this.reportMailer = reportMailer;
        reportType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    public T getReport(ReportCriteria reportCriteria)
    {
        List<ReportCriteria> reportCriteriaList = new ArrayList<ReportCriteria>();
        reportCriteriaList.add(reportCriteria);
        return getReport(reportCriteriaList);
    }
    
    @SuppressWarnings("unchecked")
    public T getReport(List<ReportCriteria> reportCriteriaList)
    {
      
            if (reportType.equals(JasperReport.class))
            {
                JasperReport jr = new JasperReport();
                jr.setReportBuilder(new JasperReportBuilder());
                jr.setReportMailer(reportMailer);
                jr.setReportCriteriaList(reportCriteriaList);
                report = (T) jr;
            }
            
        
        return report;
    }
}
