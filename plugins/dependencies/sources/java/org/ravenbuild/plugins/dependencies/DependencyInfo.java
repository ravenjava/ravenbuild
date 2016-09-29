package org.ravenbuild.plugins.dependencies;

public class DependencyInfo {
	private String id;
	private String version;
	
	public DependencyInfo() {
	}
	
	DependencyInfo(final String id, final String version) {
		this.id = id;
		this.version = version;
	}
	
	public String getId() {
		return id;
	}
	
	public String getVersion() {
		return version;
	}
	
}
