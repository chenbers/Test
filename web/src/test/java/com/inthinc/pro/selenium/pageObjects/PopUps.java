package com.inthinc.pro.selenium.pageObjects;

import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.CheckBoxTable;
import com.inthinc.pro.automation.elements.ClickableObject;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextCheckboxLabel;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldError;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextLabel;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.enums.TextEnum;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.selenium.pageEnums.PopUpEnum;

public class PopUps extends MasterTest {

    private String page;
    private String type;
    private Integer number;

    public enum Types implements TextEnum {
        SINGLE("_singleEmail"),
        POPUP("_emailPopup"),
        REPORT("_reportEmailModal"),
        PERFORMANCE("ReportEmailModal"), ;

        private String type;

        private Types(String type) {
            this.type = type;
        }

        public String getText() {
            return type;
        }
    }

    public PopUps(String page, Types type, Integer number) {
        this.page = page;
        this.type = type.getText();
        this.number = ++number; // TODO: dtanner: better way?
    }

    public PopUps(String page) {
        this.page = page;
    }

    public PopUps(Integer number) {
        this.number = number;
    }

    public PopUps() {}

    public class EditColumns {

        public EditColumnsText _text() {
            return new EditColumnsText();
        }

        public EditColumnsButton _button() {
            return new EditColumnsButton();
        }

        public EditColumnsLinks _link() {
            return new EditColumnsLinks();
        }

        public class EditColumnsLinks {

            public TextCheckboxLabel entry() {
                return new TextCheckboxLabel(PopUpEnum.EDIT_LABEL, page);
            }
        }

        public class EditColumnsButton {

            public TextButton save() {
                return new TextButton(PopUpEnum.EDIT_SAVE, page);
            }

            public TextButton cancel() {
                return new TextButton(PopUpEnum.EDIT_CANCEL, page) {

                    @Override
                    public ClickableObject click() {
                        super.click();
                        assertVisibility(false);
                        return this;
                    }
                };
            }

            public TextButton close() {
                return new TextButton(PopUpEnum.X) {

                    @Override
                    public ClickableObject click() {
                        super.click();
                        assertVisibility(false);
                        return this;
                    }
                };
            }
        }

        public class EditColumnsText {

            public Text header() {
                return new Text(PopUpEnum.EDIT_HEADER, page);
            }

            public Text title() {
                return new Text(PopUpEnum.TITLE, page);
            }

        }

        public CheckBoxTable _checkBox() {
            return new CheckBoxTable(PopUpEnum.EDIT_CHECKBOX, page);
        }

    }

    public class Email {

        public EmailButton _button() {
            return new EmailButton();
        }

        public EmailTextField _textField() {
            return new EmailTextField();
        }

        public EmailText _text() {
            return new EmailText();
        }

        public class EmailButton {

            public TextButton email() {
                return new TextButton(PopUpEnum.EMAIL_SUBMIT, page + type, number);
            }

            public TextButton cancel() {
                return new TextButton(PopUpEnum.EMAIL_CANCEL) {

                    @Override
                    public ClickableObject click() {
                        super.click();
                        assertVisibility(false);
                        return this;
                    }
                };
            }

            public Button close() {
                return new Button(PopUpEnum.X, page + type) {

                    @Override
                    public ClickableObject click() {
                        super.click();
                        assertVisibility(false);
                        return this;
                    }
                };
            }
        }

        public class EmailText {

            public Text title() {
                return new Text(PopUpEnum.TITLE, page + type);
            }

            public Text subTitle() {
                return new Text(PopUpEnum.EMAIL_SUBTITLE, page + type);
            }

            public Text header() {
                return new Text(PopUpEnum.EMAIL_HEADER, page + type);
            }
        }

        public class EmailTextField {

            public TextField emailAddresses() {
                return new TextField(PopUpEnum.EMAIL_TEXTFIELD, page + type);
            }
        }
    }

    public class ExcludeEvent {
        
