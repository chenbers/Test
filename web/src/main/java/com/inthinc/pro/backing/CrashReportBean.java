package com.inthinc.pro.backing;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

import org.ajax4jsf.model.KeepAlive;
import org.apache.log4j.Logger;

import com.inthinc.pro.dao.CrashReportDAO;
import com.inthinc.pro.dao.CrashTraceDAO;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.CrashReport;
import com.inthinc.pro.model.CrashReportStatus;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.SelectItemUtil;

@KeepAlive
public class CrashReportBean extends BaseBean {

    /**
     * 
     */
    private static final long serialVersionUID = 2534932314908787098L;

    private static final Logger logger = Logger.getLogger(CrashReportBean.class);

    private enum EditState {
        VIEW, EDIT, ADD;
    }

    private EditState editState;

    private DeviceDAO deviceDAO;
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;
    private CrashReportDAO crashReportDAO;
    private CrashTraceDAO crashTraceDAO;
    private EventDAO eventDAO;
    private List<Trip> tripList;
    private Trip selectedTrip;
    private Boolean useExistingTrip;
    private List<IdentifiableEntityBean> entityList; // Used for selecting trips in the selectCrashLocation page.
    private EntityType selectedEntityType = EntityType.ENTITY_DRIVER;

    private CrashReport crashReport;
    private String crashTraceEventID;
    private CrashTraceBean crashTraceBean;
    private Integer crashTime;
    private Integer crashReportID; // Only used by pretty faces to set the crashReportID. Use crashReport when working with the crashReportID
    private Trip crashReportTrip;
    private File crashTraceFile;
    private FileUploadBean fileUploadBean;
    
