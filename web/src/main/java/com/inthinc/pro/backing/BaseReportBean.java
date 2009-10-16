package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;

//import com.inthinc.pro.backing.listener.SearchChangeListener;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.service.ReportCriteriaService;

public abstract class BaseReportBean<T> extends BaseBean implements TablePrefOptions<T>
{
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(BaseReportBean.class);

	protected static Integer numRowsPerPg = 25;
	
    private TablePreferenceDAO tablePreferenceDAO;
    private TablePref<T> tablePref;
    private ReportRenderer reportRenderer;
    private ReportCriteriaService reportCriteriaService;
    protected Integer maxCount;
    private Integer start;
    private Integer end;
    private Integer page;

    private NavigationBean navigation;
    private SearchCoordinationBean searchCoordinationBean;

    public NavigationBean getNavigation()
    {
        return navigation;
    }

    public void setNavigation(NavigationBean navigation)
    {
        this.navigation = navigation;
    }

    public BaseReportBean()
    {

    }

    public void initBean()
    {
        setTablePref(new TablePref<T>(this));

    }

    public TablePreferenceDAO getTablePreferenceDAO()
    {
        return tablePreferenceDAO;
    }

    public void setTablePreferenceDAO(TablePreferenceDAO tablePreferenceDAO)
    {
        this.tablePreferenceDAO = tablePreferenceDAO;
    }

    @Override
    public Map<String, Boolean> getDefaultColumns()
    {
        HashMap<String, Boolean> columns = new HashMap<String, Boolean>();
        for (String col : getAvailableColumns())
            columns.put(col, true);
        return columns;
    }

    @Override
    public Integer getUserID()
    {
        return getUser().getUserID();
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

    protected abstract void loadDBData();

    protected abstract List<T> getDBData();

    protected abstract List<T> getDisplayData();

    protected abstract void setDisplayData(List<T> displayData);

    protected abstract void loadResults(List<T> data);

    protected abstract void filterResults(List<T> data);

    protected Integer getEffectiveGroupId()
    {

        if (getSearchCoordinationBean().isGoodGroupId())
        {

            return getSearchCoordinationBean().getGroup().getGroupID();
        }
        else
        {

            return getUser().getGroupID();
        }
    }
    public void loadAll(){
    	
        final List<T> matchedItems = new ArrayList<T>();
        
      	searchCoordinationBean.clearSearchFor();
      
        loadDBData();
        
        matchedItems.addAll(getDBData());

        filterResults(matchedItems);
        loadResults(matchedItems);
        maxCount = getDisplayData().size();
        resetCounts();
  	
    }
    public void loadGroup(){
    	
        final List<T> matchedItems = new ArrayList<T>();
        
        loadDBData();
        
        matchedItems.addAll(getDBData());

        filterResults(matchedItems);
        loadResults(matchedItems);
        maxCount = getDisplayData().size();
        resetCounts();
  	
    }
   public void search()
    {
    	
        loadDBData();
 
        final List<T> matchedItems = new ArrayList<T>();
        matchedItems.addAll(getDBData());

        filterResults(matchedItems);

        if (searchCoordinationBean.isGoodSearch()){
        	
        	tablePref.filter(matchedItems, searchCoordinationBean.getSearchFor(), matchAllFilterWords());
        }

        loadResults(matchedItems);
        this.maxCount = matchedItems.size();
 //     maxCount = getDisplayData().size();
 
        resetCounts();
        
    }
    
    public abstract String getMappingId();
    public abstract String getMappingIdWithCriteria();
    
    public String doSearch()
    {
        if (searchCoordinationBean.isGoodSearch())
            return getMappingIdWithCriteria();
        else
            return getMappingId();
    }

    /**
     * @return Whether matching all filter words is required to match an item. The default is <code>true</code>.
     */
    protected boolean matchAllFilterWords()
    {
        return true;
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
    public String fieldValue(T item, String column)
    {
        return TablePref.fieldValue(item, column);
    }

    protected void resetCounts()
    {
        start = getDisplayData().size() == 0 ? 0 : 1;

        end = getDisplayData().size() <= numRowsPerPg ? getDisplayData().size() : numRowsPerPg;

        page = 1;
    }

    public void scrollerListener(DataScrollerEvent se)
    {
        this.start = (se.getPage() - 1) * this.numRowsPerPg + 1;
        this.end = (se.getPage()) * this.numRowsPerPg;

        // Partial page
        if (this.end > getDisplayData().size())
        {
            this.end = getDisplayData().size();
        }
    }

    public Integer getStart()
    {
        return start;
    }

    public void setStart(Integer start)
    {
        this.start = start;
    }

    public Integer getEnd()
    {
        return end;
    }

    public void setEnd(Integer end)
    {
        this.end = end;
    }

    public Integer getMaxCount()
    {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount)
    {
        this.maxCount = maxCount;
    }

    public Integer getNumRowsPerPg()
    {
        return numRowsPerPg;
    }

    public void setNumRowsPerPg(Integer numRowsPerPg)
    {
        this.numRowsPerPg = numRowsPerPg;
    }

    protected Integer floatToInteger(float value)
    {
        Float fTmp = new Float(value * 10.0);
        return fTmp.intValue();
    }

    protected String formatPhone(String incoming)
    {
//        String result = "";
//
//        if (incoming != null)
//        {
//            result = "(" + incoming.substring(0, 3) + ") " + incoming.substring(3, 6) + "-" + incoming.substring(6, 10);
//        }
//
//        return result;
    	return incoming;
    }

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

    public SearchCoordinationBean getSearchCoordinationBean()
    {
        return searchCoordinationBean;
    }

    public void setSearchCoordinationBean(SearchCoordinationBean searchCoordinationBean)
    {
        this.searchCoordinationBean = searchCoordinationBean;
    }

//    private void reinit()
//    {
//        setDisplayData(null);
//        setStart(null);
//        setEnd(null);
//        setMaxCount(null);
//    }

    public void setPage(Integer page)
    {
        this.page = page;
    }

    public Integer getPage()
    {
        return page;
    }

}
