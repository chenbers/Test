package com.inthinc.pro.automation.jbehave;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.annotations.UsingSteps;
import org.jbehave.core.configuration.AnnotationBuilder;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.LoadFromRelativeFile;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InjectableStepsFactory;

import com.google.common.util.concurrent.MoreExecutors;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.PageObjects;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.StoryPath;
import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.automation.test.Test;


public class AutoAnnotationBuilder extends AnnotationBuilder {

    private final List<AbstractPage> pageList;
    private final Test test;
    private final String uri;
    private Configuration config;
    private Embedder embedder;
    private AutoStepsFactory stepsFactory;

    public AutoAnnotationBuilder(Class<?> testClass, Test test, String uri) {
        super(testClass);
        this.test = test;
        this.uri = uri;
        this.pageList = getPageObjects(testClass);
        buildConfiguration();
    }
    
    
    private List<AbstractPage> getPageObjects(Class<?> testClass) {
        List<AbstractPage> pages = new ArrayList<AbstractPage>();
        if (annotationFinder().isAnnotationPresent(PageObjects.class)) {
            List<Class<AbstractPage>> stepsClasses = annotationFinder().getAnnotatedClasses(PageObjects.class, AbstractPage.class, "list");
            for (Class<AbstractPage> stepsClass : stepsClasses) {
                pages.add(instanceOf(AbstractPage.class, stepsClass));
            }
            
        }
        
        return pages;
    }


    public Embedder buildEmbedder() {
        if (embedder == null){
            embedder = new AutoEmbedder(pageList);
            embedder.useExecutorService(MoreExecutors.sameThreadExecutor());
            embedder.useConfiguration(buildConfiguration());
            embedder.useStepsFactory(buildStepsFactory(buildConfiguration()));
        }
        return embedder;
    }
    
    

//  Here we specify the configuration, starting from default
//  MostUsefulConfiguration, and changing only what is needed
    @Override
    public Configuration buildConfiguration() {
        if (config == null){
            try {
                config = new AutoConfiguration()
                    // where to find the stories
                    .useStoryLoader(new LoadFromRelativeFile(new File(uri).toURI().toURL()))
//                    .useStoryParser(new AutoStoryParser())
                    // CONSOLE and TXT reporting
                    .useStoryReporterBuilder(
                            new AutoStoryReporterBuilder(test)
                                    .withDefaultFormats()
                                    .withFormats(Format.CONSOLE, Format.TXT, Format.XML, Format.HTML_TEMPLATE, Format.HTML))
    //                .useStepMonitor(new PrintStreamStepMonitor()) // default is SilentStepMonitor()
                    //.doDryRun(true)//helpful when generating new steps' methods
                    ;
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("Unable to load file as a url: " + uri);
            }
        }
       return config;
    }
    
    public List<String> findPaths() {
        if (!annotationFinder().isAnnotationPresent(StoryPath.class)) {
            return new ArrayList<String>();
        }
        List<String> stories = annotationFinder().getAnnotatedValues(StoryPath.class, String.class, "paths");
        if (stories.size()==1 && stories.get(0).equals("")){
            stories.remove(0);
        }
        String path = annotationFinder().getAnnotatedValue(StoryPath.class, String.class, "path");
        stories.add(path);
        for (String story : stories){
            stories.remove(story);
            stories.add(story);
        }
        
        return new StoryFinder().findPaths(uri, stories, null);
    }
    
    
    /**
     * Builds CandidateSteps using annotation {@link UsingSteps} found in the
     * annotated object instance and using the configuration build by
     * {@link #buildConfiguration()}
     * 
     * @return A List of CandidateSteps instances
     */
    public List<CandidateSteps> buildCandidateSteps() {
        return buildCandidateSteps(buildConfiguration());
    }

    /**
     * Builds CandidateSteps using annotation {@link UsingSteps} found in the
     * annotated object instance and the configuration provided
     * 
     * @param configuration the Configuration
     * @return A List of CandidateSteps instances
     */
    public List<CandidateSteps> buildCandidateSteps(Configuration configuration) {
        return buildStepsFactory(configuration).createCandidateSteps();
    }
    
    /**
     * Builds the {@link InjectableStepsFactory} using annotation
     * {@link UsingSteps} found in the annotated object instance and the
     * configuration provided
     * 
     * @param configuration the Configuration
     * @return A {@link InjectableStepsFactory}
     */
    public AutoStepsFactory buildStepsFactory(Configuration configuration) {
        if (stepsFactory == null){
            List<Object> stepsInstances = new ArrayList<Object>();
            if (annotationFinder().isAnnotationPresent(UsingSteps.class)) {
                List<Class<Object>> stepsClasses = annotationFinder().getAnnotatedClasses(UsingSteps.class, Object.class, "instances");
                for (Class<Object> stepsClass : stepsClasses) {
                    stepsInstances.add(instanceOf(Object.class, stepsClass));
                }
                stepsInstances.add(test);
                stepsInstances.add(new AutoCustomSteps());
                stepsFactory = new AutoStepsFactory(configuration, stepsInstances);
            }
        }

        return stepsFactory;
    }
}
