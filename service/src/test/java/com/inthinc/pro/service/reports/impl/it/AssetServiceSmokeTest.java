package com.inthinc.pro.service.reports.impl.it;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClientExecutor;
import org.joda.time.DateMidnight;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.service.reports.impl.AssetServiceImpl;

public class AssetServiceSmokeTest {

    private static final int SAMPLE_GROUP_ID = 12;
    private static final int START_ROW = 1;
    private static final int END_ROW = 10;
    private static final int STATUS_BAD_REQUEST = 400;

    private static int port;

    private static AssetServiceClient assetServiceClient;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

    /**
     * Dummy test required to avoid JUnit initialization errors.
     */
    @Test
    public void testDummy() {}

    @BeforeClass
    public static void setUp() throws Exception {
        Server server = new Server(0);
        server.addHandler(new WebAppContext("src/main/webapp", "/service"));
        server.start();
        port = server.getConnectors()[0].getLocalPort();
        System.out.println("Port is " + port);

        HttpClientParams params = new HttpClientParams();
        params.setAuthenticationPreemptive(true);
        HttpClient httpClient = new HttpClient(params);
        Credentials defaultcreds = new UsernamePasswordCredentials("mraby", "password");
        httpClient.getState().setCredentials(new AuthScope("localhost", port, AuthScope.ANY_REALM), defaultcreds);
        ApacheHttpClientExecutor clientExecutor = new ApacheHttpClientExecutor(httpClient);

        assetServiceClient = ProxyFactory.create(AssetServiceClient.class, "http://localhost:" + port + "/service/api", clientExecutor);
    }

    @Test
    public void testGetRedFlagCount() {
        @SuppressWarnings("unused")
        ClientResponse<Integer> response = assetServiceClient.getRedFlagCount(SAMPLE_GROUP_ID);
        // assertEquals(STATUS_OK, response.getStatus());
    }

    @Test
    public void testGetRedFlagCountWithStartDate() {
        @SuppressWarnings("unused")
        ClientResponse<Integer> response = assetServiceClient.getRedFlagCount(SAMPLE_GROUP_ID, getOneYearAgoDate(new Date()));
        // Assertions to be done manually as we can't rely on data in the DB until test data generation tools are in place
        // assertEquals(STATUS_OK, response.getStatus());
    }

    @Test
    public void testGetRedFlagCountWithStartDateAndEndDate() {
        Date today = new Date();

        @SuppressWarnings("unused")
        ClientResponse<Integer> response = assetServiceClient.getRedFlagCount(SAMPLE_GROUP_ID, getOneYearAgoDate(today), getOneMonthAgoDate(today));
        // Assertions to be done manually as we can't rely on data in the DB until test data generation tools are in place
        // assertEquals(STATUS_OK, response.getStatus());
    }

    @Test
    public void testGetRedFlags() {
        @SuppressWarnings("unused")
        ClientResponse<List<RedFlag>> response = assetServiceClient.getRedFlags(SAMPLE_GROUP_ID, START_ROW, END_ROW);
        // Assertions to be done manually as we can't rely on data in the DB until test data generation tools are in place
        // assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetRedFlagsWithStartDate() {
        @SuppressWarnings("unused")
        ClientResponse<List<RedFlag>> response = assetServiceClient.getRedFlags(SAMPLE_GROUP_ID, START_ROW, END_ROW, getOneYearAgoDate(new Date()));
        // Assertions to be done manually as we can't rely on data in the DB until test data generation tools are in place
        // assertEquals(STATUS_OK, response.getStatus());
    }

    @Test
    public void testGetRedFlagsWithStartDateAndEndDate() {
        Date today = new Date();

        @SuppressWarnings("unused")
        ClientResponse<List<RedFlag>> response = assetServiceClient.getRedFlags(SAMPLE_GROUP_ID, START_ROW, END_ROW, getOneYearAgoDate(today), getOneMonthAgoDate(today));
        // Assertions to be done manually as we can't rely on data in the DB until test data generation tools are in place
        // assertEquals(STATUS_OK, response.getStatus());
    }

    @Test
    public void testBadRequestOnGetCountOlderThanOneYear() {
        Date today = new Date();

        ClientResponse<Integer> response = assetServiceClient.getRedFlagCount(SAMPLE_GROUP_ID, getTwoYearsAgoDate(today));
        assertEquals(STATUS_BAD_REQUEST, response.getStatus());
        assertNotNull(response.getMetadata().getFirst(AssetServiceImpl.HEADER_ERROR_MESSAGE));
    }

    @Test
    public void testBadRequestOnGetCountStartDateGreaterThanEndDate() {
        Date today = new Date();

        ClientResponse<Integer> response = assetServiceClient.getRedFlagCount(SAMPLE_GROUP_ID, getOneYearAgoDate(today), getTwoYearsAgoDate(today));
        assertEquals(STATUS_BAD_REQUEST, response.getStatus());
        assertNotNull(response.getMetadata().getFirst(AssetServiceImpl.HEADER_ERROR_MESSAGE));
    }

    @Test
    public void testBadRequestOnGetFlagsOlderThanOneYear() {
        Date today = new Date();

        Response response = assetServiceClient.getRedFlags(SAMPLE_GROUP_ID, START_ROW, END_ROW, getTwoYearsAgoDate(today));
        assertEquals(STATUS_BAD_REQUEST, response.getStatus());
        assertNotNull(response.getMetadata().getFirst(AssetServiceImpl.HEADER_ERROR_MESSAGE));
    }

    @Test
    public void testBadRequestOnGetFlagsStartDateGreaterThanEndDate() {
        Date today = new Date();

        ClientResponse<List<RedFlag>> response = assetServiceClient.getRedFlags(SAMPLE_GROUP_ID, START_ROW, END_ROW, getOneYearAgoDate(today), getTwoYearsAgoDate(today));
        assertEquals(STATUS_BAD_REQUEST, response.getStatus());
        assertNotNull(response.getMetadata().getFirst(AssetServiceImpl.HEADER_ERROR_MESSAGE));
    }

    @Test
    public void testBadRequestOnGetFlagsStartRowGreaterThanEndRow() {
        Date today = new Date();

        ClientResponse<List<RedFlag>> response = assetServiceClient.getRedFlags(SAMPLE_GROUP_ID, END_ROW, START_ROW, getTwoYearsAgoDate(today), getOneYearAgoDate(today));
        assertEquals(STATUS_BAD_REQUEST, response.getStatus());
        assertNotNull(response.getMetadata().getFirst(AssetServiceImpl.HEADER_ERROR_MESSAGE));
    }

    private String getOneYearAgoDate(Date day) {
        DateMidnight todayMidnight = new DateMidnight(day);
        return formatter.format(todayMidnight.minusYears(1).toDate());
    }

    private String getTwoYearsAgoDate(Date day) {
        DateMidnight todayMidnight = new DateMidnight(day);
        return formatter.format(todayMidnight.minusYears(2).toDate());
    }

    private String getOneMonthAgoDate(Date day) {
        DateMidnight todayMidnight = new DateMidnight(day);
        return formatter.format(todayMidnight.minusMonths(START_ROW).toDate());
    }
}
