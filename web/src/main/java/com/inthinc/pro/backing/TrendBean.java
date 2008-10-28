package com.inthinc.pro.backing;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.User;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.dao.GraphicDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.wrapper.ScoreableEntityPkg;

public class TrendBean extends BaseBean {

	private static final Logger logger = Logger.getLogger(TrendBean.class);
	
	private GraphicDAO graphicDAO;
	private NavigationBean navigation;
	
	private String lineDef;	

	private List <ScoreableEntityPkg> scoreableEntities = new ArrayList<ScoreableEntityPkg>();
	
	//The following five may need to be placed in BaseBean
    private Duration duration = Duration.DAYS;
	private String styleClass30Days = "on";
	private String styleClass3Months = "";
	private String styleClass6Months = "";
	private String styleClass12Months = "";
	private boolean pageChange = false;
	
	private String goTo = "";
		
    public TrendBean()
	{
		super();
		logger.debug("creating trend bean"); 
	}    

	public String getLineDef() {		
		lineDef = createLineDef();
//		logger.debug("returned string: " + lineDef);
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
		
		//Handle navigation
		logger.debug("location is: " + navigation.getLocation());
		if (	this.pageChange ) {
			if (         this.navigation.getLocation().equalsIgnoreCase("home") ) {
			    this.navigation.setLocation("region");
			} else if (  this.navigation.getLocation().equalsIgnoreCase("region") ) {
			    this.navigation.setLocation("team");
			}
        }
		
		//Fetch, qualifier is who is logged-in, where in the system, 
		//date from, date to
		List<ScoreableEntity> s = null;
		try {
		    Integer endDate = DateUtil.getTodaysDate();
		    Integer startDate = DateUtil.getDaysBackDate(endDate, duration.getNumberOfDays());
		    
		    // TODO: This is not correct.  getUser().getGroupID() needs to be changed to the current group in the navigation
logger.debug("getting scores for groupID: " + getUser().getGroupID());
		    
			s = graphicDAO.getScores(getUser().getGroupID(), startDate, endDate);
		} catch (Exception e) {
			logger.debug("graphicDao error: " + e.getMessage());
		}		
		
		ScoreBox sb = new ScoreBox(0,ScoreBoxSizes.SMALL);
		if (		navigation.getLocation().equalsIgnoreCase("home") ) {	
		    logger.debug("loading home");
			int cnt = 0;
			for (ScoreableEntity score : s)
			{
				ScoreableEntityPkg se = new ScoreableEntityPkg();
				se.setSe(score);
				sb.setScore(score.getScore());
				se.setStyle(sb.getScoreStyle());
				se.setColorKey(GraphicUtil.entityColorKey.get(cnt++));
				se.setGoTo("go_region");
				scoreableEntities.add(se);		 				
			}
						
		} else if (	navigation.getLocation().equalsIgnoreCase("region") ) {
		    logger.debug("loading region");
		    ScoreableEntityPkg se = new ScoreableEntityPkg();
		    ScoreableEntity score = new ScoreableEntity();
		    
		    score.setEntityID(0);
		    score.setIdentifier("Sacramento/Bay Area");
		    score.setScore(48);
		    
            se.setSe(score);
            sb.setScore(score.getScore());
            se.setStyle(sb.getScoreStyle());
            se.setColorKey(GraphicUtil.entityColorKey.get(0));
            se.setGoTo("go_team");
            scoreableEntities.add(se);  
            
            se = new ScoreableEntityPkg();
            score = new ScoreableEntity();
            
            score.setEntityID(1);
            score.setIdentifier("Salt Lake");
            score.setScore(50);
            
            se.setSe(score);
            sb.setScore(score.getScore());
            se.setStyle(sb.getScoreStyle());
            se.setColorKey(GraphicUtil.entityColorKey.get(1));
            se.setGoTo("go_team");
            scoreableEntities.add(se);
            
            se = new ScoreableEntityPkg();
            score = new ScoreableEntity();
            
            score.setEntityID(2);
            score.setIdentifier("Vegas");
            score.setScore(39);
                        
            se.setSe(score);
            sb.setScore(score.getScore());
            se.setStyle(sb.getScoreStyle());
            se.setColorKey(GraphicUtil.entityColorKey.get(2));
            se.setGoTo("go_team");
            scoreableEntities.add(se);            
		} 

		logger.debug("location is: " + navigation.getLocation());
		this.pageChange = true;
		
		return scoreableEntities;
	}

	public void setScoreableEntities(List<ScoreableEntityPkg> scoreableEntities) {
		this.scoreableEntities = scoreableEntities;
	}	

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
	    pageChange = false;
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
	    logger.debug("in getgoto");
		return goTo;
	}

	public void setGoTo(String goTo) {
	    logger.debug("in setgoto");
		this.goTo = goTo;
	}

	public GraphicDAO getGraphicDAO() {
		return graphicDAO;
	}

	public void setGraphicDAO(GraphicDAO graphicDAO) {
		this.graphicDAO = graphicDAO;
	}

    public NavigationBean getNavigation()
    {
        return navigation;
    }

    public void setNavigation(NavigationBean navigation)
    {
        this.navigation = navigation;
    }
}
