package org.ravenbuild.plugins.projectstructure;

import org.ravenbuild.plugins.BuildPlugin;
import org.ravenbuild.plugins.PluginContext;

public class ProjectStructurePlugin implements BuildPlugin {
	@Override
	public void initialize(final PluginContext pluginContext) {
	}
	
	@Override
	public String getId() {
		return "org.ravenbuild.project-structure";
	}
}
