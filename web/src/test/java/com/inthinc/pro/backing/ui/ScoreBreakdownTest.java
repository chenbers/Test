package com.inthinc.pro.backing.ui;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.util.MiscUtil;

public class ScoreBreakdownTest
{
    
    
    @Test
    public void countMap()
    {
        List<ScoreableEntity> scoreList = new ArrayList<ScoreableEntity>();
        int scoreCnt = MiscUtil.randomInt(0, 100);
        int type3Cnt = 0;
        for (int i = 0; i < scoreCnt; i++)
        {
            int score = MiscUtil.randomInt(0, 50);
            scoreList.add(new ScoreableEntity(i, EntityType.ENTITY_DRIVER, "", score, 0, ScoreType.SCORE_OVERALL));
            if (score >= 21 && score <= 30)
                type3Cnt++;
        }
        
        ScoreBreakdown scoreBreakdown = new ScoreBreakdown(scoreList);
        assertEquals(scoreCnt, scoreBreakdown.getNumScores().intValue());
        assertEquals(type3Cnt, scoreBreakdown.getCountMap().get(ScoreCategory.CAT_3).intValue());
        
        
        // all invalid
        scoreList = new ArrayList<ScoreableEntity>();
        scoreCnt = MiscUtil.randomInt(0, 100);
        for (int i = 0; i < scoreCnt; i++)
        {
            int score = MiscUtil.randomInt(51, 100);
            scoreList.add(new ScoreableEntity(i, EntityType.ENTITY_DRIVER, "", score, 0, ScoreType.SCORE_OVERALL));
        }
        
        scoreBreakdown = new ScoreBreakdown(scoreList);
        assertEquals(0, scoreBreakdown.getNumScores().intValue());
        

    }
}
