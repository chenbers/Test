package com.inthinc.pro.reports.hos.model;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.hos.model.CummulativeData;
import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.model.RuleViolationTypes;


public class RecapUS extends Recap {
    
    private CummulativeData cummulativeData;
    
    private static final int MAX_RECAP_DAY = 8;
    
    
    
    public RecapUS(RuleSetType ruleSetType, DateTime day, List<HOSRec> hosRecList, int minutesWorkedToday, DateTimeZone dateTimeZone) {
        super(ruleSetType, day, hosRecList, RecapType.US, minutesWorkedToday, dateTimeZone);

        cummulativeData = cummulativeDataMap.get(RuleViolationTypes.CUMMULATIVE_HOURS_8_DAYS);
        
        setDay(getRecapDay(day));

    }
    
    public int getRecapDay(DateTime day)
    {
        DateTime resetDay = new DateTime(cummulativeData.getStartTime(), dateTimeZone);
//System.out.println("resetDay: " + dateTimeFormatter.print(resetDay) + " report day: " + dateTimeFormatter.print(day));        
//System.out.println("resetDay: " + resetDay.getDayOfYear() + " report day: " + day.getDayOfYear());     
        int daysDiff = day.getDayOfYear() - resetDay.getDayOfYear();
        int recapDay = daysDiff+1;
        if (recapDay > MAX_RECAP_DAY)
            recapDay = MAX_RECAP_DAY;
        return recapDay;
    }

    @Override
    public String getHoursAvailToday() {
        return formatMinutes(cummulativeData.getMinAvailToday());
    }
    public String getHoursWorked7Days() {
        return "-";
    }
    public String getHoursWorked8Days() {
        return formatMinutes(cummulativeData.getTotalMinWorked());
    }
    @Override
    public String getHoursAvailTomorrow() {
        return formatMinutes(cummulativeData.getMinAvailTomorrow());
    }
}
