package com.inthinc.pro.service.phonecontrol.impl;

import java.util.Set;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.service.phonecontrol.Clock;
import com.inthinc.pro.service.phonecontrol.PhoneWatchdog;
import com.inthinc.pro.service.phonecontrol.dao.DriverPhoneDAO;

/**
 * Watchdog to enable phones in case the communication is lost.
 * 
 * @author dcueva
 */
@Component("phoneWatchdogImpl")
public class PhoneWatchdogImpl implements PhoneWatchdog {

    private static final Logger logger = Logger.getLogger(PhoneWatchdogImpl.class);

    @Autowired
    private DriverPhoneDAO driverPhoneDAO;

    @Autowired
    private Clock systemClock;

    @Autowired
    private DriverDAO driverDAO;

    @Autowired
    PhoneControlMovementEventHandler phoneControl;

    private Integer secondsAgoEvents = new Integer(180);

    // Default value: check for events in the last 3 minutes (180 sec).
    // Can be modified in the Spring configuration

    @Override
    public void enablePhonesWhenLostComm() {

        // Fetch IDs of drivers with disabled phones
        logger.debug("Phone Watchdog started");
        Set<Integer> driverIDSet = driverPhoneDAO.getDriversWithDisabledPhones();
        logger.debug("Verifying communication for the following drivers: " + driverIDSet.toString());

        // Fetch events for each driver
        for (Integer driverID : driverIDSet) {

            LastLocation location = driverDAO.getLastLocation(driverID);
            logger.debug("Backend returned " + location + " as last location for driver " + driverID + ".");

            DateTime now = new DateTime(systemClock);
            DateTime lastEvent = new DateTime(location.getTime());
            Seconds secondsSinceLastEvent = Seconds.secondsBetween(lastEvent, now);

            if (secondsSinceLastEvent.get(secondsSinceLastEvent.getFieldType()) > secondsAgoEvents) {
                logger.debug("A communication loss is suspected for driver " + driverID + ". Sending a request to enable the phone.");
                phoneControl.handleDriverStoppedMoving(driverID);
            }
        }
    }

    /**
     * Get the number of seconds ago to check for events, starting from the present time.
     * 
     * @return the secondsAgoEvents
     */
    public Integer getSecondsAgoEvents() {
        return secondsAgoEvents;
    }

    /**
     * Set the number of seconds ago to check for events, starting from the present time.
     * 
     * @param secondsAgoEvents
     *            the secondsAgoEvents to set
     */
    public void setSecondsAgoEvents(Integer secondsAgoEvents) {
        this.secondsAgoEvents = secondsAgoEvents;
    }

    public void setDriverPhoneDAO(DriverPhoneDAO driverPhoneDAO) {
        this.driverPhoneDAO = driverPhoneDAO;
    }

    public void setSystemClock(Clock systemClock) {
        this.systemClock = systemClock;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public void setPhoneControl(PhoneControlMovementEventHandler phoneControl) {
        this.phoneControl = phoneControl;
    }

}
