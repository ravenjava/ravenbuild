package org.ravenbuild.plugins.build;

import org.ravenbuild.plugins.help.LongDescription;
import org.ravenbuild.plugins.help.ShortDescription;
import org.ravenbuild.tasks.EmptyTaskOptions;
import org.ravenbuild.tasks.TaskContext;
import org.ravenbuild.tasks.extensible.ExtensibleTask;
import org.ravenbuild.tasks.extensible.ExtensibleTaskSubtasks;

@ShortDescription("Runs all unit tests in the current project.")
@LongDescription({
		"This task is a collection of all unit test tasks for the current project. On ",
		"it's own, it does not do anything, but other plugins can hook different ",
		"unit test tasks into this task.",
		"",
		"i.e. the JUnit plugin can hook a task to run tests into this task. So if ",
		"you use the JUnit plugin, the test task of the plugin will be executed ",
		"every time you run this unitTest task."
})
public class UnitTestTask  extends ExtensibleTask<EmptyTaskOptions> {
	private CheckTask check;
	
	public UnitTestTask(final ExtensibleTaskSubtasks buildSubtasksTask, final String subtasksId) {
		super(buildSubtasksTask, subtasksId);
	}
	
	@Override
	public void initialize(final TaskContext taskContext) {
		check = taskContext.dependsOn("check");
		super.initialize(taskContext);
	}
}
