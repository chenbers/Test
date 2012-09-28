package com.inthinc.pro.backing.paging;

import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.BaseBean;
import com.inthinc.pro.backing.SearchCoordinationBean;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.HazardType;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.pagination.Range;
import com.inthinc.pro.model.pagination.TableFilterField;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.table.BasePaginationTable;
import com.inthinc.pro.table.model.provider.ReportPaginationTableDataProvider;
import com.inthinc.pro.util.SelectItemUtil;

public abstract class BasePagingReportBean<T> extends BaseBean 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5858527809036670367L;

	private ReportPaginationTableDataProvider<T> tableDataProvider;
	private BasePaginationTable<T> table;

	@SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(BasePagingReportBean.class);

    
    private ReportRenderer reportRenderer;
    private ReportCriteriaService reportCriteriaService;
    
    private SearchCoordinationBean searchCoordinationBean;
    private String searchFor;
    
	protected final static String BLANK_SELECTION = "&#160;";
    
	protected Map<String, String> scoreFilterMap;
    
    private Status statusFilter;
    private Integer statusFilterID;
	private static List<SelectItem> scoreRanges;
	private static Map<String, Range> scoreRangeMap;
	private static final Range[] ranges = new Range[] {
		new Range(0,11),
		new Range(11,21),
		new Range(21,31),
		new Range(31,41),
		new Range(41,51),
	};
	public List<SelectItem> getScoreRanges() {
		initScoreRanges();
		return scoreRanges;
	}
    public List<SelectItem> getStatuses(){
        List<SelectItem> result = SelectItemUtil.toList(Status.class, true, Status.DELETED);
//        for(SelectItem item: result){
//            Integer statusID = (item.getValue()!=null)?((Status)item.getValue()).getCode():null;
//            item.setValue(statusID);
//        }
        
        return result;
    }

	private void initScoreRanges() {
		if (scoreRanges == null) {
			scoreRangeMap = new HashMap<String, Range>();
			scoreRanges = new ArrayList<SelectItem>();
			Integer cnt = 0;
			SelectItem blankItem = new SelectItem(Integer.valueOf(cnt++).toString(), BLANK_SELECTION);
			blankItem.setEscape(false);
			scoreRanges.add(blankItem);
			DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(getLocale());
			char decimalSeparator = dfs.getDecimalSeparator();
			for (Range range : ranges) {
				String label = MessageFormat.format("{0}{1}{2} - {3}{4}{5}", new Object[] {range.getMin().intValue()/10, decimalSeparator,range.getMin().intValue()%10, range.getMax().intValue()/10, decimalSeparator, (range.getMax().intValue()-1)%10});
				scoreRanges.add(new SelectItem(Integer.valueOf(cnt).toString(), label));
				scoreRangeMap.put(Integer.valueOf(cnt++).toString(), range);
			}
		}
	}
	public Status getStatusFilter(){
	    System.out.println("getStatusFilter()");
	    return statusFilter;
	}
	public void setStatusFilter(Status statusFilter){
	    System.out.println("setStatusFilter("+statusFilter+")");
	    this.statusFilter = statusFilter;
	}
	public Integer getStatusFilterID(){
        return (statusFilter!=null)?statusFilter.getCode():null;
	}
	public void setStatusFilterID(Integer statusFilterID){
	    this.statusFilter = Status.valueOf(statusFilterID);
	}
	public Range getOverallScoreFilter() {
		String filterKey = scoreFilterMap.get("overall");
		return scoreRangeMap.get(filterKey);
	}
	public Range getSpeedScoreFilter() {
		String filterKey = scoreFilterMap.get("speed");
		return scoreRangeMap.get(filterKey);
	}
	public Range getStyleScoreFilter() {
		String filterKey = scoreFilterMap.get("style");
		return scoreRangeMap.get(filterKey);
	}
	public Range getSeatbeltScoreFilter() {
		String filterKey = scoreFilterMap.get("seatbelt");
		return scoreRangeMap.get(filterKey);
	}

	public void init() {
		initScoreRanges();
        setScoreFilterMap(new HashMap<String, String>());
		table = new BasePaginationTable<T>();
		
        tableDataProvider.setSort(getDefaultSort());
        tableDataProvider.setGroupID(this.getProUser().getUser().getGroupID());
		getTable().initModel(tableDataProvider);
		

    }

	public abstract TableSortField getDefaultSort();

	public BasePaginationTable<T> getTable() {
		return table;
	}

	public void setTable(BasePaginationTable<T> table) {
		this.table = table;
	}

    public Map<String, String> getScoreFilterMap() {
		return scoreFilterMap;
	}

	public void setScoreFilterMap(Map<String, String> scoreFilterMap) {
		this.scoreFilterMap = scoreFilterMap;
	}


    public void exportReportToPdf()
    {
        getReportRenderer().exportSingleReportToPDF(buildReportCriteria(), getFacesContext());
    }

    public void emailReport()
    {       
        getReportRenderer().exportReportToEmail(buildReportCriteria(), getEmailAddress(), getNoReplyEmailAddress());
    }

    public void exportReportToExcel()
    {
        getReportRenderer().exportReportToExcel(buildReportCriteria(), getFacesContext());
    }

    protected ReportCriteria buildReportCriteria()
    {
        ReportCriteria reportCriteria = getReportCriteria();
        reportCriteria.setDuration(Duration.TWELVE);
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setUseMetric(getMeasurementType() == MeasurementType.METRIC);
        reportCriteria.setMeasurementType(getMeasurementType());
        reportCriteria.setFuelEfficiencyType(getFuelEfficiencyType());
        
        return reportCriteria;
    }
    
	protected abstract ReportCriteria getReportCriteria();

	public void setReportRenderer(ReportRenderer reportRenderer)
    {
        this.reportRenderer = reportRenderer;
    }

    public ReportRenderer getReportRenderer()
    {
        return reportRenderer;
    }

    public void setReportCriteriaService(ReportCriteriaService reportCriteriaService)
    {
        this.reportCriteriaService = reportCriteriaService;
    }

    public ReportCriteriaService getReportCriteriaService()
    {
        return reportCriteriaService;
    }

    // search stuff (from search in header) -- need to look at this
    public SearchCoordinationBean getSearchCoordinationBean()
    {
        return searchCoordinationBean;
    }

    public void setSearchCoordinationBean(SearchCoordinationBean searchCoordinationBean)
    {
        this.searchCoordinationBean = searchCoordinationBean;
    }

    public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

    public void allAction()
    {
		table.reset();
    }
    
    public void searchAction()
    {
    	setSearchFor(getSearchCoordinationBean().getSearchFor());
		table.reset();
		getSearchCoordinationBean().setSearchFor("");
    }

	public ReportPaginationTableDataProvider<T> getTableDataProvider() {
		return tableDataProvider;
	}
	public void setTableDataProvider(ReportPaginationTableDataProvider<T> tableDataProvider) {
		this.tableDataProvider = tableDataProvider;
	}

}
