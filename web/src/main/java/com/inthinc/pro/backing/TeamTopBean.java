package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.wrapper.ScoreableEntityPkg;

public class TeamTopBean extends BaseBean
{
   private static final Logger logger = Logger.getLogger(TeamTopBean.class);
    
    private ScoreDAO scoreDAO;
    private NavigationBean navigation;
    private boolean pageChange = false;
    private Duration duration = Duration.DAYS;
    
    private List<ScoreableEntityPkg> topDrivers = new ArrayList<ScoreableEntityPkg>();
    private List<ScoreableEntityPkg> bottomDrivers = new ArrayList<ScoreableEntityPkg>();
   
    private String goTo = "";

    public List<ScoreableEntityPkg> getTopDrivers()
    {
//        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();      
        
        //Clear the returned data, if present
        if ( topDrivers.size() > 0 ) {
            topDrivers.clear();
        }
        
        //Is the group id initialized?
        if ( this.navigation.getGroupID() == null ) {
            this.navigation.setGroupID(getUser().getPerson().getGroupID());
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
//        List<ScoreableEntity> s = new ArrayList<ScoreableEntity>();
//        for (int i=0;i <5;i++){
//            
//          ScoreableEntity se = new ScoreableEntity();
//          se.setCreated(new Date());
//          se.setDate(DateUtil.getTodaysDate());
//          se.setEntityID(i);
//          se.setEntityType(EntityType.ENTITY_DRIVER);
//          se.setIdentifier("Fred");
//          se.setModified(new Date());
//          se.setPosition(i);
//          se.setScore((5-i)*10);
//          se.setScoreType(ScoreType.SCORE_OVERALL);
//          s.add(se);
//
//        }
//        s.get(0).setIdentifier("Frankenstein");
//        s.get(1).setIdentifier("Dracula");
//        s.get(2).setIdentifier("Casper");
//        s.get(3).setIdentifier("Jack O'Lantern");
//        s.get(4).setIdentifier("Herman Munster");
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
            se.setGoTo("go_team");
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
        
//        //Is the group id initialized?
//        if ( this.navigation.getGroupID() == -1 ) {
//            this.navigation.setGroupID(getUser().getGroupID());
//        }
//        
//        //Handle navigation
//        logger.debug("location is: " + navigation.getLocation());
//        if (    this.pageChange ) {
//            logger.debug(" page changed: " + this.navigation.getLocation());
//            if (         this.navigation.getLocation().equalsIgnoreCase("home") ) {
//                this.navigation.setLocation("region");
//                goTo = "go_region";
//            } else if (  this.navigation.getLocation().equalsIgnoreCase("region") ) {
//                this.navigation.setLocation("team");
//                goTo = "go_team";
//            }
//        } 
        
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
//        List<ScoreableEntity> s = new ArrayList<ScoreableEntity>();
//        for (int i=0;i <5;i++){
//            
//          ScoreableEntity se = new ScoreableEntity();
//          se.setCreated(new Date());
//          se.setDate(DateUtil.getTodaysDate());
//          se.setEntityID(i);
//          se.setEntityType(EntityType.ENTITY_DRIVER);
//          se.setIdentifier("Sid");
//          se.setModified(new Date());
//          se.setPosition(i+5);
//          se.setScore((5-i)*10);
//          se.setScoreType(ScoreType.SCORE_OVERALL);
//          s.add(se);
//        }
//        s.get(0).setIdentifier("Harry Potter");
//        s.get(1).setIdentifier("Morticia Adams");
//        s.get(2).setIdentifier("Uncle Festus");
//        s.get(3).setIdentifier("Lurch");
//        s.get(4).setIdentifier("Thing");
//        //Populate the table
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
            se.setGoTo("go_team");
            bottomDrivers.add(se);                      
        }

//        logger.debug("location is: " + navigation.getLocation());
        this.pageChange = true;
       
        return bottomDrivers;
    }

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

}
