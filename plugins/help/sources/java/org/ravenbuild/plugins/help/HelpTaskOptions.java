package org.ravenbuild.plugins.help;

public class HelpTaskOptions {
	@LongDescription({
		"Use this option to List all tasks that raven can execute for the current build.",
		"Cannot be combined with other options for the help task."
	})
	private boolean listTasks;
	
	@LongDescription({
		"Pass a task name to this option to get help for a specific task in the current build.",
		"Cannot be combined with other options for the help task."
	})
	@ExampleValue("help")
	private String task;
	
	public String getTask() {
		return task;
	}
	public boolean isListTasks() {
		return listTasks;
	}
}
