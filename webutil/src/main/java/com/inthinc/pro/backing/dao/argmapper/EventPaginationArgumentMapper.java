package com.inthinc.pro.backing.dao.argmapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.inthinc.pro.backing.dao.mapper.BaseUtilMapper;
import com.inthinc.pro.backing.dao.model.DaoMethod;
import com.inthinc.pro.model.event.NoteType;

public class EventPaginationArgumentMapper extends AbstractArgumentMapper {
//    Integer  getEventCount(Integer groupID, Date startDate, Date endDate, Integer includeForgiven, List<NoteType> noteTypes, List<TableFilterField> filters);

//    List<Event> getEventPage(Integer groupID, Date startDate, Date endDate, Integer includeForgiven, List<NoteType> noteTypes, PageParams pageParams);

    @Override
    public Object[] convertArgs(DaoMethod daoMethod, List<Class<?>> paramTypes, Object[] args) {
        BaseUtilMapper mapper = new BaseUtilMapper();
        List<Object> methodArgs = new ArrayList<Object>();
        Object param = null;
        Class<?> paramType = null;
        for (int i = 0; i < args.length; i++) {
            // groupID and forgiven flag
            if (i == 0 || i == 3) {
                methodArgs.add(args[i]);
            }
            else if (i == 1 || i == 2) {
                Long seconds = (Long)args[i];
                methodArgs.add(new DateTime(seconds * 1000l).toDate());
            }
            else if (i == 4) {
                param = args[4];
                paramType = paramTypes.get(4);
            }
            else {
                List<NoteType> noteTypeList = new ArrayList<NoteType>();
                for (Integer eventType : (Integer[])args[i]) {
                    noteTypeList.add(NoteType.valueOf(eventType));
                }
                methodArgs.add(noteTypeList);
            }
        }

        if (param instanceof java.util.Map) {
            methodArgs.add(mapper.convertToModelObject((Map<String, Object>) param, paramType));
        } else {
            methodArgs.add(param);
        }

        return methodArgs.toArray();
    }

}
