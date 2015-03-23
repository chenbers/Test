package com.inthinc.pro.util;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.service.model.VehicleStatus;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import static com.inthinc.pro.model.event.NoteType.*;

/**
 * Determines vehicle status by note status.
 */
@Component
public class VehicleStatusUtil {
    @Autowired
    public EventDAO eventDAO;

    private DateTime lastFoundDateTime;
    private Integer scanHours = 1;
    private int maxNumberOfScans = 10;
    private List<NoteType> scannedNoteTypes;
    private Integer vehicleId;

    /**
     * Bean init.
     */
    @PostConstruct
    public void init() {
        scannedNoteTypes = Arrays.asList(
                TRIP_START,
                TRIP_INPROGRESS,
                TRIP_END,
                BASE,
                FULLEVENT,
                NOTEEVENT,
                SPEEDING,
                THEFT,
                LOCATION,
                GEOFENCE,
                IGNITION_ON,
                IGNITION_OFF,
                ACCELERATION,
                DECELERATION,
                ON_ROAD,
                OFF_ROAD,
                SPEEDING_EX,
                ODOMETER,
                SPEEDING_EX2,
                FUEL_STOP,
                WSZONES_ARRIVAL,
                WSZONES_DEPARTURE,
                SPEEDING_EX3,
                WSZONES_ARRIVAL_EX,
                WSZONES_DEPARTURE_EX,
                IDLE_STATS,
                POWER_ON,
                POWER_INTERRUPTED,
                FUEL_STOP_EX,
                VERTICAL_EVENT,
                PARKING_BRAKE,
                CRASH_DATA_HI_RES,
                VERTICAL_EVENT_SECONDARY,
                SPEEDING_EX4,
                SPEEDING_LOG4,
                SPEEDING_AV,
                START_SPEEDING,
                IDLE,
                ROLLOVER,
                COACHING_SPEEDING,
                START_MOTION,
                STOP_MOTION,
                OFF_HOURS_DRIVING,
                BACKING);
    }

    /**
     * Determines vehicle status by vehicle id and it's last events.
     *
     * @param vehicleId vehicle id
     * @return vehicle status
     */
    public VehicleStatus determineStatusByVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;

        // set the initial last found datetime to now
        lastFoundDateTime = new DateTime();

        // search while status is not determined
        VehicleStatus vehicleStatus = VehicleStatus.NOT_YET_DETERMINED;
        int scans = 0;
        while (vehicleStatus == VehicleStatus.NOT_YET_DETERMINED && scans < maxNumberOfScans) {
            vehicleStatus = getStatus(lastFoundDateTime);
            scans++;
        }

        return vehicleStatus;
    }

    /**
     * Determines the status by looking at the last event between a given date and a
     * configured number of hours before that date. If it doesn't find any events, it will
     * return 'not yet determined'. It should find something in the first try.
     *
     * @param dateTime last date time for found events
     * @return vehicle status
     */
    private VehicleStatus getStatus(DateTime dateTime) {

        // scan for some events
        DateTime newLastFound = dateTime.minusHours(scanHours);
        List<Event> newEvents = eventDAO.getEventsForVehicle(vehicleId, newLastFound.toDate(), dateTime.minusSeconds(1).toDate(), scannedNoteTypes, 0);
        lastFoundDateTime = newLastFound;

        // if the list is empty, return not yet determined
        if (newEvents.isEmpty())
            return VehicleStatus.NOT_YET_DETERMINED;

        // otherwise base your logic on the last event of the list
        ListIterator<Event> it = newEvents.listIterator(newEvents.size());

        while (it.hasPrevious()) {
            Event event = it.previous();

            // based on the last event type
            switch (event.getType()) {
                case TRIP_START:
                case TRIP_INPROGRESS:
                case NOTEEVENT:
                case SPEEDING:
                case ACCELERATION:
                case DECELERATION:
                case ON_ROAD:
                case OFF_ROAD:
                case SPEEDING_EX:
                case SPEEDING_EX2:
                case SPEEDING_EX3:
                case WSZONES_ARRIVAL:
                case WSZONES_DEPARTURE:
                case WSZONES_ARRIVAL_EX:
                case WSZONES_DEPARTURE_EX:
                case VERTICAL_EVENT:
                case VERTICAL_EVENT_SECONDARY:
                case SPEEDING_EX4:
                case SPEEDING_LOG4:
                case SPEEDING_AV:
                case START_SPEEDING:
                case COACHING_SPEEDING:
                case BACKING:
                case FULLEVENT:
                case ROLLOVER:
                case START_MOTION:
                case STOP_MOTION:
                case PARKING_BRAKE:
                case LOCATION:
                case LOCATION_DEBUG:
                    return VehicleStatus.DRIVING;             // the ones above mean driving
                case IDLE:                                    // idle means idle
                    return VehicleStatus.IDLE;
                case IGNITION_ON:
                case POWER_ON:                                // ignition on or power on means standing
                    return VehicleStatus.STANDING;
                case IGNITION_OFF:
                case POWER_INTERRUPTED:                      // ignition off or power off means parking
                    return VehicleStatus.PARKING;
                default:
                    continue;
            }
        }
        return VehicleStatus.NOT_YET_DETERMINED;
    }

    public Integer getScanHours() {
        return scanHours;
    }

    public void setScanHours(Integer scanHours) {
        this.scanHours = scanHours;
    }

    public EventDAO getEventDAO() {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }
}
