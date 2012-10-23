package com.inthinc.pro.reports;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.joda.time.Interval;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.reports.model.ChartData;
import com.inthinc.pro.reports.util.MessageUtil;
import com.inthinc.pro.reports.util.TimeFrameUtil;

public class ReportCriteria
{
    private List mainDataset;
    private Map<String, Object> paramMap = new HashMap<String, Object>();
    private ReportType report;
    private Duration duration;
    private TimeFrame timeFrame;
	private Integer recordsPerReport;
    private String mainDatasetIdField;
    private String chartDataSetIdField;
    private Locale locale;
    private Boolean useMetric;
    private MeasurementType measurementType;
    private FuelEfficiencyType fuelEfficiencyType;
    private TimeZone timeZone;
    private Boolean includeInactiveDrivers = DEFAULT_EXCLUDE_INACTIVE_DRIVERS;
    private Boolean includeZeroMilesDrivers = DEFAULT_EXCLUDE_ZERO_MILES_DRIVERS;

    private static final String INTHINC_NAME = "Inthinc";
    private static final String REPORT_DATE_STRING = "REPORT_DATE_AS_STRING";
    private static final String USE_METRIC = "USE_METRIC";

    public static final String SUB_DATASET = "SUB_DATASET";
    public static final String RECORD_COUNT = "RECORD_COUNT";
    public static final String REPORT_START_DATE = "REPORT_START_DATE";
    public static final String REPORT_END_DATE = "REPORT_END_DATE";
    public static final String FUEL_EFFICIENCY_TYPE = "FUEL_EFFICIENCY_TYPE";
    public static final String GROUP = "GROUP";
    
    public static final String GROUP_SEPARATOR="->";
    public static final String SLASH_GROUP_SEPERATOR = "/";
    public static final String DATE_FORMAT = "MM/dd/yyyy";
    public static final String REPORT_IFTA_ONLY = "iftaVehiclesOnly";

    public static final boolean DEFAULT_EXCLUDE_INACTIVE_DRIVERS = false;
    public static final boolean DEFAULT_EXCLUDE_ZERO_MILES_DRIVERS = false;
    public static final boolean DEFAULT_INCLUDE_INACTIVE_DRIVERS = true;
    public static final boolean DEFAULT_INCLUDE_ZERO_MILES_DRIVERS = true;
    private int subsetIndex = 1;
    
    
    
    //Initialization Block
    {
        paramMap.put(USE_METRIC, Boolean.FALSE);
    }
    public ReportCriteria()
    {
        
    }
    public ReportCriteria(ReportCriteria reportCriteria)
    {
        setReport(reportCriteria.getReport());
        paramMap.put("ENTITY_NAME", reportCriteria.getPramMap().get("ENTITY_NAME"));
        paramMap.put("REPORT_NAME", report.toString());
        paramMap.put("REPORT_LOCALE", reportCriteria.getLocale());
        this.locale = reportCriteria.getLocale();
    }

    public ReportCriteria(ReportType report, String entityName, Locale locale)
    {
        setReport(report);
        paramMap.put("ENTITY_NAME", entityName);
        paramMap.put("REPORT_NAME", report.toString());
        paramMap.put("REPORT_LOCALE", locale);
        this.locale = locale;
       
    }
    
    /**
     * Determines whether this driver should be included in a report based on params. Used when driver and totalMiles are already KNOWN.
     * 
     * @param driver
     *            the Driver object in question
     * @param totalMiles
     *            the total number of miles for the timeframe of the report OR a non-zero int indicating to include this driver
     * @return true if the driver should be included
     */
    public boolean includeDriver(Status driverStatus, Integer totalMiles) {
        //TODO: this method also exists in HosReportCriteria  refactor to only maintain one version
        boolean includeThisInactiveDriver = (getIncludeInactiveDrivers() && totalMiles != 0);
        boolean includeThisZeroMilesDriver = (getIncludeZeroMilesDrivers() && driverStatus.equals(Status.ACTIVE));
        return ((driverStatus.equals(Status.ACTIVE) && totalMiles != 0)       // by default we include drivers that are active and have miles 
                || (getIncludeInactiveDrivers() && getIncludeZeroMilesDrivers())    // if user has selected to include both then we need not filter
                || includeThisInactiveDriver                                        // test for this individual driver based on status
                || includeThisZeroMilesDriver);                                     // test for this individual driver based on miles
    }
    /**
     * Determines whether this driver should be included in a report based on params including:
     * 
     * @param driverDAO
     *            necessary to find Driver
     * @param driverID
     *            Integer id of the Driver in question
     * @param interval
     *            the time interval being queried
     * @return true if the driver should be included
     */
    public boolean includeDriver(DriverDAO driverDAO, Integer driverID, Interval interval) {
        Integer totalMiles = 0;
        Status driverStatus = Status.INACTIVE;
        if(includeZeroMilesDrivers){
            totalMiles = 1;
        } else{
            //actually need to determine drivers miles
            List<Trip> trips = driverDAO.getTrips(driverID, interval);
            
            for (Trip trip : trips) {
                totalMiles += trip.getMileage();
            }
        }
        if(includeInactiveDrivers) {
            driverStatus = Status.ACTIVE;
        } else {
            //actually need to determine driver's status
            Driver driver = driverDAO.findByID(driverID);
            driverStatus = driver.getStatus();
        }
        return includeDriver(driverStatus, totalMiles);
    }

    public void setMainDataset(List mainDataset)
    {
        this.paramMap.put(RECORD_COUNT, mainDataset.size());
        this.mainDataset = mainDataset;
    }

