package com.inthinc.pro.reports.hos.model;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.inthinc.hos.model.CummulativeData;
import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.model.RuleViolationTypes;


public class RecapUS extends Recap {
    
    private CummulativeData cummulativeData;
    
    private int maxRecapDay;
    
    
    
    public RecapUS(RuleSetType ruleSetType, DateTime day, List<HOSRec> hosRecList, int minutesWorkedToday, DateTimeZone dateTimeZone) {
        super(ruleSetType, day, hosRecList, RecapType.US, minutesWorkedToday, dateTimeZone);

        maxRecapDay = (ruleSetType == RuleSetType.US_7DAY || ruleSetType == RuleSetType.US_OIL_7DAY) ? 7 : 8;
        cummulativeData = cummulativeDataMap.get(RuleViolationTypes.CUMMULATIVE_HOURS);
        
        setDay(getRecapDay(day));

    }
    
    public int getRecapDay(DateTime day)
    {
        DateTime resetDay = new DateTime(cummulativeData.getStartTime(), dateTimeZone);
        int daysDiff = day.getDayOfYear() - resetDay.getDayOfYear();
        int recapDay = daysDiff+1;
        if (recapDay > maxRecapDay)
            recapDay = maxRecapDay;
        return recapDay;
    }

    @Override
    public String getHoursAvailToday() {
        return formatMinutes(cummulativeData.getMinAvailToday());
    }
    public String getHoursWorked7Days() {
        if (maxRecapDay == 7)
            return formatMinutes(cummulativeData.getTotalMinWorked());
        return "-";
    }
    public String getHoursWorked8Days() {
        if (maxRecapDay == 8)
            return formatMinutes(cummulativeData.getTotalMinWorked());
        return "-";
    }
    @Override
    public String getHoursAvailTomorrow() {
        return formatMinutes(cummulativeData.getMinAvailTomorrow());
    }
}
