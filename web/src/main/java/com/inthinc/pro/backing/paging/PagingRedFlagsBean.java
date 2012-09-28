package com.inthinc.pro.backing.paging;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.ajax4jsf.model.KeepAlive;
import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;

import com.inthinc.pro.backing.LocaleBean;
import com.inthinc.pro.dao.AlertMessageDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.RedFlagAlertDAO;
import com.inthinc.pro.model.AlertMessage;
import com.inthinc.pro.model.AlertSentStatus;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.RedFlagReportItem;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.event.StatusEvent;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.table.BasePaginationTable;
import com.inthinc.pro.table.model.provider.RedFlagPaginationTableDataProvider;
import com.inthinc.pro.util.MessageUtil;
@KeepAlive
public class PagingRedFlagsBean extends BasePagingNotificationsBean<RedFlag> {
	


    /**
	 * 
	 */
	private static final long serialVersionUID = 3166689931697428969L;
	private static final Logger logger = Logger.getLogger(PagingRedFlagsBean.class);
    
	private RedFlagPaginationTableDataProvider tableDataProvider;
	private BasePaginationTable<RedFlag> table;
	
	static final List<EventCategory> CATEGORIES;
	static {
		CATEGORIES = new ArrayList<EventCategory>();
		CATEGORIES.add(EventCategory.VIOLATION);
		CATEGORIES.add(EventCategory.EMERGENCY);
		CATEGORIES.add(EventCategory.WARNING);
        CATEGORIES.add(EventCategory.ZONE);
        CATEGORIES.add(EventCategory.HOS);
        CATEGORIES.add(EventCategory.TEXT);
        CATEGORIES.add(EventCategory.DVIR);
        
	}

	@Override
	protected List<EventCategory> getCategories() {
		return CATEGORIES;
	}
	
	private String filterLevel;
	private String filterAlert;
	
	private AlertMessageDAO alertMessageDAO;
	private RedFlagAlertDAO redFlagAlertDAO;

    private PersonDAO personDAO;
	

    private RedFlag sentDetailsItem;
    private List<SelectItem> alertItems;
    private RedFlagEscalationDetails details;
    private Map<Integer, RedFlagEscalationDetails> detailsMap;
    private Integer selectedAlertID;
    

    
    public Map<Integer, RedFlagEscalationDetails> getDetailsMap() {
        return detailsMap;
    }

    public void setDetailsMap(Map<Integer, RedFlagEscalationDetails> detailsMap) {
        this.detailsMap = detailsMap;
    }

    public RedFlagEscalationDetails getDetails() {
        return details;
    }

    public void setDetails(RedFlagEscalationDetails details) {
        this.details = details;
    }


    public Integer getSelectedAlertID() {
        return selectedAlertID;
    }

    public void setSelectedAlertID(Integer selectedAlertID) {
        this.selectedAlertID = selectedAlertID;
        details = detailsMap.get(selectedAlertID);
        
    }

    public List<SelectItem> getAlertItems() {
        if (alertItems == null) {
            alertItems = new ArrayList<SelectItem>();
            alertItems.add(new SelectItem(null, ""));
        }
        return alertItems;
    }

    public void setAlertItems(List<SelectItem> alertItems) {
        this.alertItems = alertItems;
    }

    public RedFlag getSentDetailsItem() {
        return sentDetailsItem;
    }

