package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.ajax4jsf.model.KeepAlive;
import org.richfaces.json.JSONArray;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverStopReport;
import com.inthinc.pro.model.DriverStops;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.util.MessageUtil;
/**
 * Modified for #DE9193,Stops report were always in Mountain Time Zone in QA or UTC in production,
 * regardless of user's time zone setting.Added parameter USER_TIME_ZONE as user's time zone in the
 * report criteria parameter map.
 *
 */
@KeepAlive
public class TeamStopsBean extends BaseBean {
    
    private static final EnumSet<TimeFrame> validTimeFrames = 
        EnumSet.range(TimeFrame.TODAY,TimeFrame.SIX_DAYS_AGO);

    private static final long serialVersionUID = 1L;
    
    private DriverDAO driverDAO;
    
    private TeamCommonBean teamCommonBean;
        
    private List<String> colors;
    private List<String> textColors;
    private List<String> labels;
    
    private List<Driver> drivers;
    private DriverStopReport driverStopReport;

    private Integer selectedDriverID;
    private String errorMessage;
        
    private ReportRenderer reportRenderer;
    private ReportCriteriaService reportCriteriaService;
    
    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public TeamCommonBean getTeamCommonBean() {
        return teamCommonBean;
    }

    public void setTeamCommonBean(TeamCommonBean teamCommonBean) {
        this.teamCommonBean = teamCommonBean;
    }
    
    public List<Driver> getDrivers() {
        if ( drivers == null || drivers.size() == 0 ) {
            initDrivers();
        }
        return drivers;
    }  
    
   
    public Integer getSelectedDriverID() {
        return selectedDriverID;
    }

    public void setSelectedDriverID(Integer selectedDriverID) {
        this.selectedDriverID = selectedDriverID;
    }
    
    public List<String> getColors() {
        return colors;
    }
    
    public void setColors(List<String> colors) {
        this.colors = colors;
    }
    
    public JSONArray getColorsJSON(){
        return new JSONArray(colors);
    }
    
    public List<String> getTextColors() {
        return textColors;
    }
    
    public void setTextColors(List<String> textColors) {
        this.textColors = textColors;
    }
    
    public JSONArray getTextColorsJSON(){
        return new JSONArray(textColors);
    }
    
    public List<String> getLabels() {
        return labels;
    }
    
    public void setLabels(List<String> labels) {
        this.labels = labels;
    }    

    public String getErrorMessage() {
        if ( !isValidTimeFrame() ) {
            addInfoMessage(MessageUtil.getMessageString("team_timeframe_msg", getLocale()));
        } 
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isValidTimeFrame(){
        return validTimeFrames.contains(teamCommonBean.getTimeFrame());
    }

    public void init(){
        
        // Labels to the right of the driver name
        labels = new ArrayList<String>(Arrays.asList( Pattern.compile("\",\"|\"").split(
                MessageUtil.getMessageString("teamLabels"))));
        labels.remove(0);
    }
    
    private void initDrivers(){
        drivers = new ArrayList<Driver>();
        drivers = driverDAO.getDrivers(teamCommonBean.getGroupID());
        Collections.sort(drivers);
    }

    private void initDriverStopReport() {
        if (driverStopReport != null) {
            if (driverStopReport.getDriverID().equals(selectedDriverID) && driverStopReport.getTimeFrame() == teamCommonBean.getTimeFrame()) {
                return;
            }
        }
       
        String selectedDriverName = getSelectedDriverName();
        List<DriverStops> driverStops = driverDAO.getStops(selectedDriverID, selectedDriverName, teamCommonBean.getTimeFrame().getInterval(getDateTimeZone()));
        driverStopReport = new DriverStopReport(teamCommonBean.getGroup().getName(), selectedDriverID, selectedDriverName, teamCommonBean.getTimeFrame(), driverStops);
        
        fillInDriverStopAddressesAndZones(driverStopReport);
    }
    private void fillInDriverStopAddressesAndZones(DriverStopReport driverStopReport) {
        AddressLookup addressLookup = this.getAddressLookup();
        List<Zone> zones = getProUser().getZones();
        if (addressLookup != null) {
            for (DriverStops driverStop : driverStopReport.getDriverStops()) {
                   if (driverStop.getLat() != null && driverStop.getLng() != null) {
                        LatLng latLng = new LatLng(driverStop.getLat(), driverStop.getLng());
                        driverStop.setAddress(addressLookup.getAddressOrZoneOrLatLng(latLng, zones));
                        driverStop.setZoneName(findZoneName(zones, latLng));
                    }
            }
        }
    }    
    private String findZoneName(List<Zone> zoneList,LatLng latLng) {
        for ( Zone z: zoneList ) {
            if (z.containsLatLng(latLng) ) {
                return z.getName();
            }
        }
        return null;
    }

    private String getSelectedDriverName() {
        for (Driver driver : getDrivers()) {
            if (driver.getDriverID().equals(selectedDriverID))
                return driver.getPerson().getFullName();
        }
        return "";
    }

    public TimeZone getTimeZone() {
        if (getPerson() != null && getPerson().getTimeZone() != null) {
            return getPerson().getTimeZone();
        }
        return TimeZone.getTimeZone("GMT");
    }

    public void exportReportToPdf()
    {
        getReportRenderer().exportSingleReportToPDF(buildReportCriteria(), getFacesContext());
    }

    public void emailReport()
    {
        getReportRenderer().exportReportToEmail(buildReportCriteria(), getEmailAddress(), getNoReplyEmailAddress());
    }

    public void exportReportToExcel()
    {
        getReportRenderer().exportReportToExcel(buildReportCriteria(), getFacesContext());
    }

    protected ReportCriteria buildReportCriteria()
    {
        if (selectedDriverID == null)
            return null;
        
        ReportCriteria reportCriteria = getReportCriteriaService().getTeamStopsReportCriteria(teamCommonBean.getTimeFrame().getInterval(getDateTimeZone()),
                getAccountGroupHierarchy(), selectedDriverID, teamCommonBean.getTimeFrame(),
                getDateTimeZone(), getLocale(), driverStopReport);
        
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.addParameter(ReportCriteria.USER_TIME_ZONE, getUser().getPerson().getTimeZone());
        return reportCriteria;

    }
    public DriverStopReport getDriverStopReport() {
        if ( selectedDriverID != null ) {
                initDriverStopReport();
        } 
        return driverStopReport;
    }

    public void setDriverStopReport(DriverStopReport driverStopReport) {
        this.driverStopReport = driverStopReport;
    }
    public void setReportRenderer(ReportRenderer reportRenderer)
    {
        this.reportRenderer = reportRenderer;
    }

    public ReportRenderer getReportRenderer()
    {
        return reportRenderer;
    }

    public void setReportCriteriaService(ReportCriteriaService reportCriteriaService)
    {
        this.reportCriteriaService = reportCriteriaService;
    }

    public ReportCriteriaService getReportCriteriaService()
    {
        return reportCriteriaService;
    }

}
