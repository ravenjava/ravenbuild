package org.ravenbuild.plugins.java.intellij;

import org.ravenbuild.plugins.BuildPlugin;
import org.ravenbuild.plugins.PluginContext;
import org.ravenbuild.plugins.java.JavaPlugin;
import org.ravenbuild.tasks.EmptyTaskOptions;

import java.util.Optional;

public class IntelliJPlugin implements BuildPlugin {
	private Optional<JavaPlugin> javaPlugin;
	
	@Override
	public void initialize(final PluginContext pluginContext) {
		javaPlugin = pluginContext.optionallyDependsOnPlugin(JavaPlugin.class);
		
		IntelliJTask intelliJTask = new IntelliJTask(pluginContext.buildEnvironment());
		pluginContext.registerTask("intellij", intelliJTask, EmptyTaskOptions.class, "IDEs");
	}
	
	@Override
	public String getId() {
		return "org.ravenbuild.java.intellij";
	}
}
