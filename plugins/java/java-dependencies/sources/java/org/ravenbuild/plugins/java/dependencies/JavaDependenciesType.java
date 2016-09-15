package org.ravenbuild.plugins.java.dependencies;

import org.ravenbuild.plugins.dependencies.DependenciesType;
import org.ravenbuild.plugins.dependencies.Dependency;

public class JavaDependenciesType implements DependenciesType {
	@Override
	public void dependencyResolved(final Dependency dependency) {
		System.out.println("Dependency resolved: "+dependency.artifactId()+" -> "+dependency.locationOnDisk().getAbsolutePath());
	}
}
