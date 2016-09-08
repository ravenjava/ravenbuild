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
import static org.mockito.Mockito.*;

public class SubProjectBuilderTest {
	@Test
	public void loadsPluginsBeforeRunningTask() {
		PluginSystem pluginSystem = mock(PluginSystem.class);
		SubProjectBuilder builder = new SubProjectBuilder(mock(BuildOptions.class), mock(BuildConfiguration.class), mock(TaskGraph.class), mock(FastClasspathClasspathScanner.class), pluginSystem, mock(SubProjectsFactory.class, RETURNS_DEEP_STUBS));
		
		builder.run("task", Collections.emptyMap());
		
		verify(pluginSystem).loadPlugins(any(BuildConfiguration.class));
	}
	
	@Test
	public void runsTaskInTaskGraph() {
		TaskGraph taskGraph = mock(TaskGraph.class);
		SubProjectBuilder builder = new SubProjectBuilder(mock(BuildOptions.class), mock(BuildConfiguration.class), taskGraph, mock(FastClasspathClasspathScanner.class), mock(PluginSystem.class), mock(SubProjectsFactory.class, RETURNS_DEEP_STUBS));
		
		builder.run("task", Collections.emptyMap());
		
		verify(taskGraph).run(eq("task"), any(Map.class));
	}
	
	@Test
	public void configuresSubProjectsBasedOnRavenConfig() {
		BuildConfiguration buildConfiguration = mock(BuildConfiguration.class);
		Map subProjectsConfig = mock(Map.class);
		when(buildConfiguration.getConfigurationFor("subprojects", Map.class)).thenReturn(subProjectsConfig);
		SubProjectsFactory subProjectsFactory = mock(SubProjectsFactory.class);
		SubProjects subProjects = mock(SubProjects.class);
		when(subProjectsFactory.createSubProjects()).thenReturn(subProjects);
		SubProjectBuilder builder = new SubProjectBuilder(mock(BuildOptions.class), buildConfiguration, mock(TaskGraph.class), mock(FastClasspathClasspathScanner.class), mock(PluginSystem.class), subProjectsFactory);
		
		builder.run("task", Collections.emptyMap());
		
		verify(subProjects).load(subProjectsConfig);
	}
	
	@Test
	public void runsTaskInAllSubProjectsIfTaskIsReentrant() {
		BuildConfiguration buildConfiguration = mock(BuildConfiguration.class);
		Map subProjectsConfig = mock(Map.class);
		when(buildConfiguration.getConfigurationFor("subprojects", Map.class)).thenReturn(subProjectsConfig);
		SubProjectsFactory subProjectsFactory = mock(SubProjectsFactory.class);
		SubProjects subProjects = mock(SubProjects.class);
		when(subProjectsFactory.createSubProjects()).thenReturn(subProjects);
		SubProjectBuilder builder = new SubProjectBuilder(mock(BuildOptions.class), buildConfiguration, mock(TaskGraph.class), mock(FastClasspathClasspathScanner.class), mock(PluginSystem.class), subProjectsFactory);
		
		builder.run("task", Collections.emptyMap());
		
		verify(subProjects).runInAll(eq("task"), any(Map.class));
	}
}