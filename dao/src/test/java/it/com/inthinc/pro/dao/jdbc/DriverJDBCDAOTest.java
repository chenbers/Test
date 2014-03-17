package it.com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.RoleHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.TimeZoneHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.DriverJDBCDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverName;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.app.SupportedTimeZones;
import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DriverJDBCDAOTest extends BaseJDBCTest {
    private static SiloService siloService;
    private static final String XML_DATA_FILE = "ReportTest.xml";
    private static ITData itData;
    DriverJDBCDAO driverJDBCDAO= new DriverJDBCDAO();


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
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

    private static void initApp() throws Exception {
        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(siloService);
        States states = new States();
        states.setStateDAO(stateDAO);
        states.init();
        RoleHessianDAO roleDAO = new RoleHessianDAO();
        roleDAO.setSiloService(siloService);
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        TimeZoneHessianDAO timeZoneDAO = new TimeZoneHessianDAO();
        timeZoneDAO.setSiloService(siloService);
        SupportedTimeZones supportedTimeZones = new SupportedTimeZones();
        supportedTimeZones.setTimeZoneDAO(timeZoneDAO);
        supportedTimeZones.init();
    }

    @Test
     public void testGetDrivers(){
        DriverJDBCDAO driverJDBCDAO= new DriverJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        driverJDBCDAO.setDataSource(dataSource);

        for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
            GroupData team = itData.teamGroupData.get(teamIdx);
            List<Driver> driverList = driverJDBCDAO.getDrivers(team.group.getGroupID());
            assertEquals("Unexpected driver count for team " + teamIdx, 1, driverList.size());

        }

    }

    @Test
    public void testfindByPersonID(){
        DataSource dataSource = new ITDataSource().getRealDataSource();
        driverJDBCDAO.setDataSource(dataSource);

        for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
            GroupData team = itData.teamGroupData.get(teamIdx);
            Person expectedPerson= team.driver.getPerson() ;
            Driver driver = driverJDBCDAO.findByID(team.driver.getDriverID());
            assertEquals("Driver Id the same :  ",expectedPerson.getDriverID(), driver.getPerson().getDriverID());
            assertEquals("Account Id the same :  ",expectedPerson.getAccountID(), driver.getPerson().getAccountID());
            assertEquals("Address Id the same :  ",expectedPerson.getAddressID(), driver.getPerson().getAddressID());
            assertEquals("Crit  the same :  ",expectedPerson.getCrit(), driver.getPerson().getCrit());

        }

    }
    @Test
    @Ignore //TODO after insert barcode in xml
    public void testGetRfidsByBarcode (){
        DataSource dataSource = new ITDataSource().getRealDataSource();
        driverJDBCDAO.setDataSource(dataSource);
        for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
            //I don't find barcode in xml
            List<Long> getRifds = driverJDBCDAO.getRfidsByBarcode("xxx");


        }
    }

    @Test
    @Ignore //TODO after insert barcode in xml
    public void testGetDriverIDByBarcode (){
    }

        @Test
    public void testGetDriversName (){
        DataSource dataSource = new ITDataSource().getRealDataSource();
        driverJDBCDAO.setDataSource(dataSource);
        for (int teamIdx = ITData.GOOD; teamIdx <= ITData.BAD; teamIdx++) {
            GroupData team = itData.teamGroupData.get(teamIdx);
            List<DriverName> driverList = driverJDBCDAO.getDriverNames(team.group.getGroupID());
            assertEquals("Unexpected driver count for team " + teamIdx, 1, driverList.size());

        }
    }


}
