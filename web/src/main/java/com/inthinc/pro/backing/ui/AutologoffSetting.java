package com.inthinc.pro.backing.ui;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.model.BaseEnum;

public enum AutologoffSetting implements BaseEnum{
    
    OFF(0, 0, 0, "AutologoffSetting_disabled"),
    MINS5(300, 1, 5, "AutologoffSetting_minutes"),
    MINS10(600, 2, 10, "AutologoffSetting_minutes"),
    MINS15(900, 3, 15, "AutologoffSetting_minutes"),
    MINS20(1200, 4, 20, "AutologoffSetting_minutes"),
    MINS25(1500, 5, 25, "AutologoffSetting_minutes"),
    MINS30(1800, 6, 30, "AutologoffSetting_minutes"),
    MINS35(2100, 7, 35, "AutologoffSetting_minutes"),
    MINS40(2400, 8, 40, "AutologoffSetting_minutes"),
    MINS45(2700, 9, 45, "AutologoffSetting_minutes"),
    MINS50(3000, 10, 50, "AutologoffSetting_minutes"),
    MINS55(3300, 11, 55, "AutologoffSetting_minutes"),
    HOURS1(3600, 12, 1, "AutologoffSetting_hour"),
    HOURS2(7200, 13, 2, "AutologoffSetting_hours"),
    HOURS3(10800, 14, 3, "AutologoffSetting_hours"),
    HOURS4(14400, 15, 4, "AutologoffSetting_hours"),
    HOURS5(18000, 16, 5, "AutologoffSetting_hours"),
    HOURS6(21600, 17, 6, "AutologoffSetting_hours"),
    HOURS7(25200, 18, 7, "AutologoffSetting_hours"),
    HOURS8(28800, 19, 8, "AutologoffSetting_hours"),
    HOURS9(32400, 20, 9, "AutologoffSetting_hours"),
    HOURS10(36000, 21, 10, "AutologoffSetting_hours"),
    HOURS11(39600, 22, 11, "AutologoffSetting_hours"),
    HOURS12(43200, 23, 12, "AutologoffSetting_hours"),
    HOURS13(46800, 24, 13, "AutologoffSetting_hours"),
    HOURS14(50400, 25, 14, "AutologoffSetting_hours"),
    HOURS15(54000, 26, 15, "AutologoffSetting_hours"),
    HOURS16(57600, 27, 16, "AutologoffSetting_hours"),
    HOURS17(61200, 28, 17, "AutologoffSetting_hours"),
    HOURS18(64800, 29, 18, "AutologoffSetting_hours"),
    HOURSMAX(65535, 30, 0, "AutologoffSetting_max");
    
    private Integer seconds;
    private Integer slider;
    private Integer value;
    private String unit;
    
    private static final Map<Integer, AutologoffSetting> lookup = new HashMap<Integer, AutologoffSetting>();
    
    private AutologoffSetting(Integer seconds, Integer slider, Integer value, String unit) {
        this.seconds = seconds;
        this.slider = slider;
        this.value = value;
        this.unit = unit;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    public Integer getSlider() {
        return slider;
    }

    public void setSlider(Integer slider) {
        this.slider = slider;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public Integer getCode() {
        return slider;
    }
    @Override
    public String toString(){
        
        return unit;
    }
    public static AutologoffSetting findBySeconds(Integer seconds){
        
        for (AutologoffSetting setting :  EnumSet.allOf(AutologoffSetting.class)){
            
            if (seconds <= setting.getSeconds()){
                
                return setting;
            }
        }
        return AutologoffSetting.HOURSMAX;
    }
    static
    {
        for (AutologoffSetting p : EnumSet.allOf(AutologoffSetting.class))
        {
            lookup.put(p.slider, p);
        }
    }
    public static AutologoffSetting valueOf(Integer slider)
    {
        return lookup.get(slider);
    }
}
