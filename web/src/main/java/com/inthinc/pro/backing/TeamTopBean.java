package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.util.WebUtil;
import com.inthinc.pro.wrapper.ScoreableEntityPkg;

public class TeamTopBean extends BaseBean
{
   private static final Logger logger = Logger.getLogger(TeamTopBean.class);
    
    private ScoreDAO scoreDAO;
    private DriverDAO driverDAO;
    private NavigationBean navigation;
    private boolean pageChange = false;
    private Duration duration = Duration.DAYS;
    
    private List<ScoreableEntityPkg> topDrivers = new ArrayList<ScoreableEntityPkg>();
    private List<ScoreableEntityPkg> bottomDrivers = new ArrayList<ScoreableEntityPkg>();
   
    private String goTo = "go_driver";

    public List<ScoreableEntityPkg> getTopDrivers()
    {
//        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();      
        
        //Clear the returned data, if present
        if ( topDrivers.size() > 0 ) {
            topDrivers.clear();
        }
        
        //Fetch, qualifier is groupId, date from, date to
        List<ScoreableEntity> s = null;
        try {
            Integer endDate = DateUtil.getTodaysDate();
            Integer startDate = DateUtil.getDaysBackDate(endDate, duration.getNumberOfDays());
            
            // TODO: This is not correct.  getUser().getGroupID() needs to be changed to the current group in the navigation
            logger.debug("getting scores for groupID: " + this.navigation.getGroupID());
            
            s = scoreDAO.getTopFiveScores(this.navigation.getGroupID());
        } catch (Exception e) {
            logger.debug("graphicDao error: " + e.getMessage());
        }       
//        //Populate the table
        ScoreBox sb = new ScoreBox(0,ScoreBoxSizes.SMALL);  
        int cnt = 0;
        topDrivers = new ArrayList<ScoreableEntityPkg>();
        for (ScoreableEntity score : s)
        {
            ScoreableEntityPkg se = new ScoreableEntityPkg();
            score.setPosition(cnt+1);
            se.setSe(score);
            sb.setScore(score.getScore());
            se.setStyle(sb.getScoreStyle());
            se.setColorKey(GraphicUtil.entityColorKey.get(cnt++));
            se.setGoTo("go_driver");
            topDrivers.add(se);                      
        }

//        logger.debug("location is: " + navigation.getLocation());
        this.pageChange = true;
       return topDrivers;
    }

    public void setTopDrivers(List<ScoreableEntityPkg> topDrivers)
    {
        this.topDrivers = topDrivers;
    }

    public List<ScoreableEntityPkg> getBottomDrivers()
    {
//        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();      
        
        //Clear the returned data, if present
        if ( bottomDrivers.size() > 0 ) {
            bottomDrivers.clear();
        }
                
        //Fetch, qualifier is groupId, date from, date to
        List<ScoreableEntity> s = null;
        try {
            Integer endDate = DateUtil.getTodaysDate();
            Integer startDate = DateUtil.getDaysBackDate(endDate, duration.getNumberOfDays());
            
            // TODO: This is not correct.  getUser().getGroupID() needs to be changed to the current group in the navigation
            logger.debug("getting scores for groupID: " + this.navigation.getGroupID());
            
            s = scoreDAO.getBottomFiveScores(this.navigation.getGroupID());
        } catch (Exception e) {
            logger.debug("graphicDao error: " + e.getMessage());
        }       

        //Populate the table
        ScoreBox sb = new ScoreBox(0,ScoreBoxSizes.SMALL);  
        int cnt = 0;
        bottomDrivers = new ArrayList<ScoreableEntityPkg>();
       for (ScoreableEntity score : s)
        {
            ScoreableEntityPkg se = new ScoreableEntityPkg();
            score.setPosition(cnt+1);
            se.setSe(score);
            sb.setScore(score.getScore());
            se.setStyle(sb.getScoreStyle());
            se.setColorKey(GraphicUtil.entityColorKey.get(cnt++));
            se.setGoTo("go_driver");
            bottomDrivers.add(se);                      
        }

//        logger.debug("location is: " + navigation.getLocation());
        this.pageChange = true;
       
        return bottomDrivers;
    }
//    public void setupNavigation(int driverID){
//    	
//    	logger.debug("setupNavigation driverID is: " + driverID);
//    	Driver driver = driverDAO.getDriverByID(driverID);
//    	navigation.setDriver(driver);
//    	navigation.setGroupID(driverID);
//    	
//    }
    public void setBottomDrivers(List<ScoreableEntityPkg> bottomDrivers)
    {
        this.bottomDrivers = bottomDrivers;
    }

    public String getGoTo()
    {
        return goTo;
    }

    public void setGoTo(String goTo)
    {
        this.goTo = goTo;
    }

    public NavigationBean getNavigation()
    {
        return navigation;
    }

    public void setNavigation(NavigationBean navigation)
    {
        this.navigation = navigation;
    }

    public ScoreDAO getScoreDAO()
    {
        return scoreDAO;
    }

    public void setScoreDAO(ScoreDAO scoreDAO)
    {
        this.scoreDAO = scoreDAO;
    }
    public String driverAction(){
    	
    	Map<String,String> requestMap = new WebUtil().getRequestParameterMap();
    	String driverID = requestMap.get("id");
    	navigation.setDriver(driverDAO.findByID(new Integer(driverID)));
    	
    	return "go_driver";
    }

	public DriverDAO getDriverDAO() {
		return driverDAO;
	}

	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
	}
}
