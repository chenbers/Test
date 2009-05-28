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
import com.inthinc.pro.util.MiscUtil;

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
    protected void loadDBData()
    {       
        List<Vehicle> vehicList = vehicleDAO.getVehiclesInGroupHierarchy(getEffectiveGroupId());
        List <DeviceReportItem> list = new ArrayList<DeviceReportItem>();
        for( Vehicle v: vehicList )
        {
            // save only vehicles that have devices associated to them
            if ( v.getDeviceID() != null ) {
                Device dev = deviceDAO.findByID(v.getDeviceID());            
 
                dri = new DeviceReportItem();
                
                dri.setDevice(dev);
                dri.getDevice().setEphone(MiscUtil.formatPhone(dri.getDevice().getEphone()));
                dri.getDevice().setPhone(MiscUtil.formatPhone(dri.getDevice().getPhone()));
                dri.setVehicle(v);
                
                list.add(dri);
            }
        }
        
        Collections.sort(list, new Comparator<DeviceReportItem>()
        {        
            @Override
            public int compare(DeviceReportItem o1, DeviceReportItem o2)
            {
                return o1.getDevice().getName().toLowerCase().compareTo(o2.getDevice().getName().toLowerCase());
            }
        });  
        
        devicesData = list;
    }
    
    @Override
    protected void filterResults(List<DeviceReportItem> data)
    {
        // TODO Auto-generated method stub
        
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
    	deviceData.addAll(devicData);
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
        reportCriteria.setLocale(getUser().getLocale());
        getReportRenderer().exportSingleReportToPDF(reportCriteria, getFacesContext());
    }
    
    public void emailReport()
    {
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.DEVICES_REPORT,getGroupHierarchy().getTopGroup().getName());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setMainDataset(deviceData);
        reportCriteria.setLocale(getUser().getLocale());
        getReportRenderer().exportReportToEmail(reportCriteria,getEmailAddress());
    }
    
    public void exportReportToExcel()
    {
        ReportCriteria reportCriteria = new ReportCriteria(ReportType.DEVICES_REPORT,getGroupHierarchy().getTopGroup().getName());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setMainDataset(deviceData);
        reportCriteria.setLocale(getUser().getLocale());
        getReportRenderer().exportReportToExcel(reportCriteria, getFacesContext());
    }

	@Override
	protected void setDisplayData(List<DeviceReportItem> displayData) {

		deviceData = displayData;
		
	}
	
	@Override
    public String getMappingId()
    {
        return "pretty:devicesReport";
    }
    
    @Override
    public String getMappingIdWithCriteria()
    {
        return "pretty:devicesReportWithCriteria";
    }

}


