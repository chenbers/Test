package com.inthinc.pro.dao;

import com.inthinc.pro.model.form.FormSubmission;

public interface FormsDAO extends GenericDAO<Integer, Integer> {

    public FormSubmission getForm(Long timestamp, Integer vehicleID);
}
