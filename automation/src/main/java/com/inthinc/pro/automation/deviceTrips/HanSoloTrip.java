package com.inthinc.pro.automation.deviceTrips;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.models.AutomationDeviceEvents;
import com.inthinc.pro.automation.models.GeoPoint;
import com.inthinc.pro.automation.objects.TiwiProDevice;
import com.inthinc.pro.automation.objects.WaysmartDevice;
import com.inthinc.pro.automation.objects.WaysmartDevice.Direction;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.HTTPCommands;
import com.inthinc.pro.model.configurator.ProductType;

public class HanSoloTrip extends Thread{
    private final static Logger logger = Logger.getLogger(HanSoloTrip.class);
    private TiwiProDevice tiwi;
    private WaysmartDevice waySmart;
    
    private String IMEI;
    private Addresses server;
    private AutomationCalendar initialTime;
    
    
    @Override
    public void interrupt(){
        super.interrupt();
    }


    public boolean start(String IMEI, Addresses server, AutomationCalendar initialTime) {
        this.IMEI=IMEI;
        this.server=server;
        this.initialTime = initialTime.copy();
        super.start();
        return true;
    }
    
    public void hanSolosFirstTrip(String IMEI, Addresses server, AutomationCalendar initialTime) {
        this.IMEI=IMEI;
        this.server=server;
        this.initialTime = initialTime.copy();
        hanSolosFirstTrip();
    }
    
    public void rfSwitchTestTrip() {
        String imei = "FAKEIMEIDEVICE"; 
        Addresses address=Addresses.QA; 
        tiwi = new TiwiProDevice(imei, address);
        tiwi.set_time(new AutomationCalendar());
        
        String start = "980 N 1050 E, Pleasant Grove, UT 84062";
        String mid = "815 N 1020 E, Pleasant Grove, UT 84062";
        String stop = "1002 N 1020 E, Pleasant Grove, UT 84062";
       
        TripDriver driver = new TripDriver(tiwi);
        
        driver.addToTrip(start, mid);
        driver.addToTrip(mid, stop);
        driver.addToTrip(stop, start);

        //driver.addEvent(29, AutomationDeviceEvents.speeding(80, 200, 700, 40, 75, 600));
        driver.addEvent(30, AutomationDeviceEvents.rfKill());
        driver.addEvent(35, AutomationDeviceEvents.speeding(80, 200, 700, 40, 75, 600));

        driver.run();
        

    }
    
    public void de6739_funkyTrip() {
        String imei = "FAKEIMEIDEVICE"; 
        Addresses address=Addresses.QA; 
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
        
        tiwi.add_stats();
        tiwi.logout_driver(null, 890, 204, 200);
        tiwi.turn_key_off(30);
        tiwi.power_off_device(900);
    }

    private void hanSolosFirstTrip() {
        tiwi = new TiwiProDevice(IMEI, server);

        tiwi.set_time( initialTime.addToSeconds(60));
        tiwi.getState().setWMP(17116);
        tiwi.firstLocation(new GeoPoint(33.0104, -117.111));
        tiwi.power_on_device();
        tiwi.turn_key_on(15);
        tiwi.addIdlingNote(300, 300);
        tiwi.getState().setSpeedLimit(5.0);
        
        tiwi.update_location(new GeoPoint(33.0104, -117.111), 15);
        tiwi.update_location(new GeoPoint(33.0104, -117.113), 15);
        
        tiwi.addSeatbeltEvent(AutomationDeviceEvents.seatbelt(500, 90, 50, 50, 50, 600));
        tiwi.addNoteEvent(AutomationDeviceEvents.hardLeft(5, 105, 5));

        tiwi.update_location(new GeoPoint(33.01, -117.113), 15);
        tiwi.update_location(new GeoPoint(33.0097, -117.1153), 15);
        tiwi.update_location(new GeoPoint(33.015, -117.116), 15);
        
        tiwi.getState().setSpeedLimit(75.0);
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
        
        tiwi.add_stats();
        tiwi.logout_driver(null, 890, 204, 200);
        tiwi.turn_key_off(30);
        tiwi.add_lowBattery();
        tiwi.power_off_device(900);
    }
    
