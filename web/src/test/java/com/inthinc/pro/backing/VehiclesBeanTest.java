package com.inthinc.pro.backing;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.BeanUtils;

import com.inthinc.pro.backing.VehiclesBean.VehicleView;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.app.States;

public class VehiclesBeanTest extends BaseAdminBeanTest<VehiclesBean.VehicleView>
{
    @Override
    protected VehiclesBean getAdminBean()
    {
        VehiclesBean vehiclesBean = (VehiclesBean) applicationContext.getBean("vehiclesBean");
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
//    @Override
    public void setProductType(BaseAdminBean<VehicleView> adminBean){
        ((VehiclesBean)adminBean).setBatchProductChoice("Tiwipro R74");
    }
    @Override
    public void batchEdit() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        // login
        loginUser("superuser101");

        // get the bean from the applicationContext (initialized by Spring injection)
        BaseAdminBean<VehicleView> adminBean = getAdminBean();
        adminBean.getItems();
        setProductType(adminBean);

        // select items to edit
        int selected = selectItems(adminBean, 3);
        assertEquals(selected, adminBean.getSelectedItems().size());

        // edit
//        assertFalse(adminBean.isBatchEdit());
//        assertEquals(adminBean.batchEdit(), adminBean.getEditRedirect());
//        assertFalse(adminBean.isAdd());
//        assertFalse(adminBean.isBatchEdit());
//
//        // edit item
//        assertNotNull(adminBean.getItem());
//        
//        checkBatchEditId(adminBean);
//
//        // cancel edit
//        assertEquals(adminBean.cancelEdit(), adminBean.getFinishedRedirect());
//        assertEquals(adminBean.getSelectedItems().size(), 0);
//
//        // start another edit
//        selected = selectItems(adminBean, 3);
//        assertEquals(selected, adminBean.getSelectedItems().size());
//        adminBean.batchEdit();
//
//        // populate
//        populate(adminBean.getItem(), adminBean);
//        for (final String field : getBatchUpdateFields()) {
//            adminBean.getUpdateField().put(field, true);
//        }
//
//        // save
//        int count = adminBean.getItemCount();
//        assertEquals(adminBean.save(), adminBean.getFinishedRedirect());
//        assertEquals(adminBean.getItemCount(), count);
//        assertEquals(adminBean.getSelectedItems().size(), 0);
//
//        // test batch update fields
//        for (final VehicleView item : adminBean.getSelectedItems())
//            for (final String field : getBatchUpdateFields())
//            {
//                final PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(item.getClass(), field);
//                if (descriptor != null)
//                    assertEquals(descriptor.getReadMethod().invoke(item, new Object[0]), descriptor.getReadMethod().invoke(adminBean.getItem(), new Object[0]));
//            }
    }
    
}
