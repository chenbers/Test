package com.inthinc.pro.automation.scoring;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.AutomationSiloService;
import com.inthinc.pro.model.configurator.ProductType;


public class ScoringNoteProcessor {
	private final static Logger logger = Logger.getLogger(ScoringNoteProcessor.class);
	
	public static enum UnitType {DRIVER, VEHICLE}; 

	private final Double a = -10.4888220818923;
	private final Double b = 1.16563268601352;
	private final Double agg_bump = 0.36351;
	private final Double agg_turn = 0.412991;
	private final Double agg_brake = 0.164130;
	private final Double agg_accel = 0.0593694;

	private AutomationSiloService hessian;
	private ScoringNoteSorter processor = new ScoringNoteSorter();
	
	private Map<String, Double> scores;
	
	
	private Map<Long, Map<String, Integer>> speeding, aggressive, seatbelt;
	private Integer[] categories;
	private double accel;
	private double brake;
	private double turn;
	private double bump;
	private double mileage=0;
	private double seatbeltscore;
	private ProductType deviceType;
	

	public ScoringNoteProcessor(Addresses server){
		hessian = new AutomationSiloService(server);
		scores = new TreeMap<String, Double>();
	}
	
	
	public void scoreMe(UnitType type, Integer ID, AutomationCalendar start, AutomationCalendar stop, ProductType deviceType) {
		this.deviceType = deviceType;
		if (type == UnitType.DRIVER){
		    getDriverNotes(ID, start, stop);
		}
		else {
		    getVehicleNotes(ID, start, stop);
		}
		scores.put("Overall", ScoringFormulas.overallScore(speedingScore(), drivingStyleScore(), seatBeltScore()));		
	}
	
	public Map<String, Double> getOverall(){
		return scores;
	}
	
	public void changeServers(Addresses server){
		hessian = new AutomationSiloService(server);
	}
	
	private void getVehicleNotes(Integer ID, AutomationCalendar start, AutomationCalendar stop){
		processor.preProcessNotes(hessian.getVehicleNote(ID, start.epochSeconds(), stop.epochSeconds()), deviceType);
		theResultsAreIn();
	}
	
	private void getDriverNotes(Integer ID, AutomationCalendar start, AutomationCalendar stop){
		processor.preProcessNotes(hessian.getDriverNote(ID, start.epochSeconds(), stop.epochSeconds()), deviceType );
		theResultsAreIn();
	}
	
	private void theResultsAreIn() {
		aggressive = processor.getAggressive();
		speeding = processor.getSpeeding();
		seatbelt = processor.getSeatBelt();
		mileage = processor.getMileage();
		categories = processor.getCategorical();
	}
	
	
	private Double drivingStyleScore() {
		Iterator<Long> itr = aggressive.keySet().iterator();
		accel=0.0; brake=0.0; turn=0.0; bump=0.0;
		
		while (itr.hasNext()) {
			Long noteID = itr.next();
			logger.debug("Processing Aggressive note == " +noteID);
			Map<String, Integer> dumbDriver = aggressive.get(noteID);
			Double deltaX = dumbDriver.get("deltaX").doubleValue();
			Double deltaY = dumbDriver.get("deltaY").doubleValue();
			Double deltaZ = dumbDriver.get("deltaZ").doubleValue();
			Double speed = dumbDriver.get("speed").doubleValue();
			
			if (Math.abs(deltaX) > Math.abs(deltaY) && Math.abs(deltaX) > Math.abs(deltaZ)) {
				if (deltaX>0) accel += ScoringFormulas.xSeverity(deltaX, speed);
				else brake += ScoringFormulas.xSeverity(deltaX, speed);
			}
			
			else if (Math.abs(deltaY) > Math.abs(deltaX) && Math.abs(deltaY) > Math.abs(deltaZ)) {
				turn += ScoringFormulas.ySeverity(deltaY, speed);
			}
			
			else if (Math.abs(deltaZ) > Math.abs(deltaY) && Math.abs(deltaZ) > Math.abs(deltaX)) {
				bump += ScoringFormulas.zSeverity(deltaZ, speed);
			}
		}
		scores.put("Hard Acceleration", ScoringFormulas.ap2s(a,b,accel,mileage,agg_accel));
		scores.put("Hard Brake", ScoringFormulas.ap2s(a,b,brake,mileage,agg_brake));
		scores.put("Hard Turn", ScoringFormulas.ap2s(a,b,turn,mileage,agg_turn));
		scores.put("Hard Bump", ScoringFormulas.ap2s(a,b,bump,mileage,agg_bump));
		scores.put("Driving Style", ScoringFormulas.ap2s(a,b,brake+accel+turn+bump,mileage,1.0));
		return scores.get("Driving Style");
	}
	
