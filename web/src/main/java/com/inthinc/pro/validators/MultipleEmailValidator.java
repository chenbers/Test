package com.inthinc.pro.validators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;

import com.inthinc.pro.util.MessageUtil;

public class MultipleEmailValidator extends RegexValidator {
	public static final Pattern EMAIL_REGEX = Pattern
			.compile("([a-zA-Z0-9_!&`$|~#%=/?\\'\\.\\*\\^\\-\\+\\{\\}]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");

	public static final Pattern DELETED_REGEX = Pattern
			.compile("del[0-9]+@tiwi.com");

	@Override
	protected Pattern getRegex(UIComponent component) {
		return EMAIL_REGEX;
	}

	@Override
	public boolean isValid(String value, UIComponent component) {
		ArrayList<String> emailList = new ArrayList<String>(Arrays.asList(value
				.split("[,; ]+")));
		for (int i = 0; i < emailList.size(); i++) {
			if (super.isValid(emailList.get(i), component))
				return !DELETED_REGEX.matcher(value).matches();
			return false;
		}
		return false;
	}

	@Override
	protected String getDefaultErrorMessage() {
		return MessageUtil.getMessageString("error_emailInvalid");
	}
}
