package com.inthinc.pro.backing.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.inthinc.pro.util.MiscUtil;

/**
 * For use with autocompletePicker.xhtml.
 */
public class AutocompletePicker
{
    private static final Logger logger = Logger.getLogger(AutocompletePicker.class);
    
    private List<SelectItem> pickFrom;
    private List<SelectItem> picked;
    private String           value;
    private boolean          isOutdated = false;

    public AutocompletePicker(List<SelectItem> pickFrom)
    {
        this(pickFrom, new ArrayList<SelectItem>());
    }

    public AutocompletePicker(List<SelectItem> pickFrom, List<SelectItem> picked)
    {
        this.pickFrom = pickFrom;
        this.picked = picked;
        pickFrom.removeAll(picked);
    }
        
    @Override
    public String toString() {
        return "AutoCompletePicker: [pickFrom.size()="+pickFrom.size()+", picked.size()="+picked.size()+", value="+this.value+"]";
    }
    public Integer size(){
        return ((pickFrom!=null)?pickFrom.size():0)
            +((picked!=null)?picked.size():0);
    }
    public List<SelectItem> getPickFrom()
    {
        if(this.isOutdated)
            pickFrom.removeAll(picked);
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

    public List<SelectItem> autocomplete(Object value) {
      //TODO: refactor: the following line is only necessary because an AutocompletePicker can be created with a SelectItem in the pickFrom list with a duplicate(same label, so duplicate to the end user) in the pickedList.  Ideally, removal of duplicates would happen ONCE rather than every time autocomplete is called
        MiscUtil.removeAllByLabel(pickFrom, picked);

        final LinkedList<SelectItem> suggestions = new LinkedList<SelectItem>();
        final String[] words = value.toString().toLowerCase().split("[, ]");
        for (final SelectItem item : getPickFrom()) {
            boolean matches = true;
            for (final String word : words) {
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
        MiscUtil.sortSelectItems(suggestions);
        return suggestions;
    }
    public boolean containsLabel(String label) {
        String labelNoDupSpaces = (label != null)?label.replaceAll(" +", " "): null;
        return listContainsLabel(picked, labelNoDupSpaces) || listContainsLabel(pickFrom, labelNoDupSpaces);
    }
   
    public boolean listContainsLabel(List<SelectItem> list, String label) {
        for (final Iterator<SelectItem> it = list.iterator(); it.hasNext();) {
            if (it.next().getLabel().equals(label))
                return true;
        }
        return false;
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
        boolean success = false;
        if (value != null) {
            for (final Iterator<SelectItem> pickedIterator = picked.iterator(); pickedIterator.hasNext();) {
                SelectItem item = pickedIterator.next();
                if (value.equalsIgnoreCase(item.getLabel())) {
                    if(!listContainsLabel(pickFrom,item.getLabel()))
                        pickFrom.add(item);
                    pickedIterator.remove();
                    value = null;
                    success = true;
                    break;
                } 
            }
            value = null;
        }
        if(!success) {
            logger.warn("did not succeed in removing value: "+value);
        }
    }
    public void setOutdated(boolean isOutdated) {
        this.isOutdated = isOutdated;
    }

    public boolean isOutdated() {
        return isOutdated;
    }

}
