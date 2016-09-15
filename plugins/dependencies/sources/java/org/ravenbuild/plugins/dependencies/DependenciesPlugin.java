package org.ravenbuild.plugins.dependencies;

import org.ravenbuild.LogLevel;
import org.ravenbuild.logging.Logger;
import org.ravenbuild.plugins.BuildPlugin;
import org.ravenbuild.plugins.PluginContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DependenciesPlugin implements BuildPlugin {
	final Map<String, DependenciesType> dependenciesTypes = new HashMap<>();
	private Logger logger;
	private DependenciesTask dependenciesTask;
	
	@Override
	public void initialize(final PluginContext pluginContext) {
		this.logger = pluginContext.logger();
		pluginContext.configuration().registerConfigurationListener("dependencies", Map.class, this::configurationLoaded);
		
		ExistingDependenciesInformation dependenciesInfo = new ExistingDependenciesInformation();
		DependenciesTaskRunner taskRunner = new DependenciesTaskRunner(dependenciesInfo, pluginContext.allProjects(), dependenciesTypes);
		dependenciesTask = new DependenciesTask(taskRunner);
		
		pluginContext.registerTask("dependencies", dependenciesTask, DependenciesTaskOptions.class);
	}
	
	private void configurationLoaded(final Map<String, List<String>> configuration) {
		dependenciesTask.configurationLoaded(configuration);
	}
	
	@Override
	public String getId() {
		return "org.ravenbuild.dependencies";
	}
	
	public void registerDependenciesType(final String name, final DependenciesType dependenciesType) {
		logger.log(LogLevel.VERY_VERBOSE, "Dependencies", "Registering dependencies type for: \""+name+"\" dependencies.");
		dependenciesTypes.put(name, dependenciesType);
	}
}
