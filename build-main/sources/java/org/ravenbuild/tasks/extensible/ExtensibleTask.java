package org.ravenbuild.tasks.extensible;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.tasks.Task;

public abstract class ExtensibleTask<T> implements Task<T> {
	private final ExtensibleTaskSubtasks subtasksTask;
	
	public ExtensibleTask(final ExtensibleTaskSubtasks subtasksTask) {
		Args.notNull(subtasksTask, "subtasksTask");
		
		this.subtasksTask = subtasksTask;
	}
	
	public void addDependency(final String dependencyName, final Class<? extends Task> dependencyType) {
		subtasksTask.addDependency(dependencyName, dependencyType);
	}
}
