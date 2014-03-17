package com.inthinc.pro.backing;

import com.inthinc.pro.backing.ui.EventReportItem;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.map.GoogleAddressLookup;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.event.SeatBeltEvent;
import com.inthinc.pro.util.MessageUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Person;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DriverSeatBeltBeanTest extends BaseBeanTest
{
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @Test
    public void beanTest()
    {
        // just test the bean successfully creates all of the required pies
        
        // team level login
        loginUser("custom101");
        
        // get the bean from the applicationContext (initialized by Spring injection)
        DriverSeatBeltBean driverSeatBeltBean = (DriverSeatBeltBean)applicationContext.getBean("driverSeatBeltBean");
        LocaleBean lb = (LocaleBean)applicationContext.getBean("localeBean");
        Locale locale = new Locale("en", "US");


        Person p = new Person();
        p.setFirst("John");
        p.setLast("Doe");
        p.setLocale(locale);
        
        Driver d = new Driver();
        d.setDriverID(999994999);
        d.setPersonID(999994999);
        d.setGroupID(999994999);

        d.setPerson(p);

        lb.setLocale(locale);

        driverSeatBeltBean.setDriver(d);
        
        // make sure the spring injection worked
        assertNotNull(driverSeatBeltBean.getSeatBeltScore());
        assertNotNull(driverSeatBeltBean.getSeatBeltScoreStyle());
        assertNotNull(driverSeatBeltBean.getSeatBeltScoreHistoryOverall());

        //creating actual report for reportBuild with manually added scores
        List<EventReportItem> seatBeltEvents = new ArrayList<EventReportItem>();
        SeatBeltEvent sbe = new SeatBeltEvent();
        sbe.setTime(new Date());
        sbe.setLatitude(32.96453094482422);
        sbe.setLongitude(-117.12944793701172);


        DateFormat dateFormatter = new SimpleDateFormat(MessageUtil.getMessageString("dateTimeFormat"),LocaleBean.getCurrentLocale());
        EventReportItem eri = new EventReportItem(sbe, TimeZone.getTimeZone("MST"), MeasurementType.ENGLISH, dateFormatter);

        seatBeltEvents.add(eri);

        AddressLookup addressLookup  = new GoogleAddressLookup();
        addressLookup.setLocale(locale);
        driverSeatBeltBean.setReportAddressLookupBean(addressLookup);
        driverSeatBeltBean.setDisabledGoogleMapsInReportsAddressLookupBean(addressLookup);

        driverSeatBeltBean.setSeatBeltScore(40);
        driverSeatBeltBean.setSeatBeltEvents(seatBeltEvents);
        driverSeatBeltBean.setSeatBeltScoreHistoryOverall("test");

        assertNotNull(driverSeatBeltBean.buildReport());
        assertEquals(4.0, driverSeatBeltBean.buildReport().getPramMap().get("OVERALL_SCORE"));
        assertEquals(1, driverSeatBeltBean.buildReport().getPramMap().get("RECORD_COUNT"));
        
    }
}
