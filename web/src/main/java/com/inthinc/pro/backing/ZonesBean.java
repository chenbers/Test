package com.inthinc.pro.backing;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

import org.ajax4jsf.model.KeepAlive;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.dao.RedFlagAlertDAO;
import com.inthinc.pro.dao.ZoneDAO;
import com.inthinc.pro.dao.ZonePublishDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.zone.ZonePublish;
import com.inthinc.pro.model.zone.ZonePublisher;
import com.inthinc.pro.model.zone.option.ZoneAvailableOption;
import com.inthinc.pro.model.zone.option.ZoneOption;
import com.inthinc.pro.model.zone.option.type.OffOn;
import com.inthinc.pro.model.zone.option.type.OffOnDevice;
import com.inthinc.pro.model.zone.option.type.OptionValue;
import com.inthinc.pro.model.zone.option.type.ZoneVehicleType;
import com.inthinc.pro.util.FormUtil;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.SelectItemUtil;

@KeepAlive
public class ZonesBean extends BaseBean
{
    private List<Zone>           zones;
    private List<SelectItem>     zoneIDs;
    private Zone                 item;
    private boolean              editing;
    private ZoneDAO              zoneDAO;
    private RedFlagAlertDAO      zoneAlertDAO;
    private ZonePublishDAO       zonePublishDAO;
    private ZoneVehicleType      downloadType;
    private String               message;
    
    
    public String getPublishInfo() {
        Account account = getAccountDAO().findByID(getAccountID());
        Date lastPublishDate = (account.getZonePublishDate() == null) ? new Date(0) : account.getZonePublishDate();
        DateTimeFormatter fmt = DateTimeFormat.forPattern(MessageUtil.getMessageString("dateTimeFormat", getLocale())).withLocale(getLocale());
        return MessageUtil.formatMessageString("ZonesPublishInfo", fmt.print(lastPublishDate.getTime()));
    }

    public void setZoneDAO(ZoneDAO zoneDAO)
    {
        this.zoneDAO = zoneDAO;
    }

    public void setRedFlagAlertDAO(RedFlagAlertDAO zoneAlertDAO)
    {
        this.zoneAlertDAO = zoneAlertDAO;
    }
    public ZonePublishDAO getZonePublishDAO() {
        return zonePublishDAO;
    }

    public void setZonePublishDAO(ZonePublishDAO zonePublishDAO) {
        this.zonePublishDAO = zonePublishDAO;
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
        item.setStatus(Status.ACTIVE);
        editing = true;
        return "adminEditZone";
    }

    /**
     * Called when the user chooses to edit an item.
     */
    public String edit()
    {
        editing = true;
        return "adminEditZone";
    }

    /**
     * Called when the user chooses to cancel editing.
     */
    public String cancelEdit()
    {
        editing = false;
        if (isAdd())
            item = null;
        if(item != null)
            item.setOptionsMap(null);
        
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
        
        item.setOptions(getOptionsFromMap());

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
        item.setModified(new Date());

        if (add)
        {
            Zone newItem = zoneDAO.findByID(item.getZoneID());
            zones.add(newItem);
            item = null;
        }

        editing = false;
        
        reloadZones();

        return "adminZones";
}

    /**
     * Called when the user chooses to delete one or more selected items.
     */
    public void delete()
    {

        zoneAlertDAO.deleteAlertsByZoneID(item.getZoneID());

        zoneDAO.deleteByID(item.getZoneID());

        // add a message
        final String summary = MessageUtil.formatMessageString("zone_deleted", item.getName());
        final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        final FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, message);

        zones.remove(item);
        item = null;
        reloadZones();
        
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
    private List<ZoneOption> getOptionsFromMap() 
    {
        
        List<ZoneOption> options = new ArrayList<ZoneOption>();
        Map<ZoneAvailableOption, OptionValue> optionsMap = getItem().getOptionsMap();
        for (ZoneAvailableOption availOption : ZoneAvailableOption.values())
        {
            options.add(new ZoneOption(availOption, optionsMap.get(availOption)));
        }
        return options;
    }


    public ZoneAvailableOption[] getAvailableZoneOptions()
    {
        return ZoneAvailableOption.values();
    }
    public List<SelectItem> getOffOnSelectItems()
    {
        return SelectItemUtil.toList(OffOn.class, false);
    }
    public List<SelectItem> getOffOnDeviceSelectItems()
    {
        return SelectItemUtil.toList(OffOnDevice.class, false);
    }
    public List<SelectItem> getVehicleTypeSelectItems()
    {
        return SelectItemUtil.toList(ZoneVehicleType.class, false);
    }
    
    public String reset()
    {
        
        if (isAdd()) {
            item = new Zone();
            item.setCreated(new Date());
        }
        else {
            zones.remove(item);
            item = zoneDAO.findByID(item.getZoneID());
            zones.add(item);
            
        }
        
        return "adminEditZone";
        
    }

