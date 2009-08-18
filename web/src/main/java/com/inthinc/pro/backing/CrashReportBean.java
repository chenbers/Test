package com.inthinc.pro.backing;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.richfaces.component.html.HtmlExtendedDataTable;
import org.richfaces.model.selection.Selection;

import com.inthinc.pro.dao.CrashReportDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.CrashReport;
import com.inthinc.pro.model.CrashReportStatus;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.util.SelectItemUtil;

/**
 * 
 * @author mstrong
 *
 */
public class CrashReportBean extends BaseBean{
    
    /**
     * 
     */
    private static final long serialVersionUID = 2534932314908787098L;
    
    private static final Logger logger = Logger.getLogger(CrashReportBean.class);
    
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;
    private CrashReportDAO crashReportDAO;
    private PersonDAO personDAO;
    List<Vehicle> vehicleList;
    private List<Person> personList;
    List<Trip> tripList;
    List<IdentifiableEntityBean> entityList; //Used for selecting trips in the selectCrashLocation page.
    
    private CrashReport crashReport;
    private Integer crashReportID; //Only used by pretty faces to set the crashReportID. Use crashReport when working with the crashReportID
    
    
    private Selection entitySelection;
    private Selection tripSelection;
    private HtmlExtendedDataTable entityHtmlScrollableDataTable;
    private HtmlExtendedDataTable tripHtmlScrollableDataTable;
       
    
    public List<SelectItem> getCrashReportStatusAsSelectItems(){
        return SelectItemUtil.toList(CrashReportStatus.class, true);
    }
    
    private IdentifiableEntityBean getSelectedEntityBean(){
        Iterator<Object> iterator = entitySelection.getKeys();
        while(iterator.hasNext())
        {
            Object key =  iterator.next();
            entityHtmlScrollableDataTable.setRowKey(key);
            if(entityHtmlScrollableDataTable.isRowAvailable())
            {
                IdentifiableEntityBean identifiableEntityBean = (IdentifiableEntityBean)entityHtmlScrollableDataTable.getRowData();
                return identifiableEntityBean;
            }
        }
        
        return null;
    }
    
    private CrashReport createCrashReport(){
        crashReport = new CrashReport();
        return crashReport;
    }
    
    public void add(){
        entityList = new ArrayList<IdentifiableEntityBean>();
        logger.debug("Crash Report Add Begin");
        loadVehicles();
        loadDrivers();
        createCrashReport();
        
    }
    
    public void edit(){
        entityList = new ArrayList<IdentifiableEntityBean>();
        logger.debug("Crash Report Edit Begin");
        loadVehicles();
        loadDrivers();
        
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
        personList = personDAO.getPeopleInGroupHierarchy(getGroupHierarchy().getTopGroup().getGroupID());
        for(Person person:personList){
            if(person.getDriver() != null)
                entityList.add(new DriverBean(person.getDriver()));
        }
    }
    
    public void vehicleChanged(ActionEvent event){
        loadTrips();
    }
    
    public void loadTrips(){
        logger.debug("loading trips");

        IdentifiableEntityBean identifiableEntityBean = getSelectedEntityBean();
        if(identifiableEntityBean == null){
            logger.debug("selection is null");
            return;
        }
        if(identifiableEntityBean.getEntityType().equals(EntityType.ENTITY_DRIVER)){
            tripList = driverDAO.getTrips(identifiableEntityBean.getId(), new Date(0), new Date());
        }else{
            tripList = vehicleDAO.getTrips(identifiableEntityBean.getId(), new Date(0), new Date());
        }
        
    }
    
    public List<SelectItem> getVehiclesAsSelectItems(){
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for(Vehicle vehicle:vehicleList){
            selectItems.add(new SelectItem(vehicle.getVehicleID(),vehicle.getFullName()));
        }
        
        return selectItems;
    }
    
    public List<SelectItem> getPeopleAsSelectItems(){
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for(Vehicle vehicle:vehicleList){
            selectItems.add(new SelectItem(vehicle.getVehicleID(),vehicle.getFullName()));
        }
        
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
        if(crashReport != null && crashReportID != null)
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

    public void setEntitySelection(Selection entitySelection) {
        this.entitySelection = entitySelection;
    }

    public Selection getEntitySelection() {
        return entitySelection;
    }

    public void setTripSelection(Selection tripSelection) {
        this.tripSelection = tripSelection;
    }

    public Selection getTripSelection() {
        return tripSelection;
    }

    public void setEntityHtmlScrollableDataTable(HtmlExtendedDataTable entityHtmlScrollableDataTable) {
        this.entityHtmlScrollableDataTable = entityHtmlScrollableDataTable;
    }

    public HtmlExtendedDataTable getEntityHtmlScrollableDataTable() {
        return entityHtmlScrollableDataTable;
    }

    public void setTripHtmlScrollableDataTable(HtmlExtendedDataTable tripHtmlScrollableDataTable) {
        this.tripHtmlScrollableDataTable = tripHtmlScrollableDataTable;
    }

    public HtmlExtendedDataTable getTripHtmlScrollableDataTable() {
        return tripHtmlScrollableDataTable;
    }

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public PersonDAO getPersonDAO() {
        return personDAO;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public List<Person> getPersonList() {
        return personList;
    }


}
