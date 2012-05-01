package com.inthinc.pro.automation.test;

import java.util.List;

import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.steps.CandidateSteps;
import org.junit.Test;

import com.google.common.util.concurrent.MoreExecutors;
import com.inthinc.pro.automation.jbehave.AutoEmbedder;
import com.inthinc.pro.automation.selenium.AbstractPage;

/**
 * This class provides the abstract methods that<br />
 * we are going to require in order to qualify<br />
 * as a JBehave test.  This class should only be<br />
 * changed if we need to alter how the JUnit test<br />
 * is run, or how we want JBehave to work.<br />
 * 
 * @author dtanner
 *
 */
public abstract class JBehaveTest extends JUnitStories {

    /**
     * This is the method we use to hook into the JUnit tests.<br />
     * We are overriding it so we can set the embedder,<br />
     * and use the sameExecutor thread service.
     */
    @Override
    @Test
    public void run() throws Throwable {
        super.useEmbedder(new AutoEmbedder(requiredPageObjectsList()));
        
        Embedder embedder = configuredEmbedder();
        embedder.useExecutorService(MoreExecutors.sameThreadExecutor());
        try {
            embedder.runStoriesAsPaths(storyPaths());
        } finally {
            embedder.generateCrossReference();
        }
    }
    


    /**
     * Method to return Test, or RallyTest for JUnit before and after methods.
     * @return
     */
    protected abstract com.inthinc.pro.automation.test.Test getTest();
    

    /**
     * Provides a list of stories to be run.
     */
    @Override
    protected abstract List<String> storyPaths() ;


    /**
     * Provides a list of step objects that are more complicated<br />
     * than we can automate by parsing the step.
     */
    @Override
    public abstract List<CandidateSteps> candidateSteps();
        
    

    /**
     * This method MUST return page objects to be used by the automation<br />
     * so that we can interact with the pages.<br />
     * <br />
     */
    public abstract List<AbstractPage> requiredPageObjectsList();
    

}
