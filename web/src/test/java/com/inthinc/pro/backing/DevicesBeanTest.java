package com.inthinc.pro.backing;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.backing.DevicesBean.DeviceView;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.configurator.ProductType;

//marking as ignore since won't work with new pagination stuff
@Ignore
public class DevicesBeanTest extends BaseAdminBeanTest<DevicesBean.DeviceView>
{
    @Override
    protected DevicesBean getAdminBean()
    {
        return (DevicesBean) applicationContext.getBean("devicesBean");
    }

    @Override
    protected String getFilterValue()
    {
        return "a";
    }

    @Override
    protected void populate(DeviceView editItem, BaseAdminBean<DevicesBean.DeviceView> adminBean)
    {
        editItem.setStatus(DeviceStatus.ACTIVE);
        editItem.setName("DEV123");
        editItem.setImei("123456");
        editItem.setSim("123456789");
        editItem.setPhone("123-456-7890");
        editItem.setProductVersion(ProductType.TIWIPRO);
        editItem.setActivated(new Date());
    }

    @Override
    protected String[] getBatchUpdateFields()
    {
//        return new String[] { "name", "ephone" };
            return new String[]{};
    }
    
    @Test
    @Ignore
    @Override
    public void batchEdit() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        
    }

}
