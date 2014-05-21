package com.inthinc.pro.util;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.service.model.VehicleStatus;
import mockit.Expectations;
import mockit.Mock;
import mockit.Mocked;
import mockit.Expectations;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test case for {@link com.inthinc.pro.util.VehicleStatusUtil}.
 */
public class VehicleStatusUtilTest {
    private static final Integer VEHICLE_ID = 1;

    @Mocked
    private EventDAO mockEventDao;

    private VehicleStatusUtil vehicleStatusUtil;

    @Before
    public void before(){
        vehicleStatusUtil = new VehicleStatusUtil();
        vehicleStatusUtil.setEventDAO(mockEventDao);
        vehicleStatusUtil.setMaxNumTries(5);
        vehicleStatusUtil.setScanHours(1);
    }

    @Test
    public void testDrivingWithLastElem(){

        new Expectations() {{
            mockEventDao.getEventsForVehicle(VEHICLE_ID, withInstanceOf(Date.class), withInstanceOf(Date.class),(List<NoteType>)any, 0);
            times = 1;
            result = makeEvents(Arrays.asList(NoteType.PARKING_BRAKE, NoteType.ACCELERATION));
        }};

        VehicleStatus status = vehicleStatusUtil.determineStatusByVehicleId(VEHICLE_ID);
        assertEquals(status, VehicleStatus.DRIVING);
    }

    @Test
    public void testDrivingWithMiddleElem(){

        new Expectations() {{
            mockEventDao.getEventsForVehicle(VEHICLE_ID, withInstanceOf(Date.class), withInstanceOf(Date.class),(List<NoteType>)any, 0);
            times = 1;
            result = makeEvents(Arrays.asList(NoteType.IGNITION_ON, NoteType.LOCATION));
        }};

        VehicleStatus status = vehicleStatusUtil.determineStatusByVehicleId(VEHICLE_ID);
        assertEquals(status, VehicleStatus.DRIVING);
    }

    @Test
    public void testIdlingWithLastElem(){

        new Expectations() {{
            mockEventDao.getEventsForVehicle(VEHICLE_ID, withInstanceOf(Date.class), withInstanceOf(Date.class),(List<NoteType>)any, 0);
            times = 1;
            result = makeEvents(Arrays.asList(NoteType.PARKING_BRAKE, NoteType.IDLE));
        }};

        VehicleStatus status = vehicleStatusUtil.determineStatusByVehicleId(VEHICLE_ID);
        assertEquals(status, VehicleStatus.IDLE);
    }

    @Test
    public void testIdlingWithMiddleElem(){

        new Expectations() {{
            mockEventDao.getEventsForVehicle(VEHICLE_ID, withInstanceOf(Date.class), withInstanceOf(Date.class),(List<NoteType>)any, 0);
            times = 1;
            result = makeEvents(Arrays.asList(NoteType.IDLE, NoteType.COLILO_VERSION));
        }};

        VehicleStatus status = vehicleStatusUtil.determineStatusByVehicleId(VEHICLE_ID);
        assertEquals(status, VehicleStatus.IDLE);
    }

    @Test
    public void testStandingWithLastElem(){

        new Expectations() {{
            mockEventDao.getEventsForVehicle(VEHICLE_ID, withInstanceOf(Date.class), withInstanceOf(Date.class),(List<NoteType>)any, 0);
            times = 1;
            result = makeEvents(Arrays.asList(NoteType.PARKING_BRAKE, NoteType.IGNITION_ON));
        }};

        VehicleStatus status = vehicleStatusUtil.determineStatusByVehicleId(VEHICLE_ID);
        assertEquals(status, VehicleStatus.STANDING);
    }

    @Test
    public void testStandingWithMiddleElem(){

        new Expectations() {{
            mockEventDao.getEventsForVehicle(VEHICLE_ID, withInstanceOf(Date.class), withInstanceOf(Date.class),(List<NoteType>)any, 0);
            times = 1;
            result = makeEvents(Arrays.asList(NoteType.IGNITION_ON, NoteType.CHECK_HOSMINUTES));
        }};

        VehicleStatus status = vehicleStatusUtil.determineStatusByVehicleId(VEHICLE_ID);
        assertEquals(status, VehicleStatus.STANDING);
    }

