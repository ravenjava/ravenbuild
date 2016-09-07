package org.ravenbuild.plugins.java.intellij;

import org.junit.Test;
import org.ravenbuild.plugins.dependencies.DependenciesPlugin;
import org.ravenbuild.plugins.dependencies.DependenciesTask;
import org.ravenbuild.plugins.projectstructure.ProjectStructurePlugin;
import org.ravenbuild.tasks.DependencyScope;
import org.ravenbuild.tasks.TaskContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class IntelliJTaskTest {
	@Test
	public void optionallyDependsOnDependenciesTask() {
		ProjectStructurePlugin projectStructure = mock(ProjectStructurePlugin.class);
		IntelliJTask task = new IntelliJTask(projectStructure);
		
		TaskContext taskContext = mock(TaskContext.class);
		task.initialize(taskContext);
		
		verify(taskContext).dependsOn("dependencies");
	}
}