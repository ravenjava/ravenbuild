package org.ravenbuild.plugins.java.intellij;

import org.ravenbuild.plugins.BuildPlugin;
import org.ravenbuild.plugins.PluginContext;
import org.ravenbuild.plugins.projectstructure.ProjectStructurePlugin;
import org.ravenbuild.tasks.EmptyTaskOptions;

public class IntelliJPlugin implements BuildPlugin {
	private ProjectStructurePlugin projectStructure;
	
	@Override
	public void initialize(final PluginContext pluginContext) {
		projectStructure = pluginContext.dependsOnPlugin(ProjectStructurePlugin.class);
		
		pluginContext.registerTask("intellij", new IntelliJTask(projectStructure), EmptyTaskOptions.class, "Java");
	}
	
	@Override
	public String getId() {
		return "org.ravenbuild.java.intellij";
	}
}
