package com.inthinc.pro.scoring;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.inthinc.device.devices.TiwiProDevice;
import com.inthinc.device.emulation.enums.UnitType;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.GoogleTrips;
import com.inthinc.device.hessian.tcp.AutomationHessianFactory;
import com.inthinc.device.hessian.tcp.HessianException;
import com.inthinc.device.objects.TripDriver;
import com.inthinc.device.scoring.ScoringFactory;
import com.inthinc.pro.automation.enums.AutoSilos;
import com.inthinc.pro.automation.enums.ProductType;
import com.inthinc.pro.automation.enums.WebDateFormat;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.resources.FileRW;
import com.inthinc.pro.automation.utils.AutoServers;
import com.inthinc.pro.automation.utils.AutomationThread;
import com.inthinc.pro.dao.cassandra.CassandraDB;
import com.inthinc.pro.dao.cassandra.LocationCassandraDAO;
import com.inthinc.pro.dao.cassandra.ScoreCassandraDAO;
import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.GroupHessianDAO;
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.TrendItem;

public class ScoreTest {

    private static final String fileName = "scoring_imeis.dat";

    private ConcurrentHashMap<String, Map<String, String>> imeis;
    private com.inthinc.device.emulation.interfaces.SiloService proxy;

    private static final AutoSilos server = AutoSilos.QA;

    private static final String first = "40.709922,-111.993513";
    private static final String second = "40.707547,-111.801918";
    private static final String third = "40.58153,-111.85038";

    private AutomationCalendar aggTime;

	private ScoreCassandraDAO cs;

	private CassandraDB db;
	
	private SiloService ss;

	private GroupHessianDAO groupDAO;

	private DriverHessianDAO driverDAO;

