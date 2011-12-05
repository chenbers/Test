package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.Period;

public enum TimeFrame implements BaseEnum {

    TODAY(AggregationDuration.ONE_DAY, 0) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime(), dateTimeZone), new DateMidnight(new DateTime().plusDays(1), dateTimeZone));
        }
    },
    DAY(AggregationDuration.ONE_DAY, 1) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime(), dateTimeZone), new DateMidnight(new DateTime().plusDays(1), dateTimeZone));
        }
    },
    ONE_DAY_AGO(AggregationDuration.ONE_DAY, 2) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().minusDays(1), dateTimeZone), new DateMidnight(new DateTime(), dateTimeZone));
        }
    },
    TWO_DAYS_AGO(AggregationDuration.ONE_DAY, 3) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().minusDays(2), dateTimeZone), new DateMidnight(new DateTime().minusDays(1), dateTimeZone));
        }
    },
    THREE_DAYS_AGO(AggregationDuration.ONE_DAY, 4) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().minusDays(3), dateTimeZone), new DateMidnight(new DateTime().minusDays(2), dateTimeZone));
        }
    },
    FOUR_DAYS_AGO(AggregationDuration.ONE_DAY, 5) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().minusDays(4), dateTimeZone), new DateMidnight(new DateTime().minusDays(3), dateTimeZone));
        }
    },
    FIVE_DAYS_AGO(AggregationDuration.ONE_DAY, 6) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().minusDays(5), dateTimeZone), new DateMidnight(new DateTime().minusDays(4), dateTimeZone));
        }
    },
    SIX_DAYS_AGO(AggregationDuration.ONE_DAY, 7) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().minusDays(6), dateTimeZone), new DateMidnight(new DateTime().minusDays(5), dateTimeZone));
        }
    },
    SEVEN_DAYS_AGO(AggregationDuration.ONE_DAY, 8) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().minusDays(7), dateTimeZone), new DateMidnight(new DateTime().minusDays(6), dateTimeZone));
        }
    },
    WEEK(AggregationDuration.SEVEN_DAY, 9) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().minusDays(6), dateTimeZone), new DateMidnight(new DateTime().plusDays(1), dateTimeZone));
//            return new Interval(new DateMidnight(new DateTime().minusWeeks(1), dateTimeZone), new DateMidnight(new DateTime().plusDays(1), dateTimeZone));            
        }
    },
    LAST_THIRTY_DAYS(AggregationDuration.ONE_MONTH, 10) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().minusDays(30), dateTimeZone), new DateMidnight(new DateTime().plusDays(1), dateTimeZone));
        }
    },
    MONTH(AggregationDuration.ONE_MONTH, 11) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().monthOfYear().toInterval().getStart(), dateTimeZone), new DateMidnight(new DateTime().plusDays(1), dateTimeZone));
        }
        
    },
    THREE_MONTHS(AggregationDuration.THREE_MONTH, 12) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().minusMonths(3), dateTimeZone), new DateMidnight(new DateTime().plusDays(1), dateTimeZone));
        }
    },
    SIX_MONTHS(AggregationDuration.SIX_MONTH, 13) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().minusMonths(6), dateTimeZone), new DateMidnight(new DateTime().plusDays(1), dateTimeZone));
        }
    },
    YEAR(AggregationDuration.TWELVE_MONTH, 14) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().minusYears(1), dateTimeZone), new DateMidnight(new DateTime().plusDays(1), dateTimeZone));
        }
    },
    PAST_MONTH(AggregationDuration.ONE_MONTH, 15) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().monthOfYear().toInterval().getStart().minusMonths(1), dateTimeZone), new DateMidnight(new DateTime().monthOfYear().toInterval().getStart(), dateTimeZone));
        }
    };

    private AggregationDuration driveQDuration;
    private Integer code;

    TimeFrame(AggregationDuration driveQDuration, Integer code) {
        this.driveQDuration = driveQDuration;
        this.code = code;
    }

    abstract public Interval getInterval(DateTimeZone dateTimeZone);

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
