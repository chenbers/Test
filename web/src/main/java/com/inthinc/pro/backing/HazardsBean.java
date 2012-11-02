package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.ajax4jsf.model.KeepAlive;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.jdbc.AdminHazardJDBCDAO;
import com.inthinc.pro.dao.jdbc.AdminVehicleJDBCDAO;
import com.inthinc.pro.dao.jdbc.FwdCmdSpoolWS;
import com.inthinc.pro.map.GoogleAddressLookup;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.ForwardCommandID;
import com.inthinc.pro.model.ForwardCommandSpool;
import com.inthinc.pro.model.Hazard;
import com.inthinc.pro.model.HazardStatus;
import com.inthinc.pro.model.HazardType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementLengthType;
import com.inthinc.pro.model.NoAddressFoundException;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.util.FormUtil;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.SelectItemUtil;

@KeepAlive
public class HazardsBean extends BaseBean {
    private static Logger logger = Logger.getLogger(HazardsBean.class);

    private static final long serialVersionUID = -6165871690791113017L;
    //private List<Hazard> hazards;
    private HashMap<Integer, Hazard> hazards;
    private AdminHazardJDBCDAO adminHazardJDBCDAO;
    private AdminVehicleJDBCDAO adminVehicleJDBCDAO;
    private DriverDAO driverDAO;
    private UserDAO userDAO;
    private static FwdCmdSpoolWS fwdCmdSpoolWS;

    private Hazard item;
    private boolean editing;
    private boolean defaultRadius = true;
    private boolean defaultExpTime = true;
    protected List<Hazard> tableData;
    protected List<Hazard> filteredTableData;
    private String sendHazardMsg;
    static final List<String> AVAILABLE_COLUMNS;
    static {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("region");
        AVAILABLE_COLUMNS.add("location");
        AVAILABLE_COLUMNS.add("type");
        AVAILABLE_COLUMNS.add("status");
        AVAILABLE_COLUMNS.add("startDate");
        AVAILABLE_COLUMNS.add("endDate");
        AVAILABLE_COLUMNS.add("modifiedBy");
    }

    public void loadHazards(){
        //load the world?
        loadHazards(-90.0, -180.0, 90.0, 180.0); 
    }
    public void loadHazards(Double lat1, Double lng1, Double lat2, Double lng2) {
        logger.debug("public void loadHazards(Double "+lat1+", Double "+lng1+", Double "+lat2+", Double "+lng2+")");
        List<Hazard> justHazards = adminHazardJDBCDAO.findHazardsByUserAcct(this.getUser(), lat1, lng1, lat2, lng2);
        if(hazards == null){
            hazards = new HashMap<Integer, Hazard>();
        }
        hazards.clear();
        for(Hazard hazard: justHazards){
            hazards.put(hazard.getHazardID(), hazard);
        }
        
        logger.debug("adminHazardJDBCDAO: "+adminHazardJDBCDAO);
        logger.debug("hazards: "+hazards);
        if (hazards.isEmpty())
            hazards = new HashMap<Integer,Hazard>();
    }

    public List<SelectItem> getHazardTypeSelectItems(){
        return SelectItemUtil.toList(HazardType.class, false);
    }
    public List<SelectItem> getHazardRadiusTypeSelectItems(){
        return SelectItemUtil.toList(MeasurementLengthType.class, false);
    }
    public Map<Integer, Hazard> getHazards() {
        if (hazards == null) {
            loadHazards();
        }
        return hazards;
    }
    public void initTableData(){
        for(Integer key: getHazards().keySet()){
            //set driver display value
            Hazard hazard = getHazards().get(key);
            if(hazard.getDriver() == null)
                hazard.setDriver(driverDAO.findByID(hazard.getDriverID()));
            if(hazard.getUser() == null)
                hazard.setUser(userDAO.findByID(hazard.getUserID()));
            if(hazard.getState() == null)
                hazard.setState(States.getStateById(hazard.getStateID()));
        }
    }
    public List<Hazard> getTableData() {
        initTableData();
        return new ArrayList<Hazard>(getHazards().values());
    }
    public List<Vehicle> getSendToVehiclesList(){
        Long distance = MeasurementLengthType.ENGLISH_MILES.convertToMeters(200).longValue();
        List<Vehicle> results = new ArrayList<Vehicle>();
        if(item !=null && item.getLat() != null && item.getLng() != null){
            LatLng location = new LatLng(item.getLat(), item.getLng());
            results = adminVehicleJDBCDAO.findVehiclesByAccountWithinDistance(getAccountID(), distance, location);
        }
        return results;
    }
    
