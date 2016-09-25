package org.ravenbuild.plugins.java;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.ravenbuild.plugins.PluginContext;
import org.ravenbuild.plugins.PluginEvents;
import org.ravenbuild.plugins.build.BuildProjectPlugin;
import org.ravenbuild.plugins.dependencies.DependenciesPlugin;
import org.ravenbuild.plugins.events.DependenciesResolvedEventListener;
import org.ravenbuild.plugins.ides.intellij.java.IntellijJavaPlugin;
import org.ravenbuild.plugins.ides.intellij.java.JavaProjectStructure;
import org.ravenbuild.plugins.java.dependencies.JavaDependenciesPlugin;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class JavaPluginTest {
	@Test
	public void dependsOnDependenciesPlugin() {
		JavaPlugin plugin = new JavaPlugin();
		final PluginContext pluginContext = mock(PluginContext.class);
		when(pluginContext.dependsOnPlugin(JavaDependenciesPlugin.class)).thenReturn(mock(JavaDependenciesPlugin.class));
		when(pluginContext.events()).thenReturn(mock(PluginEvents.class));
		
		plugin.initialize(pluginContext);
		
		verify(pluginContext).dependsOnPlugin(JavaDependenciesPlugin.class);
	}
	
	@Test
	public void dependsOnBuildPlugin() {
		JavaPlugin plugin = new JavaPlugin();
		final PluginContext pluginContext = mock(PluginContext.class);
		when(pluginContext.dependsOnPlugin(JavaDependenciesPlugin.class)).thenReturn(mock(JavaDependenciesPlugin.class));
		when(pluginContext.events()).thenReturn(mock(PluginEvents.class));
		
		plugin.initialize(pluginContext);
		
		verify(pluginContext).dependsOnPlugin(BuildProjectPlugin.class);
	}
	
	@Test
	public void optionallyDependsOnIntellijJavaPlugin() {
		JavaPlugin plugin = new JavaPlugin();
		final PluginContext pluginContext = mock(PluginContext.class);
		when(pluginContext.dependsOnPlugin(JavaDependenciesPlugin.class)).thenReturn(mock(JavaDependenciesPlugin.class));
		when(pluginContext.events()).thenReturn(mock(PluginEvents.class));
		
		plugin.initialize(pluginContext);
		
		verify(pluginContext).optionallyDependsOnPlugin(IntellijJavaPlugin.class);
	}
	
	@Test
	public void passesProjectStructureToIntellijJavaPlugin() {
		JavaPlugin plugin = new JavaPlugin();
		final PluginContext pluginContext = mock(PluginContext.class);
		when(pluginContext.dependsOnPlugin(JavaDependenciesPlugin.class)).thenReturn(mock(JavaDependenciesPlugin.class));
		IntellijJavaPlugin intellijPlugin = mock(IntellijJavaPlugin.class);
		when(pluginContext.optionallyDependsOnPlugin(IntellijJavaPlugin.class)).thenReturn(Optional.of(intellijPlugin));
		PluginEvents events = mock(PluginEvents.class);
		when(pluginContext.events()).thenReturn(events);
		
		plugin.initialize(pluginContext);
		ArgumentCaptor<DependenciesResolvedEventListener> captor = ArgumentCaptor.forClass(DependenciesResolvedEventListener.class);
		verify(events).onDependenciesResolved(captor.capture());
		captor.getValue().dependenciesResolved();
		
		verify(intellijPlugin).setProjectStructure(any(JavaProjectStructure.class));
	}
}