package com.inthinc.pro.backing;

import com.inthinc.device.emulation.enums.Locales;
import com.inthinc.device.objects.AutomationDeviceEvents;
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
import com.inthinc.pro.model.Vehicle;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class VehicleSeatBeltBeanTest extends BaseBeanTest
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
        VehicleSeatBeltBean vehicleSeatBeltBean = (VehicleSeatBeltBean)applicationContext.getBean("vehicleSeatBeltBean");
        NavigationBean nav = (NavigationBean)applicationContext.getBean("navigationBean");
        LocaleBean lb = (LocaleBean)applicationContext.getBean("localeBean");
        PerformanceDataBean pdb = (PerformanceDataBean)applicationContext.getBean("performanceDataBean");
        Locale locale = new Locale("en", "US");
        Vehicle v = new Vehicle();
        v.setDriverID(999994999);
        v.setVehicleID(999994999);
        v.setGroupID(999994999);


        vehicleSeatBeltBean.setVehicle(v);

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
//        pdb.set

        vehicleSeatBeltBean.setDriver(d);



        // make sure the spring injection worked
        assertNotNull(vehicleSeatBeltBean.getSeatBeltScore());
        assertNotNull(vehicleSeatBeltBean.getSeatBeltScoreStyle());
        assertNotNull(vehicleSeatBeltBean.getSeatBeltScoreHistoryOverall());

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
        vehicleSeatBeltBean.setReportAddressLookupBean(addressLookup);
        vehicleSeatBeltBean.setDisabledGoogleMapsInReportsAddressLookupBean(addressLookup);

        vehicleSeatBeltBean.setSeatBeltScore(40);
        vehicleSeatBeltBean.setSeatBeltEvents(seatBeltEvents);
        vehicleSeatBeltBean.setSeatBeltScoreHistoryOverall("test");

        assertNotNull(vehicleSeatBeltBean.buildReport());
        assertEquals(4.0, vehicleSeatBeltBean.buildReport().getPramMap().get("OVERALL_SCORE"));
        assertEquals(1, vehicleSeatBeltBean.buildReport().getPramMap().get("RECORD_COUNT"));

    }

}
