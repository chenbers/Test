package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.automation.utils.RandomValues;
import com.inthinc.pro.selenium.pageEnums.TAE.Fuel_Ratio;
import com.inthinc.pro.selenium.pageEnums.TAE.Locale;
import com.inthinc.pro.selenium.pageEnums.TAE.Measurement;
import com.inthinc.pro.selenium.pageEnums.TAE.RedFlagPrefs;
import com.inthinc.pro.selenium.pageObjects.PageMyAccount;

public class EditMyAccountTest extends WebRallyTest {

	private PageMyAccount my;
	private RandomValues random;

	@Before
	public void setupPage() {
		random = new RandomValues();
		my = new PageMyAccount();
	}

	@Test(timeout=300000)
	public void CancelButton_Changes() {
		set_test_case("TC1271");
		my.loginProcess("tnilson", "password123");
		my._link().myAccount().click();
		/* Get original Values */
		String originalEmail1 = my._text().email1().getText();
		String originalEmail2 = my._text().email2().getText();
		
		String originalPhone1 = my._text().phone1().getText();
		String originalPhone2 = my._text().phone2().getText();
		
		String originalText1 = my._text().textMessage1().getText();
		String originalText2 = my._text().textMessage2().getText();
		
		
		String originalCritical = my._text().redFlagCritical().getText();
		String originalWarning = my._text().redFlagWarn().getText();
		String originalInformation = my._text().redFlagInfo().getText();
		
		String originalLocale = my._text().locale().getText();
		String originalMeasurement = my._text().measurement().getText();
		String originalFuelRatio = my._text().fuelEfficiency().getText();
		
		
		/* Edit button */
		my._button().edit().click();

		/* Login Info */
		my._select().locale().select(Locale.ENGLISH);
		my._select().measurement().select(Measurement.METRIC);
		my._select().fuelEfficiency().select(Fuel_Ratio.METRIC_LITER_PER_KILO);

		/* Contact Info */
		my._textField().email1().type(random.getEmail());
		my._textField().email2().type(random.getEmail());
		my._textField().phone1().type(random.getPhoneNumber());
		my._textField().phone2().type(random.getPhoneNumber());
		my._textField().textMessage1().type(random.getTextMessageNumber());
		my._textField().textMessage2().type(random.getTextMessageNumber());

		/* Red Flags */
		my._select().information().select(random.getEnum(RedFlagPrefs.TEXT1, originalInformation));
		my._select().warning().select(random.getEnum(RedFlagPrefs.TEXT1, originalWarning));
		my._select().critical().select(random.getEnum(RedFlagPrefs.TEXT1, originalCritical));

		/* Cancel Changes */
		my._button().cancel().click();

		/* Verify Changes did not take effect */
		/* Login Info */        
		my._text().userName().validate("tnilson");
		my._text().locale().validate(originalLocale);
		my._text().measurement().validate(originalMeasurement);
		my._text().fuelEfficiency().validate(originalFuelRatio);

		/* Account Info */
		my._text().name().validate("Tina L Nilson");
		my._text().group().validate("Top");
		my._text().team().validate("Skip's Team");

		/* Red Flags */
		my._text().redFlagInfo().validate(originalInformation);
		my._text().redFlagWarn().validate(originalWarning);
		my._text().redFlagCritical().validate(originalCritical);

		/* Contact Info */
		my._text().email1().validate(originalEmail1);
		my._text().email2().validate(originalEmail2);
		my._text().phone1().validate(originalPhone1);
		my._text().phone2().validate(originalPhone2);
		my._text().textMessage1().validate(originalText1);
		my._text().textMessage2().validate(originalText2);
	}

