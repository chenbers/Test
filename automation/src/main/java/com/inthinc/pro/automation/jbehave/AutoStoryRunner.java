package com.inthinc.pro.automation.jbehave;

import static org.codehaus.plexus.util.StringUtils.capitalizeFirstLetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.jbehave.core.annotations.ScenarioType;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.MetaFilter;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.embedder.StoryRunner;
import org.jbehave.core.failures.FailureStrategy;
import org.jbehave.core.failures.PendingStepFound;
import org.jbehave.core.failures.PendingStepStrategy;
import org.jbehave.core.failures.RestartingScenarioFailure;
import org.jbehave.core.failures.UUIDExceptionWrapper;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.model.GivenStories;
import org.jbehave.core.model.GivenStory;
import org.jbehave.core.model.Meta;
import org.jbehave.core.model.Scenario;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.ConcurrentStoryReporter;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.PendingStepMethodGenerator;
import org.jbehave.core.steps.ProvidedStepsFactory;
import org.jbehave.core.steps.Step;
import org.jbehave.core.steps.StepCollector.Stage;
import org.jbehave.core.steps.StepCreator.PendingStep;
import org.jbehave.core.steps.StepResult;

import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.selenium.AbstractPage;

public class AutoStoryRunner extends StoryRunner {
    
    private final AutoPageRunner page;
    
    public AutoStoryRunner(List<AbstractPage> pages) {
        page = new AutoPageRunner(pages);
    }
    


    private ThreadLocal<FailureStrategy> currentStrategy = new ThreadLocal<FailureStrategy>();
    private ThreadLocal<FailureStrategy> failureStrategy = new ThreadLocal<FailureStrategy>();
    private ThreadLocal<PendingStepStrategy> pendingStepStrategy = new ThreadLocal<PendingStepStrategy>();
    private ThreadLocal<UUIDExceptionWrapper> storyFailure = new ThreadLocal<UUIDExceptionWrapper>();
    private ThreadLocal<StoryReporter> reporter = new ThreadLocal<StoryReporter>();
    private ThreadLocal<String> reporterStoryPath = new ThreadLocal<String>();
    private ThreadLocal<State> storiesState = new ThreadLocal<State>();

    /**
     * Run steps before or after a collection of stories. Steps are execute only
     * <b>once</b> per collection of stories.
     * 
     * @param configuration the Configuration used to find the steps to run
     * @param candidateSteps the List of CandidateSteps containing the candidate
     *            steps methods
     * @param stage the Stage
     * @return The State after running the steps
     */
    public State runBeforeOrAfterStories(Configuration configuration, List<CandidateSteps> candidateSteps, Stage stage) {
        String storyPath = capitalizeFirstLetter(stage.name().toLowerCase()) + "Stories";
        reporter.set(configuration.storyReporter(storyPath));
        reporter.get().beforeStory(new Story(storyPath), false);
        RunContext context = new RunContext(configuration, candidateSteps, storyPath, MetaFilter.EMPTY);
        if (stage == Stage.BEFORE) {
            resetStoryFailure(context);
        }
        if (stage == Stage.AFTER && storiesState.get() != null) {
            context.stateIs(storiesState.get());
        }
        runStepsWhileKeepingState(context,
                configuration.stepCollector().collectBeforeOrAfterStoriesSteps(context.candidateSteps(), stage));
        reporter.get().afterStory(false);
        storiesState.set(context.state());
        // handle any after stories failure according to strategy
        if (stage == Stage.AFTER) {
            try {
                handleStoryFailureByStrategy();
            } catch (Throwable e) {
                return new SomethingHappened(storyFailure.get());
            } finally {
                if (reporter.get() instanceof ConcurrentStoryReporter) {
                    ((ConcurrentStoryReporter) reporter.get()).invokeDelayed();
                }
            }
        }
        return context.state();
    }

    /**
     * Runs a Story with the given configuration and steps.
     * 
     * @param configuration the Configuration used to run story
     * @param candidateSteps the List of CandidateSteps containing the candidate
     *            steps methods
     * @param story the Story to run
     * @throws Throwable if failures occurred and FailureStrategy dictates it to
     *             be re-thrown.
     */
    public void run(Configuration configuration, List<CandidateSteps> candidateSteps, Story story) throws Throwable {
        run(configuration, candidateSteps, story, MetaFilter.EMPTY);
    }

