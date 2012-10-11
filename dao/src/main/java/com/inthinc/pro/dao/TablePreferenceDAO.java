package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.TablePreference;

public interface TablePreferenceDAO extends GenericDAO<TablePreference, Integer>
{
    List<TablePreference> getTablePreferencesByUserID(Integer userID);
}
