package com.inthinc.pro.dao;

import java.util.Date;

import com.inthinc.forms.common.model.SubmissionData;
import com.inthinc.forms.common.model.enums.TriggerType;
import com.inthinc.pro.model.form.FormSubmission;

public interface FormsDAO extends GenericDAO<Integer, Integer> {

    public FormSubmission getForm(Long timestamp, Integer vehicleID);

    public SubmissionData getSubmissions(TriggerType triggerType, Date startDate, Date endDate, Integer groupID);
}
