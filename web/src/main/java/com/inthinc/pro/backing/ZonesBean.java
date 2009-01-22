package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.inthinc.pro.dao.ZoneAlertDAO;
import com.inthinc.pro.dao.ZoneDAO;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.util.MessageUtil;

public class ZonesBean extends BaseBean
{
    private List<Zone>           zones;
    private Map<String, Integer> zoneIDs;
    private Zone                 item;
    private boolean              editing;
    private ZoneDAO              zoneDAO;
    private ZoneAlertDAO         zoneAlertDAO;

    public void setZoneDAO(ZoneDAO zoneDAO)
    {
        this.zoneDAO = zoneDAO;
    }

    public void setZoneAlertDAO(ZoneAlertDAO zoneAlertDAO)
    {
        this.zoneAlertDAO = zoneAlertDAO;
    }

    public List<Zone> getZones()
    {
        if (zones == null)
        {
            zones = zoneDAO.getZones(getAccountID());
            if (zones.isEmpty())
                zones = new ArrayList<Zone>();
            sortZones();
        }
        return zones;
    }

    public Map<String, Integer> getZoneIDs()
    {
        if (zoneIDs == null)
        {
            zoneIDs = new LinkedHashMap<String, Integer>();
            for (final Zone zone : getZones())
                zoneIDs.put(zone.getName(), zone.getZoneID());
        }

        return zoneIDs;
    }

    private void sortZones()
    {
        Collections.sort(zones, new Comparator<Zone>()
        {
            @Override
            public int compare(Zone o1, Zone o2)
            {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
    }

    public int getZonesCount()
    {
        return getZoneIDs().size();
    }

    /**
     * Called when the user chooses to add an item.
     */
    public void add()
    {
        item = new Zone();
        item.setCreated(new Date());
        editing = true;
    }

    /**
     * Called when the user chooses to edit an item.
     */
    public void edit()
    {
        editing = true;
    }

    /**
     * Called when the user chooses to cancel editing.
     */
    public void cancelEdit()
    {
        editing = false;
        if (isAdd())
            item = null;
    }

    /**
     * Called when the user clicks to save changes when adding or editing.
     */
    public void save()
    {
        // validate
        if (!validate())
            return;

        final boolean add = isAdd();

        final FacesContext context = FacesContext.getCurrentInstance();

        if (add)
        {
            item.setGroupID(getUser().getGroupID());
            item.setZoneID(zoneDAO.create(getAccountID(), item));
        }
        else
        {
            item.setGroupID(getUser().getGroupID());
            zoneDAO.update(item);
        }

        // add a message
        final String summary = MessageUtil.formatMessageString(add ? "zone_added" : "zone_updated", item.getName());
        final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        context.addMessage(null, message);

        if (add)
        {
            zones.add(item);
            sortZones();
        }
        zoneIDs = null;

        editing = false;
    }

    /**
     * Called when the user chooses to delete one or more selected items.
     */
    public void delete()
    {
        final FacesContext context = FacesContext.getCurrentInstance();

        zoneAlertDAO.deleteByZoneID(item.getZoneID());

        zoneDAO.deleteByID(item.getZoneID());

        // add a message
        final String summary = MessageUtil.formatMessageString("zone_deleted", item.getName());
        final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        context.addMessage(null, message);

        zones.remove(item);
        zoneIDs = null;
        item = null;
    }

    public Integer getItemID()
    {
        if (getItem() != null)
            return item.getZoneID();
        return null;
    }

    public void setItemID(Integer itemID)
    {
        item = null;
        if (itemID != null)
            for (final Zone zone : getZones())
                if (zone.getZoneID() != null && zone.getZoneID().equals(itemID))
                {
                    item = zone;
                    break;
                }
    }

    public Zone getItem()
    {
        if ((item == null) && (getZoneIDs().size() > 0))
            item = zones.get(0);
        return item;
    }

    public String getPointsString()
    {
        if (getItem() != null)
            return item.getPointsString();
        return null;
    }

    public void setPointsString(String pointsString)
    {
        if (editing && (item != null) && (pointsString != null))
            try
            {
                item.setPointsString(pointsString);
            }
            catch (IllegalArgumentException e)
            {
                final String summary = MessageUtil.formatMessageString("zone_missingPoints", item.getName());
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                FacesContext.getCurrentInstance().addMessage(null, message);
                throw e;
            }
    }

    public String getAddress()
    {
        if (getItem() != null)
            return item.getAddress();
        return null;
    }

    public void setAddress(String address)
    {
        // the address isn't settable from the hidden field
    }

    /**
     * @return Whether the current edit operation is an item add.
     */
    public boolean isAdd()
    {
        return (item != null) && (item.getZoneID() == null);
    }

    public boolean isEditing()
    {
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
    private boolean validate()
    {
        if (item != null)
        {
            final FacesContext context = FacesContext.getCurrentInstance();
            if ((item.getPoints() == null) || (item.getPoints().size() == 0))
            {
                final String summary = MessageUtil.formatMessageString("zone_missing", item.getName());
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                context.addMessage(null, message);
                return false;
            }
            else if (item.getPoints().size() <= 3)
            {
                final String summary = MessageUtil.formatMessageString("zone_missingPoints", item.getName());
                final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
                context.addMessage(null, message);
                return false;
            }
        }
        return true;
    }
}
