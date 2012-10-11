package com.inthinc.pro.reports.hos.model;

import com.inthinc.hos.model.RuleViolationTypes;

public class NonDOTViolationsSummary extends ViolationsSummary {
    
    private Integer nonDOTDriverCount;
    
    public NonDOTViolationsSummary(String groupName) {
        super(groupName);
        this.nonDOTDriverCount = 0;
    }

    public NonDOTViolationsSummary(String groupName, Integer driverCnt, Integer nonDOTDriverCount) {
        super(groupName, driverCnt);
        this.nonDOTDriverCount = nonDOTDriverCount;
    }
    public Integer getNonDOTDriverCount() {
        return nonDOTDriverCount;
    }
    public void setNonDOTDriverCount(Integer nonDOTDriverCount) {
        this.nonDOTDriverCount = nonDOTDriverCount;
    }

    @Override
    public void updateMinutes(RuleViolationTypes violationType, int minutes) {
        if (minutes == 0)
            return;
        
        if (violationType == RuleViolationTypes.NON_DOT_DRIVER)
            nonDOTDriverCount++;
    }
    
    public void dump() {
        System.out.println("new NonDOTViolationsSummary(\", " + getGroupName() + "\"," + 
                getDriverCnt() + "," + 
                getNonDOTDriverCount() + "),");

    }

}
