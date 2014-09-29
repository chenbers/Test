package com.inthinc.pro.reports.performance;

import static junit.framework.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.dao.DriveTimeDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.configurator.MaintenanceSettings;
import com.inthinc.pro.model.configurator.VehicleSetting;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventAttr;
import com.inthinc.pro.model.event.MaintenanceEvent;
import com.inthinc.pro.model.event.NoteType;

import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;

public class MaintenanceEventsReportCriteriaTest extends BasePerformanceUnitTest {
    // System under test
    private List resultingDataset;
    
    // vars
    private static final Locale LOCALE = Locale.US;
    private static final Integer GROUP_ID = 2;
    private static final String GROUP_NAME = "Test Group Name";
    private static final Vehicle VEHICLE1;
    private static final Vehicle VEHICLE2;
    private static final Vehicle VEHICLE_WITHOUT_EVENTS;
    private static final Integer VEHICLE_ID_1 = 11;
    private static final Integer VEHICLE_ID_2 = 22;
    private static final Integer VEHICLE_ID_3 = 33;
    private static final String VEHICLE_NAME_1 = "Vehicle 1";
    private static final String VEHICLE_NAME_2 = "Vehicle 2";
    private static final String VEHICLE_NAME_3 = "Vehicle 3";
    private static final VehicleSetting vehicleSetting1;
    private static final VehicleSetting vehicleSetting2;
    private static final MaintenanceEvent V1_EVENT1;
    private static final MaintenanceEvent V1_EVENT2;
    private static final MaintenanceEvent V2_EVENT1;
    private static final MaintenanceEvent V2_EVENT2;
    private List<Vehicle> vehiclesList;
    private List<Event> eventsList1;
    private List<Event> eventsList2;
    private static Map<Integer,String> actual1;
    private static Map<Integer,String> actual2;
    
    // criteria
    private static MaintenanceEventsReportCriteria maintenanceEventsReportCriteria;
    
    // mocks
    @Mocked GroupHierarchy mockGroupHierarchy;
    @Mocked GroupReportDAO mockGroupReportDAO;
    @Mocked GroupDAO mockGroupDAO;
    @Mocked VehicleDAO mockVehicleDAO;
    @Mocked EventDAO mockEventDAO;
    @Mocked List<Integer> mockGroupIDList;
    @Mocked Interval mockInterval;
    @Mocked MeasurementType mockMeasurementType;
    @Mocked ConfiguratorDAO mockConfiguratorDAO;
    @Mocked DriveTimeDAO mockDriveTimeDAO;
    @Mocked NoteType mockNoteType;
    @Mocked Group mockGroup;
//    @Mocked VehicleSetting mockVehicleSetting;
    
