package com.inthinc.pro.backing;


import com.inthinc.pro.backing.ui.TripDisplay;
import com.inthinc.pro.dao.hessian.ZoneHessianDAO;
import com.inthinc.pro.dao.mock.proserver.SiloServiceCreator;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.map.GoogleAddressLookup;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.Zone;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class VehiclePerformanceBeanTest extends BaseBeanTest {
    static TimeZone timeZone = TimeZone.getTimeZone("US/Mountain");
    final Date startDate = new org.joda.time.DateTime(2012, 11, 25, 13, 45, 27, 1).toDate();
   final Date endDate = new org.joda.time.DateTime(2012, 11, 27, 13, 45, 27, 1).toDate();
   private  List<LatLng> route ;



    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @Test
    public void bean()
    {

        // team level login
        loginUser("custom101");
        // get the bean from the applicationContext (initialized by Spring injection)
        VehiclePerformanceBean vehiclePerformanceBean = (VehiclePerformanceBean)applicationContext.getBean("vehiclePerformanceBean");
        Vehicle v = new Vehicle();
        v.setDriverID(55555);
        v.setVehicleID(44444);
        v.setGroupID(3333);
        vehiclePerformanceBean.setVehicle(v);

        Person p = new Person();
        p.setFirst("Vali");
        p.setLast("Doe");

        Driver d = new Driver();
        d.setDriverID(55555);
        d.setPersonID(45);
        d.setGroupID(101);
        d.setPerson(p);

        vehiclePerformanceBean.setDriver(d);
        route = new ArrayList<LatLng>();
        route.add(new LatLng(32.96453094482422f, -117.12944793701172f ));
        Trip trip = new Trip();
        trip.setStartTime(startDate);
        trip.setEndTime(endDate);
        trip.setMileage(1);
        trip.setRoute(route);

        Locale locale = new Locale("en","US");

        AddressLookup addressLookup  = new GoogleAddressLookup();
        addressLookup.setLocale(locale);

        ZoneHessianDAO zoneHessianDAO = new ZoneHessianDAO();
        zoneHessianDAO.setSiloService(new SiloServiceCreator().getService());
        List<Zone> zones = zoneHessianDAO.getZones(1);

        TripDisplay tripDisplay = new TripDisplay( trip,  timeZone,  addressLookup , zones,  locale);
        vehiclePerformanceBean.setLastTrip(tripDisplay);
        vehiclePerformanceBean.getProUser().setZones(zones);
        vehiclePerformanceBean.setReportAddressLookupBean(addressLookup);
        vehiclePerformanceBean.setDisabledGoogleMapsInReportsAddressLookupBean(addressLookup);
        vehiclePerformanceBean.setLastTrip(tripDisplay);

        //test
        assertNotNull(vehiclePerformanceBean.buildReportCriteria());

    }
}
