package org.ravenbuild.tasks;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class TaskRepositoryTest {
	@Test
	public void findReturnsNullWhenTaskIsNotRegistered() {
		final TaskRepository repository = new TaskRepository();
		
		final Task task = repository.findTask("taskName");
		
		assertNull(task);
	}
	
	@Test
	public void findsRegisteredTask() {
		final TaskRepository repository = new TaskRepository();
		final Task expected = mock(Task.class);
		repository.add("taskName", new TaskRepository.TaskInfo(expected, null), "General");
		
		final Task task = repository.findTask("taskName");
		
		assertSame(expected, task);
	}
	
	@Test
	@Ignore("FIXME: Check if task name already exists when adding a new task")
	public void addReportsAnErrorWhenTaskNameAlreadyExists() {
		fail("Implement me");
	}
}