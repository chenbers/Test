package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum SensitivityType implements BaseEnum
{
    HARD_ACCEL_SETTING(1,10,5),
    HARD_BRAKE_SETTING(2,10,5),
    HARD_TURN_SETTING(3,10,5),
    HARD_VERT_SETTING(4,15,10),
    SEVERE_PEAK_2_PEAK(5,15,10);

    private Integer       settingsCount; //This is how many possible values there are for this device setting
    private Integer       defaultSetting;  //This is the index of the default value for this setting.
    private int       code;

    private SensitivityType(int code, Integer settingsCount,Integer defaultSetting)
    {
        this.code = code;
        this.settingsCount = settingsCount;
        this.defaultSetting = defaultSetting;
    }

    private static final Map<Integer, SensitivityType> lookup = new HashMap<Integer, SensitivityType>();
    static
    {
        for (SensitivityType p : EnumSet.allOf(SensitivityType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static SensitivityType valueOf(Integer code)
    {
        return lookup.get(code);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }

    public Integer getSettingsCount()
    {
        return settingsCount;
    }

    public Integer getDefaultSetting()
    {
        return defaultSetting;
    }
    
    

}

