package com.emotech.dashboard.rest_tester ;

import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

import com.emotech.dashboard.rest_tester.steps.DuckRestTester;

public class DuckDashboardTester extends JUnitStories {

	@Override
	protected List<String> storyPaths() {
		return new StoryFinder().findPaths("src/test/resources", "**/*.story", "**/excluded*.story");
	}
	
	@Override
	public Configuration configuration() {
	    return new MostUsefulConfiguration().useStoryLoader(new LoadFromClasspath(getClass().getClassLoader())).useStoryReporterBuilder(new StoryReporterBuilder().withFormats( Format.STATS , Format.TXT , Format.CONSOLE ).withFailureTrace( true ) );
	}

	@Override
	public InjectableStepsFactory stepsFactory() {
		return new InstanceStepsFactory(configuration(), new DuckRestTester());
	}
}