    /**
     * Called when the user chooses to add an item.
     */
    public String add() {
        item = new Hazard();
        item.setCreated(new Date());
        item.setStatus(HazardStatus.ACTIVE);
        editing = true;
        defaultRadius = true;
        defaultExpTime = true;
        return "adminEditHazard";
    }

    /**
     * Called when the user chooses to edit an item.
     */
    public String edit() {
        editing = true;
        defaultRadius = (item.getRadiusMeters() == item.getType().getRadius());
        DateTime startTime = new DateTime(item.getStartTime().getTime());
        DateTime defaultEndTime = startTime.plus(item.getType().getDefaultDuration());
        defaultExpTime = (item.getEndTime().equals(defaultEndTime));
        return "adminEditHazard";
    }
    
    public void editListener(ActionEvent event){
        logger.debug("editListener event: "+event);
        Integer hazardIDToEdit = (Integer)event.getComponent().getAttributes().get("hazardID");
        item = hazards.get(hazardIDToEdit);
    }

    /**
     * Called when the user chooses to cancel editing.
     */
    public String cancelEdit() {
        editing = false;
        defaultRadius = true;
        defaultExpTime = true;
        if (isAdd())
            item = null;
        //TODO: use the following if there were/are complex objects inside the Hazard object ELSE remove
//        if (item != null)
//            item.setOptionsMap(null);

        UIComponent uiComponent = FacesContext.getCurrentInstance().getViewRoot().findComponent("hazards-form");
        FormUtil.resetForm((UIForm) uiComponent);

        return "adminHazards";
    }
    public List<Device> findDevicesInRadius() {
        List<Device> results = new ArrayList<Device>();
        List<Vehicle> vehicles = adminVehicleJDBCDAO.findVehiclesByAccountWithinDistance(getAccountID(), 200l, new LatLng(this.item.getLat(), this.item.getLng()));
        for(Vehicle vehicle: vehicles) {
            results.add(vehicle.getDevice());
        }
        return results;
    }
    
    /**
     * Sends the given Hazard to Devices in range, in this account.
     * Device expecting the following order of parameters:
     * packet size - short (2 byte)
     * rh id - integer (4 byte)
     * type - byte
     * location - compressed lat/long (6 byte)
     * radius - unsigned integer (4 byte)  [meters]
     * start time - time_t (4 byte)
     * end time - time_t (4 byte)
     * details - string (60 char)
     * @param hazard the Hazard Object to send
     */
    public void sendHazard(Hazard hazard){
        try {
            for(Device device: findDevicesInRadius()){
                if(device.getStatus() == DeviceStatus.ACTIVE && device.isWaySmart()){
                    logger.debug("about to send to device: "+device);
                    queueForwardCommand(device, device.getImei(), hazard.toByteArray(), ForwardCommandID.NEW_ROAD_HAZARD);
                    logger.debug("sent to device device.name: "+device.getName());
                }
            }
            setSendHazardMsg("hazardSendToDevice.success");
        } catch (Exception e) {
            logger.error("e: "+e);
            setSendHazardMsg("haazardSendToDevice.error");
            return;
        }
    }
    public static void sendHazardToDevice(Hazard hazard, Device device) {
        queueForwardCommand(device, device.getImei(), hazard.toByteArray(), ForwardCommandID.NEW_ROAD_HAZARD);
    }
    private static void queueForwardCommand(Device device, String address, byte[] data, int command) {
        logger.debug("queueForwardCommand Begin");
        ForwardCommandSpool fcs = new ForwardCommandSpool(data, command, address);
        int addToQueue = fwdCmdSpoolWS.add(device, fcs);
        logger.debug("addToQueue: "+addToQueue);
        if (addToQueue == -1)
            throw new ProDAOException("Iridium Forward command spool failed.");
    }
    
