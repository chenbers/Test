package com.inthinc.pro.automation.models;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ProductType implements BaseEnum {
    UNKNOWN(0, 0, "Unknown"),
//    TEEN(1, 1, "Teen"),
    WAYSMART(2, 2, "waySmart", 2), //xx10
//    TIWIPRO_R71(4, 3, ProductName.TIWIPRO),
    TIWIPRO(16, 1, "tiwiPro", 3, 5, 7); //xxx1

    private int code;
    private int version;
    private String description;
    private String messageKey;
    private Integer[] versions;

    private static final Map<Integer, ProductType> lookupByCode = new HashMap<Integer, ProductType>();
    private static final Map<Integer, ProductType> lookupByVersion = new HashMap<Integer, ProductType>();
    static {
        for (ProductType p : EnumSet.allOf(ProductType.class)) {
            lookupByCode.put(p.code, p);
            lookupByVersion.put(p.version, p);
        }
    }

    public static ProductType valueOf(Integer code) {
        return lookupByCode.get(code);
    }

    public static ProductType valueOfByCode(Integer code) {
        return lookupByCode.get(code);
    }

    public static ProductType getProductTypeFromVersion(Integer version) {
        return lookupByVersion.get(version & -version);
    }

    public static EnumSet<ProductType> getSet() {
        return EnumSet.allOf(ProductType.class);
    }

    ProductType(int code, int version, String description, Integer ...versions) {
        if (code==0){
            this.messageKey = name();
        }
        this.code = code;
        this.version = version;
        this.description = description;
        this.versions = versions;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public int getVersion() {
        return version;
    }

    public boolean isForProduct(Integer mask) {
        return (mask & code) == code;
    }

    public String getMessageKey() {
        return messageKey;
    }
    
    public Integer[] getVersions(){
        return versions;
    }
}
