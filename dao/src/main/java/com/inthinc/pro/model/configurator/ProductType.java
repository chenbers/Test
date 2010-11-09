package com.inthinc.pro.model.configurator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.model.BaseEnum;

public enum ProductType implements BaseEnum
{
    UNKNOWN(0,0,"Unknown"),TEEN(1,1,"Teen"),WS820(2,2,"WaySmart"),TIWIPRO_R71(4,3,"TiwiPro R71"),
    TIWIPRO_R74(16,5,"TiwiPro R74");
    
    private int code;
    private int version;
    private String name;
    
    private static final Map<Integer, ProductType> lookupByCode = new HashMap<Integer, ProductType>();
    private static final Map<Integer, ProductType> lookupByVersion = new HashMap<Integer, ProductType>();
    private static final Map<String, ProductType> lookupByName = new HashMap<String, ProductType>();
    static
    {
        for (ProductType p : EnumSet.allOf(ProductType.class))
        {
            lookupByCode.put(p.code, p);
            lookupByVersion.put(p.version, p);
            lookupByName.put(p.name, p);
        }
    }
    public static ProductType valueOf(Integer code)
    {
        return lookupByCode.get(code);
    }
   
    public static ProductType valueOfByName(String name)
    {
        return lookupByName.get(name);
    }
    public static ProductType valueOfByCode(Integer code)
    {
        return lookupByCode.get(code);
    }
    public static ProductType getProductTypeFromVersion(Integer version)
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
    public boolean isForProduct(Integer mask){
        
        return  (mask & code)==code;
    }
}


