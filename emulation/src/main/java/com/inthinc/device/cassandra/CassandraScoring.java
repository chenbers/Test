package com.inthinc.device.cassandra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.inthinc.device.scoring.ScoringFormulas;

import me.prettyprint.hector.api.beans.AbstractComposite.Component;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.beans.CounterRow;
import me.prettyprint.hector.api.beans.CounterRows;
import me.prettyprint.hector.api.beans.CounterSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HCounterColumn;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.MutationResult;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.MultigetSliceCounterQuery;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceQuery;

import android.util.Log;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


public class CassandraScoring
{
	public final static String AGGDAY_CF = "agg";
	public final static String VEHICLEAGGINDEX_CF = "vehicleAggIndex";
	public final static String DRIVERAGGINDEX_CF = "driverAggIndex";
	public final static String VEHICLEGROUPAGGINDEX_CF = "vehicleGroupAggIndex";
	public final static String DRIVERGROUPAGGINDEX_CF = "driverGroupAggIndex";
	
	public final static String AGGMONTH_CF = "aggMonth";
	public final static String VEHICLEAGGMONTHINDEX_CF = "vehicleAggMonthIndex";
	public final static String DRIVERAGGMONTHINDEX_CF = "driverAggMonthIndex";
	public final static String VEHICLEGROUPAGGMONTHINDEX_CF = "vehicleGroupAggIndex";
	public final static String DRIVERGROUPAGGMONTHINDEX_CF = "driverGroupAggIndex";
	
	public final static String AGGDRIVERGROUP_CF = "aggDriverGroup";
	public final static String AGGDRIVERGROUPINDEX_CF = "aggDriverGroupIndex";

	public final static String AGGVEHICLEGROUP_CF = "aggVehicleGroup";
	public final static String AGGVEHICLEGROUPINDEX_CF = "aggVehicleGroupIndex";

	public final static String AGGMONTHDRIVERGROUP_CF = "aggMonthDriverGroup";
	public final static String AGGMONTHDRIVERGROUPINDEX_CF = "aggMonthDriverGroupIndex";
	
	public final static String AGGMONTHVEHICLEGROUP_CF = "aggMonthVehicleGroup";
	public final static String AGGMONTHVEHICLEGROUPINDEX_CF = "aggMonthVehicleGroupIndex";
	/////////////////////////////////////////

	//COLUMN NAMES
	public static final String DRIVETIME = "driveTime";
	public static final String SPEEDPENALTY1 = "speedPenalty1";
	public static final String SPEEDPENALTY2 = "speedPenalty2";
	public static final String SPEEDPENALTY3 = "speedPenalty3";
	public static final String SPEEDPENALTY4 = "speedPenalty4";
	public static final String SPEEDPENALTY5 = "speedPenalty5";
	public static final String SPEEDEVENTS1 = "speedEvents1";
	public static final String SPEEDEVENTS2 = "speedEvents2";
	public static final String SPEEDEVENTS3 = "speedEvents3";
	public static final String SPEEDEVENTS4 = "speedEvents4";
	public static final String SPEEDEVENTS5 = "speedEvents5";
	public static final String SPEEDODOMETER1 = "speedOdometer1";
	public static final String SPEEDODOMETER2 = "speedOdometer2";
	public static final String SPEEDODOMETER3 = "speedOdometer3";
	public static final String SPEEDODOMETER4 = "speedOdometer4";
	public static final String SPEEDODOMETER5 = "speedOdometer5";

	public static final String SPEEDEVENTS_1TO7_OVER = "speedEvents1To7MphOver";
	public static final String SPEEDEVENTS_8TO14_OVER = "speedEvents8To14MphOver";
	public static final String SPEEDEVENTS_15PLUS_OVER = "speedEvents15PlusMphOver";
	public static final String SPEEDEVENTS_OVER_80 = "speedEventsOver80Mph";

