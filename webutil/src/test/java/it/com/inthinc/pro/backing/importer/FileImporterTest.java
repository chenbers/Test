package it.com.inthinc.pro.backing.importer;

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
import com.inthinc.pro.dao.hessian.AccountHessianDAO;
import com.inthinc.pro.dao.hessian.GroupHessianDAO;
import com.inthinc.pro.dao.hessian.PersonHessianDAO;
import com.inthinc.pro.dao.hessian.RoleHessianDAO;
import com.inthinc.pro.dao.hessian.UserHessianDAO;
import com.inthinc.pro.dao.hessian.exceptions.DuplicateEmailException;
import com.inthinc.pro.dao.hessian.exceptions.DuplicateUsernameException;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.security.Role;


public class FileImporterTest extends BaseSpringTest {
    
    // this must match the account name in importTest/DriverTemplateNoErrors.xls
    private static final String TEST_ACCOUNT_NAME = "BulkImportTest_5";
    private static SiloServiceCreator siloServiceCreator;
    private static Account testAccount;
    private static final String PASSWORD = "nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        siloServiceCreator = new SiloServiceCreator(host, port);
        
        setupAccount();
        
    }
    
    private static void setupAccount() {

        AccountHessianDAO accountDAO = new AccountHessianDAO();
        accountDAO.setSiloService(siloServiceCreator.getService());
        
        List<Account> accountList = accountDAO.getAllAcctIDs();
        for (Account account : accountList) {
            if (account.getAcctName().equalsIgnoreCase(TEST_ACCOUNT_NAME)) {
                testAccount = account;
                return;
            }
        }

        // create the account 
        Account account = new Account(null, TEST_ACCOUNT_NAME, Status.ACTIVE);
        // create an account
        Integer acctID = accountDAO.create(account);
        account.setAcctID(acctID);
        testAccount = account;
        
        GroupHessianDAO groupDAO = new GroupHessianDAO();
        groupDAO.setSiloService(siloServiceCreator.getService());
        
        // create the account's top level group
        Group topGroup = new Group(0, acctID, "fleet", 0, GroupType.FLEET, null, "Initial top level group", 5, new LatLng(0.0, 0.0));
        Integer groupID = groupDAO.create(acctID, topGroup);
        topGroup.setGroupID(groupID);
        
        Group divGroup = new Group(0, acctID, "division", topGroup.getGroupID(), GroupType.DIVISION, null, "Initial division level group", 5, new LatLng(0.0, 0.0));
        groupID = groupDAO.create(acctID, divGroup);
        
        
        PersonHessianDAO personDAO = new PersonHessianDAO();
        personDAO.setSiloService(siloServiceCreator.getService());
        // create the person record for the main user
        String email = TEST_ACCOUNT_NAME+"@email.com";
        String empID = TEST_ACCOUNT_NAME;
        Person person = new Person(new Integer(0), acctID, TimeZone.getDefault(), null, email, null, "5555555555", "5555555555",
                null, null, null, null, null, empID, null,
                "title", "dept", TEST_ACCOUNT_NAME, "m", TEST_ACCOUNT_NAME, "jr", Gender.FEMALE, 65, 180, new Date(), Status.ACTIVE, MeasurementType.ENGLISH, FuelEfficiencyType.MPG_US, Locale
                        .getDefault());
        person.setAddress(new Address(null, "", null, "", null, "", acctID));
        Integer personID = null;
        try {
            personID = personDAO.create(acctID, person);
            person.setPersonID(personID);
        }
        catch (DuplicateEmailException ex) {
            groupDAO.deleteByID(groupID);
            accountDAO.deleteByID(acctID);
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
        String username = TEST_ACCOUNT_NAME;
        User user = new User(0, person.getPersonID(), roleIDs, Status.ACTIVE, username, PASSWORD, topGroup.getGroupID());
        Integer userID = null;
        try {
            userID = userDAO.create(personID, user);
            user.setUserID(userID);
        }
        catch (DuplicateUsernameException ex) {
            // personDAO.deleteByID(personID);
            groupDAO.deleteByID(groupID);
            accountDAO.deleteByID(acctID);
            System.out.println("Duplicate username: [" + username + "]");
            return;
        }
        
    }

    
    @Test
    public void driversImport()
    {
        System.out.println("rowProcessorFactory " + this.applicationContext.containsBean("rowProcessorFactory"));
        
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("importTest/DriverTemplateGood.xls");
        List<String> msgList = new FileImporter().importFile(ImportType.DRIVERS, stream);
        
        dumpErrors(msgList);
        
        
        assertTrue(msgList.size() == 0);
        
    }

    @Test
    public void driversCheckErrors()
    {
        System.out.println("RowImporterFactory " + this.applicationContext.containsBean("rowImporterFactory"));
        
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("importTest/DriverTemplateBad.xls");
        List<String> msgList = new FileImporter().importFile(ImportType.DRIVERS, stream);
        
        dumpErrors(msgList);
        System.out.println("size " + msgList.size()) ;

        assertTrue(msgList.size() == 26);
        
        // row 2 (missing fields) 
        List<String> rowList = getRowList(msgList, "2");
        assertTrue(rowList.size() == 10);  // 10 mandatory fields
        
        // row 3 (invalid account name)
        rowList = getRowList(msgList, "3");
        assertTrue(rowList.size() == 1);
        
        // row 4 (invalid fleet level group name)
        rowList = getRowList(msgList, "4");
        assertTrue(rowList.size() == 1);
        
        // row 5 (employeeID and username have different person records)
        rowList = getRowList(msgList, "5");
        assertTrue(rowList.size() == 1);

        // row 6 (employeeID and email have different person records)
        rowList = getRowList(msgList, "6");
        assertTrue(rowList.size() == 1);

        // row 7 (invalid data in several columns)
        rowList = getRowList(msgList, "7");
        assertTrue(rowList.size() == 6);
        
        
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
        
        dumpErrors(msgList);
        System.out.println("size " + msgList.size()) ;
        assertTrue(msgList.size() == 29);
        
        int warningCnt = 0;
        for (String msg : msgList)
            if (msg.startsWith("WARNING"))
                warningCnt++;
        assertTrue(warningCnt == 3);
    }

    
    private void dumpErrors(List<String> msgList) {
        for (String msg : msgList)
            System.out.println(msg);
    }

    
    
}
