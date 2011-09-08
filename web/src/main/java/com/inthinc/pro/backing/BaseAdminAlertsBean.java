package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.inthinc.pro.backing.ui.AutocompletePicker;
import com.inthinc.pro.backing.ui.ListPicker;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.DriverName;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.VehicleName;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.util.BeanUtil;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.MiscUtil;
import com.inthinc.pro.util.SelectItemUtil;

@SuppressWarnings("serial")
public abstract class BaseAdminAlertsBean<T extends BaseAdminAlertsBean.BaseAlertView> extends BaseAdminBean<T> implements PersonChangeListener
{
    protected PersonDAO         personDAO;
    protected DriverDAO         driverDAO;
    protected VehicleDAO        vehicleDAO;
    private String              assignType;
    private List<SelectItem>    allVehicles;
    private List<SelectItem>    allDrivers;
    private ListPicker          assignPicker;
    private AutocompletePicker  peoplePicker;
    private AutocompletePicker  escalationPeoplePicker;
    private AutocompletePicker  escalationEmailPicker;
    private T                   oldItem;

    private List<Person>        peopleInGroupHierarchy = new ArrayList<Person>();
    
    private String searchKeyword;
    
    @Override
    public String toString() {
        return "BaseAdminAlertsBean: [assignType="+assignType+", oldItem="+oldItem+", this.getClass()="+this.getClass()+"]";
    }
    public void setPersonDAO(PersonDAO personDAO)
    {
        this.personDAO = personDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }

    public String getAssignType()
    {
        return assignType;
    }
    
    public void ownerChangedAction() {
        
    	assignPicker = null;
    	item.setGroupIDs(null);
    	item.setVehicleIDs(null);
    	item.setDriverIDs(null);
    	
    	User owner = determineOwner();
    	
        setNewGroupIDs(owner.getGroupID());
        setNewVehicleIDs(owner.getGroupID());
        setNewDriverIDs(owner.getGroupID());
    }
    private User determineOwner(){
        Integer ownerID = item == null || item.getUserID() == null ? getUserID() : item.getUserID();
        User owner = null;
        if (!ownerID.equals(getUserID()))
            owner = userDAO.findByID(ownerID);
        else owner = getUser();
        return owner;
    }
    private void setNewGroupIDs(Integer groupID){
        List<Integer> groupIDList = getGroupHierarchy().getSubGroupIDList(groupID);
        List<Integer> selectedGroupIDs = (item.getGroupIDs() == null) ? null : new ArrayList<Integer>(item.getGroupIDs());
        List<Integer> newGroupIDs = new ArrayList<Integer>();
        for (Integer group : groupIDList){
            if(selectedGroupIDs.contains(group)){
                newGroupIDs.add(group);
            }
        }
        item.setGroupIDs(newGroupIDs);

    }
    private void setNewVehicleIDs(Integer groupID){
        List<Integer> selectedVehicleIDs = (item.getVehicleIDs() == null) ? null : new ArrayList<Integer>(item.getVehicleIDs());
        List<Integer> newVehicleIDs = new ArrayList<Integer>();
        final List<VehicleName> vehicles = vehicleDAO.getVehicleNames(groupID);
        for (final VehicleName vehicle : vehicles) {
            if(selectedVehicleIDs.contains(vehicle.getVehicleID())){
                newVehicleIDs.add(vehicle.getVehicleID());
            }
        }
        item.setVehicleIDs(newVehicleIDs);
    }
    private void setNewDriverIDs(Integer groupID){
        List<Integer> selectedDriverIDs = (item.getDriverIDs() == null) ? null : new ArrayList<Integer>(item.getDriverIDs());
        List<Integer> newDriverIDs = new ArrayList<Integer>();
        final List<DriverName> drivers = driverDAO.getDriverNames(groupID);
        for (final DriverName driver : drivers) {
            if(selectedDriverIDs.contains(driver.getDriverID())){
                newDriverIDs.add(driver.getDriverID());
            }
        }
        item.setDriverIDs(newDriverIDs);

    }
    public void setAssignType(String assignType)
    {
        this.assignType = assignType;
         if (assignPicker != null)
            assignPicker.setPickFrom(getAssignPickFrom());
    }
    private AssignTypeStrategy getAssignTypeStrategy(){
        if ((assignType == null) || "groups".equals(assignType)){
            return new GroupAssignTypeStrategy();
        }
        else if ("vehicleTypes".equals(assignType))
        {
            return new VehicleTypeAssignTypeStrategy();
        }
        else if ("vehicles".equals(assignType))
        {
            return new VehicleAssignTypeStrategy();
        }
        else if ("drivers".equals(assignType))
        {
            return new DriverAssignTypeStrategy();
        }
        return new GroupAssignTypeStrategy();

    }
    public ListPicker getAssignPicker()
    {
        if (assignPicker == null)
            assignPicker = new ListPicker(getAssignPickFrom(), getAssignPicked());
        return assignPicker;
    }

