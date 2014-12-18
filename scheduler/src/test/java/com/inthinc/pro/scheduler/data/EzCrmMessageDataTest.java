package com.inthinc.pro.scheduler.data;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.model.AlertMessageType;

public class EzCrmMessageDataTest {
    
    private List<String> myParams;
    
    @Before
    public void setupParams() {
        myParams = new ArrayList<String>();
        
        myParams.add("My Red Flag Alert");        //#0 {Red Flag Alert Name} - for subject line
        myParams.add("QA top-top-firmware");        //#1 {GROUP} - fmt: division-division-...-team
        myParams.add("0");        //#2 {CATEGORY} - 0|1|2 for now...One of: Critical|Normal|Information 
        myParams.add("2014-12-11 14:45:30");        //#3 {DATE} - fmt: yyyy-mm-dd hh:mm:ss
        myParams.add("4242");        //#4 {EMP ID} - fmt: external driver id | omitted if no driver
        myParams.add("123");        //#5 {DRIVER ID} - fmt: internal driver id | UNKNOWN if no driver - empty here
        myParams.add("John B Doe");        //#6 {DRIVER NAME} - fmt: First Middle Last | UNKNOWN - empty here
        myParams.add("MyTruck");        //#7 {VEHICLE NAME}
        myParams.add("8888");        //#8 {VEHICLE ID} - fmt: internal vehicle ID
        myParams.add("2009");        //#9 {YEAR} - fmt: yyyy | ommited if blank
        myParams.add("Ford");        //#10 {MAKE} - ommited if blank
        myParams.add("F350");        //#11 {MODEL} - ommited if blank
        myParams.add("45.12930");        //#12 {LAT} - latitude | NO GPS LOCK
        myParams.add("-103.2345");        //#13 {LON} - longitude | NO GPS LOCK
        myParams.add("123 main St., Rainbow, NY");        //#14 {ADDRESS} - address | UNKNOWN
        myParams.add("10555");        //#15 {ODOMETER} - fmt: NNNNN (KM | Mi)
        myParams.add("66");        //#16 {SPEED} - fmt: NN (KPH | MPH)
        myParams.add("0");        //#17 {MeasurementType}: 0 or 1
        myParams.add("66");        //#18 {Event Detail Param1}
        myParams.add("55");        //#19 {Event Detail Param2}
        System.out.println("setupParams()");
}
    
    private String runEzCrmTest(AlertMessageType type) {
        EzCrmMessageData ezCrm = new EzCrmMessageData(type, Locale.getDefault(), myParams);
        return ezCrm.getMessage();
    }
    
    @Test
    public void testEzCrmDataALERT_TYPE_SPEEDING() {
        System.out.print(AlertMessageType.ALERT_TYPE_SPEEDING.toString());
        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_SPEEDING);
        System.out.print(text);
        
        assertTrue(text.contains("SPEEDING"));
        assertTrue(text.contains("66 in 55 zone"));
    }
    
    @Test
    public void testEzCrmDataALERT_TYPE_HARD_BUMP() {
        System.out.print(AlertMessageType.ALERT_TYPE_HARD_BUMP.toString());
        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_HARD_BUMP);
        System.out.print(text);
        
        assertTrue(text.contains("HARD BUMP"));
        assertTrue(text.contains("Bump over severity threshold"));
    }

    @Test
    public void testEzCrmDataALERT_TYPE_HARD_TURN() {
        System.out.print(AlertMessageType.ALERT_TYPE_HARD_TURN.toString());
        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_HARD_TURN);
        System.out.print(text);
        
        assertTrue(text.contains("HARD TURN"));
        assertTrue(text.contains("Turn over severity threshold"));
    }
    
    @Test
    public void testEzCrmDataALERT_TYPE_HARD_BRAKE() {
        System.out.print(AlertMessageType.ALERT_TYPE_HARD_BRAKE.toString());
        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_HARD_BRAKE);
        System.out.print(text);
        
        assertTrue(text.contains("HARD BRAKE"));
        assertTrue(text.contains("Brake over severity threshold"));
    }
    
    //ALERT_TYPE_HARD_ACCEL
    @Test
    public void testEzCrmDataALERT_TYPE_HARD_ACCEL() {
        System.out.print(AlertMessageType.ALERT_TYPE_HARD_ACCEL.toString());
        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_HARD_ACCEL);
        System.out.print(text);
        
        assertTrue(text.contains("HARD ACCELERATION"));
        assertTrue(text.contains("Acceleration over severity threshold"));
    }
    
