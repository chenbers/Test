package com.inthinc.pro.reports.performance;

import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.testData.MockData;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

/**
 * Test for {@link com.inthinc.pro.reports.performance.SeatbeltClicksReportCriteria}.
 */
public class SeatbeltClicksReportCriteriaTest extends BasePerformanceUnitTest {

    // vars
    private static final Integer GROUP_ID = 2;
    private static final Integer ACCOUNT_ID = 1;
    private static final Integer DRIVER_ID = 1;
    private static final String GROUP_FULL_NAME = "Group Full Name";
    private static final Locale LOCALE = Locale.US;
    private static final DriverVehicleScoreWrapper DRIVER_SCORES = new DriverVehicleScoreWrapper();
    private static final TimeFrame TIME_FRAME = TimeFrame.TODAY;
    private static final MeasurementType MEASUREMENT_TYPE = MeasurementType.ENGLISH;
    private static final Driver DRIVER;

    // criteria
    private static SeatbeltClicksReportCriteria seatbeltClicksReportCriteria;

    // mocks
    @Mocked
    private GroupReportDAO groupReportDAOMock;

    private GroupHierarchy groupHierarchyMock;

    private List<DriverVehicleScoreWrapper> driverScoresMock;

    static {
        DRIVER = MockData.createMockDriver(ACCOUNT_ID, DRIVER_ID, GROUP_ID, "Test_FN_" + DRIVER_ID, "Test_LN_" + DRIVER_ID, Status.ACTIVE);
        DRIVER_SCORES.setDriver(DRIVER);
        DRIVER_SCORES.setScore(new Score());
    }

    /**
     * Test that covers building the criteria.
     */
    @Test
    public void buildTest() {

        // mock data
        groupHierarchyMock = getLocalMockGroupHierarchy();
        driverScoresMock = getMockDriverScores();

        // Expectations
        new Expectations() {
            {
                groupReportDAOMock.getDriverScores(GROUP_ID, TIME_FRAME.getInterval(), groupHierarchyMock);
                returns(driverScoresMock);

                groupHierarchyMock.getFullGroupName(GROUP_ID);
                returns(GROUP_FULL_NAME);
            }
        };

        // Build seatbeltClicksReportCriteria (and exercise code)
        SeatbeltClicksReportCriteria.Builder seatbeltClickReportCriteriaBuilder = new SeatbeltClicksReportCriteria.Builder(groupHierarchyMock, groupReportDAOMock, GROUP_ID, TIME_FRAME, MEASUREMENT_TYPE);
        seatbeltClickReportCriteriaBuilder.setLocale(LOCALE);
        seatbeltClicksReportCriteria = seatbeltClickReportCriteriaBuilder.build();

        // Other asserts for seatbeltClicksReportCriteria
        assertEquals(seatbeltClicksReportCriteria.getLocale(), LOCALE);
        assertFalse(seatbeltClicksReportCriteria.getUseMetric());
        assertEquals(seatbeltClicksReportCriteria.getIncludeInactiveDrivers().booleanValue(), ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS);
        assertEquals(seatbeltClicksReportCriteria.getIncludeZeroMilesDrivers().booleanValue(), ReportCriteria.DEFAULT_EXCLUDE_ZERO_MILES_DRIVERS);

        Map<String, Object> parammap = seatbeltClicksReportCriteria.getPramMap();
        assertNotNull(parammap);
        assertFalse(parammap.isEmpty());
        assertEquals(parammap.get("REPORT_NAME"), ReportType.SEATBELT_CLICKS_REPORT.toString());

        boolean dumpErrors = false;
        try {
            dump("seatbeltClicksReport", 1, seatbeltClicksReportCriteria, FormatType.PDF);
            dump("seatbeltClicksReport", 1, seatbeltClicksReportCriteria, FormatType.EXCEL);
        } catch (Throwable t) {
            dumpErrors = true;
        }

        assertFalse(dumpErrors);
    }

    /**
     * Creates mock group hierarchy.
     *
     * @return mocked hierarchy
     */
    private GroupHierarchy getLocalMockGroupHierarchy() {
        List<Group> groupList = new ArrayList<Group>();
        groupList.add(new Group(GROUP_ID, ACCOUNT_ID, GROUP_FULL_NAME, 1, GroupType.TEAM, 0, GROUP_FULL_NAME, 0, new LatLng(0, 0)));
        return new GroupHierarchy(groupList);
    }

    /**
     * Creates mocked driver scores.
     *
     * @return mocked list
     */
    private List<DriverVehicleScoreWrapper> getMockDriverScores() {
        List<DriverVehicleScoreWrapper> mockDriverScores = new ArrayList<DriverVehicleScoreWrapper>();
        mockDriverScores.add(DRIVER_SCORES);
        return mockDriverScores;
    }
}
