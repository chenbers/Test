package com.inthinc.pro.backing;

import com.inthinc.pro.model.TimeFrame;
import org.joda.time.DateMidnight;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Date;

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

}