        private Map<String, PopUpEnum> enums;
        private static final String message = "message";
        private static final String title = "title";
        private static final String header = "header";
        private static final String confirm = "confirm";
        private static final String cancel = "cancel";
        private static final String close = "close";
        
        

        public ExcludeEvent(boolean notifications) {
            enums = new HashMap<String, PopUpEnum>();
            if (notifications) {
                enums.put(message, PopUpEnum.EXCLUDE_NOTIFICATIONS_MESSAGE);
                enums.put(title, PopUpEnum.EXCLUDE_NOTIFICATIONS_TITLE);
                enums.put(header, PopUpEnum.EXCLUDE_NOTIFICATIONS_HEADER);
                enums.put(confirm, PopUpEnum.EXCLUDE_NOTIFICATIONS_CONFIRM);
                enums.put(cancel, PopUpEnum.EXCLUDE_NOTIFICATIONS_CANCEL);
                enums.put(close, PopUpEnum.EXCLUDE_NOTIFICATIONS_CLOSE);
            } else {
                enums.put(message, PopUpEnum.EXCLUDE_PERFORMANCE_MESSAGE);
                enums.put(title, PopUpEnum.EXCLUDE_PERFORMANCE_TITLE);
                enums.put(header, PopUpEnum.EXCLUDE_PERFORMANCE_HEADER);
                enums.put(confirm, PopUpEnum.EXCLUDE_PERFORMANCE_CONFIRM);
                enums.put(cancel, PopUpEnum.EXCLUDE_PERFORMANCE_CANCEL);
                enums.put(close, PopUpEnum.EXCLUDE_PERFORMANCE_CLOSE);
            }
        }
        
        public ExcludeEventTexts _text(){
            return new ExcludeEventTexts();
        }
        
        public class ExcludeEventTexts{
            public Text title(){
                return new Text(enums.get(title), page);
            }
            
            public Text header(){
                return new Text(enums.get(header), page);
            }
            
            public Text message(){
                return new Text(enums.get(message), page);
            }
        }
        
        
        public ExcludeEventButtons _button(){
            return new ExcludeEventButtons();
        }
        
        public class ExcludeEventButtons{
            public TextButton yes(){
                return new TextButton(enums.get(confirm), page);
            }
            
            public TextButton no(){
                return new TextButton(enums.get(cancel), page);
            }
            
            public TextButton close(){
                return new TextButton(enums.get(close), page);
            }
            
        }
    }

    public class LoginError {

        public ErrorTexts _text() {
            return new ErrorTexts();
        }

        public class ErrorTexts {

            public Text header() {
                return new Text(PopUpEnum.ERROR_HEADER);
            }

            public Text message() {
                return new Text(PopUpEnum.ERROR_MESSAGE);
            }
        }

        public ErrorButtons _button() {
            return new ErrorButtons();
        }

        public class ErrorButtons {

            public TextButton ok() {
                return new TextButton(PopUpEnum.ERROR_BUTTON_OK);
            }

            public Button close() {
                return new Button(PopUpEnum.ERROR_CLOSE);
            }
        }
    }

    public class MessageSent {

        public MessageSentText _text() {
            return new MessageSentText();
        }

        public class MessageSentText {

            public Text header() {
                return new Text(PopUpEnum.MESSAGE_SENT_HEADER);
            }

            public Text title() {
                return new Text(PopUpEnum.MESSAGE_SENT_MESSAGE);
            }

            public Text boldTitle() {
                return new Text(PopUpEnum.MESSAGE_SENT_BOLD);
            }

            public Text bullet1() {
                return new Text(PopUpEnum.MESSAGE_SENT_BULLET_1);
            }

            public Text bullet2() {
                return new Text(PopUpEnum.MESSAGE_SENT_BULLET_2);
            }

            public Text bullet3() {
                return new Text(PopUpEnum.MESSAGE_SENT_BULLET_3);
            }
        }
    }

    public class ForgotPassword {

