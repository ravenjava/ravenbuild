package org.ravenbuild.plugins.java;

import org.ravenbuild.plugins.BuildPlugin;
import org.ravenbuild.plugins.PluginContext;

public class JavaPlugin implements BuildPlugin {
	@Override
	public void initialize(final PluginContext pluginContext) {
	}
	
	@Override
	public String getId() {
		return "org.ravenbuild.java";
	}
}
