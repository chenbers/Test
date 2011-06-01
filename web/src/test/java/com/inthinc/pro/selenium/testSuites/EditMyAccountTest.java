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

	private PageMyAccount myAccountPage;
	private RandomValues random;

	@Before
	public void setupPage() {
		random = new RandomValues();
		myAccountPage = new PageMyAccount();
	}

	@Test
	public void CancelButton_Changes() {
		set_test_case("TC1271");
		myAccountPage.loginProcess("tnilson", "password123");
		myAccountPage._link().myAccount().click();
		/* Get original Values */
		String originalEmail1 = myAccountPage._text().email1().getText();
		String originalEmail2 = myAccountPage._text().email2().getText();
		
		String originalPhone1 = myAccountPage._text().phone1().getText();
		String originalPhone2 = myAccountPage._text().phone2().getText();
		
		String originalText1 = myAccountPage._text().textMessage1().getText();
		String originalText2 = myAccountPage._text().textMessage2().getText();
		
		
		String originalCritical = myAccountPage._text().redFlagCritical().getText();
		String originalWarning = myAccountPage._text().redFlagWarn().getText();
		String originalInformation = myAccountPage._text().redFlagInfo().getText();
		
		String originalLocale = myAccountPage._text().locale().getText();
		String originalMeasurement = myAccountPage._text().measurement().getText();
		String originalFuelRatio = myAccountPage._text().fuelEfficiency().getText();
		
		
		/* Edit button */
		myAccountPage._button().edit().click();

		/* Login Info */
		myAccountPage._select().locale().select(Locale.ENGLISH);
		myAccountPage._select().measurement().select(Measurement.METRIC);
		myAccountPage._select().fuelEfficiency().select(Fuel_Ratio.METRIC_LITER_PER_KILO);

		/* Contact Info */
		myAccountPage._textField().email1().type(random.getEmail());
		myAccountPage._textField().email2().type(random.getEmail());
		myAccountPage._textField().phone1().type(random.getPhoneNumber());
		myAccountPage._textField().phone2().type(random.getPhoneNumber());
		myAccountPage._textField().textMessage1().type(random.getTextMessageNumber());
		myAccountPage._textField().textMessage2().type(random.getTextMessageNumber());

		/* Red Flags */
		myAccountPage._select().information().select(random.getEnum(RedFlagPrefs.TEXT1, originalInformation));
		myAccountPage._select().warning().select(random.getEnum(RedFlagPrefs.TEXT1, originalWarning));
		myAccountPage._select().critical().select(random.getEnum(RedFlagPrefs.TEXT1, originalCritical));

		/* Cancel Changes */
		myAccountPage._button().cancel().click();

		/* Verify Changes did not take effect */
		/* Login Info */        
		myAccountPage._text().userName().validate("tnilson");
		myAccountPage._text().locale().validate(originalLocale);
		myAccountPage._text().measurement().validate(originalMeasurement);
		myAccountPage._text().fuelEfficiency().validate(originalFuelRatio);

		/* Account Info */
		myAccountPage._text().name().validate("Tina L Nilson");
		myAccountPage._text().group().validate("Top");
		myAccountPage._text().team().validate("Skip's Team");

		/* Red Flags */
		myAccountPage._text().redFlagInfo().validate(originalInformation);
		myAccountPage._text().redFlagWarn().validate(originalWarning);
		myAccountPage._text().redFlagCritical().validate(originalCritical);

		/* Contact Info */
		myAccountPage._text().email1().validate(originalEmail1);
		myAccountPage._text().email2().validate(originalEmail2);
		myAccountPage._text().phone1().validate(originalPhone1);
		myAccountPage._text().phone2().validate(originalPhone2);
		myAccountPage._text().textMessage1().validate(originalText1);
		myAccountPage._text().textMessage2().validate(originalText2);
	}

	

	@Test
	public void EmailFormatError() {
		set_test_case("TC1271");//TODO: find the actual testcase for this
		myAccountPage.loginProcess("tnilson", "password");
		myAccountPage._link().myAccount().click();

		myAccountPage._button().edit().click();
		myAccountPage._textField().email1().type("tina1965test.com");
		myAccountPage._button().save().click();

		// Clear?//
		myAccountPage._textField().email1().type("tina1965@test.com");
		myAccountPage._button().save().click();
		myAccountPage._text().email1().validate("tina1965@test.com");

		myAccountPage._button().edit().click();
		myAccountPage._textField().email2().type("tlc1965@test");
		myAccountPage._button().save().click();
		// Validate Error//
		// Clear?//
		myAccountPage._textField().email2().type("tlc1965@test.com");
		myAccountPage._button().save().click();
		myAccountPage._text().email2().validate("tlc1965@test.com");
	}

	@Test
	public void MeasurementValidation() {
		set_test_case("TC1275");
		myAccountPage.loginProcess("tnilson", "password");
		myAccountPage._link().myAccount().click();
		

		myAccountPage._button().edit().click();
		myAccountPage._select().measurement().select("Metric");
		myAccountPage._select().fuelEfficiency().select("Kilometers Per Liter");
		myAccountPage._button().save().click();

		// validate pages for Metric Units Goes Here//

		myAccountPage._button().edit().click();
		myAccountPage._select().measurement().select("English");
		myAccountPage._select().fuelEfficiency().select("Miles Per Gallon (US)");
		myAccountPage._button().save().click();

		// validation for English Units Goes Here//

	}

	@Test
	public void FuelRatioValidation() {
		set_test_case("TC1273");
		myAccountPage.loginProcess("tnilson", "password");
		myAccountPage._link().myAccount().click();

		myAccountPage._button().edit().click();
		myAccountPage._select().measurement().select("Metric");
		myAccountPage._select().fuelEfficiency().select("Kilometers Per Liter");
		myAccountPage._button().save().click();
		// Validate KM Per Liter Here//

		myAccountPage._button().edit().click();
		myAccountPage._select().measurement().select("English");
		myAccountPage._select().fuelEfficiency().select("Miles Per Gallon (US)");
		myAccountPage._button().save().click();
		// Validate MPG Here//

	}

	@Test
	public void ClearFieldsValidation() {
		set_test_case("TC1276");
		myAccountPage.loginProcess("tnilson", "password");
		myAccountPage._link().myAccount().click();

		myAccountPage._button().edit().click();

		/* Contact Info */

		myAccountPage._textField().email1().type("");
		myAccountPage._textField().email2().type("");
		myAccountPage._textField().phone1().type("");
		myAccountPage._textField().phone2().type("");
		myAccountPage._textField().textMessage1().type("");
		myAccountPage._textField().textMessage2().type("");

		/* Save Empty Fields */

		myAccountPage._button().save().click();

		// Email Required//
	}

	@Test
	public void PhoneMaxCharError() {
		set_test_case("TC1277");
		myAccountPage.loginProcess("tnilson", "password");
		myAccountPage._link().myAccount().click();
		// my.link_myAccount().click();
		myAccountPage._button().edit().click();
		myAccountPage._textField().phone1().type("801-777-7777-44444421");
		myAccountPage._button().save().click();
		// Validate Error//
		// Clear fields?//
		myAccountPage._textField().phone1().type("801-777-7777");
		myAccountPage._button().save().click();
		myAccountPage._text().phone1().validate("801-777-7777");

		myAccountPage._button().edit().click();
		myAccountPage._textField().phone2().type("8");
		myAccountPage._button().save().click();
		// Validate Error//
		// Clear fields?//
		myAccountPage._textField().phone2().type("801-999-9999");
		myAccountPage._button().save().click();
		myAccountPage._text().phone2().validate("801-999-9999");
	}

	@Test
	public void PhoneMissingCharError() {
		set_test_case("TC1278");
		myAccountPage.loginProcess("tnilson", "password");
		myAccountPage._link().myAccount().click();
		// my.link_myAccount().click();

		myAccountPage._button().edit().click();
		myAccountPage._textField().phone1().type("801-777-777");
		myAccountPage._button().save().click();
		// Validate Error//
		// Clear fields?//
		myAccountPage._textField().phone1().type("801-777-7777");
		myAccountPage._button().save().click();
		myAccountPage._text().phone1().validate("801-777-7777");

		myAccountPage._button().edit().click();
		myAccountPage._textField().phone2().type("801-999-999");
		myAccountPage._button().save().click();
		// Validate Error//
		// Clear fields?//
		myAccountPage._textField().phone2().type("801-999-9999");
		myAccountPage._button().save().click();
		myAccountPage._text().phone2().validate("801-999-9999");
	}

	@Test
	public void PhoneSpecialCharError() {
		set_test_case("TC1279");
		myAccountPage.loginProcess("tnilson", "password");
		myAccountPage._link().myAccount().click();
		// my.link_myAccount().click();
		myAccountPage._button().edit().click();
		myAccountPage._textField().phone1().type("801-@$%-777&");
		myAccountPage._button().save().click();
		// Validate Error//
		// Clear fields?//
		myAccountPage._textField().phone1().type("801-777-7777");
		myAccountPage._button().save().click();
		String phone1 = myAccountPage._text().phone1().getText();
		myAccountPage.assertEquals("801-777-7777", phone1);

		myAccountPage._button().edit().click();
		myAccountPage._textField().phone2().type("801-ABC-$%+");
		myAccountPage._button().save().click();
		// Validate Error//
		// Clear fields?//
		myAccountPage._textField().phone2().type("801-999-9999");
		myAccountPage._button().save().click();
		myAccountPage._text().phone2().validate("801-999-9999");
	}

	@Test
	public void TextMsgFormatError() {
		set_test_case("TC1282");
		myAccountPage.loginProcess("tnilson", "password");
		myAccountPage._link().myAccount().click();

		myAccountPage._button().edit().click();
		myAccountPage._textField().textMessage1().type("8017779999tmomail.net");
		myAccountPage._button().save().click();
		// Validate Error //
		// Clear fields?//
		myAccountPage._textField().textMessage1().type("8017779999@tmomail.net");
		myAccountPage._button().save().click();
		myAccountPage._text().textMessage1().validate("8017779999@tmomail.net");

		myAccountPage._button().edit().click();
		myAccountPage._textField().textMessage2().type("8019997777@tmomail");
		myAccountPage._button().save().click();
		// Validate Error //
		// Clear fields?//
		myAccountPage._textField().textMessage2().type("8019997777@tmomail.net");
		myAccountPage._button().save().click();
		myAccountPage._text().textMessage2().validate("8019997777@tmomail.net");
	}
	@Test
    public void SaveButton_Changes() {
        set_test_case("TC1280");
        myAccountPage.loginProcess("tnilson", "password");
        myAccountPage._link().myAccount().click();

        /* Edit button */
        myAccountPage._button().edit().click();

        /* Login Info */
        myAccountPage._select().locale().select(Locale.ENGLISH);
        myAccountPage._select().measurement().select(Measurement.ENGLISH);
        myAccountPage._select().fuelEfficiency().select(Fuel_Ratio.ENGLISH_MILES_UK);

        /* Contact Info */

        myAccountPage._textField().email1().type("tina1965@test.com");
        myAccountPage._textField().email2().type("tlc1965@test.com");
        myAccountPage._textField().phone1().type("801-777-7777");
        myAccountPage._textField().phone2().type("801-999-9999");
        myAccountPage._textField().textMessage1().type("8017779999@tmomail.net");
        myAccountPage._textField().textMessage2().type("8019997777@tmomail.net");

        /* Red Flags */
        myAccountPage._select().information().select(RedFlagPrefs.TEXT1);
        myAccountPage._select().warning().select(RedFlagPrefs.EMAIL1);
        myAccountPage._select().critical().select(RedFlagPrefs.PHONE1);

        /* Save Changes */
        myAccountPage._button().save().click();
        myAccountPage.getSelenium().pause(10, "wait for page to save");
        /* Verify Changes Display */
        /* Login Info */
        myAccountPage._text().userName().validate("tnilson");
        myAccountPage._text().locale().validate(Locale.ENGLISH.getText());
        myAccountPage._text().measurement().validate("English");
        myAccountPage._text().fuelEfficiency().validate("Miles Per Gallon (UK)");

        /* Account Info */
        myAccountPage._text().name().validate("Tina L Nilson");
        myAccountPage._text().group().validate("Top");
        myAccountPage._text().team().validate("Skip's Team");

        /* Red Flags */
        myAccountPage._text().redFlagInfo().validate(RedFlagPrefs.TEXT1, ":", "");
        myAccountPage._text().redFlagWarn().validate(RedFlagPrefs.EMAIL1, ":", "");
        myAccountPage._text().redFlagCritical().validate(RedFlagPrefs.PHONE1, ":", "");

        /* Contact Info */
        myAccountPage._text().email1().validate("tina1965@test.com");
        myAccountPage._text().email2().validate("tlc1965@test.com");
        myAccountPage._text().phone1().validate("801-777-7777");
        myAccountPage._text().phone2().validate("801-999-9999");
        myAccountPage._text().textMessage1().validate("8017779999@tmomail.net");
        myAccountPage._text().textMessage2().validate("8019997777@tmomail.net");
    }
}
