package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.ZoneDAO;
import com.inthinc.pro.model.Zone;

public class ZoneHessianDAO extends GenericHessianDAO<Zone, Integer> implements ZoneDAO
{
    @Override
    public List<Zone> getZonesInGroupHierarchy(Integer groupID)
    {
        final List<Map<String, Object>> ZoneIDs = getSiloService().getZoneIDsInGroupHierarchy(groupID);
        final ArrayList<Zone> people = new ArrayList<Zone>(ZoneIDs.size());
        for (final Map<String, Object> map : ZoneIDs)
        {
            Integer zoneID = getReturnKey(map);
            people.add(findByID(zoneID));
        }
        return people;
    }
}
