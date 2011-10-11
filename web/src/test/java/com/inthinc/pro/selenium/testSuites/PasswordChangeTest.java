package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.automation.enums.AutomationLogins;
import com.inthinc.pro.automation.enums.LoginCapabilities;
import com.inthinc.pro.automation.utils.RandomValues;
import com.inthinc.pro.selenium.pageObjects.PageMyAccount;

public class PasswordChangeTest extends WebRallyTest {

    private PageMyAccount myAccountPage;
    private RandomValues random;
    private static String USERNAME = "";
    private static String PASSWORD = "";

    @BeforeClass
    public static void beforeClass() {
     // * This test requires any Admin user, set as a driver with assigned vehicle/device.
        AutomationLogins login = AutomationLogins.getOneBy(LoginCapabilities.PasswordChanging);
        USERNAME = login.getUserName();
        PASSWORD = login.getPassword();
    }
    
    @Before
    public void setupPage() {
        random = new RandomValues();
        myAccountPage = new PageMyAccount();

    }

    @Test
    public void CancelPasswordChanges() {
        set_test_case("TC1285");

        // 0. login
        myAccountPage.loginProcess(USERNAME, PASSWORD);

        // 1. Change existing password and click on Cancel button.
        String password = random.getMixedString(10);
        myAccountPage._link().myAccount().click();
        myAccountPage._button().change().click();
        myAccountPage._popUp().changeMyPassword()._textField().currentPassword().type(PASSWORD);
        myAccountPage._popUp().changeMyPassword()._textField().newPassword().type(password);
        myAccountPage._popUp().changeMyPassword()._textField().confirmNewPassword().type(password);
        myAccountPage._popUp().changeMyPassword()._button().cancel().click();

        // 2. Verify no changes were made to password by logging in with
        // original password.
        myAccountPage.loginProcess(USERNAME, PASSWORD);

    }

    @Test
    public void CancelChange() {
        set_test_case("TC1286");

        // 0. login
        myAccountPage.loginProcess(USERNAME, PASSWORD);

        // 1. Open the Change Password popup and close by clicking on Cancel.
        myAccountPage._link().myAccount().click();
        myAccountPage._button().change().click();
        myAccountPage._popUp().changeMyPassword()._button().cancel().click();

        // 2. Verify no changes were made to password by logging in with
        // original password.
        myAccountPage.loginProcess(USERNAME, PASSWORD);

    }

    @Test
    public void CaseError() {
        set_test_case("TC1287");

        // 0. login
        myAccountPage.loginProcess(USERNAME, PASSWORD);

        // 1. Test entering Upper case for New password and same password in
        // Lower case results in error.
        String password = random.getMixedString(10);
        myAccountPage._link().myAccount().click();
        myAccountPage._button().change().click();
        myAccountPage._popUp().changeMyPassword()._textField().currentPassword().type(PASSWORD);
        myAccountPage._popUp().changeMyPassword()._textField().newPassword().type(password.toUpperCase());
        myAccountPage._popUp().changeMyPassword()._textField().confirmNewPassword().type(password.toLowerCase());
        myAccountPage._popUp().changeMyPassword()._button().change().click();
        myAccountPage._popUp().changeMyPassword()._text().confirmPasswordError().validate("New and Confirm New Password do not match");
        myAccountPage._popUp().changeMyPassword()._button().cancel().click();

        // 2. Verify no changes were made to password by logging in with
        // original password.
        myAccountPage.loginProcess(USERNAME, PASSWORD);
    }

    @Test
    public void ChangeButton() {
        set_test_case("TC1288");

        // 0. login
        myAccountPage.loginProcess(USERNAME, PASSWORD);

        // 1. Successfully change the password.
        String randomPassword = random.getMixedString(10);
        myAccountPage._link().myAccount().click();
        myAccountPage._button().change().click();
        myAccountPage._popUp().changeMyPassword()._textField().currentPassword().type(PASSWORD);
        myAccountPage._popUp().changeMyPassword()._textField().newPassword().type(randomPassword);
        myAccountPage._popUp().changeMyPassword()._textField().confirmNewPassword().type(randomPassword);
        myAccountPage._popUp().changeMyPassword()._button().change().click();
        myAccountPage._text().infoMessage().validate("Password successfully changed");

        // 2. Validate new password is successful.
        myAccountPage.loginProcess(USERNAME, randomPassword);

        // 3. Reset password to original
        myAccountPage._link().myAccount().click();
        myAccountPage._button().change().click();
        myAccountPage._popUp().changeMyPassword()._textField().currentPassword().type(randomPassword);
        myAccountPage._popUp().changeMyPassword()._textField().newPassword().type(PASSWORD);
        myAccountPage._popUp().changeMyPassword()._textField().confirmNewPassword().type(PASSWORD);
        myAccountPage._popUp().changeMyPassword()._button().change().click();

        // 4. Verify changes back to original password is successful.
        myAccountPage.loginProcess(USERNAME, PASSWORD);
        myAccountPage._link().myAccount().click();
    }

