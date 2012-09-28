package it.util;

import it.config.ReportTestConst;

import java.util.ArrayList;
import java.util.List;

public class EventGeneratorData {
	int speedingCnt;
	int seatbeltCnt;
	int aggressiveDrivingCnt;
	int idlingCnt;
	boolean includeCrash;
	int mpg;
	int severity;
    boolean includeExtra;
    boolean includeWaysmart;
    boolean includeCoaching;
    int zoneID;
	
	List<Integer> speedingIndexes;
	List<Integer> seatbeltIndexes;
	List<Integer> aggressiveDrivingIndexes;
	List<Integer> idlingIndexes;
	List<Integer> crashIndexes;

	List<Integer> allIndexes;


	

	public EventGeneratorData(	int speedingCnt, int seatbeltCnt, int aggressiveDrivingCnt, int idlingCnt, boolean includeCrash, int mpg, int severity)
	{
		this.speedingCnt = speedingCnt;
		this.seatbeltCnt = seatbeltCnt;
		this.aggressiveDrivingCnt = aggressiveDrivingCnt;
		this.idlingCnt = idlingCnt;
		this.includeCrash = includeCrash;
		this.mpg = mpg;
		this.severity = severity;
	    this.includeExtra = false;
	    this.includeWaysmart = false;
	    this.includeCoaching = false;
	    this.zoneID = 0;
	}
    public EventGeneratorData(  int speedingCnt, int seatbeltCnt, int aggressiveDrivingCnt, int idlingCnt, boolean includeCrash, int mpg, int severity,
            boolean includeExtra,
            boolean includeWaysmart,
            boolean includeCoaching)
    {
        this(speedingCnt, seatbeltCnt, aggressiveDrivingCnt, idlingCnt, includeCrash, mpg, severity);
        this.includeExtra = includeExtra;
        this.includeWaysmart = includeWaysmart;
        this.includeCoaching = includeCoaching;
    }
	
	public void initIndexes(int numLocs) {
		initIndexes(numLocs, false);
	}

	public void initIndexes(int numLocs, boolean includeExtraEvents)
	{
		allIndexes = new ArrayList<Integer>();
		if (includeExtraEvents) {
			allIndexes.add(Integer.valueOf(ReportTestConst.TAMPER_EVENT_IDX));
			allIndexes.add(Integer.valueOf(ReportTestConst.TAMPER2_EVENT_IDX));
			allIndexes.add(Integer.valueOf(ReportTestConst.LOW_BATTERY_EVENT_IDX));
			allIndexes.add(Integer.valueOf(3));	// ignition on
			allIndexes.add(Integer.valueOf(4));  // driver logon
			allIndexes.add(Integer.valueOf(ReportTestConst.ZONE_ENTER_EVENT_IDX));
			allIndexes.add(Integer.valueOf(ReportTestConst.ZONE_EXIT_EVENT_IDX));
			
		}
		else {
			allIndexes.add(Integer.valueOf(0));	// ignition on
            allIndexes.add(Integer.valueOf(1));  // driver logon
		}
		allIndexes.add(Integer.valueOf(numLocs-1));	// ignition off
		crashIndexes = new ArrayList<Integer>();
		if (includeCrash)
		{
			crashIndexes.add(Integer.valueOf(ReportTestConst.CRASH_EVENT_IDX));
		}

		
		
		speedingIndexes = initIndexes(speedingCnt, numLocs);
		seatbeltIndexes = initIndexes(seatbeltCnt, numLocs);
		aggressiveDrivingIndexes = initIndexes(aggressiveDrivingCnt, numLocs);
		idlingIndexes = initIndexes(idlingCnt, numLocs);
		
	}
	
	public boolean isSpeedingIndex(int idx)
	{
		return speedingIndexes.contains(Integer.valueOf(idx));
	}
	public boolean isSeatbeltIndex(int idx)
	{
		return seatbeltIndexes.contains(Integer.valueOf(idx));
	}
	public boolean isAggressiveDrivingIndex(int idx)
	{
		return aggressiveDrivingIndexes.contains(Integer.valueOf(idx));
	}
	public boolean isIdlingIndex(int idx)
	{
		return idlingIndexes.contains(Integer.valueOf(idx));
	}
	public boolean isCrashIndex(int idx)
	{
		return crashIndexes.contains(Integer.valueOf(idx));
	}

	private List<Integer> initIndexes(int cnt, int numLocs) {
		
		List<Integer> indexList = new ArrayList<Integer>();
		
		int maxIndex = numLocs - 1;
		if (includeCrash)
		{
			maxIndex = ReportTestConst.CRASH_EVENT_IDX-1;
		}
		int i = 0;
		while (i < cnt)
		{
			int idx = randomInt(0, maxIndex);
			if (!indexList.contains(Integer.valueOf(idx)) && !allIndexes.contains(Integer.valueOf(idx)))
			{
				indexList.add(Integer.valueOf(idx));
				allIndexes.add(Integer.valueOf(idx));
				i++;
			}
		}
		return indexList;
	}
	
    int randomInt(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

	public int getMpg() {
		return mpg;
	}

	public void setMpg(int mpg) {
		this.mpg = mpg;
	}

}
