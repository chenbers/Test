package com.inthinc.pro.backing.dao.mapper;

import java.util.Map;

import com.inthinc.pro.dao.annotations.ConvertFieldToColumn;
import com.inthinc.pro.model.TablePreference;

public class DaoUtilTablePrefMapper extends DaoUtilMapper {

    @SuppressWarnings("unchecked")
    @ConvertFieldToColumn(fieldName = "visible")
    public void visibleToColumn(TablePreference tablePref, Object value)
    {
        if (Map.class.isInstance(value))
        {
            StringBuffer buf = new StringBuffer();
            for (Boolean visible : tablePref.getVisible())
            {
                buf.append(visible ? "1" :  "0");
            }
            tablePref.setFlags(buf.toString());
            ((Map<String, Object>)value).put("flags", buf.toString());
            ((Map<String, Object>)value).put("visible", null);
        }
    }
}
