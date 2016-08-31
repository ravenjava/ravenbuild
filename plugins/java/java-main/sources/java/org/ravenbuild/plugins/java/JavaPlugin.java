package org.ravenbuild.plugins.java;

import org.ravenbuild.plugins.BuildPlugin;
import org.ravenbuild.plugins.PluginContext;
import org.ravenbuild.plugins.build.BuildProjectPlugin;
import org.ravenbuild.plugins.java.dependencies.JavaDependenciesPlugin;

public class JavaPlugin implements BuildPlugin {
	private JavaDependenciesPlugin javaDependenciesPlugin;
	private BuildProjectPlugin buildProjectPlugin;
	
	@Override
	public void initialize(final PluginContext pluginContext) {
		javaDependenciesPlugin = pluginContext.dependsOnPlugin(JavaDependenciesPlugin.class);
		buildProjectPlugin = pluginContext.dependsOnPlugin(BuildProjectPlugin.class);
	}
	
	@Override
	public String getId() {
		return "org.ravenbuild.java";
	}
}
