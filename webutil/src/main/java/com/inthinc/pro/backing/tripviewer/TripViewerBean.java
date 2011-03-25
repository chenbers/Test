package com.inthinc.pro.backing.tripviewer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.joda.time.DateTime;

import com.inthinc.pro.backing.BaseBean;
import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.tripviewer.model.EventAndAttr;

public class TripViewerBean extends BaseBean {
    private AccountDAO accountDAO;
    private GroupDAO groupDAO;
    private DriverDAO driverDAO;
    private EventDAO eventDAO;
    
    private Integer selectedAccount;
    private Integer selectedGroup;
    private Integer selectedDriver;
    
    private ArrayList<SelectItem> groups = new ArrayList<SelectItem>();
    private ArrayList<SelectItem> drivers = new ArrayList<SelectItem>();
    private List<Trip> trips = new ArrayList<Trip>();
    
    private Date endDate = (new DateTime()).toDate();
    private Date startDate = (new DateTime(endDate.getTime() - 7 * 24 * 60 * 60 * 1000)).toDate();
   
    private Trip tripToShow = null;
    
    private String speedLineDef;
    private String mpgLineDef;
    
    private String error = "Page loaded successfully.";
    
//    private LatLng tripStart;
//    private LatLng tripEnd;
    
    private List<EventAndAttr> tripNotes = new ArrayList<EventAndAttr>();
    
    public List<SelectItem> getAccounts()
    {
        
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        List<Account> accts = accountDAO.getAllAcctIDs();
        
        for (Account acct:accts)
        {
               selectItemList.add(new SelectItem(acct.getAcctID(),acct.getAcctName()));
        }
        
        return selectItemList;
    }
    
    public void findGroups()
    {
        if ( this.selectedAccount == null ) {
            this.error ="An account must be selected.";
            return;
        } else {
            this.error = "Groups for account found.";
        }
        
        this.groups.clear();
        List<Group> grps = groupDAO.getGroupsByAcctID(this.selectedAccount);
        
        for (Group grp:grps)
        {
               this.groups.add(new SelectItem(grp.getGroupID(),grp.getName()));
        }

    }

    public void findDrivers()
    {
        if ( this.selectedGroup == null ) {
            this.error ="A group must be selected.";
            return;
        } else {
            this.error = "Drivers for group found.";
        }        
        
        this.drivers.clear();
        List<Driver> drvrs = driverDAO.getDrivers(this.selectedGroup);
        
        for (Driver drvr:drvrs)
        {
               this.drivers.add(new SelectItem(drvr.getDriverID(),drvr.getPerson().getFullName()));
        }

    }
    
    public void findTrips() {
        
        this.trips.clear();
        this.speedLineDef = null;
        this.mpgLineDef = null;
        this.tripToShow = null;
        
        if ( this.selectedDriver == null || this.startDate == null || this.endDate == null ) {
            this.error ="A driver, start date, and end date must be selected.";
            this.speedLineDef = null;
            this.mpgLineDef = null;
            return;
        } else {
            this.error ="Trip selection completed successfully.";
        }

        this.trips = driverDAO.getTrips(this.selectedDriver, this.startDate, this.endDate);

        // Events that make up the trips
        List<NoteType> eventTypeList = new ArrayList<NoteType>();
        eventTypeList.add(NoteType.IGNITION_OFF);
        
        // Control parms
        mpgLineDef = "<chart caption=\'MPG for Trips\' xAxisName=\'Trip\' yAxisName=\'MPG\' showValues=\'0\' labelDisplay=\'ROTATE\' >";

        // x axis
        mpgLineDef += "<categories>";
        for ( Trip trp:this.trips ) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM d, yyyy hh:mm:ss");
            mpgLineDef += "<category label=\'" + dateFormatter.format(trp.getStartTime()) + "\' />";
        }
        mpgLineDef += "</categories>";

