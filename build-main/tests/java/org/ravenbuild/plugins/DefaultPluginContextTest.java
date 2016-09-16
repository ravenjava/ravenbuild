package org.ravenbuild.plugins;

import org.junit.Test;
import org.ravenbuild.config.BuildConfiguration;
import org.ravenbuild.environment.BuildEnvironment;
import org.ravenbuild.logging.Logger;
import org.ravenbuild.projectinfo.AllProjects;
import org.ravenbuild.tasks.Task;
import org.ravenbuild.tasks.TaskGraph;
import org.ravenbuild.tasks.TaskRepository;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DefaultPluginContextTest {
	@Test
	public void registerTaskRegistersTaskWithTheTaskGraph() {
		TaskGraph taskGraph = mock(TaskGraph.class);
		final DefaultPluginContext pluginContext = new DefaultPluginContext(
				mock(PluginSystem.class), taskGraph, mock(TaskRepository.class), mock(BuildConfiguration.class),
				mock(AllProjects.class), mock(BuildEnvironment.class), mock(Logger.class));
		
		final Task task = mock(Task.class);
		pluginContext.registerTask("taskName", task, Class.class);
		
		verify(taskGraph).registerTask(eq("taskName"), eq(task), eq(Class.class));
	}
}
