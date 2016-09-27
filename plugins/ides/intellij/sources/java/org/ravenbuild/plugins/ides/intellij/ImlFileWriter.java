package org.ravenbuild.plugins.ides.intellij;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.environment.FileWriterHandler;
import org.ravenbuild.plugins.dependencies.Dependency;
import org.ravenbuild.plugins.ides.intellij.xml.module.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ImlFileWriter implements FileWriterHandler {
	private final List<ModuleDataProvider> moduleDataProviders;
	
	public ImlFileWriter(final List<ModuleDataProvider> moduleDataProviders) {
		Args.notNull(moduleDataProviders, "moduleDataProviders");
		
		this.moduleDataProviders = moduleDataProviders;
	}
	
	@Override
	public void write(final FileWriter fileWriter) throws IOException {
		fileWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		
		NewModuleRootManager newModuleRootManager = new NewModuleRootManager();
		setModuleLanguageLevel(newModuleRootManager);
		setModuleOutput(newModuleRootManager);
		setOtherBookeepingAttributes(newModuleRootManager);
		addDependencies(newModuleRootManager);
		
		Module module = new Module();
		module.add(newModuleRootManager);
		
		fileWriter.write(module.render());
	}
	
	private void addDependencies(final NewModuleRootManager newModuleRootManager) {
		for(ModuleDataProvider mdp : moduleDataProviders) {
			for(Dependency dependency : mdp.dependencies()) {
				if(dependency.locationOnDisk().getAbsolutePath().contains(".raven/libraries")) {
					//FIXME should use relative path and $PROJECT_DIR$ here!
					newModuleRootManager.add(new ModuleLibrary(new ModuleLibrary.JarUrl(dependency.locationOnDisk().getAbsolutePath())));
				} else {
					String moduleId = dependency.artifactId().substring(dependency.artifactId().indexOf(":")+1);
					newModuleRootManager.add(new ModuleDependency(new ModuleDependency.ModuleName(moduleId)));
				}
			}
		}
	}
	
	private void setOtherBookeepingAttributes(final NewModuleRootManager newModuleRootManager) {
		Content moduleContent = new Content(new Content.Url("file://$MODULE_DIR$"));
		moduleDataProviders.stream()
				.forEach(mdp -> mdp.updateModuleContent(moduleContent));
		
		newModuleRootManager.add(moduleContent);
		newModuleRootManager.add(new InheritedJdk());
		newModuleRootManager.add(new SourceFolder());
	}
	
	private void setModuleOutput(final NewModuleRootManager newModuleRootManager) {
		newModuleRootManager.add(new Output(new Output.Url("file://$PROJECT_DIR$/.raven/target/production/ravenbuild")));
		newModuleRootManager.add(new TestOutput(new Output.Url("file://$PROJECT_DIR$/.raven/target/test/ravenbuild")));
		newModuleRootManager.add(new ExcludeOutput());
	}
	
	private void setModuleLanguageLevel(final NewModuleRootManager newModuleRootManager) {
		NewModuleRootManager.LanguageLevel languageLevel = new NewModuleRootManager.LanguageLevel("");
		for(ModuleDataProvider mdp : moduleDataProviders) {
			if(mdp.javaLanguageLevel().isPresent()) {
				languageLevel = mdp.javaLanguageLevel().get();
				break;
			}
		}
		newModuleRootManager.setLanguageLevel(languageLevel);
	}
}
