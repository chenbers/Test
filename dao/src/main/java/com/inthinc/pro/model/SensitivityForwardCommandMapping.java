package com.inthinc.pro.model;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.inthinc.pro.model.configurator.SettingType;

@XmlRootElement
public class SensitivityForwardCommandMapping
{
	private SettingType setting;
	private Integer fwdCmd;
	private List<String> values;
	
	
	public SensitivityForwardCommandMapping()
	{
		
	}
	public SensitivityForwardCommandMapping(SettingType setting, Integer fwdCmd, List<String> values)
	{
		this.setting = setting;
		this.fwdCmd = fwdCmd;
		this.values = values;
	}
	public SettingType getSetting()
	{
		return setting;
	}
	public void setSetting(SettingType setting)
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
