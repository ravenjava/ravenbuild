package org.ravenbuild.plugins.build;

import org.ravenbuild.plugins.dependencies.DependenciesTask;
import org.ravenbuild.tasks.EmptyTaskOptions;
import org.ravenbuild.tasks.TaskContext;
import org.ravenbuild.tasks.extensible.ExtensibleTask;
import org.ravenbuild.tasks.extensible.ExtensibleTaskSubtasks;

public class CompileTask extends ExtensibleTask<EmptyTaskOptions> {
	private DependenciesTask dependencies;
	
	public CompileTask(final ExtensibleTaskSubtasks buildSubtasksTask, final String subtasksId) {
		super(buildSubtasksTask, subtasksId);
	}
	
	@Override
	public void initialize(final TaskContext taskContext) {
		dependencies = taskContext.dependsOn("dependencies", DependenciesTask.class);
		super.initialize(taskContext);
	}
	
}
