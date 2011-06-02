package com.inthinc.pro.backing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import org.springframework.beans.BeanUtils;

import com.inthinc.pro.backing.filtering.ColumnFiltering;
import com.inthinc.pro.backing.model.VehicleSettingManager;
import com.inthinc.pro.backing.model.VehicleSettingsFactory;
import com.inthinc.pro.backing.model.supportData.AdminCacheBean;
import com.inthinc.pro.backing.model.supportData.CacheItemMap;
import com.inthinc.pro.backing.model.supportData.DeviceMap;
import com.inthinc.pro.backing.model.supportData.DriverNameMap;
import com.inthinc.pro.backing.model.supportData.GroupMap;
import com.inthinc.pro.backing.model.supportData.VehicleMap;
import com.inthinc.pro.backing.ui.DeviceStatusSelectItems;
import com.inthinc.pro.backing.ui.ProductTypeSelectItems;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverName;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.VehicleSetting;
import com.inthinc.pro.table.columns.VehicleChoiceTableColumns;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.MiscUtil;
import com.inthinc.pro.util.SelectItemUtil;
/**
 * @author David Gileadi
 */
@SuppressWarnings("serial")
public class DevicesBean extends BaseAdminBean<DevicesBean.DeviceView>
{
    private static final String imeiRegex = "[0-9]{15}";
    private static final List<String> AVAILABLE_COLUMNS;
    private static final int[] DEFAULT_COLUMN_INDICES = new int[] { 0, 1, 2, 5, 6};
    private static final List<String> CHOOSE_VEHICLE_COLUMNS;
    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("name");
        AVAILABLE_COLUMNS.add("vehicleID");
        AVAILABLE_COLUMNS.add("imei");
        AVAILABLE_COLUMNS.add("sim");
        AVAILABLE_COLUMNS.add("serialNum");
        AVAILABLE_COLUMNS.add("phone");
        AVAILABLE_COLUMNS.add("status");
        AVAILABLE_COLUMNS.add("mcmid");
        AVAILABLE_COLUMNS.add("altimei");
        AVAILABLE_COLUMNS.add("productVer");
        
