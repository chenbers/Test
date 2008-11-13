package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.BreakdownSelections;
import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.backing.ui.ScoreBreakdown;
import com.inthinc.pro.backing.ui.ScoreCategory;

import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.Distance;
import com.inthinc.pro.model.SpeedingEvent;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.charts.Line;
import com.inthinc.pro.charts.ChartSizes;

public class DriverBean extends BaseBean
{
	private static final Logger logger = Logger.getLogger(DriverBean.class);
	
	/* DATA NEEDED for a Driver
	 * 
	 * Overall Score
	 * Overall Scores for X Miles
	 *
	 * Speed Score
	 * Speed Scores for X Miles
	 * 
	 * Driving Style Score
	 * Driving Style Scores for X Miles
	 * 
	 * Seat Belt Score
	 * Seat Belt Scores for X Miles
	 * 
	 * Coaching Events for X Miles.
	 * 
	 * Last Trip or Last Location
	 * 
	 * Miles Per Gallon data for chart
	 */
	
	private Driver     driver;
	
    private ScoreDAO    scoreDAO;
    private Distance    distance = Distance.FIVEHUNDRED;
    private LatLng      lastLocation;
    
    private String      driverName;
    
    private Integer     overallScore;
    private String      overallScoreHistory;
    private String      overallScoreStyle;
   
    private Integer     seatBeltScore;
    private String      seatBeltScoreHistory;
    private String      seatBeltScoreStyle;
    
    private String      mpgHistory;
    private String      coachingHistory;
    
    private NavigationBean navigation;
    	
	private BreakdownSelections breakdownSelected = BreakdownSelections.OVERALL;
	
    Integer endDate = DateUtil.getTodaysDate();
    Integer startDate = DateUtil.getDaysBackDate(endDate, 30);
    
	public DriverBean()
	{
	   
	}

	//INIT SCORES
    private void initOverallScore()
    {
        logger.debug("## initOverallScore()");
        ScoreableEntity overallSe = scoreDAO.getAverageScoreByType(getUser().getPerson().getGroupID(), startDate, endDate, ScoreType.SCORE_OVERALL);
        setOverallScore(overallSe.getScore());
    }

    
    private void initSeatBelt()
    {
        logger.debug("## initSeatBeltScore()");
        ScoreableEntity seatBeltSe = scoreDAO.getAverageScoreByType(getUser().getPerson().getGroupID(), startDate, endDate, ScoreType.SCORE_OVERALL); //Replace with correct DAO
        setSeatBeltScore(seatBeltSe.getScore());
    }
 
	//OVERALL SCORE properties
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
	
	public String getOverallScoreHistory() {
		
        setOverallScoreHistory(createLineDef(ScoreType.SCORE_OVERALL, ChartSizes.LARGE)); // Get Chart data from DAO
		return overallScoreHistory;
	}
	
	public void setOverallScoreHistory(String overallScoreHistory) {
		this.overallScoreHistory = overallScoreHistory;
	}

	public String getOverallScoreStyle() {
	    if (overallScoreStyle == null)
        {
            initOverallScore();
        }
        return overallScoreStyle;
	}
	public void setOverallScoreStyle(String overallScoreStyle) {
		this.overallScoreStyle = overallScoreStyle;
	}
	
	//SEAT BELT properties
	public Integer getSeatBeltScore() {
		if(seatBeltScore == null)
		    initSeatBelt();
	    
	    return seatBeltScore;
	}
	public void setSeatBeltScore(Integer seatBeltScore) {
	    setSeatBeltScoreStyle(ScoreBox.GetStyleFromScore(seatBeltScore, ScoreBoxSizes.MEDIUM));
	    this.seatBeltScore = seatBeltScore;
	}
	public String getSeatBeltScoreHistory() {
		setSeatBeltScoreHistory(createLineDef(ScoreType.SCORE_SEATBELT, ChartSizes.LARGE));
		return seatBeltScoreHistory;
	}
	public void setSeatBeltScoreHistory(String seatBeltScoreHistory) {
		this.seatBeltScoreHistory = seatBeltScoreHistory;
	}
	public String getSeatBeltScoreStyle() {
	    if(seatBeltScoreStyle == null)
	        initSeatBelt();
		return seatBeltScoreStyle;
	}
	public void setSeatBeltScoreStyle(String seatBeltScoreStyle) {
		this.seatBeltScoreStyle = seatBeltScoreStyle;
	}
	
	//COACHING properties
	public String getCoachingHistory() {
		setCoachingHistory(createLineDef(ScoreType.SCORE_COACHING_EVENTS, ChartSizes.LARGE));
		return coachingHistory;
	}
	public void setCoachingHistory(String coachingHistory) {
		this.coachingHistory = coachingHistory;
	}
	
	//DRIVER NAME properties
	public String getDriverName() {
	    //setDriverName(navigation.getDriver().getPerson().getFirst() + " " + navigation.getDriver().getPerson().getLast());
	    return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
    //DISTANCE properties
    public Distance getDistance()
    {
        return distance;
    }
    public String getDistanceAsString()
    {   
        return distance.toString();
    }

    public void setDistance(Distance distance)
    {
      
        this.distance = distance;
    }

    //SCORE DAO PROPERTIES
    public ScoreDAO getScoreDAO()
    {
        return scoreDAO;
    }

    public void setScoreDAO(ScoreDAO scoreDAO)
    {
        this.scoreDAO = scoreDAO;
    }
    
  
	
	//LAST LOCATION PROPERTIES
	public LatLng getLastLocation() {
		if(lastLocation == null)
		    lastLocation = new LatLng(90.00, -120.00); // Get from DAO 
	    
	    return lastLocation;
	}

	public void setLastLocation(LatLng lastLocation) {
		this.lastLocation = lastLocation;
	}

    //BREAKDOWN SELECTION PROPERTIES
    public BreakdownSelections getBreakdownSelected()
    {
        return breakdownSelected;
    }

    public void setBreakdownSelected(BreakdownSelections breakdownSelected)
    {
        this.breakdownSelected = breakdownSelected;
    }
	
	//MPG PROPERTIES
    public String getMpgHistory()
    {
        if(mpgHistory == null)
            mpgHistory = this.createMiniLineDef();
        
        return mpgHistory;
    }

    public void setMpgHistory(String mpgHistory)
    {
        this.mpgHistory = mpgHistory;
    }

    
    private String createMiniLineDef()
    {
        StringBuffer sb = new StringBuffer();
        //Control parameters
        sb.append(GraphicUtil.createMiniLineControlParameters());
        sb.append(GraphicUtil.createFakeMiniLineData());        
        sb.append("</chart>");
        
        return sb.toString();
    }
    
    public String createLineDef(ScoreType scoreType, ChartSizes size)
    {
        StringBuffer sb = new StringBuffer();
        Line line = new Line();

        //Start XML Data
        sb.append(line.getControlParameters(size));
        
        List<ScoreableEntity> scoreList = scoreDAO.getDriverScoreHistoryByMiles(101, distance.getNumberOfMiles(), scoreType);
        for(ScoreableEntity e : scoreList)
        {
            sb.append(line.getChartItem(new Object[] {(double)(e.getScore() / 10.0d), e.getIdentifier()}));
        }

        //End XML Data
        sb.append(line.getClose());

        return sb.toString();
    }

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
