package com.inthinc.pro.reports.hos.model;

import java.util.List;

import org.joda.time.DateTime;

import com.inthinc.hos.model.CummulativeData;
import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.model.RuleViolationTypes;

public class RecapCanada extends Recap {

    private CummulativeData cummulativeDataBestToday;
    private CummulativeData cummulativeDataBestTomorrow;
    private CummulativeData cummulativeData7day;
    private CummulativeData cummulativeData8day;
    private CummulativeData cummulativeData14day;

    public RecapCanada(RuleSetType ruleSetType, DateTime day, List<HOSRec> hosRecList, int minutesWorkedToday) {
        super(ruleSetType, day, hosRecList, RecapType.CANADA, minutesWorkedToday);

        cummulativeData7day = cummulativeDataMap.get(RuleViolationTypes.CUMMULATIVE_HOURS_7_DAYS);
        cummulativeData8day = cummulativeDataMap.get(RuleViolationTypes.CUMMULATIVE_HOURS_8_DAYS);
        cummulativeData14day = cummulativeDataMap.get(RuleViolationTypes.CUMMULATIVE_HOURS_14_DAYS);

        cummulativeDataBestToday = getCummulativeDataWithMostMinutesAvailToday();
        cummulativeDataBestTomorrow = getCummulativeDataWithMostMinutesAvailTomorrow();
        
        setDay(getRecapDay(day));
    }
    
    public String getHoursAvailToday() {
        return formatMinutes(cummulativeDataBestToday.getMinAvailToday());
    }

    public String getHoursWorked7Days() {
        return formatMinutes((cummulativeData7day != null) ? cummulativeData7day.getTotalMinWorked() : 0);
    }
    public String getHoursWorked8Days() {
        return formatMinutes((cummulativeData8day != null) ? cummulativeData8day.getTotalMinWorked() : 0);
    }
    public String getHoursWorked14Days() {
        return formatMinutes((cummulativeData14day != null) ? cummulativeData14day.getTotalMinWorked() : 0);
    }

    public CummulativeData getCummulativeDataWithMostMinutesAvailToday()
    {
        long min7day = (cummulativeData7day != null) ? cummulativeData7day.getMinAvailToday() : 0; 
        long min8day = (cummulativeData8day != null) ? cummulativeData8day.getMinAvailToday() : 0;
        long min14day = (cummulativeData14day != null) ? cummulativeData14day.getMinAvailToday() : 0;

        if (min7day > min8day && min7day > min14day)
            return cummulativeData7day;
        if (min8day > min7day && min8day > min14day)
            return cummulativeData8day;
        if (min14day > min8day && min14day > min7day)
            return cummulativeData14day;
        return cummulativeData7day;
    }

    public CummulativeData getCummulativeDataWithMostMinutesAvailTomorrow()
    {
        long min7day = (cummulativeData7day != null) ? cummulativeData7day.getMinAvailTomorrow() : 0; 
        long min8day = (cummulativeData8day != null) ? cummulativeData8day.getMinAvailTomorrow() : 0;
        long min14day = (cummulativeData14day != null) ? cummulativeData14day.getMinAvailTomorrow() : 0;

        if (min7day > min8day && min7day > min14day)
            return cummulativeData7day;
        if (min8day > min7day && min8day > min14day)
            return cummulativeData8day;
        if (min14day > min8day && min14day > min7day)
            return cummulativeData14day;
        return cummulativeData7day;
    }

    public int getRecapDay(DateTime day)
    {
//        GregorianCalendar resetDayCalendar = new GregorianCalendar();
//        resetDayCalendar.setTime(cummData.getStartTime());
//
//        logger.debug("Cumm Reset Date" + cummData.getStartTime());
//        logger.debug("Current Date" + currentDayCalendar.getTime());
//
//        int daysDiff = currentDayCalendar.get(Calendar.DAY_OF_YEAR) - resetDayCalendar.get(Calendar.DAY_OF_YEAR);
//        return daysDiff + 1;
        
        DateTime resetDay = new DateTime(cummulativeDataBestToday.getStartTime());
//        DateTime currentDay = new DateTime(cummulativeDataBestToday.getCurrentTime());
//        int daysDiff = currentDay.getDayOfYear() - resetDay.getDayOfYear();
        int daysDiff = day.getDayOfYear() - resetDay.getDayOfYear();
        return daysDiff + 1;
    }
    public String getHoursAvailTomorrow() {
        return formatMinutes(cummulativeDataBestTomorrow.getMinAvailTomorrow());
    }


}
