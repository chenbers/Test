package com.inthinc.pro.backing;


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
import com.inthinc.pro.model.AggressiveDrivingEvent;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EventType;
import com.inthinc.pro.model.FullEvent;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.SpeedingEvent;

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
        loginUser("normal101");
        
        // get the bean from the applicationContext (initialized by Spring injection)
        VehicleStyleBean vehicleStyleBean = (VehicleStyleBean)applicationContext.getBean("vehicleStyleBean");
        NavigationBean nav = (NavigationBean)applicationContext.getBean("navigationBean");
              
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
        e.setDeltaX(1);
        e.setDeltaY(1);
        e.setDeltaZ(5);
        e.setTime(new Date());
        e.setNoteID(new Long(123456));
        
        // Test EventType calculation.
        assertTrue( e.getEventType() == EventType.HARD_VERT); 
        
        // Test Event Filtering
        EventReportItem eri = new EventReportItem(e, p.getTimeZone(),MeasurementType.ENGLISH);
        styleEvents.add(eri);

        vehicleStyleBean.setSelectedEventType("HARD_VERT");
        vehicleStyleBean.setStyleEvents(styleEvents);
        vehicleStyleBean.filterEventsAction();
        
        assertTrue( vehicleStyleBean.getFilteredStyleEvents().get(0).getEvent().getEventType() == EventType.HARD_VERT );
        

    }
}
