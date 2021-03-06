package com.inthinc.pro.scoring;

import java.io.StringWriter;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;


public class ScoringNoteProcessor {
    
    private final static Logger logger = Logger.getLogger(ScoringNoteProcessor.class);
	
	public static enum UnitType {DRIVER, VEHICLE}; 

	private final Double a = -10.4888220818923; // Offset for scores in log scale.
	private final Double b = 1.16563268601352;  // Scale factor for scores in log scale.
	private final Double agg_bump  = 0.270663;  // Percent of scores for bump  (scale factor used to compute bump-only scores)
	private final Double agg_turn  = 0.321069;  // Percent of scores for turn  (scale factor used to compute turn-only scores)
	private final Double agg_brake = 0.382325;  // Percent of scores for brake (scale factor used to compute brake-only scores)
	private final Double agg_accel = 0.0259425; // Percent of scores for accel (scale factor used to compute accel-only scores)

	private ScoringNoteSorter sorter = new ScoringNoteSorter();
	
	private Map<String, Double> scores;
	
	
	private Integer[] categories;
	private double accel;
	private double brake;
	private double turn;
	private double bump;
	private double mileage=0;
	private double seatbeltscore;
	

	public ScoringNoteProcessor(ScoringNoteSorter sorter){
	    if (sorter == null){
	        throw new NullPointerException("Cannot have a null sorter");
	    }
	    this.sorter = sorter;
		scores = new TreeMap<String, Double>();
	}
	
	
	
	public Map<String, Double> getOverall(){
		return scores;
	}
	
	
	public void calculateScores() {
		mileage = sorter.getMileage();
		categories = sorter.getMileageBuckets();
        scores.put("Overall", ScoringFormulas.overallScore(speedingScore(), drivingStyleScore(), seatBeltScore())); 
	}
	
	
	public Double drivingStyleScore() {
		accel=0.0; brake=0.0; turn=0.0; bump=0.0;
		
		for (Map.Entry<Long, Map<String, Integer>> note : sorter.getAggressive().entrySet()){
			logger.debug("Processing Aggressive note == " +note.getKey());
			Double deltaX = note.getValue().get("deltaX").doubleValue();
			Double deltaY = note.getValue().get("deltaY").doubleValue();
			Double deltaZ = note.getValue().get("deltaZ").doubleValue();
			Double speed  = note.getValue().get("speed" ).doubleValue();
			
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
	
	public Double seatBeltScore() {
		seatbeltscore=0.0;
		
		for (Map.Entry<Long, Map<String, Integer>> note : sorter.getSeatBelt().entrySet()) {
			logger.debug("Processing Seatbelt note == " +note.getKey());
			Double topSpeed = note.getValue().get("topSpeed").doubleValue();
			Double distance = note.getValue().get("distance").doubleValue();
			
			seatbeltscore += Math.pow(topSpeed, 2.0) * distance;
		}
		
		scores.put("Seat Belt", ScoringFormulas.p2s(.3,.2,seatbeltscore,mileage));
		return scores.get("Seat Belt");
	}

	public void testSeatBelt(Double topSpeed, Double distance, Double mileage) {
		Double penalty = (Math.pow(topSpeed, 2.0)*distance);
		Double score = ScoringFormulas.p2s(.3, .2, penalty, mileage);
		logger.info(String.format("%2.2f", score));
	}
	
	public Double speedingScore() {
		Double[] penalties = {0.0,0.0,0.0,0.0,0.0};
		Integer[] categoryCount = {0,0,0,0,0};
		
		for (Map.Entry<Long, Map<String, Integer>> note : sorter.getSpeeding().entrySet()){
			logger.debug("Processing Speeding note == " +note.getKey());
			Double top       = note.getValue().get("topSpeed").doubleValue();
			Double limit     = note.getValue().get("limit"   ).doubleValue();
			Double distance  = note.getValue().get("distance").doubleValue();
			Integer category = 0;
			if (limit<=30) category = 0;
			else if (limit<=40) category = 1;
			else if (limit<=54) category = 2;
			else if (limit<=64) category = 3;
			else category=4;
			
			penalties[category] += ScoringFormulas.speedingPenalty(top, limit, distance);
			categoryCount[category] ++;
		}

		scores.put(" 1-30",    ScoringFormulas.p2s(1.0,0.2,penalties[0],categories[0].doubleValue()));
		scores.put("31-40",    ScoringFormulas.p2s(1.0,0.2,penalties[1],categories[1].doubleValue()));
		scores.put("41-54",    ScoringFormulas.p2s(1.0,0.2,penalties[2],categories[2].doubleValue()));
		scores.put("55-64",    ScoringFormulas.p2s(1.0,0.2,penalties[3],categories[3].doubleValue()));
		scores.put("65-80",    ScoringFormulas.p2s(1.0,0.2,penalties[4],categories[4].doubleValue()));
		scores.put("Speeding", ScoringFormulas.p2s(1.0,0.2,ScoringFormulas.speedingPenalty(sorter.getSpeeding()),mileage));
		return scores.get("Speeding");
	}
	
	@Override
	public String toString(){
	    StringWriter writer = new StringWriter();
	    for (Map.Entry<String, Double> entry : scores.entrySet()){
	        double value = entry.getValue();
	        writer.write(entry.getKey() + " = " + value + "\n");
	    }
	    return writer.toString();
	}
	
}
