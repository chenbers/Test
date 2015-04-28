package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.hessian.AccountHessianDAO;
import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.configurator.ProductType;
import it.com.inthinc.pro.dao.jdbc.BaseJDBCTest;
import it.com.inthinc.pro.dao.jdbc.ReportPaginationJDBCTest;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DeviceJDBCDAOTest extends BaseJDBCTest {

    private static final Logger logger = Logger.getLogger(ReportPaginationJDBCTest.class);
    private static SiloService siloService;
    private static ITData itData;
    private static final String INTEGRATION_TEST_XML = "IntegrationTest.xml";
    private static Integer goodGroupID;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        IntegrationConfig config = new IntegrationConfig();
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

        siloService = new SiloServiceCreator(host, port).getService();
        itData = new ITData();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(INTEGRATION_TEST_XML);
        if (!itData.parseTestData(stream, siloService, true, true, true)) {
            throw new Exception("Error parsing Test data xml file");
        }

        AccountHessianDAO accountDAO = new AccountHessianDAO();
        accountDAO.setSiloService(siloService);
        Account account = accountDAO.findByID(itData.account.getAccountID());
        itData.account.setUnkDriverID(account.getUnkDriverID());
        goodGroupID = itData.fleetGroup.getGroupID();

        initApp();
    }

    private static void initApp() throws Exception {
        //StateJDBCDAO is not implemented
        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(siloService);

        States states = new States();
        states.setStateDAO(stateDAO);
        states.init();
    }

    @Test
    public void compareWithHessianTest() {
        DeviceJDBCDAO deviceJDBCDAO = new DeviceJDBCDAO();
        DataSource dataSource = new ITDataSource().getRealDataSource();
        deviceJDBCDAO.setDataSource(dataSource);

        DeviceHessianDAO deviceHessianDAO = new DeviceHessianDAO();
        deviceHessianDAO.setSiloService(new SiloServiceCreator("dev-web.tiwii.com", 8099).getService());

        VehicleHessianDAO vehicleHessianDAO = new VehicleHessianDAO();
        vehicleHessianDAO.setSiloService(new SiloServiceCreator("dev-web.tiwii.com", 8099).getService());

        deviceHessianDAO.setVehicleDAO(vehicleHessianDAO);
        deviceJDBCDAO.setVehicleDAO(vehicleHessianDAO);


        List<Device> hessianDevices = deviceHessianDAO.getDevicesByAcctID(itData.account.getAccountID());
        List<Device> jdbcDevices = deviceJDBCDAO.getDevicesByAcctID(itData.account.getAccountID());

        assertNotNull(hessianDevices);
        assertNotNull(jdbcDevices);

        Map<Integer, DeviceView> hessianViewMap = new HashMap<Integer, DeviceView>();
        Map<Integer, DeviceView> jdbcViewMap = new HashMap<Integer, DeviceView>();

        for (Device device : hessianDevices) {
            hessianViewMap.put(device.getDeviceID(), new DeviceView(device));
        }

        for (Device device : jdbcDevices) {
            jdbcViewMap.put(device.getDeviceID(), new DeviceView(device));
        }

        for (Map.Entry<Integer, DeviceView> entry: jdbcViewMap.entrySet()){
            Integer deviceID = entry.getKey();
            DeviceView jdbcDevice = entry.getValue();
            DeviceView hessianDevice = hessianViewMap.get(deviceID);

            Assert.assertEquals("Different device for deviceID: " + deviceID, hessianDevice, jdbcDevice);
        }
    }
}

class DeviceView implements Comparable<DeviceView> {
    private Integer accountID;
    private Date activated;
    private String altIMEI;
    private Integer baseID;
    private Integer deviceID;
    private String emuMD5;
    private Integer firmwareVersion;
    private String glCode;
    private String imei;
    private String mcmID;
    private String name;
    private String phone;
    private Integer productVer;
    private String serialNum;
    private String sim;
    private Integer vehicleID;
    private String vehicleName;
    private Integer witnessVersion;
    private Date creatd;
    private Date modified;