    private List<SelectItem> getAssignPickFrom()
    {
        User owner = determineOwner();
        final List<SelectItem> pickFrom = getAssignTypeStrategy().getPickFromList(owner.getGroupID());
       
        return pickFrom;
    }

    protected List<SelectItem> getAllVehicles()
    {
        if ((allVehicles == null))
        {
            allVehicles = getAllVehicles(getUser().getGroupID());
        }
        return allVehicles;
     }
    protected List<SelectItem> getAllVehicles(Integer groupID)
    {
        List<SelectItem> groupVehicles = new ArrayList<SelectItem>();
        List<VehicleName> vehicles = vehicleDAO.getVehicleNames(groupID);
//        vehicles = filterVehicles(vehicles);
        for (final VehicleName vehicle : vehicles) {

            groupVehicles.add(new SelectItem("vehicle" + vehicle.getVehicleID(), vehicle.getVehicleName()));
        }
        MiscUtil.sortSelectItems(groupVehicles);
        return groupVehicles;
    }

    protected List<SelectItem> getAllDrivers()
    {
        if ((allDrivers == null) && (driverDAO != null))
        {
            allDrivers = getAllDrivers(getUser().getGroupID());
        }
        return allDrivers;
    }
    protected List<SelectItem> getAllDrivers(Integer groupID)
    {
        List<DriverName> drivers = driverDAO.getDriverNames(groupID);
        
        List<SelectItem> groupDrivers = new ArrayList<SelectItem>(drivers.size());
        for (final DriverName driver : drivers)
            groupDrivers.add(new SelectItem("driver" + driver.getDriverID(), driver.getDriverName()));
        MiscUtil.sortSelectItems(groupDrivers);
        
        return groupDrivers;
    }
   public List<Person> getPeopleInGroupHierarchy() {
       return getPeopleInGroupHierarchy(false);
    }
   public List<Person> getPeopleInGroupHierarchy(boolean forceUpdate) {
       if( peopleInGroupHierarchy.size() == 0 || forceUpdate ) {
           peopleInGroupHierarchy = personDAO.getPeopleInGroupHierarchy(getTopGroup().getGroupID());            
       }
       
       return peopleInGroupHierarchy;
   }
 
    private List<SelectItem> getAssignPicked()
    {
        final LinkedList<SelectItem> picked = new LinkedList<SelectItem>();
        if (getItem().getGroupIDs() != null)
            for (final Group group : getAllGroups())
                if (getItem().getGroupIDs().contains(group.getGroupID()))
                    picked.add(new SelectItem("group" + group.getGroupID(), group.getName()));

        if (getItem().getVehicleTypes() != null)
            for (final VehicleType type : getItem().getVehicleTypes())
                picked.add(new SelectItem(type.toString(), MessageUtil.getMessageString("editAlerts_" + type.toString().toLowerCase() + "Vehicles")));

        if ((getItem().getVehicleIDs() != null) && (getAllVehicles() != null))
            for (final SelectItem vehicle : getAllVehicles())
                if (getItem().getVehicleIDs().contains(new Integer(vehicle.getValue().toString().substring(7))))
                    picked.add(vehicle);

        if ((getItem().getDriverIDs() != null) && (getAllDrivers() != null))
            for (final SelectItem driver : getAllDrivers())
                if (getItem().getDriverIDs().contains(new Integer(driver.getValue().toString().substring(6))))
                    picked.add(driver);

        return picked;
    }

