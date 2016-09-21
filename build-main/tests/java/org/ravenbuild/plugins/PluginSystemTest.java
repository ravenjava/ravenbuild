package org.ravenbuild.plugins;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.ravenbuild.config.BuildConfiguration;
import org.ravenbuild.environment.BuildEnvironment;
import org.ravenbuild.logging.Logger;
import org.ravenbuild.projectinfo.AllProjects;
import org.ravenbuild.tasks.TaskGraph;
import org.ravenbuild.tasks.TaskRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PluginSystemTest {
	@Test
	public void loadsPluginsUsingClasspathScannerWhenLoadingAllPlugins() {
		final TaskGraph taskgraph = mock(TaskGraph.class);
		final ClasspathScanner classpathScanner = mock(ClasspathScanner.class);
		PluginSystem pluginSystem = new PluginSystem(taskgraph, mock(TaskRepository.class), classpathScanner, mock(AllProjects.class), mock(BuildEnvironment.class), mock(Logger.class));
		
		pluginSystem.loadPlugins(mock(BuildConfiguration.class));
		
		verify(classpathScanner).findAllClassesImplementing(eq(BuildPlugin.class));
	}
	
	@Test
	public void doesNotLoadPluginIfItIsNotSpecifiedInTheBuildConfiguration() {
		BuildPluginMock.pluginInitializedWithContext = null;
		ClasspathScanner classpathScanner = mock(ClasspathScanner.class);
		PluginSystem pluginSystem = new PluginSystem(mock(TaskGraph.class), mock(TaskRepository.class), classpathScanner, mock(AllProjects.class), mock(BuildEnvironment.class), mock(Logger.class));
		
		when(classpathScanner.findAllClassesImplementing(eq(BuildPlugin.class))).thenReturn(Arrays.asList(BuildPluginMock.class));
		pluginSystem.loadPlugins(mock(BuildConfiguration.class));
		
		assertNull(BuildPluginMock.pluginInitializedWithContext);
	}
	
	@Test
	public void initializesPluginsWhenLoadingAllPlugins() {
		BuildPluginMock.pluginInitializedWithContext = null;
		final TaskGraph taskgraph = mock(TaskGraph.class);
		final ClasspathScanner classpathScanner = mock(ClasspathScanner.class);
		when(classpathScanner.findAllClassesImplementing(eq(BuildPlugin.class))).thenReturn(Arrays.asList(BuildPluginMock.class));
		PluginSystem pluginSystem = new PluginSystem(taskgraph, mock(TaskRepository.class), classpathScanner, mock(AllProjects.class), mock(BuildEnvironment.class), mock(Logger.class));
		
		BuildConfiguration buildConfiguration = mock(BuildConfiguration.class);
		when(buildConfiguration.getConfigurationFor("plugins", List.class)).thenReturn(Arrays.asList("org.ravenbuild.MockPlugin"));
		pluginSystem.loadPlugins(buildConfiguration);
		
		assertNotNull(BuildPluginMock.pluginInitializedWithContext);
	}
	
	@Test
	public void hasAListOfAllPluginsAfterLoadingAllPlugins() {
		BuildPluginMock.pluginInitializedWithContext = null;
		final TaskGraph taskgraph = mock(TaskGraph.class);
		final ClasspathScanner classpathScanner = mock(ClasspathScanner.class);
		when(classpathScanner.findAllClassesImplementing(eq(BuildPlugin.class))).thenReturn(Arrays.asList(BuildPluginMock.class));
		PluginSystem pluginSystem = new PluginSystem(taskgraph, mock(TaskRepository.class), classpathScanner, mock(AllProjects.class), mock(BuildEnvironment.class), mock(Logger.class));
		
		BuildConfiguration buildConfiguration = mock(BuildConfiguration.class);
		when(buildConfiguration.getConfigurationFor("plugins", List.class)).thenReturn(Arrays.asList("org.ravenbuild.MockPlugin"));
		pluginSystem.loadPlugins(buildConfiguration);
		
		assertThat(pluginSystem.allPlugins(), contains(instanceOf(BuildPluginMock.class)));
	}
	
	@Test
	public void loadsAndInitializesPluginDependencies() {
		BuildPluginMock.pluginInitializedWithContext = null;
		BuildPluginMock2.pluginInitializedWithContext = null;
		final TaskGraph taskgraph = mock(TaskGraph.class);
		final ClasspathScanner classpathScanner = mock(ClasspathScanner.class);
		when(classpathScanner.findAllClassesImplementing(eq(BuildPlugin.class)))
				.thenReturn(Arrays.asList(BuildPluginMock.class, BuildPluginMock2.class));
		PluginSystem pluginSystem = new PluginSystem(taskgraph, mock(TaskRepository.class), classpathScanner, mock(AllProjects.class), mock(BuildEnvironment.class), mock(Logger.class));
		
		BuildConfiguration buildConfiguration = mock(BuildConfiguration.class);
		when(buildConfiguration.getConfigurationFor("plugins", List.class)).thenReturn(Arrays.asList("org.ravenbuild.MockPlugin2"));
		pluginSystem.loadPlugins(buildConfiguration);
		
		assumeNotNull(BuildPluginMock2.pluginInitializedWithContext);
		assertNotNull(BuildPluginMock.pluginInitializedWithContext);
	}
	
	@Test
	public void initializesDependencyOfMultiplePluginsOnlyOnce() {
		BuildPluginMock.pluginInitializedWithContext = null;
		BuildPluginMock.initCount = 0;
		BuildPluginMock2.pluginInitializedWithContext = null;
		BuildPluginMock3.pluginInitializedWithContext = null;
		final TaskGraph taskgraph = mock(TaskGraph.class);
		final ClasspathScanner classpathScanner = mock(ClasspathScanner.class);
		when(classpathScanner.findAllClassesImplementing(eq(BuildPlugin.class)))
				.thenReturn(Arrays.asList(BuildPluginMock.class, BuildPluginMock2.class, BuildPluginMock3.class));
		PluginSystem pluginSystem = new PluginSystem(taskgraph, mock(TaskRepository.class), classpathScanner, mock(AllProjects.class), mock(BuildEnvironment.class), mock(Logger.class));
		
		BuildConfiguration buildConfiguration = mock(BuildConfiguration.class);
		when(buildConfiguration.getConfigurationFor("plugins", List.class)).thenReturn(Arrays.asList("org.ravenbuild.MockPlugin3"));
		pluginSystem.loadPlugins(buildConfiguration);
		
		assumeNotNull(BuildPluginMock3.pluginInitializedWithContext);
		assumeNotNull(BuildPluginMock2.pluginInitializedWithContext);
		assumeNotNull(BuildPluginMock.pluginInitializedWithContext);
		
		assertThat(BuildPluginMock.initCount, CoreMatchers.is(1));
	}
	
	
	@Test
	@Ignore("FIXME: Should detect cycles in plugin dependencies!")
	public void printsErrorIfCyclicPluginDependenciesAreDetected() {
		fail("Implement me!");
	}
	
	@Test
	@Ignore("FIXME: Should print an error if the \"plugins\" config contains a non-known plugin id!")
	public void printsAnErrorIfPluginsConfigContainsUnknowId() {
		fail("Implement me!");
	}
	
	public static class BuildPluginMock implements BuildPlugin {
		private static PluginContext pluginInitializedWithContext;
		private static int initCount = 0;
		
		@Override
		public void initialize(final PluginContext pluginContext) {
			pluginInitializedWithContext = pluginContext;
			initCount++;
		}
		
		@Override
		public String getId() {
			return "org.ravenbuild.MockPlugin";
		}
	}
	
	public static class BuildPluginMock2 implements BuildPlugin {
		private static PluginContext pluginInitializedWithContext;
		
		@Override
		public void initialize(final PluginContext pluginContext) {
			pluginContext.dependsOnPlugin(BuildPluginMock.class);
			pluginInitializedWithContext = pluginContext;
		}
		
		@Override
		public String getId() {
			return "org.ravenbuild.MockPlugin2";
		}
	}
	
	public static class BuildPluginMock3 implements BuildPlugin {
		private static PluginContext pluginInitializedWithContext;
		
		@Override
		public void initialize(final PluginContext pluginContext) {
			pluginContext.dependsOnPlugin(BuildPluginMock.class);
			pluginContext.dependsOnPlugin(BuildPluginMock2.class);
			pluginInitializedWithContext = pluginContext;
		}
		
		@Override
		public String getId() {
			return "org.ravenbuild.MockPlugin3";
		}
	}
}
