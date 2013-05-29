package it.util.hos;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.model.RuleViolationTypes;
import com.inthinc.hos.util.DateUtil;

// XML that is in  the same format as the xml for HOS firmware testcases
public class TestCaseXML {

    private static Logger logger = LoggerFactory.getLogger(TestCaseXML.class);
    List<TestCase> testCases = new ArrayList<TestCase>();

    private static SimpleDateFormat xmlDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    private static SimpleDateFormat dataDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    long elapsedSeconds;

    private static HashMap<String, HOSStatus> statusMap = new HashMap<String, HOSStatus>();
    static {
        statusMap.put("driving", HOSStatus.DRIVING);
        statusMap.put("waiting", HOSStatus.OFF_DUTY_AT_WELL);
        statusMap.put("sleeper", HOSStatus.SLEEPER);
        statusMap.put("off_duty", HOSStatus.OFF_DUTY);
        statusMap.put("on_duty", HOSStatus.ON_DUTY);
        statusMap.put("occupant_on_duty", HOSStatus.ON_DUTY_OCCUPANT);
        statusMap.put("occupant_travel_time", HOSStatus.TRAVELTIME_OCCUPANT);
        statusMap.put("occupant_off_duty", HOSStatus.OFF_DUTY_OCCUPANT);
        statusMap.put("deferral", HOSStatus.HOS_DERERRAL);
        statusMap.put("hos_npor", HOSStatus.HOS_NEAREST_PLACE_OF_REST);
        statusMap.put("hos_adverse_driving_conditions", HOSStatus.HOS_ADVERSE_DRIVING_CONDITIONS);
        statusMap.put("safety_check", HOSStatus.HOS_SAFETY_CHECK);
        statusMap.put("secure_load", HOSStatus.HOS_SECURE_LOAD);

        // new
        statusMap.put("personal_use", HOSStatus.HOS_PERSONALTIME);
        statusMap.put("fuel_stop", HOSStatus.FUEL_STOP);
        statusMap.put("mid_trip_inspection", HOSStatus.HOS_MIDTRIP_INSPECTION);
        statusMap.put("secure_load_checked", HOSStatus.HOS_SECURE_LOAD_CHECK);

        statusMap.put("timestamp", HOSStatus.TIMESTAMP);
        statusMap.put("boundary_change", HOSStatus.BOUNDARY_CHANGE);
        statusMap.put("newdriver", HOSStatus.NEWDRIVER);
        statusMap.put("ruleset", HOSStatus.RULESET);
        statusMap.put("alt_sleep", HOSStatus.HOS_ALTERNATE_SLEEPING);
        statusMap.put("propcarry_14hr", HOSStatus.HOS_PROP_CARRY_14HR);
    }