    public AutocompletePicker getPeoplePicker()
    {
        RedFlagLevel severityLevel = (this.oldItem instanceof RedFlagAlert)?((RedFlagAlert)this.oldItem).getSeverityLevel():null;
        if (peoplePicker == null || (peoplePicker.size() < 1) || peoplePicker.isOutdated())
        {
            final ArrayList<SelectItem> allUsers = new ArrayList<SelectItem>(getPeopleInGroupHierarchy().size());
            boolean forceUpdate = peoplePicker == null || peoplePicker.isOutdated();
            for (final Person person : getPeopleInGroupHierarchy(forceUpdate)) {
                //only add users if they have values for the severity level of this alert
                if(   (RedFlagLevel.INFO.equals(severityLevel)     && person.getInfo() != null && person.getInfo()>0)
                   || (RedFlagLevel.WARNING.equals(severityLevel)  && person.getWarn() != null && person.getWarn()>0)
                   || (RedFlagLevel.CRITICAL.equals(severityLevel) && person.getCrit() != null && person.getCrit()>0)) {
                    allUsers.add(new SelectItem(person.getPersonID(), person.getFirst() + " " + person.getLast()));
                }
            }
            MiscUtil.sortSelectItems(allUsers);

            final ArrayList<SelectItem> notifyPeople = getNotifyPicked();

            peoplePicker = new AutocompletePicker(allUsers, notifyPeople);
        }
        return peoplePicker;
    }
    public AutocompletePicker getEscalationEmailPicker() {
        if (escalationEmailPicker == null)
        {
            final ArrayList<SelectItem> allUsers = new ArrayList<SelectItem>(getPeopleInGroupHierarchy().size());
            for (final Person person : getPeopleInGroupHierarchy()) {
                if(null != person.getPriEmail() && !"".equals(person.getPriEmail()))
                    allUsers.add(new SelectItem(person, person.getFullNameWithPriEmail().replaceAll(" +", " ")));
            }
            MiscUtil.sortSelectItems(allUsers);

            escalationEmailPicker = new AutocompletePicker(allUsers);
        }
        return escalationEmailPicker;
    }

    public AutocompletePicker getEscalationPeoplePicker() {
        if (escalationPeoplePicker == null || escalationPeoplePicker.isOutdated()) {
            final ArrayList<SelectItem> allUsers = new ArrayList<SelectItem>(getPeopleInGroupHierarchy().size());
            for (final Person person : getPeopleInGroupHierarchy()) {
                if (null != person.getPriPhone() && !"".equals(person.getPriPhone()))
                    allUsers.add(new SelectItem(person, person.getFullNameWithPriPhone()));
            }
            MiscUtil.sortSelectItems(allUsers);

            // TODO: refactor: when this method is called, getEscalationPicked() is returning nothing even when there ARE phoneNumbers in the list already. 
            // NOTE: getEscalationPicked() returns the correct value LATER (when called by getItem())
            ArrayList<SelectItem> picked = getEscalationPicked();
            escalationPeoplePicker = new AutocompletePicker(allUsers, picked);
        }
        return escalationPeoplePicker;
    }
    
   @Override
    public void resetList() {
    	peoplePicker = null; //Reset the list of people to be assigned to an alert. 
    	escalationPeoplePicker = null;
    	allVehicles = null;
    	allDrivers = null;
    	super.resetList();
    }

