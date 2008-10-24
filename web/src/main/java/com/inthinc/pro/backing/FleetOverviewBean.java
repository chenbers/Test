package com.inthinc.pro.backing;

import org.apache.log4j.Logger;
import com.inthinc.pro.backing.ui.*;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.dao.*;
import com.inthinc.pro.model.OverallScore;

public class FleetOverviewBean extends BaseBean
{
	private double overallScore;
	private String overallScoreStyle;
	private Duration duration = Duration.DAYS;
	private static final Logger logger = Logger.getLogger(TrendBean.class);
	private UserDAO userDao;
	private ScoreBox sb;
	
	public FleetOverviewBean()
	{
		setOverallScore(3.3d);  //get from DAO

	}

	public Double getOverallScore() {
		return overallScore;
	}

	public void setOverallScore(Double overallScore) {
		this.overallScore = overallScore;
		sb = new ScoreBox(this.overallScore, ScoreBoxSizes.LARGE);
		setOverallScoreStyle(sb.getScoreStyle());
	}

	public String getOverallScoreStyle() {
		return overallScoreStyle;
	}

	public void setOverallScoreStyle(String overallScoreStyle) {
		this.overallScoreStyle = overallScoreStyle;
	}
	
	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
		
		sb = new ScoreBox(this.overallScore, ScoreBoxSizes.LARGE);
		setOverallScoreStyle(sb.getScoreStyle());
	}

	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}
}
