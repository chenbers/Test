package com.inthinc.pro.reports.hos.model;

import com.inthinc.hos.model.RuleViolationTypes;

public class WthHosViolationsSummary extends ViolationsSummary  {

    private Integer hourDriving11;
    private Integer onDutyHours14;
    private Integer onDutyHours70;
    private Integer thirtyMinuteBreak;
    private Integer driving_1;
    private Integer driving_2;
    private Integer driving_3;
    private Integer onDuty_1;
    private Integer onDuty_2;
    private Integer onDuty_3;
    private Integer cumulative_1;
    private Integer cumulative_2;
    private Integer cumulative_3;
    private Integer offDuty_1;
    private Integer offDuty_2;
    private Integer offDuty_3;
    private Double totalMiles;
    private Double totalMilesNoDriver;


    public WthHosViolationsSummary(String groupName) {
        super(groupName);
        this.hourDriving11 = 0;
        this.onDutyHours14 = 0;
        this.onDutyHours70 = 0;
        this.thirtyMinuteBreak = 0;
        this.driving_1 = 0;
        this.driving_2 = 0;
        this.driving_3 = 0;
        this.onDuty_1 = 0;
        this.onDuty_2 = 0;
        this.onDuty_3 = 0;
        this.cumulative_1 = 0;
        this.cumulative_2 = 0;
        this.cumulative_3 = 0;
        this.offDuty_1 = 0;
        this.offDuty_2 = 0;
        this.offDuty_3 = 0;
        this.totalMiles = 0d;
        this.totalMilesNoDriver = 0d;
    }
    public WthHosViolationsSummary(String groupName, Integer driving_1, Integer driving_2, Integer driving_3, Integer onDuty_1, Integer onDuty_2, Integer onDuty_3,
                                   Integer cumulative_1, Integer cumulative_2, Integer cumulative_3, Integer offDuty_1, Integer offDuty_2, Integer offDuty_3, Integer driverCnt, Double totalMiles,
                                   Double totalMilesNoDriver) {
        super(groupName, driverCnt);
        this.driving_1 = driving_1;
        this.driving_2 = driving_2;
        this.driving_3 = driving_3;
        this.onDuty_1 = onDuty_1;
        this.onDuty_2 = onDuty_2;
        this.onDuty_3 = onDuty_3;
        this.cumulative_1 = cumulative_1;
        this.cumulative_2 = cumulative_2;
        this.cumulative_3 = cumulative_3;
        this.offDuty_1 = offDuty_1;
        this.offDuty_2 = offDuty_2;
        this.offDuty_3 = offDuty_3;
        this.totalMiles = totalMiles;
        this.totalMilesNoDriver = totalMilesNoDriver;
    }
    public Integer getDriving_1() {
        return driving_1;
    }
    public void setDriving_1(Integer driving_1) {
        this.driving_1 = driving_1;
    }
    public Integer getDriving_2() {
        return driving_2;
    }
    public void setDriving_2(Integer driving_2) {
        this.driving_2 = driving_2;
    }
    public Integer getDriving_3() {
        return driving_3;
    }
    public void setDriving_3(Integer driving_3) {
        this.driving_3 = driving_3;
    }
    public Integer getOnDuty_1() {
        return onDuty_1;
    }
    public void setOnDuty_1(Integer onDuty_1) {
        this.onDuty_1 = onDuty_1;
    }
    public Integer getOnDuty_2() {
        return onDuty_2;
    }
    public void setOnDuty_2(Integer onDuty_2) {
        this.onDuty_2 = onDuty_2;
    }
    public Integer getOnDuty_3() {
        return onDuty_3;
    }
    public void setOnDuty_3(Integer onDuty_3) {
        this.onDuty_3 = onDuty_3;
    }
    public Integer getCumulative_1() {
        return cumulative_1;
    }
    public void setCumulative_1(Integer cumulative_1) {
        this.cumulative_1 = cumulative_1;
    }
    public Integer getCumulative_2() {
        return cumulative_2;
    }
    public void setCumulative_2(Integer cumulative_2) {
        this.cumulative_2 = cumulative_2;
    }
    public Integer getCumulative_3() {
        return cumulative_3;
    }
    public void setCumulative_3(Integer cumulative_3) {
        this.cumulative_3 = cumulative_3;
    }
    public Integer getOffDuty_1() {
        return offDuty_1;
    }
    public void setOffDuty_1(Integer offDuty_1) {
        this.offDuty_1 = offDuty_1;
    }
    public Integer getOffDuty_2() {
        return offDuty_2;
    }
    public void setOffDuty_2(Integer offDuty_2) {
        this.offDuty_2 = offDuty_2;
    }
    public Integer getOffDuty_3() {
        return offDuty_3;
    }
    public void setOffDuty_3(Integer offDuty_3) {
        this.offDuty_3 = offDuty_3;
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

        if (violationType == RuleViolationTypes.DRIVING_HOUR){
            if (minutes >= 60 * 11){
                hourDriving11++;
            }
        }

        if (violationType == RuleViolationTypes.ON_DUTY_HOUR){
            if (minutes >= 60 * 14){
                onDutyHours14++;
            }
        }

        if (violationType == RuleViolationTypes.ON_DUTY_HOUR){
            if (minutes >= 60 * 14 && minutes < 60 * 70){
                onDutyHours14++;
            } else if (minutes >= 60 * 70){
                onDutyHours70++;
            }
        }

        if (violationType == RuleViolationTypes.REST_BREAK){
            if (minutes >= 30) {
                thirtyMinuteBreak++;
            }
        }
        
        if (violationType == RuleViolationTypes.DRIVING_HOUR || violationType == RuleViolationTypes.DAILY_DRIVING || violationType == RuleViolationTypes.REST_BREAK || violationType == RuleViolationTypes.ALBERTA_REST_BREAK) {
            if (minutes < 15)
                driving_1++;
            else if (minutes < 30) 
                driving_2++;
            else 
                driving_3++;
        }
        else if (violationType == RuleViolationTypes.ON_DUTY_HOUR || violationType == RuleViolationTypes.DAILY_ON_DUTY) {
            if (minutes < 15) 
                onDuty_1++;
            else if (minutes < 30) 
                onDuty_2++;
            else 
                onDuty_3++;
        }
        else if (violationType == RuleViolationTypes.OFF_DUTY_HOUR || violationType == RuleViolationTypes.DAILY_OFF_DUTY) {
            if (minutes < 15) 
                offDuty_1++;
            else if (minutes < 30) 
                offDuty_2++;
            else 
                offDuty_3++;
        }
        else if (violationType == RuleViolationTypes.CUMMULATIVE_HOURS || violationType == RuleViolationTypes.TWENTY_FOUR_HOURS_OFF_DUTY) {
            if (minutes < 15) 
                cumulative_1++;
            else if (minutes < 30) 
                cumulative_2++;
            else 
                cumulative_3++;
        }
        
        
    }
    
    public void dump() {
      System.out.println("new HosViolationsSummary(\", " + getGroupName() + "\"," +
              getHourDriving11() + "," +
              getOnDutyHours14() + "," +
              getOnDutyHours70() + "," +
              getThirtyMinuteBreak() + "," +
              getDriving_2() + "," + 
              getDriving_3() + "," + 
              getOnDuty_1() + "," + 
              getOnDuty_2() + "," + 
              getOnDuty_3() + "," + 
              getCumulative_1() + "," + 
              getCumulative_2() + "," + 
              getCumulative_3() + "," + 
              getOffDuty_1() + "," + 
              getOffDuty_2() + "," + 
              getOffDuty_3() + "," + 
              getDriverCnt() + "," + 
              getTotalMiles() + "d," +
              getTotalMilesNoDriver() + "d);");
    }

}
