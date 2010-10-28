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
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.ReportParamType;
import com.inthinc.pro.reports.ReportCategory;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportGroup;
import com.inthinc.pro.util.MessageUtil;

@KeepAlive
public class WaysmartReportsBean extends ReportsBean {

    private static final long serialVersionUID = 224700504785842562L;

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.backing.ReportsBean#getReportGroupMap()
     */
    @Override
    public Map<Integer, ReportGroup> getReportGroupMap() {
        return reportGroupMap;
    }

    /**
     * The setter for reportGroupMap.
     * @param reportGroupMap
     */
    public void setReportGroupMap(Map<Integer, ReportGroup> reportGroupMap) {
        this.reportGroupMap = reportGroupMap;
    }    
    
    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.backing.ReportsBean#genReportCriteria()
     */
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
                    reportCriteriaList.add(getReportCriteriaService().getMileageByVehicleReportCriteria(getAccountGroupHierarchy(), params.getGroupIDList(), params.getDateRange().getInterval(), 
                            params.getLocale(), getUser().getPerson().getMeasurementType(), true));
                break;

            case STATE_MILEAGE_BY_VEHICLE_ROAD_STATUS:
                reportCriteriaList.add(getReportCriteriaService().getStateMileageByVehicleRoadStatusReportCriteria(getAccountGroupHierarchy(), params.getGroupIDList(), params.getDateRange().getInterval(), 
                        params.getLocale(), getUser().getPerson().getMeasurementType() , params.getIsIfta() ));
                break;

            case STATE_MILEAGE_BY_VEHICLE:
                reportCriteriaList.add(getReportCriteriaService().getStateMileageByVehicleReportCriteria(getAccountGroupHierarchy(), params.getGroupIDList(), params.getDateRange().getInterval(), 
                        params.getLocale(), getUser().getPerson().getMeasurementType(), params.getIsIfta() ));
                break;

            case STATE_MILEAGE_FUEL_BY_VEHICLE:
                reportCriteriaList.add(getReportCriteriaService().getStateMileageFuelByVehicleReportCriteria(getAccountGroupHierarchy(), params.getGroupIDList(), params.getDateRange().getInterval(), 
                        params.getLocale(), getUser().getPerson().getMeasurementType(), params.getIsIfta() ));
                break;                     
            
            case STATE_MILEAGE_COMPARE_BY_GROUP:
                reportCriteriaList.add(getReportCriteriaService().getStateMileageCompareByGroupReportCriteria(getAccountGroupHierarchy(), params.getGroupIDList(), params.getDateRange().getInterval(), 
                        params.getLocale(), getUser().getPerson().getMeasurementType(), params.getIsIfta() ));
                break;  
                
            case STATE_MILEAGE_BY_MONTH:
                reportCriteriaList.add(getReportCriteriaService().getStateMileageByMonthReportCriteria(
                        getAccountGroupHierarchy(), params.getGroupIDList(), params.getDateRange().getInterval(), params.getLocale(), 
                        getUser().getPerson().getMeasurementType(), params.getIsIfta() ));
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
     * @see com.inthinc.pro.backing.ReportsBean#getReportGroups()
     */
    public List<SelectItemGroup> getReportGroups() {

    	// The map between and item and its ID
        reportGroupMap = new HashMap<Integer, ReportGroup>();
        
        // The items categorized in groups to be shown in the UI list
        List<SelectItemGroup> itemGroups = new ArrayList<SelectItemGroup>();

        itemGroups.add(getBlankGroup());
        
        itemGroups.add(new SelectItemGroup(ReportCategory.Performance.getLabel(), 
        		ReportCategory.Performance.getLabel(), false, getItemsByCategory(ReportCategory.Performance)));
        
        itemGroups.add(new SelectItemGroup(ReportCategory.IFTA.getLabel(), 
        		ReportCategory.Performance.getDescription(), false, getItemsByCategory(ReportCategory.IFTA)));

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

	/**
	 * {@inheritDoc}
	 * Filter the Groups for WaySmart device. 
	 * @see com.inthinc.pro.backing.BaseBean#getGroupHierarchy()
	 */
	@Override
    public GroupHierarchy getGroupHierarchy() {
        GroupHierarchy full = super.getGroupHierarchy();
	    List<Group> filtered = checkGroupForWaysmart(full, full.getTopGroup());
        return new GroupHierarchy(filtered);
    }
	
    /*
     * Returns a list of the group and its subgroups if have WaySmart device.
     * @param hierarchy The GroupHierarchy to use to get children
     * @param group The current group to check
     * @return THe list with all the children and the parent having WaySmart device
     */
    private List<Group> checkGroupForWaysmart(GroupHierarchy hierarchy, Group group) {
        List<Group> checkedList = new ArrayList<Group>();
        List<Group> children = hierarchy.getChildren(group);

        if (children == null) { // we are on a leaf
            if (hasWaysmartDevice(group)) {
                checkedList.add(group);
            }
        } else { // we are on a non-leaf node
            for (Group child : children) {
                checkedList.addAll(checkGroupForWaysmart(hierarchy, child));
            }
            if (!checkedList.isEmpty() || (checkedList.isEmpty() && hasWaysmartDevice(group))) {
                checkedList.add(group);
            }
        }
        return checkedList;

    }	
	
	private boolean hasWaysmartDevice(Group group) {
        List<Driver> drivers = getDriverDAO().getDrivers(group.getGroupID());
        for (Driver driver : drivers) {
            Integer accountID = driver.getPerson().getAcctID();
            List<Device> devices = getDeviceDAO().getDevicesByAcctID(accountID);
            for (Device device : devices) {
                if (device.isWaySmart()) {
                    return true;
                }
            }
        }
        return false;
	}

}
