package com.inthinc.pro.reports.forms;

import java.util.Locale;

import com.inthinc.pro.model.form.TriggerType;
import com.inthinc.pro.reports.ReportType;

public class DVIRPreTripReportCriteria extends DVIRReportCriteria {

    public DVIRPreTripReportCriteria(Locale locale) {
        super(ReportType.DVIR_PRETRIP,locale);
    }

    @Override
    protected TriggerType getTriggerType() {
        return TriggerType.PRE_TRIP;
    }

}
