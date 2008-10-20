package com.inthinc.pro.backing;

import java.util.Random;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.Duration;

public class OverviewBean extends BaseBean
{
	// Color boxes define in PRD
	//0-1   red
	//1.1-2 orange
	//2.1-3 yellow
	//3.1-4 blue
	//4.1-5 green
	
	private static final Logger logger = Logger.getLogger(OverviewBean.class);
	
	private Duration duration = Duration.DAYS;
	private String overallScore;
	private String overallScoreStyleLG;
	private String overallScoreStyleMD;
	private String overallScoreStyleSM;
		
	public OverviewBean()
	{
		logger.debug("Overview Bean constructor");	
	}

	public String getOverallScore() {
		if(overallScore == null)
		{
			setOverallScore("0.0");
		}
		
		return overallScore;
	}

	public void setOverallScore(String overallScore) {
		this.overallScore = overallScore;
	}
	public String getOverallScoreStyleLG() {
		
		//Determine CSS Style
		
		if(		this.getParsedScore() <= 1.0) {
			setOverallScoreStyleLG("score_lg_1");
		}
		else if(this.getParsedScore() >  1.0 && this.getParsedScore() <= 1.9) {
			setOverallScoreStyleLG("score_lg_2");
		}
		else if(this.getParsedScore() >= 2.0 && this.getParsedScore() <= 2.9) {
			setOverallScoreStyleLG("score_lg_3");
		}
		else if(this.getParsedScore() >= 3.0 && this.getParsedScore() <= 3.9) {
			setOverallScoreStyleLG("score_lg_4");
		}
		else
			setOverallScoreStyleLG("score_lg_5");
		
		return overallScoreStyleLG;
	}

	public void setOverallScoreStyleLG(String overallScoreStyleLG) {
		this.overallScoreStyleLG = overallScoreStyleLG;
	}
	
	public String getOverallScoreStyleMD() {
		//Determine CSS Style
		if(		this.getParsedScore() <= 1.0){
			setOverallScoreStyleMD("score_med_1");
		}
		else if(this.getParsedScore()  > 1.0 && this.getParsedScore() <= 1.9) {
			setOverallScoreStyleMD("score_med_2");
		}
		else if(this.getParsedScore() >= 2.0 && this.getParsedScore() <= 2.9) {
			setOverallScoreStyleMD("score_med_3");
		}
		else if(this.getParsedScore() >= 3.0 && this.getParsedScore() <= 3.9) {
			setOverallScoreStyleMD("score_med_4");
		}
		else {
			setOverallScoreStyleMD("score_med_5");
		}
		
		return overallScoreStyleMD;
	}

	public void setOverallScoreStyleMD(String overallScoreStyleMD) {
		this.overallScoreStyleMD = overallScoreStyleMD;
	}

	public String getOverallScoreStyleSM() {
		//Determine CSS Style
		if(		this.getParsedScore() <= 1.0){
			setOverallScoreStyleSM("score_sm_1");
		}
		else if(this.getParsedScore()  > 1.0 && this.getParsedScore() <= 1.9) {
			setOverallScoreStyleSM("score_sm_2");
		}
		else if(this.getParsedScore() >= 2.0 && this.getParsedScore() <= 2.9) {
			setOverallScoreStyleSM("score_sm_3");
		}
		else if(this.getParsedScore() >= 3.0 && this.getParsedScore() <= 3.9) {
			setOverallScoreStyleSM("score_sm_4");
		}
		else {
			setOverallScoreStyleSM("score_sm_5");
		}
		
		return overallScoreStyleSM;
	}
	
	public void changeDurationAction()
    {        
		logger.debug("changing duration " + duration.toString());
    }

	public void setOverallScoreStyleSM(String overallScoreStyleSM) {
		this.overallScoreStyleSM = overallScoreStyleSM;
	}
		
	private double getParsedScore() {
		return Double.parseDouble(getOverallScore());
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
		this.setOverallScore(d.toString());
	}
	
    public static final double roundDouble(double d, int places) {
        return Math.round(d * Math.pow(10, (double) places)) / Math.pow(10,
            (double) places);
    }
}
