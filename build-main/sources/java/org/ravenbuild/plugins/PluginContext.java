package org.ravenbuild.plugins;

import org.ravenbuild.logging.Logger;
import org.ravenbuild.tasks.Task;
import org.ravenbuild.tasks.TaskGraph;

public interface PluginContext {
	void registerTask(String name, Task task, Class<?> taskOptionsType);
	
	Logger logger();
	
	TaskGraph taskGraph();
}
