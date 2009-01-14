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
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.BaseAlert;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.MiscUtil;
import com.inthinc.pro.validators.EmailValidator;

public abstract class BaseAdminAlertsBean<T extends BaseAdminAlertsBean.BaseAlertView> extends BaseAdminBean<T> implements PersonChangeListener
{
    protected UserDAO          userDAO;
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
            pickFrom.add(new SelectItem(VehicleType.LIGHT.toString(), MessageUtil.getMessageString("editAlerts_lightVehicles")));
            pickFrom.add(new SelectItem(VehicleType.MEDIUM.toString(), MessageUtil.getMessageString("editAlerts_mediumVehicles")));
            pickFrom.add(new SelectItem(VehicleType.HEAVY.toString(), MessageUtil.getMessageString("editAlerts_heavyVehicles")));
        }
        else if ("vehicles".equals(assignType))
        {
            if (allVehicles == null)
            {
                final List<VehicleView> vehicles = vehiclesBean.getItems();
                allVehicles = new ArrayList<SelectItem>(vehicles.size());
                for (final Vehicle vehicle : vehicles)
                    allVehicles.add(new SelectItem("vehicle" + vehicle.getVehicleID(), vehicle.getName()));
            }
            pickFrom.addAll(allVehicles);
        }
        else if ("drivers".equals(assignType))
        {
            if (allDrivers == null)
            {
                final List<Driver> drivers = driverDAO.getAllDrivers(getUser().getGroupID());
                allDrivers = new ArrayList<SelectItem>(drivers.size());
                for (final Driver driver : drivers)
                    allDrivers.add(new SelectItem("driver" + driver.getDriverID(), driver.getPerson().getFirst() + ' ' + driver.getPerson().getLast()));
            }
            pickFrom.addAll(allDrivers);
        }
        return pickFrom;
    }

    private List<SelectItem> getAssignPicked()
    {
        final LinkedList<SelectItem> picked = new LinkedList<SelectItem>();
        if (getItem().getGroupIDs() != null)
            for (final Group group : getGroupHierarchy().getGroupList())
                if (getItem().getGroupIDs().contains(group.getGroupID()))
                    picked.add(new SelectItem("group" + group.getGroupID(), group.getName()));

        if (getItem().getVehicleTypes() != null)
            for (final VehicleType type : getItem().getVehicleTypes())
                picked.add(new SelectItem(type.toString(), MessageUtil.getMessageString("editAlerts_" + type.toString().toLowerCase() + "Vehicles")));

        if (getItem().getVehicleIDs() != null && allVehicles != null)
            for (final SelectItem vehicle : allVehicles)
                if (getItem().getVehicleIDs().contains(new Integer(vehicle.getValue().toString().substring(7))))
                    picked.add(vehicle);

        if (getItem().getDriverIDs() != null && allDrivers != null)
            for (final SelectItem driver : allDrivers)
                if (getItem().getDriverIDs().contains(new Integer(driver.getValue().toString().substring(6))))
                    picked.add(driver);

        return picked;
    }

    public AutocompletePicker getPeoplePicker()
    {
        if (peoplePicker == null)
        {
            final List<User> users = userDAO.getUsersInGroupHierarchy(getUser().getGroupID());
            final ArrayList<SelectItem> allUsers = new ArrayList<SelectItem>(users.size());
            for (final User user : users)
                allUsers.add(new SelectItem(user.getUserID(), user.getPerson().getFirst() + ' ' + user.getPerson().getLast()));
            MiscUtil.sortSelectItems(allUsers);

            final ArrayList<SelectItem> notifyPeople = getNotifyPicked();

            peoplePicker = new AutocompletePicker(allUsers, notifyPeople);
        }
        return peoplePicker;
    }

    private ArrayList<SelectItem> getNotifyPicked()
    {
        final ArrayList<SelectItem> notifyPeople = new ArrayList<SelectItem>();
        if (getItem().getNotifyUserIDs() != null)
        {
            for (final Integer id : getItem().getNotifyUserIDs())
            {
                final User user = userDAO.findByID(id);
                if (user != null)
                    notifyPeople.add(new SelectItem(user.getUserID(), user.getPerson().getFirst() + ' ' + user.getPerson().getLast()));
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

        // set group, vehicle type, vehicle and driver IDs
        for (final SelectItem item : getAssignPicker().getPicked())
        {
            // group
            if (item.getValue().toString().startsWith("group"))
            {
                if (getItem().getGroupIDs() == null)
                    getItem().setGroupIDs(new ArrayList<Integer>());
                final Integer id = new Integer(item.getValue().toString().substring(5));
                if (!getItem().getGroupIDs().contains(id))
                    getItem().getGroupIDs().add(id);
            }
            // vehicle
            else if (item.getValue().toString().startsWith("vehicle"))
            {
                if (getItem().getVehicleIDs() == null)
                    getItem().setVehicleIDs(new ArrayList<Integer>());
                final Integer id = new Integer(item.getValue().toString().substring(7));
                if (!getItem().getVehicleIDs().contains(id))
                    getItem().getVehicleIDs().add(id);
            }
            // driver
            else if (item.getValue().toString().startsWith("driver"))
            {
                if (getItem().getDriverIDs() == null)
                    getItem().setDriverIDs(new ArrayList<Integer>());
                final Integer id = new Integer(item.getValue().toString().substring(6));
                if (!getItem().getDriverIDs().contains(id))
                    getItem().getDriverIDs().add(id);
            }
            else
            {
                // vehicle type
                if (getItem().getVehicleTypes() == null)
                    getItem().setVehicleTypes(new ArrayList<VehicleType>());
                final VehicleType type = VehicleType.valueOf(item.getValue().toString());
                if (!getItem().getVehicleTypes().contains(type))
                    getItem().getVehicleTypes().add(type);
            }
        }

        // set notify user IDs
        final ArrayList<Integer> userIDs = new ArrayList<Integer>(getPeoplePicker().getPicked().size());
        for (final SelectItem item : getPeoplePicker().getPicked())
            userIDs.add((Integer) item.getValue());
        getItem().setNotifyUserIDs(userIDs);

        return super.save();
    }

    @Override
    protected boolean validate(List<T> saveItems)
    {
        final FacesContext context = FacesContext.getCurrentInstance();

        boolean valid = true;
        for (final BaseAlertView alert : saveItems)
        {
            // at least one day chosen
            boolean dayPicked = false;
            for (boolean day : alert.getDayOfWeek())
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

            // assigned to something
            boolean assigned = (alert.getGroupIDs() != null) && (alert.getGroupIDs().size() > 0);
            if (!assigned)
                assigned = (alert.getDriverIDs() != null) && (alert.getDriverIDs().size() > 0);
            if (!assigned)
                assigned = (alert.getVehicleIDs() != null) && (alert.getVehicleIDs().size() > 0);
            if (!assigned)
                assigned = (alert.getVehicleTypes() != null) && (alert.getVehicleTypes().size() > 0);
            if (!assigned)
            {
                final String summary = MessageUtil.formatMessageString("editAlerts_unassigned");
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                context.addMessage("edit-form:alertAssignFrom", message);
                valid = false;
            }

            // someone is notified
            if (((alert.getNotifyUserIDs() == null) || (alert.getNotifyUserIDs().size() == 0)) && (alert.getEmailToString().length() == 0))
            {
                final String summary = MessageUtil.formatMessageString("editAlerts_noNotification");
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                context.addMessage("edit-form:emailToString", message);
                valid = false;
            }

            // valid e-mail addresses
            for (final String email : alert.getEmailTo())
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
            if ((alert.getStopTOD() == null) || (alert.getStopTOD() <= alert.getStartTOD()))
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

        public List<Integer> getNotifyUserIDs();

        public void setNotifyUserIDs(List<Integer> notifyUserIDs);

        public List<String> getEmailTo();

        public void setEmailTo(List<String> emailTo);

        public String getEmailToString();

        public void setEmailToString(String emailToString);
    }
}