    private static HashMap<String, RuleSetType> ruleSetMap = new HashMap<String, RuleSetType>();
    static {
        ruleSetMap.put("us", RuleSetType.US);
        ruleSetMap.put("us_oil", RuleSetType.US_OIL);
        ruleSetMap.put("canada", RuleSetType.CANADA);
        ruleSetMap.put("canada_north", RuleSetType.CANADA_60_DEGREES);
        ruleSetMap.put("us_home_office", RuleSetType.US_HOME_OFFICE);
        ruleSetMap.put("canada_home_office", RuleSetType.CANADA_HOME_OFFICE);
        ruleSetMap.put("canada_oil", RuleSetType.CANADA);
        ruleSetMap.put("texas", RuleSetType.TEXAS);
        ruleSetMap.put("none", RuleSetType.NON_DOT);
        ruleSetMap.put("alberta", RuleSetType.CANADA_ALBERTA);
        ruleSetMap.put("canada_oil_07", RuleSetType.CANADA_2007_OIL);
        ruleSetMap.put("canada_cycle1", RuleSetType.CANADA_2007_CYCLE_1);
        ruleSetMap.put("canada_cycle2", RuleSetType.CANADA_2007_CYCLE_2);
        ruleSetMap.put("canada_north_cycle1", RuleSetType.CANADA_2007_60_DEGREES_CYCLE_1);
        ruleSetMap.put("canada_north_cycle2", RuleSetType.CANADA_2007_60_DEGREES_CYCLE_2);
        ruleSetMap.put("canada_north_oilperm", RuleSetType.CANADA_2007_60_DEGREES_OIL);
        ruleSetMap.put("us_7day", RuleSetType.US_7DAY);
        ruleSetMap.put("us_oil_7day", RuleSetType.US_OIL_7DAY);
        ruleSetMap.put("waterwell_7day", RuleSetType.US_WATERWELL_7DAY);
        ruleSetMap.put("waterwell_8day", RuleSetType.US_WATERWELL_8DAY);
        ruleSetMap.put("us_oil_no_wait_7day", RuleSetType.US_OIL_NO_WAIT_7DAY);
        ruleSetMap.put("us_oil_no_wait_8day", RuleSetType.US_OIL_NO_WAIT_8DAY);

        ruleSetMap.put("0", RuleSetType.US);
        ruleSetMap.put("1", RuleSetType.US_OIL);
        ruleSetMap.put("2", RuleSetType.CANADA);
        ruleSetMap.put("3", RuleSetType.CANADA_60_DEGREES);
        ruleSetMap.put("4", RuleSetType.US_HOME_OFFICE);
        ruleSetMap.put("5", RuleSetType.CANADA_HOME_OFFICE);
        ruleSetMap.put("6", RuleSetType.TEXAS);
        ruleSetMap.put("7", RuleSetType.CANADA);
        ruleSetMap.put("8", RuleSetType.NON_DOT);
        ruleSetMap.put("9", RuleSetType.CANADA_ALBERTA);
        ruleSetMap.put("17", RuleSetType.US_7DAY);
        ruleSetMap.put("18", RuleSetType.US_OIL_7DAY);
    }

    private static HashMap<String, RuleViolationTypes> violationTypeMap = new HashMap<String, RuleViolationTypes>();
    static {
        violationTypeMap.put("none", null);
        violationTypeMap.put("driving", RuleViolationTypes.DRIVING_HOUR);
        violationTypeMap.put("on_duty_day", RuleViolationTypes.ON_DUTY_HOUR);
        violationTypeMap.put("cycle", RuleViolationTypes.CUMMULATIVE_HOURS); // ??
        violationTypeMap.put("daily_off_duty", RuleViolationTypes.DAILY_OFF_DUTY);
        violationTypeMap.put("daily_driving", RuleViolationTypes.DAILY_DRIVING);
        violationTypeMap.put("daily_on_duty", RuleViolationTypes.DAILY_ON_DUTY); // ??
        violationTypeMap.put("elapsed", RuleViolationTypes.DAILY_ON_DUTY); // ??
        violationTypeMap.put("7_day", RuleViolationTypes.CUMMULATIVE_HOURS);
        violationTypeMap.put("8_day", RuleViolationTypes.CUMMULATIVE_HOURS);
        violationTypeMap.put("14_day", RuleViolationTypes.CUMMULATIVE_HOURS);
        violationTypeMap.put("rest_break", RuleViolationTypes.REST_BREAK);
    }

    int drivingCnt;

