package com.inthinc.pro.reports.jasper;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.model.ChartData;
import com.inthinc.pro.reports.util.ReportMessageUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class JasperReportBuilder
{

    private static Logger logger = Logger.getLogger(JasperReportBuilder.class);
    private JasperPrint jasperPrint;
    private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

    /**
     * 
     * @param reportCriteria
     * @return
     */
    public JasperPrint buildReport(ReportCriteria reportCriteria)
    {
        processReportCriteria(reportCriteria,null);
        return jasperPrint;
    }

    /**
     * 
     * @param reportCriteriaList
     * @return JasperPrint Object - will contain multiple page report dependent on the ReportCriteriaList
     */
    public JasperPrint buildReport(List<ReportCriteria> reportCriteriaList,FormatType formatType)
    {
        for (ReportCriteria reportCriteria : reportCriteriaList)
        {
            processReportCriteria(reportCriteria,formatType);
        }
        return jasperPrint;
    }

    private void processReportCriteria(ReportCriteria reportCriteria,FormatType formatType)
    {
        if (reportCriteria.getRecordsPerReport() != null)
        {
            breakUpReport(reportCriteria,formatType);
        }
        else
        {
            populateReport(reportCriteria,formatType);
        }
    }

    private void populateReport(ReportCriteria reportCriteria,FormatType formatType)
    {
        JasperPrint jp = null;
        try
        {
            JasperReport jr = ReportUtils.loadReport(reportCriteria.getReport(),formatType);
            InputStream imageInputStream = ReportUtils.loadFile("logo_main.gif");
            if(imageInputStream != null)
            {
                reportCriteria.getPramMap().put("REPORT_LOGO", imageInputStream);
            }
            
            Locale locale = DEFAULT_LOCALE;
            if(reportCriteria.getLocale() != null)
            {
                locale = reportCriteria.getLocale();
            }
            ResourceBundle resourceBundle = ReportMessageUtil.getBundle(locale);
            reportCriteria.getPramMap().put(JRParameter.REPORT_RESOURCE_BUNDLE, resourceBundle);

            // Lets break up the report if the recordsPerReport is set
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(reportCriteria.getMainDataset() != null? reportCriteria.getMainDataset() : Collections.EMPTY_LIST);
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

    @SuppressWarnings("unchecked")
    /*
     * breaks up the report into pages according to the reportCriteria.getRecordsPerReport property
     * 
     */
    private void breakUpReport(ReportCriteria reportCriteria,FormatType formatType)
    {
        List mainDataSet = new ArrayList();
        for (int i = 0; i < reportCriteria.getMainDataset().size(); i++)
        {
            mainDataSet.add(reportCriteria.getMainDataset().get(i));
            if ((i + 1) % reportCriteria.getRecordsPerReport() == 0 || (i + 1) == reportCriteria.getMainDataset().size())
            {
                if (mainDataSet.size() > 0)
                {
                    ReportCriteria rc = new ReportCriteria(reportCriteria);
                    rc.setMainDataset(mainDataSet);
                    rc.setLocale(reportCriteria.getLocale());
                    rc.addPramMap(getNewParamMap(reportCriteria.getPramMap(), reportCriteria.getMainDataSetIdField(),reportCriteria.getChartDataSetIdField(), mainDataSet));
                    populateReport(rc,formatType);
                }
                // Clear mainData without touching the last one created
                mainDataSet = new ArrayList();
            }
        }
    }

    
    @SuppressWarnings("unchecked")
    private Map<String, Object> getNewParamMap(Map<String, Object> paramMap, String masterIdField, String chartIdField, List mainDataSet)
    {
        Map<String,Object> newParamMap = new HashMap<String, Object>();
        Iterator<Entry<String, Object>> iterator = paramMap.entrySet().iterator();
        
        List<String> masterIdFieldValueList = new ArrayList<String>();
        for(Object obj:mainDataSet)
        {
            //Get the value from the bean property specified by the key field value
            String idFieldValue = getPropertyValue(masterIdField,obj);
            masterIdFieldValueList.add(idFieldValue);
 
        }
        
        /*
         * We're going to get all the chart data sets and compare the value for the idFeild with the seriesID field from these.
         */
        while(iterator.hasNext() && masterIdFieldValueList.size() > 0)
        {
            Entry<String, Object> entry = iterator.next();
            if(entry.getKey().toLowerCase().contains(ReportCriteria.SUB_DATASET.toLowerCase()))
            {
                List<ChartData> newChartDataList = new ArrayList<ChartData>();
                List<ChartData> chartDataList = ((List<ChartData>)entry.getValue());
                for(ChartData chartData:chartDataList)
                {
                    String chartIdFieldValue = chartData.getSeriesID();
                    if(chartIdField != null)
                    {
                        chartIdFieldValue = getPropertyValue(chartIdField,chartData);
                    }
                    
                    for(String value:masterIdFieldValueList)
                    {
                        if(chartIdFieldValue.toLowerCase().equals(value.toLowerCase()))
                        {
                            newChartDataList.add(chartData);
                        }   
                    }
                    
                }
                newParamMap.put(entry.getKey(), newChartDataList);
            }
            else
            {
                newParamMap.put(entry.getKey(), entry.getValue());
            }
            
        }
        
        return newParamMap;
    }
    
    private String getPropertyValue(String idField,Object obj)
    {
        Method[] methods = obj.getClass().getDeclaredMethods();
        Object idFieldValue = null;
        String[] idFields = idField.split("[.]");
        String iField = null;
        if(idFields != null && idFields.length > 1)
        {
            iField = idFields[0];
        }else
        {
            iField = idField;
        }
        for(int i=0;i < methods.length;i++)
        {
            if(methods[i].getName().toLowerCase().contains("get" + iField.toLowerCase()))
            {
                try
                {
                    idFieldValue = methods[i].invoke(obj);
                    if(idFields.length == 1){
                        break;
                    }else
                    {
                        return getPropertyValue(idField.replace(iField + ".", ""),idFieldValue);
                    }
                }
                catch (IllegalArgumentException e)
                {
                    logger.error("idField " + idField + " cannot be found in the main data set");
                    logger.error(e);
                }
                catch (IllegalAccessException e)
                {
                    logger.error(e);
                }
                catch (InvocationTargetException e)
                {
                    logger.error(e);
                }
                break;
            }
        }
        
        
        
        String returnValue = null;
        if(idFieldValue != null)
        {
            returnValue = idFieldValue.toString();
        }
        return returnValue;
    }
    
    

}
