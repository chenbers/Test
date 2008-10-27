package com.inthinc.pro.backing;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.model.Duration;

public class DriverBean extends BaseBean
{
	private static final Logger logger = Logger.getLogger(DriverBean.class);
	
	// Get scores from DAO instead
	
	private String driverName = "Amy Johnson";
	private String duration = "500";
	
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
	
	//OVERALL SCORE properties
	public Integer getOverallScore() {

	    return overallScore;
	}
	
	public void setOverallScore(Integer overallScore) 
	{
		this.overallScoreStyle = new ScoreBox(overallScore, ScoreBoxSizes.MEDIUM).getScoreStyle();
		this.overallScore = overallScore;
	}
	public String getOverallScoreHistory() {
		
		setOverallScoreHistory(createLineDef()); // Get Chart data from DAO
		return overallScoreHistory;
	}
	public void setOverallScoreHistory(String overallScoreHistory) {
		this.overallScoreHistory = overallScoreHistory;
	}
	public String getOverallScoreStyle() {
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

	//DURATION PROPERTIES
    public String getDuration()
    {
        return duration;
    }

    public void setDuration(String duration)
    {
        Integer score = GraphicUtil.getRandomScore(); //Get score from DAO
        setOverallScore(score);
        this.duration = duration;
    }
}
