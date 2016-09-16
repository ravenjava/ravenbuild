package org.ravenbuild.plugins.dependencies;

import org.junit.Test;
import org.ravenbuild.environment.BuildEnvironment;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ExistingDependenciesInformationTest {
	@Test
	public void loadsExistingDependenciesInformationFromTheBuildEnvironment() {
		BuildEnvironment buildEnvironment = mock(BuildEnvironment.class);
		ExistingDependenciesInformation existingDependenciesInformation = new ExistingDependenciesInformation(buildEnvironment);
		
		existingDependenciesInformation.load();
		
		verify(buildEnvironment).readFile("/.raven/libraries/dependency-versions.json");
	}
}