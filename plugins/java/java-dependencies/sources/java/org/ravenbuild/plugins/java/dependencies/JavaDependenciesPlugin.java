package org.ravenbuild.plugins.java.dependencies;

import org.ravenbuild.plugins.BuildPlugin;
import org.ravenbuild.plugins.PluginContext;
import org.ravenbuild.plugins.dependencies.DependenciesPlugin;

public class JavaDependenciesPlugin implements BuildPlugin {
	private DependenciesPlugin dependenciesPlugin;
	
	@Override
	public void initialize(final PluginContext pluginContext) {
		dependenciesPlugin = pluginContext.dependsOnPlugin(DependenciesPlugin.class);
		
		dependenciesPlugin.registerDependenciesType("java", new JavaDependenciesType());
	}
	
	@Override
	public String getId() {
		return "org.ravenbuild.java.dependencies";
	}
}
