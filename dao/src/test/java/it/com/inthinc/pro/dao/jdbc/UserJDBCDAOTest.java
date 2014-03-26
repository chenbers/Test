package it.com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.UserJDBCDAO;
import com.inthinc.pro.model.User;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertNotNull;

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
//        accountId= itData.fleetUser.getPersonID()
        personId = itData.fleetUser.getPersonID();
        userId = itData.fleetUser.getUserID();



    }
    private static void initApp() throws Exception {
    }

//    @Test
//    public void findByNameTest() {
//        UserJDBCDAO userDAO = new UserJDBCDAO();
//        DataSource dataSource = new ITDataSource().getRealDataSource();
//        userDAO.setDataSource(dataSource);
//
//        //findByName method
//        User user = userDAO.findByUserName(userName);
//        assertNotNull(user);
//    }

//    @Test
//    public void getUsersInGroupHierarchyTest() {
//        UserJDBCDAO userDAO = new UserJDBCDAO();
//        DataSource dataSource = new ITDataSource().getRealDataSource();
//        userDAO.setDataSource(dataSource);
//
//        //getUsersInGroupHierarchy method
//        List<User> user = userDAO.getUsersInGroupHierarchy(groupId);
//        assertNotNull(user);
//    }

//    @Test
//    public void getUserByPersonIDTest() {
//        UserJDBCDAO userDAO = new UserJDBCDAO();
//        DataSource dataSource = new ITDataSource().getRealDataSource();
//        userDAO.setDataSource(dataSource);
//
//        //getUserByPersonID method
//        User user = userDAO.getUserByPersonID(personId);
//        assertNotNull(user);
//    }

    @Test
    public void findByIDTest() {
        UserJDBCDAO userDAO = new UserJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        userDAO.setDataSource(dataSource);

        //findById method
        User user = userDAO.findByID(userId);
        assertNotNull(user);
    }
}
