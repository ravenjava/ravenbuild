package org.ravenbuild.plugins.mactools.main;

import org.ravenbuild.plugins.BuildPlugin;
import org.ravenbuild.plugins.PluginContext;

import java.util.Arrays;

public class MacToolsPlugin implements BuildPlugin {
	@Override
	public void initialize(final PluginContext pluginContext) {
	}
	
	@Override
	public String getId() {
		return "org.ravenbuild.mactools";
	}
}
