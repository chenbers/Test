package com.inthinc.pro.backing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.BreakdownSelections;
import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.backing.ui.TripDisplay;
import com.inthinc.pro.charts.Line;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.MpgDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.util.GraphicUtil;

public class VehicleBean extends BaseDurationBean
{
    private static final Logger logger            = Logger.getLogger(VehicleBean.class);

    private VehicleDAO           vehicleDAO;
    private ScoreDAO            scoreDAO;
    private MpgDAO              mpgDAO;
    private EventDAO            eventDAO;
    private DriverDAO           driverDAO;

    private TripDisplay         lastTrip;
    private List<Event>         violationEvents;
    private Integer             overallScore;
    private String              overallScoreHistory;
    private String              overallScoreStyle;
    private String              mpgHistory;
    private String              coachingHistory;
    private Boolean             hasLastTrip;
    private Event               clearItem;

    private NavigationBean      navigation;
    private BreakdownSelections breakdownSelected = BreakdownSelections.OVERALL;

    private void initOverallScore()
    {
        ScoreableEntity overallSe = scoreDAO.getVehicleAverageScoreByType(navigation.getVehicle().getVehicleID(), getDuration(), ScoreType.SCORE_OVERALL);

        if (overallSe == null)
            setOverallScore(-1);
        else
            setOverallScore(overallSe.getScore());
    }

    // INIT VIOLATIONS
    public void initViolations(Date start, Date end)
    {
        List<Integer> types = new ArrayList<Integer>();
        types.add(EventMapper.TIWIPRO_EVENT_SPEEDING_EX3);
        types.add(EventMapper.TIWIPRO_EVENT_SEATBELT);
        types.add(EventMapper.TIWIPRO_EVENT_NOTEEVENT);

        violationEvents = new ArrayList<Event>();
        violationEvents = eventDAO.getEventsForVehicle(navigation.getVehicle().getVehicleID(), start, end, types);

        //Lookup Addresses for events
        AddressLookup lookup = new AddressLookup();
        for (Event event: violationEvents)
        {
            event.setAddressStr(lookup.getAddress(event.getLatitude(), event.getLongitude()));
        }
    }

    // OVERALL SCORE properties
    public Integer getOverallScore()
    {
        if (overallScore == null)
        {
            initOverallScore();
        }
        return overallScore;
    }

    public void setOverallScore(Integer overallScore)
    {
        this.overallScore = overallScore;
        setOverallScoreStyle(ScoreBox.GetStyleFromScore(overallScore, ScoreBoxSizes.MEDIUM));
    }

    public String getOverallScoreHistory()
    {

        setOverallScoreHistory(createLineDef(ScoreType.SCORE_OVERALL));
        return overallScoreHistory;
    }

    public void setOverallScoreHistory(String overallScoreHistory)
    {
        this.overallScoreHistory = overallScoreHistory;
    }

    public String getOverallScoreStyle()
    {
        if (overallScoreStyle == null)
        {
            initOverallScore();
        }
        return overallScoreStyle;
    }

    public void setOverallScoreStyle(String overallScoreStyle)
    {
        this.overallScoreStyle = overallScoreStyle;
    }

    // COACHING properties
    public String getCoachingHistory()
    {
        setCoachingHistory(createLineDef(ScoreType.SCORE_COACHING_EVENTS));
        return coachingHistory;
    }

    public void setCoachingHistory(String coachingHistory)
    {
        this.coachingHistory = coachingHistory;
    }

    // LAST TRIP
    public TripDisplay getLastTrip()
    {
        if(lastTrip == null)
        {
            Trip tempTrip = vehicleDAO.getLastTrip(navigation.getVehicle().getVehicleID());
            
            if (tempTrip != null && tempTrip.getRoute().size() > 0)
            {
                //Unique to VehicleBean
                navigation.setDriver(driverDAO.findByID(navigation.getVehicle().getDriverID()));
       
                hasLastTrip = true;
                TripDisplay trip = new TripDisplay(tempTrip, navigation.getDriver().getPerson().getTimeZone());
                setLastTrip(trip);
                initViolations(trip.getTrip().getStartTime(), trip.getTrip().getEndTime());
            }
            else
            {
                hasLastTrip = false;
            }
        }
        return lastTrip;
    }
    
    public void ClearEventAction()
    {
        Integer temp = eventDAO.forgive(navigation.getVehicle().getVehicleID(), clearItem.getNoteID());
        
        //logger.debug("Clearing event " + clearItem.getNoteID() + " result: " + temp.toString());
    }
    public Event getClearItem()
    {
        return clearItem;
    }

    public void setClearItem(Event clearItem)
    {
        this.clearItem = clearItem;
    }
    
    public void setLastTrip(TripDisplay lastTrip)
    {
        this.lastTrip = lastTrip;
    }

    // VIOLATIONS PROPERTIES
    public List<Event> getViolationEvents()
    {
        if (violationEvents == null)
            violationEvents = new ArrayList<Event>();

        return violationEvents;
    }

    public void setViolationEvents(List<Event> violationEvents)
    {
        this.violationEvents = violationEvents;
    }