    private ArrayList<SelectItem> getNotifyPicked()
    {
        final ArrayList<SelectItem> notifyPeople = new ArrayList<SelectItem>();
        if (getItem().getNotifyPersonIDs() != null)
        {
            for (final Integer id : getItem().getNotifyPersonIDs())
            {
                //final User user = userDAO.findByID(id);
                final Person person = personDAO.findByID(id);
                if (!isPersonDeleted(person))
                    notifyPeople.add(new SelectItem(person.getPersonID(), person.getFirst() + ' ' + person.getLast()));
            }
            MiscUtil.sortSelectItems(notifyPeople);
        }
        return notifyPeople;
    }

    private ArrayList<SelectItem> getEscalationPicked() {
        final ArrayList<SelectItem> escPicked = new ArrayList<SelectItem>();
        if (getItem().getVoiceEscalationPersonIDs() != null) {
            for (final Integer id : getItem().getVoiceEscalationPersonIDs()) {
                // final User user = userDAO.findByID(id);
                final Person person = personDAO.findByID(id);
                if (!isPersonDeleted(person))
                    escPicked.add(new SelectItem(person.getPersonID(), person.getFullNameWithPriPhone()));
            }
        }
        return escPicked;
    }

    private boolean isPersonDeleted(Person person) {
        if (person == null)
            return true;

        if (person.getStatus() == null) 
            return !((person.getUser() != null && person.getUser().getStatus() != null && !person.getUser().getStatus().equals(Status.DELETED))
                    || (person.getDriver() != null && person.getDriver().getStatus() != null && !person.getDriver().getStatus().equals(Status.DELETED)));      

        return person.getStatus().equals(Status.DELETED);
    }
    @Override
    public void personListChanged()
    {
        assignPicker = null;
        peoplePicker = null;
        escalationPeoplePicker = null;
    }

    @Override
    public T getItem()
    {
        final T item = super.getItem();
        if (item != oldItem)
        {
            oldItem = item;
            getAssignPicker().setPicked(getAssignPicked());
            getAssignPicker().setPickFrom(getAssignPickFrom());
            getPeoplePicker().setPicked(getNotifyPicked());
            getEscalationPeoplePicker().setPicked(getEscalationPicked());
        }
        if ((item.getDayOfWeek() == null) || (item.getDayOfWeek().size() != 7))
        {
            final ArrayList<Boolean> dayOfWeek = new ArrayList<Boolean>();
            for (int i = 0; i < 7; i++)
                dayOfWeek.add(false);
            item.setDayOfWeek(dayOfWeek);
        }
        return item;
    }
    
    @Override
    public String batchEdit()
    {
        String returnString = super.batchEdit();
        if(item != null)
        {
            item.setAnytime(isAnytime(getItem()));
        }
        
        // null out properties that are not common
        for (T t : getSelectedItems())
            BeanUtil.compareAndInitBoolList(getItem().getDayOfWeek(), t.getDayOfWeek());
            
        return returnString;
    }

    @Override
    public String cancelEdit()
    {
        getAssignPicker().setPicked(getAssignPicked());
        getPeoplePicker().setPicked(getNotifyPicked());
        getEscalationPeoplePicker().setPicked(getEscalationPicked());
        return super.cancelEdit();
    }

