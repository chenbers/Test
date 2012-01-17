package com.inthinc.pro.selenium.testSuites;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Level;

import com.inthinc.device.emulation.interfaces.SiloService;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.MCMProxyObject;
import com.inthinc.device.emulation.utils.Unique;
import com.inthinc.device.objects.TripDriver;
import com.inthinc.device.resources.DeviceStatistics;
import com.inthinc.emulation.hessian.AutomationHessianFactory;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.ProductType;
import com.inthinc.pro.automation.enums.UniqueValues;
import com.inthinc.pro.automation.enums.Values;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.resources.ObjectReadWrite;
import com.inthinc.pro.automation.utils.AutomationThread;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.automation.utils.RandomValues;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.selenium.util.AutomationSiloService;

/**
 * This test is used to create x number of drivers and all of the support records.<br />
 * Not an actual test!!!!
 * @author dtanner
 *
 */
public class ReportTest {
    
	private final Integer accountID=4342;
	private final Integer groupID=6533;
	private final Addresses portal;
	
	private final String address;
	
	private final AutomationSiloService portalHessian;
	private final SiloService portalProxy;
	private static Map<Integer, Map<String, String>> drivers;
	
	public ReportTest(Addresses address){
	    portal = address;
	    portalHessian = new AutomationSiloService(address);
	    portalProxy = new AutomationHessianFactory().getPortalProxy(portal);
        this.address = "src/main/resources/records_created_on_" + address.name();
	}
	
	public void create(Integer numberOfDrivers){
		Unique unique = new Unique(portal);
		RandomValues random = new RandomValues();
		
		drivers = new HashMap<Integer, Map<String, String>>();
		
		
		for (int i=1;i<=numberOfDrivers;i++){

			HashMap<String, String> objects = new HashMap<String, String>();
			String priEmail = "driver_"+i+"@tiwisucks.com";
			
			try{
			    Person person = portalHessian.getPerson(priEmail);
			    Driver driver = portalHessian.getDriverByPersonID(person.getPersonID());
			    Vehicle vehicle = portalHessian.getVehicleByDriverID(driver.getDriverID());
			    Device device = portalHessian.getDevice(vehicle.getDeviceID());
			    objects.put("driver", driver.getDriverID().toString());
	            objects.put("person", person.getPersonID().toString());     
	            objects.put("vehicle", vehicle.getVehicleID().toString());
	            objects.put("device", device.getImei());
	            objects.put("deviceID", device.getDeviceID().toString());
	            drivers.put(driver.getDriverID(), objects);
			    continue;
			} catch (Exception e){
			    MasterTest.print("Ran into an error with " + priEmail + ", error is " + e.getMessage(), Level.DEBUG);
			}
			
			Person person = new Person();
			person.setFirst("Automated");
			person.setLocale(Locale.ENGLISH);
			person.setTimeZone(TimeZone.getDefault());
			person.setMeasurementType(MeasurementType.ENGLISH);
			person.setFuelEfficiencyType(FuelEfficiencyType.MPG_US);
			person.setAcctID(accountID);
			person.setPriEmail(priEmail);
			person.setLast("Driver "+i);
			person.setPersonID(portalHessian.createPerson(person).getPersonID());
			
			
			Driver driver = new Driver();
			driver.setGroupID(groupID);
			driver.setStatus(Status.ACTIVE);
			driver.setPersonID(person.getPersonID());
			driver.setDriverID(portalHessian.createDriver(driver).getDriverID());
			
			
			Vehicle vehicle = new Vehicle();		
			vehicle.setYear(Integer.parseInt(random.getValue(Values.YEAR)));
			State state = new State(random.getStateByName("Utah"), "Utah", "UT");
			vehicle.setState(state);
			vehicle.setStatus(Status.ACTIVE);
			vehicle.setGroupID(groupID);
			vehicle.setVIN(unique.getUniqueValue(17, UniqueValues.VEHICLE_VIN));
			vehicle.setMake(random.getValue(Values.MAKE));
			vehicle.setModel(random.getValue(Values.MODEL));
			vehicle.setName("Automated Vehicle " + i);
			vehicle.setVehicleID(portalHessian.createVehicle(groupID, vehicle).getVehicleID());
			
			
			Device device = new Device();
			device.setStatus(DeviceStatus.ACTIVE);
			device.setProductVersion(com.inthinc.pro.model.configurator.ProductType.TIWIPRO_R74);
			device.setAccountID(accountID);
			device.setFirmwareVersion(17120);
			device.setWitnessVersion(50);
			device.setPhone("8885552222");
			device.setImei(unique.getUniqueValue(15, UniqueValues.DEVICEID_IMEI));
			device.setSerialNum(unique.getUniqueValue(10, UniqueValues.DEVICEID_SERIAL));
			device.setName("Automated Device " + i);

			objects.put("device", device.getImei());
            objects.put("deviceID", device.getDeviceID().toString());
			device.setDeviceID(portalHessian.createDevice(device).getDeviceID());
			
			
			portalProxy.setVehicleDevice(vehicle.getVehicleID(), device.getDeviceID());
			portalProxy.setVehicleDriver(vehicle.getVehicleID(), driver.getDriverID());
			
			objects.put("driver", driver.getDriverID().toString());
			objects.put("person", person.getPersonID().toString());		
			objects.put("vehicle", vehicle.getVehicleID().toString());

			drivers.put(driver.getDriverID(), objects);
			AutomationThread.pause(250l);
		}
		ObjectReadWrite save = new ObjectReadWrite();
		save.writeObject(drivers, address);
	}
	
