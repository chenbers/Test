package com.inthinc.pro.backing.dao.argmapper;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.inthinc.pro.backing.dao.model.DaoMethod;
import com.inthinc.pro.model.event.NoteType;

public class EventArgumentMapper extends AbstractArgumentMapper {

    @Override
    public Object[] convertArgs(DaoMethod daoMethod, List<Class<?>> paramTypes, Object[] args) {
        List<Object> methodArgs = new ArrayList<Object>();
        for (int i = 0; i < args.length; i++) {
            // groupID
            if (i == 0) {
                methodArgs.add(args[i]);
            } else if (i == 1 || i == 2) {
                Long seconds = (Long) args[i];
                methodArgs.add(new DateTime(seconds * 1000l).toDate());
            } else if (i == 4) {
                List<NoteType> noteTypeList = new ArrayList<NoteType>();
                for (Integer eventType : (Integer[]) args[i]) {
                    noteTypeList.add(NoteType.valueOf(eventType));
                }
                methodArgs.add(noteTypeList);
            }
        }

        // forgiven flag
        methodArgs.add(args[3]);
        return methodArgs.toArray();
    }

}
