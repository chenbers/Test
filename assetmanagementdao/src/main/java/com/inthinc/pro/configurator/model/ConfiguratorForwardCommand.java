package com.inthinc.pro.configurator.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ConfiguratorForwardCommand{
	
	VARIABLE_SPEED_LIMITS(85,new ForwardCommandDef[]{new ForwardCommandDef(2067,0)}),
	HARDACCEL_SLIDER(157,new ForwardCommandDef[]{new ForwardCommandDef(2091,0)}),
	HARDBRAKE_SLIDER(158,new ForwardCommandDef[]{new ForwardCommandDef(2092,0)}),
	HARDTURN_SLIDER(159,new ForwardCommandDef[]{new ForwardCommandDef(2093,0)}),
	HARDBUMP_SLIDER(160,new ForwardCommandDef[]{new ForwardCommandDef(2094,0)}),
	SPEED_LIMIT(1028,new ForwardCommandDef[]{new ForwardCommandDef(629,4)}),
	SEVERE_SPEED(1029,new ForwardCommandDef[]{new ForwardCommandDef(630,4)}),
	SPEED_BUFFER(1030,new ForwardCommandDef[]{new ForwardCommandDef(631,4)}),
	WIRELINE_DOOR_ALARM_PASSWD(1149,new ForwardCommandDef[]{new ForwardCommandDef(730,0)}),
	WIRELINE_KILL_MOTOR_PASSWD(1150,new ForwardCommandDef[]{new ForwardCommandDef(733,0)}),
	WIRELINE_AUTO_ARM_TIME(1151,new ForwardCommandDef[]{new ForwardCommandDef(734,1)}),
	SEVERE_HARDVERT_LEVEL(1165,new ForwardCommandDef[]{new ForwardCommandDef(802,4)}),
	WIRELINE_MODULE(1172,new ForwardCommandDef[]{new ForwardCommandDef(754,0),new ForwardCommandDef(753,0)}),
	WIII_HARD_VERTICAL_RMSLEVEL(1224,new ForwardCommandDef[]{new ForwardCommandDef(450,1)}),
	WIII_HARD_VERTICAL_PTPLEVEL(1225,new ForwardCommandDef[]{new ForwardCommandDef(800,4)}),
	WIII_HARD_TURN_LEVEL(1226,new ForwardCommandDef[]{new ForwardCommandDef(453,1)}),
	WIII_HARD_TURN_DELTAV(1228,new ForwardCommandDef[]{new ForwardCommandDef(772,1)}),
	WIII_HARD_BRAKE_LEVEL(1229,new ForwardCommandDef[]{new ForwardCommandDef(454,1)}),
	WIII_HARD_BRAKE_DELTAV(1231,new ForwardCommandDef[]{new ForwardCommandDef(455,1)}),
	WIII_HARD_ACCEL_LEVEL(1232,new ForwardCommandDef[]{new ForwardCommandDef(795,1)}),
	WIII_HARD_ACCEL_DELTAV(1234,new ForwardCommandDef[]{new ForwardCommandDef(797,1)});
	
	private int settingID;
	private ForwardCommandDef forwardCommands[];
	
	private ConfiguratorForwardCommand(int settingID, ForwardCommandDef forwardCommands[]) {
		this.settingID = settingID;
		this.forwardCommands = forwardCommands;
	}

	public int getSettingID() {
		return settingID;
	}

	public ForwardCommandDef[] getForwardCommands() {
		return forwardCommands;
	}

	public Integer getCode() {
		return settingID;
	}
    private static final Map<Integer, ConfiguratorForwardCommand> lookup = new HashMap<Integer, ConfiguratorForwardCommand>();
    static {
        for (ConfiguratorForwardCommand p : EnumSet.allOf(ConfiguratorForwardCommand.class)) {
            lookup.put(p.settingID, p);
        }
    }
    public static ConfiguratorForwardCommand valueOf(Integer code) {
        return lookup.get(code);
    }
}
