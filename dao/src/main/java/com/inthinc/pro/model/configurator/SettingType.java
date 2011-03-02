package com.inthinc.pro.model.configurator;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.model.BaseEnum;
import com.inthinc.pro.model.WirelineStatus;

@XmlRootElement
public enum SettingType implements BaseEnum
{
    HARD_ACCEL_SETTING(1,157,10,5, "hardAcceleration"),
    HARD_BRAKE_SETTING(2,158,10,5, "hardBrake"),
    HARD_TURN_SETTING(3,159,10,5, "hardTurn"),
    HARD_VERT_SETTING(4,160,15,10, "hardVertical"),
    SEVERE_PEAK_2_PEAK(5,1225,15,10, ""),
    AUTOLOGOFF_SETTING(6,138,29,0, "autologoffSeconds"),
    SPEED_SETTING(7,85,0,0, "speedSettings"),
    EPHONE_SETTING(8,60,0,0, "ephone"),
    
    
    //Hard vertical
    RMS_LEVEL(9,1224,0,0,"rms_level"),
    HARDVERT_DMM_PEAKTOPEAK(10,1225,0,0,"hardvert_dmm_peaktopeak"),
    SEVERE_HARDVERT_LEVEL(11,1165,0,0,"severe_hardvert_level"),
    
    //Hard Turns
    Y_LEVEL(12,1226,0,0,"y_level"),
    DVY(13,1228,0,0,"dvy"),
    
    //Hard brakes
    X_ACCEL(14,1229,0,0,"x_accel"),
    DVX(15,1231,0,0,"dvx"),
    
    //Hard Acceleration
    HARD_ACCEL_LEVEL(16,1232,0,0,"hard_accel_level"),
    HARD_ACCEL_DELTAV(17,1234,0,0,"hard_accel_deltav"),
    
    //Speed
    SPEED_LIMIT(18,1028,0,0,"speed_limit"),
    SEVERE_SPEED(19,1029,0,0,"severe_speed"),
    SPEED_BUFFER(20,1030,0,0,"speed_buffer"),
    
    WS_HARD_ACCEL_SETTING(21,0,10,5, "hardAcceleration"),
    WS_HARD_BRAKE_SETTING(22,0,10,5, "hardBrake"),
    WS_HARD_TURN_SETTING(23,0,10,5, "hardTurn"),
    WS_HARD_VERT_SETTING(24,0,15,10, "hardVertical"),
    
    //individual speed settings
    SPEED_0(25,0,10,5,"speed0"),
    SPEED_1(26,0,10,10,"speed1"),
    SPEED_2(27,0,10,15,"speed2"),
    SPEED_3(28,0,10,20,"speed3"),
    SPEED_4(29,0,10,25,"speed4"),
    SPEED_5(30,0,10,30,"speed5"),
    SPEED_6(31,0,10,35,"speed6"),
    SPEED_7(32,0,10,40,"speed7"),
    SPEED_8(33,0,10,45,"speed8"),
    SPEED_9(34,0,10,50,"speed9"),
    SPEED_10(35,0,10,55,"speed10"),
    SPEED_11(36,0,10,60,"speed11"),
    SPEED_12(37,0,10,65,"speed12"),
    SPEED_13(38,0,10,70,"speed13"),
    SPEED_14(39,0,10,75,"speed14"),
    WIRELINE_MODULE(40, 1172, 0, 0, "wireline_module"),
    WIRELINE_DOOR_ALARM_PASSCODE(41, 1149, 0, 0, "wireline_door_alarm_passwd"),
    WIRELINE_KILL_MOTOR_PASSCODE(42, 1150, 0, 0, "wireline_kill_motor_passwd"),
    WIRELINE_AUTO_ARM_TIME(43, 1151, 0, 0, "wireline_auto_armtime");
    
    private Integer       settingsCount; //This is how many possible values there are for this device setting
    private Integer       settingID; //SettingID in settingDefs table
    private Integer       defaultSetting;  //This is the index of the default value for this setting.
    private int           code;
    private String        propertyName;

    private SettingType(int code, int settingID, Integer settingsCount,Integer defaultSetting, String propertyName)
    {
        this.code = code;
        this.settingID = settingID;
        this.settingsCount = settingsCount;
        this.defaultSetting = defaultSetting;
        this.propertyName = propertyName;
    }


    private static final Map<Integer, SettingType> lookup = new HashMap<Integer, SettingType>();
    static
    {
        for (SettingType p : EnumSet.allOf(SettingType.class))
        {
            lookup.put(p.code, p);
        }
    }

    private static final Map<Integer, SettingType> bySettingID = new HashMap<Integer, SettingType>();
    static
    {
        for (SettingType p : EnumSet.allOf(SettingType.class))
        {
            bySettingID.put(p.settingID, p);
        }
    }
    public static Map<Integer, SettingType> getBySettingID(){
        
        return bySettingID;
    }
    public static Map<Integer, Integer> getDefaultSettings(){
        
        Map<Integer, Integer> defaultSettings = new HashMap<Integer, Integer>();
        
        for (SettingType p : EnumSet.allOf(SettingType.class))
        {
            defaultSettings.put(p.settingID, p.defaultSetting);
        }
        return defaultSettings;
    }
    public Integer getCode()
    {
        return this.code;
    }

    public Integer getSettingID() {
        return settingID;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public static SettingType valueOf(Integer code)
    {
        return lookup.get(code);
    }
    public static SettingType getBySettingID(Integer settingID){
        return bySettingID.get(settingID);
    }
    public static List<SettingType> getSensitivities(){
        
       List<SettingType> sensitivityTypes = new ArrayList<SettingType>();
       sensitivityTypes.add(HARD_ACCEL_SETTING);
       sensitivityTypes.add(HARD_BRAKE_SETTING);
       sensitivityTypes.add(HARD_TURN_SETTING);
       sensitivityTypes.add(HARD_VERT_SETTING);
       
       return sensitivityTypes;
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

