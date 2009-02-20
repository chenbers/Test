package com.inthinc.pro.backing;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

public abstract class CustomSortBean<T> extends BaseBean
{
    
    private static final Logger logger = Logger.getLogger(CustomSortBean.class);
    private enum                SortOrder {ASC,DESC};
    private String              sortColumn = "";
    private SortOrder           sortDirection = SortOrder.ASC;
    
    public void sort(){
        Comparator<T> comparator = createComparator();
        List<T> items = getItems();
        if(sortDirection == SortOrder.ASC)
        {
            Collections.sort(items,comparator);
        }else
        {
            Collections.sort(items,Collections.reverseOrder(comparator));
        }
    }
    
    public abstract Comparator<T> createComparator();
    
    public abstract List<T> getItems();
    
    public void setSortColumn(String sortColumn)
    {
        logger.debug("Sort Column: " + sortColumn);
        if(!sortColumn.equals(this.sortColumn))
            sortDirection = SortOrder.ASC;
        this.sortColumn = sortColumn;
    }

    public String getSortColumn()
    {
        return sortColumn;
    }

    public void setSortDirection(String sortDirection)
    {
        logger.debug("Sort Direction: " + sortDirection);
        if(sortDirection.equals(SortOrder.ASC.toString()))
        {
            this.sortDirection = SortOrder.ASC;
        }
        else
        {
            this.sortDirection = SortOrder.DESC;
        }
       
    }

    public String getSortDirection()
    {
        return sortDirection.toString();
    }

}
