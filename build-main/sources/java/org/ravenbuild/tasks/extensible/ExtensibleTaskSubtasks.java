package org.ravenbuild.tasks.extensible;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.tasks.Task;
import org.ravenbuild.tasks.TaskContext;

import java.util.ArrayList;
import java.util.List;

public abstract class ExtensibleTaskSubtasks<T> implements Task<T> {
	private TaskContext taskContext;
	private final List<Task> dependencies = new ArrayList<>();
	
	@Override
	public void initialize(final TaskContext taskContext) {
		Args.notNull(taskContext, "taskContext");
		
		this.taskContext = taskContext;
	}
	
	void addDependency(final String dependencyName, final Class<? extends Task> dependencyType) {
		Task dependency = taskContext.dependsOn(dependencyName, dependencyType);
		dependencies.add(dependency);
	}
}
