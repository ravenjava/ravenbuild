package org.ravenbuild.plugins.build;

import org.ravenbuild.tasks.EmptyTaskOptions;
import org.ravenbuild.tasks.TaskContext;
import org.ravenbuild.tasks.extensible.ExtensibleTask;
import org.ravenbuild.tasks.extensible.ExtensibleTaskSubtasks;

public class UnitTestTask  extends ExtensibleTask<EmptyTaskOptions> {
	private CheckTask check;
	
	public UnitTestTask(final ExtensibleTaskSubtasks buildSubtasksTask, final String subtasksId) {
		super(buildSubtasksTask, subtasksId);
	}
	
	@Override
	public void initialize(final TaskContext taskContext) {
		check = taskContext.dependsOn("check", CheckTask.class);
		super.initialize(taskContext);
	}
}
