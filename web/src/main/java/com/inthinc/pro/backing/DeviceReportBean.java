package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceReportItem;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;

public class DeviceReportBean extends BaseReportBean<DeviceReportItem>
{
    private static final Logger logger = Logger.getLogger(DeviceReportBean.class);
    
    //devicesData is the ONE read from the db, deviceData is what is displayed
    private List <DeviceReportItem> devicesData = new ArrayList<DeviceReportItem>(); 
    private List <DeviceReportItem> deviceData = new ArrayList<DeviceReportItem>();
    
    static final List<String> AVAILABLE_COLUMNS;

    private VehicleDAO vehicleDAO;
    private DeviceDAO deviceDAO;
   
    private DeviceReportItem dri = null;
    
    private final static String COLUMN_LABEL_PREFIX = "deviceReports_";

    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("device_name");
        AVAILABLE_COLUMNS.add("vehicle_name");
        AVAILABLE_COLUMNS.add("device_imei");
        AVAILABLE_COLUMNS.add("device_phone");
        AVAILABLE_COLUMNS.add("device_status");   
        AVAILABLE_COLUMNS.add("device_ephone");
    }
    
    public DeviceReportBean()
    {
        super();
    }
    
    @Override
    public void initBean() 
    {
        super.initBean();
       
        Collections.sort(this.devicesData,new Comparator<DeviceReportItem>()
        {
        
            @Override
            public int compare(DeviceReportItem o1, DeviceReportItem o2)
            {
                return o1.getDevice().getName().toLowerCase().compareTo(o2.getDevice().getName().toLowerCase());
            }
        });  
    }

    @Override
    protected void loadDBData()
    {
    	devicesData.clear();
    	
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
    }
        

    public List<DeviceReportItem> getDeviceData()
    {             
        return deviceData;
    }

    public void setDeviceData(List<DeviceReportItem> deviceData)
    {
        this.deviceData = deviceData;
    }

    @Override
    protected List<DeviceReportItem> getDBData()
    {
        return devicesData;
    }

    @Override
    protected List<DeviceReportItem> getDisplayData()
    {
        return deviceData;
    }

    @Override
    protected void loadResults(List <DeviceReportItem> devicData)
    {     
    	deviceData = new ArrayList<DeviceReportItem>();
        for ( DeviceReportItem a: devicData ) { 
            dri = new DeviceReportItem();
            dri.setDevice(a.getDevice());
            dri.setVehicle(a.getVehicle());
            deviceData.add(dri);                        
        }
        
        this.maxCount = this.deviceData.size();   
        resetCounts(); 
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
    public TableType getTableType()
    {
        return TableType.DEVICE_REPORT;
    }

    public void exportReportToPdf()
    {
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.DEVICES_REPORT,getGroupHierarchy().getTopGroup().getName());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setMainDataset(deviceData);
        getReportRenderer().exportSingleReportToPDF(reportCriteria, getFacesContext());
    }
    
    public void emailReport()
    {
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.DEVICES_REPORT,getGroupHierarchy().getTopGroup().getName());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setMainDataset(deviceData);
        getReportRenderer().exportReportToEmail(reportCriteria,getEmailAddress());
    }
    
    public void exportReportToExcel()
    {
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.DEVICES_REPORT,getGroupHierarchy().getTopGroup().getName());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setMainDataset(deviceData);
        getReportRenderer().exportReportToExcel(reportCriteria, getFacesContext());
    }

	@Override
	protected void setDisplayData(List<DeviceReportItem> displayData) {

		deviceData = displayData;
		
	}

}


