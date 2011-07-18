package com.inthinc.pro.backing.importer.row;

import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.backing.importer.BulkImportBean;
import com.inthinc.pro.backing.importer.VehicleTemplateFormat;
import com.inthinc.pro.backing.importer.datacheck.DataCache;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.app.States;


public class VehicleRowImporter extends RowImporter {

    private static final Logger logger = Logger.getLogger(BulkImportBean.class);
    private VehicleDAO vehicleDAO;
    
    private DataCache dataCache;
    

    @Override
    public String importRow(List<String> rowData) {

        try {
            String accountName = rowData.get(VehicleTemplateFormat.ACCOUNT_NAME_IDX);
            Account account = this.getAccountMap().get(accountName);
            
            String groupPath = rowData.get(VehicleTemplateFormat.TEAM_PATH_IDX);
            Integer groupID = findOrCreateGroupByPath(groupPath, account.getAccountID());
            
            
            String vin = rowData.get(VehicleTemplateFormat.VIN_IDX);
            Vehicle vehicle = dataCache.getVehicleForVIN(vin);
            
            vehicle = createOrUpdateVehicle(vehicle, vin, account.getAccountID(), groupID,
                    rowData.get(VehicleTemplateFormat.MAKE_IDX),
                    rowData.get(VehicleTemplateFormat.MODEL_IDX),
                    rowData.get(VehicleTemplateFormat.YEAR_IDX),
                    rowData.get(VehicleTemplateFormat.VEHICLE_NAME_IDX),
                    rowData.get(VehicleTemplateFormat.STATE_IDX),
                    rowData.get(VehicleTemplateFormat.LICENSE_IDX)
                    );
            
            
            if (rowData.size() > VehicleTemplateFormat.DEVICE_SERIAL_NUMBER_IDX) {
                String deviceSerialorIMEI = rowData.get(VehicleTemplateFormat.DEVICE_SERIAL_NUMBER_IDX);
                if (deviceSerialorIMEI != null && !deviceSerialorIMEI.isEmpty()) {
                    Device device = dataCache.getDeviceForSerialNumber(deviceSerialorIMEI);
                    if (device == null)
                        device = dataCache.getDeviceForIMEI(deviceSerialorIMEI);
                    if (device == null) {
                        logger.info("Vehicle/Device assignment failed.  Device not found for " + deviceSerialorIMEI);
                    }
                    else {
                        vehicleDAO.setVehicleDevice(vehicle.getVehicleID(), device.getDeviceID());
                        logger.info("Vehicle/Device assignment successful. " + vehicle.toString() + " " + device.toString());
                    }
                }
            }

            if (rowData.size() > VehicleTemplateFormat.DRIVER_EMPLOYEE_ID_IDX) {
                String employeeID = rowData.get(VehicleTemplateFormat.DRIVER_EMPLOYEE_ID_IDX);
                if (employeeID != null && !employeeID.isEmpty()) {
                    Person person = dataCache.getPersonForEmployeeID(account.getAccountID(), employeeID);
                    if (person == null || person.getDriverID() == null){
                        logger.info("Vehicle/Driver assignment failed.  Driver not found for " + employeeID);
                    }
                    else {
                        vehicleDAO.setVehicleDriver(vehicle.getVehicleID(), person.getDriverID());
                        logger.info("Vehicle/Driver assignment successful. " + vehicle.toString() + " " + person.getDriver().toString());
                    }
                    
                }
            }

            
            
        }
        catch (ProDAOException proEx) {
            return "ERROR: " + proEx.getMessage();
        }
        catch (Throwable t) {
            return "ERROR: " + t.getMessage();
        }
        return null;
    }

    private Vehicle createOrUpdateVehicle(Vehicle vehicle, String vin, Integer accountID, Integer groupID, String make, String model, String yearStr,
            String name, String stateAbbrev, String license) {
        boolean isCreate = false;
        if (vehicle == null) {
            isCreate = true;
            vehicle = new Vehicle();
        }

        vehicle.setGroupID(groupID);
        vehicle.setMake(make);
        vehicle.setModel(model);
        Integer year = null;
        try {
            year = Integer.valueOf(yearStr);
        }
        catch (NumberFormatException ex) {
            
        }
        vehicle.setYear(year);
        vehicle.setName(name);
        vehicle.setState(States.getStateByAbbrev(stateAbbrev));
        vehicle.setStatus(Status.ACTIVE);
        vehicle.setVIN(vin);
        vehicle.setLicense(license);
        
        if (isCreate) {
            Integer vehicleID = vehicleDAO.create(accountID, vehicle);
            vehicle.setVehicleID(vehicleID);
            logger.info("Created  " + vehicle.toString());

        }
        else {
            vehicleDAO.update(vehicle);
            logger.info("Updated  " + vehicle.toString());
        }
        
        return vehicle;
        
    }
    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }
    public DataCache getDataCache() {
        return dataCache;
    }

    public void setDataCache(DataCache dataCache) {
        this.dataCache = dataCache;
    }

}
