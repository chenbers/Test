package com.inthinc.pro.backing;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.springframework.beans.BeanUtils;

import com.inthinc.pro.backing.model.VehicleSettingManager;
import com.inthinc.pro.backing.model.VehicleSettingsFactory;
import com.inthinc.pro.backing.ui.DeviceStatusSelectItems;
import com.inthinc.pro.backing.ui.ProductTypeSelectItems;
import com.inthinc.pro.backing.ui.VehicleTypeSelectItems;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.AutoLogoff;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.SelectItemUtil;

/**
 * @author David Gileadi
 */
@SuppressWarnings("unused")
public class VehiclesBean extends BaseAdminBean<VehiclesBean.VehicleView> implements PersonChangeListener, Serializable
{
    private static final long                     serialVersionUID       = 1L;

    static final List<String>             AVAILABLE_COLUMNS;
    private static final int[]                    DEFAULT_COLUMN_INDICES = new int[] { 0, 1, 8, 12, 13,18};

    private static final Map<String, String>      YEARS;
    private static final Map<String, State>       STATES;

    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("name");
        AVAILABLE_COLUMNS.add("driverID");
        AVAILABLE_COLUMNS.add("groupID");
        AVAILABLE_COLUMNS.add("year");
        AVAILABLE_COLUMNS.add("make");
        AVAILABLE_COLUMNS.add("model");
        AVAILABLE_COLUMNS.add("color");
        AVAILABLE_COLUMNS.add("vtype");
        AVAILABLE_COLUMNS.add("VIN");
        AVAILABLE_COLUMNS.add("weight");
        AVAILABLE_COLUMNS.add("license");
        AVAILABLE_COLUMNS.add("state");
        AVAILABLE_COLUMNS.add("status");
        AVAILABLE_COLUMNS.add("deviceID");
        AVAILABLE_COLUMNS.add("odometer");
        AVAILABLE_COLUMNS.add("ephone");
        AVAILABLE_COLUMNS.add("DOT");
        AVAILABLE_COLUMNS.add("IFTA");
        AVAILABLE_COLUMNS.add("productType");
        

        // years
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        final int nextYear = cal.get(Calendar.YEAR);
        YEARS = new TreeMap<String, String>(new Comparator<String>()
        {
            @Override
            public int compare(String o1, String o2)
            {
                return o2.compareTo(o1);
            }
        });
        for (int year = 1970; year <= nextYear; year++)
            YEARS.put(String.valueOf(year), String.valueOf(year));

