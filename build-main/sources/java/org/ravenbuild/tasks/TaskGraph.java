package org.ravenbuild.tasks;

import org.ravenbuild.LogLevel;
import org.ravenbuild.logging.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskGraph {
	private final TaskRepository taskRepository;
	private final TaskRunner taskRunner;
	private final Logger logger;
	
	private final Map<TaskGraphEvent, List<TaskGraphListener>> listeners = new HashMap<>();
	
	public TaskGraph(final TaskRepository taskRepository, final TaskRunner taskRunner, final Logger logger) {
		this.taskRepository = taskRepository;
		this.taskRunner = taskRunner;
		this.logger = logger;
	}
	
	public void run(final String taskName, final Map<String, String> taskOptionsMap) {
		final Task task = taskRepository.findTask(taskName);
		
		if(task == null) {
			informAbout(TaskGraphEvent.TASK_NOT_FOUND, taskName, null);
			logger.log(LogLevel.ERROR, "Task not found", "The task \""+taskName+"\" does not exist.");
			return;
		}
		
		//TODO Maybe refactor so that task repository returns it's TaskInfo Object? Could make the code more simple here...
		final Class taskOptionsType = taskRepository.getTaskOptionsType(taskName);
		if(taskOptionsType == null) {
			throw new RuntimeException("Task options not found for task \""+taskName+"\". At this point, where we have already " +
					"found the task, this is an error: For every task we find in the task repository, we also have to " +
					"find task options.");
		}
		taskRunner.run(task, taskOptionsType, taskOptionsMap);
	}
	
	private void informAbout(final TaskGraphEvent event, final String taskName, final Task task) {
		if(listeners.containsKey(event)) {
			for(TaskGraphListener listener : listeners.get(event)) {
				listener.onTaskGraphEvent(event, taskName, task);
			}
		}
	}
	
	public void addTaskGraphListener(TaskGraphEvent event, TaskGraphListener listener) {
		if(!listeners.containsKey(event)) {
			listeners.put(event, new ArrayList<>());
		}
		
		listeners.get(event).add(listener);
	}
	
	public void registerTask(final String taskName, final Task task, final Class<?> taskOptionsType) {
		registerTask(taskName, task, taskOptionsType, "General");
	}
	
	public void registerTask(final String taskName, final Task task, final Class<?> taskOptionsType, String taskGroupName) {
		taskRepository.add(taskName, new TaskRepository.TaskInfo(task, taskOptionsType), taskGroupName);
	}
	
	public List<TaskGroup> getAllTaskGroups() {
		return taskRepository.allTaskGroups();
	}
}
