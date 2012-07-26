package com.inthinc.pro.dao.cassandra;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.inthinc.noteservice.cassandra.CassandraDB;
import com.inthinc.noteservice.model.AggressiveDriving;
import com.inthinc.noteservice.model.Note;
import com.inthinc.noteservice.model.VDD;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.GroupHessianDAO;
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.MappingException;
import com.inthinc.pro.dao.hessian.mapper.SimpleMapper;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.CrashSummary;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.DriverScore;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.IdlePercentItem;
import com.inthinc.pro.model.ScoreItem;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreTypeBreakdown;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.SpeedPercentItem;
import com.inthinc.pro.model.TrendItem;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.Trend;
import com.inthinc.pro.model.event.AggressiveDrivingEvent;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.model.event.SeatBeltEvent;
import com.inthinc.pro.model.event.SpeedingEvent;
import com.inthinc.pro.scoring.ScoringFormulas;

import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.beans.CounterRow;
import me.prettyprint.hector.api.beans.CounterRows;
import me.prettyprint.hector.api.beans.CounterSlice;
import me.prettyprint.hector.api.beans.HCounterColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.MutationResult;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.MultigetSliceCounterQuery;
import me.prettyprint.hector.api.query.QueryResult;

public abstract class AggregationCassandraDAO extends GenericCassandraDAO
{

	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;
	
	//COLUMN FAMILY NAMES
	public final static String AGGDAY_CF = "agg";
	public final static String VEHICLEAGGINDEX_CF = "vehicleAggIndex";
	public final static String DRIVERAGGINDEX_CF = "driverAggIndex";
	public final static String AGGDRIVER_CF = "aggDriver";
	public final static String AGGVEHICLE_CF = "aggVehicle";
	public final static String AGGDRIVERMONTH_CF = "aggDriverMonth";
	public final static String AGGVEHICLEMONTH_CF = "aggVehicleMonth";
	public final static String AGGDRIVERGROUP_CF = "aggDriverGroup";
	public final static String AGGDRIVERGROUPMONTH_CF = "aggDriverGroupMonth";

	//COLUMN NAMES
	public static final String DRIVETIME = "driveTime";
	public static final String SPEEDPENALTY = "speedPenalty";
	public static final String SPEEDPENALTY1 = "speedPenalty1";
	public static final String SPEEDPENALTY2 = "speedPenalty2";
	public static final String SPEEDPENALTY3 = "speedPenalty3";
	public static final String SPEEDPENALTY4 = "speedPenalty4";
	public static final String SPEEDPENALTY5 = "speedPenalty5";
	public static final String SPEEDEVENTS = "speedEvents";
	public static final String SPEEDEVENTS1 = "speedEvents1";
	public static final String SPEEDEVENTS2 = "speedEvents2";
	public static final String SPEEDEVENTS3 = "speedEvents3";
	public static final String SPEEDEVENTS4 = "speedEvents4";
	public static final String SPEEDEVENTS5 = "speedEvents5";
	public static final String SPEEDODOMETER = "speedOdometer";
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
	public final static String ODOMETER = "odometer";
	public final static String ODOMETER6 = "odometer6";
	public final static String MPGODOMETERLIGHT = "mpgOdometer1";	
	public final static String MPGGALLIGHT = "mpgGal1";
	public final static String MPGODOMETERMEDIUM = "mpgOdometer2";	
	public final static String MPGGALMEDIUM = "mpgGal2";
	public final static String MPGODOMETERHEAVY = "mpgOdometer3";	
	public final static String MPGGALHEAVY = "mpgGal3";
	public final static String EMUFEATUREMASK = "emuFeatureMask";

	
	
	protected static final Logger logger = Logger.getLogger(AggregationCassandraDAO.class);
    protected static final Integer NO_SCORE = -1;

    private GroupDAO groupDAO;
    private VehicleDAO vehicleDAO;
    private DriverDAO driverDAO;
    
	
//    protected void dumpMap(Map<String, Object> map) {
//    	for (String key : map.keySet())
//    	{
//    		System.out.println(key + " = " + map.get(key));
//    	}
//	}
	
    public GroupDAO getGroupDAO() {
		return groupDAO;
	}

