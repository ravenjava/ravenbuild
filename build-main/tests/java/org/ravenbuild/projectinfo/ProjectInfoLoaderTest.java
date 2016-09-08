package org.ravenbuild.projectinfo;

import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ProjectInfoLoaderTest {
	@Test
	public void defaultProjectNameIsNameOfCurrentDirectory() {
		String expectedName = new File("").getAbsoluteFile().getName();
		
		ProjectInfoLoader projectInfoLoader = new ProjectInfoLoader();
		
		assertThat(projectInfoLoader.projectInfo().getProjectName(), is(expectedName));
	}
	
	@Test
	public void defaultProjectGroupIsSameAsProjectName() {
		String expectedName = new File("").getAbsoluteFile().getName();
		
		ProjectInfoLoader projectInfoLoader = new ProjectInfoLoader();
		
		assertThat(projectInfoLoader.projectInfo().getProjectGroup(), is(projectInfoLoader.projectInfo().getProjectName()));
	}
	
}