//    ALERT_TYPE_EXIT_ZONE
    @Test
    public void testEzCrmDataALERT_TYPE_EXIT_ZONE() {
        System.out.print(AlertMessageType.ALERT_TYPE_EXIT_ZONE.toString());

        myParams.remove(2);
        myParams.add(2, "2");
        myParams.remove(19);
        myParams.remove(18);
        myParams.add(18, "mySafe Zone");        //#18 {Event Detail Param1}
        myParams.add(19, "9090");               //#19 {Event Detail Param2}

        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_EXIT_ZONE);
        System.out.print(text);
        
        assertTrue(text.contains("SEVERITY: Information"));
        assertTrue(text.contains("ZONE EXIT"));
        assertTrue(text.contains("Departed mySafe Zone (9090)"));
    }

    //ALERT_TYPE_ENTER_ZONE
    @Test
    public void testEzCrmDataALERT_TYPE_ENTER_ZONE() {
        System.out.print(AlertMessageType.ALERT_TYPE_ENTER_ZONE.toString());

        myParams.remove(2);
        myParams.add(2, "1");
        myParams.remove(19);
        myParams.remove(18);
        myParams.add(18, "mySafe Zone");        //#18 {Event Detail Param1}
        myParams.add(19, "9090");               //#19 {Event Detail Param2}

        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_ENTER_ZONE);
        System.out.print(text);
        
        assertTrue(text.contains("SEVERITY: Normal"));
        assertTrue(text.contains("ZONE ENTER"));
        assertTrue(text.contains("Entered mySafe Zone (9090)"));
    }

    //ALERT_TYPE_SEATBELT
    @Test
    public void testEzCrmDataALERT_TYPE_SEATBELT() {
        System.out.print(AlertMessageType.ALERT_TYPE_SEATBELT.toString());

        myParams.remove(19);
        myParams.remove(18);

        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_SEATBELT);
        System.out.print(text);
        
        assertTrue(text.contains("SEATBELT"));
        assertTrue(text.contains("Driver failed to buckle the seatbelt"));
    }

    //ALERT_TYPE_CRASH
    @Test
    public void testEzCrmDataALERT_TYPE_CRASH() {
        System.out.print(AlertMessageType.ALERT_TYPE_CRASH.toString());

        myParams.remove(9);
        myParams.add(9, "");        //#9 {YEAR} - fmt: yyyy | ommited if blank

        myParams.remove(11);
        myParams.add(11,"");        //#11 {MODEL} - ommited if blank
        myParams.remove(12);
        myParams.add(12, "");        //#12 {LAT} - latitude | NO GPS LOCK
        myParams.remove(13);
        myParams.add(13,"");        //#13 {LON} - longitude | NO GPS LOCK
        myParams.remove(14);
        myParams.add(14,"");        //#14 {ADDRESS} - address | UNKNOWN

        myParams.remove(19);
        myParams.remove(18);

        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_CRASH);
        System.out.print(text);
        
        assertTrue(text.contains("| VEHICLE:  MyTruck (8888)  Ford "));
        assertTrue(text.contains("| EVENT TYPE: CRASH"));
        assertTrue(text.contains("| EVENT DETAIL: Possible crash or rollover"));
        assertTrue(text.contains("| LATITUDE: UNKNOWN"));
        assertTrue(text.contains("| LONGIDUTE: UNKNOWN"));
        assertTrue(text.contains("| ADDRESS: UNKNOWN"));
        assertTrue(text.contains("CRASH"));
        assertTrue(text.contains("Possible crash or rollover"));
    }

    //ALERT_TYPE_TAMPERING
    @Test
    public void testEzCrmDataALERT_TYPE_TAMPERING() {
        System.out.print(AlertMessageType.ALERT_TYPE_TAMPERING.toString());

        myParams.remove(17);
        myParams.add(17, "1");        //#17 {MeasurementType}: 0 or 1

        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_TAMPERING);
        System.out.print(text);
        
        assertTrue(text.contains("| ODOMETER: 10555 KM"));
        assertTrue(text.contains("| SPEED: 66 KPH"));
        assertTrue(text.contains("TAMPERING"));
        assertTrue(text.contains("Device unplugged from the vehicle power"));
    }

    //ALERT_TYPE_LOW_BATTERY
    @Test
    public void testEzCrmDataALERT_TYPE_LOW_BATTERY() {
        System.out.print(AlertMessageType.ALERT_TYPE_LOW_BATTERY.toString());

        myParams.remove(19);
        myParams.remove(18);

        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_LOW_BATTERY);
        System.out.print(text);
        
        assertTrue(text.contains("LOW BATTERY"));
        assertTrue(text.contains("The vehicle battery voltage is low"));
    }

    //ALERT_TYPE_NO_DRIVER
    @Test
    public void testEzCrmDataALERT_TYPE_NO_DRIVER() {
        System.out.print(AlertMessageType.ALERT_TYPE_NO_DRIVER.toString());

        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_NO_DRIVER);
        System.out.print(text);
        
        assertTrue(text.contains("NO DRIVER"));
        assertTrue(text.contains("The vehicle is moving with no driver logged in"));
    }
    
    //ALERT_TYPE_OFF_HOURS
    @Test
    public void testEzCrmDataALERT_TYPE_OFF_HOURS() {
        System.out.print(AlertMessageType.ALERT_TYPE_OFF_HOURS.toString());

        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_OFF_HOURS);
        System.out.print(text);
        
        assertTrue(text.contains("OFF HOURS"));
        assertTrue(text.contains("Driving during off hours"));
    }

    //ALERT_TYPE_TEXT_MESSAGE_RECEIVED
    @Test
    public void testEzCrmDataALERT_TYPE_TEXT_MESSAGE_RECEIVED() {
        System.out.print(AlertMessageType.ALERT_TYPE_TEXT_MESSAGE_RECEIVED.toString());

        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_TEXT_MESSAGE_RECEIVED);
        System.out.print(text);
        
        assertTrue(text.contains("TEXT MESSAGE RECEIVED"));
        assertTrue(text.contains("Text message received"));
    }

    //ALERT_TYPE_PARKING_BRAKE
    @Test
    public void testEzCrmDataALERT_TYPE_PARKING_BRAKE() {
        System.out.print(AlertMessageType.ALERT_TYPE_PARKING_BRAKE.toString());

        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_PARKING_BRAKE);
        System.out.print(text);
        
        assertTrue(text.contains("PARKING BRAKE"));
        assertTrue(text.contains("Driver did not set the parking brake"));
    }

    //ALERT_TYPE_PANIC
    @Test
    public void testEzCrmDataALERT_TYPE_PANIC() {
        System.out.print(AlertMessageType.ALERT_TYPE_PANIC.toString());

        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_PANIC);
        System.out.print(text);
        
        assertTrue(text.contains("PANIC"));
        assertTrue(text.contains("Driver pressed the panic button"));
    }

    //ALERT_TYPE_MAN_DOWN
    @Test
    public void testEzCrmDataALERT_TYPE_MAN_DOWN() {
        System.out.print(AlertMessageType.ALERT_TYPE_MAN_DOWN.toString());

        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_MAN_DOWN);
        System.out.print(text);
        
        assertTrue(text.contains("MAN DOWN"));
        assertTrue(text.contains("Man down alarm activated"));
    }

    //ALERT_TYPE_MAN_DOWN_OK
    @Test
    public void testEzCrmDataALERT_TYPE_MAN_DOWN_OK() {
        System.out.print(AlertMessageType.ALERT_TYPE_MAN_DOWN_OK.toString());

        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_MAN_DOWN_OK);
        System.out.print(text);
        
        assertTrue(text.contains("MAN DOWN OK"));
        assertTrue(text.contains("Man down alarm canceled"));
    }

    //ALERT_TYPE_IGNITION_ON
    @Test
    public void testEzCrmDataALERT_TYPE_IGNITION_ON() {
        System.out.print(AlertMessageType.ALERT_TYPE_IGNITION_ON.toString());

        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_IGNITION_ON);
        System.out.print(text);
        
        assertTrue(text.contains("IGNITION ON"));
        assertTrue(text.contains("Driver turned on the vehicle ignition"));
    }

    //ALERT_TYPE_HOS_DOT_STOPPED
    @Test
    public void testEzCrmDataALERT_TYPE_HOS_DOT_STOPPED() {
        System.out.print(AlertMessageType.ALERT_TYPE_HOS_DOT_STOPPED.toString());

        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_HOS_DOT_STOPPED);
        System.out.print(text);
        
        assertTrue(text.contains("HOS DOT STOPPED"));
        assertTrue(text.contains("Driver stopped by a DOT officer"));
    }

    //ALERT_TYPE_HOS_NO_HOURS_REMAINING
    @Test
    public void testEzCrmDataALERT_TYPE_HOS_NO_HOURS_REMAINING() {
        System.out.print(AlertMessageType.ALERT_TYPE_HOS_NO_HOURS_REMAINING.toString());

        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_HOS_NO_HOURS_REMAINING);
        System.out.print(text);
        
        assertTrue(text.contains("HOS NO HOURS REMAINING"));
        assertTrue(text.contains("Driver has no hours remaining"));
    }

    //ALERT_TYPE_WIRELINE_ALARM
    @Test
    public void testEzCrmDataALERT_TYPE_WIRELINE_ALARM() {
        System.out.print(AlertMessageType.ALERT_TYPE_WIRELINE_ALARM.toString());

        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_WIRELINE_ALARM);
        System.out.print(text);
        
        assertTrue(text.contains("EVENT TYPE: WIRELINE ALARM"));
        assertTrue(text.contains("Wireline Alarm activated"));
    }

    //ALERT_TYPE_DSS_MICROSLEEP
    @Test
    public void testEzCrmDataALERT_TYPE_DSS_MICROSLEEP() {
        System.out.print(AlertMessageType.ALERT_TYPE_DSS_MICROSLEEP.toString());

        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_DSS_MICROSLEEP);
        System.out.print(text);
        
        assertTrue(text.contains("EVENT TYPE: DSS MICROSLEEP"));
        assertTrue(text.contains("Driver in microsleep"));
    }

    //ALERT_TYPE_INSTALL
    @Test
    public void testEzCrmDataALERT_TYPE_INSTALL() {
        System.out.print(AlertMessageType.ALERT_TYPE_INSTALL.toString());

        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_INSTALL);
        System.out.print(text);
        
        assertTrue(text.contains("EVENT TYPE: INSTALL"));
        assertTrue(text.contains("New device installation completed"));
    }

    //ALERT_TYPE_FIRMWARE_CURRENT
    @Test
    public void testEzCrmDataALERT_TYPE_FIRMWARE_CURRENT() {
        System.out.print(AlertMessageType.ALERT_TYPE_FIRMWARE_CURRENT.toString());

        myParams.remove(19);
        myParams.remove(18);
        myParams.add(18, "12345");
        
        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_FIRMWARE_CURRENT);
        System.out.print(text);
        
        assertTrue(text.contains("EVENT TYPE: FIRMWARE CURRENT"));
        assertTrue(text.contains("EVENT DETAIL: Version: 12345"));
    }

    //ALERT_TYPE_LOCATION_DEBUG
    @Test
    public void testEzCrmDataALERT_TYPE_LOCATION_DEBUG() {
        System.out.print(AlertMessageType.ALERT_TYPE_LOCATION_DEBUG.toString());

        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_LOCATION_DEBUG);
        System.out.print(text);
        
        assertTrue(text.contains("EVENT TYPE: LOCATION DEBUG"));
        assertTrue(text.contains("The device responded to a manual location command"));
    }

    //ALERT_TYPE_QSI_UPDATED
    @Test
    public void testEzCrmDataALERT_TYPE_QSI_UPDATED() {
        System.out.print(AlertMessageType.ALERT_TYPE_QSI_UPDATED.toString());

        myParams.remove(19);
        myParams.remove(18);
        myParams.add(18, "2");
        
        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_QSI_UPDATED);
        System.out.print(text);
        
        assertTrue(text.contains("EVENT TYPE: QSI UPDATED"));
        assertTrue(text.contains("EVENT DETAIL: Status: Already Current"));
    }

    //ALERT_TYPE_WITNESS_UPDATED
    @Test
    public void testEzCrmDataALERT_TYPE_WITNESS_UPDATED() {
        System.out.print(AlertMessageType.ALERT_TYPE_WITNESS_UPDATED.toString());

        myParams.remove(19);
        myParams.remove(18);
        myParams.add(18, "45");
        
        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_WITNESS_UPDATED);
        System.out.print(text);
        
        assertTrue(text.contains("EVENT TYPE: WITNESS UPDATED"));
        assertTrue(text.contains("EVENT DETAIL: Version: 45"));
    }

    //ALERT_TYPE_ZONES_CURRENT
    @Test
    public void testEzCrmDataALERT_TYPE_ZONES_CURRENT() {
        System.out.print(AlertMessageType.ALERT_TYPE_ZONES_CURRENT.toString());

        myParams.remove(19);
        myParams.remove(18);
        myParams.add(18, "2");
        
        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_ZONES_CURRENT);
        System.out.print(text);
        
        assertTrue(text.contains("EVENT TYPE: ZONES CURRENT"));
        assertTrue(text.contains("EVENT DETAIL: Status: Already Current"));
    }

    //ALERT_TYPE_NO_INTERNAL_THUMB_DRIVE
    @Test
    public void testEzCrmDataALERT_TYPE_NO_INTERNAL_THUMB_DRIVE() {
        System.out.print(AlertMessageType.ALERT_TYPE_NO_INTERNAL_THUMB_DRIVE.toString());

        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_NO_INTERNAL_THUMB_DRIVE);
        System.out.print(text);
        
        assertTrue(text.contains("NO INTERNAL THUMBDRIVE"));
        assertTrue(text.contains("The device external storage could not be mounted"));
    }

    //ALERT_TYPE_WITNESS_HEARTBEAT_VIOLATION
    @Test
    public void testEzCrmDataALERT_TYPE_WITNESS_HEARTBEAT_VIOLATION() {
        System.out.print(AlertMessageType.ALERT_TYPE_WITNESS_HEARTBEAT_VIOLATION.toString());

        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_WITNESS_HEARTBEAT_VIOLATION);
        System.out.print(text);
        
        assertTrue(text.contains("WITNESS HEARTBEAT VIOLATION"));
        assertTrue(text.contains("No heart beat could be detected for the crash detector"));
    }

    //ALERT_TYPE_IDLING
    @Test
    public void testEzCrmDataALERT_TYPE_IDLING() {
        System.out.print(AlertMessageType.ALERT_TYPE_IDLING.toString());

        myParams.remove(19);
        myParams.remove(18);
        myParams.add(18, "25");
        
        String text = runEzCrmTest(AlertMessageType.ALERT_TYPE_IDLING);
        System.out.print(text);
        
        assertTrue(text.contains("EVENT TYPE: IDLING"));
        assertTrue(text.contains("EVENT DETAIL: Idle 25 minutes with the engine running"));
    }

}
