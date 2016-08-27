package org.ravenbuild;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeThat;

public class BuildOptionsTest {
	@Test
	public void usesDefaultBuildOptionsWhenNoCommandLineArgsAreSupplied() {
		BuildOptions buildOptions = BuildOptions.parseFrom(new String[] {});
		
		assertThat(buildOptions, is(BuildOptions.DEFAULT));
	}
	
	@Test
	public void setsTaskCorrectlyIfOneParameterIsGiven() {
		BuildOptions buildOptions = BuildOptions.parseFrom(new String[] { "help" });
		
		assertThat(buildOptions.task(), is("help"));
	}
	
	@Test
	public void recognizesSimpleStringTaskOption() {
		BuildOptions buildOptions = BuildOptions.parseFrom(new String[] { "taskName", "foo=bar" });
		
		assumeThat(buildOptions.task(), is("taskName"));
		assertThat(buildOptions.taskOptions(), hasEntry("foo", "bar"));
	}
	
	@Test
	public void recognizesSimpleBooleanTaskOption() {
		BuildOptions buildOptions = BuildOptions.parseFrom(new String[] { "taskName", "foo" });
		
		assumeThat(buildOptions.task(), is("taskName"));
		assertThat(buildOptions.taskOptions(), hasEntry("foo", "true"));
	}
	
	@Test
	public void recognizesMultipleTaskOptions() {
		BuildOptions buildOptions = BuildOptions.parseFrom(new String[] { "taskName", "foo", "bar=baz" });
		
		assumeThat(buildOptions.task(), is("taskName"));
		assertThat(buildOptions.taskOptions(), hasEntry("foo", "true"));
		assertThat(buildOptions.taskOptions(), hasEntry("bar", "baz"));
	}
}
