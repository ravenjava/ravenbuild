package org.ravenbuild.plugins;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.config.BuildConfiguration;
import org.ravenbuild.logging.Logger;
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
	private List<BuildPlugin> allPlugins = new ArrayList<>();
	private HashSet activePluginIds = new HashSet<>();
	
	public PluginSystem(final TaskGraph taskgraph, final TaskRepository taskRepository, final ClasspathScanner classpathScanner, final Logger logger) {
		Args.notNull(taskgraph, "taskgraph");
		Args.notNull(taskRepository, "taskRepository");
		Args.notNull(classpathScanner, "classpathScanner");
		Args.notNull(logger, "logger");
		
		this.taskgraph = taskgraph;
		this.taskRepository = taskRepository;
		this.classpathScanner = classpathScanner;
		this.logger = logger;
	}
	
	public void loadPlugins(final BuildConfiguration buildConfiguration) {
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
	}
	
	<T extends BuildPlugin> T loadAndInitialize(final Class<T> pluginClass, final LoadAs loadAs) {
		try {
			final T plugin = pluginClass.newInstance();
			final String pluginId = plugin.getId();
			boolean activePlugin = false;
			
			if(activePluginIds.contains(pluginId)) {
				activePluginIds.remove(pluginId);
				activePlugin = true;
			}
			
			if(activePlugin || loadAs == LoadAs.DEPENDENCY) {
				final PluginContext pluginContext = new DefaultPluginContext(this, taskgraph, taskRepository, logger);
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
