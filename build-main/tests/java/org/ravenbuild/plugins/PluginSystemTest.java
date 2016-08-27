package org.ravenbuild.plugins;

import org.junit.Test;
import org.ravenbuild.logging.Logger;
import org.ravenbuild.tasks.TaskGraph;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PluginSystemTest {
	@Test
	public void loadsPluginsUsingClasspathScannerWhenLoadingAllPlugins() {
		final TaskGraph taskgraph = mock(TaskGraph.class);
		final ClasspathScanner classpathScanner = mock(ClasspathScanner.class);
		PluginSystem pluginSystem = new PluginSystem(taskgraph, classpathScanner, mock(Logger.class));
		
		pluginSystem.loadPlugins();
		
		verify(classpathScanner).findAllClassesImplementing(eq(BuildPlugin.class));
	}
	
	@Test
	public void initializesPluginsWhenLoadingAllPlugins() {
		BuildPluginMock.pluginInitializedWithContext = null;
		final TaskGraph taskgraph = mock(TaskGraph.class);
		final ClasspathScanner classpathScanner = mock(ClasspathScanner.class);
		when(classpathScanner.findAllClassesImplementing(eq(BuildPlugin.class))).thenReturn(Arrays.asList(BuildPluginMock.class));
		PluginSystem pluginSystem = new PluginSystem(taskgraph, classpathScanner, mock(Logger.class));
		
		pluginSystem.loadPlugins();
		
		assertNotNull(BuildPluginMock.pluginInitializedWithContext);
	}
	
	@Test
	public void hasAListOfAllPluginsAfterLoadingAllPlugins() {
		BuildPluginMock.pluginInitializedWithContext = null;
		final TaskGraph taskgraph = mock(TaskGraph.class);
		final ClasspathScanner classpathScanner = mock(ClasspathScanner.class);
		when(classpathScanner.findAllClassesImplementing(eq(BuildPlugin.class))).thenReturn(Arrays.asList(BuildPluginMock.class));
		PluginSystem pluginSystem = new PluginSystem(taskgraph, classpathScanner, mock(Logger.class));
		
		pluginSystem.loadPlugins();
		
		assertThat(pluginSystem.allPlugins(), contains(instanceOf(BuildPluginMock.class)));
	}
	
	public static class BuildPluginMock implements BuildPlugin {
		private static PluginContext pluginInitializedWithContext;
		
		@Override
		public void initialize(final PluginContext pluginContext) {
			pluginInitializedWithContext = pluginContext;
		}
	}
}
