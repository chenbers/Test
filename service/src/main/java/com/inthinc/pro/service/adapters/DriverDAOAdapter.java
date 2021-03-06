package com.inthinc.pro.service.adapters;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.CustomDuration;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.model.Person;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.report.DriverReportDAO;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.Trend;
import com.inthinc.pro.model.event.Event;

/**
 * Adapter for the Driver resources.
 *
 * @author dcueva
 */
@Component
public class DriverDAOAdapter extends BaseDAOAdapter<Driver> {

    @Autowired
	private GroupDAO groupDAO;
    @Autowired
	private DriverDAO driverDAO;
    @Autowired
    private PersonDAO personDAO;
    @Autowired
    private DriverReportDAO driverReportDAO;
    @Autowired
    private GroupReportDAO groupReportDAO;
    @Autowired
    private EventDAO eventDAO;

	@Override
	public List<Driver> getAll() {
		return driverDAO.getAllDrivers(getGroupID());
	}

	@Override
	protected GenericDAO<Driver, Integer> getDAO() {
		return driverDAO;
	}

    /**
     * Retrieve the ID to be used in the creation of the driver. </br>
     * Overriding because we need the Person ID, not the account ID (default).</br>
     * The create() method from BaseDAOAdapter will call this overriden method.</br>
     *
	 * @see com.inthinc.pro.service.adapters.BaseDAOAdapter#getAccountID(java.lang.Object)
	 */
	@Override
	protected Integer getResourceCreationID(Driver driver) {
		return driver.getPersonID();
	}

	@Override
	protected Integer getResourceID(Driver driver) {
		return driver.getDriverID();
	}

	// Specialized methods ----------------------------------------------------

//    /**
//     * Lookup for driver by Phone number.
//     * @param phoneNumber the phone number
//     * @return the Driver having assigned the phone number, if not found - null.
//     */
//    public Driver findByPhoneNumber(String phoneNumber) {
//        return driverDAO.findByPhoneNumber(phoneNumber);
//    }    

    public Score getScore(Integer driverID, Duration duration) {
        return driverReportDAO.getScore(driverID, duration);
    }

    public Score getScore(Integer driverID, CustomDuration customDuration) {
        return driverReportDAO.getScore(driverID, customDuration);
    }

    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, Interval interval) {
		Group group = groupDAO.findByID(groupID);
		GroupHierarchy gh = new GroupHierarchy(groupDAO.getGroupHierarchy(group.getAccountID(), getGroupID()));
        return groupReportDAO.getDriverScores(groupID, interval, gh);
    }

    public List<Event> getSpeedingEvents(Integer driverID) {
        DateTime dateTime = new DateTime();
        return eventDAO.getViolationEventsForDriver(driverID, dateTime.minusMonths(1).toDate(), dateTime.toDate(), 1);
    }

    public List<Trend> getTrend(Integer driverID, Duration duration) {
        return driverReportDAO.getTrend(driverID, duration);
    }

    public List<Trend> getTrend(Integer driverID, CustomDuration customDuration) {
        return driverReportDAO.getTrend(driverID, customDuration);
    }

    public Trip getLastTrip(Integer driverID) {
        return driverDAO.getLastTrip(driverID);
    }

    public LastLocation getLastLocation(Integer driverID) {
        return driverDAO.getLastLocation(driverID);
    }

    public List<Trip> getLastTrips(Integer driverID, Date startDate) {
        Date today = new Date();
        return driverDAO.getTrips(driverID, startDate, today);
    }

    public List<Trip> getTrips(Integer driverID, Date fromDateTime, Date toDateTime) {
        return driverDAO.getTrips(driverID, fromDateTime, toDateTime);
    }
    public List<DriverLocation> getDriverLocations(Integer groupID) {
        return driverDAO.getDriverLocations(groupID);
    }

    // Getters and setters -----------------------------------------------------

	/**
	 * @return the groupDAO
	 */
	public GroupDAO getGroupDAO() {
		return groupDAO;
	}

	/**
	 * @param driverDAO the driverDAO to set
	 */
	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}

	/**
	 * @return the driverDAO
	 */
	public DriverDAO getDriverDAO() {
		return driverDAO;
	}

	/**
	 * @param driverDAO the driverDAO to set
	 */
	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
	}

	/**
	 * @return the driverReportDAO
	 */
	public DriverReportDAO getDriverReportDAO() {
		return driverReportDAO;
	}

	/**
	 * @param driverReportDAO the driverReportDAO to set
	 */
	public void setDriverReportDAO(DriverReportDAO driverReportDAO) {
		this.driverReportDAO = driverReportDAO;
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

	public GroupReportDAO getGroupReportDAO() {
		return groupReportDAO;
	}

	public void setGroupReportDAO(GroupReportDAO groupReportDAO) {
		this.groupReportDAO = groupReportDAO;
	}

    /**
     * Gets the driver associated with a person that has the given employee id or null if not found.
     *
     * @param empID employee id
     * @return driver or null if not found
     */
    public Driver getDriverByEmpID(String empID) {
        Person person = personDAO.findByEmpID(getAccountID(), empID);
        if (person != null) {
            Driver driver = driverDAO.findByPersonID(person.getPersonID());
            if (driver != null) {
                return driver;
            }
        }

        return null;
    }

    /**
     * Gets the driverID associated with a person that has the given employee id or null if not found.
     *
     * @param empID employee id
     * @return driverID or null if not found
     */
    public Integer getDriverIDByEmpID(String empID) {
        Driver driver = getDriverByEmpID(empID);
        if (driver != null) {
            return driver.getDriverID();
        }

        return null;
    }
}
