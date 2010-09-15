package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.model.SelectItem;

import org.ajax4jsf.model.KeepAlive;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.richfaces.model.Ordering;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.backing.model.ReportParams;
import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.reports.CriteriaType;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportGroup;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.reports.tabular.ColumnHeader;
import com.inthinc.pro.reports.tabular.Result;
import com.inthinc.pro.reports.tabular.Tabular;
import com.inthinc.pro.util.MessageUtil;

@KeepAlive
public class HosReportsBean extends BaseBean {
    
    private List<SelectItem> reportGroups;
    private Map<Integer, ReportGroup> reportGroupMap;
    private Integer selected;
    private ReportParams params;
    private ReportParams previousParams;
    
    private String emailAddress;
    private static final TimeZone timeZone = TimeZone.getTimeZone("GMT");
    private static final DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(timeZone);

    private DriverDAO driverDAO;
    private AccountDAO accountDAO;
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

    public void init()
    {
        params = new ReportParams();
        params.setStartDate(new DateMidnight(new DateTime().minusWeeks(1), dateTimeZone).toDate());
        params.setEndDate(new DateMidnight(new DateTime(), dateTimeZone).toDateTime().plusDays(1).minus(1).toDate());
        params.updateInterval();
        params.setLocale(getUser().getPerson().getLocale());
        params.setGroupID(getUser().getGroupID());
        
        previousParams = params.clone();
        
        viewType = "";
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
        if (reportCriteriaList == null || !getTabularSupport())
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

    
    private void genReportCriteria()
    {
        if (selected == null || !params.getValid()) {
            reportCriteriaList = null;
            viewType = "";
            return;
        }
        if (reportCriteriaList != null && params.equals(previousParams))
            return;
        
        previousParams = params.clone();
        
        ReportGroup reportGroup = reportGroupMap.get(selected);
        reportCriteriaList = new ArrayList<ReportCriteria>();
        
        // todo: make calls to reportCriteria service using the params class

        switch (reportGroup.getReports()[0]) {
            case HOS_DAILY_DRIVER_LOG_REPORT:
                reportCriteriaList.addAll(reportCriteriaService.getHosDailyDriverLogReportCriteria(params.getDriverID(), 
                        params.getInterval(), params.getLocale(), getUser().getPerson().getMeasurementType() == MeasurementType.METRIC));
                break;
                
            case HOS_VIOLATIONS_SUMMARY_REPORT:
                reportCriteriaList.add(reportCriteriaService.getHosViolationsSummaryReportCriteria(params.getGroupID(), params.getInterval(), 
                        params.getLocale()));
                break;
            case HOS_VIOLATIONS_DETAIL_REPORT:
                reportCriteriaList.add(reportCriteriaService.getHosViolationsDetailReportCriteria(params.getGroupID(), params.getInterval(), 
                        params.getLocale()));
                break;
            case HOS_DRIVER_DOT_LOG_REPORT:
                reportCriteriaList.add(reportCriteriaService.getHosDriverDOTLogReportCriteria(params.getGroupID(), params.getInterval(), 
                        params.getLocale()));
                break;
            case DOT_HOURS_REMAINING:
                reportCriteriaList.add(reportCriteriaService.getDotHoursRemainingReportCriteria(params.getGroupID(),  
                        params.getLocale()));
                break;
            case HOS_ZERO_MILES:
                reportCriteriaList.add(reportCriteriaService.getHosZeroMilesReportCriteria(params.getGroupID(), params.getInterval(),  
                        params.getLocale()));
                break;
            case PAYROLL_DETAIL:
                reportCriteriaList.add(reportCriteriaService.getPayrollDetailReportCriteria(params.getGroupID(), params.getInterval(),  
                        params.getLocale()));
                break;
            case PAYROLL_SIGNOFF:
                reportCriteriaList.add(reportCriteriaService.getPayrollSignoffReportCriteria(params.getDriverID(), params.getInterval(),  
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
        

    }
    
    public TimeZone getTimeZone() {
        return timeZone;
    }

    public List<SelectItem> getReportGroups() {
        reportGroupMap = new HashMap<Integer, ReportGroup>();
        
        reportGroups = new ArrayList<SelectItem>();
        for (ReportGroup rt : EnumSet.allOf(ReportGroup.class)) {
            if (!rt.isHos())
                continue;
            reportGroups.add(new SelectItem(rt.getCode(), MessageUtil.getMessageString(rt.toString())));
            reportGroupMap.put(rt.getCode(), rt);
        }
//        sort(reportGroups);
        reportGroups.add(0, new SelectItem(null, ""));
        return reportGroups;
    }

    
    public void exportExcel()
    {
        System.out.println("export excel");
    }


    public void exportPDF()
    {
        System.out.println("export pdf");
    }
    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }
    
    public List<CriteriaType> getSelectedCriteria() {
        if (selected == null)
            return new ArrayList<CriteriaType>();
        
        return Arrays.asList(reportGroupMap.get(selected).getCriterias());
    }
    
    public EntityType getSelectedEntityType() {
        if (selected == null)
            return null;
        return reportGroupMap.get(selected).getEntityType();
        
    }
    public boolean getTabularSupport() {
        if (selected == null)
            return false;
        
        return reportGroupMap.get(selected).getReports()[0].isTabularSupport();
        
        
    }
    public ReportParams getParams() {
        return params;
    }

    public void setParams(ReportParams params) {
        this.params = params;
    }

    protected final static String BLANK_SELECTION = "&#160;";

    protected static void sort(List<SelectItem> selectItemList) {
        Collections.sort(selectItemList, new Comparator<SelectItem>() {
            @Override
            public int compare(SelectItem o1, SelectItem o2) {
                return o1.getLabel().toLowerCase().compareTo(
                        o2.getLabel().toLowerCase());
            }
        });
    }

    public List<SelectItem> getGroups() {
        List<SelectItem> groups = new ArrayList<SelectItem>();
        for (final Group group : getGroupHierarchy().getGroupList()) {
            String fullName = getGroupHierarchy().getFullGroupName(
                    group.getGroupID());
            if (fullName.endsWith(GroupHierarchy.GROUP_SEPERATOR)) {
                fullName = fullName.substring(0, fullName.length()
                        - GroupHierarchy.GROUP_SEPERATOR.length());
            }

            groups.add(new SelectItem(group.getGroupID(), fullName));

        }
        sort(groups);

        return groups;
    }
    public List<SelectItem> getDrivers() {
        List<SelectItem> drivers = new ArrayList<SelectItem>();
        List<Driver> driverList = driverDAO.getAllDrivers(getUser().getGroupID());
        for (Driver driver : driverList) {
            drivers.add(new SelectItem(driver.getDriverID(), driver.getPerson().getFullName()));
        }
        return drivers;
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

    public List<String> getColumnHeaders() {
        return (List<String>) (columnHeaders == null ? (Collections.emptyList()) : columnHeaders);

    }
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
