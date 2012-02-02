package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum DOTOfficeType implements BaseEnum {
    NONE(0), 
    MAIN(1), 
    TERMINAL(2); 

    Integer code;

    
    private DOTOfficeType(Integer code) {
        this.code = code;
    }
    
    @Override
    public Integer getCode() {
        return code;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }
    
    private static final Map<Integer, DOTOfficeType> lookup = new HashMap<Integer, DOTOfficeType>();
    static
    {
        for (DOTOfficeType p : EnumSet.allOf(DOTOfficeType.class))
        {
            lookup.put(p.code, p);
        }
    }
    public static DOTOfficeType valueOf(Integer code)
    {
        return lookup.get(code);
    }


}
