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
public class HosReportsBean extends ReportsBean {
    

    @Override
    public List<SelectItemGroup> getReportGroups() {

        // The map between an item and its ID
        reportGroupMap = new HashMap<Integer, ReportGroup>();
        
        // The items categorized in groups to be shown in the UI list
        List<SelectItemGroup> itemGroups = new ArrayList<SelectItemGroup>();

        itemGroups.add(getBlankGroup());
        
        String catLabel = MessageUtil.getMessageString(ReportCategory.HOS.toString());

        itemGroups.add(new SelectItemGroup(catLabel,catLabel, false, getItemsByCategory(ReportCategory.HOS, true)));

        return itemGroups;
    }
    

    /**
     * Returns all the report types pertaining to a given Report Category.
     * HOS only. 
     * @param category Category of reports
     * @return Array of report types as Faces SelectItems
     */
    private SelectItem[] getItemsByCategory(ReportCategory category, boolean catRequiresHOS) {
        List<SelectItem> items = new ArrayList<SelectItem>();
        for (ReportGroup rt : EnumSet.allOf(ReportGroup.class)) {
            if (!rt.isCategory(category)) continue;
            if (catRequiresHOS && !rt.getRequiresHOSAccount())
                continue;
            items.add(new SelectItem(rt.getCode(), MessageUtil.getMessageString(rt.toString())));
            reportGroupMap.put(rt.getCode(), rt);
        }
        return items.toArray(new SelectItem[0]);
    }
    

    
    public Map<Integer, ReportGroup> getReportGroupMap() {
        return reportGroupMap;
    }

    public void setReportGroupMap(Map<Integer, ReportGroup> reportGroupMap) {
        this.reportGroupMap = reportGroupMap;
    }

    
}