	@SuppressWarnings("unchecked")
	public void readDrivers(){
		ObjectReadWrite reader = new ObjectReadWrite();
		drivers = (HashMap<Integer, Map<String, String>>) reader.readObject(address).get(0);
//		MCMProxyObject.processDrivers(drivers);
	}
	
	public void driveTiwis(){
		Iterator<Integer> itr = drivers.keySet().iterator();

        AutomationCalendar initialTime = new AutomationCalendar();
        initialTime.setDate(1319726981);
		MasterTest.print(portal);
		
        long start = System.currentTimeMillis();
        
		while (itr.hasNext()){
			Integer next = itr.next();
			DeviceState state = new DeviceState(drivers.get(next).get("device"), ProductType.TIWIPRO_R74);
//		for (int i=0; i<20; i++){
//			DeviceState state = new DeviceState("DEVICEDOESNTEXIST", ProductType.TIWIPRO_R74);
        	TripDriver driver = new TripDriver(state);
        	driver.run();
			if (Thread.activeCount() >= 2){
			    break;
			}
			while (Thread.activeCount() > 50){
			    AutomationThread.pause(500l);
			    break;
			}
		}

		MasterTest.print("All Trips have been started, took " + (System.currentTimeMillis()-start) + " milliseconds to start it");
		
        while (Thread.activeCount() > 2){
		    AutomationThread.pause(1);
		    MasterTest.print("There are " + Thread.activeCount() + " threads still running", Level.DEBUG);
		}
        try{
            MCMProxyObject.closeService();
        } catch (Exception e) {
        }
        MasterTest.print("Starting time is " + DeviceStatistics.getStart());
        MasterTest.print("Ending time is " + DeviceStatistics.getStop());
		MasterTest.print("We made " + DeviceStatistics.getHessianCalls() + " calls");
        MasterTest.print("We took :" + DeviceStatistics.getTimeDelta() + " seconds to run");
        MasterTest.print("This is an average of " + DeviceStatistics.getCallsPerMinute() + " calls per minute");
        
	}
		
	public static void main(String[] args){
		ReportTest test = new ReportTest(Addresses.DEV);
//		test.create(5000);
//		MCMProxyObject.regularNote=false;
//		test.readDrivers();
		test.driveTiwis();
		
	}

}
