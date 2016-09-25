package org.ravenbuild.projectinfo;

import net.davidtanzer.jdefensive.Args;

import java.io.File;

public class ProjectInfoLoader {
	private final ProjectInfo projectInfo = new ProjectInfo();
	
	public ProjectInfoLoader() {
		File buildDirectory = new File("");
		String defaultName = buildDirectory.getAbsoluteFile().getName();
		
		projectInfo.setProjectName(defaultName);
		projectInfo.setProjectGroup(defaultName);
		projectInfo.setProjectVersion("UNKNOWN_VERSION");
		projectInfo.setLocationOnDisk(buildDirectory);
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
		Args.notNull(basePath, "basePath");
		
		if(basePath.length() > 0 && !basePath.equals(".")) {
			File locationOnDisk = new File(basePath);
			String defaultName = locationOnDisk.getAbsoluteFile().getName();
			projectInfo.setProjectName(defaultName);
			projectInfo.setLocationOnDisk(locationOnDisk);
		}
	}
}
