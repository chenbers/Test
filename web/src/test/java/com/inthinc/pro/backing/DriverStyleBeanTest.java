package com.inthinc.pro.backing;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.map.GoogleAddressLookup;
import com.inthinc.pro.model.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.backing.ui.EventReportItem;
import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.model.event.AggressiveDrivingEvent;
import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.util.MessageUtil;

public class DriverStyleBeanTest extends BaseBeanTest
{
    private Map<String, Integer> scoreMap;
    private Map<String, String>  styleMap;
    private Map<String, String> trendMap;
    
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
        DriverStyleBean driverStyleBean = (DriverStyleBean)applicationContext.getBean("driverStyleBean");
        NavigationBean nav = (NavigationBean)applicationContext.getBean("navigationBean");
        
        LocaleBean localeBean = new LocaleBean();
        localeBean.getLocale();

        Locale locale = new Locale("en", "US");
        localeBean.setLocale(locale);

        Person p = new Person();
        p.setFirst("John");
        p.setLast("Doe");
        p.setTimeZone(TimeZone.getTimeZone("MST"));
        
        Driver d = new Driver();
        d.setDriverID(101);
        d.setPersonID(45);
        d.setGroupID(101);

        d.setPerson(p);
        driverStyleBean.setDriver(d);

        // vehicle
        Vehicle v = new Vehicle();
        v.setDriverID(999999999);
        v.setVehicleID(999999939);
        v.setGroupID(999999929);

        driverStyleBean.setVehicle(v);
        
        // Test Scores and Styles
        scoreMap = new HashMap<String, Integer>();
        styleMap = new HashMap<String, String>();
        
        scoreMap.put(ScoreType.SCORE_DRIVING_STYLE.toString(), 4);
        styleMap.put(ScoreType.SCORE_DRIVING_STYLE.toString(), ScoreBox.GetStyleFromScore(4, ScoreBoxSizes.MEDIUM));
          
        driverStyleBean.setScoreMap(scoreMap);
        driverStyleBean.setStyleMap(styleMap);
     
        Integer score = driverStyleBean.getScoreMap().get( ScoreType.SCORE_DRIVING_STYLE.toString());        
        assertEquals( score.toString(), "4");
        assertEquals( driverStyleBean.getStyleMap().get( ScoreType.SCORE_DRIVING_STYLE.toString() ) , "score_med_1" );
         
        // Test Events
        List<EventReportItem> styleEvents = new ArrayList<EventReportItem>();
        AggressiveDrivingEvent e = new AggressiveDrivingEvent();
        e.setDeltaX(0);
        e.setDeltaY(0);
        e.setDeltaZ(5);
        e.setTime(new Date());
        e.setNoteID(new Long(123456));
        
        // Test EventType calculation.
        assertTrue( e.getEventType() == EventType.HARD_VERT); 
        
        // Test Event Filtering
        DateFormat dateFormatter = new SimpleDateFormat(MessageUtil.getMessageString("dateTimeFormat"),LocaleBean.getCurrentLocale());
        EventReportItem eri = new EventReportItem(e, p.getTimeZone(),MeasurementType.ENGLISH, dateFormatter);
        styleEvents.add(eri);

        driverStyleBean.setSelectedBreakdown("HARD_VERT");
        driverStyleBean.setEvents(styleEvents);
        driverStyleBean.sortEvents();
        
        assertTrue( driverStyleBean.getFilteredEvents().get(0).getEvent().getEventType() == EventType.HARD_VERT );

