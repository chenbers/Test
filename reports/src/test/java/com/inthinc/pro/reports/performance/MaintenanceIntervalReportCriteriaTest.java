package com.inthinc.pro.reports.performance;

import com.inthinc.pro.dao.*;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.*;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.configurator.MaintenanceSettings;
import com.inthinc.pro.model.configurator.VehicleSetting;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.testData.MockData;
import mockit.Expectations;
import mockit.Mocked;
import org.joda.time.Interval;
import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.*;

/**
 * Test for {@link MaintenanceIntervalReportCriteriaTest}.
 */
public class MaintenanceIntervalReportCriteriaTest extends BasePerformanceUnitTest {

    // vars
    private static final Integer GROUP_ID = 2;
    private static final Integer ACCOUNT_ID = 1;
    private static final Integer VEHICLE_ID_1 = 11;
    private static final Integer VEHICLE_ID_2 = 22;
    private static final Long SUM_ODOMETER_1 = 5000L;
    private static final Long ODOMETER_CALC_1 = -5L;
    private static final Long SUM_ODOMETER_2 = 30000L;
    private static final Long ODOMETER_CALC_2 = -81L;
    private static final Long SUM_DRIVE_TIME_1 = 0L;
    private static final Long DRIVE_TIME_CALC_1 = -33L;
    private static final Long SUM_DRIVE_TIME_2 = 0L;
    private static final Long DRIVE_TIME_CALC_2 = -333L;
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
        VEHICLE1.setGroupID(GROUP_ID);
        VEHICLE2.setVehicleID(VEHICLE_ID_2);
        VEHICLE2.setGroupID(GROUP_ID);
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

