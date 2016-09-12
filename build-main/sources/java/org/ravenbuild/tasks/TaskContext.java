package org.ravenbuild.tasks;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.config.BuildConfiguration;

public class TaskContext {
	private final TaskRepository.TaskInfo taskInfo;
	private final TaskRepository taskRepository;
	private final BuildConfiguration configuration;
	
	public TaskContext(final TaskRepository.TaskInfo taskInfo, final TaskRepository taskRepository, final BuildConfiguration configuration) {
		Args.notNull(taskInfo, "taskInfo");
		Args.notNull(taskRepository, "taskRepository");
		Args.notNull(configuration, "configuration");
		
		this.taskInfo = taskInfo;
		this.taskRepository = taskRepository;
		this.configuration = configuration;
	}
	
	public <T> T dependsOn(final String taskId) {
		return dependsOn(taskId, DependencyScope.MANDATORY);
	}
	
	public <T> T dependsOn(final String taskId, final DependencyScope dependencyScope) {
		TaskRepository.TaskInfo dependencyInfo = taskRepository.findTask(taskId);
		taskInfo.addDependency(dependencyInfo);
		return (T) dependencyInfo.getTask();
	}
	
	public BuildConfiguration configuration() {
		return configuration;
	}
}
