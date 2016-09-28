package org.ravenbuild.plugins.ides.intellij;

import org.junit.Test;
import org.ravenbuild.environment.BuildEnvironment;
import org.ravenbuild.environment.FileWriterHandler;
import org.ravenbuild.projectinfo.AllProjects;
import org.ravenbuild.projectinfo.ProjectInfo;
import org.ravenbuild.tasks.EmptyTaskOptions;
import org.ravenbuild.tasks.TaskContext;

import java.util.Optional;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class IntelliJTaskTest {
	@Test
	public void dependsOnDependenciesTask() {
		IntelliJTask task = new IntelliJTask(mock(BuildEnvironment.class), mock(AllProjects.class));
		
		TaskContext taskContext = mock(TaskContext.class);
		task.initialize(taskContext);
		
		verify(taskContext).dependsOn("dependencies");
	}
	
	@Test
	public void writesImlFileInCurrentDirectory() {
		BuildEnvironment buildEnvironment = mock(BuildEnvironment.class, RETURNS_DEEP_STUBS);
		IntelliJTask task = new IntelliJTask(buildEnvironment, mock(AllProjects.class));
		TaskContext taskContext = mock(TaskContext.class);
		ProjectInfo projectInfo = mock(ProjectInfo.class);
		when(projectInfo.getParent()).thenReturn(Optional.empty());
		when(projectInfo.getProjectName()).thenReturn("project-name");
		when(taskContext.projectInfo()).thenReturn(projectInfo);
		task.initialize(taskContext);
		
		task.run(mock(EmptyTaskOptions.class));
		
		verify(buildEnvironment).writeFile(eq("project-name.iml"), any(FileWriterHandler.class));
	}
	
	@Test
	public void writesCompilerXmlInRootProject() {
		BuildEnvironment buildEnvironment = mock(BuildEnvironment.class, RETURNS_DEEP_STUBS);
		IntelliJTask task = new IntelliJTask(buildEnvironment, mock(AllProjects.class));
		TaskContext taskContext = mock(TaskContext.class);
		ProjectInfo projectInfo = mock(ProjectInfo.class);
		when(projectInfo.getParent()).thenReturn(Optional.empty());
		when(projectInfo.getProjectName()).thenReturn("project-name");
		when(taskContext.projectInfo()).thenReturn(projectInfo);
		task.initialize(taskContext);
		
		task.run(mock(EmptyTaskOptions.class));
		
		verify(buildEnvironment).writeFile(eq(".idea/compiler.xml"), any(FileWriterHandler.class));
	}
	
	@Test
	public void writesModulesXmlInRootProject() {
		BuildEnvironment buildEnvironment = mock(BuildEnvironment.class, RETURNS_DEEP_STUBS);
		IntelliJTask task = new IntelliJTask(buildEnvironment, mock(AllProjects.class));
		TaskContext taskContext = mock(TaskContext.class);
		ProjectInfo projectInfo = mock(ProjectInfo.class);
		when(projectInfo.getParent()).thenReturn(Optional.empty());
		when(projectInfo.getProjectName()).thenReturn("project-name");
		when(taskContext.projectInfo()).thenReturn(projectInfo);
		task.initialize(taskContext);
		
		task.run(mock(EmptyTaskOptions.class));
		
		verify(buildEnvironment).writeFile(eq(".idea/modules.xml"), any(FileWriterHandler.class));
	}
	
	@Test
	public void writesMiscXmlInRootProject() {
		BuildEnvironment buildEnvironment = mock(BuildEnvironment.class, RETURNS_DEEP_STUBS);
		IntelliJTask task = new IntelliJTask(buildEnvironment, mock(AllProjects.class));
		TaskContext taskContext = mock(TaskContext.class);
		ProjectInfo projectInfo = mock(ProjectInfo.class);
		when(projectInfo.getParent()).thenReturn(Optional.empty());
		when(projectInfo.getProjectName()).thenReturn("project-name");
		when(taskContext.projectInfo()).thenReturn(projectInfo);
		task.initialize(taskContext);
		
		task.run(mock(EmptyTaskOptions.class));
		
		verify(buildEnvironment).writeFile(eq(".idea/misc.xml"), any(FileWriterHandler.class));
	}
	
	@Test
	public void doesNotWriteDotIdeaFolderInSubProjects() {
		BuildEnvironment buildEnvironment = mock(BuildEnvironment.class);
		IntelliJTask task = new IntelliJTask(buildEnvironment, mock(AllProjects.class));
		TaskContext taskContext = mock(TaskContext.class);
		ProjectInfo projectInfo = mock(ProjectInfo.class);
		when(projectInfo.getProjectName()).thenReturn("project-name");
		when(projectInfo.getParent()).thenReturn(Optional.of(mock(ProjectInfo.class)));
		when(taskContext.projectInfo()).thenReturn(projectInfo);
		task.initialize(taskContext);
		
		task.run(mock(EmptyTaskOptions.class));
		
		verify(buildEnvironment, never()).writeFile(eq(".idea/compiler.xml"), any(FileWriterHandler.class));
	}
}
