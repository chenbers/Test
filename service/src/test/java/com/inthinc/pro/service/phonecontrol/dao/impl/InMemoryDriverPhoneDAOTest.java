package com.inthinc.pro.service.phonecontrol.dao.impl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;

import org.junit.Test;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;

/**
 * Unit test for InMemoryDriverPhoneDAO
 * 
 * @author dcueva
 *
 */
public class InMemoryDriverPhoneDAOTest {

	InMemoryDriverPhoneDAO driverPhoneSUT = new InMemoryDriverPhoneDAO();
	
	/** 
	 * Happy path, 1 driver returned from the back end. 
	 * @param driverDAOMock  Mock
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testRegenerateDriverIDSet(@Mocked final DriverDAO driverDAOMock) {

		Deencapsulation.setField(driverPhoneSUT, driverDAOMock);
		
		new Expectations(){{
			driverDAOMock.getDriversWithDisabledPhones(); returns(getDrivers(1));
		}};
		
		// run test
		driverPhoneSUT.regenerateDriverIDSet(); 
		
		Set<Integer> driversIDSet = (Set<Integer>) Deencapsulation.getField(driverPhoneSUT, "driverIDSet");
		assertTrue(driversIDSet.size() == 1);
		assertTrue(driversIDSet.iterator().next().equals(new Integer(0)));
		
	}

	/** 
	 * No drivers returned from the back end. 
	 * @param driverDAOMock  Mock
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testRegenerateDriverIDSetZeroDrivers(@Mocked final DriverDAO driverDAOMock) {

		Deencapsulation.setField(driverPhoneSUT, driverDAOMock);

		new Expectations(){{
			driverDAOMock.getDriversWithDisabledPhones(); returns(getDrivers(0));
		}};

		// run test
		driverPhoneSUT.regenerateDriverIDSet(); 
		
		Set<Integer> driversIDSet = (Set<Integer>) Deencapsulation.getField(driverPhoneSUT, "driverIDSet");
		assertTrue(driversIDSet.size() == 0);
	}
	
	/** 
	 * An exception is thrown. 
	 * @param driverDAOMock  Mock
	 */
	@Test
	public void testRegenerateDriverIDException(@Mocked final DriverDAO driverDAOMock) {
	
		Deencapsulation.setField(driverPhoneSUT, driverDAOMock);

		new Expectations(){{
			driverDAOMock.getDriversWithDisabledPhones(); result = new NullPointerException(); 
		}};

		driverPhoneSUT.regenerateDriverIDSet(); 
		// No exception should be thrown
	}
	
	
	/**
	 * Return a number of drivers in a list
	 * 
	 * @param numDrivers number of drivers to include in the list
	 * @return lsit of numDrivers drivers.
	 */
	List<Driver> getDrivers(int numDrivers) {
		List<Driver> driverList = new ArrayList<Driver>();
        for (int i= 0; i < numDrivers; ++i) {
			Driver driver = new Driver();
			driver.setDriverID(i);
			driverList.add(driver);
		}
        return driverList;
	}
}
