package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.ajax4jsf.model.KeepAlive;

import com.inthinc.pro.backing.ui.ReportParams;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.ReportParamType;
import com.inthinc.pro.reports.ReportCategory;
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
                reportCriteriaList.add(getReportCriteriaService().getPayrollSummaryReportCriteria(getAccountGroupHierarchy(), params.getGroupIDList(), params.getDateRange().getInterval(),  
                        params.getLocale()));
                break;
            case PAYROLL_DETAIL:
                reportCriteriaList.add(getReportCriteriaService().getPayrollDetailReportCriteria(getAccountGroupHierarchy(), params.getGroupIDList(), params.getDateRange().getInterval(),  
                        params.getLocale()));
                break;
            case PAYROLL_SIGNOFF:
                if (params.getParamType() == ReportParamType.DRIVER )
                    reportCriteriaList.add(getReportCriteriaService().getPayrollSignoffReportCriteria(getAccountGroupHierarchy(), params.getDriverID(), params.getDateRange().getInterval(),  
                        params.getLocale()));
                else
                    reportCriteriaList.add(getReportCriteriaService().getPayrollSignoffReportCriteria(getAccountGroupHierarchy(), params.getGroupIDList(), params.getDateRange().getInterval(),  
                            params.getLocale()));
                break;
            
            case DRIVER_HOURS:
                reportCriteriaList.add(getReportCriteriaService().getDriverHoursReportCriteria(params.getGroupID(), params.getDateRange().getInterval(),  
                        params.getLocale()));
                break;
                
            case VEHICLE_USAGE:
                if (params.getParamType() == ReportParamType.DRIVER )
                    reportCriteriaList.add(getReportCriteriaService().getVehicleUsageReportCriteria(params.getDriverID(), params.getDateRange().getInterval(),  
                        params.getLocale(), false ));
                else
                    reportCriteriaList.add(getReportCriteriaService().getVehicleUsageReportCriteria(params.getGroupID(), params.getDateRange().getInterval(),  
                            params.getLocale(), true ));
                break;
                
            case MILEAGE_BY_VEHICLE:
                    reportCriteriaList.add(getReportCriteriaService().getMileageByVehicleReportCriteria(params.getGroupIDList(), params.getDateRange().getInterval(), 
                            params.getLocale(), true ));
                break;

            case STATE_MILEAGE_BY_VEHICLE_ROAD_STATUS:
                reportCriteriaList.add(getReportCriteriaService().getStateMileageByVehicleRoadStatusReportCriteria(params.getGroupIDList(), params.getDateRange().getInterval(), 
                        params.getLocale(), params.getIsIfta() ));
                break;
                
            case STATE_MILEAGE_BY_VEHICLE:
                reportCriteriaList.add(getReportCriteriaService().getStateMileageByVehicleReportCriteria(params.getGroupIDList(), params.getDateRange().getInterval(), 
                        params.getLocale(), getUser().getPerson().getMeasurementType(), params.getIsIfta() ));
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

    /**
     * {@inheritDoc}
     */
    public List<SelectItemGroup> getReportGroups() {

    	// The map between and item and its ID
        reportGroupMap = new HashMap<Integer, ReportGroup>();
        
        // The items categorized in groups to be shown in the UI list
        List<SelectItemGroup> itemGroups = new ArrayList<SelectItemGroup>();

        itemGroups.add(getBlankGroup());
        
        itemGroups.add(new SelectItemGroup(ReportCategory.Performance.getLabel(), 
        		ReportCategory.Performance.getLabel(), false, getItemsByCategory(ReportCategory.Performance)));
        
        itemGroups.add(new SelectItemGroup(ReportCategory.DOT_IFTA.getLabel(), 
        		ReportCategory.Performance.getDescription(), false, getItemsByCategory(ReportCategory.DOT_IFTA)));

        return itemGroups;
    }

    /**
     * Returns all the report types pertaining to a given Report Category. 
     * @param category Category of reports
     * @return Array of report types as Faces SelectItems
     */
	private SelectItem[] getItemsByCategory(ReportCategory category) {
        List<SelectItem> items = new ArrayList<SelectItem>();
        for (ReportGroup rt : EnumSet.allOf(ReportGroup.class)) {
            if (!rt.isCategory(category)) continue;
            items.add(new SelectItem(rt.getCode(), MessageUtil.getMessageString(rt.toString())));
            reportGroupMap.put(rt.getCode(), rt);
        }
		return items.toArray(new SelectItem[0]);
	}
	
	/**
	 * Produces an group with a single item, which is a blank item.
	 * In the UI it shows as one blank line.
	 * 
	 * @return A group with one blank item.
	 */
	private SelectItemGroup getBlankGroup(){
        SelectItem[] items = new SelectItem[1]; 
        items[0] = new SelectItem(null, "");
        return new SelectItemGroup("","",false,items);		
	}

}