    @Override
    public String save()
    {
        // if batch-changing assignment, change all assignment types
        final boolean assignTo = Boolean.TRUE.equals(getUpdateField().get("assignTo"));
        getUpdateField().put("groupIDs", assignTo);
        getUpdateField().put("vehicleTypes", assignTo);
        getUpdateField().put("vehicleIDs", assignTo);
        getUpdateField().put("driverIDs", assignTo);
        getUpdateField().put("userID", assignTo);

        // clear previous IDs
        getItem().setGroupIDs(new ArrayList<Integer>());
        getItem().setVehicleIDs(new ArrayList<Integer>());
        getItem().setDriverIDs(new ArrayList<Integer>());
        getItem().setVehicleTypes(new ArrayList<VehicleType>());

        // set group, vehicle type, vehicle and driver IDs
        for (final SelectItem item : getAssignPicker().getPicked())
        {
            // group
            if (item.getValue().toString().startsWith("group"))
            {
                final Integer id = new Integer(item.getValue().toString().substring(5));
                getItem().getGroupIDs().add(id);
            }
            // vehicle
            else if (item.getValue().toString().startsWith("vehicle"))
            {
                final Integer id = new Integer(item.getValue().toString().substring(7));
                getItem().getVehicleIDs().add(id);
            }
            // driver
            else if (item.getValue().toString().startsWith("driver"))
            {
                final Integer id = new Integer(item.getValue().toString().substring(6));
                getItem().getDriverIDs().add(id);
            }
            // vehicle type
            else
            {
                final VehicleType type = VehicleType.valueOf(item.getValue().toString());
                getItem().getVehicleTypes().add(type);
            }
        }

        // set notify user IDs
        final ArrayList<Integer> userIDs = new ArrayList<Integer>(getPeoplePicker().getPicked().size());
        
        for (final SelectItem item : getPeoplePicker().getPicked())
            userIDs.add((Integer) item.getValue());
        
        getItem().setNotifyPersonIDs(userIDs);
        if (!isBatchEdit() ||(isBatchEdit() && getUpdateField().get("escalationPersonIDs"))){
            // set notify user IDs
            final ArrayList<Integer> escalationUserIDs = new ArrayList<Integer>(getEscalationPeoplePicker().getPicked().size());
            for (final SelectItem item : getEscalationPeoplePicker().getPicked()) {
                if(item.getValue() instanceof Person)
                    escalationUserIDs.add((Integer) ((Person)item.getValue()).getPersonID());
                else if(item.getValue() instanceof Integer)
                    escalationUserIDs.add((Integer)item.getValue());
            }
            getItem().setEscalationPersonIDs(escalationUserIDs);
        }
        
        if (!isBatchEdit()) {
            for (SelectItem selectItem : getAllGroupUsers()) {
                if (selectItem.getValue().equals(item.getUserID())) {
                    getItem().setFullName(selectItem.getLabel());
                }
            }
        }

        return super.save();
    }
    
    @Override
    protected boolean validateSaveItem(T saveItem)
    {
        logger.debug("protected boolean validateSaveItem(T "+saveItem+")");
        final FacesContext context = FacesContext.getCurrentInstance();

        boolean valid = true;
        
        // at least one day chosen
        if(!isBatchEdit() || (isBatchEdit() && getUpdateField().get("dayOfWeek")))
        {
            boolean dayPicked = false;
            for (boolean day : saveItem.getDayOfWeek())
                if (day)
                {
                    dayPicked = true;
                    break;
                }
            if (!dayPicked)
            {
                final String summary = MessageUtil.formatMessageString("editAlerts_noDays");
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                context.addMessage("edit-form:"+getAlertPage()+"-day0", message);
                valid = false;
            }
        }

        if(!isBatchEdit() || (isBatchEdit() && getUpdateField().get("assignTo")))
        {
            // assigned to something
            boolean assigned = (saveItem.getGroupIDs() != null) && (saveItem.getGroupIDs().size() > 0);
            if (!assigned)
                assigned = (saveItem.getDriverIDs() != null) && (saveItem.getDriverIDs().size() > 0);
            if (!assigned)
                assigned = (saveItem.getVehicleIDs() != null) && (saveItem.getVehicleIDs().size() > 0);
            if (!assigned)
                assigned = (saveItem.getVehicleTypes() != null) && (saveItem.getVehicleTypes().size() > 0);
            if (!assigned)
            {
                final String summary = MessageUtil.formatMessageString("editAlerts_unassigned");
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                context.addMessage("edit-form:"+getAlertPage()+"-from", message);
                valid = false;
            }
        }

        return valid;
    }

    protected static boolean isAnytime(BaseAlertView alert)
    {
        return (alert.getStartTOD() == null) || alert.getStartTOD().equals(alert.getStopTOD());
    }

