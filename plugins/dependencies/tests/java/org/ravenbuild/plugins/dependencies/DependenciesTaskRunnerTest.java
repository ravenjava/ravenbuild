package org.ravenbuild.plugins.dependencies;

import org.junit.Test;
import org.ravenbuild.projectinfo.AllProjects;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DependenciesTaskRunnerTest {
	@Test
	public void loadsExistingDependenciesInformationOnInitialize() {
		ExistingDependenciesInformation dependenciesInfo = mock(ExistingDependenciesInformation.class);
		AllProjects allProjects = mock(AllProjects.class);
		DependenciesTaskRunner runner = new DependenciesTaskRunner(dependenciesInfo, allProjects);
		
		runner.initialize(Collections.emptyMap());
		
		verify(dependenciesInfo).load();
	}
	
	@Test
	public void checksProjectInfoIfDependencyIsAKnownSubProject() {
		ExistingDependenciesInformation dependenciesInfo = mock(ExistingDependenciesInformation.class);
		AllProjects allProjects = mock(AllProjects.class);
		DependenciesTaskRunner runner = new DependenciesTaskRunner(dependenciesInfo, allProjects);
		
		HashMap<String, List<String>> configuration = new HashMap<String, List<String>>() {{
			put("java", Arrays.asList("mygroup:subproject"));
		}};
		runner.initialize(configuration);
		runner.initializeDependencies();
		
		verify(allProjects).findProject("mygroup:subproject");
	}
}