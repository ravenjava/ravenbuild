package org.ravenbuild.plugins.dependencies;

import org.junit.Test;
import org.ravenbuild.environment.BuildEnvironment;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ExistingDependenciesInformationTest {
	@Test
	public void loadsExistingDependenciesInformationFromTheBuildEnvironment() {
		BuildEnvironment buildEnvironment = mock(BuildEnvironment.class);
		Reader configFileReader = new StringReader("{}");
		when(buildEnvironment.readFile(any())).thenReturn(configFileReader);
		ExistingDependenciesInformation existingDependenciesInformation = new ExistingDependenciesInformation(buildEnvironment);
		
		existingDependenciesInformation.load();
		
		verify(buildEnvironment).readFile("/.raven/libraries/dependency-versions.json");
	}
	
	@Test
	public void knowsExistingDependencyByArtifactIdAfterLoadingIt() {
		BuildEnvironment buildEnvironment = mock(BuildEnvironment.class);
		Reader configFileReader = new StringReader("{ " +
				"\"java\": [ " +
				"{ \"id\": \"foo:bar\", \"version\": \"1.2.3\", \"file\": \"foo-bar-1.2.3.jar\" }" +
				"]}");
		when(buildEnvironment.readFile(any())).thenReturn(configFileReader);
		File locationOnDisk = mock(File.class);
		when(buildEnvironment.getFile("/.raven/libraries/foo-bar-1.2.3.jar")).thenReturn(locationOnDisk);
		ExistingDependenciesInformation existingDependenciesInformation = new ExistingDependenciesInformation(buildEnvironment);
		
		existingDependenciesInformation.load();
		
		Dependency dependency = existingDependenciesInformation.getDependency("foo:bar");
		assertNotNull(dependency);
		assertThat(dependency.artifactId(), is("foo:bar"));
		assertThat(dependency.locationOnDisk(), is(locationOnDisk));
	}
}