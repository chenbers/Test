package com.inthinc.pro.scheduler.quartz.events;

import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.scheduler.quartz.EmailAlertJobTest;

import java.util.Arrays;

/**
 * Two hour break test.
 */
public class DailyMaxDrivingLimitEmailAlertJobTest extends EmailAlertJobTest {
    private final String alertName = "Test two hour break email";


    @Override
    public String getExpectedMessage() {
        return "Driver Jacquie Howard has a daily max driving violation in vehicle vehicle1 near location Salt Lake City, Utah\n" +
                "\n" +
                "| GROUP: Test group name\n" +
                "| SEVERITY: Critical\n" +
                "| DATE: 2015-01-01 10:00:00\n" +
                "| DRIVER ID:  testemp (1)\n" +
                "| DRIVER NAME: testboy\n" +
                "| VEHICLE:  testcar (1) 2015 test make test model\n" +
                "| EVENT TYPE: Daily max driving\n" +
                "| EVENT DETAIL: Driver testboy has a daily max driving violation in vehicle testcar near location Tuktoyaktuk\n" +
                "| LATITUDE: 100\n" +
                "| LONGIDUTE: 200\n" +
                "| ADDRESS: Tuktoyaktuk\n" +
                "| ODOMETER: 100 KM\n" +
                "| SPEED: 10 KPH KPH\n" +
                "| TOTAL DRIVING TIME: 10\n" +
                "| TOTAL STOP TIME: null\n" +
                "| EXPECTED STOP DURATION: null\n" +
                "| FIRST DRIVING TIME: null\n" +
                "| LAST DRIVING TIME, 1ST TRIP: null\n" +
                "| LAST DRIVING TIME, LAST TRIP: 2015-01-01 10:00:00\n" +
                "| VIOLATION START TIME: 2015-01-01 10:00:00\n" +
                "\n" +
                "You received this email because you are registered for Red Flag Alerts on inthinc.com.\n";
    }

    @Override
    public String getExpectedTitle() {
        return "ALERT: " + alertName;
    }

    @Override
    public void setAlertMessageType(AlertMessageBuilder alertMessageBuilder) {
        alertMessageBuilder.setAlertMessageType(AlertMessageType.ALERT_TYPE_DAILY_MAX_DRIVING_LIMIT);
    }

    @Override
    public void setAlertName(AlertMessageBuilder alertMessageBuilder) {
        alertMessageBuilder.setAlertName(alertName);
    }

    @Override
    public void setEzParamList(AlertMessageBuilder alertMessageBuilder) {
        alertMessageBuilder.setEzParameterList(
                Arrays.asList("ignored", // alert name
                        "Test group name", //group name
                        "0", // category
                        "2015-01-01 10:00:00", // date
                        "testemp", // emp id,
                        "1", // driver id,
                        "testboy", // driver name
                        "testcar", // vehicle name
                        "1", // vehicle id
                        "2015", // year
                        "test make", // make
                        "test model", // model
                        "100", // lat
                        "200", // lng
                        "Tuktoyaktuk", //address
                        "100", // odometer,
                        "10 KPH", // speed
                        "1", // measurement type,
                        "10", // total driving time
                        "null", //total stop time, always null
                        "null", // expected stop duration, always null
                        "null", // first driving time, always null                    
                        "null", // last driving time, 1st trip
                        "2015-01-01 10:00:00", // last driving time, last trip
                        "2015-01-01 10:00:00" //violation start time
                )
        );
    }

    @Override
    public void setParamList(AlertMessageBuilder alertMessageBuilder) {
        alertMessageBuilder.setParamterList(Arrays.asList("Aug 17, 2012 8:10 PM (GMT)", "Jacquie Howard", "1", "vehicle1", "Salt Lake City, Utah", "10", "null", "null", "null", "null",
                        "2015-01-01 10:00:00", "2015-01-01 10:00:00"));
    }
}
