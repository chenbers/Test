package com.inthinc.pro.backing.dao.argmapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.backing.dao.mapper.BaseUtilMapper;
import com.inthinc.pro.backing.dao.model.DaoMethod;

public class CrudUpdateArgumentMapper extends BaseArgumentMapper {

    @Override
    public Object[] convertArgs(DaoMethod daoMethod, List<Class<?>> paramTypes, Object[] args) {
//        BaseUtilMapper mapper = new BaseUtilMapper();
//        List<Object> methodArgs = new ArrayList<Object>();
//        // skip first argument
//        for (int i = 1; i < args.length; i++) {
//            if (args[i] instanceof java.util.Map) {
//                methodArgs.add(mapper.convertToModelObject((Map<String, Object>) args[i], paramTypes.get(i)));
//            }
//            else {
//                methodArgs.add(args[i]);
//            }
//        }
//
//        return methodArgs.toArray();
        
        Object[] skipFirstArgs = new Object[args.length-1];
        for (int i = 1; i < args.length; i++) {
            skipFirstArgs[i-1] = args[i];
        }
        paramTypes.remove(0);
        return super.convertArgs(daoMethod, paramTypes, skipFirstArgs);
        
    }

}
