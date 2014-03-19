package com.inthinc.pro.backing;


import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.backing.ui.TripDisplay;
import com.inthinc.pro.dao.hessian.ZoneHessianDAO;
import com.inthinc.pro.dao.mock.proserver.SiloServiceCreator;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.map.GoogleAddressLookup;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Zone;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class DriverPerformanceBeanTestP1 extends BaseBeanTest
{
    private Map<String, Integer> scoreMap;
    private Map<String, String>  styleMap;
    final Date startDate = new org.joda.time.DateTime(2012, 11, 25, 13, 45, 27, 1).toDate();
    final Date endDate = new org.joda.time.DateTime(2012, 11, 27, 13, 45, 27, 1).toDate();
    private  List<LatLng> route ;
    static TimeZone timeZone = TimeZone.getTimeZone("US/Mountain");

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
        // just test the bean successfully creates all of the required pies
        
        // team level login
        loginUser("custom101");
        
        // get the bean from the applicationContext (initialized by Spring injection)
        DriverPerformanceBean driverPerformanceBean = (DriverPerformanceBean)applicationContext.getBean("driverPerformanceBean");
        NavigationBean nav = (NavigationBean)applicationContext.getBean("navigationBean");
        
        LocaleBean localeBean = new LocaleBean();
        localeBean.getLocale();
       
        Person p = new Person();
        p.setFirst("John");
        p.setLast("Doe");
        p.setTimeZone(TimeZone.getTimeZone("MST"));
        
        Driver d = new Driver();
        d.setDriverID(101);
        d.setPersonID(45);
        d.setGroupID(101);

        d.setPerson(p);
        driverPerformanceBean.setDriver(d);
        Locale locale = new Locale("en","US");

        AddressLookup addressLookup  = new GoogleAddressLookup();
        addressLookup.setLocale(locale) ;

        ZoneHessianDAO zoneHessianDAO = new ZoneHessianDAO();
        zoneHessianDAO.setSiloService(new SiloServiceCreator().getService());
        List<Zone> zones = zoneHessianDAO.getZones(1);

        route = new ArrayList<LatLng>();
        route.add(new LatLng(32.96453094482422f, -117.12944793701172f ));

        Trip trip = new Trip();
        trip.setStartTime(startDate);
        trip.setEndTime(endDate);
        trip.setMileage(1);
        trip.setRoute(route);

        TripDisplay tripDisplay = new TripDisplay( trip,  timeZone,  addressLookup , zones,  locale);
        driverPerformanceBean.setLastTrip(tripDisplay);
        driverPerformanceBean.getProUser().setZones(zones);
        driverPerformanceBean.setReportAddressLookupBean(addressLookup);
        driverPerformanceBean.setDisabledGoogleMapsInReportsAddressLookupBean(addressLookup);
        driverPerformanceBean.setLastTrip(tripDisplay);
        
        // Test Scores and Styles
        scoreMap = new HashMap<String, Integer>();
        styleMap = new HashMap<String, String>();
        
        scoreMap.put(ScoreType.SCORE_SPEEDING.toString(), 4);
        styleMap.put(ScoreType.SCORE_SPEEDING.toString(), ScoreBox.GetStyleFromScore(4, ScoreBoxSizes.MEDIUM));
        
        scoreMap.put(ScoreType.SCORE_SPEEDING_21_30.toString(), 8);
        styleMap.put(ScoreType.SCORE_SPEEDING_21_30.toString(), ScoreBox.GetStyleFromScore(8, ScoreBoxSizes.MEDIUM));
        
        scoreMap.put(ScoreType.SCORE_SPEEDING_31_40.toString(), 18);
        styleMap.put(ScoreType.SCORE_SPEEDING_31_40.toString(), ScoreBox.GetStyleFromScore(18, ScoreBoxSizes.MEDIUM));
        
        scoreMap.put(ScoreType.SCORE_SPEEDING_41_54.toString(), 28);
        styleMap.put(ScoreType.SCORE_SPEEDING_41_54.toString(), ScoreBox.GetStyleFromScore(28, ScoreBoxSizes.MEDIUM));
        
        scoreMap.put(ScoreType.SCORE_SPEEDING_55_64.toString(), 38);
        styleMap.put(ScoreType.SCORE_SPEEDING_55_64.toString(), ScoreBox.GetStyleFromScore(38, ScoreBoxSizes.MEDIUM));
        
        scoreMap.put(ScoreType.SCORE_SPEEDING_65_80.toString(), 48);
        styleMap.put(ScoreType.SCORE_SPEEDING_65_80.toString(), ScoreBox.GetStyleFromScore(48, ScoreBoxSizes.MEDIUM));

        driverPerformanceBean.setScoreMap(scoreMap);
        driverPerformanceBean.setStyleMap(styleMap);

        Integer score = driverPerformanceBean.getScoreMap().get( ScoreType.SCORE_SPEEDING_21_30.toString());
        assertEquals( score.toString(), "8");
        assertEquals( driverPerformanceBean.getStyleMap().get( ScoreType.SCORE_SPEEDING_21_30.toString() ) , "score_med_1" );
        
        score = driverPerformanceBean.getScoreMap().get( ScoreType.SCORE_SPEEDING_65_80.toString());
        assertEquals( score.toString(), "48");
        assertEquals( driverPerformanceBean.getStyleMap().get( ScoreType.SCORE_SPEEDING_65_80.toString() ) , "score_med_5" );

        //test buildReportCriteria
        driverPerformanceBean.getCrashSummary();
        assertNotNull(driverPerformanceBean.buildReportCriteria());
    }
}