	public static final String SPEEDOVER = "speedOver";
	public static final String SPEEDCOACHING1 = "speedCoaching1";	
	public static final String SPEEDCOACHING2 = "speedCoaching2";	
	public static final String SPEEDCOACHING3 = "speedCoaching3";	
	public static final String SPEEDCOACHING4 = "speedCoaching4";	
	public static final String SPEEDCOACHING5 = "speedCoaching5";	
	public static final String SEATBELTPENALTY = "seatbeltPenalty";
	public static final String SEATBELTEVENTS = "seatbeltEvents";
	public static final String IDLELO = "idleLo";
	public static final String IDLELOEVENTS = "idleLoEvents";
	public static final String IDLEHI = "idleHi";
	public static final String IDLEHIEVENTS = "idleHiEvents";
	public static final String CRASHEVENTS = "crashEvents";
	public static final String RPMEVENTS = "rpmEvents";
	public static final String AGGRESSIVEBRAKEPENALTY = "aggressiveBrakePenalty";
	public static final String AGGRESSIVEBRAKEEVENTS = "aggressiveBrakeEvents";
	public static final String AGGRESSIVEACCELPENALTY = "aggressiveAccelPenalty";
	public static final String AGGRESSIVEACCELEVENTS = "aggressiveAccelEvents";
	public static final String AGGRESSIVELEFTPENALTY = "aggressiveLeftPenalty";
	public static final String AGGRESSIVELEFTEVENTS = "aggressiveLeftEvents";
	public static final String AGGRESSIVERIGHTPENALTY = "aggressiveRightPenalty";
	public static final String AGGRESSIVERIGHTEVENTS = "aggressiveRightEvents";
	public static final String AGGRESSIVEBUMPPENALTY = "aggressiveBumpPenalty";
	public static final String AGGRESSIVEBUMPEVENTS = "aggressiveBumpEvents";
	public static final String EFFICIENCYPENALTY = "efficiencyPenalty";
	public static final String PENALTY = "penalty";
	public final static String ODOMETER1 = "odometer1";
	public final static String ODOMETER2 = "odometer2";
	public final static String ODOMETER3 = "odometer3";
	public final static String ODOMETER4 = "odometer4";
	public final static String ODOMETER5 = "odometer5";
	public final static String ODOMETER6 = "odometer6";
	public final static String MPGODOMETER = "mpgOdometer";	
	public final static String MPGGAL = "mpgGal";
	public final static String EMUFEATUREMASK = "emuFeatureMask";

	
    public static void main(String[] args)
    {
/*
		if(args.length == 0)
		{
			System.out.println("Properties File was not passed In");
			System.exit(0);
		}
	
		
		String filePath = (String) args[0];
		String startTime = (String) args[1];
		String endTime = (String) args[2];
*/		
    	
    	CassandraDB cassandraDB = new CassandraDB("Iridium Archive", "note", "10.0.35.40:9160", 10, false);
		
    	CassandraScoring cs = new CassandraScoring();
    	Map<String, Object> scoreMap = null;
		scoreMap = cs.getDriverScoresForDays(66462, "2012-04-10", "2012-04-10");
		Log.info(scoreMap);
		scoreMap = cs.getDriverScoresForMonths(66462, "2012-04", "2012-04");
        Log.info(scoreMap);
		scoreMap = cs.getVehicleScoresForDays(52721, "2012-04-10", "2012-04-10");
        Log.info(scoreMap);    	
		scoreMap = cs.getVehicleScoresForMonths(52721, "2012-04", "2012-04");
        Log.info(scoreMap);    	
		scoreMap = cs.getVehicleGroupScoresForDays(5260, "2012-04-10", "2012-04-10");
        Log.info(scoreMap);
		scoreMap = cs.getVehicleGroupScoresForMonths(5260, "2012-04", "2012-04");
        Log.info(scoreMap);
		cassandraDB.shutdown();
    }
	

	public Map<String, Object> getDriverScoresForDays(int driverId, String startDay, String endDay)
	{
		return getScoresForPeriod(AGGDAY_CF, DRIVERAGGINDEX_CF, driverId, createComposite(startDay, Long.MIN_VALUE), createComposite(endDay, Long.MAX_VALUE));
	}
	