    public TestCaseXML(String filename, String currentDay, TimeZone timeZone) throws Exception {
//        URL url = Thread.currentThread().getContextClassLoader().getResource(filename);

        URL url = new URL("file", null, filename);
        InputStream stream = null;
        try {
            stream = url != null ? url.openStream() : null;
        } catch (IOException e) {
            e.printStackTrace();
            stream = null;
        }

//        xmlDateFormat.setTimeZone(new SimpleTimeZone(0, "UTC"));
//        dataDateFormat.setTimeZone(new SimpleTimeZone(0, "UTC"));
        xmlDateFormat.setTimeZone(timeZone);
        dataDateFormat.setTimeZone(timeZone);

        // read in the xml into a structure for the test to use
        Document xmlDoc = XMLUtils.parse(stream);

        NodeList nodes = xmlDoc.getElementsByTagName("testcase");

        for (int i = 0; i < nodes.getLength(); i++) {
            TestCase testCase = new TestCase();
            testCases.add(testCase);

            Node tcNode = nodes.item(i);
            NodeList childNodes = tcNode.getChildNodes();
            for (int c = 0; c < childNodes.getLength(); c++) {
                Node childNode = childNodes.item(c);
                String childNodeName = childNode.getNodeName();
                if (childNodeName.equalsIgnoreCase("description")) {
                    testCase.setDescription(childNode.getChildNodes().item(0).getNodeValue());
                    logger.debug(testCase.getDescription());
                } else if (childNodeName.equalsIgnoreCase("driver_log")) {
                    drivingCnt = 0;
                    String currentTimeStr = getCurrentTimeStr(childNode, currentDay == null ? "2013-04-01 " : currentDay.trim() + " ");
                    testCase.setCurrentTime(dataDateFormat.parse(currentTimeStr));
System.out.println("currentTime: " + testCase.getCurrentTime());                    
                    testCase.setDriverLog(parseDriverLog(childNode, currentTimeStr, testCase.getCurrentTime()));
                    testCase.setDrivingCnt(drivingCnt);
                    testCase.setHomeLat(getHomeLatLng(childNode, "home_latitude"));
                    testCase.setHomeLng(getHomeLatLng(childNode, "home_longitude"));
                    NamedNodeMap attributes = childNode.getAttributes();
                    Node occupantNode = attributes.getNamedItem("occupant");
                    if (occupantNode != null) {
                        boolean isSingleDriver = !new Boolean(occupantNode.getNodeValue());
                        testCase.setSingleDriver(isSingleDriver);
                    } else {
                        testCase.setSingleDriver(true);
                    }
                    Node idNode = attributes.getNamedItem("id");
                    testCase.setId(idNode.getNodeValue());
                } else if (childNodeName.equalsIgnoreCase("violations")) {
                    ArrayList<TestCaseResults> results = new ArrayList<TestCaseResults>();
                    testCase.setResults(results);
                    NodeList resultsNodes = childNode.getChildNodes();
                    for (int r = 0; r < resultsNodes.getLength(); r++) {
                        Node oneResultNode = resultsNodes.item(r);
                        if (oneResultNode.getNodeName().equalsIgnoreCase("violation")) {
                            results.add(parseResults(oneResultNode));
                        }
                    }
                }
            }
        }
    }

    private Double getHomeLatLng(Node childNode, String fieldName) {
        NamedNodeMap attributes = childNode.getAttributes();

        Node node = attributes.getNamedItem(fieldName);
        if (node != null) {
            return new Double(node.getNodeValue().trim());
        } 
		return null;
	}


	private TestCaseResults parseResults(Node childNode) {

        TestCaseResults results = new TestCaseResults();

        NamedNodeMap attributes = childNode.getAttributes();
        Node node = attributes.getNamedItem("ruleset");
        // logger.debug(node.getNodeValue());
        results.setDotType(ruleSetMap.get(node.getNodeValue()));

        node = attributes.getNamedItem("expected_violation");
        // logger.debug(node.getNodeValue());
        if (node == null) {
            results.setExpectedViolationType(violationTypeMap.get("none"));
        } else {
            results.setExpectedViolationType(violationTypeMap.get(node.getNodeValue()));
        }

        node = attributes.getNamedItem("on_duty_remaining");
        // logger.debug(node.getNodeValue());
        results.setExpectedOnDutyRemaining(new Integer(node.getNodeValue()).intValue());

        node = attributes.getNamedItem("driving_remaining");
        // logger.debug(node.getNodeValue());
        results.setExpectedDrivingRemaining(new Integer(node.getNodeValue()).intValue());

        node = attributes.getNamedItem("expected_propcarry14hr");
        // logger.debug(node.getNodeValue());
        if (node != null && node.getNodeValue() != null) {
        	String value = node.getNodeValue().trim();
        	results.setPropcarry14hrValid(value.equalsIgnoreCase("yes"));
        }

        return results;
    }

