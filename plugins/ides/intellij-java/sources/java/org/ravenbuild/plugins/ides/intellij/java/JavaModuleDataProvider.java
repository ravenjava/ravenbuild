package org.ravenbuild.plugins.ides.intellij.java;

import org.ravenbuild.plugins.dependencies.Dependency;
import org.ravenbuild.plugins.ides.intellij.ModuleDataProvider;
import org.ravenbuild.plugins.ides.intellij.xml.module.Content;
import org.ravenbuild.plugins.ides.intellij.xml.module.NewModuleRootManager;

import java.util.List;
import java.util.Optional;

public class JavaModuleDataProvider implements ModuleDataProvider {
	private JavaProjectStructure projectStructure;
	
	@Override
	public Optional<NewModuleRootManager.LanguageLevel> javaLanguageLevel() {
		//FIXME Should probably be configurable ;)
		return Optional.of(new NewModuleRootManager.LanguageLevel("JDK_1_8"));
	}
	
	@Override
	public List<Dependency> dependencies() {
		return projectStructure.dependencies();
	}
	
	@Override
	public void updateModuleContent(final Content moduleContent) {
		projectStructure.sourceFolders().stream().forEach(sf -> moduleContent.addSourceFolder(sf.relativePath(), sf.isTestSource()));
	}
	
	public void setProjectStructure(final JavaProjectStructure projectStructure) {
		this.projectStructure = projectStructure;
	}
}
