package com.inthinc.pro.backing.dao.argmapper;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.inthinc.pro.backing.dao.model.DaoMethod;

public class EventNearLocArgumentMapper extends AbstractArgumentMapper {

    @Override
    public Object[] convertArgs(DaoMethod daoMethod, List<Class<?>> paramTypes, Object[] args) {
        List<Object> methodArgs = new ArrayList<Object>();
        for (int i = 0; i < args.length; i++) {
            // driverID
            if (i == 3 || i == 4) {
                Long seconds = (Long) args[i];
                methodArgs.add(new DateTime(seconds * 1000l).toDate());
            } else {
                methodArgs.add(args[i]);
            }
        }
        return methodArgs.toArray();
    }

}