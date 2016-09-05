package org.ravenbuild.plugins.build;

import org.ravenbuild.plugins.dependencies.DependenciesTask;
import org.ravenbuild.plugins.help.LongDescription;
import org.ravenbuild.plugins.help.ShortDescription;
import org.ravenbuild.tasks.EmptyTaskOptions;
import org.ravenbuild.tasks.Task;
import org.ravenbuild.tasks.TaskContext;
import org.ravenbuild.tasks.extensible.ExtensibleTask;
import org.ravenbuild.tasks.extensible.ExtensibleTaskSubtasks;

@ShortDescription("Builds the project. Includes the tasks \"dependencies\", \"compile\", \"check\", \"unitTest\" and \"functionalTest\".")
@LongDescription({
		"This task is just a collection of other tasks. It executes the following tasks:",
		"   * \"dependencies\"   Initializes the dependencies of the project.",
		"   * \"compile\"        Compiles all sources of the project.",
		"   * \"resources\"      Processes all resources of the project.",
		"   * \"check\"          Runs static analysis tools.",
		"   * \"unitTest\"       Runs all unit tests in the current project.",
		"   * \"functionalTest\" Runs all functional tests of the project"
})
public class BuildTask extends ExtensibleTask<EmptyTaskOptions> {
	private FunctionalTestTask functionalTest;
	
	public BuildTask(final ExtensibleTaskSubtasks buildSubtasksTask, final String subtasksId) {
		super(buildSubtasksTask, subtasksId);
	}
	
	@Override
	public void initialize(final TaskContext taskContext) {
		functionalTest = taskContext.dependsOn("functionalTest", FunctionalTestTask.class);
		super.initialize(taskContext);
	}
}
