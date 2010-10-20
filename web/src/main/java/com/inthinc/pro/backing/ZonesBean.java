package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.ajax4jsf.model.KeepAlive;


import com.inthinc.pro.dao.ZoneAlertDAO;
import com.inthinc.pro.dao.ZoneDAO;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.zone.option.ZoneAvailableOption;
import com.inthinc.pro.model.zone.option.ZoneOption;
import com.inthinc.pro.model.zone.option.type.OptionValue;
import com.inthinc.pro.util.FormUtil;
import com.inthinc.pro.util.MessageUtil;

@KeepAlive
public class ZonesBean extends BaseBean
{
    private List<Zone>           zones;
    private List<SelectItem>     zoneIDs;
    private Zone                 item;
    private boolean              editing;
    private ZoneDAO              zoneDAO;
    private ZoneAlertDAO         zoneAlertDAO;
    private String               helpFile = "Zones.htm";

    public void setZoneDAO(ZoneDAO zoneDAO)
    {
        this.zoneDAO = zoneDAO;
    }

    public void setZoneAlertDAO(ZoneAlertDAO zoneAlertDAO)
    {
        this.zoneAlertDAO = zoneAlertDAO;
    }

    public void loadZones()
    {
        zones = zoneDAO.getZones(getAccountID());
        if (zones.isEmpty())
            zones = new ArrayList<Zone>();
        sortZones();
        
        zoneIDs = new ArrayList<SelectItem>();
        for(final Zone zone: getZones())
        {
            zoneIDs.add(new SelectItem(zone.getZoneID(),zone.getName()));
        }
        //zoneIDs.add(0,new SelectItem(null,""));
        
    }
    
    public List<Zone> getZones()
    {
        if (zones == null)
        {
            loadZones();
        }
        return zones;
    }

    public List<SelectItem> getZoneIDs()
    {
        if (zoneIDs == null)
        {
            loadZones();
        }

        return zoneIDs;
    }

    public String getHelpFile()
    {
        return helpFile;
    }

    public void setHelpFile(String helpFile)
    {
        this.helpFile = helpFile;
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
    public String add()
    {
        item = new Zone();
        item.setCreated(new Date());
        editing = true;
        helpFile = "Admin_Add_Zone.htm";
        return "adminEditZone";
    }

    /**
     * Called when the user chooses to edit an item.
     */
    public String edit()
    {
        editing = true;
        helpFile = "Admin_Add_Zone.htm";
        return "adminEditZone";
    }

    /**
     * Called when the user chooses to cancel editing.
     */
    public String cancelEdit()
    {
        editing = false;
        helpFile = "Zones.htm";
        if (isAdd())
            item = null;
        
        UIComponent uiComponent = FacesContext.getCurrentInstance().getViewRoot().findComponent("zones-form");
        FormUtil.resetForm((UIForm)uiComponent);
        
        return "adminZones";
    }
    
   
    /**
     * Called when the user clicks to save changes when adding or editing.
     */
    public String save()
    {
        // validate
        if (!validate())
            return null;

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
        helpFile = "Zones.htm";
        
        // reload the zones for the account that are carried by proUser
        getProUser().setZones(zones);

        return "adminZones";
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
        
        // reload the zones for the account that are carried by proUser
        getProUser().setZones(zones);        
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
    
    public void clearZones(){
        zoneIDs = null;
        zones = null;
    }
    
    public Map<ZoneAvailableOption, OptionValue> getOptionsMap()
    {
        Map<ZoneAvailableOption, OptionValue> optionsMap = new HashMap<ZoneAvailableOption, OptionValue>();
        List<ZoneOption> options = item.getOptions();
        for (ZoneAvailableOption availOption : ZoneAvailableOption.values())
        {
            OptionValue value = availOption.getDefaultValue();
            if (options != null) {
                for (ZoneOption zoneOption : options) {
                    if (zoneOption.getOption() == availOption) {
                        value = zoneOption.getValue();
                    }
                }
            }
            optionsMap.put(availOption, value);
        }
        return optionsMap;
    }


    
    public ZoneAvailableOption[] getAvailableZoneOptions()
    {
        return ZoneAvailableOption.values();
    }
    
}
