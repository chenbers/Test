package com.inthinc.pro.map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.util.GeoUtil;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.NoAddressFoundException;
import com.inthinc.pro.model.NoAddressFoundException.reasons;

public class TestGoogleAddressLookupTest {
    
    GoogleAddressLookup gal; 
    @Before
    public void setUp() throws Exception
    {
        gal = new GoogleAddressLookup();
    }
    @Test
    public final void getClosestTown_impossibleLatLng_returnNoAddressFound() {
        String address;
        try {
            address = gal.getClosestTownString(new LatLng(0, 0d),MeasurementType.ENGLISH, true); // Ocean off the Gulf of Guniea
            System.out.println("address: "+address);
        } catch (NoAddressFoundException e) {
            return;
        }
        fail("Expected NoAddressFoundException: returned: "+address);
    }
    @Test
    public final void getClosestTown_nullLatLng_returnNoAddressFound() {
        String address;
        try {
            address = gal.getClosestTownString(null, MeasurementType.ENGLISH);
            System.out.println("address: "+address);
        } catch (NoAddressFoundException e) {
            return;
        }
        fail("Expected NoAddressFoundException; returned: "+address);
    }
    
    @Test
    public final void getClosestTown_validLatLngNoCity_returnNoAddressFound() {
        String address = null;
        try {
            address = gal.getClosestTownString(new LatLng(37.450877,-132.269897), MeasurementType.ENGLISH);
            System.out.println("address: "+address);
        } catch (NoAddressFoundException e1) {
            //given latlng is in the ocean, no address should be found.. i.e. if it gets here the test has passed
            return;
        }
        fail("Expected NoAddressFoundException; returned: "+address);
    }
    @Test
    public final void getClosestTown_validLatLng_returnTownAndDistance() {
        HashMap<LatLng, String> testData = new HashMap<LatLng, String>();

        testData.put(new LatLng(31.5365, -96.1444), "7.1 mi N of Buffalo, TX");
        //this was the original data used to gauge acceptance
//        testData.put(new LatLng(42.448, -109.73), "20.6 mi SE of Marbleton, WY"); // just shows WY
//        testData.put(new LatLng(35.27, -119.04), "6 mi S of Bakersfield, CA"); 
//        testData.put(new LatLng(35.4411, -92.5351), "8.7 mi NW of Damascus, AR"); // we show Center Ridge, AR which is closer
//        testData.put(new LatLng(35.4406, -92.534), "8.6 mi NW of Damascus, AR");// we show Center Ridge, AR which is closer
//        testData.put(new LatLng(31.9146, -102.22), "9.2 mi E of Odessa, TX");
//        testData.put(new LatLng(37.1548, -107.833), "8.8 mi S of Durango, CO");
//        testData.put(new LatLng(41.6307, -109.246), "North Rock Springs, WY");
//        testData.put(new LatLng(31.9762, -102.104), "Midland, TX");
//        testData.put(new LatLng(34.9981, -95.5812), "9.2 mi NE of Alderson, OK");  // we show 11.57 miles ne of McAlester, OK which is a larger town - Alderson, OK look
//        testData.put(new LatLng(27.9349, -99.56),"  14.2 mi NW of Botines, TX"); // we show 29.78 miles NW of Laredo, TX
//        testData.put(new LatLng(42.448,  -109.73)," 20.6 mi SE of Marbleton, WY");  // we just show WY, kind of surprised it didn't at least find Pinedale 
//        testData.put(new LatLng(32.4201, -103.778),"20.8 mi NE of Loving, NM");  // ours had 26.29 E of Carlsbad,NM which seemed a better choice
//        testData.put(new LatLng(70.3272, -149.267),"12.9 mi W of Prudhoe Bay, AK");
//        testData.put(new LatLng(41.6295, -109.239),"North Rock Springs, WY");
//        testData.put(new LatLng(31.066,  -96.4955),"Franklin, TX");
//        testData.put(new LatLng(42.1842, -76.821),"Horseheads North, NY");
//        testData.put(new LatLng(42.6841, -109.816),"6.8 mi W of Boulder, WY");
//        testData.put(new LatLng(35.1639, -92.6402),"Plumerville, AR");
//        testData.put(new LatLng(41.6304, -109.248),"North Rock Springs, WY");
//        testData.put(new LatLng(31.4505, -102.016),"16.2 mi N of Rankin, TX");
//        testData.put(new LatLng(31.978,  -102.105),"Midland, TX");
//        testData.put(new LatLng(70.2296, -148.38),"10.2 mi SE of Prudhoe Bay, AK");
//        testData.put(new LatLng(35.9587, -96.1811),"Kellyville, OK");
//        testData.put(new LatLng(32.4383, -101.056),"6.2 mi N of Westbrook, TX");
//        testData.put(new LatLng(32.6961, -103.179),"Hobbs, NM");
//        testData.put(new LatLng(41.72,   -76.0519),"7.3 mi N of Meshoppen borough, PA");
//        testData.put(new LatLng(31.9762, -102.104),"Midland, TX");
//        testData.put(new LatLng(35.4329, -119.065),"Oildale, CA");  // we show Bakersfield -- Oildale is a Census designated place, so maybe not a town?
//        testData.put(new LatLng(55.0522, -117.282),"Valleyview, AB");
//        testData.put(new LatLng(34.7392, -96.1024),"Ashland, OK");
//        testData.put(new LatLng(32.4128, -97.2273),"Alvarado, TX");  
//        testData.put(new LatLng(35.2357, -119.423),"Valley Acres, CA");  // we show 6.71 N of Taft, CA
//        testData.put(new LatLng(35.5799, -92.1452),"Greers Ferry, AR");
//        testData.put(new LatLng(53.8875, -119.112),"Grande Cache, AB");
//        testData.put(new LatLng(70.2292, -148.382),"10.1 mi SE of Prudhoe Bay, AK");
//        testData.put(new LatLng(54.2253, -115.784),"10.5 km NW of Whitecourt, AB"); // we just return AB

        String address = null;
        boolean debugMode = false;
        try {
            for (LatLng latLng : testData.keySet()) {
                address = gal.getClosestTownString(latLng, MeasurementType.ENGLISH, debugMode);
                if(debugMode) {
                  System.out.println("expected " + testData.get(latLng) + ";  returned " + address);
                  System.out.println();
                }

                // if you run the entire list above, add this code back in to avoid the too many requests issues with google
//                try {
//                    Thread.sleep(3000l);
//                } catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
                 
            }
        } catch (NoAddressFoundException e) {
            e.printStackTrace();
            fail("exception thrown for getClosestTownString");
        }
    }
    
