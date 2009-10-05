package it.util;

import java.util.ArrayList;
import java.util.List;

public class EventGeneratorData {
	int speedingCnt;
	int seatbeltCnt;
	int aggressiveDrivingCnt;
	int idlingCnt;
	int mpg;
	
	List<Integer> speedingIndexes;
	List<Integer> seatbeltIndexes;
	List<Integer> aggressiveDrivingIndexes;
	List<Integer> idlingIndexes;

	List<Integer> allIndexes;


	

	public EventGeneratorData(	int speedingCnt, int seatbeltCnt, int aggressiveDrivingCnt, int idlingCnt, int mpg)
	{
		this.speedingCnt = speedingCnt;
		this.seatbeltCnt = seatbeltCnt;
		this.aggressiveDrivingCnt = aggressiveDrivingCnt;
		this.idlingCnt = idlingCnt;
		this.mpg = mpg;
	}
	

	public void initIndexes(int numLocs)
	{
		allIndexes = new ArrayList<Integer>();
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

	private List<Integer> initIndexes(int cnt, int numLocs) {
		
		List<Integer> indexList = new ArrayList<Integer>();
		
		int i = 0;
		while (i < cnt)
		{
			int idx = randomInt(0, numLocs-1);
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
