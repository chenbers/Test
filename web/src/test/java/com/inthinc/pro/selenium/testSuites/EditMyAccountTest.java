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
		login.page_login_process("tnilson", "password");
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
		
		
		/* Edit button */
		my._button().edit().click();

		/* Login Info */
		my._select().locale().select(Locale.ENGLISH.getText());
		my._select().measurement().select("Metric");
		my._select().fuelEfficiency().select("Liters Per 100 Kilometers");

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
		String username = my._text().userName().getText();
		my.assertEquals("tnilson", username);
		String locale = my._text().locale().getText();
		my.assertEquals(Locale.ENGLISH, locale);
		String measurement = my._text().measurement().getText();
		my.assertEquals(Measurement.METRIC, measurement);
		String fuel = my._text().fuelEfficiency().getText();
		my.assertEquals(Fuel_Ratio.METRIC_LITER_PER_KILO, fuel);

		/* Account Info */
		String name = my._text().name().getText();
		my.assertEquals("Tina L Nilson", name);
		String group = my._text().group().getText();
		my.assertEquals("Top", group);
		String team = my._text().team().getText();
		my.assertEquals("Skip's Team", team);

		/* Red Flags */
		String info = my._text().redFlagInfo().getText();
		my.assertEquals(information, info);
		String warn = my._text().redFlagWarn().getText();
		my.assertEquals(warning, warn);
		String crit = my._text().redFlagCritical().getText();
		my.assertEquals( critical, crit);

		/* Contact Info */
		String email1n = my._text().email1().getText();
		my.assertEquals(email1, email1n);
		String email2n = my._text().email2().getText();
		my.assertEquals(email2, email2n);
		String phone1n = my._text().phone1().getText();
		my.assertEquals(phone1, phone1n);
		String phone2n = my._text().phone2().getText();
		my.assertEquals(phone2, phone2n);
		String textmsg1 = my._text().textMessage1().getText();
		my.assertEquals(text1, textmsg1);
		String textmsg2 = my._text().textMessage2().getText();
		my.assertEquals(text2, textmsg2);
	}

	@Test
	public void SaveButton_Changes() {
		set_test_case("TC1280");
		login.page_login_process("tnilson", "password");
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
		my.getSelenium().pause(10);
		/* Verify Changes Display */
		/* Login Info */
		String username = my._text().userName().getText();
		my.assertEquals("tnilson", username);
		String locale = my._text().locale().getText();
		my.assertEquals(Locale.ENGLISH, locale);
		String measurement = my._text().measurement().getText();
		my.assertEquals("Metric", measurement);
		String fuel = my._text().fuelEfficiency().getText();
		my.assertEquals("Liters Per 100 Kilometers", fuel);

		/* Account Info */
		String name = my._text().name().getText();
		my.assertEquals("Tina L Nilson", name);
		String group = my._text().group().getText();
		my.assertEquals("Top", group);
		String team = my._text().team().getText();
		my.assertEquals("Skip's Team", team);

		/* Red Flags */
		String info = my._text().redFlagInfo().getText();
		my.assertEquals("Text Message 1", info);
		String warn = my._text().redFlagWarn().getText();
		my.assertEquals("E-mail 1", warn);
		String critical = my._text().redFlagCritical().getText();
		my.assertEquals("Phone 1", critical);

		/* Contact Info */
		String email1 = my._text().email1().getText();
		my.assertEquals("tina1965@test.com", email1);
		String email2 = my._text().email2().getText();
		my.assertEquals("tlc1965@test.com", email2);
		String phone1 = my._text().phone1().getText();
		my.assertEquals("801-777-7777", phone1);
		String phone2 = my._text().phone2().getText();
		my.assertEquals("801-999-9999", phone2);
		String textmsg1 = my._text().textMessage1().getText();
		my.assertEquals("8017779999@tmomail.net", textmsg1);
		String textmsg2 = my._text().textMessage2().getText();
		my.assertEquals("8019997777@tmomail.net", textmsg2);
	}

	@Test
	public void EmailFormatError() {
		set_test_case("TC1271");
		login.page_login_process("tnilson", "password");
		my._link().myAccount().click();

		my._button().edit().click();
		my._textField().email1().type("tina1965test.com");
		my._button().save().click();

		// Clear?//
		my._textField().email1().type("tina1965@test.com");
		my._button().save().click();
		String email1 = my._text().email1().getText();
		my.assertEquals("tina1965@test.com", email1);

		my._button().edit().click();
		my._textField().email2().type("tlc1965@test");
		my._button().save().click();
		// Validate Error//
		// Clear?//
		my._textField().email2().type("tlc1965@test.com");
		my._button().save().click();
		String email2 = my._text().email2().getText();
		my.assertEquals("tlc1965@test.com", email2);

	}

	@Test
	public void MeasurementValidation() {
		set_test_case("TC1275");
		login.page_login_process("tnilson", "password");
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
		login.page_login_process("tnilson", "password");
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
		login.page_login_process("tnilson", "password");
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
		login.page_login_process("tnilson", "password");
		my._link().myAccount().click();
		// my.link_myAccount().click();
		my._button().edit().click();
		my._textField().phone1().type("801-777-7777-44444421");
		my._button().save().click();
		// Validate Error//
		// Clear fields?//
		my._textField().phone1().type("801-777-7777");
		my._button().save().click();
		String phone1 = my._text().phone1().getText();
		my.assertEquals("801-777-7777", phone1);

		my._button().edit().click();
		my._textField().phone2().type("8");
		my._button().save().click();
		// Validate Error//
		// Clear fields?//
		my._textField().phone2().type("801-999-9999");
		my._button().save().click();
		String phone2 = my._text().phone2().getText();
		my.assertEquals("801-999-9999", phone2);
	}

	@Test
	public void PhoneMissingCharError() {
		set_test_case("TC1278");
		login.page_login_process("tnilson", "password");
		my._link().myAccount().click();
		// my.link_myAccount().click();

		my._button().edit().click();
		my._textField().phone1().type("801-777-777");
		my._button().save().click();
		// Validate Error//
		// Clear fields?//
		my._textField().phone1().type("801-777-7777");
		my._button().save().click();
		String phone1 = my._text().phone1().getText();
		my.assertEquals("801-777-7777", phone1);

		my._button().edit().click();
		my._textField().phone2().type("801-999-999");
		my._button().save().click();
		// Validate Error//
		// Clear fields?//
		my._textField().phone2().type("801-999-9999");
		my._button().save().click();
		String phone2 = my._text().phone2().getText();
		my.assertEquals("801-999-9999", phone2);

	}

	@Test
	public void PhoneSpecialCharError() {
		set_test_case("TC1279");
		login.page_login_process("tnilson", "password");
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
		String phone2 = my._text().phone2().getText();
		my.assertEquals("801-999-9999", phone2);
	}

	@Test
	public void TextMsgFormatError() {
		set_test_case("TC1282");
		login.page_login_process("tnilson", "password");
		my._link().myAccount().click();

		my._button().edit().click();
		my._textField().textMessage1().type("8017779999tmomail.net");
		my._button().save().click();
		// Validate Error //
		// Clear fields?//
		my._textField().textMessage1().type("8017779999@tmomail.net");
		my._button().save().click();
		String text1 = my._text().textMessage1().getText();
		my.assertEquals("8017779999@tmomail.net", text1);

		my._button().edit().click();
		my._textField().textMessage2().type("8019997777@tmomail");
		my._button().save().click();
		// Validate Error //
		// Clear fields?//
		my._textField().textMessage2().type("8019997777@tmomail.net");
		my._button().save().click();
		String text2 = my._text().textMessage2().getText();
		my.assertEquals("8019997777@tmomail.net", text2);
	}

}
