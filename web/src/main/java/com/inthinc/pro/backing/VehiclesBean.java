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

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.jdbc.DriverJDBCDAO;
import com.inthinc.pro.model.GroupHierarchy;
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
import com.inthinc.pro.model.VehicleIdentifiers;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.WS850HOSDOTType;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.util.BeanUtil;
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
    private DriverJDBCDAO                         driverJDBCDAO;
    private GroupDAO                              groupDAO;

    private List<Driver>                          drivers;
    private TreeMap<Integer, Boolean>             driverAssigned;
    
    private ProductType                           batchEditProductChoice;

    // Stuff to do with vehicleSettings for the device
    private VehicleSettingsFactory              vehicleSettingsFactory;
    private Map<Integer, VehicleSettingManager> vehicleSettingManagers;

    private CacheBean cacheBean;
    
    private FwdCmdSpoolWS fwdCmdSpoolWS;

    private Map<Integer, Driver> driverMap;

    
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
    
    @Override
    protected void initEditItem(Integer editID)
    {
        VehicleView vehicleView = createVehicleView(vehicleDAO.findByID(editID));
        vehicleSettingsFactory.resetToSettingManager(getVehicleSettingManagers(), vehicleView);
        vehicleView.setEditableVehicleSettings(getVehicleSettingManagers().get(vehicleView.getVehicleID()).associateSettings(vehicleView.getVehicleID()));

        items = new ArrayList<VehicleView>();
        items.add(vehicleView);

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
            vehicleSettingManagers.put(null, new TiwiproSettingManager(null,null, null));
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
            return vehicle.getDriverName();
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
        setBatchEditProductChoice();
        final String redirect = super.batchEdit();
        
        for (String key : this.getUpdateField().keySet()) {
            System.out.println(key);
        }
            
       
        if(isBatchEdit()){
            getItem().setVehicleID(-1);
            createSettingManagerForCreateItem();
            getItem().setEditableVehicleSettings(vehicleSettingManagers.get(-1).associateSettings(-1));
            setUpdateField(null);
            getUpdateField();

        }
        return redirect;
    }
    private void setBatchEditProductChoice()
    {
        batchEditProductChoice = null;
        ProductType productChoice = null;
        for (VehicleIdentifiers vehicleIdentifiers : vehicleIdentifiersList) {
            Boolean selected = selectedMap.get(vehicleIdentifiers.getVehicleID());
            if (selected != null && selected.equals(Boolean.TRUE)) {
                if (productChoice == null) {
                    productChoice = vehicleIdentifiers.getProductType();
                    if (productChoice == null)
                        return;
                }
                else {
                    if (vehicleIdentifiers.getProductType() == null || !vehicleIdentifiers.getProductType().equals(productChoice)) {
                        return;
                    }
                }
            }
        }
        batchEditProductChoice = productChoice;
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
        if (drivers == null) {
            Integer groupID = getUser().getGroupID();
            GroupHierarchy groupHierarchy = new GroupHierarchy(groupDAO.getGroupsByAcctID(getAccountID()));
            List<Integer> groupIDList = groupHierarchy.getSubGroupIDList(groupID);
            drivers = driverJDBCDAO.getDriversInGroupIDList(groupIDList);
            driverMap = new HashMap<Integer, Driver>();
            for (Driver d: drivers){
                driverMap.put(d.getDriverID(), d);
            }
        }
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
        
        for (final Group group : getGroupHierarchy().getGroupList()) {
            groupNames.put(group.getGroupID(), group.getName());
        }
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
        
        if (isBatchEdit()) {
            batchSave(saveItems);
            return;
        }

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
            vehicleSettingsFactory.updateVehicleSettingManager(getVehicleSettingManagers(), vehicle);
            vehicle.setEditableVehicleSettings(vehicleSettingManagers.get(vehicle.getVehicleID()).associateSettings(vehicle.getVehicleID()));
            // end - added for pagination
                        
            // add a message
            final String summary = MessageUtil.formatMessageString(create ? "vehicle_added" : "vehicle_updated", vehicle.getName());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }
    }
    
    private void batchSave(List<VehicleView> saveItems) {

      Vehicle updateVehicleTemplate = null;
      VehicleView sourceVehicle = saveItems.get(0);
      if (sourceVehicle == null)
          return;
      for (Map.Entry<String, Boolean> entry: getUpdateField().entrySet()) {
          if (entry.getValue().equals(Boolean.TRUE))  {
              if (updateVehicleTemplate == null) {
                  updateVehicleTemplate = new Vehicle();
              }
              if (BeanUtil.propertyExists(sourceVehicle, entry.getKey())) {
                  BeanUtil.copyProperty(sourceVehicle, updateVehicleTemplate, entry.getKey());
              }
          }
      }
      
      for (Map.Entry<Integer, Boolean> entry : selectedMap.entrySet()) {
          if (entry.getValue().equals(Boolean.TRUE)) {
              Integer vehicleID = entry.getKey();
              if (updateVehicleTemplate != null) {
                  updateVehicleTemplate.setVehicleID(vehicleID);
                  vehicleDAO.update(updateVehicleTemplate);
              }

              if (batchEditProductChoice != null && !batchEditProductChoice.equals(ProductType.UNKNOWN) && sourceVehicle.getEditableVehicleSettings() != null) {
                  VehicleView vehicle = new VehicleView();
                  vehicle.setVehicleID(vehicleID);
                  vehicle.setEditableVehicleSettings(getVehicleSettingManagers().get(vehicle.getVehicleID()).associateSettings(vehicle.getVehicleID()));
                  dealWithSpecialSettings(sourceVehicle);
                  vehicleSettingManagers.get(vehicle.getVehicleID()).updateVehicleSettings(vehicle.getVehicleID(),sourceVehicle.getEditableVehicleSettings(),
                                                              this.getUserID(), "portal update", getUpdateField());
                  vehicleSettingsFactory.updateVehicleSettingManager(getVehicleSettingManagers(), vehicle);
              }
          
          }
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

    public List<SelectItem> getWs850DotTypes() {
        return SelectItemUtil.toList(WS850HOSDOTType.class, false);
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
        private String           driverName;
        private String product;
        private String dot;



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

        public String getProduct() {
            return editableVehicleSettings.getProductDisplayName();
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String HosDotTypeName(Integer nr)
        { if(nr==0) return "Light Duty, No HOS";
          if(nr==1) return "Light Duty,Prompt";
          if(nr==2) return "Light Duty, HOS";
            if(nr==3) return "Heavy Duty";
            return " ";
        }

        public String DotVehicleTypeName(Integer nr)
        { if(nr==0) return "Non DOT";
            if(nr==1) return "DOT";
            if(nr==2) return "Prompt for DOT trip";
            return " "; }


        public String getDot() {
            return (getDotVehicleType() == null) ? ((getHosDotType() == null) ? " " : HosDotTypeName(getHosDotType().getConfiguratorSetting())):DotVehicleTypeName( getDotVehicleType().getConfiguratorSetting());

        }

        public void setDot(String dot) {
            this.dot = dot;
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
            return editableVehicleSettings.getProductDisplayName();
        }
        public String getProductTypeDir() {
            if(editableVehicleSettings == null || editableVehicleSettings.getProductType() == null) return ProductType.UNKNOWN.getDescription();
            return editableVehicleSettings.getProductType().getDescription();
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
                Group treegroup = bean.getGroupHierarchy().getGroup(getGroupID());
                if (treegroup == null)
                    group = bean.groupDAO.findByID(getGroupID());
                else {
                    group = new Group(treegroup.getGroupID(), treegroup.getAccountID(), treegroup.getName(), treegroup.getParentID());
                }
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
            if (bean.driverMap == null)
                bean.getDrivers();

            if (driver == null)
                driver = bean.driverMap.get(getDriverID());
            return driver;
        }


        public boolean isSelected() {
            return bean.getSelectedMap().containsKey(getVehicleID()) ? bean.getSelectedMap().get(getVehicleID()) : false; 
        }

        public void setSelected(boolean selected) {
            if (getVehicleID() != null)
                bean.getSelectedMap().put(getVehicleID(), selected);
            
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
            if (getVtype() == null)
                return "";
        	return MessageUtil.getMessageString(getVtype().toString(),bean.getLocale());
        }
        public String getStatusName(){
            if (getStatus() == null)
                return "";
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
        public WS850HOSDOTType getHosDotType(){
            if(editableVehicleSettings instanceof WS850EditableVehicleSettings){
                return WS850HOSDOTType.getFromSetting(((WS850EditableVehicleSettings)editableVehicleSettings).getDotVehicleType());
            }
            return null;
        }
        
        public void setHosDotType(WS850HOSDOTType ws850HosDotType){
            if(editableVehicleSettings instanceof WS850EditableVehicleSettings){
                ((WS850EditableVehicleSettings)editableVehicleSettings).setDotVehicleType(ws850HosDotType.getConfiguratorSetting());
            }
            
        }

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }
    }

/*
 *  pagination   
    // overriding because the pagination doesn't use the filtered list */ 

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



    @Override
    public void setSelectAll(boolean selectAll)
    {
        this.selectAll = selectAll;
    }
    
    @Override
    public boolean isSelectAll() {
        return this.selectAll;
    }

    private List<VehicleIdentifiers> vehicleIdentifiersList;
    private Map<Integer, Boolean> selectedMap = new HashMap<Integer, Boolean>();

    @Override
    public void doSelectAll() {
        selectedMap = new HashMap<Integer, Boolean>();
        if (selectAll == true) {
            for (VehicleIdentifiers vehicleIdentifiers : vehicleIdentifiersList) {
                selectedMap.put(vehicleIdentifiers.getVehicleID(),  Boolean.TRUE);
            }
        }
    }

    @Override
    public void setItems(List<VehicleView> items )
    {
        super.setItems(items);
    }
    
    public void initVehicleIdentifierList(List<VehicleIdentifiers> vehicleIdentifiersList )
    {
        this.vehicleIdentifiersList = vehicleIdentifiersList;
        this.selectAll = Boolean.FALSE;
        selectedMap = new HashMap<Integer, Boolean>();
    }

    public void updateItemSelect(Integer id, Boolean selected) {
        selectedMap.put(id,  selected);
        
    }

    public Map<Integer, Boolean> getSelectedMap() {
        return selectedMap;
    }

    public void setSelectedMap(Map<Integer, Boolean> selectedMap) {
        this.selectedMap = selectedMap;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public DriverJDBCDAO getDriverJDBCDAO() {
        return driverJDBCDAO;
    }

    public void setDriverJDBCDAO(DriverJDBCDAO driverJDBCDAO) {
        this.driverJDBCDAO = driverJDBCDAO;
    }

    public Map<Integer, Driver> getDriverMap() {
        return driverMap;
    }

    public void setDriverMap(Map<Integer, Driver> driverMap) {
        this.driverMap = driverMap;
    }
}