    private String getCurrentTimeStr(Node childNode, String timeStr) {

        NamedNodeMap attributes = childNode.getAttributes();

//        String timeStr = "2013-06-30 ";
        if (timeStr == null) {
            Node currentDayNode = attributes.getNamedItem("current_date");
            if (currentDayNode != null) {
                timeStr = currentDayNode.getNodeValue().trim() + " ";
            } 
        }
        Node startTimeNode = attributes.getNamedItem("current_time");
        if (startTimeNode != null) {
            timeStr += startTimeNode.getNodeValue().trim();
        } else {
            timeStr += "00:00:00";
        }
        return timeStr;
    }

    private List<HOSRec> parseDriverLog(Node childNode, String timeStr, Date currentTime) throws ParseException {

        List<HOSRec> driverLogList = new ArrayList<HOSRec>();
        elapsedSeconds = 0;
        NodeList driverLogNodes = childNode.getChildNodes();
        for (int c = 0; c < driverLogNodes.getLength(); c++) {
            Node driverLogNode = driverLogNodes.item(c);
            String childNodeName = driverLogNode.getNodeName();
            if (childNodeName.equalsIgnoreCase("hos_state")) {
                HOSRec hosLog = createHosLogRecordFromXML(driverLogNode);
                if (hosLog != null) {
                	driverLogList.add(hosLog);
                }
            }
        }

        driverLogList =  fixTimeStamps(driverLogList, timeStr);
        driverLogList.add(0, new HOSRec("end", HOSStatus.OFF_DUTY, 0, currentTime ));
        return driverLogList;
    }

    private List<HOSRec> fixTimeStamps(List<HOSRec> driverLogList, String timeStr) throws ParseException {
    	List<HOSRec> logList = new ArrayList<HOSRec>();
        for (int i = 0; i < driverLogList.size(); i++) {
            HOSRec log = driverLogList.get(i);
            Date logTime = getLogTime(timeStr);
            elapsedSeconds -= log.getTotalRealMinutes();
            log.setTotalRealMinutes(DateUtil.secondsToMinutes(log.getTotalRealMinutes()));
            log.setLogTimeDate(logTime);
            logList.add(0, log);
        }

        return logList;
    }


    protected HOSRec createHosLogRecordFromXML( Node driverLogNode) throws ParseException {
        NamedNodeMap attributes = driverLogNode.getAttributes();
        Node stateNode = attributes.getNamedItem("state");
        Node hoursNode = attributes.getNamedItem("hours");
        Node minutesNode = attributes.getNamedItem("minutes");
        Node secondsNode = attributes.getNamedItem("seconds");
        Node latNode = attributes.getNamedItem("latitude");
        Node lngNode = attributes.getNamedItem("longitude");

        long seconds = (((hoursNode == null) ? 0 : (int) (new Float(hoursNode.getNodeValue().trim()).floatValue() * 60))
                + ((minutesNode == null) ? 0 : new Integer(minutesNode.getNodeValue().trim()))) * 60;
        if (secondsNode != null) {
        	seconds += new Integer(secondsNode.getNodeValue().trim());
        }
        elapsedSeconds += seconds;
        String id = "";
        if (stateNode.getNodeValue().equalsIgnoreCase("driving"))
            id += drivingCnt++;

        // minutes field temporary holds elapsed seconds until we can fix up the dates
        HOSRec hosLog = new HOSRec(id, statusMap.get(stateNode.getNodeValue()), seconds, null); 
        
        if (latNode != null) {
        	hosLog.setLat(new Double(latNode.getNodeValue().trim()));
        }
        if (lngNode != null) {
        	hosLog.setLng(new Double(lngNode.getNodeValue().trim()));
        }
        
        return hosLog;
    }

    private Date getLogTime(String startTime) throws ParseException {

        Date date = dataDateFormat.parse(startTime);
        Date newDate = new Date(date.getTime() - elapsedSeconds * 1000l);
        // return dataDateFormat.format(newDate);
        return newDate;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }

}
