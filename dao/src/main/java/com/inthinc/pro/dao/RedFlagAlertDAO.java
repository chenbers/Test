package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.RedFlagAlert;

public interface RedFlagAlertDAO extends GenericDAO<RedFlagAlert, Integer>
{
    List<RedFlagAlert> getRedFlagAlerts(Integer accountID);
}
