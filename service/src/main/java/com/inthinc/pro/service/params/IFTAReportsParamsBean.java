package com.inthinc.pro.service.params;

import java.util.Date;
import java.util.Locale;

import javax.ws.rs.QueryParam;

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
public class IFTAReportsParamsBean {

	// Common parameters for all IFTA web services
	Integer groupID;
	Date startDate;
	Date endDate;
	Boolean iftaOnly;
	
	/**
	 * Admitted values in the query string are the 
	 * <a href="http://ftp.ics.uci.edu/pub/ietf/http/related/iso639.txt">ISO Language Codes</a>.</br>
	 * See {@link java.util.Locale}
	 */
	@QueryParam("locale")
	@ValidLocale
	Locale locale;
	
	MeasurementType measurementType;

	
	
	// Helper to obtain default values
	TiwiproPrincipal principal;
	
	
	/**
	 * @return the groupID
	 */
	public Integer getGroupID() {
		return groupID;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @return the iftaOnly
	 */
	public Boolean getIftaOnly() {
		return iftaOnly;
	}

	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @return the measurementType
	 */
	public MeasurementType getMeasurementType() {
		return measurementType;
	}

	/**
	 * @param principal the principal to set
	 */
	public void setPrincipal(TiwiproPrincipal principal) {
		this.principal = principal;
	}
	
}
