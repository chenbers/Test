package com.inthinc.pro.service.test.mock;

import javax.ws.rs.core.Response;

import com.inthinc.pro.service.phonecontrol.client.CellcontrolEndpoint;

/**
 * A stub implementation of {@link CellcontrolEndpoint} interface to be used for integration tests. Like a mock, this instance can be set an expected {@link Response} value through
 * the {@link CellcontrolServiceEndpointStub#setExpectedResponse(Response)} for testing purposes.
 * <p/>
 * This stub will service at URL http://<host>:<port>/service/api/API. At runtime, the server instance can be obtained from Spring's application context, under name
 * 'cellcontrolServiceStub'. Please check the applicationContext-test.xml file for more details.
 * <p/>
 * To use this class, use jetty to launch an embedded server. An example can be obtained from the {@link CellcontrolIntegrationTest} test class.
 */
public class CellcontrolServiceEndpointStub implements CellcontrolEndpoint {

    private Response expectedResponse;

    /**
     * @see com.inthinc.pro.service.phonecontrol.client.CellcontrolEndpoint#disablePhone(java.lang.String)
     */
    @Override
    public Response disablePhone(String cellPhoneNumber) {
        return expectedResponse;
    }

    /**
     * @see com.inthinc.pro.service.phonecontrol.client.CellcontrolEndpoint#enablePhone(java.lang.String)
     */
    @Override
    public Response enablePhone(String cellPhoneNumber) {
        return expectedResponse == null ? Response.ok().build() : expectedResponse;
    }

    public Response getExpectedResponse() {
        return expectedResponse == null ? Response.ok().build() : expectedResponse;
    }

    public void setExpectedResponse(Response expectedResponse) {
        this.expectedResponse = expectedResponse;
    }

}
