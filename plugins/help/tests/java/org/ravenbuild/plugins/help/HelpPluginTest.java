package org.ravenbuild.plugins.help;

import org.junit.Test;
import org.ravenbuild.plugins.PluginContext;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HelpPluginTest {
	@Test
	public void addsHelpTask() {
		HelpPlugin helpPlugin = new HelpPlugin();
		PluginContext pluginContext = mock(PluginContext.class);
		
		helpPlugin.initialize(pluginContext);
		
		verify(pluginContext).registerTask(eq("help"), any(HelpTask.class), eq(HelpTaskOptions.class));
	}
}
