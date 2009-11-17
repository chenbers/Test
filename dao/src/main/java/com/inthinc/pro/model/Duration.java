package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/* Duration Enum
 * 
 * 4/1/2009 - dvqCode added to retrieve 7 day aggregation.
 * 
 * code - specifies cumulative of entities based on 30 day aggregation
 * dvqCode - specifies cumulative of entities based on 7 day aggregation
 * dvqCount - specifies number of records to retrieve
 */

public enum Duration implements BaseEnum
{
    DAYS(1, "30 days", 30, "dd", 0, 30, 6), 
    THREE(3, "3 months", 90, "MMM", 2, 3, 3), 
    SIX(4, "6 months", 180, "MMM", 2, 6, 4), 
    TWELVE(5, "12 months", 360, "MMM", 2, 12, 5);

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
    
//	private static final int BINSIZE_1_DAY = 0;
//    private static final int BINSIZE_7_DAY = 1;
//    private static final int BINSIZE_1_MONTH = 2;
//    private static final int BINSIZE_3_MONTHS = 3;
//    private static final int BINSIZE_6_MONTHS = 4;
//    private static final int BINSIZE_12_MONTHS = 5;
//    private static final int BINSIZE_30_DAYS = 6;


	private final int numberOfDays;
    private String durationValue;
    private Integer code;
    private String datePattern;
    private Integer aggregationBinSize;
    private Integer dvqCount;
    private Integer dvqCode;
    
    
    private static final Map<Integer, Duration> lookup = new HashMap<Integer, Duration>();
    
    static
    {
        for(Duration d : EnumSet.allOf(Duration.class))
            lookup.put(d.getNumberOfDays(), d);
    }

    private static final Map<Integer, Duration> codeLookup = new HashMap<Integer, Duration>();
    
    static
    {
        for(Duration d : EnumSet.allOf(Duration.class))
            codeLookup.put(d.getCode(), d);
    }
    Duration(Integer code, String durationValue, int numberOfDays, String datePattern, Integer aggregationBinSize, Integer dvqCount, Integer dvqCode)
    {
        this.code = code;
        this.durationValue = durationValue;
        this.numberOfDays = numberOfDays;
        this.datePattern = datePattern;
        this.aggregationBinSize = aggregationBinSize;
        this.dvqCode = dvqCode;
        this.dvqCount = dvqCount;
    }

    public int getNumberOfDays()
    {
        return this.numberOfDays;
    }
    
    public static Duration getDurationByDays(int numberOfDays)
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

    public static Duration valueOf(Integer code)
    {
        return codeLookup.get(code);
    }
       
    public String getDatePattern()
    {
        return this.datePattern;
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
