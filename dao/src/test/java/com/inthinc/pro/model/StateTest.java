package com.inthinc.pro.model;

import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.IntegrationConfig;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Date;

import static org.junit.Assert.*;


public class StateTest {

    private static SiloService siloService;
    private static final String XML_DATA_FILE = "ReportTest.xml";
    private static ITData itData = new ITData();
    Integer defaultValue = 0;


    @Before
    public void setUpBeforeTest() throws Exception {
        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

        siloService = new SiloServiceCreator(host, port).getService();

        itData = new ITData();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(XML_DATA_FILE);

        if (!itData.parseTestData(stream, siloService, false, false)) {
            throw new Exception("Error parsing Test data xml file");
        }
    }

    @Test
    public void testEquals(){

        State state1 = new State();
        state1.setAbbrev("UT");
        state1.setName("Utah");
        state1.setStateID(45);
        Boolean valueForState1 =  state1.equals(itData.address.getState());
        assertTrue(valueForState1);

        State state2 = new State();
        state2.setAbbrev("AL");
        state2.setName("Alabama");
        state2.setStateID(1);
        Boolean valueForState2 =  state2.equals(itData.address.getState());
        assertFalse(valueForState2);

    }
    @Test
    public void testHasCode(){

        State state1 = new State();
        state1.setAbbrev("UT");
        state1.setName("Utah");
        state1.setStateID(45);
        Integer hashCodeNotNull = state1.hashCode();
        assertFalse(hashCodeNotNull.equals(defaultValue));

        State state2 = new State();
        state2.setAbbrev("AL");
        state2.setStateID(1);
        Integer hashCodeNull = state2.hashCode();
        assertEquals(hashCodeNull,defaultValue);

    }
}