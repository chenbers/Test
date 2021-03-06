package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.Period;

public enum TimeFrame implements BaseEnum {
    TODAY(AggregationDuration.ONE_DAY, 0) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(getCurrent(), dateTimeZone), new DateMidnight(getCurrent().plusDays(1), dateTimeZone).toDateTime().minusSeconds(1));
        }
    },
    DAY(AggregationDuration.ONE_DAY, 1) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(getCurrent(), dateTimeZone), new DateMidnight(getCurrent().plusDays(1), dateTimeZone).toDateTime().minusSeconds(1));
        }
    },
    ONE_DAY_AGO(AggregationDuration.ONE_DAY, 2) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(getCurrent().minusDays(1), dateTimeZone), new DateMidnight(getCurrent(), dateTimeZone).toDateTime().minusSeconds(1));
        }
    },
    TWO_DAYS_AGO(AggregationDuration.ONE_DAY, 3) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(getCurrent().minusDays(2), dateTimeZone), new DateMidnight(getCurrent().minusDays(1), dateTimeZone).toDateTime().minusSeconds(1));
        }
    },
    THREE_DAYS_AGO(AggregationDuration.ONE_DAY, 4) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(getCurrent().minusDays(3), dateTimeZone), new DateMidnight(getCurrent().minusDays(2), dateTimeZone).toDateTime().minusSeconds(1));
        }
    },
    FOUR_DAYS_AGO(AggregationDuration.ONE_DAY, 5) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(getCurrent().minusDays(4), dateTimeZone), new DateMidnight(getCurrent().minusDays(3), dateTimeZone).toDateTime().minusSeconds(1));
        }
    },
    FIVE_DAYS_AGO(AggregationDuration.ONE_DAY, 6) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(getCurrent().minusDays(5), dateTimeZone), new DateMidnight(getCurrent().minusDays(4), dateTimeZone).toDateTime().minusSeconds(1));
        }
    },
    SIX_DAYS_AGO(AggregationDuration.ONE_DAY, 7) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(getCurrent().minusDays(6), dateTimeZone), new DateMidnight(getCurrent().minusDays(5), dateTimeZone).toDateTime().minusSeconds(1));
        }
    },
    SEVEN_DAYS_AGO(AggregationDuration.ONE_DAY, 8) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(getCurrent().minusDays(7), dateTimeZone), new DateMidnight(getCurrent().minusDays(6), dateTimeZone).toDateTime().minusSeconds(1));
        }
    },
    WEEK(AggregationDuration.SEVEN_DAY, 9) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(getCurrent().minusDays(6), dateTimeZone), new DateMidnight(getCurrent().plusDays(1), dateTimeZone).toDateTime().minusSeconds(1));
