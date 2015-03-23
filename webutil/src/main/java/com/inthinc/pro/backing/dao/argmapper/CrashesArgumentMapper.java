package com.inthinc.pro.backing.dao.argmapper;

import java.util.List;

import com.inthinc.pro.backing.dao.model.DaoMethod;
import com.inthinc.pro.model.ForgivenType;

public class CrashesArgumentMapper extends BaseArgumentMapper {
	
    @Override
    public Object[] convertArgs(DaoMethod daoMethod, List<Class<?>> paramTypes, Object[] args) {
    	Object[] methodArgs = super.convertArgs(daoMethod, paramTypes, args);
    	
    	Integer includeForgiven = (Integer)methodArgs[3];
    	methodArgs[3] = (includeForgiven == 1) ? ForgivenType.INCLUDE : ForgivenType.EXCLUDE;
    	
    	return methodArgs;
    }


}