        public ForgotText _text() {
            return new ForgotText();
        }

        public class ForgotText {

            public Text header() {
                return new Text(PopUpEnum.FORGOT_HEADER);
            }

            public Text error() {
                return new Text(PopUpEnum.FORGOT_ERROR);
            }

            public Text title() {
                return new Text(PopUpEnum.FORGOT_TITLE);
            }

            public Text label() {
                return new Text(PopUpEnum.FORGOT_EMAIL_LABEL);
            }
        }

        public ForgotTextField _textField() {
            return new ForgotTextField();
        }

        public class ForgotTextField {

            public TextField email() {
                return new TextField(PopUpEnum.FORGOT_EMAIL_FIELD);
            }
        }

        public ForgotButtons _button() {
            return new ForgotButtons();
        }

        public class ForgotButtons {

            public TextButton send() {
                return new TextButton(PopUpEnum.FORGOT_SEND);
            }

            public TextButton cancel() {
                return new TextButton(PopUpEnum.FORGOT_CANCEL_BUTTON) {

                    @Override
                    public ClickableObject click() {
                        super.click();
                        assertVisibility(false);
                        return this;
                    }
                };
            }

            public Button close() {
                return new Button(PopUpEnum.FORGOT_CLOSE) {

                    @Override
                    public ClickableObject click() {
                        super.click();
                        assertVisibility(false);
                        return this;
                    }
                };
            }
        }
    }

    public class ForgotChangePassword {

        public ChangeButtons _button() {
            return new ChangeButtons();
        }

        public class ChangeButtons {

            public TextButton ok() {
                return new TextButton(PopUpEnum.FORGOT_CHANGE_OK);
            }
        }

        public ChangeText _text() {
            return new ChangeText();
        }

        public class ChangeText {

            public Text header() {
                return new Text(PopUpEnum.FORGOT_CHANGE_SUCCESS);
            }

            public Text message() {
                return new Text(PopUpEnum.FORGOT_CHANGE_MESSAGE);
            }
        }
    }
    public class AssignDriverToVehicle {

        public DriverToVehicleButtons _button() {
            return new DriverToVehicleButtons();
        }

        public class DriverToVehicleButtons {
            public TextButton x() {
                return new TextButton(PopUpEnum.X, "chooseDriver"){

                    @Override
                    public ClickableObject click() {
                        super.click();
                        assertVisibility(false);
                        return this;
                    }
                };
            }
        }

        public DriverToVehicleText _text() {
            return new DriverToVehicleText();
        }

        public class DriverToVehicleText {
            public TextTable columnEmployeeID() {
                return new TextTable(PopUpEnum.TXTTABLE_D2V_EMPID);
            }
            public TextTable columnName() {
                return new TextTable(PopUpEnum.TXTTABLE_D2V_NAME);
            }
            public TextTable columnDriverTeam() {
                return new TextTable(PopUpEnum.TXTTABLE_D2V_TEAM);
            }
            public TextTable columnDriverStatusD() {
                return new TextTable(PopUpEnum.TXTTABLE_D2V_STATUS);
            }
            public TextTable columnAssigned() {
                return new TextTable(PopUpEnum.TXTTABLE_D2V_ASSIGNED);
            }
        }
        public DriverToVehicleLinks _links() {
            return new DriverToVehicleLinks();
        }
        public class DriverToVehicleLinks {

            public TextLink sortEmployeeID() {
                return new TextLink(PopUpEnum.TXTLINKSORT_D2V_EMPID);
            }
            public TextLink sortName() {
                return new TextLink(PopUpEnum.TXTLINKSORT_D2V_NAME);
            }
            public TextLink sortDriverTeam() {
                return new TextLink(PopUpEnum.TXTLINKSORT_D2V_TEAM);
            }
            public TextLink sortDriverStatus() {
                return new TextLink(PopUpEnum.TXTLINKSORT_D2V_STATUS);
            }
            public TextLink sortAssigned() {
                return new TextLink(PopUpEnum.TXTLINKSORT_D2V_ASSIGNED);
            }
            
