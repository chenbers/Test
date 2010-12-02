package com.inthinc.pro.dao.hessian.mapper;

import com.inthinc.pro.dao.annotations.ConvertColumnToField;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.configurator.ProductType;

@SuppressWarnings("serial")
public class DeviceMapper extends AbstractMapper
{
    @ConvertColumnToField(columnName = "productVer")
    public void productVersionToProductType(Device device, Object value)
    {
        if (device == null || value == null) return;
        
        if (value instanceof Integer){
            
            device.setProductVersion(ProductType.getProductTypeFromVersion((Integer)value));
        }
    }
}
