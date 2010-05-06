package com.inthinc.pro.model;

import org.junit.Assert;
import org.junit.Test;

public class FuelEfficiencyTypeTest {

	@Test
	public void testFuelEfficiencyType(){
		
		Number kmpl = FuelEfficiencyType.KMPL.convertFromMPG(23.00);
		Assert.assertEquals(9.8f,kmpl);
		Number mpguk = FuelEfficiencyType.MPG_UK.convertFromMPG(23);
		Assert.assertEquals(27.6f,mpguk);
		mpguk = FuelEfficiencyType.MPG_UK.convertFromMPG(20);
		Assert.assertEquals(24.0f,mpguk);
		mpguk = FuelEfficiencyType.MPG_UK.convertFromMPG(0);
		Assert.assertEquals(0.00f,mpguk);
		mpguk = FuelEfficiencyType.MPG_UK.convertFromMPG(null);
		Assert.assertEquals(null,mpguk);
		mpguk = FuelEfficiencyType.MPG_UK.convertFromMPG(-23);
		Assert.assertEquals(-27.6f,mpguk);
		Number lp100km = FuelEfficiencyType.LP100KM.convertFromMPG(0);
		Assert.assertEquals(0.0f,lp100km);
		lp100km = FuelEfficiencyType.LP100KM.convertFromMPG(10);
		Assert.assertEquals(23.5f,lp100km);
		mpguk = FuelEfficiencyType.LP100KM.convertFromMPG(null);
		Assert.assertEquals(null,mpguk);
		lp100km = FuelEfficiencyType.LP100KM.convertFromMPG(0d);
		Assert.assertEquals(0.0f,lp100km);
		lp100km = FuelEfficiencyType.LP100KM.convertFromMPG((Number.class.cast(0d)));
		Assert.assertEquals(0.0f,lp100km);
		
	}
}
