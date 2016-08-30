package org.ravenbuild;

import org.ravenbuild.classpath.FastClasspathClasspathScanner;
import org.ravenbuild.config.BuildConfiguration;
import org.ravenbuild.logging.Logger;
import org.ravenbuild.plugins.ClasspathScanner;
import org.ravenbuild.plugins.PluginSystem;
import org.ravenbuild.tasks.TaskGraph;
import org.ravenbuild.tasks.TaskRepository;
import org.ravenbuild.tasks.TaskRunner;

public class RavenBuild {
	private final PluginSystem pluginSystem;
	private final TaskGraph taskgraph;
	private final ClasspathScanner classpathScanner;
	private final BuildOptions buildOptions;
	private final Logger logger;
	
	public RavenBuild(final BuildOptions buildOptions) {
		this.buildOptions = buildOptions;
		
		logger = new Logger(LogLevel.DEFAULT);
		final TaskRepository taskRepository = new TaskRepository();
		final TaskRunner taskRunner = new TaskRunner();
		taskgraph = new TaskGraph(taskRepository, taskRunner, logger);
		classpathScanner = new FastClasspathClasspathScanner();
		pluginSystem = new PluginSystem(taskgraph, taskRepository, classpathScanner, logger);
	}
	
	public void run() {
		BuildConfiguration buildConfiguration = new BuildConfiguration();
		pluginSystem.loadPlugins();
		
		taskgraph.run(buildOptions.task(), buildOptions.taskOptions());
	}
	
	public static void main(final String[] args) {
		BuildOptions buildOptions = BuildOptions.parseFrom(args);
		final RavenBuild build = new RavenBuild(buildOptions);
		build.run();
	}
}
