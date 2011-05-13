package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.automation.utils.RandomValues;
import com.inthinc.pro.selenium.pageEnums.TAE.Fuel_Ratio;
import com.inthinc.pro.selenium.pageEnums.TAE.Locale;
import com.inthinc.pro.selenium.pageEnums.TAE.Measurement;
import com.inthinc.pro.selenium.pageEnums.TAE.RedFlagPrefs;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageMyAccount;

public class EditMyAccountTest extends WebRallyTest {

	private PageMyAccount my;
	private PageLogin login;
	private RandomValues random;

	@Before
	public void setupPage() {
		random = new RandomValues();
		my = new PageMyAccount();
		login = new PageLogin();
	}

	@Test
	public void CancelButton_Changes() {
		set_test_case("TC1271");
		login.loginProcess("tnilson", "password");
		my._link().myAccount().click();
		/* Get original Values */
		String email1 = my._text().email1().getText();
		String email2 = my._text().email2().getText();
		
		String phone1 = my._text().phone1().getText();
		String phone2 = my._text().phone2().getText();
		
		String text1 = my._text().textMessage1().getText();
		String text2 = my._text().textMessage2().getText();
		
		
		String critical = my._text().redFlagCritical().getText();
		String warning = my._text().redFlagWarn().getText();
		String information = my._text().redFlagInfo().getText();
		
		String locale = my._text().locale().getText();
		String measurement = my._text().measurement().getText();
		String fuelRatio = my._text().fuelEfficiency().getText();
		
		
		/* Edit button */
		my._button().edit().click();
		my.getSelenium().pause(5, "wait for edit screen");

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
		my._select().information().select(random.getEnum(RedFlagPrefs.TEXT1, information));
		my._select().warning().select(random.getEnum(RedFlagPrefs.TEXT1, warning));
		my._select().critical().select(random.getEnum(RedFlagPrefs.TEXT1, critical));

		/* Cancel Changes */
		my._button().cancel().click();

		/* Verify Changes did not take effect */
		/* Login Info */        
		my._text().userName().validateText("tnilson");
		my._text().locale().validateText(locale);
		my._text().measurement().validateText(measurement);
		my._text().fuelEfficiency().validateText(fuelRatio);

		/* Account Info */
		my._text().name().validateText("Tina L Nilson");
		my._text().group().validateText("Top");
		my._text().team().validateText("Skip's Team");

		/* Red Flags */
		my._text().redFlagInfo().validateText(information);
		my._text().redFlagWarn().validateText(warning);
		my._text().redFlagCritical().validateText(critical);

		/* Contact Info */
		my._text().email1().validateText(email1);
		my._text().email2().validateText(email2);
		my._text().phone1().validateText(phone1);
		my._text().phone2().validateText(phone2);
		my._text().textMessage1().validateText(text1);
		my._text().textMessage2().validateText(text2);
	}

	@Test
	public void SaveButton_Changes() {
		set_test_case("TC1280");
		login.loginProcess("tnilson", "password");
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
		my._text().userName().validateText("tnilson");
		my._text().locale().validateText(Locale.ENGLISH.getText());
		my._text().measurement().validateText("English");
		my._text().fuelEfficiency().validateText("Miles Per Gallon (UK)");

		/* Account Info */
		my._text().name().validateText("Tina L Nilson");
		my._text().group().validateText("Top");
		my._text().team().validateText("Skip's Team");

		/* Red Flags */
		my._text().redFlagInfo().validateText(RedFlagPrefs.TEXT1.getText());
		my._text().redFlagWarn().validateText(RedFlagPrefs.EMAIL1.getText());
		my._text().redFlagCritical().validateText(RedFlagPrefs.PHONE1.getText());

		/* Contact Info */
		my._text().email1().validateText("tina1965@test.com");
		my._text().email2().validateText("tlc1965@test.com");
		my._text().phone1().validateText("801-777-7777");
		my._text().phone2().validateText("801-999-9999");
		my._text().textMessage1().validateText("8017779999@tmomail.net");
		my._text().textMessage2().validateText("8019997777@tmomail.net");
	}

