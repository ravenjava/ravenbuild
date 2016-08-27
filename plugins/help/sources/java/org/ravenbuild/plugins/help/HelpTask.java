package org.ravenbuild.plugins.help;

import org.ravenbuild.LogLevel;
import org.ravenbuild.logging.Logger;
import org.ravenbuild.tasks.Task;
import org.ravenbuild.tasks.TaskGraph;
import org.ravenbuild.tasks.TaskGroup;

import java.util.List;

public class HelpTask implements Task<HelpTaskOptions> {
	private final Logger logger;
	private final TaskGraph taskGraph;
	
	public HelpTask(final Logger logger, final TaskGraph taskGraph) {
		this.logger = logger;
		this.taskGraph = taskGraph;
	}
	
	@Override
	public void run(final HelpTaskOptions helpTaskOptions) {
		if(helpTaskOptions.isListTasks()) {
			printTaskList();
		} else {
			printGeneralHelp();
		}
	}
	
	private void printTaskList() {
		logger.log(LogLevel.DEFAULT, "Task", "Description:");
		logger.log(LogLevel.DEFAULT, "===============================================",
				"===============================================");
		
		final List<TaskGroup> taskGroups = taskGraph.getAllTaskGroups();
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
