package org.ravenbuild.plugins.dependencies;

import org.ravenbuild.LogLevel;
import org.ravenbuild.logging.Logger;
import org.ravenbuild.plugins.BuildPlugin;
import org.ravenbuild.plugins.PluginContext;

import java.util.HashMap;
import java.util.Map;

public class DependenciesPlugin implements BuildPlugin {
	final Map<String, DependenciesType> dependenciesTypes = new HashMap<>();
	private Logger logger;
	
	@Override
	public void initialize(final PluginContext pluginContext) {
		this.logger = pluginContext.logger();
		pluginContext.registerTask("dependencies", new DependenciesTask(), DependenciesTaskOptions.class);
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
