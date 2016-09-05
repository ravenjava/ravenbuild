package org.ravenbuild.tasks.extensible;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.tasks.Task;
import org.ravenbuild.tasks.TaskContext;

public abstract class ExtensibleTask<T> implements Task<T> {
	private final ExtensibleTaskSubtasks subtasksTask;
	private final String subtasksId;
	
	public ExtensibleTask(final ExtensibleTaskSubtasks subtasksTask, final String subtasksId) {
		Args.notNull(subtasksTask, "subtasksTask");
		Args.notEmpty(subtasksId, "subtasksId");
		
		this.subtasksTask = subtasksTask;
		this.subtasksId = subtasksId;
	}
	
	public void addDependency(final String dependencyName, final Class<? extends Task> dependencyType) {
		subtasksTask.addDependency(dependencyName, dependencyType);
	}
	
	public void initialize(final TaskContext taskContext) {
		taskContext.dependsOn(subtasksId, subtasksTask.getClass());
	}
	
	@Override
	public void run(final T taskOptions) {
	}
}
