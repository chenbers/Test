package com.inthinc.pro.dao.hessian.mapper;

import com.inthinc.pro.dao.annotations.ConvertColumnToField;
import com.inthinc.pro.dao.annotations.ConvertFieldToColumn;
import com.inthinc.pro.model.ZoneAlert;

@SuppressWarnings({ "serial"})
public class ZoneAlertMapper extends AbstractMapper {

    @ConvertColumnToField(columnName = "speedSettings")
    public void speedSettingsToModel(ZoneAlert zoneAlert, Object value)
    {
    }
    @ConvertFieldToColumn(fieldName = "speedSettings")
    public void speedSettingsToColumn(ZoneAlert zoneAlert, Object value)
    {
    }
    @ConvertColumnToField(columnName = "severityLevel")
    public void severityLevelToModel(ZoneAlert zoneAlert, Object value)
    {
    }
    @ConvertFieldToColumn(fieldName = "severityLevel")
    public void severityLevelToColumn(ZoneAlert zoneAlert, Object value)
    {
    }

}
