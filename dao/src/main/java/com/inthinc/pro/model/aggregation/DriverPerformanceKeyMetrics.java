package com.inthinc.pro.model.aggregation;

import java.util.Arrays;
import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.TimeFrame;

public class DriverPerformanceKeyMetrics implements Comparable<DriverPerformanceKeyMetrics> {
    
    private String groupName;
    private String teamName;
    private String driverName;
    private String driverPosition;
    private Integer loginCount;
    private TimeFrame timeFrame;
    private Interval interval;
    private Integer totalMiles;
    private Integer overallScore;
    private Integer speedingScore;
    private Integer styleScore;
    private Integer seatbeltScore;
    private Integer timeFrameBasedOverallScore;
    private Integer timeFrameBasedSpeedingScore;
   	private Integer timeFrameBasedStyleScore;
    private Integer timeFrameBasedSeatbeltScore;
    private Integer idleViolationsCount;
    private Integer loIdleViolationsMinutes;
    private Integer hiIdleViolationsMinutes;
    private String color;
    static private Integer GREEN_MIN_LIMIT = 45;
    static private Integer YELLOW_MIN_LIMIT = 30;
    static private String WHITE = "white";
    static private String RED = "red";
    static private String YELLOW = "yellow";
    static private String GREEN = "green";
    
    private void init(String groupName, String teamName, String driverName, String driverPosition, Integer loginCount, TimeFrame timeFrame, Integer totalMiles,
            Integer overallScore, Integer speedingScore, Integer styleScore, Integer seatbeltScore, Integer idleViolationsCount, Integer loIdleViolationsMinutes, Integer hiIdleViolationsMinutes, Interval interval, String color){
        this.groupName = groupName;
        this.teamName = teamName;
        this.driverName = driverName;
        this.driverPosition = driverPosition;
        this.loginCount = loginCount;
        this.timeFrame = timeFrame;
        this.setInterval(interval);
        this.totalMiles = totalMiles;
        this.overallScore = overallScore;
        this.speedingScore = speedingScore;
        this.styleScore = styleScore;
        this.seatbeltScore = seatbeltScore;
        this.idleViolationsCount = idleViolationsCount;
        this.loIdleViolationsMinutes = loIdleViolationsMinutes;
        this.hiIdleViolationsMinutes = hiIdleViolationsMinutes;
        this.color = color;
    }
    public DriverPerformanceKeyMetrics()
    {
        
    }
    public DriverPerformanceKeyMetrics(String groupName, String teamName, String driverName, String driverPosition, Integer loginCount, Interval interval, Integer totalMiles,
            Integer overallScore, Integer speedingScore, Integer styleScore, Integer seatbeltScore, Integer idleViolationsCount, Integer loIdleViolationsMinutes, Integer hiIdleViolationsMinutes, String color) {
        super();
        init(groupName, teamName, driverName, driverPosition, loginCount, timeFrame, totalMiles,
                overallScore, speedingScore, styleScore, seatbeltScore, idleViolationsCount, loIdleViolationsMinutes, hiIdleViolationsMinutes, interval, color);
    }
    public DriverPerformanceKeyMetrics(String groupName, String teamName, String driverName, String driverPosition, Integer loginCount, TimeFrame timeFrame, Integer totalMiles,
            Integer overallScore, Integer speedingScore, Integer styleScore, Integer seatbeltScore, Integer idleViolationsCount, Integer loIdleViolationsMinutes, Integer hiIdleViolationsMinutes) {
        super();
        init(groupName, teamName, driverName, driverPosition, loginCount, timeFrame, totalMiles,
                overallScore, speedingScore, styleScore, seatbeltScore, idleViolationsCount, loIdleViolationsMinutes, hiIdleViolationsMinutes, null, null);
    }
    
