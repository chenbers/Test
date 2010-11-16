package com.inthinc.pro.service.adapters;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.dao.report.DriverReportDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Duration;
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
 
	private DriverDAO driverDAO;
    private DriverReportDAO driverReportDAO;
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

    public Score getScore(Integer driverID, Duration duration) {
        return driverReportDAO.getScore(driverID, duration);
    }

    public List<Event> getSpeedingEvents(Integer driverID) {
        DateTime dateTime = new DateTime();
        return eventDAO.getViolationEventsForDriver(driverID, dateTime.minusMonths(1).toDate(), dateTime.toDate(), 1);
    }    

    public List<Trend> getTrend(Integer driverID, Duration duration) {
        return driverReportDAO.getTrend(driverID, duration);
    }

	// Getters and setters -----------------------------------------------------
    
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
    
    
}
