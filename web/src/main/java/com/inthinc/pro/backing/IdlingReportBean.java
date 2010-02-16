package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.IdlingReportData;
import com.inthinc.pro.model.IdlingReportItem;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.util.MessageUtil;

public class IdlingReportBean extends BaseReportBean<IdlingReportItem> implements PersonChangeListener {
    private static final Logger logger = Logger.getLogger(IdlingReportBean.class);
    // idlingsData is the ONE read from the db, idlingData is what is displayed
    private List<IdlingReportItem> idlingsData = new ArrayList<IdlingReportItem>();
    private List<IdlingReportItem> idlingData = new ArrayList<IdlingReportItem>();
    static final List<String> AVAILABLE_COLUMNS;
    private ScoreDAO scoreDAO;
    private Integer totalDrivers;

	private final static String COLUMN_LABEL_PREFIX = "idlingReports_";
    private Date startDate;
    private Date endDate;
    private String badDates = "";
//    private final static String NO_START_DATE = " * No start date, reset";
//    private final static String NO_END_DATE = " * No end date, reset";
//    private final static String START_BEFORE_END = " * Start before end, reset";
    // set to SEVEN days back in calendar time
    private final static long DAYS_BACK = 7 * 24 * 60 * 60 * 1000;
    private DriverDAO driverDAO;
    static {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("driver_person_fullName");
        AVAILABLE_COLUMNS.add("driveTime");
        AVAILABLE_COLUMNS.add("lowHrs");
        AVAILABLE_COLUMNS.add("lowPercent");
        AVAILABLE_COLUMNS.add("highHrs");
        AVAILABLE_COLUMNS.add("highPercent");
        AVAILABLE_COLUMNS.add("totalHrs");
        AVAILABLE_COLUMNS.add("totalPercent");
    }

    public IdlingReportBean() {
        super();
    }

    @Override
    public void initBean() {
        super.initBean();
        // Set start and end date to last 7 days
        endDate = new Date();
        startDate = new Date(endDate.getTime() - DAYS_BACK);
    }

    @Override
    protected void loadDBData() {
        checkDates();
//Date internalStartDate = startOfDay(this.startDate);
//Date internalEndDate = endOfDay(this.endDate);
//System.out.println("IDLING DATE RANGE: " + internalStartDate + "(" + internalStartDate.getTime() + ") - " + internalEndDate + "(" + internalEndDate.getTime() + ")");
        
        IdlingReportData data = scoreDAO.getIdlingReportData(getEffectiveGroupId(), 
        							startOfDay(this.startDate), 
        							endOfDay(this.endDate));
        //Extract count
        this.idlingsData = data.getItemList();
        setTotalDrivers(data.getTotal());
        Collections.sort(idlingsData); // Sort by driver name

        // Once loaded, set the group name NOW so it can be searchable IMMEDIATELY
        for (IdlingReportItem iri : this.idlingsData) {
        	if (getGroupHierarchy().getGroup(iri.getGroupID()) == null)
        	{
        		logger.info("group lookup failed -- groupID " + iri.getGroupID());
        		iri.setGroup("");
        	}
            iri.setGroup(this.getGroupHierarchy().getGroup(iri.getGroupID()).getName());
        }
    }

    @Override
    protected void filterResults(List<IdlingReportItem> data) {
        // TODO Auto-generated method stub
    }

    @Override
    public void personListChanged() {
    	// removed because it does this everytime you hit the page anyway (i.e. not cached)
//        loadDBData();
    	
    }

