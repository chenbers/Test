package com.inthinc.pro.service.adapters;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.report.VehicleReportDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.Trend;
import com.inthinc.pro.model.event.Event;

/**
 * Adapter for the Vehicle resources.
 * 
 * @author dcueva
 *
 */
@Component
public class VehicleDAOAdapter extends BaseDAOAdapter<Vehicle> {

    @Autowired
    private VehicleDAO vehicleDAO;
    @Autowired
    private EventDAO eventDAO;   
    @Autowired
    private VehicleReportDAO vehicleReportDAO;    
	
//    @Autowired
//    private ConfiguratorDAO configuratorDAO;
    
	@Override
	public List<Vehicle> getAll() {
        return vehicleDAO.getVehiclesInGroupHierarchy(getGroupID());	
    }

	@Override
	protected GenericDAO<Vehicle, Integer> getDAO() {
		return vehicleDAO;
	}

    /**
     * Retrieve the ID to be used in the creation of the vehicle. </br>
     * Overriding because we need the Group ID, not the account ID (default).</br>
     * The create() method from BaseDAOAdapter will call this overriden method.</br>
     * 
     * @see com.inthinc.pro.service.adapters.BaseDAOAdapter#getResourceCreationID(java.lang.Object)
	 */
	@Override
	protected Integer getResourceCreationID(Vehicle vehicle) {
		return vehicle.getGroupID();
	}

	@Override
	protected Integer getResourceID(Vehicle vehicle) {
		return vehicle.getVehicleID();
	}	

	// Specialized methods ----------------------------------------------------

    public Vehicle assignDevice(Integer id, Integer deviceID) {
        vehicleDAO.setVehicleDevice(id, deviceID);
        return vehicleDAO.findByID(id);
    }	

    public Vehicle assignDriver(Integer id, Integer driverID) {
        vehicleDAO.setVehicleDriver(id, driverID);
        return vehicleDAO.findByID(id);
    }    

    public Vehicle findByVIN(String vin) {
        return vehicleDAO.findByVIN(vin);
    }    

    public List<Event> getEvents(Integer vehicleID, Date startDate, Date endDate) {
        return eventDAO.getEventsForVehicle(vehicleID, startDate, endDate, null, 0);
    }    

    public LastLocation getLastLocation(Integer vehicleID) {
        return vehicleDAO.getLastLocation(vehicleID);
    }    

    public Score getScore(Integer vehicleID, Duration duration) {
        return vehicleReportDAO.getScore(vehicleID, duration);
    }    

    public List<Trend> getTrend(Integer vehicleID, Duration duration) {
        return vehicleReportDAO.getTrend(vehicleID, duration);
    }
    
    public List<Trip> getTrips(Integer vehicleID, Date startDate, Date endDate) {
        return vehicleDAO.getTrips(vehicleID, startDate, endDate);
    }
    public Trip getLastTrip(Integer vehicleID) {
        return vehicleDAO.getLastTrip(vehicleID);
    }

	// Getters and setters -----------------------------------------------------
    
	/**
	 * @return the vehicleDAO
	 */
	public VehicleDAO getVehicleDAO() {
		return vehicleDAO;
	}

	/**
	 * @param vehicleDAO the vehicleDAO to set
	 */
	public void setVehicleDAO(VehicleDAO vehicleDAO) {
		this.vehicleDAO = vehicleDAO;
	}

	/**
	 * @return the eventDAO
	 */
	public EventDAO getEventDAO() {
		return eventDAO;
	}

	/**
	 * @param eventDAO the eventDAO to set
	 */
	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

	/**
	 * @return the vehicleReportDAO
	 */
	public VehicleReportDAO getVehicleReportDAO() {
		return vehicleReportDAO;
	}

	/**
	 * @param vehicleReportDAO the vehicleReportDAO to set
	 */
	public void setVehicleReportDAO(VehicleReportDAO vehicleReportDAO) {
		this.vehicleReportDAO = vehicleReportDAO;
	}

//	public ConfiguratorDAO getConfiguratorDAO() {
//		return configuratorDAO;
//	}
//
//	public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
//		this.configuratorDAO = configuratorDAO;
//	}
//
//	@Override
//	public Vehicle update(Vehicle vehicle) {
//		updateDotSettingIfRequired(vehicle);
//		return super.update(vehicle);
//	}
//	private void updateDotSettingIfRequired(Vehicle vehicle){
//		if ( vehicle.getDot() != null){
//			Map<Integer, String> settingMap = getSettingMap(vehicle.getDot());
//			Integer userID = tiwiProPrincipal.getUser().getUserID();
//			try {
//				configuratorDAO.updateVehicleSettings(vehicle.getVehicleID(), settingMap, userID, "Web service update");
//			} catch (EmptyResultSetException e) {
//            
//			}
//		}
//	}
//
//	@Override
//	public Integer create(Vehicle vehicle) {
//		Integer vehicleID = super.create(vehicle);
//		if (vehicleID != null){
//			vehicle.setVehicleID(vehicleID);
//			createDotSettingIfRequired(vehicle);
//		}
//		return vehicleID;
//	}
//	private void createDotSettingIfRequired(Vehicle vehicle){
//		if ( vehicle.getDot() != null){
//			Map<Integer, String> settingMap =  getSettingMap(vehicle.getDot());
//			Integer userID = tiwiProPrincipal.getUser().getUserID();
//			try {
//				configuratorDAO.setVehicleSettings(vehicle.getVehicleID(), settingMap, userID, "Web service create");
//			} catch (EmptyResultSetException e) {
//			}
//		}
//	}
//	private Map<Integer,String> getSettingMap(VehicleDOTType vehicleDOTType){
//		Map<Integer, String> settingMap = new HashMap<Integer, String>();
//		settingMap.put(1261, ""+vehicleDOTType.getConfiguratorSetting());
//		return settingMap;
//	}
}
