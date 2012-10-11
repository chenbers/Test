package com.inthinc.pro.dao.hessian;

import java.util.List;

import com.inthinc.pro.dao.TimeZoneDAO;
import com.inthinc.pro.model.SupportedTimeZone;

public class TimeZoneHessianDAO extends GenericHessianDAO<SupportedTimeZone, Integer> implements TimeZoneDAO
{

    @Override
    public List<SupportedTimeZone> getSupportedTimeZones()
    {
        // don't catch the empty result set exception for this, because an empty result set in this case is a real error
        return getMapper().convertToModelObject(getSiloService().getTimezones(), SupportedTimeZone.class);
    }

}
