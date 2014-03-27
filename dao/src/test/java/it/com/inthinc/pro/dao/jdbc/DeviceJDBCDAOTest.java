package it.com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.DeviceJDBCDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.ForwardCommand;
import com.inthinc.pro.model.ForwardCommandStatus;
import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

public class DeviceJDBCDAOTest extends SimpleJdbcDaoSupport {

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

    private static void initApp() throws Exception {
    }

    @Test
    public void getDevicesByAcctIDTest() throws Exception {
        DeviceJDBCDAO deviceJDBCDAO = new DeviceJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        deviceJDBCDAO.setDataSource(dataSource);

        GroupData team = itData.teamGroupData.get(0);
        Integer acctID = team.group.getAccountID();
        List<Device> deviceList = deviceJDBCDAO.getDevicesByAcctID(acctID);

        assertTrue(deviceList.size() > 0);

    }

    @Test
    public void findByIMEITest() throws Exception {
        DeviceJDBCDAO deviceJDBCDAO = new DeviceJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        deviceJDBCDAO.setDataSource(dataSource);

        GroupData team = itData.teamGroupData.get(0);
        String imei = team.device.getImei();
        Device deviceFind = deviceJDBCDAO.findByIMEI(imei);

        assertNotNull(deviceFind);

    }

    @Test
    public void findBySerialNumTest() throws Exception {
        DeviceJDBCDAO deviceJDBCDAO = new DeviceJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        deviceJDBCDAO.setDataSource(dataSource);

        GroupData team = itData.teamGroupData.get(0);
        String serialNum = team.device.getSerialNum();
        Device deviceFindBySerial = deviceJDBCDAO.findBySerialNum(serialNum);

        assertNotNull(deviceFindBySerial);

    }

    @Test
    public void getForwardCommandsTest() throws Exception {
        DeviceJDBCDAO deviceJDBCDAO = new DeviceJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        deviceJDBCDAO.setDataSource(dataSource);

        GroupData team = itData.teamGroupData.get(0);
        Integer deviceID = team.device.getDeviceID();
        ForwardCommandStatus status = ForwardCommandStatus.STATUS_QUEUED;

        List<ForwardCommand> fwdList = deviceJDBCDAO.getForwardCommands(deviceID, status);

        assertTrue(fwdList.size() > 0);

    }

    @Test
    public void findbyIDTest() throws Exception {
        DeviceJDBCDAO deviceJDBCDAO = new DeviceJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        deviceJDBCDAO.setDataSource(dataSource);

        GroupData team = itData.teamGroupData.get(0);
        Integer deviceID = team.device.getDeviceID();
        Device findByID = deviceJDBCDAO.findByID(deviceID);

        assertNotNull(findByID);
        assertEquals("960280302116416", findByID.getImei());
        assertEquals("9602807013601337640", findByID.getSim());
        assertEquals("9602832318", findByID.getSerialNum());
        assertEquals("9602883992", findByID.getPhone());
        assertEquals("696d6acbc199d607a5704642c67f4d86", findByID.getEmuMd5());
        assertEquals("GOODDevice", findByID.getName());

    }

}
