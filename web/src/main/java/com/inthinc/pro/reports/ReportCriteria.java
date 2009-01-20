package com.inthinc.pro.reports;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.reports.model.ChartData;

public class ReportCriteria
{
    private List mainDataset;
    private Map<String, Object> paramMap = new HashMap<String, Object>();
    private Report report;
    private Duration duration;
    private Integer recordsPerReport;
    private String mainDatasetIdField;
    private String chartDataSetIdField;

    private static final String INTHINC_NAME = "Inthinc";

    public static final String SUB_DATASET = "SUB_DATASET";
    private int subsetIndex = 1;
    
    public ReportCriteria(ReportCriteria reportCriteria)
    {
        setReport(reportCriteria.getReport());
        paramMap.put("ENTITY_NAME", reportCriteria.getPramMap().get("ENTITY_NAME"));
    }

    public ReportCriteria(Report report, String groupName, String accountName)
    {
        setReport(report);

        String entityName = "";
        if (accountName == null)
        {
            paramMap.put(" ", "");
        }
        else
        {
            entityName = accountName + " - ";
        }

        entityName += groupName;

        paramMap.put("ENTITY_NAME", entityName);
    }

    public void setMainDataset(List mainDataset)
    {
        this.mainDataset = mainDataset;
    }

    public List getMainDataset()
    {
        return this.mainDataset;
    }

    private void setReport(Report report)
    {
        this.report = report;
    }

    public Report getReport()
    {
        return report;
    }

    public void addParameter(String name, Object value)
    {
        paramMap.put(name, value);
    }
    
    public void addPramMap(Map<String, Object> paramMap)
    {
        this.paramMap.putAll(paramMap);
    }

    public <T extends ChartData> void addChartDataSet(List<T> subDataSet)
    {
        String index = String.valueOf(subsetIndex++);
        paramMap.put(SUB_DATASET + "_" + index, subDataSet);
    }

    public Map<String, Object> getPramMap()
    {
        return this.paramMap;
    }

    public void setDuration(Duration duration)
    {
        paramMap.put("DURATION", duration.toString());
        this.duration = duration;
    }

    public Duration getDuration()
    {
        return duration;
    }

    public String getCopyRight()
    {
        Date today = new Date();
        return String.valueOf(today.getYear()) + " " + INTHINC_NAME;
    }
    
    /**
     * 
     * @param mainDatasetIdField = this is the field from the records in the main dataset to which the value will 
     * match the value from the field defined by chartDataSetIdField in the ChartDataSet redord. 
     * If the categoryDataSetIdField is not set then "seriesID" will be used. This is used so that the chart data matches the main 
     * data set if the records are to be broken up into seperate reports. This will be indicated
     * if the recordsPerReport attribute is set.
     * 
     * example: You may want to following to be used the following comparism. CategorySeriesData.seriesID = MpgEntityPkg.entity.entityName. 
     * In this case, the mainDataSetIdField should be set to "entity.entityNam"e
     * 
     * @param chartDataSetIdField this is the field from the chartDataSet record which will be matched to the field defined by 
     * the mainDatasetIdField in the main data set.
     */
    public void setRecordsPerReportParameters(Integer recordsPerReport,String mainDatasetIdField,String chartDataSetIdField)
    {
        this.recordsPerReport = recordsPerReport;
        this.mainDatasetIdField = mainDatasetIdField;
        this.chartDataSetIdField = chartDataSetIdField;
    }

    public Integer getRecordsPerReport()
    {
        return recordsPerReport;
    }
    
    public String getMainDataSetIdField()
    {
        return mainDatasetIdField;
    }

    public String getChartDataSetIdField()
    {
        return chartDataSetIdField;
    }

}
