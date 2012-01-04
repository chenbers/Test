package com.inthinc.pro.selenium.testSuites;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;

import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.LoadFromRelativeFile;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.io.LoadFromRelativeFile.StoryFilePath;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InstanceStepsFactory;

import com.google.common.util.concurrent.MoreExecutors;
import com.inthinc.pro.selenium.steps.AdminVehicleSteps;
import com.inthinc.pro.selenium.steps.SearchCrashesSteps;


public class SearchCrashesStory extends JUnitStories {

    public SearchCrashesStory() {
        configuredEmbedder().useExecutorService(MoreExecutors.sameThreadExecutor());
    }

     //Here we specify the configuration, starting from default
	 //MostUsefulConfiguration, and changing only what is needed
	@Override
	public Configuration configuration() {
		return new MostUsefulConfiguration()
			// where to find the stories
			.useStoryLoader(new LoadFromRelativeFile(codeLocationFromClass(this.getClass()), new StoryFilePath("/target/test-classes","/src/test/resources/stories")))
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
		return new InstanceStepsFactory(configuration(), new SearchCrashesSteps())
				.createCandidateSteps();
	}

	@Override
	protected List<String> storyPaths() {
		System.out.println("storyFilter: "+System.getProperty("storyFilter", "*") );
		System.out.println("codeLocationFromClass: "+codeLocationFromClass(this.getClass()).getFile());
		List<String> storyPaths = new StoryFinder().findPaths(codeLocationFromClass(this.getClass()).getFile()+"../../src/test/resources/stories/", asList("**/"+ System.getProperty("storyFilter", "*") + "SearchCrashes.story"), null);
		for(String path: storyPaths)
		    System.out.println("path: "+path);
		return storyPaths;
	}
}
