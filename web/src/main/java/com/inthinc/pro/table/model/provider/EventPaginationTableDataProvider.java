package com.inthinc.pro.table.model.provider;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Vehicle;
import org.apache.log4j.Logger;

import com.inthinc.pro.dao.EventDAO;
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

        populateDeviceNames(eventList);
        return eventList;
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

}
