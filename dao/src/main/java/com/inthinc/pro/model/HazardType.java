package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.Period;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.zone.option.type.OptionValue;

public enum HazardType implements OptionValue {
    OTHER(0,                        5*MeasurementConversionUtil.METERS_IN_MILE , Severity.URGENT, Period.hours(72), "Other"),
    WEATHER_SLIPPERY(1,             10*MeasurementConversionUtil.METERS_IN_MILE, Severity.URGENT, Period.hours(72), "Weather - Slippery (Wet/Snow/Ice)"),
    WEATHER_FLOODING(2,             10*MeasurementConversionUtil.METERS_IN_MILE, Severity.URGENT, Period.hours(72), "Weather - Flooding"), 
    WEATHER_VISIBILITY(3,           10*MeasurementConversionUtil.METERS_IN_MILE, Severity.URGENT, Period.hours(72), "Weather - Low Visibility (Fog/Smoke)"),
    WEATHER_WINDGUSTS(4,            10*MeasurementConversionUtil.METERS_IN_MILE, Severity.URGENT, Period.hours(72), "Weather - Wind Gusts"),
    ROADACTIVITY_TRAFFIC(5,         5*MeasurementConversionUtil.METERS_IN_MILE , Severity.NORMAL, Period.hours(4), "Road Activity - Traffic"),
    ROADACTIVITY_DEBRIS(6,          5*MeasurementConversionUtil.METERS_IN_MILE , Severity.NORMAL, Period.hours(72), "Road Activity - Debris"),
    ROADRESTRICTIONS_WEIGHT(7,      10*MeasurementConversionUtil.METERS_IN_MILE, Severity.NORMAL, Period.years(1), "Road Restrictions - Weight Restrictions (Bridge)"),
    ROADRESTRICTIONS_HEIGHT(8,      10*MeasurementConversionUtil.METERS_IN_MILE, Severity.NORMAL, Period.years(1), "Road Restrictions - Height Restrictions (Overpass Clearance)"),
    ROADRESTRICTIONS_WIDTH(9,       10*MeasurementConversionUtil.METERS_IN_MILE, Severity.NORMAL, Period.years(1), "Road Restrictions - Width Restrictions (Narrow Lane)"),
    ROADRESTRICTIONS_BAN_CLOSURE(10,10*MeasurementConversionUtil.METERS_IN_MILE, Severity.NORMAL, Period.years(1), "Road Restrictions - Road Ban/Closure"),
    ROADRESTRICTIONS_SHARPTURN(11,  10*MeasurementConversionUtil.METERS_IN_MILE, Severity.NORMAL, Period.years(1), "Road Restrictions - Sharp Turn"),
    ROADRESTRICTIONS_STEEPGRADE(12, 10*MeasurementConversionUtil.METERS_IN_MILE, Severity.NORMAL, Period.years(1), "Road Restrictions - Steep Grade"),
    ROADRESTRICTIONS_MICRO(13,      15*MeasurementConversionUtil.METERS_IN_FOOT, Severity.NORMAL, Period.years(1), "Micro Hazards - Oil Well Head"),
  

/*  //This section more closely matches PRD 1.4, but does NOT coincide with firmware's doc???
    WEATHER_OTHER(14, 10*MeasurementConversionUtil.METERS_IN_MILE, Severity.URGENT, Period.hours(72), "Weather - Other"),
    WEATHER_SNOWICE(15, 10*MeasurementConversionUtil.METERS_IN_MILE, Severity.URGENT, Period.hours(72), "Weather - Snow/Ice"),
    ROADACTIVITY_CONSTRUCTION(16 , 5*MeasurementConversionUtil.METERS_IN_MILE, Severity.NORMAL, Period.months(3), "Road Activity - Construction"),
    ROADACTIVITY_ACCIDENT(17, 5*MeasurementConversionUtil.METERS_IN_MILE, Severity.NORMAL, Period.hours(4), "Road Activity - Accident"),
    ROADACTIVITY_DISABLEDVEHICLE(18, 5*MeasurementConversionUtil.METERS_IN_MILE, Severity.NORMAL, Period.hours(4), "Road Activity - Disabled Vehicle"),
    ROADACTIVITY_OTHER(19, 5*MeasurementConversionUtil.METERS_IN_MILE, Severity.NORMAL, Period.hours(72), "Road Activity - Other"),
    ROADRESTRICTIONS_CLOSURE(20, 5*MeasurementConversionUtil.METERS_IN_MILE, Severity.NORMAL, Period.years(1), "Road Restrictions - Closure"), //JUST closure above combined with road ban
    ROADRESTRICTIONS_OTHER(21, 5*MeasurementConversionUtil.METERS_IN_MILE, Severity.NORMAL, Period.years(1), "Road Restrictions - Other"),
  */
    ;

    private int code;
    private String name;
    private double radius;
    private Severity severity;
    private Period defaultDuration;

    private HazardType(int code, double radius, Severity severity, Period defaultDuration, String name) {
        this.code = code;
        this.name = name;
        this.radius = radius;
        this.severity = severity;
        this.defaultDuration = defaultDuration;
    }

    private static final Map<Integer, HazardType> lookup = new HashMap<Integer, HazardType>();
    static {
        for (HazardType p : EnumSet.allOf(HazardType.class)) {
            lookup.put(p.code, p);
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return getCode();
    }

    static public HazardType valueOf(Integer value) {
        return lookup.get(value);
    }

    @Override
    public Boolean getBooleanValue() {
        return null;
    }
    
//    @Override
//    public String toString() {
//        return this.getClass().getSimpleName()+"."+super.toString();
//    }

    public enum Severity {
        URGENT(0, "Urgent"),
        NORMAL(1, "Normal");

        private int code;
        private String name;

        private Severity(int code, String name) {
            this.code = code;
            this.name = name;
        }

        private static final Map<Integer, Severity> lookup = new HashMap<Integer, Severity>();
        static {
            for (Severity p : EnumSet.allOf(Severity.class)) {
                lookup.put(p.code, p);
            }
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getValue() {
            return getCode();
        }

        @Override
        public String toString() {
            return name;
        }

        static public Severity valueOf(Integer value) {
            return lookup.get(value);
        }
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public Period getDefaultDuration() {
        return defaultDuration;
    }

    public void setDefaultDuration(Period defaultDuration) {
        this.defaultDuration = defaultDuration;
    }
}
