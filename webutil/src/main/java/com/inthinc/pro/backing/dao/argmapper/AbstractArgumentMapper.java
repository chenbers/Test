package com.inthinc.pro.backing.dao.argmapper;

import java.util.List;

import com.inthinc.pro.backing.dao.model.DaoMethod;

public abstract class AbstractArgumentMapper implements ArgumentMapper {

    @Override
    abstract public Object[] convertArgs(DaoMethod daoMethod, List<Class<?>> paramTypes, Object[] args);

}
