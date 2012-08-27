package com.inthinc.pro.selenium.testSuites;

import org.junit.Ignore;

import com.inthinc.device.devices.TiwiProDevice;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.pro.automation.enums.AutoSilos;

@Ignore
public class NoteTesterGeneration {
	
	public static void main(String args[]) {
		
        TiwiProDevice testVehicleOne = new TiwiProDevice("999999000109741", AutoSilos.QA);
        TiwiProDevice tiwi01 = new TiwiProDevice("011596000038897", AutoSilos.QA);
        TiwiProDevice tiwi02 = new TiwiProDevice("011596000038897", AutoSilos.QA);
        TiwiProDevice tiwi03 = new TiwiProDevice("011596000038897", AutoSilos.QA);
        TiwiProDevice tiwi04 = new TiwiProDevice("011596000038897", AutoSilos.QA);
		
		//WaysmartDevice tiwi = new WaysmartDevice("300235555777777", "MCM013795", AutoSilos.PROD, Direction.gprs);
        DeviceState state = testVehicleOne.getState();
        testVehicleOne.increment_time(60);
        testVehicleOne.getState().setWMP(17116);
        testVehicleOne.firstLocation(new GeoPoint(33.0104, -117.111));
        testVehicleOne.power_on_device();
        testVehicleOne.turn_key_on(15);
        testVehicleOne.getState().setLowIdle(300).setHighIdle(300);
        //AutomationDeviceEvents.idling(tiwi);
        testVehicleOne.getState().setSpeedLimit(10);
        
        //tiwi.increment_time(20);
        //AutomationDeviceEvents.seatbelt(tiwi);
        testVehicleOne.update_location(new GeoPoint(33.0104, -117.111), 15);
        testVehicleOne.update_location(new GeoPoint(33.0104, -117.113), 15);
        
        state.setSeatbeltViolationDistanceX100(500);
        
        testVehicleOne.increment_time(20);
        //AutomationDeviceEvents.seatbelt(tiwi);
        AutomationDeviceEvents.hardLeft(testVehicleOne, 105);
        testVehicleOne.getState().setTopSpeed(70).setSpeedingDistanceX100(100).setSpeedingSpeedLimit(65);
        AutomationDeviceEvents.speeding(testVehicleOne);
        testVehicleOne.getState().setTopSpeed(100).setSpeedingDistanceX100(6000).setSpeedingSpeedLimit(65);
        AutomationDeviceEvents.speeding(testVehicleOne);

        testVehicleOne.update_location(new GeoPoint(33.01, -117.113), 15);
        testVehicleOne.update_location(new GeoPoint(33.0097, -117.1153), 15);
        testVehicleOne.update_location(new GeoPoint(33.015, -117.116), 15);
        
        testVehicleOne.increment_time(20);
        //AutomationDeviceEvents.seatbelt(tiwi);
        testVehicleOne.getState().setSpeedLimit(100);
        //tiwi.enter_zone(2);
        
        testVehicleOne.update_location(new GeoPoint(33.0163, -117.1159), 15);
        testVehicleOne.update_location(new GeoPoint(33.018, -117.1153), 15);
        testVehicleOne.update_location(new GeoPoint(33.0188, -117.118), 15);
        testVehicleOne.update_location(new GeoPoint(33.0192, -117.1199), 15);
        
        AutomationDeviceEvents.rfKill(testVehicleOne);
        
        testVehicleOne.update_location(new GeoPoint(33.021, -117.119), 15);
        testVehicleOne.update_location(new GeoPoint(33.022, -117.114), 15);
        testVehicleOne.update_location(new GeoPoint(33.0205, -117.111), 15);
        
        testVehicleOne.increment_time(20);
        //AutomationDeviceEvents.seatbelt(tiwi);
        //tiwi.tampering(4);
        
        testVehicleOne.update_location(new GeoPoint(33.02, -117.109), 15);
        testVehicleOne.update_location(new GeoPoint(33.02, -117.108), 15);
        testVehicleOne.update_location(new GeoPoint(33.022, -117.104), 15);
        testVehicleOne.update_location(new GeoPoint(33.0217, -117.103), 15);
        testVehicleOne.update_location(new GeoPoint(33.0213, -117.1015), 15);
        testVehicleOne.update_location(new GeoPoint(33.0185, -117.1019), 15);
        testVehicleOne.update_location(new GeoPoint(33.017, -117.102), 15);
        testVehicleOne.update_location(new GeoPoint(33.015, -117.1032), 15);
        testVehicleOne.update_location(new GeoPoint(33.013, -117.105), 15);
        testVehicleOne.update_location(new GeoPoint(33.011, -117.106), 15);
        testVehicleOne.update_location(new GeoPoint(33.0108, -117.108), 15);
        testVehicleOne.update_location(new GeoPoint(33.0108, -117.109), 15);

        //tiwi.leave_zone(2);

        testVehicleOne.increment_time(20);
        //AutomationDeviceEvents.seatbelt(tiwi);
        testVehicleOne.update_location(new GeoPoint(33.0106, -117.11), 15);
        testVehicleOne.last_location(new GeoPoint(33.0104, -117.111), 15);
        
        AutomationDeviceEvents.statistics(testVehicleOne);
        //tiwi.logout_driver(null, 890, 204, 200);
        
        
        
        //note.addAttr(EventAttr.SEATBELT_TOP_SPEED, state.getSeatbeltTopSpeed());
    	//note.addAttr(EventAttr.SEATBELT_OUT_DISTANCE, state.getSeatbeltDistanceX100());
        
    	testVehicleOne.getState().setSeatBeltTopSpeed(75).setSeatbeltDistanceX100(58);
    	
        testVehicleOne.turn_key_off(30);
        AutomationDeviceEvents.lowBattery(testVehicleOne);
        testVehicleOne.power_off_device(900);
    }
	
	
//    private TiwiProDevice tiwi1;
//    private TiwiProDevice tiwi2;
//    private TiwiProDevice tiwi3;
//    private TiwiProDevice tiwi4;
//    
//    private String IMEI1;
//    private String IMEI2;
//    private String IMEI3;
//    private String IMEI4;
//    private AutomationCalendar initialTime;
//    
//    
//
//
//    public void start(String IMEI1, String IMEI2, String IMEI3, String IMEI4, AutomationCalendar initialTime) {
//        this.IMEI1=IMEI1;
//        this.IMEI2=IMEI2;
//        this.IMEI3=IMEI3;
//        this.IMEI4=IMEI4;
//        this.initialTime = initialTime;
//        super.start();
//        
//    }
//    
//    public void noteTesterFirstGeneration(String IMEI1, String IMEI2, String IMEI3, String IMEI4, AutomationCalendar initialTime) {
//        this.IMEI1=IMEI1;
//        this.IMEI2=IMEI2;
//        this.IMEI3=IMEI3;
//        this.IMEI4=IMEI4;
//        this.initialTime = initialTime;
//        noteTesterFirstGeneration();
//    }
//
//    public void noteTesterFirstGeneration() {
//        tiwi1 = new TiwiProDevice(IMEI1);
//        tiwi2 = new TiwiProDevice(IMEI2);
//        tiwi3 = new TiwiProDevice(IMEI3);
//        tiwi4 = new TiwiProDevice(IMEI4);
//        
//        
//        TiwiProDevice[] tiwiArray = new TiwiProDevice[4];
//        
//        tiwiArray[0] = tiwi1;
//        tiwiArray[1] = tiwi2;
//        tiwiArray[2] = tiwi3;
//        tiwiArray[3] = tiwi4;
//        
//        for(int i=0; i<4; i++){
//            tiwiArray[i].set_time( initialTime.addToSeconds(60));
//            tiwiArray[i].getTripTracker().addLocation(new GeoPoint(0.0, -10.0));
//            tiwiArray[i].power_on_device();
//            tiwiArray[i].turn_key_on(15);
//        }
//        
//        tiwi1.nonTripNote( initialTime.addToSeconds(60), 1, Heading.NORTH_EAST, new GeoPoint(10.0, 0.0), 60, 100);
//        tiwi2.nonTripNote( initialTime.addToSeconds(60), 1, Heading.NORTH_EAST, new GeoPoint(0.0, 10.0), 60, 100);
//        tiwi3.nonTripNote( initialTime.addToSeconds(60), 1, Heading.NORTH_EAST, new GeoPoint(-10.0, 0.0), 60, 100);
//        tiwi4.nonTripNote( initialTime.addToSeconds(60), 1, Heading.NORTH_EAST, new GeoPoint(0.0, -10.0), 60, 100);
//
//        
//        //Driving Style notes.
//        AutomationDeviceEvents.hardLeft(tiwi1, 100);
//        AutomationDeviceEvents.hardBrake(tiwi2, 37);
//        AutomationDeviceEvents.hardBump(tiwi3, 1000);
//        AutomationDeviceEvents.hardAccel(tiwi4, 750);
//        
//        //Speeding notes.
//        tiwi1.getState().setTopSpeed(70).setSpeedingDistanceX100(100).setSpeedingSpeedLimit(65);
//        AutomationDeviceEvents.speeding(tiwi1);
//        tiwi1.getState().setTopSpeed(100).setSpeedingDistanceX100(6000).setSpeedingSpeedLimit(65);
//        AutomationDeviceEvents.speeding(tiwi1);
//        
//        //Seat Belt notes.
//        tiwi1.getState().setTopSpeed(60).setAvgSpeed(40).setSeatbeltDistanceX100(100);
//        AutomationDeviceEvents.seatbelt(tiwi1);
//        tiwi3.getState().setTopSpeed(50).setAvgSpeed(45).setSeatbeltDistanceX100(50);
//        AutomationDeviceEvents.seatbelt(tiwi3);
//        
//        //TODO Send Crash notes (USER STORY'D!).
//        
//        //Tampering notes.
//        tiwi4.tampering(15);
//        tiwi2.tampering(15);
//        tiwi2.getState().setLowIdle(10).setHighIdle(20);
//        AutomationDeviceEvents.idling(tiwi2);
//        
//        tiwi3.getState().setLowIdle(23).setHighIdle(19);
//        AutomationDeviceEvents.idling(tiwi3);
//
//        //Zone notes.
//        //Pac Man is 915, Kazakhstan is 916, Tasmania is 917.
//        tiwi2.enter_zone(915);
//        tiwi3.enter_zone(916);
//        tiwi4.enter_zone(917);
//        tiwi2.leave_zone(917);
//        tiwi3.leave_zone(915);
//        tiwi4.leave_zone(916);
//        
//        tiwi1.update_location(new GeoPoint(10, 0), 15);
//        tiwi2.update_location(new GeoPoint(0, 10), 15);
//        tiwi3.update_location(new GeoPoint(-10, 0), 15);
//        tiwi4.update_location(new GeoPoint(0, -10), 15);
//        
//        for(int i=0; i<4; i++){
//        	AutomationDeviceEvents.statistics(tiwiArray[i]);
//            tiwiArray[i].turn_key_off(30);
//            tiwiArray[i].power_off_device(900);
//        }
//    }
//    
//    public static void main(String[] args){
//        NoteTesterGeneration trip = new NoteTesterGeneration();
//        AutomationCalendar initialTime = new AutomationCalendar();
//        String imei1; String imei2; String imei3; String imei4;
//        imei1 = "999999000109741";
//        imei2 = "999999000109742";
//        imei3 = "999999000109743";
//        imei4 = "999999000109744";
//        AutoServers server = new AutoServers();
//        System.out.println("NoteTesterGeneration ran on address:"+server.getMcmUrl()+"; address.webAddress:"+server.getWebAddress());
//        
//        trip.noteTesterFirstGeneration( imei1, imei2, imei3, imei4, initialTime);
//    }
//    
//
//    @Test
//    public void idlingTestTrip() {
//    	TiwiProDevice tiwi;
//    	//String imei = "FAKEIMEIDEVICE2";
//    	String imei = "FAKEIMEIDEVICE";
//        tiwi = new TiwiProDevice(imei);
//        tiwi.set_time(new AutomationCalendar());
//        
//        String start = "980 N 1050 E, Pleasant Grove, UT 84062";
//        //String start = "950 Laird Ave, Salt Lake City, UT 84105";
//        String mid = "815 N 1020 E, Pleasant Grove, UT 84062";
//        String mid2 = "1003 E 1000 N, Pleasant Grove, UT 84062";
//        String stop = "1175 North 730 East, Pleasant Grove, UT 84062";
//       
//        TripDriver driver = new TripDriver(tiwi);
//        
//        driver.addToTrip(start, mid);
//        driver.addToTrip(mid, mid2);
//        driver.addToTrip(mid2, stop);
//
//        DeviceState state = new DeviceState(null, ProductType.TIWIPRO_R74);
//
////        state.setTopSpeed(80).setSpeedingDistanceX100(200).setAvgSpeed(75).setSpeedingSpeedLimit(40);
////        driver.addEvent(29, AutomationDeviceEvents.speeding(state, null));
//        
//        tiwi.getState().setLowIdle(100).setHighIdle(200);
//        AutomationDeviceEvents.idling(tiwi);
//        driver.addEvent(1, AutomationDeviceEvents.idling(state, null));
//        
//        tiwi.getState().setLowIdle(330).setHighIdle(130);
//        driver.addEvent(10, AutomationDeviceEvents.idling(state, new GeoPoint()));
//        
//        tiwi.getState().setLowIdle(44).setHighIdle(14);
//        driver.addEvent(20, AutomationDeviceEvents.idling(state, null));
//        
//        tiwi.getState().setLowIdle(55).setHighIdle(15);
//        driver.addEvent(33, AutomationDeviceEvents.idling(state, null));
//        
//        tiwi.getState().setLowIdle(100).setHighIdle(200);
//        AutomationDeviceEvents.idling(tiwi);
//        driver.addEvent(44, AutomationDeviceEvents.idling(state, null));
//        
//        tiwi.getState().setLowIdle(330).setHighIdle(130);
//        driver.addEvent(55, AutomationDeviceEvents.idling(state, new GeoPoint()));
//        
//        tiwi.getState().setLowIdle(44).setHighIdle(14);
//        driver.addEvent(66, AutomationDeviceEvents.idling(state, null));
//        
//        tiwi.getState().setLowIdle(55).setHighIdle(15);
//        driver.addEvent(77, AutomationDeviceEvents.idling(state, null));
//        
//        tiwi.getState().setLowIdle(100).setHighIdle(200);
//        AutomationDeviceEvents.idling(tiwi);
//        driver.addEvent(88, AutomationDeviceEvents.idling(state, null));
//        
//        tiwi.getState().setLowIdle(330).setHighIdle(130);
//        driver.addEvent(99, AutomationDeviceEvents.idling(state, new GeoPoint()));
//
//        driver.run();
//        
//
//    }

}
