package org.ravenbuild.plugins.dependencies;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.ravenbuild.projectinfo.AllProjects;
import org.ravenbuild.projectinfo.ProjectInfo;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DependenciesTaskRunnerTest {
	@Test
	public void loadsExistingDependenciesInformationOnInitialize() {
		ExistingDependenciesInformation dependenciesInfo = mock(ExistingDependenciesInformation.class);
		AllProjects allProjects = mock(AllProjects.class);
		DependenciesTaskRunner runner = new DependenciesTaskRunner(dependenciesInfo, allProjects, mock(DependenciesHolder.class));
		
		runner.initialize(Collections.emptyMap());
		
		verify(dependenciesInfo).load();
	}
	
	@Test
	public void checksProjectInfoIfDependencyIsAKnownSubProject() {
		ExistingDependenciesInformation dependenciesInfo = mock(ExistingDependenciesInformation.class);
		AllProjects allProjects = mock(AllProjects.class);
		DependenciesTaskRunner runner = new DependenciesTaskRunner(dependenciesInfo, allProjects, mock(DependenciesHolder.class));
		
		HashMap<String, List<String>> configuration = new HashMap<String, List<String>>() {{
			put("java", Arrays.asList("mygroup:subproject"));
		}};
		runner.initialize(configuration);
		runner.initializeDependencies();
		
		verify(allProjects).findProject("mygroup:subproject");
	}
	
	@Test
	public void waitsForProjectDependencyWhenThereIsASubProjectDependency() {
		ExistingDependenciesInformation dependenciesInfo = mock(ExistingDependenciesInformation.class);
		AllProjects allProjects = mock(AllProjects.class);
		ProjectInfo subProject = mock(ProjectInfo.class, RETURNS_DEEP_STUBS);
		when(allProjects.findProject("mygroup:subproject")).thenReturn(subProject);
		DependenciesTaskRunner runner = new DependenciesTaskRunner(dependenciesInfo, allProjects, mock(DependenciesHolder.class));
		
		HashMap<String, List<String>> configuration = new HashMap<String, List<String>>() {{
			put("java", Arrays.asList("mygroup:subproject"));
		}};
		runner.initialize(configuration);
		runner.initializeDependencies();
		
		verify(allProjects).waitFor(subProject);
	}
	
	@Test
	public void doesNotCheckExistingDependencyInfoIfTheDependencyExistsIfItsAProjectDependency() {
		ExistingDependenciesInformation dependenciesInfo = mock(ExistingDependenciesInformation.class);
		AllProjects allProjects = mock(AllProjects.class);
		ProjectInfo subProject = mock(ProjectInfo.class, RETURNS_DEEP_STUBS);
		when(allProjects.findProject("somegroup:someid")).thenReturn(subProject);
		DependenciesTaskRunner runner = new DependenciesTaskRunner(dependenciesInfo, allProjects, mock(DependenciesHolder.class));
		
		HashMap<String, List<String>> configuration = new HashMap<String, List<String>>() {{
			put("java", Arrays.asList("somegroup:someid"));
		}};
		runner.initialize(configuration);
		runner.initializeDependencies();
		
		verify(dependenciesInfo, never()).getDependency("somegroup:someid");
	}
	
	@Test
	public void checksExistingDependencyInfoIfTheDependencyExistsIfItsNoProjectDependency() {
		ExistingDependenciesInformation dependenciesInfo = mock(ExistingDependenciesInformation.class);
		AllProjects allProjects = mock(AllProjects.class);
		DependenciesTaskRunner runner = new DependenciesTaskRunner(dependenciesInfo, allProjects, mock(DependenciesHolder.class));
		
		HashMap<String, List<String>> configuration = new HashMap<String, List<String>>() {{
			put("java", Arrays.asList("somegroup:someid"));
		}};
		runner.initialize(configuration);
		runner.initializeDependencies();
		
		verify(dependenciesInfo).getDependency("somegroup:someid");
	}
	
	@Test
	public void informsDependenciesHolderAboutExistingProjectDependency() {
		ExistingDependenciesInformation dependenciesInfo = mock(ExistingDependenciesInformation.class);
		AllProjects allProjects = mock(AllProjects.class);
		ProjectInfo subProject = mock(ProjectInfo.class);
		File location = mock(File.class);
		when(subProject.getLocationOnDisk()).thenReturn(location);
		when(allProjects.findProject("mygroup:subproject")).thenReturn(subProject);
		DependenciesHolder dependenciesHolder = mock(DependenciesHolder.class);
		DependenciesTaskRunner runner = new DependenciesTaskRunner(dependenciesInfo, allProjects, dependenciesHolder);
		
		HashMap<String, List<String>> configuration = new HashMap<String, List<String>>() {{
			put("java", Arrays.asList("mygroup:subproject"));
		}};
		runner.initialize(configuration);
		runner.initializeDependencies();
		
		ArgumentCaptor<Dependency> captor = ArgumentCaptor.forClass(Dependency.class);
		verify(dependenciesHolder).addDependency(captor.capture());
		
		assertThat(captor.getValue().artifactId(), is("mygroup:subproject"));
		assertThat(captor.getValue().locationOnDisk(), is(location));
		assertFalse(captor.getValue().downloadURL().isPresent());
	}
	
	@Test
	public void informsDependenciesHolderAboutExistingNonProjectDependency() throws MalformedURLException {
		ExistingDependenciesInformation dependenciesInfo = mock(ExistingDependenciesInformation.class);
		when(dependenciesInfo.getDependency("somegroup:someid")).thenReturn(mock(Dependency.class));
		File location = mock(File.class);
		when(dependenciesInfo.getDependency("somegroup:someid")).thenReturn(
				new Dependency("somegroup:someid", location, Optional.of(new URL("http://example.com/artifact"))));
		AllProjects allProjects = mock(AllProjects.class);
		DependenciesHolder dependenciesHolder = mock(DependenciesHolder.class);
		DependenciesTaskRunner runner = new DependenciesTaskRunner(dependenciesInfo, allProjects, dependenciesHolder);
		
		HashMap<String, List<String>> configuration = new HashMap<String, List<String>>() {{
			put("java", Arrays.asList("somegroup:someid"));
		}};
		runner.initialize(configuration);
		runner.initializeDependencies();
		
		ArgumentCaptor<Dependency> captor = ArgumentCaptor.forClass(Dependency.class);
		verify(dependenciesHolder).addDependency(captor.capture());
		
		assertThat(captor.getValue().artifactId(), is("somegroup:someid"));
		assertThat(captor.getValue().locationOnDisk(), is(location));
		assertThat(captor.getValue().downloadURL().get(), is(new URL("http://example.com/artifact")));
	}
	
	@Test
	@Ignore("TODO: Finish intellij feature first")
	public void resolvesDependencyUsingDependencyResolverIfDependencyDoesNotExistRightNow() {
		fail("Implement me!");
	}
}