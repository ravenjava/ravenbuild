package org.ravenbuild.plugins.build;

import org.ravenbuild.plugins.help.LongDescription;
import org.ravenbuild.plugins.help.ShortDescription;
import org.ravenbuild.tasks.EmptyTaskOptions;
import org.ravenbuild.tasks.TaskContext;
import org.ravenbuild.tasks.extensible.ExtensibleTask;
import org.ravenbuild.tasks.extensible.ExtensibleTaskSubtasks;

@ShortDescription("Runs all functional tests in the current project.")
@LongDescription({
		"This task is a collection of all functional test tasks for the current project. On ",
		"it's own, it does not do anything, but other plugins can hook different ",
		"functional test tasks into this task.",
		"",
		"i.e. the FitNesse plugin can hook a task to runInAll tests into this task. So if ",
		"you use the FitNesse plugin, the test task of the plugin will be executed ",
		"every time you runInAll this functionalTest task."
})
public class FunctionalTestTask extends ExtensibleTask<EmptyTaskOptions> {
	private UnitTestTask unitTest;
	
	public FunctionalTestTask(final ExtensibleTaskSubtasks buildSubtasksTask, final String subtasksId) {
		super(buildSubtasksTask, subtasksId);
	}
	
	@Override
	public void initialize(final TaskContext taskContext) {
		unitTest = taskContext.dependsOn("unitTest");
		super.initialize(taskContext);
	}
}
