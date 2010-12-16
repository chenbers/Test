package com.inthinc.pro.service.params;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.service.security.TiwiproPrincipal;
import com.inthinc.pro.service.validation.annotations.ValidLocale;

/**
 * Simple JavaBean to encapsulate the parameters received by a Web service
 * This bean will be used for validation of incoming parameters by applying the JSR-303
 * It will also be used for retrieving default values for null parameters  
 * 
 * @author dcueva
 *
 */
@Component
@Scope("prototype")
//@ValidStartEndDates
public class IFTAReportsParamsBean {
    public static final String DATE_FORMAT = "yyyyMMdd";
    protected static final Integer DAYS_BACK = -6;
	
    // Common parameters for all IFTA web services
    private List<Integer> groupIDList;
    private Integer groupID;
    private Boolean iftaOnly;
    private Date startDate;
    private Date endDate;
    /**
     * Admitted values in the query string are the 
     * <a href="http://ftp.ics.uci.edu/pub/ietf/http/related/iso639.txt">ISO Language Codes</a>.</br>
     * See {@link java.util.Locale}
     */
    @ValidLocale 
    private Locale locale; 
    private MeasurementType measurementType;

    /**
     * Helper to obtain default values
     */
    @Autowired
	TiwiproPrincipal principal;


	/**
	 * @return the groupIDList
	 */
	public List<Integer> getGroupIDList() {
		return groupIDList;
	}


	/**
	 * @param groupIDList the groupIDList to set
	 */
	public void setGroupIDList(List<Integer> groupIDList) {
		this.groupIDList = groupIDList;
	}

	/**
     * The startDate getter.
     * @return the startDate
     */
	public Date getStartDate() {
	    if (startDate == null) {
	        Calendar c = getMidnight();
	        c.add(Calendar.DAY_OF_YEAR, DAYS_BACK);
	    }
        return this.startDate;
    }

    /**
     * The groupID setter.
     * @param groupID the groupID to set
     */
    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    /**
     * The startDate setter.
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * The endDate setter.
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * The iftaOnly setter.
     * @param iftaOnly the iftaOnly to set
     */
    public void setIftaOnly(Boolean iftaOnly) {
        this.iftaOnly = iftaOnly;
    }



	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
	    if (endDate == null) {
	        endDate = getMidnight().getTime();
	    }
		return endDate;
	}


	/**
	 * @param endDate the endDate to set
	 */
	public Boolean getIftaOnly() {
		return (iftaOnly == null ? false : iftaOnly);
	}


	/**
	 * Return the locale.
	 * If the value is null, gets the default from the current authenticated user.
	 * @return the locale
	 */
	public Locale getLocale() {
	    if(locale == null) {
	        locale = principal.getPerson().getLocale();
	    }
		return locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}


	/**
	 * Return the measurement type.
	 * If the value is null, gets the default from the current authenticated user.
	 * @return the measurementType
	 */
	public MeasurementType getMeasurementType() {
        if (measurementType == null) measurementType = principal.getUser().getPerson().getMeasurementType();
		return measurementType;
	}


	/**
     * The MeasurementType setter.
     * @param measurementType the measurementType to set
	 */
	public void setMeasurementType(MeasurementType measurementType) {
		this.measurementType = measurementType;
	}
	
    /**
     * Create the Date for today and set it to midnight.
     * 
     * @return the date as Calendar
     */
    Calendar getMidnight() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0); // set hour to midnight
        today.set(Calendar.MINUTE, 0); // set minute in hour
        today.set(Calendar.SECOND, 0); // set second in minute
        today.set(Calendar.MILLISECOND, 0); // set millis in second
        return today;
    }
    
    /**
     * {@inheritDoc}
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "groupID:" + groupID +
            " startDate:" + getStartDate().toString() +
            " endDate:" + getEndDate().toString() +
            " iftaOnly:" + getIftaOnly() +
            " locale:" + getLocale() +
            " measurementType:" + getMeasurementType();
    }
	
}
