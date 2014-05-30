package com.inthinc.pro.model;

import java.util.Calendar;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom durations.
 */
public enum CustomDuration implements BaseEnum
{
    SEVEN_DAYS(5, "7 days", 7, "dd", 0, 7, 6);

    /*
     * dvqCode
	    0 - 1 day bins
	    1 - 7 day bins
	    2 - 1 month bins
	    3 - 3 month bins
	    4 - 6 month bins
	    5 - 12 month bins
	    6 - 30 day bins
    */

//    typedef enum
//    {
//        DriveQDuration1Day    = 0,
//        DriveQDuration7Day    = 1,
//        DriveQDuration1Month  = 2,
//        DriveQDuration3Month  = 3,
//        DriveQDuration6Month  = 4,
//        DriveQDuration12Month = 5,
//        DriveQDuration30Day    = 6
//    } DriveQDuration;

	public static final int BINSIZE_1_DAY = 0;
	public static final int BINSIZE_7_DAY = 1;
	public static final int BINSIZE_1_MONTH = 2;
	public static final int BINSIZE_3_MONTHS = 3;
	public static final int BINSIZE_6_MONTHS = 4;
	public static final int BINSIZE_12_MONTHS = 5;
	public static final int BINSIZE_30_DAYS = 6;


	private final int numberOfDays;
    private String durationValue;
    private Integer code;
    private String datePattern;
    private Integer aggregationBinSize;
    private Integer dvqCount;
    private Integer dvqCode;


    private static final Map<Integer, CustomDuration> lookup = new HashMap<Integer, CustomDuration>();

    static
    {
        for(CustomDuration d : EnumSet.allOf(CustomDuration.class))
            lookup.put(d.getNumberOfDays(), d);
    }

    private static final Map<Integer, CustomDuration> codeLookup = new HashMap<Integer, CustomDuration>();

    static
    {
        for(CustomDuration d : EnumSet.allOf(CustomDuration.class))
            codeLookup.put(d.getCode(), d);
    }
    CustomDuration(Integer code, String durationValue, int numberOfDays, String datePattern, Integer aggregationBinSize, Integer dvqCount, Integer dvqCode)
    {
        this.code = code;
        this.durationValue = durationValue;
        this.numberOfDays = numberOfDays;
        this.datePattern = datePattern;
        this.aggregationBinSize = aggregationBinSize;
        this.dvqCode = dvqCode;
        this.dvqCount = dvqCount;
    }

    public static int getCalendarPeriod(int binSize)
    {
    	int calendarPeriod = Calendar.DATE;
    	
    	switch (binSize)
    	{
	        case CustomDuration.BINSIZE_1_MONTH:
	        case CustomDuration.BINSIZE_3_MONTHS:
	        case CustomDuration.BINSIZE_6_MONTHS:
	        case CustomDuration.BINSIZE_12_MONTHS:
	        	calendarPeriod = Calendar.MONTH;
	        	break;
	        default:	
	        	calendarPeriod = Calendar.DATE;
    	}
    	return calendarPeriod;
    }
    

    public int getNumberOfDays()
    {
        return this.numberOfDays;
    }
    
    public static CustomDuration getDurationByDays(int numberOfDays)
    {
        return lookup.get(numberOfDays);
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }

    @Override
    public Integer getCode()
    {
        return code;
    }
    
    public Integer getDvqCode()
    {
        return dvqCode;
    }

    public static CustomDuration valueOf(Integer code)
    {
        return codeLookup.get(code);
    }
       
    public Integer getAggregationBinSize()
    {
        return aggregationBinSize;
    }

    public Integer getDvqCount()
    {
        return dvqCount;
    }

    public String getDurationValue() {
		return durationValue;
	}

}
