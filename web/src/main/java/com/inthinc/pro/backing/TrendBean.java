package com.inthinc.pro.backing;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

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

public class TrendBean extends BaseDurationBean {

	private static final Logger logger = Logger.getLogger(TrendBean.class);
	
	private ScoreDAO scoreDAO;
	private NavigationBean navigation;
	
	private String lineDef;	

	private List <ScoreableEntityPkg> scoreableEntities = new ArrayList<ScoreableEntityPkg>();

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
        Integer startDate = DateUtil.getDaysBackDate(endDate, getDuration().getNumberOfDays());
        
        //Fetch to get parents children, qualifier is groupId (parent), 
        //date from, date to
        List<ScoreableEntity> s = null;
        try {          
            // TODO: This is not correct.  getUser().getGroupID() needs to be changed to the current group in the navigation
            logger.debug("getting scores for groupID: " + this.navigation.getGroupID());            
            s = scoreDAO.getScores(
                    this.navigation.getGroupID(),startDate, endDate, ScoreType.SCORE_OVERALL);
        } catch (Exception e) {
            logger.debug("graphicDao error: " + e.getMessage());
        }      
                
        //X-coordinates
        sb.append("<categories>");
        sb.append(GraphicUtil.createMonthsString(getDuration()));        
        sb.append("</categories>");
        
        //Loop over returned set of group ids
        List<ScoreableEntity> ss = null;
        for ( int i = 0; i < s.size(); i++ ) {
            ScoreableEntity se = (ScoreableEntity)s.get(i);
            //Fetch to get children's observations
            ss = scoreDAO.getScores(
                    se.getEntityID(),startDate, endDate, ScoreType.SCORE_OVERALL_TIME);
            
            //Y-coordinates
            sb.append("<dataset seriesName=\'\' color=\'");
            sb.append((GraphicUtil.entityColorKey.get(i)).substring(1));
            sb.append("\'>");
            
            //Not a full range, pad w/ zero
            int holes = 0;
            if ( getDuration() == Duration.DAYS ) {
                holes = getDuration().getNumberOfDays() - ss.size();
            } else {
                holes = GraphicUtil.convertToMonths(getDuration()) - ss.size();
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
		//Clear the returned data, if present
		if ( scoreableEntities.size() > 0 ) {
			scoreableEntities.clear();
		}
		
		//Is the group id initialized?
		if ( this.navigation.getGroupID() == null) {
		    this.navigation.setGroupID(getUser().getGroupID());
		}
		
		//Fetch, qualifier is groupId, date from, date to
		List<ScoreableEntity> s = null;
		try {
		    Integer endDate = DateUtil.getTodaysDate();
		    Integer startDate = DateUtil.getDaysBackDate(endDate, getDuration().getNumberOfDays());
		    
		    // TODO: This is not correct.  getUser().getGroupID() needs to be changed to the current group in the navigation
		    logger.debug("getting scores for groupID: " + this.navigation.getGroupID());
		    
			s = scoreDAO.getScores(
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
		
		return scoreableEntities;
	}

	public void setScoreableEntities(List<ScoreableEntityPkg> scoreableEntities) {
		this.scoreableEntities = scoreableEntities;
	}	

	public ScoreDAO getScoreDAO() {
		return scoreDAO;
	}

	public void setScoreDAO(ScoreDAO graphicDAO) {
		this.scoreDAO = graphicDAO;
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
