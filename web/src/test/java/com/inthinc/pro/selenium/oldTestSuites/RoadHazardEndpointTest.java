package com.inthinc.pro.selenium.oldTestSuites;

import org.jbehave.core.annotations.UsingSteps;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.automation.annotations.AutomationAnnotations.StoryPath;
import com.inthinc.pro.selenium.steps.RoadHazardEndpointSteps;
import com.inthinc.pro.selenium.testSuites.WebStories;
@Ignore
@UsingSteps(instances={RoadHazardEndpointSteps.class})
@StoryPath(path="RoadHazardEndpoint.story")
public class RoadHazardEndpointTest extends WebStories  {

    @Test
    public void test(){}
 
}
