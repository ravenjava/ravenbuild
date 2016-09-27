package org.ravenbuild.plugins.ides.intellij.java;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.plugins.BuildPlugin;
import org.ravenbuild.plugins.PluginContext;
import org.ravenbuild.plugins.ides.intellij.CompilerConfiguraitonProvider;
import org.ravenbuild.plugins.ides.intellij.IntelliJPlugin;
import org.ravenbuild.plugins.ides.intellij.ModuleDataProvider;
import org.ravenbuild.plugins.ides.intellij.ProjectDataProvider;

import java.util.Optional;

public class IntellijJavaPlugin implements BuildPlugin {
	private IntelliJPlugin intellijPlugin;
	private final JavaProjectDataProvider projectDataProvider = new JavaProjectDataProvider();
	
	@Override
	public void initialize(final PluginContext pluginContext) {
		intellijPlugin = pluginContext.dependsOnPlugin(IntelliJPlugin.class);
		
		intellijPlugin.addProjectDataProvider(projectDataProvider);
	}
	
	@Override
	public String getId() {
		return "org.ravenbuild.java.intellij-java";
	}
	
	public void setProjectStructure(final JavaProjectStructure javaProjectStructure) {
		Args.notNull(javaProjectStructure, "javaProjectStructure");
		projectDataProvider.setProjectStructure(javaProjectStructure);
	}
}
