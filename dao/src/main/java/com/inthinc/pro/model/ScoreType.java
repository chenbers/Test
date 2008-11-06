package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ScoreType implements BaseEnum
{

    SCORE_OVERALL(1, "SCORE_OVERALL"),
    SCORE_SPEEDING(2, "SCORE_SPEEDING"),
    SCORE_SEATBELT(3, "SCORE_SEATBELT"),
    SCORE_DRIVING_STYLE(4, "SCORE_DRIVING_STYLE"),
    SCORE_COACHING_EVENTS(5, "SCORE_COACHING_EVENTS"),
    SCORE_OVERALL_TIME(6, "SCORE_OVERALL_TIME");
//    SCORE_OVERALL_PERCENTAGES(7, "SCORE_OVERALL_PERCENTAGES");
    
    private String description;
    private int code;

    private ScoreType(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, ScoreType> lookup = new HashMap<Integer, ScoreType>();
    static
    {
        for (ScoreType p : EnumSet.allOf(ScoreType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static ScoreType valueOf(Integer code)
    {
        return lookup.get(code);
    }

    @Override
    public String toString()
    {
        return this.description;
    }
}

