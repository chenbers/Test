package com.inthinc.pro.dao.hessian.mapper;

import java.util.List;
import java.util.Map;

public interface Mapper
{
//    T convertToModelObject(Map<String, Object> map);

    <E> E convertToModelObject(Map<String, Object> map, Class<E> type);

//    List<T> convertToModelObject(List<Map<String, Object>> list);

    <E> List<E> convertToModelObject(List<Map<String, Object>> list, Class<E> type);

    Map<String, Object> convertToMap(Object modelObject);
}
