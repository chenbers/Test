package com.inthinc.pro.backing;

import javax.faces.context.FacesContext;

import com.inthinc.pro.util.MessageUtil;;

public class ErrorTranslatorBean {
    private String errorAccessDeniedTitle = null;
    private String errorReturnToHomepage = null;
    private String errorUnavailableTitle = null;
    private String errorUnavailableMsg = null;
    private String errorTitle = null;
    private String errorAccessDeniedMsg = null;
    private String errorApplicationTitle = null;
    private String errorBaseMsg = null;

    public ErrorTranslatorBean() {
        super();
    }

    public String getErrorAccessDeniedTitle() { 
        return MessageUtil.getMessageString("error_access_denied_title",LocaleBean.getCurrentLocale());
    }

    public String getErrorReturnToHomepage() {
        return MessageUtil.getMessageString("error_return_to_homepage",LocaleBean.getCurrentLocale());
    }

    public String getErrorUnavailableTitle() {
        return MessageUtil.getMessageString("error_unavailable_title",LocaleBean.getCurrentLocale());
    }  

    public String getErrorUnavailableMsg() {
        return MessageUtil.getMessageString("error_unavailableMsg",LocaleBean.getCurrentLocale());
    }   

    public String getErrorTitle() {
        return MessageUtil.getMessageString("error_title",LocaleBean.getCurrentLocale());
    }  

    public String getErrorAccessDeniedMsg() {
        return MessageUtil.getMessageString("error_access_denied_msg",LocaleBean.getCurrentLocale());
    } 

    public String getErrorApplicationTitle() {
        return MessageUtil.getMessageString("error_application_title",LocaleBean.getCurrentLocale());
    }      
    
    public String getErrorBaseMsg() {
        return MessageUtil.getMessageString("error_baseMsg",LocaleBean.getCurrentLocale());
    }      
}
