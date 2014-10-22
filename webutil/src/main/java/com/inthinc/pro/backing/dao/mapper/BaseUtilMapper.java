package com.inthinc.pro.backing.dao.mapper;

import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.backing.dao.DateFormatBean;
import com.inthinc.pro.dao.hessian.mapper.AbstractMapper;

public class BaseUtilMapper extends AbstractMapper {

    DateFormatBean dateFormatBean;

    public DateFormatBean getDateFormatBean() {
        return dateFormatBean;
    }

    public void setDateFormatBean(DateFormatBean dateFormatBean) {
        this.dateFormatBean = dateFormatBean;
    }

    public Map<String, Object> convertToMap(Object modelObject, boolean includeNonUpdateables) {
        final Map<Object, Map<String, Object>> handled = new HashMap<Object, Map<String, Object>>();
        return convertToMap(modelObject, handled, includeNonUpdateables);

    }
}
