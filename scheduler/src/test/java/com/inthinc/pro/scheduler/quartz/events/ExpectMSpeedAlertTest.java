package com.inthinc.pro.scheduler.quartz.events;

import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.scheduler.quartz.EmailAlertJobTest;

import java.util.Arrays;
import java.util.Locale;

/**
 * Two hour break test.
 */
public class ExpectMSpeedAlertTest extends EmailAlertJobTest {
    private final String alertName = "Test miles vs kms";


    @Override
    public String getExpectedMessage() {
        return "bparam1 bparam2 (bparam4) speeding (bparam5 in bparam6 zone) near bparam7.\n" +
                "bparam3\n" +
                "\n" +
                "| GROUP: Test group name\n" +
                "| SEVERITY: Critical\n" +
                "| DATE: 2015-01-01 10:00:00\n" +
                "| DRIVER ID:  testemp (1)\n" +
                "| DRIVER NAME: testboy\n" +
                "| VEHICLE:  testcar (1) 2015 test make test model\n" +
                "| EVENT TYPE: SPEEDING\n" +
                "| EVENT DETAIL: event detail line 1 ignored in event detail line 2 ignored zone\n" +
                "| LATITUDE: 100\n" +
                "| LONGIDUTE: 200\n" +
                "| ADDRESS: Tuktoyaktuk\n" +
                "| ODOMETER: 100 MI\n" +
                "| SPEED: 16 MPH\n" +
                "\n" +
                "You received this email because you are registered for Red Flag Alerts on inthinc.com.\n";
    }

    @Override
    public String getExpectedTitle() {
        return "ALERT: " + alertName;
    }

    @Override
    public void setAlertMessageType(AlertMessageBuilder alertMessageBuilder) {
        alertMessageBuilder.setAlertMessageType(AlertMessageType.ALERT_TYPE_SPEEDING);
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
                        "16", // speed
                        "0", // measurement type,
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
        alertMessageBuilder.setParamterList(Arrays.asList("bparam1", "bparam2", "bparam3", "bparam4", "bparam5", "bparam6", "bparam7"));
    }

    @Override
    public void setAlertLocale(AlertMessageBuilder alertMessageBuilder) {
        alertMessageBuilder.setLocale(new Locale("en_US"));
    }
}
