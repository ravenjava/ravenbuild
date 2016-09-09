package org.ravenbuild.subprojects;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.BuildOptions;
import org.ravenbuild.classpath.FastClasspathClasspathScanner;
import org.ravenbuild.config.BuildConfiguration;
import org.ravenbuild.logging.Logger;
import org.ravenbuild.plugins.PluginSystem;
import org.ravenbuild.tasks.TaskGraph;
import org.ravenbuild.tasks.TaskRepository;
import org.ravenbuild.tasks.TaskRunner;

import java.util.Map;

public class SubProjectsFactory {
	private final Logger logger;
	private final BuildOptions buildOptions;
	
	public SubProjectsFactory(final Logger logger, final BuildOptions buildOptions) {
		Args.notNull(logger, "logger");
		Args.notNull(buildOptions, "buildOptions");
		
		this.logger = logger;
		this.buildOptions = buildOptions;
	}
	
	public SubProjectBuilder getSubProjectBuilder(final SubProject subProject, final Map<String, Object> parentConfiguration) {
		TaskRepository taskRepository = new TaskRepository();
		TaskRunner taskRunner = new TaskRunner();
		TaskGraph taskgraph = new TaskGraph(taskRepository, taskRunner, logger);
		FastClasspathClasspathScanner classpathScanner = new FastClasspathClasspathScanner();
		PluginSystem pluginSystem = new PluginSystem(taskgraph, taskRepository, classpathScanner, logger);
		
		BuildConfiguration buildConfiguration = new BuildConfiguration(parentConfiguration);
		buildConfiguration.load(subProject.getPath()+"/"+buildOptions.buildConfigFile());
		
		return new SubProjectBuilder(buildOptions, buildConfiguration, taskgraph, classpathScanner, pluginSystem, this);
	}
	
	public SubProjects createSubProjects(final ProjectType projectType) {
		return new SubProjects(this, logger, projectType);
	}
}
