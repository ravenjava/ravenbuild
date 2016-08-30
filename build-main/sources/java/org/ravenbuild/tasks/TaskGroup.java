package org.ravenbuild.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskGroup {
	private final String name;
	private final List<NamedTask> tasks = new ArrayList<>();
	
	public TaskGroup(final String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public List<NamedTask> tasksInGroup() {
		return Collections.unmodifiableList(tasks);
	}
	
	public void addTask(final String taskName, final Task task) {
		tasks.add(new NamedTask(taskName, task));
	}
	
	public static class NamedTask {
		private final String taskName;
		private final Task task;
		
		public NamedTask(final String taskName, final Task task) {
			this.taskName = taskName;
			this.task = task;
		}
		
		public Task task() {
			return task;
		}
		
		public String taskName() {
			return taskName;
		}
	}
}
