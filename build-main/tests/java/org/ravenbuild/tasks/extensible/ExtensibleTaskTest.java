package org.ravenbuild.tasks.extensible;

import org.junit.Test;
import org.ravenbuild.tasks.Task;
import org.ravenbuild.tasks.TaskContext;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ExtensibleTaskTest {
	@Test
	public void addsDependencyToSubtask() {
		ExtensibleTaskSubtasks subtasks = new ExtensibleTaskSubtasks() {
			@Override
			public void run(final Object taskOptions) {
			}
		};
		ExtensibleTestTask task = new ExtensibleTestTask(subtasks);
		
		TaskContext taskContext = mock(TaskContext.class);
		TaskContext subtasksContext = mock(TaskContext.class);
		
		task.initialize(taskContext);
		subtasks.initialize(subtasksContext);
		
		task.addDependency("dependency", TestDependency.class);
		
		verify(subtasksContext).dependsOn(eq("dependency"), eq(TestDependency.class));
	}
	
	private class ExtensibleTestTask extends ExtensibleTask {
		public ExtensibleTestTask(final ExtensibleTaskSubtasks subtasksTask) {
			super(subtasksTask);
		}
		
		@Override
		public void initialize(final TaskContext taskContext) {
			
		}
		
		@Override
		public void run(final Object taskOptions) {
			
		}
	}
	
	private abstract class TestDependency implements Task {
	}
}