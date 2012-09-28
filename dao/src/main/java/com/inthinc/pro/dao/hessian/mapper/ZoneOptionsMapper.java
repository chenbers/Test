package com.inthinc.pro.dao.hessian.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.annotations.ConvertColumnToField;
import com.inthinc.pro.dao.annotations.ConvertFieldToColumn;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.zone.option.ZoneAvailableOption;
import com.inthinc.pro.model.zone.option.ZoneOption;

public class ZoneOptionsMapper extends AbstractMapper {
  
    @ConvertColumnToField(columnName = "options")
    public void optionsToModel(Zone zone, Object value)
    {
        if (zone == null || value == null)
            return;

        if (value instanceof List)
        {
            List<ZoneOption> options = new ArrayList<ZoneOption>();
            
            List<Map<String, Integer>> simpleOptions = (List<Map<String, Integer>>)value;
            for (Map<String, Integer> option : simpleOptions) {
                ZoneAvailableOption zoneAvailableOption = ZoneAvailableOption.valueOf(option.get("option"));
                if (zoneAvailableOption != null)
                    options.add(new ZoneOption(zoneAvailableOption, ZoneAvailableOption.convertOptionValue(zoneAvailableOption.getOptionType(), option.get("value"))));
            }
            
            zone.setOptions(options);
            
        }
    }

    @ConvertFieldToColumn(fieldName = "options")
    public void optionsToColumn(Zone zone, Object value)
    {
        if (Map.class.isInstance(value))
        {
            List<ZoneOption> options = zone.getOptions();
            if (options == null)
                return;
            List<Option> simpleOptions = new ArrayList<Option>();
            for (ZoneOption option : options)
                simpleOptions.add(new Option(option.getOption().getCode(), option.getValue().getValue()));
            
            ((Map<String, Object>)value).put("options", simpleOptions);
        }
    }

    
    @ConvertColumnToField(columnName = "optionsMap")
    public void optionsMapToModel(Zone zone, Object value)
    {
        return;
    }

    @ConvertFieldToColumn(fieldName = "optionsMap")
    public void optionsMapToColumn(Zone zone, Object value)
    {
        return;
    }

    class Option implements Serializable{
        int option;
        int value;
        
        public Option(int option, int value) {
            this.option = option;
            this.value = value;
        }
        
        public int getOption() {
            return option;
        }

        public void setOption(int option) {
            this.option = option;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

    }

}
