package it.com.inthinc.pro.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import it.com.inthinc.pro.dao.Util;
import it.config.ITDataSource;
import it.config.IntegrationConfig;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.CustomMapJDBCDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.CustomMap;
import com.inthinc.pro.model.GoogleMapType;


public class CustomMapJDBCDAOTest extends BaseJDBCTest {
    
    
    
    private static final Integer TESTING_ACCOUNT_ID = 5620;
    

// run this if the test account gets removed
//    private static final Integer TESTING_SILO = 0; // this silo can be wiped out/restored at anytime
//    private Account createTestAccount(int id) {
//        IntegrationConfig config = new IntegrationConfig();
//        String host = config.get(IntegrationConfig.SILO_HOST).toString();
//        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
//        SiloService siloService = new SiloServiceCreator(host, port).getService();
//        AccountHessianDAO accountDAO = new AccountHessianDAO();
//        accountDAO.setSiloService(siloService);
//        Account account = new Account(null, Status.ACTIVE);
//        String timeStamp = Calendar.getInstance().getTime().toString();
//        account.setAcctName(timeStamp + "_" + id);
//        account.setHos(AccountHOSType.NONE);
//        // create
//        Integer siloID = TESTING_SILO;
//        Integer acctID = accountDAO.create(siloID, account);
//        account.setAccountID(acctID);
//        
//        return account;
//    }
    
    @Test
    public void customMapDao() {
        
//        Account testAccount = createTestAccount(3);
        Account testAccount = new Account();
        testAccount.setAccountID(TESTING_ACCOUNT_ID);
        
        // simplest case -- no vehicle
        CustomMapJDBCDAO customMapDAO = new CustomMapJDBCDAO();
        customMapDAO.setDataSource(new ITDataSource().getRealDataSource());

System.out.println("acct id " + testAccount.getAccountID());
        CustomMap customMap = new CustomMap();
        customMap.setUrl("http://stage-mail.iwiglobal.com:8080/cgi-bin/tilecache.cgi/1.0.0/barSat/{Z}/{X}/{Y}.png?type=google");
        customMap.setName("Aerial");
        customMap.setMinZoom(0);
        customMap.setMaxZoom(20);
        customMap.setOpacity(1.0);
        customMap.setPngFormat(Boolean.TRUE);
        customMap.setBottomLayer(GoogleMapType.G_SATELLITE_MAP);
        customMap.setAcctID(testAccount.getAccountID());
        
        // create
        Integer id = customMapDAO.create(testAccount.getAccountID(), customMap);
        System.out.println("returned id " + id);        
        customMap.setCustomMapID(id);
            
        // find
        CustomMap retrievedCustomMap = customMapDAO.findByID(id);
        Util.compareObjects(customMap, retrievedCustomMap);
        
        // update
        CustomMap updateCustomMap = new CustomMap();
        updateCustomMap.setUrl("UPDATE"+customMap.getUrl());
        updateCustomMap.setName("UPDATE"+customMap.getName());
        updateCustomMap.setMinZoom(customMap.getMinZoom() + 1);
        updateCustomMap.setMaxZoom(customMap.getMaxZoom() + 1);
        updateCustomMap.setOpacity(0.5);
        updateCustomMap.setPngFormat(Boolean.FALSE);
        updateCustomMap.setBottomLayer(GoogleMapType.NONE);
        updateCustomMap.setAcctID(customMap.getAcctID());
        updateCustomMap.setCustomMapID(customMap.getCustomMapID());
        customMapDAO.update(updateCustomMap);

        retrievedCustomMap = customMapDAO.findByID(id);
        Util.compareObjects(updateCustomMap, retrievedCustomMap);
        
        // create a second one
        customMap.setName("Second one " + customMap.getName());
        Integer id2 = customMapDAO.create(testAccount.getAccountID(), customMap);
        System.out.println("returned id " + id2);        
        customMap.setCustomMapID(id2);
        
        List<CustomMap> customMapList = customMapDAO.getCustomMapsByAcctID(testAccount.getAccountID());
        assertEquals("Expected 2 customMaps in list", 2, customMapList.size());
        
        for (CustomMap cm : customMapList) {
            if (cm.getName().startsWith("Second"))
                Util.compareObjects(customMap, cm);
             else Util.compareObjects(updateCustomMap, cm);
        }
        
        

        // delete
        customMapDAO.deleteByID(customMap.getCustomMapID());
        retrievedCustomMap = customMapDAO.findByID(customMap.getCustomMapID());
        assertNull("find should return null after delete", retrievedCustomMap);
        customMapDAO.deleteByID(updateCustomMap.getCustomMapID());
        retrievedCustomMap = customMapDAO.findByID(updateCustomMap.getCustomMapID());
        assertNull("find should return null after delete", retrievedCustomMap);
    }

}
