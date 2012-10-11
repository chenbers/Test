package com.inthinc.pro.reports.hos.model;

public class HosZeroMiles implements Comparable<HosZeroMiles>{
    private String groupName;
    private String unitID;
    private Double totalMilesNoDriver;

    
    public HosZeroMiles(String groupName, String unitID, Double totalMilesNoDriver) {
        super();
        this.groupName = groupName;
        this.unitID = unitID;
        this.totalMilesNoDriver = totalMilesNoDriver;
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getUnitID() {
        return unitID;
    }
    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }
    public Double getTotalMilesNoDriver() {
        return totalMilesNoDriver;
    }
    public void setTotalMilesNoDriver(Double totalMilesNoDriver) {
        this.totalMilesNoDriver = totalMilesNoDriver;
    }
    @Override
    public int compareTo(HosZeroMiles o) {
        int cmp = groupName.compareTo(o.getGroupName());
        if (cmp == 0) {
            cmp = unitID.compareTo(o.getUnitID()) * -1;
        }

        return cmp;
    }
    
    public void dump() {
        System.out.println("new HosZeroMiles(" +
                "\"" + groupName + "\"," +
                "\"" + unitID + "\"," +
                totalMilesNoDriver + "),"
                );
    }
}
