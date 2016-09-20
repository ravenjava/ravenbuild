package org.ravenbuild.plugins.java.intellij;

import org.junit.Test;
import org.ravenbuild.plugins.java.JavaPlugin;
import org.ravenbuild.tasks.TaskContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class IntelliJTaskTest {
	@Test
	public void optionallyDependsOnDependenciesTask() {
		IntelliJTask task = new IntelliJTask();
		
		TaskContext taskContext = mock(TaskContext.class);
		task.initialize(taskContext);
		
		verify(taskContext).dependsOn("dependencies");
	}
}