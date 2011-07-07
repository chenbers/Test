package com.inthinc.pro.backing.ui;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.model.BaseEnum;

public enum IdlingSetting implements BaseEnum {
    OFF(0, 0, 0, "IdlingSetting_disabled"), 
    DEFAULT(30 * 2, 1, 30 * 2, "IdlingSetting_seconds"),
    SEC90( 30 * 3, 2, 30 * 3, "IdlingSetting_seconds"),
    SEC120(30 * 4, 3, 30 * 4, "IdlingSetting_seconds"),
    SEC150(30 * 5, 4, 30 * 5, "IdlingSetting_seconds"),
    SEC180(30 * 6, 5, 30 * 6, "IdlingSetting_seconds"),
    SEC210(30 * 7, 6, 30 * 7, "IdlingSetting_seconds"),
    SEC240(30 * 8, 7, 30 * 8, "IdlingSetting_seconds"),
    SEC270(30 * 9, 8, 30 * 9, "IdlingSetting_seconds"),
    MINS5( 60 * 5, 9, 5, "IdlingSetting_minutes"),
    MINS6( 60 * 6, 10, 6, "IdlingSetting_minutes"),
    MINS7( 60 * 7, 11, 7, "IdlingSetting_minutes"),
    MINS8( 60 * 8, 12, 8, "IdlingSetting_minutes"),
    MINS9( 60 * 9, 13, 9, "IdlingSetting_minutes"),
    MINS10(60 * 10, 14, 10, "IdlingSetting_minutes"),
    MINS11(60 * 11, 15, 11, "IdlingSetting_minutes"),
    MINS12(60 * 12, 16, 12, "IdlingSetting_minutes"),
    MINS13(60 * 13, 17, 13, "IdlingSetting_minutes"),
    MINS14(60 * 14, 18, 14, "IdlingSetting_minutes"),
    MINS15(60 * 15, 19, 15, "IdlingSetting_minutes"),
    MINS16(60 * 16, 20, 16, "IdlingSetting_minutes"),
    MINS17(60 * 17, 21, 17, "IdlingSetting_minutes"),
    MINS18(60 * 18, 22, 18, "IdlingSetting_minutes"),
    MINS19(60 * 19, 23, 19, "IdlingSetting_minutes"),
    MINS20(60 * 20, 24, 20, "IdlingSetting_minutes"),
    MINS21(60 * 21, 25, 21, "IdlingSetting_minutes"),
    MINS22(60 * 22, 26, 22, "IdlingSetting_minutes"),
    MINS23(60 * 23, 27, 23, "IdlingSetting_minutes"),
    MINS24(60 * 24, 28, 24, "IdlingSetting_minutes"),
    MINS25(60 * 25, 29, 25, "IdlingSetting_minutes"),
    MINS26(60 * 26, 30, 26, "IdlingSetting_minutes"),
    MINS27(60 * 27, 31, 27, "IdlingSetting_minutes"),
    MINS28(60 * 28, 32, 28, "IdlingSetting_minutes"),
    MINS29(60 * 29, 33, 29, "IdlingSetting_minutes"),
    MINS30(60 * 30, 34, 30, "IdlingSetting_minutes"),
    MINS31(60 * 31, 35, 31, "IdlingSetting_minutes"),
    MINS32(60 * 32, 36, 32, "IdlingSetting_minutes"),
    MINS33(60 * 33, 37, 33, "IdlingSetting_minutes"),
    MINS34(60 * 34, 38, 34, "IdlingSetting_minutes"),
    MINS35(60 * 35, 39, 35, "IdlingSetting_minutes"),
    MINS36(60 * 36, 40, 36, "IdlingSetting_minutes"),
    MINS37(60 * 37, 41, 37, "IdlingSetting_minutes"),
    MINS38(60 * 38, 42, 38, "IdlingSetting_minutes"),
    MINS39(60 * 39, 43, 39, "IdlingSetting_minutes"),
    MINS40(60 * 40, 44, 40, "IdlingSetting_minutes"),
    MINS41(60 * 41, 45, 41, "IdlingSetting_minutes"),
    MINS42(60 * 42, 46, 42, "IdlingSetting_minutes"),
    MINS43(60 * 43, 47, 43, "IdlingSetting_minutes"),
    MINS44(60 * 44, 48, 44, "IdlingSetting_minutes"),
    MINS45(60 * 45, 49, 45, "IdlingSetting_minutes"),
    MINS46(60 * 46, 50, 46, "IdlingSetting_minutes"),
    MINS47(60 * 47, 51, 47, "IdlingSetting_minutes"),
    MINS48(60 * 48, 52, 48, "IdlingSetting_minutes"),
    MINS49(60 * 49, 53, 49, "IdlingSetting_minutes"),
    MINS50(60 * 50, 54, 50, "IdlingSetting_minutes"),
    MINS51(60 * 51, 55, 51, "IdlingSetting_minutes"),
    MINS52(60 * 52, 56, 52, "IdlingSetting_minutes"),
    MINS53(60 * 53, 57, 53, "IdlingSetting_minutes"),
    MINS54(60 * 54, 58, 54, "IdlingSetting_minutes"),
    MINS55(60 * 55, 59, 55, "IdlingSetting_minutes"),
    MINS56(60 * 56, 60, 56, "IdlingSetting_minutes"),
    MINS57(60 * 57, 61, 57, "IdlingSetting_minutes"),
    MINS58(60 * 58, 62, 58, "IdlingSetting_minutes"),
    MINS59(60 * 59, 63, 59, "IdlingSetting_minutes"),
    MAX(   60 * 60, 64, 60, "IdlingSetting_minutes");

    private Integer seconds;
    private Integer slider;
    private Integer value;
    private String unit;

    private static final Map<Integer, IdlingSetting> lookup = new HashMap<Integer, IdlingSetting>();

    private IdlingSetting(Integer seconds, Integer slider, Integer value, String unit) {
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
    public String toString() {
        return unit;
    }

    public static IdlingSetting getDefault(){
        return IdlingSetting.DEFAULT;
    }
    public static IdlingSetting findBySeconds(Integer seconds) {
        for (IdlingSetting setting : EnumSet.allOf(IdlingSetting.class)) {
            if (seconds <= setting.getSeconds()) {
                return setting;
            }
        }
        return IdlingSetting.MAX;
    }

    static {
        for (IdlingSetting p : EnumSet.allOf(IdlingSetting.class)) {
            lookup.put(p.slider, p);
        }
    }

    public static IdlingSetting valueOf(Integer slider) {
        return lookup.get(slider);
    }
}
