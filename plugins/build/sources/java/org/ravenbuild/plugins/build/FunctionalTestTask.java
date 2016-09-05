package org.ravenbuild.plugins.build;

import org.ravenbuild.tasks.EmptyTaskOptions;
import org.ravenbuild.tasks.TaskContext;
import org.ravenbuild.tasks.extensible.ExtensibleTask;
import org.ravenbuild.tasks.extensible.ExtensibleTaskSubtasks;

public class FunctionalTestTask extends ExtensibleTask<EmptyTaskOptions> {
	private UnitTestTask unitTest;
	
	public FunctionalTestTask(final ExtensibleTaskSubtasks buildSubtasksTask, final String subtasksId) {
		super(buildSubtasksTask, subtasksId);
	}
	
	@Override
	public void initialize(final TaskContext taskContext) {
		unitTest = taskContext.dependsOn("unitTest", UnitTestTask.class);
		super.initialize(taskContext);
	}
}
