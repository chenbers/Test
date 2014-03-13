package com.inthinc.pro.backing;

import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.event.Event;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TripsBeanTest extends BaseBeanTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception {}

    @Test
     public void validLatLng() {

        // team level login
        loginUser("custom101");

        // get the bean from the applicationContext (initialized by Spring injection)
        TripsBean tripsBean  = (TripsBean)applicationContext.getBean("driverTripsBean");

        assertTrue("null LatLng is invalid", !tripsBean.isValidLatLng(null));
        assertTrue("0,0 LatLng is invalid", !tripsBean.isValidLatLng(new LatLng(0.0, 0.0)));
        assertTrue("close to 0,0 LatLng is invalid", !tripsBean.isValidLatLng(new LatLng(0.002, 0.002)));
        assertTrue("negative close to 0,0 LatLng is invalid", !tripsBean.isValidLatLng(new LatLng(-0.002, -0.002)));
        assertTrue("over maxLatLng is invalid", !tripsBean.isValidLatLng(new LatLng(LatLng.MAX_LAT+1.0, LatLng.MAX_LNG+1)));
        assertTrue("under minLatLng is invalid", !tripsBean.isValidLatLng(new LatLng(LatLng.MIN_LAT-1.0, LatLng.MIN_LNG-1)));

    }

    @Test
    public void timeFrameString() {

        // team level login
        loginUser("custom101");

        //DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss z");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // get the bean from the applicationContext (initialized by Spring injection)
        TripsBean tripsBean  = (TripsBean)applicationContext.getBean("driverTripsBean");
        assertEquals(tripsBean.getTimeFrameString().equals(""), true);

        // set TimeFrameString TODAY
        tripsBean.setTimeFrameString(TimeFrame.TODAY.toString());
        assertEquals(tripsBean.getTimeFrameString().equals("TODAY"), true);
        assertEquals(sdf.format(tripsBean.getStartDate()).equals(sdf.format(TimeFrame.TODAY.getInterval().getStart().toDate())), true);
        assertEquals(sdf.format(tripsBean.getEndDate()).equals(sdf.format(TimeFrame.TODAY.getInterval().getEnd().toDate())), true);

        tripsBean.setTimeFrameString(TimeFrame.ONE_DAY_AGO.toString());
        assertEquals(tripsBean.getTimeFrameString().equals("ONE_DAY_AGO"), true);
        assertEquals(sdf.format(tripsBean.getStartDate()).equals(sdf.format(TimeFrame.TODAY.getInterval().getStart().toDate())), true);
        assertEquals(sdf.format(tripsBean.getEndDate()).equals(sdf.format(TimeFrame.TODAY.getInterval().getEnd().toDate())), true);

        tripsBean.setTimeFrameString(TimeFrame.TWO_DAYS_AGO.toString());
        assertEquals(tripsBean.getTimeFrameString().equals("TWO_DAYS_AGO"), true);
        assertEquals(sdf.format(tripsBean.getStartDate()).equals(sdf.format(TimeFrame.TODAY.getInterval().getStart().toDate())), true);
        assertEquals(sdf.format(tripsBean.getEndDate()).equals(sdf.format(TimeFrame.TODAY.getInterval().getEnd().toDate())), true);

        tripsBean.setTimeFrameString(TimeFrame.THREE_DAYS_AGO.toString());
        assertEquals(tripsBean.getTimeFrameString().equals("THREE_DAYS_AGO"), true);
        assertEquals(sdf.format(tripsBean.getStartDate()).equals(sdf.format(TimeFrame.TODAY.getInterval().getStart().toDate())), true);
        assertEquals(sdf.format(tripsBean.getEndDate()).equals(sdf.format(TimeFrame.TODAY.getInterval().getEnd().toDate())), true);

        tripsBean.setTimeFrameString(TimeFrame.FOUR_DAYS_AGO.toString());
        assertEquals(tripsBean.getTimeFrameString().equals("FOUR_DAYS_AGO"), true);
        assertEquals(sdf.format(tripsBean.getStartDate()).equals(sdf.format(TimeFrame.TODAY.getInterval().getStart().toDate())), true);
        assertEquals(sdf.format(tripsBean.getEndDate()).equals(sdf.format(TimeFrame.TODAY.getInterval().getEnd().toDate())), true);

        tripsBean.setTimeFrameString(TimeFrame.FIVE_DAYS_AGO.toString());
        assertEquals(tripsBean.getTimeFrameString().equals("FIVE_DAYS_AGO"), true);
        assertEquals(sdf.format(tripsBean.getStartDate()).equals(sdf.format(TimeFrame.TODAY.getInterval().getStart().toDate())), true);
        assertEquals(sdf.format(tripsBean.getEndDate()).equals(sdf.format(TimeFrame.TODAY.getInterval().getEnd().toDate())), true);

        tripsBean.setTimeFrameString(TimeFrame.SIX_DAYS_AGO.toString());
        assertEquals(tripsBean.getTimeFrameString().equals("SIX_DAYS_AGO"), true);
        assertEquals(sdf.format(tripsBean.getStartDate()).equals(sdf.format(TimeFrame.TODAY.getInterval().getStart().toDate())), true);
        assertEquals(sdf.format(tripsBean.getEndDate()).equals(sdf.format(TimeFrame.TODAY.getInterval().getEnd().toDate())), true);

        tripsBean.setTimeFrameString(TimeFrame.SEVEN_DAYS_AGO.toString());
        assertEquals(tripsBean.getTimeFrameString().equals("SEVEN_DAYS_AGO"), true);
        assertEquals(sdf.format(tripsBean.getStartDate()).equals(sdf.format(TimeFrame.TODAY.getInterval().getStart().toDate())), true);
        assertEquals(sdf.format(tripsBean.getEndDate()).equals(sdf.format(TimeFrame.TODAY.getInterval().getEnd().toDate())), true);

        tripsBean.setTimeFrameString(TimeFrame.WEEK.toString());
        assertEquals(tripsBean.getTimeFrameString().equals("WEEK"), true);
        assertEquals(sdf.format(tripsBean.getStartDate()).equals(sdf.format(TimeFrame.TODAY.getInterval().getStart().toDate())), true);
        assertEquals(sdf.format(tripsBean.getEndDate()).equals(sdf.format(TimeFrame.TODAY.getInterval().getEnd().toDate())), true);

    }

    @Test
    public void removeDuplicateEventsTest() {
        DateTime now = new DateTime();

        Event event1 = new Event();
        event1.setTime(now.toDate());
        event1.setLongitude(100.0);
        event1.setLatitude(200.0);
        event1.setNoteID(5l);

        Event event2 = new Event();
        event2.setTime(now.toDate());
        event2.setLongitude(150.0);
        event2.setLatitude(250.0);
        event2.setNoteID(2l);

        Event event3 = new Event();
        event3.setTime(now.minusDays(2).toDate());
        event3.setLongitude(231.0);
        event3.setLatitude(823.0);
        event3.setNoteID(3l);

        Event event4 = new Event();
        event4.setTime(now.toDate());
        event4.setLongitude(100.0);
        event4.setLatitude(200.0);
        event4.setNoteID(4l);

        Event event5 = new Event();
        event5.setTime(now.minusDays(1).toDate());
        event5.setLongitude(10021.0);
        event5.setLatitude(20021.0);
        event5.setNoteID(5l);

        Event event6 = new Event();
        event6.setTime(now.toDate());
        event6.setLongitude(100.0);
        event6.setLatitude(200.0);
        event6.setNoteID(6l);

        //Test equalsWithoutID method from Event class first
        assertTrue(event1.equalsWithoutID(event4));

        //verify with different latitude and longitude
        assertFalse(event1.equalsWithoutID(event2));

        //verify it does not take ID into account
        assertFalse(event1.equalsWithoutID(event5));

        List<Event> testList1 =new ArrayList <Event>();
        testList1.add(event1);
        testList1.add(event2);
        testList1.add(event3);
        testList1.add(event4);
        testList1.add(event5);
        testList1.add(event6);

        List<Event> testList2 =new ArrayList <Event>();
        testList2.add(event1);
        testList2.add(event4);
        testList2.add(event6);
        testList2.add(event2);
        testList2.add(event3);
        testList2.add(event5);

        List<Event> testList3 =new ArrayList <Event>();
        testList3.add(event2);
        testList3.add(event3);
        testList3.add(event5);
        testList3.add(event1);
        testList3.add(event4);
        testList3.add(event6);

        // team level login
        loginUser("custom101");

        // get the bean from the applicationContext (initialized by Spring injection)
        TripsBean tripsBean  = (TripsBean)applicationContext.getBean("driverTripsBean");

        testList1=tripsBean.removeDuplicateEvents(testList1);
        testList2=tripsBean.removeDuplicateEvents(testList2);
        testList3=tripsBean.removeDuplicateEvents(testList3);

        assertEquals(4,testList1.size());

        assertEquals(4,testList2.size());

        assertEquals(4,testList3.size());

    }

}