    @Test
    public void ConfirmNewPasswordError() {
        set_test_case("TC1289");

        // 0. login
        myAccountPage.loginProcess(USERNAME, PASSWORD);

        // 1. Enter non matching new passwords to generate and validate error.
        myAccountPage._link().myAccount().click();
        myAccountPage._button().change().click();
        myAccountPage._popUp().changeMyPassword()._textField().currentPassword().type(PASSWORD);
        myAccountPage._popUp().changeMyPassword()._textField().newPassword().type(random.getMixedString(12));
        myAccountPage._popUp().changeMyPassword()._textField().confirmNewPassword().type(random.getMixedString(12));
        myAccountPage._popUp().changeMyPassword()._button().change().click();
        myAccountPage._popUp().changeMyPassword()._text().confirmPasswordError().validate("New and Confirm New Password do not match");
        myAccountPage._popUp().changeMyPassword()._button().cancel().click();

        // 2. Verify no changes were made to password by logging in with
        // original password.
        myAccountPage.loginProcess(USERNAME, PASSWORD);

    }

    @Test
    public void NewPasswordFieldRequired() {
        set_test_case("TC1290");

        // 0. login
        myAccountPage.loginProcess(USERNAME, PASSWORD);

        // 1. Change password only in the Confirm field leaving New Password
        // field empty.
        myAccountPage._link().myAccount().click();
        myAccountPage._button().change().click();
        myAccountPage._popUp().changeMyPassword()._textField().currentPassword().type(PASSWORD);
        myAccountPage._popUp().changeMyPassword()._textField().confirmNewPassword().type(random.getMixedString(12));
        myAccountPage._popUp().changeMyPassword()._button().change().click();
        myAccountPage._popUp().changeMyPassword()._text().newPasswordError().validate("Required");
        myAccountPage._popUp().changeMyPassword()._button().cancel().click();

        // 2. Verify no changes were made to password by logging in with
        // original password.
        myAccountPage.loginProcess(USERNAME, PASSWORD);

    }

    @Test
    public void ConfirmPasswordFieldRequired() {
        set_test_case("TC1291");

        // 0. login
        myAccountPage.loginProcess(USERNAME, PASSWORD);

        // 1. Change existing password to less than 6 characters and validate
        // error message.
        myAccountPage._link().myAccount().click();
        myAccountPage._button().change().click();
        myAccountPage._popUp().changeMyPassword()._textField().currentPassword().type(PASSWORD);
        myAccountPage._popUp().changeMyPassword()._textField().newPassword().type(random.getMixedString(12));
        myAccountPage._popUp().changeMyPassword()._button().change().click();
        myAccountPage._popUp().changeMyPassword()._text().confirmPasswordError().validate("Required");
        myAccountPage._popUp().changeMyPassword()._button().cancel().click();

        // 2. Verify no changes were made to password by logging in with
        // original password.
        myAccountPage.loginProcess(USERNAME, PASSWORD);

    }

    @Test
    public void CurrentPasswordError() {
        set_test_case("TC1292");

        // 0. login
        myAccountPage.loginProcess(USERNAME, PASSWORD);

        // 1.Generate a password error by entering incorrect current password.
        String password = random.getMixedString(10);
        myAccountPage._link().myAccount().click();
        myAccountPage._button().change().click();
        myAccountPage._popUp().changeMyPassword()._textField().currentPassword().type(password);
        myAccountPage._popUp().changeMyPassword()._textField().newPassword().type(PASSWORD);
        myAccountPage._popUp().changeMyPassword()._textField().confirmNewPassword().type(PASSWORD);
        myAccountPage._popUp().changeMyPassword()._button().change().click();
        myAccountPage._popUp().changeMyPassword()._text().currentPasswordError().validate("Current Password is incorrect");
        myAccountPage._popUp().changeMyPassword()._button().cancel().click();

        // 2. Verify no changes were made to password by logging in with
        // original password.
        myAccountPage.loginProcess(USERNAME, PASSWORD);

    }

