package com.inthinc.pro.backing.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.util.BeanUtil;

/**
 * For use with listPicker.xhtml.
 */
public class ListPicker
{
    private List<SelectItem>            pickFrom;
    private List<SelectItem>            filteredPickFrom;
    private List<SelectItem>            picked;
    private String                      pickedIDs;
    private HashMap<String, SelectItem> cachedItems = new HashMap<String, SelectItem>();

    public ListPicker(List<SelectItem> pickFrom)
    {
        this(pickFrom, new ArrayList<SelectItem>());
    }

    public ListPicker(List<SelectItem> pickFrom, List<SelectItem> picked)
    {
        setPicked(picked);
        setPickFrom(pickFrom);
    }

    public List<SelectItem> getPickFrom()
    {
        return pickFrom;
    }

    public void setPickFrom(List<SelectItem> pickFrom)
    {
        this.pickFrom = pickFrom;
        removePickedItems();
        copyToFilteredPickFrom(pickFrom);
        addToCache(filteredPickFrom);
    }
    private void copyToFilteredPickFrom(List<SelectItem> pickFrom){
        filteredPickFrom = new ArrayList<SelectItem>();
        for(SelectItem item : pickFrom){
            SelectItem copy = new SelectItem();
            BeanUtil.deepCopy(item, copy);
            filteredPickFrom.add(copy);
        }
    }
    public List<SelectItem> getPicked()
    {
        return picked;
    }

    public void setPicked(List<SelectItem> picked)
    {
        this.picked = picked;
        this.pickedIDs = null;
        addToCache(picked);
    }

    public String getPickedIDs()
    {
        if ((pickedIDs == null) && (picked != null))
            pickedIDs = buildIDs(picked);
        return pickedIDs;
    }

    public void setPickedIDs(String pickedIDs)
    {
        this.pickedIDs = pickedIDs;
        if (picked == null)
            picked = new ArrayList<SelectItem>();
        buildFromIDs(picked, pickedIDs);
        removePickedItems();
    }

    public List<SelectItem> getEmptyItems()
    {
        return null;
    }

    public void setEmptyItems(List<SelectItem> empty)
    {
    }

    private void addToCache(List<SelectItem> list)
    {
        if (list != null)
            for (final SelectItem item : list)
                cachedItems.put(String.valueOf(item.getValue()), item);
    }

    private void removePickedItems()
    {
        if (pickFrom != null)
            for (final Iterator<SelectItem> i = this.pickFrom.iterator(); i.hasNext();)
            {
                final SelectItem test = i.next();
                for (final SelectItem item : this.picked)
                    if (test.getValue().equals(item.getValue()))
                        i.remove();
            }
    }

    private String buildIDs(List<SelectItem> list)
    {
        final StringBuilder sb = new StringBuilder();
        for (final SelectItem item : list)
        {
            if (sb.length() > 0)
                sb.append(',');
            sb.append(item.getValue());
        }
        return sb.toString();
    }

    private void buildFromIDs(List<SelectItem> list, String idString)
    {
        addToCache(list);
        list.clear();
        if ((idString != null) && (idString.length() > 0))
        {
            final String[] ids = idString.split(",");
            for (final String id : ids)
            {
                final SelectItem item = cachedItems.get(id);
                if (item != null)
                    list.add(item);
            }
        }
    }

    public List<SelectItem> getFilteredPickFrom() {
        return filteredPickFrom;
    }

    public void setFilteredPickFrom(List<SelectItem> filteredPickFrom) {
        this.filteredPickFrom = filteredPickFrom;
    }
}