    /**
     * Runs a Story with the given configuration and steps, applying the given
     * meta filter.
     * 
     * @param configuration the Configuration used to run story
     * @param candidateSteps the List of CandidateSteps containing the candidate
     *            steps methods
     * @param story the Story to run
     * @param filter the Filter to apply to the story Meta
     * @throws Throwable if failures occurred and FailureStrategy dictates it to
     *             be re-thrown.
     */
    public void run(Configuration configuration, List<CandidateSteps> candidateSteps, Story story, MetaFilter filter)
            throws Throwable {
        run(configuration, candidateSteps, story, filter, null);
    }

    /**
     * Runs a Story with the given configuration and steps, applying the given
     * meta filter, and staring from given state.
     * 
     * @param configuration the Configuration used to run story
     * @param candidateSteps the List of CandidateSteps containing the candidate
     *            steps methods
     * @param story the Story to run
     * @param filter the Filter to apply to the story Meta
     * @param beforeStories the State before running any of the stories, if not
     *            <code>null</code>
     * @throws Throwable if failures occurred and FailureStrategy dictates it to
     *             be re-thrown.
     */
    public void run(Configuration configuration, List<CandidateSteps> candidateSteps, Story story, MetaFilter filter,
            State beforeStories) throws Throwable {
        run(configuration, new ProvidedStepsFactory(candidateSteps), story, filter, beforeStories);
    }

    /**
     * Runs a Story with the given steps factory, applying the given meta
     * filter, and staring from given state.
     * 
     * @param configuration the Configuration used to run story
     * @param stepsFactory the InjectableStepsFactory used to created the
     *            candidate steps methods
     * @param story the Story to run
     * @param filter the Filter to apply to the story Meta
     * @param beforeStories the State before running any of the stories, if not
     *            <code>null</code>
     * 
     * @throws Throwable if failures occurred and FailureStrategy dictates it to
     *             be re-thrown.
     */
    public void run(Configuration configuration, InjectableStepsFactory stepsFactory, Story story, MetaFilter filter,
            State beforeStories) throws Throwable {
        RunContext context = new RunContext(configuration, stepsFactory, story.getPath(), filter);
        if (beforeStories != null) {
            context.stateIs(beforeStories);
        }
        Map<String, String> storyParameters = new HashMap<String, String>();
        run(context, story, storyParameters);
    }

    /**
     * Returns the parsed story from the given path
     * 
     * @param configuration the Configuration used to run story
     * @param storyPath the story path
     * @return The parsed Story
     */
    public Story storyOfPath(Configuration configuration, String storyPath) {
        String storyAsText = configuration.storyLoader().loadStoryAsText(storyPath);
        return configuration.storyParser().parseStory(storyAsText, storyPath);
    }

    private void run(RunContext context, Story story, Map<String, String> storyParameters) throws Throwable {
        try {
            runIt(context, story, storyParameters);
        } catch (InterruptedException interruptedException) {
            reporter.get().cancelled();
            throw interruptedException;
        }
    }

