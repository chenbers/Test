package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.DriverScoreItem;
import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.model.DriverScore;
import com.inthinc.pro.util.GraphicUtil;

public class TeamTopBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(TeamTopBean.class);

    private ScoreDAO scoreDAO;
    private NavigationBean navigation;

    private List<DriverScoreItem> topDrivers;
    private List<DriverScoreItem> bottomDrivers;

    public void init()
    {
        List<DriverScore> tempList = scoreDAO.getSortedDriverScoreList(navigation.getGroupID());
        List<DriverScore> scoreList = new ArrayList<DriverScore>();
        
        // Remove N/A drivers
        for (DriverScore d: tempList)
        {
            if(d.getScore() != null && d.getScore() > 0)
                scoreList.add(d);
        }
         
        topDrivers = convertToDriverScoreItemList(scoreList.subList(0, scoreList.size() > 5 ? 5 : scoreList.size()));

        Collections.reverse(scoreList);
        bottomDrivers = convertToDriverScoreItemList(scoreList.subList(0, scoreList.size() > 5 ? 5 : scoreList.size()));
    }

    public List<DriverScoreItem> getTopDrivers()
    {
        return topDrivers;
    }

    public void setTopDrivers(List<DriverScoreItem> topDrivers)
    {
        this.topDrivers = topDrivers;
    }

    public List<DriverScoreItem> getBottomDrivers()
    {
        return bottomDrivers;
    }

    public void setBottomDrivers(List<DriverScoreItem> bottomDrivers)
    {
        this.bottomDrivers = bottomDrivers;
    }

    private List<DriverScoreItem> convertToDriverScoreItemList(List<DriverScore> scores)
    {
        List<DriverScoreItem> returnList = new ArrayList<DriverScoreItem>();
        int cnt = 1;
        for (DriverScore score : scores)
        {
            if(score == null || score.getScore() < 0)
                continue;  //Skip N/A drivers
            
            DriverScoreItem item = new DriverScoreItem(score);
            ScoreBox sb = new ScoreBox(0, ScoreBoxSizes.SMALL);
            item.setPosition(cnt++);
            if (score.getScore() != null )
            {
                sb.setScore(score.getScore());
            }
            item.setStyle(sb.getScoreStyle());
            returnList.add(item);
        }

        return returnList;
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

    public String getTeamName()
    {

        return navigation.getGroupHierarchy().getGroup(navigation.getGroupID()).getName();
    }
}
