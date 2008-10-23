package com.inthinc.pro.backing;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.model.Duration;

public class DriverBean extends BaseBean
{
	private static final Logger logger = Logger.getLogger(BreakdownBean.class);
	
	private String driverName = "Amy Johnson";
	private Duration duration = Duration.THREE;
	
	private String overallScore = "2.6";
	private String overallScoreHistory;
	private String overallScoreStyle = "score_med_2";
	
	private String speedScore = "3.3";
	private String speedScoreHistory;
	private String speedScoreStyle = "score_med_3";

	private String drivingScore = "1.5";
	private String drivingScoreHistory;
	private String drivingScoreStyle = "score_med_1";
	
	private String seatBeltScore = "4.7";
	private String seatBeltScoreHistory;
	private String seatBeltScoreStyle = "score_med_4";
	
	private String coachingHistory;
	

	public DriverBean()
	{
		overallScoreHistory = createLineDef();
		
	}
	
	private String createLineDef() {
		StringBuffer sb = new StringBuffer();
		//Control parameters
		sb.append(GraphicUtil.getXYControlParameters());
		
		//X-axis
		sb.append("<categories>");
		sb.append(GraphicUtil.createMonthsString(duration));		
		sb.append("</categories>");
		
		//Y-axii
		
//	Test one
		sb.append("<dataset seriesName=\'\' color=\'");
		sb.append((GraphicUtil.entityColorKey.get(0)).substring(1));
		sb.append("\'>");
		sb.append(GraphicUtil.createFakeXYData(3));		
		sb.append("</dataset>");
		sb.append("</chart>");
		return sb.toString();
	}
	
	//OVERALL SCORE properties
	public String getOverallScore() {
		return overallScore;
	}
	public void setOverallScore(String overallScore) {
		this.overallScore = overallScore;
	}
	public String getOverallScoreHistory() {
		
		
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
	public String getSpeedScore() {
		return speedScore;
	}
	public void setSpeedScore(String speedScore) {
		this.speedScore = speedScore;
	}
	public String getSpeedScoreHistory() {
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
	public String getDrivingScore() {
		return drivingScore;
	}
	public void setDrivingScore(String drivingScore) {
		this.drivingScore = drivingScore;
	}
	public String getDrivingScoreStyle() {
		return drivingScoreStyle;
	}
	public void setDrivingScoreStyle(String drivingScoreStyle) {
		this.drivingScoreStyle = drivingScoreStyle;
	}
	public String getDrivingScoreHistory() {
		return drivingScoreHistory;
	}
	public void setDrivingScoreHistory(String drivingScoreHistory) {
		this.drivingScoreHistory = drivingScoreHistory;
	}
	
	//SEAT BELT properties
	public String getSeatBeltScore() {
		return seatBeltScore;
	}
	public void setSeatBeltScore(String seatBeltScore) {
		this.seatBeltScore = seatBeltScore;
	}
	public String getSeatBeltScoreHistory() {
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
}
