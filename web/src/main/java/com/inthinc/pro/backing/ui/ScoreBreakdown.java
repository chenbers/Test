package com.inthinc.pro.backing.ui;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.ScoreableEntity;

public class ScoreBreakdown
{
    private static final Logger logger = Logger.getLogger(ScoreBreakdown.class);
    Map <ScoreCategory, Integer> countMap;
    Integer numScores;
    
    public ScoreBreakdown(List<ScoreableEntity> scoreList)
    {
        countMap = new HashMap<ScoreCategory, Integer>();
        
        for (ScoreCategory p : EnumSet.allOf(ScoreCategory.class))
        {
            countMap.put(p, 0);
        }
        numScores = 0;
        
        for (ScoreableEntity score : scoreList)
        {
            ScoreCategory scoreCategory = ScoreCategory.getCategoryForScore(score.getScore());
            
            if (scoreCategory == null)
            {
                logger.error("Invalid Score");
            }
            else
            {
                numScores++;
                Integer scoreCnt = countMap.get(scoreCategory);
                scoreCnt++;
                countMap.put(scoreCategory, scoreCnt);
            }
        }
    }

    public Map<ScoreCategory, Integer> getCountMap()
    {
        return countMap;
    }

    public void setCountMap(Map<ScoreCategory, Integer> countMap)
    {
        this.countMap = countMap;
    }

    public Integer getNumScores()
    {
        return numScores;
    }

    public void setNumScores(Integer numScores)
    {
        this.numScores = numScores;
    }
}
