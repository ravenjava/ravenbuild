package org.ravenbuild.plugins.projectstructure;

import org.ravenbuild.plugins.BuildPlugin;
import org.ravenbuild.plugins.PluginContext;

import java.io.File;

public class ProjectStructurePlugin implements BuildPlugin {
	private final ProjectInfo projectInfo = new ProjectInfo();
	
	public ProjectStructurePlugin() {
		String defaultName = new File("").getAbsoluteFile().getName();
		
		projectInfo.setProjectName(defaultName);
		projectInfo.setProjectGroup(defaultName);
		projectInfo.setProjectVersion("UNKNOWN_VERSION");
	}
	
	@Override
	public void initialize(final PluginContext pluginContext) {
	}
	
	@Override
	public String getId() {
		return "org.ravenbuild.project-structure";
	}
	
	public ProjectInfo projectInfo() {
		return projectInfo;
	}
}
