package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.IdlingReportItem;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.TablePreference;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.TempColumns;

public class IdlingReportBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(IdlingReportBean.class);
    
    //idlingsData is the ONE read from the db, idlingData is what is displayed
    private List <IdlingReportItem> idlingsData = new ArrayList<IdlingReportItem>();
    private List <IdlingReportItem> idlingData = new ArrayList<IdlingReportItem>();
    
    private static final List<String> AVAILABLE_COLUMNS;
    private Map<String,TableColumn> idlingColumns;
    private Vector tmpColumns = new Vector();  
    
    private TablePreference tablePref;   
    private TablePreferenceDAO tablePreferenceDAO;
    
    private IdlingReportItem iri = null;
    
    private Integer numRowsPerPg = 25;
    private final static String COLUMN_LABEL_PREFIX = "idlingreport_";
    
    private Integer maxCount = null;
    private Integer start = 1;
    private Integer end = numRowsPerPg;
    
    private String searchFor = "";
    private String startDate = "";
    private String endDate = "";

    private DriverDAO driverDAO;
    
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
    
    public void init() {               
//        idlingsData = vehicleDAO.getVehiclesInGroupHierarchy(getUser().getPerson().getGroupID());
        idlingsData = new ArrayList<IdlingReportItem>();
        iri = new IdlingReportItem();
        Driver d = new Driver();        
        
        //Snag the data and then convert the trip string to seconds for compare
        
        //This is fake in that I'm just grabbing all the drivers and 
        //putting some other stats in there with them
        List <Driver> driversData = driverDAO.getAllDrivers(getUser().getPerson().getGroupID());
        for ( int i = 0; i < driversData.size(); i++ ) {
            d = driversData.get(i);           
            
            //Real            
            iri.setDriver(d);
            
            //Fake
            if ( i % 2 == 0 ) {
            iri.setGroup("North"); iri.setVehicle("AE-1122"); iri.setDriveTime("38:52:22"); 
            iri.setLowHrs("02:22:14"); iri.setLowPercent(22);
            iri.setHighHrs("10:48:11"); iri.setHighPercent(98); iri.setTotalHrs("41:16:36");
            iri.setTotalPercent(17);
            
            iri.setLowPerSort(this.convertToSeconds(iri.getLowHrs()));
                iri.setHighPerSort(this.convertToSeconds(iri.getHighHrs()));
                iri.setTotalPerSort(this.convertToSeconds(iri.getTotalHrs()));
                iri.setDriveTimeSort(this.convertToSeconds(iri.getDriveTime()));
            } else if ( i % 3 == 0 ) {
            iri.setGroup("South");  iri.setVehicle("ZZ-2132"); iri.setDriveTime("18:32:22"); 
            iri.setLowHrs("01:32:54"); iri.setLowPercent(12);
            iri.setHighHrs("20:18:31"); iri.setHighPercent(42); iri.setTotalHrs("11:36:46");
            iri.setTotalPercent(22); 
            
            iri.setLowPerSort(this.convertToSeconds(iri.getLowHrs()));
                iri.setHighPerSort(this.convertToSeconds(iri.getHighHrs()));
                iri.setTotalPerSort(this.convertToSeconds(iri.getTotalHrs()));
                iri.setDriveTimeSort(this.convertToSeconds(iri.getDriveTime()));    
            } else if ( i % 4 == 0 ) {
            iri.setGroup("East");  iri.setVehicle("LL-4231"); iri.setDriveTime("42:12:52"); 
            iri.setLowHrs("04:21:44"); iri.setLowPercent(2);
            iri.setHighHrs("15:58:41"); iri.setHighPercent(66); iri.setTotalHrs("41:16:36");
            iri.setTotalPercent(42);
            
            iri.setLowPerSort(this.convertToSeconds(iri.getLowHrs()));
                iri.setHighPerSort(this.convertToSeconds(iri.getHighHrs()));
                iri.setTotalPerSort(this.convertToSeconds(iri.getTotalHrs()));
                iri.setDriveTimeSort(this.convertToSeconds(iri.getDriveTime()));    
            } else {
            iri.setGroup("West");  iri.setVehicle("JK-9917"); iri.setDriveTime("118:12:42"); 
            iri.setLowHrs("03:12:54"); iri.setLowPercent(32);
            iri.setHighHrs("50:18:51"); iri.setHighPercent(12); iri.setTotalHrs("33:46:36");
            iri.setTotalPercent(55);
            
            iri.setLowPerSort(this.convertToSeconds(iri.getLowHrs()));
                iri.setHighPerSort(this.convertToSeconds(iri.getHighHrs()));
                iri.setTotalPerSort(this.convertToSeconds(iri.getTotalHrs()));
                iri.setDriveTimeSort(this.convertToSeconds(iri.getDriveTime()));
            }
                
            idlingsData.add(iri);
            iri = new IdlingReportItem();
        }

        maxCount = idlingsData.size();
        loadResults(idlingsData);
        resetCounts();
    }
    

    public List<IdlingReportItem> getIdlingData()
    {
        logger.debug("getting");           
        return idlingData;
        
    }

    public void setIdlingData(List<IdlingReportItem> idlingData)
    {
        this.idlingData = idlingData;
    }
    
    public void search() {             
        
        if ( this.idlingData.size() > 0 ) {
            this.idlingData.clear();
        }
        // TODO: Always hit the database, no matter what, too much data to hold,
        // watch for date range as well....
        String ID = this.searchFor.trim();
        List <IdlingReportItem> matchedIdlers = new ArrayList<IdlingReportItem>(); 
        
        // TODO: Date range check in here
        if ( ID.length() != 0 ) {     
            try {                                                                 
                for ( int i = 0; i < idlingsData.size(); i++ ) {
                    iri = (IdlingReportItem)idlingsData.get(i);                   
                    String localID = Integer.toString((iri.getDriver().getDriverID()).intValue());
                    logger.debug(ID + " compared to " + localID);
                    //Fuzzy
                    int index1 = localID.indexOf(ID);                    
                    if (index1 != -1) {                        
                        matchedIdlers.add(iri);
                    }
                }
                loadResults(matchedIdlers);
                          
                this.maxCount = matchedIdlers.size();
                
            //Looking for non-integer error for input search string
            } catch (Exception e) {}

        } else {
            loadResults(idlingsData);
            this.maxCount = idlingsData.size();
        }
        
        resetCounts();       
    }
    
    private void loadResults(List <IdlingReportItem> idlingsData) 
    {     
        iri = new IdlingReportItem();
                
        for ( int i = 0; i < idlingsData.size(); i++ ) {
            iri = idlingsData.get(i);                       
            idlingData.add(iri);            
        }             
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
    
    public void saveColumns() {  
        //To data store
        TablePreference pref = getTablePref();
        int cnt = 0;
        for (String column : AVAILABLE_COLUMNS)
        {
            pref.getVisible().set(cnt, idlingColumns.get(column).getVisible());
        }
        setTablePref(pref);
        tablePreferenceDAO.update(pref);
        
        //Update tmp
        Iterator it = this.idlingColumns.keySet().iterator();
        while ( it.hasNext() ) {
            Object key = it.next(); 
            Object value = this.idlingColumns.get(key);  

            for ( int i = 0; i < tmpColumns.size(); i++ ) {
                TempColumns tc = (TempColumns)tmpColumns.get(i);
                if ( tc.getColName().equalsIgnoreCase((String)key) ) {
                    Boolean b = ((TableColumn)value).getVisible();
                    tc.setColValue(b);
                }
            }
        }     
    }
    
    public void cancelColumns() {        
        //Update live
        Iterator it = this.idlingColumns.keySet().iterator();
        while ( it.hasNext() ) {
            Object key = it.next(); 
            Object value = this.idlingColumns.get(key);  
  
            for ( int i = 0; i < tmpColumns.size(); i++ ) {
                TempColumns tc = (TempColumns)tmpColumns.get(i);
                if ( tc.getColName().equalsIgnoreCase((String)key) ) {
                    this.idlingColumns.get(key).setVisible(tc.getColValue());
                }
            }
        }

    }
    
    public TablePreference getTablePref()
    {
        if (tablePref == null)
        {
            // TODO: refactor -- could probably keep in a session bean
            List<TablePreference> tablePreferenceList = tablePreferenceDAO.getTablePreferencesByUserID(getUser().getUserID());
            for (TablePreference pref : tablePreferenceList)
            {
                if (pref.getTableType().equals(TableType.VEHICLE_REPORT))
                {
                    setTablePref(pref);
                    return tablePref;
                }
            }
            tablePref = new TablePreference();
            tablePref.setUserID(getUser().getUserID());
            tablePref.setTableType(TableType.VEHICLE_REPORT);
            List<Boolean>visibleList = new ArrayList<Boolean>();
            for (String column : AVAILABLE_COLUMNS)
            {
                visibleList.add(new Boolean(true));
            }
            tablePref.setVisible(visibleList);
            
        }
        
        
        return tablePref;
    }
    
    public void setTablePref(TablePreference tablePref)
    {
        this.tablePref = tablePref;
    }
    
    public Map<String,TableColumn> getIdlingColumns()    
    {
        //Need to do the check to prevent odd access behavior
        if ( idlingColumns == null ) {     
            List<Boolean> visibleList = getTablePref().getVisible();
            idlingColumns = new HashMap<String, TableColumn>();
            int cnt = 0;
            for (String column : AVAILABLE_COLUMNS)
            {
                TableColumn tableColumn = new TableColumn(visibleList.get(cnt++), MessageUtil.getMessageString(COLUMN_LABEL_PREFIX+column));
                if (column.equals("clear"))
                    tableColumn.setCanHide(false);
                    
                idlingColumns.put(column, tableColumn);
                tmpColumns.add(new TempColumns(column,tableColumn.getVisible()));
                        
            }
        } 
        
        return idlingColumns;
    }

    public void setIdlingColumns(Map<String,  TableColumn> vehicleColumns)
    {
        this.idlingColumns = vehicleColumns;
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


    public String getStartDate()
    {
        return startDate;
    }


    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }


    public String getEndDate()
    {
        return endDate;
    }


    public void setEndDate(String endDate)
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
}

