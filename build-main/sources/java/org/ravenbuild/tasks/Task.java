package org.ravenbuild.tasks;

public interface Task<T> {
	void run(T taskOptions);
}
