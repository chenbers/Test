package com.inthinc.pro.automation.objects;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.inthinc.pro.automation.enums.TimeZones;
import com.inthinc.pro.automation.enums.WebDateFormat;

/**
 * @author dtanner
 * 
 */
@JsonIgnoreType
public class AutomationCalendar implements Comparable<Calendar>, Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1571925441932456423L;

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

    public static AutomationCalendar now(){
        return new AutomationCalendar();
    }

    public static AutomationCalendar now(WebDateFormat format){
        return new AutomationCalendar(format);
    }

    private Calendar date;

    private WebDateFormat format;

    private SimpleDateFormat formatter;

    public AutomationCalendar(){
        this(WebDateFormat.NOTE_DATE_TIME);
    }
    
    public AutomationCalendar(Calendar calendar) {
        this();
        this.date = (Calendar) calendar.clone();
    }
    
    public AutomationCalendar(Calendar date, WebDateFormat format) {
        this(format);
        this.date.setTimeInMillis(date.getTimeInMillis());
    }
    
    public AutomationCalendar(Integer integer, WebDateFormat format) {
		this(format);
		setDate(integer);
	}
    
    public AutomationCalendar(Long epochTime) {
        this();
        setDate(epochTime);
    }

    public AutomationCalendar(String dateTime, WebDateFormat format) {
        this(format);
        
        if (dateTime.endsWith("Z")){
            dateTime = dateTime.substring(0, dateTime.indexOf(".")) + "-0000";
        }
        
        setDate(dateTime);
        if (format == WebDateFormat.RALLY_DATE_FORMAT){
            date.setTimeZone(TimeZones.US_MOUNTAIN.getTimeZone());
        }
    }

    public AutomationCalendar(WebDateFormat format) {
        this.format = format;
        formatter = new SimpleDateFormat(format.toString());
        date = GregorianCalendar.getInstance();
    }

    public AutomationCalendar(String potentialDate) {
    	if (potentialDate.endsWith("Z")){
            potentialDate = potentialDate.substring(0, potentialDate.indexOf(".")) + "-0000";
        }
        for (WebDateFormat format : EnumSet.allOf(WebDateFormat.class)){
            if ((format.toString().length() + 5) < potentialDate.length()){
                continue;
            } else if (potentialDate.contains("-") ^ format.toString().contains("-")){
                continue;
            }
            SimpleDateFormat matcher = new SimpleDateFormat(format.toString());
            try {
                matcher.parse(potentialDate);
                this.format = format;
                formatter = matcher;
                setDate(potentialDate);
                return;
            } catch (ParseException e){
                continue;
            }
        }
        throw new IllegalArgumentException(potentialDate + " doesn't match any known formats, try :" + WebDateFormat.DATE_RANGE_FIELDS);
    }

    public AutomationCalendar addToDay(int amount) {
        date.add(Calendar.DAY_OF_YEAR, amount);
        return this;
    }

	public AutomationCalendar addToHours(int i){
        date.add(Calendar.HOUR_OF_DAY, i);
        return this;
    }

    public AutomationCalendar addToMilliseconds(int amount) {
        date.add(Calendar.MILLISECOND, amount);
        return this;
    }

    public AutomationCalendar addToMinutes(int amount) {
        date.add(Calendar.MINUTE, amount);
        return this;
    }

    public AutomationCalendar addToMonth(int amount) {
        date.add(Calendar.MONTH, amount);
        return this;
    }

    public AutomationCalendar addToSeconds(int amount) {
        date.add(Calendar.SECOND, amount);
        return this;
    }

    public AutomationCalendar addToYear(int amount) {
        date.add(Calendar.YEAR, amount);
        return this;
    }

    public AutomationCalendar changeDayOfMonthTo(int amount) {
        date.set(Calendar.DAY_OF_MONTH, amount);
        return this;
    }

    public AutomationCalendar changeDayOfWeekTo(int amount) {
        date.set(Calendar.DAY_OF_WEEK, amount);
        return this;
    }

    public AutomationCalendar changeDayOfYearTo(int amount) {
        date.set(Calendar.DAY_OF_YEAR, amount);
        return this;
    }

    public AutomationCalendar changeHourseTo(int i) {
        date.set(Calendar.HOUR_OF_DAY, i);
        return this;
    }
    
    public AutomationCalendar changeMillisecondsTo(int amount) {
        date.set(Calendar.MILLISECOND, amount);
        return this;
    }

    public AutomationCalendar changeMinutesTo(int amount) {
        date.set(Calendar.MINUTE, amount);
        return this;
    }

    public AutomationCalendar changeMonthTo(int amount) {
        date.set(Calendar.MONTH, amount);
        return this;
    }

    public AutomationCalendar changeSecondsTo(int amount) {
        date.set(Calendar.SECOND, amount);
        return this;
    }

    public AutomationCalendar changeYearTo(int amount) {
        date.set(Calendar.YEAR, amount);
        return this;
    }

    /**
     * Compares the two objects to see if they are on the same day.
     * @param compareAgainst
     * @return
     */
    public boolean compareDays(AutomationCalendar compareAgainst) {
        compareAgainst.zeroTimeOfDay();
        AutomationCalendar original = new AutomationCalendar(date, format);
        original.zeroTimeOfDay();
        return compareAgainst.equals(original);
    }

    /**
     * Compares the two objects to see if they are on the same day.
     * @param compareAgainst
     * @return
     */
    public boolean compareDays(Calendar compareAgainst) {
        AutomationCalendar provided = new AutomationCalendar(compareAgainst, format);
        return compareDays(provided);
    }

    /**
     * Compares the two objects to see if they are on the same day.
     * @param compareAgainst
     * @return
     */
    public boolean compareDays(String compareAgainst) {
        AutomationCalendar provided = new AutomationCalendar(compareAgainst, format);
        return compareDays(provided);
    }
    

    /**
     * Returns the time of the current object minus the given time
     * 
     * @param AutomationCalendar
     * @return
     */
    public int compareTo(AutomationCalendar o) {
        return (int) (epochSecondsInt() - o.epochSecondsInt());
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
        return compareTo(new AutomationCalendar(o, format));
    }

    public AutomationCalendar copy() {
        return new AutomationCalendar(date, format);
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
        AutomationCalendar begin = new AutomationCalendar(start, format);
        AutomationCalendar end = new AutomationCalendar(stop, format);
        return dateInRange(begin, end);
    }

    public Long epochSeconds() {
        return epochTime()/1000;
    }

    public Integer epochSecondsInt() {
        return epochSeconds().intValue();
    }

    public Long epochTime(){
        return date.getTimeInMillis();
    }

    @Override
    public boolean equals(Object obj) {
        boolean match = false;
        if (obj instanceof AutomationCalendar){
            AutomationCalendar ours = new AutomationCalendar(toString(), format);
            AutomationCalendar thiers = new AutomationCalendar(((AutomationCalendar) obj).toString(), ((AutomationCalendar) obj).format);
            
            match = epochSecondsInt().equals(((AutomationCalendar) obj).epochSecondsInt());
            match |= ours.toString().equals(thiers.toString());
        } else {
            match |= date.equals(obj);
            match |= date.getTime().equals(obj);
        }
        return match;
    }
    
    @Override
    public int hashCode(){
        return epochSecondsInt();
    }
    
    public Calendar getCalendar() {
        return date;
    }
    
    public Date getDate() {
        return date.getTime();
    }

    public int getDayOfWeek(){
        return this.date.get(Calendar.DAY_OF_WEEK);
    }
    
    public Long getDelta(AutomationCalendar time_last) {
        long delta = epochTime() - time_last.epochTime();
        return delta;
    }

    public WebDateFormat getFormat(){
        return format;
    }
    
    public int getMonth(){
        return this.date.get(Calendar.MONTH);
    }

    public int getWeekOfMonth(){
        return this.date.get(Calendar.WEEK_OF_MONTH);
    }

    public AutomationCalendar setDate(AutomationCalendar time){
        date.setTime(time.getDate());
        return this;
    }
    
    public AutomationCalendar setDate(int epochTimeWithoutMillis){
        setDate(epochTimeWithoutMillis * 1000l);
        return this;
    }
    
    public AutomationCalendar setDate(long ephochTime) {
        this.date.setTimeInMillis(ephochTime);
        return this;
    }
    
    public AutomationCalendar setDate(String dateTime) {
        if (date == null){
            date = Calendar.getInstance();
        }
        Pattern pat = Pattern.compile("(?<=\\d\\d:\\d\\d-?\\d\\d):(?=\\d\\d)"); // looking to make format fit for SimpleDateFormat Z -0800
        Matcher mat = pat.matcher(dateTime);
        if (mat.find()){
            dateTime = dateTime.substring(0, mat.start()) + dateTime.substring(mat.end());
        }
        
        try {
            date.setTime(formatter.parse(dateTime));
        } catch (ParseException e) {
            try {
                date.setTimeInMillis(Integer.parseInt(dateTime) * 1000l);
                return this;
            } catch (NumberFormatException e1){
                throw new IllegalArgumentException(dateTime + " does not match the pattern " + formatter.toPattern());    
            }
        }
        return this;
    }
    
    public AutomationCalendar setFormat(WebDateFormat format) {
        formatter = new SimpleDateFormat(format.toString());
        return this;
        
    }
    
    public AutomationCalendar setTimeZone(TimeZones timeZone){
        date.setTimeZone(timeZone.getTimeZone());
        this.formatter.setTimeZone(timeZone.getTimeZone());
        return this;
    }

    public int toInt() {
        Long time = date.getTimeInMillis()/1000;
        return time.intValue();
    }

    @Override
    public String toString() {
        return formatter.format(date.getTime());
    }

    public String toString(Calendar date) {
        SimpleDateFormat yesterday = new SimpleDateFormat(format.toString());
        return yesterday.format(date);
    }

    public String toString(WebDateFormat format){
        return new AutomationCalendar(this.getCalendar(), format).toString();
    }
    
    public AutomationCalendar zeroTimeOfDay(){
        setTimeZone(TimeZones.UTC);
        changeMillisecondsTo(0);
        changeMinutesTo(0);
        changeSecondsTo(0);
        changeHourseTo(0);
        return this;
    }

}