            public TextTableLink assign() {
                return new TextTableLink(PopUpEnum.TXTTABLELINK_D2V_ASSIGN);
            }
        }
    }
    public class MyAccountChangePassword {

        public MyChangeTexts _text() {
            return new MyChangeTexts();
        }

        public class MyChangeTexts {

            public Text title() {
                return new Text(PopUpEnum.MY_CHANGE_TITLE);
            }

            public TextFieldLabel currentPasswordLabel() {
                return new TextFieldLabel(PopUpEnum.MY_CURRENT_TEXTFIELD);
            }

            public TextFieldLabel newPasswordLabel() {
                return new TextFieldLabel(PopUpEnum.MY_NEW_TEXTFIELD);
            }

            public TextFieldLabel confirmPasswordLabel() {
                return new TextFieldLabel(PopUpEnum.MY_CONFIRM_TEXTFIELD);
            }

            public TextFieldError currentPasswordError() {
                return new TextFieldError(PopUpEnum.MY_CURRENT_TEXTFIELD);
            }

            public TextFieldError newPasswordError() {
                return new TextFieldError(PopUpEnum.MY_NEW_TEXTFIELD);
            }

            public TextFieldError confirmPasswordError() {
                return new TextFieldError(PopUpEnum.MY_CONFIRM_TEXTFIELD);
            }

            public Text passwordStrength() {
                return new Text(PopUpEnum.MY_STRENGTH_MSG);
            }

        }

        public MyChangeTextFields _textField() {
            return new MyChangeTextFields();
        }

        public class MyChangeTextFields {

            public TextField currentPassword() {
                return new TextField(PopUpEnum.MY_CURRENT_TEXTFIELD);
            }

            public TextField newPassword() {
                return new TextField(PopUpEnum.MY_NEW_TEXTFIELD);
            }

            public TextField confirmNewPassword() {
                return new TextField(PopUpEnum.MY_CONFIRM_TEXTFIELD);
            }
        }

        public MyChangeButtons _button() {
            return new MyChangeButtons();
        }

        public class MyChangeButtons {

            public TextButton change() {
                return new TextButton(PopUpEnum.MY_CHANGE_CHANGE);
            }

            public TextButton cancel() {
                return new TextButton(PopUpEnum.MY_CHANGE_CANCEL);
            }

            public Button close() {
                return new Button(PopUpEnum.MY_CHANGE_X);
            }
        }
    }
    
    public class FuelStopsDelete {
        
        public FuelStopsDeleteButtons _button(){
            return new FuelStopsDeleteButtons();
        }
        
        public class FuelStopsDeleteButtons {
            public TextButton delete(){
                return new TextButton(PopUpEnum.HOS_FUEL_STOPS_DELETE_DELETE);
            }
            
            public TextButton cancel(){
                return new TextButton(PopUpEnum.HOS_FUEL_STOPS_DELETE_CANCEL);
            }
            
            public Button close(){
                return new Button(PopUpEnum.HOS_FUEL_STOPS_DELETE_CLOSE);
            }
        }
        
        public FuelStopsDeleteTexts _text(){
            return new FuelStopsDeleteTexts();
        }
        
        public class FuelStopsDeleteTexts {
            public Text title(){
                return new Text(PopUpEnum.HOS_FUEL_STOPS_DELETE_TITLE);
            }
            
            public Text header(){
                return new Text(PopUpEnum.HOS_FUEL_STOPS_DELETE_HEADER);
            }
            
            public Text entry(){
                return new Text(PopUpEnum.HOS_FUEL_STOPS_DELETE_DETAILS);
            }
        }
    }

    public class AdminDelete {

        private PopUpEnum delete;
        private PopUpEnum cancel;

