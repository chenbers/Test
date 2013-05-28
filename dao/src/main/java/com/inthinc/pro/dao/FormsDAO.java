package com.inthinc.pro.dao;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.model.form.SubmissionData;
import com.inthinc.pro.model.form.TriggerType;

public interface FormsDAO extends GenericDAO<Integer, Integer> {

    public SubmissionData getForm(Long timestamp, Integer vehicleID);

    public List<SubmissionData> getSubmissions(TriggerType triggerType, Date startDate, Date endDate, Integer groupID);
    public SubmissionData getSingleSubmission(Integer vehicleID, Integer formDefinitionID, Date submissionDate, Integer groupID);
}
