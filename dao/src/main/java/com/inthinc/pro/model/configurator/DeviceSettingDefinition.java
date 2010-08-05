package com.inthinc.pro.model.configurator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.inthinc.pro.dao.annotations.ID;
import com.inthinc.pro.model.BaseEnum;

public class DeviceSettingDefinition implements Comparable<DeviceSettingDefinition>{
    
    public enum Category implements BaseEnum
    {
        VEHICLE(0, "Vehicle"), HOS(1, "Hours of Service"), THRESHOLDS(2, "Thresholds"),
        NOTIFICATIONS(3,"Notifications"), GEOFENCE(4, "Geofence"), ALARMS(5,"Alarms"),
        DMM_CRASH(6, "Dmm and Crash"), REMOTEUPDATE(7, "Remote Update"), COMM(8, "Communication"),
        DEVICE(9,"Device");

        private int code;
        private String name;
        
        private static final Map<Integer, Category> lookup = new HashMap<Integer, Category>();
        static
        {
            for (Category p : EnumSet.allOf(Category.class))
            {
                lookup.put(p.code, p);
            }
        }
        
        public static Category valueOf(Integer code)
        {
            return lookup.get(code);
        }

        Category(int code, String name){
            
            this.code = code;
            this.name = name;
        }
        public Integer getCode(){
            
            return code;
        }
        public String getName(){
            
            return name;
        }
    }
    
    public enum VarType implements BaseEnum
    { 
        VTBOOLEAN(0, "boolean"){
            protected boolean validateValue(String min, String max, String value){
                
                return  value.equalsIgnoreCase("true") || 
                        value.equalsIgnoreCase("false") || 
                        value.equalsIgnoreCase("0") || 
                        value.equalsIgnoreCase("1");
            }
            protected boolean checkType(String value){return true;}
            protected boolean greaterThanMax(String max, String value){return false;}
            protected boolean lessThanMin(String min, String value){return false;}

        },
        VTINTEGER(1, "integer"){
            protected boolean checkType(String value){
            	try{
            		Integer.parseInt(value);
            		return true;
            	}
            	catch(NumberFormatException nfe){
            		return false;
            	}
            }
            protected boolean greaterThanMax(String max, String value){
                return Integer.parseInt(value) > Integer.parseInt(max);
            }
            protected boolean lessThanMin(String min, String value){
                return Integer.parseInt(value) < Integer.parseInt(min);
            }
        }, 
        VTDOUBLE(2,"double"){
            protected boolean checkType(String value){
            	try{
            		Double.parseDouble(value);
            		return true;
            	}
            	catch(NumberFormatException nfe){
            		return false;
            	}
            }
            protected boolean greaterThanMax(String max, String value){
                return Double.parseDouble(value) > Double.parseDouble(max);
            }
            protected boolean lessThanMin(String min, String value){
                return Double.parseDouble(value) < Double.parseDouble(min);
            }
        }, 
        VTSTRING(3,"string"){
            protected boolean checkType(String value){return true;}
            protected boolean greaterThanMax(String max, String value){
                return value.length() > Integer.parseInt(max);
            }
            protected boolean lessThanMin(String min, String value){
                return value.length() < Integer.parseInt(min);
            }
        };
    
        private int code;
        private String name;
        
		private static final Map<Integer, VarType> lookup = new HashMap<Integer, VarType>();
        static
        {
            for (VarType p : EnumSet.allOf(VarType.class))
            {
                lookup.put(p.code, p);
            }
        }
       
        public static VarType valueOf(Integer code)
        {
            return lookup.get(code);
        }

        VarType(int code, String name){
            this.code = code;
            this.name = name;
        }
        public Integer getCode(){
            
             return code;
        }
        public String getName() {
			return name;
		}

       protected boolean validateValue(String min, String max, String value){
                
    	   	if (!checkType(value)) return false;
                
            try{
                if((max != null) && !max.isEmpty()){
                    if (greaterThanMax(max, value)) return false;
                }
                if((min != null) && !min.isEmpty()){
                    if(lessThanMin(min, value)) return false;
                }
                return true;
            }
            catch(NumberFormatException nfe){
                
                return false;
            }

        }
        protected abstract boolean checkType(String value);
        protected abstract boolean greaterThanMax(String max, String value);
        protected abstract boolean lessThanMin(String min, String value);
       
    }
    public enum Unit implements BaseEnum
    {
        NO_UNITS(0,""),SECONDS(1,"Seconds"),MINUTES(2,"Minutes"),HOURS(3,"Hours"),
        MILES(4,"Miles"),FEET(5,"Feet"),MPH(6,"Mph"),KPH(7,"Kph"),
        TOGGLE(8,"off/on"),RPM(9,"RPM"),VOLTS(10,"Volts"),
        G(11,"G"),DAYS(12,"Days"),DEGREES(13,"Degrees"),DB(14,"dB");
        
        private int code;
        private String name;
        
        private static final Map<Integer, Unit> lookup = new HashMap<Integer, Unit>();
        static
        {
            for (Unit p : EnumSet.allOf(Unit.class))
            {
                lookup.put(p.code, p);
            }
        }
        public static Unit valueOf(Integer code)
        {
            return lookup.get(code);
        }
       
        Unit(int code, String name){
            
            this.code = code;
            this.name = name;
        }
        public Integer getCode(){
            
            return code;
        }
        public String getName(){
            
            return name;
        }
      
    }
    
    public enum ProductType implements BaseEnum
    {
        UNKNOWN(0,0,"Unknown"),TEEN(1,1,"Teen"),WS820(2,2,"WaySmart"),TIWIPRO_R71(4,3,"TiwiPro R71"),
        TIWIPRO_R74(16,5,"TiwiPro R74");
        
        private int code;
        private int version;
        private String name;
        
        private static final Map<Integer, ProductType> lookupByCode = new HashMap<Integer, ProductType>();
        private static final Map<Integer, ProductType> lookupByVersion = new HashMap<Integer, ProductType>();
        static
        {
            for (ProductType p : EnumSet.allOf(ProductType.class))
            {
                lookupByCode.put(p.code, p);
                lookupByVersion.put(p.version, p);
            }
        }
        public static ProductType valueOf(Integer code)
        {
            return lookupByCode.get(code);
        }
       
        public static ProductType valueOfByCode(Integer code)
        {
            return lookupByCode.get(code);
        }
        public static ProductType valueOfByVersion(Integer version)
        {
            return lookupByVersion.get(version);
        }
        public static EnumSet<ProductType> getSet(){
            
            return EnumSet.allOf(ProductType.class);
        }
        ProductType(int code,int version, String name){
            
            this.code = code;
            this.version = version;
            this.name = name;
        }
        public Integer getCode(){
            
            return code;
        }
        public String getName(){
            
            return name;
        }

        public int getVersion() {
            
            return version;
        }
        public boolean maskBitSet(Integer mask){
            
            return  (mask & code)==code;
        }
    }
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
