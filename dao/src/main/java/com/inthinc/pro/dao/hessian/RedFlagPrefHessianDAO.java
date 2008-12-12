package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.RedFlagPrefDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.RedFlagPref;

public class RedFlagPrefHessianDAO extends GenericHessianDAO<RedFlagPref, Integer> implements RedFlagPrefDAO
{
    @Override
    public List<RedFlagPref> getRedFlagPrefs(Integer accountID)
    {
        try
        {
            return getMapper().convertToModelObject(getSiloService().getRedFlagPrefsByAcctID(accountID), RedFlagPref.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }
}
