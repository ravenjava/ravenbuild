package org.ravenbuild.tasks.extensible;

import org.junit.Test;
import org.ravenbuild.tasks.EmptyTaskOptions;
import org.ravenbuild.tasks.Task;
import org.ravenbuild.tasks.TaskContext;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ExtensibleTaskTest {
	@Test
	public void addsDependencyToSubtask() {
		ExtensibleTaskSubtasks subtasks = new ExtensibleTaskSubtasks();
		ExtensibleTestTask task = new ExtensibleTestTask(subtasks, "someId");
		
		TaskContext taskContext = mock(TaskContext.class);
		TaskContext subtasksContext = mock(TaskContext.class);
		
		task.initialize(taskContext);
		subtasks.initialize(subtasksContext);
		
		task.addDependency("dependency", TestDependency.class);
		
		verify(subtasksContext).dependsOn(eq("dependency"), eq(TestDependency.class));
	}
	
	private class ExtensibleTestTask extends ExtensibleTask {
		public ExtensibleTestTask(final ExtensibleTaskSubtasks subtasksTask, final String subtasksId) {
			super(subtasksTask, subtasksId);
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