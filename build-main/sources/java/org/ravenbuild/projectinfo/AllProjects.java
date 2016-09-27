package org.ravenbuild.projectinfo;

import java.util.*;

public class AllProjects {
	private List<ProjectInfo> projectInfos = new ArrayList<>();
	private Map<String, ProjectInfo> projectInfosByAritfactId = new HashMap<>();
	
	public ProjectInfo findProject(final String artifactId) {
		return projectInfosByAritfactId.get(artifactId);
	}
	
	public void waitFor(final ProjectInfo project) {
	}
	
	public void addProjectInfo(final ProjectInfo projectInfo) {
		projectInfos.add(projectInfo);
		projectInfosByAritfactId.put(projectInfo.getProjectGroup()+":"+projectInfo.getProjectName(), projectInfo);
	}
	
	public List<ProjectInfo> projectInfos() {
		return Collections.unmodifiableList(projectInfos);
	}
}
