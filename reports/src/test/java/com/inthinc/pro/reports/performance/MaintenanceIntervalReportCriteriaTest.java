package com.inthinc.pro.reports.performance;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import mockit.Expectations;
import mockit.Mocked;

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
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.configurator.MaintenanceSettings;
import com.inthinc.pro.model.configurator.VehicleSetting;
import com.inthinc.pro.reports.FormatType;

/**
 * Test for {@link MaintenanceIntervalReportCriteriaTest}.
 */
public class MaintenanceIntervalReportCriteriaTest extends BasePerformanceUnitTest {
    // System under test
    private List resultingDataset;

    // vars
    private static final Integer GROUP_ID = 2;
    private static final Integer ACCOUNT_ID = 1;
    private static final Integer VEHICLE_ID_1 = 11;
    private static final Integer VEHICLE_ID_2 = 22;
    private static final Long SUM_ODOMETER_1 = 5000L;
    private static final Long ODOMETER_CALC_1 = 17L;
    private static final Long SUM_ODOMETER_2 = 300L;
    private static final Long ODOMETER_CALC_2 = -33L;
    private static final Long SUM_DRIVE_TIME_1 = 64L * 3600;
    private static final Long DRIVE_TIME_CALC_1 = 20L;
    private static final Long SUM_DRIVE_TIME_2 = 440L * 3600;
    private static final Long DRIVE_TIME_CALC_2 = -4L;
    private static final String GROUP_FULL_NAME = "Group Full Name";
    private static final Locale LOCALE = Locale.US;
    private static final TimeFrame TIME_FRAME = TimeFrame.TODAY;
    private static final MeasurementType MEASUREMENT_TYPE = MeasurementType.ENGLISH;
    private static final Vehicle VEHICLE1;
    private static final Vehicle VEHICLE2;
    private static final VehicleSetting vehicleSetting1;
    private static final VehicleSetting vehicleSetting2;
    private static final Group group;

    // criteria
    private static MaintenanceIntervalReportCriteria maintenanceIntervalReportCriteria;

    // mocks
    @Mocked
    private GroupReportDAO groupReportDAOMock;
    @Mocked
    private GroupDAO groupDAOMock;
    @Mocked
    private VehicleDAO vehicleDAOMock;
    @Mocked
    private EventDAO eventDAOMock;
    @Mocked
    private ConfiguratorDAO configuratorDAOMock;
    @Mocked
    private DriveTimeDAO driveTimeDAOMock;
    @Mocked
    private GroupHierarchy groupHierarchyMock;
    @Mocked
    private List<Integer> groupIDListMock;
    @Mocked
    private MeasurementType measurementTypeMock;
    @Mocked
    private Interval intervalMock;

    private List<Vehicle> vehiclesList;

    static {

        VEHICLE1 = new Vehicle();
        VEHICLE2 = new Vehicle();
        VEHICLE1.setVehicleID(VEHICLE_ID_1);
        VEHICLE1.setName(VEHICLE_ID_1+"name");
        VEHICLE1.setGroupID(GROUP_ID);
        VEHICLE1.setOdometer(SUM_ODOMETER_1.intValue());
        VEHICLE2.setVehicleID(VEHICLE_ID_2);
        VEHICLE2.setName(VEHICLE_ID_2+"name");
        VEHICLE2.setGroupID(GROUP_ID);
        VEHICLE2.setOdometer(SUM_ODOMETER_2.intValue());
        vehicleSetting1 = new VehicleSetting();
        vehicleSetting1.setVehicleID(VEHICLE_ID_1);
        Map<Integer, String> actual1 = new HashMap<Integer, String>();
        actual1.put(MaintenanceSettings.MAINT_BY_DIST_START.getCode(),"11");
        actual1.put(MaintenanceSettings.MAINT_BY_DIST_INTERVAL.getCode(),"22");
        actual1.put(MaintenanceSettings.MAINT_BY_ENGINE_HOURS_INTERVAL.getCode(),"44");
        vehicleSetting1.setActual(actual1);
        vehicleSetting2 = new VehicleSetting();
        Map<Integer, String> actual2 = new HashMap<Integer, String>();
        actual2.put(MaintenanceSettings.MAINT_BY_DIST_START.getCode(),"111");
        actual2.put(MaintenanceSettings.MAINT_BY_DIST_INTERVAL.getCode(),"222");
        actual2.put(MaintenanceSettings.MAINT_BY_ENGINE_HOURS_INTERVAL.getCode(),"444");
        vehicleSetting2.setVehicleID(VEHICLE_ID_2);
        vehicleSetting2.setActual(actual2);
        group = new Group();
        group.setGroupID(GROUP_ID);
    }
    