	private VehicleHessianDAO vehicleDAO;
	
	
    public ScoreTest() {
		AutoServers server = new AutoServers(ScoreTest.server);
		proxy = new AutomationHessianFactory(server).getPortalProxy();
        imeis = new ConcurrentHashMap<String, Map<String, String>>();
        ss = new SiloServiceCreator(server.getPortalUrl(), server.getPortalPort()).getService();
        
        db = new CassandraDB("Iridium Archive", "note", "10.0.35.40:9160", 10, false);
        

        cs = new ScoreCassandraDAO();
        cs.setCassandraDB(db);
        
        vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(ss);
        cs.setVehicleDAO(vehicleDAO);
        
        driverDAO = new DriverHessianDAO();
        driverDAO.setSiloService(ss);
        cs.setDriverDAO(driverDAO);

        groupDAO = new GroupHessianDAO();
        groupDAO.setSiloService(ss);
        cs.setGroupDAO(groupDAO);
        
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
        
        LocationCassandraDAO loc = new LocationCassandraDAO();
        loc.setCassandraDB(db);
        loc.setDriverDAO(driverDAO);
        loc.setVehicleDAO(vehicleDAO);
        
        
        for (Map.Entry<String, Map<String, String>> entry : imeis.entrySet()) {
        	int vid = Integer.parseInt(entry.getValue().get("vid"));
            LastLocation last = loc.getLastLocationForVehicle(vid);
            
            TiwiProDevice tiwi = new TiwiProDevice(entry.getKey());
            TripDriver drive = new TripDriver(tiwi);
            tiwi.getTripTracker().setPoints(points);
            tiwi.getState().getTime().setDate(last.getTime().getTime()).addToMinutes(15);


            drive.start();
            while (Thread.activeCount() > 20) {
                AutomationThread.pause(250l);
            }
        }
        while (Thread.activeCount() > 2) {
            AutomationThread.pause(1);
        }
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
        	try {
	        	Map<UnitType, Map<String, Double>> ourScore = getSevenDayScores(entry.getKey());
	            compareToPortal(entry.getKey(), ourScore);
	            cassandraScores(entry.getKey(), ourScore);
        	} catch (HessianException e) {
        		Log.info(e);
        	} catch (NullPointerException e){
        		Log.error(e);
        	}
        }
        db.shutdown();
    }
    
    private void cassandraScores(String key, Map<UnitType, Map<String, Double>> scores){
    	int did = Integer.parseInt(imeis.get(key).get("did"));
    	int vid = Integer.parseInt(imeis.get(key).get("vid"));
    	UnitType type = UnitType.DRIVER;
    	List<TrendItem> cass;
        cass = cs.getTrendScores(did, EntityType.ENTITY_DRIVER, Duration.DAYS);
        if (compareScores(scores.get(type), cass)){
            Log.info("Driver Cassandra Matches for IMEI=%s", key);
        } else {
            Log.info("Driver Cassandra doesn't match for IMEI=%s", key);
        }
        
        cass = cs.getTrendScores(vid, EntityType.ENTITY_VEHICLE, Duration.DAYS);
        if (compareScores(scores.get(type), cass)){
            Log.info("Vehicle Cassandra Matches for IMEI=%s", key);
        } else {
            Log.info("Vehicke Cassandra doesn't match for IMEI=%s", key);
        }
    }

    private boolean compareScores(Map<String, Double> ours, List<TrendItem> cass) {
    	boolean equals = true;
        for (TrendItem item : cass){
        	ScoreType type = item.getScoreType();
        	Double score = item.getScore() * 1.0 / 10.0;
        	switch (type){
        		case SCORE_DRIVING_STYLE_HARD_BRAKE:
	        		equals |= ours.get("Hard Brake").equals(score); break;

        		case SCORE_DRIVING_STYLE_HARD_ACCEL:
        			equals |= ours.get("Hard Acceleration").equals(score); break;

        		case SCORE_DRIVING_STYLE_HARD_BUMP:
        			 equals |= ours.get("Hard Bump").equals(score); break;

        		case SCORE_DRIVING_STYLE_HARD_TURN:
        			equals |= ours.get("Hard Turn").equals(score); break;

        		case SCORE_DRIVING_STYLE:
        			equals |= ours.get("Driving Style").equals(score); break;

        		case SCORE_OVERALL:
        			 equals |= ours.get("Overall").equals(score); break;

        		case SCORE_SEATBELT:
        	        equals |= ours.get("Seat Belt").equals(score); break;

        		case SCORE_SPEEDING:
        			equals |= ours.get("Speeding").equals(score); break;

        		case SCORE_SPEEDING_21_30:
        			equals |= ours.get(" 1-30").equals(score); break;

        		case SCORE_SPEEDING_31_40:
        			equals |= ours.get("31-40").equals(score); break;

        		case SCORE_SPEEDING_41_54:
        			equals |= ours.get("41-54").equals(score); break;

        		case SCORE_SPEEDING_55_64:
        			equals |= ours.get("55-64").equals(score); break;

        		case SCORE_SPEEDING_65_80:
        			equals |= ours.get("65-80").equals(score); break;
        		
        		default:
        			break;
        	}
        }
    	return equals;
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

    private boolean compareScores(Map<String, Double> ours, Map<String, Object> map) {
        boolean equals = true;
        
        equals |= ours.get("Hard Brake").equals(map.get("aggressiveBrake"));
        equals |= ours.get("Hard Acceleration").equals(map.get("aggressiveAccel"));
        equals |= ours.get("Hard Bump").equals(map.get("aggressiveBump"));
        equals |= ours.get("Hard Turn").equals(map.get("aggressiveTurn"));
        equals |= ours.get("Driving Style").equals(map.get("drivingStyle"));
        equals |= ours.get("Overall").equals(map.get("overall"));
        equals |= ours.get("Seat Belt").equals(map.get("seatbelt"));
        equals |= ours.get("Speeding").equals(map.get("speeding"));
        equals |= ours.get(" 1-30").equals(map.get("speeding1"));
        equals |= ours.get("31-40").equals(map.get("speeding2"));
        equals |= ours.get("41-54").equals(map.get("speeding3"));
        equals |= ours.get("55-64").equals(map.get("speeding4"));
        equals |= ours.get("65-80").equals(map.get("speeding5"));
        
        return equals;
    }

    public static void main(String[] args) {
    	if (!new File(fileName).exists()){
    	
	    	int did, vid;
	    	if (ScoreTest.server == AutoSilos.QA){
	    		did = 69198;
	    		vid = 53422;
	    	} else {
	    		did = 0;
	    		vid = 0;
	    	}
	    	
	        AutomationCalendar now = new AutomationCalendar().addToDay(-1);
	        String startTime = now.setFormat(WebDateFormat.NOTE_PRECISE_TIME).toString();
	        String imei = "FAKETIWISCORING";
	        try {
	            FileWriter fstream = new FileWriter(fileName);
	            BufferedWriter out = new BufferedWriter(fstream);
	            for (int i = 0; i < 5; i++) {
	                String line = imei + (i + 1) + ":::" + startTime + ":::" + vid++ + ":::" + did++;
	                out.write(line);
	                out.newLine();
	            }
	            out.flush();
	            out.close();
	
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
    	}
    	
        ScoreTest test = new ScoreTest();
        test.runScoring();


    }

}
