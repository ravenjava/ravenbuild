package org.ravenbuild.plugins.java.projectstructure;

import org.ravenbuild.plugins.dependencies.Dependency;
import org.ravenbuild.plugins.ides.intellij.java.JavaSourceFolder;
import org.ravenbuild.plugins.java.dependencies.JavaDependenciesConsumer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JavaProjectStructure implements JavaDependenciesConsumer, org.ravenbuild.plugins.ides.intellij.java.JavaProjectStructure {
	private List<SourceFolder> sourceFolders = new ArrayList<>();
	private List<Dependency> dependencies = new ArrayList<>();
	
	@Override
	public void addDependency(final Dependency dependency) {
		dependencies.add(dependency);
	}
	
	public void addSourceFolder(final SourceFolder sourceFolder) {
		sourceFolders.add(sourceFolder);
	}
	
	@Override
	public List<Dependency> dependencies() {
		return Collections.unmodifiableList(dependencies);
	}
	
	@Override
	public List<JavaSourceFolder> sourceFolders() {
		return Collections.unmodifiableList(sourceFolders);
	}
}
