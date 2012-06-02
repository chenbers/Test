package com.inthinc.pro.dao.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.AdminDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

public class MockAdminDAO implements AdminDAO {

    private static final Logger logger = Logger.getLogger(MockAdminDAO.class);
    
    @Override
    public Integer getPersonCount(Integer groupID, List<TableFilterField> filters) {
logger.info("getPersonCount: groupID: " + groupID + " filters: " + filters.toString());
        return 100;
    }

    @Override
    public List<Person> getPersonPage(Integer groupID, PageParams pageParams) {
logger.info("getPersonPage: groupID: " + groupID + " pageParams: " + pageParams.toString());        
        List<Person> personList = new ArrayList<Person>();
        for(int i = pageParams.getStartRow(); i < pageParams.getEndRow(); i++)
            personList.add(new Person(i, 100, TimeZone.getDefault(),  null,  "email_" + i + "_" + 1000 + "@yahoo.com", null, "555555555" + 9, "555555555" + 9, null, null, null, null, null,
                    i+"-"+"EMP" + 1000, null, "title", "dept" , "first", "m", "last", "jr", Gender.MALE, 65, 180, genDate(1959, 8, 30), Status.ACTIVE, MeasurementType.ENGLISH,
                    FuelEfficiencyType.MPG_US, Locale.getDefault()));

        
        return personList;
    }
    
    
    public Date genDate(Integer year, Integer month, Integer day)
    {
        GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        cal.set(year, month, day, 0, 0, 1);
        cal.setTimeInMillis((cal.getTimeInMillis()/1000l) * 1000l);
        
        
        return cal.getTime();
        
    }

    @Override
    public Integer create(Integer id, Object entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer deleteByID(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object findByID(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer update(Object entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer getDeviceCount(Integer groupID, List<TableFilterField> filters) {
        return 200;
    }

    @Override
    public List<Device> getDevicePage(Integer groupID, PageParams pageParams) {
        logger.info("getDevicePage: groupID: " + groupID + " pageParams: " + pageParams.toString());
        List<Device> deviceList = new ArrayList<Device>();
        for(int i = pageParams.getStartRow(); i < pageParams.getEndRow(); i++) {
            Device device = new Device(0, groupID, DeviceStatus.NEW, "Device " + i, "IMEI " + groupID + i, "SIM " + i, 
                    "SN" + groupID + i,
                    "555555123" + i);
            if (i % 2 == 0){
                device.setProductVersion(ProductType.WAYSMART);
                device.setProductVer(ProductType.WAYSMART.getVersions()[0]);
            }
            else {
                device.setProductVersion(ProductType.TIWIPRO);
                device.setProductVer(ProductType.TIWIPRO.getVersions()[1]);
            }
            deviceList.add(device);

        }
        return deviceList;
    }

    @Override
    public Integer getVehicleCount(Integer groupID, List<TableFilterField> filters) {
        return 50;
    }

    @Override
    public List<Vehicle> getVehiclePage(Integer groupID, PageParams pageParams) {
        logger.info("getVehiclePage: groupID: " + groupID + " pageParams: " + pageParams.toString());
        List<Vehicle> vehicleList  = new ArrayList<Vehicle>();
        for(int i = pageParams.getStartRow(); i < pageParams.getEndRow(); i++) {
            Vehicle vehicle = new Vehicle(0, groupID, Status.ACTIVE, "Vehicle " + i, "Make " + i, "Model " + i, 2000 + i, "COLOR " + i, 
                    VehicleType.valueOf(i % 3), "VIN_" + groupID + "_" + i, 1000, "License " + i, null);
            vehicleList.add(vehicle);

        }
        return vehicleList;
    }

}


