package com.inthinc.pro.model.configurator;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.BaseEnum;

public enum SliderType implements BaseEnum{

    HARD_ACCEL_SLIDER (1, "hardAcceleration"),
    HARD_BRAKE_SLIDER (2, "hardBrake"),
    HARD_TURN_SLIDER  (3, "hardTurn"),
    HARD_BUMP_SLIDER  (4, "hardVertical");
    
    private Integer code;
    private String  propertyName;
    
    public String getPropertyName() {
        return propertyName;
    }

    private SliderType(Integer code, String property) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private static final Map<Integer, SliderType> lookup = new HashMap<Integer, SliderType>();
    static
    {
        for (SliderType p : EnumSet.allOf(SliderType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public static SliderType valueOf(Integer code)
    {
        return lookup.get(code);
    }
    public static List<SliderType> getSensitivities(){
        
        List<SliderType> sensitivityTypes = new ArrayList<SliderType>();
        sensitivityTypes.add(SliderType.HARD_ACCEL_SLIDER);
        sensitivityTypes.add(SliderType.HARD_BRAKE_SLIDER);
        sensitivityTypes.add(SliderType.HARD_TURN_SLIDER);
        sensitivityTypes.add(SliderType.HARD_BUMP_SLIDER);
        
        return sensitivityTypes;
     }

}
