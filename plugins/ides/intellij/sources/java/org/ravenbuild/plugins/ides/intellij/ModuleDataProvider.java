package org.ravenbuild.plugins.ides.intellij;

import org.ravenbuild.plugins.dependencies.Dependency;
import org.ravenbuild.plugins.ides.intellij.xml.module.Content;
import org.ravenbuild.plugins.ides.intellij.xml.module.NewModuleRootManager;
import org.ravenbuild.plugins.ides.intellij.xml.module.SourceFolder;

import java.util.List;
import java.util.Optional;

public interface ModuleDataProvider {
	Optional<NewModuleRootManager.LanguageLevel> javaLanguageLevel();
	List<Dependency> dependencies();
	void updateModuleContent(Content moduleContent);
}
