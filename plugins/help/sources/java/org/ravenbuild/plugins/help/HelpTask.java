package org.ravenbuild.plugins.help;

import org.ravenbuild.LogLevel;
import org.ravenbuild.logging.Logger;
import org.ravenbuild.tasks.Task;
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
@TaskOptions(HelpTaskOptions.class)
public class HelpTask implements Task<HelpTaskOptions> {
	private final Logger logger;
	private final TaskRepository taskRepository;
	
	public HelpTask(final Logger logger, final TaskRepository taskRepository) {
		this.logger = logger;
		this.taskRepository = taskRepository;
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
		final Task task = taskRepository.findTask(taskName);
		//FIXME add null check - and add lots of tests
		
		final String shortDescription = getShortDescription(task);
		final String[] longDescription = getLongDescription(task);
		logger.log(LogLevel.DEFAULT, taskName, shortDescription);
		logger.log(LogLevel.DEFAULT, "===============================================",
				"==================================================");
		
		for(String descriptionLine : longDescription) {
			logger.log(LogLevel.DEFAULT, "", descriptionLine);
		}
		
		logger.log(LogLevel.DEFAULT, "--------------------------------------", padMessage("Parameters"));
		printTaskOptionsHelpFor(taskName, task);
	}
	
	private void printTaskOptionsHelpFor(final String taskName, final Task task) {
		final Class taskOptionsClass = task.getClass().getAnnotation(TaskOptions.class).value();
		
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
		logger.log(LogLevel.DEFAULT, "===============================================",
				"==================================================");
		
		final List<TaskGroup> taskGroups = taskRepository.allTaskGroups();
		for(TaskGroup taskGroup : taskGroups) {
			logger.log(LogLevel.DEFAULT, "----------------------------------------------", padMessage(taskGroup.getName() + " tasks"));
			
			for(TaskGroup.NamedTask task : taskGroup.tasksInGroup()) {
				final String shortDescription = getShortDescription(task.task());
				logger.log(LogLevel.DEFAULT, task.taskName(), shortDescription);
			}
		}
	}
	
	private String getShortDescription(final Task task) {
		if(!task.getClass().isAnnotationPresent(ShortDescription.class)) {
			return "";
		}
		
		final ShortDescription shortDescriptionAnnotation = task.getClass().getAnnotation(ShortDescription.class);
		return shortDescriptionAnnotation.value();
	}
	
	private String padMessage(final String message) {
		StringBuilder paddedMessageBuilder = new StringBuilder();
		paddedMessageBuilder.append(message);
		paddedMessageBuilder.append(" ");
		final int messageLength = paddedMessageBuilder.length();
		
		for(int i=messageLength; i<50; i++) {
			paddedMessageBuilder.append("-");
		}
		
		return paddedMessageBuilder.toString();
	}
	
	private void printGeneralHelp() {
		logger.log(LogLevel.DEFAULT, "Usage", "raven [raven options] taksName [task options]");
		logger.log(LogLevel.DEFAULT, "Raven Options", "There are no raven options yet.");
		logger.log(LogLevel.DEFAULT, "Task Options", "[option name]=[option value]");
		logger.log(LogLevel.DEFAULT, "", "If the taks option is a boolean option, you can omit the value (setting the option to true).");
		logger.log(LogLevel.DEFAULT, "", "e.g.: \"raven help listTasks\" is equivalent to \"raven help listTasks=true\".");
		logger.log(LogLevel.DEFAULT, "Help about Tasks", "\"raven help list-tasks\" lists all tasks.");
		logger.log(LogLevel.DEFAULT, "", "\"raven help task=[task name]\" shows the help for a specific task.");
	}
}
