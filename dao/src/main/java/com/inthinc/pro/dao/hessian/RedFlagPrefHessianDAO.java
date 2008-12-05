package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.RedFlagPrefDAO;
import com.inthinc.pro.model.RedFlagPref;

public class RedFlagPrefHessianDAO extends GenericHessianDAO<RedFlagPref, Integer> implements RedFlagPrefDAO
{
    @Override
    public List<RedFlagPref> getRedFlagPrefsInGroupHierarchy(Integer groupID)
    {
        final List<Map<String, Object>> ids = getSiloService().getRedFlagPrefIDsInGroupHierarchy(groupID);
        final ArrayList<RedFlagPref> flags = new ArrayList<RedFlagPref>(ids.size());
        for (final Map<String, Object> map : ids)
        {
            Integer redFlagPrefID = getReturnKey(map);
            flags.add(findByID(redFlagPrefID));
        }
        return flags;
    }
}
