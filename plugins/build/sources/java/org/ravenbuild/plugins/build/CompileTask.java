package org.ravenbuild.plugins.build;

import org.ravenbuild.plugins.dependencies.DependenciesTask;
import org.ravenbuild.plugins.help.LongDescription;
import org.ravenbuild.plugins.help.ShortDescription;
import org.ravenbuild.tasks.EmptyTaskOptions;
import org.ravenbuild.tasks.TaskContext;
import org.ravenbuild.tasks.extensible.ExtensibleTask;
import org.ravenbuild.tasks.extensible.ExtensibleTaskSubtasks;

@ShortDescription("Compiles all sources of the current project.")
@LongDescription({
		"This task is a collection of all compile tasks for the current project. On ",
		"it's own, it does not do anything, but other plugins can hook different ",
		"compile tasks into this task.",
		"",
		"i.e. the Java plugin will hook a javac compile task into this task. So if ",
		"you use the Java plugin, the javac task will be executed every time you ",
		"run this compile task."
})
public class CompileTask extends ExtensibleTask<EmptyTaskOptions> {
	private DependenciesTask dependencies;
	
	public CompileTask(final ExtensibleTaskSubtasks buildSubtasksTask, final String subtasksId) {
		super(buildSubtasksTask, subtasksId);
	}
	
	@Override
	public void initialize(final TaskContext taskContext) {
		dependencies = taskContext.dependsOn("dependencies");
		super.initialize(taskContext);
	}
	
}