	@Test
	public void EmailFormatError() {
		set_test_case("TC1271");
		login.loginProcess("tnilson", "password");
		my._link().myAccount().click();

		my._button().edit().click();
		my._textField().email1().type("tina1965test.com");
		my._button().save().click();

		// Clear?//
		my._textField().email1().type("tina1965@test.com");
		my._button().save().click();
		my._text().email1().validateText("tina1965@test.com");

		my._button().edit().click();
		my._textField().email2().type("tlc1965@test");
		my._button().save().click();
		// Validate Error//
		// Clear?//
		my._textField().email2().type("tlc1965@test.com");
		my._button().save().click();
		my._text().email2().validateText("tlc1965@test.com");
	}

	@Test
	public void MeasurementValidation() {
		set_test_case("TC1275");
		login.loginProcess("tnilson", "password");
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

	@Test
	public void FuelRatioValidation() {
		set_test_case("TC1273");
		login.loginProcess("tnilson", "password");
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

	@Test
	public void ClearFieldsValidation() {
		set_test_case("TC1276");
		login.loginProcess("tnilson", "password");
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

	@Test
	public void PhoneMaxCharError() {
		set_test_case("TC1277");
		login.loginProcess("tnilson", "password");
		my._link().myAccount().click();
		// my.link_myAccount().click();
		my._button().edit().click();
		my._textField().phone1().type("801-777-7777-44444421");
		my._button().save().click();
		// Validate Error//
		// Clear fields?//
		my._textField().phone1().type("801-777-7777");
		my._button().save().click();
		my._text().phone1().validateText("801-777-7777");

		my._button().edit().click();
		my._textField().phone2().type("8");
		my._button().save().click();
		// Validate Error//
		// Clear fields?//
		my._textField().phone2().type("801-999-9999");
		my._button().save().click();
		my._text().phone2().validateText("801-999-9999");
	}

	@Test
	public void PhoneMissingCharError() {
		set_test_case("TC1278");
		login.loginProcess("tnilson", "password");
		my._link().myAccount().click();
		// my.link_myAccount().click();

		my._button().edit().click();
		my._textField().phone1().type("801-777-777");
		my._button().save().click();
		// Validate Error//
		// Clear fields?//
		my._textField().phone1().type("801-777-7777");
		my._button().save().click();
		my._text().phone1().validateText("801-777-7777");

		my._button().edit().click();
		my._textField().phone2().type("801-999-999");
		my._button().save().click();
		// Validate Error//
		// Clear fields?//
		my._textField().phone2().type("801-999-9999");
		my._button().save().click();
		my._text().phone2().validateText("801-999-9999");
	}

	@Test
	public void PhoneSpecialCharError() {
		set_test_case("TC1279");
		login.loginProcess("tnilson", "password");
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
		my._text().phone2().validateText("801-999-9999");
	}

	@Test
	public void TextMsgFormatError() {
		set_test_case("TC1282");
		login.loginProcess("tnilson", "password");
		my._link().myAccount().click();

		my._button().edit().click();
		my._textField().textMessage1().type("8017779999tmomail.net");
		my._button().save().click();
		// Validate Error //
		// Clear fields?//
		my._textField().textMessage1().type("8017779999@tmomail.net");
		my._button().save().click();
		my._text().textMessage1().validateText("8017779999@tmomail.net");

		my._button().edit().click();
		my._textField().textMessage2().type("8019997777@tmomail");
		my._button().save().click();
		// Validate Error //
		// Clear fields?//
		my._textField().textMessage2().type("8019997777@tmomail.net");
		my._button().save().click();
		my._text().textMessage2().validateText("8019997777@tmomail.net");
	}

}
