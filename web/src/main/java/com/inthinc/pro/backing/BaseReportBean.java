package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.listener.SearchChangeListener;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.service.ReportCriteriaService;

public abstract class BaseReportBean<T> extends BaseBean implements TablePrefOptions<T>, SearchChangeListener
{
    @SuppressWarnings("unused")
	private static final Logger logger       = Logger.getLogger(BaseReportBean.class);

    private boolean             mainMenu;
    private TablePreferenceDAO  tablePreferenceDAO;
    private TablePref<T>        tablePref;
    private String              emailAddress;
    private ReportRenderer      reportRenderer;
    private ReportCriteriaService reportCriteriaService;
    private Integer             numRowsPerPg = 25;
    protected Integer           maxCount     = null;
    private Integer             start        = 1;
    private Integer             end          = numRowsPerPg;

    protected String            searchFor    = "";
//    private boolean				searchChanged = true;
    private String              secret       = "";

    private SearchCoordinationBean searchCoordinationBean;

	public BaseReportBean()
    {

    }

    public void init() 
    {
        setTablePref(new TablePref<T>(this));
        
        searchCoordinationBean.addSearchChangeListener(this);
        
        // On init the search string can either be from the main menu, which can be found in the request map
        // or from the latest search done in the red flags or other report page.
        
        searchFor = checkForRequestMap();
        
        if (isMainMenu()){
        	
        	searchCoordinationBean.notifySearchChangeListeners(this,searchFor);
        	setMainMenu(false);
        }
        else {
        	
        	searchFor = searchCoordinationBean.getSearchFor();
         }	
 
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

    protected abstract void loadResults(List<T> data);

    protected void checkOnSearch()
    {
    	loadDBData();
    	if ((searchFor != null) && (!searchFor.isEmpty()))
	        {
	        	getDisplayData().clear();
	 
	            final List<T> matchedItems = new ArrayList<T>();
	            matchedItems.addAll(getDBData());
	
	            tablePref.filter(matchedItems, searchFor, matchAllFilterWords());
	
	            loadResults(matchedItems);
	            this.maxCount = matchedItems.size();
	        }
	        else
	        {
	            loadResults(getDBData());
	            maxCount = getDisplayData().size();
	        }
	
	        resetCounts();
    }

    public void search()
    {
        if (getDisplayData().size() > 0)
        {
            getDisplayData().clear();
        }

        final List<T> matchedItems = new ArrayList<T>();
        matchedItems.addAll(getDBData());

        tablePref.filter(matchedItems, searchFor, matchAllFilterWords());

        loadResults(matchedItems);
        this.maxCount = matchedItems.size();
        resetCounts();
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
        this.start = 1;

        // None found
        if (getDisplayData().size() < 1)
        {
            this.start = 0;
        }

        this.end = this.numRowsPerPg;

        // Fewer than a page
        if (getDisplayData().size() <= this.end)
        {
            this.end = getDisplayData().size();
        }
        else if (this.start == 0)
        {
            this.end = 0;
        }
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

    public String getSecret()
    {
	    String searchForLocal = checkForRequestMap();
	    
	    if ((searchForLocal != null) && !searchForLocal.isEmpty() && !searchForLocal.equalsIgnoreCase(this.searchFor))
	    {
	        this.searchFor = searchForLocal;
//	        searchChanged = true;
	    	searchCoordinationBean.notifySearchChangeListeners(this,searchFor);
		    checkOnSearch();
	    	setMainMenu(false);
	    }
		else {
			
		    checkOnSearch();
		}
//		else
//		{
//		    loadResults(getDBData());
//		}
        return secret;
    }

    public void setSecret(String secret)
    {
        this.secret = secret;
    }

    public Integer getMaxCount()
    {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount)
    {
        this.maxCount = maxCount;
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

    public Integer getNumRowsPerPg()
    {
        return numRowsPerPg;
    }

    public void setNumRowsPerPg(Integer numRowsPerPg)
    {
        this.numRowsPerPg = numRowsPerPg;
    }

    public String getSearchFor()
    {
        return searchFor;
    }

    public void setSearchFor(String searchFor)
    {
    	if (searchFor != null ){
    		
	        this.searchFor = searchFor;
//	        searchChanged = true;
	        searchCoordinationBean.notifySearchChangeListeners(this,searchFor);
    	}
    }

    protected String checkForRequestMap()
    {
        String requestSearchFor = (String)getParameter("searchFor");
        
        if (requestSearchFor != null) requestSearchFor = requestSearchFor.trim();
        
        mainMenu = ((requestSearchFor != null) && !requestSearchFor.isEmpty());
        
       	return requestSearchFor;
    }

    protected Integer floatToInteger(float value)
    {
        Float fTmp = new Float(value * 10.0);
        return fTmp.intValue();
    }

    protected String formatPhone(String incoming)
    {
        String result = "";

        if (incoming != null)
        {
            result = "(" + incoming.substring(0, 3) + ") " + incoming.substring(3, 6) + "-" + incoming.substring(6, 10);
        }

        return result;
    }

    public boolean isMainMenu()
    {
        return mainMenu;
    }

    public void setMainMenu(boolean mainMenu)
    {
        this.mainMenu = mainMenu;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress()
    {
        return emailAddress;
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
    
    public SearchCoordinationBean getSearchCoordinationBean() {
		return searchCoordinationBean;
	}

	public void setSearchCoordinationBean(
			SearchCoordinationBean searchCoordinationBean) {
		this.searchCoordinationBean = searchCoordinationBean;
	}
	
	public String updateSearchAction(){
		
		searchCoordinationBean.notifySearchChangeListeners(this,searchFor);
		return null;
	}
	@Override
	public synchronized void searchChanged(String searchFor) {

		this.searchFor = searchFor;
//		searchChanged = true;
	}

}