        // y axis
        mpgLineDef += "<dataset seriesName=\'MPG\'>";
        for ( Trip trp:this.trips ) {
            List<Event> tmp = eventDAO.getEventsForDriver(this.selectedDriver, trp.getStartTime(), trp.getEndTime(), 
                    eventTypeList, 1);
            
            // Toss out 
            List<EventAndAttr> events = getTripEvents(tmp, trp.getStartTime(), trp.getEndTime());
            float mpgAdj = 0.0f;
            
            // Looking for one ignition off only
            for ( Event evt:events ) {
                Map<Object,Object> attrMap = evt.getAttrMap();
                String mpgVal = "0";
                
                if ( attrMap != null ) {
                    mpgVal = (String)attrMap.get("149");
                    if ( mpgVal == null ) {
                        mpgVal = (String)attrMap.get("MPG");
                    }
                    
                    if ( mpgVal == null ) {
                        mpgAdj = 0.0f;
                    } else {
                        mpgAdj = (new Float(mpgVal)).floatValue()/10.f;
                    }
                } else {
                    mpgAdj = 0.0f;
                }
                
            }
            mpgLineDef += "<set value=\'" + mpgAdj + "\' />";
        }
        mpgLineDef += "</dataset>";        

        // Animate
        mpgLineDef += "<styles>" +

           "<definition>" +
              "<style name=\'CanvasAnim\' type=\'animation\' param=\'_xScale\' start=\'0\' duration=\'1\' />" +
           "</definition>" +

           "<application>" +
              "<apply toObject=\'Canvas\' styles=\'CanvasAnim\' />" +
           "</application>   " +

        "</styles>" +

