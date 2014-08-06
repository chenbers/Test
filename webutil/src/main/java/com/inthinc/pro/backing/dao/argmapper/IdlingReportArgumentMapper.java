package com.inthinc.pro.backing.dao.argmapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.inthinc.pro.backing.dao.mapper.BaseUtilMapper;
import com.inthinc.pro.backing.dao.model.DaoMethod;

public class IdlingReportArgumentMapper extends AbstractArgumentMapper {
    
    @Override
    public Object[] convertArgs(DaoMethod daoMethod, List<Class<?>> paramTypes, Object[] args) {
        BaseUtilMapper mapper = new BaseUtilMapper();
        List<Object> methodArgs = new ArrayList<Object>();
        DateTime startTime = null;
        for (int i = 0; i < args.length; i++) {
            if (i == 1) {
                Long seconds = (Long)args[1];
                startTime = new DateTime(seconds * 1000l);
            }
            else if (i == 2) {
                Long seconds = (Long)args[2];
                methodArgs.add(new Interval(startTime, new DateTime(seconds * 1000l)));
            }
            else if (args[i] instanceof java.util.Map) {
                methodArgs.add(mapper.convertToModelObject((Map<String, Object>) args[i], paramTypes.get(i)));
            } else {
                methodArgs.add(args[i]);
            }
        }

        return methodArgs.toArray();
    }


}
