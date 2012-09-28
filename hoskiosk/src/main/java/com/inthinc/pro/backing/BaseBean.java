package com.inthinc.pro.backing;

import java.io.Serializable;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.security.userdetails.ProUser;

public class BaseBean  implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(BaseBean.class);
    
    
    public boolean isLoggedIn() {
        return isProUserLoggedIn();
    }

    public Person getPerson() {
        return getProUser().getDriver().getPerson();
    }

    public Driver getDriver() {
        return getProUser().getDriver();
    }
    public ProUser getProUser() {
        return (ProUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public boolean isProUserLoggedIn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof ProUser;
    }


    public Locale getLocale() {
        if (getPerson().getLocale() != null)
            return getPerson().getLocale();
        else
            return Locale.US;
    }
    
    public DateTimeZone getDateTimeZone() {
        return DateTimeZone.forTimeZone(getPerson().getTimeZone());
    }
    
    public void setLocale(Locale locale) {
        getPerson().setLocale(locale);
    }

    public MeasurementType getMeasurementType() {
        if (getPerson().getMeasurementType() != null)
            return getPerson().getMeasurementType();
        else
            return MeasurementType.ENGLISH;
    }

    public FuelEfficiencyType getFuelEfficiencyType() {
        if (getPerson().getFuelEfficiencyType() != null) {
            return getPerson().getFuelEfficiencyType();
        }
        if (getPerson().getMeasurementType() != null) {
            if (getPerson().getMeasurementType() == MeasurementType.ENGLISH)
                return FuelEfficiencyType.MPG_US;
            else
                return FuelEfficiencyType.KMPL;
        }
        else
            return FuelEfficiencyType.MPG_US;
    }


}