//            return new Interval(new DateMidnight(getCurrent().minusWeeks(1), dateTimeZone), new DateMidnight(getCurrent().plusDays(1), dateTimeZone));            
        }
    },
    LAST_THIRTY_DAYS(AggregationDuration.ONE_MONTH, 10) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(getCurrent().minusDays(30), dateTimeZone), new DateMidnight(getCurrent().plusDays(1), dateTimeZone).toDateTime().minusSeconds(1));
        }
    },
    MONTH(AggregationDuration.ONE_MONTH, 11) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(getCurrent().monthOfYear().toInterval().getStart(), dateTimeZone), new DateMidnight(getCurrent().plusDays(1), dateTimeZone).toDateTime().minusSeconds(1));
        }
    },
    THREE_MONTHS(AggregationDuration.THREE_MONTH, 12) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(getCurrent().minusMonths(3), dateTimeZone), new DateMidnight(getCurrent().plusDays(1), dateTimeZone).toDateTime().minusSeconds(1));
        }
    },
    SIX_MONTHS(AggregationDuration.SIX_MONTH, 13) {
        public Interval getInterval(DateTimeZone dateTimeZone) { 
            return new Interval(new DateMidnight(getCurrent(dateTimeZone).minusMonths(6), dateTimeZone), new DateMidnight(getCurrent(dateTimeZone).plusDays(1), dateTimeZone).toDateTime().minusSeconds(1));
        }
    },
    YEAR(AggregationDuration.TWELVE_MONTH, 14) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(getCurrent().minusYears(1), dateTimeZone), new DateMidnight(getCurrent().plusDays(1), dateTimeZone).toDateTime().minusSeconds(1));
        }
    },
    SUN_SAT_WEEK(AggregationDuration.SEVEN_DAY, 15) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            DateTime lastSat = new DateMidnight(getCurrent().minusDays(1),dateTimeZone).toDateTime();
            while (lastSat.getDayOfWeek() != DateTimeConstants.SATURDAY)
                lastSat = new DateTime(lastSat.minusDays(1),dateTimeZone);
            return new Interval(new DateTime(lastSat.minusDays(6), dateTimeZone), new DateMidnight(lastSat.plusDays(1), dateTimeZone).toDateTime().minusSeconds(1));
        }
    },
    LAST_MONTH(AggregationDuration.ONE_MONTH, 16) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(getCurrent().monthOfYear().toInterval().getStart().minusMonths(1), dateTimeZone), new DateMidnight(getCurrent().monthOfYear().toInterval().getStart(), dateTimeZone).toDateTime().minusSeconds(1));
        }
    },
    PAST_SEVEN_DAYS(AggregationDuration.SEVEN_DAY, 17) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(getCurrent().minusDays(7), dateTimeZone), new DateMidnight(getCurrent(), dateTimeZone).toDateTime().minusSeconds(1));
        }
    },
    CUSTOM_RANGE(AggregationDuration.ONE_DAY, 18){
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(getCurrent(), dateTimeZone), new DateMidnight(getCurrent().plusDays(1), dateTimeZone).toDateTime().minusSeconds(1));
        }
        @Override
        public Interval getInterval(long startInstant, long endInstant, DateTimeZone zone){
        	return new Interval(startInstant, endInstant, zone);
        }
    };
    
    private static final Logger logger = Logger.getLogger(TimeFrame.class);
    private AggregationDuration driveQDuration;
    private Integer code;
    private static DateTime current;

    TimeFrame(AggregationDuration driveQDuration, Integer code) {
        this.driveQDuration = driveQDuration;
        this.code = code;
    }

    abstract public Interval getInterval(DateTimeZone dateTimeZone);
    public Interval getInterval(long startInstant, long endInstant, DateTimeZone zone){
        logger.warn("getInterval(long, long, DateTimeZone) does NOT change the interval for TimeFrame."+this.toString());
        throw new IllegalArgumentException("getInterval(long, long, DateTimeZone) does NOT change the interval for TimeFrame."+this.toString()+"; and should not be called");
    }
    public Integer getNumberOfDays(){
        logger.info(this.getDuration().getMillis()+"/"+DateTimeConstants.MILLIS_PER_DAY+" == "+this.getDuration().getMillis()/DateTimeConstants.MILLIS_PER_DAY);
        //System.out.println(this.getDuration().getMillis()+"/"+DateTimeConstants.MILLIS_PER_DAY+" == "+this.getDuration().getMillis()/DateTimeConstants.MILLIS_PER_DAY);
        return (int) (this.getDuration().getMillis()/DateTimeConstants.MILLIS_PER_DAY);

    }

    public DateTime getCurrent(DateTimeZone zone) {
        if (current == null)
            return new DateTime(zone);
        return current;
    }
    public DateTime getCurrent() {
        if (current == null)
            return new DateTime();
        return current;
    }
    public static void setCurrentForTesting(DateTime testCurrent) {
        current = testCurrent;
    }
    
    public Interval getInterval() {
        return getInterval(DateTimeZone.UTC);
    }

    public Duration getDuration() {
        return getInterval().toDuration();
    }

    public Period getPeriod() {
        return getInterval().toPeriod();
    }

    public AggregationDuration getAggregationDuration() {
        return driveQDuration;
    }
    
    public Integer getCode()
    {
    	return code;
    }
    
    private static final Map<Integer, TimeFrame> codeLookup = new HashMap<Integer, TimeFrame>();
    static
    {
        for(TimeFrame d : EnumSet.allOf(TimeFrame.class))
            codeLookup.put(d.getCode(), d);
    }
    
    public static TimeFrame valueOf(Integer code)
    {
        return codeLookup.get(code);
    }
}
