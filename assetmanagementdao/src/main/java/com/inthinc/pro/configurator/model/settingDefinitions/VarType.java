package com.inthinc.pro.configurator.model.settingDefinitions;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum VarType
{ 
    VTBOOLEAN(0, "boolean"){
        public boolean validateValue(String min, String max, String value){
            
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

   public boolean validateValue(String min, String max, String value){
            
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
