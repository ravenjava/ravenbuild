package org.ravenbuild.projectinfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllProjects {
	private List<ProjectInfo> projectInfos = new ArrayList<>();
	
	public ProjectInfo findProject(final String artifactId) {
		return null;
	}
	
	public void waitFor(final ProjectInfo project) {
	}
	
	public void addProjectInfo(final ProjectInfo projectInfo) {
		projectInfos.add(projectInfo);
	}
	
	public List<ProjectInfo> projectInfos() {
		return Collections.unmodifiableList(projectInfos);
	}
}
