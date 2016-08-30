package org.ravenbuild.plugins.help;

import org.ravenbuild.plugins.BuildPlugin;
import org.ravenbuild.plugins.PluginContext;

public class HelpPlugin implements BuildPlugin {
	@Override
	public void initialize(final PluginContext pluginContext) {
		pluginContext.registerTask(
				"help",
				new HelpTask(pluginContext.logger(), pluginContext.taskRepository()),
				HelpTaskOptions.class);
	}
	
	@Override
	public String getId() {
		return "org.ravenbuild.help";
	}
}
