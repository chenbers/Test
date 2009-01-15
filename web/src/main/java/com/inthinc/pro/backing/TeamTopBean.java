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

    private List<ScoreableEntityPkg> topDrivers;
    private List<ScoreableEntityPkg> bottomDrivers;

    private String goTo = "go_driver";

    // TODO: This could be refactored to do fewer db hits.

    public List<ScoreableEntityPkg> getTopDrivers()
    {
        if (topDrivers == null || topDrivers.isEmpty())
        {
            // Fetch, qualifier is groupId, date from, date to
            topDrivers = convertToScoreableEntityPkg(scoreDAO.getTopFiveScores(this.navigation.getGroupID()));
        }
        this.pageChange = true;
        return topDrivers;
    }

    public void setTopDrivers(List<ScoreableEntityPkg> topDrivers)
    {
        this.topDrivers = topDrivers;
    }

    public List<ScoreableEntityPkg> getBottomDrivers()
    {
        if (bottomDrivers == null || bottomDrivers.isEmpty())
        {
            bottomDrivers = convertToScoreableEntityPkg(scoreDAO.getBottomFiveScores(this.navigation.getGroupID()));
        }
        this.pageChange = true;

        return bottomDrivers;
    }

    // public void setupNavigation(int driverID){
    //    	
    // logger.debug("setupNavigation driverID is: " + driverID);
    // Driver driver = driverDAO.getDriverByID(driverID);
    // navigation.setDriver(driver);
    // navigation.setGroupID(driverID);
    //    	
    // }
    public void setBottomDrivers(List<ScoreableEntityPkg> bottomDrivers)
    {
        this.bottomDrivers = bottomDrivers;
    }

    private List<ScoreableEntityPkg> convertToScoreableEntityPkg(List<ScoreableEntity> scores)
    {
        List<ScoreableEntityPkg> returnList = new ArrayList<ScoreableEntityPkg>();
        int cnt = 0;
        for (ScoreableEntity score : scores)
        {
            ScoreableEntityPkg se = new ScoreableEntityPkg();
            ScoreBox sb = new ScoreBox(0, ScoreBoxSizes.SMALL);
            se.setPosition(cnt + 1);
            se.setSe(score);
            if (score.getScore() != null)
            {
                sb.setScore(score.getScore());
            }
            se.setStyle(sb.getScoreStyle());
            se.setColorKey(GraphicUtil.entityColorKey.get(cnt++));
            se.setGoTo("go_driver");
            returnList.add(se);
        }

        return returnList;
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

    public String driverAction()
    {

        Map<String, String> requestMap = new WebUtil().getRequestParameterMap();
        String driverID = requestMap.get("id");
        navigation.setDriver(driverDAO.findByID(Integer.valueOf(driverID)));

        return "go_driver";
    }

    public DriverDAO getDriverDAO()
    {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }
}