    public void serveCrashTrace() {
        HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();

        OutputStream out = null;
        try {
            out = response.getOutputStream();
            //TODO: jwimmer: populate crashTrace from this.getCrashTraceEventID()
            if(crashTraceBean == null) {
                //TODO: jwimmer: add logic to check if eventID is null, if not load REAL crash trace
                if(this.getCrashTraceEventID() != null && !("".equalsIgnoreCase(this.getCrashTraceEventID()))) {
                    crashTraceBean = new CrashTraceBean(this.getCrashTraceEventID());
                } else {
                    crashTraceBean = new CrashTraceBean();
                }
            }
            crashTraceBean.getMockObject().write(out);
            //crashTraceBean.write(out);//TODO: jwimmer: when MOCK crashtrace is not needed anymore this line represents the RIGHT way to do this

            out.flush();
            getFacesContext().responseComplete();
        } catch (IOException e) {
            logger.error(e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }
    private Driver unkDriver = null;

    public String getUnits() {
        return MessageUtil.getMessageString(getPerson().getMeasurementType()+"_miles");
    }
    
    public List<SelectItem> getCrashReportStatusAsSelectItems() {
        return SelectItemUtil.toList(CrashReportStatus.class, true, CrashReportStatus.DELETED);
    }

    public List<SelectItem> getEntityTypeAsSelectItems() {
        return SelectItemUtil.toList(EntityType.class, false, EntityType.ENTITY_GROUP);
    }

    private CrashReport createCrashReport() {
        crashReport = new CrashReport();
        crashReport.setCrashReportStatus(CrashReportStatus.NEW);
        crashReport.setOccupantCount(1);
        Calendar now = Calendar.getInstance(getLocale());
        now.set(Calendar.HOUR, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        crashReport.setDate(now.getTime());
        useExistingTrip = Boolean.TRUE;
        return crashReport;
    }

    public void add() {
        entityList = new ArrayList<IdentifiableEntityBean>();
        logger.debug("Crash Report Add Begin");
        editState = EditState.ADD;
        // Do we want to use this?
        setUseExistingTrip(Boolean.TRUE);
        loadVehicles();
        loadDrivers();
        createCrashReport();
        Collections.sort(entityList);
    }

    public void edit() {
        if (entityList == null) {
            entityList = new ArrayList<IdentifiableEntityBean>();
            logger.debug("Crash Report Edit Begin");
            editState = EditState.EDIT;
            loadVehicles();
            loadDrivers();

            selectedTrip = crashReportDAO.getTrip(crashReport);
            if (selectedTrip != null) {
                setUseExistingTrip(Boolean.TRUE);
                
                if (crashReport.getDriverID() != null && crashReport.getDriverID().equals(selectedTrip.getDriverID()))
                    selectedEntityType = EntityType.ENTITY_DRIVER;
                else if (crashReport.getVehicleID() != null && crashReport.getVehicleID().equals(selectedTrip.getVehicleID()))
                    selectedEntityType = EntityType.ENTITY_VEHICLE;

                loadTrips();
            } else {
                setUseExistingTrip(Boolean.FALSE);
            }

            if (crashReport.getCrashDataPoints() != null && crashReport.getCrashDataPoints().size() > 0) {
                setUseExistingTrip(Boolean.FALSE);
            }

            Collections.sort(entityList);
        }
    }

    public String save() {
        // Fix to make sure the correct date for the crash is loaded
        updateCrashTime();
               
        if (editState.equals(EditState.ADD)) {
            crashReportID = crashReportDAO.create(crashReport.getVehicleID(), crashReport);
        } else if (editState.equals(EditState.EDIT)) {
            crashReportDAO.update(crashReport);
        }
        return "pretty:crashReport";
    }

    public String cancelEdit() {
        if (editState.equals(EditState.EDIT))
            return "pretty:crashReport";
        else
            return "pretty:crashHistory";
    }

    // public List<Vehicle> filterVehicleList(Object suggest){
    // logger.debug("Filtering List For Autocompletion");
    // List<Vehicle> filteredVehicleList = new ArrayList<Vehicle>();
    // for(Vehicle vehicle:vehicleList){
    // if(vehicle.getFullName().toLowerCase().indexOf(((String)suggest).toLowerCase()) > -1){
    // filteredVehicleList.add(vehicle);
    // }
    // }
    //        
    // return filteredVehicleList;
    // }

    // public List<SelectItem> filterDriverList(Object suggest){
    // logger.debug("Filtering List For Autocompletion: " + suggest);
    // List<SelectItem> filteredDriverList = new ArrayList<SelectItem>();
    // for(Driver driver:driverList){
    // if(driver.getPerson().getFirst().toLowerCase().indexOf(((String)suggest).toLowerCase()) >= 0 ||
    // driver.getPerson().getLast().toLowerCase().indexOf(((String)suggest).toLowerCase()) >= 0){
    // filteredDriverList.add(new SelectItem(driver.getDriverID(),driver.getPerson().getFullName()));
    // }
    // }
    // return filteredDriverList;
    // }

    public List<SelectItem> filterEntityList(Object suggest) {
        logger.debug("Filtering List For Autocompletion: " + suggest);
        List<SelectItem> IdentifiableEntityBeanList = new ArrayList<SelectItem>();
        for (IdentifiableEntityBean identifiableEntityBean : this.entityList) {
            if (identifiableEntityBean.getName().toLowerCase().indexOf(((String) suggest).toLowerCase()) >= 0
                    || identifiableEntityBean.getLongName().toLowerCase().indexOf(((String) suggest).toLowerCase()) >= 0) {
                String label = identifiableEntityBean.getLongName() + " (" + MessageUtil.getMessageString(identifiableEntityBean.getEntityType().toString(), getLocale()) + ")";
                IdentifiableEntityBeanList.add(new SelectItem(identifiableEntityBean.getId(), label));
            }
        }
        return IdentifiableEntityBeanList;
    }
    
    public Device getDevice() {
        //TODO: jwimmer: could be improved by adding the device to what is initially retrieved from the backend
        Device result = null;
        Integer devID = crashReport.getVehicle().getDeviceID();
        if(devID != null){
            result = deviceDAO.findByID(crashReport.getVehicle().getDeviceID());
        }else{
            result = new Device();
            result.setProductVer(null); //set product ver to null so that device capabilities all come back FALSE
        }
        return result;
    }

    private void loadVehicles() {
        logger.debug("loading vehicles");
        List<Vehicle> vehicleList = vehicleDAO.getVehiclesInGroupHierarchy(getGroupHierarchy().getTopGroup().getGroupID());
        for (Vehicle vehicle : vehicleList) {
            entityList.add(new VehicleBean(vehicle));
        }
    }

    private void loadDrivers() {
        logger.debug("loading drivers");
        List<Driver> driverList = driverDAO.getAllDrivers(getGroupHierarchy().getTopGroup().getGroupID());
        
        // Add the unknown driver, should they exist
        if ( unkDriver != null ) {
            driverList.add(unkDriver);
        }
        for (Driver driver : driverList) {
            if (driver != null)
                entityList.add(new DriverBean(driver));
        }
    }

    public void vehicleChanged(ActionEvent event) {
        loadTrips();
    }

    public void loadTrips() {
        logger.debug("loading trips");
        Calendar searchStartDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Calendar searchEndDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        searchEndDate.roll(Calendar.DATE, 1);
        if (crashReport.getDate() != null) {
            searchStartDate.setTime(crashReport.getDate());
            searchEndDate.setTime(crashReport.getDate());
            resetTime(searchEndDate);
            resetTime(searchStartDate);
            searchEndDate.roll(Calendar.DATE, 1);
        }

        logger.debug("Begin Date: " + searchStartDate.getTime());
        logger.debug("End Date: " + searchEndDate.getTime());

        tripList = Collections.emptyList();
        if (selectedEntityType.equals(EntityType.ENTITY_DRIVER)) {
            if ( crashReport.getDriverID() != null ) {
                tripList = driverDAO.getTrips(crashReport.getDriverID(), searchStartDate.getTime(), searchEndDate.getTime());
            }
        } else {
            if ( crashReport.getVehicleID() != null ) {                             
                tripList = vehicleDAO.getTrips(crashReport.getVehicleID(), searchStartDate.getTime(), searchEndDate.getTime());
            }
        }

    }

    public void resetTime(Calendar calendar) {
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
    }

    public void clearSelectedVehicle() {
        crashReport.setVehicle(null);
        crashReport.setVehicleID(null);
    }

    public void clearSelectedDriver() {
        crashReport.setDriver(null);
        crashReport.setDriverID(null);
    }

    public List<SelectItem> getVehiclesAsSelectItems() {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (IdentifiableEntityBean entityBean : entityList) {
            if (EntityType.ENTITY_VEHICLE.equals(entityBean.getEntityType()))
                selectItems.add(new SelectItem(entityBean.getId(), entityBean.getLongName()));
        }

        selectItems.add(0, new SelectItem(null, ""));
        return selectItems;
    }

    public List<SelectItem> getDriversAsSelectItems() {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (IdentifiableEntityBean entityBean : entityList) {
            if (EntityType.ENTITY_DRIVER.equals(entityBean.getEntityType()))
                selectItems.add(new SelectItem(entityBean.getId(), entityBean.getLongName()));
        }

        selectItems.add(0, new SelectItem(null, ""));
        return selectItems;
    }

    public List<SelectItem> getEntitysAsSelectItems() {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (IdentifiableEntityBean entityBean : entityList) {
            if (selectedEntityType.equals(entityBean.getEntityType()))
                selectItems.add(new SelectItem(entityBean.getId(), entityBean.getLongName()));
        }

        selectItems.add(0, new SelectItem(null, ""));
        return selectItems;
    }

    public void updateCrashTime() {
        Integer driverID = null;
        if (selectedEntityType.equals(EntityType.ENTITY_DRIVER) && crashReport.getDriverID() != null)
            driverID = crashReport.getDriverID();
        else if (selectedEntityType.equals(EntityType.ENTITY_VEHICLE) && crashReport.getVehicle() != null)
            driverID = crashReport.getVehicle().getDriverID();

        if (driverID != null) {
            logger.debug("loading event to get event time");
            
            // Could be a crash with no trip so don't try to find one
            if ( getSelectedTrip() != null ) {
                Event event = eventDAO.getEventNearLocation(crashReport.getDriverID(), crashReport.getLat(), crashReport.getLng(), getSelectedTrip().getStartTime(), getSelectedTrip()
                    .getEndTime());
                crashReport.setDate(event.getTime());
            }
        }
    }
    
    public void setLatLngAction() {
        
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public DriverDAO getDriverDAO() {
        return driverDAO;
    }
    public void setDeviceDAO(DeviceDAO deviceDAO){
        this.deviceDAO = deviceDAO;
    }
    public DeviceDAO getDeviceDAO() {
        return deviceDAO;
    }
    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    public void setCrashReportDAO(CrashReportDAO crashReportDAO) {
        this.crashReportDAO = crashReportDAO;
    }

    public CrashReportDAO getCrashReportDAO() {
        return crashReportDAO;
    }

    public void setSelectedVehicleID(Integer vehicleID) {
        logger.debug("VehicleID set: " + vehicleID);
        if (crashReport != null) {
            crashReport.setVehicle(vehicleDAO.findByID(vehicleID));
            crashReport.setVehicleID(vehicleID);
        }
    }

    public Integer getSelectedVehicleID() {
        return null;
    }

    public void setSelectedDriverID(Integer driverID) {
        if (crashReport != null) {
            crashReport.setDriver(driverDAO.findByID(driverID));
            crashReport.setDriverID(driverID);
        }
    }

    public Integer getSelectedDriverID() {
        return null;
    }

    public void setCrashReportID(Integer crashReportID) {
        this.crashReport = crashReportDAO.findByID(crashReportID);
        
        // Check if the driver in this crash is the unknown driver
        if ( this.crashReport.getDriver().getDriverID() != null ) {
            Account acct = this.getAccountDAO().findByID(this.getProUser().getUser().getPerson().getAcctID());
            if ( this.crashReport.getDriver().getDriverID().equals(acct.getUnkDriverID()) ) {
                Person p = new Person();
                p.setFirst(MessageUtil.getMessageString("notes_general_unknown",getLocale()));
                p.setLast(MessageUtil.getMessageString("notes_general_driver",getLocale()));
                this.crashReport.getDriver().setPerson(p);
                this.unkDriver = this.crashReport.getDriver();
            }
        }

        this.crashReportID = crashReportID;
    }

    public CrashReport getCrashReport() {
        return crashReport;
    }

    public void setCrashReport(CrashReport crashReport) {
        this.crashReport = crashReport;
    }

    public Integer getCrashReportID() {
        return crashReportID;
    }

    public List<Trip> getTripList() {
        return tripList;
    }

    public void setTripList(List<Trip> tripList) {
        this.tripList = tripList;
    }

    public List<IdentifiableEntityBean> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<IdentifiableEntityBean> entityList) {
        this.entityList = entityList;
    }

    public void setUseExistingTrip(Boolean useExistingTrip) {
        this.useExistingTrip = useExistingTrip;
    }

    public Boolean getUseExistingTrip() {
        return useExistingTrip;
    }

    public void setEditState(EditState editState) {
        this.editState = editState;
    }

    public EditState getEditState() {
        return editState;
    }

    public void setSelectedTrip(Trip selectedTrip) {
        logger.debug("Setting selected trip: " + selectedTrip.getRoute());
        this.selectedTrip = selectedTrip;
    }

    public Trip getSelectedTrip() {
        return selectedTrip;
    }

    // returns the Trip associated with the current CrashReport. Used on the Crash Report Detail page
    public Trip getCrashReportTrip() {
        if (crashReport != null && crashReportTrip == null) {
            crashReportTrip = crashReportDAO.getTrip(crashReport);
        }
        
        // if, after looking for the trip, we don't find one, must be a
        //  crash with just a crash location
//        if ( crashReportTrip == null ) {
//            setUseExistingTrip(Boolean.FALSE);           
//        } else {
//            setUseExistingTrip(Boolean.TRUE);
//        }
        
        return crashReportTrip;
    }

    public void setCrashTime(Integer crashTime) {
        if (crashReport != null && crashReport.getDate() != null) {
            Calendar dateTime = Calendar.getInstance();
            dateTime.setTime(crashReport.getDate());
            dateTime.set(Calendar.HOUR, crashTime / 60);
            dateTime.set(Calendar.MINUTE, crashTime % 60);
        }
        this.crashTime = crashTime;
    }

    public Integer getCrashTime() {
        if (crashReport != null && crashReport.getDate() != null) {
            Calendar dateTime = Calendar.getInstance();
            dateTime.setTime(crashReport.getDate());
            int time = dateTime.get(Calendar.HOUR) * 60 * 60;
            time += dateTime.get(Calendar.MINUTE) * 60;
            crashTime = time;
        }
        return crashTime;
    }

    public void setSelectedEntityType(EntityType selectedEntityType) {
        this.selectedEntityType = selectedEntityType;
    }

    public EntityType getSelectedEntityType() {
        return selectedEntityType;
    }

    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    public EventDAO getEventDAO() {
        return eventDAO;
    }

    public File getCrashTraceFile() {
        return crashTraceFile;
    }

    public void setCrashTraceFile(File crashTraceFile) {
        this.crashTraceFile = crashTraceFile;
    }

    public FileUploadBean getFileUploadBean() {
        return fileUploadBean;
    }

    public void setFileUploadBean(FileUploadBean fileUploadBean) {
        this.fileUploadBean = fileUploadBean;
    }

    public String getCrashTraceEventID() {
        return crashTraceEventID;
    }

    public void setCrashTraceEventID(String crashTraceEventID) {
        this.crashTraceEventID = crashTraceEventID;
    }

    public void setCrashTraceBean(CrashTraceBean crashTraceBean) {
        this.crashTraceBean = crashTraceBean;
    }

    public CrashTraceBean getCrashTraceBean() {
        return crashTraceBean;
    }

}
