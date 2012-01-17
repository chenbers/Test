package com.inthinc.pro.backing;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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
        VehicleStyleBean vehicleStyleBean = (VehicleStyleBean)applicationContext.getBean("vehicleStyleBean");
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
        vehicleStyleBean.setDriver(d);
        
        Vehicle v = new Vehicle();
        v.setDriverID(101);
        v.setVehicleID(1111);
        v.setGroupID(0);
        
        vehicleStyleBean.setVehicle(v);
        
        // Test Scores and Styles
        scoreMap = new HashMap<String, Integer>();
        styleMap = new HashMap<String, String>();
        
        scoreMap.put(ScoreType.SCORE_DRIVING_STYLE.toString(), 4);
        styleMap.put(ScoreType.SCORE_DRIVING_STYLE.toString(), ScoreBox.GetStyleFromScore(4, ScoreBoxSizes.MEDIUM));
          
        vehicleStyleBean.setScoreMap(scoreMap);
        vehicleStyleBean.setStyleMap(styleMap);
     
        Integer score = vehicleStyleBean.getScoreMap().get( ScoreType.SCORE_DRIVING_STYLE.toString());        
        assertEquals( score.toString(), "4");
        assertEquals( vehicleStyleBean.getStyleMap().get( ScoreType.SCORE_DRIVING_STYLE.toString() ) , "score_med_1" );
        
        // Test Events
        List<EventReportItem> styleEvents = new ArrayList<EventReportItem>();
        AggressiveDrivingEvent e = new AggressiveDrivingEvent();
        //For tiwipro inactive deltas are 0, for waySmart inactive deltas are + or - 5
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

        vehicleStyleBean.setSelectedBreakdown("HARD_VERT");
        vehicleStyleBean.setEvents(styleEvents);
        vehicleStyleBean.sortEvents();
        
        assertTrue( vehicleStyleBean.getFilteredEvents().get(0).getEvent().getEventType() == EventType.HARD_VERT );
        

    }
}