    public double getIdleViolationsPerDay(){
        return idleViolationsCount.doubleValue() / (DateUtil.differenceInDays(timeFrame, interval));
    }
    public String getIdlingColor() {
        String color = WHITE;
        double greenMax = 1.0/7;
        double yellowMax = 4.0/7;
        double idleViolationsPerDay = getIdleViolationsPerDay();
        if(totalMiles!=null && totalMiles > 0){
            if(idleViolationsCount != null){
                if(idleViolationsPerDay < greenMax)
                    color =  GREEN;
                else if(idleViolationsPerDay < yellowMax)
                    color = YELLOW;
                else
                    color = RED;
            }
        }
        return color;
    }
    private String getScoreColor(Integer scoreToTest){
        String color = WHITE;
        if(totalMiles!=null && totalMiles > 0){
            if(scoreToTest == null || scoreToTest < 0)
                return color;
            else if(scoreToTest > GREEN_MIN_LIMIT)
                color = GREEN;
            else if(scoreToTest > YELLOW_MIN_LIMIT)
                color = YELLOW;
            else if(scoreToTest <= YELLOW_MIN_LIMIT)
                color = RED;
        }
        return color;
    }
    public String getOverallScoreColor(){
        return getScoreColor(overallScore);
    }
    public String getSpeedingScoreColor(){
        return getScoreColor(speedingScore);
    }
    public String getStyleScoreColor(){
        return getScoreColor(styleScore);
    }
    public String getSeatbeltScoreColor(){
        return getScoreColor(seatbeltScore);
    }
    public String getDriverColor(){
        String color = "white";
        if(totalMiles!=null && totalMiles > 0){
            color = "green";
            List<String> otherColors = Arrays.asList(getOverallScoreColor(), getSpeedingScoreColor(), getStyleScoreColor(), getSeatbeltScoreColor());
            if(otherColors.contains(RED))
                color = RED;
            else if(otherColors.contains(YELLOW))
                color = YELLOW;
        }
        return color;
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getTeamName() {
        return teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    public String getDriverName() {
        return driverName;
    }
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    public String getDriverPosition() {
        return driverPosition;
    }
    public void setDriverPosition(String driverPosition) {
        this.driverPosition = driverPosition;
    }
    public Integer getLoginCount() {
        return loginCount;
    }
    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }
    public Integer getTotalMiles() {
        return totalMiles;
    }
    public void setTotalMiles(Integer totalMiles) {
        this.totalMiles = totalMiles;
    }
    public Integer getOverallScore() {
        return overallScore;
    }
    public void setOverallScore(Integer overallScore) {
        this.overallScore = overallScore;
    }
    public Integer getSpeedingScore() {
        return speedingScore;
    }
    public void setSpeedingScore(Integer speedingScore) {
        this.speedingScore = speedingScore;
    }
    public Integer getStyleScore() {
        return styleScore;
    }
    public void setStyleScore(Integer styleScore) {
        this.styleScore = styleScore;
    }
    public Integer getSeatbeltScore() {
        return seatbeltScore;
    }
    public void setSeatbeltScore(Integer seatbeltScore) {
        this.seatbeltScore = seatbeltScore;
    }
    public Integer getTimeFrameBasedOverallScore(){
		if(totalMiles != null && totalMiles > 0)
			this.timeFrameBasedOverallScore = this.getOverallScore();
		else
			this.timeFrameBasedOverallScore = -1;
		return timeFrameBasedOverallScore;
	}
	public Integer getTimeFrameBasedSpeedingScore(){
		if(totalMiles != null && totalMiles > 0)
			this.timeFrameBasedSpeedingScore = this.getSpeedingScore();
		else
			this.timeFrameBasedSpeedingScore = -1;
		
		return timeFrameBasedSpeedingScore;
	}
	public Integer getTimeFrameBasedStyleScore(){
		if(totalMiles != null && totalMiles > 0)
			this.timeFrameBasedStyleScore = this.getStyleScore();
		else
			this.timeFrameBasedStyleScore = -1;
		
		return timeFrameBasedStyleScore;
	}
	public Integer getTimeFrameBasedSeatbeltScore(){
		if(totalMiles != null && totalMiles > 0)
			this.timeFrameBasedSeatbeltScore = this.getSeatbeltScore();
		else
			this.timeFrameBasedSeatbeltScore = -1;		
		return timeFrameBasedSeatbeltScore;
	}
	public Integer getLoIdleViolationsMinutes() {
        return loIdleViolationsMinutes;
    }
    public void setLoIdleViolationsMinutes(Integer loIdleViolationsMinutes) {
        this.loIdleViolationsMinutes = loIdleViolationsMinutes;
    }
    public Integer getHiIdleViolationsMinutes() {
        return hiIdleViolationsMinutes;
    }
    public void setHiIdleViolationsMinutes(Integer hiIdleViolationsMinutes) {
        this.hiIdleViolationsMinutes = hiIdleViolationsMinutes;
    }
    public TimeFrame getTimeFrame() {
        return timeFrame;
    }
    public void setTimeFrame(TimeFrame timeFrame) {
        this.timeFrame = timeFrame;
    }
    public Integer getIdleViolationsCount() {
        return idleViolationsCount;
    }
    public void setIdleViolationsCount(Integer idleViolationsCount) {
        this.idleViolationsCount = idleViolationsCount;
    }
    @Override
    public int compareTo(DriverPerformanceKeyMetrics o) {
        // sort order: division, team, driver
        int cmp = groupName.compareToIgnoreCase(o.getGroupName());
        if (cmp == 0)
            cmp = teamName.compareToIgnoreCase(o.getTeamName());
        if (cmp == 0)
            cmp = driverName.compareToIgnoreCase(o.getDriverName());
        return cmp;
    }
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Interval getInterval() {
		return interval;
	}
	public void setInterval(Interval interval) {
		this.interval = interval;
	}
}
