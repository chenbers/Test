package com.inthinc.pro.automation.device_emulation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.utils.HessianRequests;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.rally.HTTPCommands;

public class HanSoloTrip extends Thread{
    private final static Logger logger = Logger.getLogger(HanSoloTrip.class);
    private TiwiProDevice tiwi;
    private WaysmartDevice waySmart;
    
    private String IMEI;
    private Addresses server;
    private Integer initialTime;
    
    


	public void start(String IMEI, Addresses server, Integer initialTime) {
		this.IMEI=IMEI;
		this.server=server;
		this.initialTime = initialTime;
		super.start();
		
	}
	
	public void hanSolosFirstTrip(String IMEI, Addresses server, Integer initialTime) {
		this.IMEI=IMEI;
		this.server=server;
		this.initialTime = initialTime;
		hanSolosFirstTrip();
	}
	
	@Test
	public void de6739_funkyTrip() {
	    String imei = "FAKEIMEIDEVICE"; 
	    Addresses address=Addresses.QA; 
	    tiwi = new TiwiProDevice(imei, address);
	    Long currentTime = System.currentTimeMillis()/1000;
	    tiwi.set_time(currentTime);
	    tiwi.set_WMP(17116);
	    tiwi.odometer=67751-(99*1);
	    tiwi.set_location(40.74290000000001, -111.865340);
	    tiwi.power_on_device();
	    
	    tiwi.turn_key_on(15);
	    tiwi.update_location(40.74290000000001, -111.865340, 15);
        tiwi.update_location(40.725450, -111.865330, 211);
        tiwi.update_location(40.725470, -111.871310, 54);
        tiwi.update_location(40.719810, -111.871340, 63);
        tiwi.update_location(40.718380, -111.888840, 68);
        tiwi.update_location(40.71852000000001, -111.898050, 40);
        tiwi.update_location(40.71861000000001, -111.901290, 15);
        tiwi.update_location(40.72434000000001,-111.918240, 92);
        tiwi.update_location(40.724460, -111.924430, 20);
        tiwi.update_location(40.725970, -111.980440, 161);
        tiwi.update_location(40.727020, -111.986490, 29);
        tiwi.update_location(40.71832000000001,-111.985770, 185);
        tiwi.update_location(40.71089000000001,-111.994450, 113);
        
        tiwi.add_stats();
        tiwi.logout_driver(null, 890, 204, 200);
        tiwi.turn_key_off(30);
        tiwi.power_off_device(900);
	}

    private void hanSolosFirstTrip() {
        tiwi = new TiwiProDevice(IMEI, server);

        tiwi.set_time( initialTime + 60);
        tiwi.set_WMP(17116);
        tiwi.set_location(33.0104, -117.111);
        tiwi.power_on_device();
        tiwi.turn_key_on(15);
//        tiwi.addIdlingNote(300, 300);
        tiwi.update_location(33.0104, -117.111, 15);
        tiwi.update_location(33.0104, -117.113, 15);
//        tiwi.add_noDriver();
        
//        tiwi.add_seatBelt(50, 50, 50);
//        tiwi.add_note_event(5, 105, 5);

        tiwi.update_location(33.01, -117.113, 15);
        tiwi.update_location(33.0097, -117.1153, 15);
        tiwi.update_location(33.015, -117.116, 15);

//        tiwi.enter_zone(2);
        
        tiwi.update_location(33.0163, -117.1159, 15);
        tiwi.update_location(33.018, -117.1153, 15);
        tiwi.update_location(33.0188, -117.118, 15);
        tiwi.update_location(33.0192, -117.1199, 15);
        tiwi.update_location(33.021, -117.119, 15);
        tiwi.update_location(33.022, -117.114, 15);
        tiwi.update_location(33.0205, -117.111, 15);
        
//        tiwi.tampering(4);
        
        tiwi.update_location(33.02, -117.109, 15);
        tiwi.update_location(33.02, -117.108, 15);
        tiwi.update_location(33.022, -117.104, 15);
        tiwi.update_location(33.0217, -117.103, 15);
        tiwi.update_location(33.0213, -117.1015, 15);
        tiwi.update_location(33.0185, -117.1019, 15);
        tiwi.update_location(33.017, -117.102, 15);
        tiwi.update_location(33.015, -117.1032, 15);
        tiwi.update_location(33.013, -117.105, 15);
        tiwi.update_location(33.011, -117.106, 15);
        tiwi.update_location(33.0108, -117.108, 15);
        tiwi.update_location(33.0108, -117.109, 15);

//        tiwi.leave_zone(2);

        tiwi.update_location(33.0106, -117.11, 15);
        tiwi.last_location(33.0104, -117.111, 15);
        
        tiwi.add_stats();
        tiwi.logout_driver(null, 890, 204, 200);
        tiwi.turn_key_off(30);
//        tiwi.add_lowBattery();
        tiwi.power_off_device(900);
    }
    
//    @Test
//    public void testthis(){
//        try {
//            generateTrip("Vancouver Canada", "Abbotsford Canada");
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
    
