package com.inthinc.pro.model;

public enum AggregationDuration {
    // typedef enum
    // {
    // DriveQDuration1Day = 0,
    // DriveQDuration7Day = 1,
    // DriveQDuration1Month = 2,
    // DriveQDuration3Month = 3,
    // DriveQDuration6Month = 4,
    // DriveQDuration12Month = 5,
    // DriveQDuration30Day = 6
    // } DriveQDuration;

        ONE_DAY(0),
        SEVEN_DAY(1),
        THIRTY_DAY(6),
        ONE_MONTH(2),
        THREE_MONTH(3),
        SIX_MONTH(4),
        TWELVE_MONTH(5);

        private int code;

        AggregationDuration(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
}
