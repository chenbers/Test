package com.inthinc.pro.backing;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.springframework.beans.BeanUtils;

import com.inthinc.hos.model.HOSOrigin;
import com.inthinc.hos.model.HOSRec;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.rules.HOSRules;
import com.inthinc.hos.rules.RuleSetFactory;
import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.backing.ui.DateRange;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.hessian.exceptions.HessianException;
import com.inthinc.pro.dao.jdbc.FwdCmdSpoolWS;
import com.inthinc.pro.dao.util.HOSUtil;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverName;
import com.inthinc.pro.model.ForwardCommandID;
import com.inthinc.pro.model.ForwardCommandSpool;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.util.DateTimeUtil;
import com.inthinc.pro.table.PageData;
import com.inthinc.pro.util.BeanUtil;
import com.inthinc.pro.util.MessageUtil;

public class HosBean extends BaseBean {
    
    private static Logger logger = Logger.getLogger(HosBean.class);
    
    private static final long serialVersionUID = 1L;

    private Integer driverID;
    private Driver driver;
    private HOSDAO hosDAO;
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;
    private DeviceDAO deviceDAO;
    private FwdCmdSpoolWS fwdCmdSpoolWS;
    private PageData pageData;
    private int page;
    private List<SelectItem> drivers;
    private List<SelectItem> vehicles;
    private DateRange dateRange;
    
    protected HosLogView item;
    protected List<HosLogView> items;
    private boolean               batchEdit;
    private boolean               selectAll;
    private Map<String, Boolean> updateField;
    private String sendLogsMsg;
    
    private String driverName;
    private List<DriverName> driverNameList;
    
    private static final String EDIT_REDIRECT = "pretty:hosEdit";    
    private static final String VIEW_REDIRECT = "pretty:hos";    
    protected final static String BLANK_SELECTION = "&#160;";
//    private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy hh:mm:ss z");

    public HosBean() {
        super();

    }
    
