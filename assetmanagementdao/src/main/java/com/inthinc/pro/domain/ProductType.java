package com.inthinc.pro.domain;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ProductType
{
    UNKNOWN(0,0,ProductName.UNKNOWN),TEEN(1,1,ProductName.TEEN),WAYSMART(2,2,ProductName.WAYSMART),TIWIPRO_R71(4,3,ProductName.TIWIPRO),
    TIWIPRO_R74(16,5,ProductName.TIWIPRO);
    
    private int code;
    private int version;
    private ProductName description;
    
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
    public static ProductType getProductTypeFromVersion(Integer version)
    {
        return lookupByVersion.get(version);
    }
    public static EnumSet<ProductType> getSet(){
        
        return EnumSet.allOf(ProductType.class);
    }
    ProductType(int code,int version, ProductName description){
        
        this.code = code;
        this.version = version;
        this.description = description;
    }
    public Integer getCode(){
        
        return code;
    }
    public ProductName getDescription(){
        
        return description;
    }

    public int getVersion() {
        
        return version;
    }
    public boolean isForProduct(Integer mask){
        
        return  (mask & code)==code;
    }
}