    static {

        VEHICLE1 = new Vehicle();
        VEHICLE2 = new Vehicle();
        VEHICLE_WITHOUT_EVENTS = new Vehicle();
        VEHICLE1.setVehicleID(VEHICLE_ID_1);
        VEHICLE1.setGroupID(GROUP_ID);
        VEHICLE1.setName(VEHICLE_NAME_1);
        VEHICLE2.setVehicleID(VEHICLE_ID_2);
        VEHICLE2.setGroupID(GROUP_ID);
        VEHICLE2.setName(VEHICLE_NAME_2);
        VEHICLE_WITHOUT_EVENTS.setVehicleID(VEHICLE_ID_3);
        VEHICLE_WITHOUT_EVENTS.setGroupID(GROUP_ID);
        VEHICLE_WITHOUT_EVENTS.setName(VEHICLE_NAME_3);
        vehicleSetting1 = new VehicleSetting();
        vehicleSetting1.setVehicleID(VEHICLE_ID_1);
        actual1 = new HashMap<Integer, String>();
        actual1.put(MaintenanceSettings.SET_BATT_VOLTAGE.getCode(),"11");
        actual1.put(MaintenanceSettings.SET_ENGINE_TEMP.getCode(),"22");
        actual1.put(MaintenanceSettings.SET_TRANS_TEMP.getCode(),"33");
        actual1.put(MaintenanceSettings.DPF_FLOW_RATE.getCode(),"44");
        vehicleSetting1.setActual(actual1);
        vehicleSetting2 = new VehicleSetting();
        actual2 = new HashMap<Integer, String>();
        actual2.put(MaintenanceSettings.SET_BATT_VOLTAGE.getCode(),"111");
        actual2.put(MaintenanceSettings.SET_ENGINE_TEMP.getCode(),"222");
        actual2.put(MaintenanceSettings.SET_TRANS_TEMP.getCode(),"333");
        actual2.put(MaintenanceSettings.DPF_FLOW_RATE.getCode(),"444");
        vehicleSetting2.setVehicleID(VEHICLE_ID_2);
        vehicleSetting2.setActual(actual2);
        V1_EVENT1 = new MaintenanceEvent();
        V1_EVENT1.setType(NoteType.MAINTENANCE_EVENTS);
        V1_EVENT1.setVehicleID(VEHICLE_ID_1);
        V1_EVENT1.setBatteryVoltage(111);
        V1_EVENT2 = new MaintenanceEvent();
        V1_EVENT2.setType(NoteType.MAINTENANCE_EVENTS);
        V1_EVENT2.setVehicleID(VEHICLE_ID_1);
        V1_EVENT2.setEngineTemp(222);
        V2_EVENT1 = new MaintenanceEvent();
        V2_EVENT1.setType(NoteType.MAINTENANCE_EVENTS);
        V2_EVENT1.setVehicleID(VEHICLE_ID_2);
        V2_EVENT1.setTransmissionTemp(333);
        V2_EVENT2 = new MaintenanceEvent();
        V2_EVENT2.setType(NoteType.MAINTENANCE_EVENTS);
        V2_EVENT2.setVehicleID(VEHICLE_ID_2);
        V2_EVENT2.setDpfFlowRate(444);
    }
    
    @Before
    public void setUp() {

        mockGroupIDList = new ArrayList<Integer>();
        mockGroupIDList.add(GROUP_ID);
        mockMeasurementType = MeasurementType.ENGLISH;
        final DateTime mockStart = new DateTime();
        final DateTime mockEnd = new DateTime();
        vehiclesList = new ArrayList<Vehicle>();
        vehiclesList.add(VEHICLE1);
        vehiclesList.add(VEHICLE2);
        vehiclesList.add(VEHICLE_WITHOUT_EVENTS);
        eventsList1 = new ArrayList<Event>();
        eventsList1.add(V1_EVENT1);
        eventsList1.add(V1_EVENT2);
        eventsList2 = new ArrayList<Event>();
        eventsList2.add(V2_EVENT1);
        eventsList2.add(V2_EVENT2);
        
        new NonStrictExpectations() {{
            mockVehicleDAO.getVehiclesInGroupHierarchy(anyInt); returns(vehiclesList);
            mockInterval.getStart(); returns(mockStart);
            mockInterval.getEnd(); returns(mockEnd);
            mockEventDAO.getEventsForVehicle(11, null, null, null, anyInt); returns(eventsList1);
            mockInterval.getStart(); returns(mockStart);
            mockInterval.getEnd(); returns(mockEnd);
            mockEventDAO.getEventsForVehicle(22, null, null, null, anyInt); returns(eventsList2);
            mockInterval.getStart(); returns(mockStart);
            mockInterval.getEnd(); returns(mockEnd);
            mockEventDAO.getEventsForVehicle(33, null, null, null, anyInt); returns(null);
            NoteType.MAINTENANCE_EVENTS.getCode(); returns(238);
            mockGroupDAO.findByID(anyInt); returns(mockGroup);
            mockGroup.getName(); returns(GROUP_NAME);
            mockDriveTimeDAO.getDriveOdometerAtDate(VEHICLE1, (Date) any); returns(123456L);
            mockDriveTimeDAO.getEngineHoursAtDate(VEHICLE1, (Date) any); returns(1234L);
            mockDriveTimeDAO.getDriveOdometerAtDate(VEHICLE2, (Date) any); returns(23456L);
            mockDriveTimeDAO.getEngineHoursAtDate(VEHICLE2, (Date) any); returns(2345L);
            mockConfiguratorDAO.getVehicleSettings(11); returns(vehicleSetting1);
            mockConfiguratorDAO.getVehicleSettings(22); returns(vehicleSetting2);
        }};
        
        MaintenanceEventsReportCriteria.Builder maintenanceEventsBuilder = new MaintenanceEventsReportCriteria.Builder(
                            mockGroupHierarchy, mockGroupReportDAO, mockGroupDAO, mockVehicleDAO, mockEventDAO, mockGroupIDList, mockInterval, mockMeasurementType, mockConfiguratorDAO, mockDriveTimeDAO);
        maintenanceEventsBuilder.setLocale(LOCALE);
        maintenanceEventsReportCriteria = maintenanceEventsBuilder.build();
        
        resultingDataset = maintenanceEventsReportCriteria.getMainDataset();
    }
    
