package org.ravenbuild.plugins.dependencies;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DependenciesTaskTest {
	@Test
	public void runsInitializeCommandWhenInitializeOptionIsSetIgnoringOtherOptions() {
		DependenciesTaskOptions options = mock(DependenciesTaskOptions.class);
		
		when(options.isInitialize()).thenReturn(true);
		when(options.isShowUpdates()).thenReturn(true);
		when(options.isTree()).thenReturn(true);
		when(options.isUpdate()).thenReturn(true);
		
		DependenciesTaskRunner taskRunner = mock(DependenciesTaskRunner.class);
		DependenciesTask task = new DependenciesTask(taskRunner);
		
		task.run(options);
		
		verify(taskRunner).initializeDependencies();
		verifyNoMoreInteractions(taskRunner);
	}
}