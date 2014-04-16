package it.com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.UserJDBCDAO;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.security.AccessPoint;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by infrasoft05 on 3/26/14.
 */
public class UserJDBCDAOTest extends SimpleJdbcDaoSupport {

    //AddressJDBCDAOTest

    private static SiloService siloService;
    private static final String XML_DATA_FILE = "ReportTest.xml";
    private static ITData itData = new ITData();
    private String userName;
    private Integer groupId;
    private Integer personId;
    private Integer userId;
    private List<Integer> roleId;
    private List<AccessPoint> accessPoints;
    private List<Integer> mapLayers;



    @Before
    public void setUpBeforeTest() throws Exception {
        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

        siloService = new SiloServiceCreator(host, port).getService();

        initApp();
        itData = new ITData();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(XML_DATA_FILE);

        if (!itData.parseTestData(stream, siloService, false, false)) {
            throw new Exception("Error parsing Test data xml file");
        }

        userName = itData.fleetUser.getUsername();
        groupId = itData.fleetGroup.getGroupID();
        personId = itData.fleetUser.getPersonID();
        userId = itData.fleetUser.getUserID();
        roleId = itData.fleetUser.getRoles();
        accessPoints = itData.fleetUser.getAccessPoints();
        mapLayers = itData.fleetUser.getSelectedMapLayerIDs();



    }
    private static void initApp() throws Exception {
    }

    @Test
    public void findByNameTest() {
        UserJDBCDAO userDAO = new UserJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        userDAO.setDataSource(dataSource);

        //findByName method
        User user = userDAO.findByUserName(userName);
        assertNotNull(user);
    }

    @Test
    public void getUsersInGroupHierarchyTest() {
        UserJDBCDAO userDAO = new UserJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        userDAO.setDataSource(dataSource);

        //getUsersInGroupHierarchy method
        List<User> user = userDAO.getUsersInGroupHierarchy(groupId);
        assertNotNull(user);
    }

    @Test
    public void getUserByPersonIDTest() {
        UserJDBCDAO userDAO = new UserJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        userDAO.setDataSource(dataSource);

        //getUserByPersonID method
        User user = userDAO.getUserByPersonID(personId);
        assertNotNull(user);
    }

    @Test
    public void findByIDTest() {
        UserJDBCDAO userDAO = new UserJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        userDAO.setDataSource(dataSource);

        //findById method
        User user = userDAO.findByID(userId);
        assertNotNull(user);
    }

    @Test
    public void createUpdateDeleteTest(){

        //create  method  test
        boolean returnsUserID = false;
        UserJDBCDAO userDAO = new UserJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        userDAO.setDataSource(dataSource);

        User user = new User();
        user.setGroupID(6509);
        user.setUsername("TestCreate");
        user.setPassword("create");
        user.setStatus(Status.INACTIVE);


        user.setRoles(roleId);
        user.setAccessPoints(accessPoints);
        user.setSelectedMapLayerIDs(mapLayers);


        user.setPersonID(personId);
        Integer userID = userDAO.create(user.getPersonID(), user);
        returnsUserID = (userID != null);
        assertTrue(returnsUserID);

        //find by id test
        User createdUser = userDAO.findByID(userID);

        assertEquals("TestCreate", user.getUsername(), createdUser.getUsername());
        assertEquals(6509, user.getGroupID(), createdUser.getGroupID());
        assertEquals("create", user.getPassword(), createdUser.getPassword());

        //update  method  test
        User userUpdate = new User();
        userUpdate.setUserID(userID);
        userUpdate.setGroupID(6509);
        userUpdate.setUsername("TestUpdate");
        userUpdate.setPassword("update");
        userUpdate.setStatus(Status.ACTIVE);

        userUpdate.setPersonID(personId);
        userDAO.update(userUpdate);

        //find vehicle by ID  after update
        User updatedUser = userDAO.findByID(userID);

        assertEquals("TestUpdate", userUpdate.getUsername(), updatedUser.getUsername());
        assertEquals(6509, user.getGroupID(), createdUser.getGroupID());
        assertEquals("update", user.getPassword(), createdUser.getPassword());

        //delete vehicle when finish
        userDAO.deleteByID(userID);
    }

}
