package com.inthinc.pro.backing;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
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

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.backing.model.ReportParams;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.reports.CriteriaType;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.Report;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportGroup;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.jasper.JasperReport;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.util.MessageUtil;

@KeepAlive
public class HosReportsBean extends BaseBean {
    
    private List<SelectItem> reportGroups;
    private Map<Integer, ReportGroup> reportGroupMap;
    private Integer selected;
    private ReportParams params;
    private static final TimeZone timeZone = TimeZone.getTimeZone("GMT");
    private static final DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(timeZone);

    DriverDAO driverDAO;
    ReportCriteriaService reportCriteriaService;
    ReportRenderer reportRenderer;
    String html;
    List<ReportCriteria> reportCriteriaList;
    
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
        params.setLocale(getUser().getPerson().getLocale());
        params.setGroupID(getUser().getGroupID());
    }

    public void html()
    {
        genReportCriteria(); 
        setHtml("");
        if (reportCriteriaList == null)
            return;
        
        String output = reportRenderer.exportReportToString(reportCriteriaList, FormatType.HTML);
        if (output != null)
            setHtml(output);
      
    }
    public void pdf()
    {
        genReportCriteria();
        if (reportCriteriaList == null)
            return;
        
        reportRenderer.exportReportToPDF(reportCriteriaList, getFacesContext());
        
    }
    
    private void genReportCriteria()
    {
        if (selected == null) {
            reportCriteriaList = null;
            return;
        }
        if (reportCriteriaList != null)
            return;
        
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
        

        // TODO: MOVE THIS to renderer class
//        setReport(JasperReport.getInstance(reportCriteriaList));

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

    
}
