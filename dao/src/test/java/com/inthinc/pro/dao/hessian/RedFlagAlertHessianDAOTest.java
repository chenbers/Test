package com.inthinc.pro.dao.hessian;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Zone;
@Ignore
public class RedFlagAlertHessianDAOTest
{
    RedFlagAlertHessianDAO redFlagAlertHessianDAO;
    ZoneHessianDAO zoneHessianDAO;
    PersonHessianDAO personHessianDAO;
    Integer personId;
    
    @BeforeClass
    public static void runOnceBeforeAllTests() throws Exception
    {

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Before
    public void setUp() throws Exception
    {
        redFlagAlertHessianDAO = new RedFlagAlertHessianDAO();
        redFlagAlertHessianDAO.setSiloService(new SiloServiceCreator("dev-pro.inthinc.com", 8099).getService());
        zoneHessianDAO = new ZoneHessianDAO();
        zoneHessianDAO.setSiloService(new SiloServiceCreator("dev-pro.inthinc.com", 8099).getService());
        personHessianDAO = new PersonHessianDAO();
        personHessianDAO.setSiloService(new SiloServiceCreator("dev-pro.inthinc.com", 8099).getService());
        List<Person> people = personHessianDAO.getPeopleInGroupHierarchy(1);
        Person admin = null;
        for(Person person:people){
            if (person.getUser().hasRoles() && person.getUser().getRoles().contains(2)) {
                admin = person;
                break;
            }
        }
        if (admin != null){
            personId = admin.getPersonID();
        }
        else{
            personId= people.get(0).getUserID();
        }
    }

    @Test
    public void findInGroupHierarchy()
    {
        try
        {
            List<RedFlagAlert> alerts = redFlagAlertHessianDAO.getRedFlagAlerts(0);

            assertTrue("expected no red flag alerts to be returned", alerts.size() == 0);

            alerts = redFlagAlertHessianDAO.getRedFlagAlerts(1);

            assertTrue("expected to retrieve red flag alert records", alerts.size() > 0);
        }
        catch (RuntimeException t)
        {
            t.printStackTrace();
            throw t;
        }
    }
    @Test
    public void update(){
        List<RedFlagAlert> plainRedFlagOrZoneAlerts = redFlagAlertHessianDAO.getRedFlagAlertsByUserIDDeep(personId);
        assertTrue("expected to retrieve red flag alert records", plainRedFlagOrZoneAlerts.size() > 0);
        RedFlagAlert rfa = plainRedFlagOrZoneAlerts.get(0);
        List<AlertMessageType> types = new ArrayList<AlertMessageType>();
        types.add(AlertMessageType.ALERT_TYPE_ENTER_ZONE);
        types.add(AlertMessageType.ALERT_TYPE_EXIT_ZONE);
       
        rfa.setTypes(types);
        redFlagAlertHessianDAO.update(rfa);
        List<RedFlagAlert> updatedRedFlagOrZoneAlerts = redFlagAlertHessianDAO.getRedFlagAlertsByUserIDDeep(personId);
        assertTrue("expected redflag to be updated", 
                updatedRedFlagOrZoneAlerts.get(0).getTypes().contains(AlertMessageType.ALERT_TYPE_ENTER_ZONE) &&
                updatedRedFlagOrZoneAlerts.get(0).getTypes().contains(AlertMessageType.ALERT_TYPE_EXIT_ZONE));
        
        types = new ArrayList<AlertMessageType>();
        types.add(AlertMessageType.ALERT_TYPE_ENTER_ZONE);
        rfa.setTypes(types);
        redFlagAlertHessianDAO.update(rfa);
        updatedRedFlagOrZoneAlerts = redFlagAlertHessianDAO.getRedFlagAlertsByUserIDDeep(personId);
        assertTrue("expected redflag to be updated", 
                updatedRedFlagOrZoneAlerts.get(0).getTypes().contains(AlertMessageType.ALERT_TYPE_ENTER_ZONE));
    }
    @Ignore
    @Test
    public void createAndDelete(){
        List<RedFlagAlert> plainRedFlagOrZoneAlerts = redFlagAlertHessianDAO.getRedFlagAlertsByUserIDDeep(personId);
        assertTrue("expected to retrieve red flag alert records", plainRedFlagOrZoneAlerts.size() > 0);
        RedFlagAlert rfa = plainRedFlagOrZoneAlerts.get(0);
        rfa.setAlertID(null);
        Integer alertId = redFlagAlertHessianDAO.create(1,rfa);
        assertTrue("expected redflag to be created", alertId != null);
        plainRedFlagOrZoneAlerts = redFlagAlertHessianDAO.getRedFlagAlertsByUserIDDeep(personId);
        int sizeBefore = plainRedFlagOrZoneAlerts.size();
        Integer result = redFlagAlertHessianDAO.deleteByID(alertId);
        plainRedFlagOrZoneAlerts = redFlagAlertHessianDAO.getRedFlagAlertsByUserIDDeep(personId);
        int sizeAfter = plainRedFlagOrZoneAlerts.size();
        assertTrue("expected redflag to be deleted", sizeBefore > sizeAfter);
        assertTrue("expected redflag to be deleted", result == 1);
        
    }
    @Ignore
    @Test
    public void deleteByZoneID(){
        List<Zone> zones = zoneHessianDAO.getZones(1);

        assertTrue("expected to retrieve zone records", zones.size() > 0);
        Zone zone = zones.get(0);
        Integer zoneID = zone.getZoneID();
        List<RedFlagAlert> plainRedFlagOrZoneAlerts = redFlagAlertHessianDAO.getRedFlagAlertsByUserIDDeep(2797);
        int sizeBefore = plainRedFlagOrZoneAlerts.size();
        RedFlagAlert rfa = plainRedFlagOrZoneAlerts.get(0);
        rfa.setAlertID(null);
        rfa.setZoneID(zoneID);
        Integer alertId = redFlagAlertHessianDAO.create(1,rfa);
        assertTrue("expected redflag to be created", alertId != null);
        Integer result = redFlagAlertHessianDAO.deleteAlertsByZoneID(zoneID);
        assertTrue("expected redflag with zoneID to be deleted", result > 0);
        RedFlagAlert alert = redFlagAlertHessianDAO.findByID(alertId);
        assertTrue("expected redflag with alertId to have been deleted", alert.getStatus().equals(Status.DELETED) );
        plainRedFlagOrZoneAlerts = redFlagAlertHessianDAO.getRedFlagAlertsByUserIDDeep(2797);
        int sizeAfter = plainRedFlagOrZoneAlerts.size();
        assertTrue("expected redflag to be deleted", sizeBefore == sizeAfter);
   }
}
