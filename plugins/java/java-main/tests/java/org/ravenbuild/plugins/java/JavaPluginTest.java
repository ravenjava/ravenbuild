package org.ravenbuild.plugins.java;

import org.junit.Test;
import org.ravenbuild.plugins.PluginContext;
import org.ravenbuild.plugins.build.BuildProjectPlugin;
import org.ravenbuild.plugins.java.dependencies.JavaDependenciesPlugin;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class JavaPluginTest {
	@Test
	public void loadsJavaDependenciesPluginAsDependency() {
		JavaPlugin plugin = new JavaPlugin();
		final PluginContext pluginContext = mock(PluginContext.class);
		
		plugin.initialize(pluginContext);
		
		verify(pluginContext).dependsOnPlugin(JavaDependenciesPlugin.class);
	}
	
	@Test
	public void loadsBuildProjectPluginAsDependency() {
		JavaPlugin plugin = new JavaPlugin();
		final PluginContext pluginContext = mock(PluginContext.class);
		
		plugin.initialize(pluginContext);
		
		verify(pluginContext).dependsOnPlugin(BuildProjectPlugin.class);
	}
}