    @Test
    public final void getAddressLatLngBoolean_validLatLngReturnAddress_returnValidAddress() {
        LatLng latLng = new LatLng(32.0429, -102.085);
        String address = null;
        try {
            address = gal.getAddress(latLng);
        } catch (NoAddressFoundException e) {
            fail();
        }
        assertTrue("The given LatLng should reside in 79705", address.contains("79705"));
    }
    @Test
    public final void getAddressLatLngBoolean_invalidLatLng() {
        LatLng latLng = new LatLng(0.000005,0.000000);
        String address = null;
        try {
            address = gal.getAddress(latLng);
            fail();
        } catch (NoAddressFoundException e) {
            assertTrue(reasons.INVALID_LATLNG.equals(e.getReason()));
        }
    }
    
    
    @Test
    public final void clientSideLink() {
        GoogleClientSideAddressLookup googleClientSideAddressLookup = new GoogleClientSideAddressLookup();
        LatLng latLng = new LatLng(32.0429, -102.085);
        try {
            String link = googleClientSideAddressLookup.getAddress(latLng);
            System.out.println("link : " + link);
        } catch (NoAddressFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    @Test
    public final void getLatLngForAddress() {
        HashMap<LatLng, String> testData = new HashMap<LatLng, String>();

        testData.put(new LatLng(32.4128, -97.2273),"Alvarado, TX");  
        testData.put(new LatLng(35.5799, -92.1452),"Greers Ferry, AR");
        testData.put(new LatLng(53.8875, -119.112),"Grande Cache, AB");
        testData.put(new LatLng(45.193, -109.248), "602 N Hauser, Red Lodge, MT");
        String address = null;
        boolean debugMode = true;
        try {
            for (LatLng expectedLatLng : testData.keySet()) {
                address = testData.get(expectedLatLng);
                LatLng latLng = gal.lookupLatLngForAddress(address);
                
                float miles = GeoUtil.distBetween(expectedLatLng, latLng, MeasurementType.ENGLISH);
                assertTrue("expected distance to be less than 3 miles for " + address + " ("+ miles + ")", (miles < 2.0));

                if(debugMode) {
                  System.out.println("address " + testData.get(expectedLatLng) + " expected " + expectedLatLng + ";  returned " + latLng + " distance between: " + miles);
                  
                  System.out.println();
                }

                // if you add to the list above, add this code back in to avoid the too many requests issues with google
//                try {
//                    Thread.sleep(3000l);
//                } catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
                 
            }
        } catch (NoAddressFoundException e) {
            e.printStackTrace();
            fail("exception thrown for getClosestTownString");
        }
    }
}
