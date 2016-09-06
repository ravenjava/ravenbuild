package org.ravenbuild.tasks;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InOrder;
import org.ravenbuild.LogLevel;
import org.ravenbuild.logging.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class TaskGraphTest {
	@Test
	public void informsEventListenersAboutTaskNotFound() {
		TaskGraph taskGraph = new TaskGraph(mock(TaskRepository.class), mock(TaskRunner.class), mock(Logger.class));
		
		final TaskGraphListener listener = mock(TaskGraphListener.class);
		taskGraph.addTaskGraphListener(TaskGraphEvent.TASK_NOT_FOUND, listener);
		
		taskGraph.run("nonExistentTask", mock(Map.class));
		
		verify(listener).onTaskGraphEvent(eq(TaskGraphEvent.TASK_NOT_FOUND), eq("nonExistentTask"), eq(null));
	}
	
	@Test
	public void logsErrorWhenTaskNotFound() {
		Logger logger = mock(Logger.class);
		TaskGraph taskGraph = new TaskGraph(mock(TaskRepository.class), mock(TaskRunner.class), logger);
		
		taskGraph.run("nonExistentTask", mock(Map.class));
		
		verify(logger).log(eq(LogLevel.ERROR), argThat(containsString("Task not found")), argThat(containsString("nonExistentTask")));
	}
	
	@Test
	public void usesTaskRepositoryToFindTask() {
		final TaskRepository taskRepository = mock(TaskRepository.class);
		TaskGraph taskGraph = new TaskGraph(taskRepository, mock(TaskRunner.class), mock(Logger.class));
		
		taskGraph.run("existingTask", mock(Map.class));
		
		verify(taskRepository).findTask("existingTask");
	}
	
	@Test
	public void usesTaskRunnerToRunFoundTask() {
		TaskRunner taskRunner = mock(TaskRunner.class);
		TaskRepository taskRepository = mock(TaskRepository.class);
		TaskGraph taskGraph = new TaskGraph(taskRepository, taskRunner, mock(Logger.class, RETURNS_DEEP_STUBS));
		
		Task task = mock(Task.class);
		when(taskRepository.findTask("existingTask")).thenReturn(new TaskRepository.TaskInfo(task, EmptyTaskOptions.class));

		taskGraph.run("existingTask", mock(Map.class));
		
		verify(taskRunner).run(eq(task), any(), any());
	}
	
	@Test
	public void passesCorrectTaskOptionsTypeWhenRuningFoundTask() {
		TaskRunner taskRunner = mock(TaskRunner.class);
		TaskRepository taskRepository = mock(TaskRepository.class);
		TaskGraph taskGraph = new TaskGraph(taskRepository, taskRunner, mock(Logger.class, RETURNS_DEEP_STUBS));
		
		Task task = mock(Task.class);
		when(taskRepository.findTask("existingTask")).thenReturn(new TaskRepository.TaskInfo(task, EmptyTaskOptions.class));
		
		Map taskOptionsMap = mock(Map.class);
		taskGraph.run("existingTask", taskOptionsMap);
		
		verify(taskRunner).run(any(), eq(EmptyTaskOptions.class), any());
	}
	
	@Test
	public void passesCorrectTaskOptionsWhenRuningFoundTask() {
		TaskRunner taskRunner = mock(TaskRunner.class);
		TaskRepository taskRepository = mock(TaskRepository.class);
		TaskGraph taskGraph = new TaskGraph(taskRepository, taskRunner, mock(Logger.class, RETURNS_DEEP_STUBS));
		
		Task task = mock(Task.class);
		when(taskRepository.findTask("existingTask")).thenReturn(new TaskRepository.TaskInfo(task, EmptyTaskOptions.class));
		
		Map taskOptionsMap = mock(Map.class);
		taskGraph.run("existingTask", taskOptionsMap);
		
		verify(taskRunner).run(any(), any(), eq(taskOptionsMap));
	}
	
	@Test
	public void registerTaskRegistersTaskWithTaskRepository() {
		final TaskRepository taskRepository = mock(TaskRepository.class);
		TaskGraph taskGraph = new TaskGraph(taskRepository, mock(TaskRunner.class), mock(Logger.class));
		
		final Task task = mock(Task.class);
		taskGraph.registerTask("taskName", task, Class.class);
		
		verify(taskRepository).add(eq("taskName"), argThat(hasProperty("task", is(task))), anyString());
	}
	
	@Test
	public void registerTaskRegistersTaskWithCorrectOptions() {
		final TaskRepository taskRepository = mock(TaskRepository.class);
		TaskGraph taskGraph = new TaskGraph(taskRepository, mock(TaskRunner.class), mock(Logger.class));
		
		final Task task = mock(Task.class);
		taskGraph.registerTask("taskName", task, TaskGraphTest.class);
		
		verify(taskRepository).add(eq("taskName"), argThat(hasProperty("taskOptionsType", is((Object) TaskGraphTest.class))), anyString());
	}
	
	@Test
	public void resolvesDependenciesOfAllRegisteredTasksBeforeRunningFirstTask() {
		final TaskRepository taskRepository = mock(TaskRepository.class);
		
		final FakeTask task = spy(new FakeTask());
		final DependencyFakeTask dependency = spy(new DependencyFakeTask());
		
		when(taskRepository.findTask("task")).thenReturn(new TaskRepository.TaskInfo(task, EmptyTaskOptions.class));
		when(taskRepository.findTask("dependency")).thenReturn(new TaskRepository.TaskInfo(dependency, EmptyTaskOptions.class));
		when(taskRepository.allTasks()).thenReturn(Arrays.asList(
				new TaskRepository.TaskInfo(task, EmptyTaskOptions.class),
				new TaskRepository.TaskInfo(dependency, EmptyTaskOptions.class)));
		
		TaskGraph taskGraph = new TaskGraph(taskRepository, mock(TaskRunner.class), mock(Logger.class, RETURNS_DEEP_STUBS));
		
		taskGraph.run("dependency", Collections.emptyMap());
		
		verify(task).initialize(any(TaskContext.class));
		verify(dependency).initialize(any(TaskContext.class));
		assertThat(task.dependency, is(dependency));
	}
	
	@Test
	@Ignore("FIXME: Should report a meaningful error message when a dependency could not be resolved.")
	public void reportsErrorIfDependencyCouldNotBeResolved() {
		fail("Implement me!");
	}
	
	@Test
	@Ignore("FIXME: Should report a meaningful error message when a dependency resolves to a different class than expected.")
	public void reportsErrorIfDependencyResolvesToDifferentClassThanExpected() {
		fail("Implement me!");
	}
	
	@Test
	public void runsPrerequesitsOfCurrentTaskBeforeRunningTheTask() {
		final TaskRepository taskRepository = mock(TaskRepository.class);
		TaskRunner taskRunner = mock(TaskRunner.class);
		TaskGraph taskGraph = new TaskGraph(taskRepository, taskRunner, mock(Logger.class, RETURNS_DEEP_STUBS));
		
		TaskRepository.TaskInfo taskInfo = spy(new TaskRepository.TaskInfo(new FakeTask(), EmptyTaskOptions.class));
		when(taskInfo.getTaskName()).thenReturn("task");
		when(taskRepository.findTask("task")).thenReturn(taskInfo);
		TaskRepository.TaskInfo dependencyInfo = new TaskRepository.TaskInfo(new DependencyFakeTask(), EmptyTaskOptions.class);
		when(taskRepository.findTask("dependency")).thenReturn(dependencyInfo);
		when(taskRepository.allTasks()).thenReturn(Arrays.asList(
				taskInfo,
				dependencyInfo));
		
		taskGraph.run("task", Collections.emptyMap());
		
		final InOrder inOrder = inOrder(taskRunner);
		inOrder.verify(taskRunner).run(eq(dependencyInfo.getTask()), any(), any());
		inOrder.verify(taskRunner).run(eq(taskInfo.getTask()), any(), any());
	}
	
	private class FakeTask implements Task {
		public DependencyFakeTask dependency;
		
		@Override
		public void initialize(final TaskContext taskContext) {
			dependency = taskContext.dependsOn("dependency", DependencyFakeTask.class);
		}
		
		@Override
		public void run(final Object taskOptions) {
			
		}
	}
	
	private class DependencyFakeTask implements Task {
		@Override
		public void initialize(final TaskContext taskContext) {
			
		}
		
		@Override
		public void run(final Object taskOptions) {
			
		}
	}
}