        public AdminDelete(Boolean table) {
            if (table) {
                delete = PopUpEnum.DELETE_CONFIRM;
                cancel = PopUpEnum.DELETE_CANCEL;
            } else {
                delete = PopUpEnum.DETAILS_DELETE_CONFIRM;
                cancel = PopUpEnum.DETAILS_DELETE_CANCEL;
            }
        }

        public AdminDeleteButtons _button() {
            return new AdminDeleteButtons();
        }

        public class AdminDeleteButtons {

            public TextButton delete() {
                return new TextButton(delete, page);
            }

            public TextButton cancel() {
                return new TextButton(cancel, page);
            }

            public Button close() {
                return new Button(PopUpEnum.DELETE_CLOSE);
            }
        }

        public AdminDeleteText _text() {
            return new AdminDeleteText();
        }

        public class AdminDeleteText {

            public Text header() {
                return new Text(PopUpEnum.DELETE_HEADER);
            }

            public Text message() {
                return new Text(PopUpEnum.DELETE_MESSAGE);
            }
        }
    }

    public class PasswordChangeRequired {

        public PasswordChangeRequiredButtons _button() {
            return new PasswordChangeRequiredButtons();
        }

        public PasswordChangeRequiredTexts _text() {
            return new PasswordChangeRequiredTexts();
        }

        public PasswordChangeRequiredTextFields _textField() {
            return new PasswordChangeRequiredTextFields();
        }

        public class PasswordChangeRequiredTextFields {

            public TextField currentPassword() {
                return new TextField(PopUpEnum.MY_CURRENT_TEXTFIELD);
            }

            public TextField newPassword() {
                return new TextField(PopUpEnum.MY_NEW_TEXTFIELD);
            }

            public TextField confirmNewPassword() {
                return new TextField(PopUpEnum.MY_CONFIRM_TEXTFIELD);
            }
        }

        public class PasswordChangeRequiredButtons {

            public TextButton change() {
                return new TextButton(PopUpEnum.MY_CHANGE_CHANGE);
            }
        }

        public class PasswordChangeRequiredTexts {

            public TextFieldLabel currentPassword() {
                return new TextFieldLabel(PopUpEnum.MY_CURRENT_TEXTFIELD);
            }

            public TextFieldLabel newPassword() {
                return new TextFieldLabel(PopUpEnum.MY_NEW_TEXTFIELD);
            }

            public TextFieldLabel confirmPassword() {
                return new TextFieldLabel(PopUpEnum.MY_CURRENT_TEXTFIELD);
            }

            public Text header() {
                return new Text(PopUpEnum.MY_CHANGE_TITLE_REQUIRED);
            }

            public Text currentPasswordError() {
                return new Text(PopUpEnum.MY_CURRENT_TEXTFIELD);
            }

            public Text newPasswordError() {
                return new Text(PopUpEnum.MY_NEW_TEXTFIELD);
            }

            public Text confirmPasswordError() {
                return new Text(PopUpEnum.MY_CONFIRM_TEXTFIELD);
            }

            public Text passwordStrength() {
                return new Text(PopUpEnum.MY_STRENGTH_MSG);
            }
        }
    }

    public class UpdatePasswordReminder {

        public UpdateLinks _link() {
            return new UpdateLinks();
        }

        public UpdateButtons _button() {
            return new UpdateButtons();
        }

        public UpdateTexts _text() {
            return new UpdateTexts();
        }

        public class UpdateButtons {

            public Button close() {
                return new Button(PopUpEnum.UPDATE_PASSWORD_CLOSE);
            }
        }

        public class UpdateTexts {

            public Text header() {
                return new Text(PopUpEnum.UPDATE_PASSWORD_HEADER);
            }

            public Text message() {
                return new Text(PopUpEnum.UPDATE_PASSWORD_MESSAGE);
            }
        }

        public class UpdateLinks {

            public TextLink changePassword() {
                return new TextLink(PopUpEnum.UPDATE_PASSWORD_CHANGE_PASSWORD);
            }
        }
    }

    public class PasswordChange {

