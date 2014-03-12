package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.ErrorPageEnum;
import com.inthinc.pro.selenium.pageEnums.LoginEnum;

public class PageErrorPage extends Masthead {

    public PageErrorPage(){
        checkMe.add(ErrorPageEnum.TRY_AGAIN_BUTTON);
    }

    public LoginButtons _button(){
        return new LoginButtons();
    }
    public class LoginButtons extends MastheadButtons{
        public TextButton tryAgain(){
            return new TextButton(ErrorPageEnum.TRY_AGAIN_BUTTON);
        }
    }
    
    public LoginTexts _text(){
        return new LoginTexts();
    }
    public class LoginTexts extends MastheadTexts{}

    @Override
    public SeleniumEnums setUrl() {
        return ErrorPageEnum.ERROR_PAGE_URL;
    }


    @Override
    protected boolean checkIsOnPage() {
        return _button().tryAgain().isPresent();
    }
    
}