    /**
     * Called when the user clicks to save changes when adding or editing.
     */
    public String save() {
        logger.debug("HazardsBean.save() ");
        // validate
        if (!validate())
            return null;

        final boolean add = isAdd();

        final FacesContext context = FacesContext.getCurrentInstance();
        GoogleAddressLookup gal = new GoogleAddressLookup();
        String location = new String();
        try {
            location = gal.getAddress(new LatLng(item.getLat(), item.getLng()));
        } catch (NoAddressFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NullPointerException npe){
            logger.warn("null pointer when trying to find location "+npe);
        }
        item.setLocation(location);
        item.setModified(new Date());
        if (add) {
            logger.debug("hazardsBean add ... item: "+item);
            item.setAccountID(getUser().getPerson().getAccountID());
            item.setHazardID(adminHazardJDBCDAO.create(item.getAccountID(),item));

            Hazard newItem = adminHazardJDBCDAO.findByID(item.getHazardID());
            hazards.put(newItem.getHazardID(), newItem);
        } else {
            adminHazardJDBCDAO.update(item);
        }
        sendHazard(item);
        
     // add a message
        final String summary = MessageUtil.formatMessageString(add ? "hazard_added" : "hazard_updated", item.getLocation());
        final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        context.addMessage(null, message);

        editing = false;
        defaultRadius = true;
        defaultExpTime = true;

        return "adminHazards";
    }

    /**
     * Called when the user chooses to delete one or more selected items.
     */
    public void delete() {
        adminHazardJDBCDAO.deleteByID(item.getHazardID());

        // add a message
        final String summary = MessageUtil.formatMessageString("hazard_deleted", item.getLocation());
        final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        final FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, message);

