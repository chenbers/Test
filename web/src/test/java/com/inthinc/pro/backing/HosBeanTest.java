package com.inthinc.pro.backing;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import com.inthinc.pro.backing.HosBean.HosLogView;

public class HosBeanTest {
    
    private static final DateTimeZone testTimeZone = DateTimeZone.forID("US/Mountain");
    private static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withZone(testTimeZone);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy hh:mm:ss a (z)").withZone(testTimeZone);



    @Test
    public void testItemTime() {
        
        HosBean hosBean = new HosBean();
        HosLogView hosLogView = hosBean.new HosLogView();
        hosLogView.setTimeZone(testTimeZone.toTimeZone());
        DateTime daylightSavingsEndDay = dateFormatter.parseDateTime("11/03/2013");
        hosLogView.setLogTime(daylightSavingsEndDay.toDate());

        hosLogView.setTimeInSec(0);
        assertEquals("Expect start of day", "11/03/2013 12:00:00 AM (MDT)", dateTimeFormatter.print(new DateTime(hosLogView.getLogTime())));
        
        hosLogView.setTimeInSec(3600);
        assertEquals("Expect 1 am", "11/03/2013 01:00:00 AM (MDT)", dateTimeFormatter.print(new DateTime(hosLogView.getLogTime())));
        
        hosLogView.setTimeInSec(7140);
        assertEquals("Expect 01:59 am", "11/03/2013 01:59:00 AM (MDT)", dateTimeFormatter.print(new DateTime(hosLogView.getLogTime())));

        hosLogView.setTimeInSec(7200);
        assertEquals("Expect 2 am", "11/03/2013 02:00:00 AM (MST)", dateTimeFormatter.print(new DateTime(hosLogView.getLogTime())));

        hosLogView.setTimeInSec(10800);
        assertEquals("Expect 3 am", "11/03/2013 03:00:00 AM (MST)", dateTimeFormatter.print(new DateTime(hosLogView.getLogTime())));

        hosLogView.setTimeInSec(10799);
        assertEquals("Expect 2:59:59 am", "11/03/2013 02:59:59 AM (MST)", dateTimeFormatter.print(new DateTime(hosLogView.getLogTime())));
    }

}
