package com.inthinc.pro.model.aggregation;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Score {
    private Date startingDate;
    private Date endingDate;

    private Number aggressiveAccel;
    private Number aggressiveAccelEvents;
    private Number aggressiveBrake;
    private Number aggressiveBrakeEvents;
    private Number aggressiveBump;
    private Number aggressiveBumpEvents;
    private Number aggressiveEvents;
    private Number aggressiveLeftEvents;
    private Number aggressiveRightEvents;
    private Number aggressiveTurn;
    private Number avgSpeed;
    private Number coaching;
    private Number crashDays;
    private Number crashEvents;
    private Number crashOdometer;
    private Number crashTotal;
    private Number driveTime;
    private Number drivingStyle;
    private Number emuRpmDriveTime;
    private Number emuRpmVehicles;
    private Number endingOdometer;
    private Number idleHi;
    private Number idleHiEvents;
    private Number idleLo;
    private Number idleLoEvents;
    private Number mpgHeavy;
    private Number mpgLight;
    private Number mpgMedium;
    private Number nVehicles;
    private Number odometer;
    private Number odometer1;
    private Number odometer2;
    private Number odometer3;
    private Number odometer4;
    private Number odometer5;
    private Number overall;
    private Number rpmEvents;
    private Number seatbelt;
    private Number seatbeltCoaching;
    private Number seatbeltEvents;
    private Number speedCoaching;
    private Number speedCoaching1;
    private Number speedCoaching2;
    private Number speedCoaching3;
    private Number speedCoaching4;
    private Number speedCoaching5;
    private Number speedEvents;
    private Number speedEvents1;
    private Number speedEvents2;
    private Number speedEvents3;
    private Number speedEvents4;
    private Number speedEvents5;
    private Number speeding;
    private Number speeding1;
    private Number speeding2;
    private Number speeding3;
    private Number speeding4;
    private Number speeding5;
    private Number speedOdometer;
    private Number speedOdometer1;
    private Number speedOdometer2;
    private Number speedOdometer3;
    private Number speedOdometer4;
    private Number speedOdometer5;
    private Number speedOver;
    private Number speedOver1;
    private Number speedOver2;
    private Number speedOver3;
    private Number speedOver4;
    private Number speedOver5;
    private Number startingOdometer;
    private Number trips;
    private Number odometerLight;
	private Number odometerMedium;
    private Number odometerHeavy;
    private Number speedEvents1To7MphOver;
    private Number speedEvents8To14MphOver;
    private Number speedEvents15PlusMphOver;
    private Number speedEventsOver80Mph;
    
    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public Number getAggressiveAccel() {
        return aggressiveAccel;
    }

    public void setAggressiveAccel(Number aggressiveAccel) {
        this.aggressiveAccel = aggressiveAccel;
    }

    public Number getAggressiveAccelEvents() {
        return aggressiveAccelEvents;
    }

    public void setAggressiveAccelEvents(Number aggressiveAccelEvents) {
        this.aggressiveAccelEvents = aggressiveAccelEvents;
    }

    public Number getAggressiveBrake() {
        return aggressiveBrake;
    }

    public void setAggressiveBrake(Number aggressiveBrake) {
        this.aggressiveBrake = aggressiveBrake;
    }

    public Number getAggressiveBrakeEvents() {
        return aggressiveBrakeEvents;
    }

    public void setAggressiveBrakeEvents(Number aggressiveBrakeEvents) {
        this.aggressiveBrakeEvents = aggressiveBrakeEvents;
    }

    public Number getAggressiveBump() {
        return aggressiveBump;
    }

    public void setAggressiveBump(Number aggressiveBump) {
        this.aggressiveBump = aggressiveBump;
    }

    public Number getAggressiveBumpEvents() {
        return aggressiveBumpEvents;
    }

    public void setAggressiveBumpEvents(Number aggressiveBumpEvents) {
        this.aggressiveBumpEvents = aggressiveBumpEvents;
    }

    public Number getAggressiveEvents() {
        return aggressiveEvents;
    }

    public void setAggressiveEvents(Number aggressiveEvents) {
        this.aggressiveEvents = aggressiveEvents;
    }

    public Number getAggressiveLeftEvents() {
        return aggressiveLeftEvents;
    }

    public void setAggressiveLeftEvents(Number aggressiveLeftEvents) {
        this.aggressiveLeftEvents = aggressiveLeftEvents;
    }

    public Number getAggressiveRightEvents() {
        return aggressiveRightEvents;
    }

    public void setAggressiveRightEvents(Number aggressiveRightEvents) {
        this.aggressiveRightEvents = aggressiveRightEvents;
    }

    public Number getAggressiveTurn() {
        return aggressiveTurn;
    }

    public void setAggressiveTurn(Number aggressiveTurn) {
        this.aggressiveTurn = aggressiveTurn;
    }

    public Number getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(Number avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public Number getCoaching() {
        return coaching;
    }

    public void setCoaching(Number coaching) {
        this.coaching = coaching;
    }

    public Number getCrashDays() {
        return crashDays;
    }

    public void setCrashDays(Number crashDays) {
        this.crashDays = crashDays;
    }

    public Number getCrashEvents() {
        return crashEvents;
    }

    public void setCrashEvents(Number crashEvents) {
        this.crashEvents = crashEvents;
    }

    public Number getCrashOdometer() {
        return crashOdometer;
    }

    public void setCrashOdometer(Number crashOdometer) {
        this.crashOdometer = crashOdometer;
    }

    public Number getCrashTotal() {
        return crashTotal;
    }

    public void setCrashTotal(Number crashTotal) {
        this.crashTotal = crashTotal;
    }

    public Number getDriveTime() {
        return driveTime;
    }

    public void setDriveTime(Number driveTime) {
        this.driveTime = driveTime;
    }

    public Number getDrivingStyle() {
        return drivingStyle;
    }

    public void setDrivingStyle(Number drivingStyle) {
        this.drivingStyle = drivingStyle;
    }

    public Number getEmuRpmDriveTime() {
        return emuRpmDriveTime;
    }

    public void setEmuRpmDriveTime(Number emuRpmDriveTime) {
        this.emuRpmDriveTime = emuRpmDriveTime;
    }

    public Number getEmuRpmVehicles() {
        return emuRpmVehicles;
    }

    public void setEmuRpmVehicles(Number emuRpmVehicles) {
        this.emuRpmVehicles = emuRpmVehicles;
    }

    public Number getEndingOdometer() {
        return endingOdometer;
    }

    public void setEndingOdometer(Number endingOdometer) {
        this.endingOdometer = endingOdometer;
    }

    public Number getIdleHi() {
        return idleHi;
    }

    public void setIdleHi(Number idleHi) {
        this.idleHi = idleHi;
    }

    public Number getIdleHiEvents() {
        return idleHiEvents;
    }

    public void setIdleHiEvents(Number idleHiEvents) {
        this.idleHiEvents = idleHiEvents;
    }

    public Number getIdleLo() {
        return idleLo;
    }

    public void setIdleLo(Number idleLo) {
        this.idleLo = idleLo;
    }

    public Number getIdleLoEvents() {
        return idleLoEvents;
    }

    public void setIdleLoEvents(Number idleLoEvents) {
        this.idleLoEvents = idleLoEvents;
    }

    public Number getMpgHeavy() {
        return mpgHeavy;
    }

    public void setMpgHeavy(Number mpgHeavy) {
        this.mpgHeavy = mpgHeavy.doubleValue();
    }

    public Number getMpgLight() {
        return mpgLight;
    }

    public void setMpgLight(Number mpgLight) {
        this.mpgLight = mpgLight;
    }

    public Number getMpgMedium() {
        return mpgMedium;
    }

    public void setMpgMedium(Number mpgMedium) {
        this.mpgMedium = mpgMedium;
    }

    public Number getnVehicles() {
        return nVehicles;
    }

    public void setnVehicles(Number nVehicles) {
        this.nVehicles = nVehicles;
    }

    public Number getOdometer() {
        return odometer;
    }

    public void setOdometer(Number odometer) {
        this.odometer = odometer;
    }

    public Number getOdometer1() {
        return odometer1;
    }

    public void setOdometer1(Number odometer1) {
        this.odometer1 = odometer1;
    }

    public Number getOdometer2() {
        return odometer2;
    }

    public void setOdometer2(Number odometer2) {
        this.odometer2 = odometer2;
    }

    public Number getOdometer3() {
        return odometer3;
    }

    public void setOdometer3(Number odometer3) {
        this.odometer3 = odometer3;
    }

    public Number getOdometer4() {
        return odometer4;
    }

    public void setOdometer4(Number odometer4) {
        this.odometer4 = odometer4;
    }

    public Number getOdometer5() {
        return odometer5;
    }

    public void setOdometer5(Number odometer5) {
        this.odometer5 = odometer5;
    }

    public Number getOverall() {
        return overall;
    }

    public void setOverall(Number overall) {
        this.overall = overall;
    }

    public Number getRpmEvents() {
        return rpmEvents;
    }

    public void setRpmEvents(Number rpmEvents) {
        this.rpmEvents = rpmEvents;
    }

    public Number getSeatbelt() {
        return seatbelt;
    }

    public void setSeatbelt(Number seatbelt) {
        this.seatbelt = seatbelt;
    }

    public Number getSeatbeltCoaching() {
        return seatbeltCoaching;
    }

    public void setSeatbeltCoaching(Number seatbeltCoaching) {
        this.seatbeltCoaching = seatbeltCoaching;
    }

    public Number getSeatbeltEvents() {
        return seatbeltEvents;
    }

    public void setSeatbeltEvents(Number seatbeltEvents) {
        this.seatbeltEvents = seatbeltEvents;
    }

    public Number getSpeedCoaching() {
        return speedCoaching;
    }

    public void setSpeedCoaching(Number speedCoaching) {
        this.speedCoaching = speedCoaching;
    }

    public Number getSpeedCoaching1() {
        return speedCoaching1;
    }

    public void setSpeedCoaching1(Number speedCoaching1) {
        this.speedCoaching1 = speedCoaching1;
    }

    public Number getSpeedCoaching2() {
        return speedCoaching2;
    }

    public void setSpeedCoaching2(Number speedCoaching2) {
        this.speedCoaching2 = speedCoaching2;
    }

    public Number getSpeedCoaching3() {
        return speedCoaching3;
    }

    public void setSpeedCoaching3(Number speedCoaching3) {
        this.speedCoaching3 = speedCoaching3;
    }

    public Number getSpeedCoaching4() {
        return speedCoaching4;
    }

    public void setSpeedCoaching4(Number speedCoaching4) {
        this.speedCoaching4 = speedCoaching4;
    }

    public Number getSpeedCoaching5() {
        return speedCoaching5;
    }

    public void setSpeedCoaching5(Number speedCoaching5) {
        this.speedCoaching5 = speedCoaching5;
    }

    public Number getSpeedEvents() {
        return speedEvents;
    }

    public void setSpeedEvents(Number speedEvents) {
        this.speedEvents = speedEvents;
    }

    public Number getSpeedEvents1() {
        return speedEvents1;
    }

    public void setSpeedEvents1(Number speedEvents1) {
        this.speedEvents1 = speedEvents1;
    }

    public Number getSpeedEvents2() {
        return speedEvents2;
    }

    public void setSpeedEvents2(Number speedEvents2) {
        this.speedEvents2 = speedEvents2;
    }

    public Number getSpeedEvents3() {
        return speedEvents3;
    }

    public void setSpeedEvents3(Number speedEvents3) {
        this.speedEvents3 = speedEvents3;
    }

    public Number getSpeedEvents4() {
        return speedEvents4;
    }

    public void setSpeedEvents4(Number speedEvents4) {
        this.speedEvents4 = speedEvents4;
    }

    public Number getSpeedEvents5() {
        return speedEvents5;
    }

    public void setSpeedEvents5(Number speedEvents5) {
        this.speedEvents5 = speedEvents5;
    }

    public Number getSpeeding() {
        return speeding;
    }

    public void setSpeeding(Number speeding) {
        this.speeding = speeding;
    }

    public Number getSpeeding1() {
        return speeding1;
    }

    public void setSpeeding1(Number speeding1) {
        this.speeding1 = speeding1;
    }

    public Number getSpeeding2() {
        return speeding2;
    }

    public void setSpeeding2(Number speeding2) {
        this.speeding2 = speeding2;
    }

    public Number getSpeeding3() {
        return speeding3;
    }

    public void setSpeeding3(Number speeding3) {
        this.speeding3 = speeding3;
    }

    public Number getSpeeding4() {
        return speeding4;
    }

    public void setSpeeding4(Number speeding4) {
        this.speeding4 = speeding4;
    }

    public Number getSpeeding5() {
        return speeding5;
    }

    public void setSpeeding5(Number speeding5) {
        this.speeding5 = speeding5;
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

    public Number getSpeedOver() {
        return speedOver;
    }

    public void setSpeedOver(Number speedOver) {
        this.speedOver = speedOver;
    }

    public Number getSpeedOver1() {
        return speedOver1;
    }

    public void setSpeedOver1(Number speedOver1) {
        this.speedOver1 = speedOver1;
    }

    public Number getSpeedOver2() {
        return speedOver2;
    }

    public void setSpeedOver2(Number speedOver2) {
        this.speedOver2 = speedOver2;
    }

    public Number getSpeedOver3() {
        return speedOver3;
    }

    public void setSpeedOver3(Number speedOver3) {
        this.speedOver3 = speedOver3;
    }

    public Number getSpeedOver4() {
        return speedOver4;
    }

    public void setSpeedOver4(Number speedOver4) {
        this.speedOver4 = speedOver4;
    }

    public Number getSpeedOver5() {
        return speedOver5;
    }

    public void setSpeedOver5(Number speedOver5) {
        this.speedOver5 = speedOver5;
    }

    public Number getStartingOdometer() {
        return startingOdometer;
    }

    public void setStartingOdometer(Number startingOdometer) {
        this.startingOdometer = startingOdometer;
    }

    public Number getTrips() {
        return trips;
    }

    public void setTrips(Number trips) {
        this.trips = trips;
    }
    public Number getOdometerLight() {
		return odometerLight;
	}

	public void setOdometerLight(Number odometerLight) {
		this.odometerLight = odometerLight;
	}

	public Number getOdometerMedium() {
		return odometerMedium;
	}

	public void setOdometerMedium(Number odometerMedium) {
		this.odometerMedium = odometerMedium;
	}

	public Number getOdometerHeavy() {
		return odometerHeavy;
	}

	public void setOdometerHeavy(Number odometerHeavy) {
		this.odometerHeavy = odometerHeavy;
	}


    // getter methods that aggregate some of the existing fields
    public Number getSafetyTotal() {
    	return ((seatbeltEvents == null) ? 0 : seatbeltEvents.longValue()) + 
    			((speedEvents == null) ? 0 : speedEvents.longValue()) + 
    			((aggressiveAccelEvents == null) ? 0 : aggressiveAccelEvents.longValue()) + 
    			((aggressiveBrakeEvents == null) ? 0 : aggressiveBrakeEvents.longValue()) + 
    			((aggressiveBumpEvents == null) ? 0 : aggressiveBumpEvents.longValue()) + 
    			((aggressiveLeftEvents == null) ? 0 : aggressiveLeftEvents.longValue()) + 
    			((aggressiveRightEvents == null) ? 0 : aggressiveRightEvents.longValue());
    }
    public Number getWeightedMpg() {
        double totalMpg = (mpgHeavy == null ? 0 : mpgHeavy.doubleValue()/100) * (odometerHeavy == null ? 0 : odometerHeavy.doubleValue()/100d)  +
        				  (mpgMedium == null ? 0 : mpgMedium.doubleValue()/100)  * (odometerMedium == null ? 0 : odometerMedium.doubleValue()/100d)  +
        				  (mpgLight == null ? 0 : mpgLight.doubleValue()/100) * (odometerLight == null ? 0 : odometerLight.doubleValue() / 100d);
        double totalMiles = (odometerHeavy == null ? 0 : odometerHeavy.doubleValue())  +
		  					(odometerMedium == null ? 0 : odometerMedium.doubleValue())  +
		  					(odometerLight == null ? 0 : odometerLight.doubleValue());
        if (totalMiles == 0)
        	return 0d;
    	return (totalMpg / (totalMiles/100d));
    }
    public Number getMilesDriven() {
    	return ((endingOdometer == null) ? 0l : endingOdometer.longValue()) - 
    			((startingOdometer == null) ? 0l : startingOdometer.longValue());  
    }
    public Number getIdleTotal() {
    	return ((idleLo == null) ? 0l : idleLo.longValue()) + 
    			((idleHi == null) ? 0l : idleHi.longValue());  
    }
    public Number getIdlePercent() {
    	return (driveTime == null || driveTime.longValue() == 0l) ? 0 : (getIdleTotal().doubleValue() * 100.0)/driveTime.doubleValue();
    }
    public Number getSpeedEvents1To7MphOver() {
        return speedEvents1To7MphOver;
    }

    public void setSpeedEvents1To7MphOver(Number speedEvents1To7MphOver) {
        this.speedEvents1To7MphOver = speedEvents1To7MphOver;
    }

    public Number getSpeedEvents8To14MphOver() {
        return speedEvents8To14MphOver;
    }

    public void setSpeedEvents8To14MphOver(Number speedEvents8To14MphOver) {
        this.speedEvents8To14MphOver = speedEvents8To14MphOver;
    }

    public Number getSpeedEvents15PlusMphOver() {
        return speedEvents15PlusMphOver;
    }

    public void setSpeedEvents15PlusMphOver(Number speedEvents15PlusMphOver) {
        this.speedEvents15PlusMphOver = speedEvents15PlusMphOver;
    }
    public Number getSpeedEventsOver80Mph() {
        return speedEventsOver80Mph;
    }

    public void setSpeedEventsOver80Mph(Number speedEventsOver80Mph) {
        this.speedEventsOver80Mph = speedEventsOver80Mph;
    }



    @Override
    public String toString() {
        return "Score [aggressiveAccel=" + aggressiveAccel + ", aggressiveAccelEvents=" + aggressiveAccelEvents + ", aggressiveBrake=" + aggressiveBrake + ", aggressiveBrakeEvents="
                + aggressiveBrakeEvents + ", aggressiveBump=" + aggressiveBump + ", aggressiveBumpEvents=" + aggressiveBumpEvents + ", aggressiveEvents=" + aggressiveEvents
                + ", aggressiveLeftEvents=" + aggressiveLeftEvents + ", aggressiveRightEvents=" + aggressiveRightEvents + ", aggressiveTurn=" + aggressiveTurn + ", avgSpeed=" + avgSpeed
                + ", coaching=" + coaching + ", crashDays=" + crashDays + ", crashEvents=" + crashEvents + ", crashOdometer=" + crashOdometer + ", crashTotal=" + crashTotal + ", driveTime="
                + driveTime + ", drivingStyle=" + drivingStyle + ", emuRpmDriveTime=" + emuRpmDriveTime + ", emuRpmVehicles=" + emuRpmVehicles + ", endingDate=" + endingDate + ", endingOdometer="
                + endingOdometer + ", idleHi=" + idleHi + ", idleHiEvents=" + idleHiEvents + ", idleLo=" + idleLo + ", idleLoEvents=" + idleLoEvents + ", mpgHeavy=" + mpgHeavy + ", mpgLight="
                + mpgLight + ", mpgMedium=" + mpgMedium + ", nVehicles=" + nVehicles + ", odometer=" + odometer + ", odometer1=" + odometer1 + ", odometer2=" + odometer2 + ", odometer3=" + odometer3
                + ", odometer4=" + odometer4 + ", odometer5=" + odometer5 + ", overall=" + overall + ", rpmEvents=" + rpmEvents + ", seatbelt=" + seatbelt + ", seatbeltCoaching=" + seatbeltCoaching
                + ", seatbeltEvents=" + seatbeltEvents + ", speedCoaching=" + speedCoaching + ", speedCoaching1=" + speedCoaching1 + ", speedCoaching2=" + speedCoaching2 + ", speedCoaching3="
                + speedCoaching3 + ", speedCoaching4=" + speedCoaching4 + ", speedCoaching5=" + speedCoaching5 + ", speedEvents=" + speedEvents + ", speedEvents1=" + speedEvents1 + ", speedEvents2="
                + speedEvents2 + ", speedEvents3=" + speedEvents3 + ", speedEvents4=" + speedEvents4 + ", speedEvents5=" + speedEvents5 + ", speedOdometer=" + speedOdometer + ", speedOdometer1="
                + speedOdometer1 + ", speedOdometer2=" + speedOdometer2 + ", speedOdometer3=" + speedOdometer3 + ", speedOdometer4=" + speedOdometer4 + ", speedOdometer5=" + speedOdometer5
                + ", speedOver=" + speedOver + ", speedOver1=" + speedOver1 + ", speedOver2=" + speedOver2 + ", speedOver3=" + speedOver3 + ", speedOver4=" + speedOver4 + ", speedOver5=" + speedOver5
                + ", speeding=" + speeding + ", speeding1=" + speeding1 + ", speeding2=" + speeding2 + ", speeding3=" + speeding3 + ", speeding4=" + speeding4 + ", speeding5=" + speeding5
                + ", startingDate=" + startingDate + ", startingOdometer=" + startingOdometer + ", trips=" + trips + "]";
    }

}
