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
import com.inthinc.pro.dao.TablePreferenceDAO;
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
    
    //assetsData is the ONE read from the db, assetData is what is displayed
    private List <Device> devicesData = new ArrayList<Device>(); 
    private List <DeviceReportItem> deviceData = new ArrayList<DeviceReportItem>();
    
    static final List<String> AVAILABLE_COLUMNS;
    private Map<String,TableColumn> deviceColumns;
    private Vector tmpColumns = new Vector();    
    
    private TablePreference tablePref;
    private TablePreferenceDAO tablePreferenceDAO;
   
    private DeviceReportItem drt = null;
    
    private Integer numRowsPerPg = 1;
    private final static String COLUMN_LABEL_PREFIX = "devicereport_";
    
    private Integer maxCount = null;
    private Integer start = 1;
    private Integer end = numRowsPerPg;
    
    private String searchFor = "";
   
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
    
    public void init() {
        searchFor = checkForRequestMap();
        
        //Live DAO here
//        assetsData = driverDAO.getAllDrivers(getUser().getPerson().getGroupID());
        //Fake data
        drt = new DeviceReportItem();
        Device device = new Device();
        device.setName("TIWI0789");
        device.setVehicleID(456789);
        device.setImei("1c3lc46h67n6016i4a");
        device.setPhone("801-642-3159");        
        device.setStatus(DeviceStatus.DISABLED);
        device.setEphone("801-642-0918");
        devicesData.add(device);
        
        drt = new DeviceReportItem();
        device = new Device();
        device.setName("TIWI1117");
        device.setVehicleID(123456);
        device.setImei("1c3lc46h67n7891i4a");
        device.setPhone("801-555-1212");        
        device.setStatus(DeviceStatus.ACTIVE);
        device.setEphone("801-555-1133");
        devicesData.add(device);
        
        drt = new DeviceReportItem();
        device = new Device();
        device.setName("TIWI3917");
        device.setVehicleID(456789);
        device.setImei("1c3lc46h67n6719i4a");
        device.setPhone("801-324-8851");        
        device.setStatus(DeviceStatus.DELETED);
        device.setEphone("801-324-9817");
        devicesData.add(device);        
        
        if ( (searchFor != null) && (searchFor.trim().length() != 0) ) 
        {
            search();
        } else {
            loadResults(devicesData);
        }
        
        maxCount = deviceData.size();        
        resetCounts();
    }
        

    public List<DeviceReportItem> getDeviceData()
    {             
        return deviceData;
    }

    public void setDeviceData(List<DeviceReportItem> deviceData)
    {
        this.deviceData = deviceData;
    }
    
    public void search() {     
        if ( this.deviceData.size() > 0 ) {
            this.deviceData.clear();
        }
                           
        if ( this.searchFor.trim().length() != 0 ) {
            String trimmedSearch = this.searchFor.trim();            
            List <Device> matchedDevices = new ArrayList<Device>();    
            
            for ( Device d: devicesData ) {    
                //Fuzzy, imei
                String dev = d.getImei().toLowerCase();
                int index1 = dev.indexOf(trimmedSearch);                    
                if (index1 != -1) {                        
                    matchedDevices.add(d);
                }
                //Fuzzy, device name
                String name = d.getName().toLowerCase();
                int index2 = name.indexOf(trimmedSearch);                    
                if ( (index1 == -1) && (index2 != -1) ) {                        
                    matchedDevices.add(d);
                }
            }
            
            loadResults(matchedDevices);             
            this.maxCount = matchedDevices.size();
        //Nothing entered, show them all
        } else {
            loadResults(devicesData);
            this.maxCount = devicesData.size();
        }
        
        resetCounts();       
    }
    
    private void loadResults(List <Device> devicesData)
    {       
        for ( Device a: devicesData ) { 
            drt = new DeviceReportItem();
            drt.setDevice(a);
            deviceData.add(drt);                        
        }
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

}


