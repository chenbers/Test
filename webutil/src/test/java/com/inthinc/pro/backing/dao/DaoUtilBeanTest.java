package com.inthinc.pro.backing.dao;

import static org.junit.Assert.*;

import java.util.List;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class DaoUtilBeanTest {

	private DaoUtilBean dab;
	@Before
	public void setUp() throws Exception {
		dab = new DaoUtilBean();
		dab.setSelectedMethod("createAcct");
	}

	
	@Ignore
	@Test
	public void testGetSelectedMethod() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testSetSelectedMethod() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetMethodSelectList() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetMethodMap() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testSetMethodMap() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetParamList() {
		List<Param> plist = dab.getParamList();
		assertTrue(plist.size()>0);
	}

	@Ignore
	@Test
	public void testResultsAction() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testSetParamList() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetColumnHeaders() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testSetColumnHeaders() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetRecords() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testSetRecords() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetSiloServiceCreator() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testSetSiloServiceCreator() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetReportServiceCreator() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testSetReportServiceCreator() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetSelectedMethodDescription() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testSetSelectedMethodDescription() {
		fail("Not yet implemented");
	}

}
