package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.BeanUtils;

import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.ZoneAlertDAO;
import com.inthinc.pro.dao.ZoneDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.ZoneAlert;
import com.inthinc.pro.util.MessageUtil;

public class ZoneAlertsBean extends BaseAdminBean<ZoneAlertsBean.ZoneAlertView>
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
    private ZoneAlertDAO              zoneAlertDAO;
    private ZoneDAO                   zoneDAO;

    public void setPersonDAO(PersonDAO personDAO)
    {
        this.personDAO = personDAO;
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
        final List<ZoneAlert> plainZoneAlerts = zoneAlertDAO.getZoneAlertsInGroupHierarchy(getUser().getPerson().getGroupID());

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
        alertView.setPersonDAO(personDAO);
        alertView.setZoneDAO(zoneDAO);
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
    protected void doSave(List<ZoneAlertView> saveItems, boolean create)
    {
        final FacesContext context = FacesContext.getCurrentInstance();

        for (final ZoneAlertView alert : saveItems)
        {
            if (create)
                alert.setZoneAlertID(zoneAlertDAO.create(getAccountID(), alert));
            else
                zoneAlertDAO.update(alert);

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
        private List<Person>      notifyPeople;
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

        public List<Person> getNotifyPeople()
        {
            if ((notifyPeople == null) && (getNotifyPersonIDs() != null))
            {
                notifyPeople = new ArrayList<Person>();
                for (final Integer id : getNotifyPersonIDs())
                {
                    final Person person = personDAO.findByID(id);
                    if (person != null)
                        notifyPeople.add(person);
                }
            }
            return notifyPeople;
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
