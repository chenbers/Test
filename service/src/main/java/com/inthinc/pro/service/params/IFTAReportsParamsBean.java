package com.inthinc.pro.service.params;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.service.security.TiwiproPrincipal;
import com.inthinc.pro.service.validation.annotations.ValidLocale;
import com.inthinc.pro.service.validation.annotations.ValidStartEndDates;

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
@ValidStartEndDates
public class IFTAReportsParamsBean implements HasStartEndDates {

	// Common parameters for all IFTA web services
	//@NotNull
	//@Min(0)
	private List<Integer> groupIDList;
	
	private Date startDate;
	private Date endDate;
	
	/**
	 * Admitted values in the query string are the 
	 * <a href="http://ftp.ics.uci.edu/pub/ietf/http/related/iso639.txt">ISO Language Codes</a>.</br>
	 * See {@link java.util.Locale}
	 */
	@QueryParam("locale")
	@ValidLocale
	private Locale locale;
	
	// No validation required at this point for measuremetType. 
	// Type validation is done by RestEasy QueryParam unmarshalling. 
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
	 * @return the start date
	 */
	public Date getStartDate() {
		return startDate;
	}


	/**
	 * The start date setter.
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	/**
	 * @return the End date
	 */
	public Date getEndDate() {
		return endDate;
	}


	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	/**
	 * Return the locale.
	 * If the value is null, gets the default from the current authenticated user.
	 * @return the locale
	 */
	public Locale getLocale() {
		if (locale == null) locale = principal.getUser().getPerson().getLocale();
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
	 * @param measurementType the measurementType to set
	 */
	public void setMeasurementType(MeasurementType measurementType) {
		this.measurementType = measurementType;
	}

    /**
     * {@inheritDoc}
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "groupIDList:" + groupIDList +
            " startDate:" + getStartDate().toString() +
            " endDate:" + getEndDate().toString() +
            " locale:" + getLocale() +
            " measurementType:" + getMeasurementType();
    }

	
}
