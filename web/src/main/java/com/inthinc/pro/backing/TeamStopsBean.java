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
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverStops;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.util.MessageUtil;

import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

@KeepAlive
public class TeamStopsBean extends BaseBean {
    
    private static final EnumSet<TimeFrame> validTimeFrames = 
        EnumSet.range(TimeFrame.TODAY,TimeFrame.WEEK);    

    /**
     * Backing bean for the TeamStops tab of the new team page
     */
    private static final long serialVersionUID = 1L;
    
    private int stopsPerPage = 25;
    
    private DriverDAO driverDAO;
    
    private TeamCommonBean teamCommonBean;
        
    private List<String> colors;
    private List<String> textColors;
    private List<String> labels;    
    
    private List<Driver> drivers;
    private List<DriverStops> driverStops;
    private List<DriverStops> driverStopsSummary;    
    private Integer selectedDriverID;  
    private TimeZone timeZone;
        
    private ReportRenderer reportRenderer;
    private ReportCriteriaService reportCriteriaService;    

    public int getStopsPerPage() {
        return stopsPerPage;
    }

    public void setStopsPerPage(int stopsPerPage) {
        this.stopsPerPage = stopsPerPage;
    }

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
    
    public List<DriverStops> getDriverStops() { 
        if ( selectedDriverID != null ) {
            initDriverStops();
        }
        
        return driverStops;
    }
    
    public List<DriverStops> getDriverStopsSummary() {
        if ( selectedDriverID != null ) {
            initDriverStopsSummary();
        }
        
        return driverStopsSummary;
    }
    
    public Integer getSelectedDriverID() {
        return selectedDriverID;
    }

    public void setSelectedDriverID(Integer selectedDriverID) {
        this.selectedDriverID = selectedDriverID;

        // Have the new driver Id, find the info
        initDriverStops();
        initDriverStopsSummary();
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

    public boolean isValidTimeFrame(){        
        return validTimeFrames.contains(teamCommonBean.getTimeFrame());        
    }

    public void init(){            

        drivers = new ArrayList<Driver>();
        driverStops = new ArrayList<DriverStops>();
        driverStopsSummary = new ArrayList<DriverStops>();

        // Labels to the right of the driver name
        labels = new ArrayList<String>(Arrays.asList( Pattern.compile("\",\"|\"").split(
                MessageUtil.getMessageString("teamLabels"))));
        labels.remove(0);        
    }
    
    private void initDrivers(){       
        drivers = driverDAO.getDrivers(teamCommonBean.getGroupID());        
        Collections.sort(drivers);
    }

    private void initDriverStops() {       
        if (isValidTimeFrame()){
            driverStops =                        
                driverDAO.getStops(selectedDriverID, teamCommonBean.getTimeFrame().getInterval(getDateTimeZone()));
        } else {
            addInfoMessage("Please choose a valid time frame for the Team Stops tab");
        }
    }
    
    public void initDriverStopsSummary() {
        
        // Re-init values
        DriverStops d = new DriverStops();
        driverStopsSummary = new ArrayList<DriverStops>();
        
        // Extract from model method
        d = DriverStops.summarize(driverStops);
        
        driverStopsSummary.add(d);
    }

    public TimeZone getTimeZone() {
        
        // Return TimeZone if this driver is known.
//        if (tripsDrivers.containsKey(driverID))
//            return tripsDrivers.get(driverID).getPerson().getTimeZone();
        // Lookup driver, save Driver for repeat requests. 
        
        // Find by driver
//        Driver driver = driverDAO.findByID(selectedDriverID);
        
//        if (driver != null && driver.getPerson() != null && driver.getPerson().getTimeZone() != null) {
//            tripsDrivers.put(driver.getDriverID(), driver);
//            timeZone = driver.getPerson().getTimeZone();
//        }
//        else {
            // Use GMT for default if no driver associated.
//            timeZone = TimeZone.getTimeZone("GMT");
//        }
        
        // Find by logged-in user
        if (getPerson() != null && getPerson().getTimeZone() != null) {
//            tripsDrivers.put(driver.getDriverID(), driver);
            timeZone = getPerson().getTimeZone();
        }
        else {
            // Use GMT for default if no driver associated.
            timeZone = TimeZone.getTimeZone("GMT");
        }        
        
        return timeZone;
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
        ReportCriteria reportCriteria = getReportCriteria();
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        List<DriverStops> reportDataSet = new ArrayList<DriverStops>();
        reportDataSet.addAll(this.getDriverStops());
        reportDataSet.addAll(this.getDriverStopsSummary());
        reportCriteria.setMainDataset(reportDataSet);
        return reportCriteria;
    }
    
    protected ReportCriteria getReportCriteria()
    {
        return getReportCriteriaService().getTeamStopsReportCriteria(selectedDriverID, teamCommonBean.getTimeFrame(), 
                getDateTimeZone(), getLocale(), false);
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
