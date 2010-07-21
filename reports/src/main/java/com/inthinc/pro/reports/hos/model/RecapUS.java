package com.inthinc.pro.reports.hos.model;

import java.util.List;

import org.joda.time.DateTime;

import com.inthinc.hos.model.CummulativeData;
import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.model.RuleViolationTypes;


public class RecapUS extends Recap {
    
    private CummulativeData cummulativeData;
    

    public RecapUS(RuleSetType ruleSetType, DateTime day, List<HOSRec> hosRecList, int minutesWorkedToday) {
        super(ruleSetType, day, hosRecList, RecapType.US, minutesWorkedToday);
        
        cummulativeData = cummulativeDataMap.get(RuleViolationTypes.CUMMULATIVE_HOURS_8_DAYS);
        
        // plus 1 because we want Monday to be day 1 instead of sunday
        setDay(day.plusDays(1).getDayOfWeek());

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