    /**
     * Sets the minute,hour, and second to 0
     * 
     * @param date
     * @return
     */
    private Date startOfDay(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        if (date != null) // Otherwise it will be the current date
            calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    private Date endOfDay(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        if (date != null) // Otherwise it will be the current date
            calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public List<IdlingReportItem> getIdlingData() {
        return idlingData;
    }

    public void setIdlingData(List<IdlingReportItem> idlingData) {
        this.idlingData = idlingData;
    }

    @Override
    protected List<IdlingReportItem> getDBData() {
        return idlingsData;
    }

    @Override
    protected List<IdlingReportItem> getDisplayData() {
        return idlingData;
    }

    private void checkDates() {
        StringBuffer sb = new StringBuffer();
        boolean good = true;
        // null checks
        if (this.startDate == null) {
            sb.append(MessageUtil.getMessageString("noStartDate",getLocale()));
            good = false;
        }
        if (this.endDate == null) {
            sb.append(MessageUtil.getMessageString("noEndDate",getLocale()));
            good = false;
        }
        // start after end?
        if (this.startDate.getTime() > this.endDate.getTime()) {
            this.startDate = null;
            this.endDate = null;
            sb.append(MessageUtil.getMessageString("endDateBeforeStartDate",getLocale()));
            good = false;
        }
        badDates = sb.toString();
        // CAREFULLY compute the search dates
//        this.internalStartDate = resetTime(this.startDate);
//        this.internalEndDate = resetTime(new Date(this.endDate.getTime() + DateUtil.MILLISECONDS_IN_DAY));
    }

    @Override
    protected void loadResults(List<IdlingReportItem> idlsData) {
        idlingData = new ArrayList<IdlingReportItem>();
        for (IdlingReportItem i : idlsData) {
            // Group name
            i.setGroup(this.getGroupHierarchy().getGroup(i.getGroupID()).getName());
            idlingData.add(i);
        }
        this.maxCount = this.idlingData.size();
        resetCounts();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
    	if (!this.startDate.equals(startDate))
    		setRefreshRequired(true);
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
    	if (!this.endDate.equals(endDate))
    		setRefreshRequired(true);
        this.endDate = endDate;
    }

    public int convertToSeconds(String trip) {
        StringTokenizer st = new StringTokenizer(trip, ":");
        int total = 0;
        // hh
        total += (Integer.valueOf(st.nextToken())).intValue() * 3600;
        // mm
        total += (Integer.valueOf(st.nextToken())).intValue() * 60;
        // ss
        total += (Integer.valueOf(st.nextToken()));
        return total;
    }

    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public String getBadDates() {
        return badDates;
    }

    public void setBadDates(String badDates) {
        this.badDates = badDates;
    }

    public ScoreDAO getScoreDAO() {
        return scoreDAO;
    }

    public void setScoreDAO(ScoreDAO scoreDAO) {
        this.scoreDAO = scoreDAO;
    }

    @Override
    public List<String> getAvailableColumns() {
        return AVAILABLE_COLUMNS;
    }

    @Override
    public String getColumnLabelPrefix() {
        return COLUMN_LABEL_PREFIX;
    }

    @Override
    public TableType getTableType() {
        return TableType.IDLING_REPORT;
    }

    public void exportReportToPdf() {
        ReportCriteria reportCriteria = loadReportCriteria();
        getReportRenderer().exportSingleReportToPDF(reportCriteria, getFacesContext());
    }

    public void emailReport() {
        ReportCriteria reportCriteria = loadReportCriteria();
        getReportRenderer().exportReportToEmail(reportCriteria, getEmailAddress());
    }

    public void exportReportToExcel() {
        ReportCriteria reportCriteria = loadReportCriteria();
        getReportRenderer().exportReportToExcel(reportCriteria, getFacesContext());
    }

    private ReportCriteria loadReportCriteria() {
        ReportCriteria reportCriteria = getReportCriteriaService().getIdlingReportCriteria(getGroupHierarchy().getTopGroup().getGroupID(),
        				startOfDay(this.startDate),
        				endOfDay(this.endDate),
        				getLocale());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setMainDataset(idlingData);
        
        return reportCriteria;
    }

    @Override
    protected void setDisplayData(List<IdlingReportItem> displayData) {
        idlingData = displayData;
    }

    @Override
    public String getMappingId() {
        return "pretty:idlingReport";
    }

    @Override
    public String getMappingIdWithCriteria() {
        return "pretty:idlingReportWithCriteria";
    }
    public Integer getTotalDrivers() {
		return totalDrivers;
	}

	public void setTotalDrivers(Integer totalDrivers) {
		this.totalDrivers = totalDrivers;
	}
	public Integer getTotalEMUDrivers(){
		return idlingsData.size();
	}
}
