package org.ravenbuild.plugins.mactools.simulator;

import org.ravenbuild.plugins.BuildPlugin;
import org.ravenbuild.plugins.PluginContext;
import org.ravenbuild.tasks.EmptyTaskOptions;
import org.ravenbuild.tasks.Task;

public class MactoolsSimulatorPlugin implements BuildPlugin {
	@Override
	public void initialize(final PluginContext pluginContext) {
		SimulatorListTask simulatorListTask = new SimulatorListTask(pluginContext.logger());
		pluginContext.registerTask("simulator-list", simulatorListTask, EmptyTaskOptions.class, "iOS Simulator");
	}
	
	@Override
	public String getId() {
		return "org.ravenbuild.mactools-simulator";
	}
}
