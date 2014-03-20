package com.inthinc.pro.backing;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.GroupDAO;
import static org.easymock.classextension.EasyMock.*;

import com.inthinc.pro.dao.jdbc.DriverJDBCDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.User;
import org.junit.Ignore;

import com.inthinc.pro.backing.VehiclesBean.VehicleView;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.app.States;
import org.junit.Test;

//marking as ignore since won't work with new pagination stuff
@Ignore
public class VehiclesBeanTest extends BaseAdminBeanTest<VehiclesBean.VehicleView>
{
    private static Integer GROUP_ID = 1;
    private static Integer ACCOUNT_ID = 1;
    private static Integer DRIVER_ID = 1;
    private static Integer USER_ID = 1;
    private static Group mockGroup;
    private static Driver mockDriver;
    private static User mockUser;

    static {
        mockGroup = new Group();
        mockGroup.setAccountID(ACCOUNT_ID);
        mockGroup.setGroupID(GROUP_ID);
        mockGroup.setParentID(0);

        mockDriver = new Driver();
        mockDriver.setDriverID(DRIVER_ID);
        mockDriver.setGroupID(GROUP_ID);

        mockUser = new User();
        mockUser.setGroupID(GROUP_ID);
    }

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

    @Test
    public void getDriversTest() throws NoSuchMethodException {
        // create bean, set dao
        VehiclesBean vehiclesBean = createMock(VehiclesBean.class, new Method[]{VehiclesBean.class.getMethod("getUser", null),
                VehiclesBean.class.getMethod("getAccountID")});

        GroupDAO mockGroupDAO = createMock(GroupDAO.class);
        DriverJDBCDAO mockDriverJDBCDAO = createMock(DriverJDBCDAO.class);
        vehiclesBean.setGroupDAO(mockGroupDAO);
        vehiclesBean.setDriverJDBCDAO(mockDriverJDBCDAO);

        // expectations
        List<Group> mockGroupList = Arrays.asList(mockGroup);
        List<Integer> mockGroupIDList = Arrays.asList(GROUP_ID);
        List<Driver> mockDriverList = Arrays.asList(mockDriver);
        expect(mockGroupDAO.getGroupsByAcctID(ACCOUNT_ID)).andReturn(mockGroupList);
        expect(mockDriverJDBCDAO.getDriversInGroupIDList(mockGroupIDList)).andReturn(mockDriverList);
        expect(vehiclesBean.getUser()).andReturn(mockUser);
        expect(vehiclesBean.getAccountID()).andReturn(ACCOUNT_ID);
        replay(vehiclesBean, mockGroupDAO, mockDriverJDBCDAO);

        // exercise the code
        List<Driver> driverList = vehiclesBean.getDrivers();
        assertTrue(driverList!=null);
        assertFalse(driverList.isEmpty());
        assertTrue(driverList.size() == 1);
        Driver driver = driverList.get(0);
        assertEquals(driver.getGroupID(), GROUP_ID);
        assertEquals(driver.getDriverID(), DRIVER_ID);

        Map<Integer, Driver> driverMap = vehiclesBean.getDriverMap();
        assertTrue(driverMap!=null);
        assertFalse(driverMap.isEmpty());
        assertTrue(driverMap.size() == 1);
        driver = driverMap.get(DRIVER_ID);
        assertTrue(driver!=null);
        assertEquals(driver.getGroupID(), GROUP_ID);
        assertEquals(driver.getDriverID(), DRIVER_ID);
    }
}
