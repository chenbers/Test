package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.util.MessageUtil;

public class TeamStatisticsBean extends BaseBean {

//  Request scope bean for new team page    
    private static final long serialVersionUID = 1L;
    
    private int numRowsPerPg = 3;
    private List<DriverVehicleScoreWrapper> driverStatistics;
    private List<DriverVehicleScoreWrapper> driverTotals;   
    private Map<String,List> cachedResults = new HashMap<String,List>();

    private GroupReportDAO groupReportDAO;  
    private TeamCommonBean teamCommonBean;    

    public GroupReportDAO getGroupReportDAO() {
        return groupReportDAO;
    }

    public void setGroupReportDAO(GroupReportDAO groupReportDAO) {
        this.groupReportDAO = groupReportDAO;
    }

    public TeamCommonBean getTeamCommonBean() {
        return teamCommonBean;
    }

    public void setTeamCommonBean(TeamCommonBean teamCommonBean) {
        this.teamCommonBean = teamCommonBean;
    }

    public List<DriverVehicleScoreWrapper> getDriverStatistics() {
        
        // Have this cached?
        String key = teamCommonBean.getTimeFrame().name();
        if (cachedResults.containsKey(key)) {
            return cachedResults.get(key);
        }

        // Get the data
        boolean useDaily = whichMethodToUse();
        
        if ( useDaily ) {
            driverStatistics = groupReportDAO.getDriverScores(teamCommonBean.getGroupID(), teamCommonBean.getTimeFrame().getInterval());
            
        } else {
            driverStatistics = groupReportDAO.getDriverScores(teamCommonBean.getGroupID(), teamCommonBean.getTimeFrame().getAggregationDuration());
        }
        
        // Set the styles for the color-coded box and convert the mpg data               
        loadScoreStyles();
        convertMPGData();
        cleanData();

        // All set, save so we don't grab the data again
        cachedResults.put(key, driverStatistics);
        
        return driverStatistics;
    }

    public void setDriverStatistics(List<DriverVehicleScoreWrapper> driverStatistics) {
        this.driverStatistics = driverStatistics;
    }
    
    private boolean whichMethodToUse() {      
        
        if (    this.teamCommonBean.getTimeFrame().equals(TimeFrame.WEEK) ||
                this.teamCommonBean.getTimeFrame().equals(TimeFrame.MONTH) ||
                this.teamCommonBean.getTimeFrame().equals(TimeFrame.YEAR) ) {
            return false;
        }
    
        return true;
    }

    public Integer getGroupID() {
        return teamCommonBean.getGroupID();
    }
    
    private void loadScoreStyles() {
        for ( DriverVehicleScoreWrapper dvsw: driverStatistics) {
        	
            // -1 to get the N/A to show
        	if(dvsw.getScore().getOverall()== null){
        		dvsw.getScore().setOverall(-1);        	    
                dvsw.setScoreStyle(ScoreBox.GetStyleFromScore(
                        -1, ScoreBoxSizes.SMALL));
        	}
         	else{
        		
        		dvsw.setScoreStyle(ScoreBox.GetStyleFromScore(
        				dvsw.getScore().getOverall().intValue(), ScoreBoxSizes.SMALL));
        	}
        }
    }
    
    private void convertMPGData() {
        for ( DriverVehicleScoreWrapper dvsw: driverStatistics ) {
                        
            dvsw.getScore().setMpgHeavy(
                    dvsw.getScore().getMpgHeavy() != null ? MeasurementConversionUtil.convertMpgToFuelEfficiencyType(
                            dvsw.getScore().getMpgHeavy(), getMeasurementType(), getFuelEfficiencyType()) : 0);
            
            dvsw.getScore().setMpgLight(
                    dvsw.getScore().getMpgLight() != null ? MeasurementConversionUtil.convertMpgToFuelEfficiencyType(
                            dvsw.getScore().getMpgLight(), getMeasurementType(), getFuelEfficiencyType()) : 0);            
            
            dvsw.getScore().setMpgMedium(
                    dvsw.getScore().getMpgMedium() != null ? MeasurementConversionUtil.convertMpgToFuelEfficiencyType(
                            dvsw.getScore().getMpgMedium(), getMeasurementType(), getFuelEfficiencyType()) : 0);
        }        
    }
    