    // DAO PROPERTIES
    public ScoreDAO getScoreDAO()
    {
        return scoreDAO;
    }

    public void setScoreDAO(ScoreDAO scoreDAO)
    {
        this.scoreDAO = scoreDAO;
    }

    public MpgDAO getMpgDAO()
    {
        return mpgDAO;
    }

    public void setMpgDAO(MpgDAO mpgDAO)
    {
        this.mpgDAO = mpgDAO;
    }

    public VehicleDAO getVehicleDAO()
    {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    public EventDAO getEventDAO()
    {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO)
    {
        this.eventDAO = eventDAO;
    }

    public DriverDAO getDriverDAO()
    {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }
    // BREAKDOWN SELECTION PROPERTIES
    public BreakdownSelections getBreakdownSelected()
    {
        return breakdownSelected;
    }

    public void setBreakdownSelected(BreakdownSelections breakdownSelected)
    {
        this.breakdownSelected = breakdownSelected;
    }

    // MPG PROPERTIES
    public String getMpgHistory()
    {
        if (mpgHistory == null)
            mpgHistory = this.createMultiLineDef();

        return mpgHistory;
    }

    public void setMpgHistory(String mpgHistory)
    {
        this.mpgHistory = mpgHistory;
    }

    private String createMultiLineDef() // TODO REFACTOR WITH UTILITY CLASS
    {
        StringBuffer sb = new StringBuffer();

        // Control parameters
        sb.append(GraphicUtil.createMiniLineControlParameters());
        // sb.append(GraphicUtil.createFakeMiniLineData());

        List<MpgEntity> mpgEntities = mpgDAO.getVehicleEntities(navigation.getVehicle().getVehicleID(), getDuration(), 10);

        sb.append("<categories>");
        sb.append(" <category label=\"\"/>");
        sb.append(" <category label=\"\"/>");
        sb.append(" <category label=\"\"/>");
        sb.append(" <category label=\"\"/>");
        sb.append(" <category label=\"\"/>");
        sb.append("</categories>");

        // LIGHT DATASET
        sb.append("<dataset seriesName=\"Light\" color=\"B1D1DC\" plotBorderColor=\"B1D1DC\"> ");
        for (MpgEntity entity : mpgEntities)
        {
            sb.append("<set value=\"" + entity.getLightValue() + "\"/>");
        }
        sb.append("</dataset>");

        // MEDIUM DATASET
        sb.append("<dataset seriesName=\"Medium\" color=\"C8A1D1\" plotBorderColor=\"C8A1D1\"> ");
        for (MpgEntity entity : mpgEntities)
        {
            sb.append("<set value=\"" + entity.getMediumValue() + "\"/>");
        }
        sb.append("</dataset>");

        // HEAVY DATASET
        sb.append("<dataset seriesName=\"Heavy\" color=\"A8C634\" plotBorderColor=\"A8C634\"> ");
        for (MpgEntity entity : mpgEntities)
        {
            sb.append("<set value=\"" + entity.getHeavyValue() + "\"/>");
        }
        sb.append("</dataset>");
        sb.append("</chart>");
        return sb.toString();
    }

    public String createLineDef(ScoreType scoreType)
    {
        StringBuffer sb = new StringBuffer();
        Line line = new Line();

        // Start XML Data
        sb.append(line.getControlParameters());

        List<ScoreableEntity> scoreList = scoreDAO.getVehicleScoreHistory(
                navigation.getVehicle().getVehicleID(), getDuration(), scoreType, 
                GraphicUtil.getDurationSize(getDuration()));
//                10);
        DateFormat dateFormatter = new SimpleDateFormat(getDuration().getDatePattern());

        // Get "x" values
        List<String> monthList = GraphicUtil.createMonthList(getDuration());

        int cnt = 0;
        for (ScoreableEntity e : scoreList)
        {            
//            sb.append(line.getChartItem(new Object[] { (double) (e.getScore() / 10.0d), monthList.get(cnt)}));
            if ( e.getScore() != null ) 
            {
                sb.append(line.getChartItem(new Object[] { 
                      (double) (e.getScore() / 10.0d), 
                      monthList.get(cnt) }));
            } else 
            {
                sb.append(line.getChartItem(new Object[] { 
                        null, 
                        monthList.get(cnt) }));
            }    
//          sb.append(line.getChartItem(new Object[] { (double) (e.getScore() / 10.0d), 
//                    dateFormatter.format(e.getCreated()) }));                        
            cnt++;
        }
        
        // End XML Data
        sb.append(line.getClose());

        return sb.toString();
    }

    // NAVIGATION BEAN PROPERTIES
    public NavigationBean getNavigation()
    {
        return navigation;
    }

    public void setNavigation(NavigationBean navigation)
    {
        this.navigation = navigation;
    }

    public Boolean getHasLastTrip()
    {
        return hasLastTrip;
    }

    public void setHasLastTrip(Boolean hasLastTrip)
    {
        this.hasLastTrip = hasLastTrip;
    }
    
}
