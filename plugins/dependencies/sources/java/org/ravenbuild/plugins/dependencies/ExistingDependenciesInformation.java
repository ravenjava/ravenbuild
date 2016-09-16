package org.ravenbuild.plugins.dependencies;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.environment.BuildEnvironment;

import java.io.Reader;

class ExistingDependenciesInformation {
	private final BuildEnvironment buildEnvironment;
	
	public ExistingDependenciesInformation(final BuildEnvironment buildEnvironment) {
		Args.notNull(buildEnvironment, "buildEnvironment");
		
		this.buildEnvironment = buildEnvironment;
	}
	
	void load() {
		Reader existingDependenciesConfigReader = buildEnvironment.readFile("/raven/libraries/dependency-versions.json");
	}
	
	public Dependency getDependency(final String artifactId) {
		return null;
	}
}
