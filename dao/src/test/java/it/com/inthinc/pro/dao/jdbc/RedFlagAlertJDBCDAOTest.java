package it.com.inthinc.pro.dao.jdbc;


import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.RedFlagAlertJDBCDAO;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.Status;
import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RedFlagAlertJDBCDAOTest extends SimpleJdbcDaoSupport {

    private static SiloService siloService;
    private static final String XML_DATA_FILE = "AlertTest.xml";
    private static ITData itData = new ITData();


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

        assertTrue(redFlagAlertList.size() > 0);

    }

    @Test
    public void getRedFlagAlertsByUserIDTest() throws Exception {
        RedFlagAlertJDBCDAO redFlagAlertJDBCDAO = new RedFlagAlertJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        redFlagAlertJDBCDAO.setDataSource(dataSource);

        RedFlagAlert team = itData.redFlagAlertList.get(0);
        Integer userID = team.getUserID();

        List<RedFlagAlert> redFlagAlertListUser = redFlagAlertJDBCDAO.getRedFlagAlertsByUserID(userID);

        assertTrue(redFlagAlertListUser.size() > 0);

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

    }

    @Test
    public void getAlertsByTeamGroupIDTest() throws Exception {
        RedFlagAlertJDBCDAO redFlagAlertJDBCDAO = new RedFlagAlertJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        redFlagAlertJDBCDAO.setDataSource(dataSource);

        GroupData team = itData.teamGroupData.get(0);
        Integer groupID = team.group.getGroupID();

        List<RedFlagAlert> redflagByGroup = redFlagAlertJDBCDAO.getAlertsByTeamGroupID(groupID);

        assertNotNull(redflagByGroup);

    }

    @Test
    public void getRedFlagAlertsByUserIDdeepTest() throws Exception {
        RedFlagAlertJDBCDAO redFlagAlertJDBCDAO = new RedFlagAlertJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        redFlagAlertJDBCDAO.setDataSource(dataSource);

        RedFlagAlert team = itData.redFlagAlertList.get(0);
        Integer userID = team.getUserID();

        List<RedFlagAlert> redFlagAlertListUserD = redFlagAlertJDBCDAO.getRedFlagAlertsByUserIDDeep(userID);

        assertTrue(redFlagAlertListUserD.size() > 0);

    }

    @Test
    public void createTest() throws Exception {
        RedFlagAlertJDBCDAO redFlagAlertJDBCDAO = new RedFlagAlertJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        redFlagAlertJDBCDAO.setDataSource(dataSource);
        boolean returnRedFlagID = false;

        RedFlagAlert team = itData.redFlagAlertList.get(0);
        Integer userID = team.getUserID();

        RedFlagAlert rfCreate= new RedFlagAlert();


//        rfCreate.setTypes(AlertMessageType.ALERT_TYPE_CRASH.getCode());
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


        Integer alertID = redFlagAlertJDBCDAO.create(rfCreate.getAccountID(), rfCreate);
        returnRedFlagID = (alertID != null);
        assertTrue(returnRedFlagID);





    }




}
