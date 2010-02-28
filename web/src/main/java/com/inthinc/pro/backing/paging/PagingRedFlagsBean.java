package com.inthinc.pro.backing.paging;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.LocaleBean;
import com.inthinc.pro.backing.TablePref;
import com.inthinc.pro.backing.TablePrefOptions;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.EventType;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.RedFlagReportItem;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.pagination.EventCategoryFilter;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.table.BasePaginationTable;
import com.inthinc.pro.table.model.provider.RedFlagPaginationTableDataProvider;
import com.inthinc.pro.util.MessageUtil;
public class PagingRedFlagsBean extends BasePagingNotificationsBean<RedFlag> implements TablePrefOptions<RedFlag>{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3166689931697428969L;
	private static final Logger logger = Logger.getLogger(PagingRedFlagsBean.class);
    private final static String COLUMN_LABEL_PREFIX = "notes_redflags_";
    private TablePreferenceDAO       tablePreferenceDAO;
    
    private TablePref<RedFlag> tablePref;
    
    // package level -- so unit test can get it
    static final List<String> AVAILABLE_COLUMNS;
    static {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("level");
        AVAILABLE_COLUMNS.add("alerts");
        AVAILABLE_COLUMNS.add("date");
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("driver");
        AVAILABLE_COLUMNS.add("vehicle");
        AVAILABLE_COLUMNS.add("category");
        AVAILABLE_COLUMNS.add("detail");
        AVAILABLE_COLUMNS.add("clear");
    }
	private RedFlagPaginationTableDataProvider tableDataProvider;
	private BasePaginationTable<RedFlag> table;
	
	static final List<EventCategory> CATEGORIES;
	static {
		CATEGORIES = new ArrayList<EventCategory>();
		CATEGORIES.add(EventCategory.NONE);
		CATEGORIES.add(EventCategory.VIOLATION);
		CATEGORIES.add(EventCategory.EMERGENCY);
		CATEGORIES.add(EventCategory.DRIVER);
		CATEGORIES.add(EventCategory.WARNING);
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
        tablePref = new TablePref<RedFlag>(this);
		
        
        tableDataProvider.setSort(new TableSortField(SortOrder.DESCENDING, "time"));
		
		table = new BasePaginationTable<RedFlag>();
		table.initModel(tableDataProvider);
	}


    public TablePref<RedFlag> getTablePref() {
        return tablePref;
    }

    public void setTablePref(TablePref<RedFlag> tablePref) {
        this.tablePref = tablePref;
    }

    public Map<String, TableColumn> getTableColumns() {
        return tablePref.getTableColumns();
    }

    public void setTableColumns(Map<String, TableColumn> tableColumns) {
        tablePref.setTableColumns(tableColumns);
    }

    
    public void setTablePreferenceDAO(TablePreferenceDAO tablePreferenceDAO) {
	    this.tablePreferenceDAO = tablePreferenceDAO;
	}
    
    
    // TablePrefOptions interface
    @Override
    public String getColumnLabelPrefix() {
        return COLUMN_LABEL_PREFIX;
    }

    @Override
    public TablePreferenceDAO getTablePreferenceDAO() {
	    return tablePreferenceDAO;
	}

    @Override
    public List<String> getAvailableColumns() {
        return AVAILABLE_COLUMNS;
    }

    @Override
    public Map<String, Boolean> getDefaultColumns() {
        HashMap<String, Boolean> columns = new HashMap<String, Boolean>();
        for (String col : AVAILABLE_COLUMNS)
            columns.put(col, true);
        return columns;
    }


    @Override
    public TableType getTableType() {
        return TableType.RED_FLAG;
    }

    @Override
    public Integer getUserID() {
        return getUser().getUserID();
    }
    /**
     * Returns the value of the property of the given item described by the given column name. The default implementation calls TablePref.fieldValue.
     * 
     * @param item
     *            The item to get the value from.
     * @param column
     *            The name of the column to get the value of.
     * @return The value or <code>null</code> if unavailable.
     */
    public String fieldValue(RedFlag item, String column) {
        if ("driver".equals(column))
            column = "driverName";
        else if ("vehicle".equals(column))
            column = "vehicleName";
        else if ("alert".equals(column)) {
            if ((item != null) && (item.getAlert() != null))
                return item.getAlert() ? "yes" : "no";
            return null;
        }
        else if ("level".equals(column))
            column = "redFlag_level_description";
        else if ("alerts".equals(column))
            return "";
        else if ("clear".equals(column))
            return "";
        return TablePref.fieldValue(item, column);
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

            redFlagReportItemList.add(new RedFlagReportItem(redFlag, getMeasurementType(), dateFormatString, detailsFormatStr, mphString));
        }
        Collections.sort(redFlagReportItemList);
        Collections.reverse(redFlagReportItemList);
        
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
