package it.com.inthinc.pro.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;

import java.util.Calendar;

import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.ZonePublishDAO;
import com.inthinc.pro.dao.hessian.AccountHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.ZonePublishJDBCDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.AccountHOSType;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.zone.ZonePublish;
import com.inthinc.pro.model.zone.option.type.ZoneVehicleType;


public class ZonePublishJDBCDAOTest extends BaseJDBCTest {
    
    private static SiloService siloService;
    
    private static final Integer TESTING_SILO = 0; // this silo can be wiped out/restored at anytime

    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        IntegrationConfig config = new IntegrationConfig();
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        siloService = new SiloServiceCreator(host, port).getService();
    }
    
    private Account createTestAccount(int id) {
        AccountHessianDAO accountDAO = new AccountHessianDAO();
        accountDAO.setSiloService(siloService);
        Account account = new Account(null, Status.ACTIVE);
        String timeStamp = Calendar.getInstance().getTime().toString();
        account.setAcctName(timeStamp + "_" + id);
        account.setHos(AccountHOSType.NONE);
        // create
        Integer siloID = TESTING_SILO;
        Integer acctID = accountDAO.create(siloID, account);
        account.setAccountID(acctID);
        
        return account;
    }

    @Test
    public void publishZone() {
        
        Account testAccount = createTestAccount(1);
        assertNotNull("test Account was not successfully created", testAccount);
        
        // simplest case -- no vehicle
        ZonePublishDAO zonePublishDAO = new ZonePublishJDBCDAO();
        ((ZonePublishJDBCDAO)zonePublishDAO).setDataSource(new ITDataSource().getRealDataSource());

System.out.println("acct id " + testAccount.getAccountID());        
        for (ZoneVehicleType zoneVehicleType : ZoneVehicleType.values()) {
            ZonePublish zonePublish = new ZonePublish();
            zonePublish.setAcctID(testAccount.getAccountID());
            zonePublish.setZoneVehicleType(zoneVehicleType);
            String zoneDataStr = new String(zoneVehicleType.getName() + "ZONE DATA");
            zonePublish.setPublishZoneData(zoneDataStr.getBytes());
            
            // create
            zonePublishDAO.publishZone(zonePublish);
            
            ZonePublish retrievedZonePublish = zonePublishDAO.getPublishedZone(testAccount.getAccountID(), zoneVehicleType);
            assertEquals("acctID should match", zonePublish.getAcctID(), retrievedZonePublish.getAcctID());
            assertEquals("zoneVehicleType should match", zonePublish.getZoneVehicleType(), retrievedZonePublish.getZoneVehicleType());
            for (int i = 0; i < zonePublish.getPublishZoneData().length; i++)
                assertEquals("Byte " + i, zonePublish.getPublishZoneData()[i], retrievedZonePublish.getPublishZoneData()[i]);
            
            
            // update
            zoneDataStr += " UPDATE";
            zonePublish.setPublishZoneData(zoneDataStr.getBytes());
            zonePublishDAO.publishZone(zonePublish);
            
            retrievedZonePublish = zonePublishDAO.getPublishedZone(testAccount.getAccountID(), zoneVehicleType);
            assertEquals("acctID should match", zonePublish.getAcctID(), retrievedZonePublish.getAcctID());
            assertEquals("zoneVehicleType should match", zonePublish.getZoneVehicleType(), retrievedZonePublish.getZoneVehicleType());
            for (int i = 0; i < zonePublish.getPublishZoneData().length; i++)
                assertEquals("Byte " + i, zonePublish.getPublishZoneData()[i], retrievedZonePublish.getPublishZoneData()[i]);
        }
            
    }

    @Test
    public void publishZoneCrud() {
        
        Account testAccount = createTestAccount(2);
        assertNotNull("test Account was not successfully created", testAccount);
        
        // simplest case -- no vehicle
        ZonePublishDAO zonePublishDAO = new ZonePublishJDBCDAO();
        ((ZonePublishJDBCDAO)zonePublishDAO).setDataSource(new ITDataSource().getRealDataSource());

System.out.println("acct id " + testAccount.getAccountID());        
        for (ZoneVehicleType zoneVehicleType : ZoneVehicleType.values()) {
            ZonePublish zonePublish = new ZonePublish();
            zonePublish.setAcctID(testAccount.getAccountID());
            zonePublish.setZoneVehicleType(zoneVehicleType);
            String zoneDataStr = new String(zoneVehicleType.getName() + "ZONE DATA");
            zonePublish.setPublishZoneData(zoneDataStr.getBytes());
            
            // create
            Integer id = zonePublishDAO.create(testAccount.getAccountID(), zonePublish);
            System.out.println("returned id " + id);        
            zonePublish.setZonePublishID(id);
            
            // find
            ZonePublish retrievedZonePublish = zonePublishDAO.findByID(id);
            assertEquals("acctID should match", zonePublish.getAcctID(), retrievedZonePublish.getAcctID());
            assertEquals("zoneVehicleType should match", zonePublish.getZoneVehicleType(), retrievedZonePublish.getZoneVehicleType());
            for (int i = 0; i < zonePublish.getPublishZoneData().length; i++)
                assertEquals("Byte " + i, zonePublish.getPublishZoneData()[i], retrievedZonePublish.getPublishZoneData()[i]);
            
            //update
            zoneDataStr += " UPDATE";
            zonePublish.setPublishZoneData(zoneDataStr.getBytes());
            zonePublishDAO.publishZone(zonePublish);
            
            retrievedZonePublish = zonePublishDAO.findByID(id);
            assertEquals("acctID should match", zonePublish.getAcctID(), retrievedZonePublish.getAcctID());
            assertEquals("zoneVehicleType should match", zonePublish.getZoneVehicleType(), retrievedZonePublish.getZoneVehicleType());
            for (int i = 0; i < zonePublish.getPublishZoneData().length; i++)
                assertEquals("Byte " + i, zonePublish.getPublishZoneData()[i], retrievedZonePublish.getPublishZoneData()[i]);
        }
    }
}
