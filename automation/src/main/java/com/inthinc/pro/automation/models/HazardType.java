package com.inthinc.pro.automation.models;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@XmlRootElement
@XmlType(name = "hazardType")
@XmlEnum
public enum HazardType implements OptionValue {
    WEATHER_SLIPPERY(1,             10*MeasurementConversionUtil.METERS_IN_MILE, Severity.URGENT, Period.hours(72), "WEATHER", "SLIPPERY"),
    WEATHER_FLOODING(2,             10*MeasurementConversionUtil.METERS_IN_MILE, Severity.URGENT, Period.hours(72), "WEATHER", "FLOODING"),
    WEATHER_SNOWICE(3,              10*MeasurementConversionUtil.METERS_IN_MILE, Severity.URGENT, Period.hours(72), "WEATHER", "SNOWICE") ,
    WEATHER_WINDGUSTS(4,            10*MeasurementConversionUtil.METERS_IN_MILE, Severity.URGENT, Period.hours(72), "WEATHER", "WINDGUSTS"),
    WEATHER_VISIBILITY(5,           10*MeasurementConversionUtil.METERS_IN_MILE, Severity.URGENT, Period.hours(72), "WEATHER", "VISIBILITY"),
    WEATHER_OTHER(6,                10*MeasurementConversionUtil.METERS_IN_MILE, Severity.URGENT, Period.hours(72), "WEATHER", "OTHER"),
    
    ROADACTIVITY_TRAFFIC(21,        5*MeasurementConversionUtil.METERS_IN_MILE , Severity.NORMAL, Period.hours(4), "ROADACTIVITY", "TRAFFIC"),
    ROADACTIVITY_DEBRIS(9,          5*MeasurementConversionUtil.METERS_IN_MILE , Severity.NORMAL, Period.hours(72), "ROADACTIVITY", "DEBRIS"),
    ROADACTIVITY_CONSTRUCTION(7,    5*MeasurementConversionUtil.METERS_IN_MILE, Severity.NORMAL, Period.months(3), "ROADACTIVITY", "CONSTRUCTION"),
    ROADACTIVITY_ACCIDENT(8,        5*MeasurementConversionUtil.METERS_IN_MILE, Severity.NORMAL, Period.hours(4), "ROADACTIVITY", "ACCIDENT"),
    ROADACTIVITY_DISABLEDVEHICLE(10,5*MeasurementConversionUtil.METERS_IN_MILE, Severity.NORMAL, Period.hours(4), "ROADACTIVITY", "DISABLEDVEHICLE"),
    ROADACTIVITY_OTHER(11,          5*MeasurementConversionUtil.METERS_IN_MILE, Severity.NORMAL, Period.hours(72), "ROADACTIVITY", "OTHER"),
    
    ROADRESTRICTIONS_WEIGHT(12,     10*MeasurementConversionUtil.METERS_IN_MILE, Severity.NORMAL, Period.years(1), "ROADRESTRICTIONS", "WEIGHT"),
    ROADRESTRICTIONS_HEIGHT(13,     10*MeasurementConversionUtil.METERS_IN_MILE, Severity.NORMAL, Period.years(1), "ROADRESTRICTIONS", "HEIGHT"),
    ROADRESTRICTIONS_WIDTH(20,      10*MeasurementConversionUtil.METERS_IN_MILE, Severity.NORMAL, Period.years(1), "ROADRESTRICTIONS", "WIDTH"),
    ROADRESTRICTIONS_BAN(14,        10*MeasurementConversionUtil.METERS_IN_MILE, Severity.NORMAL, Period.years(1), "ROADRESTRICTIONS", "BAN"),
    ROADRESTRICTIONS_CLOSURE(15,    5*MeasurementConversionUtil.METERS_IN_MILE, Severity.NORMAL, Period.years(1), "ROADRESTRICTIONS", "CLOSURE"),
    ROADRESTRICTIONS_SHARPTURN(16,  10*MeasurementConversionUtil.METERS_IN_MILE, Severity.NORMAL, Period.years(1), "ROADRESTRICTIONS", "SHARPTURN"),
    ROADRESTRICTIONS_STEEPGRADE(17, 10*MeasurementConversionUtil.METERS_IN_MILE, Severity.NORMAL, Period.years(1), "ROADRESTRICTIONS", "STEEPGRADE"),
    ROADRESTRICTIONS_OTHER(18,      5*MeasurementConversionUtil.METERS_IN_MILE, Severity.NORMAL, Period.years(1), "ROADRESTRICTIONS", "OTHER"),
    
    MICRO_WELLHEAD(19,              15*MeasurementConversionUtil.METERS_IN_FOOT, Severity.NORMAL, Period.years(1), "MICRO", "WELLHEAD"),
    ;

    private int code;
    private String group;
    private String name;
    private double radius;
    private Severity severity;
    private Period defaultDuration;

    private HazardType(int code, double radius, Severity severity, Period defaultDuration, String group, String name) {
        this.code = code;
        this.group = group;
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

    public long getShelfLifeSeconds() { 
    	Duration duration = defaultDuration.toDurationFrom(new DateTime());
    	return duration.toStandardSeconds().getSeconds();
    }
    @XmlElement(name = "radiusMeters")
    @JsonProperty(value = "radiusMeters")
    public double getRadius() {
        return radius;
    }
    @XmlElement(name = "details")
    public String getDetails() {
        return group +" "+ name;
    }
    @XmlElement(name = "urgent")
    public boolean isUrgent() {
        return severity.equals(Severity.URGENT);
    }
    
    public String getGroup() {
        return group;
    }
    
    public void setGroup(String group) {
        this.group = group;
    }

    public void setCode(int code) {
        this.code = code;
    }
    
    @Override
    public String getName(){
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
