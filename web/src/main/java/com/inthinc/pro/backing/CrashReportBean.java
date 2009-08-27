package com.inthinc.pro.backing;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.CrashReportDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.CrashReport;
import com.inthinc.pro.model.CrashReportStatus;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.SelectItemUtil;

public class CrashReportBean extends BaseBean{
    
    /**
     * 
     */
    private static final long serialVersionUID = 2534932314908787098L;
    
    private static final Logger logger = Logger.getLogger(CrashReportBean.class);
    
    private enum EditState
    {
        VIEW, EDIT, ADD;
    }
    
    private EditState editState;
    
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;
    private CrashReportDAO crashReportDAO;
    private List<Trip> tripList;
    private Trip selectedTrip;
    private Boolean useExistingTrip = Boolean.TRUE;
    private List<IdentifiableEntityBean> entityList; //Used for selecting trips in the selectCrashLocation page.
    private EntityType selectedEntityType = EntityType.ENTITY_DRIVER;

    private CrashReport crashReport;
    private Integer crashTime;
    private Integer crashReportID; //Only used by pretty faces to set the crashReportID. Use crashReport when working with the crashReportID
    
       
    public List<SelectItem> getCrashReportStatusAsSelectItems(){
        return SelectItemUtil.toList(CrashReportStatus.class, true,CrashReportStatus.FORGIVEN,CrashReportStatus.DELETED);
    }
   
    public List<SelectItem> getEntityTypeAsSelectItems(){
        return SelectItemUtil.toList(EntityType.class, false, EntityType.ENTITY_GROUP);
    }
    