    // TODO:Add Test Case TC1293: Default command button (enter key).
    @Test
    public void MissingRequiredFields() {
        set_test_case("TC1294");

        // 0. login
        myAccountPage.loginProcess(USERNAME, PASSWORD);

        // 1. Validate error displays when submitting password change with empty
        // fields.
        myAccountPage._link().myAccount().click();
        myAccountPage._button().change().click();
        myAccountPage._popUp().changeMyPassword()._button().change().click();
        myAccountPage._popUp().changeMyPassword()._text().currentPasswordError().validate("Required");
        myAccountPage._popUp().changeMyPassword()._text().newPasswordError().validate("Required");
        myAccountPage._popUp().changeMyPassword()._text().confirmPasswordError().validate("Required");
        myAccountPage._popUp().changeMyPassword()._button().cancel().click();

        // 2. Verify no changes were made to password by logging in with
        // original password.
        myAccountPage.loginProcess(USERNAME, PASSWORD);

    }

    @Test
    public void MaxCharacterError() {
        set_test_case("TC1295");

        // 0. login
        myAccountPage.loginProcess(USERNAME, PASSWORD);

        // 1. Change existing password to more than 12 characters and validate
        // error message.
        String randomPassword = random.getMixedString(13);
        myAccountPage._link().myAccount().click();
        myAccountPage._button().change().click();
        myAccountPage._popUp().changeMyPassword()._textField().currentPassword().type(PASSWORD);
        myAccountPage._popUp().changeMyPassword()._textField().newPassword().type(randomPassword);
        myAccountPage._popUp().changeMyPassword()._textField().confirmNewPassword().type(randomPassword);
        myAccountPage._popUp().changeMyPassword()._button().change().click();
        if (myAccountPage._popUp().changeMyPassword()._text().newPasswordError().validate("Must be 6 to 12 characters")) {
            myAccountPage._popUp().changeMyPassword()._button().cancel().click();

        }
        // 2. Verify no changes were made to password by logging in with
        // original password.
        myAccountPage.loginProcess(USERNAME, PASSWORD);
        myAccountPage._link().myAccount().click();

    }

    @Test
    public void MinCharacterError() {
        set_test_case("TC1296");

        // 0. login
        myAccountPage.loginProcess(USERNAME, PASSWORD);

        // 1. Change existing password to less than 6 characters and validate
        // error message.
        String password = random.getMixedString(4);
        myAccountPage._link().myAccount().click();
        myAccountPage._button().change().click();
        myAccountPage._popUp().changeMyPassword()._textField().currentPassword().type(PASSWORD);
        myAccountPage._popUp().changeMyPassword()._textField().newPassword().type(password);
        myAccountPage._popUp().changeMyPassword()._textField().confirmNewPassword().type(password);
        myAccountPage._popUp().changeMyPassword()._button().change().click();
        myAccountPage._popUp().changeMyPassword()._text().newPasswordError().validate("Must be 6 to 12 characters");
        myAccountPage._popUp().changeMyPassword()._button().cancel().click();

        // 2. Verify no changes were made to password by logging in with
        // original password.
        myAccountPage.loginProcess(USERNAME, PASSWORD);
        myAccountPage._link().myAccount().click();
    }

    // TODO: Add TC1297: Tabbing Order (need TAB key).

    @Test
    public void ChangePasswordUI() {
        set_test_case("TC1298");

        // 0. login
        myAccountPage.loginProcess(USERNAME, PASSWORD);

        // 1. Validate fields, buttons display in pop up and cursor focus is in
        // current password field.
        myAccountPage._link().myAccount().click();
        myAccountPage._button().change().click();
        myAccountPage._popUp().changeMyPassword()._textField().currentPassword().focus();
        myAccountPage._popUp().changeMyPassword()._textField().currentPassword().assertVisibility(true);
        myAccountPage._popUp().changeMyPassword()._textField().newPassword().assertVisibility(true);
        myAccountPage._popUp().changeMyPassword()._textField().confirmNewPassword().assertVisibility(true);
        myAccountPage._popUp().changeMyPassword()._button().change().assertVisibility(true);
        myAccountPage._popUp().changeMyPassword()._button().cancel().assertVisibility(true);
        myAccountPage._popUp().changeMyPassword()._button().cancel().click();

        // 2. Verify no changes were made to password by logging in with
        // original password.
        myAccountPage.loginProcess(USERNAME, PASSWORD);
        myAccountPage._link().myAccount().click();
    }

