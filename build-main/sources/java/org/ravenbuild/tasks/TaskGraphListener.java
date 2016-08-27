package org.ravenbuild.tasks;

@FunctionalInterface
public interface TaskGraphListener {
	void onTaskGraphEvent(TaskGraphEvent event, String taskName, Task task);
}
