package com.inthinc.device.scoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import me.prettyprint.hector.api.query.MultigetSliceCounterQuery;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;

import com.inthinc.device.cassandra.CassandraDB;
import com.inthinc.device.emulation.enums.ScoringTypes;
import com.inthinc.device.emulation.enums.UnitType;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.objects.AutomationCalendar;

public class ScoresFromCassandra {

    // COLUMN NAMES
    public static final String DRIVE_TIME = "driveTime";
    public static final String SPEED_PENALTY1 = "speedPenalty1";
    public static final String SPEED_PENALTY2 = "speedPenalty2";
    public static final String SPEED_PENALTY3 = "speedPenalty3";
    public static final String SPEED_PENALTY4 = "speedPenalty4";
    public static final String SPEED_PENALTY5 = "speedPenalty5";
    public static final String SPEED_EVENTS1 = "speedEvents1";
    public static final String SPEED_EVENTS2 = "speedEvents2";
    public static final String SPEED_EVENTS3 = "speedEvents3";
    public static final String SPEED_EVENTS4 = "speedEvents4";
    public static final String SPEED_EVENTS5 = "speedEvents5";
    public static final String SPEED_ODOMETER1 = "speedOdometer1";
    public static final String SPEED_ODOMETER2 = "speedOdometer2";
    public static final String SPEED_ODOMETER3 = "speedOdometer3";
    public static final String SPEED_ODOMETER4 = "speedOdometer4";
    public static final String SPEED_ODOMETER5 = "speedOdometer5";

    
    public static final String SPEED_EVENTS_1TO7_OVER = "speedEvents1To7MphOver";
    public static final String SPEED_EVENTS_8TO14_OVER = "speedEvents8To14MphOver";
    public static final String SPEED_EVENTS_15PLUS_OVER = "speedEvents15PlusMphOver";
    public static final String SPEED_EVENTS_OVER_80 = "speedEventsOver80Mph";

    public static final String SPEED_OVER = "speedOver";
    public static final String SPEED_COACHING1 = "speedCoaching1";
    public static final String SPEED_COACHING2 = "speedCoaching2";
    public static final String SPEED_COACHING3 = "speedCoaching3";
    public static final String SPEED_COACHING4 = "speedCoaching4";
    public static final String SPEED_COACHING5 = "speedCoaching5";
    public static final String SEATBELT_PENALTY = "seatbeltPenalty";
    public static final String SEATBELT_EVENTS = "seatbeltEvents";
    public static final String IDLE_LO = "idleLo";
    public static final String IDLE_LO_EVENTS = "idleLoEvents";
    public static final String IDLE_HI = "idleHi";
    public static final String IDLE_HI_EVENTS = "idleHiEvents";
    public static final String CRASH_EVENTS = "crashEvents";
    public static final String RPM_EVENTS = "rpmEvents";
    public static final String AGGRESSIVE_BRAKE_PENALTY = "aggressiveBrakePenalty";
    public static final String AGGRESSIVE_BRAKE_EVENTS = "aggressiveBrakeEvents";
    public static final String AGGRESSIVE_ACCEL_PENALTY = "aggressiveAccelPenalty";
    public static final String AGGRESSIVE_ACCEL_EVENTS = "aggressiveAccelEvents";
    public static final String AGGRESSIVE_LEFT_PENALTY = "aggressiveLeftPenalty";
    public static final String AGGRESSIVE_LEFT_EVENTS = "aggressiveLeftEvents";
    public static final String AGGRESSIVE_RIGHT_PENALTY = "aggressiveRightPenalty";
    public static final String AGGRESSIVE_RIGHT_EVENTS = "aggressiveRightEvents";
    public static final String AGGRESSIVE_BUMP_PENALTY = "aggressiveBumpPenalty";
    public static final String AGGRESSIVE_BUMP_EVENTS = "aggressiveBumpEvents";
    public static final String EFFICIENCY_PENALTY = "efficiencyPenalty";
    public static final String PENALTY = "penalty";
    public final static String ODOMETER1 = "odometer1";
    public final static String ODOMETER2 = "odometer2";
    public final static String ODOMETER3 = "odometer3";
    public final static String ODOMETER4 = "odometer4";
    public final static String ODOMETER5 = "odometer5";
    public final static String ODOMETER6 = "odometer6";
    public final static String MPG_ODOMETER = "mpgOdometer";
    public final static String MPG_GAL = "mpgGal";
    public final static String EMU_FEATURE_MASK = "emuFeatureMask";

