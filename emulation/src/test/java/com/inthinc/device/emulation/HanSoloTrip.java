package com.inthinc.device.emulation;

import java.util.Iterator;

import com.inthinc.device.devices.TiwiProDevice;
import com.inthinc.device.devices.WaysmartDevice;
import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.emulation.enums.DeviceEnums.HOSState;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.device.objects.TripDriver;
import com.inthinc.device.objects.TripTracker;
import com.inthinc.pro.automation.enums.AutoSilos;
import com.inthinc.pro.automation.enums.ProductType;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.objects.AutomationCalendar;

public class HanSoloTrip extends Thread{
    private TiwiProDevice tiwi;
    private WaysmartDevice waySmart;
    
    private String IMEI;
    private AutoSilos server;
    private AutomationCalendar initialTime;
    
    
    @Override
    public void interrupt(){
        super.interrupt();
    }


    public boolean start(String IMEI, AutoSilos server, AutomationCalendar initialTime) {
        this.IMEI=IMEI;
        this.server=server;
        this.initialTime = initialTime.copy();
        super.start();
        return true;
    }
    
    public void hanSolosFirstTrip(String IMEI, AutoSilos server, AutomationCalendar initialTime) {
        this.IMEI=IMEI;
        this.server=server;
        this.initialTime = initialTime.copy();
        hanSolosFirstTrip();
    }
    
    public void rfSwitchTestTrip() {
        String imei = "FAKEIMEIDEVICE"; 
        AutoSilos address=AutoSilos.QA; 
        tiwi = new TiwiProDevice(imei, address);
        tiwi.set_time(new AutomationCalendar());
        
        String start = "980 N 1050 E, Pleasant Grove, UT 84062";
        String mid = "815 N 1020 E, Pleasant Grove, UT 84062";
        String stop = "1002 N 1020 E, Pleasant Grove, UT 84062";
       
        TripDriver driver = new TripDriver(tiwi);
        
        driver.addToTrip(start, mid);
        driver.addToTrip(mid, stop);
        driver.addToTrip(stop, start);

        DeviceState state = new DeviceState(null, ProductType.TIWIPRO_R74);

//        state.setTopSpeed(80).setSpeedingDistanceX100(200).setAvgSpeed(75).setSpeedingSpeedLimit(40);
//        driver.addEvent(29, AutomationDeviceEvents.speeding(state, null));
        driver.addEvent(30, AutomationDeviceEvents.rfKill(state, null));
        state.setTopSpeed(80).setSpeedingDistanceX100(200).setAvgSpeed(75).setSpeedingSpeedLimit(40);
        driver.addEvent(35, AutomationDeviceEvents.speeding(state, null));

        driver.start();
        

    }
    
    public void routeTestTrip() {
        waySmart = new WaysmartDevice("FAKEIMEIDEVICE", "FAKEIMEIDEVICE", AutoSilos.QA, Direction.gprs);
        waySmart.set_time(new AutomationCalendar());
        
        String start = "980 N 1050 E, Pleasant Grove, UT 84062";
        String mid = "815 N 1020 E, Pleasant Grove, UT 84062";
        String stop = "1002 N 1020 E, Pleasant Grove, UT 84062";
        String mid2 = "1100 N 1100 E, Pleasant Grove, UT 84062";
       
        TripDriver driver = new TripDriver(waySmart);
        
        driver.addToTrip(start, mid);
        driver.addToTrip(mid, stop);
        driver.addToTrip(stop, start);
        driver.addToTrip(start, mid2);

        DeviceState state = new DeviceState("FAKEIMEIDEVICE", ProductType.WAYSMART_850);

//        state.setTopSpeed(80).setSpeedingDistanceX100(200).setAvgSpeed(75).setSpeedingSpeedLimit(40);
//        driver.addEvent(29, AutomationDeviceEvents.speeding(state, null));
        driver.addEvent(30, AutomationDeviceEvents.routeStopArrival(state, new GeoPoint(40.74290000000001, -111.865340), "12345"));
        driver.addEvent(35, AutomationDeviceEvents.routeStopDeparture(state, new GeoPoint(40.74290000000001, -111.865340), "12345"));

        driver.start();
        

    }
    
