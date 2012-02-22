package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.ajax4jsf.model.KeepAlive;

import com.inthinc.pro.reports.ReportCategory;
import com.inthinc.pro.reports.ReportGroup;
import com.inthinc.pro.util.MessageUtil;

@KeepAlive
public class WaysmartReportsBean extends ReportsBean {

    private static final long serialVersionUID = 224700504785842562L;

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.backing.ReportsBean#getReportGroupMap()
     */
    @Override
    public Map<Integer, ReportGroup> getReportGroupMap() {
        return reportGroupMap;
    }

    /**
     * The setter for reportGroupMap.
     * @param reportGroupMap
     */
    public void setReportGroupMap(Map<Integer, ReportGroup> reportGroupMap) {
        this.reportGroupMap = reportGroupMap;
    }    
    

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.backing.ReportsBean#getReportGroups()
     */
    public List<SelectItemGroup> getReportGroups() {

    	// The map between and item and its ID
        reportGroupMap = new HashMap<Integer, ReportGroup>();
        
        // The items categorized in groups to be shown in the UI list
        List<SelectItemGroup> itemGroups = new ArrayList<SelectItemGroup>();

        itemGroups.add(getBlankGroup());
        
        String catLabel = MessageUtil.getMessageString(ReportCategory.Performance.toString());

        itemGroups.add(new SelectItemGroup(catLabel,catLabel, 
        		false, getItemsByCategory(ReportCategory.Performance)));
        
        catLabel = MessageUtil.getMessageString(ReportCategory.IFTA.toString());
        itemGroups.add(new SelectItemGroup(catLabel,catLabel, 
        		false, getItemsByCategory(ReportCategory.IFTA)));
        
        return itemGroups;
    }
    

    /**
     * Returns all the report types pertaining to a given Report Category. 
     * @param category Category of reports
     * @return Array of report types as Faces SelectItems
     */
	private SelectItem[] getItemsByCategory(ReportCategory category, ReportGroup... excludeItem) {
        List<SelectItem> items = new ArrayList<SelectItem>();
        for (ReportGroup rt : EnumSet.allOf(ReportGroup.class)) {
            if (!rt.isCategory(category)) continue;
            if (rt.getRequiresHOSAccount() && !getAccountIsHOS())
                continue;
            
            boolean exclude = false;
            for(int i=0;i<excludeItem.length;i++)
                if(excludeItem[i] == rt) {
                    exclude = true;
                    break;
                }
                    
                    
            if (!exclude) {
                items.add(new SelectItem(rt.getCode(), MessageUtil.getMessageString(rt.toString())));
                reportGroupMap.put(rt.getCode(), rt);
            }
        }
		return items.toArray(new SelectItem[0]);
	}
	/*
	 * {@inheritDoc}
	 * Filter the Groups for WaySmart device. 
	 * @see com.inthinc.pro.backing.BaseBean#getGroupHierarchy()
	 */
	/*// As decided - Tek removes the code filtering the group and show all the groups (because configuration can change)
	@Override
    public GroupHierarchy getGroupHierarchy() {
        GroupHierarchy full = super.getGroupHierarchy();
	    List<Group> filtered = checkGroupForWaysmart(full, full.getTopGroup());
        return new GroupHierarchy(filtered);
    }*/
	
    /*
     * Returns a list of the group and its subgroups if have WaySmart device.
     * @param hierarchy The GroupHierarchy to use to get children
     * @param group The current group to check
     * @return THe list with all the children and the parent having WaySmart device
     * /
    private List<Group> checkGroupForWaysmart(GroupHierarchy hierarchy, Group group) {
        List<Group> checkedList = new ArrayList<Group>();
        List<Group> children = hierarchy.getChildren(group);

        if (children == null) { // we are on a leaf
            if (hasWaysmartDevice(group)) {
                checkedList.add(group);
            }
        } else { // we are on a non-leaf node
            for (Group child : children) {
                checkedList.addAll(checkGroupForWaysmart(hierarchy, child));
            }
            if (!checkedList.isEmpty() || (checkedList.isEmpty() && hasWaysmartDevice(group))) {
                checkedList.add(group);
            }
        }
        return checkedList;

    }	
	
	private boolean hasWaysmartDevice(Group group) {
	    if(group != null){    
	        Integer accountID = group.getAccountID();
    	    List<Device> devices = getDeviceDAO().getDevicesByAcctID(accountID);
            for (Device device : devices) {
                if (device.isWaySmart()) {
                    return true;
                }
            }
	    }
        return false;
	} */

}
