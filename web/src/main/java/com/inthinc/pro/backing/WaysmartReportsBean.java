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
public class WaysmartReportsBean extends ReportsBean {

    private static final long serialVersionUID = 224700504785842562L;
    
    private Map<Integer, ReportGroup> reportGroupMap;

    @Override
    public Map<Integer, ReportGroup> getReportGroupMap() {
        return reportGroupMap;
    }

    public void setReportGroupMap(Map<Integer, ReportGroup> reportGroupMap) {
        this.reportGroupMap = reportGroupMap;
    }    
    
    @Override
    public void genReportCriteria()
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
            case TEN_HOUR_DAY_VIOLATIONS:
                reportCriteriaList.add(getReportCriteriaService().getTenHoursDayViolationsCriteria(params.getGroupID(), params.getDateRange().getInterval(),  
                        params.getLocale()));
                break;
            case PAYROLL_SUMMARY:
                reportCriteriaList.add(getReportCriteriaService().getPayrollSummaryReportCriteria(getUser().getGroupID(), params.getGroupIDList(), params.getDateRange().getInterval(),  
                        params.getLocale()));
                break;
            case PAYROLL_DETAIL:
                reportCriteriaList.add(getReportCriteriaService().getPayrollDetailReportCriteria(getUser().getGroupID(), params.getGroupIDList(), params.getDateRange().getInterval(),  
                        params.getLocale()));
                break;
            case PAYROLL_SIGNOFF:
                if (params.getParamType() == ReportParamType.DRIVER )
                    reportCriteriaList.add(getReportCriteriaService().getPayrollSignoffReportCriteria(params.getDriverID(), params.getDateRange().getInterval(),  
                        params.getLocale()));
                else
                    reportCriteriaList.add(getReportCriteriaService().getPayrollSignoffReportCriteria(getUser().getGroupID(), params.getGroupIDList(), params.getDateRange().getInterval(),  
                            params.getLocale()));
                break;
            
            case DRIVER_HOURS:
                reportCriteriaList.add(getReportCriteriaService().getDriverHoursReportCriteria(params.getGroupID(), params.getDateRange().getInterval(),  
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

    public List<SelectItem> getReportGroups() {
        reportGroupMap = new HashMap<Integer, ReportGroup>();
        
        List<SelectItem> reportGroups = new ArrayList<SelectItem>();
        for (ReportGroup rt : EnumSet.allOf(ReportGroup.class)) {
            if (!rt.isPerformance())
                continue;
            reportGroups.add(new SelectItem(rt.getCode(), MessageUtil.getMessageString(rt.toString())));
            reportGroupMap.put(rt.getCode(), rt);
        }
//        sort(reportGroups);
        reportGroups.add(0, new SelectItem(null, ""));
        return reportGroups;
    }

}
