package org.ravenbuild.plugins.java.dependencies;

import org.ravenbuild.plugins.BuildPlugin;
import org.ravenbuild.plugins.PluginContext;
import org.ravenbuild.plugins.dependencies.DependenciesPlugin;

public class JavaDependenciesPlugin implements BuildPlugin {
	private final JavaDependenciesType dependenciesType = new JavaDependenciesType();
	
	private DependenciesPlugin dependenciesPlugin;
	
	@Override
	public void initialize(final PluginContext pluginContext) {
		dependenciesPlugin = pluginContext.dependsOnPlugin(DependenciesPlugin.class);
		
		dependenciesPlugin.registerDependenciesType("java", dependenciesType);
	}
	
	@Override
	public String getId() {
		return "org.ravenbuild.java.dependencies";
	}
	
	public void reportDependenciesTo(final JavaDependenciesConsumer dependenciesConsumer) {
		dependenciesType.reportDependenciesTo(dependenciesConsumer);
	}
}
