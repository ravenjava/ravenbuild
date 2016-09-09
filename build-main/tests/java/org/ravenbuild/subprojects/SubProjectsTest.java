package org.ravenbuild.subprojects;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.ravenbuild.logging.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class SubProjectsTest {
	@Test
	public void initializesSubProjectBuilderForEverySubProject() {
		HashMap<String, Object> config = new HashMap<String, Object>() {{
			put("list", Arrays.asList("subproject1", "subproject2"));
		}};
		SubProjectsFactory subProjectBuilderFactory = mock(SubProjectsFactory.class, RETURNS_DEEP_STUBS);
		Logger logger = mock(Logger.class);
		SubProjects subProjects = new SubProjects(subProjectBuilderFactory, logger, ProjectType.SUB_PROJECT);
		
		subProjects.load(config, null);
		
		verify(subProjectBuilderFactory).getSubProjectBuilder(argThat(hasProperty("path", is("subproject1"))), anyMap());
		verify(subProjectBuilderFactory).getSubProjectBuilder(argThat(hasProperty("path", is("subproject2"))), anyMap());
	}
	
	@Test
	public void runInAllRunsTaskInAllSubProjects() {
		HashMap<String, Object> config = new HashMap<String, Object>() {{
			put("list", Arrays.asList("subproject1", "subproject2"));
		}};
		SubProjectsFactory subProjectBuilderFactory = mock(SubProjectsFactory.class);
		SubProjectBuilder subProjectBuilder1 = mock(SubProjectBuilder.class);
		SubProjectBuilder subProjectBuilder2 = mock(SubProjectBuilder.class);
		when(subProjectBuilderFactory.getSubProjectBuilder(any(), anyMap()))
				.thenReturn(subProjectBuilder1)
				.thenReturn(subProjectBuilder2);
		Logger logger = mock(Logger.class);
		SubProjects subProjects = new SubProjects(subProjectBuilderFactory, logger, ProjectType.MAIN_PROJECT);
		
		subProjects.load(config, null);
		subProjects.runInAll("task", mock(Map.class));
		
		verify(subProjectBuilder1).run(eq("task"), any(Map.class), any());
		verify(subProjectBuilder2).run(eq("task"), any(Map.class), any());
	}
	
	@Test
	public void initializesSubProjectBuilderWithParentConfiguration() {
		HashMap<String, Object> config = new HashMap<String, Object>() {{
			put("list", Arrays.asList("subproject1"));
			put("foo", "bar");
		}};
		SubProjectsFactory subProjectBuilderFactory = mock(SubProjectsFactory.class, RETURNS_DEEP_STUBS);
		Logger logger = mock(Logger.class);
		SubProjects subProjects = new SubProjects(subProjectBuilderFactory, logger, ProjectType.SUB_PROJECT);
		
		subProjects.load(config, null);
		
		ArgumentCaptor<Map> captor = ArgumentCaptor.forClass(Map.class);
		verify(subProjectBuilderFactory).getSubProjectBuilder(any(), captor.capture());
		
		Map<String, Object> parentConfig = captor.getValue();
		assertThat(parentConfig, hasEntry("foo", "bar"));
	}
	
	@Test
	@Ignore("FIXME: Should report a meaningful error message when subproject directory does not exist")
	public void reportsErrorWhenSubprojectDirectoryDoesNotExist() {
		fail("Implement me");
	}
}