    public List<SelectItem> getDrivers() {
        if (drivers == null) {
            drivers = new ArrayList<SelectItem>();
            SelectItem blankItem = new SelectItem("", BLANK_SELECTION);
            blankItem.setEscape(false);
            drivers.add(blankItem);
            for (Driver d : driverDAO.getAllDrivers(this.getUser().getGroupID())) {
                drivers.add(new SelectItem(d.getDriverID(), d.getPerson().getFullName()));
            }
        }
        sort(drivers);
        return drivers;
    }
    public List<SelectItem> getVehicles() {
        if (vehicles == null) {
            vehicles = new ArrayList<SelectItem>();
            SelectItem blankItem = new SelectItem("", BLANK_SELECTION);
            blankItem.setEscape(false);
            vehicles.add(blankItem);
            for (Vehicle v : vehicleDAO.getVehiclesInGroupHierarchy(this.getUser().getGroupID())) {
                vehicles.add(new SelectItem(v.getVehicleID(), v.getName()));
            }
        }
        sort(vehicles);
        return vehicles;
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

    public void init()
    {
        pageData = new PageData();
        page = 0;
        pageData.initPage(page);
        // use the timezone of the logged on user for date range
        dateRange = new DateRange(getLocale(), getDateTimeZone().toTimeZone());
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
    public List<SelectItem> getStatuses() {
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        for (HOSStatus e : EnumSet.allOf(HOSStatus.class)) {
            if (e.isInternal() || (batchEdit && e == HOSStatus.HOS_PROP_CARRY_14HR))
                continue;
             selectItemList.add(new SelectItem(e,MessageUtil.getMessageString(e.getName())));
        }
        
        return selectItemList;
    }

    public List<SelectItem> getDots() {
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        for (RuleSetType ruleSetType : EnumSet.allOf(RuleSetType.class)) {
           if(ruleSetType != RuleSetType.SLB_INTERNAL && !ruleSetType.isDeprecated() )
               selectItemList.add(new SelectItem(ruleSetType,MessageUtil.getMessageString(ruleSetType.toString())));
        }
        
        return selectItemList;
        
    }
    
    public Integer getDriverID() {
        return driverID;
    }
    public void setDriverID(Integer driverID) {
        setSendLogsMsg(null);
        
        if (this.driverID == null) {
            if (driverID != null) {
                this.driverID = driverID;
                loadDriver(driverID);
                items = null;
            }
            return;
        }
        if (!this.driverID.equals(driverID)) {
            loadDriver(driverID);
            items = null;
        }
        
        this.driverID = driverID;
    }


    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    private void loadDriver(Integer driverID)
    {
        driver = driverDAO.findByID(driverID);
    }
    public HOSDAO getHosDAO() {
        return hosDAO;
    }

    public void setHosDAO(HOSDAO hosDAO) {
        this.hosDAO = hosDAO;
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

    public DeviceDAO getDeviceDAO() {
        return deviceDAO;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }


//    @Override
    public List<HosLogView> getItems() {
        if (items == null) {
            setItems(loadItems());
            initPageData();
        }
        
        return items;
    }
    
    private void initPageData() {
        pageData = new PageData();
        pageData.initPage(items.size());
        page = pageData.getCurrentPage();
    }

    public void setItems(List<HosLogView> items) {
        this.items = items;
    }

    public PageData getPageData() {
        return pageData;
    }

    public void setPageData(PageData pageData) {
        this.pageData = pageData;
    }
    

    public HosLogView getItem() {
        if (item == null)
        {
            batchEdit = false;

            int selected = 0;
            HosLogView selection = null;
            for (HosLogView t : getItems())
                if (t.isSelected())
                {
                    selection = t;
                    selected++;
                    if (selected > 1)
                        break;
                }
            
            if(logger.isTraceEnabled())
                logger.trace("BEGIN getItem: " + selection);
            if (selected == 0)
                item = createAddItem();
            else if (selected == 1)
                item = selection;
            else
            {
                batchEdit = true;
                item = createAddItem();
                List<String> ignoreFields = new LinkedList<String>();
                ignoreFields.add("timeInSec");
                BeanUtil.deepCopy(selection, item, ignoreFields);

                // null out properties that are not common
                for (HosLogView t : getSelectedItems())
                    BeanUtil.compareAndInit(item, t);
            } 
            
            if(logger.isTraceEnabled())
                logger.trace("END getItem: " + item);
        }
        return item;
        
    }

    public void setItem(HosLogView item) {
        this.item = item;
    }
    
    public boolean isBatchEdit() {
        return batchEdit;
    }

    public void setBatchEdit(boolean batchEdit) {
        this.batchEdit = batchEdit;
    }

    public boolean isSelectAll() {
        return selectAll;
    }

    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
    }
    protected List<HosLogView> loadItems() {
        LinkedList<HosLogView> items = new LinkedList<HosLogView>();
        if (getDriverID() == null)
            return items;
        
        Interval interval = dateRange.getInterval();
        List<HOSRecord> plainRecords = hosDAO.getHOSRecords(getDriverID(), interval, false);
        if (plainRecords == null)
            return items;
        for (final HOSRecord rec : plainRecords) {
            if (rec.getStatus() == null || rec.getStatus().isInternal() || !interval.contains(rec.getLogTime().getTime()))
                continue;
            items.add(createLogView(rec));
        }
        return items;
    }
    

    private HosLogView createLogView(HOSRecord hosRecord)
    {
        final HosLogView hosLogView = new HosLogView();
        String[] ignoreProperties = {"timeInSec"};
        BeanUtils.copyProperties(hosRecord, hosLogView, ignoreProperties);
        hosLogView.setSelected(false);
        hosLogView.setDriverName((driver != null) ? driver.getPerson().getFullName() : "");
        return hosLogView;
    }
    

    public class HosLogView extends HOSRecord implements EditItem
    {
        @Column(updateable = false)
        private static final long serialVersionUID = 8372507838051791866L;
        @Column(updateable = false)
        private boolean selected;

        private String driverName;
        
        public HosLogView()
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
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        @Override
        public String getName() {
            // TODO Auto-generated method stub
            return null;
        }

        public Integer getTimeInSec() {
            if (getLogTime() == null || getTimeZone() == null)
                return null;
            DateTime dateTime = new DateTime(getLogTime(), DateTimeZone.forID(getTimeZone().getID()));
            return dateTime.getSecondOfDay();
        }

        public void setTimeInSec(Integer timeInSec) {
            try
            {
            DateTime dateTime = new DateMidnight(getLogTime(), DateTimeZone.forID(getTimeZone().getID())).toDateTime().plusSeconds(timeInSec);
            setLogTime(dateTime.toDate());
            }
            catch (java.lang.NullPointerException e) {
                e.printStackTrace();
            }
            
        }

        public boolean getCanDelete() {
            return getOrigin() != HOSOrigin.DEVICE;
        }

        public boolean getIsWebLogin() {
            return getOrigin() == HOSOrigin.KIOSK;
        }
        
        public String getTimezoneName() {
            return HosBean.this.getTimeZoneDisplayName(getTimeZone());
        }
    }
    
    public String add()
    {
        if (driverID == null)
            return "";
        
        batchEdit = false;
        item = createAddItem();
        item.setSelected(false);
        
        if (items != null)
            for (HosLogView item : getSelectedItems())
                item.setSelected(false);

        return EDIT_REDIRECT;
    }

    public String delete()
    {
        
        List<HosLogView> selected = getSelectedItems();
        if (selected.size() != 1) 
            return VIEW_REDIRECT;   //shouldn't happen
        
        HosLogView delItem = selected.get(0);
        hosDAO.deleteByID(delItem.getHosLogID());
        items.remove(delItem);
        
        return VIEW_REDIRECT;
    }
    
    private HosLogView createAddItem() {
        HOSRecord hosRecord = new HOSRecord();
        hosRecord.setTimeZone(driver.getPerson().getTimeZone());
        hosRecord.setLogTime(new Date());
        hosRecord.setDriverID(driverID);
        hosRecord.setDriverDotType(driver.getDot());
        
        return createLogView(hosRecord);
    }

    public List<HosLogView> getSelectedItems()
    {
        LinkedList<HosLogView> selected = new LinkedList<HosLogView>();
        for (HosLogView t : getItems())
            if (t.isSelected())
                selected.add(t);

        if (selected.size() == 0 && !isAdd())
            selected.add(item);

        return selected;
    }
    
    public boolean isAdd()
    {
        return !isBatchEdit() && (getItem() != null) && (getItem().getId() == null);
    }


    public String batchEdit()
    {
        item = null;
        getItem();

        // take off if nothing was selected
        if (isAdd())
        {
            final FacesContext context = FacesContext.getCurrentInstance();
            final String summary = MessageUtil.getMessageString("adminTable_noneSelected");
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null);
            context.addMessage(null, message);

            item = null;
            return "";
        }

        // select no fields for update
        for (final String key : getUpdateField().keySet())
            updateField.put(key, false);

        return EDIT_REDIRECT;
        
    }

    public Map<String, Boolean> getUpdateField()
    {
        if ((updateField == null) || (updateField.size() == 0))
        {
            if (updateField == null)
                updateField = new HashMap<String, Boolean>();
            if (getItem() != null)
                for (final String name : BeanUtil.getPropertyNames(getItem()))
                    updateField.put(name, false);
        }
        return updateField;
    }

    public String save()
    {
        boolean driverChange = false;
        List<HosLogView> selected = getSelectedItems();
        if ((selected.size() == 0) && isAdd())
            selected.add(item);

        if (batchEdit)
        {
            // get the fields to update
            final LinkedList<String> ignoreFields = new LinkedList<String>();
            for (final String key : updateField.keySet())
                if (!updateField.get(key))
                    ignoreFields.add(key);
                
            if (updateField.get("vehicleID"))
                ignoreFields.remove("vehicleName");

            if (updateField.get("driverID"))
                driverChange = true;

            //we need to validate the item before we copy the properties. 
            if(!validateSaveItem(item))
            {
                return null;
            }
            
            updateVehicleName();
//            if (getUpdateField().get("location")) {
//                updateLatLng();
//            }
            // copy properties
            for (HosLogView t : selected)
                BeanUtil.deepCopy(item, t, ignoreFields);
        }
        else {
            updateVehicleName();
//            updateLatLng();
            if (!item.getDriverID().equals(getDriverID())) {
                driverChange = true;
            }
        }

        // validate
        if (!validate(selected)) {
            return null;
        }

        final boolean add = isAdd();
        try
        {
            doSave(selected, add);
        }
        catch (HessianException e)
        {
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getLocalizedMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            logger.debug("Hessian error while saving", e);
            return null;
        }
        
        if (driverChange) {
            items = null;
        }
        else {
            if (add)
            {
                items.add(item);
            }
            
            // deselect all edited items
            for (HosLogView  item : getSelectedItems())
                item.setSelected(false);
            }

        return VIEW_REDIRECT;
    }
    private void updateVehicleName() {
        if (item != null && item.getVehicleID() != null && item.getVehicleID().intValue() != 0) {
            Vehicle vehicle = vehicleDAO.findByID(item.getVehicleID());
            item.setVehicleName(vehicle.getName());
        }
        
    }
    protected boolean validate(List<HosLogView> saveItems)
    {
        boolean valid = true;
        for (final HosLogView saveItem : saveItems)
        {
            valid = validateSaveItem(saveItem);
            if(!valid)
                break;
        }
        return valid;
    }

    private boolean validateSaveItem(HosLogView log) {
        final FacesContext context = FacesContext.getCurrentInstance();
        boolean valid = true;
        // required Location
        if (log.getLocation() == null || log.getLocation().isEmpty() && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("location")))) {
            valid = false;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("required"), null);
            context.addMessage("edit-form:editHosLog_location", message);
        }
        // future date not allowed
        if (!isBatchEdit() && log.getLogTime().after(new Date())) {
            valid = false;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("hosLog_future_date_not_allowed"), null);
            context.addMessage("edit-form:editHosLog_dateTime", message);
            
        }
        HOSRules rules = RuleSetFactory.getRulesForRuleSetType(log.getDriverDotType());
        if (rules == null || !rules.isValidStatusForRuleSet(log.getStatus())) {
            valid = false;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("hosLog_invalid_status_for_ruleset"), null);
            context.addMessage("edit-form:editHosLog_status", message);
            
        }
        
        if (log.getStatus() == HOSStatus.HOS_PROP_CARRY_14HR) {
            if (log.getVehicleID() == null) {
                valid = false;
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("hosLog_vehicle_required_for_prop_carry_exception"), null);
                context.addMessage("edit-form:editHosLog_vehicle", message);
                
            }
            else {
                LatLng homeOfficeLocation = hosDAO.getVehicleHomeOfficeLocation(item.getVehicleID());
                if (homeOfficeLocation == null) {
                    valid = false;
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("hosLog_home_office_required_for_prop_carry_exception"), null);
                    context.addMessage("edit-form:editHosLog_vehicle", message);
                }
                else {
                    
                    DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
                    int daysBack = RuleSetFactory.getDaysBackForRuleSetType(log.getDriverDotType());
                    Interval queryInterval = DateTimeUtil.getDaysBackInterval(new DateTime(log.getLogTime()), dateTimeZone, daysBack); 
                    List<HOSRecord> hosRecordList = hosDAO.getHOSRecords(driver.getDriverID(), queryInterval, false);
                    Collections.sort(hosRecordList);
                    
                    List<HOSRec> recList = HOSUtil.getRecListFromLogList(hosRecordList, log.getLogTime(), false);
                    log.setLat(new Float(homeOfficeLocation.getLat()));
                    log.setLng(new Float(homeOfficeLocation.getLng()));
                        
                    HOSRec newHOSRec = HOSUtil.mapHOSRecord(log, 0, log.getLogTime(), true);

                    if (!rules.isValidException(newHOSRec, recList)) {
                        valid = false;
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("hosLog_invalid_prop_carrier_exception"), null);
                        context.addMessage("edit-form:editHosLog_status", message);
                        
                    }

                }
            }
        }
        
        return valid;
    }
    
    public String cancelEdit()
    {
        // revert the edit item
        if (!isBatchEdit() && !isAdd())
        {
            final int index = getItems().indexOf(getItem());
            if (index != -1)
                items.set(index, revertItem(item));
        }

        // deselect all edit items
        for (HosLogView  item : getSelectedItems())
            item.setSelected(false);

        return VIEW_REDIRECT;
    }
    
    protected HosLogView revertItem(HosLogView editItem)
    {
        return createLogView(hosDAO.findByID(editItem.getHosLogID()));
    }

    protected void doSave(List<HosLogView> saveItems, boolean create)
    {
        final FacesContext context = FacesContext.getCurrentInstance();
        
        Integer editUserID = getUser().getUserID();

        for (HosLogView log : saveItems)
        {
            log.setEditUserID(editUserID);
            log.setEdited(true);
            if (create)
                log.setHosLogID(hosDAO.create(0l, log));
            else
                hosDAO.update(log);

            // add a message
            final String summary = MessageUtil.formatMessageString(create ? "hosLog_added" : "hosLog_updated", log.getId());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }

    }

    public void doSelectAll()
    {
        for (HosLogView log : getItems())
            log.setSelected(selectAll);
    }

    public String edit()
    {
        selectItem("editID");
        return EDIT_REDIRECT;
    }
    

    protected boolean selectItem(String paramName)
    {
        final Map<String, String> parameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (parameterMap.get(paramName) != null)
        {
            final int editID = Integer.parseInt(parameterMap.get(paramName));
            selectItem(editID);
            return true;
        }
        return false;
    }
    
    protected void selectItem(Integer id)
    {
            for (HosLogView log : getItems())
                log.setSelected(log.getId().equals(id));

            item = null;
            getItem();
            item.setSelected(false);
    }
   

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
        pageData.initPage(page, getItems().size());
    }
    
    public String getSendLogsMsg() {
        return sendLogsMsg;
    }

    public void setSendLogsMsg(String sendLogsMsg) {
        this.sendLogsMsg = sendLogsMsg;
    }

    public Boolean getCanSendLogs() {
        if (this.getDriverID() == null) {
            return false;
        }
        
        Vehicle vehicle = vehicleDAO.findByDriverID(getDriverID());
        if (vehicle == null) {
            return false;
        }

        if (vehicle.getDeviceID() == null) {
            return false;
            
        }
        Device device = deviceDAO.findByID(vehicle.getDeviceID());
        if (device == null) {
            return false;
        }
        return device.isWaySmart() && !device.isGPRSOnly(); 
        
    }
    public void sendLogs() {
        setSendLogsMsg(null);

        if (this.getDriverID() == null) {
            setSendLogsMsg("hosSendLogsToDevice.noDriver");
            return;
        }
        
        Vehicle vehicle = vehicleDAO.findByDriverID(getDriverID());
        if (vehicle == null) {
            setSendLogsMsg("hosSendLogsToDevice.noVehicle");
            return;
        }

        if (vehicle.getDeviceID() == null) {
            setSendLogsMsg("hosSendLogsToDevice.noDevice");
            return;
            
        }
        Device device = deviceDAO.findByID(vehicle.getDeviceID());
        if (device == null) {
            setSendLogsMsg("hosSendLogsToDevice.noDevice");
            return;
            
        }
        List<HOSRecord> recordList = getHosLogsForDriver(driver);
        
        try {
            List<ByteArrayOutputStream > logShipList = HOSUtil.packageLogsToShip(recordList, driver);
            
            for (ByteArrayOutputStream output :  logShipList ) {    
              queueForwardCommand(device, device.getImei(), output.toByteArray(), ForwardCommandID.HOSLOG_SUMMARY);
            }
            setSendLogsMsg("hosSendLogsToDevice.success");
        }
        catch (Exception e) {
            setSendLogsMsg("hosSendLogsToDevice.logShippingError");
            return;
        }
    }
    private List<HOSRecord> getHosLogsForDriver(Driver driver) {
        final RuleSetType dotType = driver.getDot() == null ? RuleSetType.NON_DOT : driver.getDot();

        final int daysBack = dotType.getLogShipDaysBack();
        DateTime currentDate = new DateTime();
        Interval interval = new Interval(currentDate.minusDays(daysBack), currentDate);

        return hosDAO.getHOSRecords(driver.getDriverID(), interval, true);
    }
    
    private void queueForwardCommand(Device device, String address, byte[] data, int command) 
    {
        logger.debug("queueForwardCommand Begin");
        ForwardCommandSpool fcs = new ForwardCommandSpool(data, command, address);
        if (fwdCmdSpoolWS.add(device, fcs) == -1)
            throw new ProDAOException("Iridium Forward command spool failed.");
                
    }

    public FwdCmdSpoolWS getFwdCmdSpoolWS() {
        return fwdCmdSpoolWS;
    }

    public void setFwdCmdSpoolWS(FwdCmdSpoolWS fwdCmdSpoolWS) {
        this.fwdCmdSpoolWS = fwdCmdSpoolWS;
    }

    public void getDriversForSuggestionBox(){
        driverNameList = driverDAO.getDriverNames(this.getUser().getGroupID());
    }

    public List<DriverName> autocomplete(Object suggest) {
        String pref = (String)suggest;
        ArrayList<DriverName> result = new ArrayList<DriverName>();
        getDriversForSuggestionBox();
        for(DriverName driver :driverNameList){
            String name =driver.getDriverName();
            if (name != null && name.toLowerCase().contains(pref.toLowerCase()) || "".equals(pref))
            {
                result.add(driver);
            }
        }
        Collections.sort(result);
        return result;
    }
    public String getDriverName() {
        return driverName;
    }
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}

    