    public static void generateTrip(String origin, String destination, Object device) {
        String sendRequestResults = "";
        try {
            String mode = "json"; // json or xml
            String googleMapsApi = "http://maps.googleapis.com/maps/api/directions/" + mode;// +"?"sensor=false&origin="+origin+"&destination="+destination+"";
            String query = "sensor=false&origin=" + URLEncoder.encode(origin, "UTF-8") + "&destination=" + URLEncoder.encode(destination, "UTF-8") + "";
            URL request = new URL(googleMapsApi + "?" + query);
            sendRequestResults = sendRequest(request);
            logger.info("sendRequest(" + request + ") returns " + sendRequestResults);
//            logger.info(PrettyJSON.toString(sendRequestResults));
//            JSONObject workspace = new JSONObject();
//            JSONArray routes = workspace.getJSONArray("routes");
//            JSONArray legs = routes.getJSONObject(0).getJSONArray("legs");
//            JSONArray steps = legs.getJSONObject(0).getJSONArray("steps");
//            for (int i = 0; i < steps.length(); i++) {
//                double lat = steps.getJSONObject(i).getJSONObject("end_location").getDouble("lat");
//                double lng = steps.getJSONObject(i).getJSONObject("end_location").getDouble("lng");
//                int time_delta = steps.getJSONObject(i).getJSONObject("duration").getInt("value");
//                if (device instanceof WaysmartDevice)
//                    ((WaysmartDevice) device).update_location(new GeoPoint(lat, lng, time_delta);
//                else if (device instanceof TiwiProDevice)
//                    ((TiwiProDevice) device).update_location(new GeoPoint(lat, lng, time_delta);
//                else
//                    throw new IllegalArgumentException("generateTrip requires device to be either a WaysmartDevice or a TiwiProDevice");
//            }

        } catch (UnsupportedEncodingException uee) {
            logger.error("generateTrips couldn't encode either origin: " + origin + "; or destination: " + destination + ";");
        } catch (MalformedURLException murle) {
            logger.error("MalformedURLExcpetion: " + murle);
            murle.printStackTrace();
//        } catch (JSONException jsone) {
//            logger.debug(sendRequestResults);
//            jsone.printStackTrace();
        }
    }
    private static String sendRequest(URL request) {
        logger.info("private String sendRequest(URL "+request+")");
        URLConnection conn;
        HttpURLConnection httpConn = null;
        try
        {
            conn = request.openConnection();
            if (conn instanceof HttpURLConnection)
            {
                httpConn = (HttpURLConnection) conn;
                String status = httpConn.getResponseMessage();
                if (status.equals("OK"))
                {
                    return HTTPCommands.getResponseBodyFromStream(httpConn.getInputStream());
                }
                else { 
                    logger.warn("status: "+status);
                    return null;
                }
            }
        }
        catch (IOException e)
        {
           e.printStackTrace();      
        }
        finally
        {
            if (httpConn != null)
            {
                httpConn.disconnect();
                httpConn = null;
            }
        }
        return null;
    }
    
    public void de6587_canadaWaysmartTrip() {
        String satImei = "virt_ws_de6587";
        String mcmID = "virt_MCM39731";
        String driverID = "CANADA";
        String occupantID = "TWO";
        Addresses server = Addresses.QA; 
        AutomationCalendar initialTime = new AutomationCalendar();
        String vehicleID="virtualWS"; 
        int accountID=2;
        
        waySmart = new WaysmartDevice(satImei, mcmID, server, Direction.wifi);
        waySmart.set_time(initialTime);
        waySmart.firstLocation(new GeoPoint(33.0104, -117.111));
        waySmart.setBaseOdometer(5000);
        waySmart.addInstallEvent(AutomationDeviceEvents.install(vehicleID, mcmID, accountID));
        waySmart.power_on_device();
        waySmart.logInDriver(driverID);
        
        waySmart.logInOccupant(occupantID);
        waySmart.turn_key_on(15);

        generateTrip("Vancouver Canada", "Abbotsford Canada", waySmart);
        
        waySmart.turn_key_off(15);
        waySmart.power_off_device(100);
    }
    
