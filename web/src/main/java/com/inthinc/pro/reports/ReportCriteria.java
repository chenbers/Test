package com.inthinc.pro.reports;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.reports.model.ChartData;

public class ReportCriteria
{
    private List mainDataset;
    private Map<String, Object> paramMap = new HashMap<String, Object>();
    private ReportType reportType;
    
    private static final String SUB_DATASET = "SUB_DATASET";
    private int subsetIndex = 1;
    
    public ReportCriteria(ReportType reportType,String entityName,String accountName)
    {
        setReportType(reportType);
        paramMap.put("ENTITY_NAME", entityName);
        paramMap.put("ACCOUNT_NAME", accountName);
    }
    
    public void setMainDataset(List mainDataset){
        this.mainDataset = mainDataset;
    }
    
    public List getMainDataset(){
        return this.mainDataset;
    }

    public void setReportType(ReportType reportType)
    {
        this.reportType = reportType;
    }

    public ReportType getReportType()
    {
        return reportType;
    }
    
    public void addParameter(String name,Object value){
        paramMap.put(name, value);
    }
    
    public <T extends ChartData> void addSubDataSet(List<T> subDataSet){
        paramMap.put(SUB_DATASET + "_" + String.valueOf(subsetIndex++), subDataSet);
    }
    
    public Map<String,Object> getPramMap(){
        return this.paramMap;
    }

}