    @After
    public void tearDown() {
        resultingDataset = null;
    }
    
    @Test
    public void testDatasetSizeIsCorrect(){
        assertTrue(resultingDataset.size() == 4);
    }
    
    @Test
    public void testVehicleNamesAreCorrect(){
        assertTrue(((MaintenanceEventsReportCriteria.BackingWrapper)resultingDataset.get(0)).getVehicleID().equals(VEHICLE_NAME_1));
        assertTrue(((MaintenanceEventsReportCriteria.BackingWrapper)resultingDataset.get(1)).getVehicleID().equals(VEHICLE_NAME_1));
        assertTrue(((MaintenanceEventsReportCriteria.BackingWrapper)resultingDataset.get(2)).getVehicleID().equals(VEHICLE_NAME_2));
        assertTrue(((MaintenanceEventsReportCriteria.BackingWrapper)resultingDataset.get(3)).getVehicleID().equals(VEHICLE_NAME_2));
    }
    
    @Test
    public void testValuesAreCorrect(){
        assertTrue(((MaintenanceEventsReportCriteria.BackingWrapper)resultingDataset.get(0)).getValue().equals(V1_EVENT1.getBatteryVoltage()+""));
        assertTrue(((MaintenanceEventsReportCriteria.BackingWrapper)resultingDataset.get(1)).getValue().equals(V1_EVENT2.getEngineTemp()+""));
        assertTrue(((MaintenanceEventsReportCriteria.BackingWrapper)resultingDataset.get(2)).getValue().equals(V2_EVENT1.getTransmissionTemp()+""));
        assertTrue(((MaintenanceEventsReportCriteria.BackingWrapper)resultingDataset.get(3)).getValue().equals(V2_EVENT2.getDpfFlowRate()+""));
    }
    
    @Test
    public void testActualsAreCorrect(){
        assertTrue(((MaintenanceEventsReportCriteria.BackingWrapper)resultingDataset.get(0)).getActual().equals(vehicleSetting1.getActual().get(MaintenanceSettings.SET_BATT_VOLTAGE.getCode())));
        assertTrue(((MaintenanceEventsReportCriteria.BackingWrapper)resultingDataset.get(1)).getActual().equals(vehicleSetting1.getActual().get(MaintenanceSettings.SET_ENGINE_TEMP.getCode())));
        assertTrue(((MaintenanceEventsReportCriteria.BackingWrapper)resultingDataset.get(2)).getActual().equals(vehicleSetting2.getActual().get(MaintenanceSettings.SET_TRANS_TEMP.getCode())));
        assertTrue(((MaintenanceEventsReportCriteria.BackingWrapper)resultingDataset.get(3)).getActual().equals(vehicleSetting2.getActual().get(MaintenanceSettings.DPF_FLOW_RATE.getCode())));
    }
}