package org.ravenbuild.plugins.ides.intellij.java;

import org.ravenbuild.plugins.ides.intellij.CompilerConfiguraitonProvider;
import org.ravenbuild.plugins.ides.intellij.ModuleDataProvider;
import org.ravenbuild.plugins.ides.intellij.ProjectDataProvider;

import java.util.Optional;

class JavaProjectDataProvider implements ProjectDataProvider {
	private Optional<CompilerConfiguraitonProvider> compilerConfigurationProvider = Optional.of(new JavaCompilerConfigurationProvider());
	private Optional<ModuleDataProvider> moduleDataProvider = Optional.of(new JavaModuleDataProvider());
	private JavaProjectStructure javaProjectStructure;
	
	@Override
	public Optional<CompilerConfiguraitonProvider> compilerConfigurationProvider() {
		return compilerConfigurationProvider;
	}
	
	@Override
	public Optional<ModuleDataProvider> moduleDataProvider() {
		return moduleDataProvider;
	}
	
	void setProjectStructure(final JavaProjectStructure javaProjectStructure) {
		moduleDataProvider.ifPresent(mdp -> ((JavaModuleDataProvider) mdp).setProjectStructure(javaProjectStructure));
	}
}