    private void runIt(RunContext context, Story story, Map<String, String> storyParameters) throws Throwable {
        if (!context.givenStory) {
            reporter.set(reporterFor(context, story));
        }
        pendingStepStrategy.set(context.configuration().pendingStepStrategy());
        failureStrategy.set(context.configuration().failureStrategy());

        try {
            resetStoryFailure(context);

            if (context.dryRun()) {
                reporter.get().dryRun();
            }

            if (context.configuration().storyControls().resetStateBeforeStory()) {
                context.resetState();
            }

            // run before story steps, if any
            reporter.get().beforeStory(story, context.givenStory());

            boolean storyAllowed = true;

            FilterContext filterContext = context.filter(story);
            Meta storyMeta = story.getMeta();
            if (!filterContext.allowed()) {
                reporter.get().storyNotAllowed(story, context.metaFilterAsString());
                storyAllowed = false;
            }

            if (storyAllowed) {

                reporter.get().narrative(story.getNarrative());

                runBeforeOrAfterStorySteps(context, story, Stage.BEFORE);

                // determine if before and after scenario steps should be run
                boolean runBeforeAndAfterScenarioSteps = shouldRunBeforeOrAfterScenarioSteps(context);

                for (Scenario scenario : story.getScenarios()) {
                    // scenario also inherits meta from story
                    boolean scenarioAllowed = true;
                    if (failureOccurred(context) && context.configuration().storyControls().skipScenariosAfterFailure()) {
                        continue;
                    }
                    reporter.get().beforeScenario(scenario.getTitle());
                    reporter.get().scenarioMeta(scenario.getMeta());

                    if ( !filterContext.allowed(scenario) ) {
                        reporter.get().scenarioNotAllowed(scenario, context.metaFilterAsString());
                        scenarioAllowed = false;
                    }

                    if (scenarioAllowed) {
                        if (context.configuration().storyControls().resetStateBeforeScenario()) {
                            context.resetState();
                        }
                        Meta storyAndScenarioMeta = scenario.getMeta().inheritFrom(storyMeta);
                        // run before scenario steps, if allowed
                        if (runBeforeAndAfterScenarioSteps) {
                            runBeforeOrAfterScenarioSteps(context, scenario, storyAndScenarioMeta, Stage.BEFORE,
                                    ScenarioType.NORMAL);
                        }

                        // run given stories, if any
                        runGivenStories(scenario, context);
                        if (isParameterisedByExamples(scenario)) {
                            // run parametrised scenarios by examples
                            runParametrisedScenariosByExamples(context, scenario, storyAndScenarioMeta);
                        } else { // run as plain old scenario
                            addMetaParameters(storyParameters, storyAndScenarioMeta);
                            runScenarioSteps(context, scenario, storyParameters);
                        }

                        // run after scenario steps, if allowed
                        if (runBeforeAndAfterScenarioSteps) {
                            runBeforeOrAfterScenarioSteps(context, scenario, storyAndScenarioMeta, Stage.AFTER,
                                    ScenarioType.NORMAL);
                        }

                    }

                    reporter.get().afterScenario();
                }

                // run after story steps, if any
                runBeforeOrAfterStorySteps(context, story, Stage.AFTER);

            }

            reporter.get().afterStory(context.givenStory());

            // handle any failure according to strategy
            if (!context.givenStory()) {
                handleStoryFailureByStrategy();
            }
        } catch (Exception e){ 
            Log.error(e);
        } finally {
            if (!context.givenStory() && reporter.get() instanceof ConcurrentStoryReporter) {
                ((ConcurrentStoryReporter) reporter.get()).invokeDelayed();
            }
        }
    }

    private void addMetaParameters(Map<String, String> storyParameters, Meta meta) {
        for (String name : meta.getPropertyNames()) {
            storyParameters.put(name, meta.getProperty(name));
        }
    }

    private boolean shouldRunBeforeOrAfterScenarioSteps(RunContext context) {
        Configuration configuration = context.configuration();
        if (!configuration.storyControls().skipBeforeAndAfterScenarioStepsIfGivenStory()) {
            return true;
        }

        return !context.givenStory();
    }

    private boolean failureOccurred(RunContext context) {
        return context.failureOccurred();
    }

    private StoryReporter reporterFor(RunContext context, Story story) {
        Configuration configuration = context.configuration();
        if (context.givenStory()) {
            return configuration.storyReporter(reporterStoryPath.get());
        } else {
            // store parent story path for reporting
            reporterStoryPath.set(story.getPath());
            return configuration.storyReporter(reporterStoryPath.get());
        }
    }

    private void handleStoryFailureByStrategy() throws Throwable {
        Throwable throwable = storyFailure.get();
        if (throwable != null) {
            currentStrategy.get().handleFailure(throwable);
        }
    }

    private void resetStoryFailure(RunContext context) {
        if (context.givenStory()) {
            // do not reset failure for given stories
            return;
        }
        currentStrategy.set(context.configuration().failureStrategy());
        storyFailure.set(null);
    }

    private void runGivenStories(Scenario scenario, RunContext context) throws Throwable {
        GivenStories givenStories = scenario.getGivenStories();
        if (givenStories.getPaths().size() > 0) {
            reporter.get().givenStories(givenStories);
            for (GivenStory givenStory : givenStories.getStories()) {
                RunContext childContext = context.childContextFor(givenStory);
                // run given story, using any parameters if provided
                Story story = storyOfPath(context.configuration(), childContext.path());
                run(childContext, story, givenStory.getParameters());
            }
        }
    }

    private boolean isParameterisedByExamples(Scenario scenario) {
        return scenario.getExamplesTable().getRowCount() > 0 && !scenario.getGivenStories().requireParameters();
    }

