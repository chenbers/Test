package com.inthinc.pro.backing;

import java.util.List;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.ZoneHessianDAO;
import com.inthinc.pro.dao.mock.proserver.SiloServiceCreator;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.NoAddressFoundException;
import com.inthinc.pro.model.Zone;

public class AddressLookupTest extends BaseBeanTest{
	@Ignore
	@Test
	public void addressLookupTest(){
		
		AddressLookup addressLookup = (AddressLookup)applicationContext.getBean("addressLookupBean");

		try{
		
			String address = addressLookup.getAddress(new LatLng(40.618057,-111.787941));
			Assert.assertEquals("3861 Prospector Dr, Cottonwood Heights, UT 84121".toUpperCase(), address.toUpperCase());
			
		}
		catch(NoAddressFoundException nafe){
			
			fail("No Address Found - "+nafe.getMessage());
		}
	}

    @Test
    @Ignore
    public void addressLookupZoneTest(){
        ZoneHessianDAO zoneHessianDAO = new ZoneHessianDAO();
        zoneHessianDAO.setSiloService(new SiloServiceCreator().getService());
        List<Zone> zones = zoneHessianDAO.getZones(1);

        AddressLookup addressLookup = (AddressLookup)applicationContext.getBean("addressLookupBean");

        String address = addressLookup.getAddressOrZoneOrLatLng(new LatLng(40.618057,-111.787941),zones);
        Assert.assertTrue("Has not returned a zone name", address.toUpperCase().contains("ZONE"));
            
    }
	@Test
	public void googleAddressLookupTest(){
		
		AddressLookup googleAddressLookup = (AddressLookup)applicationContext.getBean("googleAddressLookupBean");
		
		try{
			
				String address = googleAddressLookup.getAddress(new LatLng(40.618057,-111.787941));
				System.out.println(address);
				Assert.assertNotNull("Address was null", address);
				
			}
			catch(NoAddressFoundException nafe){
	            fail("No Address Found - "+nafe.getMessage());
			}
			String noAddressNoLatLng = null;
			try{
				
				noAddressNoLatLng = googleAddressLookup.getAddress(new LatLng(100,200));
				Assert.assertNotNull("Address was null", noAddressNoLatLng);
				
			}
			catch(NoAddressFoundException nafe){
				
				Assert.assertNull(noAddressNoLatLng);
			}
			String noAddressLatLng = null;
			try{
				
				noAddressLatLng = googleAddressLookup.getAddress(new LatLng(100,200));
				Assert.assertNotNull("Address was null", noAddressLatLng);
				
			}
			catch(NoAddressFoundException nafe){
				
				Assert.assertNull(noAddressLatLng);
			}
			try{
				
				noAddressLatLng = googleAddressLookup.getAddress(new LatLng(0.000005,0.00000));
				Assert.assertNotNull("Address was null", noAddressLatLng);
				
			}
			catch(NoAddressFoundException nafe){
				
				Assert.assertNull(noAddressLatLng);
			}
		
	}
}
