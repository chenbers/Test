package it.com.inthinc.pro.backing.importer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.com.inthinc.pro.BaseSpringTest;
import it.config.IntegrationConfig;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.backing.importer.FileChecker;
import com.inthinc.pro.backing.importer.FileImporter;
import com.inthinc.pro.backing.importer.ImportType;
import com.inthinc.pro.backing.importer.ProgressBarBean;
import com.inthinc.pro.dao.hessian.AccountHessianDAO;
import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.GroupHessianDAO;
import com.inthinc.pro.dao.hessian.PersonHessianDAO;
import com.inthinc.pro.dao.hessian.RoleHessianDAO;
import com.inthinc.pro.dao.hessian.UserHessianDAO;
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.exceptions.DuplicateEmailException;
import com.inthinc.pro.dao.hessian.exceptions.DuplicateUsernameException;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.security.Role;


public class FileImporterTest extends BaseSpringTest {
    
    // this must match the account name in importTest/DriverTemplateNoErrors.xls
    private static final String TEST_ACCOUNT_NAME = "BulkImportTest_6";
    private static final String OTHER_TEST_ACCOUNT_NAME = "BulkImportTest6A";
    private static SiloServiceCreator siloServiceCreator;
    private static final String PASSWORD = "nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        siloServiceCreator = new SiloServiceCreator(host, port);
        
