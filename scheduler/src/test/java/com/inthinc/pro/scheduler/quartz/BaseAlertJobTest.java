package com.inthinc.pro.scheduler.quartz;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import mockit.Mocked;

import org.junit.Test;

import com.inthinc.pro.dao.AlertMessageDAO;
import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageType;

public class BaseAlertJobTest {
    
    @Mocked AlertMessageDAO alertMessageDAO;
   
    private BaseAlertJob baseAlertJob;

    @Test
    public void testGetSubject(){
        
       AlertMessageBuilder message = new AlertMessageBuilder();
       message.setAcknowledge(false);
       message.setAddress("jhoward@inthinc.com");
       message.setAlertID(0);
//       message.setAlertMessageType(AlertMessageType.ALERT_TYPE_HARD_ACCEL);
       message.setAlertMessageType(AlertMessageType.ALERT_TYPE_DVIR_PRE_TRIP_FAIL);
       message.setLocale(Locale.getDefault());
       message.setMessageID(1);
       message.setParamterList(null);
       baseAlertJob = new BaseAlertJob();
       @SuppressWarnings("unused")
       String subject = baseAlertJob.getSubject(message);
       assertEquals("message missing","tiwiPro Alert: Pre-trip inspection failed",subject);

       message.setAlertMessageType(AlertMessageType.ALERT_TYPE_DVIR_PRE_TRIP_PASS);
       subject = baseAlertJob.getSubject(message);
       assertEquals("message missing","tiwiPro Alert: Pre-trip inspection passed",subject);

       message.setAlertMessageType(AlertMessageType.ALERT_TYPE_DVIR_POST_TRIP_FAIL);
       subject = baseAlertJob.getSubject(message);
       assertEquals("message missing","tiwiPro Alert: Post-trip inspection failed",subject);
       message.setAlertMessageType(AlertMessageType.ALERT_TYPE_DVIR_POST_TRIP_PASS);
       subject = baseAlertJob.getSubject(message);
       assertEquals("message missing","tiwiPro Alert: Post-trip inspection passed",subject);
    }

}