	public Map<String, Object> getVehicleScoresForDays(int vehicleId, String startDay, String endDay)
	{
		return getScoresForPeriod(AGGDAY_CF, VEHICLEAGGINDEX_CF, vehicleId, createComposite(startDay, Long.MIN_VALUE), createComposite(endDay, Long.MAX_VALUE));
	}
	
	public Map<String, Object> getDriverScoresForMonths(int driverId, String startMonth, String endMonth)
	{
		return getScoresForPeriod(AGGMONTH_CF, DRIVERAGGMONTHINDEX_CF, driverId, createComposite(startMonth, Long.MIN_VALUE), createComposite(endMonth, Long.MAX_VALUE));
	}
	
	public Map<String, Object> getVehicleScoresForMonths(int vehicleId, String startMonth, String endMonth)
	{
		return getScoresForPeriod(AGGMONTH_CF, VEHICLEAGGMONTHINDEX_CF, vehicleId, createComposite(startMonth, Long.MIN_VALUE), createComposite(endMonth, Long.MAX_VALUE));
	}

	public Map<String, Object> getDriverGroupScoresForDays(int driverGroupId, String startDay, String endDay)
	{
		return getScoresForPeriod(AGGDRIVERGROUP_CF, AGGDRIVERGROUPINDEX_CF, driverGroupId, createComposite(startDay), createComposite(endDay));
	}
	
	public Map<String, Object> getVehicleGroupScoresForDays(int vehicleGroupId, String startDay, String endDay)
	{
		return getScoresForPeriod(AGGVEHICLEGROUP_CF, AGGVEHICLEGROUPINDEX_CF, vehicleGroupId, createComposite(startDay), createComposite(endDay));
	}
	
	public Map<String, Object> getDriverGroupScoresForMonths(int driverGroupId, String startMonth, String endMonth)
	{
		return getScoresForPeriod(AGGMONTHDRIVERGROUP_CF, AGGMONTHDRIVERGROUPINDEX_CF, driverGroupId, createComposite(startMonth), createComposite(endMonth));
	}
	
	public Map<String, Object> getVehicleGroupScoresForMonths(int vehicleGroupId, String startMonth, String endMonth)
	{
		return getScoresForPeriod(AGGMONTHVEHICLEGROUP_CF, AGGMONTHVEHICLEGROUPINDEX_CF, vehicleGroupId, createComposite(startMonth), createComposite(endMonth));
	}
	
	private Map<String, Object> getScoresForPeriod(String aggColumnFamily, String indexColumnFamily, int id, Composite startRange, Composite endRange)
	{
	    Log.info("column (%s) index (%s)", aggColumnFamily, indexColumnFamily);
        List<Integer> indexRowKeys = new ArrayList<Integer>();
		indexRowKeys.add(id);

        Log.info(startRange);
        Log.info(endRange);

        List<Composite> rowKeys = fetchRowKeysFromIndex(indexColumnFamily, indexRowKeys, startRange, endRange);
        CounterRows<Composite, String> rows = fetchRowsForKeys(aggColumnFamily, rowKeys);
        Map<String, Long> valuesMap = getAggregateValues(rows);
        return getScores(valuesMap);
	}

	
	private List<Composite> fetchRowKeysFromIndex(String INDEX_CF, List<Integer> indexRowKeys, Composite startRange, Composite endRange)
    {
        MultigetSliceQuery<Integer, Composite, Composite> sliceQuery = HFactory.createMultigetSliceQuery(CassandraDB.getKeyspace(), CassandraDB.is, CassandraDB.cs, CassandraDB.cs);

        sliceQuery.setRange(startRange, endRange, false, 1000);
        
        sliceQuery.setColumnFamily(INDEX_CF);            
        sliceQuery.setKeys(indexRowKeys);
        //rangeSlicesQuery.setReturnKeysOnly();
        
        QueryResult<Rows<Integer, Composite, Composite>> result = sliceQuery.execute();
        Rows<Integer, Composite, Composite> rows = result.get();            
        
        List<Composite> keyList = new ArrayList<Composite>();
        for(Row<Integer, Composite, Composite> row : rows)
        {
        	ColumnSlice<Composite, Composite> columnSlice = row.getColumnSlice();
        	List<HColumn<Composite, Composite>> columnList = columnSlice.getColumns();
        
	        for (HColumn<Composite, Composite> column : columnList)
	        {
	        	Composite rowKey = column.getValue();
	        	keyList.add(rowKey);
	        }
        }
    	return keyList;
     }