    @Before
    public void setUp(){
     // mock data
        groupIDListMock = new ArrayList<Integer>();
        groupIDListMock.add(GROUP_ID);
        measurementTypeMock = MeasurementType.ENGLISH;
        vehiclesList = new ArrayList<Vehicle>();
        vehiclesList.add(VEHICLE1);
        vehiclesList.add(VEHICLE2);


        // Expectations
        new Expectations() {
            {
                vehicleDAOMock.getVehiclesInGroupHierarchy(GROUP_ID);
                returns(vehiclesList);

                configuratorDAOMock.getVehicleSettings(VEHICLE_ID_1);
                returns(vehicleSetting1);

                groupDAOMock.findByID(GROUP_ID);
                returns(group);

                driveTimeDAOMock.getDriveOdometerSum(VEHICLE1);
                returns(SUM_ODOMETER_1);

                driveTimeDAOMock.getDriveTimeSum((Vehicle)any);
                returns(SUM_DRIVE_TIME_1);

                configuratorDAOMock.getVehicleSettings(VEHICLE_ID_2);
                returns(vehicleSetting2);

                groupDAOMock.findByID(GROUP_ID);
                returns(group);

                driveTimeDAOMock.getDriveOdometerSum(VEHICLE2);
                returns(SUM_ODOMETER_2);

                driveTimeDAOMock.getDriveTimeSum((Vehicle)any);
                returns(SUM_DRIVE_TIME_2);
            }
        };

        // Build maintenanceIntervalBuilder (and exercise code)
        MaintenanceIntervalReportCriteria.Builder maintenanceIntervalBuilder = new MaintenanceIntervalReportCriteria.Builder(groupHierarchyMock, groupReportDAOMock, groupDAOMock, vehicleDAOMock, eventDAOMock, groupIDListMock,
                intervalMock, measurementTypeMock, configuratorDAOMock, driveTimeDAOMock);

        maintenanceIntervalBuilder.setLocale(LOCALE);
        maintenanceIntervalReportCriteria = maintenanceIntervalBuilder.build();
        
        resultingDataset = maintenanceIntervalReportCriteria.getMainDataset();
    }
    
    @After
    public void tearDown(){
        maintenanceIntervalReportCriteria = null;
    }
    
    @Test
    public void testDatasetSizeIsCorrect(){
        assertTrue(resultingDataset.size() == 1);
    }
    
    @Test
    public void testVehicleNamesAreCorrect(){
        assertEquals(((MaintenanceIntervalReportCriteria.BackingWrapper)resultingDataset.get(0)).getVehicleID(), VEHICLE_ID_2+"name");
    }
    
    @Test
    public void testOdometerValuesAreCorrect(){
        assertEquals(((MaintenanceIntervalReportCriteria.BackingWrapper)resultingDataset.get(0)).getOdometer(),
                        SUM_ODOMETER_2+"");
    }
    
    @Test
    public void testHourValuesAreCorrect(){
        assertEquals(((MaintenanceIntervalReportCriteria.BackingWrapper)resultingDataset.get(0)).getHours(),
                        (SUM_DRIVE_TIME_2/3600)+"");
    }
    
