package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.ajax4jsf.model.KeepAlive;

import com.inthinc.pro.backing.ui.ReportParams;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.ReportParamType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportGroup;
import com.inthinc.pro.util.MessageUtil;

@KeepAlive
public class HosReportsBean extends ReportsBean {
    Map<Integer, ReportGroup> reportGroupMap;
    
    @Override
    protected void genReportCriteria()
    {
        ReportParams params = getParams();
        if (getSelected() == null || !params.getValid()) {
            setReportCriteriaList(null);
            setViewType("");
            return;
        }
        if (getReportCriteriaList() != null && params.equals(getPreviousParams()))
            return;
        
        setPreviousParams(params.clone());
        
        ReportGroup reportGroup = reportGroupMap.get(getSelected());
        List<ReportCriteria> reportCriteriaList = new ArrayList<ReportCriteria>();
        
        switch (reportGroup.getReports()[0]) {
            case HOS_DAILY_DRIVER_LOG_REPORT:
                if (params.getParamType() == ReportParamType.DRIVER )
                    reportCriteriaList.addAll(getReportCriteriaService().getHosDailyDriverLogReportCriteria(getAccountGroupHierarchy(), params.getDriverID(), 
                        params.getDateRange().getInterval(), params.getLocale(), getUser().getPerson().getMeasurementType() == MeasurementType.METRIC));
                else
                    reportCriteriaList.addAll(getReportCriteriaService().getHosDailyDriverLogReportCriteria(getAccountGroupHierarchy(),  
                            params.getGroupIDList(), 
                            params.getDateRange().getInterval(), params.getLocale(), getUser().getPerson().getMeasurementType() == MeasurementType.METRIC));
                break;
                
            case HOS_VIOLATIONS_SUMMARY_REPORT:
                reportCriteriaList.add(getReportCriteriaService().getHosViolationsSummaryReportCriteria(getAccountGroupHierarchy(), params.getGroupIDList(), params.getDateRange().getInterval(), 
                        params.getLocale()));
                break;
            case HOS_VIOLATIONS_DETAIL_REPORT:
                if (params.getParamType() == ReportParamType.DRIVER )
                    reportCriteriaList.add(getReportCriteriaService().getHosViolationsDetailReportCriteria(getAccountGroupHierarchy(), 
                       params.getDriverID(), params.getDateRange().getInterval(), 
                        params.getLocale()));
                else
                    reportCriteriaList.add(getReportCriteriaService().getHosViolationsDetailReportCriteria(getAccountGroupHierarchy(), 
                            params.getGroupIDList(), params.getDateRange().getInterval(), 
                            params.getLocale()));
                break;
            case HOS_DRIVER_DOT_LOG_REPORT:
                reportCriteriaList.add(getReportCriteriaService().getHosDriverDOTLogReportCriteria(params.getDriverID(), params.getDateRange().getInterval(), 
                        params.getLocale()));
                break;
            case DOT_HOURS_REMAINING:
                reportCriteriaList.add(getReportCriteriaService().getDotHoursRemainingReportCriteria(getAccountGroupHierarchy(), params.getGroupIDList(),  
                        params.getLocale()));
                break;
            case HOS_ZERO_MILES:
                reportCriteriaList.add(getReportCriteriaService().getHosZeroMilesReportCriteria(getAccountGroupHierarchy(), params.getGroupIDList(), params.getDateRange().getInterval(),  
                        params.getLocale()));
                break;
            case HOS_EDITS:
                reportCriteriaList.add(getReportCriteriaService().getHosEditsReportCriteria(getAccountGroupHierarchy(), params.getGroupIDList(), params.getDateRange().getInterval(),  
                        params.getLocale()));
                break;
                
            default:
                break;

        }
        for (ReportCriteria reportCriteria : reportCriteriaList) {
            reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
            reportCriteria.setLocale(getUser().getPerson().getLocale());
            reportCriteria.setUseMetric((getUser().getPerson().getMeasurementType() != null && getUser().getPerson().getMeasurementType().equals(MeasurementType.METRIC)));
            reportCriteria.setMeasurementType(getUser().getPerson().getMeasurementType());
            reportCriteria.setFuelEfficiencyType(getUser().getPerson().getFuelEfficiencyType());

        }
        
        setReportCriteriaList(reportCriteriaList);
    }

    @Override
    public List<SelectItem> getReportGroups() {
        reportGroupMap = new HashMap<Integer, ReportGroup>();
        
        List<SelectItem> reportGroups = new ArrayList<SelectItem>();
        for (ReportGroup rt : EnumSet.allOf(ReportGroup.class)) {
            if (!rt.isDotIfta())
                continue;
            reportGroups.add(new SelectItem(rt.getCode(), MessageUtil.getMessageString(rt.toString())));
            reportGroupMap.put(rt.getCode(), rt);
        }
//        sort(reportGroups);
        reportGroups.add(0, new SelectItem(null, ""));
        return reportGroups;
    }

    
    public Map<Integer, ReportGroup> getReportGroupMap() {
        return reportGroupMap;
    }

    public void setReportGroupMap(Map<Integer, ReportGroup> reportGroupMap) {
        this.reportGroupMap = reportGroupMap;
    }

    
}
