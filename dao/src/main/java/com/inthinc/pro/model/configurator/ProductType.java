package com.inthinc.pro.model.configurator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ProductType
{
    UNKNOWN(new Integer[]{0},new Integer[]{0},"Unknown"),
    TEEN(new Integer[]{1},new Integer[]{1},"Teen"),
    WAYSMART(new Integer[]{2},new Integer[]{2},"waySmart"),
    TIWIPRO(new Integer[]{4,16},new Integer[]{3,5},"tiwiPro");
    
    private Integer[] codes;
    private Integer[] versions;
    private String description;
    
    private static final Map<Integer, ProductType> lookupByCode = new HashMap<Integer, ProductType>();
    private static final Map<Integer, ProductType> lookupByVersion = new HashMap<Integer, ProductType>();
    private static final Map<String, ProductType> lookupByName = new HashMap<String, ProductType>();
    static
    {
        for (ProductType p : EnumSet.allOf(ProductType.class))
        {
            for (Integer code :p.codes){
                lookupByCode.put(code, p);
            }
            for (Integer version :p.versions){
                lookupByVersion.put(version, p);
            }
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
    ProductType(Integer[] codes,Integer[] versions, String description){
        
        this.codes = codes;
        this.versions = versions;
        this.description = description;
    }
    public Integer[] getCodes(){
        
        return codes;
    }
    public String getDescription(){
        
        return description;
    }

    public Integer[] getVersions() {
        
        return versions;
    }
    public boolean isForProduct(Integer mask){
        for (Integer code :codes){
            if ((mask & code)==code) return true;
        }
        return  false;
    }
}


