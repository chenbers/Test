package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.TimeZoneDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.SupportedTimeZone;

public class TimeZoneHessianDAO extends GenericHessianDAO<SupportedTimeZone, Integer> implements TimeZoneDAO
{

    @Override
    public List<SupportedTimeZone> getSupportedTimeZones()
    {
        try
        {
            return getMapper().convertToModelObject(getSiloService().getTimezones(), SupportedTimeZone.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

}
