package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.ZoneAlertDAO;
import com.inthinc.pro.model.ZoneAlert;

public class ZoneAlertHessianDAO extends GenericHessianDAO<ZoneAlert, Integer> implements ZoneAlertDAO
{
    @Override
    public List<ZoneAlert> getZoneAlertsInGroupHierarchy(Integer groupID)
    {
        final List<Map<String, Object>> ids = getSiloService().getZoneAlertIDsInGroupHierarchy(groupID);
        final ArrayList<ZoneAlert> flags = new ArrayList<ZoneAlert>(ids.size());
        for (final Map<String, Object> map : ids)
        {
            Integer ZoneAlertID = getReturnKey(map);
            flags.add(findByID(ZoneAlertID));
        }
        return flags;
    }
}
