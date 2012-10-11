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
    Map <ScoreCategory, Integer> valueMap;
    
    public ScoreBreakdown(List<ScoreableEntity> scoreList)
    {
        valueMap = new HashMap<ScoreCategory, Integer>();
        
        int idx = 0;
        for (ScoreCategory p : EnumSet.allOf(ScoreCategory.class))
        {
            valueMap.put(p, scoreList.get(idx++).getScore());
        }
        
        
    }

    public Map<ScoreCategory, Integer> getValueMap()
    {
        return valueMap;
    }

    public void setValueMap(Map<ScoreCategory, Integer> valueMap)
    {
        this.valueMap = valueMap;
    }

}
