package org.ravenbuild.tasks;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TaskRunnerTest {
	@Test
	public void eventuallyRunsTheGivenTask() {
		TaskRunner taskRunner = new TaskRunner();
		
		final Task task = mock(Task.class);
		taskRunner.run(task, TaskOptions.class, mock(Map.class));
		
		verify(task).run(any());
	}
	
	@Test
	public void fillsTaskOptionsFromOptionsMapCorrectly() {
		Map<String, String> optionsMap = new HashMap<>();
		optionsMap.put("booleanOption", "true");
		optionsMap.put("stringOption", "some value");
		optionsMap.put("integerOption", "5");
		
		TaskRunner taskRunner = new TaskRunner();
		
		final Task task = mock(Task.class);
		taskRunner.run(task, TaskOptions.class, optionsMap);
		
		final ArgumentCaptor<TaskOptions> captor = ArgumentCaptor.forClass(TaskOptions.class);
		verify(task).run(captor.capture());
		
		assertThat(captor.getValue().isBooleanOption(), is(true));
		assertThat(captor.getValue().getStringOption(), is("some value"));
		assertThat(captor.getValue().getIntegerOption(), is(5));
	}
	
	public static class TaskOptions {
		private boolean booleanOption;
		private String stringOption;
		private int integerOption;
		
		public boolean isBooleanOption() {
			return booleanOption;
		}
		public String getStringOption() {
			return stringOption;
		}
		public int getIntegerOption() {
			return integerOption;
		}
	}
}