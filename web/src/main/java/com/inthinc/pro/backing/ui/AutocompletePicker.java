package com.inthinc.pro.backing.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.util.MiscUtil;

/**
 * For use with autocompletePicker.xhtml.
 */
public class AutocompletePicker
{
    private List<SelectItem> pickFrom;
    private List<SelectItem> picked;
    private String           value;

    public AutocompletePicker(List<SelectItem> pickFrom)
    {
        this(pickFrom, new ArrayList<SelectItem>());
    }

    public AutocompletePicker(List<SelectItem> pickFrom, List<SelectItem> picked)
    {
        this.pickFrom = pickFrom;
        this.picked = picked;
    }

    public List<SelectItem> getPickFrom()
    {
        return pickFrom;
    }

    public void setPickFrom(List<SelectItem> pickFrom)
    {
        this.pickFrom = pickFrom;
    }

    public List<SelectItem> getPicked()
    {
        // delete an item if asked
        if (value != null)
        {
            for (final Iterator<SelectItem> i = picked.iterator(); i.hasNext();)
                if (value.equals(i.next().getValue().toString()))
                {
                    i.remove();
                    value = null;
                    break;
                }
        }
        return picked;
    }

    public void setPicked(List<SelectItem> picked)
    {
        this.picked = picked;
    }

    public List<SelectItem> autocomplete(Object value)
    {
        final LinkedList<SelectItem> suggestions = new LinkedList<SelectItem>();
        final String[] words = value.toString().toLowerCase().split("[, ]");
        for (final SelectItem item : getPickFrom())
        {
            boolean matches = true;
            for (final String word : words)
            {
                matches = false;
                final String[] itemWords = item.getLabel().toLowerCase().split("[, ]");
                for (final String itemWord : itemWords)
                    matches |= itemWord.startsWith(word);
                if (!matches)
                    break;
            }
            if (matches)
                suggestions.add(item);
        }
       
        
        suggestions.removeAll(picked);
        removeByLabel(suggestions, picked);
        MiscUtil.sortSelectItems(suggestions);
      
        return suggestions;
    }
    public boolean removeByLabel(List<SelectItem> orig, List<SelectItem> remove){
        boolean changed = false;
        for (final Iterator<SelectItem> origIterator = orig.iterator(); origIterator.hasNext();) {
            SelectItem item = origIterator.next();
            for(SelectItem removeItem: remove){
                if(item.getLabel().equalsIgnoreCase(removeItem.getLabel())){
                    origIterator.remove();
                    changed = true;
                    break;
                }
            }
        }
        
        return changed;
    }

    public Object getItemValue()
    {
        return null;
    }

    public void setItemValue(Object value)
    {
        this.value = (value!=null)?value.toString():null;
    }

    public void addItem()
    {
        if (value == null)
            return;
        for (final SelectItem item : getPickFrom()) {
            if (value.equals(item.getValue().toString()))
            {
                picked.add(item);
                break;
            }
        }
        value = null;
    }
    
    public void removeItem() {
        if (value != null) {
            for (final Iterator<SelectItem> pickedIterator = picked.iterator(); pickedIterator.hasNext();) {
                SelectItem item = pickedIterator.next();
                if (value.equalsIgnoreCase(item.getLabel())) {
                    pickFrom.add(item);
                    pickedIterator.remove();
                    value = null;
                    break;
                } 
            }
            value = null;
        }
    }
}
