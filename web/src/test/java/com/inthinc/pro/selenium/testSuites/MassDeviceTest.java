package com.inthinc.pro.selenium.testSuites;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import com.inthinc.device.devices.TiwiProDevice;
import com.inthinc.device.emulation.interfaces.SiloService;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.GoogleTrips;
import com.inthinc.device.emulation.utils.MCMProxyObject;
import com.inthinc.device.emulation.utils.Unique;
import com.inthinc.device.hessian.tcp.AutomationHessianFactory;
import com.inthinc.device.hessian.tcp.HessianException;
import com.inthinc.device.objects.TripDriver;
import com.inthinc.device.resources.DeviceStatistics;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.UniqueValues;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.resources.ObjectReadWrite;
import com.inthinc.pro.automation.utils.AutomationThread;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.selenium.util.AutomationSiloService;

/**
 * This test is used to create x number of drivers and all of the support records.<br />
 * Not an actual test!!!!
 * @author dtanner
 *
 */
public class MassDeviceTest {
    
//	private final Integer accountID=4342;
//	private final Integer groupID=6533;
//	private final Addresses portal = Addresses.DEV;
	

    private final Integer accountID=1;
    private final Integer groupID=10;
    private final Addresses portal = Addresses.TP_INTHINC;
	
	private final String address;
	
	private final AutomationSiloService portalHessian;
	private final SiloService portalProxy;
	private static ConcurrentHashMap<Integer, Map<String, String>> drivers;
	

    private static final String start = "40.710459,-111.993245";
    private static final String stop = "35.446687,-112.003148";
	
	public MassDeviceTest(){
	    Log.info(portal);
	    portalHessian = new AutomationSiloService(portal);
	    portalProxy = new AutomationHessianFactory().getPortalProxy(portal);
        this.address = "src/main/resources/records_created_on_" + portal.name();
	}
	
	public class Maker extends Thread {
	    private Integer i;
	    
	    public Maker(Integer number){
	        this.i = number;
	    }
	    
	    @Override
	    public void run(){
	        try {
    	        Unique unique = new Unique(portal);
    	        HashMap<String, String> objects = new HashMap<String, String>();
                String priEmail = "driver_"+i+"@tiwisucks.com";
                Person person = null;
                try{
                    person = portalHessian.getPerson(priEmail);
                    Driver driver = portalHessian.getDriverByPersonID(person.getPersonID());
                    Vehicle vehicle = portalHessian.getVehicleByDriverID(driver.getDriverID());
                    Device device = portalHessian.getDevice(vehicle.getDeviceID());
                    objects.put("driver", driver.getDriverID().toString());
                    objects.put("person", person.getPersonID().toString());     
                    objects.put("vehicle", vehicle.getVehicleID().toString());
                    objects.put("device", device.getImei());
                    objects.put("deviceID", device.getDeviceID().toString());
                    drivers.put(driver.getDriverID(), objects);
                    Log.info(i);
                    return;
                } catch (Exception e){
                    Log.debug("Ran into an error with " + priEmail + ", error is " + e.getMessage());
                }
                if (person == null){
                    person = new Person();
                    person.setFirst("Automated");
                    person.setLocale(Locale.ENGLISH);
                    person.setTimeZone(TimeZone.getDefault());
                    person.setMeasurementType(MeasurementType.ENGLISH);
                    person.setFuelEfficiencyType(FuelEfficiencyType.MPG_US);
                    person.setAcctID(accountID);
                    person.setPriEmail(priEmail);
                    person.setLast("Driver "+i);
                    person.setPersonID(portalHessian.createPerson(person).getPersonID());
                } 
                
                Driver driver = person.getDriver();
                if (driver==null){
                    try {               
                        driver = portalHessian.getDriverByPersonID(person.getPersonID());
                    } catch (HessianException e){
                    }
                    if (driver==null){
                        driver = new Driver();
                        driver.setGroupID(groupID);
                        driver.setStatus(Status.ACTIVE);
                        driver.setPersonID(person.getPersonID());
                        driver.setDriverID(portalHessian.createDriver(driver).getDriverID());
                    }
                }
                
                
                Vehicle vehicle = new Vehicle();        
                vehicle.setYear(2008);
                com.inthinc.pro.model.State state = new com.inthinc.pro.model.State(45, "Utah", "UT");
                vehicle.setState(state);
                vehicle.setStatus(Status.ACTIVE);
                vehicle.setGroupID(groupID);
                vehicle.setVIN(unique.getUniqueValue(17, UniqueValues.VEHICLE_VIN));
                vehicle.setMake("fake make");
                vehicle.setModel("fake model");
                vehicle.setName("Automated Vehicle " + i);
                vehicle.setVehicleID(portalHessian.createVehicle(groupID, vehicle).getVehicleID());
                
                
                Device device = new Device();
                device.setStatus(DeviceStatus.ACTIVE);
                device.setProductVer(5);
                
                device.setAccountID(accountID);
                device.setFirmwareVersion(17120);
                device.setWitnessVersion(50);
                device.setPhone("8885552222");
                device.setImei(unique.getUniqueValue(15, UniqueValues.DEVICEID_IMEI));
                device.setSerialNum(unique.getUniqueValue(10, UniqueValues.DEVICEID_SERIAL));
                device.setName("Automated Device " + i);
    
                device.setDeviceID(portalHessian.createDevice(device).getDeviceID());
                objects.put("device", device.getImei());
                objects.put("deviceID", device.getDeviceID().toString());
                
                
                portalProxy.setVehicleDevice(vehicle.getVehicleID(), device.getDeviceID());
                portalProxy.setVehicleDriver(vehicle.getVehicleID(), driver.getDriverID());
                
                objects.put("driver", driver.getDriverID().toString());
                objects.put("person", person.getPersonID().toString());     
                objects.put("vehicle", vehicle.getVehicleID().toString());
    
                drivers.put(driver.getDriverID(), objects);
                Log.info(i);
	        } catch (Exception e){
	            new Maker(i).start();
	        }
	    }
	}
	