	@Test(timeout=300000)
	public void SaveButton_Changes() {
		set_test_case("TC1280");
		my.loginProcess("jwimmer", "password");
		my._link().myAccount().click();

		/* Edit button */
		my._button().edit().click();

		/* Login Info */
		my._select().locale().select(Locale.ENGLISH);
		my._select().measurement().select(Measurement.ENGLISH);
		my._select().fuelEfficiency().select(Fuel_Ratio.ENGLISH_MILES_UK);

		/* Contact Info */

		my._textField().email1().type("tina1965@test.com");
		my._textField().email2().type("tlc1965@test.com");
		my._textField().phone1().type("801-777-7777");
		my._textField().phone2().type("801-999-9999");
		my._textField().textMessage1().type("8017779999@tmomail.net");
		my._textField().textMessage2().type("8019997777@tmomail.net");

		/* Red Flags */
		my._select().information().select(RedFlagPrefs.TEXT1);
		my._select().warning().select(RedFlagPrefs.EMAIL1);
		my._select().critical().select(RedFlagPrefs.PHONE1);

		/* Save Changes */
		my._button().save().click();
		my.getSelenium().pause(10, "wait for page to save");
		/* Verify Changes Display */
		/* Login Info */
		my._text().userName().validate("tnilson");
		my._text().locale().validate(Locale.ENGLISH.getText());
		my._text().measurement().validate("English");
		my._text().fuelEfficiency().validate("Miles Per Gallon (UK)");

		/* Account Info */
		my._text().name().validate("Tina L Nilson");
		my._text().group().validate("Top");
		my._text().team().validate("Skip's Team");

		/* Red Flags */
		my._text().redFlagInfo().validate(RedFlagPrefs.TEXT1, ":", "");
		my._text().redFlagWarn().validate(RedFlagPrefs.EMAIL1, ":", "");
		my._text().redFlagCritical().validate(RedFlagPrefs.PHONE1, ":", "");

		/* Contact Info */
		my._text().email1().validate("tina1965@test.com");
		my._text().email2().validate("tlc1965@test.com");
		my._text().phone1().validate("801-777-7777");
		my._text().phone2().validate("801-999-9999");
		my._text().textMessage1().validate("8017779999@tmomail.net");
		my._text().textMessage2().validate("8019997777@tmomail.net");
	}

	@Test(timeout=300000)
	public void EmailFormatError() {
		set_test_case("TC1271");//TODO: find the actual testcase for this
		my.loginProcess("tnilson", "password");
		my._link().myAccount().click();

		my._button().edit().click();
		my._textField().email1().type("tina1965test.com");
		my._button().save().click();

		// Clear?//
		my._textField().email1().type("tina1965@test.com");
		my._button().save().click();
		my._text().email1().validate("tina1965@test.com");

		my._button().edit().click();
		my._textField().email2().type("tlc1965@test");
		my._button().save().click();
		// Validate Error//
		// Clear?//
		my._textField().email2().type("tlc1965@test.com");
		my._button().save().click();
		my._text().email2().validate("tlc1965@test.com");
	}

	@Test(timeout=300000)
	public void MeasurementValidation() {
		set_test_case("TC1275");
		my.loginProcess("tnilson", "password");
		my._link().myAccount().click();

		my._button().edit().click();
		my._select().measurement().select("Metric");
		my._select().fuelEfficiency().select("Kilometers Per Liter");
		my._button().save().click();

		// validate pages for Metric Units Goes Here//

		my._button().edit().click();
		my._select().measurement().select("English");
		my._select().fuelEfficiency().select("Miles Per Gallon (US)");
		my._button().save().click();

		// validation for English Units Goes Here//

	}

	@Test(timeout=300000)
	public void FuelRatioValidation() {
		set_test_case("TC1273");
		my.loginProcess("tnilson", "password");
		my._link().myAccount().click();

		my._button().edit().click();
		my._select().measurement().select("Metric");
		my._select().fuelEfficiency().select("Kilometers Per Liter");
		my._button().save().click();
		// Validate KM Per Liter Here//

		my._button().edit().click();
		my._select().measurement().select("English");
		my._select().fuelEfficiency().select("Miles Per Gallon (US)");
		my._button().save().click();
		// Validate MPG Here//

	}

