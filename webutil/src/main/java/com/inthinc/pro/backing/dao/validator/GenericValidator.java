package com.inthinc.pro.backing.dao.validator;

public interface GenericValidator {

	public boolean isValid(String input);
	public String invalidMsg();
}