    DeviceView(Device device) {
        accountID = device.getAccountID();
        activated = device.getActivated();
        altIMEI = device.getAltimei();
        baseID = device.getBaseID();
        deviceID = device.getDeviceID();
        emuMD5 = device.getEmuMd5();
        firmwareVersion = device.getFirmwareVersion();
        glCode = device.getGlcode();
        imei = device.getImei();
        mcmID = device.getMcmid();
        name = device.getName();
        phone = device.getPhone();
        productVer = device.getProductVer();
        ProductType productVersion = device.getProductVersion();
        serialNum = device.getSerialNum();
        sim = device.getSim();
        DeviceStatus status = device.getStatus();
        vehicleID = device.getVehicleID();
        vehicleName = device.getVehicleName();
        witnessVersion = device.getWitnessVersion();
        creatd = device.getCreated();
        modified = device.getModified();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeviceView)) return false;

        DeviceView that = (DeviceView) o;

        if (accountID != null ? !accountID.equals(that.accountID) : that.accountID != null) return false;
        if (activated != null ? !activated.equals(that.activated) : that.activated != null) return false;
        if (altIMEI != null ? !altIMEI.equals(that.altIMEI) : that.altIMEI != null) return false;
        if (baseID != null ? !baseID.equals(that.baseID) : that.baseID != null) return false;
        if (creatd != null ? !creatd.equals(that.creatd) : that.creatd != null) return false;
        if (deviceID != null ? !deviceID.equals(that.deviceID) : that.deviceID != null) return false;
        if (emuMD5 != null ? !emuMD5.equals(that.emuMD5) : that.emuMD5 != null) return false;
        if (firmwareVersion != null ? !firmwareVersion.equals(that.firmwareVersion) : that.firmwareVersion != null)
            return false;
        if (glCode != null ? !glCode.equals(that.glCode) : that.glCode != null) return false;
        if (imei != null ? !imei.equals(that.imei) : that.imei != null) return false;
        if (mcmID != null ? !mcmID.equals(that.mcmID) : that.mcmID != null) return false;
        if (modified != null ? !modified.equals(that.modified) : that.modified != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (productVer != null ? !productVer.equals(that.productVer) : that.productVer != null) return false;
        if (serialNum != null ? !serialNum.equals(that.serialNum) : that.serialNum != null) return false;
        if (sim != null ? !sim.equals(that.sim) : that.sim != null) return false;
        if (vehicleID != null ? !vehicleID.equals(that.vehicleID) : that.vehicleID != null) return false;
        if (vehicleName != null ? !vehicleName.equals(that.vehicleName) : that.vehicleName != null) return false;
        if (witnessVersion != null ? !witnessVersion.equals(that.witnessVersion) : that.witnessVersion != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accountID != null ? accountID.hashCode() : 0;
        result = 31 * result + (activated != null ? activated.hashCode() : 0);
        result = 31 * result + (altIMEI != null ? altIMEI.hashCode() : 0);
        result = 31 * result + (baseID != null ? baseID.hashCode() : 0);
        result = 31 * result + (deviceID != null ? deviceID.hashCode() : 0);
        result = 31 * result + (emuMD5 != null ? emuMD5.hashCode() : 0);
        result = 31 * result + (firmwareVersion != null ? firmwareVersion.hashCode() : 0);
        result = 31 * result + (glCode != null ? glCode.hashCode() : 0);
        result = 31 * result + (imei != null ? imei.hashCode() : 0);
        result = 31 * result + (mcmID != null ? mcmID.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (productVer != null ? productVer.hashCode() : 0);
        result = 31 * result + (serialNum != null ? serialNum.hashCode() : 0);
        result = 31 * result + (sim != null ? sim.hashCode() : 0);
        result = 31 * result + (vehicleID != null ? vehicleID.hashCode() : 0);
        result = 31 * result + (vehicleName != null ? vehicleName.hashCode() : 0);
        result = 31 * result + (witnessVersion != null ? witnessVersion.hashCode() : 0);
        result = 31 * result + (creatd != null ? creatd.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(DeviceView o) {
        if (o == null)
            return 1;
        else if (o == null)
            return 1;
        else if (this == null)
            return -1;

        if (this.getDeviceID() > o.getDeviceID())
            return 1;
        else if (this.getDeviceID() == o.getDeviceID())
            return 0;
        else return -1;
    }

    @Override
    public String toString() {
        return "DeviceView {" +
                "\n accountID=" + accountID +
                "\n, activated=" + activated +
                "\n, altIMEI='" + altIMEI + '\'' +
                "\n, baseID=" + baseID +
                "\n, deviceID=" + deviceID +
                "\n, emuMD5='" + emuMD5 + '\'' +
                "\n, firmwareVersion=" + firmwareVersion +
                "\n, glCode='" + glCode + '\'' +
                "\n, imei='" + imei + '\'' +
                "\n, mcmID='" + mcmID + '\'' +
                "\n, name='" + name + '\'' +
                "\n, phone='" + phone + '\'' +
                "\n, productVer=" + productVer +
                "\n, serialNum='" + serialNum + '\'' +
                "\n, sim='" + sim + '\'' +
                "\n, vehicleID=" + vehicleID +
                "\n, vehicleName='" + vehicleName + '\'' +
                "\n, witnessVersion=" + witnessVersion +
                "\n, creatd=" + creatd +
                "\n, modified=" + modified +
                "\n }";
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public Date getActivated() {
        return activated;
    }

    public void setActivated(Date activated) {
        this.activated = activated;
    }

    public String getAltIMEI() {
        return altIMEI;
    }

    public void setAltIMEI(String altIMEI) {
        this.altIMEI = altIMEI;
    }

    public Integer getBaseID() {
        return baseID;
    }

    public void setBaseID(Integer baseID) {
        this.baseID = baseID;
    }

    public Integer getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Integer deviceID) {
        this.deviceID = deviceID;
    }

    public String getEmuMD5() {
        return emuMD5;
    }

    public void setEmuMD5(String emuMD5) {
        this.emuMD5 = emuMD5;
    }

    public Integer getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(Integer firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getGlCode() {
        return glCode;
    }

    public void setGlCode(String glCode) {
        this.glCode = glCode;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getMcmID() {
        return mcmID;
    }

    public void setMcmID(String mcmID) {
        this.mcmID = mcmID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getProductVer() {
        return productVer;
    }

    public void setProductVer(Integer productVer) {
        this.productVer = productVer;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public Integer getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public Integer getWitnessVersion() {
        return witnessVersion;
    }

    public void setWitnessVersion(Integer witnessVersion) {
        this.witnessVersion = witnessVersion;
    }

    public Date getCreatd() {
        return creatd;
    }

    public void setCreatd(Date creatd) {
        this.creatd = creatd;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
}



