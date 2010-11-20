package com.inthinc.pro.backing.paging;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.faces.model.SelectItem;

import org.ajax4jsf.model.KeepAlive;
import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;

import com.inthinc.pro.backing.LocaleBean;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.RedFlagReportItem;
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
		CATEGORIES.add(EventCategory.NONE);
		CATEGORIES.add(EventCategory.VIOLATION);
		CATEGORIES.add(EventCategory.EMERGENCY);
		CATEGORIES.add(EventCategory.WARNING);
        CATEGORIES.add(EventCategory.ZONE);
//??        CATEGORIES.add(EventCategory.HOS);
	}

	@Override
	protected List<EventCategory> getCategories() {
		return CATEGORIES;
	}
	
	private String filterLevel;
	private String filterAlert;
	public String getFilterAlert() {
		return filterAlert;
	}

	public void setFilterAlert(String filterAlert) {
logger.info("setfilterAlert " + ((filterAlert == null) ? "" : filterAlert));		
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
        	filterAlerts.add(new SelectItem("1", MessageUtil.getMessageString("yes", getLocale())));
        	filterAlerts.add(new SelectItem("0", MessageUtil.getMessageString("no", getLocale())));
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
        String dateFormatString = MessageUtil.getMessageString("dateTimeFormat", LocaleBean.getCurrentLocale());
		        
        for (RedFlag redFlag : redFlagList)
        {
            String detailsFormatStr = MessageUtil.getMessageString("redflags_details" + redFlag.getEvent().getEventType());
            if (redFlag.getEvent().getDriverName() == null || redFlag.getEvent().getDriverName().isEmpty()) {
            	redFlag.getEvent().setDriverName(MessageUtil.getMessageString("unknown_driver"));
            }

            redFlagReportItemList.add(new RedFlagReportItem(redFlag, getMeasurementType(), dateFormatString, detailsFormatStr, mphString, LocaleBean.getCurrentLocale()));
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

	
}
