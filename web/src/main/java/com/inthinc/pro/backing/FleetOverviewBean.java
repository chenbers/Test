package com.inthinc.pro.backing;

import org.apache.log4j.Logger;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.User;
import com.inthinc.pro.backing.ui.*;
import java.util.Random;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.dao.*;

public class FleetOverviewBean extends BaseBean
{
	private double overallScore;
	private String overallScoreStyle;
	private Duration duration = Duration.DAYS;
	private static final Logger logger = Logger.getLogger(TrendBean.class);
	private UserDAO userDao;
	
	public FleetOverviewBean()
	{
		User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logger.debug("getting overall score, user is: " + u.getUsername());
		
		setOverallScore(3.1); //Get score from DAO here.
		ScoreBox sb = new ScoreBox(getOverallScore(), ScoreBoxSizes.LARGE);
		setOverallScoreStyle(sb.getScoreStyle());
	}

	public Double getOverallScore() {
		return overallScore;
	}

	public void setOverallScore(Double overallScore) {
		this.overallScore = overallScore;
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
		
		com.inthinc.pro.model.User user = this.getUser();
        com.inthinc.pro.model.User newUser = userDao.findByEmail(user.getEmail());
		
//		com.inthinc.pro.model.User newUser = userDao.findByEmail("rabyma@gmail.com");
		
		logger.debug("DAO Test " + newUser.getUsername());
		
		
		// TODO recalculate score. user changed time range
		Random r = new Random();
		Double d = r.nextDouble();
		
		d = d * 5.0;
		d = this.roundDouble(d, 1);
		this.setOverallScore(d);
		
		ScoreBox sb = new ScoreBox(this.overallScore, ScoreBoxSizes.LARGE);
		setOverallScoreStyle(sb.getScoreStyle());
	}

	   public static final double roundDouble(double d, int places) {
	        return Math.round(d * Math.pow(10, (double) places)) / Math.pow(10,
	            (double) places);
	    }

	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}
}
