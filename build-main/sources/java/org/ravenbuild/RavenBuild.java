package org.ravenbuild;

import org.ravenbuild.logging.Logger;
import org.ravenbuild.projectinfo.AllProjects;
import org.ravenbuild.subprojects.ProjectType;
import org.ravenbuild.subprojects.SubProjectsFactory;
import org.ravenbuild.subprojects.SubProjects;

import java.util.Arrays;
import java.util.HashMap;

public class RavenBuild {
	private final BuildOptions buildOptions;
	private final Logger logger;
	private final SubProjectsFactory subProjectsFactory;
	private final AllProjects allProjects;
	
	public RavenBuild(final BuildOptions buildOptions) {
		this.buildOptions = buildOptions;
		
		logger = new Logger(buildOptions.logLevel());
		allProjects = new AllProjects();
		subProjectsFactory = new SubProjectsFactory(logger, buildOptions, allProjects);
	}
	
	public void run() {
		SubProjects subProjects = subProjectsFactory.createSubProjects(ProjectType.MAIN_PROJECT);
		subProjects.load(new HashMap<String, Object>() {{
			put("list", Arrays.asList("."));
		}}, null);
		subProjects.runInAll(buildOptions.task(), buildOptions.taskOptions());
	}
	
	public static void main(final String[] args) {
		BuildOptions buildOptions = BuildOptions.parseFrom(args);
		final RavenBuild build = new RavenBuild(buildOptions);
		build.run();
	}
}