    public static void main(String[] args) {
        /*
         * if(args.length == 0) { System.out.println("Properties File was not passed In"); System.exit(0); }
         * 
         * 
         * String filePath = (String) args[0]; String startTime = (String) args[1]; String endTime = (String) args[2];
         */

        CassandraDB cassandraDB = new CassandraDB("Iridium Archive", "note", "10.0.35.40:9160", 10, false);

        ScoresFromCassandra cs = new ScoresFromCassandra();
        Map<String, Object> scoreMap = null;
        AutomationCalendar start, end;
        start = new AutomationCalendar("2012-04-10");
        end = new AutomationCalendar("2012-04-10");
        scoreMap = cs.getScores(UnitType.DRIVER, ScoringTypes.DAYS, 3, start, end);
        cassandraDB.shutdown();
        Log.info(scoreMap);
    }


    public Map<String, Object> getScores(UnitType type, ScoringTypes interval, int id, AutomationCalendar startDate, AutomationCalendar endDate) {
        Composite startRange, endRange;
        startDate.setFormat(interval.getFormat());
        endDate.setFormat(interval.getFormat());
        String start = startDate.toString();
        String end = endDate.toString();
        if (interval.equals(ScoringTypes.DAYS) || interval.equals(ScoringTypes.MONTHS)){
            startRange = createComposite(start, Long.MIN_VALUE);
            endRange = createComposite(end, Long.MAX_VALUE);
        } else {
            startRange = createComposite(start);
            endRange = createComposite(end);
        }

        return getScoresForPeriod(interval.getColumnFamily(type), interval.getIndex(type), id, startRange, endRange); 
    }


    private Map<String, Object> getScoresForPeriod(String aggColumnFamily, String indexColumnFamily, int id, Composite startRange, Composite endRange) {
        Log.debug("column (%s) index (%s)", aggColumnFamily, indexColumnFamily);
        List<Integer> indexRowKeys = new ArrayList<Integer>();
        indexRowKeys.add(id);

        List<Composite> rowKeys = fetchRowKeysFromIndex(indexColumnFamily, indexRowKeys, startRange, endRange);
        CounterRows<Composite, String> rows = fetchRowsForKeys(aggColumnFamily, rowKeys);
        Map<String, Long> valuesMap = getAggregateValues(rows);
        return getScores(valuesMap);
    }

    private List<Composite> fetchRowKeysFromIndex(String INDEX_CF, List<Integer> indexRowKeys, Composite startRange, Composite endRange) {
        MultigetSliceQuery<Integer, Composite, Composite> sliceQuery = HFactory.createMultigetSliceQuery(CassandraDB.getKeyspace(), CassandraDB.is, CassandraDB.cs, CassandraDB.cs);

        sliceQuery.setRange(startRange, endRange, false, 1000);

        sliceQuery.setColumnFamily(INDEX_CF);
        sliceQuery.setKeys(indexRowKeys);
        // rangeSlicesQuery.setReturnKeysOnly();

        QueryResult<Rows<Integer, Composite, Composite>> result = sliceQuery.execute();
        Rows<Integer, Composite, Composite> rows = result.get();

        List<Composite> keyList = new ArrayList<Composite>();
        for (Row<Integer, Composite, Composite> row : rows) {
            ColumnSlice<Composite, Composite> columnSlice = row.getColumnSlice();
            List<HColumn<Composite, Composite>> columnList = columnSlice.getColumns();

            for (HColumn<Composite, Composite> column : columnList) {
                Composite rowKey = column.getValue();
                keyList.add(rowKey);
            }
        }
        return keyList;
    }

