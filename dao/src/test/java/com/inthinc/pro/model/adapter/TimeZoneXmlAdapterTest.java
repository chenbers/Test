package com.inthinc.pro.model.adapter;

import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import junit.framework.Assert;

import org.junit.Test;

public class TimeZoneXmlAdapterTest {

	@Test
	public void testTimeZoneXMLMarshall(){
		
		TimeZoneXmlAdapter tzxmla = new TimeZoneXmlAdapter();
		
		try {
			
			String[] timeZones = TimeZone.getAvailableIDs();
			List<String> timeZoneList = Arrays.asList(timeZones);
			
			for(String tzId: timeZoneList){
				
				TimeZone tz = TimeZone.getTimeZone(tzId);
				String tzString = tzxmla.marshal(TimeZone.getTimeZone(tzId));
				Assert.assertEquals(tzId,tzString);
			}
		}
		catch (Exception e){
			
		}
		String tzidNull = null;
		try{
			
			//try null as the id
			tzidNull = tzxmla.marshal(null);
			//should throw a NullPointerException
		}
		catch (Exception e){
			
			Assert.assertTrue("null should throw a NullPointerException", e instanceof NullPointerException);
		}
	}
	@Test
	public void testTimeZoneXMLUnmarshall(){

		TimeZoneXmlAdapter tzxmla = new TimeZoneXmlAdapter();
		
		try {
			
			String[] timeZones = TimeZone.getAvailableIDs();
			List<String> timeZoneList = Arrays.asList(timeZones);
			
			for(String tzId: timeZoneList){
				
				TimeZone tzum = tzxmla.unmarshal(tzId);
				TimeZone tz = TimeZone.getTimeZone(tzId);
				Assert.assertEquals(tz,tzum);
			}
		}
		catch (Exception e){
		}
		TimeZone tzumBad = null;
		try{
			//try other values - should return GMT
			tzumBad = tzxmla.unmarshal("random obviously not a time zone string");
			Assert.assertEquals(TimeZone.getTimeZone("GMT"), tzumBad);
		}
		catch (Exception e){
			
			Assert.assertNull("Could not get TimeZone", tzumBad);
		}
		TimeZone tzumNull = null;
		try{
			
			//try null as the id
			tzumNull = tzxmla.unmarshal(null);
			//should throw a NullPointerException
		}
		catch (Exception e){
			
			Assert.assertTrue("null should throw a NullPointerException", e instanceof NullPointerException);
		}

	}
}
