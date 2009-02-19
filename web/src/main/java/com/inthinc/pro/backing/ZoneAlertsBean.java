package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.springframework.beans.BeanUtils;

import com.inthinc.pro.dao.ZoneAlertDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.BaseAlert;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.ZoneAlert;
import com.inthinc.pro.util.MessageUtil;

public class ZoneAlertsBean extends BaseAdminAlertsBean<ZoneAlertsBean.ZoneAlertView>
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

    private ZoneAlertDAO              zoneAlertDAO;
    private ZonesBean                 zonesBean;

    public void setZoneAlertDAO(ZoneAlertDAO zoneAlertDAO)
    {
        this.zoneAlertDAO = zoneAlertDAO;
    }

    public void setZonesBean(ZonesBean zonesBean)
    {
        this.zonesBean = zonesBean;
    }

    @Override
    public List<ZoneAlertView> getFilteredItems()
    {
        for (final ZoneAlertView alert : filteredItems)
            if (getZoneByID(alert.getZoneID()) == null)
            {
                items = null;
                break;
            }

        return super.getFilteredItems();
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
        alertView.setAnytime(false);
        BeanUtils.copyProperties(alert, alertView);
        alertView.setAnytime(isAnytime(alertView));
        alertView.setZoneAlertsBean(this);
        alertView.setSelected(false);
        return alertView;
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
        final String zoneID = parameterMap.get("zones-form:zone");
        if (zoneID != null)
            alert.setZoneID(Integer.valueOf(zoneID));
        else
        {
            final List<SelectItem> zones = getZones();
            if ((zones != null) && (zones.size() > 0))
                alert.setZoneID((Integer)zones.get(0).getValue());
        }
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
        boolean valid = super.validate(saveItems);

        // at least one alert is defined
        for (final ZoneAlertView alert : saveItems)
            if (!Boolean.TRUE.equals(alert.getArrival()) && !Boolean.TRUE.equals(alert.getDeparture()))
            {
                final String summary = MessageUtil.formatMessageString("editZoneAlert_noAlerts");
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                context.addMessage("edit-form:arrival", message);
                valid = false;
            }
        return valid;
    }

    @Override
    protected ZoneAlertView revertItem(ZoneAlertView editItem)
    {
        return createZoneAlertView(zoneAlertDAO.findByID(editItem.getZoneAlertID()));
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

            setOldEmailToString(alert.getEmailToString());

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
        List<SelectItem> zoneList = zonesBean.getZoneIDs();
        return zoneList;
    }

    protected Zone getZoneByID(Integer zoneID)
    {
        for (final Zone zone : zonesBean.getZones())
            if (zone.getZoneID() != null && zone.getZoneID().equals(zoneID))
                return zone;
        return null;
    }

    public static class ZoneAlertView extends ZoneAlert implements BaseAdminAlertsBean.BaseAlertView
    {
        @Column(updateable = false)
        private static final long serialVersionUID = 8372507838051791866L;

        @Column(updateable = false)
        private ZoneAlertsBean    zoneAlertsBean;
        @Column(updateable = false)
        private Zone              zone;
        @Column(updateable = false)
        private Boolean           anytime;
        @Column(updateable = false)
        private boolean           selected;

        public Integer getId()
        {
            return getZoneAlertID();
        }

        void setZoneAlertsBean(ZoneAlertsBean zoneAlertsBean)
        {
            this.zoneAlertsBean = zoneAlertsBean;
        }

        @Override
        public void setZoneID(Integer zoneID)
        {
            super.setZoneID(zoneID);
            zone = null;
        }

        public Zone getZone()
        {
            if (zone == null && getZoneID() != null)
                zone = zoneAlertsBean.getZoneByID(getZoneID());
            return zone;
        }

        public boolean isAnytime()
        {
            if (anytime == null)
            {
                if (getStartTOD() == null)
                    super.setStartTOD(BaseAlert.MIN_TOD);
                if (getStopTOD() == null)
                    super.setStopTOD(BaseAlert.MIN_TOD);
                anytime = BaseAdminAlertsBean.isAnytime(this);
            }
            return anytime;
        }

        public void setAnytime(boolean anytime)
        {
            this.anytime = anytime;
            BaseAdminAlertsBean.onSetAnytime(this, anytime);
        }

        @Override
        public void setStartTOD(Integer startTOD)
        {
            if (!isAnytime())
                super.setStartTOD(startTOD);
            else
                super.setStartTOD(BaseAlert.MIN_TOD);
        }

        @Override
        public void setStopTOD(Integer stopTOD)
        {
            if (!isAnytime())
                super.setStopTOD(stopTOD);
            else
                super.setStopTOD(BaseAlert.MIN_TOD);
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
