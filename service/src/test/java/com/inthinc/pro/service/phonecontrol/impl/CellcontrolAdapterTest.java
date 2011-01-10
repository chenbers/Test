package com.inthinc.pro.service.phonecontrol.impl;

import static junit.framework.Assert.fail;

import javax.ws.rs.core.Response;

import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.junit.Test;

import com.inthinc.pro.service.exceptions.RemoteErrorException;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapter;
import com.inthinc.pro.service.phonecontrol.client.CellcontrolEndpoint;

public class CellcontrolAdapterTest {

    @Test
    public void testDisablePhone(final CellcontrolEndpoint cellcontrolEndpointMock) {

        final String cellPhoneNumber = "15145555555";
        PhoneControlAdapter cellcontrolAdapter = new CellcontrolAdapter(cellcontrolEndpointMock);

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                cellcontrolEndpointMock.disablePhone(cellPhoneNumber);
                result = Response.ok().build();
            }
        };

        // Execution
        cellcontrolAdapter.disablePhone(cellPhoneNumber);

        // Verification
        new Verifications() {
            {
                cellcontrolEndpointMock.disablePhone(cellPhoneNumber);
                times = 1;
            }
        };
    }

    @Test
    public void testEnablePhone(final CellcontrolEndpoint cellcontrolEndpointMock) {

        final String cellPhoneNumber = "15145555555";
        PhoneControlAdapter cellcontrolAdapter = new CellcontrolAdapter(cellcontrolEndpointMock);

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                cellcontrolEndpointMock.enablePhone(cellPhoneNumber);
                result = Response.ok().build();
            }
        };

        // Execution
        cellcontrolAdapter.enablePhone(cellPhoneNumber);

        // Verification
        new Verifications() {
            {
                cellcontrolEndpointMock.enablePhone(cellPhoneNumber);
                times = 1;
            }
        };
    }

    @Test
    public void testThrowsRemoteServerErrorOnNonOkResponse(final CellcontrolEndpoint cellcontrolEndpointMock) {

        final String cellPhoneNumber = "15145555555";
        PhoneControlAdapter cellcontrolAdapter = new CellcontrolAdapter(cellcontrolEndpointMock);

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                cellcontrolEndpointMock.enablePhone(cellPhoneNumber);
                result = Response.serverError().build();
            }
        };

        // Execution
        try {
            cellcontrolAdapter.enablePhone(cellPhoneNumber);
            fail("Expected exception " + RemoteErrorException.class + " not thrown.");
        } catch (RemoteErrorException e) {
            // Expected exception
        }
    }
}
