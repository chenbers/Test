package com.inthinc.pro.model.phone;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.BaseEnum;

/**
 * Cell phone control Service Provider enum.
 */
public enum CellProviderType implements BaseEnum{
    UNDEFINED_PROVIDER(0,"None"),
    CELL_CONTROL (1,"Cellcontrol"),
    ZOOM_SAFER (2,"ZoomSafer");
    
    private String name;
    private int code;
    
    private CellProviderType(int code, String providerName) {
        this.name = providerName;
        this.code = code;
    }

    /**
     * The name getter.
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    private static final Map<Integer, CellProviderType> lookup = new HashMap<Integer, CellProviderType>();
    static
    {
        for (CellProviderType p : EnumSet.allOf(CellProviderType.class))
        {
            lookup.put(p.code, p);
        }
    }
    
    public static CellProviderType valueOf(Integer code)
    {
        return lookup.get(code);
    }

    public static List<CellProviderType> getAll(){
        return new ArrayList<CellProviderType>(EnumSet.of(CellProviderType.CELL_CONTROL,CellProviderType.ZOOM_SAFER));
    }
    @Override
    public Integer getCode() {
        return code;
    }
    
    
}
