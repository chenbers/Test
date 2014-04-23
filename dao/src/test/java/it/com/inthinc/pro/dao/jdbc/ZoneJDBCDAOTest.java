package it.com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.AddressJDBCDAO;
import com.inthinc.pro.dao.jdbc.ZoneJDBCDAO;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Zone;
import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import javax.sql.DataSource;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class ZoneJDBCDAOTest extends SimpleJdbcDaoSupport {

    //Zone JDBC test

    private static SiloService siloService;
    private static final String XML_DATA_FILE = "ReportTest.xml";
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

        if (!itData.parseTestData(stream, siloService, false, false)) {
            throw new Exception("Error parsing Test data xml file");
        }


    }



    @Test
    public void findByIDTest() {
        ZoneJDBCDAO zoneDAO = new ZoneJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        zoneDAO.setDataSource(dataSource);
        Integer zoneI= 4;
        //findById method
        Zone zone = zoneDAO.findByID(zoneI);

        assertNotNull(zone);
    }

    //@Test
    @Ignore
    public void createUpdateDeleteTest() {
        ZoneJDBCDAO zoneJDBCDAO = new ZoneJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        zoneJDBCDAO.setDataSource(dataSource);
        boolean returZoneID=false ;

        GroupData team = itData.teamGroupData.get(0);
        Integer acctID = team.group.getAccountID();
        Integer groupID = team.group.getGroupID();

        Zone zoneToInsert = new Zone();
        zoneToInsert.setAccountID(acctID);
        zoneToInsert.setGroupID(groupID);
        zoneToInsert.setStatus(Status.ACTIVE);
        zoneToInsert.setName("cezarica fara frica");
        zoneToInsert.setAddress("bucuresti sector 6");

        // create method
        Integer zoneID = zoneJDBCDAO.create(zoneToInsert.getAccountID(), zoneToInsert);
        returZoneID = (zoneID != null);
        assertTrue(returZoneID);

        //find by id test method
        Zone createdZone = zoneJDBCDAO.findByID(zoneID);
        assertEquals("cezarica fara frica", zoneToInsert.getName(), createdZone.getName());
        assertEquals("bucuresti sector 6", zoneToInsert.getAddress(), createdZone.getAddress());


        //now delete  using method deleteByID
        zoneJDBCDAO.deleteByID(zoneID);
    }









    private static void initApp() throws Exception {
    }
}