    private CrashReport createCrashReport(){
        crashReport = new CrashReport();
        crashReport.setCrashReportStatus(CrashReportStatus.NEW);
        crashReport.setOccupantCount(1);
        Calendar now = Calendar.getInstance(getLocale());
        now.set(Calendar.HOUR,0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        crashReport.setDate(now.getTime());
        return crashReport;
    }
    
    public void add(){
        entityList = new ArrayList<IdentifiableEntityBean>();
        logger.debug("Crash Report Add Begin");
        editState = EditState.ADD;
        setUseExistingTrip(false);
        loadVehicles();
        loadDrivers();
        createCrashReport();
        Collections.sort(entityList);
    }
   
    public void edit(){
        entityList = new ArrayList<IdentifiableEntityBean>();
        logger.debug("Crash Report Edit Begin");
        editState = EditState.EDIT;
        loadVehicles();
        loadDrivers();
        Collections.sort(entityList);
        
    }
    
    public String save(){
        if(editState.equals(EditState.ADD)){
            crashReportID = crashReportDAO.create(crashReport.getVehicleID(), crashReport);
        }else if(editState.equals(EditState.EDIT)){
            crashReportDAO.update(crashReport);
        }
        return "pretty:crashReport";
    }
    
    public String cancelEdit(){
        if(editState.equals(EditState.EDIT))
            return "pretty:crashReport";
        else
            return "pretty:crashHistory";
    }
    
//    public List<Vehicle> filterVehicleList(Object suggest){
//        logger.debug("Filtering List For Autocompletion");
//        List<Vehicle> filteredVehicleList = new ArrayList<Vehicle>();
//        for(Vehicle vehicle:vehicleList){
//            if(vehicle.getFullName().toLowerCase().indexOf(((String)suggest).toLowerCase()) > -1){
//                filteredVehicleList.add(vehicle);
//            }
//        }
//        
//        return filteredVehicleList;
//    }
    
    
//    public List<SelectItem> filterDriverList(Object suggest){
//        logger.debug("Filtering List For Autocompletion: " + suggest);
//        List<SelectItem> filteredDriverList = new ArrayList<SelectItem>();
//        for(Driver driver:driverList){
//            if(driver.getPerson().getFirst().toLowerCase().indexOf(((String)suggest).toLowerCase()) >= 0 ||
//                    driver.getPerson().getLast().toLowerCase().indexOf(((String)suggest).toLowerCase()) >= 0){
//                filteredDriverList.add(new SelectItem(driver.getDriverID(),driver.getPerson().getFullName()));
//            }
//        }
//        return filteredDriverList;
//    }
    
    public List<SelectItem> filterEntityList(Object suggest){
        logger.debug("Filtering List For Autocompletion: " + suggest);
        List<SelectItem> IdentifiableEntityBeanList = new ArrayList<SelectItem>();
        for(IdentifiableEntityBean identifiableEntityBean:this.entityList){
            if(identifiableEntityBean.getName().toLowerCase().indexOf(((String)suggest).toLowerCase()) >= 0 ||
                   identifiableEntityBean.getLongName().toLowerCase().indexOf(((String)suggest).toLowerCase()) >= 0){
                String label = identifiableEntityBean.getLongName() + " (" + 
                        MessageUtil.getMessageString(identifiableEntityBean.getEntityType().toString(), getLocale()) + ")";
                IdentifiableEntityBeanList.add(new SelectItem(identifiableEntityBean.getId(), label));
            }
        }
        return IdentifiableEntityBeanList;
    }
    
    private void loadVehicles(){
        logger.debug("loading vehicles");
        List<Vehicle> vehicleList = vehicleDAO.getVehiclesInGroupHierarchy(getGroupHierarchy().getTopGroup().getGroupID());
        for(Vehicle vehicle:vehicleList){
            entityList.add(new VehicleBean(vehicle));
        }
    }
    
    private void loadDrivers(){
        logger.debug("loading drivers");
        List<Driver> driverList = driverDAO.getAllDrivers(getGroupHierarchy().getTopGroup().getGroupID());
        for(Driver driver:driverList){
            if(driver != null)
                entityList.add(new DriverBean(driver));
        }
    }
    
    public void vehicleChanged(ActionEvent event){
        loadTrips();
    }
    
    public void loadTrips(){
        logger.debug("loading trips");
        if(selectedEntityType.equals(EntityType.ENTITY_DRIVER)){
            tripList = driverDAO.getTrips(crashReport.getDriverID() == null?0:crashReport.getDriverID(), new Date(0), new Date());
        }else {
            tripList = vehicleDAO.getTrips(crashReport.getVehicleID() == null?0:crashReport.getVehicleID(), new Date(0), new Date());
        }
        
    }
    
    public void clearSelectedVehicle(){
        crashReport.setVehicle(null);
        crashReport.setVehicleID(null);
    }
    
    public void clearSelectedDriver(){
        crashReport.setDriver(null);
        crashReport.setDriverID(null);
    }


    public List<SelectItem> getVehiclesAsSelectItems(){
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for(IdentifiableEntityBean entityBean:entityList){
            if(EntityType.ENTITY_VEHICLE.equals(entityBean.getEntityType()))
                selectItems.add(new SelectItem(entityBean.getId(),entityBean.getLongName()));
        }
        
        selectItems.add(0, new SelectItem(null, ""));
        return selectItems;
    }
    
    public List<SelectItem> getDriversAsSelectItems(){
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for(IdentifiableEntityBean entityBean:entityList){
            if(EntityType.ENTITY_DRIVER.equals(entityBean.getEntityType()))
                selectItems.add(new SelectItem(entityBean.getId(),entityBean.getLongName()));
        }
        
        selectItems.add(0, new SelectItem(null, ""));
        return selectItems;
    }
    
    public List<SelectItem> getEntitysAsSelectItems(){
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for(IdentifiableEntityBean entityBean:entityList){
            if(selectedEntityType.equals(entityBean.getEntityType()))
                selectItems.add(new SelectItem(entityBean.getId(),entityBean.getLongName()));
        }
        
        selectItems.add(0, new SelectItem(null, ""));
        return selectItems;
    }
    
    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public DriverDAO getDriverDAO() {
        return driverDAO;
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
    
    public void setSelectedVehicleID(Integer vehicleID){
        logger.debug("VehicleID set: " + vehicleID);
        if(crashReport != null){
            crashReport.setVehicle(vehicleDAO.findByID(vehicleID));
            crashReport.setVehicleID(vehicleID);
        }
    }
    
    public Integer getSelectedVehicleID(){
        return null;
    }
    
    public void setSelectedDriverID(Integer driverID){
        if(crashReport != null){
            crashReport.setDriver(driverDAO.findByID(driverID));
            crashReport.setDriverID(driverID);
        }
    }
    
    public Integer getSelectedDriverID(){
        return null;
    }

    public void setCrashReportID(Integer crashReportID) {
        this.crashReport = crashReportDAO.findByID(crashReportID);
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


    public void setCrashTime(Integer crashTime) {
        if(crashReport != null && crashReport.getDate() != null){
            Calendar dateTime = Calendar.getInstance();
            dateTime.setTime(crashReport.getDate());
            dateTime.set(Calendar.HOUR, crashTime / 60);
            dateTime.set(Calendar.MINUTE, crashTime % 60 );
        }
        this.crashTime = crashTime;
    }


    public Integer getCrashTime() {
        if(crashReport != null && crashReport.getDate() != null){
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


    
}
