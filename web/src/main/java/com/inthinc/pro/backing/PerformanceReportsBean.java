package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.ajax4jsf.model.KeepAlive;
import org.apache.log4j.Logger;

import com.inthinc.pro.reports.ReportCategory;
import com.inthinc.pro.reports.ReportGroup;
import com.inthinc.pro.util.MessageUtil;

@KeepAlive
public class PerformanceReportsBean extends ReportsBean {
    
    private static Logger logger = Logger.getLogger(PerformanceReportsBean.class);

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
        
        String catLabel = MessageUtil.getMessageString(ReportCategory.DriverPerformance.toString());
        String mileageLabel = MessageUtil.getMessageString(ReportCategory.Mileage.toString());
        itemGroups.add(new SelectItemGroup(catLabel, 
        		catLabel, false, getItemsByCategory(ReportCategory.DriverPerformance, ReportGroup.DRIVER_PERFORMANCE_INDIVIDUAL, ReportGroup.DRIVER_PERFORMANCE_RYG_INDIVIDUAL)));
        itemGroups.add(new SelectItemGroup(mileageLabel, 
                catLabel, false, getItemsByCategory(ReportCategory.Mileage)));
        
        return itemGroups;
    }
    

    /**
     * Returns all the report types pertaining to a given Report Category. 
     * @param category Category of reports
     * @return Array of report types as Faces SelectItems
     */
	private SelectItem[] getItemsByCategory(ReportCategory category, ReportGroup... excludeItem) {
	    //logger.trace(String.format("Loading report into SelectItem[] for category %s", category.name()));
        List<SelectItem> items = new ArrayList<SelectItem>();
        for (ReportGroup rt : EnumSet.allOf(ReportGroup.class)) {
            //logger.trace(String.format("Evaluatin report %s", rt.name()));
            if (!rt.isCategory(category)) continue;
            //logger.trace(String.format("Report %s was in categroy", rt.name()));
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

}
