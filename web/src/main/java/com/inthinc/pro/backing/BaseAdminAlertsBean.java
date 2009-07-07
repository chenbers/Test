package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.inthinc.pro.backing.VehiclesBean.VehicleView;
import com.inthinc.pro.backing.ui.AutocompletePicker;
import com.inthinc.pro.backing.ui.ListPicker;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.BaseAlert;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.util.BeanUtil;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.MiscUtil;
import com.inthinc.pro.validators.EmailValidator;

public abstract class BaseAdminAlertsBean<T extends BaseAdminAlertsBean.BaseAlertView> extends BaseAdminBean<T> implements PersonChangeListener
{
    protected UserDAO          userDAO;
    protected PersonDAO        personDAO;
    protected DriverDAO        driverDAO;
    private VehiclesBean       vehiclesBean;
    private String             assignType;
    private List<SelectItem>   allVehicles;
    private List<SelectItem>   allDrivers;
    private ListPicker         assignPicker;
    private AutocompletePicker peoplePicker;
    private T                  oldItem;
    private String             oldEmailToString;

    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    public void setPersonDAO(PersonDAO personDAO)
    {
        this.personDAO = personDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }

    public void setVehiclesBean(VehiclesBean vehiclesBean)
    {
        this.vehiclesBean = vehiclesBean;
    }

    public String getAssignType()
    {
        return assignType;
    }

    public void setAssignType(String assignType)
    {
        this.assignType = assignType;
        if (assignPicker != null)
            assignPicker.setPickFrom(getAssignPickFrom());
    }

    public ListPicker getAssignPicker()
    {
        if (assignPicker == null)
            assignPicker = new ListPicker(getAssignPickFrom(), getAssignPicked());
        return assignPicker;
    }

    private List<SelectItem> getAssignPickFrom()
    {
        final LinkedList<SelectItem> pickFrom = new LinkedList<SelectItem>();
        if ((assignType == null) || "groups".equals(assignType))
        {
            for (final Group group : getGroupHierarchy().getGroupList())
                pickFrom.add(new SelectItem("group" + group.getGroupID(), group.getName()));
        }
        else if ("vehicleTypes".equals(assignType))
        {
            pickFrom.add(new SelectItem(VehicleType.LIGHT.name(), MessageUtil.getMessageString("editAlerts_lightVehicles")));
            pickFrom.add(new SelectItem(VehicleType.MEDIUM.name(), MessageUtil.getMessageString("editAlerts_mediumVehicles")));
            pickFrom.add(new SelectItem(VehicleType.HEAVY.name(), MessageUtil.getMessageString("editAlerts_heavyVehicles")));
        }
        else if ("vehicles".equals(assignType))
        {
            pickFrom.addAll(getAllVehicles());
        }
        else if ("drivers".equals(assignType))
        {
            pickFrom.addAll(getAllDrivers());
        }
        return pickFrom;
    }

    protected List<SelectItem> getAllVehicles()
    {
        if ((allVehicles == null) && (vehiclesBean != null))
        {
            final List<VehicleView> vehicles = vehiclesBean.getItems();
            allVehicles = new ArrayList<SelectItem>(vehicles.size());
            for (final Vehicle vehicle : vehicles)
                allVehicles.add(new SelectItem("vehicle" + vehicle.getVehicleID(), vehicle.getName()));
            MiscUtil.sortSelectItems(allVehicles);
        }
        return allVehicles;
    }

