package com.inthinc.pro.backing;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.User;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.util.GraphicUtil;

public class TrendBean extends BaseBean {

	private static final Logger logger = Logger.getLogger(TrendBean.class);
	
	private String lineDef;	

	private List <ScoreableEntity> scoreableEntities = new ArrayList<ScoreableEntity>();
	
	//The following five may need to be placed in BaseBean
    private Duration duration = Duration.DAYS;
	private String styleClass30Days = "on";
	private String styleClass3Months = "";
	private String styleClass6Months = "";
	private String styleClass12Months = "";
	
	private String goTo = "go_region";
		
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
		ScoreBox sb = new ScoreBox(0.d,ScoreBoxSizes.SMALL);
		//The fake scores are strings with one decimal place
		se.setEntityID(0);
		se.setIdentifier("New England");
		se.setScore("0.9");
		sb.setScore(0.9d);
		se.setStyle(sb.getScoreStyle());
		se.setColorKey(GraphicUtil.entityColorKey.get(0));
		se.setGoTo(goTo);
		scoreableEntities.add(se);
		
		se = new ScoreableEntity();
		se.setEntityID(1);
		se.setIdentifier("South");
		se.setScore("1.8");		
		sb.setScore(1.8d);
		se.setStyle(sb.getScoreStyle());
		se.setColorKey(GraphicUtil.entityColorKey.get(1));
		se.setGoTo(goTo);
		scoreableEntities.add(se);
		
		se = new ScoreableEntity();
		se.setEntityID(2);
		se.setIdentifier("Lakes");
		se.setScore("2.9");
		sb.setScore(2.9d);
		se.setStyle(sb.getScoreStyle());
		se.setColorKey(GraphicUtil.entityColorKey.get(2));
		se.setGoTo(goTo);
		scoreableEntities.add(se);
		
		se = new ScoreableEntity();
		se.setEntityID(3);
		se.setIdentifier("Midwest");
		se.setScore("3.9");
		sb.setScore(3.9d);
		se.setStyle(sb.getScoreStyle());
		se.setColorKey(GraphicUtil.entityColorKey.get(3));
		se.setGoTo(goTo);
		scoreableEntities.add(se);

		se = new ScoreableEntity();
		se.setEntityID(4);
		se.setIdentifier("West Coast");
		se.setScore("4.6");
		sb.setScore(4.6d);
		se.setStyle(sb.getScoreStyle());
		se.setColorKey(GraphicUtil.entityColorKey.get(4));
		se.setGoTo(goTo);
		scoreableEntities.add(se);
		
		return scoreableEntities;
	}

	public void setScoreableEntities(List<ScoreableEntity> scoreableEntities) {
		this.scoreableEntities = scoreableEntities;
	}	

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public String getStyleClass30Days() {
		reset();
		if ( this.duration.toString().equalsIgnoreCase(Duration.DAYS.toString()) ) {
			styleClass30Days = "on";
		}
	
		return styleClass30Days;
	}

	public void setStyleClass30Days(String styleClass30Days) {
		this.styleClass30Days = styleClass30Days;
	}

	public String getStyleClass3Months() {
		reset();
		if ( this.duration.toString().equalsIgnoreCase(Duration.THREE.toString()) ) {
			styleClass3Months = "on";
		}
	
		return styleClass3Months;
	}

	public void setStyleClass3Months(String styleClass3Months) {
		this.styleClass3Months = styleClass3Months;
	}

	public String getStyleClass6Months() {
		reset();
		if ( this.duration.toString().equalsIgnoreCase(Duration.SIX.toString()) ) {
			styleClass6Months = "on";
		}
	
		return styleClass6Months;
	}

	public void setStyleClass6Months(String styleClass6Months) {
		this.styleClass6Months = styleClass6Months;
	}

	public String getStyleClass12Months() {
		reset();
		if ( this.duration.toString().equalsIgnoreCase(Duration.TWELVE.toString()) ) {
			styleClass12Months = "on";
		}
	
		return styleClass12Months;
	}

	public void setStyleClass12Months(String styleClass12Months) {
		this.styleClass12Months = styleClass12Months;
	}
	
	private void reset() {
		this.styleClass30Days = "";
		this.styleClass3Months = "";
		this.styleClass6Months = "";
		this.styleClass12Months = "";
	}

	public String getGoTo() {
		return goTo;
	}

	public void setGoTo(String goTo) {
		this.goTo = goTo;
	}
}
