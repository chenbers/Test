package com.inthinc.pro.backing.dao.mapper;

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
}
