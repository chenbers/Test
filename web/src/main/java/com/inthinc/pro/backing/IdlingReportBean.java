package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.IdlingReportItem;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.reports.Report;
import com.inthinc.pro.reports.ReportCriteria;

public class IdlingReportBean extends BaseReportBean<IdlingReportItem>
{
    private static final Logger logger = Logger.getLogger(IdlingReportBean.class);
    
    //idlingsData is the ONE read from the db, idlingData is what is displayed
    private List <IdlingReportItem> idlingsData = new ArrayList<IdlingReportItem>();
    private List <IdlingReportItem> idlingData = new ArrayList<IdlingReportItem>();
    
    static final List<String> AVAILABLE_COLUMNS;

    private ScoreDAO scoreDAO;
    
    private IdlingReportItem iri = null;
    
    private final static String COLUMN_LABEL_PREFIX = "idlingReports_";
    
    private Date startDate = new Date();
    private Date defaultStartDate = null;
    private Integer internalStartDate = null;
    private Date endDate = new Date();
    private Date defaultEndDate = null;
    private Integer internalEndDate = null;    
    private String badDates = "Search dates: * Okay.";
    private final static String NO_START_DATE = " * No start date, reset";
    private final static String NO_END_DATE = " * No end date, reset";
    private final static String START_BEFORE_END = " * Start before end, reset";
    
    // set to SEVEN days back
    private final static int DAYS_BACK = 7 * 24 * 60 * 60 * 1000;