        // scoreMap
        scoreMap = new HashMap<String, Integer>();
        scoreMap.put(ScoreType.SCORE_DRIVING_STYLE.toString(), 4);
        scoreMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE.toString(), 15);
        scoreMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL.toString(), 16);
        scoreMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN.toString(), 17);
        scoreMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP.toString(), 18);

        // set scoreMap for vehicleStyleBean
        driverStyleBean.setScoreMap(scoreMap);

        // test scoreMap
        assertNotNull(driverStyleBean.getScoreMap());
        assertEquals(driverStyleBean.getScoreMap().get(ScoreType.SCORE_DRIVING_STYLE.toString()).toString(), "4");
        assertEquals(driverStyleBean.getScoreMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE.toString()).toString(), "15");
        assertEquals(driverStyleBean.getScoreMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL.toString()).toString(), "16");
        assertEquals(driverStyleBean.getScoreMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN.toString()).toString(), "17");
        assertEquals(driverStyleBean.getScoreMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP.toString()).toString(), "18");

        // styleMap
        styleMap = new HashMap<String, String>();
        styleMap.put(ScoreType.SCORE_DRIVING_STYLE.toString(), ScoreBox.GetStyleFromScore(4, ScoreBoxSizes.MEDIUM));
        styleMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE.toString(), ScoreBox.GetStyleFromScore(15, ScoreBoxSizes.MEDIUM));
        styleMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL.toString(), ScoreBox.GetStyleFromScore(16, ScoreBoxSizes.MEDIUM));
        styleMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN.toString(), ScoreBox.GetStyleFromScore(17, ScoreBoxSizes.MEDIUM));
        styleMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP.toString(), ScoreBox.GetStyleFromScore(18, ScoreBoxSizes.MEDIUM));

        // set styleMap for vehicleStyleBean
        driverStyleBean.setStyleMap(styleMap);

        // test styleMap
        assertNotNull(driverStyleBean.getStyleMap());
        assertEquals(driverStyleBean.getStyleMap().get(ScoreType.SCORE_DRIVING_STYLE.toString()).toString(), "score_med_1");
        assertEquals(driverStyleBean.getStyleMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE.toString()).toString(), "score_med_2");
        assertEquals(driverStyleBean.getStyleMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL.toString()).toString(), "score_med_2");
        assertEquals(driverStyleBean.getStyleMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN.toString()).toString(), "score_med_2");
        assertEquals(driverStyleBean.getStyleMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP.toString()).toString(), "score_med_2");

        // trendMap
        trendMap = new HashMap<String, String>();
        trendMap.put(ScoreType.SCORE_DRIVING_STYLE.toString(), driverStyleBean.createFusionMultiLineDef(driverStyleBean.getVehicle().getVehicleID(), driverStyleBean.durationBean.getDuration(), ScoreType.SCORE_DRIVING_STYLE));
        trendMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL.toString(), driverStyleBean.createFusionMultiLineDef(driverStyleBean.getVehicle().getVehicleID(), driverStyleBean.durationBean.getDuration(), ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL));
        trendMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE.toString(), driverStyleBean.createFusionMultiLineDef(driverStyleBean.getVehicle().getVehicleID(), driverStyleBean.durationBean.getDuration(), ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE));
        trendMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP.toString(), driverStyleBean.createFusionMultiLineDef(driverStyleBean.getVehicle().getVehicleID(), driverStyleBean.durationBean.getDuration(), ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP));
        trendMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN.toString(), driverStyleBean.createFusionMultiLineDef(driverStyleBean.getVehicle().getVehicleID(), driverStyleBean.durationBean.getDuration(), ScoreType.SCORE_DRIVING_STYLE_HARD_TURN));

        // set trendMap for driverStyleBean
        driverStyleBean.setTrendMap(trendMap);

        // test trendMap
        assertNotNull(driverStyleBean.getTrendMap());
        assertNotNull(driverStyleBean.getTrendMap().get(ScoreType.SCORE_DRIVING_STYLE.toString()));
        assertNotNull(driverStyleBean.getTrendMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL.toString()));
        assertNotNull(driverStyleBean.getTrendMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE.toString()));
        assertNotNull(driverStyleBean.getTrendMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP.toString()));
        assertNotNull(driverStyleBean.getTrendMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN.toString()));

        // events
        List<EventReportItem> vehicleStyleEvents = new ArrayList<EventReportItem>();
        //For tiwipro inactive deltas are 0, for waySmart inactive deltas are + or - 5
        AggressiveDrivingEvent e1 = new AggressiveDrivingEvent();
        e1.setDeltaX(0);
        e1.setDeltaY(0);
        e1.setDeltaZ(5);
        e1.setLatitude(32.96453094482422);
        e1.setLongitude(-117.12944793701172);
        e1.setTime(new Date());
        e1.setNoteID(new Long(123456));

        AggressiveDrivingEvent e2 = new AggressiveDrivingEvent();
        e2.setDeltaX(-10);
        e2.setDeltaY(5);
        e2.setDeltaZ(5);
        e2.setLatitude(32.96453094482422);
        e2.setLongitude(-117.12944793701172);
        e2.setTime(new Date());
        e2.setNoteID(new Long(234567));

        DateFormat dateFormatt = new SimpleDateFormat(MessageUtil.getMessageString("dateTimeFormat"),LocaleBean.getCurrentLocale());
        EventReportItem eri1 = new EventReportItem(e1, p.getTimeZone(),MeasurementType.ENGLISH, dateFormatt);
        EventReportItem eri2 = new EventReportItem(e2, p.getTimeZone(),MeasurementType.ENGLISH, dateFormatt);

        AddressLookup addressLookup  = new GoogleAddressLookup();
        addressLookup.setLocale(locale);
        driverStyleBean.setReportAddressLookupBean(addressLookup);
        driverStyleBean.setDisabledGoogleMapsInReportsAddressLookupBean(addressLookup);

        vehicleStyleEvents.add(eri1);
        vehicleStyleEvents.add(eri2);

        // set events for vehicleStyleBean and sort events
        driverStyleBean.setEvents(vehicleStyleEvents);
        driverStyleBean.sortEvents();

        // test events
        assertNotNull(driverStyleBean.getEvents());
        assertTrue(e1.getEventType() == EventType.HARD_VERT);
        assertTrue(e2.getEventType() == EventType.HARD_BRAKE);

        //test buildReport method
        assertNotNull(driverStyleBean.buildReport());
        assertEquals(0.4, driverStyleBean.buildReport().getPramMap().get("OVERALL_SCORE"));
        assertEquals(1.5, driverStyleBean.buildReport().getPramMap().get("SCORE_HARDBRAKE"));
        assertEquals(1.6, driverStyleBean.buildReport().getPramMap().get("SCORE_HARDACCEL"));
        assertEquals(1.7, driverStyleBean.buildReport().getPramMap().get("SCORE_HARDTURN"));
        assertEquals(2, driverStyleBean.buildReport().getPramMap().get("RECORD_COUNT"));

    }
}
