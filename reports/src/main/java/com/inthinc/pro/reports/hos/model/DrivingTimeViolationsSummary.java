package com.inthinc.pro.reports.hos.model;

import com.inthinc.hos.model.RuleViolationTypes;

public class DrivingTimeViolationsSummary extends ViolationsSummary {
    
    private Integer drivingHourBanCount;
    private Integer onDuty16HrCount;
    private Integer nonDOTDriverCount;
    
    
    public DrivingTimeViolationsSummary(String groupName) {
        super(groupName);
        this.drivingHourBanCount = 0;
        this.onDuty16HrCount = 0;
        this.nonDOTDriverCount = 0;
    }
    public DrivingTimeViolationsSummary(String groupName, Integer drivingHourBanCount, Integer onDuty16HrCount, Integer nonDOTDriverCount, Integer driverCnt) {
        super(groupName, driverCnt);
        this.drivingHourBanCount = drivingHourBanCount;
        this.onDuty16HrCount = onDuty16HrCount;
        this.nonDOTDriverCount = nonDOTDriverCount;
    }
    public Integer getDrivingHourBanCount() {
        return drivingHourBanCount;
    }
    public void setDrivingHourBanCount(Integer drivingHourBanCount) {
        this.drivingHourBanCount = drivingHourBanCount;
    }
    public Integer getNonDOTDriverCount() {
        return nonDOTDriverCount;
    }
    public void setNonDOTDriverCount(Integer nonDOTDriverCount) {
        this.nonDOTDriverCount = nonDOTDriverCount;
    }
    public Integer getOnDuty16HrCount() {
        return onDuty16HrCount;
    }
    public void setOnDuty16HrCount(Integer onDuty16HrCount) {
        this.onDuty16HrCount = onDuty16HrCount;
    }


    @Override
    public void updateMinutes(RuleViolationTypes violationType, int minutes) {
        if (minutes == 0)
            return;
        
        if (violationType == RuleViolationTypes.NON_DOT_DRIVER)
            nonDOTDriverCount++; 
        if (violationType == RuleViolationTypes.DRIVING_HOUR_BAN)
            drivingHourBanCount++; 
        if (violationType == RuleViolationTypes.DRIVING_TIME_GENERAL)
            onDuty16HrCount++; 
    }

    public void dump() {
        System.out.println("new DrivingTimeViolationsSummary(\", " + getGroupName() + "\"," + 
                    getDrivingHourBanCount() + "," + 
                    getOnDuty16HrCount() + "," + 
                    getNonDOTDriverCount() + "," + 
                    getDriverCnt() + ");");
    }
}
