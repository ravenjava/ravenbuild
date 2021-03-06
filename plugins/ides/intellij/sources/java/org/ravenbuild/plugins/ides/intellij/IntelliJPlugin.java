package org.ravenbuild.plugins.ides.intellij;

import org.ravenbuild.plugins.BuildPlugin;
import org.ravenbuild.plugins.PluginContext;
import org.ravenbuild.tasks.EmptyTaskOptions;

public class IntelliJPlugin implements BuildPlugin {
	private IntelliJTask intelliJTask;
	
	@Override
	public void initialize(final PluginContext pluginContext) {
		intelliJTask = new IntelliJTask(pluginContext.buildEnvironment(), pluginContext.allProjects());
		pluginContext.registerTask("intellij", intelliJTask, EmptyTaskOptions.class, "IDEs");
	}
	
	@Override
	public String getId() {
		return "org.ravenbuild.java.intellij";
	}
	
	public void addProjectDataProvider(final ProjectDataProvider projectDataProvider) {
		intelliJTask.addProjectDataProvider(projectDataProvider);
	}
}
