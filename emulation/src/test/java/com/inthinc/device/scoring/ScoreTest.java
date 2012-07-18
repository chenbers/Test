package com.inthinc.device.scoring;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.inthinc.device.cassandra.CassandraDB;
import com.inthinc.device.devices.TiwiProDevice;
import com.inthinc.device.emulation.enums.ScoringTypes;
import com.inthinc.device.emulation.enums.UnitType;
import com.inthinc.device.emulation.interfaces.SiloService;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.GoogleTrips;
import com.inthinc.device.hessian.tcp.AutomationHessianFactory;
import com.inthinc.device.objects.TripDriver;
import com.inthinc.device.scoring.ScoringFactory;
import com.inthinc.pro.automation.enums.AutoSilos;
import com.inthinc.pro.automation.enums.ProductType;
import com.inthinc.pro.automation.enums.WebDateFormat;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.resources.FileRW;
import com.inthinc.pro.automation.utils.AutomationThread;

public class ScoreTest {

    private static final String fileName = "scoring_imeis.dat";

    private ConcurrentHashMap<String, Map<String, String>> imeis;
    private SiloService proxy;

    private static final AutoSilos server = AutoSilos.QA;

    private static final String first = "40.709922,-111.993513";
    private static final String second = "40.707547,-111.801918";
    private static final String third = "40.58153,-111.85038";

    private AutomationCalendar aggTime;

	private ScoresFromCassandra cs;

	private CassandraDB cassandraDB;

    public ScoreTest() {
        proxy = new AutomationHessianFactory().getPortalProxy(server);
        imeis = new ConcurrentHashMap<String, Map<String, String>>();
        
        cassandraDB = new CassandraDB("Iridium Archive", "note", "10.0.35.40:9160", 10, false);

        cs = new ScoresFromCassandra();
        
        List<String> temp = new FileRW().read(ScoreTest.class, fileName);
        
        

        for (String line : temp) {
            String[] value = line.split(":::");
            Map<String, String> elements = new HashMap<String, String>();
            elements.put("date", value[1]);
            elements.put("vid", value[2]);
            elements.put("did", value[3]);

            imeis.put(value[0], elements);
        }
        
    }

