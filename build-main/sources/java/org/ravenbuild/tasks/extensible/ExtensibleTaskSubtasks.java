package org.ravenbuild.tasks.extensible;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.tasks.Task;
import org.ravenbuild.tasks.TaskContext;

public abstract class ExtensibleTaskSubtasks<T> implements Task<T> {
	private TaskContext taskContext;
	
	@Override
	public void initialize(final TaskContext taskContext) {
		Args.notNull(taskContext, "taskContext");
		
		this.taskContext = taskContext;
	}
}
