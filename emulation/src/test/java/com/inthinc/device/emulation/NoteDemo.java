package com.inthinc.device.emulation;

import java.io.IOException;
import java.util.ArrayList;
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

public class NoteDemo {
	private MCMProxyObject proxy;

	private int speed = 60;
	private int maprev = 1;
	private int sats = 15;
	private int odometer = 3;
	

	public NoteDemo(AutoSilos server) {
		proxy = new MCMProxyObject(server);
	}

	public void tiwiNoteBasic(String imei) {

		TiwiNote note = new TiwiNote(DeviceNoteTypes.LOCATION,
				new AutomationCalendar(), new GeoPoint(), Heading.NORTH, sats,
				maprev, speed, odometer);

		note.addAttr(EventAttr.SPEED_LIMIT, 70); // Should be present on all
													// notes for a tiwi.
		List<TiwiNote> notes = new ArrayList<TiwiNote>();
		notes.add(note);
		proxy.tiwiNote(imei, notes);
	}
	
	public void tiwiNoteAdvanced(String imei){
		DeviceState state = new DeviceState(imei, ProductType.TIWIPRO_R74);
		
		state.setSpeed(60).setOdometerX100(3).setSpeedLimit(70);
		
		DeviceNote note = AutomationDeviceEvents.location(state, new GeoPoint()).getNote();
		
		proxy.sendNotes(state, note);
	}
	
	

	public void waySNoteBasicType3(String mcmID, String imei) {
		SatelliteEvent_t note = new SatelliteEvent_t(DeviceNoteTypes.LOCATION,
				new AutomationCalendar(), new GeoPoint(), false, false,
				HOSFlags.DRIVING, false, false, false, Heading.NORTH, 15, 60,
				65, 0, 0, 47, 0);

		note.addAttr(EventAttr.NO_GPS_LOCK_LOCATION, "West Valley, UT");

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

	
	public void waySNoteBasicType2(String mcmID, String imei) {
		SatelliteEvent note = new SatelliteEvent(DeviceNoteTypes.LOCATION, // The Attributes required are 
																		   // located in the enum DeviceNoteTypes
																		   // for each note type
				new AutomationCalendar(), new GeoPoint(), Heading.NORTH, 15,
				60, 0);


		List<SatelliteEvent> notes = new ArrayList<SatelliteEvent>();
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

	
	public void waySNoteSat(String imei){
		SatelliteEvent_t note = new SatelliteEvent_t(DeviceNoteTypes.LOCATION,
				new AutomationCalendar(), new GeoPoint(), false, false,
				HOSFlags.DRIVING, false, false, false, Heading.NORTH, 15, 60,
				65, 0, 0, 47, 0);

		note.addAttr(EventAttr.NO_GPS_LOCK_LOCATION, "West Valley, UT");

		List<SatelliteEvent_t> notes = new ArrayList<SatelliteEvent_t>();
		notes.add(note);
		proxy.sendSatNote(imei, notes);
	}
	
	
	public static void main(String[] args){
		String imei = "999456789012345";
		String mcmID = "";
		
		NoteDemo demo = new NoteDemo(AutoSilos.QA);
		demo.tiwiNoteBasic("483548625738283");
		demo.tiwiNoteAdvanced("483548625738283");
		
		demo.waySNoteBasicType2(mcmID, imei);
		demo.waySNoteBasicType3(mcmID, imei);
		
		demo.waySNoteSat(imei);
	}
}
