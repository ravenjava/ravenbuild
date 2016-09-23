package org.ravenbuild.plugins.java.intellij;

import org.junit.Test;
import org.ravenbuild.environment.BuildEnvironment;
import org.ravenbuild.plugins.PluginContext;
import org.ravenbuild.plugins.java.JavaPlugin;
import org.ravenbuild.tasks.EmptyTaskOptions;

import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class IntelliJPluginTest {
	@Test
	public void dependsOnJavaPlugin() {
		IntelliJPlugin plugin = new IntelliJPlugin();
		
		PluginContext context = mock(PluginContext.class);
		when(context.optionallyDependsOnPlugin(JavaPlugin.class)).thenReturn(Optional.of(mock(JavaPlugin.class)));
		when(context.buildEnvironment()).thenReturn(mock(BuildEnvironment.class));
		plugin.initialize(context);
		
		verify(context).optionallyDependsOnPlugin(JavaPlugin.class);
	}
	
	@Test
	public void registersIntelliJTask() {
		IntelliJPlugin plugin = new IntelliJPlugin();
		
		PluginContext context = mock(PluginContext.class);
		when(context.optionallyDependsOnPlugin(JavaPlugin.class)).thenReturn(Optional.of(mock(JavaPlugin.class)));
		when(context.buildEnvironment()).thenReturn(mock(BuildEnvironment.class));
		plugin.initialize(context);
		
		verify(context).registerTask(eq("intellij"), any(IntelliJTask.class), eq(EmptyTaskOptions.class), eq("IDEs"));
	}
}