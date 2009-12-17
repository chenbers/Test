package com.inthinc.pro.backing;

import junit.framework.Assert;

import org.junit.Test;

import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.NoAddressFoundException;

public class AddressLookupTest extends BaseBeanTest{
	
	@Test
	public void addressLookupTest(){
		
		AddressLookup addressLookup = (AddressLookup)applicationContext.getBean("addressLookupBean");

		try{
		
			String address = addressLookup.getAddress(new LatLng(40.618057,-111.787941));
			Assert.assertEquals("3861 Prospector Dr, Cottonwood Heights, UT 84121".toUpperCase(), address.toUpperCase());
			
		}
		catch(NoAddressFoundException nafe){
			
			
		}
//		try{
//			
//			addressLookup.getAddress(null); 
//		}
//		catch(NoAddressFoundException nafe){
//			
//			Assert.assertEquals("No address found at lat ,lng ", nafe.getMessage());
//		}
	}

	@Test
	public void googleAddressLookupTest(){
		
		AddressLookup googleAddressLookup = (AddressLookup)applicationContext.getBean("googleAddressLookupBean");
		
		try{
			
				String address = googleAddressLookup.getAddress(new LatLng(40.618057,-111.787941),true);
				System.out.println(address);
				Assert.assertNotNull("Address was null", address);
				
			}
			catch(NoAddressFoundException nafe){
				
				
			}
			String noAddressNoLatLng = null;
			try{
				
				noAddressNoLatLng = googleAddressLookup.getAddress(new LatLng(100,200),false);
				Assert.assertNotNull("Address was null", noAddressNoLatLng);
				
			}
			catch(NoAddressFoundException nafe){
				
				Assert.assertNull(noAddressNoLatLng);
			}
			String noAddressLatLng = null;
			try{
				
				noAddressLatLng = googleAddressLookup.getAddress(new LatLng(100,200),true);
				Assert.assertNotNull("Address was null", noAddressLatLng);
				
			}
			catch(NoAddressFoundException nafe){
				
				Assert.assertNull(noAddressLatLng);
			}
		
	}
}
