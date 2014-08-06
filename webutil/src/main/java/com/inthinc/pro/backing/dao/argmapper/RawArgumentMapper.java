package com.inthinc.pro.backing.dao.argmapper;

import java.util.List;

import com.inthinc.pro.backing.dao.model.DaoMethod;

public class RawArgumentMapper extends AbstractArgumentMapper {

    @Override
    public Object[] convertArgs(DaoMethod daoMethod, List<Class<?>> paramTypes, Object[] args) {
        return args;
    }

}
