package org.ravenbuild.projectinfo;

import java.io.File;

public class ProjectInfo {
	private String projectName;
	private String projectGroup;
	private String projectVersion;
	private File locationOnDisk;
	
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
	
	public File getLocationOnDisk() {
		return locationOnDisk;
	}
	
	void setLocationOnDisk(final File locationOnDisk) {
		this.locationOnDisk = locationOnDisk;
	}
}
