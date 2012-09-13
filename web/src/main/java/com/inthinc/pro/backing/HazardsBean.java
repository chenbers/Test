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
import org.joda.time.DateTime;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.jdbc.AdminHazardJDBCDAO;
import com.inthinc.pro.dao.jdbc.AdminVehicleJDBCDAO;
import com.inthinc.pro.map.GoogleAddressLookup;
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
    /**
     * 
     */
    private static final long serialVersionUID = -6165871690791113017L;
    //private List<Hazard> hazards;
    private HashMap<Integer, Hazard> hazards;
    private AdminHazardJDBCDAO adminHazardJDBCDAO;
    private AdminVehicleJDBCDAO adminVehicleJDBCDAO;
    private DriverDAO driverDAO;
    private UserDAO userDAO;

    private Hazard item;
    private boolean editing;
    protected List<Hazard> tableData;
    protected List<Hazard> filteredTableData;
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
        //load the world
        loadHazards(-90.0, -180.0, 90.0, 180.0); 
    }
    public void loadHazards(Double lat1, Double lng1, Double lat2, Double lng2) {
        System.out.println("public void loadHazards(Double "+lat1+", Double "+lng1+", Double "+lat2+", Double "+lng2+")");
        List<Hazard> justHazards = adminHazardJDBCDAO.findHazardsByUserAcct(this.getUser(), lat1, lng1, lat2, lng2);
        if(hazards == null){
            hazards = new HashMap<Integer, Hazard>();
        }
        hazards.clear();
        for(Hazard hazard: justHazards){
            hazards.put(hazard.getHazardID(), hazard);
        }
        System.out.println("adminHazardJDBCDAO: "+adminHazardJDBCDAO);
        System.out.println("hazards: "+hazards);
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
            hazard.setDriver(driverDAO.findByID(hazard.getDriverID()));
            hazard.setUser(userDAO.findByID(hazard.getUserID()));
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
        return "adminEditHazard";
    }

    /**
     * Called when the user chooses to edit an item.
     */
    public String edit() {
        editing = true;
        return "adminEditHazard";
    }
    
    public void editListener(ActionEvent event){
        System.out.println("editListener event: "+event);
        Integer hazardIDToEdit = (Integer)event.getComponent().getAttributes().get("hazardID");
        item = hazards.get(hazardIDToEdit);
    }

    /**
     * Called when the user chooses to cancel editing.
     */
    public String cancelEdit() {
        editing = false;
        if (isAdd())
            item = null;
        //TODO: use the following if there were/are complex objects inside the Hazard object ELSE remove
//        if (item != null)
//            item.setOptionsMap(null);

        UIComponent uiComponent = FacesContext.getCurrentInstance().getViewRoot().findComponent("hazards-form");
        FormUtil.resetForm((UIForm) uiComponent);

        return "adminHazards";
    }

    /**
     * Called when the user clicks to save changes when adding or editing.
     */
    public String save() {
        System.out.println("HazardsBean.save() ");
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
            System.out.println("null pointer when trying to find location "+npe);
        }
        item.setLocation(location);
        item.setModified(new Date());
        if (add) {
            System.out.println("hazardsBean add ... item: "+item);
            item.setAccountID(getUser().getPerson().getAccountID());
            item.setHazardID(adminHazardJDBCDAO.create(item));
            
        } else {
            adminHazardJDBCDAO.update(item);
        }

        if (add) {
            Hazard newItem = adminHazardJDBCDAO.findByID(item.getHazardID());
            hazards.put(newItem.getHazardID(), newItem);
            //item = null;
        }
        
     // add a message
        final String summary = MessageUtil.formatMessageString(add ? "hazard_added" : "hazard_updated", item.getLocation());
        final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        context.addMessage(null, message);

        editing = false;

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
        //TODO this is the place for validation
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
        System.out.println("onTypeChange();");
        DateTime startTime = new DateTime(item.getStartTime().getTime());
        DateTime endTime = startTime.plus(item.getType().getDefaultDuration());
        item.setEndTime(endTime.toDate());
        
        item.setRadiusMeters(item.getType().getRadius());
        Integer radiusInUnits = (Integer) item.getRadiusUnits().convertFromMeters(item.getRadiusMeters()).intValue();
        item.setRadiusInUnits(radiusInUnits);
    }
    public void onRadiusChange() {
        item.setRadiusMeters((Double) item.getRadiusUnits().convertToMeters(item.getRadiusInUnits()));
    }
    public void onUnitChange() {
        System.out.println("onUnitChange()");
        item.setRadiusInUnits((Integer) item.getRadiusUnits().convertFromMeters(item.getRadiusMeters()).intValue());
    }
    public String reset() {

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
}
