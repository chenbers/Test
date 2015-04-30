package it.com.inthinc.pro.dao.jdbc;


import com.inthinc.pro.dao.hessian.RedFlagAlertHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.RedFlagAlertJDBCDAO;
import com.inthinc.pro.model.*;

import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import javax.sql.DataSource;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RedFlagAlertJDBCDAOTest extends SimpleJdbcDaoSupport {

    private static SiloService siloService;
    private static final String XML_DATA_FILE = "AlertTest.xml";
    private static ITData itData = new ITData();    
    private RedFlagAlertHessianDAO redFlagAlertHessianDAO;


    @Before
    public void setUpBeforeTest() throws Exception {
        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

        siloService = new SiloServiceCreator(host, port).getService();

        initApp();
        itData = new ITData();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(XML_DATA_FILE);
        if (!itData.parseTestData(stream, siloService, true, true, true)) {
            throw new Exception("Error parsing Test data xml file");
        }
        
        redFlagAlertHessianDAO = new RedFlagAlertHessianDAO();
        redFlagAlertHessianDAO.setSiloService(new SiloServiceCreator(host, port).getService());
    }

    private static void initApp() throws Exception {
    }

    @Test
    public void getRedFlagAlertsTest() throws Exception {
        RedFlagAlertJDBCDAO redFlagAlertJDBCDAO = new RedFlagAlertJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        redFlagAlertJDBCDAO.setDataSource(dataSource);

        RedFlagAlert team = itData.redFlagAlertList.get(0);
        Integer acctID = team.getAccountID();

        List<RedFlagAlert> redFlagAlertList = redFlagAlertJDBCDAO.getRedFlagAlerts(acctID);
        List<RedFlagAlert> redFlagAlertListHessian = redFlagAlertHessianDAO.getRedFlagAlerts(acctID);

        assertTrue(redFlagAlertList.size() > 0);
        assertTrue(redFlagAlertListHessian.size() > 0);
        assertEquals(redFlagAlertListHessian.size(), redFlagAlertList.size());        
        
        compare(redFlagAlertList, redFlagAlertListHessian);
        checkNotDeleted(redFlagAlertList);
        checkNotDeleted(redFlagAlertListHessian);
    }

    @Test
    public void getRedFlagAlertsByUserIDTest() throws Exception {
        RedFlagAlertJDBCDAO redFlagAlertJDBCDAO = new RedFlagAlertJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        redFlagAlertJDBCDAO.setDataSource(dataSource);

        RedFlagAlert team = itData.redFlagAlertList.get(0);
        Integer userID = team.getUserID();

        List<RedFlagAlert> redFlagAlertListUser = redFlagAlertJDBCDAO.getRedFlagAlertsByUserID(userID);
        List<RedFlagAlert> redFlagAlertListUserHessian = redFlagAlertHessianDAO.getRedFlagAlertsByUserID(userID);

        assertTrue(redFlagAlertListUser.size() > 0);
        assertTrue(redFlagAlertListUserHessian.size() > 0);
        assertEquals(redFlagAlertListUserHessian.size(), redFlagAlertListUser.size());
        
        compare(redFlagAlertListUser, redFlagAlertListUserHessian);
        checkNotDeleted(redFlagAlertListUser);
        checkNotDeleted(redFlagAlertListUserHessian);
    }

    @Test
    public void findbyIDTest() throws Exception {
        RedFlagAlertJDBCDAO redFlagAlertJDBCDAO = new RedFlagAlertJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        redFlagAlertJDBCDAO.setDataSource(dataSource);

        RedFlagAlert team = itData.redFlagAlertList.get(0);
        Integer alertID = team.getAlertID();

        RedFlagAlert rf = redFlagAlertJDBCDAO.findByID(alertID);
        assertNotNull(rf);
        
        RedFlagAlert rfHes = redFlagAlertHessianDAO.findByID(alertID);
        assertNotNull(rfHes);
        
        compare(rf, rfHes);
    }

    @Test
    public void getAlertsByTeamGroupIDTest() throws Exception {
        RedFlagAlertJDBCDAO redFlagAlertJDBCDAO = new RedFlagAlertJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        redFlagAlertJDBCDAO.setDataSource(dataSource);

        GroupData team = itData.teamGroupData.get(0);
        Integer groupID = team.group.getGroupID();

        List<RedFlagAlert> redflagByGroup = redFlagAlertJDBCDAO.getAlertsByTeamGroupID(groupID);
        List<RedFlagAlert> redflagByGroupHessian = redFlagAlertHessianDAO.getAlertsByTeamGroupID(groupID); //hessian returns empty list for some reason

        assertNotNull(redflagByGroup);
        assertNotNull(redflagByGroupHessian);
        
        assertTrue(redflagByGroup.size() > 0);
        checkNotDeleted(redflagByGroup);
    }

    @Test
    public void getRedFlagAlertsByUserIDdeepTest() throws Exception {
        RedFlagAlertJDBCDAO redFlagAlertJDBCDAO = new RedFlagAlertJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        redFlagAlertJDBCDAO.setDataSource(dataSource);

        RedFlagAlert team = itData.redFlagAlertList.get(0);
        Integer userID = team.getUserID();

        List<RedFlagAlert> redFlagAlertListUserD = redFlagAlertJDBCDAO.getRedFlagAlertsByUserIDDeep(userID);
        List<RedFlagAlert> redFlagAlertListUserDHessian = redFlagAlertHessianDAO.getRedFlagAlertsByUserIDDeep(userID);

        assertTrue(redFlagAlertListUserD.size() > 0);
        assertTrue(redFlagAlertListUserDHessian.size() > 0);
        assertEquals(redFlagAlertListUserD.size(), redFlagAlertListUserDHessian.size());
        
        compare(redFlagAlertListUserD, redFlagAlertListUserDHessian);
        checkNotDeleted(redFlagAlertListUserD);
        checkNotDeleted(redFlagAlertListUserDHessian);
    }

    @Test
    public void createAndUpdateTest() throws Exception {
        RedFlagAlertJDBCDAO redFlagAlertJDBCDAO = new RedFlagAlertJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        redFlagAlertJDBCDAO.setDataSource(dataSource);
        boolean returnRedFlagID = false;

        RedFlagAlert team = itData.redFlagAlertList.get(0);
        Integer userID = team.getUserID();

        RedFlagAlert rfCreate= new RedFlagAlert();

        rfCreate.setStatus(Status.ACTIVE);
        rfCreate.setModified(new Date());
        rfCreate.setAccountID(1);
        rfCreate.setUserID(userID);
        rfCreate.setName("name");
        rfCreate.setDescription("description");
        rfCreate.setHardAcceleration(2);
        rfCreate.setHardBrake(2);
        rfCreate.setHardTurn(4);
        rfCreate.setHardVertical(3);
        rfCreate.setSeverityLevel(RedFlagLevel.CRITICAL);
        rfCreate.setNotifyManagers(false);
        rfCreate.setZoneID(31211);

        Integer alertID = redFlagAlertJDBCDAO.create(rfCreate.getAccountID(), rfCreate);
        returnRedFlagID = (alertID != null);
        assertTrue(returnRedFlagID);

        //find by ID
        RedFlagAlert rfFind = redFlagAlertJDBCDAO.findByID(alertID);

        assertEquals(1, rfCreate.getStatus().getCode(), rfFind.getStatus().getCode());
        assertEquals(1, rfCreate.getAccountID(), rfFind.getAccountID());
        assertEquals(userID, rfCreate.getUserID(), rfFind.getUserID());
        assertEquals("name", rfCreate.getName(), rfFind.getName());
        assertEquals("description", rfCreate.getDescription(), rfFind.getDescription());
        assertEquals("description", rfCreate.getDescription(), rfFind.getDescription());
        assertEquals(2, rfCreate.getHardAcceleration(), rfFind.getHardAcceleration());
        assertEquals(2, rfCreate.getHardBrake(), rfFind.getHardBrake());
        assertEquals(4, rfCreate.getHardTurn(), rfFind.getHardTurn());
        assertEquals(3, rfCreate.getHardVertical(), rfFind.getHardVertical());
        assertEquals(1, rfCreate.getSeverityLevel().getCode(), rfFind.getSeverityLevel().getCode());
        assertEquals(31211, rfCreate.getZoneID(), rfFind.getZoneID());

        //now make update
        RedFlagAlert rftoUpdate= new RedFlagAlert();

//        rftoUpdate.setTypes();
        rftoUpdate.setStatus(Status.INACTIVE);
        rftoUpdate.setAccountID(2);
        rftoUpdate.setUserID(3980);
        rftoUpdate.setName("noNameUpdate");
        rftoUpdate.setDescription("newDescUpdate");
        rftoUpdate.setHardAcceleration(4);
        rftoUpdate.setHardBrake(4);
        rftoUpdate.setHardTurn(2);
        rftoUpdate.setHardVertical(1);
        rftoUpdate.setSeverityLevel(RedFlagLevel.NONE);
        rftoUpdate.setNotifyManagers(true);
        rftoUpdate.setZoneID(312199);

        rftoUpdate.setAlertID(alertID);

        //update now
        redFlagAlertJDBCDAO.update(rftoUpdate);

        //find by ID after update
        RedFlagAlert rfFindAfterUpdate = redFlagAlertJDBCDAO.findByID(alertID);

        assertEquals(2, rftoUpdate.getStatus().getCode(), rfFindAfterUpdate.getStatus().getCode());
        assertEquals(2, rftoUpdate.getAccountID(), rfFindAfterUpdate.getAccountID());
        assertEquals(3980, rftoUpdate.getUserID(), rfFindAfterUpdate.getUserID());
        assertEquals("noNameUpdate", rftoUpdate.getName(), rfFindAfterUpdate.getName());
        assertEquals("newDescUpdate", rftoUpdate.getDescription(), rfFindAfterUpdate.getDescription());
        assertEquals(4, rftoUpdate.getHardAcceleration(), rfFindAfterUpdate.getHardAcceleration());
        assertEquals(4, rftoUpdate.getHardBrake(), rfFindAfterUpdate.getHardBrake());
        assertEquals(2, rftoUpdate.getHardTurn(), rfFindAfterUpdate.getHardTurn());
        assertEquals(1, rftoUpdate.getHardVertical(), rfFindAfterUpdate.getHardVertical());
        assertEquals(0, rftoUpdate.getSeverityLevel().getCode(), rfFindAfterUpdate.getSeverityLevel().getCode());
        assertEquals(31211, rftoUpdate.getZoneID(), rfFindAfterUpdate.getZoneID());

        //now delete
        redFlagAlertJDBCDAO.deleteByID(alertID);
        
        RedFlagAlert rfFindAfterDelete = redFlagAlertJDBCDAO.findByID(alertID);
        RedFlagAlert rfFindAfterDeleteHessian = redFlagAlertHessianDAO.findByID(alertID);
        assertEquals(Status.DELETED, rfFindAfterDelete.getStatus());        
        compare(rfFindAfterDelete, rfFindAfterDeleteHessian);
        
        alertID = redFlagAlertJDBCDAO.create(rfCreate.getAccountID(), rfCreate);
        redFlagAlertHessianDAO.deleteByID(alertID);
        
        rfFindAfterDelete = redFlagAlertJDBCDAO.findByID(alertID);
        rfFindAfterDeleteHessian = redFlagAlertHessianDAO.findByID(alertID);
        assertEquals(Status.DELETED, rfFindAfterDelete.getStatus());        
        compare(rfFindAfterDelete, rfFindAfterDeleteHessian);
    }

        @Test
        public void getRedFlagAlertGroupsByAlertIDTest() throws Exception {
            RedFlagAlertJDBCDAO redFlagAlertJDBCDAO = new RedFlagAlertJDBCDAO();
            DataSource dataSource = new ITDataSource().getRealDataSource();
            redFlagAlertJDBCDAO.setDataSource(dataSource);

            RedFlagAlert team = itData.redFlagAlertList.get(1);
            Integer alertID = team.getAlertID();

            List<RedFlagAlertAssignItem> redFlagAlertListGroupList = redFlagAlertJDBCDAO.getRedFlagAlertGroupsByAlertID(alertID);

            assertTrue(redFlagAlertListGroupList.size() > 0);

        }


        @Test
        public void createGroupTest() throws Exception {
            RedFlagAlertJDBCDAO redFlagAlertJDBCDAO = new RedFlagAlertJDBCDAO();
            DataSource dataSource = new ITDataSource().getRealDataSource();
            redFlagAlertJDBCDAO.setDataSource(dataSource);
            boolean returnalertGrId = false;

            RedFlagAlertAssignItem rfcreateSimple = new RedFlagAlertAssignItem();

            rfcreateSimple.setRedFlagId(1111);
            rfcreateSimple.setItemId(22222);

            Integer grID = redFlagAlertJDBCDAO.createRedFlagAlertGroup(rfcreateSimple.getItemId(), rfcreateSimple);
            returnalertGrId = (grID != null);
            assertTrue(returnalertGrId);

            //delete from group
            redFlagAlertJDBCDAO.deleteRedFlagAlertGroupById(grID);


        }
        
        private void compare(RedFlagAlert jdbc, RedFlagAlert hessian) {
            assertEquals(hessian.getAlertID(), jdbc.getAlertID());
            assertEquals(hessian.getAccountID(), jdbc.getAccountID());
            assertEquals(hessian.getUserID(), jdbc.getUserID());
            assertEquals(hessian.getName(), jdbc.getName());
            assertEquals(hessian.getDescription(), jdbc.getDescription());
            assertEquals(hessian.getStartTOD(), jdbc.getStartTOD());
            assertEquals(hessian.getStopTOD(), jdbc.getStopTOD());
            assertEquals(hessian.getDayOfWeek(), jdbc.getDayOfWeek());
            assertEquals(hessian.getGroupIDs(), jdbc.getGroupIDs());
            assertEquals(hessian.getDriverIDs(), jdbc.getDriverIDs());
            assertEquals(hessian.getVehicleIDs(), jdbc.getVehicleIDs());
            assertEquals(hessian.getVehicleTypes(), jdbc.getVehicleTypes());
            assertEquals(hessian.getNotifyPersonIDs(), jdbc.getNotifyPersonIDs());
            assertEquals(hessian.getNotifyManagers(), jdbc.getNotifyManagers());
            assertEquals(hessian.getStatus(), jdbc.getStatus());
            assertEquals(hessian.getEscalationList().size(), jdbc.getEscalationList().size());
            assertEquals(hessian.getMaxEscalationTries(), jdbc.getMaxEscalationTries());
            assertEquals(hessian.getMaxEscalationTryTime(), jdbc.getMaxEscalationTryTime());
            assertEquals(hessian.getEscalationTimeBetweenRetries(), jdbc.getEscalationTimeBetweenRetries());
            assertEquals(hessian.getSeverityLevel(), jdbc.getSeverityLevel());
            assertEquals(hessian.getTypes(), jdbc.getTypes());
            assertTrue(Arrays.equals(hessian.getSpeedSettings(), jdbc.getSpeedSettings()));
            assertEquals(hessian.getHardAcceleration(), jdbc.getHardAcceleration());
            assertEquals(hessian.getHardBrake(), jdbc.getHardBrake());
            assertEquals(hessian.getHardTurn(), jdbc.getHardTurn());
            assertEquals(hessian.getHardVertical(), jdbc.getHardVertical());
            assertEquals(hessian.getSatellite(), jdbc.getSatellite());
            assertEquals(hessian.getZoneID(), jdbc.getZoneID());
            assertEquals(hessian.getIdlingThreshold(), jdbc.getIdlingThreshold());
            assertEquals(hessian.getUseMaxSpeed(), jdbc.getUseMaxSpeed());
            assertEquals(hessian.getMaxSpeed(), jdbc.getMaxSpeed());            
        }
        
        private void compare(List<RedFlagAlert> redFlagAlertList, List<RedFlagAlert> redFlagAlertListHessian) {
            Collections.sort(redFlagAlertList);
            Collections.sort(redFlagAlertListHessian);
            
            for (int i = 0; i < redFlagAlertList.size(); i++) {
                RedFlagAlert jdbc = redFlagAlertList.get(i);
                RedFlagAlert hessian = redFlagAlertListHessian.get(i);
                compare(jdbc, hessian);
            }
        }
        
        private void checkNotDeleted(RedFlagAlert redFlagAlert) {
            assertTrue(!redFlagAlert.getStatus().equals(Status.DELETED));
        }
        
        private void checkNotDeleted(List<RedFlagAlert> redFlagAlertList) {            
            for (RedFlagAlert redFlagAlert : redFlagAlertList) {
                checkNotDeleted(redFlagAlert);
            }            
        }


}
