package com.inthinc.pro.selenium.testSuites;

import org.jbehave.core.annotations.UsingSteps;
import org.junit.Test;

import com.inthinc.pro.automation.annotations.AutomationAnnotations.StoryPath;
import com.inthinc.pro.selenium.steps.RoadHazardEndpointSteps;

@UsingSteps(instances={RoadHazardEndpointSteps.class})
@StoryPath(path="RoadHazardEndpoint.story")
public class RoadHazardEndpointTest extends WebStories  {

    @Test
    public void test(){}
 
}