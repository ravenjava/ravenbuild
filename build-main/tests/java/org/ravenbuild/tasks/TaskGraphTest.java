package org.ravenbuild.tasks;

import org.junit.Test;
import org.ravenbuild.LogLevel;
import org.ravenbuild.logging.Logger;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
		TaskGraph taskGraph = new TaskGraph(taskRepository, taskRunner, mock(Logger.class));
		
		Task task = mock(Task.class);
		when(taskRepository.findTask("existingTask")).thenReturn(task);
		when(taskRepository.getTaskOptionsType("existingTask")).thenReturn(TaskGraphTest.class);

		taskGraph.run("existingTask", mock(Map.class));
		
		verify(taskRunner).run(eq(task), any(), any());
	}
	
	@Test
	public void passesCorrectTaskOptionsTypeWhenRuningFoundTask() {
		TaskRunner taskRunner = mock(TaskRunner.class);
		TaskRepository taskRepository = mock(TaskRepository.class);
		TaskGraph taskGraph = new TaskGraph(taskRepository, taskRunner, mock(Logger.class));
		
		Task task = mock(Task.class);
		when(taskRepository.findTask("existingTask")).thenReturn(task);
		when(taskRepository.getTaskOptionsType("existingTask")).thenReturn(TaskGraphTest.class);
		
		Map taskOptionsMap = mock(Map.class);
		taskGraph.run("existingTask", taskOptionsMap);
		
		verify(taskRunner).run(any(), eq(TaskGraphTest.class), any());
	}
	
	@Test
	public void passesCorrectTaskOptionsWhenRuningFoundTask() {
		TaskRunner taskRunner = mock(TaskRunner.class);
		TaskRepository taskRepository = mock(TaskRepository.class);
		TaskGraph taskGraph = new TaskGraph(taskRepository, taskRunner, mock(Logger.class));
		
		Task task = mock(Task.class);
		when(taskRepository.findTask("existingTask")).thenReturn(task);
		when(taskRepository.getTaskOptionsType("existingTask")).thenReturn(TaskGraphTest.class);
		
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
}