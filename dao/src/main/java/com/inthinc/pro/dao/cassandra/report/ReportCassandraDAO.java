package com.inthinc.pro.dao.cassandra.report;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.beans.CounterRows;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.inthinc.pro.dao.cassandra.AggregationCassandraDAO;
import com.inthinc.pro.dao.hessian.exceptions.MappingException;
import com.inthinc.pro.dao.hessian.mapper.SimpleMapper;
import com.inthinc.pro.dao.report.DriverReportDAO;
import com.inthinc.pro.model.AggregationDuration;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.Trend;
import com.inthinc.pro.scoring.ScoringFormulas;

public class ReportCassandraDAO extends AggregationCassandraDAO {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ReportCassandraDAO.class);
	

    protected Score getScoreForAsset(Integer id, EntityType entityType, Duration duration)
    {
        List<Composite> rowKeys = createDateIDKeys(getToday(TimeZone.getDefault()), id, duration.getDvqCode(), duration.getDvqCount()); //TO DO: set correct timezone

        String cf = (entityType.equals(EntityType.ENTITY_DRIVER)) ? getDriverAggCF(duration.getDvqCode()) : getVehicleAggCF(duration.getDvqCode()); 
        CounterRows<Composite, String> rows = fetchAggsForKeys(cf, rowKeys);
        Map<String, Map<String, Long>> scoreMap = summarize(rows, 1);
        Score score = new Score();
        for (Map.Entry<String, Map<String, Long>> entry : scoreMap.entrySet())
        {
        	Map<String, Long> columnMap = entry.getValue();
            score = convertToScore(columnMap);
        }
        return score;
    }

    protected Score getScoreForAsset(Integer id, EntityType entityType, Integer binSize)
    {
        List<Composite> rowKeys = createDateIDKeys(getToday(TimeZone.getDefault()), id, binSize, 1); //TO DO: set correct timezone

        String cf = (entityType.equals(EntityType.ENTITY_DRIVER)) ? getDriverAggCF(binSize) : getVehicleAggCF(binSize); 
        CounterRows<Composite, String> rows = fetchAggsForKeys(cf, rowKeys);
        logger.debug("row count: " + rows.getCount());
        Map<String, Map<String, Long>> scoreMap = summarize(rows, 1);
        logger.debug("scoreMap: " + scoreMap);
        Score score = new Score();
        for (Map.Entry<String, Map<String, Long>> entry : scoreMap.entrySet())
        {
        	Map<String, Long> columnMap = entry.getValue();
            score = convertToScore(columnMap);
        }
        logger.debug("Score: " + score);
        return score;
    }

    protected Score getScoreForAsset(Integer id, EntityType entityType, Date startTime, Date endTime)
    {
//        List<Composite> rowKeys = createDateIDKeys(getToday(TimeZone.getDefault()), id, duration.getDvqCode(), duration.getDvqCount()); //TO DO: set correct timezone
        logger.debug("startTime: " + startTime + " endTime: " + endTime);
        List<Composite> rowKeys = createDateIDKeys(startTime, endTime, id); //TO DO: set correct timezone
        logger.debug("rowKeys: " + rowKeys);

        String cf = (entityType.equals(EntityType.ENTITY_DRIVER)) ? getDriverAggCF(Duration.DAYS.getDvqCode()) : getVehicleAggCF(Duration.DAYS.getDvqCode()); 
        logger.debug("CF: " + cf);
        CounterRows<Composite, String> rows = fetchAggsForKeys(cf, rowKeys);
        logger.debug("row count: " + rows.getCount());
        Map<String, Map<String, Long>> scoreMap = summarize(rows, 1);
        logger.debug("scoreMap: " + scoreMap);
        Score score = new Score();
        for (Map.Entry<String, Map<String, Long>> entry : scoreMap.entrySet())
        {
        	Map<String, Long> columnMap = entry.getValue();
            score = convertToScore(columnMap);
        }
        logger.debug("Score: " + score);
        return score;
    }

    protected List<Trend> getTrendForAsset(Integer id, EntityType entityType, Duration duration)
    {
        List<Composite> rowKeys = createDateIDKeys(getToday(TimeZone.getDefault()), id, duration.getDvqCode(), duration.getDvqCount()); //TO DO: set correct timezone

        String cf = (entityType.equals(EntityType.ENTITY_DRIVER)) ? getDriverAggCF(duration.getDvqCode()) : getVehicleAggCF(duration.getDvqCode()); 
        CounterRows<Composite, String> rows = fetchAggsForKeys(cf, rowKeys);
        Map<String, Map<String, Long>> scoreMap = summarize(rows, duration.getDvqCount());
        List<Trend> trendList = new ArrayList<Trend>();
        for (Map.Entry<String, Map<String, Long>> entry : scoreMap.entrySet())
        {
        	Map<String, Long> columnMap = entry.getValue();
            Trend trend = convertToTrend(columnMap);
            trendList.add(trend);
        }
        return trendList;
    }
	
    public List<Trend> getTrendForGroup(Integer groupID, Duration duration, GroupHierarchy gh, boolean includeSubGroups)
    {
    	logger.debug("gettrendForGroup groupID: " + groupID);
    	List<Integer> groupIDList = new ArrayList<Integer>();
    	if (includeSubGroups)
    		groupIDList = gh.getGroupIDList(groupID);
    	else
    		groupIDList.add(groupID);
        List<Composite> rowKeys = new ArrayList<Composite>(); 
        for (Integer groupId : groupIDList)
        	rowKeys.addAll(createDateIDKeys(getToday(TimeZone.getDefault()), groupId, duration.getCode(), 1));
        	
        String columnFamily = getDriverGroupAggCF(duration.getCode());
        return getTrendForGroup(rowKeys, columnFamily, duration);
    }

    private List<Trend> getTrendForGroup(List<Composite> rowKeys, String columnFamily, Duration duration)
    {
        CounterRows<Composite, String> rows = fetchAggsForKeys(columnFamily, rowKeys);
        Map<String, Map<String, Long>> scoreMap = summarize(rows, duration.getDvqCount());
        List<Trend> trendList = new ArrayList<Trend>();
        for (Map.Entry<String, Map<String, Long>> entry : scoreMap.entrySet())
        {
        	Map<String, Long> columnMap = entry.getValue();
            Trend trend = convertToTrend(columnMap);
            trendList.add(trend);
       }
    	return trendList;    	
    }

    public Score getScoreForGroup(Integer groupID, Date startDate, Date endDate, GroupHierarchy gh, boolean includeSubGroups)
    {
    	logger.debug("getScoreForGroup groupID: " + groupID);
    	List<Integer> groupIDList = new ArrayList<Integer>();
    	if (includeSubGroups)
    		groupIDList = gh.getGroupIDList(groupID);
    	else
    		groupIDList.add(groupID);
        List<Composite> rowKeys = new ArrayList<Composite>(); 
        for (Integer groupId : groupIDList)
        	rowKeys.addAll(createDateIDKeys(startDate, endDate, groupID));
        	
        String columnFamily = getDriverGroupAggCF(Duration.BINSIZE_1_DAY);
        return getScoreForGroup(rowKeys, columnFamily);
    }

    public Score getScoreForGroup(Integer groupID, Integer durationCode, GroupHierarchy gh, boolean includeSubGroups)
    {
    	logger.debug("getScoreForGroup groupID: " + groupID);
    	List<Integer> groupIDList = new ArrayList<Integer>();
    	if (includeSubGroups)
    		groupIDList = gh.getGroupIDList(groupID);
    	else
    		groupIDList.add(groupID);
        List<Composite> rowKeys = new ArrayList<Composite>(); 
        for (Integer groupId : groupIDList)
        	rowKeys.addAll(createDateIDKeys(getToday(TimeZone.getDefault()), groupId, durationCode, 1));
        	
        String columnFamily = getDriverGroupAggCF(durationCode);
        return getScoreForGroup(rowKeys, columnFamily);
    }
    
    private Score getScoreForGroup(List<Composite> rowKeys, String columnFamily)
    {
        CounterRows<Composite, String> rows = fetchAggsForKeys(columnFamily, rowKeys);
        Map<String, Map<String, Long>> scoreMap = summarize(rows, 1);
        Score score = new Score();
        for (Map.Entry<String, Map<String, Long>> entry : scoreMap.entrySet())
        {
        	Map<String, Long> columnMap = entry.getValue();
            score = convertToScore(columnMap);
        }
    	return score;    	
    }
    
    

    public Score convertToScore(Map<String, Long> map) {
    	Score score = new Score();
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            String key = entry.getKey();
            Long value = entry.getValue();
            if (key.contains("Penalty"))
                setField(score, key, convertLong2Double(value));
            else
            	setField(score, key, value);
        }
        score.setOverall(getResultForScoreType(ScoreType.SCORE_OVERALL, map));
        score.setSpeeding(getResultForScoreType(ScoreType.SCORE_SPEEDING, map));
        score.setSpeeding1(getResultForScoreType(ScoreType.SCORE_SPEEDING_21_30, map));
        score.setSpeeding2(getResultForScoreType(ScoreType.SCORE_SPEEDING_31_40, map));
        score.setSpeeding3(getResultForScoreType(ScoreType.SCORE_SPEEDING_41_54, map));
        score.setSpeeding4(getResultForScoreType(ScoreType.SCORE_SPEEDING_55_64, map));
        score.setSpeeding5(getResultForScoreType(ScoreType.SCORE_SPEEDING_65_80, map));
        score.setSeatbelt(getResultForScoreType(ScoreType.SCORE_SEATBELT, map));
        score.setAggressiveAccel(getResultForScoreType(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL, map));
        score.setAggressiveBrake(getResultForScoreType(ScoreType.SCORE_DRIVING_STYLE_HARD_BRAKE, map));
        score.setAggressiveTurn(getResultForScoreType(ScoreType.SCORE_DRIVING_STYLE_HARD_TURN, map));
        score.setAggressiveBump(getResultForScoreType(ScoreType.SCORE_DRIVING_STYLE_HARD_BUMP, map));
        score.setDrivingStyle(getResultForScoreType(ScoreType.SCORE_DRIVING_STYLE, map));
        return score;
    }

    public Trend convertToTrend(Map<String, Long> map) {
    	Trend trend = new Trend();
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            String key = entry.getKey();
            Long value = entry.getValue();
            setField(trend, key, value);
        }
        return trend;
    }

    private void setField(Object score, String name, Object value) {
        try {
            PropertyUtils.setProperty(score, name, value);
        } catch (InvocationTargetException e) {
            if (logger.isTraceEnabled()) {
                logger.trace("The property \"" + name + "\" could not be set to the value \"" + value + "\"", e);
            }
        } catch (NoSuchMethodException e) {
            if (logger.isTraceEnabled()) {
                logger.trace("The property \"" + name + "\" could not be set to the value \"" + value + "\"", e);
            }
        } catch (IllegalAccessException e) {
            if (logger.isTraceEnabled()) {
                logger.trace("The property \"" + name + "\" could not be set to the value \"" + value + "\"", e);
            }
            throw new MappingException(e);
        } catch (IllegalArgumentException e) {
            if (logger.isTraceEnabled()) {
                logger.trace("The property \"" + name + "\" could not be set to the value \"" + value + "\"", e);
            }
            throw new MappingException(e);
        } 
    }
}