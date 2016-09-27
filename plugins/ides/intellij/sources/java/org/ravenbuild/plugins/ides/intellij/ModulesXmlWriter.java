package org.ravenbuild.plugins.ides.intellij;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.environment.FileWriterHandler;
import org.ravenbuild.plugins.ides.intellij.xml.project.Modules;
import org.ravenbuild.plugins.ides.intellij.xml.project.Project;
import org.ravenbuild.plugins.ides.intellij.xml.project.ProjectModuleManager;
import org.ravenbuild.projectinfo.AllProjects;
import org.ravenbuild.projectinfo.ProjectInfo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ModulesXmlWriter implements FileWriterHandler {
	private final AllProjects allProjects;
	private final File buildBasePath;
	
	public ModulesXmlWriter(final AllProjects allProjects, final File buildBasePath) {
		Args.notNull(allProjects, "allProjects");
		Args.notNull(buildBasePath, "buildBasePath");
		
		this.allProjects = allProjects;
		this.buildBasePath = buildBasePath;
	}
	
	@Override
	public void write(final FileWriter fileWriter) throws IOException {
		fileWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		
		Modules modules = new Modules();
		for(ProjectInfo projectInfo : allProjects.projectInfos()) {
			String relativeModulePath = buildBasePath.toURI().relativize(projectInfo.getLocationOnDisk().toURI()).getPath();
			modules.addModule(relativeModulePath+projectInfo.getProjectName()+".iml");
		}
		
		Project project = new Project();
		ProjectModuleManager projectModuleManager = new ProjectModuleManager();
		projectModuleManager.addModules(modules);
		project.addComponent(projectModuleManager);
		
		fileWriter.write(project.render());
	}
}
