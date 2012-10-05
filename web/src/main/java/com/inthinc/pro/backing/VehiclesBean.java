package com.inthinc.pro.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.springframework.beans.BeanUtils;

import com.inthinc.pro.backing.fwdcmd.WaysmartForwardCommand;
import com.inthinc.pro.backing.fwdcmd.WirelineDoorAlarmCommand;
import com.inthinc.pro.backing.fwdcmd.WirelineKillMotorCommand;
import com.inthinc.pro.backing.model.VehicleSettingManager;
import com.inthinc.pro.backing.model.VehicleSettingsFactory;
import com.inthinc.pro.backing.ui.DeviceStatusSelectItems;
import com.inthinc.pro.backing.ui.ProductTypeSelectItems;
import com.inthinc.pro.backing.ui.VehicleTypeSelectItems;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.jdbc.FwdCmdSpoolWS;
import com.inthinc.pro.model.AutoLogoff;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleDOTType;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.SelectItemUtil;

/**
 * @author David Gileadi
 */
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

    private List<Driver>                          drivers;
    private TreeMap<Integer, Boolean>             driverAssigned;
    
    private ProductType                           batchEditProductChoice;
    
    // Stuff to do with vehicleSettings for the device
    private VehicleSettingsFactory              vehicleSettingsFactory;
    private Map<Integer, VehicleSettingManager> vehicleSettingManagers;

    private CacheBean cacheBean;
    
    private FwdCmdSpoolWS fwdCmdSpoolWS;

    
    @Override
    public void initBean() {
        super.initBean();
    }
    @Override
    public void initFilterValues(){
        super.initFilterValues();
        for(String column:AVAILABLE_COLUMNS){
            getFilterValues().put(column, null);
        }
    }
    @Override
    public void refreshItems()
    {
        super.refreshItems();
        setBatchEdit(false);
    }
    public boolean isUserAbleToCreate(){
        return isUserInRole("ROLE_VEHICLESCREATE");
    }
    public boolean isVehicleInfoDisabled(){
        return !isUserInRole("ROLE_VEHICLESCREATE");
    }
    public boolean isWirelineInfoDisabled(){
        return !isUserInRole("ROLE_VEHICLESWIRELINE");
    }
    public boolean isAssignmentInfoDisabled(){
        return !isUserInRole("ROLE_VEHICLESDRIVER");
    }
    public boolean isSpeedInfoDisabled(){
        return !isUserInRole("ROLE_VEHICLESSPEED");
    }
    public boolean isFilterProductChoice(ProductType productType){
        
        return getFilterValues().get("productType") == null || getFilterValues().get("productType").equals(productType);
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

    @Override
    protected List<VehicleView> loadItems()
    {
        //  cj - removed code and returned null since pagination does this now         
       return null;
    }
    
    public void setVehicleSettingManagers(Map<Integer, VehicleSettingManager> vehicleSettingManagers) {
        this.vehicleSettingManagers = vehicleSettingManagers;
    }
    
    public Map<Integer, VehicleSettingManager> getVehicleSettingManagers() {
        if (vehicleSettingManagers == null) {
            vehicleSettingManagers = vehicleSettingsFactory.retrieveVehicleSettings(getUser().getGroupID(), null);
        }
        return vehicleSettingManagers;
    }
    
    public Set<Integer> getKeySet(){
        
        return vehicleSettingManagers.keySet();
    }

    public List<String> getClassTypes(){
        
        List<String> classNames = new ArrayList<String>();
        if(vehicleSettingManagers.get(null) == null){
            vehicleSettingManagers.put(null, new TiwiproSettingManager(null,null, batchEditProductChoice, null));
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
    public VehicleView createVehicleView(Vehicle vehicle)
    {
        final VehicleView vehicleView = new VehicleView();
        vehicleView.bean = this;
        BeanUtils.copyProperties(vehicle, vehicleView);
        vehicleView.setOldGroupID(vehicle.getGroupID());
        vehicleView.setOldDriverID(vehicle.getDriverID());
        vehicleView.setSelected(false);
        if (!isBatchEdit())
            vehicleView.initForwardCommandDefs();

        return vehicleView;
    }
    
    @Override
    protected List<String> getBatchEditIgnoreField() {
        List<String> ignoreFields = new ArrayList<String>();
        ignoreFields.add("wirelineDoorAlarm");
        ignoreFields.add("wirelineKillMotor");
        return ignoreFields;
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
                return MessageUtil.getMessageString(vehicle.getStatus().toString());
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
        return vehicleView;
    }

    @Override
    public String batchEdit()
    {
        List<VehicleView> inViewItems = columnFiltering.getInViewItems(filteredItems);
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
        if (firstSelected == -1) return;
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
                        
        vehicleSettingManagers.put(-1, vehicleSettingsFactory.getSettingManagerForBatchEditing(batchEditProductChoice));
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
        // for batch edit only
        if ( isBatchEdit() ) {

            if (    getItem().editableVehicleSettings.getEphone() != null && 
                    getItem().editableVehicleSettings.getEphone().trim().length() != 0 ) {
                getUpdateField().put("editableVehicleSettings.ephone", true);
            }

            if (    getItem().editableVehicleSettings instanceof TiwiproEditableVehicleSettings ) {
                TiwiproEditableVehicleSettings tevs = (TiwiproEditableVehicleSettings) getItem().editableVehicleSettings;
            
                if ( tevs.getAutoLogoffSlider() != null & tevs.getAutoLogoffSlider() != 0 ) {
                    getUpdateField().put("editableVehicleSettings.autologoffSeconds", true);
                }
            }
        }

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
            if((getFilterValues().get("productType") != null) && !ProductType.UNKNOWN.getDescription().equals(getFilterValues().get("productType"))){
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
                context.addMessage("edit-form:editVehicle-vin", message);
            }
         }
        
        return valid;
    }

    @Override
    protected VehicleView revertItem(VehicleView vehicleView)
    {
        VehicleView view = createVehicleView(vehicleDAO.findByID(vehicleView.getVehicleID()));
        vehicleSettingsFactory.resetToSettingManager(vehicleSettingManagers, vehicleView);
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
                
                dealWithSpecialSettings(vehicle);
                vehicleSettingManagers.get(vehicle.getVehicleID()).updateVehicleSettings(vehicle.getVehicleID(),vehicle.getEditableVehicleSettings(),
                                                            this.getUserID(), "portal update", isBatchEdit()?getUpdateField():null);
            }
            vehicle.setOldGroupID(vehicle.getGroupID());

            if (vehicle.isDriverChanged()){
                assignDriver(vehicle);
                drivers = null;
            }
            
            // added for pagination
            vehicleSettingsFactory.upatedVehicleSettingManager(getVehicleSettingManagers(), vehicle);
            vehicle.setEditableVehicleSettings(vehicleSettingManagers.get(vehicle.getVehicleID()).associateSettings(vehicle.getVehicleID()));
            // end - added for pagination
                        
            // add a message
            final String summary = MessageUtil.formatMessageString(create ? "vehicle_added" : "vehicle_updated", vehicle.getName());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }
    }
    private void dealWithSpecialSettings(VehicleView vehicle){
        
       	vehicle.getEditableVehicleSettings().dealWithSpecialSettings(vehicle, item, getUpdateField(), isBatchEdit());
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
    
    public List<SelectItem> getDotTypes() {
        return SelectItemUtil.toList(VehicleDOTType.class, false);
    }

    
    public List<SelectItem> getTiwiProTypes() {
        return SelectItemUtil.toList(VehicleType.class, false);
    }

    public List<SelectItem> getProductTypesSelectItems() {

        return ProductTypeSelectItems.getSelectItems();
    }

    public List<SelectItem> getVehicleTypesSelectItems() {
        return VehicleTypeSelectItems.getSelectItems();
    }

    public List<SelectItem> getStatuses() {
        return DeviceStatusSelectItems.getSelectItems();
    }

    public Map<String, State> getStates()
    {
        return STATES;
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
    public FwdCmdSpoolWS getFwdCmdSpoolWS() {
        return fwdCmdSpoolWS;
    }
    public void setFwdCmdSpoolWS(FwdCmdSpoolWS fwdCmdSpoolWS) {
        this.fwdCmdSpoolWS = fwdCmdSpoolWS;
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
        private EditableVehicleSettings editableVehicleSettings;
        @Column(updateable = false)
        private boolean           selected;
        
        @Column(updateable = false)
        private WaysmartForwardCommand wirelineDoorAlarm;

        @Column(updateable = false)
        private WaysmartForwardCommand wirelineKillMotor;
        
        

        
        public VehicleView() {
            super();
        }
        public VehicleView(Integer vehicleID, Integer groupID, Status status, String name, String make, String model, Integer year, String color, VehicleType vtype, String vin, Integer weight,
                String license, State state) {
            super(vehicleID, groupID, status, name, make, model, year, color, vtype, vin, weight, license, state);
        }
        
        public void initForwardCommandDefs() {
            wirelineDoorAlarm = new WirelineDoorAlarmCommand(getDevice(), getFwdCmdAddress(), bean.getFwdCmdSpoolWS());
            wirelineKillMotor = new WirelineKillMotorCommand(getDevice(), getFwdCmdAddress(), bean.getFwdCmdSpoolWS());
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
            if(editableVehicleSettings == null) return ProductType.UNKNOWN.getDescription();
//            return editableVehicleSettings.getProductType().getDescription().getProductName();
            return editableVehicleSettings.getProductDisplayName();
        }
        public ProductType getProductType() {
            if(editableVehicleSettings == null) return ProductType.UNKNOWN;
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
        public void setGroupID(Integer groupID) {
            if (super.getGroupID() != groupID) {
                super.setGroupID(groupID);
                group = null;
                driver = null;
            }
        }

        public Group getGroup()
        {
            if (group == null) {
                group = bean.getGroupHierarchy().getGroup(getGroupID());
                if (group == null)
                    group = bean.groupDAO.findByID(getGroupID());
            }
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

        public boolean isSelected()
        {
            return selected;
        }

        public void setSelected(boolean selected)
        {
            this.selected = selected;
        }

        public WaysmartForwardCommand getWirelineDoorAlarm() {
            return wirelineDoorAlarm;
        }
        public void setWirelineDoorAlarm(WaysmartForwardCommand wirelineDoorAlarm) {
            this.wirelineDoorAlarm = wirelineDoorAlarm;
        }
        public WaysmartForwardCommand getWirelineKillMotor() {
            return wirelineKillMotor;
        }
        public void setWirelineKillMotor(WaysmartForwardCommand wirelineKillMotor) {
            this.wirelineKillMotor = wirelineKillMotor;
        }
        
        private String getFwdCmdAddress() {
            if (getDevice() != null && getDevice().getImei() != null)
                return getDevice().getImei();
            
            return null;
        }
        public String getVehicleTypeString(){
        	return MessageUtil.getMessageString(getVtype().toString(),bean.getLocale());
        }
        public String getStatusName(){
        	return MessageUtil.getMessageString(getStatus().toString());
        }

        public VehicleDOTType getDotVehicleType(){
        	if(editableVehicleSettings instanceof WaySmartEditableVehicleSettings){
        		return VehicleDOTType.getFromSetting(((WaySmartEditableVehicleSettings)editableVehicleSettings).getDotVehicleType());
        	}
        	return null;
        }
        
        public void setDotVehicleType(VehicleDOTType vehicleDOTType){
           	if(editableVehicleSettings instanceof WaySmartEditableVehicleSettings){
        		((WaySmartEditableVehicleSettings)editableVehicleSettings).setDotVehicleType(vehicleDOTType.getConfiguratorSetting());
        	}
        	
        }
    }
    
    // overriding because the pagination doesn't use the filtered list 

    @Override
    public List<VehiclesBean.VehicleView> getFilteredItems() {
        filteredItems.clear();
        filteredItems.addAll(items);
        return filteredItems;
    }

    @Override
    protected void applyFilter(int page)
    {
        filteredItems.clear();
        filteredItems.addAll(items);
    }
}
