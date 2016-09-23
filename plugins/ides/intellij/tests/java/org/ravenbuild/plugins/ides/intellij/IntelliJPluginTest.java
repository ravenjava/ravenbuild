package org.ravenbuild.plugins.ides.intellij;

import org.junit.Test;
import org.ravenbuild.environment.BuildEnvironment;
import org.ravenbuild.plugins.PluginContext;
import org.ravenbuild.tasks.EmptyTaskOptions;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class IntelliJPluginTest {
	@Test
	public void registersIntelliJTask() {
		IntelliJPlugin plugin = new IntelliJPlugin();
		
		PluginContext context = mock(PluginContext.class);
		when(context.buildEnvironment()).thenReturn(mock(BuildEnvironment.class));
		plugin.initialize(context);
		
		verify(context).registerTask(eq("intellij"), any(IntelliJTask.class), eq(EmptyTaskOptions.class), eq("IDEs"));
	}
}