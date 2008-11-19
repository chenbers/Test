package com.inthinc.pro.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ScoreType implements BaseEnum
{

    SCORE_OVERALL(1, "SCORE_OVERALL"),
    SCORE_SPEEDING(2, "SCORE_SPEEDING"),
    SCORE_SEATBELT(3, "SCORE_SEATBELT"),
    SCORE_DRIVING_STYLE(4, "SCORE_DRIVING_STYLE"),
    SCORE_COACHING_EVENTS(5, "SCORE_COACHING_EVENTS"),
    SCORE_OVERALL_TIME(6, "SCORE_OVERALL_TIME"),
    // subTypes of SPEEDING
    SCORE_SPEEDING_21_30(7, "SCORE_SPEEDING_21_30"),
    SCORE_SPEEDING_31_40(8, "SCORE_SPEEDING_31_40"),
    SCORE_SPEEDING_41_54(9, "SCORE_SPEEDING_41_54"),
    SCORE_SPEEDING_55_64(10, "SCORE_SPEEDING_55_64"),
    SCORE_SPEEDING_65_80(11, "SCORE_SPEEDING_65_80"),
    // subTypes of SEAT BELT
    SCORE_SEATBELT_30_DAYS(12, "SCORE_SEATBELT_30_DAYS"),
    SCORE_SEATBELT_3_MONTHS(13, "SCORE_SEATBELT_3_MONTHS"),
    SCORE_SEATBELT_6_MONTHS(14, "SCORE_SEATBELT_6_MONTHS"),
    SCORE_SEATBELT_12_MONTHS(15, "SCORE_SEATBELT_12_MONTHS"),
    // subTypes of DRIVING_STYLE
    SCORE_DRIVING_STYLE_HARD_BRAKE(16, "SCORE_DRIVING_STYLE_HARD_BRAKE"),
    SCORE_DRIVING_STYLE_HARD_ACCEL(17, "SCORE_DRIVING_STYLE_HARD_ACCEL"),
    SCORE_DRIVING_STYLE_HARD_LTURN(18, "SCORE_DRIVING_STYLE_HARD_LTURN"),
    SCORE_DRIVING_STYLE_HARD_RTURN(19, "SCORE_DRIVING_STYLE_HARD_RTURN"),
    SCORE_DRIVING_STYLE_HARD_BUMP(20, "SCORE_DRIVING_STYLE_HARD_BUMP");

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
    private static final Map<ScoreType, List<ScoreType>> lookupSubtypes = new HashMap<ScoreType, List<ScoreType>>();
    static
    {
        List<ScoreType> subTypeList = new ArrayList<ScoreType>();
        Collections.addAll(subTypeList, ScoreType.SCORE_OVERALL, ScoreType.SCORE_SEATBELT, ScoreType.SCORE_SPEEDING, ScoreType.SCORE_DRIVING_STYLE); 
        lookupSubtypes.put(ScoreType.SCORE_OVERALL, subTypeList) ;
        subTypeList = new ArrayList<ScoreType>();
        Collections.addAll(subTypeList, ScoreType.SCORE_SEATBELT, ScoreType.SCORE_SEATBELT_30_DAYS, ScoreType.SCORE_SEATBELT_3_MONTHS, ScoreType.SCORE_SEATBELT_6_MONTHS, ScoreType.SCORE_SEATBELT_12_MONTHS); 
        lookupSubtypes.put(ScoreType.SCORE_SEATBELT, subTypeList) ;

        subTypeList = new ArrayList<ScoreType>();
        Collections.addAll(subTypeList, ScoreType.SCORE_SPEEDING, ScoreType.SCORE_SPEEDING_21_30, ScoreType.SCORE_SPEEDING_31_40, ScoreType.SCORE_SPEEDING_41_54, ScoreType.SCORE_SPEEDING_55_64, ScoreType.SCORE_SPEEDING_65_80); 
        lookupSubtypes.put(ScoreType.SCORE_SPEEDING, subTypeList) ;

        subTypeList = new ArrayList<ScoreType>();
        Collections.addAll(subTypeList, ScoreType.SCORE_DRIVING_STYLE, ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL, ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE, ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP, ScoreType.SCORE_DRIVING_STYLE_HARD_LTURN, ScoreType.SCORE_DRIVING_STYLE_HARD_RTURN); 
        lookupSubtypes.put(ScoreType.SCORE_DRIVING_STYLE, subTypeList) ;

    }

    public Integer getCode()
    {
        return this.code;
    }
    
    public List<ScoreType> getSubTypes()
    {
        return lookupSubtypes.get(this);
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

