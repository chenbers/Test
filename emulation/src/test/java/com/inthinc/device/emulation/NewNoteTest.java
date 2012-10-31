package com.inthinc.device.emulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.emulation.enums.DeviceEnums.HOSFlags;
import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.device.emulation.enums.EventAttr;
import com.inthinc.device.emulation.notes.SatelliteEvent_t;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.device.emulation.utils.MCMProxyObject;
import com.inthinc.pro.automation.enums.AutoSilos;
import com.inthinc.pro.automation.models.LatLng;
import com.inthinc.pro.automation.objects.AutomationCalendar;

public class NewNoteTest {
	private MCMProxyObject proxy;

	private int speed = 60;
	private int maprev = 1;
	private int sats = 15;
	private int odometer = 3;
	

	public NewNoteTest(AutoSilos server) {
		proxy = new MCMProxyObject(server);
	}

	public void testHazardNote(String mcmID, String imei){
	    testHazardNote(mcmID, imei, 40.71015,-111.993438);
	}
	
	public void testHazardNote(String mcmID, String imei, LatLng latLng){
	    testHazardNote(mcmID, imei, latLng.getLat(), latLng.getLng());
	}

	public void testHazardNote(String mcmID, String imei, Double lat, Double lng) {
		SatelliteEvent_t note = new SatelliteEvent_t(DeviceNoteTypes.CREATE_ROAD_HAZARD,
				new AutomationCalendar(), new GeoPoint(lat,lng), false, false,
				HOSFlags.DRIVING, false, false, false, Heading.NORTH, 15, 60,
				65, 0, 0, 47, 0);

        note.addAttr(EventAttr.DRIVER_ID_STR, "71572");
        note.addAttr(EventAttr.RHA_TYPE, 1);
        note.addAttr(EventAttr.RHA_RADIUS_METERS, 10);
        Date currentTime = new Date();
        note.addAttr(EventAttr.RHA_ENDTIME, currentTime.getTime()/1000 + 86400); //One day later
		note.addAttr(EventAttr.RHA_DESCRIPTION, "Test Description");

		List<SatelliteEvent_t> notes = new ArrayList<SatelliteEvent_t>();
		notes.add(note);
		try {
			String[] response = proxy.sendHttpNote(mcmID, Direction.wifi, notes, imei);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public void testDVIRNote(String mcmID, String imei, int inspectionType, int vehicleSafeToOp) {
        SatelliteEvent_t note = new SatelliteEvent_t(DeviceNoteTypes.HOS_CHANGE_STATE_NO_GPS_LOCK,
                new AutomationCalendar(), new GeoPoint(), false, false,
                HOSFlags.DRIVING, false, false, false, Heading.NORTH, 15, 60,
                65, 0, 0, 47, 0);

        note.addAttr(EventAttr.DRIVER_HOS_STATE, 1); 
        note.addAttr(EventAttr.CLEAR_DRIVER_FLAG, 0);         
        note.addAttr(EventAttr.DRIVER_ID_STR, "71572");
        note.addAttr(EventAttr.NO_GPS_LOCK_LOCATION, "test location");
        note.addAttr(EventAttr.INSPECTION_TYPE, inspectionType);
        note.addAttr(EventAttr.VEHICLE_SAFE_TO_OPERATE, vehicleSafeToOp);
        
        

        List<SatelliteEvent_t> notes = new ArrayList<SatelliteEvent_t>();
        notes.add(note);
        try {
            proxy.sendHttpNote(mcmID, Direction.wifi, notes, imei);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void testNote(String mcmID, String imei, DeviceNoteTypes type) {
        SatelliteEvent_t note = new SatelliteEvent_t(type,
                new AutomationCalendar(), new GeoPoint(), false, false,
                HOSFlags.DRIVING, false, false, false, Heading.NORTH, 15, 60,
                65, 0, 0, 47, 0);

        List<SatelliteEvent_t> notes = new ArrayList<SatelliteEvent_t>();
        notes.add(note);
        try {
            proxy.sendHttpNote(mcmID, Direction.wifi, notes, imei);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void testSeatbeltClicks(String mcmID, String imei) {
        SatelliteEvent_t note = new SatelliteEvent_t(DeviceNoteTypes.IGNITION_OFF,
                new AutomationCalendar(), new GeoPoint(), false, false,
                HOSFlags.ON_DUTY_NOT_DRIVING, false, false, false, Heading.NORTH, 15, 60,
                65, 0, 0, 47, 0);

        note.addAttr(EventAttr.SEATBELT_CLICKS, 1); 

        List<SatelliteEvent_t> notes = new ArrayList<SatelliteEvent_t>();
        notes.add(note);
        try {
            proxy.sendHttpNote(mcmID, Direction.wifi, notes, imei);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException{
        String imeiOnDev = "30023FKEWS00001";
        String mcmIDOnDev = "FKE00001";

        String imeiOnQA = "30099FKEWS99999";
        String mcmIDOnQA = "MCMFAKEWS";

/*
        Map<String, LatLng> hazardLocations = new HashMap<String, LatLng>();
        hazardLocations.put("inthinc", new LatLng(40.7106, -111.9945));
        hazardLocations.put("bangerter_21st", new LatLng(40.7257, -111.9863));
        hazardLocations.put("spagetti", new LatLng(40.721, -111.9046));
        hazardLocations.put("summitPark", new LatLng(40.7525, -111.613));
*/
/*        NewNoteTest test = new NewNoteTest(AutoSilos.DEV);
        String imei = imeiOnDev;
        String mcmID = mcmIDOnDev;
*/        
        
        NewNoteTest test = new NewNoteTest(AutoSilos.QA);
        String imei = imeiOnQA;
        String mcmID = mcmIDOnQA;
        
        
/*      test.testHazardNote(mcmID, imei);
        Thread.sleep(2*1000);
        test.testHazardNote(mcmID, imei, hazardLocations.get("bangerter_21st"));
        Thread.sleep(2*1000);
        test.testHazardNote(mcmID, imei, hazardLocations.get("spagetti"));
        Thread.sleep(2*1000);
        test.testHazardNote(mcmID, imei, hazardLocations.get("summitPark"));
        Thread.sleep(2*1000);
*/        
        
		test.testDVIRNote(mcmID, imei, 1, 0);
        Thread.sleep(2*1000);
        test.testDVIRNote(mcmID, imei, 1, 1);
        Thread.sleep(2*1000);
        test.testDVIRNote(mcmID, imei, 2, 0);
        Thread.sleep(2*1000);
  
/*        test.testNote(mcmID, imei, DeviceNoteTypes.DVIR_DRIVEN_UNSAFE);
        Thread.sleep(2*1000);
        test.testNote(mcmID, imei, DeviceNoteTypes.DVIR_DRIVEN_NOPREINSPEC);
        Thread.sleep(2*1000);
        test.testNote(mcmID, imei, DeviceNoteTypes.DVIR_DRIVEN_NOPOSTINSPEC);
*/        
//      test.testSeatbeltClicks(mcmID, imei);
    }
}
