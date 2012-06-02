package it.com.inthinc.pro.dao.jdbc;

import static org.junit.Assert.*;
import it.com.inthinc.pro.dao.Util;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.hos.util.DateUtil;
import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.GroupHessianDAO;
import com.inthinc.pro.dao.hessian.PersonHessianDAO;
import com.inthinc.pro.dao.hessian.RoleHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.TimeZoneHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.AdminPersonJDBCDAO;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.app.SupportedTimeZones;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;


public class AdminTest extends BaseJDBCTest {
    
    private static SiloService siloService;
    private static final String XML_DATA_FILE = "ReportTest.xml";
    private static ITData itData;

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
    public void compareTest() {
        PersonHessianDAO personHessianDAO = new PersonHessianDAO();
        personHessianDAO.setSiloService(siloService);
        GroupHessianDAO groupHessianDAO = new GroupHessianDAO();
        groupHessianDAO.setSiloService(siloService);
        
        
        AdminPersonJDBCDAO adminPersonJDBCDAO = new AdminPersonJDBCDAO();
        adminPersonJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        
        
//        List<Person> hessianPersonList = personHessianDAO.getPeopleInGroupHierarchy(itData.fleetGroup.getGroupID());
//        List<Person> hessianPersonList = personHessianDAO.getPeopleInGroupHierarchy(itData.fleetGroup.getGroupID(), itData.fleetUser.getGroupID());
        List<Person> hessianPersonList = personHessianDAO.getPeopleInGroupHierarchy(1,1);
//        GroupHierarchy groupHierarchy = new GroupHierarchy(groupHessianDAO.getGroupsByAcctID(itData.account.getAccountID()));
        GroupHierarchy groupHierarchy = new GroupHierarchy(groupHessianDAO.getGroupsByAcctID(1));
        List<TableFilterField> filters = new ArrayList<TableFilterField>();
        
        PageParams pageParams = new PageParams();
        pageParams.setFilterList(filters);
        pageParams.setStartRow(0);
        
//        List<Integer> groupIDList = groupHierarchy.getSubGroupIDList(itData.fleetGroup.getGroupID());
        List<Integer> groupIDList = groupHierarchy.getSubGroupIDList(1);
        
        Integer cnt = adminPersonJDBCDAO.getCount(groupIDList, filters);
        assertEquals("hessian vs jdbc person cnt", hessianPersonList.size(), cnt.intValue());
        pageParams.setEndRow(cnt);
        
        List<Person> jdbcPersonList = adminPersonJDBCDAO.getPeople(groupIDList, pageParams);
        assertEquals("hessian vs jdbc person cnt", hessianPersonList.size(), jdbcPersonList.size());
        
        
        String personIgnoreFields[] = {
                // person ok to ignore, unused fields
                "addressID", 
                "dept", 
                "height",
                "weight",
                "modified",

                "driver", 
                "user", 
                "driverID",
                "userID",

        };       
        String userIgnoreFields[] = {
                "person",
                "modified",
                "selectedMapLayerIDs",

                "lastLogin",
                "passwordDT",
                
        };
        String driverIgnoreFields[] = {
                "person",
                "modified",

                
        };

        for (Person hessianPerson : hessianPersonList) {
            boolean found = false;
            for (Person jdbcPerson : jdbcPersonList) {
                if (jdbcPerson.getPersonID().equals(hessianPerson.getPersonID())) {
                    found = true;
                    Util.compareObjects(hessianPerson, jdbcPerson, personIgnoreFields);

                    if (hessianPerson.getUser() != null) {
//System.out.println(hessianPerson.getUser().getUserID() + " " + DateUtil.getDisplayDate(hessianPerson.getUser().getLastLogin(), TimeZone.getTimeZone("UTC")) + " " + DateUtil.getDisplayDate(jdbcPerson.getUser().getLastLogin(), TimeZone.getTimeZone("UTC")));                        
                        assertTrue("user should not be null " + hessianPerson.getPersonID(), jdbcPerson.getUser() != null);
                        Util.compareObjects(hessianPerson.getUser(), jdbcPerson.getUser(), userIgnoreFields);
                    }
                    else {
                        assertTrue("user should be null " + hessianPerson.getPersonID(), jdbcPerson.getUser() == null);
                    }
                    if (hessianPerson.getDriver() != null) {
                        assertTrue("driver should not be null " + hessianPerson.getFullName(), jdbcPerson.getDriver() != null);
                        Util.compareObjects(hessianPerson.getDriver(), jdbcPerson.getDriver(), driverIgnoreFields);
                    }
                    else {
                        assertTrue("driver should be null " + hessianPerson.getPersonID(), jdbcPerson.getDriver() == null);
                    }
                    
                    break;
                }
            }
            assertTrue("Unable to find match for person " + hessianPerson.getPersonID(), found);
        }
        
        
        
    }
}
