package com.inthinc.pro.automation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

import com.inthinc.pro.automation.deviceTrips.HanSoloTrip;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.UniqueValues;
import com.inthinc.pro.automation.enums.Values;
import com.inthinc.pro.automation.utils.AutomationHessianFactory;
import com.inthinc.pro.automation.utils.AutomationThread;
import com.inthinc.pro.automation.utils.AutomationSiloService;
import com.inthinc.pro.automation.utils.ObjectReadWrite;
import com.inthinc.pro.automation.utils.RandomValues;
import com.inthinc.pro.automation.utils.Unique;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.configurator.ProductType;

public class ReportTest {
	private final Integer accountID=32;
	private final Integer groupID=53;
	private static final Addresses portal = Addresses.QA;
	
	private static final String address = "ReportTest Stuff";
	
	private final AutomationSiloService portalHessian = new AutomationSiloService(portal);
	private final SiloService portalProxy = new AutomationHessianFactory().getPortalProxy(portal);
	private HashMap<Integer, HashMap<String, String>> drivers;
	
	public void create(Integer numberOfDrivers){
		Unique unique = new Unique(portal);
		RandomValues random = new RandomValues();
		
		drivers = new HashMap<Integer, HashMap<String, String>>();
		
		
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
	            drivers.put(driver.getDriverID(), objects);
			    continue;
			} catch (Exception e){
			    e.printStackTrace();
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
			device.setProductVersion(ProductType.TIWIPRO_R74);
			device.setAccountID(accountID);
			device.setFirmwareVersion(17120);
			device.setWitnessVersion(50);
			device.setPhone("8885552222");
			device.setImei(unique.getUniqueValue(15, UniqueValues.DEVICEID_IMEI));
			device.setSerialNum(unique.getUniqueValue(10, UniqueValues.DEVICEID_SERIAL));
			device.setName("Automated Device " + i);

			objects.put("device", device.getImei());
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
		drivers = (HashMap<Integer, HashMap<String, String>>) reader.readObject(address).get(0);
	}
	
	public void driveTiwis(){
		Integer size = drivers.size();
		Integer i = size;
		Integer threads = 30;
		Iterator<Integer> itr = drivers.keySet().iterator();
		HanSoloTrip[] trips = new HanSoloTrip[size];

		while (itr.hasNext()){
			Integer driverID = itr.next();
			Long currentTime = System.currentTimeMillis()/1000;
	        Integer initialTime = currentTime.intValue();
	        Addresses address = Addresses.QA;
	        trips[--i]=new HanSoloTrip();
	        trips[i].start(drivers.get(driverID).get("device"), address, 1315327860);
//	        trips[i].start("DEVICEDOESNTEXIST", address, 1315326435);
	        AutomationThread.pause(1);
		}
	}
		
	public static void main(String[] args){
		ReportTest test = new ReportTest();
		test.create(1000);
		test.readDrivers();
		test.driveTiwis();
	}

}
