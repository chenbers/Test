package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.pagination.FilterableEnum;

@XmlRootElement
public enum VehicleType implements BaseEnum, FilterableEnum
{
    LIGHT(0, "Light"), MEDIUM(1, "Medium"), HEAVY(2, "Heavy");

    private int    code;
    private String description;

    private VehicleType(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    @Override
    public Integer getCode()
    {
        return this.code;
    }

    public String getDescription()
    {
        return this.description;
    }

    private static final HashMap<Integer, VehicleType> lookup = new HashMap<Integer, VehicleType>();
    static
    {
        for (VehicleType p : EnumSet.allOf(VehicleType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public static VehicleType valueOf(Integer code)
    {
        return lookup.get(code);
    }
    public String getName(){
    	return name();
    }
    @Override
    public String toString()
    {
    	return this.getClass().getSimpleName()+"."+super.toString();
//        StringBuilder sb = new StringBuilder();
//        sb.append(this.name());
//        return sb.toString();
    }

    @Override
    public Object getFilter() {
        return ""+code;
    }
    
    @Override
    public Boolean includeNull() {
        return false;
    }

}
