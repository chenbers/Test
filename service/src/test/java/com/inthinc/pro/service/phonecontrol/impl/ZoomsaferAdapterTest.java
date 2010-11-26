package com.inthinc.pro.service.phonecontrol.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.core.Response;

import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.junit.Test;

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
                zoomsaferEndpointMock.disablePhone(cellPhoneNumber, expectedTimestamp);
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
                zoomsaferEndpointMock.disablePhone(cellPhoneNumber, expectedTimestamp);
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
                zoomsaferEndpointMock.enablePhone(cellPhoneNumber, expectedTimestamp);
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
                zoomsaferEndpointMock.enablePhone(cellPhoneNumber, expectedTimestamp);
                times = 1;
            }
        };
    }
}