    private CounterRows<Composite, String> fetchRowsForKeys(String AGG_CF, List<Composite> keys) {
        MultigetSliceCounterQuery<Composite, String> sliceQuery = HFactory.createMultigetSliceCounterQuery(CassandraDB.getKeyspace(), CassandraDB.cs, CassandraDB.ss);

        sliceQuery.setColumnFamily(AGG_CF);
        sliceQuery.setRange("", "", false, 1000); // get all the columns
        sliceQuery.setKeys(keys);

        QueryResult<CounterRows<Composite, String>> result = sliceQuery.execute();
        CounterRows<Composite, String> rows = result.get();
        return rows;
    }

    private Map<String, Long> getAggregateValues(CounterRows<Composite, String> rows) {
        Map<String, Long> columnMap = new HashMap<String, Long>();
        for (CounterRow<Composite, String> row : rows) {
            CounterSlice<String> columnSlice = row.getColumnSlice();
            List<HCounterColumn<String>> columnList = columnSlice.getColumns();
            for (HCounterColumn<String> column : columnList) {
                Long value = getValue(columnMap, column.getName());
                if (value == null)
                    value = 0L;
                value += column.getValue();
                columnMap.put(column.getName(), value);
            }
        }
        return columnMap;
    }

    private Map<String, Object> getScores(Map<String, Long> columnMap) {

        Map<String, Object> scoreMap = new HashMap<String, Object>();

        Long totalMiles = getValue(columnMap, ODOMETER6);
        if (totalMiles == null || totalMiles == 0)
            return scoreMap;

        totalMiles /= 100;

        Double score = 0.0;
        long speedPenalty = 0;
        long stylePenalty = 0;
        long seatbeltPenalty = 0;

        // SCORE_SPEEDING:
        speedPenalty = getValue(columnMap, SPEED_PENALTY1) + getValue(columnMap, SPEED_PENALTY2) + getValue(columnMap, SPEED_PENALTY3) + getValue(columnMap, SPEED_PENALTY4)
                + getValue(columnMap, SPEED_PENALTY5);
        score = ScoringFormulas.getSpeedingScore(totalMiles, convertLong2Double(speedPenalty));
        scoreMap.put("speeding", score);

        // SCORE_SEATBELT:
        seatbeltPenalty = getValue(columnMap, SEATBELT_PENALTY);
        score = ScoringFormulas.getSeatBeltScore(totalMiles, convertLong2Double(seatbeltPenalty));
        scoreMap.put("seatbelt", score);

        // SCORE_DRIVING_STYLE:
        stylePenalty = getValue(columnMap, AGGRESSIVE_BRAKE_PENALTY) + getValue(columnMap, AGGRESSIVE_ACCEL_PENALTY) + getValue(columnMap, AGGRESSIVE_LEFT_PENALTY)
                + getValue(columnMap, AGGRESSIVE_RIGHT_PENALTY) + getValue(columnMap, AGGRESSIVE_BUMP_PENALTY);
        score = ScoringFormulas.getStyleScore(totalMiles, convertLong2Double(stylePenalty));
        scoreMap.put("drivingStyle", score);

        // SCORE_COACHING_EVENTS:
        // score = (double)getValue(columnMap, SPEEDCOACHING1)+getValue(columnMap, SPEEDCOACHING2)+getValue(columnMap, SPEEDCOACHING3)+getValue(columnMap,
        // SPEEDCOACHING4)+getValue(columnMap, SPEEDCOACHING5);

        // SCORE_OVERALL:
        score = ScoringFormulas.getOverallFromPenalty(totalMiles, convertLong2Double(seatbeltPenalty), convertLong2Double(stylePenalty), convertLong2Double(speedPenalty));
        scoreMap.put("overall", score);

        // subTypes of SPEEDING
        // SCORE_SPEEDING_21_30:
        speedPenalty = getValue(columnMap, SPEED_PENALTY1);
        score = ScoringFormulas.getSpeedingScore(getValue(columnMap, ODOMETER1) / 100.0, convertLong2Double(speedPenalty));
        scoreMap.put("speeding1", score);

        // SCORE_SPEEDING_31_40:
        speedPenalty = getValue(columnMap, SPEED_PENALTY2);
        score = ScoringFormulas.getSpeedingScore(getValue(columnMap, ODOMETER2) / 100.0, convertLong2Double(speedPenalty));
        scoreMap.put("speeding2", score);

        // SCORE_SPEEDING_41_54:
        speedPenalty = getValue(columnMap, SPEED_PENALTY3);
        score = ScoringFormulas.getSpeedingScore(getValue(columnMap, ODOMETER3) / 100.0, convertLong2Double(speedPenalty));
        scoreMap.put("speeding3", score);

        // SCORE_SPEEDING_55_64:
        speedPenalty = getValue(columnMap, SPEED_PENALTY4);
        score = ScoringFormulas.getSpeedingScore(getValue(columnMap, ODOMETER4) / 100.0, convertLong2Double(speedPenalty));
        scoreMap.put("speeding4", score);

        // SCORE_SPEEDING_65_80:
        speedPenalty = getValue(columnMap, SPEED_PENALTY5);
        score = ScoringFormulas.getSpeedingScore(getValue(columnMap, ODOMETER5) / 100.0, convertLong2Double(speedPenalty));
        scoreMap.put("speeding5", score);

        // subTypes of DRIVING_STYLE
        // SCORE_DRIVING_STYLE_HARD_BRAKE:
        stylePenalty = getValue(columnMap, AGGRESSIVE_BRAKE_PENALTY);
        score = ScoringFormulas.getStyleScore(totalMiles, convertLong2Double(stylePenalty));
        scoreMap.put("aggressiveBrake", score);

        // SCORE_DRIVING_STYLE_HARD_ACCEL:
        stylePenalty = getValue(columnMap, AGGRESSIVE_ACCEL_PENALTY);
        score = ScoringFormulas.getStyleScore(totalMiles, convertLong2Double(stylePenalty));
        scoreMap.put("aggressiveAccel", score);

        // SCORE_DRIVING_STYLE_HARD_BUMP:
        stylePenalty = getValue(columnMap, AGGRESSIVE_BUMP_PENALTY);
        score = ScoringFormulas.getStyleScore(totalMiles, convertLong2Double(stylePenalty));
        scoreMap.put("aggressiveBump", score);

        // SCORE_DRIVING_STYLE_HARD_TURN:
        stylePenalty = getValue(columnMap, AGGRESSIVE_RIGHT_PENALTY) + getValue(columnMap, AGGRESSIVE_LEFT_PENALTY);
        score = ScoringFormulas.getStyleScore(totalMiles, convertLong2Double(stylePenalty));
        scoreMap.put("aggressiveTurn", score);

        return scoreMap;
    }
    
    public static Composite createComposite(AutomationCalendar start, long value) {
        return createComposite(start.toString(), value);
    }

    public static Composite createComposite(String value1, long value2) {
        Composite composite = new Composite();
        composite.add(0, value1);
        composite.add(1, value2);
        return composite;
    }
    

    public static Composite createComposite(AutomationCalendar start) {
        return createComposite(start.toString());
    }

    public static Composite createComposite(String value) {
        Composite composite = new Composite();
        composite.add(0, value);
        return composite;
    }

    public double convertLong2Double(long penaltyAsDouble) {
        return penaltyAsDouble / 10000D;
    }

    public long getValue(Map<String, Long> columnMap, String columnName) {
        Long val = columnMap.get(columnName);
        if (val == null)
            val = 0L;

        return val;
    }

}
