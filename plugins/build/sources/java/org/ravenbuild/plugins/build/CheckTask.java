package org.ravenbuild.plugins.build;

import org.ravenbuild.plugins.help.LongDescription;
import org.ravenbuild.plugins.help.ShortDescription;
import org.ravenbuild.tasks.EmptyTaskOptions;
import org.ravenbuild.tasks.TaskContext;
import org.ravenbuild.tasks.extensible.ExtensibleTask;
import org.ravenbuild.tasks.extensible.ExtensibleTaskSubtasks;

@ShortDescription("Runs static code analysis and other checks in the current project.")
@LongDescription({
		"This task is a collection of all check / analysis tasks for the current project. On ",
		"it's own, it does not do anything, but other plugins can hook different ",
		"check / analysis tasks into this task.",
		"",
		"i.e. the sonar plugin can hook a task to run sonar analysis into this task. So if ",
		"you use the sonar plugin, the analysis task of the plugin will be executed ",
		"every time you run this check task."
})
public class CheckTask extends ExtensibleTask<EmptyTaskOptions> {
	private ResourcesTask resources;
	
	public CheckTask(final ExtensibleTaskSubtasks buildSubtasksTask, final String subtasksId) {
		super(buildSubtasksTask, subtasksId);
	}
	
	@Override
	public void initialize(final TaskContext taskContext) {
		resources = taskContext.dependsOn("resources");
		super.initialize(taskContext);
	}
}
