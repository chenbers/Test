package com.inthinc.pro.automation.jbehave;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jbehave.core.reporters.ConcurrentStoryReporter;
import org.jbehave.core.reporters.StoryReporter;

import com.inthinc.pro.automation.test.RallyTest;

public class AutoStoryReporter extends ConcurrentStoryReporter {
    
    private final static String testCase = "[Tt][Cc][0123456789]*";
    private final static String defect = "[Dd][Ee][0123456789]*";

    public AutoStoryReporter(StoryReporter crossReferencing, StoryReporter delegate, boolean multiThreading) {
        super(crossReferencing, delegate, multiThreading);
    }

    
    @Override
    public void beforeScenario(String scenarioTitle) {
        Pattern tc = Pattern.compile(testCase);
        Matcher matchTC = tc.matcher(scenarioTitle);
        
        if (matchTC.find()) {
            Long threadID = Thread.currentThread().getId();
            
            String tcID = scenarioTitle.substring(matchTC.start(), matchTC.end());
            RallyTest.set_test_case(tcID, threadID);

            Pattern de = Pattern.compile(defect);
            Matcher matchDE = de.matcher(scenarioTitle);
            if (matchDE.find()){
                String deID = scenarioTitle.substring(matchDE.start(), matchDE.end());
                RallyTest.set_defect(deID, threadID);
            }
        }
        super.beforeScenario(scenarioTitle);
    }

}
