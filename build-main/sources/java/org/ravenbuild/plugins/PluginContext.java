package org.ravenbuild.plugins;

import org.ravenbuild.logging.Logger;
import org.ravenbuild.tasks.Task;
import org.ravenbuild.tasks.TaskGraph;
import org.ravenbuild.tasks.TaskRepository;

public interface PluginContext {
	void registerTask(String name, Task task, Class<?> taskOptionsType);
	void registerTask(String name, Task task, Class<?> taskOptionsType, String taskGroupName);
	
	Logger logger();
	TaskGraph taskGraph();
	TaskRepository taskRepository();
	
	<T extends BuildPlugin> T dependsOnPlugin(Class<T> dependency);
}
