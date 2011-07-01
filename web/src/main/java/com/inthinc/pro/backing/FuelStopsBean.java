package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    private String vehicleName;
    private Vehicle vehicle;
    
    protected FuelStopView item;
    protected LinkedHashMap<Long,FuelStopView> items;

    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;
    private HOSDAO hosDAO;
    
    private PageData pageData;
    private int page;
    
    private List<SelectItem> driversSelectItems;
    private List<SelectItem> vehiclesSelectItems;
    
    private DateRange dateRange;
    
    private boolean               selectAll;
    private List<VehicleName> vehicleNameList;
    
    private CRUDStrategy crudStrategy;
    
    private static final String EDIT_REDIRECT = "pretty:fuelStopEdit";    
    private static final String VIEW_REDIRECT = "pretty:fuelStops"; 
    
    private static Logger logger = Logger.getLogger(FuelStopsBean.class);
    
    public FuelStopsBean() {
        super();
    }
    public void init(){
        pageData = new PageData();
        page = 0;
        pageData.initPage(page);
        // use the timezone of the logged on user for date range
        dateRange = new DateRange(getLocale(), getDateTimeZone().toTimeZone());
    }
    
    public List<SelectItem> getDriversSelectItems() {
        if (driversSelectItems == null) {
            driversSelectItems = initSelectItems();
            for (Driver d : driverDAO.getAllDrivers(this.getUser().getGroupID())) {
                if(d.getDot() != null)
                    driversSelectItems.add(new SelectItem(d.getDriverID(), d.getPerson().getFullName()));
            }
            sort(driversSelectItems);
        }
        return driversSelectItems;
    }
    public List<SelectItem> getVehiclesSelectItems() {
        if (vehiclesSelectItems == null) {
            vehiclesSelectItems = initSelectItems();
            for (Vehicle v : vehicleDAO.getVehiclesInGroupHierarchy(this.getUser().getGroupID())) {
                vehiclesSelectItems.add(new SelectItem(v.getVehicleID(), v.getName()));
            }
            sort(vehiclesSelectItems);
        }
        return vehiclesSelectItems;
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
            items = null;
        }
    }
    // end date range stuff
    
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
    public void setVehicleID(Integer vehicleID) {
        if(vehicleIDChanged(vehicleID)){
            
            this.vehicleID = vehicleID;
            vehicle = vehicleDAO.findByID(vehicleID);
            items = null;
        }
    }
    private boolean vehicleIDChanged(Integer newVehicleID){
        return (this.vehicleID == null && newVehicleID != null) || (this.vehicleID != newVehicleID);
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

//    @Override
    public List<FuelStopView> getItems() {
        if (items == null) {
            loadItems();
            initPageData();
        }
        
        return new ArrayList<FuelStopView>(items.values());
    }
    protected void loadItems() {

        items = new LinkedHashMap<Long,FuelStopView>();

        if (getVehicleID() == null) return;
        
        List<HOSRecord> plainRecords = hosDAO.getFuelStopRecordsForVehicle(getVehicleID(), dateRange.getInterval());
        if (plainRecords == null) return;

        for (final HOSRecord rec : plainRecords) {
            items.put(rec.getHosLogID(),createFuelStopView(rec));
        }
    }
    
    private void initPageData() {
        pageData = new PageData();
        pageData.initPage(items.size());
        page = pageData.getCurrentPage();
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

    public boolean isSelectAll() {
        return selectAll;
    }

    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
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
            // TODO Auto-generated method stub
            return null;
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
        
        unselectAllItems();
        
        crudStrategy = new CreateStrategy();
        return crudStrategy.init();
    }
    private void unselectAllItems(){
        if (items != null)
            for (FuelStopView item : getSelectedItems())
                item.setSelected(false);
    }
    public String delete()
    {
//        List<FuelStopView> selected = getSelectedItems();
//        if (selected.size() != 1) 
//            return VIEW_REDIRECT;   //shouldn't happen
//        
//        FuelStopView delItem = selected.get(0);
        hosDAO.deleteByID(item.getHosLogID());
//        items.remove(item.getHosLogID());
        
        return VIEW_REDIRECT;
    }
    
    private FuelStopView createAddItem() {
        HOSRecord fuelStopRecord = new HOSRecord();
        fuelStopRecord.setTimeZone(TimeZone.getDefault());
        fuelStopRecord.setLogTime(new Date());
        fuelStopRecord.setVehicleID(vehicleID);
        fuelStopRecord.setVehicleName(vehicleName);
        fuelStopRecord.setStatus(HOSStatus.FUEL_STOP);
        fuelStopRecord.setDriverID(getVehicle().getDriverID());
        
        return createFuelStopView(fuelStopRecord);
    }

    public List<FuelStopView> getSelectedItems()
    {
        List<FuelStopView> selectedItems = new ArrayList<FuelStopView>();
        for (FuelStopView t : getItems())
            if (t.isSelected())
                selectedItems.add(t);

//        if (selectedItems.size() == 0 && !isAdd())
//            selectedItems.add(item);

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

    
    public String cancelEdit()
    {
       crudStrategy.cancel();
       crudStrategy = null;
       
       return VIEW_REDIRECT;
    }
    
    protected FuelStopView revertItem(FuelStopView editItem)
    {
        return createFuelStopView(hosDAO.findByID(editItem.getHosLogID()));
    }

    public void doSelectAll()
    {
        for (FuelStopView log : getItems())
            log.setSelected(selectAll);
    }

    public String edit()
    {
        crudStrategy = new UpdateStrategy();
        return crudStrategy.init();
    }
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
        pageData.initPage(page, getItems().size());
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
        
//        protected FuelStopView item;
        protected abstract String init();
        protected abstract String save();
        protected abstract void cancel();
        protected abstract String getMessageKey();
        protected abstract Boolean isAdd();

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
            if (fuelStopView.getLocation() == null || fuelStopView.getLocation().isEmpty()) {
                
                addMessageForField("required","edit-form:editFuelStop_dateTime", FacesMessage.SEVERITY_ERROR);
                return false;
            }
            return true;
        }
        private Boolean validateDateTime(FuelStopView fuelStopView){
            
            if (fuelStopView.getLogTime().after(new Date())) {
                addMessageForField(" editFuelStop_future_date_not_allowed","edit-form:editFuelStop_dateTime", FacesMessage.SEVERITY_ERROR);
                return false;
            }
            return true;
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
        @Override
        protected String save() {
            
            // validate
            if (!validate(item)) {
                return null;
            }

            try
            {
                setEditingUser();
                RuleSetType dotType = getDotTypeFromDriver(item.getDriverID());
                item.setDriverDotType(dotType);
                item.setHosLogID(hosDAO.create(0l, item));

                getItems();
                items.put(item.getHosLogID(),item);
                
                addMessageForPage("fuelStop_added", FacesMessage.SEVERITY_INFO);
                    // add a message
                
                return VIEW_REDIRECT;
            }
            catch (HessianException e)
            {
                addMessageForPage(e.getLocalizedMessage(),FacesMessage.SEVERITY_ERROR);
                logger.debug("Hessian error while saving", e);
                return null;
            }
            
        }
        @Override
        protected void cancel() {
            
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
//            selectItem("editID");
            return EDIT_REDIRECT;
        }
//        private void selectItem(String paramName)
//        {
//            final Map<String, String> parameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//            if (parameterMap.get(paramName) != null)
//            {
//                final long editID = Long.parseLong(parameterMap.get(paramName));
//                selectItem(editID);
//            }
//        }
//        
//        private void selectItem(Long id)
//        {
//                for (FuelStopView fuelStop : getItems())
//                    fuelStop.setSelected(fuelStop.getId().equals(id));
//
////                item = items.get(id);
////                item.setSelected(false);
//        }

        @Override
        protected String save() {
//            updateVehicleName();

            // validate
            if (!validate(item)) {
                return null;
            }
            try
            {
                  setEditingUser();
                  hosDAO.update(item);
                  
                  addMessageForPage("fuelStop_updated",FacesMessage.SEVERITY_INFO);
                  // add a message
                  return VIEW_REDIRECT;
            }
            catch (HessianException e)
            {
                addMessageForPage(e.getLocalizedMessage(),FacesMessage.SEVERITY_ERROR);
                logger.debug("Hessian error while saving", e);
                return null;
            }
        }
        @Override
        protected void cancel() {
            final int index = getItems().indexOf(item);
            if (index != -1)
                items.put(item.getHosLogID(), revertItem(item));
            // deselect all edit items
            unselectAllItems();
            
        }
        @Override
        protected String getMessageKey() {
            return "updateFuelStop";
        }
        protected Boolean isAdd(){
            return false;
        }

    }

}
