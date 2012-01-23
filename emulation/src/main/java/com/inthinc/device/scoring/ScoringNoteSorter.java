package com.inthinc.device.scoring;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.pro.automation.enums.ProductType;

public class ScoringNoteSorter {
	
	private final static Logger logger = Logger.getLogger(ScoringNoteSorter.class);

	private static final String odometer = "odometer";
	private static final String forgiven = "forgiven";
	private static final String topSpeed = "topSpeed";
	private static final String distance = "odometer";
	private static final String limit = "speedLimit";
	private static final String noteID = "noteID";
	private static final String deltaX = "deltaX";
	private static final String deltaY = "deltaY";
	private static final String deltaZ = "deltaZ";
	private static final String speed = "speed";
	private static final String lowIdle = "lowIdle";
	private static final String highIdle = "highIdle";

	
	private HashMap<Long, Map<String, Integer>> aggressive;
	private HashMap<Long, Map<String, Integer>> idleing;
	private HashMap<Long, Map<String, Integer>> speeding;
	private HashMap<Long, Map<String, Integer>> seatbelt;

	private Integer[] categories;

	private int mileage;


	

	public void preProcessNotes(List<Map<String, Object>> lotsANotes, ProductType type){

		logger.info("Number of Notes == "+lotsANotes.size());
		speedCategories(lotsANotes, type);
		
		aggressive = new HashMap<Long, Map<String, Integer>>();
		idleing = new HashMap<Long, Map<String, Integer>>();
		speeding = new HashMap<Long, Map<String, Integer>>();
		seatbelt = new HashMap<Long, Map<String, Integer>>();
//		mpg = new HashMap<String, ArrayList<Integer>>();
//		mpg.put("mpgOdometer", new ArrayList<Integer>());
//		mpg.put("mpg", new ArrayList<Integer>());
		
		Iterator<Map<String, Object>> itr = lotsANotes.iterator();
		mileage = 0;
		if (type.equals(ProductType.WAYSMART))
			mileage = ((Integer)lotsANotes.get(0).get(odometer))-((Integer)lotsANotes.get(lotsANotes.size()-1).get(odometer));
		
		while (itr.hasNext()) {
			
			Map<String, Object> next = itr.next();
			DeviceNoteTypes eventType = DeviceNoteTypes.valueOf((Integer) next.get("type"));
			
			if (!type.equals(ProductType.WAYSMART))mileage+=((Integer)next.get(odometer));
			
			Map<String, Integer> map = new HashMap<String, Integer>();
			
			if (((Integer)next.get(forgiven))==1)continue;
			
			if (eventType.equals(DeviceNoteTypes.SPEEDING) || eventType.equals(DeviceNoteTypes.SPEEDING_EX3)) {
				map.put(topSpeed, (Integer) next.get(topSpeed));
				map.put(distance, (Integer) next.get(distance)	);
				map.put(limit, (Integer) next.get(limit));
				speeding.put((Long) next.get(noteID), map);
				
			}else if (next.equals(DeviceNoteTypes.SEATBELT)) {
				map.put(topSpeed, (Integer) next.get(topSpeed)	);
				map.put(odometer, (Integer) next.get(odometer)	);
				
				seatbelt.put((Long) next.get(noteID), map);
				
			}else if (next.equals(DeviceNoteTypes.NOTE_EVENT)) {
				map.put(deltaX, (Integer) next.get(deltaX));
				map.put(deltaY, (Integer) next.get(deltaY));
				map.put(deltaZ, (Integer) next.get(deltaZ));
				map.put(speed, (Integer) next.get(speed));
				
				aggressive.put((Long) next.get(noteID), map);
				
			}else if (next.equals(DeviceNoteTypes.IDLING)) {
				map.put(lowIdle, (Integer) next.get(lowIdle));
				map.put(highIdle, (Integer) next.get(highIdle));
				idleing.put((Long) next.get(noteID), map);
				
			}else if (next.equals(DeviceNoteTypes.VERTICALEVENT) || next.equals(DeviceNoteTypes.VERTICALEVENT_SECONDARY)) {
				map.put(deltaX, 0);
				map.put(deltaY, 0);
				map.put(deltaZ, 0);
				map.put(speed, 0);
				aggressive.put((Long) next.get(noteID), map);
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

	private void speedCategories(List<Map<String, Object>> everything, ProductType deviceType) {
		Integer[] miles= {0,0,0,0,0};
		
		Iterator<Map<String, Object>> itr = everything.iterator();
		int m = 0;
		while (itr.hasNext()) {
			Map<String, Object> note = itr.next();
			if (!note.containsKey(limit))note.put(limit, 78);
			int l = (Integer) note.get(limit);
			if (deviceType.equals(ProductType.WAYSMART)) {
				
			}
			else {
				m = (Integer) note.get(odometer);
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
