package com.inthinc.pro.selenium.testSuites;

import org.jbehave.core.annotations.UsingSteps;
import org.junit.Test;

import com.inthinc.pro.automation.annotations.AutomationAnnotations.PageObjects;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.StoryPath;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveDashboard;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.steps.SearchCrashesSteps;

@UsingSteps(instances={SearchCrashesSteps.class})
@PageObjects(list={PageLogin.class, PageExecutiveDashboard.class, PageAdminUsers.class})
@StoryPath(path="SearchCrashes.story")
public class SearchCrashesStory extends WebStories {

    @Test
    public void test(){};
}
