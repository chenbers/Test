package com.inthinc.pro.service.reports.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import mockit.Expectations;
import mockit.Mocked;

import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.reports.ifta.model.MileageByVehicle;
import com.inthinc.pro.service.impl.BaseUnitTest;
import com.inthinc.pro.service.reports.facade.impl.ReportsFacadeImpl;
import com.inthinc.pro.util.GroupList;

/**
 * Unit tests for MileageByVehicle IFTA Services.
 */
public class IFTAServiceMileageByVehicleImplTest extends BaseUnitTest {

    private final Integer expectedGroupID = 15;
    private final Date startDate = getMidnight().getTime();
    private final Date endDate = new Date();
    private final Locale locale = Locale.US;
    private final MeasurementType measureType = MeasurementType.ENGLISH;
    private List<MileageByVehicle> list;
    
    @SuppressWarnings("serial")
    private List<Integer> expectedIds = new ArrayList<Integer>() {{
        add(expectedGroupID);
    }};
    
    @SuppressWarnings("serial")
    private List<Integer> sampleIds = new ArrayList<Integer>() {{
        add(1);
        add(2);
    }};

    @Mocked
    private ReportsFacadeImpl reportsFacadeMock;

    private IFTAServiceMileageByVehicleImpl iftaServiceSUT;

    @Before
    public void beforeTest() {
        list = new ArrayList<MileageByVehicle>();
        list.add(new MileageByVehicle());

        iftaServiceSUT = new IFTAServiceMileageByVehicleImpl(reportsFacadeMock);
    }

    @Test
    public void testGetMileageByVehicleWhenOneItemListReturned() {
        new Expectations() {
            {
                reportsFacadeMock.getMileageByVehicle(expectedIds, (Interval) any, true, locale, measureType);
                returns(list);
            }
        };
        // happy path
        Response response = iftaServiceSUT.getMileageByVehicle(expectedGroupID, 
                startDate, endDate, true, locale, measureType);
        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetMileageByVehicleWhenNullListReturned() {
        new Expectations() {
            {
                reportsFacadeMock.getMileageByVehicle(expectedIds, (Interval) any, true, locale, measureType);
                returns(null);
            }
        };
        // check for null list
        Response response = iftaServiceSUT.getMileageByVehicle(expectedGroupID, 
                startDate, endDate, true, locale, measureType);
        assertNotNull(response);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetMileageByVehicleWhenEmptyListReturned() {
        new Expectations() {
            {
                reportsFacadeMock.getMileageByVehicle(expectedIds, (Interval) any, true, locale, measureType);
                returns(new ArrayList<MileageByVehicle>());
            }
        };
        // check for empty list
        Response response = iftaServiceSUT.getMileageByVehicle(expectedGroupID, 
                startDate, endDate, true, locale, measureType);
        assertNotNull(response);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetMileageByVehicleWhenExceptionThrown() {
        new Expectations() {
            {
                reportsFacadeMock.getMileageByVehicle(expectedIds, (Interval) any, false, locale, measureType);
                returns(Exception.class);
            }
        };
        // check for exception
        Response response = iftaServiceSUT.getMileageByVehicle(expectedGroupID, 
                startDate, endDate, false, locale, measureType);
        assertNotNull(response);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetMileageByVehicleDefaults() {
        new Expectations() {
            {
                reportsFacadeMock.getMileageByVehicle(expectedIds, (Interval) any, false, locale, measureType);
                returns(list);
            }
        };
        Response response = iftaServiceSUT.getMileageByVehicleDefaults(expectedGroupID, locale, measureType);
        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetMileageByVehicleMultiGroup() {
        new Expectations() {
            {
                reportsFacadeMock.getMileageByVehicle(withEqual(sampleIds), (Interval) any, false, locale, measureType);
                returns(list);
            }
        };
        
        GroupList list = new GroupList(sampleIds);
        
        Response response = iftaServiceSUT.getMileageByVehicleDefaultsMultiGroup(list, locale, measureType);
        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetMileageByVehicleWithIfta() {
        new Expectations() {
            {
                reportsFacadeMock.getMileageByVehicle(expectedIds, (Interval) any, true, locale, measureType);
                returns(list);
            }
        };

        Response response = iftaServiceSUT.getMileageByVehicleWithIfta(expectedGroupID, locale, measureType);
        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetMileageByVehicleWithDates() {
        new Expectations() {
            {
                reportsFacadeMock.getMileageByVehicle(expectedIds, (Interval) any, false, locale, measureType);
                returns(list);
            }
        };

        Response response = iftaServiceSUT.getMileageByVehicleWithDates(expectedGroupID, 
                startDate, endDate, locale, measureType);
        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetMileageByVehicleWithIftaAndDates() {
        new Expectations() {
            {
                reportsFacadeMock.getMileageByVehicle(expectedIds, (Interval) any, true, locale, measureType);
                returns(list);
            }
        };

        Response response = iftaServiceSUT.getMileageByVehicleWithIftaAndDates(expectedGroupID, 
                startDate, endDate, locale, measureType);
        assertNotNull(response);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    private Calendar getMidnight() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0); // set hour to midnight
        today.set(Calendar.MINUTE, 0); // set minute in hour
        today.set(Calendar.SECOND, 0); // set second in minute
        today.set(Calendar.MILLISECOND, 0); // set millis in second
        return today;
    }
}
