package it.com.inthinc.pro.dao.jdbc;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;

import java.io.InputStream;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.DeviceJDBCDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.ForwardCommand;
import com.inthinc.pro.model.ForwardCommandStatus;

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
        ForwardCommandStatus status = ForwardCommandStatus.STATUS_SENT;

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
    }

    @Test
    public void create_updateTest() {
        DeviceJDBCDAO deviceJDBCDAO = new DeviceJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        deviceJDBCDAO.setDataSource(dataSource);

        //  get values from xml
        GroupData team = itData.teamGroupData.get(0);
        Integer acctID = team.device.getDeviceID();
        String sim = team.device.getSim();
        String ph = team.device.getPhone();
        String emuMd = team.device.getEmuMd5();

        boolean returnedDeviceID = false;

        Device createdDevice = new Device();
        createdDevice.setAccountID(acctID);
        createdDevice.setBaseID(0);
        createdDevice.setStatus(DeviceStatus.ACTIVE);
        createdDevice.setProductVer(2);
        createdDevice.setSerialNum("SN19456");
        createdDevice.setName("Device CZ");
        createdDevice.setImei("IMEI_TEST_1");
        createdDevice.setSim(sim);
        createdDevice.setPhone(ph);
        createdDevice.setEmuMd5(emuMd);

        // create method
        Integer deviceID = deviceJDBCDAO.create(createdDevice.getDeviceID(), createdDevice);
        returnedDeviceID = (deviceID != null);
        assertTrue(returnedDeviceID);

        //find by id method
        Device createdDeviceF = deviceJDBCDAO.findByID(deviceID);

        assertEquals(0, createdDeviceF.getBaseID(), createdDevice.getBaseID());
        assertEquals(1, createdDeviceF.getStatus().getCode(), createdDevice.getStatus().getCode());
        assertEquals("Device CZ", createdDeviceF.getName(), createdDevice.getName());
        assertEquals("IMEI_TEST_1", createdDeviceF.getImei(), createdDevice.getImei());
        assertEquals(sim, createdDeviceF.getSim(), createdDevice.getSim());
        assertEquals("SN19456", createdDeviceF.getSerialNum(), createdDevice.getSerialNum());

        //find by imei
        Device findbyIm = deviceJDBCDAO.findByIMEI("IMEI_TEST_1");

        assertEquals(0, findbyIm.getBaseID(), createdDevice.getBaseID());
        assertEquals(1, findbyIm.getStatus().getCode(), createdDevice.getStatus().getCode());
        assertEquals("Device CZ", findbyIm.getName(), createdDevice.getName());
        assertEquals(sim, findbyIm.getSim(), createdDevice.getSim());
        assertEquals(ph, findbyIm.getPhone(), createdDevice.getPhone());
        assertEquals("SN19456", findbyIm.getSerialNum(), createdDevice.getSerialNum());

        //find by serialNum
        Device findBySerialNum= deviceJDBCDAO.findBySerialNum("SN19456");

        assertEquals(0, findBySerialNum.getBaseID(), createdDevice.getBaseID());
        assertEquals(1, findBySerialNum.getStatus().getCode(), createdDevice.getStatus().getCode());
        assertEquals("Device CZ", findBySerialNum.getName(), createdDevice.getName());
        assertEquals(sim, findBySerialNum.getSim(), createdDevice.getSim());
        assertEquals(ph, findBySerialNum.getPhone(), createdDevice.getPhone());
        assertEquals("IMEI_TEST_1", findBySerialNum.getImei(), createdDevice.getImei());

        //info for update
        Device deviceToUpdate = new Device();
        deviceToUpdate.setAccountID(acctID);
        deviceToUpdate.setBaseID(0);
        deviceToUpdate.setStatus(DeviceStatus.INACTIVE);
        deviceToUpdate.setProductVer(1);
        deviceToUpdate.setSerialNum("SN22221");
        deviceToUpdate.setName("DEVICE UP");
        deviceToUpdate.setImei("IMEI_T_2");
        deviceToUpdate.setSim("9202807013601337000");
        deviceToUpdate.setPhone("9602883000");
        deviceToUpdate.setEmuMd5("696d6acbc199d607a5704642c67f4000");
        deviceToUpdate.setDeviceID(deviceID);

        //update method
        deviceJDBCDAO.update(deviceToUpdate);

        //find by id after update
        Device updatedDevice = deviceJDBCDAO.findByID(deviceID);

        assertEquals(0, updatedDevice.getBaseID(), deviceToUpdate.getBaseID());
        assertEquals(2, updatedDevice.getStatus().getCode(), deviceToUpdate.getStatus().getCode());
        assertEquals("DEVICE UP", updatedDevice.getName(), deviceToUpdate.getName());
        assertEquals("IMEI_T_2", updatedDevice.getImei(), deviceToUpdate.getImei());
        assertEquals("9202807013601337000", updatedDevice.getSim(), deviceToUpdate.getSim());
        assertEquals("9602883000", updatedDevice.getPhone(), deviceToUpdate.getPhone());
        assertEquals("696d6acbc199d607a5704642c67f4000", updatedDevice.getEmuMd5(), deviceToUpdate.getEmuMd5());

        //delete when finish
        deviceJDBCDAO.deleteByID(deviceID);

    }

}
