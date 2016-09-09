package org.ravenbuild.subprojects;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.BuildOptions;
import org.ravenbuild.classpath.FastClasspathClasspathScanner;
import org.ravenbuild.config.BuildConfiguration;
import org.ravenbuild.plugins.PluginSystem;
import org.ravenbuild.projectinfo.ProjectInfo;
import org.ravenbuild.projectinfo.ProjectInfoLoader;
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
	private final SubProject subProject;
	private ProjectInfoLoader projectInfoLoader;
	
	public SubProjectBuilder(final SubProject subProject, final BuildOptions buildOptions, final BuildConfiguration buildConfiguration,
			final TaskGraph taskgraph, final FastClasspathClasspathScanner classpathScanner,
			final PluginSystem pluginSystem, final SubProjectsFactory subProjectsFactory) {
		Args.notNull(subProject, "subProject");
		Args.notNull(buildOptions, "buildOptions");
		Args.notNull(buildConfiguration, "buildConfiguration");
		Args.notNull(taskgraph, "taskgraph");
		Args.notNull(classpathScanner, "classpathScanner");
		Args.notNull(pluginSystem, "pluginSystem");
		Args.notNull(subProjectsFactory, "subProjectsFactory");
		
		this.subProject = subProject;
		this.buildOptions = buildOptions;
		this.buildConfiguration = buildConfiguration;
		this.taskgraph = taskgraph;
		this.classpathScanner = classpathScanner;
		this.pluginSystem = pluginSystem;
		this.subProjectsFactory = subProjectsFactory;
		
		this.projectInfoLoader = new ProjectInfoLoader();
	}
	
	public void loadProjectInfo(final ProjectInfo parentProjectInfo) {
		if(parentProjectInfo != null) {
			projectInfoLoader = new ProjectInfoLoader(parentProjectInfo);
		}
		
		projectInfoLoader.loadProjectInfo(subProject.getPath());
	}
	
	public void run(final String taskName, final Map<String, String> taskOptions, final ProjectType projectType) {
		pluginSystem.loadPlugins(buildConfiguration);
		
		SubProjects subProjects = subProjectsFactory.createSubProjects(ProjectType.SUB_PROJECT);
		Map subprojectsConfiguration = buildConfiguration.getConfigurationFor("subprojects", Map.class);
		if(subprojectsConfiguration == null) {
			subprojectsConfiguration = Collections.emptyMap();
		}
		subProjects.load(subprojectsConfiguration, projectInfoLoader.projectInfo());
		subProjects.runInAll(taskName, taskOptions);
		
		taskgraph.run(taskName, taskOptions, projectType);
	}
}
