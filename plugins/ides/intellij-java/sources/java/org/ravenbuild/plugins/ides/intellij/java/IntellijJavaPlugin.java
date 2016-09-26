package org.ravenbuild.plugins.ides.intellij.java;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.plugins.BuildPlugin;
import org.ravenbuild.plugins.PluginContext;
import org.ravenbuild.plugins.ides.intellij.CompilerConfiguraitonProvider;
import org.ravenbuild.plugins.ides.intellij.IntelliJPlugin;
import org.ravenbuild.plugins.ides.intellij.ProjectDataProvider;

import java.util.Optional;

public class IntellijJavaPlugin implements BuildPlugin, ProjectDataProvider {
	private IntelliJPlugin intellijPlugin;
	private JavaProjectStructure javaProjectStructure;
	private Optional<CompilerConfiguraitonProvider> compilerConfigurationProvider = Optional.of(new JavaCompilerConfigurationProvider());
	
	@Override
	public void initialize(final PluginContext pluginContext) {
		intellijPlugin = pluginContext.dependsOnPlugin(IntelliJPlugin.class);
		
		intellijPlugin.addProjectDataProvider(this);
	}
	
	@Override
	public String getId() {
		return "org.ravenbuild.java.intellij-java";
	}
	
	public void setProjectStructure(final JavaProjectStructure javaProjectStructure) {
		Args.notNull(javaProjectStructure, "javaProjectStructure");
		this.javaProjectStructure = javaProjectStructure;
	}
	
	@Override
	public Optional<CompilerConfiguraitonProvider> compilerConfigurationProvider() {
		return compilerConfigurationProvider;
	}
}
