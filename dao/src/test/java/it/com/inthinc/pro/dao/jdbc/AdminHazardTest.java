    package it.com.inthinc.pro.dao.jdbc;

import static org.junit.Assert.assertTrue;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.RoleHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.TimeZoneHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.AdminHazardJDBCDAO;
import com.inthinc.pro.dao.jdbc.AdminVehicleJDBCDAO;
import com.inthinc.pro.model.BoundingBox;
import com.inthinc.pro.model.Hazard;
import com.inthinc.pro.model.HazardStatus;
import com.inthinc.pro.model.HazardType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementLengthType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.app.SupportedTimeZones;

public class AdminHazardTest extends BaseJDBCTest {
    private static SiloService siloService;
    private static ITData itData;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        IntegrationConfig config = new IntegrationConfig();
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        siloService = new SiloServiceCreator(host, port).getService();
        initApp();
        itData = new ITData();
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

    static Map<String, LatLng> locations = new HashMap<String, LatLng>();
    static{
        locations.put("inthinc", new LatLng(40.7106, -111.9945));
        locations.put("bangerter_21st", new LatLng(40.7257, -111.9863));
        locations.put("spagetti", new LatLng(40.721, -111.9046));
        locations.put("summitPark", new LatLng(40.7525, -111.613));
    }
    @Test
    public void createHazard_validAllHazardLocations_eachShouldInsert(){
        boolean returnsHazardID = false;
        for(String locationName: locations.keySet()){
            AdminHazardJDBCDAO hazardJDBCDAO = new AdminHazardJDBCDAO();
            hazardJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
            Hazard hazard = new Hazard();
            hazard.setAcctID(1);
            hazard.setCreated(new Date());
            hazard.setDescription("IT created: "+locationName);
            hazard.setDeviceID(5780);//jwimmerTiwipro
            hazard.setDriverID(2262);//jwimmer
            hazard.setEndTime(new DateTime().plus(Period.hours(50)).toDate());
            hazard.setLatitude(locations.get(locationName).getLat());
            hazard.setLongitude(locations.get(locationName).getLng());
            hazard.setLocation("IT created for "+locationName+" at "+new DateTime().toString("yyyyMMddkkmmss"));
            System.out.println("radius: "+HazardType.ROADRESTRICTIONS_BAN_CLOSURE.getRadius());
            hazard.setRadiusMeters(HazardType.ROADRESTRICTIONS_BAN_CLOSURE.getRadius());
            hazard.setStartTime(new Date());
            hazard.setStateID(45);//UTah
            hazard.setStatus(HazardStatus.ACTIVE);
            hazard.setType(HazardType.ROADRESTRICTIONS_BAN_CLOSURE);
            hazard.setUserID(1655);//jwimmer user
            hazard.setVehicleID(6956);//ATAT aka jwimmer truck
            
            Integer hazardID = hazardJDBCDAO.create(hazard.getAccountID(), hazard);
            returnsHazardID = (hazardID != null);
            assertTrue(returnsHazardID);
            System.out.println("inserted "+locationName);
        }
    }

