package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Pattern;

import org.richfaces.json.JSONArray;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverStopsReportItem;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.util.MessageUtil;

public class TeamStopsBean extends BaseBean {
    
    private static final EnumSet<TimeFrame> validTimeFrames = 
        EnumSet.range(TimeFrame.TODAY,TimeFrame.WEEK);    

    /**
     * Backing bean for the TeamStops tab of the new team page
     */
    private static final long serialVersionUID = 1L;
    private static final int driversPerPage = 25;
    
    private DriverDAO driverDAO;
    
    private TeamCommonBean teamCommonBean;
        
    private List<String> colors;
    private List<String> textColors;
    private List<String> labels;    
    
    private List<Driver> drivers;
    private List<DriverStopsReportItem> driverStops;
    private List<DriverStopsReportItem> driverStopsSummary;    
    private Integer selectedDriverID;   

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
        return drivers;
    }  
    
    public List<DriverStopsReportItem> getDriverStops() {
        return driverStops;
    }
    
    public List<DriverStopsReportItem> getDriverStopsSummary() {
        return driverStopsSummary;
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

    public boolean isValidTimeFrame(){        
        return validTimeFrames.contains(teamCommonBean.getTimeFrame());        
    }

    public void init(){            
        initDrivers();
        initDriverStops();
        initDriverStopsSummary();

        // Labels to the right of the driver name
        labels = new ArrayList<String>(Arrays.asList( Pattern.compile("\",\"|\"").split(
                MessageUtil.getMessageString("teamLabels"))));
        labels.remove(0);        
    }
    
    private void initDrivers(){       
        drivers = driverDAO.getDrivers(teamCommonBean.getGroupID());
    }

    private void initDriverStops() {
        
        // fake some data to get all the formatting correct in the page
        DriverStopsReportItem dsri = new DriverStopsReportItem();
        driverStops = new ArrayList<DriverStopsReportItem>();
        
        dsri.setArrive("1/1/2010 8:05AM");
        dsri.setDepart("1/1/2010 8:11AM");
        dsri.setDriveTime(200);
        dsri.setHighIdle(20);
        dsri.setLatitude(40.710882);
        dsri.setLongitude(-111.993299);
        dsri.setLowIdle(40);
        dsri.setRoundTrip(1);
        dsri.setStopLocation("");
        dsri.setTotalTimeAtStop(160);
        dsri.setWait(100);
        
        driverStops.add(dsri);
        dsri = new DriverStopsReportItem();
        
        dsri.setArrive("1/1/2010 9:01AM");
        dsri.setDepart("1/1/2010 9:21AM");
        dsri.setDriveTime(400);
        dsri.setHighIdle(50);
        dsri.setLatitude(40.594077);
        dsri.setLongitude(-111.952615);
        dsri.setLowIdle(70);
        dsri.setRoundTrip(2);
        dsri.setStopLocation("");
        dsri.setTotalTimeAtStop(130);
        dsri.setWait(10);
        
        driverStops.add(dsri);        
    }
    
    public void initDriverStopsSummary() {
        
        DriverStopsReportItem d = new DriverStopsReportItem();
        driverStopsSummary = new ArrayList<DriverStopsReportItem>();
        
        int roundTrip = 0;
        int totalTimeAtStop = 0;
        int lowIdle = 0;
        int highIdle = 0;
        int wait = 0;
        int driveTime = 0;
        
        for ( DriverStopsReportItem dsri: driverStops ) {
            if ( dsri.getRoundTrip() != null ) {
                roundTrip++;
            }
            if ( dsri.getTotalTimeAtStop() != null ) {
                totalTimeAtStop += dsri.getTotalTimeAtStop();
            }
            if ( dsri.getLowIdle() != null ) {
                lowIdle += dsri.getLowIdle();
            }
            if ( dsri.getHighIdle() != null ) {
                highIdle += dsri.getHighIdle();
            }
            if ( dsri.getWait() != null ) {
                wait += dsri.getWait();
            }
            if ( dsri.getDriveTime() != null ) {
                driveTime += dsri.getDriveTime();
            }
        }
        
        d.setRoundTrip(roundTrip);
        d.setTotalTimeAtStop(totalTimeAtStop);
        d.setLowIdle(lowIdle);
        d.setHighIdle(highIdle);
        d.setWait(wait);
        d.setDriveTime(driveTime);
        
        driverStopsSummary.add(d);
    }
}
