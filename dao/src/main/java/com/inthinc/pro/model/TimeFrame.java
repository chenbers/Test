package com.inthinc.pro.model;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.Period;

public enum TimeFrame {

    TODAY(AggregationDuration.ONE_DAY) {
        public Interval getInterval() {
            return new Interval(new DateMidnight(new DateTime().minusDays(1), DateTimeZone.UTC), new DateMidnight(new DateTime(), DateTimeZone.UTC));
        }
    },
    DAY(AggregationDuration.ONE_DAY) {
        public Interval getInterval() {
            return new Interval(new DateMidnight(new DateTime().minusDays(1), DateTimeZone.UTC), new DateMidnight(new DateTime(), DateTimeZone.UTC));
        }
    },
    ONE_DAY_AGO(AggregationDuration.ONE_DAY) {
        public Interval getInterval() {
            return new Interval(new DateMidnight(new DateTime().minusDays(2), DateTimeZone.UTC), new DateMidnight(new DateTime().minusDays(1), DateTimeZone.UTC));
        }
    },
    TWO_DAYS_AGO(AggregationDuration.ONE_DAY) {
        public Interval getInterval() {
            return new Interval(new DateMidnight(new DateTime().minusDays(3), DateTimeZone.UTC), new DateMidnight(new DateTime().minusDays(2), DateTimeZone.UTC));
        }
    },
    THREE_DAYS_AGO(AggregationDuration.ONE_DAY) {
        public Interval getInterval() {
            return new Interval(new DateMidnight(new DateTime().minusDays(4), DateTimeZone.UTC), new DateMidnight(new DateTime().minusDays(3), DateTimeZone.UTC));
        }
    },
    FOUR_DAYS_AGO(AggregationDuration.ONE_DAY) {
        public Interval getInterval() {
            return new Interval(new DateMidnight(new DateTime().minusDays(5), DateTimeZone.UTC), new DateMidnight(new DateTime().minusDays(4), DateTimeZone.UTC));
        }
    },
    FIVE_DAYS_AGO(AggregationDuration.ONE_DAY) {
        public Interval getInterval() {
            return new Interval(new DateMidnight(new DateTime().minusDays(6), DateTimeZone.UTC), new DateMidnight(new DateTime().minusDays(5), DateTimeZone.UTC));
        }
    },
    SIX_DAYS_AGO(AggregationDuration.ONE_DAY) {
        public Interval getInterval() {
            return new Interval(new DateMidnight(new DateTime().minusDays(7), DateTimeZone.UTC), new DateMidnight(new DateTime().minusDays(6), DateTimeZone.UTC));
        }
    },
    SEVEN_DAYS_AGO(AggregationDuration.ONE_DAY) {
        public Interval getInterval() {
            return new Interval(new DateMidnight(new DateTime().minusDays(8), DateTimeZone.UTC), new DateMidnight(new DateTime().minusDays(7), DateTimeZone.UTC));
        }
    },
    WEEK(AggregationDuration.SEVEN_DAY) {
        public Interval getInterval() {
            return new Interval(new DateMidnight(new DateTime().minusWeeks(1), DateTimeZone.UTC), new DateMidnight(new DateTime(), DateTimeZone.UTC));
        }
    },
    MONTH(AggregationDuration.ONE_MONTH) {
        public Interval getInterval() {
            return new Interval(new DateMidnight(new DateTime().minusMonths(1), DateTimeZone.UTC), new DateMidnight(new DateTime(), DateTimeZone.UTC));
        }
    },
    THREE_MONTHS(AggregationDuration.THREE_MONTH) {
        public Interval getInterval() {
            return new Interval(new DateMidnight(new DateTime().minusMonths(3), DateTimeZone.UTC), new DateMidnight(new DateTime(), DateTimeZone.UTC));
        }
    },
    SIX_MONTHS(AggregationDuration.SIX_MONTH) {
        public Interval getInterval() {
            return new Interval(new DateMidnight(new DateTime().minusMonths(6), DateTimeZone.UTC), new DateMidnight(new DateTime(), DateTimeZone.UTC));
        }
    },
    YEAR(AggregationDuration.TWELVE_MONTH) {
        public Interval getInterval() {
            return new Interval(new DateMidnight(new DateTime().minusYears(1), DateTimeZone.UTC), new DateMidnight(new DateTime(), DateTimeZone.UTC));
        }
    };

    private AggregationDuration driveQDuration;

    TimeFrame(AggregationDuration driveQDuration) {
        this.driveQDuration = driveQDuration;
    }

    abstract public Interval getInterval();
    
    public Duration getDuration() {
        return getInterval().toDuration();
    }
    
    public Period getPeriod() {
        return getInterval().toPeriod();
    }

    public AggregationDuration getAggregationDuration() {
        return driveQDuration;
    }
}