    private CounterRows<Composite, String> fetchRowsForKeys(String AGG_CF, List<Composite> keys)
    {
        MultigetSliceCounterQuery<Composite, String> sliceQuery = HFactory.createMultigetSliceCounterQuery(CassandraDB.getKeyspace(), CassandraDB.cs, CassandraDB.ss);
        
        sliceQuery.setColumnFamily(AGG_CF);            
        sliceQuery.setRange("", "", false, 1000);  //get all the columns
        sliceQuery.setKeys(keys);
        
        QueryResult<CounterRows<Composite, String>> result = sliceQuery.execute();
        CounterRows<Composite, String> rows = result.get();            
        return rows;
    }
	
	private Map<String, Long> getAggregateValues(CounterRows<Composite, String> rows)
    {
		Map<String, Long> columnMap = new HashMap<String, Long>();
		for(CounterRow<Composite, String> row : rows)
		{
			CounterSlice<String> columnSlice = row.getColumnSlice();
        	List<HCounterColumn<String>> columnList = columnSlice.getColumns();
        	for (HCounterColumn<String> column : columnList)
        	{
    			Long value = getValue(columnMap, column.getName());
    			if (value == null)
    				value = 0L;
    			value += column.getValue();
    			columnMap.put(column.getName(), value);
        	}
		}
        return columnMap;
    }

