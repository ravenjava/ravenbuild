package org.ravenbuild.plugins.java.dependencies;

import org.junit.Test;
import org.ravenbuild.plugins.PluginContext;
import org.ravenbuild.plugins.dependencies.DependenciesPlugin;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JavaDependenciesPluginTest {
	@Test
	public void loadsDependenciesPluginAsADependency() {
		JavaDependenciesPlugin plugin = new JavaDependenciesPlugin();
		final PluginContext pluginContext = mock(PluginContext.class);
		when(pluginContext.dependsOnPlugin(DependenciesPlugin.class)).thenReturn(mock(DependenciesPlugin.class));
		
		plugin.initialize(pluginContext);
		
		verify(pluginContext).dependsOnPlugin(DependenciesPlugin.class);
	}
	
	@Test
	public void registersTheJavaDependenciesTypeWithTheDependenciesPlugin() {
		JavaDependenciesPlugin plugin = new JavaDependenciesPlugin();
		final PluginContext pluginContext = mock(PluginContext.class);
		final DependenciesPlugin dependenciesPlugin = mock(DependenciesPlugin.class);
		when(pluginContext.dependsOnPlugin(DependenciesPlugin.class)).thenReturn(dependenciesPlugin);
		
		plugin.initialize(pluginContext);
		
		verify(dependenciesPlugin).registerDependenciesType(eq("java"), any(JavaDependenciesType.class));
	}
}