        setupAccount(TEST_ACCOUNT_NAME);
        setupAccount(OTHER_TEST_ACCOUNT_NAME);
        
    }
    
    private static void setupAccount(String accountName) {

        
        AccountHessianDAO accountDAO = new AccountHessianDAO();
        accountDAO.setSiloService(siloServiceCreator.getService());
        
        List<Account> accountList = accountDAO.getAllAcctIDs();
        for (Account account : accountList) {
            if (account.getAcctName().equalsIgnoreCase(accountName)) {
                System.out.println("account: " + accountName + " id: " + account.getAccountID());
                return;
            }
        }

        // create the account 
        Account account = new Account(null, accountName, Status.ACTIVE);
        // create an account
        Integer acctID = accountDAO.create(account);
        account.setAccountID(acctID);
        
        GroupHessianDAO groupDAO = new GroupHessianDAO();
        groupDAO.setSiloService(siloServiceCreator.getService());

        // create the account's top level group
        Group topGroup = new Group(0, acctID, "fleet", 0, GroupType.FLEET, null, "Initial top level group", 5, new LatLng(0.0, 0.0));
        Integer groupID = groupDAO.create(acctID, topGroup);
        topGroup.setGroupID(groupID);
        
        Group divGroup = new Group(0, acctID, "division", topGroup.getGroupID(), GroupType.DIVISION, null, "Initial division level group", 5, new LatLng(0.0, 0.0));
        groupID = groupDAO.create(acctID, divGroup);
        divGroup.setGroupID(groupID);

        Group teamGroup = new Group(0, acctID, "vteam", divGroup.getGroupID(), GroupType.TEAM, null, "Initial team level group", 5, new LatLng(0.0, 0.0));
        groupID = groupDAO.create(acctID, teamGroup);
        teamGroup.setGroupID(groupID);
  
        PersonHessianDAO personDAO = new PersonHessianDAO();
        personDAO.setSiloService(siloServiceCreator.getService());
        // create the person record for the main user
        String email = accountName+"@email.com";
        String empID = accountName;
        Person person = new Person(new Integer(0), acctID, TimeZone.getDefault(), null, email, null, "5555555555", "5555555555",
                null, null, null, null, null, empID, null,
                "title", "dept", accountName, "m", accountName, "jr", Gender.FEMALE, 65, 180, new Date(), Status.ACTIVE, 
                MeasurementType.ENGLISH, FuelEfficiencyType.MPG_US, Locale.getDefault());
        person.setAddress(new Address(null, "", null, "", null, "", acctID));
        Integer personID = null;
        try {
            personID = personDAO.create(acctID, person);
            person.setPersonID(personID);
        }
        catch (DuplicateEmailException ex) {
            System.out.println("Duplicate email: [" + email + "]");
            return;
        }
        // create the superuser
        
        RoleHessianDAO roleDAO = new RoleHessianDAO();
        roleDAO.setSiloService(siloServiceCreator.getService());
        List<Role> roles = roleDAO.getRoles(acctID);
        List<Integer> roleIDs = new ArrayList<Integer>();
        for (Role role : roles)
            roleIDs.add(role.getRoleID());

        UserHessianDAO userDAO = new UserHessianDAO();
        userDAO.setSiloService(siloServiceCreator.getService());
        String username = accountName;
        User user = new User(0, person.getPersonID(), roleIDs, Status.ACTIVE, username, PASSWORD, topGroup.getGroupID());
        Integer userID = null;
        try {
            userID = userDAO.create(personID, user);
            user.setUserID(userID);
        }
        catch (DuplicateUsernameException ex) {
            System.out.println("Duplicate username: [" + username + "]");
            return;
        }
        
    
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloServiceCreator.getService());
        String vin = accountName;
        Vehicle vehicle = new Vehicle(0, teamGroup.getGroupID(), Status.ACTIVE, accountName, "MAKE", "MODEL", 2011, "RED", VehicleType.LIGHT, vin, 2000, "ut1111", null);
        vehicleDAO.create(acctID, vehicle);

        
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloServiceCreator.getService());
        Device device = new Device(0, acctID, DeviceStatus.ACTIVE, accountName, 
                accountName.substring(2), accountName+"XX", accountName.substring(7), 
                accountName.substring(7));
        device.setEmuMd5("696d6acbc199d607a5704642c67f4d86");
        device.setProductVersion(ProductType.TIWIPRO);
        Integer deviceID = deviceDAO.create(acctID, device);
        device.setDeviceID(deviceID);

    }

    
    // Note: this test depends on an entry being in the centdb, rfid table on dev server (or where ever tests are hitting)
    // If the db gets wiped out run this to insert the needed entry into the centdb.
    //insert into rfid(rfid, barcode) values (11112222, 'UNITTESTBAR')
    @Test
    public void driversImport()
    {
        
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("importTest/DriverTemplateGood.xls");
        
        ProgressBarBean progressBarBean = new ProgressBarBean();
        progressBarBean.startProcess();
        List<String> msgList = new FileImporter().importFile(ImportType.DRIVERS, stream, progressBarBean);
        assertTrue(msgList == null);
        
        boolean completed = false;
        for (int i = 0; i < 100; i++) {
            if (progressBarBean.getCurrentValue() != null && progressBarBean.getCurrentValue() > 100l) {
                completed = true;
                break;
            }
            
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
                
        }
        
        assertTrue("import process completed", completed);
        
    }

    @Test
    public void driversCheckErrors()
    {
        
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("importTest/DriverTemplateBad.xls");
        List<String> msgList = new FileImporter().importFile(ImportType.DRIVERS, stream);
        
        if (msgList.size() != 34)
            dumpErrors(msgList);
        assertEquals("expected msg List size" , 34, msgList.size());
        
        List<String> rowList = getRowList(msgList, "2");
        assertEquals("row 2 (missing fields- 10 mandatory fields)", 10, rowList.size());  
        
        rowList = getRowList(msgList, "3");
        assertEquals("row 3 (invalid account name)", 1, rowList.size());
        
        rowList = getRowList(msgList, "4");
        assertEquals("row 4 (invalid fleet level group name)", 1, rowList.size());
        
        rowList = getRowList(msgList, "5");
        assertEquals("row 5 (employeeID and username have different person records)", 1, rowList.size());

        rowList = getRowList(msgList, "6");
        assertEquals("row 6 (employeeID and email have different person records)", 1, rowList.size());

        rowList = getRowList(msgList, "7");
        assertEquals("row 7 (invalid data in several columns)", 6, rowList.size());
        
        rowList = getRowList(msgList, "8");
        assertEquals("row 8 (invalid rfid barcode)", 1, rowList.size());
        
        rowList = getRowList(msgList, "9");
        assertEquals("row 9 (no fleet group)", 1, rowList.size());

        rowList = getRowList(msgList, "10");
        assertEquals("row 10 (need team group)", 1, rowList.size());

        rowList = getRowList(msgList, "11");
        assertEquals("row 11 (cannot create a team under a team group)", 1, rowList.size());

    }

    private List<String> getRowList(List<String> msgList, String row) {
        boolean inRow = false;
        List<String>rowList = new ArrayList<String>();
        for (String msg : msgList) {
            if (msg.contains("Row " + row)) {
                inRow = true;
            }
            else if (inRow && msg.contains("Row"))
                return rowList;
            else if (inRow) {
                rowList.add(msg);
            }
        }
        return rowList;
    }

    @Test
    public void driversCheckWarnings()
    {
        System.out.println("RowImporterFactory " + this.applicationContext.containsBean("rowImporterFactory"));
        
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("importTest/DriverTemplateBad.xls");
        List<String> msgList = new FileChecker().checkFile(ImportType.DRIVERS, stream, true);
        
//        dumpErrors(msgList);
//        System.out.println("size " + msgList.size()) ;
        assertTrue("Unexpected msgList size", msgList.size() == 42);
        
        int warningCnt = 0;
        for (String msg : msgList)
            if (msg.startsWith("WARNING"))
                warningCnt++;
        assertTrue(warningCnt == 4);
    }

    @Test
    public void vehiclesCheckErrors()
    {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("importTest/VehicleTemplateBad.xls");
        List<String> msgList = new FileImporter().importFile(ImportType.VEHICLES, stream);
        
        if (msgList.size() != 19)
            dumpErrors(msgList);

        assertEquals("Unexpected message count returned", 19, msgList.size());
        
        List<String> rowList = getRowList(msgList, "2");
        assertEquals("row 2 (missing fields - 4 mandatory fields)", 4, rowList.size());  
        
        rowList = getRowList(msgList, "3");
        assertEquals("row 3 (invalid account name)", 1, rowList.size());
        
        rowList = getRowList(msgList, "4");
        assertEquals("row 4 (invalid fleet level group name)", 1, rowList.size());
        
        rowList = getRowList(msgList, "5");
        assertEquals("row 5 (state code is invalid)", 1, rowList.size());

        rowList = getRowList(msgList, "6");
        assertEquals("row 6 (vin in use by different account)", 1, rowList.size());
    
        rowList = getRowList(msgList, "7");
        assertEquals("row 7 (device not found in this account)", 1, rowList.size());
        
        rowList = getRowList(msgList, "8");
        assertEquals("row 8 (device not found in this account)", 1, rowList.size());

        rowList = getRowList(msgList, "9");
        assertEquals("row 9 (employeeID not found)", 1, rowList.size());
    }
    
    @Test
    public void vehiclesImport()
    {
        
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("importTest/VehicleTemplateGood.xls");
        ProgressBarBean progressBarBean = new ProgressBarBean();
        progressBarBean.startProcess();
        List<String> msgList = new FileImporter().importFile(ImportType.VEHICLES, stream, progressBarBean);
        assertTrue(msgList == null);
        
        boolean completed = false;
        for (int i = 0; i < 100; i++) {
            if (progressBarBean.getCurrentValue() != null && progressBarBean.getCurrentValue() > 100l) {
                completed = true;
                break;
            }
            
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
                
        }
        
        assertTrue("import process completed", completed);
        
    }
    
    private void dumpErrors(List<String> msgList) {
        for (String msg : msgList)
            System.out.println(msg);
    }

    
    
}
