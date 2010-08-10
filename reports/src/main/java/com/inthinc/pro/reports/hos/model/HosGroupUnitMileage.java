package com.inthinc.pro.reports.hos.model;

public class HosGroupUnitMileage extends HosGroupMileage {

    private String unitID;
    public HosGroupUnitMileage(Integer groupID, Long distance) {
        super(groupID, distance);
    }
    
    public HosGroupUnitMileage(Integer groupID, String unitID, Long distance) {
        super(groupID, distance);
        this.unitID = unitID;
    }
    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }

    
}
