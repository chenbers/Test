package com.inthinc.pro.reports.asset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.assets.AssetWarrantyRecord;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.asset.model.Warranty;
import com.inthinc.pro.reports.dao.WaysmartDAO;

/**
 * Report Criteria for WarrantyList report.
 */
public class WarrantyListReportCriteria extends ReportCriteria {

	protected String CUSTOMER_NAME_KEY = "customerName";
	protected String CUSTOMER_ID_KEY = "customerId";
	
    protected DateTimeFormatter dateTimeFormatter;
    protected GroupDAO groupDAO;
    protected WaysmartDAO waysmartDAO;
    
    /**
     * Default constructor.
     * 
     * @param locale The user locale.
     */
    public WarrantyListReportCriteria(Locale locale) {
        super(ReportType.WARRANTY_LIST, "", locale);
        dateTimeFormatter = DateTimeFormat.forPattern(ReportCriteria.DATE_FORMAT).withLocale(locale);
    }

    /**
     * Retrieves the Warranty List records from the back end and copies them into Jasper beans.
     * 
     * @param groupID The group ID.
     * @param accountID The customer ID.
     * @param accountName The customer account name.
     * @param expiredOnly True to show expired warranties only.
     */
    public void init(GroupHierarchy accountGroupHierarchy, Integer groupID, 
            Integer accountID, String accountName, boolean expiredOnly) {
        
        List<Group> groupList = accountGroupHierarchy.getSubGroupList(groupID);
        
        Map<Integer, List<AssetWarrantyRecord>> recordMap = 
            new HashMap<Integer, List<AssetWarrantyRecord>>();
        for (Group group : groupList) {            
            List<AssetWarrantyRecord> warrantyList = waysmartDAO.getWarrantyList(group.getGroupID(), expiredOnly);
            if (warrantyList != null) {
                recordMap.put(group.getGroupID(), warrantyList);
            }
        }
        // Add all parameters
        addParameter(CUSTOMER_NAME_KEY, accountName);
        addParameter(CUSTOMER_ID_KEY, accountID.toString());
        initDataSet(accountGroupHierarchy, recordMap);
    }
    
    /**
     * Copies the records retrieved from the back end into the beans to be used by Jasper.
     * 
     * @param groupHierarchy The group hierarchy.
     * @param recordMap A map with the records retrieved from the back end.
     */
    private void initDataSet(GroupHierarchy groupHierarchy, Map<Integer, List<AssetWarrantyRecord>> recordMap) {
        List<Warranty> beanList = new ArrayList<Warranty>();

        for (Entry<Integer, List<AssetWarrantyRecord>> entry : recordMap.entrySet()) {
            String groupName = groupHierarchy.getShortGroupName(entry.getKey(), SLASH_GROUP_SEPERATOR);
            
            for (AssetWarrantyRecord rec : entry.getValue()) {
                Warranty bean = new Warranty();
                bean.setGroupName(groupName);
                bean.setVehicleName(rec.getVehicleName());
                bean.setImei(rec.getImei());
                bean.setWarrantyStartDate(dateTimeFormatter.print(rec.getStartDate().getTime()));
                bean.setWarrantyEndDate(dateTimeFormatter.print(rec.getEndDate().getTime()));
                bean.setExpired(rec.isExpired());
                beanList.add(bean);
            }
        }
        setMainDataset(beanList);
    }

    /**
     * The groupDAO getter.
     * @return the groupDAO
     */
    public GroupDAO getGroupDAO() {
        return this.groupDAO;
    }
    

    /**
     * The waysmartDAO getter.
     * @return the waysmartDAO
     */
    public WaysmartDAO getWaysmartDAO() {
        return this.waysmartDAO;
    }
    

    /**
     * The groupDAO setter.
     * @param groupDAO the groupDAO to set
     */
    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    /**
     * The waysmartDAO setter.
     * @param waysmartDAO the waysmartDAO to set
     */
    public void setWaysmartDAO(WaysmartDAO waysmartDAO) {
        this.waysmartDAO = waysmartDAO;
    }    
}
