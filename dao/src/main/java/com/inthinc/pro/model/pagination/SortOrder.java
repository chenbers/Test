/**
 * 
 */
package com.inthinc.pro.model.pagination;

import com.inthinc.pro.model.BaseEnum;

public enum SortOrder implements BaseEnum {	
	ASCENDING("0", 0),
	DESCENDING("1", 1);
	
	private String value;
	private Integer code;
	SortOrder(String value, Integer code)
	{
		this.value = value;
		this.code = code;
	}
			
	public String toString()
	{
		return value;
	}

	@Override
	public Integer getCode() {
		return code;
	}
}