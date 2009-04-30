package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.springframework.security.context.SecurityContextHolder;
public class LocaleBean extends BaseBean
{
    private static final List<Locale> supportedLocles = new ArrayList<Locale>();
    private static final List<SelectItem> supportedLocalesItems = new ArrayList<SelectItem>();
    static
    {
        Iterator<Locale> iterator = FacesContext.getCurrentInstance().getApplication().getSupportedLocales();
        while (iterator.hasNext())
        {
            Locale locale = iterator.next();
            supportedLocles.add(locale);
            supportedLocalesItems.add(new SelectItem(locale, locale.getDisplayName(locale)));
        }
    }

    public Locale getLocale()
    {
        // If the user has NOT logged in yet, the Principle will be the String "Anonymous". In this case, return the requests locale
        // If the user has logged in, then the Principle will be a User object which contains a Locale
        if (String.class.isInstance(SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
            return FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
        else
            return getUser().getLocale();
        
        
    }

    public List<Locale> getSupportedLocales()
    {
        return supportedLocles;
    }
    
    public List<SelectItem> getSupportedLocalesItems()
    {
        return supportedLocalesItems;
    }
    
    public Locale[] getAllLocales()
    {
        return Locale.getAvailableLocales();
    }
}
