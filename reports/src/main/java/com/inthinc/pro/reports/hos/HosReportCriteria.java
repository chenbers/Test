package com.inthinc.pro.reports.hos;

import java.util.Locale;

import org.apache.log4j.Logger;

import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;

public abstract class HosReportCriteria extends ReportCriteria {

    public static boolean HOS_INACTIVE_DRIVERS_DEFAULT = true;
    public static boolean HOS_ZERO_MILES_DRIVERS_DEFAULT = true;

    private static final Logger logger = Logger.getLogger(HosReportCriteria.class);

    public HosReportCriteria() {

    }

    public HosReportCriteria(ReportCriteria reportCriteria) {
        super(reportCriteria);
    }

    public HosReportCriteria(ReportType report, String entityName, Locale locale) {
        super(report, entityName, locale);
    }

    /**
     * Overrides {@link #getIncludeInactiveDrivers()}
     * 
     * @see com.inthinc.pro.reports.ReportCriteria#getIncludeInactiveDrivers()
     * 
     *      FYI, HOS reports do not currently have options to IGNORE inactive drivers or drivers with zero miles
     */
    @Override
    public Boolean getIncludeInactiveDrivers() {
        return HOS_INACTIVE_DRIVERS_DEFAULT;
    }

    /**
     * Overrides {{@link #setIncludeInactiveDrivers(Boolean)} keeping in mind that HOS reports do not currently have options to IGNORE inactive drivers or drivers with zero miles.
     * 
     * @see com.inthinc.pro.reports.ReportCriteria#setIncludeInactiveDrivers(java.lang.Boolean)
     */
    @Override
    @Deprecated
    public void setIncludeInactiveDrivers(Boolean includeInactiveDrivers) {
        logger.warn("public void setIncludeInactiveDrivers(Boolean " + includeInactiveDrivers + ") should not be called!");
    }

    /**
     * Overrides {@link #getIncludeZeroMilesDrivers()}
     * 
     * @see com.inthinc.pro.reports.ReportCriteria#getIncludeZeroMilesDrivers()
     * 
     *      FYI, HOS reports do not currently have options to IGNORE inactive drivers or drivers with zero miles
     */
    @Override
    public Boolean getIncludeZeroMilesDrivers() {
        return HOS_ZERO_MILES_DRIVERS_DEFAULT;
    }

    /**
     * Overrides {{@link #setIncludeZeroMilesDrivers(Boolean)} keeping in mind that HOS reports do not currently have options to IGNORE inactive drivers or drivers with zero miles.
     * 
     * @see com.inthinc.pro.reports.ReportCriteria#setIncludeZeroMilesDrivers(java.lang.Boolean)
     */
    @Override
    @Deprecated
    public void setIncludeZeroMilesDrivers(Boolean includeZeroMilesDrivers) {
        logger.warn("public void setIncludeZeroMilesDrivers(Boolean " + includeZeroMilesDrivers + ") should not be called!");
    }
}