    public void cloneZone()
    {
        Zone clonedZone = item.clone();
        clonedZone.setName(constructCloneName(item.getName()));

        // add a message
        final String summary = MessageUtil.formatMessageString("zone_cloned", item.getName(), clonedZone.getName());
        final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        final FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, message);

        item = clonedZone;
        item.setGroupID(getUser().getGroupID());
        item.setZoneID(zoneDAO.create(getAccountID(), item));
        zones.add(item);
        reloadZones();
    }

    private String constructCloneName(String name) {
        for (int cnt = 1; cnt < 100; cnt++) {
            String cloneName = name + "(" + cnt + ")";
            if (!nameFound(cloneName))
                return cloneName;
        }
        return name;
    }

    private boolean nameFound(String cloneName) {
        for (Zone zone : zones) {
            if (zone.getName().equalsIgnoreCase(cloneName))
                return true;
        }
        return false;
    }
    private void reloadZones() {
        sortZones();
        zoneIDs = new ArrayList<SelectItem>();
        for(final Zone zone: getZones())
        {
            zoneIDs.add(new SelectItem(zone.getZoneID(),zone.getName()));
        }
        
        // reload the zones for the account that are carried by proUser
        getProUser().setZones(zones);
        
    }

/*
    public Boolean getEnablePublish() {
        if (enablePublish == null)
            initEnablePublish();
        return enablePublish;
    }
    
    private void initEnablePublish() {
        Account account = getAccountDAO().findByID(getAccountID());
        Date lastPublishDate = (account.getZonePublishDate() == null) ? new Date(0) : account.getZonePublishDate();

        setEnablePublish(false);
        
        for (Zone zone : getZones()) {
            if ((zone.getCreated() != null && zone.getCreated().after(lastPublishDate)) || 
                (zone.getModified() != null && zone.getModified().after(lastPublishDate)))
                setEnablePublish(true);
        }
    }
    public void setEnablePublish(Boolean enablePublish) {
        this.enablePublish = enablePublish;
    }
    
    public void publish() throws IOException {
        setMessage(null);        
        ZonePublisher zonePublisher = new ZonePublisher();

        for (ZoneVehicleType zoneVehicleType : ZoneVehicleType.values()) {
            
            ZonePublish zonePublish = new ZonePublish();
            zonePublish.setAcctID(getAccountID());
            zonePublish.setZoneVehicleType(zoneVehicleType);
            zonePublish.setPublishZoneData(zonePublisher.publish(getZones(),  zoneVehicleType));
            zonePublishDAO.publishZone(zonePublish);
        }
        
        zoneDAO.publishZones(getAccountID());
        
        setEnablePublish(false);
        setMessage("zones_publishMsg");
        
    }
*/

    
    private boolean zonesNeedPublish() {
        Account account = getAccountDAO().findByID(getAccountID());
        Date lastPublishDate = (account.getZonePublishDate() == null) ? new Date(0) : account.getZonePublishDate();

        for (Zone zone : getZones()) {
            if ((zone.getCreated() != null && zone.getCreated().after(lastPublishDate)) || 
                (zone.getModified() != null && zone.getModified().after(lastPublishDate)))
                return true;
        }
        return false;
    }

    private void publishToDatabase() throws IOException {
        setMessage(null);        
        ZonePublisher zonePublisher = new ZonePublisher();

        for (ZoneVehicleType zoneVehicleType : ZoneVehicleType.values()) {
            
            ZonePublish zonePublish = new ZonePublish();
            zonePublish.setAcctID(getAccountID());
            zonePublish.setZoneVehicleType(zoneVehicleType);
            zonePublish.setPublishZoneData(zonePublisher.publish(getZones(),  zoneVehicleType));
            zonePublishDAO.publishZone(zonePublish);
        }
        
        
        setMessage("zones_publishMsg");
        
    }

    public void download() throws IOException {
        // make sure all zone edits are in the database published zone blob before download
        if (zonesNeedPublish())
            publishToDatabase();
        
        setMessage(null);        
        ZonePublish zonePublish = zonePublishDAO.getPublishedZone(getAccountID(), getDownloadType());
        if (zonePublish == null || zonePublish.getPublishZoneData() == null) {
            setMessage("zones_downloadError");
            return;
        }
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();
        
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment; filename=\"Zones.md5_download.bin\"");

        OutputStream out = null;
        DataOutputStream dataOut = null;

        try
        {
            out = response.getOutputStream();
            dataOut = new DataOutputStream(out);
            dataOut.write(zonePublish.getPublishZoneData());
            dataOut.flush();
            out.flush();
            facesContext.responseComplete();
        }
        finally
        {
            out.close();
        }
        
    }
    
    public ZoneVehicleType getDownloadType() {
        if (downloadType == null)
            return ZoneVehicleType.ALL;
        return downloadType;
    }

    public void setDownloadType(ZoneVehicleType downloadType) {
        this.downloadType = downloadType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
