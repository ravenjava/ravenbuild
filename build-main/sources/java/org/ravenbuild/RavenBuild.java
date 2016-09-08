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
	private final BuildOptions buildOptions;
	private final Logger logger;
	private final SubProjectBuilderFactory subProjectBuilderFactory;
	
	public RavenBuild(final BuildOptions buildOptions) {
		this.buildOptions = buildOptions;
		
		logger = new Logger(buildOptions.logLevel());
		subProjectBuilderFactory = new SubProjectBuilderFactory(logger, buildOptions);
	}
	
	public void run() {
		SubProjects subProjects = new SubProjects(subProjectBuilderFactory, logger);
		subProjects.load(new HashMap<String, Object>() {{
			put("list", Arrays.asList("."));
		}});
		subProjects.runInAll(buildOptions.task(), buildOptions.taskOptions());
	}
	
	public static void main(final String[] args) {
		BuildOptions buildOptions = BuildOptions.parseFrom(args);
		final RavenBuild build = new RavenBuild(buildOptions);
		build.run();
	}
}
