package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.richfaces.json.JSONArray;

import com.inthinc.pro.backing.TeamTripsBean.DriverTrips;
import com.inthinc.pro.backing.TeamTripsBean.EventData;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Event;
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

        labels = new ArrayList<String>(Arrays.asList( Pattern.compile("\",\"|\"").split(MessageUtil.getMessageString("teamLabels"))));
        labels.remove(0);        
    }
    
    private void initDrivers(){       
        drivers = driverDAO.getDrivers(teamCommonBean.getGroupID());
    }

}
