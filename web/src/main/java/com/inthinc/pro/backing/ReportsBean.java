package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.richfaces.model.Ordering;

import com.inthinc.pro.backing.ui.ReportParams;
import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.ReportParamType;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportGroup;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.reports.tabular.ColumnHeader;
import com.inthinc.pro.reports.tabular.Result;
import com.inthinc.pro.reports.tabular.Tabular;

public abstract class ReportsBean extends BaseBean {
    
    private static final long serialVersionUID = 224700504785842562L;
    
    private Integer selected;
    private ReportParams params;
    private ReportParams previousParams;
    
    private String emailAddress;

    private DriverDAO driverDAO;
    private AccountDAO accountDAO;
    private DeviceDAO deviceDAO;
    private ReportCriteriaService reportCriteriaService;
    private ReportRenderer reportRenderer;
    private String html;
    private List<ReportCriteria> reportCriteriaList;

    private String viewType;

    private List<String> columnHeaders;
    private List<List<Result>> records;
    private int recordCount;
    private Map<String, Object> sortOrder;
    private List<ColumnHeader> columnSummaryHeaders;
    
    Map<Integer, ReportGroup> reportGroupMap;
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
            case DRIVING_TIME_VIOLATIONS_SUMMARY_REPORT:
                reportCriteriaList.add(getReportCriteriaService().getDrivingTimeViolationsSummaryReportCriteria(getAccountGroupHierarchy(), params.getGroupIDList(), params.getDateRange().getInterval(), 
                        params.getLocale()));
                break;
            case DRIVING_TIME_VIOLATIONS_DETAIL_REPORT:
                if (params.getParamType() == ReportParamType.DRIVER )
                    reportCriteriaList.add(getReportCriteriaService().getDrivingTimeViolationsDetailReportCriteria(getAccountGroupHierarchy(), 
                       params.getDriverID(), params.getDateRange().getInterval(), 
                        params.getLocale()));
                else
                    reportCriteriaList.add(getReportCriteriaService().getDrivingTimeViolationsDetailReportCriteria(getAccountGroupHierarchy(), 
                            params.getGroupIDList(), params.getDateRange().getInterval(), 
                            params.getLocale()));
                break;

            
            case NON_DOT_VIOLATIONS_SUMMARY_REPORT:
                reportCriteriaList.add(getReportCriteriaService().getNonDOTViolationsSummaryReportCriteria(getAccountGroupHierarchy(), params.getGroupIDList(), params.getDateRange().getInterval(), 
                        params.getLocale()));
                break;
            case NON_DOT_VIOLATIONS_DETAIL_REPORT:
                if (params.getParamType() == ReportParamType.DRIVER )
                    reportCriteriaList.add(getReportCriteriaService().getNonDOTViolationsDetailReportCriteria(getAccountGroupHierarchy(), 
                       params.getDriverID(), params.getDateRange().getInterval(), 
                        params.getLocale()));
                else
                    reportCriteriaList.add(getReportCriteriaService().getNonDOTViolationsDetailReportCriteria(getAccountGroupHierarchy(), 
                            params.getGroupIDList(), params.getDateRange().getInterval(), 
                            params.getLocale()));
                break;
            
