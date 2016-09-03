package org.ravenbuild.plugins.build;

import org.ravenbuild.plugins.BuildPlugin;
import org.ravenbuild.plugins.PluginContext;
import org.ravenbuild.tasks.EmptyTaskOptions;

public class BuildProjectPlugin implements BuildPlugin {
	@Override
	public void initialize(final PluginContext pluginContext) {
		pluginContext.registerTask("build", new BuildTask(), EmptyTaskOptions.class, "Build");
	}
	
	@Override
	public String getId() {
		return null;
	}
}
