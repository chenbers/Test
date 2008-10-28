package com.inthinc.pro.backing;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.dao.GraphicDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.Distance;

public class DriverBean extends BaseBean
{
	private static final Logger logger = Logger.getLogger(DriverBean.class);
	
	// Get scores from DAO instead
	
	private String driverName = "Amy Johnson";
	
	private Integer overallScore;
	private String overallScoreHistory;
	private String overallScoreStyle = "score_med_2";
	
	private Integer speedScore = GraphicUtil.getRandomScore();
	private String speedScoreHistory;
	private String speedScoreStyle = "score_med_3";

	private Integer drivingScore = GraphicUtil.getRandomScore();
	private String drivingScoreHistory;
	private String drivingScoreStyle = "score_med_1";
	
	private Integer seatBeltScore = GraphicUtil.getRandomScore();
	private String seatBeltScoreHistory;
	private String seatBeltScoreStyle = "score_med_4";
	
	private String coachingHistory;
	
	private GraphicDAO     graphicDAO;
	private Distance       distance = Distance.FIVEHUNDRED;

	public DriverBean()
	{
	      Integer score = GraphicUtil.getRandomScore();
	      setOverallScore(score);
	}
	
    private String createLineDef() {
		StringBuffer sb = new StringBuffer();
		//Control parameters
		sb.append(GraphicUtil.createLineControlParameters());
		sb.append(GraphicUtil.createFakeLineData());		
		sb.append("</chart>");
		
		return sb.toString();
	}
	
    private void init()
    {
        //TODO get overall score for DRIVER not group.
        Integer endDate = DateUtil.getTodaysDate();
        Integer startDate = DateUtil.getDaysBackDate(endDate, distance.getNumberOfMiles());
        ScoreableEntity scoreableEntity = graphicDAO.getOverallScore(getUser().getGroupID(), startDate, endDate);
        setOverallScore(scoreableEntity.getScore());
    }
    
    private void initStyle()
    {
        if (overallScore == null)
        {
            init();
        }

        ScoreBox sb = new ScoreBox(getOverallScore(), ScoreBoxSizes.LARGE);
        setOverallScoreStyle(sb.getScoreStyle());
    }
    
	//OVERALL SCORE properties
	public Integer getOverallScore()
	{
        if (overallScore == null)
        {
            init();
        }
        return overallScore;
	}
	
	public void setOverallScore(Integer overallScore) 
	{
		this.overallScore = overallScore;
		initStyle();
	}
	public String getOverallScoreHistory() {
		
		setOverallScoreHistory(createLineDef()); // Get Chart data from DAO
		return overallScoreHistory;
	}
	public void setOverallScoreHistory(String overallScoreHistory) {
		this.overallScoreHistory = overallScoreHistory;
	}
	public String getOverallScoreStyle() {
	    if (overallScoreStyle == null)
        {
            initStyle();
        }
        logger.debug("overallScoreStyle = " + overallScoreStyle);
        return overallScoreStyle;
	}
	public void setOverallScoreStyle(String overallScoreStyle) {
		this.overallScoreStyle = overallScoreStyle;
	}
	
	//SPEED properties
	public Integer getSpeedScore() {
		return speedScore;
	}
	public void setSpeedScore(Integer speedScore) {
		this.speedScore = speedScore;
	}
	public String getSpeedScoreHistory() {
		setSpeedScoreHistory(createLineDef());
		return speedScoreHistory;
	}
	public void setSpeedScoreHistory(String speedScoreHistory) {
		this.speedScoreHistory = speedScoreHistory;
	}
	public String getSpeedScoreStyle() {
		return speedScoreStyle;
	}
	public void setSpeedScoreStyle(String speedScoreStyle) {
		this.speedScoreStyle = speedScoreStyle;
	}

	//DRIVING STYLE properties
	public Integer getDrivingScore() {
		return drivingScore;
	}
	public void setDrivingScore(Integer drivingScore) {
		this.drivingScore = drivingScore;
	}
	public String getDrivingScoreStyle() {
		return drivingScoreStyle;
	}
	public void setDrivingScoreStyle(String drivingScoreStyle) {
		this.drivingScoreStyle = drivingScoreStyle;
	}
	public String getDrivingScoreHistory() {
		
		setDrivingScoreHistory(createLineDef());
		return drivingScoreHistory;
	}
	public void setDrivingScoreHistory(String drivingScoreHistory) {
		this.drivingScoreHistory = drivingScoreHistory;
	}
	
	//SEAT BELT properties
	public Integer getSeatBeltScore() {
		return seatBeltScore;
	}
	public void setSeatBeltScore(Integer seatBeltScore) {
		this.seatBeltScore = seatBeltScore;
	}
	public String getSeatBeltScoreHistory() {
		setSeatBeltScoreHistory(createLineDef());
		return seatBeltScoreHistory;
	}
	public void setSeatBeltScoreHistory(String seatBeltScoreHistory) {
		this.seatBeltScoreHistory = seatBeltScoreHistory;
	}
	public String getSeatBeltScoreStyle() {
		return seatBeltScoreStyle;
	}
	public void setSeatBeltScoreStyle(String seatBeltScoreStyle) {
		this.seatBeltScoreStyle = seatBeltScoreStyle;
	}
	
	//COACHING properties
	public String getCoachingHistory() {
		setCoachingHistory(createLineDef());
		return coachingHistory;
	}
	public void setCoachingHistory(String coachingHistory) {
		this.coachingHistory = coachingHistory;
	}
	
	//DRIVER properties
	public String getDriverName() {
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

    //GRAPHIC DAO PROPERTIES
    public GraphicDAO getGraphicDAO()
    {
        return graphicDAO;
    }

    public void setGraphicDAO(GraphicDAO graphicDAO)
    {
        this.graphicDAO = graphicDAO;
    }
}
