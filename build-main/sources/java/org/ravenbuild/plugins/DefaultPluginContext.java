package org.ravenbuild.plugins;

import org.ravenbuild.logging.Logger;
import org.ravenbuild.tasks.Task;
import org.ravenbuild.tasks.TaskGraph;
import org.ravenbuild.tasks.TaskRepository;

class DefaultPluginContext implements PluginContext {
	private final PluginSystem pluginSystem;
	private final TaskGraph taskGraph;
	private final TaskRepository taskRepository;
	private final Logger logger;
	
	public DefaultPluginContext(final PluginSystem pluginSystem, final TaskGraph taskGraph, final TaskRepository taskRepository, final Logger logger) {
		this.pluginSystem = pluginSystem;
		this.taskGraph = taskGraph;
		this.taskRepository = taskRepository;
		this.logger = logger;
	}
	
	@Override
	public Logger logger() {
		return logger;
	}
	
	@Override
	public TaskGraph taskGraph() {
		return taskGraph;
	}
	
	@Override
	public TaskRepository taskRepository() {
		return taskRepository;
	}
	
	@Override
	public <T extends BuildPlugin> T dependsOnPlugin(final Class<T> dependency) {
		return pluginSystem.loadAndInitialize(dependency, PluginSystem.LoadAs.DEPENDENCY);
	}
	
	@Override
	public void registerTask(final String name, final Task task, final Class<?> taskOptionsType) {
		taskGraph.registerTask(name, task, taskOptionsType);
	}
	
	@Override
	public void registerTask(final String name, final Task task, final Class<?> taskOptionsType, final String taskGroupName) {
		taskGraph.registerTask(name, task, taskOptionsType, taskGroupName);
	}
}
