package com.inthinc.pro.model.configurator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.inthinc.pro.dao.annotations.ID;

public class DeviceSettingDefinition implements Comparable<DeviceSettingDefinition>{
    
    @ID
    private Integer settingID;
    private String name;
    private String description;
    private Category category;
    private VarType varType;
    private Unit unit;
    private List<String> choices;
    private String min;
    private String max;
    private Integer visibility;
    private Integer ignore;
    private Integer productMask;
    private Pattern regex;
	private ValidationMode validationMode;
    
    public Pattern getRegex() {
		return regex;
	}
	public void setRegex(Pattern regex) {
		this.regex = regex;
		validationMode = new RegexValidationMode();
	}
    public Integer getSettingID() {
        return settingID;
    }
    public void setSettingID(Integer settingID) {
        this.settingID = settingID;
//        if (settingID.intValue()== 85){
//        	regex = Pattern.compile("([0-9]|[1-2][0-9]|30) ([0-9]|[1-2][0-9]|3[0-5]) ([0-9]|[1-3][0-9]|40) ([0-9]|[1-3][0-9]|4[0-5]) ([0-9]|[1-4][0-9]|50) ([5-9]|[1-4][0-9]|5[0-5]) ([1-5][0-9]|60) (1[5-9]|[2-5][0-9]|6[0-5]) ([2-6][0-9]|70) (2[5-9]|[3-6][0-9]|7[0-5]) ([3-7][0-9]|80) (3[5-9]|[4-7][0-9]|8[0-5]) ([4-8][0-9]|90) (4[5-9]|[5-8][0-9]|9[0-5]) ([5-9][0-9]|100)");
//    		validationMode = new RegexValidationMode();
//        }
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public VarType getVarType() {
        return varType;
    }
    public String getVarTypeString() {
        return varType.name();
    }

    public void setVarType(VarType varType) {
        this.varType = varType;
    }
    public void setChoices( List<String> choices){
        
        this.choices = choices;
        if (choices != null && !choices.isEmpty() && !(choices.get(0).length()==0)){
            
            validationMode = new ChoiceValidationMode();
        }
    }
    @Override
    public int compareTo(DeviceSettingDefinition o) {
        
        return settingID.compareTo(o.getSettingID());
    }
    public Unit getUnit() {
        return unit;
    }
    public void setUnit(Unit unit) {
        this.unit = unit;
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
    public Integer getVisibility() {
        return visibility;
    }
    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }
    public boolean isInclude(){
        return ignore == 0;
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
    public List<String> getChoices() {
        return choices;
    }
    public boolean validate( String value){
        
        return validationMode.validate(value);
    }
    
    public DeviceSettingDefinition() {
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
    protected class VariableSpeedLimitsValidationMode implements ValidationMode{

		@Override
		public boolean validate(String value) {
			
            if (value == null) return true;
            
			String[] speedLimits = value.split(" ");
			if(speedLimits.length != 15) return false;
			int mph = 5;
			for (String speedLimit : speedLimits){
				
				if (!VarType.VTINTEGER.checkType(speedLimit)) return false;
				if (!VarType.VTINTEGER.validateValue(""+Math.max(0, mph-25), ""+(mph+25), speedLimit)) return false;
				mph+=5;
			}
			return true;
		}
    }
    protected class RegexValidationMode implements ValidationMode{

		@Override
		public boolean validate(String value) {
			
            if (value == null) return true;
            
			Matcher matcher = regex.matcher(value);
			return matcher.matches();
		}
    	
    }
}
