package com.inthinc.pro.reports.hos.model;

import com.inthinc.hos.model.RuleViolationTypes;

public abstract class ViolationsSummary implements Comparable<ViolationsSummary> {
    
    private String groupName;
    private Integer driverCnt;
    
    
    public ViolationsSummary(String groupName) {
        this.groupName = groupName;
        this.driverCnt = 0;
    }
    public ViolationsSummary(String groupName, Integer driverCnt) {
        this.groupName = groupName;
        this.driverCnt = driverCnt;
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public Integer getDriverCnt() {
        return driverCnt;
    }
    public void setDriverCnt(Integer driverCnt) {
        this.driverCnt = driverCnt;
    }

    
    public abstract void updateMinutes(RuleViolationTypes violationType, int minutes);

    @Override
    public int compareTo(ViolationsSummary o) {
        return groupName.compareTo(o.getGroupName());
    }
}