	public void create(Integer numberOfDrivers){
	    if (drivers == null){
	        drivers = new ConcurrentHashMap<Integer, Map<String, String>>();
	    }
		
		for (int i=drivers.size();i<numberOfDrivers;i++){
		    new Maker(i).start();
		    while (Thread.activeCount() > 50){
                AutomationThread.pause(500l);
            }
		}
		
		while (Thread.activeCount() > 2){
            AutomationThread.pause(1);
            Log.info("There are " + Thread.activeCount() + " threads still running");
        }
		ObjectReadWrite save = new ObjectReadWrite();
		save.writeObject(drivers, address);
	}
	
	@SuppressWarnings("unchecked")
	public void readDrivers(){
		ObjectReadWrite reader = new ObjectReadWrite();
		drivers = (ConcurrentHashMap<Integer, Map<String, String>>) reader.readObject(address).get(0);
//		MCMProxyObject.processDrivers(drivers);
	}
	
	public void driveTiwis(){
        GoogleTrips trip = new GoogleTrips();
        List<GeoPoint> points = trip.getTrip(start, stop);
		Iterator<Integer> itr = drivers.keySet().iterator();

        AutomationCalendar initialTime = new AutomationCalendar();
//        initialTime.setDate(1319726981);
		Log.info(portal);
		
        long start = System.currentTimeMillis();
        
		while (itr.hasNext()){
			Integer next = itr.next();
			TiwiProDevice tiwi = new TiwiProDevice(drivers.get(next).get("device"));
			tiwi.getState().getTime().setDate(initialTime);
        	TripDriver driver = new TripDriver(tiwi);
        	driver.getTripTracker().setPoints(points);
        	driver.run();
			while (Thread.activeCount() > 40){
			    AutomationThread.pause(500l);
			}
		}

		Log.info("All Trips have been started, took " + (System.currentTimeMillis()-start) + " milliseconds to start it");
		
        while (Thread.activeCount() > 2){
		    AutomationThread.pause(1);
		    Log.info("There are " + Thread.activeCount() + " threads still running");
		}
        try{
            MCMProxyObject.closeService();
        } catch (Exception e) {
        }
        Log.info("Starting time is " + DeviceStatistics.getStart());
        Log.info("Ending time is " + DeviceStatistics.getStop());
		Log.info("We made " + DeviceStatistics.getHessianCalls() + " calls");
        Log.info("We took :" + DeviceStatistics.getTimeDelta() + " seconds to run");
        Log.info("This is an average of " + DeviceStatistics.getCallsPerMinute() + " calls per minute");
        
	}
		
	public static void main(String[] args){
		MassDeviceTest test = new MassDeviceTest();
        test.readDrivers();
		test.create(8000);
//		MCMProxyObject.regularNote=false;
//		test.readDrivers();
//		test.fixDevices();
//		test.driveTiwis();
		
	}

}
