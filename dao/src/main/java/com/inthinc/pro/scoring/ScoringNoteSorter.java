package com.inthinc.pro.scoring;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.event.AggressiveDrivingEvent;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.HardVertical820Event;
import com.inthinc.pro.model.event.IdleEvent;
import com.inthinc.pro.model.event.SeatBeltEvent;
import com.inthinc.pro.model.event.SpeedingEvent;

public class ScoringNoteSorter {
	
	private final static Logger logger = Logger.getLogger(ScoringNoteSorter.class);
	
	private HashMap<Long, Map<String, Integer>> aggressive;
	private HashMap<Long, Map<String, Integer>> idleing;
	private HashMap<Long, Map<String, Integer>> speeding;
	private HashMap<Long, Map<String, Integer>> seatbelt;

	private Integer[] mileageBuckets;

	private int mileage;

	private Object note;

	public void preProcessNotes(List<Event> lotsANotes, ProductType type){

		logger.info("Number of Notes == "+lotsANotes.size());
		getMileageBuckets(lotsANotes, type);
		
		aggressive = new HashMap<Long, Map<String, Integer>>();
		idleing = new HashMap<Long, Map<String, Integer>>();
		speeding = new HashMap<Long, Map<String, Integer>>();
		seatbelt = new HashMap<Long, Map<String, Integer>>();
//		mpg = new HashMap<String, ArrayList<Integer>>();
//		mpg.put("mpgOdometer", new ArrayList<Integer>());
//		mpg.put("mpg", new ArrayList<Integer>());
		
		Iterator<Event> itr = lotsANotes.iterator();
		mileage = 0;
		if (type.equals(ProductType.WAYSMART))
			mileage = lotsANotes.get(0).getOdometer()-lotsANotes.get(lotsANotes.size()-1).getOdometer();
		
		while (itr.hasNext()) {
			
			note = itr.next();
			
			if (!type.equals(ProductType.WAYSMART))mileage+=((Event)note).getOdometer();
			
			Map<String, Integer> map = new HashMap<String, Integer>();
			
			if (((Event)note).getForgiven()==1)continue;
			
			if (note instanceof SpeedingEvent) {
				SpeedingEvent event = (SpeedingEvent) note;
				map.put("topSpeed", event.getTopSpeed()	);
				map.put("distance", event.getDistance()	);
				map.put("limit", event.getSpeedLimit()	);
				speeding.put(event.getNoteID(), map);
				
			}else if (note instanceof SeatBeltEvent) {
				SeatBeltEvent event = (SeatBeltEvent) note;
				map.put("topSpeed", event.getTopSpeed()	);
				map.put("distance", event.getDistance()	);
				
				seatbelt.put(event.getNoteID(), map);
				
			}else if (note instanceof AggressiveDrivingEvent) {
				AggressiveDrivingEvent event = (AggressiveDrivingEvent) note;
				map.put("deltaX", event.getDeltaX());
				map.put("deltaY", event.getDeltaY());
				map.put("deltaZ", event.getDeltaZ());
				map.put("speed", event.getSpeed());
				
				aggressive.put(event.getNoteID(), map);
				
			}else if (note instanceof IdleEvent) {
				IdleEvent event = (IdleEvent) note;
				map.put("lowIdle", event.getLowIdle());
				map.put("highIdle", event.getHighIdle());
				idleing.put(event.getNoteID(), map);
			}else if (note instanceof HardVertical820Event) {
				HardVertical820Event event = (HardVertical820Event) note;		
				map.put("deltaX", 0);
				map.put("deltaY", 0);
				map.put("deltaZ", 0);
				map.put("speed", 0);
				aggressive.put(event.getNoteID(), map);
			}
			
		}
		logger.info("Total Mileage == " + mileage);
		
		logger.debug("Idle Map  == " +idleing);
		logger.info("Idle       NumNotes == " +idleing.size());
		
		logger.debug("Aggressive Map  == " +aggressive);
		logger.info("Aggressive NumNotes == " +aggressive.size());
		
		logger.debug("Speeding Map  == " +speeding);
		logger.info("Speeding   NumNotes == " +speeding.size());
		
		logger.debug("Seatbelt Map  == " +seatbelt);
		logger.info("Seatbelt   NumNotes == " +seatbelt.size());
		
	}

	private void getMileageBuckets(List<Event> everything, ProductType deviceType) {
		Integer[] buckets= {0,0,0,0,0};
		
		Iterator<Event> itr = everything.iterator();
		int milesDriven = 0;
		while (itr.hasNext()) {
			Event note = itr.next();
			if (note.getSpeedLimit()==null)note.setSpeedLimit(78);
			int speedLimit = note.getSpeedLimit();
			if (deviceType.equals(ProductType.WAYSMART)) {
				
			}
			else {
				milesDriven = note.getOdometer();
			}
			
			if (speedLimit <= 30 ) buckets[0]+=milesDriven;
			else if (speedLimit <= 40 ) buckets[1]+=milesDriven;
			else if (speedLimit <= 54 ) buckets[2]+=milesDriven;
			else if (speedLimit <= 64 ) buckets[3]+=milesDriven;
			else buckets[4]+=milesDriven;
		}
		logger.info( "Mile Categories  ==  " +buckets[0]+", "+buckets[1]+", "+buckets[2]+", "+buckets[3]+", "+buckets[4]);
		mileageBuckets = buckets;
	}
	
	public HashMap<Long, Map<String, Integer>> getAggressive() {
		return aggressive;
	}
	public HashMap<Long, Map<String, Integer>> getSpeeding() {
		return speeding;
	}
	public HashMap<Long, Map<String, Integer>> getSeatBelt() {
		return seatbelt;
	}
	public HashMap<Long, Map<String, Integer>> getIdleing() {
		return idleing;
	}
	
	public Integer getMileage() {
		return mileage;
	}
	
	public Integer[] getMileageBuckets() {
		return mileageBuckets;
	}
}
