package com.inthinc.pro.backing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.backing.ui.ScoreBreakdown;
import com.inthinc.pro.backing.ui.ScoreCategory;
import com.inthinc.pro.charts.Line;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.AggressiveDrivingEvent;
import com.inthinc.pro.model.Distance;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.SeatBeltEvent;
import com.inthinc.pro.model.SpeedingEvent;
import com.inthinc.pro.util.GraphicUtil;

public class VehicleSeatBeltBean extends BaseDurationBean
{
    private static final Logger logger = Logger.getLogger(VehicleSeatBeltBean.class);
    
    private NavigationBean  navigation;
    private ScoreDAO        scoreDAO;
    private EventDAO        eventDAO;

    private Integer         seatBeltScore;
    private String          seatBeltScoreHistoryOverall;
    private String          seatBeltScoreStyle;
      
    private List<SeatBeltEvent> seatBeltEvents = new ArrayList<SeatBeltEvent>();
    
    private void initSeatBelt()
    {
        ScoreableEntity seatBeltSe = scoreDAO.getVehicleAverageScoreByType(navigation.getVehicle().getVehicleID(), getDuration(), ScoreType.SCORE_SEATBELT);
        if (seatBeltSe == null)
            setSeatBeltScore(0);
        else setSeatBeltScore(seatBeltSe.getScore());
    }
    
    //SCORE PROPERTIES
    public Integer getSeatBeltScore() {
        if(seatBeltScore == null)
            initSeatBelt();
        
        return seatBeltScore;
    }
    public void setSeatBeltScore(Integer seatBeltScore) {
        this.seatBeltScore = seatBeltScore;
        setSeatBeltScoreStyle(ScoreBox.GetStyleFromScore(seatBeltScore, ScoreBoxSizes.MEDIUM));
    }
    
    //SCORE HISTORY PROPERTIES
    public String getSeatBeltScoreHistoryOverall() {
        setSeatBeltScoreHistoryOverall(createLineDef(ScoreType.SCORE_SEATBELT));
        return seatBeltScoreHistoryOverall;
    }
    public void setSeatBeltScoreHistoryOverall(String seatBeltScoreHistoryOverall) {
        this.seatBeltScoreHistoryOverall = seatBeltScoreHistoryOverall;
    }
    
    //SCOREBOX STYLE PROPERTIES
    public String getSeatBeltScoreStyle() {
        if(seatBeltScoreStyle == null)
            initSeatBelt();
        
        return seatBeltScoreStyle;
    }
    public void setSeatBeltScoreStyle(String seatBeltScoreStyle) {
        this.seatBeltScoreStyle = seatBeltScoreStyle;
    }
    
    public String createLineDef(ScoreType scoreType)
    {
        StringBuffer sb = new StringBuffer();
        Line line = new Line();

        //Start XML Data
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
            sb.append(line.getChartItem(new Object[] { (double) (e.getScore() / 10.0d), monthList.get(cnt) }));
//          sb.append(line.getChartItem(new Object[] { (double) (e.getScore() / 10.0d), 
//              dateFormatter.format(e.getCreated()) }));
            cnt++;
        }

        //End XML Data
        sb.append(line.getClose());

        return sb.toString();
    }

    //DAO PROPERTIES
    public ScoreDAO getScoreDAO()
    {
        return scoreDAO;
    }
    public void setScoreDAO(ScoreDAO scoreDAO)
    {
        this.scoreDAO = scoreDAO;
    }
    public EventDAO getEventDAO()
    {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO)
    {
        this.eventDAO = eventDAO;
    }

    //SEATBELT EVENTS LIST
    public List<SeatBeltEvent> getSeatBeltEvents() 
    {
        if(seatBeltEvents.size() < 1)
        {
            List<Integer> types = new ArrayList<Integer>();    
            types.add(3);
            
            List<Event> tempEvents = new ArrayList<Event>();
            tempEvents = eventDAO.getEventsForVehicle(navigation.getVehicle().getVehicleID(), getStartDate(), getEndDate(), types);
           
            AddressLookup lookup = new AddressLookup();
            for(Event event: tempEvents)
            {
                event.setAddressStr(lookup.getAddress(event.getLatitude(), event.getLongitude()));
                seatBeltEvents.add( (SeatBeltEvent)event );   
            }
        }
        
        return seatBeltEvents;
    }

    public void setSeatBeltEvents(List<SeatBeltEvent> seatBeltEvents) {
        this.seatBeltEvents = seatBeltEvents;
    }
    
    @Override
    public void setDuration(Duration duration)
    {
        super.setDuration(duration);
   
    }

    //NAVIGATION BEAN PROPERTIES
    public NavigationBean getNavigation()
    {
        return navigation;
    }
    public void setNavigation(NavigationBean navigation)
    {
        this.navigation = navigation;
    }
}
