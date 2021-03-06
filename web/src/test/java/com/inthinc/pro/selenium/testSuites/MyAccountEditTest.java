package com.inthinc.pro.selenium.testSuites;

import org.jbehave.core.annotations.UsingSteps;
import org.junit.Test;

import com.inthinc.pro.automation.annotations.AutomationAnnotations.PageObjects;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.StoryPath;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformance;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformanceSeatBelt;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformanceSpeed;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformanceStyle;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformanceTrips;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveDashboard;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveOverallExpansion;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageMyAccount;
import com.inthinc.pro.selenium.pageObjects.PageMyAccountEdit;
import com.inthinc.pro.selenium.pageObjects.PageMyMessages;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsRedFlags;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsSafety;
import com.inthinc.pro.selenium.pageObjects.PageReportsDrivers;
import com.inthinc.pro.selenium.pageObjects.PageReportsVehicles;
import com.inthinc.pro.selenium.pageObjects.PageTeamDriverStatistics;
import com.inthinc.pro.selenium.pageObjects.PageTeamSpeed;
import com.inthinc.pro.selenium.pageObjects.PageTeamVehicleStatistics;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformance;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformanceSeatBelt;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformanceSpeed;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformanceStyle;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformanceTrips;
import com.inthinc.pro.selenium.steps.LoginSteps;

@UsingSteps(instances={LoginSteps.class})
@PageObjects(list={PageLogin.class, PageExecutiveDashboard.class, PageMyAccount.class, PageMyAccountEdit.class, PageMyMessages.class, PageExecutiveOverallExpansion.class, 
        PageReportsDrivers.class, PageReportsVehicles.class, PageNotificationsRedFlags.class, PageNotificationsSafety.class, 
        PageDriverPerformance.class, PageDriverPerformanceSeatBelt.class, PageDriverPerformanceSpeed.class, PageDriverPerformanceStyle.class, PageDriverPerformanceTrips.class,
        PageVehiclePerformance.class, PageVehiclePerformanceSeatBelt.class, PageVehiclePerformanceSpeed.class, PageVehiclePerformanceStyle.class, PageVehiclePerformanceTrips.class,
        PageTeamDriverStatistics.class, PageTeamVehicleStatistics.class, PageTeamSpeed.class})
@StoryPath(path="MyAccountEdit.story")
public class MyAccountEditTest extends WebStories {
    
    @Test
    public void test(){}

}

