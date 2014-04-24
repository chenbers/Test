package it.com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.AddressJDBCDAO;
import com.inthinc.pro.dao.jdbc.ZoneJDBCDAO;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.LatLng;
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
import java.util.ArrayList;
import java.util.List;

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


    @Ignore
    public void findByIDTest() {
        ZoneJDBCDAO zoneDAO = new ZoneJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        zoneDAO.setDataSource(dataSource);
        Integer zoneI= 4;
        //findById method
        Zone zone = zoneDAO.findByID(zoneI);

        assertNotNull(zone);
    }

    @Test
    public void latLngToHex(){
        ZoneJDBCDAO zoneJDBCDAO = new ZoneJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        zoneJDBCDAO.setDataSource(dataSource);
        boolean returnZoneID=false ;

        Integer zoneI= 4;
        //findById method
        Zone zone = zoneJDBCDAO.findByID(zoneI);


        GroupData team = itData.teamGroupData.get(0);
        Integer acctID = team.group.getAccountID();
        Integer groupID = team.group.getGroupID();

        List<LatLng> latLngList = new ArrayList<LatLng>();

        latLngList.add(0,new LatLng(8.345,9.3562));
        latLngList.add(1,new LatLng(6.2345,2.3435));


        Zone zoneToInsert = new Zone();
        zoneToInsert.setAccountID(acctID);
        zoneToInsert.setGroupID(groupID);
        zoneToInsert.setStatus(Status.ACTIVE);
        zoneToInsert.setName("cezarica fara frica");
        zoneToInsert.setAddress("bucuresti sector 6");

        zoneToInsert.setPoints(zone.getPoints());

        // create method
        Integer zoneID = zoneJDBCDAO.create(zoneToInsert.getAccountID(), zoneToInsert);
        returnZoneID = (zoneID != null);
        assertTrue(returnZoneID);

        Zone zone1 = zoneJDBCDAO.findByID(zoneID);

        zone1.getName();
    }

    @Ignore
    public void createUpdateDeleteTest() {
        ZoneJDBCDAO zoneJDBCDAO = new ZoneJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        zoneJDBCDAO.setDataSource(dataSource);
        boolean returnZoneID=false ;
        boolean returnUpdatedZoneID=false ;

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
        returnZoneID = (zoneID != null);
        assertTrue(returnZoneID);

        //find by id test method
        Zone createdZone = zoneJDBCDAO.findByID(zoneID);
        assertEquals("cezarica fara frica", zoneToInsert.getName(), createdZone.getName());
        assertEquals("bucuresti sector 6", zoneToInsert.getAddress(), createdZone.getAddress());

        //update test method
        Zone updateZone = zoneJDBCDAO.findByID(zoneID);

        updateZone.setName("00101012");

        Integer returnUpdateZoneID  =  zoneJDBCDAO.update(updateZone);
        returnUpdatedZoneID = (returnUpdateZoneID != null);
        assertTrue(returnUpdatedZoneID);



        //now delete  using method deleteByID
        zoneJDBCDAO.deleteByID(zoneID);
    }









    private static void initApp() throws Exception {
    }
}
