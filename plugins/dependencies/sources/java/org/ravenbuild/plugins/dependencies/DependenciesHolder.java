package org.ravenbuild.plugins.dependencies;

import java.util.List;

public interface DependenciesHolder {
	void addDependency(Dependency dependency);
	List<Dependency> allDependencies();
}
