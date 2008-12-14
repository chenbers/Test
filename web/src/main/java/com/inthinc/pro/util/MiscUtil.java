package com.inthinc.pro.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.model.SelectItem;

public class MiscUtil
{
    static public int randomInt(int min, int max)
    {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    public static void sortSelectItems(List<SelectItem> items)
    {
        Collections.sort(items, new Comparator<SelectItem>()
        {
            @Override
            public int compare(SelectItem o1, SelectItem o2)
            {
                return o1.getLabel().compareToIgnoreCase(o2.getLabel());
            }
        });
    }
}
