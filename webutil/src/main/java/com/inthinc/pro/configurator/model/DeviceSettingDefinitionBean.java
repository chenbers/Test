package com.inthinc.pro.configurator.model;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import com.inthinc.pro.model.configurator.Category;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition;
import com.inthinc.pro.model.configurator.Unit;
import com.inthinc.pro.model.configurator.VarType;
import com.inthinc.pro.util.DummyMap;

public class DeviceSettingDefinitionBean implements Comparable<DeviceSettingDefinitionBean>, Serializable{

	private static final long serialVersionUID = 1L;
	private DeviceSettingDefinition deviceSettingDefinition;
	private ErrorColor errorColor = new ErrorColor();
	
	public DeviceSettingDefinitionBean(DeviceSettingDefinition deviceSettingDefinition) {

		this.deviceSettingDefinition = deviceSettingDefinition;
	}

	public ErrorColor getErrorColor() {
		return errorColor;
	}
	public boolean isInclude() {
		return deviceSettingDefinition.isInclude();
	}
	public Category getCategory() {
		return deviceSettingDefinition.getCategory();
	}
	public List<String> getChoices() {
		return deviceSettingDefinition.getChoices();
	}
	public String getDescription() {
		return deviceSettingDefinition.getDescription();
	}
	public boolean getHasChoices() {
		return deviceSettingDefinition.getHasChoices();
	}
	public Integer getIgnore() {
		return deviceSettingDefinition.getIgnore();
	}
	public String getMax() {
		return deviceSettingDefinition.getMax();
	}
	public String getMin() {
		return deviceSettingDefinition.getMin();
	}
	public String getName() {
		return deviceSettingDefinition.getName();
	}
	public Integer getProductMask() {
		return deviceSettingDefinition.getProductMask();
	}
	public Pattern getRegex() {
		return deviceSettingDefinition.getRegex();
	}
	public Integer getSettingID() {
		return deviceSettingDefinition.getSettingID();
	}
	public String getSettingIDKey(){
		return deviceSettingDefinition.getSettingID().toString();
	}
	public VarType getVarType() {
		return deviceSettingDefinition.getVarType();
	}
	public String getVarTypeString() {
		return deviceSettingDefinition.getVarTypeString();
	}
	public Integer getVisibility() {
		return deviceSettingDefinition.getVisibility();
	}
	
	public Unit getUnit() {
		return deviceSettingDefinition.getUnit();
	}
	public boolean validate(String value){
		
		return deviceSettingDefinition.validate(value);
	}
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		if (!deviceSettingDefinition.validate((String)value)){
			String message = deviceSettingDefinition.getValidationMessage();
			throw new ValidatorException(new FacesMessage(message));
		}
	}
	@Override
	public int compareTo(DeviceSettingDefinitionBean o) {

		return this.deviceSettingDefinition.compareTo(o.deviceSettingDefinition);
	}

	public class ErrorColor extends DummyMap<Integer, String>{

		@Override
		public String get(Object key) {
			return validate((String) key)?"white":"pink";
		}
	}
	@Override
	public String toString() {
		return "DeviceSettingDefinitionBean [deviceSettingDefinition="
				+ deviceSettingDefinition + ", errorColor=" + errorColor + "]";
	}
}
