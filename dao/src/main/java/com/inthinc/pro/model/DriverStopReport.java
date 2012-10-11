package com.inthinc.pro.model;

import java.util.List;


public class DriverStopReport {
    
    private Integer driverID;
    private String driverName;
    private List<DriverStops> driverStops;
    private Integer idleLoTotal;
    private Integer idleHiTotal;
    private Integer waitTotal;
    private Integer total;
    private Long driveTimeTotal; 
    private TimeFrame timeFrame;
    private String teamName;

    public DriverStopReport(String teamName, Integer driverID, String driverName, TimeFrame timeFrame, List<DriverStops> driverStops) {
        super();
        this.teamName = teamName;
        this.driverID = driverID;
        this.driverName = driverName;
        this.timeFrame = timeFrame;
        this.driverStops = driverStops;
        summarize();
    }
    
    public Integer getDriverID() {
        return driverID;
    }
    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }
    public String getDriverName() {
        return driverName;
    }
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    public List<DriverStops> getDriverStops() {
        return driverStops;
    }
    public void setDriverStops(List<DriverStops> driverStops) {
        this.driverStops = driverStops;
        summarize();
    }
    public Integer getIdleLoTotal() {
        return idleLoTotal;
    }
    public void setIdleLoTotal(Integer idleLoTotal) {
        this.idleLoTotal = idleLoTotal;
    }
    public Integer getIdleHiTotal() {
        return idleHiTotal;
    }
    public void setIdleHiTotal(Integer idleHiTotal) {
        this.idleHiTotal = idleHiTotal;
    }
    public Integer getWaitTotal() {
        return waitTotal;
    }
    public void setWaitTotal(Integer waitTotal) {
        this.waitTotal = waitTotal;
    }
    public Long getDriveTimeTotal() {
        return driveTimeTotal;
    }
    public void setDriveTimeTotal(Long driveTimeTotal) {
        this.driveTimeTotal = driveTimeTotal;
    }
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
    public TimeFrame getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(TimeFrame timeFrame) {
        this.timeFrame = timeFrame;
    }
    
    private void summarize() {
        total = 0;
        idleLoTotal = 0;
        idleHiTotal = 0;
        waitTotal = 0;
        driveTimeTotal = 0l;    
        
        if (driverStops == null || driverStops.size() == 0)
            return;
        
        for (DriverStops driverStop : driverStops) {
            idleLoTotal += ( driverStop.getIdleLo() != null ) ? driverStop.getIdleLo() : 0;
            idleHiTotal += ( driverStop.getIdleHi() != null )? driverStop.getIdleHi() : 0;
            driveTimeTotal += ( driverStop.getDriveTime() != null )? driverStop.getDriveTime() : 0;
            waitTotal += ( driverStop.getWaitTime() != null )? driverStop.getWaitTime() : 0;
        }
        total = idleLoTotal + idleHiTotal + waitTotal;
    }
    
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

}
