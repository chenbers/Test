package com.inthinc.pro.table.model.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.*;
import com.inthinc.pro.model.*;
import org.apache.log4j.Logger;

import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.pagination.PageParams;

public class EventPaginationTableDataProvider  extends BaseNotificationPaginationDataProvider<Event> {

    /**
	 *
	 */
	private static final long serialVersionUID = -2324384773462992656L;

    private static final Logger logger = Logger.getLogger(EventPaginationTableDataProvider.class);

	private EventDAO                eventDAO;
	private Integer 				groupID;
	private EventCategory			eventCategory;
    private VehicleDAO              vehicleDAO;
    private DeviceDAO               deviceDAO;
	private LocationDAO 			locationDAO;

	public EventPaginationTableDataProvider() {

//		logger.info("EventPaginationTableDataProvider:constructor");
	}

	@Override
	public List<Event> getItemsByRange(int firstRow, int endRow) {

		if (endRow < 0) {
			return new ArrayList<Event>();
		}
		if (endDate == null || startDate == null) {
			initStartEndDates();
		}

		PageParams pageParams = new PageParams(firstRow, endRow, getSort(), getFilters());
        List<Event> eventList = eventDAO.getEventPage(groupID, startDate, endDate, EventDAO.INCLUDE_FORGIVEN, eventCategory.getNoteTypesInCategory(), pageParams);
		eventList = replaceZeroLocationsWithLastLocations(eventList);

        populateDeviceNames(eventList);
        return eventList;
	}

	private List<Event> replaceZeroLocationsWithLastLocations(List<Event> eventList) {
		List<Event> newEventList = new ArrayList<Event>();
		for (Event event : eventList) {
			if (event != null && event.getLatitude() == 0 && event.getLongitude() == 0) {
				LastLocation lastLocation = locationDAO.getLastLocationForDriver(event.getDriverID());
				if (lastLocation == null)
					lastLocation = locationDAO.getLastLocationForVehicle(event.getVehicleID());
				if (lastLocation != null && lastLocation.getLoc() != null) {
					LatLng loc = lastLocation.getLoc();
					event.setLatitude(loc.getLatitude());
					event.setLongitude(loc.getLongitude());
				}
			}
			newEventList.add(event);
		}
		return newEventList;
	}

	@Override
	public int getRowCount() {

		if (groupID == null) {
			return 0;
		}
		initStartEndDates();
		return eventDAO.getEventCount(groupID, startDate, endDate, EventDAO.INCLUDE_FORGIVEN, eventCategory.getNoteTypesInCategory(), getFilters());
	}

    /**
     * Adds device names to event list.
     *
     * @param events event list.
     */
    public void populateDeviceNames(List<Event> events) {
        for (Event event : events) {
            if (event.getDeviceName() == null || event.getDeviceName().trim().isEmpty()) {
                Integer deviceId = event.getDeviceID();
                if (deviceId == null) {
                    Integer vehicleId = event.getVehicleID();
                    if (vehicleId != null) {
                        Vehicle vehicle = vehicleDAO.findByID(vehicleId);
                        deviceId = vehicle.getDeviceID();
                    }
                }

                if (deviceId != null) {
                    Device device = deviceDAO.findByID(deviceId);
                    event.setDeviceName(device.getProductVersion().name());
                }
            }
        }
    }

    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public DeviceDAO getDeviceDAO() {
        return deviceDAO;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

	public EventDAO getEventDAO() {
		return eventDAO;
	}

	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

	public Integer getGroupID() {
		return groupID;
	}

	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}

	public EventCategory getEventCategory() {
		return eventCategory;
	}

	public void setEventCategory(EventCategory eventCategory) {
		this.eventCategory = eventCategory;
	}

	public LocationDAO getLocationDAO() {
		return locationDAO;
	}

	public void setLocationDAO(LocationDAO locationDAO) {
		this.locationDAO = locationDAO;
	}
}
