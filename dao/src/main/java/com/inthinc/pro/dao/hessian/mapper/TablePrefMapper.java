package com.inthinc.pro.dao.hessian.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.annotations.ConvertColumnToField;
import com.inthinc.pro.dao.annotations.ConvertFieldToColumn;
import com.inthinc.pro.model.TablePreference;

public class TablePrefMapper extends AbstractMapper
{
    @ConvertColumnToField(columnName = "flags")
    public void flagsToModel(TablePreference tablePref, Object value)
    {
        if (tablePref == null || value == null)
            return;

        if (value instanceof String)
        {
            List<Boolean> visible = new ArrayList<Boolean>();
            
            String list = (String)value;
            for (int i = 0; i < list.length(); i++)
            {
                visible.add(list.charAt(i) == '1');   
            }
            
            
            
            tablePref.setVisible(visible);
            
        }
    }

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
