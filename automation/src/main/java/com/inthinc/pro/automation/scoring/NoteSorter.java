package com.inthinc.pro.automation.scoring;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.utils.AutomationLogger;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.event.AggressiveDrivingEvent;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.HardVertical820Event;
import com.inthinc.pro.model.event.IdleEvent;
import com.inthinc.pro.model.event.SeatBeltEvent;
import com.inthinc.pro.model.event.SpeedingEvent;

public class NoteSorter {
	
	private final static Logger logger = Logger.getLogger(AutomationLogger.class);
	
	private HashMap<Long, Map<String, Integer>> aggressive;
	private HashMap<Long, Map<String, Integer>> idleing;
	private HashMap<Long, Map<String, Integer>> speeding;
	private HashMap<Long, Map<String, Integer>> seatbelt;

	private Integer[] categories;

	private int mileage;

	private Object itHappens;

	public void preProcessNotes(List<Event> lotsANotes, ProductType type){

		logger.info("Number of Notes == "+lotsANotes.size());
		speedCategories(lotsANotes, type);
		
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
			
			itHappens = itr.next();
			
			if (!type.equals(ProductType.WAYSMART))mileage+=((Event)itHappens).getOdometer();
			
			Map<String, Integer> map = new HashMap<String, Integer>();
			
			if (((Event)itHappens).getForgiven()==1)continue;
			
			if (itHappens instanceof SpeedingEvent) {
				SpeedingEvent event = (SpeedingEvent) itHappens;
				map.put("topSpeed", event.getTopSpeed()	);
				map.put("distance", event.getDistance()	);
				map.put("limit", event.getSpeedLimit()	);
				speeding.put(event.getNoteID(), map);
				
			}else if (itHappens instanceof SeatBeltEvent) {
				SeatBeltEvent event = (SeatBeltEvent) itHappens;
				map.put("topSpeed", event.getTopSpeed()	);
				map.put("distance", event.getDistance()	);
				
				seatbelt.put(event.getNoteID(), map);
				
			}else if (itHappens instanceof AggressiveDrivingEvent) {
				AggressiveDrivingEvent event = (AggressiveDrivingEvent) itHappens;
				map.put("deltaX", event.getDeltaX());
				map.put("deltaY", event.getDeltaY());
				map.put("deltaZ", event.getDeltaZ());
				map.put("speed", event.getSpeed());
				
				aggressive.put(event.getNoteID(), map);
				
			}else if (itHappens instanceof IdleEvent) {
				IdleEvent event = (IdleEvent) itHappens;
				map.put("lowIdle", event.getLowIdle());
				map.put("highIdle", event.getHighIdle());
				idleing.put(event.getNoteID(), map);
			}else if (itHappens instanceof HardVertical820Event) {
				HardVertical820Event event = (HardVertical820Event) itHappens;		
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

	private void speedCategories(List<Event> everything, ProductType deviceType) {
		Integer[] miles= {0,0,0,0,0};
		
		Iterator<Event> itr = everything.iterator();
		int m = 0;
		while (itr.hasNext()) {
			Event note = itr.next();
			if (note.getSpeedLimit()==null)note.setSpeedLimit(78);
			int l = note.getSpeedLimit();
			if (deviceType.equals(ProductType.WAYSMART)) {
				
			}
			else {
				m = note.getOdometer();
			}
			
			if (l <= 30 ) miles[0]+=m;
			else if (l <= 40 ) miles[1]+=m;
			else if (l <= 54 ) miles[2]+=m;
			else if (l <= 64 ) miles[3]+=m;
			else miles[4]+=m;
		}
		logger.info( "Mile Categories  ==  " +miles[0]+", "+miles[1]+", "+miles[2]+", "+miles[3]+", "+miles[4]);
		categories = miles;
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
	
	public Integer[] getCategorical() {
		return categories;
	}
}
