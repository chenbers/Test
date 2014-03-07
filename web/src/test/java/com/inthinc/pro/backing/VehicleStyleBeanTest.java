package com.inthinc.pro.backing;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.map.GoogleAddressLookup;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.backing.ui.EventReportItem;
import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.event.AggressiveDrivingEvent;
import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.util.MessageUtil;

public class VehicleStyleBeanTest extends BaseBeanTest
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
        
        // get the beans from the applicationContext (initialized by Spring injection)
        VehicleStyleBean vehicleStyleBean = (VehicleStyleBean)applicationContext.getBean("vehicleStyleBean");
        PerformanceDataBean pdb = (PerformanceDataBean)applicationContext.getBean("performanceDataBean");
        NavigationBean nav = (NavigationBean)applicationContext.getBean("navigationBean");

        // locale
        LocaleBean localeBean = (LocaleBean)applicationContext.getBean("localeBean");
        Locale locale = new Locale("en", "US");
        localeBean.setLocale(locale);

        // person
        Person p = new Person();
        p.setFirst("John");
        p.setLast("Doe");
        p.setTimeZone(TimeZone.getTimeZone("MST"));

        // driver
        Driver d = new Driver();
        d.setDriverID(999999999);
        d.setPersonID(999999919);
        d.setGroupID(999999929);
        d.setPerson(p);

        // set driver for vehicleStyleBean
        vehicleStyleBean.setDriver(d);

        // vehicle
        Vehicle v = new Vehicle();
        v.setDriverID(999999999);
        v.setVehicleID(999999939);
        v.setGroupID(999999929);

        // set vehicle for vehicleStyleBean
        vehicleStyleBean.setVehicle(v);

        // test make sure that driver and vehicle are not null
        assertNotNull(vehicleStyleBean.getVehicle());
        assertNotNull(vehicleStyleBean.getDriver());

        // scoreMap
        scoreMap = new HashMap<String, Integer>();
        scoreMap.put(ScoreType.SCORE_DRIVING_STYLE.toString(), 4);
        scoreMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE.toString(), 15);
        scoreMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL.toString(), 16);
        scoreMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN.toString(), 17);
        scoreMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP.toString(), 18);

        // set scoreMap for vehicleStyleBean
        vehicleStyleBean.setScoreMap(scoreMap);

        // test scoreMap
        assertNotNull(vehicleStyleBean.getScoreMap());
        assertEquals(vehicleStyleBean.getScoreMap().get(ScoreType.SCORE_DRIVING_STYLE.toString()).toString(), "4");
        assertEquals(vehicleStyleBean.getScoreMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE.toString()).toString(), "15");
        assertEquals(vehicleStyleBean.getScoreMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL.toString()).toString(), "16");
        assertEquals(vehicleStyleBean.getScoreMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN.toString()).toString(), "17");
        assertEquals(vehicleStyleBean.getScoreMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP.toString()).toString(), "18");

        // styleMap
        styleMap = new HashMap<String, String>();
        styleMap.put(ScoreType.SCORE_DRIVING_STYLE.toString(), ScoreBox.GetStyleFromScore(4, ScoreBoxSizes.MEDIUM));
        styleMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE.toString(), ScoreBox.GetStyleFromScore(15, ScoreBoxSizes.MEDIUM));
        styleMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL.toString(), ScoreBox.GetStyleFromScore(16, ScoreBoxSizes.MEDIUM));
        styleMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN.toString(), ScoreBox.GetStyleFromScore(17, ScoreBoxSizes.MEDIUM));
        styleMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP.toString(), ScoreBox.GetStyleFromScore(18, ScoreBoxSizes.MEDIUM));

        // set styleMap for vehicleStyleBean
        vehicleStyleBean.setStyleMap(styleMap);

        // test styleMap
        assertNotNull(vehicleStyleBean.getStyleMap());
        assertEquals(vehicleStyleBean.getStyleMap().get(ScoreType.SCORE_DRIVING_STYLE.toString()).toString(), "score_med_1");
        assertEquals(vehicleStyleBean.getStyleMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE.toString()).toString(), "score_med_2");
        assertEquals(vehicleStyleBean.getStyleMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL.toString()).toString(), "score_med_2");
        assertEquals(vehicleStyleBean.getStyleMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN.toString()).toString(), "score_med_2");
        assertEquals(vehicleStyleBean.getStyleMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP.toString()).toString(), "score_med_2");

        // trendMap
        trendMap = new HashMap<String, String>();
        trendMap.put(ScoreType.SCORE_DRIVING_STYLE.toString(), vehicleStyleBean.createFusionMultiLineDef(vehicleStyleBean.getVehicle().getVehicleID(), vehicleStyleBean.durationBean.getDuration(), ScoreType.SCORE_DRIVING_STYLE));
        trendMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL.toString(), vehicleStyleBean.createFusionMultiLineDef(vehicleStyleBean.getVehicle().getVehicleID(), vehicleStyleBean.durationBean.getDuration(), ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL));
        trendMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE.toString(), vehicleStyleBean.createFusionMultiLineDef(vehicleStyleBean.getVehicle().getVehicleID(), vehicleStyleBean.durationBean.getDuration(), ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE));
        trendMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP.toString(), vehicleStyleBean.createFusionMultiLineDef(vehicleStyleBean.getVehicle().getVehicleID(), vehicleStyleBean.durationBean.getDuration(), ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP));
        trendMap.put(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN.toString(), vehicleStyleBean.createFusionMultiLineDef(vehicleStyleBean.getVehicle().getVehicleID(), vehicleStyleBean.durationBean.getDuration(), ScoreType.SCORE_DRIVING_STYLE_HARD_TURN));

        // set trendMap for vehicleStyleBean
        vehicleStyleBean.setTrendMap(trendMap);

        // test trendMap
        assertNotNull(vehicleStyleBean.getTrendMap());
        assertNotNull(vehicleStyleBean.getTrendMap().get(ScoreType.SCORE_DRIVING_STYLE.toString()));
        assertNotNull(vehicleStyleBean.getTrendMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL.toString()));
        assertNotNull(vehicleStyleBean.getTrendMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE.toString()));
        assertNotNull(vehicleStyleBean.getTrendMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP.toString()));
        assertNotNull(vehicleStyleBean.getTrendMap().get(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN.toString()));

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

        DateFormat dateFormatter = new SimpleDateFormat(MessageUtil.getMessageString("dateTimeFormat"),LocaleBean.getCurrentLocale());
        EventReportItem eri1 = new EventReportItem(e1, p.getTimeZone(),MeasurementType.ENGLISH, dateFormatter);
        EventReportItem eri2 = new EventReportItem(e2, p.getTimeZone(),MeasurementType.ENGLISH, dateFormatter);

        AddressLookup addressLookup  = new GoogleAddressLookup();
        addressLookup.setLocale(locale);
        vehicleStyleBean.setReportAddressLookupBean(addressLookup);
        vehicleStyleBean.setDisabledGoogleMapsInReportsAddressLookupBean(addressLookup);

        vehicleStyleEvents.add(eri1);
        vehicleStyleEvents.add(eri2);

        // set events for vehicleStyleBean and sort events
        vehicleStyleBean.setEvents(vehicleStyleEvents);
        vehicleStyleBean.sortEvents();

        // test events
        assertNotNull(vehicleStyleBean.getEvents());
        assertTrue(e1.getEventType() == EventType.HARD_VERT);
        assertTrue(e2.getEventType() == EventType.HARD_BRAKE);

        // selectedBreakdown
        vehicleStyleBean.setSelectedBreakdown("HARD_VERT");
        assertTrue(vehicleStyleBean.getFilteredEvents().get(0).getEvent().getEventType() == EventType.HARD_VERT);

        // test bulidReport  method
        assertNotNull(vehicleStyleBean.buildReport());
        assertEquals(0.4, vehicleStyleBean.buildReport().getPramMap().get("OVERALL_SCORE"));
        assertEquals(1.5, vehicleStyleBean.buildReport().getPramMap().get("SCORE_HARDBRAKE"));
        assertEquals(1.6, vehicleStyleBean.buildReport().getPramMap().get("SCORE_HARDACCEL"));
        assertEquals(1.7, vehicleStyleBean.buildReport().getPramMap().get("SCORE_HARDTURN"));
        assertEquals(2, vehicleStyleBean.buildReport().getPramMap().get("RECORD_COUNT"));
    }
}