    public void de6739_funkyTrip() {
        String imei = "FAKEIMEIDEVICE"; 
        AutoSilos address=AutoSilos.QA; 
        tiwi = new TiwiProDevice(imei, address);
        tiwi.set_time(new AutomationCalendar());
        tiwi.getState().setWMP(17116);
        tiwi.getState().setWMP(67751-(99*1));
        tiwi.firstLocation(new GeoPoint(40.74290000000001, -111.865340));
        tiwi.power_on_device();
        
        tiwi.turn_key_on(15);
        tiwi.update_location(new GeoPoint(40.74290000000001, -111.865340), 15);
        tiwi.update_location(new GeoPoint(40.725450, -111.865330), 211);
        tiwi.update_location(new GeoPoint(40.725470, -111.871310), 54);
        tiwi.update_location(new GeoPoint(40.719810, -111.871340), 63);
        tiwi.update_location(new GeoPoint(40.718380, -111.888840), 68);
        tiwi.update_location(new GeoPoint(40.71852000000001, -111.898050), 40);
        tiwi.update_location(new GeoPoint(40.71861000000001, -111.901290), 15);
        tiwi.update_location(new GeoPoint(40.72434000000001,-111.918240), 92);
        tiwi.update_location(new GeoPoint(40.724460, -111.924430), 20);
        tiwi.update_location(new GeoPoint(40.725970, -111.980440), 161);
        tiwi.update_location(new GeoPoint(40.727020, -111.986490), 29);
        tiwi.update_location(new GeoPoint(40.71832000000001,-111.985770), 185);
        tiwi.update_location(new GeoPoint(40.71089000000001,-111.994450), 113);
        
        AutomationDeviceEvents.statistics(tiwi);
        tiwi.logout_driver(null, 890, 204, 200);
        tiwi.turn_key_off(30);
        tiwi.power_off_device(900);
    }

    private void hanSolosFirstTrip() {
        tiwi = new TiwiProDevice(IMEI, server);
        DeviceState state = tiwi.getState();

        tiwi.set_time( initialTime.addToSeconds(60));
        tiwi.getState().setWMP(17116);
        tiwi.firstLocation(new GeoPoint(33.0104, -117.111));
        tiwi.power_on_device();
        tiwi.turn_key_on(15);
        tiwi.getState().setLowIdle(300).setHighIdle(300);
        AutomationDeviceEvents.idling(tiwi);
        tiwi.getState().setSpeedLimit(5);
        
        tiwi.update_location(new GeoPoint(33.0104, -117.111), 15);
        tiwi.update_location(new GeoPoint(33.0104, -117.113), 15);
        
        state.setSeatbeltViolationDistanceX100(500).setTopSpeed(tiwi.getState().getSpeed());
        AutomationDeviceEvents.seatbelt(tiwi);
        AutomationDeviceEvents.hardLeft(tiwi, 105);

        tiwi.update_location(new GeoPoint(33.01, -117.113), 15);
        tiwi.update_location(new GeoPoint(33.0097, -117.1153), 15);
        tiwi.update_location(new GeoPoint(33.015, -117.116), 15);
        
        tiwi.getState().setSpeedLimit(75);
        tiwi.enter_zone(2);
        
        tiwi.update_location(new GeoPoint(33.0163, -117.1159), 15);
        tiwi.update_location(new GeoPoint(33.018, -117.1153), 15);
        tiwi.update_location(new GeoPoint(33.0188, -117.118), 15);
        tiwi.update_location(new GeoPoint(33.0192, -117.1199), 15);
        tiwi.update_location(new GeoPoint(33.021, -117.119), 15);
        tiwi.update_location(new GeoPoint(33.022, -117.114), 15);
        tiwi.update_location(new GeoPoint(33.0205, -117.111), 15);
        
        tiwi.tampering(4);
        
        tiwi.update_location(new GeoPoint(33.02, -117.109), 15);
        tiwi.update_location(new GeoPoint(33.02, -117.108), 15);
        tiwi.update_location(new GeoPoint(33.022, -117.104), 15);
        tiwi.update_location(new GeoPoint(33.0217, -117.103), 15);
        tiwi.update_location(new GeoPoint(33.0213, -117.1015), 15);
        tiwi.update_location(new GeoPoint(33.0185, -117.1019), 15);
        tiwi.update_location(new GeoPoint(33.017, -117.102), 15);
        tiwi.update_location(new GeoPoint(33.015, -117.1032), 15);
        tiwi.update_location(new GeoPoint(33.013, -117.105), 15);
        tiwi.update_location(new GeoPoint(33.011, -117.106), 15);
        tiwi.update_location(new GeoPoint(33.0108, -117.108), 15);
        tiwi.update_location(new GeoPoint(33.0108, -117.109), 15);

        tiwi.leave_zone(2);

        tiwi.update_location(new GeoPoint(33.0106, -117.11), 15);
        tiwi.last_location(new GeoPoint(33.0104, -117.111), 15);
        
        AutomationDeviceEvents.statistics(tiwi);
//        tiwi.logout_driver(null, 890, 204, 200);
        tiwi.turn_key_off(30);
        AutomationDeviceEvents.lowBattery(tiwi);
        tiwi.power_off_device(900);
    }
    
    
    public void de6587_canadaWaysmartTrip() {
        String satImei = "virt_ws_de6587";
        String mcmID = "virt_MCM39731";
        String driverID = "CANADA";
        String occupantID = "TWO";
        AutoSilos server = AutoSilos.QA; 
        AutomationCalendar initialTime = new AutomationCalendar();
        String vehicleID="virtualWS"; 
        int accountID=2;
        
        waySmart = new WaysmartDevice(satImei, mcmID, server, Direction.wifi);

        TripTracker trip = waySmart.getTripTracker();
        trip.getTrip("Vancouver Canada", "Abbotsford Canada");
        
        waySmart.set_time(initialTime);
        waySmart.setBaseOdometer(5000);
        waySmart.setVehicleID(vehicleID); waySmart.setAccountID(accountID);
        AutomationDeviceEvents.install(waySmart);
        waySmart.power_on_device();
        waySmart.changeDriverStatus(driverID, HOSState.ON_DUTY_NOT_DRIVING);
        waySmart.changeDriverStatus(occupantID, HOSState.OCCUPANT_ON_DUTY);
        waySmart.turn_key_on(15);
        
        Iterator<GeoPoint> itr = trip.iterator();
        
        while (itr.hasNext()){
        	trip.getNextLocation(65, false);
        	waySmart.addLocation();
        }
        
        waySmart.turn_key_off(15);
        waySmart.power_off_device(100);
    }
    