    // TODO: TEMPORARY FOR TESTING UNTIL THE MESSAGE ID IS POPULUATED 
    static int randomInt(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    public void setSentDetailsItem(RedFlag sentDetailsItem) {
        // TODO: TEMPORARY FOR TESTING UNTIL THE MESSAGE ID IS POPULUATED 
//        List<Integer> msgIDList = new ArrayList<Integer>();
//        msgIDList.add(Integer.valueOf(50584));
//        msgIDList.add(Integer.valueOf(50937));
//        msgIDList.add(Integer.valueOf(randomInt(1,100)));
//        msgIDList.add(Integer.valueOf(randomInt(1,100)));
//        msgIDList.add(Integer.valueOf(42));// this one has status of ESCALATED_AWAITING_ACK
//        sentDetailsItem.setMsgIDList(msgIDList);
        // TODO: DONE
        this.sentDetailsItem = sentDetailsItem;
        
        List<Integer> redFlagMsgIDList = sentDetailsItem.getMsgIDList();
     
        detailsMap = new HashMap<Integer, RedFlagEscalationDetails>();
        alertItems = new ArrayList<SelectItem>();
        alertItems.add(new SelectItem(null, ""));
        for (Integer msgID : redFlagMsgIDList) {
            AlertMessage message = alertMessageDAO.findByID(msgID);
            
            if (detailsMap.get(message.getAlertID()) == null) {
                RedFlagEscalationDetails details = new RedFlagEscalationDetails(alertMessageDAO, redFlagAlertDAO, personDAO, sentDetailsItem, message);
                alertItems.add(new SelectItem(details.getAlert().getAlertID(), details.getAlert().getName()));
                detailsMap.put(details.getAlert().getAlertID(), details);
            }
            else {
                RedFlagEscalationDetails details = detailsMap.get(message.getAlertID());
                details.addMessage(message);
            }
        }
    }



    public String getFilterAlert() {
		return filterAlert;
	}

	public void setFilterAlert(String filterAlert) {
		this.filterAlert = filterAlert;
	}

	public String getFilterLevel() {
		return filterLevel;
	}

	public void setFilterLevel(String filterLevel) {
		this.filterLevel = filterLevel;
	}

    private List<SelectItem> filterLevels;
	public List<SelectItem>  getFilterLevels() {
		if (filterLevels == null) {
	    	filterLevels = new ArrayList<SelectItem> ();
			SelectItem blankItem = new SelectItem("", BLANK_SELECTION);
			blankItem.setEscape(false);
    		filterLevels.add(blankItem);
	        for (RedFlagLevel p : EnumSet.allOf(RedFlagLevel.class)) {
	        	if (p.equals(RedFlagLevel.NONE))
	        		continue;
	    		filterLevels.add(new SelectItem(p.getCode().toString(), MessageUtil.getMessageString(p.toString(), getLocale())));
	    	}
		}
	    
	    return filterLevels;
    }

    private List<SelectItem> filterAlerts;
	public List<SelectItem> getFilterAlerts() {
		if (filterAlerts == null) {
			filterAlerts = new ArrayList<SelectItem>();
			SelectItem blankItem = new SelectItem("", BLANK_SELECTION);
			blankItem.setEscape(false);
			filterAlerts.add(blankItem);
            for (AlertSentStatus p : EnumSet.allOf(AlertSentStatus.class)) {
                filterAlerts.add(new SelectItem(p.getCode().toString(), MessageUtil.getMessageString(p.toString(), getLocale())));
            }
		}
	    
	    return filterAlerts;
    }
 	@Override
	public void init()
	{
		super.init();
		
		logger.debug("PagingRedFlagsBean - init");
		
        
		tableDataProvider.setDateTimeZone(DateTimeZone.forTimeZone(getUser().getPerson().getTimeZone()));
        tableDataProvider.setSort(new TableSortField(SortOrder.DESCENDING, "time"));
        tableDataProvider.getTimeFrameBean().setYearSelection(true);
		
		table = new BasePaginationTable<RedFlag>();
		table.initModel(tableDataProvider);
	}


    @Override
    protected List<RedFlagReportItem> getReportTableData()
    {
        List<RedFlagReportItem> redFlagReportItemList = new ArrayList<RedFlagReportItem>();
        
        List<RedFlag> redFlagList = new ArrayList<RedFlag>();
       	redFlagList = this.getReportRedFlags();
        
       	MeasurementType measurementType = this.getMeasurementType();
        String mphString = MessageUtil.getMessageString(measurementType.toString()+"_mph");
        String miString  = MessageUtil.getMessageString(measurementType.toString()+"_miles");
        String dateFormatString = MessageUtil.getMessageString("dateTimeFormat", LocaleBean.getCurrentLocale());
		        
        for (RedFlag redFlag : redFlagList)
        {
            String detailsFormatStr = MessageUtil.getMessageString("redflags_details" + redFlag.getEvent().getEventType());
            if (redFlag.getEvent().getDriverName() == null || redFlag.getEvent().getDriverName().isEmpty()) {
            	redFlag.getEvent().setDriverName(MessageUtil.getMessageString("unknown_driver"));
            }

            String statusString = null;
            if (redFlag.getEvent() instanceof StatusEvent) {
                statusString = MessageUtil.getMessageString(((StatusEvent)redFlag.getEvent()).getStatusMessageKey());
            }
            redFlagReportItemList.add(new RedFlagReportItem(redFlag, getMeasurementType(), dateFormatString, detailsFormatStr, (statusString == null) ? mphString : statusString, miString, LocaleBean.getCurrentLocale()));
        }
        return redFlagReportItemList;
    }

    protected List<RedFlag> getReportRedFlags() {
		int totalCount = getTableDataProvider().getRowCount();
		if (totalCount == 0)
			return new ArrayList<RedFlag>();
		
		return getTableDataProvider().getItemsByRange(0, totalCount);
	}

	public void refreshAction(){
		table.reset();
    }
	
	
	@Override
	public void refreshPage(){
		table.resetPage();
    }

    @Override
	protected ReportCriteria getReportCriteria() {
	    ReportCriteria reportCriteria = getReportCriteriaService().getRedFlagsReportCriteria(getUser().getGroupID(), getLocale());
	    return reportCriteria;
	}


	public RedFlagPaginationTableDataProvider getTableDataProvider() {
		return tableDataProvider;
	}


	public void setTableDataProvider(
			RedFlagPaginationTableDataProvider tableDataProvider) {
		this.tableDataProvider = tableDataProvider;
	}


    public BasePaginationTable<RedFlag> getTable() {
		return table;
	}


	public void setTable(BasePaginationTable<RedFlag> table) {
		this.table = table;
	}

    public AlertMessageDAO getAlertMessageDAO() {
        return alertMessageDAO;
    }

    public void setAlertMessageDAO(AlertMessageDAO alertMessageDAO) {
        this.alertMessageDAO = alertMessageDAO;
    }

	
    public PersonDAO getPersonDAO() {
        return personDAO;
    }

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }
    public RedFlagAlertDAO getRedFlagAlertDAO() {
        return redFlagAlertDAO;
    }

    public void setRedFlagAlertDAO(RedFlagAlertDAO redFlagAlertDAO) {
        this.redFlagAlertDAO = redFlagAlertDAO;
    }


    public void closeDetailsAction() {
        details = null;
        alertItems = null;
        detailsMap = null;
        selectedAlertID = null;
        sentDetailsItem = null;
    }
    
    public void cancelPendingAction() {
        if (details == null)
            return;
        details.cancelPendingAction();
        closeDetailsAction();
        refreshPage();
    }

}
