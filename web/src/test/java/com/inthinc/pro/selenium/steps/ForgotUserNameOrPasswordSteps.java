package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.Then;

import com.inthinc.pro.automation.enums.LoginCapability;
import com.inthinc.pro.automation.models.AutomationUser;
import com.inthinc.pro.automation.objects.AutomationUsers;
import com.inthinc.pro.selenium.pageObjects.PageLogin;

public class ForgotUserNameOrPasswordSteps extends LoginSteps {
    
    private static final PageLogin page = new PageLogin();
//    private static final AutomationUser autouser = AutomationUsers.getUsers().getOneBy(LoginCapability.StatusActive);
    
    @Then("i click the forgot your user name or password link")
    public void thenIClickTheForgotYourUserNameOrPasswordLink(){
        page._link().forgotUsernamePassword().click();
    }

    @Then("i type a valid email address in the email address field")
    public void thenITypeAValidEmailAddressInTheEmailAddressField(){
        // PENDING functionality has not been implemented yet
        // page._popUp().forgotPassword()._textField().email().type(inputText)
    }

    @Then("i click send")
    public void thenIClickSend(){
        page._popUp().forgotPassword()._button().send().click();
    }

    @Then("i log in to my email account")
    public void thenILogInToMyEmailAccount(){
    // PENDING functionality has not been implemented yet
    }

    @Then("i open the 'Update Your Password' message")
    public void thenIOpenTheUpdateYourPasswordMessage(){
    // PENDING functionality has not been implemented yet
    }

    @Then("i click on the link provided in the email message")
    public void thenIClickOnTheLinkProvidedInTheEmailMessage(){
     // PENDING functionality has not been implemented yet
    }

    @Then("the 'change password' page appears")
    public void thenThechangePasswordPageAppears(){
     // PENDING functionality has not been implemented yet
    }

    @Then("the page contains the user name static text string")
    public void thenThePageContainsTheUserNameStaticTextString(){
     // PENDING
    }

    @Then("the page contains the new password text field")
    public void thenThePageContainsTheNewPasswordTextField(){
      // PENDING
    }

    @Then("the page contains the confirm new password text field")
    public void thenThePageContainsTheConfirmNewPasswordTextField(){
      // PENDING
    }

    @Then("the page contains the change button in the left position")
    public void thenThePageContainsTheChangeButtonInTheLeftPosition(){
      // PENDING
    }

    @Then("the page contains the cancel button in the right position")
    public void thenThePageContainsTheCancelButtonInTheRightPosition(){
      // PENDING
    }

    @Then("the portal version is displayed in the lower-right corner")
    public void thenThePortalVersionIsDisplayedInTheLowerRightCorner(){
      // PENDING
    }

}
