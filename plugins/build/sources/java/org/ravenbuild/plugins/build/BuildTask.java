package org.ravenbuild.plugins.build;

import org.ravenbuild.plugins.help.LongDescription;
import org.ravenbuild.plugins.help.ShortDescription;
import org.ravenbuild.tasks.EmptyTaskOptions;
import org.ravenbuild.tasks.Task;

@ShortDescription("Builds the project. Includes the tasks \"dependencies\", \"compile\", \"check\", \"unitTest\" and \"functionalTest\".")
@LongDescription({
		"This task is just a collection of other tasks. It executes the following tasks:",
		"   * \"dependencies\"   Initializes the dependencies of the project.",
		"   * \"compile\"        Compiles all sources of the project.",
		"   * \"check\"          Runs static analysis tools.",
		"   * \"unitTest\"       Runs all unit tests in the current project.",
		"   * \"functionalTest\" Runs all functional tests of the project"
})
public class BuildTask implements Task<EmptyTaskOptions> {
	@Override
	public void run(final EmptyTaskOptions taskOptions) {
	}
}
