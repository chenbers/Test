package com.inthinc.pro.util;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.faces.model.SelectItem;


public class SelectItemUtil
{    
    /**
     * 
     * @param clazz - Enumeration (i.g. GroupType.class)
     * @param includeEmptySet - If TRUE will insert an empty select item in the beginning of the list
     * @param ignoreList - any Enum<T> elements that should not be included in the returned list of SelectItems
     * @return List of Select Items
     */
    public static <T extends Enum<T>> List<SelectItem> toList(Class<T> clazz,Boolean includeEmptySet,T... ignoreList)
    {
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        for (T e : EnumSet.allOf(clazz))
        {
           boolean ignore = false;
           
           for(int i=0;i<ignoreList.length;i++)
               if(ignoreList[i] == e)
                   ignore = true;
           
           if(ignore == false)
               selectItemList.add(new SelectItem(e,MessageUtil.getMessageString(e.toString())));
        }
        
        if(includeEmptySet)
            selectItemList.add(0, new SelectItem(null,""));
        return selectItemList;
    }
}
