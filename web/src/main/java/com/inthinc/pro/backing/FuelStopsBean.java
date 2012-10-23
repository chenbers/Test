package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.springframework.beans.BeanUtils;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.backing.model.LocateVehicleByTime;
import com.inthinc.pro.backing.ui.DateRange;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.hessian.exceptions.HessianException;
import com.inthinc.pro.dao.util.NumberUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleDOTType;
import com.inthinc.pro.model.VehicleName;
import com.inthinc.pro.model.configurator.SettingType;
import com.inthinc.pro.model.configurator.VehicleSetting;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.table.PageData;
import com.inthinc.pro.util.MessageUtil;

public class FuelStopsBean extends BaseBean {

    private static final long serialVersionUID = 1L;
    
    private Integer vehicleID;
    private Vehicle vehicle;
    @SuppressWarnings("unused")
	private String vehicleName;
    private String vehicleNameNow;
	private FuelStopView item;
	
    UIInput truckGallonsUI;
    UIInput trailerGallonsUI;

    private ItemLoader itemLoader;
    
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;
    private ConfiguratorDAO configuratorDAO;
    public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
		this.configuratorDAO = configuratorDAO;
	}
	private HOSDAO hosDAO;
    private LocateVehicleByTime locateVehicleByTime;
    private PageData pageData;
    
    private List<SelectItem> driversSelectItems;
    private List<SelectItem> vehiclesSelectItems;
    
    private DateRange dateRange;
    
    private boolean   allSelected;
    
    private CRUDStrategy crudStrategy;
    
    private static final String EDIT_REDIRECT = "pretty:fuelStopEdit";    
    private static final String VIEW_REDIRECT = "pretty:fuelStops"; 
    
    private static Logger logger = Logger.getLogger(FuelStopsBean.class);
    
    public FuelStopsBean() {
        super();
    }
    public void init(){
        itemLoader = new ItemLoader();
 
        pageData = new PageData();
        pageData.initPage(0);

        dateRange = new DateRange(getLocale(), getDateTimeZone().toTimeZone());
    }
    public List<SelectItem> getDriversSelectItems() {
        if (driversSelectItems == null) {
            driversSelectItems = initSelectItems();
            createDriversSelectItemsForUsersGroup();
            sort(driversSelectItems);
        }
        return driversSelectItems;
    }
    private void createDriversSelectItemsForUsersGroup(){
        for (Driver d : driverDAO.getAllDrivers(this.getUser().getGroupID())) {
            if(d.getDot() != null)
                driversSelectItems.add(new SelectItem(d.getDriverID(), d.getPerson().getFullName()));
        }
    }
    public List<SelectItem> getVehiclesSelectItems() {
        if (vehiclesSelectItems == null) {
            vehiclesSelectItems = initSelectItems();
            createVehiclesSelectItemsForUsersGroup();
            sort(vehiclesSelectItems);
        }
        return vehiclesSelectItems;
    }
    private void createVehiclesSelectItemsForUsersGroup(){
        List<Vehicle> eligibleVehicles = getEligibleVehicles();
        for (Vehicle v : eligibleVehicles) {
            vehiclesSelectItems.add(new SelectItem(v.getVehicleID(), v.getName()));
        }
    }
    private List<SelectItem> initSelectItems(){
        
        final String BLANK_SELECTION = "&#160;";

        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        SelectItem blankItem = new SelectItem("", BLANK_SELECTION);
        blankItem.setEscape(false);
        selectItems.add(blankItem);
        
        return selectItems;
    }

    protected static void sort(List<SelectItem> selectItemList) {
        Collections.sort(selectItemList, new Comparator<SelectItem>() {
            @Override
            public int compare(SelectItem o1, SelectItem o2) {
                return o1.getLabel().toLowerCase().compareTo(
                        o2.getLabel().toLowerCase());
            }
        });
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    public void refresh() {
        
        if (dateRange.getInterval() != null) {
            dropData();
        }
    }
    
    public void dropData(){
        itemLoader.reset();
        item = null;
        allSelected = false;
    }
    public void checkData(){
    	if ((vehicle!= null) && !vehicleNameNow.equalsIgnoreCase(vehicle.getName())){
    		vehicle = null;
    		vehicleID = null;
    		dropData();
    	}
    }
    private boolean validateVehicleName(){
    	if ((vehicle!= null) && !vehicleNameNow.equalsIgnoreCase(vehicle.getName())){
	        for(Vehicle vehicle :getEligibleVehicles()){
	            String name = vehicle.getName();
	            if (name != null && name.toLowerCase().equals(vehicleNameNow.toLowerCase())){
	                return true;
	            }
	        }
	       	return false;
    	}
    	return true;
     }
    // end date range stuff
    
    public String waitForSelects(){
        //action to complete to stop delete popup from displaying until all the
        //select actions are complete
        return null;
    }
    public String unselectAll(){
        if (itemLoader.hasItems())
            for (FuelStopView item : itemLoader.getItems())
                item.setSelected(false);
        return null;
    }
    public String getVehicleName() {
        if (vehicle != null) return vehicle.getName();
        return null;
    }
    public void setVehicleName(String vehicleName) {
    	this.vehicleName = vehicleName;
    }

    public Integer getVehicleID() {
        return vehicleID;
    }
    public String fetchFuelStopsForVehicle(){
    	if (vehicleID != null) {
    		vehicle = vehicleDAO.findByID(vehicleID);
    	}
    	dropData();
    	return null; 
    }
    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }
    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }
    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public HOSDAO getHosDAO() {
        return hosDAO;
    }
    public void setHosDAO(HOSDAO hosDAO) {
        this.hosDAO = hosDAO;
    }

    public List<FuelStopView> getItems() {
        return itemLoader.getItems();
    }
    
    public PageData getPageData() {
        return pageData;
    }

    public void setPageData(PageData pageData) {
        this.pageData = pageData;
    }
    
    public String getCrudMessageKey(){
        defaultToAdd();
        return crudStrategy.getMessageKey();
    }
    public Boolean getAdd(){
        defaultToAdd();
        return crudStrategy.isAdd();
    }
    private void defaultToAdd(){
        if(crudStrategy == null){
            add();
        }
    }
    public boolean isBatchEdit() {
        return false;
    }

    public boolean isAllSelected() {
        if(itemLoader.isEmpty()) return false;
        for (FuelStopView fsv : itemLoader.getItems())
            if(!fsv.isSelected() && fsv.getEditable()) return false;

        return true;
    }

    public void setAllSelected(boolean allSelected) {
        this.allSelected = allSelected;
    }

    public FuelStopView getItem() {
        return item;
    }
    public void setItem(FuelStopView item){
        this.item = item;
    }
    public void setLocateVehicleByTime(LocateVehicleByTime locateVehicleByTime) {
        this.locateVehicleByTime = locateVehicleByTime;
    }    
    public void updateDateAndLocation(ValueChangeEvent event){
        changeJustTheItemLogDate((Date)event.getNewValue());
        locateVehicleByTime();
    }
    public void changeJustTheItemLogDate(Date date){
        DateTime currentDate = new DateTime(item.getLogTime());
        DateTime newDateTime = new DateTime(date).plusSeconds(currentDate.getSecondOfDay());
        item.setLogTime(newDateTime.toDate());
    }
    public String locateVehicleByTime(){
        String location = locateVehicleByTime.getNearestCity(item.getVehicleID(), item.getLogTime());
        item.setLocation(location);
        return null;
    }
    public String add()
    {
        if (!validateVehicleName() || vehicleID == null)
            return VIEW_REDIRECT;
        
        crudStrategy = new CreateStrategy();
        return crudStrategy.init();
    }
    public String deleteSingle()
    {
        hosDAO.deleteByID(item.getHosLogID());
        dropData();

        return VIEW_REDIRECT;
    }
    //Batch delete - standard name to use the confirmDelete.xhtml
    public String delete(){
        for(FuelStopView fsv : getSelectedItems()){
            hosDAO.deleteByID(fsv.getHosLogID());
        }
        dropData();

        return VIEW_REDIRECT;
    }

    public List<FuelStopView> getSelectedItems()
    {
        List<FuelStopView> selectedItems = new ArrayList<FuelStopView>();
        for (FuelStopView t : itemLoader.getItems())
            if (t.isSelected())
                selectedItems.add(t);
        return selectedItems;
    }
    
    public String save()
    {
        return crudStrategy.save();
    }
    
    public String cancel()
    {
       crudStrategy.cancel();
       crudStrategy = null;
       
       return VIEW_REDIRECT;
    }
    
    public void selectAllDependingOnAllSelected()
    {
        for (FuelStopView fsv : itemLoader.getItems())
            fsv.setSelected(allSelected);
    }

    public String edit()
    {
        crudStrategy = new UpdateStrategy();
        return crudStrategy.init();
    }
    public int getPage() {
        return pageData.getCurrentPage();
    }

    public void setPage(int page) {
        pageData.initPage(page, itemLoader.getSize());
    }
    

    private List<Vehicle> getEligibleVehicles(){
        Set<VehicleDOTType> dotTypes = VehicleDOTType.getDOTTypes();

        List<Vehicle> vehicles = vehicleDAO.getVehiclesInGroupHierarchy(this.getUser().getGroupID());
        Map<Integer, VehicleDOTType> vehicleDOTTypes = getVehicleDOTTypes();
        Iterator<Vehicle> it = vehicles.iterator();
        while (it.hasNext()){
            Vehicle vehicle = it.next();
            VehicleDOTType dot = vehicleDOTTypes.get(vehicle.getVehicleID());
            if ((dot == null) || !dotTypes.contains(dot)){
                it.remove();
            }
        }
        return vehicles;
    }
    private Map<Integer, VehicleDOTType> getVehicleDOTTypes(){
    	Map<Integer, VehicleDOTType> vehicleDOTTypes = new HashMap<Integer, VehicleDOTType>();
        List<VehicleSetting> vehicleSettings = configuratorDAO.getVehicleSettingsByGroupIDDeep(this.getUser().getGroupID());
        for(VehicleSetting vs : vehicleSettings){
        	Integer dotVehicleType = NumberUtil.convertString(vs.getBestOption(SettingType.DOT_VEHICLE_TYPE.getSettingID()));
        	vehicleDOTTypes.put(vs.getVehicleID(), VehicleDOTType.getFromSetting(dotVehicleType));
        }
    	return vehicleDOTTypes;
    }
    public List<VehicleName> autocomplete(Object suggest) {
        String pref = (String)suggest;
        ArrayList<VehicleName> result = new ArrayList<VehicleName>();
        for(Vehicle vehicle :getEligibleVehicles()){
            String name =vehicle.getName();
            if (name != null && name.toLowerCase().contains(pref.toLowerCase()) || "".equals(pref))
            {
                result.add(new VehicleName(vehicle.getVehicleID(),vehicle.getName()));
            }
        }
        Collections.sort(result);
        return result;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    public String getVehicleNameNow() {
		return vehicleNameNow;
	}
	public void setVehicleNameNow(String vehicleNameNow) {
		this.vehicleNameNow = vehicleNameNow;
	}

	public void validateDate(FacesContext context, 
            			 UIComponent dateUI,
            			 Object value) {
		String invalidDateMessageKey = "fuelStop_future_date_not_allowed";

		if (dateInFuture((Date) value))	{
		    ((UIInput)dateUI).setValid(false);
            addMessageForField(invalidDateMessageKey,dateUI.getClientId(context), FacesMessage.SEVERITY_ERROR);
		}
	}
    private boolean dateInFuture(Date date){
        return date.after(new Date());
    }
	public void validateFuel(FacesContext context, UIComponent toValidate, Object value) {
		String invalidTruckAndTrailerFuelCombinationMessageKey = "fuelStop_truckAndTrailerFuelInvalid";
		if(trailerSet() && bothTruckAndTrailerGallonsInvalid()){
			((UIInput)truckGallonsUI).setValid(false);
            addMessageForField(invalidTruckAndTrailerFuelCombinationMessageKey,"edit-form:editFuelStop_bothFuelFields", FacesMessage.SEVERITY_ERROR); 
		}
	}
	private boolean trailerSet(){
		return (item.getTrailerID() != null) && !item.getTrailerID().isEmpty();
	}
    private boolean bothTruckAndTrailerGallonsInvalid(){
        return truckGallonsInvalid() && trailerGallonsInvalid();
    }
    private boolean truckGallonsInvalid(){
    	return (getTruckGallonsUI().getLocalValue() == null)||((Float)getTruckGallonsUI().getLocalValue() <= 0.0f);
    }
    private boolean trailerGallonsInvalid(){
    	return (getTrailerGallonsUI().getLocalValue() == null)||((Float)getTrailerGallonsUI().getLocalValue() <= 0.0f);
    }
    private void addMessageForField(String messageKey, String messageField, FacesMessage.Severity severity){
        FacesMessage message = new FacesMessage(severity, MessageUtil.getMessageString(messageKey), null);
        FacesContext.getCurrentInstance().addMessage(messageField, message);
    }
	public UIInput getTruckGallonsUI() {
		return truckGallonsUI;
	}

	public void setTruckGallonsUI(UIInput truckGallonsUI) {
		this.truckGallonsUI = truckGallonsUI;
	}

	public UIInput getTrailerGallonsUI() {
		return trailerGallonsUI;
	}

	public void setTrailerGallonsUI(UIInput trailerGallonsUI) {
		this.trailerGallonsUI = trailerGallonsUI;
	}
		
    public  class FuelStopView extends HOSRecord implements EditItem
    {
        @Column(updateable = false)
        private static final long serialVersionUID = 8372507838051791866L;
        @Column(updateable = false)
        private static final int iftaAggregationDays = 25;
        @Column(updateable = false)
        private boolean selected;
        private Driver driver;
        
        
        public  FuelStopView(HOSRecord fuelStopRecord)
        {
            String[] ignoreProperties = {"timeInSec"};
            BeanUtils.copyProperties(fuelStopRecord, this, ignoreProperties);
            this.setSelected(false);
        }

        public FuelStopView()
        {
            super();
        }

        public Integer getId()
        {
            if (getHosLogID() == null)
                return null;
            
            return getHosLogID().intValue();
        }

        public boolean isSelected()
        {
            return selected;
        }

        public void setSelected(boolean selected)
        {
            this.selected = selected && getEditable();
        }
        
        public String getDriverName() {
            if ((driver == null) && (getDriverID() != null)){
                driver = driverDAO.findByID(getDriverID());
            }
            return driver.getPerson().getFullName();
        }

        @Override
        public String getName() {
            return getDriverName() +", "+getLogTime();
        }
 
        public int getTimeInSec() {
            DateTime dateTime = new DateTime(getLogTime(), DateTimeZone.forID(getTimeZone().getID()));
            return dateTime.getSecondOfDay();
        }

        public void setTimeInSec(int timeInSec) {
            try
            {
                DateTime dateTime = new DateMidnight(getLogTime(), DateTimeZone.forID(getTimeZone().getID())).toDateTime().plusSeconds(timeInSec);
                setLogTime(dateTime.toDate());
            }
            catch (java.lang.NullPointerException e) {
                e.printStackTrace();
            }
            
        }        
        public String getTimezoneName() {
            return FuelStopsBean.this.getTimeZoneDisplayName(getTimeZone());

        }
        public Boolean getEditable(){
            LocalDate localDate = new LocalDate(new DateMidnight(new Date(), DateTimeZone.forID(getTimeZone().getID())));
            Date dateTooManyDaysAgo = localDate.toDateTimeAtStartOfDay(DateTimeZone.forID(getTimeZone().getID())).minusDays(iftaAggregationDays).toDate();
            
            return this.getLogTime().after(dateTooManyDaysAgo);
        }

    }
    
    private abstract class CRUDStrategy {
        
        protected abstract String init();
        protected abstract void databaseAction();
        protected abstract void cancel();
        protected abstract String getMessageKey();
        
        protected Boolean isAdd(){
            return false;
        }

        protected String save() {
            
            try
            {
                setEditingUser();
                RuleSetType dotType = getDotTypeFromDriver(item.getDriverID());
                item.setDriverDotType(dotType);
                
                databaseAction();

                dropData();

                return VIEW_REDIRECT;
            }
            catch (HessianException e)
            {
                addMessageForPage(e.getLocalizedMessage(),FacesMessage.SEVERITY_ERROR);
                logger.debug("Hessian error while saving", e);
                return null;
            }
            
        }

        protected void setEditingUser(){
            Integer editUserID = getUser().getUserID();

            item.setEditUserID(editUserID);
            item.setEdited(true);
        }

        protected void addMessageForPage(String messageKey, FacesMessage.Severity severity){
            final String summary = MessageUtil.formatMessageString(messageKey, item.getId());
            final FacesMessage message = new FacesMessage(severity, summary, null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        protected RuleSetType getDotTypeFromDriver(Integer driverID){
            Driver driver = driverDAO.findByID(driverID);
            if (driver == null) return RuleSetType.NON_DOT;
            return driver.getDot();
        }
    }
    private class CreateStrategy extends CRUDStrategy{
        
        @Override
        protected String init(){
            item = createAddItem();
            return EDIT_REDIRECT;
        }
        private FuelStopView createAddItem() {
            HOSRecord fuelStopRecord = new HOSRecord();
            fuelStopRecord.setTimeZone(TimeZone.getDefault());
            fuelStopRecord.setLogTime(new Date());
            fuelStopRecord.setVehicleID(vehicleID);
            fuelStopRecord.setVehicleName(getVehicleName());
            fuelStopRecord.setStatus(HOSStatus.FUEL_STOP);
            fuelStopRecord.setDriverID(getVehicle()==null?null:getVehicle().getDriverID());
            fuelStopRecord.setLocation(locateVehicleByTime.getNearestCity(vehicleID, fuelStopRecord.getLogTime()));
            
            return  new FuelStopView(fuelStopRecord);
        }
        @Override
        protected void databaseAction(){
            hosDAO.create(0l, item);
            addMessageForPage("fuelStop_added", FacesMessage.SEVERITY_INFO);
        }
        @Override
        protected void cancel() {
           item = null; 
        }
        @Override
        protected String getMessageKey() {
            return "createFuelStop";
        }
        @Override
        protected Boolean isAdd(){
            return true;
        }

    }
    public class UpdateStrategy extends CRUDStrategy{
        
        @Override
        protected String init() {
            return EDIT_REDIRECT;
        }
        protected void databaseAction(){
            hosDAO.update(item);
            addMessageForPage("fuelStop_updated", FacesMessage.SEVERITY_INFO);
        }

        @Override
        protected void cancel() {
        	dropData();
            item = null;
        }
        @Override
        protected String getMessageKey() {
            return "updateFuelStop";
        }
    }
    public class ItemLoader {
        
        private List<FuelStopView> items;
    
        public List<FuelStopView> getItems() {
            if(dataNeedsLoading()){
                loadItems();
            }
            return items;
        }
        private boolean dataNeedsLoading(){
            return items==null;
        }
        private void loadItems() {
            
            items = new ArrayList<FuelStopView>();
  
            if (getVehicleID() == null) return;
            
            List<HOSRecord> plainRecords = hosDAO.getFuelStopRecordsForVehicle(getVehicleID(), dateRange.getInterval());
            if (plainRecords == null) return;
  
            for (final HOSRecord rec : plainRecords) {
                items.add(new FuelStopView(rec));
            }
            pageData.updatePage(items.size());
        }
        public void reset() {
            items = null;
            pageData = new PageData();
            pageData.initPage(0);

        }
        public boolean isEmpty() {
            return getItems().isEmpty();
        }
        public int getSize() {
            return getItems().size();
        }
        public boolean hasItems(){
            return items != null;
        }
    }
}
