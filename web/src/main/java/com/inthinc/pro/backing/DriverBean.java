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
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.MpgDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.model.Distance;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.util.GraphicUtil;


public class DriverBean extends BaseBean
{
	private static final Logger logger = Logger.getLogger(DriverBean.class);
	
	private DriverDAO   driverDAO;
    private ScoreDAO    scoreDAO;
    private MpgDAO      mpgDAO;
    private EventDAO    eventDAO;
    private Distance    distance = Distance.FIVEHUNDRED;
    private Duration    duration = Duration.THREE;
    private TripDisplay lastTrip;
    private List<Event> violationEvents;
    private String      driverName;  
    private Integer     overallScore;
    private String      overallScoreHistory;
    private String      overallScoreStyle;
    private String      mpgHistory;
    private String      coachingHistory;
  
    private NavigationBean navigation;
	private BreakdownSelections breakdownSelected = BreakdownSelections.OVERALL;

    private void initOverallScore()
    {
        logger.debug("## initOverallScore()");
        //ScoreableEntity overallSe = scoreDAO.getAverageScoreByTypeAndMiles(navigation.getDriver().getDriverID(), distance.getNumberOfMiles(), ScoreType.SCORE_OVERALL);
        ScoreableEntity overallSe = scoreDAO.getDriverAverageScoreByType(navigation.getDriver().getDriverID(), duration, ScoreType.SCORE_OVERALL);
        
        if (overallSe == null)
            setOverallScore(0);
        else setOverallScore(overallSe.getScore());
    }
    
    //INIT VIOLATIONS
    public void initViolations(Date start, Date end)
    {
        List<Integer> types = new ArrayList<Integer>();
        types.add(93); // EventMapper.TIWIPRO_EVENT_SPEEDING_EX3
        types.add(3);  // EventMapper.TIWIPRO_EVENT_SEATBELT
        types.add(2);  // EventMapper.TIWIPRO_EVENT_NOTEEVENT
        
        logger.debug("VIOLATION EVENTS " + start.toGMTString() + end.toGMTString());
        violationEvents = eventDAO.getEventsForDriver(navigation.getDriver().getDriverID(), start, end, types);  
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
		
        setOverallScoreHistory(createLineDef(ScoreType.SCORE_OVERALL));
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
	    setDriverName(navigation.getDriver().getPerson().getFirst() + " " + navigation.getDriver().getPerson().getLast());
	    return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	//LAST TRIP
    public TripDisplay getLastTrip()
    {
        Trip tempTrip = driverDAO.getLastTrip(navigation.getDriver().getDriverID());
        
        if(tempTrip != null) 
        {
            TripDisplay trip = new TripDisplay(tempTrip);
            setLastTrip(trip);
            initViolations(trip.getTrip().getStartTime(), trip.getTrip().getEndTime());
        }
        return lastTrip;
    }
    public void setLastTrip(TripDisplay lastTrip)
    {
        this.lastTrip = lastTrip;
    }
    //VIOLATIONS PROPERTIES
    public List<Event> getViolationEvents() 
    {
        if(violationEvents == null)
            violationEvents = new ArrayList<Event>();
        
        return violationEvents;
    }

    public void setViolationEvents(List<Event> violationEvents) {
        this.violationEvents = violationEvents;
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

    //DAO PROPERTIES
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
    public DriverDAO getDriverDAO()
    {
        return driverDAO;
    }
    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }
    public EventDAO getEventDAO() {
        return eventDAO;
    }
    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
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
            mpgHistory = this.createMultiLineDef();
        
        return mpgHistory;
    }

    public void setMpgHistory(String mpgHistory)
    {
        this.mpgHistory = mpgHistory;
    }

    
    private String createMultiLineDef()  //TODO REFACTOR WITH UTILITY CLASS
    {
        StringBuffer sb = new StringBuffer();
        
        //Control parameters
        sb.append(GraphicUtil.createMiniLineControlParameters());
        //sb.append(GraphicUtil.createFakeMiniLineData());        
        
        List<MpgEntity> mpgEntities = mpgDAO.getDriverEntities(navigation.getDriver().getDriverID(), distance.getNumberOfMiles(), 10);
        
        sb.append("<categories>");
        sb.append(" <category label=\"\"/>");
        sb.append(" <category label=\"\"/>");
        sb.append(" <category label=\"\"/>");
        sb.append(" <category label=\"\"/>");
        sb.append(" <category label=\"\"/>");
        sb.append("</categories>");
        
        //LIGHT DATASET
        sb.append("<dataset seriesName=\"Light\" color=\"B1D1DC\" plotBorderColor=\"B1D1DC\"> ");
        for (MpgEntity entity: mpgEntities)
        {  
            sb.append("<set value=\"" + entity.getLightValue() + "\"/>"); 
        }
        sb.append("</dataset>");
        
        //MEDIUM DATASET
        sb.append("<dataset seriesName=\"Medium\" color=\"C8A1D1\" plotBorderColor=\"C8A1D1\"> ");
        for (MpgEntity entity: mpgEntities)
        {  
            sb.append("<set value=\"" + entity.getMediumValue() + "\"/>"); 
        }
        sb.append("</dataset>");
        
        //HEAVY DATASET
        sb.append("<dataset seriesName=\"Heavy\" color=\"A8C634\" plotBorderColor=\"A8C634\"> ");
        for (MpgEntity entity: mpgEntities)
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

        //Start XML Data
        sb.append(line.getControlParameters());
        
        if(navigation.getDriver() == null)
            logger.debug("Driver is null");
        
        //List<ScoreableEntity> scoreList = scoreDAO.getDriverScoreHistoryByMiles(navigation.getDriver().getDriverID(), distance.getNumberOfMiles(), scoreType);
        List<ScoreableEntity> scoreList = scoreDAO.getDriverScoreHistory(navigation.getDriver().getDriverID(), duration, scoreType, 10);
        
        //DateFormat dateFormatter = new SimpleDateFormat("MMMMM");
        for(ScoreableEntity e : scoreList)
        {
            //dateFormatter.f
            
            sb.append(line.getChartItem(new Object[] {(double)(e.getScore() / 10.0d), e.getIdentifier()}));
        }

        //End XML Data
        sb.append(line.getClose());

        return sb.toString();
    }
    
    public String createLineDef2(ScoreType scoreType)
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
