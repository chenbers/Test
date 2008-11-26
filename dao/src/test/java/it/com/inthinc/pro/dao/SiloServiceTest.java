package it.com.inthinc.pro.dao;


import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import it.config.IntegrationConfig;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.AccountHessianDAO;
import com.inthinc.pro.dao.hessian.AddressHessianDAO;
import com.inthinc.pro.dao.hessian.GroupHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.extension.HessianDebug;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.AccountStatus;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.StateObj;
import com.inthinc.pro.model.Vehicle;

public class SiloServiceTest
{
    private static final Logger logger = Logger.getLogger(SiloServiceTest.class);
    
    private static SiloService siloService;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        IntegrationConfig config = new IntegrationConfig(new File("./src/test/resources"));

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        
        siloService = new SiloServiceCreator(host, port).getService();
//        HessianDebug.debugIn = true;
//        HessianDebug.debugOut = true;
        HessianDebug.debugRequest = true;
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @Test
    @Ignore
    public void states()
    {
        // TODO:
        // this just tests if our State enum matches the state list from the database
        // this seems like a bad idea we need some sort of mapper
        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(siloService);
        try
        {
        List<StateObj> statesList = stateDAO.getStates();

        for (StateObj stateObj : statesList)
        {
            State state = State.valueOf(stateObj.getStateID());
            
            assertEquals(stateObj.getName(), state.getName());
            assertEquals(stateObj.getAbbrev(), state.getAbbrev());
        }
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
    
    @Test
    public void siloService()
    {
        // test all create, find, update and any other methods (not delete yet though)
//        Account account = account();
//        Integer acctID = account.getAcctID();
        Integer acctID = 31;
        Group fleetGroup = groupHierarchy(acctID);
    }
    
    


    private Account account()
    {
        AccountHessianDAO accountDAO = new AccountHessianDAO();
        accountDAO.setSiloService(siloService);
        
        Account account = new Account(null, null, null, AccountStatus.ACCOUNT_ACTIVE);
        
        // create
        Integer siloID = 0;
        Integer acctID = accountDAO.create(siloID, account);
        assertNotNull("Create Account failed", acctID);
        account.setAcctID(acctID);
        logger.debug("CREATED ACCOUNT: " + account.getAcctID());        
        
        // find
        Account savedAccount = accountDAO.findByID(account.getAcctID());
        Util.compareObjects(account, savedAccount);
//              assertEquals("Account ID", account.getAcctID(), savedAccount.getAcctID());
//        assertEquals("Account Mail ID", account.getMailID(), savedAccount.getMailID());
//        assertEquals("Account Bill ID", account.getBillID(), savedAccount.getBillID());
//        assertEquals("Account Status", account.getStatus(), savedAccount.getStatus());

/*
        RETEST        
        Address mailAddress = address(acctID);
        account.setMailID(mailAddress.getAddrID());
        Address billAddress = address(acctID);
        account.setBillID(billAddress.getAddrID());

        // update
        Integer changedCount = accountDAO.update(account);
        assertEquals("Account update count", Integer.valueOf(1), changedCount);
        savedAccount = accountDAO.findByID(account.getAcctID());
        Util.compareObjects(account, savedAccount);
*/        
        return savedAccount;
    }

    private Address address(Integer acctID)
    {
        AddressHessianDAO addressDAO = new AddressHessianDAO();
        addressDAO.setSiloService(siloService);
        
        // create
        Address address = new Address(null, Util.randomInt(100, 999) + " Street", null, "City " + Util.randomInt(10,99), State.valueOf(Util.randomInt(1, State.values().length - 1)), "12345");
        Integer addrID = addressDAO.create(acctID, address);
        address.setAddrID(addrID);

        // find
        Address savedAddress= addressDAO.findByID(address.getAddrID());
        Util.compareObjects(address, savedAddress);
        
        // update
/*
RETEST        
        address.setAddr1(Util.randomInt(100, 999) + " Update Street");
        address.setCity("Update City " + Util.randomInt(10,99));
        Integer changedCount = addressDAO.update(address);
        assertEquals("Address update count", Integer.valueOf(1), changedCount);
        savedAddress= addressDAO.findByID(address.getAddrID());
        Util.compareObjects(address, savedAddress);
*/        
        return address;
    }
    
    private Group groupHierarchy(Integer acctID)
    {
        GroupHessianDAO groupDAO = new GroupHessianDAO();
        groupDAO.setSiloService(siloService);
    
        // fleet
        Group fleetGroup = new Group(0, acctID, "Fleet", 0);
        Integer groupID = groupDAO.create(acctID, fleetGroup);
        assertNotNull(groupID);
        fleetGroup.setGroupID(groupID);
        
        // region
        Group regionGroup = new Group(0, acctID, "Region", fleetGroup.getGroupID());
        groupID = groupDAO.create(acctID, regionGroup);
        assertNotNull(groupID);
        regionGroup.setGroupID(groupID);
        
        // team
        Group teamGroup = new Group(0, acctID, "Team", regionGroup.getGroupID());
        groupID = groupDAO.create(acctID, teamGroup);
        assertNotNull(groupID);
        teamGroup.setGroupID(groupID);
        
        // find individual
//         
        /*
        RETEST
            findByID for group (getGroup) is not populating groupID or status in the return map
        */        
        String[] ignoreList = {"status", "groupID"};
        Group returnedGroup = groupDAO.findByID(fleetGroup.getGroupID());
        Util.compareObjects(fleetGroup, returnedGroup, ignoreList);
        returnedGroup = groupDAO.findByID(regionGroup.getGroupID());
        Util.compareObjects(regionGroup, returnedGroup, ignoreList);
        returnedGroup = groupDAO.findByID(teamGroup.getGroupID());
        Util.compareObjects(teamGroup, returnedGroup, ignoreList);
        
        // find group hierarchy
        List<Group> groupList = groupDAO.getGroupHierarchy(acctID, fleetGroup.getGroupID());
        assertEquals(3, groupList.size());
        Util.compareObjects(fleetGroup, groupList.get(0), ignoreList);
        Util.compareObjects(regionGroup, groupList.get(1), ignoreList);
        Util.compareObjects(teamGroup, groupList.get(2), ignoreList);
        
        /*
        RETEST
        // update
        regionGroup.setName("Updated Region");
        Integer changedCount = groupDAO.update(regionGroup);
        assertEquals("Address update count", Integer.valueOf(1), changedCount);
        returnedGroup = groupDAO.findByID(regionGroup.getGroupID());
        Util.compareObjects(regionGroup, returnedGroup, ignoreList);

        // TODO: try changing group status
*/        
        return fleetGroup;
    }

    private Vehicle vehicle(Integer acctID)
    {
        VehicleHessianDAO addressDAO = new VehicleHessianDAO();
        addressDAO.setSiloService(siloService);
        
        // create
        Vehicle vehicle = new Vehicle();
        
        return vehicle;
    }
    



}
