package com.inthinc.pro.backing;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.User;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.dao.GraphicDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.wrapper.ScoreableEntityPkg;

public class TrendBean extends BaseBean {

	private static final Logger logger = Logger.getLogger(TrendBean.class);
	
	private GraphicDAO graphicDAO;
	
	private String lineDef;	

	private List <ScoreableEntityPkg> scoreableEntities = new ArrayList<ScoreableEntityPkg>();
	
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

	public List<ScoreableEntityPkg> getScoreableEntities() {		
		User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();		
		logger.debug("getting scoreable entities, user is: " + u.getUsername());
		
		//Keep it clean
		if ( scoreableEntities.size() > 0 ) {
			scoreableEntities.clear();
		}
		
		//Fetch, qualifier is who is logged-in, where in the system, 
		//date from, date to
		List s = null;
		try {						
			s = graphicDAO.getScores(new Integer(1),new Integer(1),new Integer(1),new Integer(1));
		} catch (Exception e) {
			logger.debug("graphicDao error: " + e.getMessage());
		}		
		
		ScoreableEntityPkg se = new ScoreableEntityPkg();
		ScoreBox sb = new ScoreBox(0.d,ScoreBoxSizes.SMALL);
		
		se.setSe((ScoreableEntity)s.get(0));
		sb.setScore(0.9d);
		se.setStyle(sb.getScoreStyle());
		se.setColorKey(GraphicUtil.entityColorKey.get(0));
		se.setGoTo(goTo);
		scoreableEntities.add(se);
		
		se = new ScoreableEntityPkg();
		se.setSe((ScoreableEntity)s.get(1));
		sb.setScore(1.8d);
		se.setStyle(sb.getScoreStyle());
		se.setColorKey(GraphicUtil.entityColorKey.get(1));
		se.setGoTo(goTo);
		scoreableEntities.add(se);
		
		se = new ScoreableEntityPkg();
		se.setSe((ScoreableEntity)s.get(2));
		sb.setScore(2.9d);
		se.setStyle(sb.getScoreStyle());
		se.setColorKey(GraphicUtil.entityColorKey.get(2));
		se.setGoTo(goTo);
		scoreableEntities.add(se);

		se = new ScoreableEntityPkg();
		se.setSe((ScoreableEntity)s.get(3));
		sb.setScore(3.9d);
		se.setStyle(sb.getScoreStyle());
		se.setColorKey(GraphicUtil.entityColorKey.get(3));
		se.setGoTo(goTo);
		scoreableEntities.add(se);

		se = new ScoreableEntityPkg();
		se.setSe((ScoreableEntity)s.get(4));
		sb.setScore(4.6d);
		se.setStyle(sb.getScoreStyle());
		se.setColorKey(GraphicUtil.entityColorKey.get(4));
		se.setGoTo(goTo);
		scoreableEntities.add(se);
		
		return scoreableEntities;
	}

	public void setScoreableEntities(List<ScoreableEntityPkg> scoreableEntities) {
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

	public GraphicDAO getGraphicDAO() {
		return graphicDAO;
	}

	public void setGraphicDAO(GraphicDAO graphicDAO) {
		this.graphicDAO = graphicDAO;
	}
}
