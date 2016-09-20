package org.ravenbuild.projectinfo;

import net.davidtanzer.jdefensive.Args;

import java.io.File;
import java.util.Optional;

public class ProjectInfo {
	private Optional<ProjectInfo> parent = Optional.empty();
	private String projectName;
	private String projectGroup;
	private String projectVersion;
	private File locationOnDisk;
	
	public Optional<ProjectInfo> getParent() {
		return parent;
	}
	
	void setParent(final ProjectInfo parent) {
		Args.notNull(parent, "parent");
		
		this.parent = Optional.of(parent);
	}
	
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