    @Test
    public void testParkingWithLastElem(){

        new Expectations() {{
            mockEventDao.getEventsForVehicle(VEHICLE_ID, withInstanceOf(Date.class), withInstanceOf(Date.class),(List<NoteType>)any, 0);
            times = 1;
            result = makeEvents(Arrays.asList(NoteType.PARKING_BRAKE, NoteType.IGNITION_OFF));
        }};

        VehicleStatus status = vehicleStatusUtil.determineStatusByVehicleId(VEHICLE_ID);
        assertEquals(status, VehicleStatus.PARKING);
    }

    @Test
    public void testParkingWithMiddleElem(){

        new Expectations() {{
            mockEventDao.getEventsForVehicle(VEHICLE_ID, withInstanceOf(Date.class), withInstanceOf(Date.class),(List<NoteType>)any, 0);
            times = 1;
            result = makeEvents(Arrays.asList(NoteType.IGNITION_OFF, NoteType.CHECK_HOSMINUTES));
        }};

        VehicleStatus status = vehicleStatusUtil.determineStatusByVehicleId(VEHICLE_ID);
        assertEquals(status, VehicleStatus.PARKING);
    }

    @Test
    public void testDrivingAfterRecursion(){
        new Expectations() {{
            mockEventDao.getEventsForVehicle(VEHICLE_ID, withInstanceOf(Date.class), withInstanceOf(Date.class),(List<NoteType>)any, 0);
            times = 1;
            result = makeEvents(Arrays.asList(NoteType.LOCATION, NoteType.LOCATION, NoteType.LOCATION));
        }};

        new Expectations() {{
            mockEventDao.getEventsForVehicle(VEHICLE_ID, withInstanceOf(Date.class), withInstanceOf(Date.class),(List<NoteType>)any, 0);
            times = 1;
            result = makeEvents(Arrays.asList(NoteType.LOCATION, NoteType.LOCATION, NoteType.LOCATION));
        }};

        new Expectations() {{
            mockEventDao.getEventsForVehicle(VEHICLE_ID, withInstanceOf(Date.class), withInstanceOf(Date.class),(List<NoteType>)any, 0);
            times = 1;
            result = makeEvents(Arrays.asList(NoteType.IGNITION_ON, NoteType.LOCATION, NoteType.LOCATION));
        }};

        VehicleStatus status = vehicleStatusUtil.determineStatusByVehicleId(VEHICLE_ID);
        assertEquals(status, VehicleStatus.DRIVING);
    }

    @Test
    public void testRecursionLimit(){
        vehicleStatusUtil.setMaxNumTries(2);

        new Expectations() {{
            mockEventDao.getEventsForVehicle(VEHICLE_ID, withInstanceOf(Date.class), withInstanceOf(Date.class),(List<NoteType>)any, 0);
            times = 1;
            result = makeEvents(Arrays.asList(NoteType.LOCATION, NoteType.LOCATION, NoteType.LOCATION));
        }};

        new Expectations() {{
            mockEventDao.getEventsForVehicle(VEHICLE_ID, withInstanceOf(Date.class), withInstanceOf(Date.class),(List<NoteType>)any, 0);
            times = 1;
            result = makeEvents(Arrays.asList(NoteType.LOCATION, NoteType.LOCATION, NoteType.LOCATION));
        }};

        new Expectations() {{
            mockEventDao.getEventsForVehicle(VEHICLE_ID, withInstanceOf(Date.class), withInstanceOf(Date.class),(List<NoteType>)any, 0);
            times = 1;
            result = makeEvents(Arrays.asList(NoteType.LOCATION, NoteType.LOCATION, NoteType.LOCATION));
        }};

        VehicleStatus status = vehicleStatusUtil.determineStatusByVehicleId(VEHICLE_ID);
        assertEquals(status, VehicleStatus.PARKING);
    }

    /**
     * Makes events with given event types.
     *
     * @param types types
     * @return events with types
     */
    private List<Event> makeEvents (List<NoteType> types){
        List<Event> eventList = new ArrayList<Event>();

        for (NoteType noteType: types){
            Event event = new Event();
            event.setType(noteType);
            eventList.add(event);
        }

        return eventList;
    }
}