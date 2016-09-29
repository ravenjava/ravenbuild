package org.ravenbuild.plugins.dependencies;

import java.util.Collections;
import java.util.List;

public class ExistingDependency extends DependencyInfo {
	private String file;
	private List<DependencyInfo> dependencies = Collections.emptyList();
	
	public ExistingDependency() {
	}
	
	public String getFile() {
		return file;
	}
	
	public List<DependencyInfo> getDependencies() {
		return dependencies;
	}
}
