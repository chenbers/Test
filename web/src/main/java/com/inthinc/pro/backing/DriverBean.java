package com.inthinc.pro.backing;

import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.BreakdownSelections;
import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.charts.ChartSizes;
import com.inthinc.pro.charts.Line;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.model.Distance;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.util.GraphicUtil;

public class DriverBean extends BaseBean
{
	private static final Logger logger = Logger.getLogger(DriverBean.class);
	
    private ScoreDAO    scoreDAO;
    private Distance    distance = Distance.FIVEHUNDRED;
    private LatLng      lastLocation;
    
    private String      driverName;
    
    private Integer     overallScore;
    private String      overallScoreHistory;
    private String      overallScoreStyle;
    
    private String      mpgHistory;
    private String      coachingHistory;
    
    private NavigationBean navigation;
    	
	private BreakdownSelections breakdownSelected = BreakdownSelections.OVERALL;
	
	public DriverBean()
	{
	   
	}

	//INIT SCORES
    private void initOverallScore()
    {
        logger.debug("## initOverallScore()");
        ScoreableEntity overallSe = scoreDAO.getAverageScoreByTypeAndMiles(navigation.getDriver().getDriverID(), distance.getNumberOfMiles(), ScoreType.SCORE_OVERALL);
        if (overallSe == null)
            setOverallScore(0);
        else setOverallScore(overallSe.getScore());
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
		
        setOverallScoreHistory(createLineDef(ScoreType.SCORE_OVERALL)); // Get Chart data from DAO
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
	
	//COACHING properties
	public String getCoachingHistory() {
		setCoachingHistory(createLineDef(ScoreType.SCORE_COACHING_EVENTS));
		return coachingHistory;
	}
	public void setCoachingHistory(String coachingHistory) {
		this.coachingHistory = coachingHistory;
	}
	
	//DRIVER NAME properties
	public String getDriverName()
	{
//		if(logger.isDebugEnabled()){
//			logger.debug("getDriverName" + navigation.toString());
//			logger.debug("getDriverName" + navigation.getDriver().toString());
//			logger.debug("getDriverName" + navigation.getDriver().getPerson().toString());
//			logger.debug("getDriverName" + navigation.getDriver().getPerson().getFirst());
//			logger.debug("getDriverName" + navigation.getDriver().getPerson().getLast());
//		}
		logger.debug("getDriverName" + navigation.getDriver().toString());
	    setDriverName(navigation.getDriver().getPerson().getFirst() + " " + navigation.getDriver().getPerson().getLast());
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
    
    public String createLineDef(ScoreType scoreType)
    {
        
        StringBuffer sb = new StringBuffer();
        Line line = new Line();

        //Start XML Data
        sb.append(line.getControlParameters());
        
        if(navigation.getDriver() == null)
            logger.debug("Driver is null");
        
        List<ScoreableEntity> scoreList = scoreDAO.getDriverScoreHistoryByMiles(navigation.getDriver().getDriverID(), distance.getNumberOfMiles(), scoreType);
        for(ScoreableEntity e : scoreList)
        {
            sb.append(line.getChartItem(new Object[] {(double)(e.getScore() / 10.0d), e.getIdentifier()}));
        }

        //End XML Data
        sb.append(line.getClose());

        return sb.toString();
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
