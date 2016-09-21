package org.ravenbuild.projectinfo;

import net.davidtanzer.jdefensive.Args;

import java.io.File;

public class ProjectInfoLoader {
	private final ProjectInfo projectInfo = new ProjectInfo();
	
	public ProjectInfoLoader() {
		String defaultName = new File("").getAbsoluteFile().getName();
		
		projectInfo.setProjectName(defaultName);
		projectInfo.setProjectGroup(defaultName);
		projectInfo.setProjectVersion("UNKNOWN_VERSION");
	}
	
	public ProjectInfoLoader(final ProjectInfo parent) {
		this();
		Args.notNull(parent, "parent");
		
		projectInfo.setParent(parent);
		projectInfo.setProjectGroup(parent.getProjectGroup());
	}
	
	public ProjectInfo projectInfo() {
		return projectInfo;
	}
	
	public void loadProjectInfo(final String basePath) {
		
	}
}
