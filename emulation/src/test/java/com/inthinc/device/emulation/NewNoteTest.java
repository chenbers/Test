package com.inthinc.device.emulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.emulation.enums.DeviceEnums.HOSFlags;
import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.device.emulation.enums.EventAttr;
import com.inthinc.device.emulation.notes.DeviceNote;
import com.inthinc.device.emulation.notes.SatelliteEvent;
import com.inthinc.device.emulation.notes.SatelliteEvent_t;
import com.inthinc.device.emulation.notes.TiwiNote;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.device.emulation.utils.MCMProxyObject;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.pro.automation.enums.AutoSilos;
import com.inthinc.pro.automation.enums.ProductType;
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

	

	public void testHazardNote(String mcmID, String imei) {
		SatelliteEvent_t note = new SatelliteEvent_t(DeviceNoteTypes.CREATE_ROAD_HAZARD,
				new AutomationCalendar(), new GeoPoint(), false, false,
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
			proxy.sendHttpNote(mcmID, Direction.wifi, notes, imei);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args){
		String imei = "30099FKEWS99999";
		String mcmID = "MCMFAKEWS";
		
		NewNoteTest test = new NewNoteTest(AutoSilos.QA);
		
		test.testHazardNote(mcmID, imei);
	}
}
