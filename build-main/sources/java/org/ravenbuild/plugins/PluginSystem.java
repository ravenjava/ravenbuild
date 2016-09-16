package org.ravenbuild.plugins;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.LogLevel;
import org.ravenbuild.config.BuildConfiguration;
import org.ravenbuild.environment.BuildEnvironment;
import org.ravenbuild.logging.Logger;
import org.ravenbuild.projectinfo.AllProjects;
import org.ravenbuild.tasks.TaskGraph;
import org.ravenbuild.tasks.TaskRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PluginSystem {
	private final TaskGraph taskgraph;
	private final ClasspathScanner classpathScanner;
	private final Logger logger;
	private final TaskRepository taskRepository;
	private final AllProjects allProjects;
	private final BuildEnvironment buildEnvironment;
	private List<BuildPlugin> allPlugins = new ArrayList<>();
	private HashSet activePluginIds = new HashSet<>();
	private BuildConfiguration buildConfiguration;
	
	public PluginSystem(final TaskGraph taskgraph, final TaskRepository taskRepository, final ClasspathScanner classpathScanner,
			final AllProjects allProjects, final BuildEnvironment buildEnvironment, final Logger logger) {
		Args.notNull(taskgraph, "taskgraph");
		Args.notNull(taskRepository, "taskRepository");
		Args.notNull(classpathScanner, "classpathScanner");
		Args.notNull(allProjects, "allProjects");
		Args.notNull(buildEnvironment, "buildEnvironment");
		Args.notNull(logger, "logger");
		
		this.taskgraph = taskgraph;
		this.taskRepository = taskRepository;
		this.classpathScanner = classpathScanner;
		this.allProjects = allProjects;
		this.buildEnvironment = buildEnvironment;
		this.logger = logger;
	}
	
	public void loadPlugins(final BuildConfiguration buildConfiguration) {
		Args.notNull(buildConfiguration, "buildConfiguration");
		this.buildConfiguration = buildConfiguration;
		
		populateActivePluginIds(buildConfiguration);
		final List<Class<? extends BuildPlugin>> allPluginClasses = classpathScanner.findAllClassesImplementing(BuildPlugin.class);
		
		for(Class<? extends BuildPlugin> pluginClass : allPluginClasses) {
			loadAndInitialize(pluginClass, LoadAs.ACTIVE_PLUGIN);
		}
	}
	
	private void populateActivePluginIds(final BuildConfiguration buildConfiguration) {
		List activePluginIdsList = buildConfiguration.getConfigurationFor("plugins", List.class);
		if(activePluginIdsList != null) {
			activePluginIds.addAll(activePluginIdsList);
		}
		activePluginIds.add("org.ravenbuild.help");
		activePluginIds.add("org.ravenbuild.project-structure");
	}
	
	<T extends BuildPlugin> T loadAndInitialize(final Class<T> pluginClass, final LoadAs loadAs) {
		try {
			final T plugin = pluginClass.newInstance();
			final String pluginId = plugin.getId();
			if(pluginId==null || pluginId.isEmpty()) {
				logger.log(LogLevel.ERROR, "Plugin System", "Plugin id is \"null\" for plugin "+pluginClass);
				throw new IllegalStateException("Plugin id is \"null\" for plugin "+pluginClass);
			}
			boolean activePlugin = false;
			
			if(activePluginIds.contains(pluginId)) {
				activePluginIds.remove(pluginId);
				activePlugin = true;
			}
			
			if(activePlugin || loadAs == LoadAs.DEPENDENCY) {
				logger.log(LogLevel.VERY_VERBOSE, "Loading Plugin", pluginId+", isActive="+activePlugin+", loading as: "+loadAs);
				final PluginContext pluginContext = new DefaultPluginContext(this, taskgraph, taskRepository, buildConfiguration, allProjects, buildEnvironment, logger);
				plugin.initialize(pluginContext);
				
				allPlugins.add(plugin);
				return plugin;
			}
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IllegalStateException("Could not instantiate build plugin \""+pluginClass.getName()+"\"", e);
		}
		return null;
	}
	
	public List<BuildPlugin> allPlugins() {
		return allPlugins;
	}
	
	static enum LoadAs {
		ACTIVE_PLUGIN, DEPENDENCY;
	}
}