    private DriverDAO driverDAO;

    
    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("driver_person_fullName");
        AVAILABLE_COLUMNS.add("vehicle_name");
        AVAILABLE_COLUMNS.add("driveTime");
        AVAILABLE_COLUMNS.add("lowHrs");
        AVAILABLE_COLUMNS.add("lowPercent");
        AVAILABLE_COLUMNS.add("highHrs");
        AVAILABLE_COLUMNS.add("highPercent");
        AVAILABLE_COLUMNS.add("totalHrs");
        AVAILABLE_COLUMNS.add("totalPercent");
    }
    
    public IdlingReportBean()
    {
        super();
    }
    
    public void init() 
    {   
        setTablePref(new TablePref(this));
        
        searchFor = checkForRequestMap();
        
        this.defaultEndDate = endDate;
        startDate.setTime(this.endDate.getTime() - DAYS_BACK);
        this.defaultStartDate = startDate;
        
        // end initialized to today, start
        //  seven days back
        Integer defaultEndDate = DateUtil.getTodaysDate();
        Integer defaultStartDate = DateUtil.getDaysBackDate(
                defaultEndDate,7);
   
        this.idlingsData = 
            scoreDAO.getIdlingReportData(
                    getUser().getGroupID(),
                    defaultStartDate, defaultEndDate);
   
        //Bean creation could be from Reports selection or
        //  search on main menu. This accounts for a search
        //  from the main menu w/ never having been to the 
        //  Idlings report page.
        if ( super.isMainMenu() ) {  
            checkOnSearch();
            super.setMainMenu(false);
        } else {
            loadResults(this.idlingsData);
        }
    }
    

    public List<IdlingReportItem> getIdlingData()
    {         
        return idlingData;
    }

    public void setIdlingData(List<IdlingReportItem> idlingData)
    {
        this.idlingData = idlingData;
    }

    @Override
    protected List<IdlingReportItem> getDBData()
    {
        return idlingsData;
    }

    @Override
    protected List<IdlingReportItem> getDisplayData()
    {
        return idlingData;
    }
    
    @Override
    public void search()
    {
        // snagged from the db
        if ( this.idlingsData.size() > 0 ) {
            this.idlingsData.clear();
        }
        
        // always hit the database, no matter what, 
        //   because of date range....
        checkDates();
        
        this.idlingsData = 
            scoreDAO.getIdlingReportData(
                    getUser().getGroupID(),
                    internalStartDate, internalEndDate);

        super.search();
    }
    
    private void checkDates() {
        StringBuffer sb = new StringBuffer();
        sb.append("Search Dates: ");
        boolean good = true;
                        
        // null checks
        if ( startDate == null ) {
            startDate = defaultStartDate;
            sb.append(NO_START_DATE);
            good = false;
        } 
        if ( endDate == null ) {
            endDate = defaultEndDate;
            sb.append(NO_END_DATE);
            good = false;
        } 
        
        // start after end?
        if ( startDate.getTime() > endDate.getTime() ) {
            startDate = defaultStartDate;
            endDate = defaultEndDate;
            sb.append(START_BEFORE_END);
            good = false;
        }
        
        // all good?
        if ( good ) {
            sb.append("Okay");
        }
        sb.append(".");
               
        badDates = sb.toString();
        
        // CAREFULLY compute the search dates
        long tm = startDate.getTime();
        tm = tm/1000L;
        internalStartDate = (int)tm;
        
        tm = endDate.getTime();
        tm = tm/1000L;
        internalEndDate = (int)tm;
    }

    @Override
    protected void loadResults(List <IdlingReportItem> idlsData) 
    {     
        if ( this.idlingData.size() > 0 ) {
            this.idlingData.clear();
        }
                
        iri = new IdlingReportItem();
                
        for ( IdlingReportItem i: idlsData ) {
            iri = i;    
                    
            //Group name
            iri.setGroup(this.getGroupHierarchy().getGroup(i.getGroupID()).getName());
            
            //Total idling            
            Float tot = iri.getLowHrs() + iri.getHighHrs();
            iri.setTotalHrs(tot);
            
            //Percentages, if any driving
            iri.setLowPercent("0.0");
            iri.setHighPercent("0.0");
            iri.setTotalPercent("0.0");
            Float totHrs = new Float(iri.getDriveTime());
            
            if ( totHrs != 0.0f ) {
                Float lo = 100.0f*iri.getLowHrs()/totHrs;  
                String fmt = lo.toString();
                iri.setLowPercent(fmt.substring(0,3));  
//                iri.setLowPerSort(lo);
                
                Float hi = 100.0f*iri.getHighHrs()/totHrs;
                fmt = hi.toString();
                iri.setHighPercent(fmt.substring(0,3));
//                iri.setHighPerSort(hi);
                
                Float to = 100.0f*iri.getTotalHrs()/totHrs;
                fmt = to.toString();
                iri.setTotalPercent(fmt.substring(0,3));
//                iri.setTotalPerSort(to);
            } 
 
            idlingData.add(iri);            
        }   
        
        this.maxCount = this.idlingData.size();   
        resetCounts();    
    }

    public Date getStartDate()
    {
        return startDate;
    }


    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }


    public Date getEndDate()
    {
        return endDate;
    }


    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public int convertToSeconds(String trip) {
        StringTokenizer st = new StringTokenizer(trip,":");

        int total = 0;
        //hh        
        total += (Integer.valueOf(st.nextToken())).intValue()*3600;
        //mm
        total += (Integer.valueOf(st.nextToken())).intValue()*60;
        //ss
        total += (Integer.valueOf(st.nextToken()));
        
        return total;
    }


    public DriverDAO getDriverDAO()
    {
        return driverDAO;
    }


    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }


    public String getBadDates()
    {
        return badDates;
    }


    public void setBadDates(String badDates)
    {
        this.badDates = badDates;
    }

    public ScoreDAO getScoreDAO()
    {
        return scoreDAO;
    }

    public void setScoreDAO(ScoreDAO scoreDAO)
    {
        this.scoreDAO = scoreDAO;
    }

    @Override
    public List<String> getAvailableColumns()
    {
        return AVAILABLE_COLUMNS;
    }

    @Override
    public String getColumnLabelPrefix()
    {
        return COLUMN_LABEL_PREFIX;
    }

    @Override
    public TableType getTableType()
    {
        return TableType.IDLING_REPORT;
    }

    public void exportReportToPdf()
    {
        ReportCriteria reportCriteria = new ReportCriteria(Report.IDLING_REPORT,getGroupHierarchy().getTopGroup().getName(),null);
        reportCriteria.setMainDataset(idlingData);
        getReportRenderer().exportSingleReportToPDF(reportCriteria, getFacesContext());
    }
    
    public void emailReport()
    {
        ReportCriteria reportCriteria = new ReportCriteria(Report.IDLING_REPORT,getGroupHierarchy().getTopGroup().getName(),getAccountName());
        reportCriteria.setMainDataset(idlingData);
        getReportRenderer().exportReportToEmail(reportCriteria,getEmailAddress());
    }
    
    public void exportReportToExcel()
    {
        ReportCriteria reportCriteria = new ReportCriteria(Report.IDLING_REPORT,getGroupHierarchy().getTopGroup().getName(),getAccountName());
        reportCriteria.setMainDataset(idlingData);
        getReportRenderer().exportReportToExcel(reportCriteria, getFacesContext());
    }
}

