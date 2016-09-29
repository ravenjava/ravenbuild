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
	private final Map<String, DependenciesType> dependenciesTypes;
	private DependenciesConfiguration configuration;
	
	DependenciesTaskRunner(final ExistingDependenciesInformation dependenciesInfo, final AllProjects allProjects, final Map<String, DependenciesType> dependenciesTypes) {
		Args.notNull(dependenciesInfo, "dependenciesInfo");
		Args.notNull(allProjects, "allProjects");
		Args.notNull(dependenciesTypes, "dependenciesTypes");
		
		this.dependenciesInfo = dependenciesInfo;
		this.allProjects = allProjects;
		this.dependenciesTypes = dependenciesTypes;
	}
	
	void initialize(final DependenciesConfiguration configuration) {
		Args.notNull(configuration, "configuration");
		this.configuration = configuration;
		
		dependenciesInfo.load();
	}

	void initializeDependencies() {
		for(String configType : configuration.configurationTypes()) {
			List<String> dependencies = configuration.getDependenciesFor(configType);
			DependenciesType dependenciesType = dependenciesTypes.get(configType);
			for(String artifactId : dependencies) {
				ProjectInfo projectInfo = allProjects.findProject(artifactId);
				if(projectInfo != null) {
					allProjects.waitFor(projectInfo);
					dependenciesType.dependencyResolved(new Dependency(artifactId, projectInfo.getLocationOnDisk(), Optional.empty()));
				} else {
					Dependency dependency = dependenciesInfo.getDependency(artifactId);
					if(dependency != null) {
						dependenciesType.dependencyResolved(dependency);
					}
				}
			}
		}
	}
}