    public void chewiesTurn(String mcmID, String satImei, String vehicleID, int accountID, Addresses server, AutomationCalendar initialTime){
        waySmart = new WaysmartDevice(satImei, mcmID, server, Direction.wifi);
        waySmart.set_time(initialTime);
        waySmart.firstLocation(new GeoPoint(33.0104, -117.111));
        waySmart.setBaseOdometer(5000);
        waySmart.addInstallEvent(AutomationDeviceEvents.install(vehicleID, mcmID, accountID));
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
        Addresses address;
        String imei = "FAKEIMEIDEVICE"; address=Addresses.DEV;
        imei = "DEVICEDOESNTEXIST";
//        imei = "500000000007272"; address=Addresses.DEV;// initialTime.setDate(time)
//        imei = "011596000100366";     address=Addresses.TEEN_PROD;
//        imei = "javadeviceindavidsaccount"; address=Addresses.QA;   initialTime.setDate(1324155471);  // vehicleID=37706       deviceID=34506
//        address=Addresses.QA;           initialTime.setDate(1323834000);  // vehicleID=7293        deviceID=3753
//          address=Addresses.DEV;		initialTime.setDate(1323817719);
//        address=Addresses.STAGE;        initialTime.setDate(1323817719);  // vehicleID=117441441   deviceID=117441936 
//        address=Addresses.PROD;         initialTime.setDate(1323819788);  // vehicleID=1           deviceID=1
//        trip.hanSolosFirstTrip( imei, address, initialTime);
        address=Addresses.CHEVRON;      initialTime.setDate(1323823926);  // vehicleID=117441441   deviceID=117441936
//        trip.hanSolosFirstTrip( imei, address, initialTime);
//        address=Addresses.SCHLUMBERGER; initialTime.setDate(1323821857);  // vehicleID=150994955   deviceID=150994955
//        trip.hanSolosFirstTrip( imei, address, initialTime);
//        address=Addresses.WEATHERFORD;  initialTime.setDate(1323817719);  // vehicleID=184549575   deviceID=184549735
//        trip.hanSolosFirstTrip( imei, address, initialTime);
//        address=Addresses.TECK;         initialTime.setDate(1323817719);  // vehicleID=251658249   deviceID=251658248
//        trip.hanSolosFirstTrip( imei, address, initialTime);
//        address=Addresses.BARRICK;      initialTime.setDate(1323817719);  // vehicleID=83886085    deviceID=83886086
//        trip.hanSolosFirstTrip( imei, address, initialTime);
//        address=Addresses.CINTAS;       initialTime.setDate(1323821857);  // vehicleID=234881465   deviceID=234881624
//        trip.hanSolosFirstTrip( imei, address, initialTime);
//        address=Addresses.LDS;       initialTime.setDate(1323817719);  // vehicleID=100663298   deviceID=100663298
        trip.hanSolosFirstTrip( imei, address, initialTime);
        //trip.rfSwitchTestTrip();
        
        
        
//        011596000074009
//        String satIMEI;
//        String mcmID;
//        String vehicleID;
//        int accountID;
        
//        satIMEI = "626546911105880"; mcmID = "MCM39731"; address=Addresses.QA; initialTime = 1316471529; vehicleID=7284; accountID=3;//deviceID=3763
//        satIMEI = "778899663322114"; mcmID = "MCMFAKE"; address=Addresses.QA; initialTime = 1316471529; vehicleID="dddd"; accountID=3;//deviceID=3763
//        
//        trip.chewiesTurn(mcmID, satIMEI, vehicleID, accountID, address, initialTime);
    }


}
