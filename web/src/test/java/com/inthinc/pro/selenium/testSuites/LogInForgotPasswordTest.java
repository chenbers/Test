package com.inthinc.pro.selenium.testSuites;

import java.util.List;

import org.jbehave.core.steps.CandidateSteps;

import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.selenium.steps.ForgotUserNameOrPasswordSteps;

public class LogInForgotPasswordTest extends WebStories {

    @Override
    protected List<String> storyPaths() {

        return storyPaths("LogInForgotPassword.story");
    }

    @Override
    public List<CandidateSteps> candidateSteps() {

        return candidateSteps(new ForgotUserNameOrPasswordSteps());
    }
    
    @Override
    public List<AbstractPage> requiredPageObjectsList() {
        return pageList();
    }
}