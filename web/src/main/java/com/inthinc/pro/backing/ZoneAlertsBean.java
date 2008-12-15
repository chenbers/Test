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
import com.inthinc.pro.dao.ZoneDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.ZoneAlert;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.MiscUtil;

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
    private ZoneDAO                   zoneDAO;
    private ArrayList<SelectItem>     zones;

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
        alertView.setZoneDAO(zoneDAO);
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
            if (!Boolean.TRUE.equals(alert.getArrival()) && !Boolean.TRUE.equals(alert.getDeparture()) && !Boolean.TRUE.equals(alert.getDriverIDViolation())
                    && !Boolean.TRUE.equals(alert.getIgnitionOn()) && !Boolean.TRUE.equals(alert.getIgnitionOff()) && !Boolean.TRUE.equals(alert.getPosition())
                    && !Boolean.TRUE.equals(alert.getSeatbeltViolation()) && ((alert.getSpeedLimit() == null) || (alert.getSpeedLimit() == 0))
                    && !Boolean.TRUE.equals(alert.getSpeedViolation()) && !Boolean.TRUE.equals(alert.getMasterBuzzer()) && !Boolean.TRUE.equals(alert.getCautionArea())
                    && !Boolean.TRUE.equals(alert.getDisableRF()) && !Boolean.TRUE.equals(alert.getMonitorIdle()))
            {
                final String summary = MessageUtil.formatMessageString("editZoneAlert_noAlerts");
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                context.addMessage("edit-form:arrival", message);
                valid = false;
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
        if (zones == null)
        {
            zones = new ArrayList<SelectItem>();
            final List<Zone> list = zoneDAO.getZones(getAccountID());
            for (final Zone zone : list)
                zones.add(new SelectItem(zone.getZoneID(), zone.getName()));
            MiscUtil.sortSelectItems(zones);
        }
        return zones;
    }

    public static class ZoneAlertView extends ZoneAlert implements BaseAdminAlertsBean.BaseAlertView
    {
        @Column(updateable = false)
        private static final long serialVersionUID = 8372507838051791866L;

        @Column(updateable = false)
        private ZoneDAO           zoneDAO;
        @Column(updateable = false)
        private Zone              zone;
        @Column(updateable = false)
        private boolean           anytime;
        @Column(updateable = false)
        private boolean           selected;

        public Integer getId()
        {
            return getZoneAlertID();
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
                anytime = BaseAdminAlertsBean.isAnytime(this);
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

        public boolean isSpeedLimitSelected()
        {
            return (getSpeedLimit() != null) && (getSpeedLimit() > 0);
        }

        public void setSpeedLimitSelected(boolean speedLimitSelected)
        {
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
