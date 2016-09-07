package org.ravenbuild.tasks.extensible;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.tasks.EmptyTaskOptions;
import org.ravenbuild.tasks.Task;
import org.ravenbuild.tasks.TaskContext;

import java.util.ArrayList;
import java.util.List;

public class ExtensibleTaskSubtasks implements Task<EmptyTaskOptions> {
	private TaskContext taskContext;
	private final List<Task> dependencies = new ArrayList<>();
	
	@Override
	public void initialize(final TaskContext taskContext) {
		Args.notNull(taskContext, "taskContext");
		
		this.taskContext = taskContext;
	}
	
	@Override
	public void run(final EmptyTaskOptions taskOptions) {
	}
	
	void addDependency(final String dependencyName) {
		Task dependency = taskContext.dependsOn(dependencyName);
		dependencies.add(dependency);
	}
}
