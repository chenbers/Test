package com.inthinc.pro.backing;

import java.util.Date;

import com.inthinc.pro.backing.DevicesBean.DeviceView;
import com.inthinc.pro.model.DeviceStatus;

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
        editItem.setBaselineID(1234);
        editItem.setStatus(DeviceStatus.ACTIVE);
        editItem.setName("DEV123");
        editItem.setImei("123456");
        editItem.setSim("123456789");
        editItem.setPhone("123-456-7890");
        editItem.setEphone("098-765-4321");
        editItem.setActivated(new Date());
    }

    @Override
    protected String[] getBatchUpdateFields()
    {
        return new String[] { "name", "ephone" };
    }
}
