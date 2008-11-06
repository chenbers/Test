package com.inthinc.pro.backing;

import org.apache.log4j.Logger;
import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.util.DateUtil;

public class FleetOverviewBean extends BaseBean
{
    private Integer             overallScore;
    private String              overallScoreStyle;
    private Duration            duration = Duration.DAYS;
    private static final Logger logger   = Logger.getLogger(FleetOverviewBean.class);

    private ScoreDAO          scoreDAO;
    private NavigationBean navigation;


    public FleetOverviewBean()
    {

    }

    private void initStyle()
    {
        if (overallScore == null)
        {
            init();
        }
     
        setOverallScoreStyle(ScoreBox.GetStyleFromScore(getOverallScore(), ScoreBoxSizes.LARGE));
    }

    private void init()
    {
        logger.debug("FleetOverviewBean:init()");
        Integer endDate = DateUtil.getTodaysDate();
        Integer startDate = DateUtil.getDaysBackDate(endDate, duration.getNumberOfDays());
        Integer groupID = navigation.getGroupID();
        if (groupID == null)
        {
            groupID = getUser().getPerson().getGroupID();
        }
        ScoreableEntity scoreableEntity = scoreDAO.getOverallScore(groupID, startDate, endDate);
        setOverallScore(scoreableEntity.getScore());
    }

    public Integer getOverallScore()
    {

        if (overallScore == null)
        {
            init();
        }
        return overallScore;
    }

    public void setOverallScore(Integer overallScore)
    {
        this.overallScore = overallScore;
        initStyle();
    }

    public String getOverallScoreStyle()
    {
        if (overallScoreStyle == null)
        {
            initStyle();
        }
        logger.debug("overallScoreStyle = " + overallScoreStyle);
        return overallScoreStyle;
    }

    public void setOverallScoreStyle(String overallScoreStyle)
    {
        this.overallScoreStyle = overallScoreStyle;
    }

    public Duration getDuration()
    {
        return duration;
    }

    public String getDurationAsString()
    {
    	return duration.toString();
    }
    
    public void setDuration(Duration duration)
    {
        this.duration = duration;
        overallScore = null;
    }

    public ScoreDAO getScoreDAO()
    {
        return scoreDAO;
    }

    public void setScoreDAO(ScoreDAO scoreDAO)
    {
        this.scoreDAO = scoreDAO;
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
