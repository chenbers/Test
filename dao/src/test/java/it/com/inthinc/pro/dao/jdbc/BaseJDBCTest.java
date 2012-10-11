package it.com.inthinc.pro.dao.jdbc;

import it.config.ITDataSource;
import it.config.IntegrationConfig;

import org.junit.BeforeClass;
import org.junit.Test;


public class BaseJDBCTest {
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        
            
        
//        String host = config.get(IntegrationConfig.SILO_HOST).toString();
//        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
//        siloService = new SiloServiceCreator(host, port).getService();

        
//        itData = new ITData();
//
//        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(XML_DATA_FILE);
//        if (!itData.parseTestData(stream, siloService, true, true)) {
//            throw new Exception("Error parsing Test data xml file");
//        }
//        zoneAlerts = zoneAlertHessianDAO.getZoneAlerts(itData.account.getAcctID());
//        originalZoneAlerts = zoneAlertHessianDAO.getZoneAlerts(itData.account.getAcctID());
//        
//        redFlagAlerts = redFlagAlertHessianDAO.getRedFlagAlerts(itData.account.getAcctID());
//        originalRedFlagAlerts = redFlagAlertHessianDAO.getRedFlagAlerts(itData.account.getAcctID());
    }


    @Test
    public void dummy()
    {
        
    }
}
