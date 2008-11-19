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
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.SeatBeltEvent;
import com.inthinc.pro.model.SpeedingEvent;

public class DriverSeatBeltBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(DriverSeatBeltBean.class);
    
    private NavigationBean  navigation;
    private ScoreDAO    scoreDAO;
    private String      driverName;
    
    private Integer     seatBeltScore;
    private String      seatBeltScoreHistorySmall;
    private String      seatBeltScoreHistoryLarge;
    private String      seatBeltScoreStyle;
    
    private Distance distance = Distance.FIVEHUNDRED;
    
    private List<SeatBeltEvent> seatBeltEvents = new ArrayList<SeatBeltEvent>();
    
    private void initSeatBelt()
    {
        logger.debug("##### initStyle()  driverid:  " + navigation.getDriver().getDriverID());
        
        ScoreableEntity seatBeltSe = scoreDAO.getAverageScoreByTypeAndMiles(navigation.getDriver().getDriverID(), distance.getNumberOfMiles(), ScoreType.SCORE_SEATBELT);
        setSeatBeltScore(seatBeltSe.getScore());
    }
    
    public Integer getSeatBeltScore() {
        if(seatBeltScore == null)
            initSeatBelt();
        
        return seatBeltScore;
    }
    public void setSeatBeltScore(Integer seatBeltScore) {
        this.seatBeltScore = seatBeltScore;
        setSeatBeltScoreStyle(ScoreBox.GetStyleFromScore(seatBeltScore, ScoreBoxSizes.MEDIUM));
    }
    public String getSeatBeltScoreHistoryLarge() {
        setSeatBeltScoreHistoryLarge(createLineDef(ScoreType.SCORE_SEATBELT, ChartSizes.LARGE));
        return seatBeltScoreHistoryLarge;
    }
    public void setSeatBeltScoreHistoryLarge(String seatBeltScoreHistoryLarge) {
        this.seatBeltScoreHistoryLarge = seatBeltScoreHistoryLarge;
    }
 
    public String getSeatBeltScoreHistorySmall() {
        setSeatBeltScoreHistorySmall(createLineDef(ScoreType.SCORE_SEATBELT, ChartSizes.SMALL));
        return seatBeltScoreHistorySmall;
    }
    public void setSeatBeltScoreHistorySmall(String seatBeltScoreHistorySmall) {
        this.seatBeltScoreHistorySmall = seatBeltScoreHistorySmall;
    }
    
    public String getSeatBeltScoreStyle() {
        if(seatBeltScoreStyle == null)
            initSeatBelt();
        
        return seatBeltScoreStyle;
    }
    public void setSeatBeltScoreStyle(String seatBeltScoreStyle) {
        this.seatBeltScoreStyle = seatBeltScoreStyle;
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

    //STYLE EVENTS LIST
    public List<SeatBeltEvent> getSeatBeltEvents() {
        
        SeatBeltEvent s = new SeatBeltEvent();  //SeatBelt EVENT?
        s.setLatitude(90.000);
        s.setLongitude(-110.0000);
        s.setTime(new Date(456655000l));
        s.setSpeed(87);
        s.setAvgSpeed(65);
        s.setTopSpeed(91);
        s.setDistance(32);
        seatBeltEvents.add(s);

        SeatBeltEvent s2 = new SeatBeltEvent();
        s2.setLatitude(85.000);
        s2.setLongitude(-119.0000);
        s2.setTime(new Date(47054000l));
        s2.setSpeed(87);
        s2.setAvgSpeed(64);
        s2.setTopSpeed(78);
        s2.setDistance(22);
        
        seatBeltEvents.add(s2);
         
        return seatBeltEvents;
    }

    public void setSeatBeltEvents(List<SeatBeltEvent> seatBeltEvents) {
        this.seatBeltEvents = seatBeltEvents;
    }
    
    //DRIVER NAME PROPERTIES
    public String getDriverName() {
        //setDriverName(driverBean.getDriverName());
        return driverName;
    }
    public void setDriverName(String driverName) {
        this.driverName = driverName;
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

    //DISTANCE PROPERTIES
    public Distance getDistance()
    {
        return distance;
    }
    public void setDistance(Distance distance)
    {
        this.distance = distance;
    }
}
