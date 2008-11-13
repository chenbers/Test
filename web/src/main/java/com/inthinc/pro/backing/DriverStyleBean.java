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
import com.inthinc.pro.model.SpeedingEvent;

public class DriverStyleBean extends BaseBean
{
    private Driver     driver;
    private String      driverName;
    
    private static final Logger logger = Logger.getLogger(DriverSpeedBean.class);
    
    private ScoreDAO    scoreDAO;
    private DriverBean  driverBean;
 
    
    private Integer     styleScore;
    private String      styleScoreHistorySmall;
    private String      styleScoreHistoryLarge;
    private String      styleScoreStyle;
    
    private List<Event> styleEvents = new ArrayList<Event>();
    
    Integer endDate = DateUtil.getTodaysDate();
    Integer startDate = DateUtil.getDaysBackDate(endDate, 30);
    
    private void initStyle()
    {
        
        logger.debug("## initStyle()");
        
        ScoreableEntity styleSe = scoreDAO.getAverageScoreByType(getUser().getPerson().getGroupID(), startDate, endDate, ScoreType.SCORE_SPEEDING); //Replace with correct DAO
        setStyleScore(styleSe.getScore());
    }
    
  
    public Integer getStyleScore() {
        if(styleScore == null)
            initStyle();
        
        return styleScore;
    }
    public void setStyleScore(Integer styleScore) {
        this.styleScore = styleScore;
        setStyleScoreStyle(ScoreBox.GetStyleFromScore(styleScore, ScoreBoxSizes.MEDIUM));
    }
    public String getStyleScoreHistoryLarge() {
        setStyleScoreHistoryLarge(createLineDef(ScoreType.SCORE_DRIVING_STYLE, ChartSizes.LARGE));
        return styleScoreHistoryLarge;
    }
    public void setStyleScoreHistoryLarge(String styleScoreHistoryLarge) {
        this.styleScoreHistoryLarge = styleScoreHistoryLarge;
    }
 
    public String getStyleScoreHistorySmall() {
        setStyleScoreHistorySmall(createLineDef(ScoreType.SCORE_DRIVING_STYLE, ChartSizes.SMALL));
        return styleScoreHistorySmall;
    }
    public void setStyleScoreHistorySmall(String styleScoreHistorySmall) {
        this.styleScoreHistorySmall = styleScoreHistorySmall;
    }
    
    public String getStyleScoreStyle() {
        if(styleScoreStyle == null)
            initStyle();
        
        return styleScoreStyle;
    }
    public void setStyleScoreStyle(String styleScoreStyle) {
        this.styleScoreStyle = styleScoreStyle;
    }
    
    public String createLineDef(ScoreType scoreType, ChartSizes size)
    {
        StringBuffer sb = new StringBuffer();
        Line line = new Line();

        //Start XML Data
        sb.append(line.getControlParameters(size));
        
        List<ScoreableEntity> scoreList = scoreDAO.getDriverScoreHistoryByMiles(101, driverBean.getDistance().getNumberOfMiles(), scoreType);
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
    public List<Event> getStyleEvents() {
        
        SpeedingEvent s = new SpeedingEvent();
        s.setLatitude(90.000);
        s.setLongitude(-110.0000);
        s.setTime(new Date(456655000l));
        s.setSpeed(87);
        s.setAvgSpeed(65);
        s.setTopSpeed(91);
        s.setDistance(32);
        styleEvents.add(s);

        SpeedingEvent s2 = new SpeedingEvent();
        s2.setLatitude(85.000);
        s2.setLongitude(-119.0000);
        s2.setTime(new Date(47054000l));
        s2.setSpeed(87);
        s2.setAvgSpeed(64);
        s2.setTopSpeed(78);
        s2.setDistance(22);
        styleEvents.add(s2);
         
        return styleEvents;
    }

    public void setStyleEvents(List<Event> styleEvents) {
        this.styleEvents = styleEvents;
    }
    
    //DRIVER PROPERTIES
    public String getDriverName() {
        //setDriverName(driverBean.getDriverName());
        return driverName;
    }
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    
    //DRIVER PROPERTIES
    public Driver getDriver()
    {
        return driver;
    }
    public void setDriver(Driver driver)
    {
        logger.debug("## setDriver() called " + driver.getDriverID()); 
        setDriverName(driver.getPerson().getFirst() + " " + driver.getPerson().getLast());
        this.driver = driver;
    }
    
    //DRIVER BEAN PROPERTIES used by Spring Injection
    public DriverBean getDriverBean()
    {
        return driverBean;
    }
    public void setDriverBean(DriverBean driverBean)
    {
        this.driverBean = driverBean;
    }


}
