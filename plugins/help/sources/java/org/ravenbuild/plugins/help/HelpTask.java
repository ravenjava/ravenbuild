package org.ravenbuild.plugins.help;

import org.ravenbuild.LogLevel;
import org.ravenbuild.logging.Logger;
import org.ravenbuild.tasks.Task;
import org.ravenbuild.tasks.TaskContext;
import org.ravenbuild.tasks.TaskGroup;
import org.ravenbuild.tasks.TaskRepository;

import java.lang.reflect.Field;
import java.util.List;

@ShortDescription("Prints a general help message, help for a task or the task list. Run \"raven help task=help\" for more info.")
@LongDescription({
		"Use the help task to find out how to use \"raven\" in general, how to ",
		"use a specific taks or to find out which tasks exist in your current ",
		"build.",
		"",
		"Call \"raven help\" with no extra parameters to get a general help message."
})
public class HelpTask implements Task<HelpTaskOptions> {
	private final Logger logger;
	private final TaskRepository taskRepository;
	
	public HelpTask(final Logger logger, final TaskRepository taskRepository) {
		this.logger = logger;
		this.taskRepository = taskRepository;
	}
	
	@Override
	public void initialize(final TaskContext taskContext) {
		
	}
	
	@Override
	public void run(final HelpTaskOptions helpTaskOptions) {
		if(helpTaskOptions.isListTasks()) {
			printTaskList();
		} else if(helpTaskOptions.getTask() != null) {
			printTaskHelp(helpTaskOptions.getTask());
		} else {
			printGeneralHelp();
		}
	}
	
	private void printTaskHelp(final String taskName) {
		final TaskRepository.TaskInfo taskInfo = taskRepository.findTask(taskName);
		//FIXME add null check - and add lots of tests
		
		final String shortDescription = getShortDescription(taskInfo.getTask());
		final String[] longDescription = getLongDescription(taskInfo.getTask());
		logger.log(LogLevel.DEFAULT, taskName, shortDescription);
		logger.logMajorSeparator(LogLevel.DEFAULT);
		
		for(String descriptionLine : longDescription) {
			logger.log(LogLevel.DEFAULT, "", descriptionLine);
		}
		
		logger.logSeparator(LogLevel.DEFAULT, "Parameters");
		printTaskOptionsHelpFor(taskName, taskInfo.getTaskOptionsType());
	}
	
	private void printTaskOptionsHelpFor(final String taskName, final Class<?> taskOptionsClass) {
		final Field[] fields = taskOptionsClass.getDeclaredFields();
		for(Field field : fields) {
			final String fieldName = field.getName();
			final String[] longDescritption = field.getAnnotation(LongDescription.class).value();
			
			String example = "";
			if(!(field.getType().equals(boolean.class) || field.getType().equals(Boolean.class))) {
				example = "="+field.getAnnotation(ExampleValue.class).value();
			}
			logger.log(LogLevel.DEFAULT, fieldName, "["+field.getType().getSimpleName()+"], e.g. \"raven "+taskName+" "+fieldName+example+"\".");
			for(String descriptionLine : longDescritption) {
				logger.log(LogLevel.DEFAULT, "", descriptionLine);
			}
		}
	}
	
	private String[] getLongDescription(final Task task) {
		if(!task.getClass().isAnnotationPresent(LongDescription.class)) {
			return new String[] {};
		}
		
		final LongDescription longDescriptionAnnotation = task.getClass().getAnnotation(LongDescription.class);
		return longDescriptionAnnotation.value();
	}
	
	private void printTaskList() {
		logger.log(LogLevel.DEFAULT, "Task", "Description:");
		logger.logMajorSeparator(LogLevel.DEFAULT);
		
		final List<TaskGroup> taskGroups = taskRepository.allTaskGroups();
		for(TaskGroup taskGroup : taskGroups) {
			if(taskGroup.getName().equals("Internal")) {
				continue;
			}
			logger.logSeparator(LogLevel.DEFAULT, taskGroup.getName() + " tasks");
			
			for(TaskGroup.NamedTask task : taskGroup.tasksInGroup()) {
				final String shortDescription = getShortDescription(task.task());
				logger.log(LogLevel.DEFAULT, task.taskName(), shortDescription);
			}
			
			logger.logNewLine(LogLevel.DEFAULT);
		}
	}
	
	private String getShortDescription(final Task task) {
		if(!task.getClass().isAnnotationPresent(ShortDescription.class)) {
			return "";
		}
		
		final ShortDescription shortDescriptionAnnotation = task.getClass().getAnnotation(ShortDescription.class);
		return shortDescriptionAnnotation.value();
	}
	
	private void printGeneralHelp() {
		logger.log(LogLevel.DEFAULT, "Usage", "raven [raven options] taksName [task options]");
		logger.log(LogLevel.DEFAULT, "Raven Options", "-[option]");
		logger.log(LogLevel.DEFAULT, "-dry", "Dry Run - Attempt to run the build in a way that it does not modify the project.");
		logger.log(LogLevel.DEFAULT, "-v", "Verbose - Prints more output. Use this to debug your build.");
		logger.log(LogLevel.DEFAULT, "-vv", "Very Verbose - Prints even more output than verbose. Use this to debug your build.");
		logger.log(LogLevel.DEFAULT, "-D", "Debug - Prints even more output that will be only interesting to the raven developers or plugin developers.");
		logger.log(LogLevel.DEFAULT, "Task Options", "[option name]=[option value]");
		logger.log(LogLevel.DEFAULT, "", "If the taks option is a boolean option, you can omit the value (setting the option to true).");
		logger.log(LogLevel.DEFAULT, "", "e.g.: \"raven help listTasks\" is equivalent to \"raven help listTasks=true\".");
		logger.log(LogLevel.DEFAULT, "Help about Tasks", "\"raven help list-tasks\" lists all tasks.");
		logger.log(LogLevel.DEFAULT, "", "\"raven help task=[task name]\" shows the help for a specific task.");
	}
	
	@Override
	public boolean shouldRunInSubProjects() {
		return false;
	}
}
