package com.inthinc.pro.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum RedFlagEzCrmDataType implements BaseEnum
{
    EZCRM_DATA_TYPE_GROUP(1),
    EZCRM_DATA_TYPE_SEVERITY(2),
    EZCRM_DATA_TYPE_DATE(3),
    EZCRM_DATA_TYPE_DRIVER(4),
    EZCRM_DATA_TYPE_VEHICLE(5),
    EZCRM_DATA_TYPE_EVENT(6),
    EZCRM_DATA_TYPE_LOCATION(7),
    EZCRM_DATA_TYPE_ODOMETER(8),
    EZCRM_DATA_TYPE_SPEED(9);
    
    private int code;

    private RedFlagEzCrmDataType(int code)
    {
        this.code = code;
    }

    private static final Map<Integer, RedFlagEzCrmDataType> lookup = new HashMap<Integer, RedFlagEzCrmDataType>();
    static
    {
        for (RedFlagEzCrmDataType p : EnumSet.allOf(RedFlagEzCrmDataType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }
    public Long getBitMask(){
        
       return 1l << (code-1);
        
    }
    public static RedFlagEzCrmDataType valueOf(Integer code)
    {
        return lookup.get(code);
    }
    public static int getMax(){
        return EnumSet.allOf(RedFlagEzCrmDataType.class).size();
    }
    public static List<RedFlagEzCrmDataType> getRedFlagEzCrmDataTypes(Long alertTypeMask){
        
        Set<RedFlagEzCrmDataType> ezCrmDataType = EnumSet.noneOf(RedFlagEzCrmDataType.class);
        if (alertTypeMask == null) return Collections.emptyList();
        for(RedFlagEzCrmDataType amt : EnumSet.allOf(RedFlagEzCrmDataType.class)){
            
            long bitValue = amt.getBitMask();
            if((alertTypeMask.longValue() & bitValue) == bitValue){
                ezCrmDataType.add(amt); 
            }
        }
        return new ArrayList<RedFlagEzCrmDataType>(ezCrmDataType);
    }
    public static  Long convertTypes(Set<RedFlagEzCrmDataType> ezCrmDataType){
        Long ezCrmDataTypeMask = new Long(0);
        if(ezCrmDataType != null){
            for(RedFlagEzCrmDataType amt : ezCrmDataType){
                
                long bitValue = amt.getBitMask();
                ezCrmDataTypeMask = ezCrmDataTypeMask.longValue()  | bitValue;
            }
        }
        return ezCrmDataTypeMask;
    }
    public static  Long convertTypes(List<RedFlagEzCrmDataType> ezCrmDataType){
        Long ezCrmDataTypeMask = new Long(0);
        if(ezCrmDataType != null){
            for(RedFlagEzCrmDataType amt : ezCrmDataType){
                
                long bitValue = amt.getBitMask();
                ezCrmDataTypeMask = ezCrmDataTypeMask.longValue()  | bitValue;
            }
        }
        return ezCrmDataTypeMask;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }
}
