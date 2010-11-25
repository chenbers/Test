package com.inthinc.pro.service.phonecontrol.impl;

import javax.ws.rs.core.Response;

import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.junit.Test;

import com.inthinc.pro.service.client.CellcontrolEndpoint;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapter;

public class CellcontrolAdapterTest {

    @Test
    public void testDisablePhone(final CellcontrolEndpoint cellcontrolEndpointMock) {
        
        final String cellPhoneNumber = "15145555555";
        PhoneControlAdapter cellControlAdapter = new CellcontrolAdapter(cellcontrolEndpointMock);
        
        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                cellcontrolEndpointMock.disablePhone(cellPhoneNumber);
                result = Response.ok().build();
            }
        };
        
        // Execution
        cellControlAdapter.disablePhone(cellPhoneNumber);
        
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
        PhoneControlAdapter cellControlAdapter = new CellcontrolAdapter(cellcontrolEndpointMock);

        // Expectations & stubbing
        new NonStrictExpectations() {
            {
                cellcontrolEndpointMock.enablePhone(cellPhoneNumber);
                result = Response.ok().build();
            }
        };

        // Execution
        cellControlAdapter.enablePhone(cellPhoneNumber);

        // Verification
        new Verifications() {
            {
                cellcontrolEndpointMock.enablePhone(cellPhoneNumber);
                times = 1;
            }
        };
    }
}
