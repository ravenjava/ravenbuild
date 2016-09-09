package org.ravenbuild.subprojects;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.LogLevel;
import org.ravenbuild.logging.Logger;

import java.util.*;

public class SubProjects {
	private final SubProjectsFactory subProjectBuilderFactory;
	private final Logger logger;
	private List<SubProjectBuilder> subProjectBuilders = new ArrayList<>();
	private final ProjectType projectType;
	
	public SubProjects(final SubProjectsFactory subProjectBuilderFactory, final Logger logger, final ProjectType projectType) {
		Args.notNull(subProjectBuilderFactory, "subProjectBuilderFactory");
		Args.notNull(logger, "logger");
		Args.notNull(projectType, "projectType");
		
		this.subProjectBuilderFactory = subProjectBuilderFactory;
		this.logger = logger;
		this.projectType = projectType;
	}
	
	public void load(final Map<String, Object> configuration) {
		List<String> subProjectPaths = (List<String>) configuration.get("list");
		if(subProjectPaths == null) {
			subProjectPaths = Collections.emptyList();
		}
		
		Map<String, Object> parentConfiguration = new HashMap<>(configuration);
		parentConfiguration.remove("list");
		initializeSubProjects(subProjectPaths, parentConfiguration);
	}
	
	private void initializeSubProjects(final List<String> subProjectPaths, final Map<String, Object> parentConfiguration) {
		assert subProjectPaths != null;
		
		for(String path : subProjectPaths) {
			logger.log(LogLevel.DEBUG, "Subprojects", "Initializing sub project builder for path \""+path+"\".");
			SubProject subProject = new SubProject(path);
			subProjectBuilders.add(subProjectBuilderFactory.getSubProjectBuilder(subProject, parentConfiguration));
		}
	}
	
	public void runInAll(final String task, final Map<String, String> taskOptions) {
		logger.log(LogLevel.DEBUG, "Subprojects", "Running task in all sub projects: \""+task+"\".");
		for(SubProjectBuilder builder : subProjectBuilders) {
			builder.run(task, taskOptions, projectType);
		}
	}
}
