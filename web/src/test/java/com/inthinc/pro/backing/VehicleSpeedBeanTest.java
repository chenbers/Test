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
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.event.SpeedingEvent;
import com.inthinc.pro.util.MessageUtil;

public class VehicleSpeedBeanTest extends BaseBeanTest
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
        // just test the bean successfully creates all of the required pies
        
        // team level login
        loginUser("custom101");
        
        // get the bean from the applicationContext (initialized by Spring injection)
        VehicleSpeedBean vehicleSpeedBean = (VehicleSpeedBean)applicationContext.getBean("vehicleSpeedBean");
        NavigationBean nav = (NavigationBean)applicationContext.getBean("navigationBean");
        LocaleBean localeBean = new LocaleBean();
        localeBean.getLocale();
        
        
        Vehicle v = new Vehicle();
        v.setDriverID(101);
        v.setVehicleID(1111);
        v.setGroupID(0);

        vehicleSpeedBean.setVehicle(v);
        
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
        
        vehicleSpeedBean.setScoreMap(scoreMap);
        vehicleSpeedBean.setStyleMap(styleMap);
        
        Integer score = vehicleSpeedBean.getScoreMap().get( ScoreType.SCORE_SPEEDING_21_30.toString());        
        assertEquals( score.toString(), "8");
        assertEquals( vehicleSpeedBean.getStyleMap().get( ScoreType.SCORE_SPEEDING_21_30.toString() ) , "score_med_1" );
        
        score = vehicleSpeedBean.getScoreMap().get( ScoreType.SCORE_SPEEDING_65_80.toString()); 
        assertEquals( score.toString(), "48");
        assertEquals( vehicleSpeedBean.getStyleMap().get( ScoreType.SCORE_SPEEDING_65_80.toString() ) , "score_med_5" );
       
        // Test Event Sorting
        List<EventReportItem> speedingEvents = new ArrayList<EventReportItem>();
        
        SpeedingEvent se = new SpeedingEvent();
        se.setSpeedLimit(45);
        se.setTime(new Date());
        
        DateFormat dateFormatter = new SimpleDateFormat(MessageUtil.getMessageString("dateTimeFormat"),LocaleBean.getCurrentLocale());
        EventReportItem eri = new EventReportItem(se, TimeZone.getTimeZone("MST"),MeasurementType.ENGLISH, dateFormatter);
        speedingEvents.add(eri);
        
        vehicleSpeedBean.setEvents(speedingEvents);
        vehicleSpeedBean.setSelectedBreakdown("FOURTYONE");
        assertTrue(vehicleSpeedBean.getEvents().size() > 0);

    }
}
