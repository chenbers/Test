package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.model.IdlingReportItem;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.reports.ReportCriteria;

public class IdlingReportBean extends BaseReportBean<IdlingReportItem> implements PersonChangeListener {
    private static final Logger logger = Logger.getLogger(IdlingReportBean.class);
    // idlingsData is the ONE read from the db, idlingData is what is displayed
    private List<IdlingReportItem> idlingsData = new ArrayList<IdlingReportItem>();
    private List<IdlingReportItem> idlingData = new ArrayList<IdlingReportItem>();
    static final List<String> AVAILABLE_COLUMNS;
    private ScoreDAO scoreDAO;
    private final static String COLUMN_LABEL_PREFIX = "idlingReports_";
    private Date startDate = null;
    private Date intStartDate = null;
    private Date defaultStartDate = null;
    private Date internalStartDate = null;
    private Date endDate = null;
    private Date intEndDate = null;
    private Date defaultEndDate = null;
    private Date internalEndDate = null;
    private String badDates = "Search dates: * Okay.";
    private final static String NO_START_DATE = " * No start date, reset";
    private final static String NO_END_DATE = " * No end date, reset";
    private final static String START_BEFORE_END = " * Start before end, reset";
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
        startDate = new Date();
        startDate.setTime(endDate.getTime() - DAYS_BACK);
        // Start with today
        intEndDate = resetTime(null);
        // Now, seven days back
        intStartDate = resetTime(new Date(intEndDate.getTime() - DAYS_BACK));
    }

    @Override
    protected void loadDBData() {
        checkDates();
        this.idlingsData = scoreDAO.getIdlingReportData(getEffectiveGroupId(), this.internalStartDate, this.internalEndDate);
        // Once loaded, set the group name NOW so it can be searchable IMMEDIATELY
        for (IdlingReportItem iri : this.idlingsData) {
            iri.setGroup(this.getGroupHierarchy().getGroup(iri.getGroupID()).getName());
        }
    }

    @Override
    protected void filterResults(List<IdlingReportItem> data) {
        // TODO Auto-generated method stub
    }

    @Override
    public void personListChanged() {
        loadDBData();
    }

    /**
     * Sets the minute,hour, and second to 0
     * 
     * @param date
     * @return
     */
    private Date resetTime(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        if (date != null) // Otherwise it will be the current date
            calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
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
        sb.append("Search Dates: ");
        boolean good = true;
        // null checks
        if (this.startDate == null) {
            this.startDate = this.defaultStartDate;
            sb.append(NO_START_DATE);
            good = false;
        }
        if (this.endDate == null) {
            this.endDate = this.defaultEndDate;
            sb.append(NO_END_DATE);
            good = false;
        }
        // start after end?
        if (this.startDate.getTime() > this.endDate.getTime()) {
            this.startDate = this.defaultStartDate;
            this.endDate = this.defaultEndDate;
            sb.append(START_BEFORE_END);
            good = false;
        }
        // all good?
        if (good) {
            sb.append("Okay");
        }
        sb.append(".");
        badDates = sb.toString();
        // CAREFULLY compute the search dates
        this.internalStartDate = resetTime(this.startDate);
        this.internalEndDate = resetTime(this.endDate);
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
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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
        ReportCriteria reportCriteria = getReportCriteriaService().getIdlingReportCriteria(getGroupHierarchy().getTopGroup().getGroupID(), internalStartDate, internalEndDate);
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setMainDataset(idlingData);
        reportCriteria.setLocale(getLocale());
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
}
