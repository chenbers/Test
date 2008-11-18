package com.inthinc.pro.backing;

import java.util.ArrayList;
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
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Distance;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.SpeedingEvent;

public class DriverSpeedBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(DriverSpeedBean.class);
    
    private String      driverName;
    private NavigationBean navigation;
    private ScoreDAO    scoreDAO;
    private Distance    distance = Distance.FIVEHUNDRED;
    
    private Integer     speedScore;
    private String      speedScoreHistorySmall;
    private String      speedScoreHistoryLarge;
    private String      speedScoreStyle;
    
    private List<SpeedingEvent> speedingEvents = new ArrayList<SpeedingEvent>();
    
    private void initSpeed()
    {
        logger.debug("## initSpeed()");
        
        ScoreableEntity speedSe = scoreDAO.getAverageScoreByTypeAndMiles(navigation.getDriver().getDriverID(), distance.getNumberOfMiles(), ScoreType.SCORE_SPEEDING); //Replace with correct DAO
        setSpeedScore(speedSe.getScore());
    }
  
    public Integer getSpeedScore() {
        if(speedScore == null)
            initSpeed();
        
        return speedScore;
    }
    public void setSpeedScore(Integer speedScore) {
        this.speedScore = speedScore;
        setSpeedScoreStyle(ScoreBox.GetStyleFromScore(speedScore, ScoreBoxSizes.MEDIUM));
    }
    public String getSpeedScoreHistoryLarge() {
        setSpeedScoreHistoryLarge(createLineDef(ScoreType.SCORE_SPEEDING, ChartSizes.LARGE));
        return speedScoreHistoryLarge;
    }
    public void setSpeedScoreHistoryLarge(String speedScoreHistoryLarge) {
        this.speedScoreHistoryLarge = speedScoreHistoryLarge;
    }
 
    public String getSpeedScoreHistorySmall() {
        setSpeedScoreHistorySmall(createLineDef(ScoreType.SCORE_SPEEDING, ChartSizes.SMALL));
        return speedScoreHistorySmall;
    }
    public void setSpeedScoreHistorySmall(String speedScoreHistorySmall) {
        this.speedScoreHistorySmall = speedScoreHistorySmall;
    }
    
    public String getSpeedScoreStyle() {
        if(speedScoreStyle == null)
            initSpeed();
        
        return speedScoreStyle;
    }
    public void setSpeedScoreStyle(String speedScoreStyle) {
        this.speedScoreStyle = speedScoreStyle;
    }
    
    public String createLineDef(ScoreType scoreType, ChartSizes size)
    {
        StringBuffer sb = new StringBuffer();
        Line line = new Line();

        //Start XML Data
        sb.append(line.getControlParameters(size));
        
        List<ScoreableEntity> scoreList = scoreDAO.getDriverScoreHistoryByMiles(navigation.getDriver().getDriverID(), distance.getNumberOfMiles(), scoreType);
        for(ScoreableEntity e : scoreList)
        {
            sb.append(line.getChartItem(new Object[] {(double)(e.getScore() / 10.0d), e.getIdentifier()}));
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

    //DISTANCE PROPERTIES
    public Distance getDistance()
    {
        return distance;
    }
    public void setDistance(Distance distance)
    {
        this.distance = distance;
    }

    //SPEEDING EVENTS LIST
    public List<SpeedingEvent> getSpeedingEvents() {
        
        SpeedingEvent s = new SpeedingEvent();
        s.setLatitude(90.000);
        s.setLongitude(-110.0000);
        s.setTime(new Date(456655000l));
        s.setSpeed(87);
        s.setAvgSpeed(65);
        s.setTopSpeed(91);
        s.setDistance(32);
        speedingEvents.add(s);

        SpeedingEvent s2 = new SpeedingEvent();
        s2.setLatitude(85.000);
        s2.setLongitude(-119.0000);
        s2.setTime(new Date(47054000l));
        s2.setSpeed(87);
        s2.setAvgSpeed(64);
        s2.setTopSpeed(78);
        s2.setDistance(22);
        speedingEvents.add(s2);
        
        return speedingEvents;
    }

    public void setSpeedingEvents(List<SpeedingEvent> speedingEvents) {
        this.speedingEvents = speedingEvents;
    }
    
    //DRIVER NAME PROPERTIES
    public String getDriverName() {
        setDriverName(navigation.getDriver().getPerson().getFirst() + " " + navigation.getDriver().getPerson().getLast());
        return driverName;
    }
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    //NAVIGATION PROPERTIES
    public NavigationBean getNavigation()
    {
        return navigation;
    }
    public void setNavigation(NavigationBean navigation)
    {
        this.navigation = navigation;
    }
}
