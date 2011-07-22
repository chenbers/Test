package com.inthinc.pro.backing;

import java.util.Locale;

import javax.faces.context.FacesContext;

import org.springframework.security.context.SecurityContextHolder;

public class LocaleBean extends BaseBean {

    @Override
    public Locale getLocale() {
        // If the user has NOT logged in yet, the Principle will be the String "Anonymous". In this case, return the requests locale
        // If the user has logged in, then the Principle will be a User object which contains a Locale
        if (String.class.isInstance(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
            return FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
        }
        return super.getLocale();
    }

    @Override
    public void setLocale(Locale locale) {
        super.setLocale(locale);
    }

}
