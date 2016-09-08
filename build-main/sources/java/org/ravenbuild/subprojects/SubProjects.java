package org.ravenbuild.subprojects;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.LogLevel;
import org.ravenbuild.logging.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubProjects {
	private final SubProjectBuilderFactory subProjectBuilderFactory;
	private final Logger logger;
	private List<SubProjectBuilder> subProjectBuilders = new ArrayList<>();
	
	public SubProjects(final SubProjectBuilderFactory subProjectBuilderFactory, final Logger logger) {
		Args.notNull(subProjectBuilderFactory, "subProjectBuilderFactory");
		Args.notNull(logger, "logger");
		
		this.subProjectBuilderFactory = subProjectBuilderFactory;
		this.logger = logger;
	}
	
	public void load(final Map<String, Object> configuration) {
		initializeSubProjects((List<String>) configuration.get("list"));
	}
	
	private void initializeSubProjects(final List<String> subProjectPaths) {
		for(String path : subProjectPaths) {
			logger.log(LogLevel.DEBUG, "Subprojects", "Initializing sub project builder for path \""+path+"\".");
			SubProject subProject = new SubProject(path);
			subProjectBuilders.add(subProjectBuilderFactory.getSubProjectBuilder(subProject));
		}
	}
	
	public void runInAll(final String task, final Map<String, String> taskOptions) {
		logger.log(LogLevel.DEBUG, "Subprojects", "Running task in all sub projects: \""+task+"\".");
		for(SubProjectBuilder builder : subProjectBuilders) {
			builder.run(task, taskOptions);
		}
	}
}
