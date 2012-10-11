package com.inthinc.device.emulation;

import org.junit.Test;

import android.util.Log;

import com.inthinc.device.devices.WaysmartDevice;
import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.objects.TripDriver;
import com.inthinc.pro.automation.enums.AutoSilos;

public class DE7570 {
    
    @Test
    public void testMileage(){
//        int accountID = 398;
        int accountID = 1;
        String start = "4225 E Lake Park Blvd, WVC, UT";
        String stop = "40.572501,-111.801124";
        int addToDays = 11;
//        int odometer = 163217;
        int odometer = 0;
        
        
        WaysmartDevice waysmart = new WaysmartDevice("30023FKE1DE7570", "F1DE7570", AutoSilos.MY, Direction.sat);
//        WaysmartDevice waysmart = new WaysmartDevice("FAKETESTER", "NOPE", AutoSilos.QA, Direction.wifi);
        waysmart.firstLocation(new GeoPoint());
        waysmart.getState().setVehicleID("1DE7570").setAccountID(accountID).setEmployeeID("1DE7570").setOdometerX100(odometer);
        waysmart.getState().getTime().addToMinutes(1);
//        waysmart.getState().getTime()
//            .setDate(new AutomationCalendar(1331704800, WebDateFormat.NOTE_PRECISE_TIME))
//            .addToDay(addToDays); // Time is for 3/14/2012 midnight MDT
        createTrip(waysmart, start, stop);
        Log.info(waysmart.getOdometer() + " Trip for number 1");
        
        
//        waysmart = new WaysmartDevice("30023FKE2DE7570", "F2DE7570", AutoSilos.QA, Direction.sat);
//        waysmart.firstLocation(new GeoPoint());
//        waysmart.getState().setVehicleID("2DE7570").setAccountID(accountID).setEmployeeID("2DE7570").setOdometerX100(odometer);
//        waysmart.getState().getTime()
//            .setDate(new AutomationCalendar(1331683200, WebDateFormat.NOTE_PRECISE_TIME))
//            .addToDay(addToDays); // Time is for 3/14/2012 midnight GMT
//        createTrip(waysmart, start, stop);
//        MasterTest.print(waysmart.getOdometer() + " Trip for number 2");
    }
    
    public void createTrip(WaysmartDevice waysmart, String start, String stop){
        int trips = 3;
        TripDriver driver = new TripDriver(waysmart);
        for (int i=0; i<trips;i++){
            if (i % 2 == 0){
                driver.addToTrip(start, stop);
            } else {
                driver.addToTrip(stop, start);
            }
            driver.start();
            waysmart.getState().getTime().addToHours(2);
        }
        waysmart.flushNotes();
    }

}
