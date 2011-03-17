package com.inthinc.pro.backing.dao.mapper;

import java.util.Map;

import com.inthinc.pro.dao.annotations.ConvertColumnToField;
import com.inthinc.pro.dao.annotations.ConvertFieldToColumn;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.configurator.ProductType;

public class DaoUtilDeviceMapper extends DaoUtilMapper {
    
    @ConvertColumnToField(columnName = "productVer")
    public void productVersionToProductType(Device device, Object value)
    {
        if (device == null || value == null) return;
        
        if (value instanceof Integer){
            
            device.setProductVersion(ProductType.getProductTypeFromVersion((Integer)value));
        }
    }
    
    @SuppressWarnings("unchecked")
    @ConvertFieldToColumn(fieldName = "productVersion")
    public void productTypeToProductVersion(Device device, Object value)
    {
        if (device == null || value == null || device.getProductVersion() == null) return;
        
        if (Map.class.isInstance(value))
        {
            ((Map<String, Object>)value).put("productVer", device.getProductVersion().getVersion());
        }

    }
    
}
