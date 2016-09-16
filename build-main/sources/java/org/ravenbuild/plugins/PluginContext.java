package org.ravenbuild.plugins;

import org.ravenbuild.config.BuildConfiguration;
import org.ravenbuild.environment.BuildEnvironment;
import org.ravenbuild.logging.Logger;
import org.ravenbuild.projectinfo.AllProjects;
import org.ravenbuild.tasks.Task;
import org.ravenbuild.tasks.TaskGraph;
import org.ravenbuild.tasks.TaskRepository;

public interface PluginContext {
	void registerTask(String name, Task task, Class<?> taskOptionsType);
	void registerTask(String name, Task task, Class<?> taskOptionsType, String taskGroupName);
	
	Logger logger();
	TaskGraph taskGraph();
	TaskRepository taskRepository();
	BuildConfiguration configuration();
	
	<T extends BuildPlugin> T dependsOnPlugin(Class<T> dependency);
	
	AllProjects allProjects();
	
	BuildEnvironment buildEnvironment();
}