            case TEN_HOUR_DAY_VIOLATIONS:
                reportCriteriaList.add(getReportCriteriaService().getTenHoursDayViolationsCriteria(getAccountGroupHierarchy(), params.getGroupID(), params.getDateRange().getInterval(),  
                        params.getLocale()));
                break;
            case PAYROLL_SUMMARY:
                reportCriteriaList.add(getReportCriteriaService().getPayrollSummaryReportCriteria(getAccountGroupHierarchy(), params.getGroupIDList(), params.getDateRange().getInterval(),  
                        params.getLocale()));
                break;
            case PAYROLL_COMPENSATED_HOURS:
                reportCriteriaList.add(getReportCriteriaService().getPayrollCompensatedHoursReportCriteria(getAccountGroupHierarchy(), params.getGroupIDList(), params.getDateRange().getInterval(),  
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
                reportCriteriaList.add(getReportCriteriaService().getDriverHoursReportCriteria(getAccountGroupHierarchy(), params.getGroupID(), params.getDateRange().getInterval(),  
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
                            params.getLocale(), getUser().getPerson().getMeasurementType(), false));
                break;

            case STATE_MILEAGE_BY_VEHICLE_ROAD_STATUS:
                reportCriteriaList.add(getReportCriteriaService().getStateMileageByVehicleRoadStatusReportCriteria(getAccountGroupHierarchy(), params.getGroupIDList(), params.getDateRange().getInterval(), 
                        params.getLocale(), getUser().getPerson().getMeasurementType() , params.getIsIfta() ));
                break;

            case STATE_MILEAGE_BY_VEHICLE:
                reportCriteriaList.add(getReportCriteriaService().getStateMileageByVehicleReportCriteria(getAccountGroupHierarchy(), params.getGroupIDList(), params.getDateRange().getInterval(), 
                        params.getLocale(), getUser().getPerson().getMeasurementType(), params.getIsIfta()));
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
                        getAccountGroupHierarchy(), params.getGroupIDList(), 
                        params.getDateRange().getInterval(),
                        params.getLocale(), 
                        getUser().getPerson().getMeasurementType(), params.getIsIfta() ));
                break;
            case STATE_MILEAGE_BY_VEHICLE_TIWI:
                reportCriteriaList.add(getReportCriteriaService().getStateMileageByVehicleReportCriteria(getAccountGroupHierarchy(), params.getGroupID(), 
                        params.getDateRange().getInterval(), params.getLocale(), getUser().getPerson().getMeasurementType()));
                break;
            case WARRANTY_LIST:
                reportCriteriaList.add(getReportCriteriaService().getWarrantyListReportCriteria(
                        getAccountGroupHierarchy(), params.getGroupID(), getAccountID(),
                        getAccountName(), params.getLocale(), params.getIsExpired()));
                break;
            case DRIVER_PERFORMANCE_TEAM:
                reportCriteriaList.add(getReportCriteriaService().getDriverPerformanceReportCriteria(getAccountGroupHierarchy(), params.getGroupID(), params.getDateRange().getInterval(),  
                        params.getLocale(), false));
                break;
            case DRIVER_PERFORMANCE_RYG_TEAM:
                reportCriteriaList.add(getReportCriteriaService().getDriverPerformanceReportCriteria(getAccountGroupHierarchy(), params.getGroupID(), params.getDateRange().getInterval(),  
                        params.getLocale(), true));
                break;
            case DRIVER_PERFORMANCE_KEY_METRICS:
                reportCriteriaList.add(getReportCriteriaService().getDriverPerformanceKeyMetricsReportCriteria(getAccountGroupHierarchy(), 
                        params.getGroupIDList(), params.getTimeFrameSelect().getTimeFrame(),  
                        params.getLocale(), getUser().getPerson().getMeasurementType()));
                break;
            case TEAM_STOPS_REPORT:
                reportCriteriaList.add(getReportCriteriaService().getTeamStopsReportCriteriaByGroup(getAccountGroupHierarchy(),params.getGroupIDList(), params.getTimeFrameSelect().getTimeFrame(), 
                        getDateTimeZone(), getLocale()));
                break;    
            case DRIVER_COACHING:
                if (params.getParamType().equals(ReportParamType.DRIVER)){
                    reportCriteriaList.add(getReportCriteriaService().getDriverCoachingReportCriteriaByDriver(getAccountGroupHierarchy(),params.getDriverID(),params.getDateRange().getInterval(), getLocale(),getDateTimeZone()));
                   
                }
                if (params.getParamType().equals(ReportParamType.GROUPS)){
                    reportCriteriaList.addAll(getReportCriteriaService().getDriverCoachingReportCriteriaByGroup(getAccountGroupHierarchy(),params.getGroupID(),  params.getDateRange().getInterval(), getLocale(),getDateTimeZone()));
                }
                break;
            case DRIVER_EXCLUDED_VIOLATIONS:
                reportCriteriaList.add(getReportCriteriaService().getDriverExcludedViolationCriteria(getAccountGroupHierarchy(),params.getGroupID(),params.getDateRange().getInterval(), getLocale(),getDateTimeZone()));
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
            reportCriteria.setTimeZone(getUser().getPerson().getTimeZone());

        }
        
        setReportCriteriaList(reportCriteriaList);
    }
    
    
    /**
     * Produces an group with a single item, which is a blank item.
     * In the UI it shows as one blank line.
     * 
     * @return A group with one blank item.
     */
    protected SelectItemGroup getBlankGroup(){
        SelectItem[] items = new SelectItem[1]; 
        items[0] = new SelectItem(null, "");
        return new SelectItemGroup("","",false,items);      
    }

    /**
     * Returns the Items to be shown in the Reports Item List.
     * These items can be divided in groups by using SelectItemGroup.
     *  
     * @return A list of items or groups of items to be shown.
     */
    public abstract List<? extends SelectItem> getReportGroups();
    protected abstract Map<Integer, ReportGroup> getReportGroupMap();
    protected abstract void setReportGroupMap(Map<Integer, ReportGroup> reportGroupMap);

    
    public void init()
    {
        params = new ReportParams(getUser().getPerson().getLocale());
        params.setGroupHierarchy(getGroupHierarchy());
        params.setDriverList(driverDAO.getAllDrivers(getUser().getGroupID()));
        params.setIsIfta(false);
        previousParams = params.clone();
        viewType = "";
    }
    

    public ReportParams getPreviousParams() {
        return previousParams;
    }

    public void setPreviousParams(ReportParams previousParams) {
        this.previousParams = previousParams;
    }

    public List<ReportCriteria> getReportCriteriaList() {
        return reportCriteriaList;
    }

    public void setReportCriteriaList(List<ReportCriteria> reportCriteriaList) {
        this.reportCriteriaList = reportCriteriaList;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public ReportRenderer getReportRenderer() {
        return reportRenderer;
    }

    public void setReportRenderer(ReportRenderer reportRenderer) {
        this.reportRenderer = reportRenderer;
    }

    public ReportCriteriaService getReportCriteriaService() {
        return reportCriteriaService;
    }

    public void setReportCriteriaService(ReportCriteriaService reportCriteriaService) {
        this.reportCriteriaService = reportCriteriaService;
    }


    public void htmlReport()
    {
        genReportCriteria(); 
        setHtml("");
        if (reportCriteriaList == null)
            return;
        
        viewType = FormatType.HTML.name();
        
        String output = reportRenderer.exportReportToString(reportCriteriaList, FormatType.HTML, getFacesContext());
        if (output != null) {
            setHtml(output);
        }
      
    }

    public void tabular()
    {
        genReportCriteria(); 
        if (reportCriteriaList == null || params.getReportGroup() == null || !params.getReportGroup().isTabularSupport())
            return;
        
        ReportCriteria criteria = reportCriteriaList.get(0);
        if (!(criteria instanceof Tabular))
            return;
        
        Tabular tabular = (Tabular)criteria;
        columnHeaders = tabular.getColumnHeaders();
        columnSummaryHeaders = tabular.getColumnSummaryHeaders();
        records = tabular.getTableRows();
        recordCount = (records == null) ? 0 : records.size();
        
        
        sortOrder = new HashMap<String, Object>();
        if (columnHeaders != null) {
            for (String col : columnHeaders)
            sortOrder.put(col, Ordering.UNSORTED);
        }
        viewType = FormatType.CSV.name();
    }

    
    public void pdf()
    {
        genReportCriteria();
        if (reportCriteriaList == null)
            return;
        
        reportRenderer.exportReportToPDF(reportCriteriaList, getFacesContext());
        
    }
    public void excel()
    {
        genReportCriteria();
        if (reportCriteriaList == null)
            return;
        
        reportRenderer.exportReportToExcel(reportCriteriaList, getFacesContext());
    }
    
    public void emailReport()
    {
        genReportCriteria();
        if (reportCriteriaList == null)
            return;
        
        reportRenderer.exportReportToEmail(reportCriteriaList, getEmailAddress(), getNoReplyEmailAddress());
        
        
    }
    public String getEmailAddress() {
        if (emailAddress == null) {
            emailAddress = getProUser().getUser().getPerson().getPriEmail();
        }
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getNoReplyEmailAddress() {

        Account acct = accountDAO.findByID(getProUser().getUser().getPerson().getAcctID());
        String localAddr = acct.getProps().getNoReplyEmail();
            
        if ( localAddr != null && localAddr.trim().length() != 0 ) {
            localAddr = localAddr.trim();
        }       
        
        return localAddr;
    }
    
    /**
     * Returns report description based on the appropriate resource bundle (the report is selected by the user) 
     * The key in the resource bundle is build from the ReportGroup enum (converted to String). 
     * From this string, we change all "_" to "." character.
     * If the key is not found in the rb, then null is returned.
     *  
     * @return The report description or null if report description key not found.
     */

    public String getReportDescription(){
        String res = null;
        if(reportGroupMap != null){
            ReportGroup reportGroup = reportGroupMap.get(getSelected());
            if(reportGroup != null){
                ResourceBundle rb = reportGroup.getReports()[0].getResourceBundle(getLocale());
                try {
                    res = rb.getString("description." + reportGroup.getReports()[0].toString().toLowerCase().replaceAll("_","."));
                }
                catch (MissingResourceException e){
                    res = null;
                }
                catch (Exception e) {
                    res = null;
                }
            }
        }
        return res;
    }
 
    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
        this.viewType = "";
        reportCriteriaList = null;
        
        params.setReportGroup(getReportGroupMap().get(selected));
    }
    public ReportParams getParams() {
        return params;
    }

    public void setParams(ReportParams params) {
        this.params = params;
    }
    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /**
     * The deviceDAO setter.
     * @param deviceDAO the deviceDAO to set
     */
    public void setDeviceDAO(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

    /**
     * The deviceDAO getter.
     * @return the deviceDAO
     */
    public DeviceDAO getDeviceDAO() {
        return this.deviceDAO;
    }

    @SuppressWarnings("unchecked")
    public List<String> getColumnHeaders() {
        return (List<String>) (columnHeaders == null ? (Collections.emptyList()) : columnHeaders);

    }
    @SuppressWarnings("unchecked")
    public List<ColumnHeader> getColumnSummaryHeaders() {
        return (List<ColumnHeader>) (columnSummaryHeaders == null ? (Collections.emptyList()) : columnSummaryHeaders);
    }

    public void setColumnSummaryHeaders(List<ColumnHeader> columnSummaryHeaders) {
        this.columnSummaryHeaders = columnSummaryHeaders;
    }

    public void setColumnHeaders(List<String> columnHeaders) {
        this.columnHeaders = columnHeaders;
    }

    public List<List<Result>> getRecords() {
        return records;
    }

    public void setRecords(List<List<Result>> records) {
        this.records = records;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public Map<String, Object> getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Map<String, Object> sortOrder) {
        this.sortOrder = sortOrder;
    }
}
