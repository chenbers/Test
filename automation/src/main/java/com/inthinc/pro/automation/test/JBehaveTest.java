package com.inthinc.pro.automation.test;

import java.util.List;
import java.util.Map;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;
import org.jbehave.core.configuration.AnnotationBuilder;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.StoryNameResolver;
import org.jbehave.core.junit.AnnotatedPathRunner;
import org.jbehave.core.model.Scenario;
import org.jbehave.core.model.Story;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

import com.inthinc.pro.automation.jbehave.AutoAnnotationBuilder;
import com.inthinc.pro.automation.jbehave.AutoStoryReporter;

/**
 * This class provides the abstract methods that<br />
 * we are going to require in order to qualify<br />
 * as a JBehave test. This class should only be<br />
 * changed if we need to alter how the JUnit test<br />
 * is run, or how we want JBehave to work.<br />
 * 
 * @author dtanner
 * 
 */
public abstract class JBehaveTest extends AnnotatedPathRunner {

    private final AnnotationBuilder annotationBuilder;
    private final StoryNameResolver nameResolver;
    private final List<String> paths;
    private Description storyDescription;

    public JBehaveTest(Class<?> annotatedClass) throws InitializationError {
        super(annotatedClass);
        this.annotationBuilder = annotationBuilder();
        this.nameResolver = storyNameResolver();
        this.paths = annotationBuilder.findPaths();
    }

    @Override
    public AnnotationBuilder annotationBuilder() {
        if (annotationBuilder != null) {
            return annotationBuilder;
        }
        return new AutoAnnotationBuilder(testClass(), getTest(), getUri());
    }

    @Override
    public void run(RunNotifier notifier) {
        AutoStoryReporter.registerRunNotifier(notifier);
        notifier.fireTestStarted(getDescription());
        Embedder embedder = annotationBuilder.buildEmbedder();
        embedder.runStoriesAsPaths(paths);
        
    }

    /**
     * Method to return Test, or RallyTest for JUnit before and after methods.
     * 
     * @return
     */
    protected abstract Test getTest();

    /**
     * Method to return the corrected address for story paths
     * 
     * @return
     */
    protected abstract String getUri();

    public String getUri(String uri) {
        try {
            URLCodec urlc = new URLCodec();
            return urlc.decode(urlc.decode(uri));
        } catch (DecoderException e) {
            throw new NullPointerException("Unable to parse uri: " + uri);
        }
    }

    @Override
    public Description getDescription() {
        if (storyDescription != null) {
            return storyDescription;
        }
        storyDescription = Description.createSuiteDescription(testClass());
        for (String path : paths)
            storyDescription.addChild(createDescriptionForPath(path));
        AutoStoryReporter.registerDescription(storyDescription);
        return storyDescription;
    }

    private Description createDescriptionForPath(String path) {
        String name = nameResolver.resolveName(path);
        Description story = Description.createSuiteDescription(name);
        createDescriptionForScenarios(story, path);
        return story;
    }

    private void createDescriptionForScenarios(Description story, String path) {
        Story details = annotationBuilder().buildEmbedder().storyManager().storyOfPath(path);
        List<Scenario> scenarios = details.getScenarios();
        for (Scenario scenario : scenarios) {
            Description desc = Description.createSuiteDescription(scenario.getTitle());
            createDescriptionForSteps(desc, scenario);
            story.addChild(desc);
        }
        return;
    }

    private void createDescriptionForSteps(Description scenarioDesc, Scenario scenario) {
        List<Map<String, String>> table = scenario.getExamplesTable().getRows();
        if (table.isEmpty()) {
            for (String step : scenario.getSteps()) {
                scenarioDesc.addChild(Description.createSuiteDescription(step));
            }
        } else {
            for (int i = 0; i < table.size(); i++) {
                Description ex = Description.createSuiteDescription("Example #" + i);
                Map<String, String> example = table.get(i);
                for (String step : scenario.getSteps()) {
                    for (Map.Entry<String, String> entry : example.entrySet()) {
                        step.replace(entry.getKey(), entry.getValue());
                    }
                    Description line = Description.createSuiteDescription(step);
                    ex.addChild(line);
                }
                scenarioDesc.addChild(ex);
            }
        }
    }
}
