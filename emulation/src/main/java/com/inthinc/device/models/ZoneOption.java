package com.inthinc.device.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inthinc.pro.automation.logging.Log;

public class ZoneOption implements Serializable {

	private static final long serialVersionUID = 1L;

	private ZoneAvailableOption option;
	private OptionValue value;
	private Integer rawValue;

	public ZoneOption() {
	}

	public ZoneOption(ZoneAvailableOption option, OptionValue value) {
		super();
		this.option = option;
		this.value = value;
	}

	public ZoneAvailableOption getOption() {
		return option;
	}

	public void setOption(ZoneAvailableOption option) {
		this.option = option;
	}
	
	@JsonProperty("option")
	public void setOption(int option){
		this.option = ZoneAvailableOption.valueOf(option);
		if (rawValue != null){
			this.value = ZoneAvailableOption.convertOptionValue(this.option.getOptionType(), rawValue);
			rawValue = null;
		}
	}

	public OptionValue getValue() {
		return value;
	}

	public void setValue(OptionValue value) {
		this.value = value;
	}

	@JsonProperty("value")
	public void setValue(int value){
		Log.info(value);
		if (option == null){
			rawValue = value;
		} else {
			this.value = ZoneAvailableOption.convertOptionValue(option.getOptionType(), value);
		}
//		this.value = option.
	}
}
