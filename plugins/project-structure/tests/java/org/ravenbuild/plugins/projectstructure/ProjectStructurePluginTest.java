package org.ravenbuild.plugins.projectstructure;

import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ProjectStructurePluginTest {
	@Test
	public void defaultProjectNameIsNameOfCurrentDirectory() {
		String expectedName = new File("").getAbsoluteFile().getName();
		
		ProjectStructurePlugin plugin = new ProjectStructurePlugin();
		
		assertThat(plugin.projectInfo().getProjectName(), is(expectedName));
	}
	
	@Test
	public void defaultProjectGroupIsSameAsProjectName() {
		String expectedName = new File("").getAbsoluteFile().getName();
		
		ProjectStructurePlugin plugin = new ProjectStructurePlugin();
		
		assertThat(plugin.projectInfo().getProjectGroup(), is(plugin.projectInfo().getProjectName()));
	}
}