    private void runParametrisedScenariosByExamples(RunContext context, Scenario scenario, Meta storyAndScenarioMeta) {
        ExamplesTable table = scenario.getExamplesTable();
        reporter.get().beforeExamples(scenario.getSteps(), table);
        for (Map<String, String> scenarioParameters : table.getRows()) {
            reporter.get().example(scenarioParameters);
            if (context.configuration().storyControls().resetStateBeforeScenario()) {
                context.resetState();
            }
            runBeforeOrAfterScenarioSteps(context, scenario, storyAndScenarioMeta, Stage.BEFORE, ScenarioType.EXAMPLE);
            runScenarioSteps(context, scenario, scenarioParameters);
            runBeforeOrAfterScenarioSteps(context, scenario, storyAndScenarioMeta, Stage.AFTER, ScenarioType.EXAMPLE);
        }
        reporter.get().afterExamples();
    }

    private void runBeforeOrAfterStorySteps(RunContext context, Story story, Stage stage) {
        runStepsWhileKeepingState(context, context.collectBeforeOrAfterStorySteps(story, stage));
    }

    private void runBeforeOrAfterScenarioSteps(RunContext context, Scenario scenario, Meta storyAndScenarioMeta,
            Stage stage, ScenarioType type) {
        runStepsWhileKeepingState(context, context.collectBeforeOrAfterScenarioSteps(storyAndScenarioMeta, stage, type));
    }

    private void runScenarioSteps(RunContext context, Scenario scenario, Map<String, String> scenarioParameters) {
        boolean restart = true;
        while (restart) {
            restart = false;
            List<Step> steps = context.collectScenarioSteps(scenario, scenarioParameters);
            try {
                runStepsWhileKeepingState(context, steps);
            } catch (RestartingScenarioFailure e) {
                restart = true;
                continue;
            }
            generatePendingStepMethods(context, steps);
        }
    }

    private void generatePendingStepMethods(RunContext context, List<Step> steps) {
        List<PendingStep> pendingSteps = new ArrayList<PendingStep>();
        for (Step step : steps) {
            if (step instanceof PendingStep) {
                pendingSteps.add((PendingStep) step);
            }
        }
        if (!pendingSteps.isEmpty()) {
            PendingStepMethodGenerator generator = new PendingStepMethodGenerator(context.configuration().keywords());
            List<String> methods = new ArrayList<String>();
            for (PendingStep pendingStep : pendingSteps) {
                if (!pendingStep.annotated()) {
                    methods.add(generator.generateMethod(pendingStep));
                }
            }
            reporter.get().pendingMethods(methods);
        }
    }

    private void runStepsWhileKeepingState(RunContext context, List<Step> steps) {
        if (steps == null || steps.size() == 0) {
            return;
        }
        State state = context.state();
        Stack<Step> stepsToRemove = new Stack<Step>();
        List<Step> stepsToAdd = new ArrayList<Step>();
        for (Step step : steps) {
            try {
                if (step instanceof PendingStep){
                    stepsToRemove.addElement(step);
                    step = page.tryStep((PendingStep) step);
                    if (!(step instanceof PendingStep)){
                        stepsToAdd.add(step);
                    } else {
                        stepsToRemove.pop();
                    }
                }
                state = state.run(step);
            } catch (RestartingScenarioFailure e) {
                reporter.get().restarted(step.toString(), e);
                throw e;
            }
        }
        steps.removeAll(stepsToRemove);
        context.stateIs(state);
    }

    private final class FineSoFar implements State {

        public State run(Step step) {
            UUIDExceptionWrapper storyFailureIfItHappened = storyFailure.get();
            StepResult result = step.perform(storyFailureIfItHappened);
            result.describeTo(reporter.get());
            UUIDExceptionWrapper stepFailure = result.getFailure();
            if (stepFailure == null) {
                return this;
            }

            storyFailure.set(mostImportantOf(storyFailureIfItHappened, stepFailure));
            currentStrategy.set(strategyFor(storyFailure.get()));
            return new SomethingHappened(stepFailure);
        }

        private UUIDExceptionWrapper mostImportantOf(UUIDExceptionWrapper failure1, UUIDExceptionWrapper failure2) {
            return failure1 == null ? failure2
                    : failure1.getCause() instanceof PendingStepFound ? (failure2 == null ? failure1 : failure2)
                            : failure1;
        }

        private FailureStrategy strategyFor(Throwable failure) {
            if (failure instanceof PendingStepFound) {
                return pendingStepStrategy.get();
            } else {
                return failureStrategy.get();
            }
        }
    }

    private final class SomethingHappened implements State {
        UUIDExceptionWrapper scenarioFailure;

