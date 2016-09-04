package org.ravenbuild.tasks;

import net.davidtanzer.jdefensive.Args;
import net.davidtanzer.jdefensive.Assert;

import java.util.*;

public class TaskRepository {
	private final Map<String, TaskInfo> tasks = new HashMap<>();
	
	private final List<TaskGroup> allTaskGroups = new ArrayList<>();
	private final Map<String, TaskGroup> taskGroupsByName = new HashMap<>();
	
	public TaskInfo findTask(final String taskName) {
		if (tasks.containsKey(taskName)) {
			return tasks.get(taskName);
		}
	
		return null;
	}
	
	public void add(final String taskName, final TaskInfo taskInfo, final String taskGroupName) {
		taskInfo.setTaskName(taskName);
		tasks.put(taskName, taskInfo);
		
		final TaskGroup group = findOrAddTaskGroup(taskGroupName);
		group.addTask(taskName, taskInfo.getTask());
	}
	
	public List<TaskGroup> allTaskGroups() {
		return allTaskGroups;
	}
	
	public List<TaskInfo> allTasks() {
		return Collections.unmodifiableList(new ArrayList<>(tasks.values()));
	}
	
	public static class TaskInfo {
		private final Task task;
		private final Class<?> taskOptionsType;
		private List<TaskInfo> dependencies = new ArrayList<>();
		private String taskName;
		
		public TaskInfo(final Task task, final Class<?> taskOptionsType) {
			Args.notNull(task, "task");
			Args.notNull(taskOptionsType, "taskOptionsType");
			
			this.task = task;
			this.taskOptionsType = taskOptionsType;
		}
		
		public Task getTask() {
			return task;
		}
		
		public Class<?> getTaskOptionsType() {
			return taskOptionsType;
		}
		
		public List<TaskInfo> getDependencies() {
			return Collections.unmodifiableList(dependencies);
		}
		
		public void addDependency(final TaskInfo dependencyInfo) {
			dependencies.add(dependencyInfo);
		}
		
		public String getTaskName() {
			return taskName;
		}
		
		private void setTaskName(final String taskName) {
			this.taskName = taskName;
		}
	}
	
	private TaskGroup findOrAddTaskGroup(final String taskGroupName) {
		addTaskGroupIfMissing(taskGroupName);
		return taskGroupsByName.get(taskGroupName);
	}
	
	private void addTaskGroupIfMissing(final String taskGroupName) {
		if(!taskGroupsByName.containsKey(taskGroupName)) {
			final TaskGroup group = new TaskGroup(taskGroupName);
			taskGroupsByName.put(taskGroupName, group);
			allTaskGroups.add(group);
		}
	}
	
}
