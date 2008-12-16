package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.inthinc.pro.backing.ui.AutocompletePicker;
import com.inthinc.pro.backing.ui.ListPicker;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.BaseAlert;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.MiscUtil;

public abstract class BaseAdminAlertsBean<T extends BaseAdminAlertsBean.BaseAlertView> extends BaseAdminBean<T>
{
    protected PersonDAO        personDAO;
    protected VehicleDAO       vehicleDAO;
    protected DriverDAO        driverDAO;
    private String             assignType;
    private List<SelectItem>   allVehicles;
    private List<SelectItem>   allDrivers;
    private ListPicker         assignPicker;
    private AutocompletePicker peoplePicker;
    private T                  oldItem;
    private String             oldEmailToString;

    public void setPersonDAO(PersonDAO personDAO)
    {
        this.personDAO = personDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
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
                final List<Vehicle> vehicles = vehicleDAO.getVehiclesInGroupHierarchy(getUser().getPerson().getGroupID());
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
                final List<Driver> drivers = driverDAO.getAllDrivers(getUser().getPerson().getGroupID());
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

        return picked;
    }

    public AutocompletePicker getPeoplePicker()
    {
        if (peoplePicker == null)
        {
            final List<Person> people = personDAO.getPeopleInGroupHierarchy(getUser().getPerson().getGroupID());
            final ArrayList<SelectItem> allPeople = new ArrayList<SelectItem>(people.size());
            for (final Person person : people)
                allPeople.add(new SelectItem(person.getPersonID(), person.getFirst() + ' ' + person.getLast()));
            MiscUtil.sortSelectItems(allPeople);

            final ArrayList<SelectItem> notifyPeople = getNotifyPicked();

            peoplePicker = new AutocompletePicker(allPeople, notifyPeople);
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
                final Person person = personDAO.findByID(id);
                if (person != null)
                    notifyPeople.add(new SelectItem(person.getPersonID(), person.getFirst() + ' ' + person.getLast()));
            }
            MiscUtil.sortSelectItems(notifyPeople);
        }
        return notifyPeople;
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
            getPeoplePicker().setPicked(getNotifyPicked());
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
        }

        // set notify person IDs
        final ArrayList<Integer> personIDs = new ArrayList<Integer>(getPeoplePicker().getPicked().size());
        for (final SelectItem item : getPeoplePicker().getPicked())
            personIDs.add((Integer) item.getValue());
        getItem().setNotifyPersonIDs(personIDs);

        return super.save();
    }

    @Override
    protected boolean validate(List<T> saveItems)
    {
        final FacesContext context = FacesContext.getCurrentInstance();

        boolean valid = true;
        for (final BaseAlertView alert : saveItems)
        {
            // time of day
            if (!alert.isAnytime() && (alert.getStartTOD() >= alert.getStopTOD()))
            {
                final String summary = MessageUtil.formatMessageString("editAlerts_invalidTimeframe");
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                context.addMessage("edit-form:stopTOD", message);
                valid = false;
            }

            // assigned to something
            if ((alert.getGroupIDs() == null) || (alert.getGroupIDs().size() == 0))
            {
                final String summary = MessageUtil.formatMessageString("editAlerts_unassigned");
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                context.addMessage("edit-form:assignType", message);
                valid = false;
            }

            // someone is notified
            if (((alert.getNotifyPersonIDs() == null) || (alert.getNotifyPersonIDs().size() == 0)) && (alert.getEmailToString().length() == 0))
            {
                final String summary = MessageUtil.formatMessageString("editAlerts_noNotification");
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                context.addMessage("edit-form:emailToString", message);
                valid = false;
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
            alert.setStartTOD(null);
            alert.setStopTOD(null);
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

        public List<Integer> getNotifyPersonIDs();

        public void setNotifyPersonIDs(List<Integer> notifyUserIDs);

        public List<String> getEmailTo();

        public void setEmailTo(List<String> emailTo);

        public String getEmailToString();

        public void setEmailToString(String emailToString);
    }
}
