package com.inthinc.pro.backing;

import java.lang.reflect.InvocationTargetException;

import com.inthinc.pro.backing.VehiclesBean.VehicleView;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.app.States;

public class VehiclesBeanTest extends BaseAdminBeanTest<VehiclesBean.VehicleView>
{
    @Override
    protected BaseAdminBean<VehicleView> getAdminBean()
    {
        @SuppressWarnings("unchecked")
		BaseAdminBean<VehicleView> vehiclesBean = (BaseAdminBean<VehicleView>) applicationContext.getBean("vehiclesBean");
        return vehiclesBean;
    }

    @Override
    protected String getFilterValue()
    {
        return "a";
    }

    @Override
    protected void populate(VehicleView editItem, BaseAdminBean<VehiclesBean.VehicleView> adminBean)
    {
        editItem.setStatus(Status.ACTIVE);
        editItem.setName("Vehicle");
        editItem.setMake("Chevy");
        editItem.setModel("Test");
        editItem.setYear(2009);
        editItem.setColor("Puce");
        editItem.setVtype(VehicleType.LIGHT);
        editItem.setVIN("12345678901234567");
        editItem.setWeight(5000);
        editItem.setLicense("ABC123");
        editItem.setState(States.getStateByAbbrev("HI"));
        
    }

    @Override
    protected String[] getBatchUpdateFields()
    {
        return new String[] { "name", "model" };
    }

    @Override
    public void checkBatchEditId(BaseAdminBean<VehicleView> adminBean) {

        assertEquals(adminBean.getItem().getId().intValue(), -1);
    }
    @Override
    public void setProductType(BaseAdminBean<VehicleView> adminBean, String type){
        ((BaseAdminBean<VehicleView>)adminBean).getFilterValues().put("productType",type);
    }
    @Override
    public void batchEdit() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        // login
        loginUser("superuser101");

        // get the bean from the applicationContext (initialized by Spring injection)
        BaseAdminBean<VehicleView> adminBean = getAdminBean();
        adminBean.getItems();
        setProductType(adminBean,null);

        // select items to edit
        int selected = selectItems(adminBean, 3);
        assertEquals(selected, adminBean.getSelectedItems().size());
        setProductType(adminBean,null);

    }
}
