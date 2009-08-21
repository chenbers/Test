package com.inthinc.pro.backing;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.CrashReportDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.CrashDataPoint;
import com.inthinc.pro.model.CrashReport;
import com.inthinc.pro.model.CrashReportStatus;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;
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
    private List<Vehicle> vehicleList;
    private List<Person> personList;
    private List<Driver> driverList;
    private List<Trip> tripList;
    private Trip selectedTrip;
    private Boolean useExistingTrip;
    private List<IdentifiableEntityBean> entityList; //Used for selecting trips in the selectCrashLocation page.
    private Integer entityID;
    

    
    private CrashReport crashReport;
    private Integer crashReportID; //Only used by pretty faces to set the crashReportID. Use crashReport when working with the crashReportID
    
       
    public List<SelectItem> getCrashReportStatusAsSelectItems(){
        return SelectItemUtil.toList(CrashReportStatus.class, true);
    }
   
    
    private CrashReport createCrashReport(){
        crashReport = new CrashReport();
        crashReport.setCrashReportStatus(CrashReportStatus.NEW);
        crashReport.setOccupantCount(1);
        
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
        
    }
   
    public void edit(){
        entityList = new ArrayList<IdentifiableEntityBean>();
        logger.debug("Crash Report Edit Begin");
        editState = EditState.EDIT;
        loadVehicles();
        loadDrivers();
        
    }
    
    public String save(){
        crashReportID = crashReportDAO.create(getAccountID(), crashReport);
        return "pretty:crashReport";
    }
    
    public String cancelEdit(){
        if(editState.equals(EditState.EDIT))
            return "pretty:crashReport";
        else
            return "pretty:crashHistory";
    }
    
    public List<Vehicle> autocomplete(Object suggest){
        logger.debug("Filtering List For Autocompletion");
        List<Vehicle> filteredVehicleList = new ArrayList<Vehicle>();
        for(Vehicle vehicle:vehicleList){
            if(vehicle.getFullName().equals(suggest)){
                filteredVehicleList.add(vehicle);
            }
        }
        
        return filteredVehicleList;
    }
    
    private void loadVehicles(){
        logger.debug("loading vehicles");
        vehicleList = vehicleDAO.getVehiclesInGroupHierarchy(getGroupHierarchy().getTopGroup().getGroupID());
        for(Vehicle vehicle:vehicleList){
            entityList.add(new VehicleBean(vehicle));
        }
    }
    
    private void loadDrivers(){
        logger.debug("loading drivers");
        driverList = driverDAO.getAllDrivers(getGroupHierarchy().getTopGroup().getGroupID());
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

        IdentifiableEntityBean identifiableEntityBean = getSelectedEntityBean();
       
        if(identifiableEntityBean.getEntityType().equals(EntityType.ENTITY_DRIVER)){
            tripList = driverDAO.getTrips(identifiableEntityBean.getId(), new Date(0), new Date());
        }else{
            tripList = vehicleDAO.getTrips(identifiableEntityBean.getId(), new Date(0), new Date());
        }
        
    }
    
    private IdentifiableEntityBean getSelectedEntityBean() {
        for(IdentifiableEntityBean entityBean:entityList){
            if(entityBean.getId().equals(entityID)){
                return entityBean;
            }
        }
        
        return null;
    }


    public List<SelectItem> getVehiclesAsSelectItems(){
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for(Vehicle vehicle:vehicleList){
            selectItems.add(new SelectItem(vehicle.getVehicleID(),vehicle.getFullName()));
        }
        selectItems.add(0, new SelectItem(null, ""));
        return selectItems;
    }
    
    public List<SelectItem> getDriversAsSelectItems(){
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for(Driver driver:driverList){
            selectItems.add(new SelectItem(driver.getDriverID(),driver.getPerson().getFullName()));
        }
        
        selectItems.add(0, new SelectItem(null, ""));
        return selectItems;
    }
    
    public List<SelectItem> getEntitysAsSelectItems(){
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for(IdentifiableEntityBean entityBean:entityList){
            selectItems.add(new SelectItem(entityBean.getId(),entityBean.getName() + " (" + entityBean.getEntityType() + ")"));
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

    public void setCrashReportID(Integer crashReportID) {
        this.crashReport = crashReportDAO.findByID(crashReportID);
        List<CrashDataPoint> crashDataPointList = new ArrayList<CrashDataPoint>();
        for(int i = 10; i > 0; i--) {
            CrashDataPoint point = new CrashDataPoint();
            point.setDate(DateUtil.getDaysBackDate(new Date(), 4));
            point.setGpsSpeed(76);
            point.setObdSpeed(70);
            point.setSeatBelt(false);
            point.setRpm(5800);
            point.setLatLng(new LatLng((40.7109991d + (i * 0.75)),(-111.9928979d) + (i * 0.75)));
            crashDataPointList.add(point);
        }
        crashReport.setCrashDataPoints(crashDataPointList);
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


    
    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    
    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
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

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setUseExistingTrip(Boolean useExistingTrip) {
        this.useExistingTrip = useExistingTrip;
    }

    public Boolean getUseExistingTrip() {
        return useExistingTrip;
    }

    public void setDriverList(List<Driver> driverList) {
        this.driverList = driverList;
    }

    public List<Driver> getDriverList() {
        return driverList;
    }

    public void setEditState(EditState editState) {
        this.editState = editState;
    }

    public EditState getEditState() {
        return editState;
    }


    public void setEntityID(Integer entityID) {
        this.entityID = entityID;
    }


    public Integer getEntityID() {
        return entityID;
    }


    public void setSelectedTrip(Trip selectedTrip) {
        logger.debug("Setting selected trip: " + selectedTrip.getRoute());
        this.selectedTrip = selectedTrip;
    }


    public Trip getSelectedTrip() {
        return selectedTrip;
    }
}