    protected static void onSetAnytime(BaseAlertView alert, boolean anytime)
    {
        if (anytime)
        {
            alert.setStartTOD(RedFlagAlert.MIN_TOD);
            alert.setStopTOD(RedFlagAlert.MIN_TOD);
        }
        else
        {
            if (alert.getStartTOD() == null)
                alert.setStartTOD(RedFlagAlert.DEFAULT_START_TOD);
            if (alert.getStopTOD() == null)
                alert.setStopTOD(RedFlagAlert.DEFAULT_STOP_TOD);
        }
    }
    
    public List<SelectItem> getStatuses() {
        return SelectItemUtil.toList(Status.class, false, Status.DELETED);
    }

    public abstract String getAlertPage();

    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }
    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }
    public String getSearchKeyword() {
        return searchKeyword;
    }
    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }
    public interface BaseAlertView extends EditItem
    {
        public boolean isAnytime();

        public void setAnytime(boolean anytime);

        public Integer getStartTOD();

        public void setStartTOD(Integer startTOD);

        public Integer getStopTOD();

        public void setStopTOD(Integer stopTOD);

        public List<Boolean> getDayOfWeek();

        public void setDayOfWeek(List<Boolean> dayOfWeek);

        public List<Integer> getGroupIDs();

        public void setGroupIDs(List<Integer> groupIDs);

        public List<Integer> getDriverIDs();

        public void setDriverIDs(List<Integer> driverIDs);

        public List<Integer> getVehicleIDs();

        public void setVehicleIDs(List<Integer> vehicleIDs);

        public List<VehicleType> getVehicleTypes();

        public void setVehicleTypes(List<VehicleType> vehicleTypes);

        public List<Integer> getNotifyPersonIDs();

        public void setNotifyPersonIDs(List<Integer> notifyPersonIDs);
        
        public void setEscalationPersonIDs(List<Integer> notifyPersonIDs);

        public Integer getUserID();
        
        public void setFullName(String fullName);
        
        public List<Integer> getVoiceEscalationPersonIDs();
        
        public Integer getEmailEscalationPersonID();

    }
    private interface AssignTypeStrategy{
        public List<SelectItem> getPickFromList(Integer groupID);
    }
    private class GroupAssignTypeStrategy implements AssignTypeStrategy{
        
        public List<SelectItem> getPickFromList(Integer groupID){
             List<SelectItem> pickFrom = new ArrayList<SelectItem>();
             for (final Group group : getGroupHierarchy().getSubGroupList(groupID))
                 pickFrom.add(new SelectItem("group" + group.getGroupID(), group.getName()));

             return pickFrom;
         }
     }
    private class DriverAssignTypeStrategy implements AssignTypeStrategy{
        public List<SelectItem> getPickFromList(Integer groupID){
             List<SelectItem> pickFrom = new ArrayList<SelectItem>();
             pickFrom.addAll(getAllDrivers(groupID));
             return pickFrom;
         }
     }
    private class VehicleAssignTypeStrategy implements AssignTypeStrategy{
        public List<SelectItem> getPickFromList(Integer groupID){
            
             List<SelectItem> pickFrom = new ArrayList<SelectItem>();
             pickFrom.addAll(getAllVehicles(groupID));
             
             return pickFrom;
         }
     }
    private class VehicleTypeAssignTypeStrategy implements AssignTypeStrategy{
        public List<SelectItem> getPickFromList(Integer groupID){
             List<SelectItem> pickFrom = new ArrayList<SelectItem>();
             pickFrom.add(new SelectItem(VehicleType.LIGHT.name(), MessageUtil.getMessageString("editAlerts_lightVehicles")));
             pickFrom.add(new SelectItem(VehicleType.MEDIUM.name(), MessageUtil.getMessageString("editAlerts_mediumVehicles")));
             pickFrom.add(new SelectItem(VehicleType.HEAVY.name(), MessageUtil.getMessageString("editAlerts_heavyVehicles")));
             return pickFrom;
         }
     }
}
