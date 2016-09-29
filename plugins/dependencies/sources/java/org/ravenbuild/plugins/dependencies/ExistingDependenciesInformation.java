package org.ravenbuild.plugins.dependencies;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.environment.BuildEnvironment;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class ExistingDependenciesInformation {
	private final BuildEnvironment buildEnvironment;
	
	private Map<String, List<ExistingDependency>> existingDependencies;
	private Map<String, ExistingDependency> existingDependenciesByArtifactId = new HashMap<>();
	
	public ExistingDependenciesInformation(final BuildEnvironment buildEnvironment) {
		Args.notNull(buildEnvironment, "buildEnvironment");
		
		this.buildEnvironment = buildEnvironment;
	}
	
	void load() {
		Reader existingDependenciesConfigReader = buildEnvironment.readFile("/.raven/libraries/dependency-versions.json");
		
		loadConfiguration(existingDependenciesConfigReader);
		processExistingDependencies();
	}
	
	private void loadConfiguration(final Reader existingDependenciesConfigReader) {
		Gson gson = new Gson();
		
		existingDependencies = gson.fromJson(existingDependenciesConfigReader, new TypeToken<Map<String, List<ExistingDependency>>>() {}.getType());
	}
	
	private void processExistingDependencies() {
		assert existingDependencies != null : "Existing dependencies must have been loaded at this point.";
		
		for(List<ExistingDependency> dependencies : existingDependencies.values()) {
			for(ExistingDependency dependency : dependencies) {
				existingDependenciesByArtifactId.put(dependency.getId(), dependency);
			}
		}
	}
	
	public Dependency getDependency(final String artifactId) {
		if(existingDependenciesByArtifactId.containsKey(artifactId)) {
			ExistingDependency existingDependency = existingDependenciesByArtifactId.get(artifactId);
			Dependency dependency = new Dependency(artifactId, buildEnvironment.getFile("/.raven/libraries/" + existingDependency.getFile()), Optional.empty(), existingDependency.getDependencies());
			return dependency;
		}
		return null;
	}
}
