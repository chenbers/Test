package com.inthinc.pro.model;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.Period;

public enum TimeFrame {

    TODAY(AggregationDuration.ONE_DAY) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime(), dateTimeZone), new DateMidnight(new DateTime().plusDays(1), dateTimeZone));
        }
    },
    DAY(AggregationDuration.ONE_DAY) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime(), dateTimeZone), new DateMidnight(new DateTime().plusDays(1), dateTimeZone));
        }
    },
    ONE_DAY_AGO(AggregationDuration.ONE_DAY) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().minusDays(1), dateTimeZone), new DateMidnight(new DateTime(), dateTimeZone));
        }
    },
    TWO_DAYS_AGO(AggregationDuration.ONE_DAY) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().minusDays(2), dateTimeZone), new DateMidnight(new DateTime().minusDays(1), dateTimeZone));
        }
    },
    THREE_DAYS_AGO(AggregationDuration.ONE_DAY) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().minusDays(3), dateTimeZone), new DateMidnight(new DateTime().minusDays(2), dateTimeZone));
        }
    },
    FOUR_DAYS_AGO(AggregationDuration.ONE_DAY) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().minusDays(4), dateTimeZone), new DateMidnight(new DateTime().minusDays(3), dateTimeZone));
        }
    },
    FIVE_DAYS_AGO(AggregationDuration.ONE_DAY) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().minusDays(5), dateTimeZone), new DateMidnight(new DateTime().minusDays(4), dateTimeZone));
        }
    },
    SIX_DAYS_AGO(AggregationDuration.ONE_DAY) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().minusDays(6), dateTimeZone), new DateMidnight(new DateTime().minusDays(5), dateTimeZone));
        }
    },
    SEVEN_DAYS_AGO(AggregationDuration.ONE_DAY) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().minusDays(7), dateTimeZone), new DateMidnight(new DateTime().minusDays(6), dateTimeZone));
        }
    },
    WEEK(AggregationDuration.SEVEN_DAY) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().minusWeeks(1), dateTimeZone), new DateMidnight(new DateTime().plusDays(1), dateTimeZone));
        }
    },
    LAST_THIRTY_DAYS(AggregationDuration.ONE_MONTH) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().minusDays(30), dateTimeZone), new DateMidnight(new DateTime().plusDays(1), dateTimeZone));
        }
    },
    MONTH(AggregationDuration.ONE_MONTH) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().monthOfYear().toInterval().getStart(), dateTimeZone), new DateMidnight(new DateTime().plusDays(1), dateTimeZone));
        }
        
    },
    THREE_MONTHS(AggregationDuration.THREE_MONTH) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().minusMonths(3), dateTimeZone), new DateMidnight(new DateTime().plusDays(1), dateTimeZone));
        }
    },
    SIX_MONTHS(AggregationDuration.SIX_MONTH) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().minusMonths(6), dateTimeZone), new DateMidnight(new DateTime().plusDays(1), dateTimeZone));
        }
    },
    YEAR(AggregationDuration.TWELVE_MONTH) {
        public Interval getInterval(DateTimeZone dateTimeZone) {
            return new Interval(new DateMidnight(new DateTime().minusYears(1), dateTimeZone), new DateMidnight(new DateTime().plusDays(1), dateTimeZone));
        }
    };

    private AggregationDuration driveQDuration;

    TimeFrame(AggregationDuration driveQDuration) {
        this.driveQDuration = driveQDuration;
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
}
