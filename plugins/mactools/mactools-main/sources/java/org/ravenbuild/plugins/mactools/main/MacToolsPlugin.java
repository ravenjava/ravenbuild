package org.ravenbuild.plugins.mactools.main;

import org.ravenbuild.plugins.BuildPlugin;
import org.ravenbuild.plugins.PluginContext;
import org.ravenbuild.plugins.build.BuildProjectPlugin;
import org.ravenbuild.plugins.mactools.simulator.MactoolsSimulatorPlugin;

import java.util.Arrays;

public class MacToolsPlugin implements BuildPlugin {
	private MactoolsSimulatorPlugin simulatorPlugin;
	private BuildProjectPlugin buildProjectPlugin;
	
	@Override
	public void initialize(final PluginContext pluginContext) {
		buildProjectPlugin = pluginContext.dependsOnPlugin(BuildProjectPlugin.class);
		simulatorPlugin = pluginContext.dependsOnPlugin(MactoolsSimulatorPlugin.class);
	}
	
	@Override
	public String getId() {
		return "org.ravenbuild.mactools";
	}
}
