package com.inthinc.pro.backing;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.dao.GraphicDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.ScoreableEntity;

public class TeamOverviewBean extends BaseBean
{
    private Integer overallScore;
    private GraphicDAO graphicDAO;

    private void init()
    {
        Integer endDate = DateUtil.getTodaysDate();
        Integer startDate = DateUtil.getDaysBackDate(endDate, Duration.DAYS.getNumberOfDays());
        
        // TODO: should be passing in the team's groupID
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
    }
    public String getOverallScoreStyle()
    {
        ScoreBox sb = new ScoreBox(getOverallScore(), ScoreBoxSizes.MEDIUM);
        return sb.getScoreStyle();
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
