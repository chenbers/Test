package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.BeanUtils;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.backing.model.ItemsGetter;
import com.inthinc.pro.backing.ui.DateRange;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.hessian.exceptions.HessianException;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleName;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.table.PageData;
import com.inthinc.pro.util.MessageUtil;

public class FuelStopsBean extends BaseBean {

    private static final long serialVersionUID = 1L;
    
    private Integer vehicleID;
    private Vehicle vehicle;
    
    protected FuelStopView item;
    protected List<FuelStopView> items;
    protected ItemsGetter<FuelStopView> itemsGetter;
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;
    private HOSDAO hosDAO;
    
    private PageData pageData;
    
    private List<SelectItem> driversSelectItems;
    private List<SelectItem> vehiclesSelectItems;
    
    private DateRange dateRange;
    
    private boolean               allSelected;
    private List<VehicleName> vehicleNameList;
    
    private CRUDStrategy crudStrategy;
    
    private static final String EDIT_REDIRECT = "pretty:fuelStopEdit";    
    private static final String VIEW_REDIRECT = "pretty:fuelStops"; 
    
    private static Logger logger = Logger.getLogger(FuelStopsBean.class);
    
    public FuelStopsBean() {
        super();
    }
    public void init(){
        itemsGetter = new NullFuelStopGetter();
        initPageData(0);
        initDateRange();
    }
    private void initPageData(int dataSize){
        pageData = new PageData();
        pageData.initPage(dataSize);
    }
    private void initDateRange(){
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
        for (Vehicle v : vehicleDAO.getVehiclesInGroupHierarchy(this.getUser().getGroupID())) {
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
            itemsGetter.reset();
        }
    }
    // end date range stuff

    public String unselectAll(){
        if (items != null)
            for (FuelStopView item : itemsGetter.getItems())
                item.setSelected(false);
        return null;
    }
    public String getVehicleName() {
        if (vehicle != null) return vehicle.getName();
        return null;
    }
    public void setVehicleName(String vehicleName) {
    }

    public Integer getVehicleID() {
        return vehicleID;
    }
    public String fetchFuelStopsForVehicle(){
       vehicle = vehicleDAO.findByID(vehicleID);
       itemsGetter.reset();
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
        return itemsGetter.getItems();
    }
    
    public PageData getPageData() {
        return pageData;
    }

    public void setPageData(PageData pageData) {
        this.pageData = pageData;
    }
    
    public String getCrudMessageKey(){
        if(crudStrategy == null){
            add();
        }
        return crudStrategy.getMessageKey();
    }
    public Boolean getAdd(){
        if(crudStrategy == null){
            add();
        }
        return crudStrategy.isAdd();
        
    }

    public boolean isBatchEdit() {
        return false;
    }

    public boolean isAllSelected() {
        if(itemsGetter.isEmpty()) return false;
        for (FuelStopView fsv : itemsGetter.getItems())
            if(!fsv.isSelected()) return false;

        return true;
    }

    public void setAllSelected(boolean allSelected) {
        this.allSelected = allSelected;
    }
    

    private FuelStopView createFuelStopView(HOSRecord fuelStopRecord)
    {
        final FuelStopView fuelStopView = new FuelStopView();
        String[] ignoreProperties = {"timeInSec"};
        BeanUtils.copyProperties(fuelStopRecord, fuelStopView, ignoreProperties);
        fuelStopView.setSelected(false);
        
        return fuelStopView;
    }

    public FuelStopView getItem() {
        return item;
    }
    public void setItem(FuelStopView item){
        this.item = item;
    }
    
    
    public  class FuelStopView extends HOSRecord implements EditItem
    {
        @Column(updateable = false)
        private static final long serialVersionUID = 8372507838051791866L;
        @Column(updateable = false)
        private boolean selected;