    public List getMainDataset()
    {
        return this.mainDataset;
    }

    private void setReport(ReportType report)
    {
        this.report = report;
    }

    public ReportType getReport()
    {
        return report;
    }

    public ReportCriteria addParameter(String name, Object value)
    {
        paramMap.put(name, value);
        return this;
    }

    public Object getParameter(String key)
    {
        return paramMap.get(key);
    }
    
    
    public void addPramMap(Map<String, Object> paramMap)
    {
        this.paramMap.putAll(paramMap);
    }

    public <T extends ChartData> void addChartDataSet(List<T> subDataSet)
    {
        String index = String.valueOf(subsetIndex++);
        paramMap.put(SUB_DATASET + "_" + index, subDataSet);
    }

    public Map<String, Object> getPramMap()
    {
        return this.paramMap;
    }

    public void setDuration(Duration duration)
    {
        paramMap.put("DURATION", duration.toString());
        this.duration = duration;
    }
    
    public void setUseMetric(Boolean useMetric)
    {
        paramMap.put(USE_METRIC, useMetric);
        this.useMetric = useMetric;
    }
    
    public Boolean getUseMetric(){
        return useMetric;
    }

    public Duration getDuration()
    {
        return duration;
    }

    public String getCopyRight()
    {
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR)) + " " + INTHINC_NAME;
    }
    
    /**
     * 
     * @param mainDatasetIdField = this is the field from the records in the main dataset to which the value will 
     * match the value from the field defined by chartDataSetIdField in the ChartDataSet redord. 
     * If the categoryDataSetIdField is not set then "seriesID" will be used. This is used so that the chart data matches the main 
     * data set if the records are to be broken up into seperate reports. This will be indicated
     * if the recordsPerReport attribute is set.
     * 
     * example: You may want to following to be used the following comparism. CategorySeriesData.seriesID = MpgEntityPkg.entity.entityName. 
     * In this case, the mainDataSetIdField should be set to "entity.entityNam"e
     * 
     * @param chartDataSetIdField this is the field from the chartDataSet record which will be matched to the field defined by 
     * the mainDatasetIdField in the main data set.
     */
    public void setRecordsPerReportParameters(Integer recordsPerReport,String mainDatasetIdField,String chartDataSetIdField)
    {
        this.recordsPerReport = recordsPerReport;
        this.mainDatasetIdField = mainDatasetIdField;
        this.chartDataSetIdField = chartDataSetIdField;
    }
    
    /**
     * This takes a Date and Timezone, formats the date according to the timezone and puts it in the reports parameter map.
     * This method was added to maximize code reuse. 
     * 
     * @param date Date of the report.
     * @param timeZone Time Zone to 
     */
    public void setReportDate(Date date,TimeZone timeZone){
        addDateParameter(REPORT_DATE_STRING,date,timeZone);
    }
    
    public void addDateParameter(String parameter,Date date,TimeZone timeZone){
        SimpleDateFormat sdf = new SimpleDateFormat(MessageUtil.getMessageString("dateFormat", locale), locale);
        sdf.setTimeZone(timeZone);
        paramMap.put(parameter, sdf.format(date));
    }

    public Integer getRecordsPerReport()
    {
        return recordsPerReport;
    }
    
    public String getMainDataSetIdField()
    {
        return mainDatasetIdField;
    }

    public String getChartDataSetIdField()
    {
        return chartDataSetIdField;
    }

    public void setLocale(Locale locale)
    {
        paramMap.put("REPORT_LOCALE",locale);
        this.locale = locale;
    }

    public Locale getLocale()
    {
        return locale;
    }

    public MeasurementType getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(MeasurementType measurementType) {
        paramMap.put("MEASUREMENT_TYPE", measurementType);
        this.measurementType = measurementType;
    }

    public FuelEfficiencyType getFuelEfficiencyType() {
        return fuelEfficiencyType;
    }

    public void setFuelEfficiencyType(FuelEfficiencyType fuelEfficiencyType) {
        paramMap.put(FUEL_EFFICIENCY_TYPE, fuelEfficiencyType);
        this.fuelEfficiencyType = fuelEfficiencyType;
    }

    public TimeFrame getTimeFrame() {
		return timeFrame;
	}

	public void setTimeFrame(TimeFrame timeFrame) {
        paramMap.put("TIME_FRAME", TimeFrameUtil.getTimeFrameStr(timeFrame, locale));
		this.timeFrame = timeFrame;
	}

    public TimeZone getTimeZone() {
        return timeZone;
    }
    public void setTimeZone(TimeZone timeZone) {
        paramMap.put("TIME_ZONE", timeZone);
        this.timeZone = timeZone;
    }
    public void setGroupID(Integer groupID) {
        paramMap.put("GROUP", ""+groupID);
    }
    public Boolean getIncludeInactiveDrivers() {
        return (includeInactiveDrivers!=null)?includeInactiveDrivers:DEFAULT_EXCLUDE_INACTIVE_DRIVERS;
    }
    public void setIncludeInactiveDrivers(Boolean includeInactiveDrivers) {
        this.includeInactiveDrivers = includeInactiveDrivers;
    }
    public Boolean getIncludeZeroMilesDrivers() {
        return (includeZeroMilesDrivers!=null)?includeZeroMilesDrivers:DEFAULT_EXCLUDE_ZERO_MILES_DRIVERS;
    }
    public void setIncludeZeroMilesDrivers(Boolean includeZeroMilesDrivers) {
        this.includeZeroMilesDrivers = includeZeroMilesDrivers;
    }

}
