package com.inthinc.pro.backing.dao.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.inthinc.pro.dao.annotations.ConvertColumnToField;
import com.inthinc.pro.dao.annotations.ConvertFieldToColumn;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.zone.option.ZoneAvailableOption;
import com.inthinc.pro.model.zone.option.ZoneOption;

public class DaoUtilZoneMapper extends DaoUtilMapper {
    
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
            try {
                    PropertyUtils.setProperty(zone, "options", options);
            }
            catch (Exception e) {
                
            }
        }
    }

    @Override
    protected List<Map<String, Object>> convertList(List<?> list, Map<Object, Map<String, Object>> handled, boolean includeNonUpdateables) {
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        for (Object o : list) {
            if (o instanceof ZoneOption) {
                ZoneOption option = (ZoneOption)o;
                returnList.add(getMapForZoneOption(option));
            }
            else 
                returnList.add(convertToMap(o, handled, includeNonUpdateables));
        }
        return returnList;
    }

    
    private Map<String, Object> getMapForZoneOption(ZoneOption option) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("value", option.getValue().getValue());
        returnMap.put("option", option.getOption().getCode());
        return returnMap;
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
