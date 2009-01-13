package com.inthinc.pro.reports;

import java.util.List;
import org.apache.log4j.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ProReportCompiler
{

    private static Logger logger = Logger.getLogger(ProReportCompiler.class);
    private JasperPrint jasperPrint;

    public ProReportCompiler()
    {
       
    }
    
    public JasperPrint compileReport(ReportCriteria reportCriteria)
    {
        populateReport(reportCriteria);
        return jasperPrint;
    }
    
    public JasperPrint compileReport(List<ReportCriteria> reportCriteriaList)
    {
        for(ReportCriteria reportCriteria: reportCriteriaList)
        {
            populateReport(reportCriteria);
        }
        return jasperPrint;
    }

    public void populateReport(ReportCriteria reportCriteria)
    {
        JasperPrint jp = null;
        try
        {
            JasperReport jr = ReportUtils.loadReport(reportCriteria.getReportType());
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(reportCriteria.getMainDataset());
            jp = JasperFillManager.fillReport(jr, reportCriteria.getPramMap(), ds);
            if (jasperPrint == null)
            {
                jasperPrint = jp;
            }
            else
            {
                for (Object o : jp.getPages())
                {
                    jasperPrint.addPage((JRPrintPage) o);
                }
            }

        }
        catch (JRException e)
        {
            logger.error(e);
        }
    }

}
