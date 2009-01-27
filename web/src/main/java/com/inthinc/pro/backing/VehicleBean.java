package com.inthinc.pro.backing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.ui.BreakdownSelections;
import com.inthinc.pro.backing.ui.EventReportItem;
import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.backing.ui.TripDisplay;
import com.inthinc.pro.charts.FusionMultiLineChart;
import com.inthinc.pro.charts.Line;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.MpgDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.Duration;
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

    private VehicleDAO          vehicleDAO;
    private DriverDAO           driverDAO;
    private ScoreDAO            scoreDAO;
    private MpgDAO              mpgDAO;
    private EventDAO            eventDAO;
    private NavigationBean      navigation;

    private TripDisplay         lastTrip;
    private List<Event>         violationEvents = new ArrayList<Event>();
    private Integer             overallScore;
    private String              overallScoreHistory;
    private String              overallScoreStyle;
    private String              mpgHistory;
    private String              coachingHistory;
    private Boolean             hasLastTrip;

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
        if(violationEvents.size() > 0) return;
        
        List<Integer> types = new ArrayList<Integer>();
        types.add(EventMapper.TIWIPRO_EVENT_SPEEDING_EX3);
        types.add(EventMapper.TIWIPRO_EVENT_SEATBELT);
        types.add(EventMapper.TIWIPRO_EVENT_NOTEEVENT);

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
                hasLastTrip = true;
                navigation.setDriver(driverDAO.findByID(tempTrip.getDriverID()));
                
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
    
    public void setLastTrip(TripDisplay lastTrip)
    {
        this.lastTrip = lastTrip;
    }

    // VIOLATIONS PROPERTIES
    public List<Event> getViolationEvents()
    {
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

    private String createMultiLineDef()
    {
        List<MpgEntity> mpgEntities = mpgDAO.getVehicleEntities(navigation.getVehicle().getVehicleID(), getDuration(), null);
        List<String> catLabelList = GraphicUtil.createMonthList(getDuration());
        
        StringBuffer sb = new StringBuffer();
        FusionMultiLineChart multiLineChart = new FusionMultiLineChart();
        sb.append(multiLineChart.getControlParameters());

        Integer lightValues[] = new Integer[mpgEntities.size()];
        Integer medValues[] = new Integer[mpgEntities.size()];
        Integer heavyValues[] = new Integer[mpgEntities.size()];
        int cnt = 0;
        sb.append(multiLineChart.getCategoriesStart());
        for (MpgEntity entity : mpgEntities)
        {
            lightValues[cnt] = entity.getLightValue() == null ? 0 : entity.getLightValue();
            medValues[cnt] = entity.getMediumValue() == null ? 0 : entity.getMediumValue();
            heavyValues[cnt] = entity.getHeavyValue() == null ? 0 : entity.getHeavyValue();
            sb.append(multiLineChart.getCategoryLabel(catLabelList.get(cnt)));
            cnt++;

        }
        sb.append(multiLineChart.getCategoriesEnd());
        
        sb.append(multiLineChart.getChartDataSet("Light", "B1D1DC", "B1D1DC", lightValues, catLabelList));
        sb.append(multiLineChart.getChartDataSet("Medium", "C8A1D1", "C8A1D1", medValues, catLabelList));
        sb.append(multiLineChart.getChartDataSet("Heavy", "A8C634", "A8C634", heavyValues, catLabelList));

        sb.append(multiLineChart.getClose());
        
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
//        List<String> monthList = GraphicUtil.createMonthListFromMapDate(scoreList);
        List<String> monthList = GraphicUtil.createMonthList(getDuration());        

        int cnt = 0;
        for (ScoreableEntity e : scoreList)
        {            
//          sb.append(line.getChartItem(new Object[] { 
//              (double) (e.getScore() / 10.0d), 
//              monthList.get(cnt) }));
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
//              dateFormatter.format(e.getCreated()) })); 
            
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
