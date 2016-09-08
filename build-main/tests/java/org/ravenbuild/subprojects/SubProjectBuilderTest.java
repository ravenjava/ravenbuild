package org.ravenbuild.subprojects;

import org.junit.Test;
import org.ravenbuild.BuildOptions;
import org.ravenbuild.classpath.FastClasspathClasspathScanner;
import org.ravenbuild.config.BuildConfiguration;
import org.ravenbuild.plugins.PluginSystem;
import org.ravenbuild.tasks.TaskGraph;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SubProjectBuilderTest {
	@Test
	public void loadsPluginsBeforeRunningTask() {
		PluginSystem pluginSystem = mock(PluginSystem.class);
		SubProjectBuilder builder = new SubProjectBuilder(mock(BuildOptions.class), mock(BuildConfiguration.class), mock(TaskGraph.class), mock(FastClasspathClasspathScanner.class), pluginSystem);
		
		builder.run("task", Collections.emptyMap());
		
		verify(pluginSystem).loadPlugins(any(BuildConfiguration.class));
	}
	
	@Test
	public void runsTaskInTaskGraph() {
		TaskGraph taskGraph = mock(TaskGraph.class);
		SubProjectBuilder builder = new SubProjectBuilder(mock(BuildOptions.class), mock(BuildConfiguration.class), taskGraph, mock(FastClasspathClasspathScanner.class), mock(PluginSystem.class));
		
		builder.run("task", Collections.emptyMap());
		
		verify(taskGraph).run(eq("task"), any(Map.class));
	}
}