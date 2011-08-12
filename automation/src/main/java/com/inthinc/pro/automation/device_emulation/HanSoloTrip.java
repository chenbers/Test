package com.inthinc.pro.automation.device_emulation;

import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.utils.HessianRequests;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.Vehicle;

public class HanSoloTrip extends Thread{
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

    private void hanSolosFirstTrip() {
        tiwi = new TiwiProDevice(IMEI, server);

        tiwi.set_time( initialTime + 60);
        tiwi.set_WMP(17116);
        tiwi.set_location(33.0104, -117.111);
        tiwi.power_on_device();
        tiwi.turn_key_on(15);
        tiwi.addIdlingNote(300, 300);
        tiwi.update_location(33.0104, -117.111, 15);
        tiwi.update_location(33.0104, -117.113, 15);
        tiwi.add_noDriver();
        
        tiwi.add_seatBelt(50, 50, 50);
        tiwi.add_note_event(5, 105, 5);

        tiwi.update_location(33.01, -117.113, 15);
        tiwi.update_location(33.0097, -117.1153, 15);
        tiwi.update_location(33.015, -117.116, 15);

        tiwi.enter_zone(2);
        
        tiwi.update_location(33.0163, -117.1159, 15);
        tiwi.update_location(33.018, -117.1153, 15);
        tiwi.update_location(33.0188, -117.118, 15);
        tiwi.update_location(33.0192, -117.1199, 15);
        tiwi.update_location(33.021, -117.119, 15);
        tiwi.update_location(33.022, -117.114, 15);
        tiwi.update_location(33.0205, -117.111, 15);
        
        tiwi.tampering(4);
        
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

        tiwi.leave_zone(2);

        tiwi.update_location(33.0106, -117.11, 15);
        tiwi.last_location(33.0104, -117.111, 15);
        
        tiwi.add_stats();
        tiwi.logout_driver(null, 890, 204, 200);
        tiwi.turn_key_off(30);
        tiwi.add_lowBattery();
        tiwi.power_off_device(900);
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
        imei = "DEVICEDOESNTEXIST";
//        imei = "011596000100366";     address=Addresses.TEEN_PROD;
//        imei = "javadeviceindavidsaccount"; address=Addresses.QA;   initialTime = 1313104210;  // vehicleID=37706       deviceID=34506
//        address=Addresses.QA;           initialTime = 1313104210;  // vehicleID=7293        deviceID=3753
//        address=Addresses.STAGE;        initialTime = 1313104210;  // vehicleID=117441441   deviceID=117441936 
//        address=Addresses.PROD;         initialTime = 1313104210;  // vehicleID=1           deviceID=1
//        address=Addresses.CHEVRON;      initialTime = 1313104210;  // vehicleID=117441441   deviceID=117441936
//        address=Addresses.SCHLUMBERGER; initialTime = 1313104210;  // vehicleID=150994955   deviceID=150994955
//        address=Addresses.WEATHORFORD;  initialTime = 1313104210;  // vehicleID=184549575   deviceID=184549735
//        address=Addresses.TECK;         initialTime = 1313104210;  // vehicleID=251658249   deviceID=251658248
//        address=Addresses.BARRICK;      initialTime = 1313104210;  // vehicleID=83886085    deviceID=83886086
//        address=Addresses.CINTAS;       initialTime = 1313104210;  // vehicleID=234881465   deviceID=234881624
        
        trip.hanSolosFirstTrip( imei, address, initialTime);
        
        
//        011596000074009
//        String satIMEI;
//        String mcmID;
//        int vehicleID, companyID, accountID;
//        
//        satIMEI = "626546911105880"; mcmID = "MCM39731"; address=Addresses.QA; initialTime = 1311114913; vehicleID=7284; companyID=1; accountID=3;//deviceID=3763
//        
//        trip.chewiesTurn(mcmID, satIMEI, vehicleID, accountID, address, initialTime);
    }


}
