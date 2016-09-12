package org.ravenbuild.plugins.dependencies;

import org.junit.Test;
import org.ravenbuild.plugins.PluginContext;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DependenciesPluginTest {
	@Test
	public void registersDependenciesTask() {
		DependenciesPlugin plugin = new DependenciesPlugin();
		final PluginContext pluginContext = mock(PluginContext.class, RETURNS_DEEP_STUBS);
		
		plugin.initialize(pluginContext);
		
		verify(pluginContext).registerTask(eq("dependencies"), any(DependenciesTask.class), eq(DependenciesTaskOptions.class));
	}
}