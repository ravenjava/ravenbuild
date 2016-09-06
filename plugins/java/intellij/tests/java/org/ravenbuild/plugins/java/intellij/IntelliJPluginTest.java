package org.ravenbuild.plugins.java.intellij;

import org.junit.Test;
import org.ravenbuild.plugins.PluginContext;
import org.ravenbuild.plugins.projectstructure.ProjectStructurePlugin;
import org.ravenbuild.tasks.EmptyTaskOptions;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class IntelliJPluginTest {
	@Test
	public void dependsOnProjectStructurePlugin() {
		IntelliJPlugin plugin = new IntelliJPlugin();
		
		PluginContext context = mock(PluginContext.class);
		when(context.dependsOnPlugin(ProjectStructurePlugin.class)).thenReturn(mock(ProjectStructurePlugin.class));
		plugin.initialize(context);
		
		verify(context).dependsOnPlugin(ProjectStructurePlugin.class);
	}
	
	@Test
	public void registersIntelliJTask() {
		IntelliJPlugin plugin = new IntelliJPlugin();
		
		PluginContext context = mock(PluginContext.class);
		ProjectStructurePlugin projectStructurePlugin = mock(ProjectStructurePlugin.class);
		when(context.dependsOnPlugin(ProjectStructurePlugin.class)).thenReturn(projectStructurePlugin);
		plugin.initialize(context);
		
		verify(context).registerTask(eq("intellij"), any(IntelliJTask.class), eq(EmptyTaskOptions.class), eq("Java"));
	}
}