    private static void generateTrip(String origin, String destination, Object device)// throws Exception
    {
        try {
            String mode = "json"; // json or xml
            String googleMapsApi = "http://maps.googleapis.com/maps/api/directions/" + mode;// +"?"sensor=false&origin="+origin+"&destination="+destination+"";
            String query = "sensor=false&origin=" + URLEncoder.encode(origin, "UTF-8") + "&destination=" + URLEncoder.encode(destination, "UTF-8") + "";
            URL request = new URL(googleMapsApi + "?" + query);
            String sendRequestResults = sendRequest(request);
            System.out.println("sendRequest(" + request + ") returns " + sendRequestResults);
            JSONObject workspace = new JSONObject();
            System.out.println("workspace: " + workspace);
            for (Object o : JSONObject.getNames(workspace)) {
                System.out.println("o: " + o.toString());
            }
            JSONArray routes = workspace.getJSONArray("routes");
            JSONArray legs = routes.getJSONObject(0).getJSONArray("legs");
            JSONArray steps = legs.getJSONObject(0).getJSONArray("steps");
            for (int i = 0; i < steps.length(); i++) {
                double lat = steps.getJSONObject(i).getJSONObject("end_location").getDouble("lat");
                double lng = steps.getJSONObject(i).getJSONObject("end_location").getDouble("lng");
                int time_delta = steps.getJSONObject(i).getJSONObject("duration").getInt("value");
                System.out.println(i + " " + lat + ", " + lng);
                if (device instanceof WaysmartDevice)
                    ((WaysmartDevice) device).update_location(lat, lng, time_delta);
                else if (device instanceof TiwiProDevice)
                    ((TiwiProDevice) device).update_location(lat, lng, time_delta);
                else
                    throw new IllegalArgumentException("generateTrip requires device to be either a WaysmartDevice or a TiwiProDevice");
            }

        } catch (UnsupportedEncodingException uee) {
            System.out.println("generateTrips couldn't encode either origin: " + origin + "; or destination: " + destination + ";");
        } catch (MalformedURLException murle) {
            System.out.println("MalformedURLExcpetion: " + murle);
            murle.printStackTrace();
        } catch (JSONException jsone) {
            jsone.printStackTrace();
        }
    }
    private static String sendRequest(URL request) {
        System.out.println("private String sendRequest(URL "+request+")");
        
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
                    System.out.println("status: "+status);
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

//    public static String getResponseBodyFromStream(InputStream is) {
//        String str = "";
//        try {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            byte[] buffer = new byte[128];
//            int size = 0;
//            while ((size = is.read(buffer)) > 0) {
//                baos.write(buffer, 0, size);
//            }
//            str = new String(baos.toByteArray());
//        } catch (IOException ioe) {}
//        return str;
//    }
    
    @Test
    public void de6587_canadaWaysmartTrip() {
        String satImei = "virt_ws_de6587";
        String mcmID = "virt_MCM39731";
        String driverID = "CANADA";//TODO: get driverID from Tina
        String occupantID = "TWO";//TODO: get occupantID from Tina
        Addresses server = Addresses.QA; 
        Long currentTime = System.currentTimeMillis()/1000;
        Integer initialTime = currentTime.intValue();
        //initialTime = 1311114913; 
        String vehicleID="virtualWS"; 
        //companyID=1; 
        int accountID=2;
        //deviceID=3763
        
        waySmart = new WaysmartDevice(satImei, mcmID, server);
        waySmart.set_time(initialTime);
        waySmart.set_location(33.0104, -117.111);
        waySmart.setBaseOdometer(5000);
        waySmart.addInstallEvent(vehicleID, accountID);
        waySmart.power_on_device();
        waySmart.logInDriver(driverID);
        
        waySmart.logInOccupant(occupantID);//TODO: talk with dTanner about what is needed to add this to the virtual waysmart device stuff?  or Eric Capps?
        waySmart.turn_key_on(15);

        generateTrip("Vancouver Canada", "Abbotsford Canada", waySmart);
        
        waySmart.turn_key_off(15);
        waySmart.power_off_device(100);
    }
    
    public void chewiesTurn(String mcmID, String satImei, String vehicleID, int accountID, Addresses server, Integer initialTime){
        waySmart = new WaysmartDevice(satImei, mcmID, server);
        waySmart.set_time(initialTime);
        waySmart.set_location(33.0104, -117.111);
        waySmart.setBaseOdometer(5000);
        waySmart.addInstallEvent(vehicleID, accountID);
        waySmart.power_on_device();
        waySmart.turn_key_on(15);
        waySmart.turn_key_off(15);
        waySmart.power_off_device(100);
        
    }
    
    public int createVehicle(Addresses server){
        Vehicle vehicle = new Vehicle();
        vehicle.setFullName("fakevehicleon"+server.name());
        HessianRequests create = new HessianRequests(server);
        vehicle.setGroupID(create.getGroupByName("Automated Team", create.getQAAccount().getAccountID()).getGroupID());
        
        return 0;
    }
    
    public int createDevice(Addresses server){
        Device device = new Device();
        device.setFirmwareVersion(17207);
        device.setImei("fakeimeion"+server.name());
        device.setName("fakedeviceon"+server.name());
        device.setPhone("8015559876");
        device.setSim("9989898989998989");
        device.setSerialNum("TP"+server.name());
        device.setStatus(DeviceStatus.ACTIVE);
        device.setWitnessVersion(51);
        HessianRequests create = new HessianRequests(server);
        device.setAccountID(create.getQAAccount().getAccountID());
        return create.createDevice(device).getDeviceID();
    }
    

	public void run() {
		hanSolosFirstTrip();
		
	}
    
    public static void main(String[] args){
        HanSoloTrip trip = new HanSoloTrip();
        Long currentTime = System.currentTimeMillis()/1000;
        Integer initialTime = currentTime.intValue();
        Addresses address;
        String imei = "FAKEIMEIDEVICE"; address=Addresses.QA;
//        imei = "DEVICEDOESNTEXIST";
//        imei = "011596000100366";     address=Addresses.TEEN_PROD;
        imei = "javadeviceindavidsaccount"; address=Addresses.QA;   initialTime = 1313106000;  // vehicleID=37706       deviceID=34506
//        address=Addresses.QA;           initialTime = 1313104210;  // vehicleID=7293        deviceID=3753
//        address=Addresses.STAGE;        initialTime = 1313104210;  // vehicleID=117441441   deviceID=117441936 
//        address=Addresses.PROD;         initialTime = 1313104210;  // vehicleID=1           deviceID=1
//        trip.hanSolosFirstTrip( imei, address, initialTime);
//        address=Addresses.CHEVRON;      initialTime = 1313104210;  // vehicleID=117441441   deviceID=117441936
//        trip.hanSolosFirstTrip( imei, address, initialTime);
//        address=Addresses.SCHLUMBERGER; initialTime = 1313104210;  // vehicleID=150994955   deviceID=150994955
//        trip.hanSolosFirstTrip( imei, address, initialTime);
//        address=Addresses.WEATHORFORD;  initialTime = 1313104210;  // vehicleID=184549575   deviceID=184549735
//        trip.hanSolosFirstTrip( imei, address, initialTime);
//        address=Addresses.TECK;         initialTime = 1313104210;  // vehicleID=251658249   deviceID=251658248
//        trip.hanSolosFirstTrip( imei, address, initialTime);
//        address=Addresses.BARRICK;      initialTime = 1313104210;  // vehicleID=83886085    deviceID=83886086
//        trip.hanSolosFirstTrip( imei, address, initialTime);
//        address=Addresses.CINTAS;       initialTime = 1313104210;  // vehicleID=234881465   deviceID=234881624
//        trip.hanSolosFirstTrip( imei, address, initialTime);
        
        
        
//        011596000074009
        String satIMEI;
        String mcmID;
        String vehicleID;
        int accountID;
        
//        satIMEI = "626546911105880"; mcmID = "MCM39731"; address=Addresses.QA; initialTime = 1311114913; vehicleID=7284; accountID=3;//deviceID=3763
        satIMEI = "778899663322114"; mcmID = "MCMFAKE"; address=Addresses.QA; initialTime = 1314296363; vehicleID="dddd"; accountID=3;//deviceID=3763
        
        trip.chewiesTurn(mcmID, satIMEI, vehicleID, accountID, address, initialTime);
    }


}
