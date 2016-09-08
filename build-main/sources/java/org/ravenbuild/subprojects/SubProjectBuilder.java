package org.ravenbuild.subprojects;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.BuildOptions;
import org.ravenbuild.classpath.FastClasspathClasspathScanner;
import org.ravenbuild.config.BuildConfiguration;
import org.ravenbuild.plugins.PluginSystem;
import org.ravenbuild.tasks.TaskGraph;

import java.util.Map;

public class SubProjectBuilder {
	private final TaskGraph taskgraph;
	private final FastClasspathClasspathScanner classpathScanner;
	private final PluginSystem pluginSystem;
	private final BuildOptions buildOptions;
	private final BuildConfiguration buildConfiguration;
	
	public SubProjectBuilder(final BuildOptions buildOptions, final BuildConfiguration buildConfiguration, final TaskGraph taskgraph, final FastClasspathClasspathScanner classpathScanner, final PluginSystem pluginSystem) {
		Args.notNull(buildOptions, "buildOptions");
		Args.notNull(buildConfiguration, "buildConfiguration");
		Args.notNull(taskgraph, "taskgraph");
		Args.notNull(classpathScanner, "classpathScanner");
		Args.notNull(pluginSystem, "pluginSystem");
		
		this.buildOptions = buildOptions;
		this.buildConfiguration = buildConfiguration;
		this.taskgraph = taskgraph;
		this.classpathScanner = classpathScanner;
		this.pluginSystem = pluginSystem;
	}
	
	public void run(final String taskName, final Map<String, String> taskOptions) {
		pluginSystem.loadPlugins(buildConfiguration);
		
		taskgraph.run(taskName, taskOptions);
	}
}