        CHOOSE_VEHICLE_COLUMNS = new ArrayList<String>();
        CHOOSE_VEHICLE_COLUMNS.add("name");
        CHOOSE_VEHICLE_COLUMNS.add("driverID");
        CHOOSE_VEHICLE_COLUMNS.add("groupID");
        CHOOSE_VEHICLE_COLUMNS.add("year");
        CHOOSE_VEHICLE_COLUMNS.add("make");
        CHOOSE_VEHICLE_COLUMNS.add("model");
        CHOOSE_VEHICLE_COLUMNS.add("VIN");
        CHOOSE_VEHICLE_COLUMNS.add("status");
        CHOOSE_VEHICLE_COLUMNS.add("deviceID");
        CHOOSE_VEHICLE_COLUMNS.add("productVer");

        
    }
    private DeviceDAO   deviceDAO;
    private VehicleDAO  vehicleDAO;
    private DriverDAO   driverDAO;

    private ColumnFiltering<Vehicle> chooseVehicleFiltering;
    private VehicleChoiceTableColumns vehicleChoiceTableColumns;
    
    private AdminCacheBean adminCacheBean;
    
    private List<Vehicle> chooseVehicleItems;
    private String chooseVehicleSearchKeyword;
    private String chosenVehicleID;
    
    // Stuff to do with vehicleSettings for the device
    private VehicleSettingsFactory vehicleSettingsFactory;
    private VehicleSettingManager vehicleSettingManager;

    
    public void setDeviceDAO(DeviceDAO deviceDAO)
    {
        this.deviceDAO = deviceDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    @Override
    public void initFilterValues(){
        super.initFilterValues();
        for(String column:AVAILABLE_COLUMNS){
            getFilterValues().put(column, null);
        }
        chooseVehicleFiltering = new ColumnFiltering<Vehicle>();
        for(String column:CHOOSE_VEHICLE_COLUMNS){
            chooseVehicleFiltering.put(column, null);
        }
    }
    
    public List<SelectItem> getProductTypesSelectItems(){
        
        return ProductTypeSelectItems.INSTANCE.getSelectItems();
    }
    public String getViewPath() {
        if(isAdd()){
             
            return getFinishedRedirect();
        }
        else {
            
            return getEditRedirect();
        }
    }
    /**
     * Creates a VehicleView object from the given Vehicle object.
     * 
     * @param vehicle
     *            The vehicle.
     * @return The new VehicleView object.
     */
    private void loadGroups()
    {
        CacheItemMap<Group,Group> groupMap = new GroupMap(getUser().getPerson().getAcctID(),getUser().getGroupID());
        groupMap.setDAO(groupDAO);
        groupMap.buildMap();
        adminCacheBean.addAssetMap("groups",groupMap);
   }
    private void loadDrivers()
    {
        CacheItemMap<Driver,DriverName> driverMap = new DriverNameMap(getUser().getGroupID());
        driverMap.setDAO(driverDAO);
        driverMap.buildMap();
        adminCacheBean.addAssetMap("drivers",driverMap);
   }
    private void loadDevices()
    {
        
        CacheItemMap<Device,Device>supportDeviceMap = new DeviceMap(getUser().getPerson().getAcctID());
        supportDeviceMap.setDAO(deviceDAO);
        supportDeviceMap.buildMap();
        adminCacheBean.addAssetMap("devices",supportDeviceMap);
    }

    private void loadVehicles(){
        CacheItemMap<Vehicle,Vehicle> vehicleMap = new VehicleMap(getUser().getGroupID());
        vehicleMap.setDAO(vehicleDAO);
        vehicleMap.buildMap();
        adminCacheBean.addAssetMap("vehicles",vehicleMap);
    }
    public List<Vehicle> getVehicles(){
        //filter on the filter item
        return vehicleChoiceTableColumns.getFilteredItems(adminCacheBean,chooseVehicleSearchKeyword,true);
    }
    
    @Override
    protected List<DeviceView> loadItems()
    {
        // get the devices
        final List<Device> plainDevices = deviceDAO.getDevicesByAcctID(getAccountID());
        // convert the Devices to DeviceViews
        final LinkedList<DeviceView> items = new LinkedList<DeviceView>();
        for (final Device device : plainDevices){
            items.add(createDeviceView(device));
        }
        loadSupportData();
        vehicleChoiceTableColumns = new VehicleChoiceTableColumns();
       
        return items;
    }
    
    @SuppressWarnings("unchecked")
    private void loadSupportData(){
        
        adminCacheBean = new AdminCacheBean();
        loadDevices();
        loadGroups();
        loadDrivers();
        loadVehicles();
        chooseVehicleItems = (List<Vehicle>) adminCacheBean.getAssets("vehicles");
        
    }
    /**
     * Creates a DeviceView object from the given Device object.
     * 
     * @param device
     *            The device.
     * @return The new DeviceView object.
     */
    private DeviceView createDeviceView(Device device)
    {
        final DeviceView deviceView = new DeviceView();
        deviceView.bean = this;
        BeanUtils.copyProperties(device, deviceView);
        deviceView.setOldVehicleID(device.getVehicleID());
        deviceView.setSelected(false);
        deviceView.setFirmwareVersionDate();
        if (device.getPhone() != null)
            deviceView.setPhone(MiscUtil.formatPhone(device.getPhone()));
        
        return deviceView;
    }

    @Override
    public String fieldValue(DeviceView device, String columnName)
    {
        if (columnName.equals("vehicleID"))
        {
            if (device.getVehicle() != null)
                return device.getVehicle().getName();
            return null;
        }
        else if (columnName.equals("status"))
        {
            if (device.getStatus() != null)
                return MessageUtil.getMessageString(device.getStatus().getDescription().toLowerCase());
            return null;
        }
        return super.fieldValue(device, columnName);
    }

    @Override
    public List<String> getAvailableColumns()
    {
        return AVAILABLE_COLUMNS;
    }

    @Override
    public Map<String, Boolean> getDefaultColumns()
    {
        final HashMap<String, Boolean> columns = new HashMap<String, Boolean>();
        final List<String> availableColumns = getAvailableColumns();
        for (int i : DEFAULT_COLUMN_INDICES)
            columns.put(availableColumns.get(i), true);
        return columns;
    }

    @Override
    public String getColumnLabelPrefix()
    {
        return "devicesHeader_";
    }

    @Override
    public TableType getTableType()
    {
        return TableType.ADMIN_DEVICES;
    }

    @Override
    public String add() {
         return getFinishedRedirect();
    }
    @Override
    protected DeviceView createAddItem()
    {
        final Device device = new Device();
        device.setStatus(DeviceStatus.ACTIVE);
        device.setAccountID(getAccountID());
        return createDeviceView(device);
    }

    @Override
    public DeviceView getItem()
    {
        final DeviceView item = super.getItem();
        return item;
    }

    @Override
    public String cancelEdit()
    {
        getItem().setVehicleID(getItem().getOldVehicleID());
        return super.cancelEdit();
    }

    @Override
    protected void doDelete(List<DeviceView> deleteItems)
    {
        final FacesContext context = FacesContext.getCurrentInstance();
        for (final DeviceView device : deleteItems)
        {
            if (device.getVehicleID() != null)
                setVehicleDevice(device.getVehicleID(), null);
            deviceDAO.deleteByID(device.getDeviceID());
            // add a message
            final String summary = MessageUtil.formatMessageString("device_deleted", device.getName());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }
    }

    public void assignVehicle(){
        getItem().setVehicleID(Integer.parseInt(chosenVehicleID));
        
    }
    public void chooseVehicle()
    {
        final Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        final String vehicleID = params.get("vehicleID");
        if (vehicleID != null)
            getItem().setVehicleID(Integer.parseInt(vehicleID));
        if (Boolean.parseBoolean(params.get("immediate")) && !isAdd() && !isBatchEdit())
            assignVehicle(getItem());
    }
    
    @Override
    protected Boolean authorizeAccess(DeviceView item)
    {
        Integer acctID = item.getAccountID();

        if (getGroupHierarchy().getTopGroup().getAccountID().equals(acctID))
        {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    @Override
    protected boolean validateSaveItem(DeviceView deviceView)
    {
        boolean valid = true;
        final String required = "required";
        // Name.  
        if ((deviceView.getName() == null || deviceView.getName().equals("")) && !isBatchEdit() || (isBatchEdit() && getUpdateField().get("name")))
        {
            valid = false;
            final String summary = MessageUtil.getMessageString(required);
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
            getFacesContext().addMessage("edit-form:editDevice-name", message);
        }
        
        // If a waysmart, the only attribute that can be changed is name, currently, so, 
        //  return the results of that check.
        if ( deviceView.isWaySmart() ) {
            return valid;
        }
        
        Device byImei = null;
        if (deviceView.getImei() != null)
            byImei = deviceDAO.findByIMEI(deviceView.getImei());
        // IMEI
        if (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("imei")))
        {
            if ((deviceView.getImei() == null || deviceView.getImei().equals("")))
            {
                valid = false;
                final String summary = MessageUtil.getMessageString(required);
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                getFacesContext().addMessage("edit-form:editDevice-imei", message);
            }
            else if (!deviceView.getImei().matches(imeiRegex)) // format
            {
                valid = false;
                final String summary = MessageUtil.getMessageString("editDevice_imeiFormat");
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                getFacesContext().addMessage("edit-form:editDevice-imei", message);
            }
            else if ((byImei != null) && !byImei.getDeviceID().equals(getItem().getDeviceID())) // unique
            {
                valid = false;
                final String summary = MessageUtil.getMessageString("editDevice_uniqueImei");
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                getFacesContext().addMessage("edit-form:editDevice-imei", message);
            }
        }
        // SIM
        if ((deviceView.getSim() == null || deviceView.getSim().equals("")) && !isBatchEdit() || (isBatchEdit() && getUpdateField().get("sim")))
        {
            valid = false;
            final String summary = MessageUtil.getMessageString(required);
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
            getFacesContext().addMessage("edit-form:editDevice-sim", message);
        }
        // PHONE        
        if (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("phone")))
        {
            if (deviceView.getPhone() == null || deviceView.getPhone().equals(""))
            {
                valid = false;
                final String summary = MessageUtil.getMessageString(required);
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                getFacesContext().addMessage("edit-form:editDevice-phone", message);
            }
            else if(deviceView.getPhone() != null &&             		
                    ((deviceView.getPhone().length() > 22) || (MiscUtil.unformatPhone(deviceView.getPhone()).length() > 15)) )                
            {
                valid = false;
                final String summary = MessageUtil.getMessageString("editDevice_phoneFormat");
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                getFacesContext().addMessage("edit-form:editDevice-phone", message);
            }
        }
        return valid;
    }

    @Deprecated
    public void validateImei(FacesContext context, UIComponent component, Object value)
    {
        final String imei = (String) value;
        if (!imei.matches(imeiRegex))
        {
            final String summary = MessageUtil.getMessageString("editDevice_imeiFormat");
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
            throw new ValidatorException(message);
        }
        // unique
        final Device byImei = deviceDAO.findByIMEI(imei);
        if ((byImei != null) && !byImei.getDeviceID().equals(getItem().getDeviceID()))
        {
            final String summary = MessageUtil.getMessageString("editDevice_uniqueImei");
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
            throw new ValidatorException(message);
        }
    }

    @Override
    protected DeviceView revertItem(DeviceView editItem)
    {
        return createDeviceView(deviceDAO.findByID(editItem.getDeviceID()));
    }

    @Override
    protected void doSave(List<DeviceView> saveItems, boolean create)
    {
        final FacesContext context = FacesContext.getCurrentInstance();
        for (final DeviceView device : saveItems)
        {
            if (create)
                device.setDeviceID(deviceDAO.create(getAccountID(), device));
            else
                deviceDAO.update(device);
            if (device.isVehicleChanged())
                assignVehicle(device);
            // add a message
            final String summary = MessageUtil.formatMessageString(create ? "device_added" : "device_updated", device.getName());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }
    }

    private void assignVehicle(final DeviceView device)
    {
        if (device.getOldVehicleID() != null)
            setVehicleDevice(device.getOldVehicleID(), null);
        if (device.getVehicleID() != null)
            for (final DeviceView otherDevice : getItems())
                if (device.getVehicleID().equals(otherDevice.getVehicleID()) && !otherDevice.getDeviceID().equals(device.getDeviceID()))
                {
                    vehicleDAO.clearVehicleDevice(device.getVehicleID(), otherDevice.getDeviceID());
                    otherDevice.setVehicleID(null);
                    otherDevice.setOldVehicleID(null);
                }
        vehicleDAO.setVehicleDevice(device.getVehicleID(), device.getDeviceID());
        setVehicleDevice(device.getVehicleID(), device.getDeviceID());
        device.setOldVehicleID(device.getVehicleID());
        //Create configurator settings record if it doesn't exist
        updateSettingsRecord(device);
    }

    private void setVehicleDevice(Integer vehicleID, Integer deviceID)
    {
        Vehicle vehicle = (Vehicle)adminCacheBean.getAsset("vehicles", vehicleID);
        vehicle.setDeviceID(deviceID);
    }
    private void updateSettingsRecord(DeviceView device){
        //Only want to do this if they aren't set to something else already
        //But can't tell if it's a new record here so not sure what to do
        //It somehow magically comes into existence
        if(!vehicleSettingsFactory.settingsRecordExists(device.getVehicleID())){
            vehicleSettingManager = vehicleSettingsFactory.getSettingManager(device.getProductVersion(), device.getVehicleID(), device.getDeviceID());
            EditableVehicleSettings editableVehicleSettings = vehicleSettingManager.createDefaultValues(device.getVehicleID());
            vehicleSettingManager.setVehicleSettings(device.getVehicleID(), editableVehicleSettings, this.getUserID(), "Assigning new vehicle");
        }
     }
    @Override
    protected String getDisplayRedirect()
    {
        return "pretty:adminDevice";
    }

    @Override
    protected String getEditRedirect()
    {
        return "pretty:adminEditDeviceAccess";
    }

    @Override
    protected String getFinishedRedirect()
    {
        return "pretty:adminDevices";
    }

    public List<SelectItem> getStatuses()
    {
        return SelectItemUtil.toList(DeviceStatus.class, false, DeviceStatus.DELETED);
    }
    public List<SelectItem> getStatusSelectItems() {
        return DeviceStatusSelectItems.INSTANCE.getSelectItems();
    }

    @Override
    public void resetList()
    {
        super.resetList();
//        vehiclesBean.resetList();
    }
    public static class DeviceView extends Device implements EditItem
    {
        @Column(updateable = false)
        private static final long serialVersionUID = 8372507838051791866L;
        @Column(updateable = false)
        private DevicesBean      bean;
//        @Column(updateable = false)
//        private VehicleDAO vehicleDAO;
        @Column(updateable = false)
        private Integer oldVehicleID;
        @Column(updateable = false)
        private Vehicle vehicle;
        @Column(updateable = false)
        private boolean selected;
        @Column(updateable = false)
        private Date firmwareVersionDate;
        
        public Integer getId()
        {
            return getDeviceID();
        }

//        public void setVehicleDAO(VehicleDAO vehicleDAO)
//        {
//            this.vehicleDAO = vehicleDAO;
//        }

        Integer getOldVehicleID()
        {
            return oldVehicleID;
        }

        void setOldVehicleID(Integer oldVehicleID)
        {
            this.oldVehicleID = oldVehicleID;
        }

        public boolean isVehicleChanged()
        {
            return (oldVehicleID != getVehicleID()) && ((getVehicleID() == null) || !getVehicleID().equals(oldVehicleID));
        }

        @Override
        public String getPhone()
        {
            return MiscUtil.formatPhone(super.getPhone());
        }

        @Override
        public void setVehicleID(Integer vehicleID)
        {
            super.setVehicleID(vehicleID);
            vehicle = null;
        }

        public Vehicle getVehicle()
        {
            if (vehicle == null && getVehicleID() != null)
                vehicle = (Vehicle)bean.adminCacheBean.getAsset("vehicles", getVehicleID());
//                vehicle = vehicleDAO.findByID(getVehicleID());
            return vehicle;
        }

        public boolean isSelected()
        {
            return selected;
        }

        public void setSelected(boolean selected)
        {
            this.selected = selected;
        }

        public String getFirmwareVersionDate() {
            if (firmwareVersionDate == null)
                return "";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, yyyy h:mm:ss");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return simpleDateFormat.format(firmwareVersionDate);
        }

        public void setFirmwareVersionDate() {
            if (getFirmwareVersion() == null) {
                firmwareVersionDate = null;
                return;
            }
            firmwareVersionDate = new Date(getFirmwareVersion()*1000L);
        }

        public DevicesBean getBean() {
            return bean;
        }

        public void setBean(DevicesBean bean) {
            this.bean = bean;
        }
    }
    public boolean isBatchProductChoice(ProductType productType){
        
        return getFilterValues().get("productVersion") == null || getFilterValues().get("productVersion").equals(productType.getDescription());
    }
    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public DeviceDAO getDeviceDAO() {
        return deviceDAO;
    }

    @SuppressWarnings("unchecked")
    public CacheItemMap<Driver, Driver> getDriverMap() {
        return (CacheItemMap<Driver, Driver>)adminCacheBean.getMap("drivers");
    }

    @SuppressWarnings("unchecked")
    public CacheItemMap<Device, Device> getDeviceMap() {
        return (CacheItemMap<Device, Device>)adminCacheBean.getMap("devices");
    }

    @SuppressWarnings("unchecked")
    public CacheItemMap<Vehicle, Vehicle> getVehicleMap() {
        return (CacheItemMap<Vehicle, Vehicle>)adminCacheBean.getMap("vehicles");
    }

    @SuppressWarnings("unchecked")
    public CacheItemMap<Group, Group> getGroupMap() {
        return (CacheItemMap<Group, Group>)adminCacheBean.getMap("groups");
    }
    
    @SuppressWarnings("unchecked")
    public CacheItemMap<DeviceSettingDefinition,VehicleSetting> getVehicleSettingMap() {
        return (CacheItemMap<DeviceSettingDefinition, VehicleSetting>)adminCacheBean.getMap("vehicleSettings");
    }

    public ColumnFiltering<Vehicle> getChooseVehicleFiltering() {
        return chooseVehicleFiltering;
    }

    public String getChooseVehicleSearchKeyword() {
        return chooseVehicleSearchKeyword;
    }

    public void setChooseVehicleSearchKeyword(String chooseVehicleSearchKeyword) {
        this.chooseVehicleSearchKeyword = chooseVehicleSearchKeyword;
    }

    public List<Vehicle> getChooseVehicleItems() {
        return chooseVehicleItems;
    }

    public String getChosenVehicleID() {
        return chosenVehicleID;
    }

    public void setChosenVehicleID(String chosenVehicleID) {
        this.chosenVehicleID = chosenVehicleID;
    }

    public VehicleSettingsFactory getVehicleSettingsFactory() {
        return vehicleSettingsFactory;
    }

    public void setVehicleSettingsFactory(VehicleSettingsFactory vehicleSettingsFactory) {
        this.vehicleSettingsFactory = vehicleSettingsFactory;
    }
}
