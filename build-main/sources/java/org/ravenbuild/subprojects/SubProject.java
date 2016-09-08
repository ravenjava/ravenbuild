package org.ravenbuild.subprojects;

public class SubProject {
	private String path;
	
	public SubProject(final String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
	
	void setPath(final String path) {
		this.path = path;
	}
}
