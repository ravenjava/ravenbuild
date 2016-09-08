package org.ravenbuild.projectinfo;

public class ProjectInfo {
	private String projectName;
	private String projectGroup;
	private String projectVersion;
	
	public String getProjectName() {
		return projectName;
	}
	
	void setProjectName(final String projectName) {
		this.projectName = projectName;
	}
	
	public String getProjectGroup() {
		return projectGroup;
	}
	
	void setProjectGroup(final String projectGroup) {
		this.projectGroup = projectGroup;
	}
	
	public String getProjectVersion() {
		return projectVersion;
	}
	
	void setProjectVersion(final String projectVersion) {
		this.projectVersion = projectVersion;
	}
}
