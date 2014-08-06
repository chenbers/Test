package com.inthinc.pro.backing.dao.mapper;

import java.util.HashMap;
import java.util.Map;

public class DaoUtilBooleanMapper extends BaseUtilMapper {
    

    @Override
    public Map<String, Object> convertToMap(Object modelObject, boolean includeNonUpdateables) {
        if (modelObject instanceof Boolean) {
            if ((Boolean)modelObject == Boolean.TRUE) {
                Map<String, Object> returnMap = new HashMap<String, Object>();
                returnMap.put("count", 1);
                return returnMap;
            }
        }
        return null;
    }

}