    @Test
    public void testBaseOdometerValuesAreCorrect(){
        assertEquals(((MaintenanceIntervalReportCriteria.BackingWrapper)resultingDataset.get(0)).getBaseOdometer(),
                        vehicleSetting2.getActual().get(MaintenanceSettings.MAINT_BY_DIST_START.getCode()));
    }
    
    @Test
    public void testIntervalOdometerValuesAreCorrect(){
        assertEquals(((MaintenanceIntervalReportCriteria.BackingWrapper)resultingDataset.get(0)).getIntervalOdometer(),
                        vehicleSetting2.getActual().get(MaintenanceSettings.MAINT_BY_DIST_INTERVAL.getCode()));
    }
    
    @Test
    public void testIntervalHourValuesAreCorrect(){
        assertEquals(((MaintenanceIntervalReportCriteria.BackingWrapper)resultingDataset.get(0)).getIntervalHours(),
                        vehicleSetting2.getActual().get(MaintenanceSettings.MAINT_BY_ENGINE_HOURS_INTERVAL.getCode()));
    }
    
    @Test
    public void testDistancesOverAreCorrect(){
        assertEquals(((MaintenanceIntervalReportCriteria.BackingWrapper)resultingDataset.get(0)).getDistanceOver(),ODOMETER_CALC_2+"");
    }
    
    @Test
    public void testHoursOverAreCorrect(){
        assertEquals(((MaintenanceIntervalReportCriteria.BackingWrapper)resultingDataset.get(0)).getHoursOver(),
                        DRIVE_TIME_CALC_2+"");
    }
    
    @Test
    public void testPramMapIsSet(){
        Map<String, Object> parammap = maintenanceIntervalReportCriteria.getPramMap();
        assertNotNull(parammap);
        assertFalse(parammap.isEmpty());
    }
    
    @Test
    public void testDumpErrors(){
        boolean dumpErrors = false;
        try {
            dump("vehicleMaintenanceIntervalReport", 1, maintenanceIntervalReportCriteria, FormatType.PDF);
            dump("vehicleMaintenanceIntervalReport", 1, maintenanceIntervalReportCriteria, FormatType.EXCEL);
        } catch (Throwable t) {
            dumpErrors = true;
        }

        assertFalse(dumpErrors);
    }
    
    @Test
    public void calcDistanceOver_betweenOneAndTwoTimesInterval_positive() {
        assertEquals((Integer)(0), MaintenanceIntervalReportCriteria.calcDistanceOver(1, 2, 3));
        assertEquals((Integer)(1), MaintenanceIntervalReportCriteria.calcDistanceOver(0, 1000, 1001));
    }
    @Test
    public void calcDistanceOver_moreThanTwoTimesInterval_positive() {
        assertEquals((Integer)(10), MaintenanceIntervalReportCriteria.calcDistanceOver(1, 10, 1011));
    }
    @Test
    public void calcDistanceOver_lessThanInterval_negative() {   
        assertEquals((Integer)(-357), MaintenanceIntervalReportCriteria.calcDistanceOver(5, 1000, 648));
        assertEquals((Integer)(-1940), MaintenanceIntervalReportCriteria.calcDistanceOver(1000, 3000, 2060));
        assertEquals((Integer)(-9860), MaintenanceIntervalReportCriteria.calcDistanceOver(5000, 5000, 140));
    }

    @Test
    public void calcDistanceOver_somethingIsNull_null() {   
        assertEquals((Integer)(-352), MaintenanceIntervalReportCriteria.calcDistanceOver(null, 1000, 648));
        assertEquals(null, MaintenanceIntervalReportCriteria.calcDistanceOver(1000, null, 2060));
        assertEquals(null, MaintenanceIntervalReportCriteria.calcDistanceOver(5000, 5000, null));
    }

    @Test
    public void calcDistanceOver_divideByZero_dont() {   
        assertEquals(null, MaintenanceIntervalReportCriteria.calcDistanceOver(5, 0, 648));
        assertEquals(null, MaintenanceIntervalReportCriteria.calcDistanceOver(0, 0, 648));
    }
}
