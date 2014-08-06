package com.inthinc.pro.backing.dao.model;

import java.lang.reflect.Method;


public class DaoMethod
{
    Method method;
    int interfaceIdx;
    String description;
    CrudType crudType;
    Class<?> modelClass;
    Class<? extends com.inthinc.pro.backing.dao.mapper.BaseUtilMapper> mapperClass;
    String populateMethod;
    String daoID;
    String daoMethod;
    Class<? extends com.inthinc.pro.backing.dao.argmapper.AbstractArgumentMapper> daoParamMapper;
    String daoReturnValueName;
    Boolean useMapper;

    public DaoMethod(Method method, int interfaceIdx)
    {
        super();
        this.method = method;
        this.interfaceIdx = interfaceIdx;
    }
    public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public CrudType getCrudType() {
		return crudType;
	}
	public void setCrudType(CrudType crudType) {
		this.crudType = crudType;
	}
	public Class<?> getModelClass() {
		return modelClass;
	}
	public void setModelClass(Class<?> modelClass) {
		this.modelClass = modelClass;
	}
	public Class<? extends com.inthinc.pro.backing.dao.mapper.BaseUtilMapper> getMapperClass() {
		return mapperClass;
	}
	public void setMapperClass(
			Class<? extends com.inthinc.pro.backing.dao.mapper.BaseUtilMapper> mapperClass) {
		this.mapperClass = mapperClass;
	}
	public Method getMethod()
    {
        return method;
    }
    public void setMethod(Method method)
    {
        this.method = method;
    }
    public int getInterfaceIdx()
    {
        return interfaceIdx;
    }
    public void setInterfaceIdx(int interfaceIdx)
    {
        this.interfaceIdx = interfaceIdx;
    }
    public String getPopulateMethod() {
        return populateMethod;
    }
    public void setPopulateMethod(String populateMethod) {
        this.populateMethod = populateMethod;
    }
    
    public Class<? extends com.inthinc.pro.backing.dao.argmapper.AbstractArgumentMapper> getDaoParamMapper() {
        return daoParamMapper;
    }
    public void setDaoParamMapper(Class<? extends com.inthinc.pro.backing.dao.argmapper.AbstractArgumentMapper> daoParamMapper) {
        this.daoParamMapper = daoParamMapper;
    }
    public String getDaoReturnValueName() {
        return daoReturnValueName;
    }
    public void setDaoReturnValueName(String daoReturnValueName) {
        this.daoReturnValueName = daoReturnValueName;
    }
    public String getDaoID() {
        return daoID;
    }
    public void setDaoID(String daoID) {
        this.daoID = daoID;
    }
    public String getDaoMethod() {
        return daoMethod;
    }
    public void setDaoMethod(String daoMethod) {
        this.daoMethod = daoMethod;
    }
    public Boolean getUseMapper() {
        return useMapper;
    }
    public void setUseMapper(Boolean useMapper) {
        this.useMapper = useMapper;
    }
}
