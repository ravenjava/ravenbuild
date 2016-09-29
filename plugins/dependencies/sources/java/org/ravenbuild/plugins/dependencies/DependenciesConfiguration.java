package org.ravenbuild.plugins.dependencies;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class DependenciesConfiguration {
	private Map<String, List<String>> configValues;
	
	public Set<String> configurationTypes() {
		return configValues.keySet();
	}
	
	public List<String> getDependenciesFor(final String configType) {
		return configValues.get(configType);
	}
}