//@Ignore
//public class EditMyAccountTest extends WebRallyTest {
////TODO for TC1275 Need to add story steps for Admin>User Account, Vehicle>Speed & Sensitivity, Red Flags>Add Red Flag>Speeding>Limit table, SBS>create a new SBS request>Speed Limit current column, once Admin has been rebuilt
////TODO for TC1273 Need to add story steps for Admin>User once Admin has been rebuilt
////TODO for TC1282 Might need to add more test situations
//    
//    
//    
//    
//    private PageMyAccount myAccountPage;
//    private RandomValues random;
//    private AutomationUser login;
//    
//
//    @Before
//    public void setupPage() {
//        random = new RandomValues();
//        myAccountPage = new PageMyAccount();
//        login = AutomationUsers.getUsers().getOneBy(LoginCapability.IsDriver, LoginCapability.HasVehicle, LoginCapability.RoleAdmin);
//    }
//
//    @Test
//    public void MeasurementValidation() {
//        set_test_case("TC1275");
//        // 0. login
//        myAccountPage.loginProcess(login);
//
//        // 1. From the Edit My Account page,
//        myAccountPage._link().myAccount().click();
//        String originalMeasurement = myAccountPage._text().measurement().getText();
//        myAccountPage._button().edit().click();
//
//        Measurement newMeasure = null;
//        Fuel_Ratio newFuel = null;
//        if (compare(Measurement.ENGLISH, originalMeasurement)) {
//            newMeasure = Measurement.METRIC;
//            newFuel = Fuel_Ratio.METRIC_KILO_PER_LITER;
//        } else if (compare(Measurement.METRIC, originalMeasurement)) {
//            newMeasure = Measurement.ENGLISH;
//            newFuel = Fuel_Ratio.ENGLISH_MILES_UK;
//        } else {
//            addError("Measurement", "Original Measurement has unexpected value of: " + originalMeasurement, ErrorLevel.FATAL);
//        }
//        myAccountPage._select().measurement().select(newMeasure);
//        myAccountPage._select().fuelEfficiency().select(newFuel);
//
//        // 2. Click Save.
//        myAccountPage._button().save().click();
//        myAccountPage._text().measurement().validate(newMeasure.getText());
//
//        // TODO: Need to add vehicle/device and assign to driver in order to validate fuel ratio measurements.
//        // 3. Navigate to other pages throughout the UI.
//        // myAccountPage._link().liveFleet().click();
//        // PageLiveFleet liveFleet = new PageLiveFleet();
//        // liveFleet._link().entryVehicleByPosition().row(1).click();
//        // PageVehiclePerformance vPerform = new PageVehiclePerformance();
//
//        // 1. VERIFY - The display of distance (miles or kilometers) throughout
//        // the UI appears in the selected measurement setting.
//        // goes to the vehicle performance page and checks the crashes per
//        // million miles(/kilo) title
//        // vPerform._text().crashesPerMillionMilesTitle()
//        // .validateContains(distanceDisplay);
//
//        // TODO: add other pages to verify (original Rally instructions states
//        // "pageS")
//    }
//
//    @Test
//    public void FuelRatioValidation() {
//        set_test_case("TC1273");
//        // 0. login
//        myAccountPage.loginProcess(login);
//
//        // 1. From the Edit My Account page,
//        myAccountPage._link().myAccount().click();
//        myAccountPage._text().measurement().getText();
//        String originalFuelRatio = myAccountPage._text().fuelEfficiency().getText();
//        myAccountPage._button().edit().click();
//
//        Measurement newMeasure = null;
//        Fuel_Ratio newFuel = null;
//        // find old value, setup new value. notice that we rotate through ALL 4
//        // fuel ratio options if the test is run 4 times
//        if (compare(Fuel_Ratio.ENGLISH_MILES_UK, originalFuelRatio)) {
//            newMeasure = Measurement.METRIC;
//            newFuel = Fuel_Ratio.METRIC_KILO_PER_LITER;
//        } else if (compare(Fuel_Ratio.METRIC_KILO_PER_LITER, originalFuelRatio)) {
//            newMeasure = Measurement.ENGLISH;
//            newFuel = Fuel_Ratio.ENGLISH_MILES_US;
//        } else if (compare(Fuel_Ratio.ENGLISH_MILES_US, originalFuelRatio)) {
//            newMeasure = Measurement.METRIC;
//            newFuel = Fuel_Ratio.METRIC_LITER_PER_KILO;
//        } else if (compare(Fuel_Ratio.METRIC_LITER_PER_KILO, originalFuelRatio)) {
//            newMeasure = Measurement.ENGLISH;
//            newFuel = Fuel_Ratio.ENGLISH_MILES_UK;
//        } else {
//            addError("Fuel Ratio", "Original Fuel Ratio has unexpected value of: " + originalFuelRatio, ErrorLevel.FATAL);
//        }
//        myAccountPage._select().measurement().select(newMeasure);
//        myAccountPage._select().fuelEfficiency().select(newFuel);
//
//        // 2. Click Save.
//        myAccountPage._button().save().click();
//        myAccountPage._text().measurement().validate(newMeasure);
//        myAccountPage._text().fuelEfficiency().validate(newFuel);
//
//        // TODO: Need to add vehicle/device so it can be assigned to driver in order to validate fuel ratio on other pages
//        // 3. Navigate to other pages throughout the UI.
//        // myAccountPage._link().liveFleet().click();
//        // PageLiveFleet liveFleet = new PageLiveFleet();
//        // liveFleet._link().entryVehicleByPosition().row(1).click(); // brittle...
//        // new PageVehiclePerformance();
//
//        // 1. VERIFY - The display of distance (miles or kilometeres) throughout
//        // the UI appears in the selected measurement setting.
//        // goes to the vehicle performance page and checks the crashes per
//        // million miles(/kilo) title
//        // TODO: add AT LEAST ONE validation of distance per volume values
//        // TODO: add other pages to verify (original Rally instructions states
//        // "pageS")
//    }
//
//    @Test
//    public void ClearFieldsValidation() {
//        set_test_case("TC1276");
//        // Rally: Input
//        /*
//         * 1. If necessary, click My Account then click Edit. 2. Clear all possible fields. 3. Click Save.
//         */
//        myAccountPage.loginProcess(login);
//        myAccountPage._link().myAccount().click();
//
//        myAccountPage._button().edit().click();
//
//        /* 2. Clear all possible fields */
//        myAccountPage._textField().email1().type("");
//        myAccountPage._textField().email2().type("");
//        myAccountPage._textField().phone1().type("");
//        myAccountPage._textField().phone2().type("");
//        myAccountPage._textField().textMessage1().type("");
//        myAccountPage._textField().textMessage2().type("");
//
//        /* 3. Click Save. */
//        myAccountPage._button().save().click();
//
//        // Rally: Expected
//        // 1. A 'Required' validation error alert appears above the following
//        // fields:
//        // * E-mail 1
//        myAccountPage._text().errorEmail1().validate("Required");
//    }
//
//    @Test
//    public void PhoneMaxCharError() {
//        set_test_case("TC1277");
//        String phoneNumEighteen = random.getIntString(18);
//        String phoneNumTwentyFive = random.getIntString(25);
//        // the following are left to show additional ways constants MIGHT be
//        // used in tests.
//        // String phoneNumRandomTwelve = random.getPhoneNumber()
//        // +"-"+random.getNumberString(2);
//        // String phoneNumReal = "801-777-7777";
//        // String phoneNumShort = "8";
//
//        // Rally: Input
//        // 1. From the Edit My Account page, enter more than 10 characters in
//        // the Phone 1 and Phone 2 text fields.
//        // 2. Click Save.
//        myAccountPage.loginProcess(login);
//        myAccountPage._link().myAccount().click();
//        myAccountPage._button().edit().click();
//
//        myAccountPage._textField().phone1().type(phoneNumEighteen);
//        myAccountPage._textField().phone2().type(phoneNumTwentyFive);
//        myAccountPage._button().save().click();
//
//        myAccountPage._text().errorPhone1().validate("Must consist of up to 15 numeric characters");
//        myAccountPage._text().errorPhone2().validate("Must consist of up to 15 numeric characters");
//    }
//
//    @Test
//    public void PhoneMissingCharError() {
//        set_test_case("TC1278");
//        String phoneNumShortOne = random.getIntString(1);
//        String phoneNumShortTwo = random.getIntString(2);
//
//        myAccountPage.loginProcess(login);
//        myAccountPage._link().myAccount().click();
//        myAccountPage._button().edit().click();
//        myAccountPage._textField().phone1().type(phoneNumShortOne);
//        myAccountPage._textField().phone2().type(phoneNumShortTwo);
//        myAccountPage._button().save().click();
//
//        myAccountPage._text().errorPhone1().validate("Must consist of up to 15 numeric characters");
//        myAccountPage._text().errorPhone2().validate("Must consist of up to 15 numeric characters");
//
//    }
//
//    @Test
//    public void PhoneSpecialCharError() {
//        set_test_case("TC1279");
//        // Rally: input
//        // 1. From the Edit My Account page, enter special characters (e.g. &,
//        // ^, $) in the Phone 1 and Phone 2 text fields.
//        // 2. Click Save.
//        myAccountPage.loginProcess(login);
//        myAccountPage._link().myAccount().click();
//        myAccountPage._button().edit().click();
//        myAccountPage._textField().phone1().type(random.getSpecialString(10));
//        myAccountPage._textField().phone2().type(random.getSpecialString(10));
//        myAccountPage._button().save().click();
//
//        myAccountPage._text().errorPhone1().validate("Must consist of up to 15 numeric characters");
//        myAccountPage._text().errorPhone2().validate("Must consist of up to 15 numeric characters");
//    }
//
//    @Test
//    @Ignore //TODO: until defect 6654 is fixed
//    public void TextMsgFormatError() {
//        set_test_case("TC1282");
//        ArrayList<String> badTextMessageAddresses = new ArrayList<String>();
//        badTextMessageAddresses.add("8015551234@domain@domain.com"); // NOTE: this is defect 6654
//        badTextMessageAddresses.add(random.getIntString(5) + "@domain.com"); // phone number is to short
//        badTextMessageAddresses.add("8015551234 @domain.com"); // contains a space
//        badTextMessageAddresses.add("801555\"1234\"@domain.com"); // has quotes inside
//        badTextMessageAddresses.add(random.getSpecialString(10) + "@domain.com"); // Special Characters for number
//        badTextMessageAddresses.add("801362judi@domain.com"); // contains letters
//
//        // Rally: input
//        // 1. From the Edit My Account page, enter a text message address that
//        // DOES NOT conform to the address attributes listed in Note 1 below in
//        // the Text Message 1 and Text Message 2 text fields.
//        // 2. Click Save.
//        myAccountPage.loginProcess(login);
//        myAccountPage._link().myAccount().click();
//        myAccountPage._button().edit().click();
//
//        for (String address : badTextMessageAddresses) {
//            myAccountPage._textField().textMessage1().type(address);
//            myAccountPage._textField().textMessage2().type(address); // NOTE: there might be an additional TC to test if
//            // textMessage1 is in an incorrect format by textMessage2
//            // is not (and vice versa)
//            myAccountPage._button().save().click(); // NOTE: IF one of these "saves" is successful, it will cause subsequent
//            // iterations of this loop to FAIL (because we will NOT be on the Edit page
//            // any longer)
//
//            // Rally: Expected Result
//            // An error message appears in red saying 'Incorrect format
//            // (8015551212@tmomail.com)'
//            myAccountPage._text().errorText1().validate("Incorrect format (8015551212@tmomail.com)");
//            myAccountPage._text().errorText2().validate("Incorrect format (8015551212@tmomail.com)");
//        }
//
//    }
//
//    @Test
//    public void SaveButton_Changes() {
//        set_test_case("TC1280");
//        // Rally: input
//        // 1. If necessary, click My Account then click Edit.
//        // 2. Add or change the data in all fields.
//        // 3. Click Save.
//        
//        String testData_email1 ="someUnusedEmail@test.com";
//        String testData_email2 = "anotherUnusedEmail@test.com";
//        String testData_phone1 = "801-777-7777";
//        String testData_phone2 = "801-999-9999";
//        String testData_txt1 = "8017779999@tmomail.net";
//        String testData_txt2 = "8019997777@tmomail.net";
//        
//
//        myAccountPage.loginProcess(login);
//
//        myAccountPage._link().myAccount().click();
//
//        // hang onto original values so the account can be returned to it's original state
//        String origLocal = myAccountPage._text().locale().getText();
//        String origMeasure = myAccountPage._text().measurement().getText();
//        String origFuelEfficiency = myAccountPage._text().fuelEfficiency().getText();
//        String origEmail1 = myAccountPage._text().email1().getText();
//        String origEmail2 = myAccountPage._text().email2().getText();
//        String origPhone1 = myAccountPage._text().phone1().getText();
//        String origPhone2 = myAccountPage._text().phone2().getText();
//        String origText1 = myAccountPage._text().textMessage1().getText();
//        String origText2 = myAccountPage._text().textMessage2().getText();
//        String origInfo = myAccountPage._text().redFlagInfo().getText();
//        String origWarn = myAccountPage._text().redFlagWarn().getText();
//        String origCrit = myAccountPage._text().redFlagCritical().getText();
//        // these 3 can't be changed but hanging onto them allows us to test with different (any?) login
//        String origName = myAccountPage._text().name().getText();
//        String origGroup = myAccountPage._text().group().getText();
//        String origTeam = myAccountPage._text().team().getText();
//
//        myAccountPage._button().edit().click();
//
//        /* Login Info */
//        myAccountPage._select().locale().select(Locale.ENGLISH);
//        myAccountPage._select().measurement().select(Measurement.ENGLISH);
//        myAccountPage._select().fuelEfficiency().select(Fuel_Ratio.ENGLISH_MILES_UK);
//
//        /* Contact Info */
//        myAccountPage._textField().email1().type(testData_email1);
//        myAccountPage._textField().email2().type(testData_email2);
//        myAccountPage._textField().phone1().type(testData_phone1);
//        myAccountPage._textField().phone2().type(testData_phone2);
//        myAccountPage._textField().textMessage1().type(testData_txt1);
//        myAccountPage._textField().textMessage2().type(testData_txt2);
//
//        /* Red Flags */
//        myAccountPage._select().information().select(RedFlagPrefs.TEXT1);
//        myAccountPage._select().warning().select(RedFlagPrefs.EMAIL1);
//        myAccountPage._select().critical().select(RedFlagPrefs.PHONE1);
//
//        /* Save Changes */
//        myAccountPage._button().save().click();
//        pause(10, "wait for page to save");
//
//        /* Verify Changes Display */
//        /* Login Info */
//        myAccountPage._text().userName().validate(login.getUsername());
//        myAccountPage._text().locale().validate(Locale.ENGLISH.getText());
//        myAccountPage._text().measurement().validate("English");
//        myAccountPage._text().fuelEfficiency().validate("Miles Per Gallon (UK)");
//
//        /* Account Info */
//        myAccountPage._text().name().validate(origName);
//        myAccountPage._text().group().validate(origGroup);
//        myAccountPage._text().team().validate(origTeam);
//
//        /* Red Flags */
//        myAccountPage._text().redFlagInfo().validate(RedFlagPrefs.TEXT1, ":", "");
//        myAccountPage._text().redFlagWarn().validate(RedFlagPrefs.EMAIL1, ":", "");
//        myAccountPage._text().redFlagCritical().validate(RedFlagPrefs.PHONE1, ":", "");
//
//        /* Contact Info */
//        myAccountPage._text().email1().validate(testData_email1);
//        myAccountPage._text().email2().validate(testData_email2);
//        myAccountPage._text().phone1().validate(testData_phone1);
//        myAccountPage._text().phone2().validate(testData_phone2);
//        myAccountPage._text().textMessage1().validate(testData_txt1);
//        myAccountPage._text().textMessage2().validate(testData_txt2);
//
//        // return account to original condition
//        myAccountPage._button().edit().click();
//        myAccountPage._select().locale().select(origLocal);
//        myAccountPage._select().measurement().select(origMeasure);
//        myAccountPage._select().fuelEfficiency().select(origFuelEfficiency);
//        myAccountPage._textField().email1().type(origEmail1);
//        myAccountPage._textField().email2().type(origEmail2);
//        myAccountPage._textField().phone1().type(origPhone1);
//        myAccountPage._textField().phone2().type(origPhone2);
//        myAccountPage._textField().textMessage1().type(origText1);
//        myAccountPage._textField().textMessage2().type(origText2);
//        myAccountPage._select().information().selectPartMatch(origInfo);
//        myAccountPage._select().warning().selectPartMatch(origWarn);
//        myAccountPage._select().critical().selectPartMatch(origCrit);
//        myAccountPage._button().save().click();
//
//    }
//
//    @Test
//    public void EmailFormatError() {
//        set_test_case("TC1272");
//        myAccountPage.loginProcess(login);
//        myAccountPage._link().myAccount().click();
//
//        myAccountPage._button().edit().click();
//        myAccountPage._textField().email1().clear();
//        String email_invalid_noAtSymbol = "tina1960test.com";
//        myAccountPage._textField().email1().type(email_invalid_noAtSymbol);
//        myAccountPage._button().save().click();
//
//        myAccountPage._text().errorEmail1().validate("Incorrect format (jdoe@tiwipro.com)");
//
//        myAccountPage._textField().email1().clear();
//        String email_valid = "tina1960@test.com";
//        myAccountPage._textField().email1().type(email_valid);
//        myAccountPage._button().save().click();
//        myAccountPage._text().email1().validate(email_valid);
//
//        myAccountPage._button().edit().click();
//        String email_invalid_NoTLD = "tlc1960test";
//        myAccountPage._textField().email2().type(email_invalid_NoTLD);
//        myAccountPage._button().save().click();
//
//        myAccountPage._text().errorEmail2().validate("Incorrect format (jdoe@tiwipro.com)");
//
//        myAccountPage._textField().email2().clear();
//        String email_valid_2 ="tlc1960@test.com";
//        myAccountPage._textField().email2().type(email_valid_2);
//        myAccountPage._button().save().click();
//        myAccountPage._text().email2().validate(email_valid_2);
//    }
//
//    @Test
//    public void CancelButton_Changes() {
//        set_test_case("TC1271");
//        set_defect("DE6454");
//        myAccountPage.loginProcess(login);
//        myAccountPage._link().myAccount().click();
//        /* Get original Values */
//
//        String originalEmail1 = myAccountPage._text().email1().getText();
//        String originalEmail2 = myAccountPage._text().email2().getText();
//
//        String originalPhone1 = myAccountPage._text().phone1().getText();
//        String originalPhone2 = myAccountPage._text().phone2().getText();
//
//        String originalText1 = myAccountPage._text().textMessage1().getText();
//        String originalText2 = myAccountPage._text().textMessage2().getText();
//
//        String originalCritical = myAccountPage._text().redFlagCritical().getText();
//        String originalWarning = myAccountPage._text().redFlagWarn().getText();
//        String originalInformation = myAccountPage._text().redFlagInfo().getText();
//
//        String originalLocale = myAccountPage._text().locale().getText();
//        String originalMeasurement = myAccountPage._text().measurement().getText();
//        String originalFuelRatio = myAccountPage._text().fuelEfficiency().getText();
//        
//        // these 3 can't be changed but hanging onto them allows us to test with different (any?) login
//        String origName = myAccountPage._text().name().getText();
//        String origGroup = myAccountPage._text().group().getText();
//        String origTeam = myAccountPage._text().team().getText();
//
//        /* Edit button */
//        myAccountPage._button().edit().click();
//
//        /* Login Info */
//        myAccountPage._select().locale().select(Locale.ENGLISH);
//        Measurement newMeasurement = Measurement.ENGLISH;
//        Fuel_Ratio newFuelRatio;
//        if(Measurement.ENGLISH.toString().equals(originalMeasurement)){
//            newMeasurement = Measurement.METRIC;
//            newFuelRatio = Fuel_Ratio.METRIC_LITER_PER_KILO;
//        } else {
//            newFuelRatio = Fuel_Ratio.ENGLISH_MILES_US;
//        }
//        myAccountPage._select().measurement().select(newMeasurement);
//        myAccountPage._select().fuelEfficiency().select(newFuelRatio);
//        
//        /* Contact Info */
//        myAccountPage._textField().email1().type(random.getEmail());
//        myAccountPage._textField().email2().type(random.getEmail());
//        myAccountPage._textField().phone1().type(random.getPhoneNumber());
//        myAccountPage._textField().phone2().type(random.getPhoneNumber());
//        myAccountPage._textField().textMessage1().type(random.getTextMessageNumber());
//        myAccountPage._textField().textMessage2().type(random.getTextMessageNumber());
//
//        /* Red Flags */
//        myAccountPage._select().information().select(random.getEnum(RedFlagPrefs.TEXT1, originalInformation));
//        myAccountPage._select().warning().select(random.getEnum(RedFlagPrefs.TEXT1, originalWarning));
//        myAccountPage._select().critical().select(random.getEnum(RedFlagPrefs.TEXT1, originalCritical));
//
//        /* Cancel Changes */
//        myAccountPage._button().cancel().click();
//
//        /* Verify Changes did not take effect */
//        /* Login Info */
//        myAccountPage._text().userName().validate(login.getUsername());
//        myAccountPage._text().locale().validate(originalLocale);
//        myAccountPage._text().measurement().validate(originalMeasurement);
//        myAccountPage._text().fuelEfficiency().validate(originalFuelRatio);
//
//        /* Account Info */
//        myAccountPage._text().name().validate(origName);//TODO: tina: review: I updated this so that it doesn't depend on a specific account
//        myAccountPage._text().group().validate(origGroup);
//        myAccountPage._text().team().validate(origTeam);
//
//        /* Red Flags */
//        myAccountPage._text().redFlagInfo().validate(originalInformation);
//        myAccountPage._text().redFlagWarn().validate(originalWarning);
//        myAccountPage._text().redFlagCritical().validate(originalCritical);
//
//        /* Contact Info */
//        myAccountPage._text().email1().validate(originalEmail1);
//        myAccountPage._text().email2().validate(originalEmail2);
//        myAccountPage._text().phone1().validate(originalPhone1);
//        myAccountPage._text().phone2().validate(originalPhone2);
//        myAccountPage._text().textMessage1().validate(originalText1);
//        myAccountPage._text().textMessage2().validate(originalText2);
//    }
//}
