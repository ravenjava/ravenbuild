package org.ravenbuild.tasks;

import net.davidtanzer.jdefensive.Args;

public class TaskContext {
	private final TaskRepository.TaskInfo taskInfo;
	private final TaskRepository taskRepository;
	
	public TaskContext(final TaskRepository.TaskInfo taskInfo, final TaskRepository taskRepository) {
		Args.notNull(taskInfo, "taskInfo");
		Args.notNull(taskRepository, "taskRepository");
		
		this.taskInfo = taskInfo;
		this.taskRepository = taskRepository;
	}
	
	public <T> T dependsOn(final String taskId, final Class<T> dependencyType) {
		TaskRepository.TaskInfo dependencyInfo = taskRepository.findTask(taskId);
		taskInfo.addDependency(dependencyInfo);
		return (T) dependencyInfo.getTask();
	}
}
