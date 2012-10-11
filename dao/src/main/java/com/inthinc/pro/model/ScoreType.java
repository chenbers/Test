package com.inthinc.pro.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum ScoreType implements BaseEnum
{

    SCORE_OVERALL(1, "SCORE_OVERALL", DriveQMetric.DRIVEQMETRIC_OVERALL),
    SCORE_SPEEDING(2, "SCORE_SPEEDING", DriveQMetric.DRIVEQMETRIC_SPEEDING),
    SCORE_SEATBELT(3, "SCORE_SEATBELT", DriveQMetric.DRIVEQMETRIC_SEATBELT),
    SCORE_DRIVING_STYLE(4, "SCORE_DRIVING_STYLE", DriveQMetric.DRIVEQMETRIC_DRIVING_STYLE),
    SCORE_COACHING_EVENTS(5, "SCORE_COACHING_EVENTS", DriveQMetric.DRIVEQMETRIC_COACHING),
    // subTypes of SPEEDING
    SCORE_SPEEDING_21_30(6, "SCORE_SPEEDING_21_30", DriveQMetric.DRIVEQMETRIC_SPEEDING1),
    SCORE_SPEEDING_31_40(7, "SCORE_SPEEDING_31_40", DriveQMetric.DRIVEQMETRIC_SPEEDING2),
    SCORE_SPEEDING_41_54(8, "SCORE_SPEEDING_41_54", DriveQMetric.DRIVEQMETRIC_SPEEDING3),
    SCORE_SPEEDING_55_64(9, "SCORE_SPEEDING_55_64", DriveQMetric.DRIVEQMETRIC_SPEEDING4),
    SCORE_SPEEDING_65_80(10, "SCORE_SPEEDING_65_80", DriveQMetric.DRIVEQMETRIC_SPEEDING5),
    // subTypes of SEAT BELT
    SCORE_SEATBELT_30_DAYS(11, "SCORE_SEATBELT_30_DAYS", DriveQMetric.DRIVEQMETRIC_SEATBELT, Duration.DAYS),
    SCORE_SEATBELT_3_MONTHS(12, "SCORE_SEATBELT_3_MONTHS", DriveQMetric.DRIVEQMETRIC_SEATBELT, Duration.THREE),
    SCORE_SEATBELT_6_MONTHS(13, "SCORE_SEATBELT_6_MONTHS", DriveQMetric.DRIVEQMETRIC_SEATBELT, Duration.SIX),
    SCORE_SEATBELT_12_MONTHS(14, "SCORE_SEATBELT_12_MONTHS", DriveQMetric.DRIVEQMETRIC_SEATBELT, Duration.TWELVE),
    // subTypes of DRIVING_STYLE
    SCORE_DRIVING_STYLE_HARD_BRAKE(15, "HARD_BRAKE", DriveQMetric.DRIVEQMETRIC_AGGRESSIVE_BRAKE),
    SCORE_DRIVING_STYLE_HARD_ACCEL(16, "HARD_ACCEL", DriveQMetric.DRIVEQMETRIC_AGGRESSIVE_ACCEL),
    SCORE_DRIVING_STYLE_HARD_TURN(17, "HARD_TURN", DriveQMetric.DRIVEQMETRIC_AGGRESSIVE_TURN),
    SCORE_DRIVING_STYLE_HARD_BUMP(18, "HARD_VERT", DriveQMetric.DRIVEQMETRIC_AGGRESSIVE_BUMP),
    // TODO: need to split hard turn into hard left/right
    SCORE_DRIVING_STYLE_HARD_LTURN(19, "SCORE_DRIVING_STYLE_HARD_LTURN", DriveQMetric.DRIVEQMETRIC_AGGRESSIVE_LEFT),
    SCORE_DRIVING_STYLE_HARD_RTURN(20, "SCORE_DRIVING_STYLE_HARD_RTURN", DriveQMetric.DRIVEQMETRIC_AGGRESSIVE_RIGHT);

    private String description;
    private int code;
    private int driveQMetric;
    private Duration duration;
    
    private ScoreType(int code, String description, int driveQMetric)
    {
        this.code = code;
        this.description = description;
        this.driveQMetric = driveQMetric;
    }
    private ScoreType(int code, String description, int driveQMetric, Duration duration)
    {
        this.code = code;
        this.description = description;
        this.driveQMetric = driveQMetric;
        this.duration = duration;
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
        Collections.addAll(subTypeList, ScoreType.SCORE_DRIVING_STYLE, ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL, ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE, ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP, ScoreType.SCORE_DRIVING_STYLE_HARD_TURN); 
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
    
    public String getKey()
    {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }

    @Override
    public String toString()
    {
        return this.description;
    }

    public int getDriveQMetric()
    {
        return driveQMetric;
    }

    public void setDriveQMetric(int driveQMetric)
    {
        this.driveQMetric = driveQMetric;
    }
    public Duration getDuration()
    {
        return duration;
    }
    public void setDuration(Duration duration)
    {
        this.duration = duration;
    }
    public String getDescription() {
        return description;
    }
}

