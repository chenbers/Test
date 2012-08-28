package com.inthinc.pro.dao;

import java.util.Date;
import java.util.List;

import com.inthinc.forms.common.model.SubmissionData;
import com.inthinc.forms.common.model.enums.TriggerType;

public interface FormsDAO extends GenericDAO<Integer, Integer> {

    public SubmissionData getForm(Long timestamp, Integer vehicleID);

    public List<SubmissionData> getSubmissions(TriggerType triggerType, Date startDate, Date endDate, Integer groupID);
}