    public void chewiesTurn(String mcmID, String satImei, String vehicleID, int accountID, AutoSilos server, AutomationCalendar initialTime){
        waySmart = new WaysmartDevice(satImei, mcmID, server, Direction.wifi);
        waySmart.set_time(initialTime);
        waySmart.firstLocation(new GeoPoint(33.0104, -117.111));
        waySmart.setBaseOdometer(5000);
        waySmart.setVehicleID(vehicleID);
        waySmart.setAccountID(accountID);
        AutomationDeviceEvents.install(waySmart);
        
        waySmart.power_on_device();
        waySmart.turn_key_on(15);
        waySmart.turn_key_off(15);
        waySmart.power_off_device(100);
        
    }
    
    

    public void run() {
        hanSolosFirstTrip();
        
    }
    
    public static void main(String[] args){
//        generateTrip("Vancouver Canada", "Abbotsford Canada", new TiwiProDevice("0000"));
                
        
        HanSoloTrip trip = new HanSoloTrip();
        AutomationCalendar initialTime = new AutomationCalendar();
        AutoSilos address;
        String imei = "FAKEIMEIDEVICE"; 
        address=AutoSilos.QA;
        
//        String imei = "DEVICEDOESNTEXIST";

        //        imei = "500000000007272"; address=AutoSilos.DEV;// initialTime.setDate(time)
//        imei = "011596000100366";     address=AutoSilos.TEEN_PROD;
//        imei = "javadeviceindavidsaccount"; address=AutoSilos.QA;   initialTime.setDate(1335460214);  // vehicleID=37706       deviceID=34506
//        address=AutoSilos.QA;           initialTime.setDate(1334940345);  // vehicleID=7293        deviceID=3753
//        trip.hanSolosFirstTrip( imei, address, initialTime);
//          address=AutoSilos.DEV;		initialTime.setDate(1334940345);
//        address=AutoSilos.STAGE;        initialTime.setDate(1334940345);  // vehicleID=117441441   deviceID=117441936 
//        address=AutoSilos.PROD;         initialTime.setDate(1334941814);  // vehicleID=1           deviceID=1
//        trip.hanSolosFirstTrip( imei, address, initialTime);
//        address=AutoSilos.CHEVRON;      initialTime.setDate(1334941814);  // vehicleID=117441441   deviceID=117441936
//        trip.hanSolosFirstTrip( imei, address, initialTime);
//        address=AutoSilos.SCHLUMBERGER; initialTime.setDate(1334941814);  // vehicleID=150994955   deviceID=150994955
//        trip.hanSolosFirstTrip( imei, address, initialTime);
//        address=AutoSilos.WEATHERFORD;  initialTime.setDate(1334941814);  // vehicleID=184549575   deviceID=184549735
//        trip.hanSolosFirstTrip( imei, address, initialTime);
//        address=AutoSilos.TECK;         initialTime.setDate(1334943283);  // vehicleID=251658249   deviceID=251658248
//        trip.hanSolosFirstTrip( imei, address, initialTime);
//        address=AutoSilos.BARRICK;      initialTime.setDate(1334941814);  // vehicleID=83886085    deviceID=83886086
//        trip.hanSolosFirstTrip( imei, address, initialTime);
//        address=AutoSilos.CINTAS;       initialTime.setDate(1334941814);  // vehicleID=234881465   deviceID=234881624
//        trip.hanSolosFirstTrip( imei, address, initialTime);
//        address=AutoSilos.LDS;       initialTime.setDate(1334941814);  // vehicleID=100663298   deviceID=100663298
//        trip.hanSolosFirstTrip( imei, address, initialTime);
        
        
        
//        011596000074009
        String satIMEI;
        String mcmID;
        String vehicleID;
        int accountID;
        
//        satIMEI = "626546911105880"; mcmID = "MCM39731"; address=AutoSilos.QA; initialTime = 1316471529; vehicleID=7284; accountID=3;//deviceID=3763
        satIMEI = "778899663322114"; mcmID = "MCMFAKE"; address=AutoSilos.QA; initialTime = new AutomationCalendar(); vehicleID="dddd"; accountID=3;//deviceID=3763
        
        trip.routeTestTrip();  //run the route test trip method
        
        Log.info(trip);
    }


}