	private Map<String, Object> getScores(Map<String, Long> columnMap)
	{

    	Map<String, Object> scoreMap = new HashMap<String, Object>();

    	Long totalMiles = getValue(columnMap, ODOMETER6);
    	if (totalMiles == null || totalMiles == 0)
    		return scoreMap;
    	
    	totalMiles /= 100;
    	
		Double score = 0.0;
    	long speedPenalty = 0;
    	long stylePenalty = 0;
    	long seatbeltPenalty = 0;
		
		//SCORE_SPEEDING:
		speedPenalty = getValue(columnMap, SPEEDPENALTY1) + getValue(columnMap, SPEEDPENALTY2) + getValue(columnMap, SPEEDPENALTY3) + getValue(columnMap, SPEEDPENALTY4) + getValue(columnMap, SPEEDPENALTY5);
		score = ScoringFormulas.getSpeedingScore(totalMiles, convertLong2Double(speedPenalty));
		scoreMap.put("speeding", score);

		//SCORE_SEATBELT:
		seatbeltPenalty = getValue(columnMap, SEATBELTPENALTY);
		score = ScoringFormulas.getSeatBeltScore(totalMiles, convertLong2Double(seatbeltPenalty));
		scoreMap.put("seatbelt", score);

		//SCORE_DRIVING_STYLE:
		stylePenalty = getValue(columnMap, AGGRESSIVEBRAKEPENALTY) + getValue(columnMap, AGGRESSIVEACCELPENALTY) + getValue(columnMap, AGGRESSIVELEFTPENALTY) + getValue(columnMap, AGGRESSIVERIGHTPENALTY) + getValue(columnMap, AGGRESSIVEBUMPPENALTY);
		score = ScoringFormulas.getStyleScore(totalMiles, convertLong2Double(stylePenalty));
		scoreMap.put("drivingStyle", score);

		//SCORE_COACHING_EVENTS:
		//score = (double)getValue(columnMap, SPEEDCOACHING1)+getValue(columnMap, SPEEDCOACHING2)+getValue(columnMap, SPEEDCOACHING3)+getValue(columnMap, SPEEDCOACHING4)+getValue(columnMap, SPEEDCOACHING5);

		//SCORE_OVERALL:
		score = ScoringFormulas.getOverallFromPenalty(totalMiles, convertLong2Double(seatbeltPenalty), convertLong2Double(stylePenalty), convertLong2Double(speedPenalty));
		scoreMap.put("overall", score);

		// subTypes of SPEEDING
		//SCORE_SPEEDING_21_30:
		speedPenalty = getValue(columnMap, SPEEDPENALTY1);
		score = ScoringFormulas.getSpeedingScore(getValue(columnMap, ODOMETER1)/100.0, convertLong2Double(speedPenalty));
		scoreMap.put("speeding1", score);

		//SCORE_SPEEDING_31_40:
		speedPenalty = getValue(columnMap, SPEEDPENALTY2);
		score = ScoringFormulas.getSpeedingScore(getValue(columnMap, ODOMETER2)/100.0, convertLong2Double(speedPenalty));
		scoreMap.put("speeding2", score);
		
		//SCORE_SPEEDING_41_54:
		speedPenalty = getValue(columnMap, SPEEDPENALTY3);
		score = ScoringFormulas.getSpeedingScore(getValue(columnMap, ODOMETER3)/100.0, convertLong2Double(speedPenalty));
		scoreMap.put("speeding3", score);
		
		//SCORE_SPEEDING_55_64:
		speedPenalty = getValue(columnMap, SPEEDPENALTY4);
		score = ScoringFormulas.getSpeedingScore(getValue(columnMap, ODOMETER4)/100.0, convertLong2Double(speedPenalty));
		scoreMap.put("speeding4", score);
	
		//SCORE_SPEEDING_65_80:
		speedPenalty = getValue(columnMap, SPEEDPENALTY5);
		score = ScoringFormulas.getSpeedingScore(getValue(columnMap, ODOMETER5)/100.0, convertLong2Double(speedPenalty));
		scoreMap.put("speeding5", score);

		// subTypes of DRIVING_STYLE
		//SCORE_DRIVING_STYLE_HARD_BRAKE:
		stylePenalty = getValue(columnMap, AGGRESSIVEBRAKEPENALTY);
		score = ScoringFormulas.getStyleScore(totalMiles, convertLong2Double(stylePenalty));
		scoreMap.put("aggressiveBrake", score);
		
		//SCORE_DRIVING_STYLE_HARD_ACCEL:
		stylePenalty = getValue(columnMap, AGGRESSIVEACCELPENALTY);
		score = ScoringFormulas.getStyleScore(totalMiles, convertLong2Double(stylePenalty));
		scoreMap.put("aggressiveAccel", score);
	
		//SCORE_DRIVING_STYLE_HARD_BUMP:
		stylePenalty = getValue(columnMap, AGGRESSIVEBUMPPENALTY);
		score = ScoringFormulas.getStyleScore(totalMiles, convertLong2Double(stylePenalty));
		scoreMap.put("aggressiveBump", score);
	
		//SCORE_DRIVING_STYLE_HARD_TURN:
		stylePenalty = getValue(columnMap, AGGRESSIVERIGHTPENALTY) + getValue(columnMap, AGGRESSIVELEFTPENALTY);
		score = ScoringFormulas.getStyleScore(totalMiles, convertLong2Double(stylePenalty));
		scoreMap.put("aggressiveTurn", score);
		
		return scoreMap;
    }

    private Composite createComposite(String value1, long value2)
    {
        Composite composite = new Composite();
        composite.add(0, value1);
        composite.add(1, value2);
        return composite;
    }
	
    private Composite createComposite(String value)
    {
        Composite composite = new Composite();
        composite.add(0, value);
        return composite;
    }

    private double convertLong2Double(long penaltyAsDouble)
	{
		return penaltyAsDouble/10000D;
	}

	
	private long getValue(Map<String, Long> columnMap, String columnName)
	{
		Long val = columnMap.get(columnName);
		if (val == null)
			val = 0L;
		
		return val;	
	}
}