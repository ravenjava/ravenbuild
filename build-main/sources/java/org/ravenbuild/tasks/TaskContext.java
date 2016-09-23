package org.ravenbuild.tasks;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.config.BuildConfiguration;
import org.ravenbuild.projectinfo.ProjectInfo;

public class TaskContext {
	private final TaskRepository.TaskInfo taskInfo;
	private final TaskRepository taskRepository;
	private final ProjectInfo projectInfo;
	
	public TaskContext(final TaskRepository.TaskInfo taskInfo, final TaskRepository taskRepository, final ProjectInfo projectInfo) {
		Args.notNull(taskInfo, "taskInfo");
		Args.notNull(taskRepository, "taskRepository");
		Args.notNull(projectInfo, "projectInfo");
		
		this.taskInfo = taskInfo;
		this.taskRepository = taskRepository;
		this.projectInfo = projectInfo;
	}
	
	public <T> T dependsOn(final String taskId) {
		return dependsOn(taskId, DependencyScope.MANDATORY);
	}
	
	public <T> T dependsOn(final String taskId, final DependencyScope dependencyScope) {
		TaskRepository.TaskInfo dependencyInfo = taskRepository.findTask(taskId);
		taskInfo.addDependency(dependencyInfo);
		return (T) dependencyInfo.getTask();
	}
	
	public ProjectInfo projectInfo() {
		return projectInfo;
	}
}
