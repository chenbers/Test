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

@SuppressWarnings("serial")
public class ZoneAlertsBean extends BaseAdminAlertsBean<ZoneAlertsBean.ZoneAlertView>
{
    private static final List<String> AVAILABLE_COLUMNS;
    private static final int[]        DEFAULT_COLUMN_INDICES = new int[] { 0, 1, 2, 3};
    private static final int[]        DEFAULT_ADMIN_COLUMN_INDICES = new int[] { 4 };

    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("name");
        AVAILABLE_COLUMNS.add("description");
        AVAILABLE_COLUMNS.add("zone");
        AVAILABLE_COLUMNS.add("status");
        AVAILABLE_COLUMNS.add("owner");     // only admins see this
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
//        final List<ZoneAlert> plainZoneAlerts = zoneAlertDAO.getZoneAlerts(getAccountID());
    	List<ZoneAlert> plainZoneAlerts = null;
    	if (getProUser().isAdmin()) {
    		plainZoneAlerts = zoneAlertDAO.getZoneAlertsByUserIDDeep(getUser().getUserID());
    	}
    	else {
    		plainZoneAlerts = zoneAlertDAO.getZoneAlertsByUserID(getUser().getUserID());
    	}

        // convert the ZoneAlerts to ZoneAlertViews
        final LinkedList<ZoneAlertView> items = new LinkedList<ZoneAlertView>();
        for (final ZoneAlert alert : plainZoneAlerts)
            items.add(createZoneAlertView(alert));

        return items;
    }
    
    @Override
    public String fieldValue(ZoneAlertView item, String column)
    {
        if("zone".equals(column)){
            if(item.getZone() != null)
                return item.getZone().getName();
        }
        return super.fieldValue(item, column);
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
        alertView.setAnytime(true);
     
        BeanUtils.copyProperties(alert, alertView);
        if(alertView.getStartTOD() == null)
            alertView.setStartTOD(BaseAlert.MIN_TOD);
        if(alertView.getStopTOD() == null)
            alertView.setStopTOD(BaseAlert.MIN_TOD);
        alertView.setAnytime(isAnytime(alertView));
        alertView.setZoneAlertsBean(this);
        alertView.setSelected(false);
        return alertView;
    }

    @Override
    public List<String> getAvailableColumns()
    {
        if (!getProUser().isAdmin()) {
            return AVAILABLE_COLUMNS.subList(0, 4);
        }
        return AVAILABLE_COLUMNS;
    }

    @Override
    public Map<String, Boolean> getDefaultColumns()
    {
        final HashMap<String, Boolean> columns = new HashMap<String, Boolean>();
        final List<String> availableColumns = getAvailableColumns();
        for (int i : DEFAULT_COLUMN_INDICES)
            columns.put(availableColumns.get(i), true);
        if (getProUser().isAdmin()) {
            for (int i : DEFAULT_ADMIN_COLUMN_INDICES)
                columns.put(availableColumns.get(i), true);
        }
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
        zonesBean.loadZones();
        
        if (zoneID != null)
            alert.setZoneID(Integer.valueOf(zoneID));
        else
        {
            final List<SelectItem> zones = getZones();
            if ((zones != null) && (zones.size() > 0))
                alert.setZoneID((Integer)zones.get(0).getValue());
        }
        alert.setAccountID(getAccountID());
        alert.setUserID(getUserID());
        return createZoneAlertView(alert);
    }

    @Override
    protected void doDelete(List<ZoneAlertView> deleteItems)
    {
        final FacesContext context = FacesContext.getCurrentInstance();

        for (final ZoneAlertView alert : deleteItems)
        {
            zoneAlertDAO.deleteByID(alert.getAlertID());

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
        
//System.out.println("ZoneAlert - fields");
//for (String key : updateField.keySet())
//	System.out.println(key + " " + updateField.get(key));
//System.out.println("ZoneAlert - fields end");

        final boolean defineAlerts = Boolean.TRUE.equals(updateField.get("defineAlerts"));
//System.out.println("defineAlerts = " + defineAlerts);
        updateField.put("arrival", defineAlerts);
        updateField.put("departure", defineAlerts);
        
/* 	not currently used        
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
*/
        
        if(isBatchEdit() && updateField.get("anytime"))
        {
            updateField.put("startTOD",true);
            updateField.put("stopTOD",true);
        }

        return super.save();
    }
    
    protected boolean validateSaveItem(ZoneAlertView saveItem)
    {
        final FacesContext context = FacesContext.getCurrentInstance();
        boolean valid = true;
        valid = super.validateSaveItem(saveItem);
        if ((!Boolean.TRUE.equals(saveItem.getArrival()) && !Boolean.TRUE.equals(saveItem.getDeparture())) 
                && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("defineAlerts"))))
        {
            final String summary = MessageUtil.formatMessageString("editZoneAlert_noAlerts");
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
            context.addMessage("edit-form:editZoneAlert-arrival", message);
            valid = false;
        }
        
        //Validate required name field
        if ((saveItem.getName() == null) || (saveItem.getName().length() == 0)
                && (!isBatchEdit() || (isBatchEdit() && getUpdateField().get("name"))))
        {
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessageString("required"), null);
            FacesContext.getCurrentInstance().addMessage("edit-form:editZoneAlert-name", message);
            valid = false;
        }
        
        return valid;
    }

    @Override
	public String getAlertPage() {

		return "editZoneAlert";
	}

	@Override
    protected ZoneAlertView revertItem(ZoneAlertView editItem)
    {
        return createZoneAlertView(zoneAlertDAO.findByID(editItem.getAlertID()));
    }

    @Override
    protected void doSave(List<ZoneAlertView> saveItems, boolean create)
    {
        final FacesContext context = FacesContext.getCurrentInstance();

        for (final ZoneAlertView alert : saveItems)
        {
            if(alert.isAnytime())
            {
                alert.setStartTOD(BaseAlert.MIN_TOD);
                alert.setStopTOD(BaseAlert.MIN_TOD);
            }
            
            if (create)
                alert.setAlertID(zoneAlertDAO.create(getAccountID(), alert));
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
        return "pretty:adminZoneAlert";
    }

    @Override
    protected String getEditRedirect()
    {
        return "pretty:adminEditZoneAlert";
    }

    @Override
    protected String getFinishedRedirect()
    {
        return "pretty:adminZoneAlerts";
    }
    
    @Override
    protected Boolean authorizeAccess(ZoneAlertView item)
    {
        Integer acctID = item.getAccountID();

        if (getGroupHierarchy().getTopGroup().getAccountID().equals(acctID))
        {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
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
    
    @Override
    public void resetList()
    {
        zonesBean.clearZones();
        super.resetList();
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

        @Override
        public Integer getId() {
            // TODO Auto-generated method stub
            return getAlertID();
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
            return anytime;
        }

        public void setAnytime(boolean anytime)
        {
            this.anytime = anytime;
        }

        @Override
        public void setStartTOD(Integer startTOD)
        {
            if(startTOD == null)
            {
                super.setStartTOD(BaseAlert.MIN_TOD);
            }
            else
            {
                super.setStartTOD(startTOD);
            }
        }
        
        @Override
        public void setStopTOD(Integer stopTOD)
        {
          
            if(stopTOD == null)
            {
                super.setStopTOD(BaseAlert.MIN_TOD);
            }
            else
            {
                super.setStopTOD(stopTOD);
            }
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
