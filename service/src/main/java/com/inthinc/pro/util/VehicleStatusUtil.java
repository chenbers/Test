package com.inthinc.pro.util;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.service.model.VehicleStatus;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import static com.inthinc.pro.model.event.NoteType.*;

/**
 * Determines vehicle status by note status.
 */
@Component
public class VehicleStatusUtil {
    @Autowired
    public EventDAO eventDAO;

    private Integer maxNumTries = 5;
    private Integer scanHours = 1;
    private List<NoteType> scannedNoteTypes;
    private Integer vehicleId;

    private final String IGN_ON_KEY = "ignition_on";
    private final String IGN_OFF_KEY = "ignition_off";
    private final String IDL_KEY = "idling";
    private final String LOC_KEY = "location";

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
        DateTime lastFoundDateTime = new DateTime();

        // set the initial notable events as empty
        Map<String, Event> notableEvents = new HashMap<String, Event>();

        // set the current num tries to 0
        Integer numTries = 0;

        // execute the recursive logic function
        return getStatusRecursive(lastFoundDateTime, notableEvents, numTries);
    }

    /**
     * Determines the status recursively.
     *
     * @param lastFoundDateTime last date time for found events
     * @param notableEvents     notable events
     * @param numTries          number of tries
     * @return vehicle status
     */
    private VehicleStatus getStatusRecursive(DateTime lastFoundDateTime, Map<String, Event> notableEvents, Integer numTries) {

        // if we exceeded the max tries, semi-lie - it's probably parked
        if (numTries > maxNumTries) {
            return VehicleStatus.PARKING;
        }

        // scan for some events
        DateTime newLastFound = lastFoundDateTime.minusHours(scanHours);
        List<Event> newEvents = eventDAO.getEventsForVehicle(vehicleId, newLastFound.toDate(), lastFoundDateTime.minusSeconds(1).toDate(), scannedNoteTypes, 0);

        // if the event list is not empty, treat it and try to come to a determination,
        if (!newEvents.isEmpty()) {

            /**
             * Walk backwards on the event list.
             * The presence of some evetns trigger an immediate return (example IGNITION ON OR OFF).
             * The presence of others can help determine the status after another iteration.
             */
            ListIterator<Event> it = newEvents.listIterator(newEvents.size());
            while(it.hasPrevious()){
                Event event = it.previous();
                switch(event.getType()){
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
                        return VehicleStatus.DRIVING;             // if the last event is a motion event then it's driving
                    case FULLEVENT:
                    case ROLLOVER:
                    case STOP_MOTION:
                    case PARKING_BRAKE:
                        return VehicleStatus.PARKING;             // if the last event is ignition on or park break then it's parking
                    case LOCATION:
                    case LOCATION_DEBUG:
                        notableEvents.put(LOC_KEY, event);        // if the last event is location, it's stored in notable events
                        break;
                    case IDLE:                                    // idle means idle
                        return VehicleStatus.IDLE;
                    case IGNITION_ON:                             // ignition on has custom logic (if it had onr not previous location events)
                        if (notableEvents.containsKey(LOC_KEY))
                            return VehicleStatus.DRIVING;
                        else
                            return VehicleStatus.STANDING;
                    case IGNITION_OFF:                            // ignition off is parking
                        return VehicleStatus.PARKING;
                }
            }
        }

        // retry
        return getStatusRecursive(newLastFound, notableEvents, ++numTries);
    }

    public Integer getMaxNumTries() {
        return maxNumTries;
    }

    public void setMaxNumTries(Integer maxNumTries) {
        this.maxNumTries = maxNumTries;
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
