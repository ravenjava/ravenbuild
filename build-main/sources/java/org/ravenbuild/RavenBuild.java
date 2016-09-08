package org.ravenbuild;

import org.ravenbuild.classpath.FastClasspathClasspathScanner;
import org.ravenbuild.config.BuildConfiguration;
import org.ravenbuild.logging.Logger;
import org.ravenbuild.plugins.ClasspathScanner;
import org.ravenbuild.plugins.PluginSystem;
import org.ravenbuild.subprojects.SubProjectBuilderFactory;
import org.ravenbuild.subprojects.SubProjects;
import org.ravenbuild.tasks.TaskGraph;
import org.ravenbuild.tasks.TaskRepository;
import org.ravenbuild.tasks.TaskRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RavenBuild {
	private final PluginSystem pluginSystem;
	private final TaskGraph taskgraph;
	private final ClasspathScanner classpathScanner;
	private final BuildOptions buildOptions;
	private final Logger logger;
	
	public RavenBuild(final BuildOptions buildOptions) {
		this.buildOptions = buildOptions;
		
		logger = new Logger(buildOptions.logLevel());
		final TaskRepository taskRepository = new TaskRepository();
		final TaskRunner taskRunner = new TaskRunner();
		taskgraph = new TaskGraph(taskRepository, taskRunner, logger);
		classpathScanner = new FastClasspathClasspathScanner();
		pluginSystem = new PluginSystem(taskgraph, taskRepository, classpathScanner, logger);
	}
	
	public void run() {
		//BuildConfiguration buildConfiguration = new BuildConfiguration();
		//buildConfiguration.load(buildOptions.buildConfigFile());
		
		//pluginSystem.loadPlugins(buildConfiguration);
		
		SubProjectBuilderFactory subProjectBuilderFactory = new SubProjectBuilderFactory();
		SubProjects subProjects = new SubProjects(subProjectBuilderFactory, logger);
		subProjects.load(new HashMap<String, Object>() {{
			put("list", Arrays.asList("."));
		}});
		subProjects.runInAll(buildOptions.task(), buildOptions.taskOptions());

		//taskgraph.run(buildOptions.task(), buildOptions.taskOptions());
	}
	
	public static void main(final String[] args) {
		BuildOptions buildOptions = BuildOptions.parseFrom(args);
		final RavenBuild build = new RavenBuild(buildOptions);
		build.run();
	}
}
