package com.inthinc.pro.model.configurator;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.model.BaseEnum;
import com.inthinc.pro.model.pagination.FilterableEnum;

/**
 * Enumerated values to represent the seperate products that interact with the portal.<br />
 * <br />
 * This representation of each product type uses a bit mask to seperate the types, <br />
 * while ignoring the different version numbers for each type.  This is accomplished<br />
 * by taking the lowest asserted bit in the binary representation of the version number.<br />
 * This means that each product has a reserved lowest order asserted bit.<br />
 * <br />
 * <code>TiwiPro</code> uses the least significant bit, <code>0xXXX1</code>, and has versions 1, 3, 5, 7, etc.<br />
 * <br />
 * <code>Waysmart</code> uses the second bit, <code>0xXX10</code>, and has versions 2, 6, 10, 14, etc.<br />
 * <br />
 * <code>WS850</code> uses the third big, <code>0xX100</code>, and has versions 4, 12, 20, 28, etc.
 */
public enum ProductType implements BaseEnum, FilterableEnum {
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

    @Override
    public Object getFilter() {
        if (this == ProductType.UNKNOWN) {
            Integer v[] = new Integer[1];
            v[0] = 0;
            return Arrays.asList(v);
        }
        
        return Arrays.asList(versions);
    }

    @Override
    public Boolean includeNull() {
        return (this == ProductType.UNKNOWN);
    }
}
