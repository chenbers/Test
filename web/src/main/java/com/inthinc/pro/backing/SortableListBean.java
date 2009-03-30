package com.inthinc.pro.backing;

public abstract class SortableListBean extends BaseBean
{
    protected String sortColumnName;
    protected boolean ascending;
    protected String oldSortName;
    protected boolean oldAscending;

    protected SortableListBean(String defaultSortColumnName)
    {
        this.sortColumnName = defaultSortColumnName;
        ascending = isDefaultAscending(defaultSortColumnName);
        oldSortName = sortColumnName;
        // make sure sortColumnName on first render
        oldAscending = !ascending;
    }

    protected abstract void sort();

    protected abstract boolean isDefaultAscending(String sortColumn);

    protected void sortCheck()
    {
        // if sortColumnName or ascending has changed, then resort
        if (!oldSortName.equals(sortColumnName) || oldAscending != ascending)
        {
            sort();
            oldSortName = sortColumnName;
            oldAscending = ascending;
        }
    }

    public String getSortColumnName()
    {
        return sortColumnName;
    }

    public void setSortColumnName(String sortColumnName)
    {
        oldSortName = this.sortColumnName;
        this.sortColumnName = sortColumnName;
    }

    public boolean isAscending()
    {
        return ascending;
    }

    public void setAscending(boolean ascending)
    {
        oldAscending = this.ascending;
        this.ascending = ascending;
    }

}
