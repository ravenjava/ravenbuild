package org.ravenbuild.plugins;

import org.ravenbuild.logging.Logger;
import org.ravenbuild.tasks.Task;
import org.ravenbuild.tasks.TaskGraph;

class DefaultPluginContext implements PluginContext {
	private final PluginSystem pluginSystem;
	private final TaskGraph taskGraph;
	private final Logger logger;
	
	public DefaultPluginContext(final PluginSystem pluginSystem, final TaskGraph taskGraph, final Logger logger) {
		this.pluginSystem = pluginSystem;
		this.taskGraph = taskGraph;
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
	public void registerTask(final String name, final Task task, final Class<?> taskOptionsType) {
		taskGraph.registerTask(name, task, taskOptionsType);
	}
}
