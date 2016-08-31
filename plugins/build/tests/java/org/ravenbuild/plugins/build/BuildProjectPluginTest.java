package org.ravenbuild.plugins.build;

import org.junit.Test;
import org.ravenbuild.plugins.PluginContext;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BuildProjectPluginTest {
	@Test
	public void registersBuildTask() {
		BuildProjectPlugin buildPlugin = new BuildProjectPlugin();
		final PluginContext pluginContext = mock(PluginContext.class);
		
		buildPlugin.initialize(pluginContext);
		
		verify(pluginContext).registerTask(eq("build"), any(BuildTask.class), eq(BuildTaskOptions.class), eq("Build"));
	}
}