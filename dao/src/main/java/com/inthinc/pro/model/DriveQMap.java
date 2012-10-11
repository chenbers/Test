package com.inthinc.pro.model;

import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.dao.annotations.Column;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DriveQMap extends BaseEntity
{
    private Number startingOdometer;
    private Number endingOdometer;				// mileage over all time
    private Number odometer;					// mileage in time period
    private Number odometer1;
    private Number odometer2;
    private Number odometer3;
    private Number odometer4;
    private Number odometer5;
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
    private Number idleLo;
    private Number idleHi;
    private Number driveTime;
    private Number emuRpmDriveTime;
    private Integer emuRpmVehicles;
	private Integer nVehicles;
	// CRASH SUMMARY  
    private Integer crashEvents;			// total crashes in the time period			
    private Integer crashTotal;				// total crashes ever
	private Number crashOdometer;
    private Integer crashDays;
    // END - CRASH SUMMARY
    
	private Date startingDate;
	private Date endingDate;
	private Number speedOdometer;
    private Number speedOdometer1;
    private Number speedOdometer2;
    private Number speedOdometer3;
    private Number speedOdometer4;
    private Number speedOdometer5;
    
/*
 * These fields are also available in the driveQ map, but excluding for now in the interest of performance since we don't use these fields.
		aggressiveBrakeEvents
		seatbeltEvents
		speedEvents
		speedEvents1
		speedEvents2
		speedEvents3
		speedEvents4
		speedEvents5
		idleLoEvents
		idleHiEvents
		rpmEvents
		aggressiveEvents
		aggressiveAccelEvents
		aggressiveBumpEvents
		aggressiveRightEvents
		aggressiveLeftEvents
		
		speedCoaching
		speedCoaching1
		speedCoaching2
		speedCoaching3
		speedCoaching4
		speedCoaching5
		seatbeltCoaching
		
		speedOver
		speedOver4
		avgSpeed
		trips
 * 
 */
	@Column(updateable = false)
    private transient Map<ScoreType, Integer> scoreMap = new HashMap<ScoreType, Integer>();
    
    public Number getStartingOdometer()
    {
        return startingOdometer;
    }
    public void setStartingOdometer(Number startingOdometer)
    {
        this.startingOdometer = startingOdometer;
    }
    public Number getEndingOdometer()
    {
        return endingOdometer;
    }
    public void setEndingOdometer(Number endingOdometer)
    {
        this.endingOdometer = endingOdometer;
    }
    public Number getOdometer()
    {
        return odometer;
    }
    public void setOdometer(Number odometer)
    {
        this.odometer = odometer;
    }
    public Number getOdometer1()
    {
        return odometer1;
    }
    public void setOdometer1(Number odometer1)
    {
        this.odometer1 = odometer1;
    }
    public Number getOdometer2()
    {
        return odometer2;
    }
    public void setOdometer2(Number odometer2)
    {
        this.odometer2 = odometer2;
    }
    public Number getOdometer3()
    {
        return odometer3;
    }
    public void setOdometer3(Number odometer3)
    {
        this.odometer3 = odometer3;
    }
    public Number getOdometer4()
    {
        return odometer4;
    }
    public void setOdometer4(Number odometer4)
    {
        this.odometer4 = odometer4;
    }
    public Number getOdometer5()
    {
        return odometer5;
    }
    public void setOdometer5(Number odometer5)
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
        getScoreMap().put(ScoreType.SCORE_OVERALL, overall);
    }
    public Integer getSpeeding()
    {
        return speeding;
    }
    public void setSpeeding(Integer speeding)
    {
        this.speeding = speeding;
        getScoreMap().put(ScoreType.SCORE_SPEEDING, speeding);
    }
    public Integer getSpeeding1()
    {
        return speeding1;
    }
    public void setSpeeding1(Integer speeding1)
    {
        this.speeding1 = speeding1;
        getScoreMap().put(ScoreType.SCORE_SPEEDING_21_30, speeding1);
    }
    public Integer getSpeeding2()
    {
        return speeding2;
    }
    public void setSpeeding2(Integer speeding2)
    {
        this.speeding2 = speeding2;
        getScoreMap().put(ScoreType.SCORE_SPEEDING_31_40, speeding2);
    }
    public Integer getSpeeding3()
    {
        return speeding3;
    }
    public void setSpeeding3(Integer speeding3)
    {
        this.speeding3 = speeding3;
        getScoreMap().put(ScoreType.SCORE_SPEEDING_41_54, speeding3);
    }
    public Integer getSpeeding4()
    {
        return speeding4;
    }
    public void setSpeeding4(Integer speeding4)
    {
        this.speeding4 = speeding4;
        getScoreMap().put(ScoreType.SCORE_SPEEDING_55_64, speeding4);
    }
    public Integer getSpeeding5()
    {
        return speeding5;
    }
    public void setSpeeding5(Integer speeding5)
    {
        this.speeding5 = speeding5;
        getScoreMap().put(ScoreType.SCORE_SPEEDING_65_80, speeding5);
    }
    public Integer getDrivingStyle()
    {
        return drivingStyle;
    }
    public void setDrivingStyle(Integer drivingStyle)
    {
        this.drivingStyle = drivingStyle;
        getScoreMap().put(ScoreType.SCORE_DRIVING_STYLE, drivingStyle);
    }
    public Integer getAggressiveBrake()
    {
        return aggressiveBrake;
    }
    public void setAggressiveBrake(Integer aggressiveBrake)
    {
        this.aggressiveBrake = aggressiveBrake;
        getScoreMap().put(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE, aggressiveBrake);
    }
    public Integer getAggressiveAccel()
    {
        return aggressiveAccel;
    }
    public void setAggressiveAccel(Integer aggressiveAccel)
    {
        this.aggressiveAccel = aggressiveAccel;
        getScoreMap().put(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL, aggressiveAccel);
    }
    public Integer getAggressiveTurn()
    {
        return aggressiveTurn;
    }
    public void setAggressiveTurn(Integer aggressiveTurn)
    {
        this.aggressiveTurn = aggressiveTurn;
        getScoreMap().put(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN, aggressiveTurn);
    }
    public Integer getAggressiveLeft()
    {
        return aggressiveLeft;
    }
    public void setAggressiveLeft(Integer aggressiveLeft)
    {
        this.aggressiveLeft = aggressiveLeft;
        getScoreMap().put(ScoreType.SCORE_DRIVING_STYLE_HARD_LTURN, aggressiveLeft);
    }
    public Integer getAggressiveRight()
    {
        return aggressiveRight;
    }
    public void setAggressiveRight(Integer aggressiveRight)
    {
        this.aggressiveRight = aggressiveRight;
        getScoreMap().put(ScoreType.SCORE_DRIVING_STYLE_HARD_RTURN, aggressiveRight);
    }
    public Integer getAggressiveBump()
    {
        return aggressiveBump;
    }
    public void setAggressiveBump(Integer aggressiveBump)
    {
        this.aggressiveBump = aggressiveBump;
        getScoreMap().put(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP, aggressiveBump);
    }
    public Integer getSeatbelt()
    {
        return seatbelt;
    }
    public void setSeatbelt(Integer seatbelt)
    {
        this.seatbelt = seatbelt;
        getScoreMap().put(ScoreType.SCORE_SEATBELT, seatbelt);
    }
    public Integer getCoaching()
    {
        return coaching;
    }
    public void setCoaching(Integer coaching)
    {
        this.coaching = coaching;
        getScoreMap().put(ScoreType.SCORE_COACHING_EVENTS, coaching);
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
    public Number getIdleLo()
    {
        return idleLo;
    }
    public void setIdleLo(Number idleLo)
    {
        this.idleLo = idleLo;
    }
    public Number getIdleHi()
    {
        return idleHi;
    }
    public void setIdleHi(Number idleHi)
    {
        this.idleHi = idleHi;
    }
    public Number getDriveTime()
    {
        return driveTime;
    }
    public void setDriveTime(Number driveTime)
    {
        this.driveTime = driveTime;
    }
    public Number getEmuRpmDriveTime() {
		return emuRpmDriveTime;
	}
	public void setEmuRpmDriveTime(Number emuRpmDriveTime) {
		this.emuRpmDriveTime = emuRpmDriveTime;
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
	public Number getCrashOdometer() {
		return crashOdometer;
	}
	public void setCrashOdometer(Number crashOdometer) {
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
	public Number getSpeedOdometer() {
		return speedOdometer;
	}
	public void setSpeedOdometer(Number speedOdometer) {
		this.speedOdometer = speedOdometer;
	}
	public Number getSpeedOdometer1() {
		return speedOdometer1;
	}
	public void setSpeedOdometer1(Number speedOdometer1) {
		this.speedOdometer1 = speedOdometer1;
	}
	public Number getSpeedOdometer2() {
		return speedOdometer2;
	}
	public void setSpeedOdometer2(Number speedOdometer2) {
		this.speedOdometer2 = speedOdometer2;
	}
	public Number getSpeedOdometer3() {
		return speedOdometer3;
	}
	public void setSpeedOdometer3(Number speedOdometer3) {
		this.speedOdometer3 = speedOdometer3;
	}
	public Number getSpeedOdometer4() {
		return speedOdometer4;
	}
	public void setSpeedOdometer4(Number speedOdometer4) {
		this.speedOdometer4 = speedOdometer4;
	}
	public Number getSpeedOdometer5() {
		return speedOdometer5;
	}
	public void setSpeedOdometer5(Number speedOdometer5) {
		this.speedOdometer5 = speedOdometer5;
	}
    public Integer getEmuRpmVehicles() {
		return emuRpmVehicles;
	}
	public void setEmuRpmVehicles(Integer emuRpmVehicles) {
		this.emuRpmVehicles = emuRpmVehicles;
	}
	public Integer getnVehicles() {
		return nVehicles;
	}
	public void setnVehicles(Integer nVehicles) {
		this.nVehicles = nVehicles;
	}
}