        // Close
     "</chart>";        
        
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public EventDAO getEventDAO() {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    public Integer getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(Integer selectedAccount) {
        this.selectedAccount = selectedAccount;
    }

    public Integer getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(Integer selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public Integer getSelectedDriver() {
        return selectedDriver;
    }

    public void setSelectedDriver(Integer selectedDriver) {
        this.selectedDriver = selectedDriver;
    }

    public ArrayList<SelectItem> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<SelectItem> groups) {
        this.groups = groups;
    }

    public ArrayList<SelectItem> getDrivers() {
        return drivers;
    }

    public void setDrivers(ArrayList<SelectItem> drivers) {
        this.drivers = drivers;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Trip getTripToShow() {
        return tripToShow;
    }

    public void setTripToShow(Trip tripToShow) {
        this.tripToShow = tripToShow;
        this.speedLineDef = null;
        
        // Events that make up the trip
        List<NoteType> eventTypeList = new ArrayList<NoteType>();
        eventTypeList.add(NoteType.IGNITION_ON);
        eventTypeList.add(NoteType.IGNITION_OFF);
        eventTypeList.add(NoteType.LOCATION);
        
        List<Event> tmp = eventDAO.getEventsForDriver(this.selectedDriver, this.tripToShow.getStartTime(), this.tripToShow.getEndTime(), 
                eventTypeList, 1);
        
        // Toss out 
        tripNotes = getTripEvents(tmp, this.tripToShow.getStartTime(), this.tripToShow.getEndTime());
        
        // Have the selected trip, create speed line plot
//        StringBuffer sb = new StringBuffer();

        // Control parms
        speedLineDef = "<chart caption=\'Speed vs Limit\' xAxisName=\'Time\' yAxisName=\'Speed\' showValues=\'0\'  labelDisplay=\'ROTATE\' labelStep=\'5\' >";
        
        // The following loops are reversed as the data comes back sorted, just backward....
        // x axis
        speedLineDef += "<categories>";
        for ( int i=0; i< tripNotes.size() - 1; i++ ) {
            Event evt = tripNotes.get(tripNotes.size() - i - 1);
            SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM d, yyyy hh:mm:ss");
            speedLineDef += "<category label=\'" + dateFormatter.format(evt.getTime()) + "\' />";
        }
        speedLineDef += "</categories>";

        // y axis
        speedLineDef += "<dataset seriesName=\'Speed\'>";
        for ( int i=0; i< tripNotes.size() - 1; i++ ) {
            Event evt = tripNotes.get(tripNotes.size() - i - 1);
           speedLineDef += "<set value=\'" + evt.getSpeed() + "\' />";
        }
        speedLineDef += "</dataset>";
        speedLineDef += "<dataset seriesName=\'Limit\'>";
        for ( int i=0; i< tripNotes.size() - 1; i++ ) {
            Event evt = tripNotes.get(tripNotes.size() - i - 1);
           speedLineDef += "<set value=\'" + evt.getSpeedLimit() + "\' />";
        }
        speedLineDef += "</dataset>";        

        // Animate
        speedLineDef += "<styles>" +

           "<definition>" +
              "<style name=\'CanvasAnim\' type=\'animation\' param=\'_xScale\' start=\'0\' duration=\'1\' />" +
           "</definition>" +

           "<application>" +
              "<apply toObject=\'Canvas\' styles=\'CanvasAnim\' />" +
           "</application>   " +

        "</styles>" +

        // Close
     "</chart>";
    }

    public String getSpeedLineDef() {
        return speedLineDef;
    }

    public void setSpeedLineDef(String speedLineDef) {
        this.speedLineDef = speedLineDef;
    }
    
    public String getMpgLineDef() {
        return mpgLineDef;
    }

    public void setMpgLineDef(String mpgLineDef) {
        this.mpgLineDef = mpgLineDef;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LatLng getTripStart() {
        LatLng l = new LatLng();
        
        if ( tripToShow != null ) {
            l.setLat(tripToShow.getRoute().get(0).getLat());
            l.setLng(tripToShow.getRoute().get(0).getLng());
        }
        
        return l;
    }

//    public void setTripStart(LatLng tripStart) {
//        this.tripStart = tripStart;
//    }

    public LatLng getTripEnd() {
        LatLng l = new LatLng();
        
        if ( tripToShow != null ) {
            l.setLat(tripToShow.getRoute().get(tripToShow.getRoute().size()-1).getLat());
            l.setLng(tripToShow.getRoute().get(tripToShow.getRoute().size()-1).getLng());
        }
        
        return l;
    }

//    public void setTripEnd(LatLng tripEnd) {
//        this.tripEnd = tripEnd;
//    }

    public List<EventAndAttr> getTripNotes() {
        return tripNotes;
    }

    public void setTripNotes(List<EventAndAttr> tripNotes) {
        this.tripNotes = tripNotes;
    }

    private boolean eventInInterval(Date eventTime, Date startTime, Date endTime){
        
        return (eventTime.after(startTime) && eventTime.before(endTime))||
        eventTime.equals(startTime) || eventTime.equals(endTime);
    }
    
    private List<EventAndAttr> getTripEvents(List<Event> evt,Date startTime, Date endTime){
        
        List<EventAndAttr> tripEvents = new ArrayList<EventAndAttr>();
        
        for (Event event:evt){
            
            if (eventInInterval(event.getTime(), startTime, endTime)){
                
                // Sanitize bad data
                EventAndAttr eaa = new EventAndAttr(
                        event.getSats()!=null?event.getSats():0,
                        event.getSpeed()!=null?event.getSpeed():0,
                        event.getSpeedLimit()!=null?event.getSpeedLimit():0,
                        event.getLatitude()!=null?event.getLatitude():0.0,
                        event.getLongitude()!=null?event.getLongitude():0.0,
                        event.getTime()!=null?event.getTime():new Date(),
                        event.getAttrMap()!=null?event.getAttrMap():null,
                        event.getCreated()!=null?event.getCreated():new Date());
                eaa.setDecodedAttrMap(event.getAttrMap()); 
                tripEvents.add(eaa);
            }
        }
        return tripEvents;
    }    

}
