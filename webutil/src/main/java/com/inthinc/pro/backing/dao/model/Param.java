package com.inthinc.pro.backing.dao.model;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.backing.dao.ui.PickList;
import com.inthinc.pro.backing.dao.ui.SelectList;
import com.inthinc.pro.backing.dao.ui.UIInputType;
import com.inthinc.pro.convert.ConvertUtil;


public class Param
{
    String paramName;
	Class<?> paramType;
    String paramInputDesc;
    Class<?> inputType;
    Integer index;
    Boolean isAccountID;

	Object paramValue;
	List<Object> paramValueList;
    
	public Param(String paramName, Class<?> paramType, Integer index, Class<?> inputType, Object paramValue)
    {
    	this.paramName = paramName;
    	this.paramType = paramType;
    	this.index = index;
    	this.inputType = inputType;
    	this.paramValue = paramValue;
    }
    public Param()
    {
    	
    }
    public Class<?> getParamType()
    {
        return paramType;
    }
    public void setParamType(Class<?> paramType)
    {
        this.paramType = paramType;
        
    }
    public String getParamTypeName()
    {
        return paramType.toString();
    }
    public Object getParamValue()
    {
        return paramValue;
    }
    public void setParamValue(Object paramValue)
    {
   		this.paramValue = paramValue;
    }
    public String getParamName()
    {
        return paramName;
    }
    public void setParamName(String paramName)
    {
        this.paramName = paramName;
    }
    public String getParamInputDesc()
    {
        return paramInputDesc;
    }
    public void setParamInputDesc(String paramInputDesc)
    {
        this.paramInputDesc = paramInputDesc;
    }
    
    public Class<?> getInputType() {
		return inputType;
	}
	public void setInputType(Class<?> inputType) {
		this.inputType = inputType;
	}

    public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}

	public UIInputType getUiInputType() {
		if (getIsAccountID())
			return UIInputType.ACCOUNT_ID; 
		return UIInputType.forType(getInputType());
	}
	
    public List<SelectItem> getSelectList()
    {
    	if (getUiInputType().equals(UIInputType.SELECT_LIST)) {
    		try {
				SelectList selectList = (SelectList)(getInputType().newInstance());
				return selectList.getSelectList();
			} catch (InstantiationException e1) {
				// TODO:
			} catch (IllegalAccessException e1) {
				// TODO:
			}
    		
    	}
    	if (getUiInputType().equals(UIInputType.PICK_LIST)) {
    		try {
				PickList selectList = (PickList)(getInputType().newInstance());
				return selectList.getSelectList();
			} catch (InstantiationException e1) {
				// TODO:
			} catch (IllegalAccessException e1) {
				// TODO:
			}
    		
    	}
   		return null;
    }

	public Object getConvertedParamValue() {

		
		if (paramType.isArray()) {
			if (paramValueList != null) {
				Class<?> componentType = paramValueList.toArray().getClass().getComponentType();
				if (ConvertUtil.converterExists(componentType, paramType.getComponentType())) {
					return ConvertUtil.convertList(paramValueList, componentType, paramType.getComponentType());
				}
			}
			else {
				String params[] = paramValue.toString().trim().split(",");
				Object values[] = new Object[params.length]; 
				for (int i = 0; i < params.length; i++)
	            {
	                values[i] = ConvertUtil.convert(params[i], paramType.getComponentType());
	            }
				return values;
			}
		}
		if (ConvertUtil.converterExists(paramValue, paramType)) {
			return ConvertUtil.convert(paramValue, paramType);
			
		}
		return paramValue;
	}

	public Boolean getIsAccountID() {
		return isAccountID;
	}
	public void setIsAccountID(Boolean isAccountID) {
		this.isAccountID = isAccountID;
	}

	public List<Object> getParamValueList() {
		return paramValueList;
	}
	public void setParamValueList(List<Object> paramValueList) {
		this.paramValueList = paramValueList;
	}


}
