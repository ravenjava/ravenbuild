package org.ravenbuild.plugins.build;

import org.ravenbuild.tasks.EmptyTaskOptions;
import org.ravenbuild.tasks.TaskContext;
import org.ravenbuild.tasks.extensible.ExtensibleTask;
import org.ravenbuild.tasks.extensible.ExtensibleTaskSubtasks;

public class CheckTask extends ExtensibleTask<EmptyTaskOptions> {
	private ResourcesTask resources;
	
	public CheckTask(final ExtensibleTaskSubtasks buildSubtasksTask, final String subtasksId) {
		super(buildSubtasksTask, subtasksId);
	}
	
	@Override
	public void initialize(final TaskContext taskContext) {
		resources = taskContext.dependsOn("resources", ResourcesTask.class);
		super.initialize(taskContext);
	}
}
