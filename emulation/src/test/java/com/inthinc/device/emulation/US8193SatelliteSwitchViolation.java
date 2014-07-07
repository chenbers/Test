package com.inthinc.device.emulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.inthinc.device.devices.WaysmartDevice;
import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.pro.automation.enums.AutoSilos;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.device.emulation.utils.MCMProxyObject;
import com.inthinc.device.emulation.notes.SatelliteEvent_t;
import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.device.emulation.enums.EventAttr;


import org.apache.http.client.ClientProtocolException;

/**
 * Emulates some things for  US8193 Satellite Switch Violation.
 */
public class US8193SatelliteSwitchViolation {

    private MCMProxyObject proxy;

    public US8193SatelliteSwitchViolation(AutoSilos server) {
        proxy = new MCMProxyObject(server);
    }


    public void testNewDriver(String mcmID, String imei) {

        WaysmartDevice tiwi = new WaysmartDevice(mcmID, imei, AutoSilos.QA, Direction.gprs);
        tiwi.dump_settings();
        DeviceState state = tiwi.getState();
        state.setDriverID(78619);
        state.setVehicleID("59025");
        state.getTime().setDate(1401300600000L);
        SatelliteEvent_t note = new SatelliteEvent_t(DeviceNoteTypes.SATELLITE_SWITCH,
          //      state, new GeoPoint());
        state, (new GeoPoint(40.6679, -111.9525)));

        note.addAttr(EventAttr.RF_OFF_DISTANCE, 1);

        List<SatelliteEvent_t> notes = new ArrayList<SatelliteEvent_t>();
        notes.add(note);
        try {
            proxy.sendHttpNote(mcmID, Direction.gprs, notes, imei);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException{

        String imeiOnQA = "911596000068449";
        String mcmIDOnQA = "FAKE8449";

        US8193SatelliteSwitchViolation test = new US8193SatelliteSwitchViolation(AutoSilos.QA);

        test.testNewDriver(mcmIDOnQA, imeiOnQA);
    }
}
