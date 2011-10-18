package com.inthinc.pro.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VehicleDOTTypeTest {

	@Test
	public void vehicleDOTTypeTest(){
		
		assertEquals(null,VehicleDOTType.getFromSetting(null));
		assertEquals(VehicleDOTType.NON_DOT, VehicleDOTType.getFromSetting(1));
		assertEquals(VehicleDOTType.DOT, VehicleDOTType.getFromSetting(0));
		assertEquals(VehicleDOTType.PROMPT_FOR_DOT_TRIP, VehicleDOTType.getFromSetting(2));
		assertEquals(null, VehicleDOTType.getFromSetting(4));
	}
}
