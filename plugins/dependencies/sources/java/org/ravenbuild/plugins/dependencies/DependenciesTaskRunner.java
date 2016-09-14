package org.ravenbuild.plugins.dependencies;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.projectinfo.AllProjects;
import org.ravenbuild.projectinfo.ProjectInfo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

class DependenciesTaskRunner {
	private final ExistingDependenciesInformation dependenciesInfo;
	private final AllProjects allProjects;
	private final DependenciesHolder dependenciesHolder;
	private Map<String, List<String>> configuration;
	
	DependenciesTaskRunner(final ExistingDependenciesInformation dependenciesInfo, final AllProjects allProjects, final DependenciesHolder dependenciesHolder) {
		Args.notNull(dependenciesInfo, "dependenciesInfo");
		Args.notNull(allProjects, "allProjects");
		Args.notNull(dependenciesHolder, "dependenciesHolder");
		
		this.dependenciesInfo = dependenciesInfo;
		this.allProjects = allProjects;
		this.dependenciesHolder = dependenciesHolder;
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
				if(projectInfo != null) {
					allProjects.waitFor(projectInfo);
					dependenciesHolder.addDependency(new Dependency(artifactId, projectInfo.getLocationOnDisk(), Optional.empty()));
				} else {
					Dependency dependency = dependenciesInfo.getDependency(artifactId);
					if(dependency != null) {
						dependenciesHolder.addDependency(dependency);
					}
				}
			}
		}
	}
}
