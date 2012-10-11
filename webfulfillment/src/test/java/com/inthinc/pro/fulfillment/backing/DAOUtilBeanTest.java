package com.inthinc.pro.fulfillment.backing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class DAOUtilBeanTest {
	DAOUtilBean dm;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Ignore("not ready yet") 
	@Before
	public void setUp() throws Exception {
		String server = "localhost";
		int port = 8099;
		// !!!PRODUCTION!!!
	    server = "67.208.138.211";
		port = 8099;
//		dm = new DAOUtilBean(server, port);
	}
	
	@Ignore("not ready yet") 
	@Test
	public void TestGetAccounts() {
		Map<Integer, String> accts = dm.getAccountMap();
		Set keys = accts.keySet();
		for (Iterator iter=keys.iterator(); iter.hasNext();){
			Integer id = (Integer) iter.next();
			String name = (String) accts.get(id);
			System.out.println(id + " " + name);
		}
		assertTrue(accts.values().size()>2);
	}

	@Ignore("not ready yet")
	@Test
	public void TestCheckDevice() {
		dm.setImei("011596000058838");
		dm.setImei("011596000063655");
		dm.setImei("XXXXXXX");
		assertFalse(dm.checkDevice());
		assertTrue(dm.getErrorMsg().indexOf("Device not found")>0);
	}

	
	@Ignore("not ready yet")
	@Test
	public void TestMoveDevice() {
		dm.setImei("XXXXXXX");
		dm.moveDeviceAction();
		assertTrue(dm.getErrorMsg().indexOf("Device not found")>0);
	}
}
