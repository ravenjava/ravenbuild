package org.ravenbuild.plugins.build;

import org.ravenbuild.plugins.help.LongDescription;
import org.ravenbuild.plugins.help.ShortDescription;
import org.ravenbuild.tasks.EmptyTaskOptions;
import org.ravenbuild.tasks.TaskContext;
import org.ravenbuild.tasks.extensible.ExtensibleTask;
import org.ravenbuild.tasks.extensible.ExtensibleTaskSubtasks;

@ShortDescription("Processes all resources of the current project.")
@LongDescription({
		"This task is a collection of all resources tasks for the current project. On ",
		"it's own, it does not do anything, but other plugins can hook different ",
		"resources tasks into this task.",
		"",
		"i.e. any plugin can hook a task to process resources into this task. So if ",
		"you use that plugin, the resources task of the plugin will be executed ",
		"every time you runInAll this resources task."
})
public class ResourcesTask extends ExtensibleTask<EmptyTaskOptions> {
	private CompileTask compile;
	
	public ResourcesTask(final ExtensibleTaskSubtasks buildSubtasksTask, final String subtasksId) {
		super(buildSubtasksTask, subtasksId);
	}
	
	@Override
	public void initialize(final TaskContext taskContext) {
		compile = taskContext.dependsOn("compile");
		super.initialize(taskContext);
	}
	
}
