package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.RedFlagPref;

public interface RedFlagPrefDAO extends GenericDAO<RedFlagPref, Integer>
{
    List<RedFlagPref> getRedFlagPrefs(Integer accountID);
}
