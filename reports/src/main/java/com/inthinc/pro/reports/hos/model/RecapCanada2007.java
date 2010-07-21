package com.inthinc.pro.reports.hos.model;

import java.util.List;

import org.joda.time.DateTime;

import com.inthinc.hos.model.CummulativeData;
import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.model.RuleViolationTypes;

public class RecapCanada2007 extends Recap {
    
    private CummulativeData cummulativeData;
    private int cycle;

    public RecapCanada2007(RuleSetType ruleSetType, DateTime day, List<HOSRec> hosRecList, int minutesWorkedToday) {
        super(ruleSetType, day, hosRecList, RecapType.CANADA_2007, minutesWorkedToday);
        

        CummulativeData cycle1Data = cummulativeDataMap.get(RuleViolationTypes.CUMMULATIVE_HOURS_7_DAYS);
        CummulativeData cycle2Data = cummulativeDataMap.get(RuleViolationTypes.CUMMULATIVE_HOURS_14_DAYS);
        cummulativeData = (cycle1Data == null) ? cycle2Data : cycle1Data;
        setCycle((cycle1Data == null) ? 2 : 1);
        
        setDay(getCycleRecapDay());

    }
    @Override
    public String getHoursAvailToday() {
        return formatMinutes(cummulativeData.getMinAvailToday());
    }

    public String getHoursWorkedCycle() {
        return formatMinutes(cummulativeData.getTotalMinWorked());
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }
    public int getCycle() {
        return cycle;
    }
    
    @Override
    public String getHoursAvailTomorrow() {
        return formatMinutes(cummulativeData.getMinAvailTomorrow());
    }


    private int getCycleRecapDay()
    {
        DateTime resetDay = new DateTime(cummulativeData.getStartTime());
        DateTime currentDay = new DateTime(cummulativeData.getCurrentTime());
        int daysDiff = currentDay.getDayOfYear() - resetDay.getDayOfYear();
        return daysDiff + 1;
    }

}
