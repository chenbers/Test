package com.inthinc.pro.selenium.testSuites;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.LoadFromRelativeFile;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InstanceStepsFactory;

import com.google.common.util.concurrent.MoreExecutors;

public abstract class WebStories extends JUnitStories{

	

	// The uri string provides the location to start looking for stories from.
	private static final String uri = codeLocationFromClass(WebStories.class).getFile().replace("%2520", " ") + "/stories";
	
	public WebStories(){
        configuredEmbedder().useExecutorService(MoreExecutors.sameThreadExecutor());
    }

//	Here we specify the configuration, starting from default
//	MostUsefulConfiguration, and changing only what is needed
	@Override
	public Configuration configuration() {
		try {
			return new MostUsefulConfiguration()
				// where to find the stories
				.useStoryLoader(new LoadFromRelativeFile(new File(uri).toURI().toURL()))
				// CONSOLE and TXT reporting
				.useStoryReporterBuilder(
						new StoryReporterBuilder()
								.withDefaultFormats()
								.withFormats(Format.CONSOLE, Format.TXT, Format.XML, Format.HTML_TEMPLATE, Format.HTML))
				//.useStepMonitor(new PrintStreamStepMonitor()) // default is SilentStepMonitor()
				//.doDryRun(true)//helpful when generating new steps' methods
				;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected abstract List<String> storyPaths() ;
	
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
	
	
//	Here we specify the steps classes
	@Override
	public abstract List<CandidateSteps> candidateSteps();
		
		
	/**
	 * This method provides the different steps objects<br />
	 * for the story to run.  By default it will always<br />
	 * include the ConfiguratorRallyTest so that things<br />
	 * can be recorded in Rally, and it provides the <br />
	 * Test before and after methods.
	 * 
	 * @param steps
	 * @return
	 */
	public List<CandidateSteps> candidateSteps(Object ...steps){
		List<Object> total = new ArrayList<Object>();
		total.addAll(asList(steps));
		total.add(new WebRallyTest());
		return new InstanceStepsFactory(configuration(), total).createCandidateSteps();
	}
}
