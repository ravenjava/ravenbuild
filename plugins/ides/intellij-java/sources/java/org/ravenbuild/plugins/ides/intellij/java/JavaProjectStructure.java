package org.ravenbuild.plugins.ides.intellij.java;

import org.ravenbuild.plugins.dependencies.Dependency;

import java.util.List;

public interface JavaProjectStructure {
	List<Dependency> dependencies();
	List<JavaSourceFolder> sourceFolders();
}