        // states
        STATES = new TreeMap<String, State>();
        for (final State state : States.getStates().values())
            STATES.put(state.getName(), state);
    }

    private VehicleDAO                            vehicleDAO;
    private DriverDAO                             driverDAO;
    private DeviceDAO                             deviceDAO;

    private List<Driver>                          drivers;
    private TreeMap<Integer, Boolean>             driverAssigned;
    
    private ProductType                           batchEditProductChoice;
    
    // Stuff to do with vehicleSettings for the device
    private VehicleSettingsFactory              vehicleSettingsFactory;
    private Map<Integer, VehicleSettingManager> vehicleSettingManagers;

    private CacheBean cacheBean;
    
    @Override
    public void initBean() {

        super.initBean();
    }
    @Override
    public void initFilterValues(){
        super.initFilterValues();
        for(String column:AVAILABLE_COLUMNS){
            filterValues.put(column, null);
        }
    }
    public boolean isFilterProductChoice(ProductType productType){
        
        return filterValues.get("productType") == null || filterValues.get("productType").equals(productType);
    }
    public CacheBean getCacheBean() {
		return cacheBean;
	}

	public void setCacheBean(CacheBean cacheBean) {
		this.cacheBean = cacheBean;
	}

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO)
    {
        this.deviceDAO = deviceDAO;
    }

    public List<VehicleView> getPlainVehicles(){
        
        List<Vehicle> plainVehicles = vehicleDAO.getVehiclesInGroupHierarchy(getUser().getGroupID());
        final LinkedList<VehicleView> items = new LinkedList<VehicleView>();
        for (final Vehicle vehicle : plainVehicles)
        {
            VehicleView view = createVehicleView(vehicle);
            items.add(view);   
        }
        return items;
    }
    @Override
    protected List<VehicleView> loadItems()
    {
        
        // Get all the vehicles
        final List<Vehicle> plainVehicles = vehicleDAO.getVehiclesInGroupHierarchy(getUser().getGroupID());
        
       // Get all the settings 
        
        vehicleSettingManagers = vehicleSettingsFactory.retrieveVehicleSettings(getUser().getGroupID());
        
        // Wrap Vehicles and Devices
        final LinkedList<VehicleView> items = new LinkedList<VehicleView>();
        for (final Vehicle vehicle : plainVehicles)
        {
            VehicleView vehicleView = createVehicleView(vehicle);
            checkForSettings(vehicle.getVehicleID());
            vehicleView.setEditableVehicleSettings(vehicleSettingManagers.get(vehicle.getVehicleID()).associateSettings(vehicle.getVehicleID()));

            items.add(vehicleView);   
        }

        return items;
    }
    private void checkForSettings(Integer vehicleID){
        
        if(vehicleSettingManagers.get(vehicleID) == null){
            if (!isBatchEdit()){
            
                vehicleSettingManagers.put(vehicleID, vehicleSettingsFactory.getUnknownSettingManager(vehicleID));
            }
            else {
                
                vehicleSettingManagers.put(vehicleID, vehicleSettingsFactory.getSettingManager(batchEditProductChoice,vehicleID, null));
            }
        }
    }
    
    public Map<Integer, VehicleSettingManager> getVehicleSettingManagers() {
        return vehicleSettingManagers;
    }
    public Set<Integer> getKeySet(){
        
        return vehicleSettingManagers.keySet();
    }

    public List<String> getClassTypes(){
        
        List<String> classNames = new ArrayList<String>();
        if(vehicleSettingManagers.get(null) == null){
            vehicleSettingManagers.put(null, new TiwiproSettingManager(null, batchEditProductChoice, null));
        }
        for (Entry<Integer,VehicleSettingManager> vsm : vehicleSettingManagers.entrySet()){
            
            classNames.add(vsm.getValue().getClass().toString());
        }
        return classNames;
    }
    public VehicleSettingsFactory getVehicleSettingsFactory() {
        return vehicleSettingsFactory;
    }
    public void setVehicleSettingsFactory(VehicleSettingsFactory vehicleSettingsFactory) {
        this.vehicleSettingsFactory = vehicleSettingsFactory;
    }
    /**
     * Creates a VehicleView object from the given Vehicle object.
     * 
     * @param vehicle
     *            The vehicle.
     * @return The new VehicleView object.
     */
    private VehicleView createVehicleView(Vehicle vehicle)
    {
        final VehicleView vehicleView = new VehicleView();
        vehicleView.bean = this;
        BeanUtils.copyProperties(vehicle, vehicleView);
        vehicleView.setOldGroupID(vehicle.getGroupID());
        vehicleView.setOldDriverID(vehicle.getDriverID());
        vehicleView.setSelected(false);

        return vehicleView;
    }
    @Override
    public String fieldValue(VehicleView vehicle, String column)
    {
        if (column.equals("driverID"))
        {
            if ((vehicle.getDriver() != null) && (vehicle.getDriver().getPerson() != null))
                return vehicle.getDriver().getPerson().getFullName();
            return null;
        }
        else if (column.equals("groupID"))
        {
            if (vehicle.getGroup() != null)
                return vehicle.getGroup().getName();
        }
        else if (column.equals("status"))
        {
            if (vehicle.getStatus() != null)
                return MessageUtil.getMessageString(vehicle.getStatus().getDescription().toLowerCase());
            return null;
        }
        else if (column.equals("deviceID"))
        {
            if(vehicle.getDevice() != null)
                return vehicle.getDevice().getName();
            return null;
        }

        return super.fieldValue(vehicle, column);
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
        return "vehiclesHeader_";
    }
   
    @Override
    protected Boolean authorizeAccess(VehicleView item)
    {
        if (item.getGroupID() == null) return Boolean.FALSE;
        
        Group group = getGroupHierarchy().getGroup(item.getGroupID());

        if (group != null)
        {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    @Override
    public TableType getTableType()
    {
        return TableType.ADMIN_VEHICLES;
    }

    @Override
    protected VehicleView createAddItem()
    {
        final Vehicle vehicle = new Vehicle();
        vehicle.setStatus(Status.ACTIVE);
        //TODO decide how to create add item
        VehicleView vehicleView = createVehicleView(vehicle);
//        if(batchEditProductChoice != null){
//            createSettingManagerForCreateItem();
//            vehicleView.setEditableVehicleSettings(vehicleSettingManagers.get(-1).associateSettings(-1));
//        }
        return vehicleView;
    }

    @Override
    public String batchEdit()
    {
        List<VehicleView> inViewItems = getInViewItems();
        setBatchEditProductChoice(inViewItems);
        final String redirect = super.batchEdit();
       
        if(isBatchEdit()){
            getItem().setVehicleID(-1);
            if(batchEditProductChoice != null){
                createSettingManagerForCreateItem();
                getItem().setEditableVehicleSettings(vehicleSettingManagers.get(-1).associateSettings(-1));
            }
        }
        return redirect;
    }
    private void setBatchEditProductChoice(List<VehicleView> inViewItems){
        batchEditProductChoice = null;
        ProductType productChoice = null;
        //set first value
        int firstSelected = getFirstSelectedItem(inViewItems);
        VehicleView firstSelectedVehicle = inViewItems.get(firstSelected);
        if(firstSelectedVehicle.getEditableVehicleSettings()!= null){
            productChoice = firstSelectedVehicle.getEditableVehicleSettings().getProductType();
        }
        if (productChoice == null) return;
        for(VehicleView vehicleView : inViewItems){
            
            if (vehicleView.isSelected()){
                
                if ((vehicleView.editableVehicleSettings == null) || 
                        (vehicleView.editableVehicleSettings.getProductType() == null) ||
                        !(vehicleView.editableVehicleSettings.getProductType().equals(productChoice))){
                    return;
                }
            }
        }
        batchEditProductChoice = productChoice;
    }
    private int getFirstSelectedItem(List<VehicleView> inViewItems){
        int firstSelected = 0;
        for(VehicleView vehicleView : inViewItems){
            
            if (vehicleView.isSelected()) return firstSelected;
            firstSelected++;
        }
        return -1;
    }
    private void createSettingManagerForCreateItem(){
                        
        vehicleSettingManagers.put(-1, vehicleSettingsFactory.getSettingManager(batchEditProductChoice,-1, null));
    }
    @Override
    public String cancelEdit()
    {
        getItem().setGroupID(getItem().getOldGroupID());
        getItem().setDriverID(getItem().getOldDriverID());
        return super.cancelEdit();
    }

    @Override
    protected void doDelete(List<VehicleView> deleteItems)
    {
        final FacesContext context = FacesContext.getCurrentInstance();

        for (final VehicleView vehicle : deleteItems)
        {
            vehicleDAO.deleteByID(vehicle.getVehicleID());

            // add a message
            final String summary = MessageUtil.formatMessageString("vehicle_deleted", vehicle.getName());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }
    }

    public List<Driver> getDrivers()
    {
        if (drivers == null)
            drivers = driverDAO.getAllDrivers(getUser().getGroupID());
        return drivers;
    }

    @Override
    public void personListChanged()
    {
        drivers = null;
    }
    
    public Integer getAccountID()
    {
        return getProUser().getGroupHierarchy().getTopGroup().getAccountID();

    }
    
    public TreeMap<Integer, String> getGroupNames()
    {
        final TreeMap<Integer, String> groupNames = new TreeMap<Integer, String>();
        for (final Group group : getGroupHierarchy().getGroupList())
            groupNames.put(group.getGroupID(), group.getName());
        return groupNames;
    }

    public TreeMap<Integer, Boolean> getDriverAssigned()
    {
        if (driverAssigned == null)
        {
            driverAssigned = new TreeMap<Integer, Boolean>();
            for (final VehicleView vehicle : getItems())
                if (vehicle.getDriverID() != null)
                    driverAssigned.put(vehicle.getDriverID(), true);
        }
        return driverAssigned;
    }

    public void chooseDriver()
    {
        final Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        final String driverID = params.get("driverID");
        if (driverID != null)
            getItem().setDriverID(Integer.parseInt(driverID));

        if (Boolean.parseBoolean(params.get("immediate")) && !isAdd() && !isBatchEdit())
            assignDriver(getItem());

        drivers = null;
    }

    @Override
    public String save()
    {
        // prefix the sensitivity settings with "device." x
        // prefix the sensitivity settings with "editableVehicleSettings."
//        final Map<String, Boolean> updateField = getUpdateField();
//        Map<String,Boolean> tempUpdateField = new HashMap<String, Boolean>();
//        
//        
//        if (updateField != null)
//        {
//            for (final String key : updateField.keySet())
//            {
//                if (key.startsWith("hard"))
//                {
//                    tempUpdateField.put("editableVehicleSettings." + key,  updateField.get(key));
//                }
//            }
//            
//            for(final String key : tempUpdateField.keySet())
//            {
//                updateField.put(key, tempUpdateField.get(key));
//            }
//        }
//        
//        updateField.put("device.speedSettings", false);
//        updateField.put("device.deviceID", false);
//        updateField.put("device.accountID", false);
//        updateField.put("device.vehicleID", false);
//        updateField.put("device.status", false);
//        updateField.put("device.name", false);
//        updateField.put("device.imei", false);
//        updateField.put("device.sim", false);

        // if ((getItem().getDevice() != null) && getItem().getDevice().isSensitivitiesInverted())
        // getItem().getDevice().invertSensitivities();

        // TODO: this should be refactored, but to be on the save side, just clear the cache and force a refresh when vehicle(s) change
        cacheBean.setVehicleMap(null);
        return super.save();
    }
    
    
    @Override
    protected boolean validateSaveItem(VehicleView vehicleView)
    {
        final FacesContext context = FacesContext.getCurrentInstance();
        boolean valid = true;
        final String required = "required";
        // Required fields check
        if(!isBatchEdit()){
            if((vehicleView.getEditableVehicleSettings() != null)){
                valid = vehicleView.getEditableVehicleSettings().validateSaveItems(context, isBatchEdit(), getUpdateField());
            }
        }
        else {
            if((filterValues.get("productType") != null) && !ProductType.UNKNOWN.getDescription().equals(filterValues.get("productType"))){
                valid = vehicleView.getEditableVehicleSettings().validateSaveItems(context, isBatchEdit(), getUpdateField());
            }
        }
        if(vehicleView.getMake() == null || vehicleView.getMake().equals("")
                && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("make"))))
        {
            valid = false;
            String summary = MessageUtil.getMessageString(required);
            context.addMessage("edit-form:editVehicle-make", new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
        }
        if(!isBatchEdit()){
            if(vehicleView.getName() == null || vehicleView.getName().equals("")) 
            {
                valid = false;
                String summary = MessageUtil.getMessageString(required);
                context.addMessage("edit-form:editVehicle-name", new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));        
            } else {
                // Pattern to check for, note blank being sneaky on the end
                Pattern pat = Pattern.compile("[a-zA-Z0-9 ]+");
                Matcher mtch = pat.matcher(vehicleView.getName());
                if ( !mtch.matches() ) {
                    valid = false;
                    String summary = MessageUtil.getMessageString("vehicle_name_rules", getLocale());
                    context.addMessage("edit-form:editVehicle-name", new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));                   
                } 
            }
        }
        if(vehicleView.getModel() == null || vehicleView.getModel().equals("")
                && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("model"))))
        {
            valid = false;
            String summary = MessageUtil.getMessageString(required);
            context.addMessage("edit-form:editVehicle-model", new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
        }
        
        if((vehicleView.getGroupID() == null)
                && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("groupID"))))
        {
            valid = false;
            String summary = MessageUtil.getMessageString(required);
            context.addMessage("edit-form:editVehicle-groupID", new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
        }
        
        // unique VIN
        if (!isBatchEdit()){
            final Vehicle byVIN = vehicleDAO.findByVIN(vehicleView.getVIN());
            if ((byVIN != null) && !byVIN.getVehicleID().equals(vehicleView.getVehicleID()))
            {
                valid = false;
                final String summary = MessageUtil.getMessageString("editVehicle_uniqueVIN");
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                context.addMessage("edit-form:editVehicle-VIN", message);
            }
         }
        
        return valid;
    }

    @Override
    protected VehicleView revertItem(VehicleView vehicleView)
    {
        //TODO decide how to revert an item

        VehicleView view = createVehicleView(vehicleDAO.findByID(vehicleView.getVehicleID()));
        checkForSettings(vehicleView.getVehicleID());
        view.setEditableVehicleSettings(vehicleSettingManagers.get(vehicleView.getVehicleID()).associateSettings(vehicleView.getVehicleID()));
        return view;
    }

    @Override
    protected void doSave(List<VehicleView> saveItems, boolean create)
    {
        final FacesContext context = FacesContext.getCurrentInstance();

        for (final VehicleView vehicle : saveItems)
        {
            if (create){
                
                vehicle.setVehicleID(vehicleDAO.create(vehicle.getGroupID(), vehicle));
            }
            else{
                
                vehicleDAO.update(vehicle);
                
                dealWithSpeedSettings(vehicle);
                vehicleSettingManagers.get(vehicle.getVehicleID()).updateVehicleSettings(vehicle.getVehicleID(),vehicle.getEditableVehicleSettings(),
                                                            this.getUserID(), "portal update");
            }
            vehicle.setOldGroupID(vehicle.getGroupID());

            if (vehicle.isDriverChanged())
                assignDriver(vehicle);
                        
            // add a message
            final String summary = MessageUtil.formatMessageString(create ? "vehicle_added" : "vehicle_updated", vehicle.getName());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }

        drivers = null;
    }
    private void dealWithSpeedSettings(VehicleView vehicle){
        
        if (isBatchEdit() && vehicle.getEditableVehicleSettings() instanceof TiwiproEditableVehicleSettings){
            final Map<String, Boolean> updateField = getUpdateField();
            String keyBase = "speed";
            for (int i=0; i< 15;i++){
                Boolean isSpeedFieldUpdated = updateField.get(keyBase+i);
                if(isSpeedFieldUpdated != null && isSpeedFieldUpdated){
                    ((TiwiproEditableVehicleSettings)vehicle.getEditableVehicleSettings()).getSpeedSettings()[i] = 
                        ((TiwiproEditableVehicleSettings)getItem().getEditableVehicleSettings()).getSpeedSettings()[i];
                }
            }
        }
    }
    private void assignDriver(final VehicleView vehicleView)
    {
        vehicleDAO.setVehicleDriver(vehicleView.getVehicleID(), vehicleView.getDriverID());
        vehicleView.setOldDriverID(vehicleView.getDriverID());

        if (vehicleView.getDriverID() != null)
            getDriverAssigned().put(vehicleView.getDriverID(), true);
    }

    @Override
    protected String getDisplayRedirect()
    {
        return "pretty:adminVehicle";
    }

    @Override
    protected String getEditRedirect()
    {
        return "pretty:adminEditVehicle";
    }

    @Override
    protected String getFinishedRedirect()
    {
        return "pretty:adminVehicles";
    }

    public Map<String, String> getYears()
    {
        return YEARS;
    }

    public List<SelectItem> getWaySmartTypes() {
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        selectItemList.add(new SelectItem(VehicleType.LIGHT,MessageUtil.getMessageString(VehicleType.LIGHT.toString())));
        selectItemList.add(new SelectItem(VehicleType.HEAVY,MessageUtil.getMessageString(VehicleType.HEAVY.toString())));
        
        return selectItemList;
    }
    public List<SelectItem> getTiwiProTypes() {
        return SelectItemUtil.toList(VehicleType.class, false);
    }

    public List<SelectItem> getProductTypesSelectItems() {

        return ProductTypeSelectItems.INSTANCE.getSelectItems();
    }

    public List<SelectItem> getVehicleTypesSelectItems() {
        return VehicleTypeSelectItems.INSTANCE.getSelectItems();
    }

    public List<SelectItem> getStatusSelectItems() {
        return DeviceStatusSelectItems.INSTANCE.getSelectItems();
    }
    public List<SelectItem> getZoneTypeSelectItems()
    {
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();

        for (VehicleType p : EnumSet.allOf(VehicleType.class))
        {
            SelectItem selectItem = new SelectItem(p.getCode(),MessageUtil.getMessageString(p.toString()));
            selectItemList.add(selectItem);
        }

        return selectItemList;
    }

    public Map<String, State> getStates()
    {
        return STATES;
    }

    public List<SelectItem> getStatuses()
    {
        return SelectItemUtil.toList(Status.class, false, Status.DELETED);
    }

    public TreeMap<String, Integer> getTeams(){
        return getGroupHierarchy().getTeams();
    }
    public List<SelectItem> getAutoLogoffs()
    {
        return SelectItemUtil.toList(AutoLogoff.class, false);
    }
    public ProductType getBatchEditProductChoice() {
        return batchEditProductChoice;
    }

    public static class VehicleView extends Vehicle implements EditItem
    {
        @Column(updateable = false)
        private static final long serialVersionUID = -2051727502162908991L;

        @Column(updateable = false)
        private VehiclesBean      bean;
        @Column(updateable = false)
        private Integer           oldGroupID;
        @Column(updateable = false)
        private Group             group;
        @Column(updateable = false)
        private Integer           oldDriverID;
        @Column(updateable = false)
        private Driver            driver;
        @Column(updateable = false)
        private Device            device;
        @Column(updateable = false)
        private EditableVehicleSettings editableVehicleSettings;
        @Column(updateable = false)
        private boolean           selected;

        
        public VehicleView() {
            super();
        }
        public VehicleView(Integer vehicleID, Integer groupID, Status status, String name, String make, String model, Integer year, String color, VehicleType vtype, String vin, Integer weight,
                String license, State state) {
            super(vehicleID, groupID, status, name, make, model, year, color, vtype, vin, weight, license, state);
        }
        public void setEditableVehicleSettings(EditableVehicleSettings editableVehicleSettings) {
            this.editableVehicleSettings = editableVehicleSettings;
        }
        public EditableVehicleSettings getEditableVehicleSettings() {
            return editableVehicleSettings;
        }
        public Integer getId()
        {
            return getVehicleID();
        }

        public String getProductTypeName() {
            if(editableVehicleSettings == null ||editableVehicleSettings.getProductType() == null) return ProductType.UNKNOWN.getDescription().getProductName();
            return editableVehicleSettings.getProductType().getDescription().getProductName();
        }
        public ProductType getProductType() {
            if(editableVehicleSettings == null ||editableVehicleSettings.getProductType() == null) return ProductType.UNKNOWN;
            return editableVehicleSettings.getProductType();
        }
        @Override
        public Integer getWeight()
        {
            final Integer weight = super.getWeight();
            if ((weight != null) && (weight == 0))
                return null;
            return weight;
        }

        Integer getOldGroupID()
        {
            return oldGroupID;
        }

        void setOldGroupID(Integer oldGroupID)
        {
            this.oldGroupID = oldGroupID;
        }

        @Override
        public void setGroupID(Integer groupID)
        {
            super.setGroupID(groupID);
            group = null;
            driver = null;
            bean.drivers = null;
        }

        public Group getGroup()
        {
            if (group == null)
                group = bean.groupDAO.findByID(getGroupID());
            return group;
        }

        Integer getOldDriverID()
        {
            return oldDriverID;
        }

        void setOldDriverID(Integer oldDriverID)
        {
            this.oldDriverID = oldDriverID;
        }

        public boolean isDriverChanged()
        {
            return (oldDriverID != getDriverID()) && ((getDriverID() == null) || !getDriverID().equals(oldDriverID));
        }

        @Override
        public void setDriverID(Integer driverID)
        {
            super.setDriverID(driverID);
            driver = null;
        }

        public Driver getDriver()
        {
            if (driver == null)
                driver = bean.driverDAO.findByID(getDriverID());
            return driver;
        }

        public Device getDevice()
        {
            if (device == null)
                device = bean.deviceDAO.findByID(getDeviceID());
            return device;
        }
        
        public void setDevice(Device device)
        {
            this.device = device;
        }

        public boolean isSelected()
        {
            return selected;
        }

        public void setSelected(boolean selected)
        {
            this.selected = selected;
        }
    }

}
