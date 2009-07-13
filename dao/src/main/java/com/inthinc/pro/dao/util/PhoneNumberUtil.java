package com.inthinc.pro.dao.util;

import java.text.MessageFormat;

public class PhoneNumberUtil
{
    
    public static String formatPhone(String phone,String pattern){
        if(phone == null || pattern == null)
            return "";
        
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
        
        return MessageFormat.format(pattern, phone.substring(0, 3), phone.substring(3, 6), phone.substring(6));
        
    }

    private static String unformatPhone(String phone)
    {
        if (phone == null)
            return null;
        return phone.replaceAll("\\D", "");
    }

}
