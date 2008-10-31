package com.inthinc.pro.backing;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.User;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.wrapper.ScoreableEntityPkg;

public class TrendBean extends BaseBean {

	private static final Logger logger = Logger.getLogger(TrendBean.class);
	
	private ScoreDAO graphicDAO;
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
	
//	private String goTo = "";
		
    public TrendBean()
	{
		super();
		logger.debug("creating trend bean");
	}    

	public String getLineDef() {	
		lineDef = createLineDef();
		return lineDef;
	}

	public void setLineDef(String lineDef) {
		this.lineDef = lineDef;
	}
	
	private String createLineDef() {
        StringBuffer sb = new StringBuffer();
        
        //Control parameters
        sb.append(GraphicUtil.getXYControlParameters());
        
        //Is the group id initialized?
        if ( this.navigation.getGroupID() == null ) {
            this.navigation.setGroupID(getUser().getGroupID());
        }       

        //Date range qualifiers
        Integer endDate = DateUtil.getTodaysDate();
        Integer startDate = DateUtil.getDaysBackDate(endDate, duration.getNumberOfDays());
        
        //Fetch to get parents children, qualifier is groupId (parent), 
        //date from, date to
        List<ScoreableEntity> s = null;
        try {          
            // TODO: This is not correct.  getUser().getGroupID() needs to be changed to the current group in the navigation
            logger.debug("getting scores for groupID: " + this.navigation.getGroupID());            
            s = graphicDAO.getScores(
                    this.navigation.getGroupID(),startDate, endDate, ScoreType.SCORE_OVERALL);
        } catch (Exception e) {
            logger.debug("graphicDao error: " + e.getMessage());
        }      
                
        //X-coordinates
        sb.append("<categories>");
        sb.append(GraphicUtil.createMonthsString(duration));        
        sb.append("</categories>");
        
        //Loop over returned set of group ids
        List<ScoreableEntity> ss = null;
        for ( int i = 0; i < s.size(); i++ ) {
            ScoreableEntity se = (ScoreableEntity)s.get(i);
            //Fetch to get children's observations
            ss = graphicDAO.getScores(
                    se.getEntityID(),startDate, endDate, ScoreType.SCORE_OVERALL_TIME);
            
            //Y-coordinates
            sb.append("<dataset seriesName=\'\' color=\'");
            sb.append((GraphicUtil.entityColorKey.get(i)).substring(1));
            sb.append("\'>");
            
            //Not a full range, pad w/ zero
            int holes = 0;
            if ( duration == Duration.DAYS ) {
                holes = duration.getNumberOfDays() - ss.size();
            } else {
                holes = GraphicUtil.convertToMonths(duration) - ss.size();
            }
            for ( int k = 0; k < holes; k++ ) {
                sb.append("<set value=\'0.0\'/>");
            }
            
            ScoreableEntity sss = null;          
            for ( int j = 0; j < ss.size(); j++ ) {                
                sss = (ScoreableEntity)ss.get(j);

                sb.append("<set value=\'");
                Float score = new Float(sss.getScore()/10.0);
                sb.append(score.toString()).substring(0,3);
                sb.append("'/>");
            }
            sb.append("</dataset>");
        }
        
        sb.append("</chart>");
        
        return sb.toString();
	}

	public List<ScoreableEntityPkg> getScoreableEntities() {		
		User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();		
		
		//Clear the returned data, if present
		if ( scoreableEntities.size() > 0 ) {
			scoreableEntities.clear();
		}
		
		//Is the group id initialized?
		if ( this.navigation.getGroupID() == null) {
		    this.navigation.setGroupID(getUser().getGroupID());
		}
		
		//Handle navigation
/*		
		logger.debug("location is: " + navigation.getLocation());
		if (	this.pageChange ) {
		    logger.debug(" page changed: " + this.navigation.getLocation());
			if (         this.navigation.getLocation().equalsIgnoreCase("home") ) {
			    this.navigation.setLocation("region");
			    goTo = "go_region";
			} else if (  this.navigation.getLocation().equalsIgnoreCase("region") ) {
			    this.navigation.setLocation("team");
			    goTo = "go_team";
			}
        } 
*/		
		//Fetch, qualifier is groupId, date from, date to
		List<ScoreableEntity> s = null;
		try {
		    Integer endDate = DateUtil.getTodaysDate();
		    Integer startDate = DateUtil.getDaysBackDate(endDate, duration.getNumberOfDays());
		    
		    // TODO: This is not correct.  getUser().getGroupID() needs to be changed to the current group in the navigation
		    logger.debug("getting scores for groupID: " + this.navigation.getGroupID());
		    
			s = graphicDAO.getScores(
			        this.navigation.getGroupID(),startDate, endDate, ScoreType.SCORE_OVERALL);
		} catch (Exception e) {
			logger.debug("graphicDao error: " + e.getMessage());
		}		
		
		//Populate the table
		ScoreBox sb = new ScoreBox(0,ScoreBoxSizes.SMALL);	
		int cnt = 0;
		for (ScoreableEntity score : s)
		{
		    ScoreableEntityPkg se = new ScoreableEntityPkg();
		    se.setSe(score);
		    sb.setScore(score.getScore());
		    se.setStyle(sb.getScoreStyle());
		    se.setColorKey(GraphicUtil.entityColorKey.get(cnt++));
		    if (score.getEntityType().equals(EntityType.ENTITY_GROUP))
		    {
		        se.setGoTo(getGroupHierarchy().getGroupLevel(score.getEntityID()).getLocation());
		    }
		    scoreableEntities.add(se);		 				
		}

//		logger.debug("location is: " + navigation.getLocation());
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
/*
	public String getGoTo() {
		return goTo;
	}

	public void setGoTo(String goTo) {
		this.goTo = goTo;
	}
*/
	public ScoreDAO getGraphicDAO() {
		return graphicDAO;
	}

	public void setGraphicDAO(ScoreDAO graphicDAO) {
		this.graphicDAO = graphicDAO;
	}

    public NavigationBean getNavigation()
    {
        return navigation;
    }

    public void setNavigation(NavigationBean navigation)
    {
        this.navigation = navigation;
/*        
        if (         this.navigation.getLocation().equalsIgnoreCase("home") ) {                
            goTo = "go_region";
        } else if (  this.navigation.getLocation().equalsIgnoreCase("region") ) {
            goTo = "go_team";
        }
*/                               
    }
}
