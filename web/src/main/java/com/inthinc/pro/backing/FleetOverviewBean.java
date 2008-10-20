package com.inthinc.pro.backing;

import org.apache.log4j.Logger;
import com.inthinc.pro.backing.ui.*;
import java.util.Random;
import com.inthinc.pro.model.Duration;

public class FleetOverviewBean extends BaseBean
{
	private double overallScore;
	private String overallScoreStyle;
	private Duration duration = Duration.DAYS;
	
	public FleetOverviewBean()
	{
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
}
