package com.inthinc.pro.backing.paging;

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
import com.inthinc.pro.backing.TablePref;
import com.inthinc.pro.backing.TablePrefOptions;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.pagination.Range;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.service.ReportCriteriaService;

public abstract class BasePagingReportBean<T> extends BaseBean implements TablePrefOptions<T>
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5858527809036670367L;


	@SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(BasePagingReportBean.class);

    
    private TablePreferenceDAO tablePreferenceDAO;
    private TablePref<T> tablePref;
    private ReportRenderer reportRenderer;
    private ReportCriteriaService reportCriteriaService;
    
    private SearchCoordinationBean searchCoordinationBean;
	protected final static String BLANK_SELECTION = "&#160;";
    
	protected Map<String, String> scoreFilterMap;
    
	
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

	private void initScoreRanges() {
		if (scoreRanges == null) {
			scoreRangeMap = new HashMap<String, Range>();
			scoreRanges = new ArrayList<SelectItem>();
			Integer cnt = 0;
			SelectItem blankItem = new SelectItem(Integer.valueOf(cnt++).toString(), BLANK_SELECTION);
			blankItem.setEscape(false);
			scoreRanges.add(blankItem);
			for (Range range : ranges) {
				String label = MessageFormat.format("{0}.{1} - {2}.{3}", new Object[] {range.getMin().intValue()/10, range.getMin().intValue()%10, range.getMax().intValue()/10, (range.getMax().intValue()-1)%10});
				scoreRanges.add(new SelectItem(Integer.valueOf(cnt).toString(), label));
				scoreRangeMap.put(Integer.valueOf(cnt++).toString(), range);
			}
		}
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
        setTablePref(new TablePref<T>(this));
		initScoreRanges();
        setScoreFilterMap(new HashMap<String, String>());
    }


    public Map<String, String> getScoreFilterMap() {
		return scoreFilterMap;
	}

	public void setScoreFilterMap(Map<String, String> scoreFilterMap) {
		this.scoreFilterMap = scoreFilterMap;
	}

	public void setTablePreferenceDAO(TablePreferenceDAO tablePreferenceDAO)
    {
        this.tablePreferenceDAO = tablePreferenceDAO;
    }

    
    
    public Map<String, TableColumn> getTableColumns()
    {
        return tablePref.getTableColumns();
    }

    public void setTableColumns(Map<String, TableColumn> tableColumns)
    {
        tablePref.setTableColumns(tableColumns);
    }

    public TablePref<T> getTablePref()
    {
        return tablePref;
    }

    public void setTablePref(TablePref<T> tablePref)
    {
        this.tablePref = tablePref;
    }

    public void exportReportToPdf()
    {
        getReportRenderer().exportSingleReportToPDF(buildReportCriteria(), getFacesContext());
    }

    public void emailReport()
    {
        getReportRenderer().exportReportToEmail(buildReportCriteria(), getEmailAddress());
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

    // TablePrefOptions implementation
    @Override
    abstract public String getColumnLabelPrefix();

    @Override
    public TablePreferenceDAO getTablePreferenceDAO()
    {
        return tablePreferenceDAO;
    }
    
    @Override
    abstract public List<String> getAvailableColumns();


    @Override
    public Map<String, Boolean> getDefaultColumns()
    {
        HashMap<String, Boolean> columns = new HashMap<String, Boolean>();
        for (String col : getAvailableColumns())
            columns.put(col, true);
        return columns;
    }


    @Override
    abstract public TableType getTableType();
    
    @Override
    public Integer getUserID()
    {
        return getUser().getUserID();
    }

    @Override
    public String fieldValue(T item, String column)
    {
        return TablePref.fieldValue(item, column);
    }
    
    // END - TablePrefOptions implementation

}
