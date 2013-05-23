package com.inthinc.pro.reports.hos.testData;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.joda.time.Interval;

import com.inthinc.hos.model.HOSRecBase;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.model.hos.HOSRecord;

public class TestCase {

    String description;
    String id;
    List<HOSRecord> driverLog;
    Interval interval;
    Date currentTime;
    TimeZone timeZone;
    List<HOSRecBase> resultLog;
    RuleSetType ruleSetType;
    String recapDay;
    String recapHoursWorked;

    public String getRecapDay() {
        return recapDay;
    }

    public void setRecapDay(String recapDay) {
        this.recapDay = recapDay;
    }

    public String getRecapHoursWorked() {
        return recapHoursWorked;
    }

    public void setRecapHoursWorked(String recapHoursWorked) {
        this.recapHoursWorked = recapHoursWorked;
    }

    public RuleSetType getRuleSetType() {
        return ruleSetType;
    }

    public void setRuleSetType(RuleSetType ruleSetType) {
        this.ruleSetType = ruleSetType;
    }

    public List<HOSRecBase> getResultLog() {
        return resultLog;
    }

    public void setResultLog(List<HOSRecBase> resultLog) {
        this.resultLog = resultLog;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    public List<HOSRecord> getDriverLog() {
        return driverLog;
    }

    public void setDriverLog(List<HOSRecord> driverLog) {
        this.driverLog = driverLog;
    }


    public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
