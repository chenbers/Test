package com.inthinc.pro.backing;

import com.inthinc.pro.backing.VehiclesBean.VehicleView;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.VehicleStatus;
import com.inthinc.pro.model.VehicleType;

public class VehiclesBeanTest extends BaseAdminBeanTest<VehiclesBean.VehicleView>
{
    @Override
    protected VehiclesBean getAdminBean()
    {
        return (VehiclesBean) applicationContext.getBean("vehiclesBean");
    }

    @Override
    protected String getFilterValue()
    {
        return "a";
    }

    @Override
    protected void populate(VehicleView editItem)
    {
        editItem.setCostPerHour(1000);
        editItem.setStatus(VehicleStatus.ACTIVE);
        editItem.setName("Vehicle");
        editItem.setMake("Chevy");
        editItem.setModel("Test");
        editItem.setYear(2009);
        editItem.setColor("Puce");
        editItem.setVtype(VehicleType.LIGHT);
        editItem.setVIN("12345678901234567");
        editItem.setWeight(5000);
        editItem.setLicense("ABC123");
        editItem.setState(State.HI);
    }

    @Override
    protected String[] getBatchUpdateFields()
    {
        return new String[] { "name", "model" };
    }
}
