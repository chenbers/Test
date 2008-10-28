package com.inthinc.pro.backing;

import org.apache.log4j.Logger;
import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.dao.GraphicDAO;
import com.inthinc.pro.dao.util.DateUtil;

public class FleetOverviewBean extends BaseBean
{
    private Integer             overallScore;
    private String              overallScoreStyle;
    private Duration            duration = Duration.DAYS;
    private static final Logger logger   = Logger.getLogger(FleetOverviewBean.class);

    private GraphicDAO          graphicDAO;

    public FleetOverviewBean()
    {

    }

    private void initStyle()
    {
        if (overallScore == null)
        {
            init();
        }

        ScoreBox sb = new ScoreBox(getOverallScore(), ScoreBoxSizes.LARGE);
        setOverallScoreStyle(sb.getScoreStyle());
    }

    private void init()
    {
        Integer endDate = DateUtil.getTodaysDate();
        Integer startDate = DateUtil.getDaysBackDate(endDate, duration.getNumberOfDays());
        ScoreableEntity scoreableEntity = graphicDAO.getOverallScore(getUser().getGroupID(), startDate, endDate);
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

    public GraphicDAO getGraphicDAO()
    {
        return graphicDAO;
    }

    public void setGraphicDAO(GraphicDAO graphicDAO)
    {
        this.graphicDAO = graphicDAO;
    }
}
