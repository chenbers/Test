package com.inthinc.pro.selenium.testSuites;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
//  import static org.jbehave.core.reporters.Format.CONSOLE;
//import static org.jbehave.web.selenium.WebDriverHtmlOutput.WEB_DRIVER_HTML;

import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
//import org.jbehave.core.embedder.StoryControls;
//import org.jbehave.core.failures.FailingUponPendingStep;
//import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.LoadFromRelativeFile;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.io.LoadFromRelativeFile.StoryFilePath;
import org.jbehave.core.junit.JUnitStories;
//import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InstanceStepsFactory;
//import org.jbehave.core.steps.PrintStreamStepMonitor;
//import org.jbehave.web.selenium.ContextView;
//import org.jbehave.web.selenium.LocalFrameContextView;
//import org.jbehave.web.selenium.SeleniumConfiguration;
//import org.jbehave.web.selenium.SeleniumContext;
//import org.jbehave.web.selenium.SeleniumContextOutput;
//import org.jbehave.web.selenium.SeleniumStepMonitor;
//import org.jbehave.core.steps.spring.SpringStepsFactory;

import com.inthinc.pro.selenium.steps.AdminUserSteps;
import com.inthinc.pro.selenium.steps.LoginSteps;
import com.google.common.util.concurrent.MoreExecutors;


public class AdminUserStory extends JUnitStories {

    public AdminUserStory(){
        configuredEmbedder().useExecutorService(MoreExecutors.sameThreadExecutor());
    }

     //Here we specify the configuration, starting from default
	 //MostUsefulConfiguration, and changing only what is needed
	@Override
	public Configuration configuration() {
		return new MostUsefulConfiguration()
			// where to find the stories
			.useStoryLoader(new LoadFromRelativeFile(codeLocationFromClass(this.getClass()), new StoryFilePath("/target/test-classes/stories","/src/test/resources/stories")))
			// CONSOLE and TXT reporting
			.useStoryReporterBuilder(
					new StoryReporterBuilder()
							.withDefaultFormats()
							.withFormats(Format.CONSOLE, Format.TXT, Format.XML, Format.HTML_TEMPLATE, Format.HTML))
			//.useStepMonitor(new PrintStreamStepMonitor()) // default is SilentStepMonitor()
			//.doDryRun(true)//helpful when generating new steps' methods
			;
	}

	// Here we specify the steps classes
	@Override
	public List<CandidateSteps> candidateSteps() {
		// varargs, can have more that one steps classes
		return new InstanceStepsFactory(configuration(), new AdminUserSteps())
				.createCandidateSteps();
	}

	@Override
	protected List<String> storyPaths() {
		System.out.println("storyFilter: "+System.getProperty("storyFilter", "*") );
		System.out.println("codeLocationFromClass: "+codeLocationFromClass(this.getClass()).getFile());
		List<String> storyPaths = new StoryFinder().findPaths(codeLocationFromClass(this.getClass()).getFile()+"../../src/test/resources/stories/", asList("**/"+ System.getProperty("storyFilter", "*") + "AdminUser.story"), null);
		for(String path: storyPaths)
		    System.out.println("path: "+path);
		return storyPaths;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// public LoginStory() {
//  CrossReference crossReference = new CrossReference().withJsonOnly()
//          .withOutputAfterEachStory(true)
//          .excludingStoriesWithNoExecutedScenarios(true);
//  ContextView contextView = new LocalFrameContextView().sized(640, 120);
//  SeleniumContext seleniumContext = new SeleniumContext();
//  SeleniumStepMonitor stepMonitor = new SeleniumStepMonitor(contextView,
//          seleniumContext, crossReference.getStepMonitor());
//  Format[] formats = new Format[] { new SeleniumContextOutput(seleniumContext), CONSOLE, WEB_DRIVER_HTML };
//  StoryReporterBuilder reporterBuilder = new StoryReporterBuilder()
//          .withCodeLocation(codeLocationFromClass(LoginStory.class))
//          .withFailureTrace(true).withFailureTraceCompression(true)
//          .withDefaultFormats().withFormats(formats)
//          .withCrossReference(crossReference);
//
//  Configuration configuration = new SeleniumConfiguration()
//          .useSeleniumContext(seleniumContext).useFailureStrategy(
//                  new FailingUponPendingStep()).useStoryControls(
//                  new StoryControls().doResetStateBeforeScenario(false))
//          .useStepMonitor(stepMonitor).useStoryLoader(
//                  new LoadFromClasspath(LoginStory.class))
//          .useStoryReporterBuilder(reporterBuilder);
//  useConfiguration(configuration);
//
//  // ApplicationContext context = new SpringApplicationContextFactory("etsy-steps.xml").createApplicationContext();
//  // useStepsFactory(new SpringStepsFactory(configuration, context));
//}
}
