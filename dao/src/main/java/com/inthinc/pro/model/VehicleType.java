package com.inthinc.pro.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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

    public Long getBitMask(){
        return 1l << code;
    }

    public static List<VehicleType> getVehicleTypes(Long vehicleTypeMask){
        Set<VehicleType> vehicleTypes = EnumSet.noneOf(VehicleType.class);
        if (vehicleTypeMask == null) return Collections.emptyList();
        for(VehicleType vt : EnumSet.allOf(VehicleType.class)){
           if (vehicleTypeMatch(vt.code, vehicleTypeMask)){
               vehicleTypes.add(vt);
           }
        }
        return new ArrayList<VehicleType>(vehicleTypes);
    }

    public static  Long convertTypes(List<VehicleType> vehicleTypes){
        Long vehicleTypeMask = new Long(0);
        if(vehicleTypes != null){
            for(VehicleType vt : vehicleTypes){

                long bitValue = vt.getBitMask();
                vehicleTypeMask = vehicleTypeMask.longValue()  | bitValue;
            }
        }
        return vehicleTypeMask;
    }

    private static boolean vehicleTypeMatch(int vtype, long vtypeMask) {
        return ((vtypeMask & (1 << vtype)) != 0);
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
