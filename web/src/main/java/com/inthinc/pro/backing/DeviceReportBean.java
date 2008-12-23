package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceReportItem;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.TablePreference;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.TempColumns;

public class DeviceReportBean extends BaseReportBean
{
    private static final Logger logger = Logger.getLogger(DeviceReportBean.class);
    
    //devicesData is the ONE read from the db, deviceData is what is displayed
    private List <DeviceReportItem> devicesData = new ArrayList<DeviceReportItem>(); 
    private List <DeviceReportItem> deviceData = new ArrayList<DeviceReportItem>();
    
    static final List<String> AVAILABLE_COLUMNS;
    private Map<String,TableColumn> deviceColumns;
    private Vector tmpColumns = new Vector();    
    
    private TablePreference tablePref;
    private TablePreferenceDAO tablePreferenceDAO;
    private VehicleDAO vehicleDAO;
    private DeviceDAO deviceDAO;
   
    private DeviceReportItem dri = null;
    
    private Integer numRowsPerPg = 1;
    private final static String COLUMN_LABEL_PREFIX = "devicereport_";
    
    private Integer maxCount = null;
    private Integer start = 1;
    private Integer end = numRowsPerPg;
    
    private String searchFor = "";
    private String secret = "";
   
    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("tiwiproID");
        AVAILABLE_COLUMNS.add("assignedVehicle");
        AVAILABLE_COLUMNS.add("IMEI");
        AVAILABLE_COLUMNS.add("phoneNbr");
        AVAILABLE_COLUMNS.add("status");   
        AVAILABLE_COLUMNS.add("emergPhoneNbr");
    }
    
    public DeviceReportBean()
    {
        super();
    }
    
    public void init() {
        searchFor = checkForRequestMap();
        
        List<Vehicle> vehicList = 
            vehicleDAO.getVehiclesInGroupHierarchy(getUser().getPerson().getGroupID());

        for( Vehicle v: vehicList )
        {
            // save only vehicles that have devices associated to them
            if ( v.getDeviceID() != null ) {
                Device dev = deviceDAO.findByID(v.getDeviceID());            
 
                dri = new DeviceReportItem();
                dri.setDevice(dev);
                
                this.devicesData.add(dri);
            }
        }
        
        if ( super.isMainMenu() ) {  
            checkOnSearch();
            super.setMainMenu(false);
        } else {
            loadResults(this.devicesData);
        }
    }
        

    public List<DeviceReportItem> getDeviceData()
    {             
        return deviceData;
    }
    
    private void checkOnSearch() 
    {
        if ( (searchFor != null) && 
             (searchFor.trim().length() != 0) ) 
        {
            search();
        } else {
            loadResults(this.devicesData);
        }
        
        maxCount = this.deviceData.size();        
        resetCounts();        
    }

    public void setDeviceData(List<DeviceReportItem> deviceData)
    {
        this.deviceData = deviceData;
    }
    
    public void search() 
    {
        if ( this.deviceData.size() > 0 ) {
            this.deviceData.clear();
        }
        
        if ( this.searchFor.trim().length() != 0 ) {
            String trimmedSearch = this.searchFor.trim().toLowerCase();            
            List <DeviceReportItem> matchedDevices = new ArrayList<DeviceReportItem>();    
            
            for ( DeviceReportItem d: devicesData ) {
                
                int index1;
                int index2;
                
                // imei                
                String dev = d.getDevice().getImei().toLowerCase();
                index1 = dev.indexOf(trimmedSearch);                    
                if (index1 != -1) {                        
                    matchedDevices.add(d);
                }
                
                // device name
                String name = d.getDevice().getName().toLowerCase();
                index2 = name.indexOf(trimmedSearch);                    
                if ( (index1 == -1) && 
                     (index2 != -1) ) {                        
                    matchedDevices.add(d);
                }
            }
            
            loadResults(matchedDevices);             
            this.maxCount = matchedDevices.size();
                    
        } else {
            loadResults(devicesData);
            this.maxCount = devicesData.size();
        }
        
        resetCounts();       
    }
    
    private void loadResults(List <DeviceReportItem> devicData)
    {     
        if ( this.deviceData.size() > 0 ) {
            this.deviceData.clear();
        }
 
        for ( DeviceReportItem a: devicData ) { 
            dri = new DeviceReportItem();
            dri.setDevice(a.getDevice());
            deviceData.add(dri);                        
        }
        
        this.maxCount = this.deviceData.size();   
        resetCounts(); 
    }
    
    private void resetCounts() {        
        this.start = 1;
        
        //None found
        if ( this.deviceData.size() < 1 ) {
            this.start = 0;
        }
        
        this.end = this.numRowsPerPg;
        
        //Fewer than a page
        if ( this.deviceData.size() <= this.end ) {
            this.end = this.deviceData.size();
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
            pref.getVisible().set(cnt++, 
                    deviceColumns.get(column).getVisible());
        }
        setTablePref(pref);
        tablePreferenceDAO.update(pref);
        
        //Update tmp
        Iterator it = this.deviceColumns.keySet().iterator();
        while ( it.hasNext() ) {
            Object key = it.next(); 
            Object value = this.deviceColumns.get(key);  

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
        Iterator it = this.deviceColumns.keySet().iterator();
        while ( it.hasNext() ) {
            Object key = it.next(); 
            Object value = this.deviceColumns.get(key);  
  
            for ( int i = 0; i < tmpColumns.size(); i++ ) {
                TempColumns tc = (TempColumns)tmpColumns.get(i);
                if ( tc.getColName().equalsIgnoreCase((String)key) ) {
                    this.deviceColumns.get(key).setVisible(tc.getColValue());
                }
            }
        }

    }

    public TablePreference getTablePref()
    {
        if (tablePref == null)
        {
            // TODO: refactor -- could probably keep in a session bean
            List<TablePreference> tablePreferenceList = 
                tablePreferenceDAO.getTablePreferencesByUserID(getUser().getUserID());
            for (TablePreference pref : tablePreferenceList)
            {
                if (pref.getTableType().equals(TableType.DEVICE_REPORT))
                {
                    setTablePref(pref);
                    return tablePref;
                }
            }
            tablePref = new TablePreference();
            tablePref.setUserID(getUser().getUserID());
            tablePref.setTableType(TableType.DEVICE_REPORT);
            List<Boolean>visibleList = new ArrayList<Boolean>();
            for (String column : AVAILABLE_COLUMNS)
            {
                visibleList.add(new Boolean(true));
            }
            tablePref.setVisible(visibleList);
            Integer tablePrefID = getTablePreferenceDAO().create(getUser().getUserID(), tablePref);
            tablePref.setTablePrefID(tablePrefID);
            setTablePref(tablePref);
            
        }
        
        
        return tablePref;
    }
    
    public void setTablePref(TablePreference tablePref)
    {
        this.tablePref = tablePref;
    }
    
    public Map<String,TableColumn> getdeviceColumns()    
    {
        if ( deviceColumns == null ) {     
            List<Boolean> visibleList = getTablePref().getVisible();
            deviceColumns = new HashMap<String, TableColumn>();
            int cnt = 0;
            for (String column : AVAILABLE_COLUMNS)
            {
                TableColumn tableColumn = new TableColumn(visibleList.get(cnt++), 
                        MessageUtil.getMessageString(COLUMN_LABEL_PREFIX+column));
                if (column.equals("clear"))
                    tableColumn.setCanHide(false);
                    
                deviceColumns.put(column, tableColumn);
                tmpColumns.add(new TempColumns(column,tableColumn.getVisible()));
                        
            }
        } 
        
        return deviceColumns;
    }

    public void setdeviceColumns(Map<String,  TableColumn> deviceColumns)
    {
        this.deviceColumns = deviceColumns;
    }
    
    public void scrollerListener(DataScrollerEvent se)     
    {               
        this.start = (se.getPage()-1)*this.numRowsPerPg + 1;
        this.end = (se.getPage())*this.numRowsPerPg;
        
        //Partial page
        if ( this.end > this.deviceData.size() ) {
            this.end = this.deviceData.size();
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

    public String getSecret()
    {
        searchFor = checkForRequestMap();        
              
        if ( super.isMainMenu() ) {  
            checkOnSearch();
            super.setMainMenu(false);
        } else {
            loadResults(this.devicesData);
        }   
        
        return secret;
    }

    public void setSecret(String secret)
    {
        this.secret = secret;
    }

    public VehicleDAO getVehicleDAO()
    {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    public DeviceDAO getDeviceDAO()
    {
        return deviceDAO;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO)
    {
        this.deviceDAO = deviceDAO;
    }

}


