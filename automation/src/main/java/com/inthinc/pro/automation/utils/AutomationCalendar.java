package com.inthinc.pro.automation.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.inthinc.pro.automation.enums.TextEnum;

/**
 * @author dtanner
 * 
 */
public class AutomationCalendar implements Comparable<Calendar> {

    public static enum WebDateFormat implements TextEnum {
        NOTE_DATE_TIME("MMM d, yyyy HH:mm a (z)"),
        HOS_REPORTS("yyyy-MM-dd HH:mm"),
        FUEL_STOPS("MMM d, yyyy HH:mm:ss a (z)"),
        DATE_RANGE_FIELDS("MMM d, yyyy"),
        TIME("HH:mm a (z)"),
        
        DURATION("HH:mm:ss"),
        
        ;

        private String format;

        private WebDateFormat(String format) {
            this.format = format;
        }

        public String getText() {
            return format;
        }

        @Override
        public String toString() {
            return format;
        }

    }

    public static final String getCurrentMonth() {
        Calendar today = GregorianCalendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMMM");
        String month = sdf.format(today.getTime());
        return month;
    }

    public static final String[] getFiveDayPeriodLong() {
        String[] timeFrame = new String[5];
        Calendar today = GregorianCalendar.getInstance();
        today.add(Calendar.DATE, -2);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");

        for (int i = 0; i < 5; i++) {
            timeFrame[i] = sdf.format(today.getTime());
            today.add(Calendar.DATE, -1);
        }

        return timeFrame;
    }

    public static final String[] getFiveDayPeriodShort() {
        String[] timeFrame = new String[5];
        Calendar today = GregorianCalendar.getInstance();
        today.add(Calendar.DATE, -2);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE");

        for (int i = 0; i < 5; i++) {
            timeFrame[i] = sdf.format(today.getTime());
            today.add(Calendar.DATE, -1);
        }

        return timeFrame;
    }

    public static final String[] getTimeFrameOptions() {
        String[] timeFrame = new String[11];
        String[] fiveDays = getFiveDayPeriodLong();

        timeFrame[0] = "Today";
        timeFrame[1] = "Yesterday";

        for (int i = 2; i < 7; i++) {
            timeFrame[i] = fiveDays[i - 2];
        }

        timeFrame[7] = "Past Week";
        timeFrame[8] = getCurrentMonth();
        timeFrame[9] = "Past 30 Days";
        timeFrame[10] = "Past Year";

        return timeFrame;
    }

    private Calendar date;

    private String format;

    private WebDateFormat rawEnum;

    private SimpleDateFormat formatter;

    public AutomationCalendar(Calendar date, WebDateFormat format) {
        this(format);
        this.date = date;
    }

    public AutomationCalendar(String dateTime, WebDateFormat format) {
        this(format);
        setDate(dateTime);
    }

    public AutomationCalendar(WebDateFormat format) {
        this.format = format.toString();
        rawEnum = format;
        formatter = new SimpleDateFormat(this.format);
        date = GregorianCalendar.getInstance();
    }

    public void addToDay(int amount) {
        date.add(Calendar.DAY_OF_YEAR, amount);
    }

    public void addToMilliseconds(int amount) {
        date.add(Calendar.MILLISECOND, amount);
    }

    public void addToMinutes(int amount) {
        date.add(Calendar.MINUTE, amount);
    }

    public void addToMonth(int amount) {
        date.add(Calendar.MONTH, amount);
    }

    public void addToSeconds(int amount) {
        date.add(Calendar.SECOND, amount);
    }

    public void addToYear(int amount) {
        date.add(Calendar.YEAR, amount);
    }

    public void changeDayOfMonthTo(int amount) {
        date.set(Calendar.DAY_OF_MONTH, amount);
    }

    public void changeDayOfWeekTo(int amount) {
        date.set(Calendar.DAY_OF_WEEK, amount);
    }

    public void changeDayOfYearTo(int amount) {
        date.set(Calendar.DAY_OF_YEAR, amount);
    }

    public void changeMillisecondsTo(int amount) {
        date.set(Calendar.MILLISECOND, amount);
    }

    public void changeMinutesTo(int amount) {
        date.set(Calendar.MINUTE, amount);
    }

    public void changeMonthTo(int amount) {
        date.set(Calendar.MONTH, amount);
    }

    public void changeSecondsTo(int amount) {
        date.set(Calendar.SECOND, amount);
    }

    public void changeYearTo(int amount) {
        date.set(Calendar.YEAR, amount);
    }

    public boolean compareDays(AutomationCalendar compareAgainst) {
        compareAgainst.changeMillisecondsTo(0);
        compareAgainst.changeMinutesTo(0);
        compareAgainst.changeSecondsTo(0);
        AutomationCalendar original = new AutomationCalendar(date, rawEnum);
        original.changeMillisecondsTo(0);
        original.changeMinutesTo(0);
        original.changeSecondsTo(0);
        return compareAgainst.equals(original);
    }

    public boolean compareDays(Calendar compareAgainst) {
        AutomationCalendar provided = new AutomationCalendar(compareAgainst, rawEnum);
        return compareDays(provided);
    }

    public boolean compareDays(String compareAgainst) {
        AutomationCalendar provided = new AutomationCalendar(compareAgainst, rawEnum);
        return compareDays(provided);
    }

    /**
     * Returns the time of the current object minus the given time
     * 
     * @param AutomationCalendar
     * @return
     */
    public int compareTo(AutomationCalendar o) {
        return (int) (getEpochTime() - o.getEpochTime());
    }

    @Override
    public int compareTo(Calendar o) {
        return date.compareTo(o);
    }

    /**
     * Returns the time of the current object minus the given time
     * 
     * @param AutomationCalendar
     * @return
     */
    public int compareTo(String o) {
        return compareTo(new AutomationCalendar(o, rawEnum));
    }

    public boolean dateInRange(AutomationCalendar start, AutomationCalendar stop) {
        return dateInRange(start.getCalendar(), stop.getCalendar());
    }

    public boolean dateInRange(Calendar start, Calendar stop) {
        if (start.getTimeInMillis() > stop.getTimeInMillis()) {
            throw new IllegalArgumentException("Start: " + formatter.format(start.getTime()) + " is after Stop: " 
                    + formatter.format(stop.getTime()));
        }
        return (start.compareTo(date) <= 0 && stop.compareTo(date) >= 0);
    }

    public boolean dateInRange(String start, String stop) {
        AutomationCalendar begin = new AutomationCalendar(start, rawEnum);
        AutomationCalendar end = new AutomationCalendar(stop, rawEnum);
        return dateInRange(begin, end);
    }

    @Override
    public boolean equals(Object obj) {
        return date.equals(obj);
    }

    public Calendar getCalendar() {
        return date;
    }

    public Date getDate() {
        return date.getTime();
    }

    public long getEpochTime() {
        return date.getTimeInMillis();
    }

    public void setDate(String dateTime) {
        try {
            date.setTime(formatter.parse(dateTime));
        } catch (ParseException e) {
            throw new IllegalArgumentException(dateTime + " does not match the pattern " + formatter.toPattern());
        }
    }

    public void setFormat(WebDateFormat format) {
        this.format = format.toString();
    }

    @Override
    public String toString() {
        return formatter.format(date.getTime());
    }

    public String toString(Calendar date) {
        SimpleDateFormat yesterday = new SimpleDateFormat(format);
        return yesterday.format(date);
    }

}
