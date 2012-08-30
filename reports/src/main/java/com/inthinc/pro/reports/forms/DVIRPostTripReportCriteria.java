package com.inthinc.pro.reports.forms;

import java.util.Locale;

import com.inthinc.forms.common.model.enums.TriggerType;
import com.inthinc.pro.reports.ReportType;

public class DVIRPostTripReportCriteria extends DVIRReportCriteria {

    public DVIRPostTripReportCriteria(Locale locale) {
        super(ReportType.DVIR_POSTTRIP, locale);
    }

    @Override
    protected TriggerType getTriggerType() {
        // TODO Auto-generated method stub
        return TriggerType.POST_TRIP;
    }

}
