package org.ravenbuild.plugins.java.intellij;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.plugins.dependencies.DependenciesTask;
import org.ravenbuild.plugins.help.LongDescription;
import org.ravenbuild.plugins.help.ShortDescription;
import org.ravenbuild.plugins.java.JavaPlugin;
import org.ravenbuild.tasks.EmptyTaskOptions;
import org.ravenbuild.tasks.Task;
import org.ravenbuild.tasks.TaskContext;

@ShortDescription("Writes IntelliJ IDEA project files for the current project.")
@LongDescription({
		"Use this task to write the IntelliJ IDEA project files for the current project.",
		"After running this task, you can import / re-import the project in IntelliJ",
		"IDEA."
})
public class IntelliJTask implements Task<EmptyTaskOptions> {
	private DependenciesTask dependenciesTask;
	
	public IntelliJTask() {
	}
	
	@Override
	public void initialize(final TaskContext taskContext) {
		dependenciesTask = taskContext.dependsOn("dependencies");
	}
	
	@Override
	public void run(final EmptyTaskOptions taskOptions) {
		
	}
}
