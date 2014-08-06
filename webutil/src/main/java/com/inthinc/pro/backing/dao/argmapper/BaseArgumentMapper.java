package com.inthinc.pro.backing.dao.argmapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.inthinc.pro.backing.dao.mapper.BaseUtilMapper;
import com.inthinc.pro.backing.dao.model.DaoMethod;
import com.inthinc.pro.backing.dao.ui.SelectList;

public class BaseArgumentMapper extends AbstractArgumentMapper {

	@Override
	public Object[] convertArgs(DaoMethod daoMethod, List<Class<?>> paramTypes,
			Object[] args) {
		BaseUtilMapper mapper = new BaseUtilMapper();
		List<Object> methodArgs = new ArrayList<Object>();
		for (int i = 0; i < args.length; i++) {
			if (args[i] instanceof java.util.Map) {
				methodArgs.add(mapper.convertToModelObject((Map<String, Object>) args[i], paramTypes.get(i)));
			} else if (paramTypes.get(i).equals(java.util.Date.class)) {
				Long seconds = (Long) args[i];
				methodArgs.add(new DateTime(seconds * 1000l).toDate());
			} else if (SelectList.class.isAssignableFrom(paramTypes.get(i))
					&& args[i] instanceof Integer) {
				try {
					SelectList selectList = (SelectList) paramTypes.get(i).newInstance();
					methodArgs.add(selectList.valueOf(args[i]));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				methodArgs.add(args[i]);
			}
		}

		return methodArgs.toArray();
	}

}
