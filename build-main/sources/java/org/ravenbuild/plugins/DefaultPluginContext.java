package org.ravenbuild.plugins;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.config.BuildConfiguration;
import org.ravenbuild.logging.Logger;
import org.ravenbuild.projectinfo.AllProjects;
import org.ravenbuild.tasks.Task;
import org.ravenbuild.tasks.TaskGraph;
import org.ravenbuild.tasks.TaskRepository;

class DefaultPluginContext implements PluginContext {
	private final PluginSystem pluginSystem;
	private final TaskGraph taskGraph;
	private final TaskRepository taskRepository;
	private final BuildConfiguration configuration;
	private final AllProjects allProjects;
	private final Logger logger;
	
	public DefaultPluginContext(final PluginSystem pluginSystem, final TaskGraph taskGraph, final TaskRepository taskRepository,
			final BuildConfiguration configuration, final AllProjects allProjects, final Logger logger) {
		Args.notNull(pluginSystem, "pluginSystem");
		Args.notNull(taskGraph, "taskGraph");
		Args.notNull(taskRepository, "taskRepository");
		Args.notNull(configuration, "configuration");
		Args.notNull(allProjects, "allProjects");
		Args.notNull(logger, "logger");
		
		this.pluginSystem = pluginSystem;
		this.taskGraph = taskGraph;
		this.taskRepository = taskRepository;
		this.configuration = configuration;
		this.allProjects = allProjects;
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
	public BuildConfiguration configuration() {
		return configuration;
	}
	
	@Override
	public <T extends BuildPlugin> T dependsOnPlugin(final Class<T> dependency) {
		return pluginSystem.loadAndInitialize(dependency, PluginSystem.LoadAs.DEPENDENCY);
	}
	
	@Override
	public AllProjects allProjects() {
		return allProjects;
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
