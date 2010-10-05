package com.inthinc.pro.model;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.inthinc.pro.model.configurator.SensitivityType;

@XmlRootElement
public class SensitivityForwardCommandMapping
{
	private SensitivityType setting;
	private Integer fwdCmd;
	private List<String> values;
	
	
	public SensitivityForwardCommandMapping()
	{
		
	}
	public SensitivityForwardCommandMapping(SensitivityType setting, Integer fwdCmd, List<String> values)
	{
		this.setting = setting;
		this.fwdCmd = fwdCmd;
		this.values = values;
	}
	public SensitivityType getSetting()
	{
		return setting;
	}
	public void setSetting(SensitivityType setting)
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
