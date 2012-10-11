package com.inthinc.pro.service.adapters;

import static mockit.Mockit.tearDownMocks;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import mockit.Expectations;
import mockit.Mocked;

import org.apache.commons.lang.NotImplementedException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.model.Device;

/**
 * Test for the DeviceDAOAdapter class.
 * 
 * @author dcueva
 */
public class DeviceDAOAdapterTest {

	private final Integer DEVICE_ID = new Integer(1);
	private final Integer ACCOUNT_ID = new Integer(9);
	private final Device device = new Device();
	private final String IMEI = "IMEI";
	private final String SERIAL_NUM = "SERIAL_NUM";
	
	@Mocked(methods = {"getAccountID"})
	private DeviceDAOAdapter adapterSUT; 
	
	@Mocked DeviceDAO deviceDAOMock;
	
	@Before
	public void beforeMethod() {
		adapterSUT.setDeviceDAO(deviceDAOMock);
	}
	
	@After
	public void afterMethod(){
		tearDownMocks();
	}
	
	@Test
	public void testGetAll(){
		final List<Device> deviceList = new ArrayList<Device>();
		deviceList.add(device);
		
		new Expectations(){{
			adapterSUT.getAccountID(); returns(ACCOUNT_ID);
			deviceDAOMock.getDevicesByAcctID(ACCOUNT_ID); returns(deviceList);
		}};
		assertEquals(adapterSUT.getAll(), deviceList);
	}	
	
	@Test
	public void testGetDAO(){
		assertEquals(adapterSUT.getDAO(), deviceDAOMock);
	}		

	@Test
	public void testGetResourceID(){
		device.setDeviceID(DEVICE_ID);
		
		assertEquals(adapterSUT.getResourceID(device), DEVICE_ID);
	}
	
	@Test(expected= NotImplementedException.class)
	public void testUpdate(){
		adapterSUT.update(device);
	}		

	@Test(expected= NotImplementedException.class)
	public void testDelete(){
		adapterSUT.delete(DEVICE_ID);
	}
	
	@Test
	public void testFindByIMEI(){
		new Expectations(){{
			deviceDAOMock.findByIMEI(IMEI); returns(device);
		}};
		assertEquals(adapterSUT.findByIMEI(IMEI), device);
	}
	
	@Test
	public void testFindBySerialNum(){
		new Expectations(){{
			deviceDAOMock.findBySerialNum(SERIAL_NUM); returns(device);
		}};
		assertEquals(adapterSUT.findBySerialNum(SERIAL_NUM), device);
	}	
	
}
