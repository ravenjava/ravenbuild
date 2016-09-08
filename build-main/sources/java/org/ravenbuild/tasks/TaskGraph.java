package org.ravenbuild.tasks;

import org.ravenbuild.LogLevel;
import org.ravenbuild.logging.Logger;
import org.ravenbuild.subprojects.ProjectType;

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
	
	public void run(final String taskName, final Map<String, String> taskOptionsMap, final ProjectType projectType) {
		initializeAllTasks();
		
		final TaskRepository.TaskInfo taskInfo = taskRepository.findTask(taskName);
		
		if(taskInfo == null) {
			informAbout(TaskGraphEvent.TASK_NOT_FOUND, taskName, null);
			logger.log(LogLevel.ERROR, "Task not found", "The task \""+taskName+"\" does not exist.");
			return;
		}
		
		if(taskInfo.getTask().shouldRunInSubProjects() || projectType == ProjectType.MAIN_PROJECT) {
			runSingle(taskInfo, taskOptionsMap, "");
		}
	}
	
	private void runPrerequisitesFor(final TaskRepository.TaskInfo taskInfo, final Map<String, String> taskOptionsMap, final String taskGraphChain) {
		final List<TaskRepository.TaskInfo> prerequisites = taskInfo.getDependencies();
		
		for(TaskRepository.TaskInfo prerequesite : prerequisites) {
			runSingle(prerequesite, taskOptionsMap, taskGraphChain);
		}
	}
	
	private void runSingle(final TaskRepository.TaskInfo taskInfo, final Map<String, String> taskOptionsMap, final String taskGraphChain) {
		assert taskInfo != null : "This internal method must not be called with a null-taskInfo";
		assert taskOptionsMap != null : "This internal method must not be called with a null-taskOptionsMap";
		
		String newTaskGraphChain;
		if(taskGraphChain.isEmpty()) {
			newTaskGraphChain = taskInfo.getTaskName();
		} else {
			newTaskGraphChain = taskGraphChain + "->" + taskInfo.getTaskName();
		}
		runPrerequisitesFor(taskInfo, taskOptionsMap, newTaskGraphChain);
		
		final Task task = taskInfo.getTask();
		assert task != null : "Task repository does not even let us save a null-task.";
		final Class taskOptionsType = taskInfo.getTaskOptionsType();
		assert taskOptionsType != null : "Task repository does not even let us save a task without task options.";
		
		String taskGraphChainInfo = "";
		if(!taskGraphChain.isEmpty()) {
			taskGraphChainInfo = "("+taskGraphChain+"->"+taskInfo.getTaskName()+") ";
		}
		logger.or()
				.defaultLevel("Running task", taskInfo.getTaskName())
				.verbose("Running task", taskInfo.getTaskName()+" "+taskGraphChainInfo)
				.veryVerbose("Running task", taskInfo.getTaskName()+" "+taskGraphChainInfo+"with options: "+taskOptionsMap)
				.debug("Running task", taskInfo.getTaskName()+" "+taskGraphChainInfo+"with options: "+taskOptionsMap);

		taskRunner.run(task, taskOptionsType, taskOptionsMap);
	}
	
	private void initializeAllTasks() {
		final List<TaskRepository.TaskInfo> allTasks = taskRepository.allTasks();
		assert allTasks != null : "All tasks from task repository never returns null";
		
		for(TaskRepository.TaskInfo taskInfo : allTasks) {
			final TaskContext taskContext = new TaskContext(taskInfo, taskRepository);
			taskInfo.getTask().initialize(taskContext);
		}
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
