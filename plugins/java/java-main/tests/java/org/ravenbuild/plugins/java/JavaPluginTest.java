package org.ravenbuild.plugins.java;

import org.junit.Test;
import org.ravenbuild.plugins.PluginContext;
import org.ravenbuild.plugins.build.BuildProjectPlugin;
import org.ravenbuild.plugins.dependencies.DependenciesPlugin;
import org.ravenbuild.plugins.java.dependencies.JavaDependenciesPlugin;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class JavaPluginTest {
	@Test
	public void loadsJavaDependenciesPluginAsDependency() {
		JavaPlugin plugin = new JavaPlugin();
		final PluginContext pluginContext = mock(PluginContext.class);
		JavaDependenciesPlugin dependenciesPlugin = mock(JavaDependenciesPlugin.class);
		when(pluginContext.dependsOnPlugin(JavaDependenciesPlugin.class)).thenReturn(dependenciesPlugin);
		
		plugin.initialize(pluginContext);
		
		verify(pluginContext).dependsOnPlugin(JavaDependenciesPlugin.class);
	}
	
	@Test
	public void loadsBuildProjectPluginAsDependency() {
		JavaPlugin plugin = new JavaPlugin();
		final PluginContext pluginContext = mock(PluginContext.class);
		JavaDependenciesPlugin dependenciesPlugin = mock(JavaDependenciesPlugin.class);
		when(pluginContext.dependsOnPlugin(JavaDependenciesPlugin.class)).thenReturn(dependenciesPlugin);
		
		plugin.initialize(pluginContext);
		
		verify(pluginContext).dependsOnPlugin(BuildProjectPlugin.class);
	}
}