    @Test
    public void ChangePasswordValidation() {
        set_test_case("TC1299");

        // 0. login
        myAccountPage.loginProcess(USERNAME, PASSWORD);

        // 1. Successfully change the password.
        String randomPassword = random.getMixedString(10);
        myAccountPage._link().myAccount().click();
        myAccountPage._button().change().click();
        myAccountPage._popUp().changeMyPassword()._textField().currentPassword().type(PASSWORD);
        myAccountPage._popUp().changeMyPassword()._textField().newPassword().type(randomPassword);
        myAccountPage._popUp().changeMyPassword()._textField().confirmNewPassword().type(randomPassword);
        myAccountPage._popUp().changeMyPassword()._button().change().click();
        myAccountPage._text().infoMessage().validate("Password successfully changed");

        // 2. Validate new password is successful and logs back into the portal.
        myAccountPage.loginProcess(USERNAME, randomPassword);

        // 3. Reset password to original
        myAccountPage._link().myAccount().click();
        myAccountPage._button().change().click();
        myAccountPage._popUp().changeMyPassword()._textField().currentPassword().type(randomPassword);
        myAccountPage._popUp().changeMyPassword()._textField().newPassword().type(PASSWORD);
        myAccountPage._popUp().changeMyPassword()._textField().confirmNewPassword().type(PASSWORD);
        myAccountPage._popUp().changeMyPassword()._button().change().click();

        // 4. Verify changes back to original password is successful.
        myAccountPage.loginProcess(USERNAME, PASSWORD);
        myAccountPage._link().myAccount().click();
    }

    @Test
    public void ValidationSpecialCharacters() {
        set_test_case("TC1301");

        // 0. login
        myAccountPage.loginProcess(USERNAME, PASSWORD);

        // 1. Successfully change the password using special characters.
        String randomPassword = random.getSpecialString(10);
        myAccountPage._link().myAccount().click();
        myAccountPage._button().change().click();
        myAccountPage._popUp().changeMyPassword()._textField().currentPassword().type(PASSWORD);
        myAccountPage._popUp().changeMyPassword()._textField().newPassword().type(randomPassword);
        myAccountPage._popUp().changeMyPassword()._textField().confirmNewPassword().type(randomPassword);
        myAccountPage._popUp().changeMyPassword()._button().change().click();
        myAccountPage._text().infoMessage().validate("Password successfully changed");

        // 2. Validate new password is successful and logs back into the portal.
        myAccountPage.loginProcess(USERNAME, randomPassword);

        // 3. Reset password to original
        myAccountPage._link().myAccount().click();
        myAccountPage._button().change().click();
        myAccountPage._popUp().changeMyPassword()._textField().currentPassword().type(randomPassword);
        myAccountPage._popUp().changeMyPassword()._textField().newPassword().type(PASSWORD);
        myAccountPage._popUp().changeMyPassword()._textField().confirmNewPassword().type(PASSWORD);
        myAccountPage._popUp().changeMyPassword()._button().change().click();

        // 4. Verify changes back to original password is successful.
        myAccountPage.loginProcess(USERNAME, PASSWORD);
        myAccountPage._link().myAccount().click();
    }

    @Test
    public void XWindowControlClose() {
        set_test_case("TC1302");

        // 0. login
        myAccountPage.loginProcess(USERNAME, PASSWORD);

        // 1. Open the Change Password pop-up and close without saving changes
        // by clicking on the X window control.
        String randomPassword = random.getMixedString(10);
        myAccountPage._link().myAccount().click();
        myAccountPage._button().change().click();
        myAccountPage._popUp().changeMyPassword()._textField().newPassword().type(randomPassword);
        myAccountPage._popUp().changeMyPassword()._textField().confirmNewPassword().type(randomPassword);
        myAccountPage._popUp().changeMyPassword()._button().close();

        // 2. Verify no changes were made to password by logging in with
        // original password.
        myAccountPage.loginProcess(USERNAME, PASSWORD);
        myAccountPage._link().myAccount().click();
    }

}
