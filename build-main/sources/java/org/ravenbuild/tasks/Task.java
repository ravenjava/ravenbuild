package org.ravenbuild.tasks;

public interface Task<T> {
	default void initialize(TaskContext taskContext) {
	}
	void run(T taskOptions);
	default boolean shouldRunInSubProjects() {
		return true;
	}
}
