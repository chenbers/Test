package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.springframework.security.context.SecurityContextHolder;

public class LocaleBean extends BaseBean {
    private static final List<Locale> supportedLocales = new ArrayList<Locale>();
    private static final List<SelectItem> supportedLocalesItems = new ArrayList<SelectItem>();
    private static Locale currentLocale;
    static {
        Iterator<Locale> iterator = FacesContext.getCurrentInstance().getApplication().getSupportedLocales();
        while (iterator.hasNext()) {
            Locale locale = iterator.next();
            supportedLocales.add(locale);
            supportedLocalesItems.add(new SelectItem(locale, locale.getDisplayName(locale)));
        }
    }

    @Override
    public Locale getLocale() {
        // If the user has NOT logged in yet, the Principle will be the String "Anonymous". In this case, return the requests locale
        // If the user has logged in, then the Principle will be a User object which contains a Locale
        if (String.class.isInstance(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
            currentLocale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
        }
        else {
            currentLocale = super.getLocale();
        }
        return currentLocale;
    }

    @Override
    public void setLocale(Locale locale) {
        super.setLocale(locale);
        currentLocale = locale;
    }

    public static boolean supportedLocale(Locale locale) {
        boolean supported = false;
        for (Locale l : supportedLocales) {
            if (l.equals(locale))
                supported = true;
        }
        return supported;
    }

    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    public List<Locale> getSupportedLocales() {
        return supportedLocales;
    }

    public List<SelectItem> getSupportedLocalesItems() {
        return supportedLocalesItems;
    }

    public Locale[] getAllLocales() {
        return Locale.getAvailableLocales();
    }
    
    public String getDisplayName(){
    	return currentLocale.getDisplayName(currentLocale);
    }
}
