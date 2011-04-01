package com.inthinc.pro.automation.utils;

import com.inthinc.pro.dao.hessian.mapper.AbstractMapper;

public class BaseUtilMapper extends AbstractMapper {
	
	DateFormatBean dateFormatBean;
	
    public DateFormatBean getDateFormatBean() {
		return dateFormatBean;
	}

	public void setDateFormatBean(DateFormatBean dateFormatBean) {
		this.dateFormatBean = dateFormatBean;
	}
}
