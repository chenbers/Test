package com.inthinc.pro.backing.dao.validator;

public class DefaultValidator extends BaseValidator {

	@Override
	public boolean isValid(String input) {
		return true;
	}

	@Override
	public String invalidMsg() {
		return "this is impossible";
	}

}