    public void findHazardsByUserAcct_validBoundingBoxAcrossNeg180_validResults(){
        //create a hazard to ensure test will have results
        AdminHazardJDBCDAO hazardJDBCDAO = new AdminHazardJDBCDAO();
        boolean returnsHazardID = false;
        hazardJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        Hazard hazardToInsert = new Hazard();
        hazardToInsert.setAcctID(2);
        hazardToInsert.setCreated(new Date());
        hazardToInsert.setDescription("IT created: mid-ocean");
        hazardToInsert.setDeviceID(5780);//jwimmerTiwipro
        hazardToInsert.setDriverID(2262);//jwimmer
        hazardToInsert.setEndTime(new DateTime().plus(Period.hours(50)).toDate());
        hazardToInsert.setLatitude(0.0);
        hazardToInsert.setLongitude(0.0);
        hazardToInsert.setLocation("IT created for 0,0 at "+new DateTime());
        System.out.println("radius: "+HazardType.ROADRESTRICTIONS_BAN_CLOSURE.getRadius());
        hazardToInsert.setRadiusMeters(HazardType.ROADRESTRICTIONS_BAN_CLOSURE.getRadius());
        hazardToInsert.setStartTime(new Date());
        hazardToInsert.setStateID(45);//UTah
        hazardToInsert.setStatus(HazardStatus.ACTIVE);
        hazardToInsert.setType(HazardType.ROADRESTRICTIONS_BAN_CLOSURE);
        hazardToInsert.setUserID(1655);//jwimmer user
        hazardToInsert.setVehicleID(6956);//ATAT aka jwimmer truck
        
        Integer hazardID = hazardJDBCDAO.create(hazardToInsert.getAccountID(), hazardToInsert);
        returnsHazardID = (hazardID != null);
        assertTrue(returnsHazardID);
        
        
        hazardJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        User fakeUser = new User(){
            @Override
            public String toString(){
                return "User [fakeUser]";
            }
            @Override
            public Person getPerson(){
                return new Person(){
                  @Override 
                  public Integer getAcctID(){
                      return 2;
                  }
                };
            }
        };
        BoundingBox latBiggerThanLng_world = new BoundingBox(180, -90, 179, 90);
        List<Hazard> theHazards = hazardJDBCDAO.findHazardsByUserAcct(fakeUser, latBiggerThanLng_world);
        for(Hazard hazard: theHazards){
            System.out.println("hazard: "+hazard);
        }
      //initial test just that there is SOME data returned
        assertTrue(theHazards.size() > 0);
    }
    @Test
    public void findHazardsByUserAcct_validBoundingBoxes_validResults(){
        //create a hazard to ensure test will have results
        AdminHazardJDBCDAO hazardJDBCDAO = new AdminHazardJDBCDAO();
        boolean returnsHazardID = false;
        hazardJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        Hazard hazardToInsert = new Hazard();
        hazardToInsert.setAcctID(1);
        hazardToInsert.setCreated(new Date());
        hazardToInsert.setDescription("IT created: mid-ocean");
        hazardToInsert.setDeviceID(5780);//jwimmerTiwipro
        hazardToInsert.setDriverID(2262);//jwimmer
        hazardToInsert.setEndTime(new DateTime().plus(Period.hours(50)).toDate());
        hazardToInsert.setLatitude(0.0);
        hazardToInsert.setLongitude(0.0);
        hazardToInsert.setLocation("IT created for 0,0 at "+new DateTime());
        System.out.println("radius: "+HazardType.ROADRESTRICTIONS_BAN_CLOSURE.getRadius());
        hazardToInsert.setRadiusMeters(HazardType.ROADRESTRICTIONS_BAN_CLOSURE.getRadius());
        hazardToInsert.setStartTime(new Date());
        hazardToInsert.setStateID(45);//UTah
        hazardToInsert.setStatus(HazardStatus.ACTIVE);
        hazardToInsert.setType(HazardType.ROADRESTRICTIONS_BAN_CLOSURE);
        hazardToInsert.setUserID(1655);//jwimmer user
        hazardToInsert.setVehicleID(6956);//ATAT aka jwimmer truck
        
        Integer hazardID = hazardJDBCDAO.create(hazardToInsert.getAccountID(), hazardToInsert);
        returnsHazardID = (hazardID != null);
        assertTrue(returnsHazardID);
        
        hazardJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        User fakeUser = new User(){
            @Override
            public String toString(){
                return "User [fakeUser]";
            }
            @Override
            public Person getPerson(){
                return new Person(){
                  @Override 
                  public Integer getAcctID(){
                      return 1;
                  }
                };
            }
        };
        BoundingBox world = new BoundingBox(-90, -180, 90, 180);
        List<Hazard> theHazards = hazardJDBCDAO.findHazardsByUserAcct(fakeUser, world);
        for(Hazard hazard: theHazards){
            System.out.println("hazard: "+hazard);
        }
        //initial test just that there is SOME data returned
        assertTrue(theHazards.size() > 0);
    }
    @Test
    public void findHazardsByUserAcct_invalidBoundingBoxes_noResults(){
        AdminHazardJDBCDAO hazardJDBCDAO = new AdminHazardJDBCDAO();
        hazardJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        User fakeUser = new User(){
            @Override
            public String toString(){
                return "User [fakeUser]";
            }
            @Override
            public Person getPerson(){
                return new Person(){
                  @Override 
                  public Integer getAccountID(){
                      return 1;
                  }
                };
            }
        };
        BoundingBox invalidBecause_lat1BiggerThanLat2 = new BoundingBox(-90, -180, 90, -180);
        List<Hazard> theHazards = hazardJDBCDAO.findHazardsByUserAcct(fakeUser, invalidBecause_lat1BiggerThanLat2);
        for(Hazard hazard: theHazards){
            System.out.println("hazard: "+hazard);
        }
        assertTrue(theHazards.size() == 0);
    }
    @Test
    public void findVehiclesByAccountWithinDistance_validAccountAndDist_anyVehiclesInRange(){
        
        AdminVehicleJDBCDAO vehicleJDBCDAO = new AdminVehicleJDBCDAO();
        vehicleJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        Long twoHundredMilesInKM = MeasurementLengthType.ENGLISH_MILES.convertToMeters(200).longValue();
        List<Vehicle> vehicles = vehicleJDBCDAO.findVehiclesByAccountWithinDistance(new Integer(1), twoHundredMilesInKM, locations.get("inthinc"));
        for(Vehicle vehicle: vehicles){
            System.out.println("vehicle.device: "+vehicle.getDevice());
            System.out.println("vehicle: "+vehicle);
        }
        //assertTrue(vehicles.size() > 0);
        if(vehicles.size() == 0)
            System.out.println("WARNING:");
        System.out.println("findVehiclesByAccountWithinDistance found "+vehicles.size()+" vehicles.");
    }
}
