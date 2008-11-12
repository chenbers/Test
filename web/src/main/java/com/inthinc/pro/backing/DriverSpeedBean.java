package com.inthinc.pro.backing;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.backing.ui.ScoreBreakdown;
import com.inthinc.pro.backing.ui.ScoreCategory;
import com.inthinc.pro.charts.ChartSizes;
import com.inthinc.pro.charts.Line;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Distance;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;

public class DriverSpeedBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(DriverSpeedBean.class);
    
    private ScoreDAO    scoreDAO;
    private Distance    distance = Distance.FIVEHUNDRED;
    
    private Integer     speedScore;
    private String      speedScoreHistorySmall;
    private String      speedScoreHistoryLarge;
    private String      speedScoreStyle;
    
    Integer endDate = DateUtil.getTodaysDate();
    Integer startDate = DateUtil.getDaysBackDate(endDate, 30);
    
    private void initSpeed()
    {
        
        logger.debug("## initSpeed()");
        
        ScoreableEntity speedSe = scoreDAO.getAverageScoreByType(getUser().getPerson().getGroupID(), startDate, endDate, ScoreType.SCORE_OVERALL); //Replace with correct DAO
        setSpeedScore(speedSe.getScore());
    }
    
  
    public Integer getSpeedScore() {
        if(speedScore == null)
            initSpeed();
        
        return speedScore;
    }
    public void setSpeedScore(Integer speedScore) {
        this.speedScore = speedScore;
        setSpeedScoreStyle(ScoreBox.GetStyleFromScore(speedScore, ScoreBoxSizes.MEDIUM));
    }
    public String getSpeedScoreHistoryLarge() {
        setSpeedScoreHistoryLarge(createLineDef(ScoreType.SCORE_SPEEDING, ChartSizes.LARGE));
        return speedScoreHistoryLarge;
    }
    public void setSpeedScoreHistoryLarge(String speedScoreHistoryLarge) {
        this.speedScoreHistoryLarge = speedScoreHistoryLarge;
    }
 
    public String getSpeedScoreHistorySmall() {
        setSpeedScoreHistorySmall(createLineDef(ScoreType.SCORE_SPEEDING, ChartSizes.SMALL));
        return speedScoreHistorySmall;
    }
    public void setSpeedScoreHistorySmall(String speedScoreHistorySmall) {
        this.speedScoreHistorySmall = speedScoreHistorySmall;
    }
    
    public String getSpeedScoreStyle() {
        if(speedScoreStyle == null)
            initSpeed();
        
        return speedScoreStyle;
    }
    public void setSpeedScoreStyle(String speedScoreStyle) {
        this.speedScoreStyle = speedScoreStyle;
    }
    
    public String createLineDef(ScoreType scoreType, ChartSizes size)
    {
        StringBuffer sb = new StringBuffer();
        Line line = new Line();

        // Control parameters
        sb.append(line.getControlParameters(size));
        
        List<ScoreableEntity> scoreList = scoreDAO.getScoreBreakdown(101, startDate, endDate, scoreType);

        ScoreBreakdown scoreBreakdown = new ScoreBreakdown(scoreList);
        if (scoreList.size() == 0)
        {
            // TODO:  What color/text (see use case)?
            sb.append(line.getChartItem(new Object[] {100, "No Data To Display", "F6B305"}));
        }
        else
        {
            Map<ScoreCategory, Integer> valueMap = scoreBreakdown.getValueMap();
            for (Map.Entry<ScoreCategory, Integer> item : valueMap.entrySet())
            {
                if (item.getValue().intValue() == 0)
                {
                    continue;
                }
                
                double temp = (double)(item.getValue() / 10);
                sb.append(line.getChartItem(new Object[] {temp, item.getKey().toString()}));

            }

        }
        sb.append(line.getClose());

        return sb.toString();
    }

    //DAO PROPERTIES
    public ScoreDAO getScoreDAO()
    {
        return scoreDAO;
    }

    public void setScoreDAO(ScoreDAO scoreDAO)
    {
        this.scoreDAO = scoreDAO;
    }

    //DISTANCE PROPERTIES
    public Distance getDistance()
    {
        return distance;
    }
    public void setDistance(Distance distance)
    {
        this.distance = distance;
    }



}
