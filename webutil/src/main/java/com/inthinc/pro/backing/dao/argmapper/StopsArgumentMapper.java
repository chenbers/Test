package com.inthinc.pro.backing.dao.argmapper;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.inthinc.pro.backing.dao.model.DaoMethod;

public class StopsArgumentMapper extends AbstractArgumentMapper {
    
    @Override
    public Object[] convertArgs(DaoMethod daoMethod, List<Class<?>> paramTypes, Object[] args) {
        List<Object> methodArgs = new ArrayList<Object>();
        DateTime startTime = null;
        for (int i = 0; i < args.length; i++) {
            if (i == 0) {
                methodArgs.add(args[i]); // driver id
                methodArgs.add(""); // driver name
            }
            else if (i == 1) {
                Long seconds = (Long)args[1];
                startTime = new DateTime(seconds * 1000l);
            }
            else if (i == 2) {
                Long seconds = (Long)args[2];
                methodArgs.add(new Interval(startTime, new DateTime(seconds * 1000l)));
            }
        }

        return methodArgs.toArray();
    }


}