        private Driver driver;
        
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
            this.selected = selected;
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
            return getTimeZone().getID();
        }
    }
    
    public String add()
    {
        if (vehicleID == null)
            return "";
        
        crudStrategy = new CreateStrategy();
        return crudStrategy.init();
    }
    public String deleteSingle()
    {
        hosDAO.deleteByID(item.getHosLogID());
        itemsGetter.reset();
        return VIEW_REDIRECT;
    }
    //Batch delete - standard name to use the confirmDelete.xhtml
    public String delete(){
        for(FuelStopView fsv : getSelectedItems()){
            hosDAO.deleteByID(fsv.getHosLogID());
        }
        itemsGetter.reset();
        return VIEW_REDIRECT;
    }
    private FuelStopView createAddItem() {
        HOSRecord fuelStopRecord = new HOSRecord();
        fuelStopRecord.setTimeZone(TimeZone.getDefault());
        fuelStopRecord.setLogTime(new Date());
        fuelStopRecord.setVehicleID(vehicleID);
        fuelStopRecord.setVehicleName(getVehicleName());
        fuelStopRecord.setStatus(HOSStatus.FUEL_STOP);
        fuelStopRecord.setDriverID(getVehicle().getDriverID());
        
        return createFuelStopView(fuelStopRecord);
    }

    public List<FuelStopView> getSelectedItems()
    {
        List<FuelStopView> selectedItems = new ArrayList<FuelStopView>();
        for (FuelStopView t : itemsGetter.getItems())
            if (t.isSelected())
                selectedItems.add(t);
        return selectedItems;
    }
    
    public String save()
    {
        crudStrategy.save();
        return VIEW_REDIRECT;
    }
    protected boolean validate(List<FuelStopView> saveItems)
    {
        boolean valid = true;
        for (final FuelStopView saveItem : saveItems)
        {
            valid = crudStrategy.validate(saveItem);
            if(!valid)
                break;
        }
        return valid;
    }

    
    public String cancel()
    {
       crudStrategy.cancel();
       crudStrategy = null;
       
       return VIEW_REDIRECT;
    }
    
    public void selectAllDependingOnAllSelected()
    {
        for (FuelStopView fsv : itemsGetter.getItems())
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
        pageData.initPage(page, itemsGetter.getSize());
    }
    

    public void getVehiclesForSuggestionBox(){
        vehicleNameList = vehicleDAO.getVehicleNames(this.getUser().getGroupID());
    }

    public List<VehicleName> autocomplete(Object suggest) {
        String pref = (String)suggest;
        ArrayList<VehicleName> result = new ArrayList<VehicleName>();
        getVehiclesForSuggestionBox();
        for(VehicleName vehicle :vehicleNameList){
            String name =vehicle.getVehicleName();
            if (name != null && name.toLowerCase().contains(pref.toLowerCase()) || "".equals(pref))
            {
                result.add(vehicle);
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
    
    private abstract class CRUDStrategy {
        
        protected abstract String init();
        protected abstract void dao();
        protected abstract void cancel();
        protected abstract String getMessageKey();
        protected abstract Boolean isAdd();

        protected String save() {
            
            if (!validate(item)) {
                return null;
            }

            try
            {
                setEditingUser();
                RuleSetType dotType = getDotTypeFromDriver(item.getDriverID());
                item.setDriverDotType(dotType);
                dao();

                itemsGetter.reset();

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
        protected Boolean validate(FuelStopView fuelStopView) {
            boolean valid = true;
            if (!validateLocation(fuelStopView)) valid = false;
            if (!validateDateTime(fuelStopView)) valid = false;
            
            return valid;
        }
        private Boolean validateLocation(FuelStopView fuelStopView){
            if (locationIsInvalid(fuelStopView.getLocation())) {
                
                addMessageForField("required","edit-form:editFuelStop_dateTime", FacesMessage.SEVERITY_ERROR);
                return false;
            }
            return true;
        }
        private Boolean locationIsInvalid(String location){
            return location == null || location.isEmpty();
        }
        private Boolean validateDateTime(FuelStopView fuelStopView){
            
            if (dateInFuture(fuelStopView.getLogTime())) {
                addMessageForField(" editFuelStop_future_date_not_allowed","edit-form:editFuelStop_dateTime", FacesMessage.SEVERITY_ERROR);
                return false;
            }
            return true;
        }
        private Boolean dateInFuture(Date date){
            return date.after(new Date());
        }
        private void addMessageForField(String messageKey, String messageField, FacesMessage.Severity severity){
            FacesMessage message = new FacesMessage(severity, MessageUtil.getMessageString(messageKey), null);
            FacesContext.getCurrentInstance().addMessage(messageField, message);
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
        protected void dao(){
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
        protected Boolean isAdd(){
            return true;
        }

    }
    public class UpdateStrategy extends CRUDStrategy{
        
        @Override
        protected String init() {
            return EDIT_REDIRECT;
        }
        protected void dao(){
            hosDAO.update(item);
            addMessageForPage("fuelStop_updated", FacesMessage.SEVERITY_INFO);
        }

        @Override
        protected void cancel() {
            item = null;
        }
        @Override
        protected String getMessageKey() {
            return "updateFuelStop";
        }
        protected Boolean isAdd(){
            return false;
        }

    }
    public class FuelStopGetter implements ItemsGetter<FuelStopView>{

        @Override
        public List<FuelStopView> getItems() {
            return items;
        }

        @Override
        public void reset() {
            items = null;
            item = null;
            itemsGetter = new NullFuelStopGetter();
        }

        @Override
        public boolean isEmpty() {
            return items.isEmpty();
        }

        @Override
        public int getSize() {
            return items.size();
        }
    }
    public class NullFuelStopGetter implements ItemsGetter<FuelStopView>{
      @Override
      public List<FuelStopView> getItems() {
          loadItems();
          initPageData(items.size());
          itemsGetter = new FuelStopGetter();
          return items;
      }
      protected void loadItems() {

          items = new ArrayList<FuelStopView>();

          if (getVehicleID() == null) return;
          
          List<HOSRecord> plainRecords = hosDAO.getFuelStopRecordsForVehicle(getVehicleID(), dateRange.getInterval());
          if (plainRecords == null) return;

          for (final HOSRecord rec : plainRecords) {
              items.add(createFuelStopView(rec));
          }
      }
    @Override
    public void reset() {
        item = null;
        items = null;
    }
    @Override
    public boolean isEmpty() {
        getItems();
        return items.isEmpty();
    }
    @Override
    public int getSize() {
        getItems();
        return items.size();
    }
  }

}
