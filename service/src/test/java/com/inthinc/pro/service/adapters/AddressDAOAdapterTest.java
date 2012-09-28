package com.inthinc.pro.service.adapters;

import static mockit.Mockit.tearDownMocks;
import static org.junit.Assert.assertEquals;
import mockit.Mocked;

import org.apache.commons.lang.NotImplementedException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.AddressDAO;
import com.inthinc.pro.model.Address;

/**
 * Test for the AddressDAOAdapter class.
 * 
 * @author dcueva
 */
public class AddressDAOAdapterTest {

	private final Integer ADDRESS_ID = new Integer(1);
	private final Address address = new Address();
	
	private static final AddressDAOAdapter adapterSUT = new AddressDAOAdapter(); 
	
	@Mocked AddressDAO addressDAOMock;
	
	@Before
	public void beforeMethod() {
		adapterSUT.setAddressDAO(addressDAOMock);
	}
	
	@After
	public void afterMethod(){
		tearDownMocks();
	}
	
	@Test(expected= NotImplementedException.class)
	public void testGetAll(){
		adapterSUT.getAll();
	}	
	
	@Test
	public void testGetDAO(){
		assertEquals(adapterSUT.getDAO(), addressDAOMock);
	}		

	@Test
	public void testGetResourceID(){
		address.setAddrID(ADDRESS_ID); 
		
		assertEquals(adapterSUT.getResourceID(address), ADDRESS_ID);
	}		
	
}
