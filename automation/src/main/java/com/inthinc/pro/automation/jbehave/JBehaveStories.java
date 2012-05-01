package com.inthinc.pro.automation.jbehave;

import static java.util.Arrays.asList;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.LoadFromRelativeFile;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.steps.CandidateSteps;

import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.automation.test.JBehaveTest;
import com.inthinc.pro.automation.test.Test;

public abstract class JBehaveStories extends JBehaveTest {
    
    private Test test;
    private Configuration config;
    
    public JBehaveStories(String uri){
        try {
            URLCodec urlc = new URLCodec();
            this.uri = urlc.decode(urlc.decode(uri));
        } catch (DecoderException e) {
            throw new NullPointerException("Unable to parse uri: " + uri);
        }
    }
    
    /**
     * Helper method to convert an array of page objects into a list<br />
     * to be used by requiredPageObjectsList.<br />
     * Should never be a null list.
     */
    protected List<AbstractPage> pageList(AbstractPage ...pages){
        List<AbstractPage> list = new ArrayList<AbstractPage>();
        list.addAll(asList(pages));
        return list;
    }
    
    // The uri string provides the location to start looking for stories from.
    private final String uri;
    
//  Here we specify the configuration, starting from default
//  MostUsefulConfiguration, and changing only what is needed
    @Override
    public Configuration configuration() {
        if (test == null){
            test = getTest();
        }
        try {
            config = new MostUsefulConfiguration()
                // where to find the stories
                .useStoryLoader(new LoadFromRelativeFile(new File(uri).toURI().toURL()))
                // CONSOLE and TXT reporting
                .useStoryReporterBuilder(
                        new AutoStoryReporterBuilder(test)
                                .withDefaultFormats()
                                .withFormats(Format.CONSOLE, Format.TXT, Format.XML, Format.HTML_TEMPLATE, Format.HTML))
//                .useStepMonitor(new PrintStreamStepMonitor()) // default is SilentStepMonitor()
                //.doDryRun(true)//helpful when generating new steps' methods
                ;
            return config;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected abstract Test getTest();
    
    /**
     * This method supplies the names of the stories<br />
     * that you want to run with the Story class.<br />
     * Only StoryName.story is required, the path<br />
     * is already taken care of.
     * 
     * @param stories
     * @return
     */
    protected List<String> storyPaths(String ...stories){
        List<String> storyPaths = new StoryFinder().findPaths(uri, asList(stories), null);
        return storyPaths;
    }
    
    
        
    /**
     * This method provides the different steps objects<br />
     * for the story to run.  By default it will always<br />
     * include the RallyTest so that things<br />
     * can be recorded in Rally, and it provides the <br />
     * Test before and after methods.
     * 
     * @param steps
     * @return
     */
    public List<CandidateSteps> candidateSteps(Object ...steps){
        List<Object> total = new ArrayList<Object>();
        if (steps[0] != null ){
            total.addAll(asList(steps));
        }
        total.add(test);
        return new AutoStepsFactory(config, total).createCandidateSteps();
    }

}
