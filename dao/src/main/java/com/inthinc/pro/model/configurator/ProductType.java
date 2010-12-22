package com.inthinc.pro.model.configurator;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.BaseEnum;

public enum ProductType implements BaseEnum
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
//            addProductTypeToLookupByName(p);
        }
    }
//    private static void addProductTypeToLookupByName(ProductType productType){
//        List<ProductType> names = lookupByName.get(productType.getDescription());
//        if(names == null){
//            names = new ArrayList<ProductType>();
//            lookupByName.put(productType.getDescription(), names);
//        }
//        names.add(productType);
//    }
    public static ProductType valueOf(Integer code)
    {
        return lookupByCode.get(code);
    }
   
//    public static List<ProductType> valueOfByName(String name)
//    {
//        return lookupByName.get(name);
//    }
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


