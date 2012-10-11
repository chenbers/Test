package com.inthinc.pro.service.reports.impl;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import mockit.Expectations;
import mockit.Mocked;

import org.junit.Test;
import org.springframework.security.AccessDeniedException;

import com.inthinc.pro.service.adapters.GroupDAOAdapter;
import com.inthinc.pro.service.impl.BaseUnitTest;
import com.inthinc.pro.util.ReportsUtil;

public class ReportsUtilTest extends BaseUnitTest {

    private ReportsUtil serviceSUT = new ReportsUtil();

    private Integer expectedGroupID = 1504;

    @Test
    public void checkParametersWithBadInputTestNullGroup() {

        Response response = serviceSUT.checkParameters(null, buildDateFromString("20090101"), buildDateFromString("20100101"));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void checkParametersWithBadInputTestNullStartDate() {

        Response response = serviceSUT.checkParameters(expectedGroupID, null, buildDateFromString("20100101"));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void checkParametersWithBadInputTestNullEndDate() {

        Response response = serviceSUT.checkParameters(expectedGroupID, buildDateFromString("20100101"), null);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void checkParametersWithBadInputTestStartDateBiggerThanEndDate() {

        Response response = serviceSUT.checkParameters(expectedGroupID, buildDateFromString("20100101"), buildDateFromString("20090101"));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void checkParametersWithBadInputTestNegativeGroupID() {

        Response response = serviceSUT.checkParameters(-expectedGroupID, buildDateFromString("20090101"), buildDateFromString("20100101"));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void checkParametersWithBadInputTestUnexistingGroupID(@Mocked final GroupDAOAdapter groupDAOAdapterMock) {

        new Expectations() {
            {
                groupDAOAdapterMock.findByID(expectedGroupID);
                result = null;
            }
        };

        serviceSUT.setGroupDAOAdapter(groupDAOAdapterMock);
        Response response = serviceSUT.checkParameters(expectedGroupID, buildDateFromString("20090101"), buildDateFromString("20100101"));

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void checkParametersWithBadInputTestDeniedAccess(@Mocked final GroupDAOAdapter groupDAOAdapterMock) {

        new Expectations() {
            {
                groupDAOAdapterMock.findByID(expectedGroupID);
                result = new AccessDeniedException(null);
            }
        };

        serviceSUT.setGroupDAOAdapter(groupDAOAdapterMock);
        Response response = serviceSUT.checkParameters(expectedGroupID, buildDateFromString("20090101"), buildDateFromString("20100101"));

        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

}
