package com.inthinc.pro.backing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Vehicle;

public class CacheBean  extends BaseBean 
{
    /**
     * 
     */
    private static final long serialVersionUID = -5482494970488726972L;

    private static final Logger logger = Logger.getLogger(CacheBean.class);

    // Spring managed beans
    private DriverDAO driverDAO;
	private VehicleDAO vehicleDAO;

	Map<Integer, Driver> driverMap;
	Map<Integer, Vehicle> vehicleMap;
	
	
    public Map<Integer, Driver> getDriverMap() {
    	if (driverMap == null)
    		driverMap = fetchDriverMap();
		return driverMap;
	}
	private Map<Integer, Driver> fetchDriverMap() {
        List<Driver> driverList = driverDAO.getAllDrivers(getUser().getGroupID());
        Map<Integer, Driver> driverMap = new HashMap<Integer, Driver>();
        for (Driver driver : driverList) {
        	driverMap.put(driver.getDriverID(), driver);
        }

		return driverMap;
	}
	public void setDriverMap(Map<Integer, Driver> driverMap) {
		this.driverMap = driverMap;
	}
	public Map<Integer, Vehicle> getVehicleMap() {
		if (vehicleMap == null)
			vehicleMap = fetchVehicleMap();
		return vehicleMap;
	}
	private Map<Integer, Vehicle> fetchVehicleMap() {
        List<Vehicle> vehicleList = vehicleDAO.getVehiclesInGroupHierarchy(getUser().getGroupID());
        Map<Integer, Vehicle> vehicleMap = new HashMap<Integer, Vehicle>();
        for (Vehicle vehicle : vehicleList) {
        	vehicleMap.put(vehicle.getVehicleID(), vehicle);
        }
		
		return vehicleMap;
	}
	public void setVehicleMap(Map<Integer, Vehicle> vehicleMap) {
		this.vehicleMap = vehicleMap;
	}
	public DriverDAO getDriverDAO() {
		return driverDAO;
	}
	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
	}
	public VehicleDAO getVehicleDAO() {
		return vehicleDAO;
	}
	public void setVehicleDAO(VehicleDAO vehicleDAO) {
		this.vehicleDAO = vehicleDAO;
	}
}
