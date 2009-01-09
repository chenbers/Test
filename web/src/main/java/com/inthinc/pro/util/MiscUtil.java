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
        if ((phone == null) || (phone.length() == 0))
            return null;
        if (phone.length() != 10)
        {
            final String unfo = unformatPhone(phone);
            if (unfo.length() == 10)
                phone = unfo;
            else
                return phone;
        }
        return MessageUtil.formatMessageString("phoneFormat", phone.substring(0, 3), phone.substring(3, 6), phone.substring(6));
    }

    public static String unformatPhone(String phone)
    {
        if (phone == null)
            return null;
        return phone.replaceAll("\\D", "");
    }
}