        public PasswordChangeTexts _text() {
            return new PasswordChangeTexts();
        }

        public class PasswordChangeTexts {

            public Text title() {
                return new Text(PopUpEnum.MY_CHANGE_TITLE);
            }

            public TextFieldLabel currentPasswordLabel() {
                return new TextFieldLabel(PopUpEnum.MY_CURRENT_TEXTFIELD);
            }

            public TextFieldLabel newPasswordLabel() {
                return new TextFieldLabel(PopUpEnum.MY_NEW_TEXTFIELD);
            }

            public TextFieldLabel confirmPasswordLabel() {
                return new TextFieldLabel(PopUpEnum.MY_CONFIRM_TEXTFIELD);
            }

            public TextFieldError currentPasswordError() {
                return new TextFieldError(PopUpEnum.MY_CURRENT_TEXTFIELD);
            }

            public TextFieldError newPasswordError() {
                return new TextFieldError(PopUpEnum.MY_NEW_TEXTFIELD);
            }

            public TextFieldError confirmPasswordError() {
                return new TextFieldError(PopUpEnum.MY_CONFIRM_TEXTFIELD);
            }

            public Text passwordStrength() {
                return new Text(PopUpEnum.CHANGE_PASSWORD_FORM_STRENGTH_MSG);
            }

        }

        public PasswordChangeTextFields _textField() {
            return new PasswordChangeTextFields();
        }

        public class PasswordChangeTextFields {

            public TextField currentPassword() {
                return new TextField(PopUpEnum.MY_CURRENT_TEXTFIELD);
            }

            public TextField newPassword() {
                return new TextField(PopUpEnum.MY_NEW_TEXTFIELD);
            }

            public TextField confirmNewPassword() {
                return new TextField(PopUpEnum.MY_CONFIRM_TEXTFIELD);
            }
        }

        public PasswordChangeButtons _button() {
            return new PasswordChangeButtons();
        }

        public class PasswordChangeButtons {

            public TextButton change() {
                return new TextButton(PopUpEnum.MY_CHANGE_CHANGE);
            }

            public TextButton cancel() {
                return new TextButton(PopUpEnum.MY_CHANGE_CANCEL);
            }

            public Button close() {
                return new Button(PopUpEnum.CHANGE_PASSWORD_FORM_CHANGE_X);
            }
        }
    }
    
    public class LocationPopUp {
        public class LocationTexts {
            public Text title(){
                return new Text(PopUpEnum.LOCATION_HEADER);
            }
            
            public Text bubbleValueName(){
                return new Text(PopUpEnum.LOCATION_BUBBLE_NAME);
            }
            
            public Text bubbleValueDateTime(){
                return new Text(PopUpEnum.LOCATION_BUBBLE_DATE_TIME);
            }
            
            public Text bubbleValueDetail(){
                return new Text(PopUpEnum.LOCATION_BUBBLE_DETAIL);
            }
            
            public TextLabel bubbleLabelName(){
                return new TextLabel(PopUpEnum.LOCATION_BUBBLE_NAME);
            }
            
            public TextLabel bubbleLabelDateTime(){
                return new TextLabel(PopUpEnum.LOCATION_BUBBLE_DATE_TIME);
            }
            
            public TextLabel bubbleLabelDetail(){
                return new TextLabel(PopUpEnum.LOCATION_BUBBLE_DETAIL);
            }
            
            
        }
        public class LocationButtons {
            public Button xLocationPopUp(){
                return new Button(PopUpEnum.LOCATION_CLOSE, page);
            }
            
            public TextButton closeLocationPopUp(){
                return new TextButton(PopUpEnum.LOCATION_CLOSE_BUTTON, page);
            }
            
            public Button closeLocationBubble(){
                return new Button(PopUpEnum.LOCATION_BUBBLE_CLOSE);
            }
        }
        
        public LocationTexts _text(){
            return new LocationTexts();
        }
        
        public LocationButtons _button(){
            return new LocationButtons();
        }
    }
}
