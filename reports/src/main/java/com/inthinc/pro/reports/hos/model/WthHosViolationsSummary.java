package com.inthinc.pro.reports.hos.model;

import com.inthinc.hos.model.RuleViolationTypes;

public class WthHosViolationsSummary extends ViolationsSummary {

    private Integer hourDriving11;
    private Integer onDutyHours14;
    private Integer onDutyHours70;
    private Integer thirtyMinuteBreak;
    private Double totalMiles;
    private Double totalMilesNoDriver;

    public WthHosViolationsSummary(String groupName) {
        super(groupName);
        this.hourDriving11 = 0;
        this.onDutyHours14 = 0;
        this.onDutyHours70 = 0;
        this.thirtyMinuteBreak = 0;
        this.totalMiles = 0d;
        this.totalMilesNoDriver = 0d;
    }

    public WthHosViolationsSummary(String groupName, Integer driverCnt, Integer hourDriving11, Integer onDutyHours14, Integer onDutyHours70, Integer thirtyMinuteBreak, Double totalMiles, Double totalMilesNoDriver) {
        super(groupName, driverCnt);
        this.hourDriving11 = hourDriving11;
        this.onDutyHours14 = onDutyHours14;
        this.onDutyHours70 = onDutyHours70;
        this.thirtyMinuteBreak = thirtyMinuteBreak;
        this.totalMiles = totalMiles;
        this.totalMilesNoDriver = totalMilesNoDriver;
    }

    public Double getTotalMiles() {
        return totalMiles;
    }

    public void setTotalMiles(Double totalMiles) {
        this.totalMiles = totalMiles;
    }

    public Double getTotalMilesNoDriver() {
        return totalMilesNoDriver;
    }

    public void setTotalMilesNoDriver(Double totalMilesNoDriver) {
        this.totalMilesNoDriver = totalMilesNoDriver;
    }

    public Integer getHourDriving11() {
        return hourDriving11;
    }

    public void setHourDriving11(Integer hourDriving11) {
        this.hourDriving11 = hourDriving11;
    }

    public Integer getOnDutyHours14() {
        return onDutyHours14;
    }

    public void setOnDutyHours14(Integer onDutyHours14) {
        this.onDutyHours14 = onDutyHours14;
    }

    public Integer getOnDutyHours70() {
        return onDutyHours70;
    }

    public void setOnDutyHours70(Integer onDutyHours70) {
        this.onDutyHours70 = onDutyHours70;
    }

    public Integer getThirtyMinuteBreak() {
        return thirtyMinuteBreak;
    }

    public void setThirtyMinuteBreak(Integer thirtyMinuteBreak) {
        this.thirtyMinuteBreak = thirtyMinuteBreak;
    }

    @Override
    public void updateMinutes(RuleViolationTypes violationType, int minutes) {
        if (minutes == 0)
            return;

        if (violationType == RuleViolationTypes.DRIVING_HOUR) {
            hourDriving11++;
        } else if (violationType == RuleViolationTypes.ON_DUTY_HOUR || violationType == RuleViolationTypes.DAILY_ON_DUTY) {
            onDutyHours14++;
        } else if (violationType == RuleViolationTypes.CUMMULATIVE_HOURS) {
            onDutyHours70++;
        } else if (violationType == RuleViolationTypes.REST_BREAK || violationType == RuleViolationTypes.ALBERTA_REST_BREAK) {
            thirtyMinuteBreak++;
        }
    }

    public void dump() {
        System.out.println("new WthHosViolationsSummary(\", " + getGroupName() + "\"," +
                getDriverCnt() + "," +
                getHourDriving11() + "," +
                getOnDutyHours14() + "," +
                getOnDutyHours70() + "," +
                getThirtyMinuteBreak() + "," +
                getTotalMiles() + "d," +
                getTotalMilesNoDriver() + "d);");
    }
}