    public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}
	public VehicleDAO getVehicleDAO() {
		return vehicleDAO;
	}

	public void setVehicleDAO(VehicleDAO vehicleDAO) {
		this.vehicleDAO = vehicleDAO;
	}

	public DriverDAO getDriverDAO() {
		return driverDAO;
	}

	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
	}

    protected List<TrendItem> getTrendForAsset(Integer id, EntityType entityType, Integer binSize, Integer count)
    {
    	logger.debug("getTrendForAsset id: " + id + " binSize: " + binSize + " count: " + count);
    	
    	List<TrendItem> trendItemList = new ArrayList<TrendItem>();
    	
        List<Composite> rowKeys = createDateIDKeys(getToday(TimeZone.getDefault()), id, binSize, count); //TO DO: set correct timezone
        logger.debug("rowKeys: " + rowKeys);

        String cf = (entityType.equals(EntityType.ENTITY_DRIVER)) ? getDriverAggCF(binSize) : getVehicleAggCF(binSize); 
        CounterRows<Composite, String> rows = fetchAggsForKeys(cf, rowKeys);
//        Map<String, Map<String, Long>> driverMap = (binSize == Duration.BINSIZE_1_MONTH) ? summarizeByDate(rows, count) : summarizeByWeekID(rows, count);
        Map<String, Map<String, Long>> driverMap = summarizeByDate(rows, count);
        logger.debug("driverMap: " + driverMap);
        
        for (Map.Entry<String, Map<String, Long>> entry : driverMap.entrySet())
        {
        	logger.debug("entry.getKey(): " + entry.getKey());
        	Map<String, Long> columnMap = entry.getValue();
            for (ScoreType scoreType : EnumSet.allOf(ScoreType.class)) {
            	TrendItem item = new TrendItem();
                item.setScoreType(scoreType);
                item.setScore(getResultForScoreType(scoreType, columnMap));
                item.setDate(getDatefromString(entry.getKey(), TimeZone.getDefault()));
                if(scoreType == ScoreType.SCORE_SPEEDING_21_30)
                	item.setDistance(getValue(columnMap,SPEEDODOMETER1));
                else if(scoreType == ScoreType.SCORE_SPEEDING_31_40)
                	item.setDistance(getValue(columnMap,SPEEDODOMETER2));
                else if(scoreType == ScoreType.SCORE_SPEEDING_41_54)
                	item.setDistance(getValue(columnMap,SPEEDODOMETER3));
                else if(scoreType == ScoreType.SCORE_SPEEDING_55_64)
                	item.setDistance(getValue(columnMap,SPEEDODOMETER4));
                else if(scoreType == ScoreType.SCORE_SPEEDING_65_80)
                	item.setDistance(getValue(columnMap,SPEEDODOMETER5));
                else
                	item.setDistance(getValue(columnMap,ODOMETER6));
            	trendItemList.add(item);
            	
            	logger.debug("TrendItem: " + item);
        	}
        }
    	logger.debug("TrendItemList count(): " + trendItemList.size());
        return trendItemList;
    	
    }

    protected Group getGroup(Integer groupID)
	{
		Group group = groupDAO.findByID(groupID);
		return group;
	}

    protected List<Integer> getSubgroups(Integer groupID)
	{
		List<Group> groupList = groupDAO.getGroupHierarchy(-1, groupID);
		List<Integer> groupIDList = new ArrayList<Integer>();  
		for (Group group : groupList)
			groupIDList.add(group.getGroupID());

		return groupIDList;
	}

    protected List<Composite> createDateIDKeys(String aggDate, Integer ID, int binSize, int count)
	{
		List<Composite> dateIDList = new ArrayList<Composite>();
		GregorianCalendar cal = (GregorianCalendar) Calendar.getInstance();
	    int year = Integer.parseInt(aggDate.substring(0,4));
	    int month = Integer.parseInt(aggDate.substring(5,7));
	    int day = Integer.parseInt(aggDate.substring(8,10));
	    cal.set(year, month-1, day, 0, 0, 0);


        int period = Duration.getCalendarPeriod(binSize);
        int amount = -1;
        switch (binSize)
        {
	        case Duration.BINSIZE_1_DAY:
	        	amount = (count)-1;
	        	break;
	        case Duration.BINSIZE_7_DAY:
//	        	amount = (count+7);
	        	amount = (count-1);
	        	break;
	        case Duration.BINSIZE_30_DAYS:
	        	amount = (count+30)-1;
	        	break;

	        case Duration.BINSIZE_1_MONTH:
	        	amount = (1*count)-1;
	        	break;
	        case Duration.BINSIZE_3_MONTHS:
	        	amount = (3*count)-1;
	        	break;
	        case Duration.BINSIZE_6_MONTHS:
	        	amount = (6*count)-1;
	        	break;
	        case Duration.BINSIZE_12_MONTHS:
	        	amount = (12*count)-1;
	        	break;
        }

        
        if (amount != -1)
        {
        	String format = "yyyy-MM-dd";
        	if (period == Calendar.MONTH)
        		format = "yyyy-MM";
        	
        	SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        	dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        	for(; amount >= 0; amount-- )
        	{
    			Composite key = new Composite();
    			key.add(0, dateFormat.format(cal.getTime().getTime()));
    			key.add(1, ID);
    			dateIDList.add(key);
        		cal.add(period, -1);
        	}
        	
        	
        }
        return dateIDList;
	}
    
    protected List<Composite> createDateIDKeys(Date startDate, Date endDate, Integer ID)
	{
		List<Composite> dateIDList = new ArrayList<Composite>();
		GregorianCalendar cal = (GregorianCalendar) Calendar.getInstance();
	    cal.setTimeZone(TimeZone.getTimeZone("UTC"));
		cal.setTime(startDate);
	    

    	String format = "yyyy-MM-dd";
    	SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    	dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    	while(cal.getTime().getTime() <= endDate.getTime())
    	{
			Composite key = new Composite();
			key.add(0, dateFormat.format(cal.getTime().getTime()));
			key.add(1, ID);
			dateIDList.add(key);
    		cal.add(Calendar.DATE, 1);
        }
        return dateIDList;
	}

    protected CounterRows<Composite, String> fetchAggsForKeys(String AGG_CF, List<Composite> keys)
    {
        MultigetSliceCounterQuery<Composite, String> sliceQuery = HFactory.createMultigetSliceCounterQuery(getKeyspace(), compositeSerializer, stringSerializer);
        
        sliceQuery.setColumnFamily(AGG_CF);            
        sliceQuery.setRange("", "", false, 1000);  //get all the columns
        sliceQuery.setKeys(keys);
        
        QueryResult<CounterRows<Composite, String>> result = sliceQuery.execute();
        CounterRows<Composite, String> rows = result.get();            
        return rows;
		
    }
    
    protected Map<String, Map<String, Long>>  summarizeByID(CounterRows<Composite, String> rows, int count)
    {
    	return summarize(rows, false, true, false, count);
    }
    
    protected Map<String, Map<String, Long>>  summarizeByDate(CounterRows<Composite, String> rows, int count)
    {
    	return summarize(rows, true, false, false, count);
    }

    protected Map<String, Map<String, Long>>  summarizeByDateID(CounterRows<Composite, String> rows, int count)
    {
    	return summarize(rows, true, true, false, count);
    }

    protected Map<String, Map<String, Long>>  summarizeByWeekID(CounterRows<Composite, String> rows, int count)
    {
    	return summarize(rows, true, true, true, count);
    }

    protected Map<String, Map<String, Long>>  summarizeByWeek(CounterRows<Composite, String> rows, int count)
    {
    	return summarize(rows, true, false, true, count);
    }

    protected Map<String, Map<String, Long>>  summarize(CounterRows<Composite, String> rows, int count)
    {
    	return summarize(rows, false, false, false, count);
    }

    protected Map<String, Map<String, Long>> summarize(CounterRows<Composite, String> rows, boolean includeAggDate, boolean includeID, boolean bucketByWeek, int count)
    {
    	//An Aggregate composite rowkey consists of 1.) AggDate (2.) ID of either driver,vehicle,group
        TreeMap<String, Map<String, Long>>keys2ColumnsMap = new TreeMap<String, Map<String, Long>>(new RowkeyDecreaseComparator());

        for (CounterRow<Composite, String> row : rows)
        {
        	String key = "";
        	
//        	String aggDate = row.getKey().getComponent(0).toString();
        	String aggDate = stringSerializer.fromByteBuffer((ByteBuffer) row.getKey().get(0));
        	
        	Date startDate = getDatefromString(aggDate);
        	Date endDate = startDate;
        	
        	if (bucketByWeek)
        		endDate = addWeek(startDate);
        	
        	String id = bigIntegerSerializer.fromByteBuffer((ByteBuffer) row.getKey().get(1)).toString();
        	
        	while (startDate.getTime() <= endDate.getTime())
        	{
        		key = "";
        		aggDate = getDateStringfromDate(startDate);
	        	if (includeAggDate)
	        		key += aggDate;
	        	
	        	if (includeID)
	        	{
	        		if (!key.isEmpty())
	        			key += ":";
	        		key += id;
	        	}
	        	logger.debug("key: " + key);	    			
	
	
	        	Map<String, Long> columnMap = (Map<String, Long>) keys2ColumnsMap.get(key);
	        	if (columnMap == null)
	        		columnMap = new HashMap<String, Long>();
	
				CounterSlice<String> columnSlice = row.getColumnSlice();
	        	List<HCounterColumn<String>> columnList = columnSlice.getColumns();
	        	for (HCounterColumn<String> column : columnList)
	        	{
	    			Long value = getValue(columnMap,column.getName());
	    			if (value == null)
	    				value = 0L;
	    			value += column.getValue();
	    			
	    			columnMap.put(column.getName(), value);
	        	}
				keys2ColumnsMap.put(key, columnMap);
				startDate  = addDay(startDate);
				logger.debug("startDate: " + startDate);	    			
        	}
        }
        
        //List currently ordered by date descending. Truncate to count.
/*        
		while (keys2ColumnsMap.size() > count)
		{
			String key = (String)keys2ColumnsMap.lastKey();
			keys2ColumnsMap.remove(key);
		}
*/		
		
//		return new TreeMap<String, Map<String, Long>>(keys2ColumnsMap);  //Change the sort back to ascending order.
		return keys2ColumnsMap;
    }
    
    protected Integer getResultForScoreType(ScoreType scoreType, Map<String, Long> columnMap)
    {
    	Double score = 0.0;
    	long totalMiles = getValue(columnMap,ODOMETER6);

    	long speedPenalty = 0;
    	long stylePenalty = 0;
    	long seatbeltPenalty = 0;
    	
    	logger.debug("ScoreType: " + scoreType);
    	logger.debug("columnMap: " + columnMap);
    	
    	switch (scoreType)
    	{
    		case SCORE_OVERALL:
    	    	speedPenalty = getValue(columnMap, SPEEDPENALTY1)+getValue(columnMap, SPEEDPENALTY2)+getValue(columnMap, SPEEDPENALTY3)+getValue(columnMap, SPEEDPENALTY4)+getValue(columnMap, SPEEDPENALTY5);
    	    	stylePenalty = getValue(columnMap,AGGRESSIVEBRAKEPENALTY)+getValue(columnMap, AGGRESSIVEACCELPENALTY)+getValue(columnMap,AGGRESSIVELEFTPENALTY)+getValue(columnMap,AGGRESSIVERIGHTPENALTY)+getValue(columnMap,AGGRESSIVEBUMPPENALTY);
    	    	seatbeltPenalty = getValue(columnMap,SEATBELTPENALTY);
    	    	score = ScoringFormulas.getOverallFromPenalty(totalMiles, convertLong2Double(seatbeltPenalty), convertLong2Double(stylePenalty), convertLong2Double(speedPenalty));
    	    	break;
    		case SCORE_SPEEDING:
    	    	speedPenalty = getValue(columnMap,SPEEDPENALTY1)+getValue(columnMap,SPEEDPENALTY2)+getValue(columnMap,SPEEDPENALTY3)+getValue(columnMap,SPEEDPENALTY4)+getValue(columnMap,SPEEDPENALTY5);
    	    	score = ScoringFormulas.getSpeedingScore(totalMiles, convertLong2Double(speedPenalty));
    			break;
    		case SCORE_SEATBELT:
    	    	seatbeltPenalty = getValue(columnMap,SEATBELTPENALTY);
    	    	score = ScoringFormulas.getSeatBeltScore(totalMiles, convertLong2Double(seatbeltPenalty));
    			break;
    		case SCORE_DRIVING_STYLE:
    	    	stylePenalty = getValue(columnMap,AGGRESSIVEBRAKEPENALTY)+getValue(columnMap,AGGRESSIVEACCELPENALTY)+getValue(columnMap,AGGRESSIVELEFTPENALTY)+getValue(columnMap,AGGRESSIVERIGHTPENALTY)+getValue(columnMap,AGGRESSIVEBUMPPENALTY);
    	    	score = ScoringFormulas.getStyleScore(totalMiles, convertLong2Double(stylePenalty));
    			break;
    		case SCORE_COACHING_EVENTS:
    	    	score = (double)getValue(columnMap,SPEEDCOACHING1)+getValue(columnMap,SPEEDCOACHING2)+getValue(columnMap,SPEEDCOACHING3)+getValue(columnMap,SPEEDCOACHING4)+getValue(columnMap,SPEEDCOACHING5);
    			break;
    	    // subTypes of SPEEDING
    		case SCORE_SPEEDING_21_30:
    	    	speedPenalty = getValue(columnMap,SPEEDPENALTY1);
    	    	score = ScoringFormulas.getSpeedingScore(totalMiles, convertLong2Double(speedPenalty));
    			break;
    		case SCORE_SPEEDING_31_40:
    	    	speedPenalty = getValue(columnMap,SPEEDPENALTY2);
    	    	score = ScoringFormulas.getSpeedingScore(totalMiles, convertLong2Double(speedPenalty));
    			break;
    		case SCORE_SPEEDING_41_54:
    	    	speedPenalty = getValue(columnMap,SPEEDPENALTY3);
    	    	score = ScoringFormulas.getSpeedingScore(totalMiles, convertLong2Double(speedPenalty));
    			break;
    		case SCORE_SPEEDING_55_64:
    	    	speedPenalty = getValue(columnMap,SPEEDPENALTY4);
    	    	score = ScoringFormulas.getSpeedingScore(totalMiles, convertLong2Double(speedPenalty));
    			break;
    		case SCORE_SPEEDING_65_80:
    	    	speedPenalty = getValue(columnMap,SPEEDPENALTY5);
    	    	score = ScoringFormulas.getSpeedingScore(totalMiles, convertLong2Double(speedPenalty));
    			break;
    		case SCORE_SEATBELT_30_DAYS:
    		case SCORE_SEATBELT_3_MONTHS:
    		case SCORE_SEATBELT_6_MONTHS:
    		case SCORE_SEATBELT_12_MONTHS:
    			break;
    	    // subTypes of DRIVING_STYLE
    		case SCORE_DRIVING_STYLE_HARD_BRAKE:
    	    	stylePenalty = getValue(columnMap,AGGRESSIVEBRAKEPENALTY);
    	    	score = ScoringFormulas.getStyleScore(totalMiles, convertLong2Double(stylePenalty));
    	    	break;
    		case SCORE_DRIVING_STYLE_HARD_ACCEL:
    	    	stylePenalty = getValue(columnMap,AGGRESSIVEACCELPENALTY);
    	    	score = ScoringFormulas.getStyleScore(totalMiles, convertLong2Double(stylePenalty));
    	    	break;
    		case SCORE_DRIVING_STYLE_HARD_TURN:
    	    	stylePenalty = getValue(columnMap,AGGRESSIVELEFTPENALTY) + getValue(columnMap,AGGRESSIVERIGHTPENALTY);
    	    	score = ScoringFormulas.getStyleScore(totalMiles, convertLong2Double(stylePenalty));
    	    	break;
    		case SCORE_DRIVING_STYLE_HARD_BUMP:
    	    	stylePenalty = getValue(columnMap,AGGRESSIVEBUMPPENALTY);
    	    	score = ScoringFormulas.getStyleScore(totalMiles, convertLong2Double(stylePenalty));
    	    	break;
    		case SCORE_DRIVING_STYLE_HARD_LTURN:
    	    	stylePenalty = getValue(columnMap,AGGRESSIVELEFTPENALTY);
    	    	score = ScoringFormulas.getStyleScore(totalMiles, convertLong2Double(stylePenalty));
    	    	break;
    		case SCORE_DRIVING_STYLE_HARD_RTURN:
    	    	stylePenalty = getValue(columnMap,AGGRESSIVERIGHTPENALTY);
    	    	score = ScoringFormulas.getStyleScore(totalMiles, convertLong2Double(stylePenalty));
    	    	break;
    	}

    	logger.debug("speedPenalty: " + speedPenalty + " " + convertLong2Double(speedPenalty));
    	logger.debug("stylePenalty: " + stylePenalty + " " + convertLong2Double(stylePenalty));
    	logger.debug("seatbeltPenalty: " + seatbeltPenalty + " " + convertLong2Double(seatbeltPenalty));
    	
    	score = score*10;
    	logger.debug("Score: " + score);
    	return (int) Math.round(score);
    }
    
    protected String getKeyPart(String key, int part)
    {
    	String keyPart = "";
    	if (part == 1)
    		keyPart = key.substring(0, key.indexOf(":") - 1);  
    	if (part == 2)
    		keyPart = key.substring(key.indexOf(":") + 1);
    	
    	return keyPart;
    }
    
    protected Date getEndingDate(String strDate)
    {
		//We are converting a Trip Day (UTC) into a Driver Day (Driver TZ)
	    GregorianCalendar cal = new GregorianCalendar();
//	    cal.setTimeZone(driverTZ);
	    int year = Integer.parseInt(strDate.substring(0,4));
	    int month = Integer.parseInt(strDate.substring(5,7));
	    int day = (strDate.length() < 8) ? 1 :Integer.parseInt(strDate.substring(8,10));
	    cal.set(year, month-1, day, 0, 0, 0);

	    if (strDate.length() < 8)
	    	cal.add(Calendar.DATE, 1);
	    else
	    	cal.add(Calendar.MONTH, 1);

	    return cal.getTime();
    }
    
	protected double convertLong2Double(long penaltyAsDouble)
	{
		return penaltyAsDouble/10000D;
	}
	
	private long convertDouble2Long(double penaltyAsDouble)
	{
		return (long)(Math.round(penaltyAsDouble*10000));
	}


	protected String getDriverAggCF(Integer binSize)
	{
	    String aggCF = AGGDRIVER_CF;
	    if (Duration.getCalendarPeriod(binSize) == Calendar.MONTH)
	    	aggCF = AGGDRIVERMONTH_CF;
	    return aggCF;
	}

	protected String getVehicleAggCF(Integer binSize)
	{
	    String aggCF = AGGVEHICLE_CF;
	    if (Duration.getCalendarPeriod(binSize) == Calendar.MONTH)
	    	aggCF = AGGVEHICLEMONTH_CF;
	    return aggCF;
	}

	protected String getDriverGroupAggCF(Integer binSize)
	{
	    String aggCF = AGGDRIVERGROUP_CF;
	    if (Duration.getCalendarPeriod(binSize) == Calendar.MONTH)
	    	aggCF = AGGDRIVERGROUPMONTH_CF;
	    return aggCF;
	}


	protected int getScoreQuintile(int score)
	{
	  	if (score >= 41)
	  		return 4;
	  	else if (score >= 31)
	  		return 3;
	  	else if (score >= 21)
	  		return 2;
	  	else if (score >= 11)
	  		return 1;
	  	else return 0;
	}

	protected String getToday(TimeZone tz)
	{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(tz);
        return dateFormat.format(new Date());
	}

	protected Date getDatefromString(String strDate)
	{
		return getDatefromString(strDate, TimeZone.getTimeZone("UTC"));
	}

	protected Date getDatefromString(String strDate, TimeZone tz)
	{
		logger.debug("getDatefromString: " + strDate);
		
		if (strDate.contains(":"))
			strDate = strDate.substring(0, strDate.indexOf(":"));
		//if passing in a month
		if (strDate.length() < 9)
			strDate += "-01";

	    Calendar cal = new GregorianCalendar();
	    cal.setTimeZone(tz);
	    int year = Integer.parseInt(strDate.substring(0,4));
	    int month = Integer.parseInt(strDate.substring(5,7));
	    int day = Integer.parseInt(strDate.substring(8,10));
	    cal.set(year, month-1, day, 0, 0, 0);
	    return cal.getTime();
	}
	
	protected String getDateStringfromDate(Date date)
	{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
	}
	
	protected Date addDay(Date endDate)
	{
		return addDays(endDate, 1);
	}

	protected Date addWeek(Date endDate)
	{
		return addDays(endDate, 7);
	}

	protected Date addDays(Date endDate, int days)
	{
	    Calendar endCal = Calendar.getInstance();
	    endCal.setTime(endDate);
	    endCal.add(Calendar.DATE, days);
	    
        return endCal.getTime();
	}

	
	protected long getValue(Map<String, Long> columnMap, String column)
	{
		Long value = columnMap.get(column);
		if (value == null)
			value = 0L;
		
		return value;
	}

	
	protected class RowkeyDecreaseComparator implements Comparator<String> {
	    @Override
	    public int compare(String s1, String s2) {
	    	return s1.compareTo(s2);
	    }
	}
	
	protected void updateAggregates(Event event, int multiplier)
	{
		Map<String, Long> counterColumns = new HashMap<String, Long>();
		if (event.getEventType() == EventType.SPEEDING)
			updateSpeedingFields((SpeedingEvent) event, counterColumns, multiplier);
		if (event.getEventType() == EventType.HARD_BRAKE || event.getEventType() == EventType.HARD_ACCEL
			|| event.getEventType() == EventType.HARD_TURN || event.getEventType() == EventType.HARD_VERT)
			updateAggressiveDrivingFields((AggressiveDrivingEvent) event, counterColumns, multiplier);
		if (event.getEventType() == EventType.SEATBELT)
			updateSeatbeltFields((SeatBeltEvent) event, counterColumns, multiplier);
		updateAggregateRecords(event, counterColumns);
	}

	private void updateSpeedingFields(SpeedingEvent event, Map<String, Long> counterColumns, int multiplier)
	{
		logger.info("updateSpeedingFields distance: " + event.getDistance() + " speedLimit: " + event.getSpeedLimit() + " topSpeed: " + event.getTopSpeed() + " range: " + getRange(event.getSpeedLimit()));
		if (event.getTopSpeed() > 0 && event.getDistance() > 0 && event.getSpeedLimit() > 0 && event.getTopSpeed() > event.getSpeedLimit())
		{
			long speedEvents = 0;
			long speedOdometer = 0;
			long speedOver = 0;

			
			
			
			double penaltyAsDouble = Math.pow((event.getTopSpeed()-event.getSpeedLimit())/(event.getSpeedLimit() * 1.0), 2) * event.getDistance(); 
			logger.info("penaltyAsDouble: " + penaltyAsDouble);
			long penalty =	convertDouble2Long(penaltyAsDouble) * multiplier;

			String range = getRange(event.getSpeedLimit());
			counterColumns.put(SPEEDPENALTY + range, penalty);
			speedEvents = multiplier * 1;
			counterColumns.put(SPEEDEVENTS + range, speedEvents);
			speedOdometer = multiplier * event.getDistance();
			counterColumns.put(SPEEDODOMETER + range, speedOdometer);
			if (event.getAvgSpeed() > event.getSpeedLimit())
			{
				speedOver = (event.getAvgSpeed() - event.getSpeedLimit()) * speedOdometer;
				counterColumns.put(SPEEDOVER + range, speedOver);
			}
			if (between(event.getTopSpeed() - event.getSpeedLimit(), 1, 7))
				counterColumns.put(SPEEDEVENTS_1TO7_OVER, 1L * multiplier);

			if (between(event.getTopSpeed() - event.getSpeedLimit(), 8, 14))
				counterColumns.put(SPEEDEVENTS_8TO14_OVER, 1L * multiplier);
			
			if (between(event.getTopSpeed() - event.getSpeedLimit(), 15, 100))
				counterColumns.put(SPEEDEVENTS_15PLUS_OVER, 1L * multiplier);
			
			if (event.getTopSpeed() > 80)
				counterColumns.put(SPEEDEVENTS_OVER_80, 1L * multiplier);
		}
	}
	
	private void updateAggressiveDrivingFields(AggressiveDrivingEvent event, Map<String, Long> counterColumns, int multiplier)
	{
		logger.info("AggressiveDrivingEvent type: " + event.getEventType());  
		if (event.getEventType() == EventType.HARD_BRAKE || event.getEventType() == EventType.HARD_ACCEL)
		{
			double adjSpeed = event.getSpeed() + Math.abs(event.getDeltaX())/10;
			double penaltyAsDouble = adjSpeed*adjSpeed*event.getDeltaX()*event.getDeltaX();  
			long penalty =	convertDouble2Long(penaltyAsDouble) * multiplier;
			if (event.getEventType() == EventType.HARD_BRAKE)
			{
				counterColumns.put(AGGRESSIVEBRAKEPENALTY, penalty);
				counterColumns.put(AGGRESSIVEBRAKEEVENTS, multiplier * 1L);
			}
			else
			{
				counterColumns.put(AGGRESSIVEACCELPENALTY, penalty);
				counterColumns.put(AGGRESSIVEACCELEVENTS, multiplier * 1L);
			}
		}

		if (event.getEventType() == EventType.HARD_TURN)
		{
			double adjSpeed = (event.getSpeed()) < 5 ? 5 : event.getSpeed();
			double penaltyAsDouble = adjSpeed*adjSpeed*event.getDeltaY()*event.getDeltaY();  
			long penalty =	convertDouble2Long(penaltyAsDouble) * multiplier;
			if ((event.getDeltaY() < 0))
			{
				counterColumns.put(AGGRESSIVELEFTPENALTY, penalty);
				counterColumns.put(AGGRESSIVELEFTEVENTS, multiplier * 1L);
			}
			else
			{
				counterColumns.put(AGGRESSIVERIGHTPENALTY, penalty);
				counterColumns.put(AGGRESSIVERIGHTEVENTS, multiplier * 1L);
			}
		}

		if (event.getEventType() == EventType.HARD_VERT)
		{
			double adjSpeed = (event.getSpeed()) < 5 ? 5 : event.getSpeed();
			double penaltyAsDouble = (adjSpeed*adjSpeed*event.getDeltaZ()*event.getDeltaZ())/9.0;  
			long penalty =	convertDouble2Long(penaltyAsDouble) * multiplier;
			counterColumns.put(AGGRESSIVEBUMPPENALTY, penalty);
			counterColumns.put(AGGRESSIVEBUMPEVENTS, multiplier * 1L);
		}
	}
	
	private void updateSeatbeltFields(SeatBeltEvent event, Map<String, Long> counterColumns, int multiplier)
	{
		if (event.getTopSpeed() > 0 && event.getDistance() > 0)
		{
			double penaltyAsDouble = Math.pow((event.getTopSpeed()), 2) * event.getDistance(); 
			long penalty =	convertDouble2Long(penaltyAsDouble) * multiplier;

			counterColumns.put(SEATBELTPENALTY, penalty);
			counterColumns.put(SEATBELTEVENTS, multiplier * 1L);
		}
	}

	private void updateAggregateRecords(Event event, Map<String, Long> counterColumns)
	{
		if (counterColumns.size() > 0)
		{

			logger.info("vehicleID: " + event.getVehicleID() + " DriverID: " + event.getDriverID() + " noteTime: " +  event.getTime());
	    	Driver driver = driverDAO.findByID(event.getDriverID());
	    	TimeZone tz = driver.getPerson().getTimeZone();
			
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateFormat.setTimeZone(tz);
			String aggDay = dateFormat.format(event.getTime());
			String aggMonth = aggDay.substring(0, 7);
			
		    Composite aggDayKey = createKey(aggDay, event.getDriverID(), event.getVehicleID());
		    Composite aggDriverKey = createKey(aggDay, event.getDriverID());
		    Composite aggVehicleKey = createKey(aggDay, event.getVehicleID());
		    Composite aggDriverMonthKey = createKey(aggMonth, event.getDriverID());
		    Composite aggVehicleMonthKey = createKey(aggMonth, event.getVehicleID());

		    Composite aggDriverGroupKey = createKey(aggDay, driver.getGroupID());
		    Composite aggDriverGroupMonthKey = createKey(aggMonth, driver.getGroupID());
		    
			

		    
		    Mutator<Composite> m = HFactory.createMutator(CassandraDB.getKeyspace(), compositeSerializer);	
			
		   try {
				for (Map.Entry<String, Long> entry: counterColumns.entrySet())
				{
					logger.debug("updateAggregateRecords: " + entry.getKey() + " " + entry.getValue());
					m.addCounter(aggDayKey, AGGDAY_CF, HFactory.createCounterColumn(entry.getKey(), entry.getValue(), stringSerializer));
					m.addCounter(aggDriverKey, AGGDRIVER_CF, HFactory.createCounterColumn(entry.getKey(), entry.getValue(), stringSerializer));
					m.addCounter(aggVehicleKey, AGGVEHICLE_CF, HFactory.createCounterColumn(entry.getKey(), entry.getValue(), stringSerializer));
					m.addCounter(aggDriverMonthKey, AGGDRIVERMONTH_CF, HFactory.createCounterColumn(entry.getKey(), entry.getValue(), stringSerializer));
					m.addCounter(aggVehicleMonthKey, AGGVEHICLEMONTH_CF, HFactory.createCounterColumn(entry.getKey(), entry.getValue(), stringSerializer));
					m.addCounter(aggDriverGroupKey, AGGDRIVERGROUP_CF, HFactory.createCounterColumn(entry.getKey(), entry.getValue(), stringSerializer));
					m.addCounter(aggDriverGroupMonthKey, AGGDRIVERGROUPMONTH_CF, HFactory.createCounterColumn(entry.getKey(), entry.getValue(), stringSerializer));
				}
				MutationResult mr = m.execute();
			} catch (Exception e)
			{
				logger.error("Exception: " + e);
			}
		}
	}



	private boolean between(int val, int low, int high)
	{
		return  (val >= low && val <= high);
	}

	private String getRange(int speedVal)
	{
		String range = "1";
		if (speedVal > 30 && speedVal < 41)
			range = "2";
		if (speedVal > 40 && speedVal < 55)
			range = "3";
		if (speedVal > 54 && speedVal < 65)
			range = "4";
		if (speedVal > 64)
			range = "5";
		
		return range;
	}
	
    private Composite createKey(String date, int id)
    {
        Composite composite = new Composite();
        composite.add(0, date);
        composite.add(1, id);
        return composite;
    }

    private Composite createKey(String date, int id1, int id2)
    {
        Composite composite = new Composite();
        composite.add(0, date);
        composite.add(1, id1);
        composite.add(2, id2);
        return composite;
    }

}
