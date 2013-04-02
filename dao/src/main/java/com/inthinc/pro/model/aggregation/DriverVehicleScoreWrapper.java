package com.inthinc.pro.model.aggregation;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import org.apache.log4j.Logger;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.util.NumberUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Vehicle;

@XmlRootElement
public class DriverVehicleScoreWrapper implements Comparable<DriverVehicleScoreWrapper> {
	private static final Logger logger = Logger.getLogger(DriverVehicleScoreWrapper.class);

    private Driver driver;
    private Vehicle vehicle;
    @Column(name="driveQ")
    private Score score;
    private String scoreStyle;
    private Boolean summary;

	public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    
    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public String getScoreStyle() {
        return scoreStyle;
    }

    public void setScoreStyle(String scoreStyle) {
        this.scoreStyle = scoreStyle;
    }

    public Boolean getSummary() {
		return summary;
	}

	public void setSummary(Boolean summary) {
		this.summary = summary;
	}


    @Override
    public String toString() {
        return "DriverVehicleScoreWrapper [driver=" + driver + ", score=" + score + ", vehicle=" + vehicle + "]";
    }


    public static DriverVehicleScoreWrapper summarize(List<DriverVehicleScoreWrapper> driverStatisticsList, Group group)
    {
        DriverVehicleScoreWrapper dvsw = new DriverVehicleScoreWrapper();
        
        // Score
        int totScore = 0;
        int totTrips = 0;
        int totIdleHi = 0;
        int totIdleLo = 0;
        int totIdleHiEvt = 0;
        int totIdleLoEvt = 0;
        int totMilesDriven = 0;
        int totDriveTime = 0;
//        float totMpg = 0;
        double totMpgLight = 0;
        double totMpgMedium = 0;
        double totMpgHeavy = 0;
        double totMilesLight = 0;
        double totMilesMedium = 0;
        double totMilesHeavy = 0;
        int totCrash = 0;
        int totSeatBeltEvt = 0;
        int totSeatBeltClicks = 0;
        int totSpeedEvt = 0;
        int totAggAccelEvt = 0;
        int totAggBrakeEvt = 0;
        int totAggBumpEvt = 0;
        int totAggLeftEvt = 0;
        int totAggRightEvt = 0;   
        
        float totActiveDrivers = 0;
        int totHeavyDrivers = 0;
        int totMediumDrivers = 0;
        int totLightDrivers = 0;
        int totScoringDrivers = 0;
        
        // Crash related
        int totCrashes = 0;
        int milesSinceLastCrash = 1000000;
        int daysSinceLastCrash = 1000000;
        
        if (driverStatisticsList==null) return null;
        
        for ( DriverVehicleScoreWrapper dvsc: driverStatisticsList ) { 
            if ( (dvsc.getScore().getOverall() != null) && 
                 (dvsc.getScore().getOverall().intValue() >= 0) ) {
                totScore += dvsc.getScore().getOverall().intValue();
                totScoringDrivers++;                
            }
            if ( dvsc.getScore().getTrips() != null ) {
                totTrips += dvsc.getScore().getTrips().intValue();
            }
            if ( dvsc.getScore().getIdleHi() != null ) {
                totIdleHi += dvsc.getScore().getIdleHi().intValue();
            }
            if ( dvsc.getScore().getIdleLo() != null ) {
                totIdleLo += dvsc.getScore().getIdleLo().intValue();
            }             
            if ( dvsc.getScore().getIdleHiEvents() != null ) {
                totIdleHiEvt += dvsc.getScore().getIdleHiEvents().intValue();
            }
            if ( dvsc.getScore().getIdleLoEvents() != null ) {
                totIdleLoEvt += dvsc.getScore().getIdleLoEvents().intValue();
            }            
/*            
            if ( dvsc.getScore().getEndingOdometer() != null && dvsc.getScore().getStartingOdometer() != null ) {
                totMilesDriven += ( dvsc.getScore().getEndingOdometer().intValue() - dvsc.getScore().getStartingOdometer().intValue() );
            }
*/
            if ( dvsc.getScore().getOdometer6() != null ) {
                totMilesDriven += dvsc.getScore().getOdometer6().intValue();
            }            
            
            if ( dvsc.getScore().getDriveTime() != null ) {
                totDriveTime += dvsc.getScore().getDriveTime().intValue();
            }            
            
            if (dvsc.getScore().getMpgHeavy() != null ||
            		dvsc.getScore().getMpgMedium() != null ||
            		dvsc.getScore().getMpgLight() != null) {
                
                if(dvsc.getScore().getMpgHeavy() != null && NumberUtil.intValue(dvsc.getScore().getOdometerHeavy())>0){
                    totHeavyDrivers++;
                    totMpgHeavy += (dvsc.getScore().getMpgHeavy() == null) ? 0 : dvsc.getScore().getMpgHeavy().doubleValue();
                    totMilesHeavy += (dvsc.getScore().getOdometerHeavy()== null) ? 0d : dvsc.getScore().getOdometerHeavy().doubleValue();
                }
                if(dvsc.getScore().getMpgMedium() != null && NumberUtil.intValue(dvsc.getScore().getOdometerMedium())>0){
                    totMediumDrivers++;
                    totMpgMedium += (dvsc.getScore().getMpgMedium() == null) ? 0 : dvsc.getScore().getMpgMedium().doubleValue();
                    totMilesMedium += (dvsc.getScore().getOdometerMedium()== null) ? 0d : dvsc.getScore().getOdometerMedium().doubleValue();
                }
                if(dvsc.getScore().getMpgLight() != null && NumberUtil.intValue(dvsc.getScore().getOdometerLight())>0){
                    totLightDrivers++;
                    totMpgLight += (dvsc.getScore().getMpgLight() == null) ? 0 : dvsc.getScore().getMpgLight().doubleValue();
                    totMilesLight += (dvsc.getScore().getOdometerLight()== null) ? 0d : dvsc.getScore().getOdometerLight().doubleValue();
                }
            }

            
            
            if ( dvsc.getScore().getCrashEvents() != null ) {
                totCrash += dvsc.getScore().getCrashEvents().intValue();
            }
            if ( dvsc.getScore().getSeatbeltEvents() != null ) {
                totSeatBeltEvt += dvsc.getScore().getSeatbeltEvents().intValue();
            }
            if ( dvsc.getScore().getSeatbeltClicks() != null ) {
                totSeatBeltClicks += dvsc.getScore().getSeatbeltClicks().intValue();
            }
            if ( dvsc.getScore().getSpeedEvents() != null ) {
                totSpeedEvt += dvsc.getScore().getSpeedEvents().intValue();
            }
            if ( dvsc.getScore().getAggressiveAccelEvents() != null ) {
                totAggAccelEvt += dvsc.getScore().getAggressiveAccelEvents().intValue();
            }
            if ( dvsc.getScore().getAggressiveBrakeEvents() != null ) {
                totAggBrakeEvt += dvsc.getScore().getAggressiveBrakeEvents().intValue();
            }
            if ( dvsc.getScore().getAggressiveBumpEvents() != null ) {
                totAggBumpEvt += dvsc.getScore().getAggressiveBumpEvents().intValue();
            }
            if ( dvsc.getScore().getAggressiveLeftEvents() != null ) {
                totAggLeftEvt += dvsc.getScore().getAggressiveLeftEvents().intValue();
            }
            if ( dvsc.getScore().getAggressiveRightEvents() != null ) {
                totAggRightEvt += dvsc.getScore().getAggressiveRightEvents().intValue();
            }
            
            // Crash stuff, do days or miles define the most recent? (Use both for now)
            if ( dvsc.getScore().getCrashTotal() != null ) {
                totCrashes += dvsc.getScore().getCrashTotal().intValue();
            }
            if ( dvsc.getScore().getCrashDays() != null ) {
                if ( dvsc.getScore().getCrashDays().intValue() < daysSinceLastCrash ) {
                    daysSinceLastCrash = dvsc.getScore().getCrashDays().intValue();
                }
            }
            if ( dvsc.getScore().getCrashOdometer() != null ) {
                if ( dvsc.getScore().getCrashOdometer().intValue() < milesSinceLastCrash ) {
                    milesSinceLastCrash = dvsc.getScore().getCrashOdometer().intValue();
                }
            }
        }
        
        // The total miles are determined by setting ending to the total
        //  and starting to 0. 
        Score tmp = new Score();
        tmp.setOverall((totScoringDrivers != 0 && totScore != 0)?totScore/totScoringDrivers:50);
        tmp.setTrips(totTrips);
        tmp.setIdleHi(totIdleHi);
        tmp.setIdleLo(totIdleLo);
        tmp.setIdleHiEvents(totIdleHiEvt);
        tmp.setIdleLoEvents(totIdleLoEvt);
        tmp.setDriveTime(totDriveTime);
        tmp.setEndingOdometer(totMilesDriven);
        tmp.setStartingOdometer(0); 
        tmp.setMpgHeavy(totHeavyDrivers>0?(totMpgHeavy/totHeavyDrivers):0);
        tmp.setMpgMedium(totMediumDrivers>0?(totMpgMedium/totMediumDrivers):0);
        tmp.setMpgLight(totLightDrivers>0?(totMpgLight/totLightDrivers):0);
        tmp.setOdometerHeavy(totHeavyDrivers>0?(totMilesHeavy/totHeavyDrivers):0);
        tmp.setOdometerMedium(totMediumDrivers>0?(totMilesMedium/totMediumDrivers):0);
        tmp.setOdometerLight(totLightDrivers>0?(totMilesLight/totLightDrivers):0);
        
        tmp.setCrashEvents(totCrash);       
        tmp.setSeatbeltEvents(totSeatBeltEvt);        
        tmp.setSeatbeltClicks(totSeatBeltClicks);        
        tmp.setSpeedEvents(totSpeedEvt);
        tmp.setAggressiveAccelEvents(totAggAccelEvt);
        tmp.setAggressiveBrakeEvents(totAggBrakeEvt);
        tmp.setAggressiveBumpEvents(totAggBumpEvt);
        tmp.setAggressiveLeftEvents(totAggLeftEvt);
        tmp.setAggressiveRightEvents(totAggRightEvt);
        
        dvsw.setScore(tmp);
        
        
        // Driver/Vehicle
        Driver drv = new Driver();
        drv.setDriverID(0);
        Vehicle veh = new Vehicle();
        veh.setName("");
        Person prs = new Person();

        // Group check, may be driven by bad data        
        prs.setFirst("");
        if ( (group != null) && (group.getName()!= null) ) {
            prs.setFirst(group.getName());
        }        
        
        drv.setPerson(prs);
        dvsw.setVehicle(veh);
        dvsw.setDriver(drv);
        dvsw.setSummary(true);
        
        return dvsw;

    }

    @Override
    public int compareTo(DriverVehicleScoreWrapper o) {
    	//in case of driver is null, the sorting process will throw null pointer exception. Check null bidirectionally.
		if (o.getDriver() == null) {
			//after
			return 1;
		}
		if (this.getDriver() == null) {
			//before
			return -1;
		} else {
			return this.driver.compareTo(o.getDriver());
		}
    }
}
