package org.ravenbuild.subprojects;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.BuildOptions;
import org.ravenbuild.classpath.FastClasspathClasspathScanner;
import org.ravenbuild.config.BuildConfiguration;
import org.ravenbuild.plugins.PluginSystem;
import org.ravenbuild.tasks.TaskGraph;

import java.util.Collections;
import java.util.Map;

public class SubProjectBuilder {
	private final TaskGraph taskgraph;
	private final FastClasspathClasspathScanner classpathScanner;
	private final PluginSystem pluginSystem;
	private final BuildOptions buildOptions;
	private final BuildConfiguration buildConfiguration;
	private final SubProjectsFactory subProjectsFactory;
	
	public SubProjectBuilder(final BuildOptions buildOptions, final BuildConfiguration buildConfiguration,
			final TaskGraph taskgraph, final FastClasspathClasspathScanner classpathScanner,
			final PluginSystem pluginSystem, final SubProjectsFactory subProjectsFactory) {
		Args.notNull(buildOptions, "buildOptions");
		Args.notNull(buildConfiguration, "buildConfiguration");
		Args.notNull(taskgraph, "taskgraph");
		Args.notNull(classpathScanner, "classpathScanner");
		Args.notNull(pluginSystem, "pluginSystem");
		Args.notNull(subProjectsFactory, "subProjectsFactory");
		
		this.buildOptions = buildOptions;
		this.buildConfiguration = buildConfiguration;
		this.taskgraph = taskgraph;
		this.classpathScanner = classpathScanner;
		this.pluginSystem = pluginSystem;
		this.subProjectsFactory = subProjectsFactory;
	}
	
	public void run(final String taskName, final Map<String, String> taskOptions) {
		pluginSystem.loadPlugins(buildConfiguration);
		
		SubProjects subProjects = subProjectsFactory.createSubProjects();
		Map subprojectsConfiguration = buildConfiguration.getConfigurationFor("subprojects", Map.class);
		if(subprojectsConfiguration == null) {
			subprojectsConfiguration = Collections.emptyMap();
		}
		subProjects.load(subprojectsConfiguration);
		subProjects.runInAll(taskName, taskOptions);
		
		taskgraph.run(taskName, taskOptions);
	}
}