	@Test(timeout=300000)
	public void ClearFieldsValidation() {
		set_test_case("TC1276");
		my.loginProcess("tnilson", "password");
		my._link().myAccount().click();

		my._button().edit().click();

		/* Contact Info */

		my._textField().email1().type("");
		my._textField().email2().type("");
		my._textField().phone1().type("");
		my._textField().phone2().type("");
		my._textField().textMessage1().type("");
		my._textField().textMessage2().type("");

		/* Save Empty Fields */

		my._button().save().click();

		// Email Required//
	}

	@Test(timeout=300000)
	public void PhoneMaxCharError() {
		set_test_case("TC1277");
		my.loginProcess("tnilson", "password");
		my._link().myAccount().click();
		// my.link_myAccount().click();
		my._button().edit().click();
		my._textField().phone1().type("801-777-7777-44444421");
		my._button().save().click();
		// Validate Error//
		// Clear fields?//
		my._textField().phone1().type("801-777-7777");
		my._button().save().click();
		my._text().phone1().validate("801-777-7777");

		my._button().edit().click();
		my._textField().phone2().type("8");
		my._button().save().click();
		// Validate Error//
		// Clear fields?//
		my._textField().phone2().type("801-999-9999");
		my._button().save().click();
		my._text().phone2().validate("801-999-9999");
	}

	@Test(timeout=300000)
	public void PhoneMissingCharError() {
		set_test_case("TC1278");
		my.loginProcess("tnilson", "password");
		my._link().myAccount().click();
		// my.link_myAccount().click();

		my._button().edit().click();
		my._textField().phone1().type("801-777-777");
		my._button().save().click();
		// Validate Error//
		// Clear fields?//
		my._textField().phone1().type("801-777-7777");
		my._button().save().click();
		my._text().phone1().validate("801-777-7777");

		my._button().edit().click();
		my._textField().phone2().type("801-999-999");
		my._button().save().click();
		// Validate Error//
		// Clear fields?//
		my._textField().phone2().type("801-999-9999");
		my._button().save().click();
		my._text().phone2().validate("801-999-9999");
	}

	@Test(timeout=300000)
	public void PhoneSpecialCharError() {
		set_test_case("TC1279");
		my.loginProcess("tnilson", "password");
		my._link().myAccount().click();
		// my.link_myAccount().click();
		my._button().edit().click();
		my._textField().phone1().type("801-@$%-777&");
		my._button().save().click();
		// Validate Error//
		// Clear fields?//
		my._textField().phone1().type("801-777-7777");
		my._button().save().click();
		String phone1 = my._text().phone1().getText();
		my.assertEquals("801-777-7777", phone1);

		my._button().edit().click();
		my._textField().phone2().type("801-ABC-$%+");
		my._button().save().click();
		// Validate Error//
		// Clear fields?//
		my._textField().phone2().type("801-999-9999");
		my._button().save().click();
		my._text().phone2().validate("801-999-9999");
	}

	@Test(timeout=300000)
	public void TextMsgFormatError() {
		set_test_case("TC1282");
		my.loginProcess("tnilson", "password");
		my._link().myAccount().click();

		my._button().edit().click();
		my._textField().textMessage1().type("8017779999tmomail.net");
		my._button().save().click();
		// Validate Error //
		// Clear fields?//
		my._textField().textMessage1().type("8017779999@tmomail.net");
		my._button().save().click();
		my._text().textMessage1().validate("8017779999@tmomail.net");

		my._button().edit().click();
		my._textField().textMessage2().type("8019997777@tmomail");
		my._button().save().click();
		// Validate Error //
		// Clear fields?//
		my._textField().textMessage2().type("8019997777@tmomail.net");
		my._button().save().click();
		my._text().textMessage2().validate("8019997777@tmomail.net");
	}

}
