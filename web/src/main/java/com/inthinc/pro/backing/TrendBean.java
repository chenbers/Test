package com.inthinc.pro.backing;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.User;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.util.GraphicUtil;

public class TrendBean extends BaseBean {

	private static final Logger logger = Logger.getLogger(TrendBean.class);
	
	private String lineDef;	
    private Duration duration = Duration.DAYS;
	private List <ScoreableEntity> scoreableEntities = new ArrayList<ScoreableEntity>();
	
	private OverviewBean ob = new OverviewBean();
		
    public TrendBean()
	{
		super();
		logger.debug("creating trend bean"); 
	}    

	public String getLineDef() {		
		lineDef = createLineDef();
		logger.debug("returned string: " + lineDef);
		return lineDef;
	}

	public void setLineDef(String lineDef) {
		this.lineDef = lineDef;
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
		if (		this.duration.toString().equalsIgnoreCase(Duration.THREE.toString()) ) {
			sb.append(GraphicUtil.createFakeXYData(3));		
		}
		else if (	this.duration.toString().equalsIgnoreCase(Duration.SIX.toString()) ) {
			sb.append(GraphicUtil.createFakeXYData(6));	
		}
		else if (	this.duration.toString().equalsIgnoreCase(Duration.TWELVE.toString()) ) {
			sb.append(GraphicUtil.createFakeXYData(12));	
		}
		else {
			sb.append(GraphicUtil.createFakeXYData(30));	
		}
		
		sb.append("</dataset>");
		
//	Test two
		sb.append("<dataset seriesName=\'\' color=\'");
		sb.append((GraphicUtil.entityColorKey.get(1)).substring(1));
		sb.append("\'>");
		if (		this.duration.toString().equalsIgnoreCase(Duration.THREE.toString()) ) {
			sb.append(GraphicUtil.createFakeXYData(3));		
		}
		else if (	this.duration.toString().equalsIgnoreCase(Duration.SIX.toString()) ) {
			sb.append(GraphicUtil.createFakeXYData(6));	
		}
		else if (	this.duration.toString().equalsIgnoreCase(Duration.TWELVE.toString()) ) {
			sb.append(GraphicUtil.createFakeXYData(12));	
		}
		else {
			sb.append(GraphicUtil.createFakeXYData(30));	
		}
		
		sb.append("</dataset>");
		
//	Test three
		sb.append("<dataset seriesName=\'\' color=\'");
		sb.append((GraphicUtil.entityColorKey.get(2)).substring(1));
		sb.append("\'>");
		if (		this.duration.toString().equalsIgnoreCase(Duration.THREE.toString()) ) {
			sb.append(GraphicUtil.createFakeXYData(3));		
		}
		else if (	this.duration.toString().equalsIgnoreCase(Duration.SIX.toString()) ) {
			sb.append(GraphicUtil.createFakeXYData(6));	
		}
		else if (	this.duration.toString().equalsIgnoreCase(Duration.TWELVE.toString()) ) {
			sb.append(GraphicUtil.createFakeXYData(12));	
		}
		else {
			sb.append(GraphicUtil.createFakeXYData(30));	
		}
		
		sb.append("</dataset>");
		
//	Test four
		sb.append("<dataset seriesName=\'\' color=\'");
		sb.append((GraphicUtil.entityColorKey.get(3)).substring(1));
		sb.append("\'>");
		if (		this.duration.toString().equalsIgnoreCase(Duration.THREE.toString()) ) {
			sb.append(GraphicUtil.createFakeXYData(3));		
		}
		else if (	this.duration.toString().equalsIgnoreCase(Duration.SIX.toString()) ) {
			sb.append(GraphicUtil.createFakeXYData(6));	
		}
		else if (	this.duration.toString().equalsIgnoreCase(Duration.TWELVE.toString()) ) {
			sb.append(GraphicUtil.createFakeXYData(12));	
		}
		else {
			sb.append(GraphicUtil.createFakeXYData(30));	
		}
		
		sb.append("</dataset>");
		
//	Test five
		sb.append("<dataset seriesName=\'\' color=\'");
		sb.append((GraphicUtil.entityColorKey.get(4)).substring(1));
		sb.append("\'>");
		if (		this.duration.toString().equalsIgnoreCase(Duration.THREE.toString()) ) {
			sb.append(GraphicUtil.createFakeXYData(3));		
		}
		else if (	this.duration.toString().equalsIgnoreCase(Duration.SIX.toString()) ) {
			sb.append(GraphicUtil.createFakeXYData(6));	
		}
		else if (	this.duration.toString().equalsIgnoreCase(Duration.TWELVE.toString()) ) {
			sb.append(GraphicUtil.createFakeXYData(12));	
		}
		else {
			sb.append(GraphicUtil.createFakeXYData(30));	
		}		
		sb.append("</dataset>");					
		
		sb.append("</chart>");
		
		return sb.toString();
	}

	public List<ScoreableEntity> getScoreableEntities() {		
		User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		logger.debug("getting scoreable entities, user is: " + u.getUsername());
				
		//Keep it clean...		
		if ( scoreableEntities.size() > 0 ) {
			scoreableEntities.clear();
		}
		
		ScoreableEntity se = new ScoreableEntity();
		//The fake scores are strings with one decimal place
		se.setEntityID(0);
		se.setIdentifier("New England");
		se.setScore("0.9");
		ob.setOverallScore(se.getScore());
		se.setStyle(ob.getOverallScoreStyleSM());
		se.setColorKey(GraphicUtil.entityColorKey.get(0));
		scoreableEntities.add(se);
		
		se = new ScoreableEntity();
		se.setEntityID(1);
		se.setIdentifier("South");
		se.setScore("1.8");
		ob.setOverallScore(se.getScore());
		se.setStyle(ob.getOverallScoreStyleSM());
		se.setColorKey(GraphicUtil.entityColorKey.get(1));
		scoreableEntities.add(se);
		
		se = new ScoreableEntity();
		se.setEntityID(2);
		se.setIdentifier("Lakes");
		se.setScore("2.9");
		ob.setOverallScore(se.getScore());
		se.setStyle(ob.getOverallScoreStyleSM());
		se.setColorKey(GraphicUtil.entityColorKey.get(2));
		scoreableEntities.add(se);
		
		se = new ScoreableEntity();
		se.setEntityID(3);
		se.setIdentifier("Midwest");
		se.setScore("3.9");
		ob.setOverallScore(se.getScore());
		se.setStyle(ob.getOverallScoreStyleSM());
		se.setColorKey(GraphicUtil.entityColorKey.get(3));
		scoreableEntities.add(se);

		se = new ScoreableEntity();
		se.setEntityID(4);
		se.setIdentifier("West Coast");
		se.setScore("4.6");
		ob.setOverallScore(se.getScore());
		se.setStyle(ob.getOverallScoreStyleSM());
		se.setColorKey(GraphicUtil.entityColorKey.get(4));
		scoreableEntities.add(se);
		
		return scoreableEntities;
	}

	public void setScoreableEntities(List<ScoreableEntity> scoreableEntities) {
		this.scoreableEntities = scoreableEntities;
	}	
	
	public void changeDurationAction()
    {        
		logger.debug("changing duration " + duration.toString());
    }

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}	
}
