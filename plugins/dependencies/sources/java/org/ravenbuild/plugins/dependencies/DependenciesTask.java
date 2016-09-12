package org.ravenbuild.plugins.dependencies;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.plugins.help.LongDescription;
import org.ravenbuild.plugins.help.ShortDescription;
import org.ravenbuild.tasks.Task;
import org.ravenbuild.tasks.TaskContext;

@ShortDescription("Lists, initializes and updates dependencies of the current project and shows information about available dependency updates.")
@LongDescription({
		"Use the dependencies task to manage the dependencies of your current",
		"project. This task allows you to manage different types of depencencies",
		"(e.g. java, javascript, build file, ...). You can configure your dependencies",
		"in \"raven.json\", section \"dependencies\". Use \"dependency-versions.json\"",
		"to configure the versions of your depencencies.",
		"",
		"When a certain dependency is not available, and no version information is",
		"available in \"dependency-versions.json\", this task will download the",
		"newest version of the dependency and add the version information to the",
		"\"dependency-versions.json\" configuration.",
		"",
		"With the dependencies task, you can also find out if updates are available",
		"for some of your dependencies and update some or all of them. For more",
		"information, refer to the task options \"find-updates\" and \"update\"",
		"below."
})
public class DependenciesTask implements Task<DependenciesTaskOptions> {
	private final DependenciesTaskRunner dependenciesTaskRunner;
	
	public DependenciesTask() {
		this(new DependenciesTaskRunner());
	}
	
	DependenciesTask(final DependenciesTaskRunner dependenciesTaskRunner) {
		Args.notNull(dependenciesTaskRunner, "dependenciesTaskRunner");
		this.dependenciesTaskRunner = dependenciesTaskRunner;
	}
	
	@Override
	public void initialize(final TaskContext taskContext) {
		
	}
	
	@Override
	public void run(final DependenciesTaskOptions taskOptions) {
		if(taskOptions.isInitialize()) {
			dependenciesTaskRunner.initializeDependencies();
		}
	}
}
