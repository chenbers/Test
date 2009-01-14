package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.IdlingReportItem;
import com.inthinc.pro.model.TablePreference;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.Report;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.TempColumns;

public class IdlingReportBean extends BaseReportBean implements TablePrefOptions
{
    private static final Logger logger = Logger.getLogger(IdlingReportBean.class);
    
    //idlingsData is the ONE read from the db, idlingData is what is displayed
    private List <IdlingReportItem> idlingsData = new ArrayList<IdlingReportItem>();
    private List <IdlingReportItem> idlingData = new ArrayList<IdlingReportItem>();
    
    static final List<String> AVAILABLE_COLUMNS;

    private TablePreferenceDAO tablePreferenceDAO;
    private ScoreDAO scoreDAO;
    
    private IdlingReportItem iri = null;
    
    private Integer numRowsPerPg = 25;
    private final static String COLUMN_LABEL_PREFIX = "idlingReports_";
    
    private Integer maxCount = null;
    private Integer start = 1;
    private Integer end = numRowsPerPg;
    
    private String searchFor = "";
    private String secret = "";
    
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
    
    private TablePref tablePref;

    
    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("driver");
        AVAILABLE_COLUMNS.add("vehicle");
        AVAILABLE_COLUMNS.add("drivetime");
        AVAILABLE_COLUMNS.add("lowhrs");
        AVAILABLE_COLUMNS.add("lowpercent");
        AVAILABLE_COLUMNS.add("highhrs");
        AVAILABLE_COLUMNS.add("highpercent");
        AVAILABLE_COLUMNS.add("totalhrs");
        AVAILABLE_COLUMNS.add("totalpercent");
    }
    
    public IdlingReportBean()
    {
        super();
    }
    
    public void init() 
    {   
        tablePref = new TablePref(this);
        
        searchFor = checkForRequestMap();
        
        defaultEndDate = endDate;
        startDate.setTime(this.endDate.getTime() - DAYS_BACK);
        defaultStartDate = startDate;
        
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
    
    private void checkOnSearch() 
    {
        if ( (searchFor != null) && 
             (searchFor.trim().length() != 0) ) 
        {
            search();
        } else {
            loadResults(this.idlingsData);
        }
        
        maxCount = this.idlingData.size();        
        resetCounts();        
    }

    public void setIdlingData(List<IdlingReportItem> idlingData)
    {
        this.idlingData = idlingData;
    }
    
    public void search() 
    {
        // to be shown
        if ( this.idlingData.size() > 0 ) {
            this.idlingData.clear();
        }
        
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
        
        String trimmedSearch = this.searchFor.trim().toLowerCase();

        if ( trimmedSearch.length() != 0 ) {
            
            // always hit the database, no matter what, 
            //   because of date range....
            checkDates();
            
            this.idlingsData = 
                scoreDAO.getIdlingReportData(
                        getUser().getGroupID(),
                        internalStartDate, internalEndDate);
            
            List <IdlingReportItem> matchedIdlers = new ArrayList<IdlingReportItem>();                 
                                                                         
            for ( IdlingReportItem iri: this.idlingsData ) {
                
                int index1;
                int index2;
                int index3;
   
                // first name 
                index1 = iri.getDriver().getPerson().getFirst().toLowerCase().indexOf(trimmedSearch);                    
                if (index1 != -1) {                        
                    matchedIdlers.add(iri);
                }
                
                // last name 
                index2 = iri.getDriver().getPerson().getLast().toLowerCase().indexOf(trimmedSearch);                    
                if ((index1 == -1) &&
                    (index2 != -1) ) {                        
                    matchedIdlers.add(iri);
                }
                
                // vehicle name 
                index3 = iri.getVehicle().getName().trim().toLowerCase().indexOf(trimmedSearch);                    
                if ((index1 == -1) &&
                    (index2 == -1) &&
                    (index3 != -1) ) {                        
                    matchedIdlers.add(iri);
                }
            }
            loadResults(matchedIdlers);                        
            this.maxCount = matchedIdlers.size();
                           
        } else {
            loadResults(this.idlingsData);
            this.maxCount = this.idlingsData.size();
        }
        
        resetCounts();       
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
        internalStartDate = (int)startDate.getTime()*1000;
        internalEndDate = (int)endDate.getTime()*1000;
    }
    
    private void loadResults(List <IdlingReportItem> idlsData) 
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
            Integer tot = 
                new Integer(iri.getLowHrs()) +
                new Integer(iri.getHighHrs());
            iri.setTotalHrs(String.valueOf(tot.intValue()));
            
            //Percentages, if any driving
            iri.setLowPercent(0);
            iri.setHighPercent(0);
            iri.setTotalPercent(0);
            Integer totHrs = new Integer(iri.getDriveTime());
            
            if ( totHrs != 0 ) {
                int lo = 100*(Integer.parseInt(iri.getLowHrs()));               
                iri.setLowPercent(lo/totHrs);
                int hi = 100*(Integer.parseInt(iri.getHighHrs()));
                iri.setHighPercent(hi/totHrs);
                int to = 100*(Integer.parseInt(iri.getTotalHrs()));
                iri.setTotalPercent(to/totHrs);
            } 
 
            idlingData.add(iri);            
        }   
        
        this.maxCount = this.idlingData.size();   
        resetCounts();    
    }
    
    private void resetCounts() {
        this.start = 1;
        
        //None found
        if ( this.idlingData.size() < 1 ) {
            this.start = 0;
        }
        
        this.end = this.numRowsPerPg;
        
        //Fewer than a page
        if ( this.idlingData.size() <= this.end ) {
            this.end = this.idlingData.size();
        } else if ( this.start == 0 ) {
            this.end = 0;
        }
    }
    public void scrollerListener(DataScrollerEvent se)     
    {        
        this.start = (se.getPage()-1)*this.numRowsPerPg + 1;
        this.end = (se.getPage())*this.numRowsPerPg;
        //Partial page
        if ( this.end > this.idlingData.size() ) {
            this.end = this.idlingData.size();
        }
    }
    

    public Integer getMaxCount()
    {
        return maxCount;
    }


    public void setMaxCount(Integer maxCount)
    {        
        this.maxCount = maxCount;
    }


    public Integer getStart()
    {
        return start;
    }


    public void setStart(Integer start)
    {
        this.start = start;
    }


    public Integer getEnd()
    {
        return end;
    }


    public void setEnd(Integer end)
    {
        this.end = end;
    }


    public Integer getNumRowsPerPg()
    {
        return numRowsPerPg;
    }


    public void setNumRowsPerPg(Integer numRowsPerPg)
    {
        this.numRowsPerPg = numRowsPerPg;
    }


    public String getSearchFor()
    {
        return searchFor;
    }


    public void setSearchFor(String searchFor)
    {
        this.searchFor = searchFor;
    }

    public TablePreferenceDAO getTablePreferenceDAO()
    {
        return tablePreferenceDAO;
    }

    public void setTablePreferenceDAO(TablePreferenceDAO tablePreferenceDAO)
    {
        this.tablePreferenceDAO = tablePreferenceDAO;
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
    
    
    public String getSecret()
    {
        String searchForLocal = checkForRequestMap();
        String search = searchForLocal.toLowerCase().trim();
        if ( (search.length() != 0) && (!search.equalsIgnoreCase(this.searchFor)) ) 
        {
            this.searchFor = searchForLocal.toLowerCase().trim();
        }
              
        if ( super.isMainMenu() ) {  
            checkOnSearch();
            super.setMainMenu(false);
        } else if ( this.searchFor.trim().length() != 0 ) {
            checkOnSearch();
        } else {
            loadResults(this.idlingsData);
        }   
        
        return secret;
    }

    public void setSecret(String secret)
    {
        this.secret = secret;
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
    public Map<String, Boolean> getDefaultColumns()
    {
        HashMap<String, Boolean> columns = new HashMap<String, Boolean>();
        for (String col : AVAILABLE_COLUMNS)
            columns.put(col, true);
        return columns;
    }

    @Override
    public TableType getTableType()
    {
        return TableType.IDLING_REPORT;
    }

    @Override
    public Integer getUserID()
    {
        return getUser().getUserID();
    }

    public Map<String, TableColumn> getTableColumns()
    {
        return tablePref.getTableColumns();
    }
    public void setTableColumns(Map<String, TableColumn> tableColumns)
    {
        tablePref.setTableColumns(tableColumns);
    }

    public TablePref getTablePref()
    {
        return tablePref;
    }

    public void setTablePref(TablePref tablePref)
    {
        this.tablePref = tablePref;
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

