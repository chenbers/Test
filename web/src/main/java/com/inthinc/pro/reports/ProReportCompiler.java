package com.inthinc.pro.reports;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ReportType reportType;
    private JasperPrint jasperPrint;
    private Map<String, Object> paramMap;

    public ProReportCompiler(ReportType reportType)
    {
        this.reportType = reportType;
        paramMap = new HashMap<String, Object>();
        paramMap.put("REPORT_TITLE", reportType.getLabel());
    }

    public JasperPrint compileReport()
    {
        populateReport(reportType,null);
        return jasperPrint;
    }

    /**
     * 
     * @param masterDatasetCollection - each report template will be able to accept a master data set. The master datasets 
     *                           for each report template are passed in via the masterDataSetCollection. 
     *                           The key for each masterDataSet will be the name of the report. (The master dataset is usually used to 
     *                           populate the detail bands in the jasper templates.)
     * @param paramMap The parameterMap will contain any parameters that will need to be passed to any of the compliled 
     *                           jasper report templates. Any Sub Datasets (usually used for charts) will be also passed in via 
     *                           the parameter map.
     * 
     * @return JasperPrint object
     */
    @SuppressWarnings("unchecked")
    public JasperPrint compileReport(Map<String, List> masterDatasetCollection,Map<String,Object> paramMap)
    {
        this.paramMap.putAll(paramMap);
        if (reportType.getSubtypes() != null && reportType.getSubtypes().length > 0)
        {
            for (int i = 0; i < reportType.getSubtypes().length; i++)
            {
                List collection = masterDatasetCollection.get(reportType.getSubtypes()[1].toString());
                JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(collection);
                populateReport(reportType.getSubtypes()[1], ds);
            }
        }
        else
        {
            List collection = masterDatasetCollection.get(reportType.toString());
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(collection);
            populateReport(reportType, ds);
        }
        
        
        return jasperPrint;
    }

    public void populateReport(ReportType reportSection, JRBeanCollectionDataSource ds)
    {
        JasperPrint jp = null;
        try
        {
            JasperReport jr = ReportUtils.loadReport(reportSection);
            jp = JasperFillManager.fillReport(jr, paramMap, ds);
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
