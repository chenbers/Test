package com.inthinc.pro.service.phonecontrol.impl;

import static junit.framework.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.core.Response;

import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.junit.Test;

import com.inthinc.pro.service.exceptions.RemoteErrorException;
import com.inthinc.pro.service.phonecontrol.Clock;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapter;
import com.inthinc.pro.service.phonecontrol.client.ZoomsaferEndPoint;

public class ZoomsaferAdapterTest {

    @Test
    public void testDisablePhone(final ZoomsaferEndPoint zoomsaferEndpointMock, final Clock clockMock) throws InterruptedException {

        final String cellPhoneNumber = "15145555555";
        final Date now = new Date();
        final String expectedTimestamp = new SimpleDateFormat(ZoomsaferAdapter.ZOOM_SAFER_TIMESTAMP_FORMAT).format(now);

        PhoneControlAdapter zoomsaferAdapter = new ZoomsaferAdapter(zoomsaferEndpointMock, clockMock);

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                zoomsaferEndpointMock.disablePhone(ZoomsaferEndPoint.DISABLE_PHONE_EVENT_TYPE, cellPhoneNumber, expectedTimestamp);
                result = Response.ok().build();

                clockMock.getNow();
                result = now;
            }
        };

        // Execution
        zoomsaferAdapter.disablePhone(cellPhoneNumber);

        // Verification
        new Verifications() {
            {
                zoomsaferEndpointMock.disablePhone(ZoomsaferEndPoint.DISABLE_PHONE_EVENT_TYPE, cellPhoneNumber, expectedTimestamp);
                times = 1;
            }
        };
    }

    @Test
    public void testEnablePhone(final ZoomsaferEndPoint zoomsaferEndpointMock, final Clock clockMock) {

        final String cellPhoneNumber = "15145555555";
        final Date now = new Date();
        final String expectedTimestamp = new SimpleDateFormat(ZoomsaferAdapter.ZOOM_SAFER_TIMESTAMP_FORMAT).format(now);

        PhoneControlAdapter zoomsaferAdapter = new ZoomsaferAdapter(zoomsaferEndpointMock, clockMock);

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                zoomsaferEndpointMock.enablePhone(ZoomsaferEndPoint.ENABLE_PHONE_EVENT_TYPE, cellPhoneNumber, expectedTimestamp);
                result = Response.ok().build();

                clockMock.getNow();
                result = now;
            }
        };

        // Execution
        zoomsaferAdapter.enablePhone(cellPhoneNumber);

        // Verification
        new Verifications() {
            {
                zoomsaferEndpointMock.enablePhone(ZoomsaferEndPoint.ENABLE_PHONE_EVENT_TYPE, cellPhoneNumber, expectedTimestamp);
                times = 1;
            }
        };
    }

    @Test
    public void testThrowsRemoteServerErrorOnNonOkResponse(final ZoomsaferEndPoint zoomsaferEndpointMock, final Clock clockMock) {

        final String cellPhoneNumber = "15145555555";
        final Date now = new Date();
        final String expectedTimestamp = new SimpleDateFormat(ZoomsaferAdapter.ZOOM_SAFER_TIMESTAMP_FORMAT).format(now);
        PhoneControlAdapter zoomsaferAdapter = new ZoomsaferAdapter(zoomsaferEndpointMock, clockMock);

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                zoomsaferEndpointMock.enablePhone(ZoomsaferEndPoint.ENABLE_PHONE_EVENT_TYPE, cellPhoneNumber, expectedTimestamp);
                result = Response.serverError().build();

                clockMock.getNow();
                result = now;
            }
        };

        // Execution
        try {
            zoomsaferAdapter.enablePhone(cellPhoneNumber);
            fail("Expected exception " + RemoteErrorException.class + " not thrown.");
        } catch (RemoteErrorException e) {
            // Expected exception
        }
    }
}
