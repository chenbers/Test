package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.silo.SiloDef;

public interface SiloDAO extends GenericDAO<SiloDef, Integer>
{
    List<SiloDef> getSiloDefs();
}
