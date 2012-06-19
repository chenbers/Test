package com.inthinc.pro.selenium.testSuites;

import org.jbehave.core.annotations.UsingSteps;
import org.junit.Test;

import com.inthinc.pro.automation.annotations.AutomationAnnotations.PageObjects;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.StoryPath;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveDashboard;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.steps.ForgotUserNameOrPasswordSteps;

@UsingSteps(instances={ForgotUserNameOrPasswordSteps.class})
@PageObjects(list={PageLogin.class, PageExecutiveDashboard.class})
@StoryPath(path="LogInForgotPassword.story")
public class LogInForgotPasswordTest extends WebStories {

    @Test
    public void test(){}
}