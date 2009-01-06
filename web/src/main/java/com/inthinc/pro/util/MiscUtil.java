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

    public static String formatPhone(String phone)
    {
        if (phone == null)
            return null;
        if (phone.length() != 10)
            throw new IllegalArgumentException();
        final StringBuilder sb = new StringBuilder();
        sb.append('(');
        sb.append(phone.substring(0, 3));
        sb.append(") ");
        sb.append(phone.substring(3, 6));
        sb.append('-');
        sb.append(phone.substring(6));
        return sb.toString();
    }

    public static String unformatPhone(String phone)
    {
        if (phone == null)
            return null;
        return phone.replaceAll("\\D", "");        
    }
}
