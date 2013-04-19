package com.inthinc.pro.selenium.testSuites;

import org.jbehave.core.annotations.UsingSteps;
import org.junit.Test;

import com.inthinc.pro.automation.annotations.AutomationAnnotations.PageObjects;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.StoryPath;
import com.inthinc.pro.selenium.pageObjects.PageRouteGetEndpoint;
import com.inthinc.pro.selenium.steps.RouteGetEndpointSteps;

@UsingSteps(instances={RouteGetEndpointSteps.class})
@PageObjects(list={PageRouteGetEndpoint.class })
@StoryPath(path="RouteGetEndpoint.story")
public class RouteGetEndpointTest extends WebStories {
    
    @Test
    public void test(){}

}

