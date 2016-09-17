package org.ravenbuild.plugins.java.dependencies;

import org.ravenbuild.plugins.dependencies.Dependency;

public interface JavaDependenciesConsumer {
	void addDependency(Dependency dependency);
}