    private void writeValues() {

        try {
            FileWriter fstream = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(fstream);
            for (Map.Entry<String, Map<String, String>> entry : imeis.entrySet()) {
                String line = entry.getKey() + ":::" + entry.getValue().get("date") + ":::" + entry.getValue().get("vid") + ":::" + entry.getValue().get("did");
                out.write(line);
                out.newLine();
            }
            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private AutomationCalendar getLastNoteTime(String imei) {
        return new AutomationCalendar(imeis.get(imei).get("date"), WebDateFormat.NOTE_PRECISE_TIME);
    }

    private Map<UnitType, Map<String, Double>> getSevenDayScores(String imei) {
        ScoringFactory sf = new ScoringFactory(server);
        AutomationCalendar start = aggTime.copy().zeroTimeOfDay().addToDay(-7);
        Map<UnitType, Map<String, Double>> value = new HashMap<UnitType, Map<String, Double>>();
        sf.scoreMe(UnitType.DRIVER, Integer.parseInt(imeis.get(imei).get("did")), start, aggTime, ProductType.TIWIPRO_R74);
        value.put(UnitType.DRIVER, sf.getOverallScores());
        sf.scoreMe(UnitType.VEHICLE, Integer.parseInt(imeis.get(imei).get("vid")), start, aggTime, ProductType.TIWIPRO_R74);
        value.put(UnitType.VEHICLE, sf.getOverallScores());
        return value;
    }

    private void sendNotes() {
        GoogleTrips gt = new GoogleTrips();
        List<GeoPoint> points = gt.getTrip(first, second);
        points.addAll(gt.getTrip(second, third));
        points.addAll(gt.getTrip(third, first));
        AutomationCalendar timeKeeper = null;
        for (Map.Entry<String, Map<String, String>> entry : imeis.entrySet()) {
            TiwiProDevice tiwi = new TiwiProDevice(entry.getKey());
            TripDriver drive = new TripDriver(tiwi);
            tiwi.getTripTracker().setPoints(points);
            tiwi.getState().getTime().setDate(getLastNoteTime(entry.getKey()));

            if (timeKeeper == null) {
                timeKeeper = tiwi.getState().getTime();
            }

            drive.start();
            while (Thread.activeCount() > 20) {
                AutomationThread.pause(250l);
            }
        }
        while (Thread.activeCount() > 2) {
            AutomationThread.pause(1);
        }
        String time = timeKeeper.addToMinutes(20).toString(WebDateFormat.NOTE_PRECISE_TIME);
        for (Map.Entry<String, Map<String, String>> entry : imeis.entrySet()) {
            entry.getValue().put("date", time);
        }
        writeValues();
    }

    public void runScoring() {
//        sendNotes();
        
        AutomationCalendar start = new AutomationCalendar();
        AutomationCalendar agg = start.copy();
//        agg.changeMinutesTo(0).changeMillisecondsTo(0).changeSecondsTo(0).addToHours(1);
//        if (agg.compareTo(start) < 2) {
//            agg.addToHours(1);
//        }
//        AutomationThread.pause(agg.addToMinutes(5).compareTo(start));
        aggTime = agg.copy().changeMinutesTo(0).changeSecondsTo(0).changeMillisecondsTo(0);
        for (Map.Entry<String, Map<String, String>> entry : imeis.entrySet()) {
        	Map<UnitType, Map<String, Double>> ourScore = getSevenDayScores(entry.getKey());
            compareToPortal(entry.getKey(), ourScore);
            cassandraScores(entry.getKey(), ourScore, start);
        }
        cassandraDB.shutdown();
    }
    
    private void cassandraScores(String key, Map<UnitType, Map<String, Double>> scores, AutomationCalendar endTime){
    	AutomationCalendar startDate = endTime.addToDay(-7).zeroTimeOfDay();
    	int did = Integer.parseInt(imeis.get(key).get("did"));
    	int vid = Integer.parseInt(imeis.get(key).get("vid"));
    	UnitType type = UnitType.DRIVER;
    	Map<String, Object> cass;
    	
        cass = cs.getScores(type, ScoringTypes.DAYS, did, startDate, endTime);
        if (compareScores(scores.get(type), cass)){
            Log.info("Driver Cassandra Matches for IMEI=%s", key);
        } else {
            Log.info("Driver Cassandra doesn't match for IMEI=%s", key);
        }
        
        cass = cs.getScores(type, ScoringTypes.DAYS, vid, startDate, new AutomationCalendar());
        if (compareScores(scores.get(type), cass)){
            Log.info("Vehicle Cassandra Matches for IMEI=%s", key);
        } else {
            Log.info("Vehicke Cassandra doesn't match for IMEI=%s", key);
        }
    }

    private void compareToPortal(String key, Map<UnitType, Map<String, Double>> scores) {
        int did = Integer.parseInt(imeis.get(key).get("did"));
        UnitType type = UnitType.DRIVER;
        if (compareScores(scores.get(type), proxy.getDTrendByDTC(did, 0, 1).get(0))){
            Log.info("Driver Hessian Matches for IMEI=%s", key);
        } else {
            Log.info("Driver Hessian doesn't match for IMEI=%s", key);
        }
        
        int vid = Integer.parseInt(imeis.get(key).get("vid"));
        type = UnitType.VEHICLE;
        if (compareScores(scores.get(type), proxy.getVTrendByVTC(vid, 0, 1).get(0))){
            Log.info("Vehicle Hessian Matches for IMEI=%s", key);
        } else {
            Log.info("Driver Hessian doesn't match for IMEI=%s", key);
        }
    }

    private boolean compareScores(Map<String, Double> ours, Map<String, Object> thiers) {
        boolean equals = true;
        
        equals |= ours.get("Hard Brake").equals(thiers.get("aggressiveBrake"));
        equals |= ours.get("Hard Acceleration").equals(thiers.get("aggressiveAccel"));
        equals |= ours.get("Hard Bump").equals(thiers.get("aggressiveBump"));
        equals |= ours.get("Hard Turn").equals(thiers.get("aggressiveTurn"));
        equals |= ours.get("Driving Style").equals(thiers.get("drivingStyle"));
        equals |= ours.get("Overall").equals(thiers.get("overall"));
        equals |= ours.get("Seat Belt").equals(thiers.get("seatbelt"));
        equals |= ours.get("Speeding").equals(thiers.get("speeding"));
        equals |= ours.get(" 1-30").equals(thiers.get("speeding1"));
        equals |= ours.get("31-40").equals(thiers.get("speeding2"));
        equals |= ours.get("41-54").equals(thiers.get("speeding3"));
        equals |= ours.get("55-64").equals(thiers.get("speeding4"));
        equals |= ours.get("65-80").equals(thiers.get("speeding5"));
        
        return equals;
    }

    public static void main(String[] args) {

        ScoreTest test = new ScoreTest();
        test.runScoring();

//        int did = 69198, vid = 53422;
//        AutomationCalendar now = new AutomationCalendar().addToDay(-1);
//        String startTime = now.setFormat(WebDateFormat.NOTE_PRECISE_TIME).toString();
//        String imei = "FAKETIWISCORING";
//        try {
//            FileWriter fstream = new FileWriter(fileName);
//            BufferedWriter out = new BufferedWriter(fstream);
//            for (int i = 0; i < 5; i++) {
//                String line = imei + (i + 1) + ":::" + startTime + ":::" + vid++ + ":::" + did++;
//                out.write(line);
//                out.newLine();
//            }
//            out.flush();
//            out.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
