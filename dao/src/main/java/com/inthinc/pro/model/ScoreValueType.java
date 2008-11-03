package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ScoreValueType implements BaseEnum
{
    SCORE_SCALE_0_50(1, "SCORE_SCALE_0_50"),
    SCORE_PERCENTAGE(2, "SCORE_PERCENTAGE");
    
    
    private String description;
    private int code;

    private ScoreValueType(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, ScoreValueType> lookup = new HashMap<Integer, ScoreValueType>();
    static
    {
        for (ScoreValueType p : EnumSet.allOf(ScoreValueType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static ScoreValueType getScoreValueType(Integer code)
    {
        return lookup.get(code);
    }

    @Override
    public String toString()
    {
        return this.description;
    }

}
