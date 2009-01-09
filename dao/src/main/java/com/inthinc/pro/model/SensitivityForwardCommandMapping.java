package com.inthinc.pro.model;

import java.util.List;

public class SensitivityForwardCommandMapping
{
	private Integer setting;
	private Integer fwdCmd;
	private List<String> values;
	
	public static final Integer HARD_ACCEL_SETTING = 1;
    public static final Integer HARD_BRAKE_SETTING = 2;
	public static final Integer HARD_TURN_SETTING = 3;
	public static final Integer HARD_VERT_SETTING = 4;
	
	public SensitivityForwardCommandMapping()
	{
		
	}
	public SensitivityForwardCommandMapping(Integer setting, Integer fwdCmd, List<String> values)
	{
		this.setting = setting;
		this.fwdCmd = fwdCmd;
		this.values = values;
	}
	public Integer getSetting()
	{
		return setting;
	}
	public void setSetting(Integer setting)
	{
		this.setting = setting;
	}
	public Integer getFwdCmd()
	{
		return fwdCmd;
	}
	public void setFwdCmd(Integer fwdCmd)
	{
		this.fwdCmd = fwdCmd;
	}
	public List<String> getValues()
	{
		return values;
	}
	public void setValues(List<String> values)
	{
		this.values = values;
	}
	
	
	
}
