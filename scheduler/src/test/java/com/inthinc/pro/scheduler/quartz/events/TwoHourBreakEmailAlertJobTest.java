package com.inthinc.pro.scheduler.quartz.events;

import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.scheduler.quartz.EmailAlertJobTest;

import java.util.Arrays;

/**
 * Two hour break test.
 */
public class TwoHourBreakEmailAlertJobTest extends EmailAlertJobTest {
    private final String alertName = "Test two hour break email";


    @Override
    public String getExpectedMessage() {
        return "Driver Test driver name has a 2-hour break violation in vehicle Test vehicle name near location Test location\n" +
                "\n" +
                "| GROUP: Test group name\n" +
                "| SEVERITY: Critical\n" +
                "| DATE: 2015-01-01 10:00:00\n" +
                "| DRIVER ID:  testemp (1)\n" +
                "| DRIVER NAME: testboy\n" +
                "| VEHICLE:  testcar (1) 2015 test make test model\n" +
                "| EVENT TYPE: 2-HOUR BREAK\n" +
                "| EVENT DETAIL: Daily max driving\n" +
                "| LATITUDE: 100\n" +
                "| LONGIDUTE: 200\n" +
                "| ADDRESS: Tuktoyaktuk\n" +
                "| ODOMETER: 100 KM\n" +
                "| SPEED: 10 KPH KPH\n" +
                "| TOTAL DRIVING TIME: param1\n" +
                "| TOTAL STOP TIME: param2\n" +
                "| EXPECTED STOP DURATION: param3\n" +
                "| FIRST DRIVING TIME: param4\n" +
                "| LAST DRIVING TIME, 1ST TRIP: param5\n" +
                "| LAST DRIVING TIME, LAST TRIP: param6\n" +
                "| VIOLATION START TIME: param7\n" +
                "\n" +
                "You received this email because you are registered for Red Flag Alerts on inthinc.com.\n";
    }

    @Override
    public String getExpectedTitle() {
        return "ALERT: " + alertName;
    }

    @Override
    public void setAlertMessageType(AlertMessageBuilder alertMessageBuilder) {
        alertMessageBuilder.setAlertMessageType(AlertMessageType.ALERT_TYPE_TWO_HOURS_BREAK);
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
                        "event detail line 1 ignored", // event detail line 1
                        "event detail line 2 ignored", // event detail line 2
                        "param1", // other params
                        "param2",
                        "param3",
                        "param4",
                        "param5",
                        "param6",
                        "param7",
                        "param8"
                )
        );
    }

    @Override
    public void setParamList(AlertMessageBuilder alertMessageBuilder) {
        alertMessageBuilder.setParamterList(Arrays.asList("Test driver name", "Test vehicle name", "Test location"));
    }
}
