package org.ravenbuild.plugins.dependencies;

import org.ravenbuild.plugins.BuildPlugin;
import org.ravenbuild.plugins.PluginContext;

import java.util.HashMap;
import java.util.Map;

public class DependenciesPlugin implements BuildPlugin {
	final Map<String, DependenciesType> dependenciesTypes = new HashMap<>();
	
	@Override
	public void initialize(final PluginContext pluginContext) {
		pluginContext.registerTask("dependencies", new DependenciesTask(), DependenciesTaskOptions.class);
	}
	
	@Override
	public String getId() {
		return "org.ravenbuild.dependencies";
	}
	
	public void registerDependenciesType(final String name, final DependenciesType dependenciesType) {
		dependenciesTypes.put(name, dependenciesType);
	}
}
