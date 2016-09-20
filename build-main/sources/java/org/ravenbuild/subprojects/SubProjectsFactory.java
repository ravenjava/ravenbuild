package org.ravenbuild.subprojects;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.BuildOptions;
import org.ravenbuild.classpath.FastClasspathClasspathScanner;
import org.ravenbuild.config.BuildConfiguration;
import org.ravenbuild.environment.BuildEnvironment;
import org.ravenbuild.logging.Logger;
import org.ravenbuild.plugins.PluginSystem;
import org.ravenbuild.projectinfo.AllProjects;
import org.ravenbuild.projectinfo.ProjectInfo;
import org.ravenbuild.tasks.TaskGraph;
import org.ravenbuild.tasks.TaskRepository;
import org.ravenbuild.tasks.TaskRunner;

import java.util.Map;

public class SubProjectsFactory {
	private final Logger logger;
	private final BuildOptions buildOptions;
	private final AllProjects allProjects;
	private BuildEnvironment buildEnvironment;
	
	public SubProjectsFactory(final Logger logger, final BuildOptions buildOptions, final BuildEnvironment buildEnvironment,
			final AllProjects allProjects) {
		Args.notNull(logger, "logger");
		Args.notNull(buildOptions, "buildOptions");
		Args.notNull(buildEnvironment, "buildEnvironment");
		Args.notNull(allProjects, "allProjects");
		
		this.logger = logger;
		this.buildOptions = buildOptions;
		this.buildEnvironment = buildEnvironment;
		this.allProjects = allProjects;
	}
	
	public SubProjectBuilder getSubProjectBuilder(final SubProject subProject, final Map<String, Object> parentConfiguration) {
		BuildConfiguration buildConfiguration = new BuildConfiguration(parentConfiguration);
		buildConfiguration.load(subProject.getPath()+"/"+buildOptions.buildConfigFile());
		
		TaskRepository taskRepository = new TaskRepository();
		TaskRunner taskRunner = new TaskRunner();
		TaskGraph taskgraph = new TaskGraph(taskRepository, taskRunner, buildConfiguration, logger);
		FastClasspathClasspathScanner classpathScanner = new FastClasspathClasspathScanner();
		PluginSystem pluginSystem = new PluginSystem(taskgraph, taskRepository, classpathScanner, allProjects, buildEnvironment, logger);
		
		return new SubProjectBuilder(subProject, buildOptions, buildConfiguration, taskgraph, classpathScanner, pluginSystem, this);
	}
	
	public SubProjects createSubProjects(final ProjectType projectType) {
		return new SubProjects(this, logger, projectType);
	}
}
