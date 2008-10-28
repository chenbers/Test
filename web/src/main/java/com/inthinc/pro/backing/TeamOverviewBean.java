package com.inthinc.pro.backing;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.backing.ui.ScoreBreakdown;
import com.inthinc.pro.backing.ui.ScoreCategory;
import com.inthinc.pro.charts.Pie;
import com.inthinc.pro.dao.GraphicDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.util.GraphicUtil;

public class TeamOverviewBean extends BaseBean
{

    private Integer             overallScore;
    private GraphicDAO          graphicDAO;
    private String              overallPieDef;
    private static final Logger logger = Logger.getLogger(TeamOverviewBean.class);

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

    public String getOverallPieDef()
    {
        if (overallPieDef == null)
        {
            overallPieDef = createPieDef();
        }
        logger.debug("returned string: " + overallPieDef);
        return overallPieDef;
    }

    public void setOverallPieDef(String overallPieDef)
    {
        this.overallPieDef = overallPieDef;
    }

    public String createPieDef()
    {
        StringBuffer sb = new StringBuffer();
        Pie pie = new Pie();

        // Control parameters
        sb.append(pie.getControlParameters());

        Integer endDate = DateUtil.getTodaysDate();
        Integer startDate = DateUtil.getDaysBackDate(endDate, Duration.DAYS.getNumberOfDays());
        List<ScoreableEntity> scoreList = graphicDAO.getScores(getUser().getGroupID(), startDate, endDate, ScoreType.SCORE_OVERALL);
        ScoreBreakdown scoreBreakdown = new ScoreBreakdown(scoreList);
        Integer numScores = scoreBreakdown.getNumScores();
        if (numScores == 0)
        {
            // TODO:  What color/text (see use case)?
            sb.append(pie.getChartItem(new Object[] {100, "No Data To Display", "F6B305"}));
        }
        else
        {
            Map<ScoreCategory, Integer> countMap = scoreBreakdown.getCountMap();
            for (Map.Entry<ScoreCategory, Integer> item : countMap.entrySet())
            {
                if (item.getValue().intValue() == 0)
                {
                    continue;
                }
                Integer value = (item.getValue() * 100 / numScores);
                sb.append(pie.getChartItem(new Object[] {value, "", item.getKey().getColor()}));

            }

        }
        sb.append(pie.getClose());

        return sb.toString();
    }

}