    protected List<SelectItem> getAllDrivers()
    {
        if ((allDrivers == null) && (driverDAO != null))
        {
            final List<Driver> drivers = driverDAO.getAllDrivers(getUser().getGroupID());
            allDrivers = new ArrayList<SelectItem>(drivers.size());
            for (final Driver driver : drivers)
                allDrivers.add(new SelectItem("driver" + driver.getDriverID(), driver.getPerson().getFirst() + ' ' + driver.getPerson().getLast()));
            MiscUtil.sortSelectItems(allDrivers);
        }
        return allDrivers;
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
        if (peoplePicker == null)
        {
            //final List<User> users = userDAO.getUsersInGroupHierarchy(getTopGroup().getGroupID());
            final List<Person> people = personDAO.getPeopleInGroupHierarchy(getTopGroup().getGroupID());
            final ArrayList<SelectItem> allUsers = new ArrayList<SelectItem>(people.size());
            
            for (final Person person : people)
                allUsers.add(new SelectItem(person.getPersonID(), person.getFirst() + " " + person.getLast()));
                //allUsers.add(new SelectItem(user.getUserID(), user.getPerson().getFirst() + ' ' + user.getPerson().getLast()));
            
                MiscUtil.sortSelectItems(allUsers);

            final ArrayList<SelectItem> notifyPeople = getNotifyPicked();

            peoplePicker = new AutocompletePicker(allUsers, notifyPeople);
        }
        return peoplePicker;
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
                if (person != null)
                    notifyPeople.add(new SelectItem(person.getPersonID(), person.getFirst() + ' ' + person.getLast()));
            }
            MiscUtil.sortSelectItems(notifyPeople);
        }
        return notifyPeople;
    }

    @Override
    public void personListChanged()
    {
        assignPicker = null;
        peoplePicker = null;
    }

    @Override
    public T getItem()
    {
        final T item = super.getItem();
        if (item != oldItem)
        {
            oldItem = item;
            oldEmailToString = item.getEmailToString();
            getAssignPicker().setPicked(getAssignPicked());
            getAssignPicker().setPickFrom(getAssignPickFrom());
            getPeoplePicker().setPicked(getNotifyPicked());
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
        getItem().setEmailToString(getOldEmailToString());
        return super.cancelEdit();
    }

    @Override
    public String save()
    {
        // if batch-changing assignment, change all assignment types
        final Map<String, Boolean> updateField = getUpdateField();
        final boolean assignTo = Boolean.TRUE.equals(updateField.get("assignTo"));
        updateField.put("groupIDs", assignTo);
        updateField.put("vehicleTypes", assignTo);
        updateField.put("vehicleIDs", assignTo);
        updateField.put("driverIDs", assignTo);

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

        return super.save();
    }
    
    @Override
    protected boolean validateSaveItem(T saveItem)
    {
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
                context.addMessage("edit-form:day0", message);
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
                context.addMessage("edit-form:alertAssignFrom", message);
                valid = false;
            }
        }

        if(!isBatchEdit() || (isBatchEdit() && getUpdateField().get("emailToString")))
        {
            // valid e-mail addresses
            for (final String email : saveItem.getEmailTo())
            {
                final Matcher matcher = EmailValidator.EMAIL_REGEX.matcher(email);
                if (!matcher.matches())
                {
                    final String summary = MessageUtil.formatMessageString("editAlerts_emailFormat", email);
                    final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                    context.addMessage("edit-form:emailToString", message);
                    valid = false;
                    break;
                }
            }
        }
        
        return valid;
    }

    protected String getOldEmailToString()
    {
        return oldEmailToString;
    }

    protected void setOldEmailToString(String oldEmailToString)
    {
        this.oldEmailToString = oldEmailToString;
    }

    protected static boolean isAnytime(BaseAlertView alert)
    {
        return (alert.getStartTOD() == null) || alert.getStartTOD().equals(alert.getStopTOD());
    }

    protected static void onSetAnytime(BaseAlertView alert, boolean anytime)
    {
        if (anytime)
        {
            alert.setStartTOD(BaseAlert.MIN_TOD);
            alert.setStopTOD(BaseAlert.MIN_TOD);
        }
        else
        {
            if (alert.getStartTOD() == null)
                alert.setStartTOD(BaseAlert.DEFAULT_START_TOD);
            if (alert.getStopTOD() == null)
                alert.setStopTOD(BaseAlert.DEFAULT_STOP_TOD);
        }
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

        public void setNotifyPersonIDs(List<Integer> notifyUserIDs);

        public List<String> getEmailTo();

        public void setEmailTo(List<String> emailTo);

        public String getEmailToString();

        public void setEmailToString(String emailToString);
    }
}
