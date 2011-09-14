package com.inthinc.pro.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.inthinc.pro.model.configurator.Category;
import com.inthinc.pro.model.configurator.Unit;
import com.inthinc.pro.model.configurator.VarType;

@Entity
@Table(name="settingDef")
public class DeviceSettingDefinitionJPA {

    @Id
    private Integer settingID;
    private String name;
    private String description;
    private Category category;
    private VarType varType;
    private Unit unit;
//    private List<String> choices;
    private String min;
    private String max;
    private Integer visibility;
    private Integer ignore;
    private Integer productMask;
    private String regex;
//    private Pattern regex;
//	private ValidationMode validationMode;
    
    public String getRegex() {
		return regex;
	}
	public void setRegex(String regex) {
		this.regex = regex;
	}
    public Integer getSettingID() {
        return settingID;
    }
    public void setSettingID(Integer settingID) {
        this.settingID = settingID;
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
//    public void setChoices( List<String> choices){
//        
//        this.choices = choices;
//        if (choices != null && !choices.isEmpty() && !(choices.get(0).length()==0)){
//            
//            validationMode = new ChoiceValidationMode();
//        }
//    }
//    @Override
//    public int compareTo(DeviceSettingDefinitionJPA o) {
//        
//        return settingID.compareTo(o.getSettingID());
//    }
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
//    public List<String> getChoices() {
//        return choices;
//    }
//    public boolean validate( String value){
//        
//        return validationMode.validate(value);
//    }
//    
//    public DeviceSettingDefinitionJPA() {
//        super();
//        validationMode = new RangeValidationMode();
//    }
//    public boolean getHasChoices(){
//        return (choices != null) && !choices.isEmpty() && (choices.get(0).length()!=0);
//    }
//    protected interface ValidationMode {
//        
//        public boolean validate(String value);
//    }
//    protected class ChoiceValidationMode implements ValidationMode{
//        
//        public boolean validate(String value) {
//
//            if (value == null) return true;
//            
//            return getChoices().contains(value);
//        }
//    }
//    protected class RangeValidationMode implements ValidationMode{
//        
//        public boolean validate(String value) {
//
//            if (value == null) return true;
//
//            return getVarType().validateValue(getMin(), getMax(), value);
//        }
//    }
//    protected class RegexValidationMode implements ValidationMode{
//
//		@Override
//		public boolean validate(String value) {
//			
//            if (value == null) return true;
//            
//			Matcher matcher = regex.matcher(value);
//			return matcher.matches();
//		}
//    	
//    }
	@Override
	public String toString() {
		return "DeviceSettingDefinitionJPA [settingID=" + settingID + ", name="
				+ name + ", description=" + description + ", category="
				+ category + ", varType=" + varType + ", unit=" + unit
				+ ", min=" + min + ", max=" + max
				+ ", visibility=" + visibility + ", ignore=" + ignore
				+ ", productMask=" + productMask + ", regex=" + regex + "]";
	}
}