	private Double seatBeltScore() {
		seatbeltscore=0.0;
		
		Iterator<Long> itr = seatbelt.keySet().iterator();
		while (itr.hasNext()) {
			Long noteID = itr.next();
			logger.debug("Processing Seatbelt note == " +noteID);
			Map<String, Integer> badDriver = seatbelt.get(noteID);
			Double topSpeed = badDriver.get("topSpeed").doubleValue();
			Double distance = badDriver.get("distance").doubleValue();
			
			seatbeltscore += Math.pow(topSpeed, 2.0) * distance;
		}
		
		scores.put("Seat Belt", ScoringFormulas.p2s(.3,.2,seatbeltscore,mileage));
		return scores.get("Seat Belt");
	}

	public void testSeatBelt(Double topSpeed, Double distance, Double mileage) {
		Double penalty = (Math.pow(topSpeed, 2.0)*distance);
		Double score = ScoringFormulas.p2s(.3, .2, penalty, mileage);
		System.out.println(score);
	}
	
	private Double speedingScore() {
		Iterator<Long> itr = speeding.keySet().iterator();
		Double[] cats = {0.0,0.0,0.0,0.0,0.0};
		Integer[] numbers = {0,0,0,0,0};
		
		while (itr.hasNext()) {
			Long noteID = itr.next();
			Map<String, Integer> speedDemon = speeding.get(noteID);
			logger.debug("Processing Speeding note == " +noteID);
			Double top = speedDemon.get("topSpeed").doubleValue();
			Double limit = speedDemon.get("limit").doubleValue();
			Double distance = speedDemon.get("distance").doubleValue();
			Integer meow = 0;
			if (limit<=30) meow = 0;
			else if (limit<=40) meow = 1;
			else if (limit<=54) meow = 2;
			else if (limit<=64) meow = 3;
			else meow=4;
			
			cats[meow] += ScoringFormulas.speedingPenalty(top, limit, distance);
			numbers[meow] ++;
		}

		scores.put(" 1-30",    ScoringFormulas.p2s(1.0,0.2,cats[0],categories[0].doubleValue()));
		scores.put("31-40",    ScoringFormulas.p2s(1.0,0.2,cats[1],categories[1].doubleValue()));
		scores.put("41-54",    ScoringFormulas.p2s(1.0,0.2,cats[2],categories[2].doubleValue()));
		scores.put("55-64",    ScoringFormulas.p2s(1.0,0.2,cats[3],categories[3].doubleValue()));
		scores.put("65-80",    ScoringFormulas.p2s(1.0,0.2,cats[4],categories[4].doubleValue()));
		scores.put("Speeding", ScoringFormulas.p2s(1.0,0.2,ScoringFormulas.speedingPenalty(speeding),mileage));
		return scores.get("Speeding");
	}
	
	@Override
	public String toString(){
	    StringWriter writer = new StringWriter();
	    Iterator<String> itr = scores.keySet().iterator();
	    while (itr.hasNext()){
	        String key = itr.next();
	        double value = scores.get(key);
	        writer.write(key + " = " + value + "\n");
	    }
	    return writer.toString();
	}
	
}
