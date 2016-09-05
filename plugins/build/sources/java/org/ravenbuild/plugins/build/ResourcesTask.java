package org.ravenbuild.plugins.build;

import org.ravenbuild.tasks.EmptyTaskOptions;
import org.ravenbuild.tasks.TaskContext;
import org.ravenbuild.tasks.extensible.ExtensibleTask;
import org.ravenbuild.tasks.extensible.ExtensibleTaskSubtasks;

public class ResourcesTask extends ExtensibleTask<EmptyTaskOptions> {
	private CompileTask compile;
	
	public ResourcesTask(final ExtensibleTaskSubtasks buildSubtasksTask, final String subtasksId) {
		super(buildSubtasksTask, subtasksId);
	}
	
	@Override
	public void initialize(final TaskContext taskContext) {
		compile = taskContext.dependsOn("compile", CompileTask.class);
		super.initialize(taskContext);
	}
	
}
