package org.ravenbuild.plugins.build;

import org.ravenbuild.plugins.BuildPlugin;
import org.ravenbuild.plugins.PluginContext;
import org.ravenbuild.tasks.EmptyTaskOptions;

public class BuildProjectPlugin implements BuildPlugin {
	@Override
	public void initialize(final PluginContext pluginContext) {
		BuildSubtasksTask buildSubtasksTask = new BuildSubtasksTask();
		pluginContext.registerTask("buildSubtasks", buildSubtasksTask, EmptyTaskOptions.class, "Internal");
		pluginContext.registerTask("build", new BuildTask(buildSubtasksTask), EmptyTaskOptions.class, "Build");
	}
	
	@Override
	public String getId() {
		return null;
	}
}