        hazards.remove(item);
        item = null;
    }

    public String getPublishInfo() {
//        Account account = getAccountDAO().findByID(getAccountID());
//        Date lastPublishDate = (account.getZonePublishDate() == null) ? new Date(0) : account.getZonePublishDate();
//        DateTimeFormatter fmt = DateTimeFormat.forPattern(MessageUtil.getMessageString("dateTimeFormat", getLocale())).withLocale(getLocale());
//        return MessageUtil.formatMessageString("ZonesPublishInfo", fmt.print(lastPublishDate.getTime()));
        //TODO: determine if there is a corollary here... possibly something like "this zone has been pushed to 17 vehicles over it's life of 5 days"???
        return "determine if there is a corollary here... possibly something like this zone has been pushed to 17 vehicles over it's life of 5 days???";
        }

    public int getHazardsCount() {
        return getHazards().size();
    }

    public Integer getItemID() {
        if (getItem() != null)
            return item.getHazardID();
        return null;
    }

    public void setItemID(Integer itemID) {
        item = hazards.get(itemID);
    }

    public Hazard getItem() {
        if(item == null){
            if(getHazards().isEmpty()){
                item = new Hazard();
                hazards.put(null,item);
            }
            item = hazards.get(0);
        }
        return item;
    }

    /**
     * @return Whether the current edit operation is an item add.
     */
    public boolean isAdd() {
        return (item != null) && (item.getHazardID() == null);
    }

    public boolean isEditing() {
        return editing;
    }

    /**
     * Perform custom validation on the list of items to save. If invalid, messages may be displayed via code similar to:
     * 
     * <pre>
     * final String summary = MessageUtil.getMessageString(&quot;error_message&quot;);
     * final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
     * context.addMessage(&quot;my-form:component-id&quot;, message);
     * </pre>
     * 
     * @return Whether the edit item passed validation.
     */
    private boolean validate() {
        final FacesContext context = FacesContext.getCurrentInstance();
        if(item != null){
            boolean startsBeforeItEnds = item.getStartTime().before(item.getEndTime());
            if(!startsBeforeItEnds){
                final String summary = MessageUtil.getMessageString("hazard_endBeforeStart");
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                context.addMessage(null, message);
                return false;
            }
            boolean validRadius = item.getRadiusMeters()!=null && item.getRadiusMeters() > 0;
            if(!validRadius){
                final String summary = MessageUtil.getMessageString("hazard_needValidRadius");
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                context.addMessage(null, message);
                return false;
            }
            
        }
        return true;
    }
    public void onTypeChange() {
        logger.debug("onTypeChange();");
        DateTime startTime = new DateTime(item.getStartTime().getTime());
        Date newEndTime =        (item.getType()==null)?null: startTime.plus(item.getType().getDefaultDuration()).toDate();
        Double newRadiusMeters = (item.getType()==null)?null: item.getType().getRadius();
        Integer radiusInUnits =  (item.getType()==null)?null: (Integer) item.getRadiusUnits().convertFromMeters(item.getRadiusMeters()).intValue();

        if(defaultExpTime){
            item.setEndTime(newEndTime);
        }
        if(defaultRadius){
            item.setRadiusMeters(newRadiusMeters);
            item.setRadiusInUnits(radiusInUnits);
        }
    }
    
    public void onExpTimeChangeListener(ActionEvent event){
        logger.debug("onExpTimeChangeListener event: "+event);
        defaultExpTime = false;
    }
    public void onExpTimeChange() {
        logger.debug("onExpTimeChange");
        defaultExpTime = false;
    }
    public void onRadiusChange() {
        logger.debug("onRadiusChange() ");
        defaultRadius = false;
        item.setRadiusMeters((Double) item.getRadiusUnits().convertToMeters(item.getRadiusInUnits()));
    }
    public void onUnitChange() {
        logger.debug("onUnitChange()");
        if(item.getRadiusMeters() !=null){
            item.setRadiusInUnits((Integer) item.getRadiusUnits().convertFromMeters(item.getRadiusMeters()).intValue());
        }
    }
    public String reset() {
        defaultRadius = true;
        defaultExpTime = true;
        if (isAdd()) {
            item = new Hazard();
            item.setCreated(new Date());
            item.setUserID(getProUser().getUser().getUserID());
        } else {
            hazards.remove(item);
            item = adminHazardJDBCDAO.findByID(item.getHazardID());
            hazards.put(item.getHazardID(), item);
        }
        
        return "adminEditHazard";
    }
    public AdminHazardJDBCDAO getAdminHazardJDBCDAO() {
        return adminHazardJDBCDAO;
    }
    public void setAdminHazardJDBCDAO(AdminHazardJDBCDAO adminHazardJDBCDAO) {
        this.adminHazardJDBCDAO = adminHazardJDBCDAO;
    }
    public void setHazards(HashMap<Integer, Hazard> hazards) {
        this.hazards = hazards;
    }
    public void setItem(Hazard item) {
        this.item = item;
    }
    public void setEditing(boolean editing) {
        this.editing = editing;
    }
    
    //placeholders for message if needed
    public String getMessage() {
        return "";
    }
    public boolean isShowMessage() {
        return false;
    }
    public DriverDAO getDriverDAO() {
        return driverDAO;
    }
    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }
    public UserDAO getUserDAO() {
        return userDAO;
    }
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    public AdminVehicleJDBCDAO getAdminVehicleJDBCDAO() {
        return adminVehicleJDBCDAO;
    }
    public void setAdminVehicleJDBCDAO(AdminVehicleJDBCDAO adminVehicleJDBCDAO) {
        this.adminVehicleJDBCDAO = adminVehicleJDBCDAO;
    }
    public String getSendHazardMsg() {
        return sendHazardMsg;
    }
    public void setSendHazardMsg(String sendHazardMsg) {
        this.sendHazardMsg = sendHazardMsg;
    }
    public FwdCmdSpoolWS getFwdCmdSpoolWS() {
        return fwdCmdSpoolWS;
    }
    public void setFwdCmdSpoolWS(FwdCmdSpoolWS fwdCmdSpoolWS) {
        this.fwdCmdSpoolWS = fwdCmdSpoolWS;
    }
}