    /**
     * Test that covers building the criteria.
     */
    @Test
    public void buildTest() {

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
                vehicleDAOMock.getVehiclesInGroup(GROUP_ID);
                returns(vehiclesList);

                configuratorDAOMock.getVehicleSettings(VEHICLE_ID_1);
                returns(vehicleSetting1);

                groupDAOMock.findByID(GROUP_ID);
                returns(group);

                driveTimeDAOMock.getDriveOdometerSum(VEHICLE1);
                returns(SUM_ODOMETER_1);

                driveTimeDAOMock.getDriveTimeSum(VEHICLE1);
                returns(SUM_DRIVE_TIME_1);

                configuratorDAOMock.getVehicleSettings(VEHICLE_ID_2);
                returns(vehicleSetting2);

                groupDAOMock.findByID(GROUP_ID);
                returns(group);

                driveTimeDAOMock.getDriveOdometerSum(VEHICLE2);
                returns(SUM_ODOMETER_2);

                driveTimeDAOMock.getDriveTimeSum(VEHICLE2);
                returns(SUM_DRIVE_TIME_2);
            }
        };

        // Build maintenanceIntervalBuilder (and exercise code)
        MaintenanceIntervalReportCriteria.Builder maintenanceIntervalBuilder = new MaintenanceIntervalReportCriteria.Builder(groupHierarchyMock, groupReportDAOMock, groupDAOMock, vehicleDAOMock, eventDAOMock, groupIDListMock,
                intervalMock, measurementTypeMock, configuratorDAOMock, driveTimeDAOMock);

        maintenanceIntervalBuilder.setLocale(LOCALE);
        maintenanceIntervalReportCriteria = maintenanceIntervalBuilder.build();
        assertTrue(maintenanceIntervalReportCriteria.getMainDataset().size() == 2);
        assertTrue(((MaintenanceIntervalReportCriteria.BackingWrapper)maintenanceIntervalReportCriteria.getMainDataset().get(0)).getVehicleID().equals(VEHICLE_ID_1+""));
        assertTrue(((MaintenanceIntervalReportCriteria.BackingWrapper)maintenanceIntervalReportCriteria.getMainDataset().get(0)).getOdometer().equals(SUM_ODOMETER_1+""));
        assertTrue(((MaintenanceIntervalReportCriteria.BackingWrapper)maintenanceIntervalReportCriteria.getMainDataset().get(0)).getHours().equals(SUM_DRIVE_TIME_1+""));
        assertTrue(((MaintenanceIntervalReportCriteria.BackingWrapper)maintenanceIntervalReportCriteria.getMainDataset().get(0)).getBaseOdometer().equals(vehicleSetting1.getActual().get(MaintenanceSettings.MAINT_BY_DIST_START.getCode())));
        assertTrue(((MaintenanceIntervalReportCriteria.BackingWrapper)maintenanceIntervalReportCriteria.getMainDataset().get(0)).getIntervalOdometer().equals(vehicleSetting1.getActual().get(MaintenanceSettings.MAINT_BY_DIST_INTERVAL.getCode())));
        assertTrue(((MaintenanceIntervalReportCriteria.BackingWrapper)maintenanceIntervalReportCriteria.getMainDataset().get(0)).getIntervalHours().equals(vehicleSetting1.getActual().get(MaintenanceSettings.MAINT_BY_ENGINE_HOURS_INTERVAL.getCode())));
        assertTrue(((MaintenanceIntervalReportCriteria.BackingWrapper)maintenanceIntervalReportCriteria.getMainDataset().get(0)).getDistanceOver().equals(ODOMETER_CALC_1+""));
        assertTrue(((MaintenanceIntervalReportCriteria.BackingWrapper)maintenanceIntervalReportCriteria.getMainDataset().get(0)).getHoursOver().equals(DRIVE_TIME_CALC_1+""));

        assertTrue(((MaintenanceIntervalReportCriteria.BackingWrapper)maintenanceIntervalReportCriteria.getMainDataset().get(1)).getVehicleID().equals(VEHICLE_ID_2+""));
        assertTrue(((MaintenanceIntervalReportCriteria.BackingWrapper)maintenanceIntervalReportCriteria.getMainDataset().get(1)).getOdometer().equals(SUM_ODOMETER_2+""));
        assertTrue(((MaintenanceIntervalReportCriteria.BackingWrapper)maintenanceIntervalReportCriteria.getMainDataset().get(1)).getHours().equals(SUM_DRIVE_TIME_2+""));
        assertTrue(((MaintenanceIntervalReportCriteria.BackingWrapper)maintenanceIntervalReportCriteria.getMainDataset().get(1)).getBaseOdometer().equals(vehicleSetting2.getActual().get(MaintenanceSettings.MAINT_BY_DIST_START.getCode())));
        assertTrue(((MaintenanceIntervalReportCriteria.BackingWrapper)maintenanceIntervalReportCriteria.getMainDataset().get(1)).getIntervalOdometer().equals(vehicleSetting2.getActual().get(MaintenanceSettings.MAINT_BY_DIST_INTERVAL.getCode())));
        assertTrue(((MaintenanceIntervalReportCriteria.BackingWrapper)maintenanceIntervalReportCriteria.getMainDataset().get(1)).getIntervalHours().equals(vehicleSetting2.getActual().get(MaintenanceSettings.MAINT_BY_ENGINE_HOURS_INTERVAL.getCode())));
        assertTrue(((MaintenanceIntervalReportCriteria.BackingWrapper)maintenanceIntervalReportCriteria.getMainDataset().get(1)).getDistanceOver().equals(ODOMETER_CALC_2+""));
        assertTrue(((MaintenanceIntervalReportCriteria.BackingWrapper)maintenanceIntervalReportCriteria.getMainDataset().get(1)).getHoursOver().equals(DRIVE_TIME_CALC_2+""));

        Map<String, Object> parammap = maintenanceIntervalReportCriteria.getPramMap();
        assertNotNull(parammap);
        assertFalse(parammap.isEmpty());

        boolean dumpErrors = false;
        try {
            dump("vehicleMaintenanceIntervalReport", 1, maintenanceIntervalReportCriteria, FormatType.PDF);
            dump("vehicleMaintenanceIntervalReport", 1, maintenanceIntervalReportCriteria, FormatType.EXCEL);
        } catch (Throwable t) {
            dumpErrors = true;
        }

        assertFalse(dumpErrors);
    }
}
