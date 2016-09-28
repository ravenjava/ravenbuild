package org.ravenbuild.plugins.ides.intellij;

import org.ravenbuild.environment.FileWriterHandler;
import org.ravenbuild.plugins.ides.intellij.xml.Output;
import org.ravenbuild.plugins.ides.intellij.xml.project.Project;
import org.ravenbuild.plugins.ides.intellij.xml.project.ProjectRootManager;

import java.io.FileWriter;
import java.io.IOException;

public class MiscXmlWriter implements FileWriterHandler {
	@Override
	public void write(final FileWriter fileWriter) throws IOException {
		fileWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		
		Project project = new Project();
		ProjectRootManager projectRootManager = new ProjectRootManager("JDK_1_8");
		projectRootManager.add(new Output(new Output.Url("file://$PROJECT_DIR$/.raven/target")));
		project.addComponent(projectRootManager);
		
		fileWriter.write(project.render());
	}
}
