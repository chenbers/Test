package com.inthinc.pro.model;

import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.dao.annotations.Column;

public class DriveQMap extends BaseEntity
{
    private Integer startingOdometer;
    private Integer endingOdometer;
    private Integer odometer;
    private Integer overall;
    private Integer speeding;
    private Integer speeding1;
    private Integer speeding2;
    private Integer speeding3;
    private Integer speeding4;
    private Integer speeding5;
    private Integer drivingStyle;
    private Integer aggressiveBreak;
    private Integer aggressiveAccel;
    private Integer aggressiveTurn;
    private Integer aggressiveLeft;
    private Integer aggressiveRight;
    private Integer aggressiveBump;
    private Integer seatbelt;
    private Integer coaching;
    private Integer mpgLight;
    private Integer mpgMedium;
    private Integer mpgHeavy;
    private Integer idleLo;
    private Integer idleHi;
    private Integer driveTime;
    
    @Column(updateable = false)
    private transient Map<ScoreType, Integer> scoreMap = new HashMap<ScoreType, Integer>();
    
    public Integer getStartingOdometer()
    {
        return startingOdometer;
    }
    public void setStartingOdometer(Integer startingOdometer)
    {
        this.startingOdometer = startingOdometer;
    }
    public Integer getEndingOdometer()
    {
        return endingOdometer;
    }
    public void setEndingOdometer(Integer endingOdometer)
    {
        this.endingOdometer = endingOdometer;
    }
    public Integer getOdometer()
    {
        return odometer;
    }
    public void setOdometer(Integer odometer)
    {
        this.odometer = odometer;
    }
    public Integer getOverall()
    {
        return overall;
    }
    public void setOverall(Integer overall)
    {
        this.overall = overall;
        scoreMap.put(ScoreType.SCORE_OVERALL, overall);
    }
    public Integer getSpeeding()
    {
        return speeding;
    }
    public void setSpeeding(Integer speeding)
    {
        this.speeding = speeding;
        scoreMap.put(ScoreType.SCORE_SPEEDING, speeding);
    }
    public Integer getSpeeding1()
    {
        return speeding1;
    }
    public void setSpeeding1(Integer speeding1)
    {
        this.speeding1 = speeding1;
        scoreMap.put(ScoreType.SCORE_SPEEDING_21_30, speeding1);
    }
    public Integer getSpeeding2()
    {
        return speeding2;
    }
    public void setSpeeding2(Integer speeding2)
    {
        this.speeding2 = speeding2;
        scoreMap.put(ScoreType.SCORE_SPEEDING_31_40, speeding2);
    }
    public Integer getSpeeding3()
    {
        return speeding3;
    }
    public void setSpeeding3(Integer speeding3)
    {
        this.speeding3 = speeding3;
        scoreMap.put(ScoreType.SCORE_SPEEDING_41_54, speeding3);
    }
    public Integer getSpeeding4()
    {
        return speeding4;
    }
    public void setSpeeding4(Integer speeding4)
    {
        this.speeding4 = speeding4;
        scoreMap.put(ScoreType.SCORE_SPEEDING_55_64, speeding4);
    }
    public Integer getSpeeding5()
    {
        return speeding5;
    }
    public void setSpeeding5(Integer speeding5)
    {
        this.speeding5 = speeding5;
        scoreMap.put(ScoreType.SCORE_SPEEDING_65_80, speeding5);
    }
    public Integer getDrivingStyle()
    {
        return drivingStyle;
    }
    public void setDrivingStyle(Integer drivingStyle)
    {
        this.drivingStyle = drivingStyle;
        scoreMap.put(ScoreType.SCORE_DRIVING_STYLE, drivingStyle);
    }
    public Integer getAggressiveBreak()
    {
        return aggressiveBreak;
    }
    public void setAggressiveBreak(Integer aggressiveBreak)
    {
        this.aggressiveBreak = aggressiveBreak;
        scoreMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE, aggressiveBreak);
    }
    public Integer getAggressiveAccel()
    {
        return aggressiveAccel;
    }
    public void setAggressiveAccel(Integer aggressiveAccel)
    {
        this.aggressiveAccel = aggressiveAccel;
        scoreMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL, aggressiveAccel);
    }
    public Integer getAggressiveTurn()
    {
        return aggressiveTurn;
    }
    public void setAggressiveTurn(Integer aggressiveTurn)
    {
        this.aggressiveTurn = aggressiveTurn;
        scoreMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN, aggressiveTurn);
    }
    public Integer getAggressiveLeft()
    {
        return aggressiveLeft;
    }
    public void setAggressiveLeft(Integer aggressiveLeft)
    {
        this.aggressiveLeft = aggressiveLeft;
        scoreMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_LTURN, aggressiveLeft);
    }
    public Integer getAggressiveRight()
    {
        return aggressiveRight;
    }
    public void setAggressiveRight(Integer aggressiveRight)
    {
        this.aggressiveRight = aggressiveRight;
        scoreMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_RTURN, aggressiveRight);
    }
    public Integer getAggressiveBump()
    {
        return aggressiveBump;
    }
    public void setAggressiveBump(Integer aggressiveBump)
    {
        this.aggressiveBump = aggressiveBump;
        scoreMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP, aggressiveBump);
    }
    public Integer getSeatbelt()
    {
        return seatbelt;
    }
    public void setSeatbelt(Integer seatbelt)
    {
        this.seatbelt = seatbelt;
        scoreMap.put(ScoreType.SCORE_SEATBELT, seatbelt);
    }
    public Integer getCoaching()
    {
        return coaching;
    }
    public void setCoaching(Integer coaching)
    {
        this.coaching = coaching;
        scoreMap.put(ScoreType.SCORE_COACHING_EVENTS, coaching);
    }
    public Integer getMpgLight()
    {
        return mpgLight;
    }
    public void setMpgLight(Integer mpgLight)
    {
        this.mpgLight = mpgLight;
    }
    public Integer getMpgMedium()
    {
        return mpgMedium;
    }
    public void setMpgMedium(Integer mpgMedium)
    {
        this.mpgMedium = mpgMedium;
    }
    public Integer getMpgHeavy()
    {
        return mpgHeavy;
    }
    public void setMpgHeavy(Integer mpgHeavy)
    {
        this.mpgHeavy = mpgHeavy;
    }
    public Integer getIdleLo()
    {
        return idleLo;
    }
    public void setIdleLo(Integer idleLo)
    {
        this.idleLo = idleLo;
    }
    public Integer getIdleHi()
    {
        return idleHi;
    }
    public void setIdleHi(Integer idleHi)
    {
        this.idleHi = idleHi;
    }
    public Integer getDriveTime()
    {
        return driveTime;
    }
    public void setDriveTime(Integer driveTime)
    {
        this.driveTime = driveTime;
    }
    
    // maps from individual fields to our score types
    public Map<ScoreType, Integer> getScoreMap()
    {
        return scoreMap;
    }
}
