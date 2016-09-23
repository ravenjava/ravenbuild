package org.ravenbuild.plugins.ides.intellij;

import org.ravenbuild.plugins.BuildPlugin;
import org.ravenbuild.plugins.PluginContext;
import org.ravenbuild.tasks.EmptyTaskOptions;

public class IntelliJPlugin implements BuildPlugin {
	@Override
	public void initialize(final PluginContext pluginContext) {
		IntelliJTask intelliJTask = new IntelliJTask(pluginContext.buildEnvironment());
		pluginContext.registerTask("intellij", intelliJTask, EmptyTaskOptions.class, "IDEs");
	}
	
	@Override
	public String getId() {
		return "org.ravenbuild.java.intellij";
	}
}