    public void cleanData() {
        
        // A place to facilitate sorting and other good things
        for ( DriverVehicleScoreWrapper dvsw: driverStatistics ) {
            
            if ( dvsw.getScore().getTrips() == null ) {
                dvsw.getScore().setTrips(0);
            }
            
            if ( dvsw.getScore().getCrashEvents() == null ) {
                dvsw.getScore().setCrashEvents(0);
            }
            
            if ( dvsw.getVehicle() == null ) {
                Vehicle v = new Vehicle();
                v.setName(MessageUtil.getMessageString("reports_none_assigned"));
                dvsw.setVehicle(v);
            }
        }
    }
    
    public List<DriverVehicleScoreWrapper> getDriverTotals() {
        driverTotals = new ArrayList<DriverVehicleScoreWrapper>();
        
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
        int totMpg = 0;
        int totCrash = 0;
        int totSeatBeltEvt = 0;
        int totSpeedEvt = 0;
        int totAggAccelEvt = 0;
        int totAggBrakeEvt = 0;
        int totAggBumpEvt = 0;
        int totAggLeftEvt = 0;
        int totAggRightEvt = 0;        
        
        for ( DriverVehicleScoreWrapper dvsc: driverStatistics ) { 
            if ( dvsc.getScore().getOverall() != null ) {
                totScore += dvsc.getScore().getOverall().intValue();
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
            if ( dvsc.getScore().getEndingOdometer() != null && dvsc.getScore().getStartingOdometer() != null ) {
                totMilesDriven += ( dvsc.getScore().getEndingOdometer().intValue() - dvsc.getScore().getStartingOdometer().intValue() );
            }
            if ( dvsc.getScore().getDriveTime() != null ) {
                totDriveTime += dvsc.getScore().getDriveTime().intValue();
            }            
            if ( dvsc.getScore().getMpgHeavy() != null ) {
                totMpg += dvsc.getScore().getMpgHeavy().intValue();
            }
            if ( dvsc.getScore().getMpgLight() != null ) {
                totMpg += dvsc.getScore().getMpgLight().intValue();                
            }
            if ( dvsc.getScore().getMpgMedium() != null ) {
                totMpg += dvsc.getScore().getMpgMedium().intValue();                
            }
            if ( dvsc.getScore().getCrashEvents() != null ) {
                totCrash += dvsc.getScore().getCrashEvents().intValue();
            }
            if ( dvsc.getScore().getSeatbeltEvents() != null ) {
                totSeatBeltEvt += dvsc.getScore().getSeatbeltEvents().intValue();
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
        }
        
        // The total miles are determined by setting ending to the total
        //  and starting to 0. 
        Score tmp = new Score();
        tmp.setOverall(totScore/driverStatistics.size());
        dvsw.setScoreStyle(ScoreBox.GetStyleFromScore(
                        tmp.getOverall().intValue(), ScoreBoxSizes.SMALL));
        tmp.setTrips(totTrips);
        tmp.setIdleHi(totIdleHi);
        tmp.setIdleLo(totIdleLo);
        tmp.setIdleHiEvents(totIdleHiEvt);
        tmp.setIdleLoEvents(totIdleLoEvt);
        tmp.setDriveTime(totDriveTime);
        tmp.setEndingOdometer(totMilesDriven);
        tmp.setStartingOdometer(0); 
        Number mpg = totMpg/driverStatistics.size();        
        tmp.setMpgHeavy(MeasurementConversionUtil.convertMpgToFuelEfficiencyType(
                mpg, getMeasurementType(), getFuelEfficiencyType()));
        tmp.setMpgMedium(0);
        tmp.setMpgLight(0);
        
        tmp.setCrashEvents(totCrash);       
        tmp.setSeatbeltEvents(totSeatBeltEvt);        
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
       
        Group grp = this.getGroupHierarchy().getGroup(this.teamCommonBean.getGroupID());
        
        // Group check, may be driven by bad data        
        prs.setFirst("");
        if ( (grp != null) && (grp.getName()!= null) ) {
            prs.setFirst(grp.getName());
        }        
        
        drv.setPerson(prs);
        dvsw.setDriver(drv);
 
        driverTotals.add(dvsw);
        
        return driverTotals;
    }

    public void setDriverTotals(List<DriverVehicleScoreWrapper> driverTotals) {
        this.driverTotals = driverTotals;
    }

    public int getNumRowsPerPg() {
        return numRowsPerPg;
    }

    public void setNumRowsPerPg(int numRowsPerPg) {
        this.numRowsPerPg = numRowsPerPg;
    }
    
}
