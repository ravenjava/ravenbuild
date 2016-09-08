package org.ravenbuild.subprojects;

import org.ravenbuild.config.BuildConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubProjects {
	private List<SubProject> subProjects = new ArrayList<>();
	
	public SubProjects() {
	}
	
	public void load(final BuildConfiguration buildConfiguration) {
		Map<String, Object> configuration = buildConfiguration.getConfigurationFor("subprojects", Map.class);
	}
	
	public void run(final String task, final Map<String, String> taskOptions) {
		
	}
}
