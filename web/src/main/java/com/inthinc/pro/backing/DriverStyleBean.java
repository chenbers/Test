package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.backing.ui.ScoreBreakdown;
import com.inthinc.pro.backing.ui.ScoreCategory;
import com.inthinc.pro.charts.ChartSizes;
import com.inthinc.pro.charts.Line;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Distance;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventType;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.AggressiveDrivingEvent;

public class DriverStyleBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(DriverStyleBean.class);
    private NavigationBean  navigation;
    private ScoreDAO        scoreDAO;
    private EventDAO        eventDAO;
    
    private String          driverName;
    private Distance        distance = Distance.FIVEHUNDRED;
    
    private Integer         styleScoreOverall;
    private String          styleScoreOverallStyle;
    private Integer         styleScoreAccel;
    private String          styleScoreAccelStyle;
    private Integer         styleScoreBrake;
    private String          styleScoreBrakeStyle;
    private Integer         styleScoreBump; 
    private String          styleScoreBumpStyle;
    private Integer         styleScoreTurn;
    private String          styleScoreTurnStyle;
    
    private String          styleScoreHistoryOverall;
    private String          styleScoreHistoryAccel;
    private String          styleScoreHistoryBrake;
    private String          styleScoreHistoryBump;
    private String          styleScoreHistoryTurn;
    
    private List<AggressiveDrivingEvent> styleEvents = new ArrayList<AggressiveDrivingEvent>();
    
    private void init()
    {
        int dist = distance.getNumberOfMiles();
        int driverID = navigation.getDriver().getDriverID();
        
        ScoreableEntity se = scoreDAO.getAverageScoreByTypeAndMiles(driverID, dist, ScoreType.SCORE_DRIVING_STYLE);
        setStyleScoreOverall(se == null ? 0 : se.getScore());
        
        se = scoreDAO.getAverageScoreByTypeAndMiles(driverID, dist, ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL);
        setStyleScoreAccel(se == null ? 0 : se.getScore());
        
        se = scoreDAO.getAverageScoreByTypeAndMiles(driverID, dist, ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE);
        setStyleScoreBrake(se == null ? 0 : se.getScore());
        
        se = scoreDAO.getAverageScoreByTypeAndMiles(driverID, dist, ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP);
        setStyleScoreBump(se == null ? 0 : se.getScore());
        
        se = scoreDAO.getAverageScoreByTypeAndMiles(driverID, dist, ScoreType.SCORE_DRIVING_STYLE_HARD_TURN);
        setStyleScoreTurn(se == null ? 0 : se.getScore());
        
    }
    
    public String createLineDef(ScoreType scoreType)
    {
        logger.debug("*** Getting score history for " + driverName + " " + scoreType.toString());
        StringBuffer sb = new StringBuffer();
        Line line = new Line();
        
        //Start XML Data
        sb.append(line.getControlParameters());
        
        List<ScoreableEntity> scoreList = scoreDAO.getDriverScoreHistoryByMiles(navigation.getDriver().getDriverID(), distance.getNumberOfMiles(), scoreType);
        for(ScoreableEntity e : scoreList)
        {
            sb.append(line.getChartItem(new Object[] {(double)(e.getScore() / 10.0d), e.getIdentifier()}));
        }

        //End XML Data
        sb.append(line.getClose());

        return sb.toString();
    }
    
    //SPEED OVERALL SCORE PROPERTY
    public Integer getStyleScoreOverall() {
        return styleScoreOverall;
    }
    public void setStyleScoreOverall(Integer styleScoreOverall) {
        this.styleScoreOverall = styleScoreOverall;
        setStyleScoreOverallStyle(ScoreBox.GetStyleFromScore(styleScoreOverall, ScoreBoxSizes.MEDIUM));
    }
    public String getStyleScoreOverallStyle() {
        return styleScoreOverallStyle;
    }
    public void setStyleScoreOverallStyle(String styleScoreOverallStyle) {
        this.styleScoreOverallStyle = styleScoreOverallStyle;
    }
    
    //STYLE SCORE HARD ACCEL
    public Integer getStyleScoreAccel()
    {
        return styleScoreAccel;
    }
    public void setStyleScoreAccel(Integer styleScoreAccel)
    {
        this.styleScoreAccel = styleScoreAccel;
        setStyleScoreAccelStyle(ScoreBox.GetStyleFromScore(styleScoreAccel, ScoreBoxSizes.MEDIUM));
    }
    public String getStyleScoreAccelStyle()
    {
        return styleScoreAccelStyle;
    }
    public void setStyleScoreAccelStyle(String styleScoreAccelStyle)
    {
        this.styleScoreAccelStyle = styleScoreAccelStyle;
    }
    
    //STYLE SCORE HARD BRAKE
    public Integer getStyleScoreBrake()
    {
        return styleScoreBrake;
    }
    public void setStyleScoreBrake(Integer styleScoreBrake)
    {
        setStyleScoreBrakeStyle(ScoreBox.GetStyleFromScore(styleScoreBrake, ScoreBoxSizes.MEDIUM));
        this.styleScoreBrake = styleScoreBrake;
    }
    public String getStyleScoreBrakeStyle()
    {
        return styleScoreBrakeStyle;
    }
    public void setStyleScoreBrakeStyle(String styleScoreBrakeStyle)
    {
        this.styleScoreBrakeStyle = styleScoreBrakeStyle;
    }

    //STYLE SCORE HARD BUMP
    public Integer getStyleScoreBump()
    {
        return styleScoreBump;
    }
    public void setStyleScoreBump(Integer styleScoreBump)
    {
        setStyleScoreBumpStyle(ScoreBox.GetStyleFromScore(styleScoreBump, ScoreBoxSizes.MEDIUM));
        this.styleScoreBump = styleScoreBump;
    }
    public String getStyleScoreBumpStyle()
    {
        return styleScoreBumpStyle;
    }
    public void setStyleScoreBumpStyle(String styleScoreBumpStyle)
    {
        this.styleScoreBumpStyle = styleScoreBumpStyle;
    }

    //STYLE SCORE HARD TURN
    public Integer getStyleScoreTurn()
    {
        return styleScoreTurn;
    }
    public void setStyleScoreTurn(Integer styleScoreTurn)
    {
        setStyleScoreTurnStyle(ScoreBox.GetStyleFromScore(styleScoreTurn, ScoreBoxSizes.MEDIUM));
        this.styleScoreTurn = styleScoreTurn;
    }
    public String getStyleScoreTurnStyle()
    {
        return styleScoreTurnStyle;
    }
    public void setStyleScoreTurnStyle(String styleScoreTurnStyle)
    {
        this.styleScoreTurnStyle = styleScoreTurnStyle;
    }

    //OVERALL HISTORY PROPERTY
    public String getStyleScoreHistoryOverall() {
        setStyleScoreHistoryOverall(createLineDef(ScoreType.SCORE_DRIVING_STYLE));
        return styleScoreHistoryOverall;
    }
    public void setStyleScoreHistoryOverall(String styleScoreHistoryOverall) {
        this.styleScoreHistoryOverall = styleScoreHistoryOverall;
    }
    
    //SCORE HISTORY HARD ACCEL
    public String getStyleScoreHistoryAccel() {
        setStyleScoreHistoryAccel(createLineDef(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL));
        return styleScoreHistoryAccel;
    }
    public void setStyleScoreHistoryAccel(String styleScoreHistoryAccel) {
        this.styleScoreHistoryAccel = styleScoreHistoryAccel;
    }
    //STYLE HISTORY HARD BRAKE
    public String getStyleScoreHistoryBrake()
    {
        setStyleScoreHistoryBrake(createLineDef(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE));
        return styleScoreHistoryBrake;
    }
    public void setStyleScoreHistoryBrake(String styleScoreHistoryBrake)
    {
        this.styleScoreHistoryBrake = styleScoreHistoryBrake;
    }

    //STYLE HISTORY HARD BUMP
    public String getStyleScoreHistoryBump()
    {
        setStyleScoreHistoryBump(createLineDef(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP));
        return styleScoreHistoryBump;
    }
    public void setStyleScoreHistoryBump(String styleScoreHistoryBump)
    {
        this.styleScoreHistoryBump = styleScoreHistoryBump;
    }

    //STYLE HISTORY HARD TURN
    public String getStyleScoreHistoryTurn()
    {
        setStyleScoreHistoryTurn(createLineDef(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN));
        return styleScoreHistoryTurn;
    }
    public void setStyleScoreHistoryTurn(String styleScoreHistoryTurn)
    {
        this.styleScoreHistoryTurn = styleScoreHistoryTurn;
    }

    //DAO PROPERTY
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

    //DISTANCE PROPERTY
    public Distance getDistance()
    {
        return distance;
    }
    public void setDistance(Distance distance)
    {
        this.distance = distance;
        init();
    }

    //DRIVING STYLE EVENTS LIST
    public List<AggressiveDrivingEvent> getStyleEvents()
    {
        List<Integer> types = new ArrayList<Integer>();    
        types.add(2);
        
        List<Event> tempEvents = new ArrayList<Event>();
        tempEvents = eventDAO.getEventsForDriverByMiles(navigation.getDriver().getDriverID(), distance.getNumberOfMiles(), types);
       
        for(Event event: tempEvents)
        {
            styleEvents.add( (AggressiveDrivingEvent)event );   
        }
        
        return styleEvents;
    }
   
    public void setStyleEvents(List<AggressiveDrivingEvent> styleEvents) {
        this.styleEvents = styleEvents;
    }
    
    //DRIVER NAME PROPERTY
    public String getDriverName() {
        setDriverName(navigation.getDriver().getPerson().getFirst() + " " + navigation.getDriver().getPerson().getLast());
        return driverName;
    }
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    //NAVIGATION PROPERTY
    public NavigationBean getNavigation()
    {
        return navigation;
    }
    public void setNavigation(NavigationBean navigation)
    {
        this.navigation = navigation;
    }
}
