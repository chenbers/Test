package com.inthinc.pro.configurator.model.settingDefinitions;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DeviceSettingValidation {
    private Integer settingID;
    private String name;
    private String description;
    private Category category;
    private Unit unit;
    private Integer visibility;
    private Integer ignore;
    private Integer productMask;
    private String min;
    private String max;
    private VarType varType;
    private List<String> choices;
    private Pattern regex;
    private ValidationMode validationMode;
    
	public List<String> getChoices() {
	  return choices;
	}
	public boolean validate( String value){
		return validationMode.validate(value);
	}

	public DeviceSettingValidation() {
	  super();
	  validationMode = new RangeValidationMode();
	}
	public boolean getHasChoices(){
	  return (choices != null) && !choices.isEmpty() && (choices.get(0).length()!=0);
	}
	protected interface ValidationMode {
	  
	  public boolean validate(String value);
	}
	protected class ChoiceValidationMode implements ValidationMode{
	  
	  public boolean validate(String value) {
	
	      if (value == null) return true;
	      
	      return getChoices().contains(value);
	  }
	}
	protected class RangeValidationMode implements ValidationMode{
	  
	  public boolean validate(String value) {
	
	      if (value == null) return true;
	
	      return getVarType().validateValue(getMin(), getMax(), value);
	  }
	}
	protected class RegexValidationMode implements ValidationMode{
	
		public boolean validate(String value) {
			
	      if (value == null) return true;
	      
			Matcher matcher = regex.matcher(value);
			return matcher.matches();
		}
		
	}
	public Integer getSettingID() {
		return settingID;
	}
	public void setSettingID(Integer settingID) {
		this.settingID = settingID;
	}
	public VarType getVarType() {
		return varType;
	}
	public void setVarType(VarType varType) {
		this.varType = varType;
	}
	public Pattern getRegex() {
		return regex;
	}
	public void setRegex(Pattern regex) {
		this.regex = regex;
	}
	public ValidationMode getValidationMode() {
		return validationMode;
	}
	public void setValidationMode(ValidationMode validationMode) {
		this.validationMode = validationMode;
	}
	public void setChoices(List<String> choices) {
		this.choices = choices;
	}
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}
	public String getMax() {
		return max;
	}
	public void setMax(String max) {
		this.max = max;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	public Integer getVisibility() {
		return visibility;
	}
	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
	}
	public Integer getIgnore() {
		return ignore;
	}
	public void setIgnore(Integer ignore) {
		this.ignore = ignore;
	}
	public Integer getProductMask() {
		return productMask;
	}
	public void setProductMask(Integer productMask) {
		this.productMask = productMask;
	}
}
