package com.inthinc.pro.reports.hos.model;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.hos.model.CummulativeData;
import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.model.RuleViolationTypes;
import com.inthinc.hos.rules.HOSRules;
import com.inthinc.hos.rules.RuleSetFactory;

public abstract class Recap {
    private RuleSetType ruleSetType;
    private RecapType recapType;
   
    private Integer day;
    private int minutesWorkedToday;
    protected Map<RuleViolationTypes, CummulativeData> cummulativeDataMap;
    public static final DecimalFormat hoursFormat = new DecimalFormat("00.00");
    protected DateTimeZone dateTimeZone;
    
    
    protected DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss z");
    
    



    public Recap(RuleSetType ruleSetType, DateTime day, List<HOSRec> hosRecList, RecapType recapType, int minutesWorkedToday, DateTimeZone dateTimeZone) {
        this.ruleSetType = ruleSetType;
        this.recapType = recapType;
        this.minutesWorkedToday = minutesWorkedToday;
        this.dateTimeZone = dateTimeZone;
        
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        if (hosRecList != null && hosRecList.size() > 0)
            timeZone = hosRecList.get(0).getLogTimeZone();

        LocalDate localDate = new LocalDate(day);
        DateTime dayEnd = localDate.toDateTimeAtStartOfDay(DateTimeZone.forTimeZone(timeZone)).plusDays(1).minusSeconds(1);
        
        HOSRules rules = RuleSetFactory.getRulesForRuleSetType(ruleSetType);
        cummulativeDataMap = rules.getCummulativeData(dayEnd.toDate(), hosRecList);
        

    }
    
    public abstract String getHoursAvailToday();
    public abstract String getHoursAvailTomorrow();

    

    public String formatMinutes(long minutes) {
        return hoursFormat.format((minutes / 15)/4.0);
    }

    public String getHoursWorkedToday() {
        return formatMinutes(minutesWorkedToday);
    }


    public RecapType getRecapType() {
        return recapType;
    }

    public void setRecapType(RecapType recapType) {
        this.recapType = recapType;
    }

    public RuleSetType getRuleSetType() {
        return ruleSetType;
    }

    public void setRuleSetType(RuleSetType ruleSetType) {
        this.ruleSetType = ruleSetType;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

}
