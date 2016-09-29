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
		DependenciesTaskRunner runner = new DependenciesTaskRunner(dependenciesInfo, allProjects, mock(Map.class));
		
		runner.initialize(mock(DependenciesConfiguration.class));
		
		verify(dependenciesInfo).load();
	}
	
	@Test
	public void checksProjectInfoIfDependencyIsAKnownSubProject() {
		ExistingDependenciesInformation dependenciesInfo = mock(ExistingDependenciesInformation.class);
		AllProjects allProjects = mock(AllProjects.class);
		DependenciesTaskRunner runner = new DependenciesTaskRunner(dependenciesInfo, allProjects, mock(Map.class));
		
		DependenciesConfiguration configuration = mock(DependenciesConfiguration.class);
		when(configuration.configurationTypes()).thenReturn(new HashSet<String>() {{ add("java"); }});
		when(configuration.getDependenciesFor("java")).thenReturn(Arrays.asList("mygroup:subproject"));

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
		DependenciesTaskRunner runner = new DependenciesTaskRunner(dependenciesInfo, allProjects, new HashMap<String, DependenciesType>() {{
			put("java", mock(DependenciesType.class));
		}});
		
		DependenciesConfiguration configuration = mock(DependenciesConfiguration.class);
		when(configuration.configurationTypes()).thenReturn(new HashSet<String>() {{ add("java"); }});
		when(configuration.getDependenciesFor("java")).thenReturn(Arrays.asList("mygroup:subproject"));
		
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
		DependenciesTaskRunner runner = new DependenciesTaskRunner(dependenciesInfo, allProjects, new HashMap<String, DependenciesType>() {{
			put("java", mock(DependenciesType.class));
		}});
		
		DependenciesConfiguration configuration = mock(DependenciesConfiguration.class);
		when(configuration.configurationTypes()).thenReturn(new HashSet<String>() {{ add("java"); }});
		when(configuration.getDependenciesFor("java")).thenReturn(Arrays.asList("somegroup:someid"));
		
		runner.initialize(configuration);
		runner.initializeDependencies();
		
		verify(dependenciesInfo, never()).getDependency("somegroup:someid");
	}
	
	@Test
	public void checksExistingDependencyInfoIfTheDependencyExistsIfItsNoProjectDependency() {
		ExistingDependenciesInformation dependenciesInfo = mock(ExistingDependenciesInformation.class);
		AllProjects allProjects = mock(AllProjects.class);
		DependenciesTaskRunner runner = new DependenciesTaskRunner(dependenciesInfo, allProjects, mock(Map.class));
		
		DependenciesConfiguration configuration = mock(DependenciesConfiguration.class);
		when(configuration.configurationTypes()).thenReturn(new HashSet<String>() {{ add("java"); }});
		when(configuration.getDependenciesFor("java")).thenReturn(Arrays.asList("somegroup:someid"));
		
		runner.initialize(configuration);
		runner.initializeDependencies();
		
		verify(dependenciesInfo).getDependency("somegroup:someid");
	}
	
	@Test
	public void informsDependenciesTypeAboutExistingProjectDependency() {
		ExistingDependenciesInformation dependenciesInfo = mock(ExistingDependenciesInformation.class);
		AllProjects allProjects = mock(AllProjects.class);
		ProjectInfo subProject = mock(ProjectInfo.class);
		File location = mock(File.class);
		when(subProject.getLocationOnDisk()).thenReturn(location);
		when(allProjects.findProject("mygroup:subproject")).thenReturn(subProject);
		DependenciesType dependenciesType = mock(DependenciesType.class);
		DependenciesTaskRunner runner = new DependenciesTaskRunner(dependenciesInfo, allProjects, new HashMap<String, DependenciesType>() {{
			put("java", dependenciesType);
		}});
		
		DependenciesConfiguration configuration = mock(DependenciesConfiguration.class);
		when(configuration.configurationTypes()).thenReturn(new HashSet<String>() {{ add("java"); }});
		when(configuration.getDependenciesFor("java")).thenReturn(Arrays.asList("mygroup:subproject"));
		
		runner.initialize(configuration);
		runner.initializeDependencies();
		
		ArgumentCaptor<Dependency> captor = ArgumentCaptor.forClass(Dependency.class);
		verify(dependenciesType).dependencyResolved(captor.capture());
		
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
		DependenciesType dependenciesType = mock(DependenciesType.class);
		DependenciesTaskRunner runner = new DependenciesTaskRunner(dependenciesInfo, allProjects, new HashMap<String, DependenciesType>() {{
			put("java", dependenciesType);
		}});
		
		DependenciesConfiguration configuration = mock(DependenciesConfiguration.class);
		when(configuration.configurationTypes()).thenReturn(new HashSet<String>() {{ add("java"); }});
		when(configuration.getDependenciesFor("java")).thenReturn(Arrays.asList("somegroup:someid"));
		
		runner.initialize(configuration);
		runner.initializeDependencies();
		
		ArgumentCaptor<Dependency> captor = ArgumentCaptor.forClass(Dependency.class);
		verify(dependenciesType).dependencyResolved(captor.capture());
		
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