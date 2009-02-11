package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.model.IdlingReportItem;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.reports.ReportCriteria;

public class IdlingReportBean extends BaseReportBean<IdlingReportItem> implements PersonChangeListener
{
    private static final Logger logger = Logger.getLogger(IdlingReportBean.class);
    
    //idlingsData is the ONE read from the db, idlingData is what is displayed
    private List <IdlingReportItem> idlingsData = new ArrayList<IdlingReportItem>();
    private List <IdlingReportItem> idlingData = new ArrayList<IdlingReportItem>();
    
    static final List<String> AVAILABLE_COLUMNS;

    private ScoreDAO scoreDAO;
    
    private IdlingReportItem iri = null;
    
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

    @Override
    protected void loadDBData()
    {
        // Dates for show
        this.endDate = new Date();
        this.startDate = new Date();
        this.startDate.setTime(this.endDate.getTime() - DAYS_BACK);

        // Start with today
        this.intEndDate = getGregDate(null);
       
        // Now, seven days back
        this.intStartDate = new Date();
        this.intStartDate.setTime(
                this.intEndDate.getTime() - DAYS_BACK);         
                                
        // Get some other dates, for use if none input        
        this.defaultEndDate = this.intEndDate;   
        this.defaultStartDate = this.intStartDate;         

        this.idlingsData = 
            scoreDAO.getIdlingReportData(
                    getUser().getGroupID(),
                    intStartDate, intEndDate);
    }

    @Override
    public void personListChanged()
    {
        loadDBData();
        search();
    }
    
    private Date getGregDate(Date in) 
    {
        GregorianCalendar gc = new GregorianCalendar(
                TimeZone.getTimeZone("GMT"));        
        
        // Date supplied, reset to...
        if ( in != null ) {
            gc.setTimeInMillis(in.getTime());
        }
            
        gc.clear(Calendar.HOUR_OF_DAY);
        gc.clear(Calendar.HOUR);
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.clear(Calendar.MINUTE);
        gc.set(Calendar.MINUTE, 0);
        gc.clear(Calendar.SECOND);
        gc.set(Calendar.SECOND,0);
    
        return gc.getTime();
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
                    this.internalStartDate, 
                    this.internalEndDate);

        super.search();
    }
    
    private void checkDates() {
        StringBuffer sb = new StringBuffer();
        sb.append("Search Dates: ");
        boolean good = true;
                        
        // null checks
        if ( this.startDate == null ) {
            this.startDate = this.defaultStartDate;
            sb.append(NO_START_DATE);
            good = false;
        } 
        if ( this.endDate == null ) {
            this.endDate = this.defaultEndDate;
            sb.append(NO_END_DATE);
            good = false;
        } 
        
        // start after end?
        if ( this.startDate.getTime() > this.endDate.getTime() ) {
            this.startDate = this.defaultStartDate;
            this.endDate = this.defaultEndDate;
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
        this.internalStartDate = getGregDate(this.startDate);   
        this.internalEndDate   = getGregDate(this.endDate);   
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
            Float totHrs = new Float(iri.getDriveTime()) +
                iri.getLowHrs() + iri.getHighHrs();                
            
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
        ReportCriteria reportCriteria = getReportCriteriaService().getIdlingReportCriteria(getGroupHierarchy().getTopGroup().getGroupID(), intStartDate, intEndDate);
        reportCriteria.setMainDataset(idlingData);
        getReportRenderer().exportSingleReportToPDF(reportCriteria, getFacesContext());
    }
    
    public void emailReport()
    {
        ReportCriteria reportCriteria = getReportCriteriaService().getIdlingReportCriteria(getGroupHierarchy().getTopGroup().getGroupID(), intStartDate, intEndDate);
        reportCriteria.setMainDataset(idlingData);
        getReportRenderer().exportReportToEmail(reportCriteria,getEmailAddress());
    }
    
    public void exportReportToExcel()
    {
        ReportCriteria reportCriteria = getReportCriteriaService().getIdlingReportCriteria(getGroupHierarchy().getTopGroup().getGroupID(), intStartDate, intEndDate);
        reportCriteria.setMainDataset(idlingData);
        getReportRenderer().exportReportToExcel(reportCriteria, getFacesContext());
    }
}