        public SomethingHappened(UUIDExceptionWrapper scenarioFailure) {
            this.scenarioFailure = scenarioFailure;
        }

        public State run(Step step) {
            StepResult result = step.doNotPerform(scenarioFailure);
            result.describeTo(reporter.get());
            return this;
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    /**
     * The context for running a story.
     */
    private class RunContext {
        private final Configuration configuration;
        private final List<CandidateSteps> candidateSteps;
        private final String path;
        private final MetaFilter filter;
        private final boolean givenStory;
        private State state;

        public RunContext(Configuration configuration, InjectableStepsFactory stepsFactory, String path,
                MetaFilter filter) {
            this(configuration, stepsFactory.createCandidateSteps(), path, filter);
        }

        public RunContext(Configuration configuration, List<CandidateSteps> steps, String path, MetaFilter filter) {
            this(configuration, steps, path, filter, false);
        }

        private RunContext(Configuration configuration, List<CandidateSteps> steps, String path, MetaFilter filter,
                boolean givenStory) {
            this.configuration = configuration;
            this.candidateSteps = steps;
            this.path = path;
            this.filter = filter;
            this.givenStory = givenStory;
            resetState();
        }

        public boolean dryRun() {
            return configuration.storyControls().dryRun();
        }

        public Configuration configuration() {
            return configuration;
        }

        public List<CandidateSteps> candidateSteps() {
            return candidateSteps;
        }

        public boolean givenStory() {
            return givenStory;
        }

        public String path() {
            return path;
        }

        public FilterContext filter(Story story) {
            return new FilterContext(filter, story, configuration.storyControls());
        }

        public String metaFilterAsString() {
            return filter.asString();
        }

        public List<Step> collectBeforeOrAfterStorySteps(Story story, Stage stage) {
            return configuration.stepCollector().collectBeforeOrAfterStorySteps(candidateSteps, story, stage,
                    givenStory);
        }

        public List<Step> collectBeforeOrAfterScenarioSteps(Meta storyAndScenarioMeta, Stage stage, ScenarioType type) {
            return configuration.stepCollector().collectBeforeOrAfterScenarioSteps(candidateSteps,
                    storyAndScenarioMeta, stage, type);
        }

        public List<Step> collectScenarioSteps(Scenario scenario, Map<String, String> parameters) {
            return configuration.stepCollector().collectScenarioSteps(candidateSteps, scenario, parameters);
        }

        public RunContext childContextFor(GivenStory givenStory) {
            String actualPath = configuration.pathCalculator().calculate(path, givenStory.getPath());
            return new RunContext(configuration, candidateSteps, actualPath, filter, true);
        }

        public State state() {
            return state;
        }

        public void stateIs(State state) {
            this.state = state;
        }

        public boolean failureOccurred() {
            return failed(state);
        }

        public void resetState() {
            this.state = new FineSoFar();
        }

    }

    public class FilterContext {

        private boolean storyAllowed;
        private Map<Scenario, Boolean> scenariosAllowed;

        public FilterContext(MetaFilter filter, Story story, StoryControls storyControls) {
            String storyMetaPrefix = storyControls.storyMetaPrefix();
            String scenarioMetaPrefix = storyControls.scenarioMetaPrefix();
            Meta storyMeta = story.getMeta().inheritFrom(story.asMeta(storyMetaPrefix));
            storyAllowed = filter.allow(storyMeta);
            scenariosAllowed = new HashMap<Scenario, Boolean>();
            for (Scenario scenario : story.getScenarios()) {
                Meta scenarioMeta = scenario.getMeta().inheritFrom(scenario.asMeta(scenarioMetaPrefix).inheritFrom(storyMeta));
                boolean scenarioAllowed = filter.allow(scenarioMeta);
                scenariosAllowed.put(scenario, scenarioAllowed);
            }
        }

        public boolean allowed() {
            return storyAllowed || scenariosAllowed.values().contains(true);
        }

        public boolean allowed(Scenario scenario){
            return scenariosAllowed.get(scenario);
        }
    }

    public boolean failed(State state) {
        return !state.getClass().equals(FineSoFar.class);
    }

    public Throwable failure(State state) {
        if (failed(state)) {
            return ((SomethingHappened) state).scenarioFailure.getCause();
        }
        return null;
    }

    public void setEmbedder(AutoEmbedder autoEmbedder) {
        page.setEmbedder(autoEmbedder);
    }

}
