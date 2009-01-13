package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceReportItem;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Vehicle;

public class DeviceReportBean extends BaseReportBean implements TablePrefOptions
{
    private static final Logger logger = Logger.getLogger(DeviceReportBean.class);
    
    //devicesData is the ONE read from the db, deviceData is what is displayed
    private List <DeviceReportItem> devicesData = new ArrayList<DeviceReportItem>(); 
    private List <DeviceReportItem> deviceData = new ArrayList<DeviceReportItem>();
    
    static final List<String> AVAILABLE_COLUMNS;

    private TablePreferenceDAO tablePreferenceDAO;
    private VehicleDAO vehicleDAO;
    private DeviceDAO deviceDAO;
   
    private DeviceReportItem dri = null;
    
    private Integer numRowsPerPg = 25;
    private final static String COLUMN_LABEL_PREFIX = "deviceReports_";
    
    private Integer maxCount = null;
    private Integer start = 1;
    private Integer end = numRowsPerPg;
    
    private String searchFor = "";
    private String secret = "";

    private TablePref tablePref;

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
    
    public void init() 
    {
        tablePref = new TablePref(this);

        searchFor = checkForRequestMap();
        
        List<Vehicle> vehicList = 
            vehicleDAO.getVehiclesInGroupHierarchy(getUser().getGroupID());

        for( Vehicle v: vehicList )
        {
            // save only vehicles that have devices associated to them
            if ( v.getDeviceID() != null ) {
                Device dev = deviceDAO.findByID(v.getDeviceID());            
 
                dri = new DeviceReportItem();
                
                dri.setDevice(dev);
                dri.getDevice().setEphone(formatPhone(dri.getDevice().getEphone()));
                dri.getDevice().setPhone(formatPhone(dri.getDevice().getPhone()));
                dri.setVehicle(v);
                
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
            dri.setVehicle(a.getVehicle());
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


    @Override
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
        return TableType.DEVICE_REPORT;
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

}


