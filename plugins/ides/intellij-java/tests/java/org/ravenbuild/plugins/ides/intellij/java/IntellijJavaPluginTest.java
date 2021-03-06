package org.ravenbuild.plugins.ides.intellij.java;

import org.junit.Test;
import org.ravenbuild.plugins.PluginContext;
import org.ravenbuild.plugins.ides.intellij.IntelliJPlugin;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IntellijJavaPluginTest {
	@Test
	public void dependsOnIntelliJPlugin() {
		IntellijJavaPlugin plugin = new IntellijJavaPlugin();
		PluginContext pluginContext = mock(PluginContext.class);
		when(pluginContext.dependsOnPlugin(IntelliJPlugin.class)).thenReturn(mock(IntelliJPlugin.class));
		
		plugin.initialize(pluginContext);
		
		verify(pluginContext).dependsOnPlugin(IntelliJPlugin.class);
	}
}