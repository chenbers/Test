package com.inthinc.pro.backing;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author David Gileadi
 */
public abstract class BaseAdminBean<T extends Selectable> extends BaseBean
{
    protected static final Logger  logger  = LogManager.getLogger(BaseAdminBean.class);

    protected List<T>              items;
    protected List<T>              filteredItems = new LinkedList<T>();
    protected String               filterValue;
    protected int                  page    = 1;
    protected Map<String, Boolean> columns = getDefaultColumns();

    /**
     * @return the items
     */
    public List<T> getItems()
    {
        return items;
    }

    /**
     * @return the filteredItems
     */
    public List<T> getFilteredItems()
    {
        return filteredItems;
    }

    /**
     * @return the number of filtered items.
     */
    public int getItemCount()
    {
        return filteredItems.size();
    }

    /**
     * @return the filterValue
     */
    public String getFilterValue()
    {
        return filterValue;
    }

    /**
     * @param filterValue
     *            the filterValue to set
     */
    public void setFilterValue(String filterValue)
    {
        boolean changed = false;
        if ((filterValue != null) && (filterValue.length() > 0))
        {
            filterValue = filterValue.toLowerCase();
            changed = !filterValue.equals(this.filterValue);
        }
        else
        {
            filterValue = null;
            changed = this.filterValue != null;
        }

        if (changed)
        {
            setPage(1);

            filteredItems.clear();
            this.filterValue = filterValue;
            if (filterValue != null)
            {
                final boolean matchAllWords = matchAllFilterWords();
                final String[] filterWords = filterValue.split("\\s+");
                for (final T item : items)
                {
                    boolean matched = false;
                    for (final String word : filterWords)
                    {
                        matched = matchesFilter(item, word);

                        // we can break if we didn't match and we're required to match all words,
                        // or if we did match and we're only required to match one word
                        if (matched ^ matchAllWords)
                            break;
                    }
                    if (matched)
                        filteredItems.add(item);
                }
            }
            else
                filteredItems.addAll(items);
        }
    }

    /**
     * @return Whether matching all filter words is required to match an item. The default is <code>true</code>.
     */
    protected boolean matchAllFilterWords()
    {
        return true;
    }

    /**
     * @param item
     *            The item to filter in or out of the results.
     * @param filterWord
     *            The lowercase filter word. If there are multiple words in the filter string this method will be called once for each word.
     * @return Whether the item matches the filter string.
     */
    protected abstract boolean matchesFilter(T item, String filterWord);

    /**
     * @return the current page
     */
    public int getPage()
    {
        return page;
    }

    /**
     * @param page
     *            the current page to set
     */
    public void setPage(int page)
    {
        this.page = page;
        for (final T item : items)
            item.setSelected(false);
    }

    /**
     * @return The list of available columns for type T.
     */
    public abstract List<String> getAvailableColumns();

    /**
     * @return The number of columns returned by {@link #getAvailableColumns()}.
     */
    public int getAvailableColumnCount()
    {
        return getAvailableColumns().size();
    }

    /**
     * @return A map of column keys and boolean values for whether a column is selected. Only the default-selected columns need be in the map, but their value must be
     *         <code>true</code>.
     */
    public abstract Map<String, Boolean> getDefaultColumns();

    /**
     * @return the columns
     */
    public Map<String, Boolean> getColumns()
    {
        return columns;
    }

    /**
     * @param columns
     *            the columns to set
     */
    public void setColumns(Map<String, Boolean> columns)
    {
        this.columns = columns;
    }

    /**
     * Called when the user chooses new visible columns.
     */
    public abstract void saveColumns();

    /**
     * Called when the user chooses to edit one or more selected items.
     * 
     * @return A new navigation string or <code>null</code> to remain on the page.
     */
    public abstract String edit();

    /**
     * Called when the user chooses to delete one or more selected items.
     * 
     * @return A new navigation string or <code>null</code> to remain on the page.
     */
    public abstract String delete();
}
