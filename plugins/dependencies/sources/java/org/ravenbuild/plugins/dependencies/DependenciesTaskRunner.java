package org.ravenbuild.plugins.dependencies;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.projectinfo.AllProjects;
import org.ravenbuild.projectinfo.ProjectInfo;

import java.util.List;
import java.util.Map;

class DependenciesTaskRunner {
	private final ExistingDependenciesInformation dependenciesInfo;
	private final AllProjects allProjects;
	private Map<String, List<String>> configuration;
	
	DependenciesTaskRunner(final ExistingDependenciesInformation dependenciesInfo, final AllProjects allProjects) {
		Args.notNull(dependenciesInfo, "dependenciesInfo");
		Args.notNull(allProjects, "allProjects");
		
		this.dependenciesInfo = dependenciesInfo;
		this.allProjects = allProjects;
	}
	
	void initialize(final Map<String, List<String>> configuration) {
		Args.notNull(configuration, "configuration");
		this.configuration = configuration;
		
		dependenciesInfo.load();
	}

	void initializeDependencies() {
		for(String configType : configuration.keySet()) {
			List<String> dependencies = configuration.get(configType);
			for(String artifactId : dependencies) {
				ProjectInfo projectInfo = allProjects.findProject(artifactId);
				
			}
		}
	}
}
