package org.ravenbuild.plugins;

public interface BuildPlugin {
	void initialize(PluginContext pluginContext);
	String getId();
}
