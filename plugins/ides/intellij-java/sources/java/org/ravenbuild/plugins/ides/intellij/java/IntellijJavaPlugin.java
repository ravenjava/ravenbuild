package org.ravenbuild.plugins.ides.intellij.java;

import org.ravenbuild.plugins.BuildPlugin;
import org.ravenbuild.plugins.PluginContext;
import org.ravenbuild.plugins.ides.intellij.IntelliJPlugin;

public class IntellijJavaPlugin implements BuildPlugin {
	private IntelliJPlugin intellijPlugin;
	
	@Override
	public void initialize(final PluginContext pluginContext) {
		intellijPlugin = pluginContext.dependsOnPlugin(IntelliJPlugin.class);
	}
	
	@Override
	public String getId() {
		return "org.ravenbuild.java.intellij-java";
	}
}
