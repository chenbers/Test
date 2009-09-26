package com.inthinc.pro.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.dao.annotations.Column;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DriveQMap extends BaseEntity
{
    private Integer startingOdometer;
    private Integer endingOdometer;				// mileage over all time
    private Integer odometer;					// mileage in time period
    private Integer odometer1;
    private Integer odometer2;
    private Integer odometer3;
    private Integer odometer4;
    private Integer odometer5;
    private Integer overall;
    private Integer speeding;
    private Integer speeding1;
    private Integer speeding2;
    private Integer speeding3;
    private Integer speeding4;
    private Integer speeding5;
    private Integer drivingStyle;
    private Integer aggressiveBrake;
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
    // CRASH SUMMARY  
    private Integer crashEvents;			// total crashes in the time period			
    private Integer crashTotal;				// total crashes ever
	private Integer crashOdometer;
    private Integer crashDays;
    // END - CRASH SUMMARY
    
    // not sure what these are for
	private Date startingDate;
	private Date endingDate;
    // TODO: these will change to Longs in the future, but leaving as Integer now for consistency
	private Integer speedOdometer;
    private Integer speedOdometer1;
    private Integer speedOdometer2;
    private Integer speedOdometer3;
    private Integer speedOdometer4;
    private Integer speedOdometer5;
    
    
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
    public Integer getOdometer1()
    {
        return odometer1;
    }
    public void setOdometer1(Integer odometer1)
    {
        this.odometer1 = odometer1;
    }
    public Integer getOdometer2()
    {
        return odometer2;
    }
    public void setOdometer2(Integer odometer2)
    {
        this.odometer2 = odometer2;
    }
    public Integer getOdometer3()
    {
        return odometer3;
    }
    public void setOdometer3(Integer odometer3)
    {
        this.odometer3 = odometer3;
    }
    public Integer getOdometer4()
    {
        return odometer4;
    }
    public void setOdometer4(Integer odometer4)
    {
        this.odometer4 = odometer4;
    }
    public Integer getOdometer5()
    {
        return odometer5;
    }
    public void setOdometer5(Integer odometer5)
    {
        this.odometer5 = odometer5;
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
    public Integer getAggressiveBrake()
    {
        return aggressiveBrake;
    }
    public void setAggressiveBrake(Integer aggressiveBrake)
    {
        this.aggressiveBrake = aggressiveBrake;
        scoreMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE, aggressiveBrake);
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
    public Date getStartingDate()
    {
        return startingDate;
    }
    public void setStartingDate(Date startingDate)
    {
        this.startingDate = startingDate;
    }
    public Date getEndingDate()
    {
        return endingDate;
    }
    public void setEndingDate(Date endingDate)
    {
        this.endingDate = endingDate;
    }
	public Integer getCrashOdometer() {
		return crashOdometer;
	}
	public void setCrashOdometer(Integer crashOdometer) {
		this.crashOdometer = crashOdometer;
	}
    public Integer getCrashTotal() {
		return crashTotal;
	}
	public void setCrashTotal(Integer crashTotal) {
		this.crashTotal = crashTotal;
	}
	public Integer getCrashDays() {
		return crashDays;
	}
	public void setCrashDays(Integer crashDays) {
		this.crashDays = crashDays;
	}
    public Integer getCrashEvents() {
		return crashEvents;
	}
	public void setCrashEvents(Integer crashEvents) {
		this.crashEvents = crashEvents;
	}
	public Integer getSpeedOdometer() {
		return speedOdometer;
	}
	public void setSpeedOdometer(Integer speedOdometer) {
		this.speedOdometer = speedOdometer;
	}
	public Integer getSpeedOdometer1() {
		return speedOdometer1;
	}
	public void setSpeedOdometer1(Integer speedOdometer1) {
		this.speedOdometer1 = speedOdometer1;
	}
	public Integer getSpeedOdometer2() {
		return speedOdometer2;
	}
	public void setSpeedOdometer2(Integer speedOdometer2) {
		this.speedOdometer2 = speedOdometer2;
	}
	public Integer getSpeedOdometer3() {
		return speedOdometer3;
	}
	public void setSpeedOdometer3(Integer speedOdometer3) {
		this.speedOdometer3 = speedOdometer3;
	}
	public Integer getSpeedOdometer4() {
		return speedOdometer4;
	}
	public void setSpeedOdometer4(Integer speedOdometer4) {
		this.speedOdometer4 = speedOdometer4;
	}
	public Integer getSpeedOdometer5() {
		return speedOdometer5;
	}
	public void setSpeedOdometer5(Integer speedOdometer5) {
		this.speedOdometer5 = speedOdometer5;
	}
}
