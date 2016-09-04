package org.ravenbuild.tasks;

import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class TaskRepositoryTest {
	@Test
	public void findReturnsNullWhenTaskIsNotRegistered() {
		final TaskRepository repository = new TaskRepository();
		
		final TaskRepository.TaskInfo task = repository.findTask("taskName");
		
		assertNull(task);
	}
	
	@Test
	public void findsRegisteredTask() {
		final TaskRepository repository = new TaskRepository();
		TaskRepository.TaskInfo expected = new TaskRepository.TaskInfo(mock(Task.class), EmptyTaskOptions.class);
		repository.add("taskName", expected, "General");
		
		final TaskRepository.TaskInfo task = repository.findTask("taskName");
		
		assertSame(expected, task);
	}
	
	@Test
	public void findsAllRegisteredTaskGroups() {
		final TaskRepository repository = new TaskRepository();
		repository.add("generalTask", new TaskRepository.TaskInfo(mock(Task.class), EmptyTaskOptions.class), "General");
		repository.add("specialTask", new TaskRepository.TaskInfo(mock(Task.class), EmptyTaskOptions.class), "Special");
		
		final List<TaskGroup> groups = repository.allTaskGroups();
		
		assertThat(groups, containsInAnyOrder(hasProperty("name", is("General")), hasProperty("name", is("Special"))));
	}
	
	@Test
	@Ignore("FIXME: Check if task name already exists when adding a new task")
	public void addReportsAnErrorWhenTaskNameAlreadyExists() {
		fail("Implement me");
	}
}