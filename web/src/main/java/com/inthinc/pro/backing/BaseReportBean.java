package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.reports.ReportRenderer;

public abstract class BaseReportBean<T> extends BaseBean implements TablePrefOptions
{
    private static final Logger logger       = Logger.getLogger(BaseReportBean.class);

    private boolean             mainMenu;
    private TablePreferenceDAO  tablePreferenceDAO;
    private TablePref           tablePref;
    private String              emailAddress;
    private ReportRenderer      reportRenderer;
    private Integer             numRowsPerPg = 25;
    protected Integer           maxCount     = null;
    private Integer             start        = 1;
    private Integer             end          = numRowsPerPg;

    protected String              searchFor    = "";
    private String              secret       = "";

    public BaseReportBean()
    {

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

    public TablePref getTablePref()
    {
        return tablePref;
    }

    public void setTablePref(TablePref tablePref)
    {
        this.tablePref = tablePref;
    }

    protected abstract List<T> getDBData();

    protected abstract List<T> getDisplayData();

    protected abstract void loadResults(List<T> data);

    protected void checkOnSearch()
    {
        if ((searchFor != null) && (searchFor.trim().length() != 0))
        {
            search();
        }
        else
        {
            loadResults(getDBData());
        }

        maxCount = getDisplayData().size();
        resetCounts();
    }

    public void search()
    {
        if (getDisplayData().size() > 0)
        {
            getDisplayData().clear();
        }

        if (this.searchFor.trim().length() != 0)
        {
            final boolean matchAllWords = matchAllFilterWords();
            final String[] filterWords = this.searchFor.trim().toLowerCase().split("\\s+");
            final List<T> matchedItems = new ArrayList<T>();

            for (T t : getDBData())
            {
                boolean matched = false;
                for (final String word : filterWords)
                {
                    for (final String column : getTableColumns().keySet())
                        if (getTableColumns().get(column).getVisible())
                            {
                                final String value = columnValue(t, column);
                                if ((value != null) && value.toLowerCase().contains(word))
                                    matched = true;
                            }

                    // we can break if we didn't match and we're required to match all words,
                    // or if we did match and we're only required to match one word
                    if (matched ^ matchAllWords)
                        break;
                }
                if (matched)
                    matchedItems.add(t);
            }

            loadResults(matchedItems);
            this.maxCount = matchedItems.size();
        }
        else
        {
            loadResults(getDBData());
            this.maxCount = getDBData().size();
        }

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
     * Returns the value of the property of the given item described by the given column name. The default implementation converts the column name to property syntax and uses
     * BeanUtils to get the property value.
     * 
     * @param item
     *            The item to get the value from.
     * @param columnName
     *            The name of the column to get the value of.
     * @return The value or <code>null</code> if unavailable.
     */
    protected String columnValue(T item, String columnName)
    {
        try
        {
            return String.valueOf(BeanUtils.getProperty(item, columnName.replace('_', '.')));
        }
        catch (NestedNullException e)
        {
            // ignore nested nulls
        }
        catch (Exception e)
        {
            logger.error("Error filtering on column " + columnName, e);
        }
        return null;
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
        String search = searchForLocal.toLowerCase().trim();
        if ((search.length() != 0) && (!search.equalsIgnoreCase(this.searchFor)))
        {
            this.searchFor = searchForLocal.toLowerCase().trim();
        }

        if (isMainMenu())
        {
            checkOnSearch();
            setMainMenu(false);
        }
        else if (this.searchFor.trim().length() != 0)
        {
            checkOnSearch();
        }
        else
        {
            loadResults(getDBData());
        }

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
        this.searchFor = searchFor;
    }

    protected String checkForRequestMap()
    {
        String searchFor = "";
        mainMenu = false;

        Map<String, String> m = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Iterator<Map.Entry<String, String>> imap = m.entrySet().iterator();

        // if there is a map, the request came from the
        // main menu search, so grab it
        while (imap.hasNext())
        {
            Map.Entry<String, String> entry = imap.next();
            String key = entry.getKey();
            String value = entry.getValue();

            // search parm, either from the search in the main menu or
            // one from the report
            if (key.equalsIgnoreCase("searchFor"))
            {
                searchFor = value;
                mainMenu = true;
            }
        }

        return searchFor;
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
}
