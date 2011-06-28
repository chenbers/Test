package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.TextEnum;

public class AdminTables {

    public enum AdminUsersEntries implements TextEnum {
	/* Person Attributes */
	FULL_NAME("name"),

	FIRST_NAME("first"),
	MIDDLE_NAME("middle"),
	LAST_NAME("last"),
	SUFFIX("suffix"),
	DOB_MAIN("dob"),
	DOB_ADD_EDIT("dobInputDate"),
	EMP_ID("empId"),
	REPORTS_TO("reportsTo"),
	TITLE("title"),
	LOCALE("displayName"),
	TIME_ZONE("timeZone"),
	MEASUREMENT("measurementType"),
	FUEL_RATIO("fuelEfficiencyType"),
	GENDER("gender"),
	PHONE_1("priPhone"),
	PHONE_2("secPhone"),
	EMAIL_1("priMail"),
	EMAIL_2("secMail"),
	TEXT_MESSAGE_1("priText"),
	TEXT_MESSAGE_2("secText"),
	INFORMATION("info"),
	WARNING("warn"),
	CRITICAL("crit"),

	/* User Attributes */
	USER_NAME("username"),
	PASSWORD("password"),
	PASSWORD_AGAIN("confirmPassword"),
	GROUP("groupName"),
	ROLES("role"),
	USER_STATUS("description"),

	/* Driver Attributes */
	LICENSE_NUMBER("license"),
	CLASS("licenseClass"),
	STATE("state"),
	EXPIRATION_MAIN("expiration"),
	EXPIRATION_ADD_EDIT("expirationInputDate"),
	CERTIFICATIONS("certification"),
	DOT("dot"),
	TEAM("teamName"),
	STATUS("description"),
	BAR_CODE("barcode"),
	RFID1("rfid1"),
	RFID2("rfid2"), ;

	private String text;

	private AdminUsersEntries(String text) {
	    this.text = text;
	}

	@Override
	public String getText() {
	    return text;
	}

    }

}
