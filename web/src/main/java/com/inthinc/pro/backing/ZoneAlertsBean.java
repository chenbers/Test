package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.springframework.beans.BeanUtils;

import com.inthinc.pro.backing.ui.AutocompletePicker;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.ZoneAlertDAO;
import com.inthinc.pro.dao.ZoneDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.BaseAlert;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.ZoneAlert;
import com.inthinc.pro.util.MessageUtil;

public class ZoneAlertsBean extends BaseAdminBean<ZoneAlertsBean.ZoneAlertView> implements AutocompletePicker
{
    private static final List<String> AVAILABLE_COLUMNS;
    private static final int[]        DEFAULT_COLUMN_INDICES = new int[] { 0, 1, 2 };

    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("name");
        AVAILABLE_COLUMNS.add("description");
        AVAILABLE_COLUMNS.add("zone");
    }

    private PersonDAO                 personDAO;
    private VehicleDAO                vehicleDAO;
    private ZoneAlertDAO              zoneAlertDAO;
    private ZoneDAO                   zoneDAO;
    private List<Person>              people;
    private Integer                   personID;
    private ArrayList<SelectItem>     zones;
    private String                    vehicleFilter;
    private List<SelectItem>          vehicles;
    private List<SelectItem>          vehicleGroups;
    private List<Vehicle>             allVehicles;

    public void setPersonDAO(PersonDAO personDAO)
    {
        this.personDAO = personDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    public void setZoneAlertDAO(ZoneAlertDAO zoneAlertDAO)
    {
        this.zoneAlertDAO = zoneAlertDAO;
    }

    public void setZoneDAO(ZoneDAO zoneDAO)
    {
        this.zoneDAO = zoneDAO;
    }

    @Override
    protected List<ZoneAlertView> loadItems()
    {
        // get the zone alerts
        final List<ZoneAlert> plainZoneAlerts = zoneAlertDAO.getZoneAlerts(getAccountID());

        // convert the ZoneAlerts to ZoneAlertViews
        final LinkedList<ZoneAlertView> items = new LinkedList<ZoneAlertView>();
        for (final ZoneAlert alert : plainZoneAlerts)
            items.add(createZoneAlertView(alert));

        return items;
    }

    /**
     * Creates a ZoneAlertView object from the given ZoneAlert object.
     * 
     * @param alert
     *            The alert.
     * @return The new ZoneAlertView object.
     */
    private ZoneAlertView createZoneAlertView(ZoneAlert alert)
    {
        final ZoneAlertView alertView = new ZoneAlertView();
        BeanUtils.copyProperties(alert, alertView);
        if ((alertView.getDayOfWeek() == null) || (alertView.getDayOfWeek().size() != 7))
        {
            final ArrayList<Boolean> dayOfWeek = new ArrayList<Boolean>();
            for (int i = 0; i < 7; i++)
                dayOfWeek.add(false);
            alertView.setDayOfWeek(dayOfWeek);
        }
        alertView.setPersonDAO(personDAO);
        alertView.setZoneDAO(zoneDAO);
        alertView.setOldVehicleIDs(alert.getVehicleIDs());
        alertView.setOldNotifyPersonIDs(alert.getNotifyPersonIDs());
        alertView.setOldEmailToString(alertView.getEmailToString());
        alertView.setSelected(false);
        return alertView;
    }

    @Override
    protected boolean matchesFilter(ZoneAlertView alert, String filterWord)
    {
        for (final String column : getTableColumns().keySet())
            if (getTableColumns().get(column).getVisible())
            {
                boolean matches = false;
                try
                {
                    matches = String.valueOf(org.apache.commons.beanutils.BeanUtils.getProperty(alert, column.replace('_', '.'))).toLowerCase().startsWith(filterWord);
                }
                catch (Exception e)
                {
                    logger.error("Error filtering on column " + column, e);
                }

                if (matches)
                    return true;
            }

        return false;
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
        return "zoneAlertsHeader_";
    }

    @Override
    public TableType getTableType()
    {
        return TableType.ADMIN_ZONE_ALERTS;
    }

    @Override
    protected ZoneAlertView createAddItem()
    {
        final ZoneAlert alert = new ZoneAlert();
        final Map<String, String> parameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        final String zoneID = parameterMap.get("zoneID");
        if (zoneID != null)
            alert.setZoneID(new Integer(zoneID));
        return createZoneAlertView(alert);
    }

    @Override
    protected void doDelete(List<ZoneAlertView> deleteItems)
    {
        final FacesContext context = FacesContext.getCurrentInstance();

        for (final ZoneAlertView alert : deleteItems)
        {
            zoneAlertDAO.deleteByID(alert.getZoneAlertID());

            // add a message
            final String summary = MessageUtil.formatMessageString("zoneAlert_deleted", alert.getName());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }
    }

    @Override
    public String cancelEdit()
    {
        getItem().setVehicleIDs(getItem().getOldVehicleIDs());
        getItem().setNotifyPersonIDs(getItem().getOldNotifyPersonIDs());
        getItem().setEmailToString(getItem().getOldEmailToString());
        return super.cancelEdit();
    }

    @Override
    public String save()
    {
        // if batch-changing alert definition, change all of its children
        final Map<String, Boolean> updateField = getUpdateField();
        final boolean defineAlerts = Boolean.TRUE.equals(updateField.get("defineAlerts"));
        updateField.put("arrival", defineAlerts);
        updateField.put("departure", defineAlerts);
        updateField.put("driverIDViolation", defineAlerts);
        updateField.put("ignitionOn", defineAlerts);
        updateField.put("ignitionOff", defineAlerts);
        updateField.put("position", defineAlerts);
        updateField.put("seatbeltViolation", defineAlerts);
        updateField.put("speedLimit", defineAlerts);
        updateField.put("speedViolation", defineAlerts);
        updateField.put("masterBuzzer", defineAlerts);
        updateField.put("cautionArea", defineAlerts);
        updateField.put("disableRF", defineAlerts);
        updateField.put("monitorIdle", defineAlerts);

        return super.save();
    }

    @Override
    protected boolean validate(List<ZoneAlertView> saveItems)
    {
        final FacesContext context = FacesContext.getCurrentInstance();

        boolean valid = true;
        for (final ZoneAlertView alert : saveItems)
        {
            if (!alert.isAnytime() && (alert.getStartTOD() >= alert.getStopTOD()))
            {
                final String summary = MessageUtil.formatMessageString("editZoneAlert_invalidTimeframe");
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
                context.addMessage("edit-form:stopTOD", message);
                valid = false;
            }
            // TODO: make sure at least one alert is defined, at least one vehicle is added, and at least one person is being notified
        }
        return valid;
    }

    @Override
    protected void doSave(List<ZoneAlertView> saveItems, boolean create)
    {
        final FacesContext context = FacesContext.getCurrentInstance();

        for (final ZoneAlertView alert : saveItems)
        {
            if (create)
                alert.setZoneAlertID(zoneAlertDAO.create(getAccountID(), alert));
            else
                zoneAlertDAO.update(alert);

            alert.setOldVehicleIDs(alert.getVehicleIDs());
            alert.setOldNotifyPersonIDs(alert.getNotifyPersonIDs());
            alert.setOldEmailToString(alert.getEmailToString());

            // add a message
            final String summary = MessageUtil.formatMessageString(create ? "zoneAlert_added" : "zoneAlert_updated", alert.getName());
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            context.addMessage(null, message);
        }
    }

    @Override
    protected String getDisplayRedirect()
    {
        return "go_adminZoneAlert";
    }

    @Override
    protected String getEditRedirect()
    {
        return "go_adminEditZoneAlert";
    }

    @Override
    protected String getFinishedRedirect()
    {
        return "go_adminZoneAlerts";
    }

    public List<SelectItem> getZones()
    {
        if (zones == null)
        {
            zones = new ArrayList<SelectItem>();
            final List<Zone> list = zoneDAO.getZones(getAccountID());
            for (final Zone zone : list)
                zones.add(new SelectItem(zone.getZoneID(), zone.getName()));
            sortSelectItems(zones);
        }
        return zones;
    }

    public List<SelectItem> getVehicleGroups()
    {
        if (vehicleGroups == null)
        {
            vehicleGroups = new ArrayList<SelectItem>();
            for (final Group group : getGroupHierarchy().getGroupList())
                vehicleGroups.add(new SelectItem(group.getGroupID(), MessageUtil.formatMessageString("editZoneAlert_vehicleGroup", group.getName())));
        }
        return vehicleGroups;
    }

    public String getVehicleFilter()
    {
        return vehicleFilter;
    }

    public void setVehicleFilter(String vehicleFilter)
    {
        this.vehicleFilter = vehicleFilter;
        vehicles = null;
    }

    private boolean matchesFilter(Vehicle vehicle)
    {
        if ((vehicleFilter == null) || (vehicleFilter.length() == 0) || ((vehicle.getVtype() != null) && vehicle.getVtype().getDescription().equals(vehicleFilter)))
            return true;
        else
        {
            try
            {
                final Integer groupID = new Integer(vehicleFilter);
                if (groupID.equals(vehicle.getGroupID()))
                    return true;

                // test for a parent group
                Group vehicleGroup = findGroup(vehicle.getGroupID());
                while (vehicleGroup != null)
                {
                    if (groupID.equals(vehicleGroup.getParentID()))
                        return true;
                    vehicleGroup = findGroup(vehicleGroup.getParentID());
                }
            }
            catch (NumberFormatException e)
            {
            }
        }
        return false;
    }

    private Group findGroup(Integer groupID)
    {
        for (final Group group : getGroupHierarchy().getGroupList())
            if (group.getGroupID().equals(groupID))
                return group;
        return null;
    }

    public List<SelectItem> getVehicles()
    {
        if (vehicles == null)
        {
            vehicles = new ArrayList<SelectItem>();
            if (allVehicles == null)
                allVehicles = vehicleDAO.getVehiclesInGroupHierarchy(getUser().getPerson().getGroupID());
            for (final Vehicle vehicle : allVehicles)
                if (matchesFilter(vehicle))
                    vehicles.add(new SelectItem(vehicle.getVehicleID(), vehicle.getName()));
        }
        return vehicles;
    }

    @Override
    public List<SelectItem> autocomplete(Object value)
    {
        if (people == null)
            people = personDAO.getPeopleInGroupHierarchy(getUser().getPerson().getGroupID());

        final ArrayList<SelectItem> suggestions = new ArrayList<SelectItem>();
        final String[] names = value.toString().toLowerCase().split("[, ]");
        for (final Person person : people)
        {
            boolean matches = true;
            for (final String name : names)
            {
                matches &= (person.getFirst().toLowerCase().startsWith(name) || person.getLast().toLowerCase().startsWith(name));
                if (!matches)
                    break;
            }
            if (matches)
                suggestions.add(new SelectItem(person.getPersonID(), person.getFirst() + ' ' + person.getLast()));
        }

        final List<SelectItem> notifyPeople = getItem().getNotifyPeople();
        if (notifyPeople != null)
            for (final SelectItem item : notifyPeople)
                for (final Iterator<SelectItem> i = suggestions.iterator(); i.hasNext();)
                    if (i.next().getValue().equals(item.getValue()))
                        i.remove();

        sortSelectItems(suggestions);
        return suggestions;
    }

    @Override
    public Object getItemValue()
    {
        return null;
    }

    @Override
    public void setItemValue(Object value)
    {
        try
        {
            personID = new Integer(value.toString());
        }
        catch (NumberFormatException e)
        {
            personID = null;
        }
    }

    public int getPickedItemCount()
    {
        return getItem().getNotifyPeople().size();
    }

    @Override
    public void addItem()
    {
        getItem().addNotifyPerson(personID);
        personID = null;
    }

    @Override
    public void deleteItem()
    {
        getItem().removeNotifyPerson(personID);
        personID = null;
    }

    private static void sortSelectItems(List<SelectItem> items)
    {
        Collections.sort(items, new Comparator<SelectItem>()
        {
            @Override
            public int compare(SelectItem o1, SelectItem o2)
            {
                return o1.getLabel().compareTo(o2.getLabel());
            }
        });
    }

    public static class ZoneAlertView extends ZoneAlert implements EditItem
    {
        @Column(updateable = false)
        private static final long serialVersionUID = 8372507838051791866L;

        @Column(updateable = false)
        private PersonDAO         personDAO;
        @Column(updateable = false)
        private ZoneDAO           zoneDAO;
        @Column(updateable = false)
        private Zone              zone;
        @Column(updateable = false)
        private boolean           anytime;
        @Column(updateable = false)
        private List<SelectItem>  notifyPeople;
        @Column(updateable = false)
        private List<Integer>     oldVehicleIDs;
        @Column(updateable = false)
        private List<Integer>     oldNotifyPersonIDs;
        @Column(updateable = false)
        private String            oldEmailToString;
        @Column(updateable = false)
        private boolean           selected;

        public Integer getId()
        {
            return getZoneAlertID();
        }

        void setPersonDAO(PersonDAO personDAO)
        {
            this.personDAO = personDAO;
        }

        void setZoneDAO(ZoneDAO zoneDAO)
        {
            this.zoneDAO = zoneDAO;
        }

        @Override
        public void setZoneID(Integer zoneID)
        {
            super.setZoneID(zoneID);
            zone = null;
        }

        public Zone getZone()
        {
            if (zone == null)
                zone = zoneDAO.findByID(getZoneID());
            return zone;
        }

        public boolean isAnytime()
        {
            if (!anytime)
                anytime = (getStartTOD() == null) || getStartTOD().equals(getStopTOD());
            return anytime;
        }

        public void setAnytime(boolean anytime)
        {
            this.anytime = anytime;
            if (anytime)
            {
                setStartTOD(null);
                setStopTOD(null);
            }
            else
            {
                if (getStartTOD() == null)
                    setStartTOD(BaseAlert.DEFAULT_START_TOD);
                if ((getStopTOD() == null) || (getStopTOD() <= getStartTOD()))
                    setStopTOD(BaseAlert.DEFAULT_STOP_TOD);
            }
        }

        @Override
        public void setStartTOD(Integer startTOD)
        {
            if (!anytime)
                super.setStartTOD(startTOD);
            else
                super.setStartTOD(null);
        }

        @Override
        public void setStopTOD(Integer stopTOD)
        {
            if (!anytime)
                super.setStopTOD(stopTOD);
            else
                super.setStopTOD(null);
        }

        List<Integer> getOldVehicleIDs()
        {
            return oldVehicleIDs;
        }

        void setOldVehicleIDs(List<Integer> oldVehicleIDs)
        {
            this.oldVehicleIDs = oldVehicleIDs;
        }

        public List<SelectItem> getNotifyPeople()
        {
            if (notifyPeople == null)
            {
                notifyPeople = new ArrayList<SelectItem>();
                if (getNotifyPersonIDs() != null)
                {
                    for (final Integer id : getNotifyPersonIDs())
                    {
                        final Person person = personDAO.findByID(id);
                        if (person != null)
                            notifyPeople.add(new SelectItem(person.getPersonID(), person.getFirst() + ' ' + person.getLast()));
                    }
                    sortSelectItems(notifyPeople);
                }
            }
            return notifyPeople;
        }

        public void addNotifyPerson(Integer personID)
        {
            if (getNotifyPersonIDs() == null)
                setNotifyPersonIDs(new ArrayList<Integer>());
            getNotifyPersonIDs().add(personID);
            notifyPeople = null;
        }

        public void removeNotifyPerson(Integer personID)
        {
            if (getNotifyPersonIDs() != null)
            {
                getNotifyPersonIDs().remove(personID);
                notifyPeople = null;
            }
        }

        List<Integer> getOldNotifyPersonIDs()
        {
            return oldNotifyPersonIDs;
        }

        void setOldNotifyPersonIDs(List<Integer> oldNotifyPersonIDs)
        {
            this.oldNotifyPersonIDs = oldNotifyPersonIDs;
        }

        public String getEmailToString()
        {
            final StringBuilder sb = new StringBuilder();
            if (getEmailTo() != null)
                for (final String email : getEmailTo())
                {
                    if (sb.length() > 0)
                        sb.append(", ");
                    sb.append(email);
                }
            return sb.toString();
        }

        public void setEmailToString(String emailToString)
        {
            if ((emailToString != null) && (emailToString.trim().length() > 0))
                setEmailTo(Arrays.asList(emailToString.split("[,; ]+")));
            else
                setEmailTo(null);
        }

        String getOldEmailToString()
        {
            return oldEmailToString;
        }

        void setOldEmailToString(String oldEmailToString)
        {
            this.oldEmailToString = oldEmailToString;
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
