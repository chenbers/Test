package com.inthinc.pro.model.aggregation;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.inthinc.pro.model.BaseEnum;

public enum MetricType implements BaseEnum {
    STARTING_ODOMETER(0, EnumSet.of(MetricCategoryType.NONE)),
    ENDING_ODOMETER(1, EnumSet.of(MetricCategoryType.NONE)),
    ODOMETER(2, EnumSet.of(MetricCategoryType.NONE)),
    OVERALL(3, EnumSet.of(MetricCategoryType.OVERALL)),
    SPEEDING(4, EnumSet.of(MetricCategoryType.SPEEDING, MetricCategoryType.OVERALL)),
    SPEEDING_1(5, EnumSet.of(MetricCategoryType.SPEEDING)),
    SPEEDING_2(6, EnumSet.of(MetricCategoryType.SPEEDING)),
    SPEEDING_3(7, EnumSet.of(MetricCategoryType.SPEEDING)),
    SPEEDING_4(8, EnumSet.of(MetricCategoryType.SPEEDING)),
    SPEEDING_5(9, EnumSet.of(MetricCategoryType.SPEEDING)),
    DRIVING_STYLE(10, EnumSet.of(MetricCategoryType.DRIVING_STYLE, MetricCategoryType.OVERALL)),
    AGGRESSIVE_BRAKE(11, EnumSet.of(MetricCategoryType.DRIVING_STYLE)),
    AGGRESSIVE_ACCEL(12, EnumSet.of(MetricCategoryType.DRIVING_STYLE)),
    AGGRESSIVE_TURN(13, EnumSet.of(MetricCategoryType.DRIVING_STYLE)),
    AGGRESSIVE_LEFT(14, EnumSet.of(MetricCategoryType.DRIVING_STYLE)),
    AGGRESSIVE_RIGHT(15, EnumSet.of(MetricCategoryType.DRIVING_STYLE)),
    AGGRESSIVE_BUMP(16, EnumSet.of(MetricCategoryType.DRIVING_STYLE)),
    SEAT_BELT(17, EnumSet.of(MetricCategoryType.SEAT_BELT, MetricCategoryType.OVERALL)),
    COACHING(18, EnumSet.of(MetricCategoryType.COACHING, MetricCategoryType.OVERALL)),
    MPG_LIGHT(19, EnumSet.of(MetricCategoryType.FUEL_EFFICIENCY)),
    MPG_MEDIUM(20, EnumSet.of(MetricCategoryType.FUEL_EFFICIENCY)),
    MPG_HEAVY(21, EnumSet.of(MetricCategoryType.FUEL_EFFICIENCY)),
    IDLE_LO(22, EnumSet.of(MetricCategoryType.IDLING)),
    IDLE_HI(23, EnumSet.of(MetricCategoryType.IDLING)),
    DRIVE_TIME(24, EnumSet.of(MetricCategoryType.NONE));

    private int code;
    private Set<MetricCategoryType> categorySet;
    private static final Map<MetricCategoryType, Set<MetricType>> categoryMap = new EnumMap<MetricCategoryType, Set<MetricType>>(MetricCategoryType.class);

    static {
        for (MetricCategoryType category : MetricCategoryType.values()) {
            final Set<MetricType> set = new HashSet<MetricType>();
            categoryMap.put(category, set);
        }
        for (MetricType type : MetricType.values()) {
            for (MetricCategoryType category : type.getCategories()) {
                MetricType.categoryMap.get(category).add(type);
            }
        }
    }

    private MetricType(int code, Set<MetricCategoryType> categorySet) {
        this.code = code;
        this.categorySet = categorySet;
    }

    public Set<MetricCategoryType> getCategories() {
        return categorySet;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }

    public enum MetricCategoryType {
        NONE,
        OVERALL,
        SPEEDING,
        SEAT_BELT,
        DRIVING_STYLE,
        COACHING,
        FUEL_EFFICIENCY,
        IDLING;

        public Set<MetricType> getMetrics() {
            return MetricType.categoryMap.get(this);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
            sb.append(".");
            sb.append(this.name());
            return sb.toString();
        }
    }
}
